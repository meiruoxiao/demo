package com.mrx.www.controller;

import com.mrx.www.domain.CommandBus;
import com.mrx.www.domain.CreateOrderCommand;
import com.mrx.www.domain.CreateOrderModel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * order controller.
 *
 * @author Mei Ruoxiao
 * @since 2020/06/02
 */
@Slf4j
@Controller
@RestController
@RequestMapping(value = "/order")
public class OrderController {
    //    @Resource
//    private GetOrderInfoService getOrderInfoService;
    @Resource
    private CreateOrderCommand createOrderCommand;
    @Resource
    private CommandBus commandBus;

//    @PostMapping(value = "/getInfo")
//    public Object getOrderInfo(GetOrderInfoModel model) {
//        return getOrderInfoService.getOrderInfos(model);
//    }

    @PostMapping(value = "/creat")
    public Object createOrderInfo(CreateOrderModel model) {
        return commandBus.dispatch(createOrderCommand, model);
    }
}
