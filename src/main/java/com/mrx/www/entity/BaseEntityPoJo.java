package com.mrx.www.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础类.
 *
 * @author Mei Ruoxiao
 * @since 2020/05/26
 */
@Data
public class BaseEntityPoJo implements Serializable {
    private Long id;
    private Date createTime;
    private Date updateTime;
    private Boolean isDelete;

    void getOne(final long id) {
        System.out.println("这是父类的getOne方法");
        System.out.println(id);
    }
}
