package com.chan.customer.controller;

import com.chan.customer.dto.CustomerDto;
import com.chan.customer.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.Charset;

@RestController("customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("/signUp")
    public ResponseEntity<Message> signUp(@RequestBody CustomerDto customerDto){

        Message message = new Message();
        HttpHeaders headers = makeJsonTypeHeaders();

        try{
            customerService.signUp(customerDto.toEntity());
            message.setStatus(StatusEnum.OK);
            message.setData("가입 성공");
        }catch (Exception exception){
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(exception.getMessage());
        }

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Message> updateProfile(@RequestBody CustomerDto customerDto){

        Message message = new Message();
        HttpHeaders headers = makeJsonTypeHeaders();

        try{
            customerService.update(customerDto.toEntity());
            message.setStatus(StatusEnum.OK);
            message.setData("수정 성공");
        }catch (Exception exception){
            message.setStatus(StatusEnum.BAD_REQUEST);
            message.setMessage(exception.getMessage());
        }

        message.setStatus(StatusEnum.OK);
        message.setData("업데이트 성공");

        return new ResponseEntity<>(message, headers, HttpStatus.OK);
    }

    private HttpHeaders makeJsonTypeHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));

        return headers;
    }

}
