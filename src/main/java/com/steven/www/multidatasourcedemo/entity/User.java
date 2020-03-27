package com.steven.www.multidatasourcedemo.entity;

import lombok.Data;

/**
 * 实体类，没啥好备注的
 * Lombok工具: @Data 注解来自工具Lombok, 加上这个之后就不需要写GetSet方法。
 */
@Data
public class User {

    private String id;
    private String name;

}
