package com.chan.customer.controller;

import com.chan.customer.common.Message;
import com.chan.customer.common.StatusEnum;
import com.chan.customer.domain.Menu;
import com.chan.customer.domain.Order;
import com.chan.customer.dto.MenuDto;
import com.chan.customer.dto.OrderRequestDto;
import com.chan.customer.dto.OrderResponseDto;
import com.chan.customer.dto.OrderUpdateDto;
import com.chan.customer.exception.OrderRequestValidationFailedException;
import com.chan.customer.service.OrderService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/customer/order")
public class OrderController {

    private final OrderService orderService;

    private final ObjectMapper objectMapper;

    @GetMapping
    public ResponseEntity<Message> orders(@RequestParam String accountId,
                                          @RequestParam
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                          LocalDateTime start,
                                          @RequestParam
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                          LocalDateTime end
    ) {
        Message message = new Message();

        if(start.isAfter(end)){
            throw new OrderRequestValidationFailedException("검색 범위 불일치");
        }

        List<Order> orders = orderService.findOrder(accountId, start, end);

        message.setStatus(StatusEnum.OK);
        message.setMessage("주문 조회 성공");
        message.setData(orders);

        return ResponseEntity.ok().body(message);

    }
    @PostMapping
    public ResponseEntity<Message> requestOrder(@Valid @RequestBody OrderRequestDto orderDto, Errors errors) throws JsonProcessingException {

        Message message = new Message();

        if (errors.hasErrors()) {
            throw new OrderRequestValidationFailedException(objectMapper.writeValueAsString(errors));
        }

        List<Menu> menuList = orderDto
                .getMenuDtoList().stream()
                .map(MenuDto::toEntity)
                .collect(Collectors.toList());

        Order order = orderService.requestOrder(orderDto.getAccountId(), menuList);

        message.setStatus(StatusEnum.OK);
        message.setMessage("주문 성공");
        message.setData(new OrderResponseDto(order));

        return ResponseEntity.ok().body(message);
    }

    @PutMapping
    public ResponseEntity<Message> updateOrder(@RequestBody OrderUpdateDto orderUpdateDto){

        Message message = new Message();

        Order order = orderService.updateOrder(orderUpdateDto.getOrderId(), orderUpdateDto.getOrderStatus());

        message.setStatus(StatusEnum.OK);
        message.setMessage("주문 상태 변경 완료");
        message.setData(new OrderResponseDto(order));

        return ResponseEntity.ok().body(message);
    }

}
