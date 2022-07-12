package com.chan.customer.service;

import com.chan.customer.domain.Customer;
import com.chan.customer.exception.CustomerFindFailedException;
import com.chan.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public Long signUp(Customer customer){

        checkCustomer(customer);
        customerRepository.save(customer);
        return customer.getId();
    }

    @Transactional
    public Long update(Customer newInfo){

        Customer customer = customerRepository.findByAccountId(newInfo.getAccountId());

        if(customer == null){
            throw new CustomerFindFailedException("아이디가 존재하지 않습니다.");
        }

        customer.setAddress(newInfo.getAddress());

        customerRepository.save(customer);

        return customer.getId();
    }

    public Customer findAccountId(String AccountId){

        Customer customer = customerRepository.findByAccountId(AccountId);

        if(customer == null){
            throw new CustomerFindFailedException("아이디가 존재하지 않습니다.");
        }

        return customer;
    }

    private void checkCustomer(Customer customer) {
        Customer findCustomer = customerRepository.findByAccountId(customer.getAccountId());

        if(findCustomer != null){
            throw new CustomerFindFailedException("아이디가 존재합니다.");
        }
    }
}
