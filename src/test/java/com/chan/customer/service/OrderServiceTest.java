package com.chan.customer.service;

import com.chan.customer.domain.*;
import com.chan.customer.repository.CustomerRepository;
import com.chan.customer.repository.OrderReposiotry;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Transactional
class OrderServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    OrderService orderService;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    OrderReposiotry orderReposiotry;

    @Test
    public void requestOrder() throws Exception{

        String accountId = "requestOrder";

        Customer customer = new Customer();
        customer.setAccountId(accountId);
        customer.setAddress(new Address());

        List<Menu> menuList = new ArrayList<>();
        Menu menu1 = Menu.builder()
                .menuName("menu name1")
                .menuNo((long)1234)
                .menuCount(2)
                .menuPlan("1010100")
                .menuPrice(1000)
                .build();
        Menu menu2 = Menu.builder()
                .menuName("menu name2")
                .menuNo((long)1235)
                .menuCount(2)
                .menuPlan("1010100")
                .menuPrice(2000)
                .build();
        menuList.add(menu1);
        menuList.add(menu2);

        customerService.signUp(customer);
        Long orderId = orderService.requestOrder(accountId, menuList);

        Order order = orderReposiotry.findById(orderId).orElseThrow();

        Assertions.assertEquals(OrderStatus.ORDER, order.getStatus());
        Assertions.assertEquals(order.getTotalCount(), 4);
        Assertions.assertEquals(order.getTotalPrice(), 2000+4000);

    }

    @Test
    public void searchLocalDataTime(){
        String accountId = "searchLocalDataTime";

        Customer customer = new Customer();
        customer.setAccountId(accountId);
        customer.setAddress(new Address());

        List<Menu> menuList = new ArrayList<>();
        Menu menu = Menu.builder()
                .menuName("menu name1")
                .menuNo((long)4321)
                .menuCount(1)
                .menuPlan("1010100")
                .menuPrice(1000)
                .build();
        menuList.add(menu);

        customerService.signUp(customer);
        orderService.requestOrder(accountId, menuList);

        LocalDateTime start = LocalDateTime.now().minusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(1);
        List<Order> findOrderList = orderService.findOrder(accountId, start, end);

        Assertions.assertEquals(findOrderList.size(), 1);
    }

    @Test
    public void searchLocalDataTime_fail(){
        String accountId = "searchLocalDataTime_fail";

        Customer customer = new Customer();
        customer.setAccountId(accountId);
        customer.setAddress(new Address());

        List<Menu> menuList = new ArrayList<>();
        Menu menu = Menu.builder()
                        .menuName("menu name1")
                        .menuNo((long)4321)
                        .menuCount(1)
                        .menuPlan("1010100")
                        .menuPrice(1000)
                    .build();
        menuList.add(menu);

        customerService.signUp(customer);
        orderService.requestOrder(accountId, menuList);

        LocalDateTime start = LocalDateTime.now().plusDays(1);
        LocalDateTime end = LocalDateTime.now().plusDays(2);
        List<Order> findOrderList = orderService.findOrder(accountId, start, end);

        Assertions.assertEquals(findOrderList.size(), 0);
    }

}