package com.chan.customer.dto;

import com.chan.customer.domain.*;
import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private String accountId;

    private Long menuNo;

    private String menuName;

    private String menuPlan;

    private int menuPrice;

    private int menuCount;

}