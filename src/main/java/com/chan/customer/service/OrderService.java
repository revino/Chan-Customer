package com.chan.customer.service;

import com.chan.customer.domain.*;
import com.chan.customer.repository.CustomerRepository;
import com.chan.customer.repository.OrderReposiotry;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderReposiotry orderReposiotry;
    private final CustomerRepository customerRepository;

    @Transactional
    public Long requestOrder(String accountId, List<Menu> menuList){

        //고객 정보 조회
        Customer customer = customerRepository.findByAccountId(accountId);

        //배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(customer.getAddress());

        //주문 메뉴 생성
        List<OrderMenu> orderMenuList = new ArrayList<>();
        for(var menu : menuList){
            OrderMenu orderMenu = new OrderMenu();
            orderMenu.setMenu(menu);
            orderMenuList.add(orderMenu);
        }

        //주문 생성
        Order order = Order.requestOrder(customer, delivery, orderMenuList);

        //주문 저장
        orderReposiotry.save(order);

        return order.getId();
    }

    public List<Order> findOrder(String accountId, LocalDateTime start, LocalDateTime end){

        //고객 정보 조회
        Customer customer = customerRepository.findByAccountId(accountId);

        //주문 정보 조회
        List<Order> orderList = orderReposiotry.findByCustomerAndOrderDateTimeBetween(customer,start,end);

        return orderList;
    }
}
