package com.mrx.www.controller;

import com.mrx.www.common.Rs;
import com.mrx.www.entity.PersonPoJo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Person Controller.
 *
 * @author Mei Ruoxiao
 * @since 2020/05/22
 */
@Slf4j
@Controller
@RestController
@RequestMapping("/person")
public class PersonController {

    @GetMapping("/detail")
    public ResponseEntity<Rs<Object>> detail(
            @RequestParam(value = "id", required = false) final long id) {
        log.info("detail:获取详情[id:{}]", id);
        long start = System.currentTimeMillis();
        PersonPoJo poJo = new PersonPoJo();
        PersonPoJo poJo1 = new PersonPoJo();
        poJo.setName("张三");
        poJo.setAge(25);
        poJo1.setName("李四");
        poJo1.setAge(18);
        long end = System.currentTimeMillis();
        log.info("detail:cost[{}]", end - start);
        return Rs.ok(poJo);

    }

    @GetMapping("/test")
    public String show() {
        String str = "Hello World!";
        return str;
    }
}
