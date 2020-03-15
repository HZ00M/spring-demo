package com.bigdata.demo.schedule.taskGroup;

import com.bigdata.demo.schedule.task.OrderTask;

import java.util.Collection;

/**
 *  todo 操作定义
 */
public interface Operable {
    boolean add(OrderTask task);

    boolean add(Collection<OrderTask> collection);

    boolean remove(OrderTask task);

    boolean removeAll();
}
