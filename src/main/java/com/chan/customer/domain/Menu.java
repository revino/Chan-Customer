package com.chan.customer.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
@Getter
public class Menu {

    @Id
    @GeneratedValue
    @Column( name = "menu_id")
    private Long id;

    @OneToOne(mappedBy = "menu", fetch = FetchType.LAZY)
    private OrderMenu orderMenu;

    @NotNull
    private Long menuNo;

    @NotNull
    private String menuName;

    @NotNull
    @Size(min = 7, max = 7)
    private String menuPlan;

    @NotNull
    private int menuPrice;

    @NotNull
    private int menuCount;

    public void setOrderMenu(OrderMenu orderMenu) {
        this.orderMenu = orderMenu;
    }

}
