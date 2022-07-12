package com.chan.customer.dto;

import com.chan.customer.domain.Address;
import com.chan.customer.domain.Customer;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    @NotNull
    private String accountId;

    private Address address;

    public Customer toEntity(){
        Customer customer = new Customer();
        customer.setAccountId(accountId);
        customer.setAddress(address);
        return customer;

    }
}
