package com.bigdata.demo.schedule.task;


import org.chiefdata.schedule.exception.QueryException;

/**
 * todo 调度任务定义
 * @param <IN> 上一个任务执行结果
 * @param <OUT> 当前任务执行结果
 */
@FunctionalInterface
public interface OrderTask<IN,OUT> {
    OUT execute(IN in)throws QueryException;

    default int getOrder(){
        return 0;
    }
}
