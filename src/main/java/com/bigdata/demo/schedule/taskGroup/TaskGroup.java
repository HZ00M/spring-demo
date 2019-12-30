package com.bigdata.demo.schedule.taskGroup;

import java.util.Optional;
import java.util.concurrent.Callable;

/**
 *  todo 任务组定义
 */
public interface TaskGroup extends Callable ,Operable{

    void work();

    boolean isSuccess();

    boolean isDone();

    default int size(){
        return -1;
    }

    default Optional getResult(){
        return Optional.empty();
    }
}
