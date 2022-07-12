package com.chan.customer.controller;

import com.chan.customer.domain.Customer;
import com.chan.customer.domain.Menu;
import com.chan.customer.dto.CustomerDto;
import com.chan.customer.dto.OrderDto;
import com.chan.customer.service.CustomerService;
import com.chan.customer.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@RestController("order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final CustomerService customerService;

    @PostMapping("/")
    public ResponseEntity<Message> requestOrder(@RequestBody OrderDto orderDto){

        Message message = new Message();
        HttpHeaders headers = makeJsonTypeHeaders();

        try{
            
            List<Menu> menuList = new ArrayList<>();
            menuList.add(Menu.builder()
                    .menuName(orderDto.getMenuName())
                    .menuNo(orderDto.getMenuNo())
                    .menuCount(orderDto.getMenuCount())
                    .menuPlan(orderDto.getMenuPlan())
                    .menuPrice(orderDto.getMenuPrice())
                    .build());

            orderService.requestOrder(orderDto.getAccountId(), menuList);

            message.setStatus(StatusEnum.OK);
            message.setData("주문 성공");

        }catch (Exception exception){

            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(exception.getMessage());
        }

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    private HttpHeaders makeJsonTypeHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return headers;
    }

}
