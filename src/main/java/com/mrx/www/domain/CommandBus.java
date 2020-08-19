package com.mrx.www.domain;

import org.springframework.stereotype.Component;

/**
 * Command distribution processing .
 *
 * @author Mei Ruoxiao
 * @since 2020/06/02
 */
@Component
public class CommandBus {

    public <T> Object dispatch(Command<T> cmd, T model) {
        return cmd.execute(model);
    }
}
