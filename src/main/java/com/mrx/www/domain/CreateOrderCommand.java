package com.mrx.www.domain;

import org.springframework.stereotype.Component;

/**
 * create order command.
 *
 * @author Mei Ruoxiao
 * @since 2020/06/02
 */
@Component
public class CreateOrderCommand implements Command<CreateOrderModel> {

    @Override
    public Object execute(CreateOrderModel commandModel) {
        //Specific logic
        return null;
    }
}
