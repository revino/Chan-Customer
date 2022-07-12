package com.chan.customer.service;

import com.chan.customer.domain.Address;
import com.chan.customer.domain.Customer;
import com.chan.customer.exception.CustomerFindFailedException;
import com.chan.customer.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class CustomerServiceTest {
    
    @Autowired
    CustomerService customerService;

    @Autowired
    CustomerRepository customerRepository;

    @Test
    public void customerSignUp() throws Exception{

        Customer customer = new Customer();
        customer.setAccountId("test");
        customer.setAddress(new Address("서울시 종로구", 11110));

        Long customerId = customerService.signUp(customer);
        Customer findCustomer = customerRepository.findById(customerId).orElseThrow();

        Assertions.assertEquals(customer, findCustomer);
    }

    @Test
    public void customerUpdate() throws Exception{

        Customer customer = new Customer();
        customer.setAccountId("test");
        customer.setAddress(new Address("서울시 종로구", 11110));

        Long customerId = customerService.signUp(customer);

        Customer findCustomer = customerRepository.findById(customerId).orElseThrow();
        findCustomer.setAddress(new Address("서울시 중구", 11140));

        customerService.update(findCustomer);

        Customer changeCustomer = customerRepository.findById(customerId).orElseThrow();

        Assertions.assertEquals(changeCustomer.getAddress().getSigunguCode(), 11140);
    }

    @Test
    public void customerOverlap() throws  Exception{

        Address address = new Address();
        Customer customer1 = new Customer();
        customer1.setAccountId("test1");
        customer1.setAddress(address);

        Customer customer2 = new Customer();
        customer2.setAccountId("test1");
        customer2.setAddress(address);

        customerService.signUp(customer1);
        Assertions.assertThrows(CustomerFindFailedException.class, () -> {
            customerService.signUp(customer2);
        });

    }
}