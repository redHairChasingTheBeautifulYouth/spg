package com.spg.commom;

import lombok.Data;

/**
 * @author trevor
 * @date 2019/3/4 11:02
 */
@Data
public class JsonEntity<T> {

    /**
     * 大于零表示正确得返回
     */
    private Integer code;

    /**
     * 正确或错误信息
     */
    private String message;

    /**
     * 数据
     */
    private T data;

    public JsonEntity() {
    }

    public JsonEntity(Integer code , String message) {
        this.code = code;
        this.message = message;
    }

    public JsonEntity(T data) {
        this.data = data;
    }

}
