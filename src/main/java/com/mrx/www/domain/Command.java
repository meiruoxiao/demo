package com.mrx.www.domain;

/**
 * CQRS（Command Query Responsibility Segregation）.
 *
 * @author Mei Ruoxiao
 * @since 2020/06/02
 */
public interface Command<T> {
    /**
     * execute.
     *
     * @param commandModel model
     * @return object
     */
    Object execute(T commandModel);
}
