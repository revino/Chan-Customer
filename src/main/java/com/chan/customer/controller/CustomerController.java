package com.chan.customer.controller;

import com.chan.customer.common.Message;
import com.chan.customer.common.StatusEnum;
import com.chan.customer.dto.CustomerDto;
import com.chan.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/signUp")
    public ResponseEntity<Message> signUp(@RequestBody CustomerDto customerDto){

        Message message = new Message();

        customerService.signUp(customerDto.toEntity());
        message.setStatus(StatusEnum.OK);
        message.setData("가입 성공");

        return ResponseEntity.ok().body(message);
    }

    @PostMapping("/")
    public ResponseEntity<Message> updateProfile(@RequestBody CustomerDto customerDto) {

        Message message = new Message();

        customerService.update(customerDto.toEntity());
        message.setStatus(StatusEnum.OK);
        message.setData("수정 성공");

        message.setStatus(StatusEnum.OK);
        message.setData("업데이트 성공");

        return ResponseEntity.ok().body(message);
    }

}
