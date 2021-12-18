package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 请求结果类
 *
 * @param <T> 数据体类型
 */

@Setter
@Getter
@NoArgsConstructor
@JsonIgnoreProperties(value = {"status"})

public class Result<T> implements Serializable {
    /**
     * status code
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer code;
    /**
     * 消息
     */
    private String message;

    @JsonIgnore
    private boolean success = true;
    /**
     * 返回的数据主体（返回的内容）
     */
    private T data;

    /**
     * 设定结果为成功
     *
     * @param msg  消息
     * @param data 数据体
     */
    public void setResultSuccess(String msg, T data, Integer code) {
        this.message = msg;
        this.success = true;
        this.code = code;
        this.data = data;
    }

    /**
     * 设定结果为失败
     *
     * @param msg 消息
     */
    public void setResultFailed(String msg, Integer code) {
        this.message = msg;
        this.success = false;
        this.code = code;
        this.data = null;
    }

    public boolean isSuccess() {
        return success;
    }

    public Integer getStatus() {
        return code;
    }

    public void setStatus(Integer status) {
        this.code = status;
    }

    public T getData() {
        return data;
    }
}
