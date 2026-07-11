package com.example.hgn.trekgrop;

import com.example.hgn.common.ServerResponse;
import com.example.hgn.exception.BadRequestException;
import com.example.hgn.exception.NotFoundException;
import com.example.hgn.order.OrderEntity;
import com.example.hgn.order.OrderRepository;
import com.example.hgn.trekgrop.constant.TrekGroupType;
import com.example.hgn.trekgrop.dto.CreateTreakGroupRequest;
import com.example.hgn.trekgrop.dto.TrekGroupListResponseDTO;
import com.example.hgn.trekgrop.dto.UpdateTrekGroupRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;

@Service
public class TrekGroupService {

    private final TrekGroupRepository trekGroupRepository;
    private final OrderRepository orderRepository;

    public TrekGroupService(TrekGroupRepository trekGroupRepository, OrderRepository orderRepository){
        this.trekGroupRepository = trekGroupRepository;
        this.orderRepository = orderRepository;
    }

    public ServerResponse createTrekGroup(CreateTreakGroupRequest request){
        OrderEntity order = orderRepository.findByBookingNumber(request.getBookingNumber()).orElseThrow(()->new NotFoundException("Booking not found"));
        if(trekGroupRepository.existsByOrderId(order.getId())){
            throw new BadRequestException("This booking already has a trek group assigned");
        }
        TrekGroupEntity group = createTrekGroupEntity(order,request);
        group = trekGroupRepository.save(group);
        return ServerResponse.successResponse("group created successfully", HttpStatus.CREATED);
    }
    private TrekGroupEntity createTrekGroupEntity(OrderEntity order, CreateTreakGroupRequest request){
        TrekGroupEntity group = new TrekGroupEntity();
        group.setGroupName(request.getGroupName());
        group.setTrekGroupType(request.getTrekGroupType());
        group.setOrder(order);
        return group;
    }

    public ServerResponse updateTrekGroup(String id, UpdateTrekGroupRequest request){
        TrekGroupEntity group = trekGroupRepository.findById(id).orElseThrow(()->  new NotFoundException("trek group found found"));
        if(request.getGroupName() != null){
            group.setGroupName(request.getGroupName());
        }
        if(request.getTrekGroupType() != null){
            group.setTrekGroupType(request.getTrekGroupType());
        }
        trekGroupRepository.save(group);
        return  ServerResponse.successResponse("Trek group updated sucessfully", HttpStatus.OK);
    }

    public ServerResponse fetchAllTrekGroup(Map<String,String> filters, Pageable pageable){
        String groupName = filters.getOrDefault("groupName", null);
        TrekGroupType trekGroupType = null;
        if(filters.containsKey("trekGroupType")){
            trekGroupType = TrekGroupType.valueOf(filters.get("trekGroupType").toUpperCase());
        }
        Page<TrekGroupEntity> trekGroupResponse = trekGroupRepository.findAllGroup(groupName, trekGroupType, pageable);
        List<TrekGroupListResponseDTO> dtoList = trekGroupResponse.stream().map(this::mapToTrekGroupDTO).toList();
        if(!dtoList.isEmpty()){
            return ServerResponse.successObjectResponse("Trek group fetched successfully",HttpStatus.OK,dtoList,dtoList.size());
        }
        return ServerResponse.successObjectResponse("Trek group empty",HttpStatus.OK,List.of(),0);

    }
    private TrekGroupListResponseDTO mapToTrekGroupDTO(TrekGroupEntity trekGroupEntity){
        String bookingNumber = trekGroupEntity.getOrder().getBookingNumber();
        return new TrekGroupListResponseDTO(
                trekGroupEntity.getId(),
              trekGroupEntity.getGroupName(),
              trekGroupEntity.getTrekGroupType(),
              bookingNumber
        );
    }
}
