package com.mrx.www.domain;

import com.mrx.www.entity.BaseEntityPoJo;
import lombok.*;

/**
 * CreateOrderModel.
 *
 * @author Mei Ruoxiao
 * @since 2020/06/02
 */
@Data
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CreateOrderModel extends BaseEntityPoJo {
    private String orderNum;

    private Long totalAmount;
}
