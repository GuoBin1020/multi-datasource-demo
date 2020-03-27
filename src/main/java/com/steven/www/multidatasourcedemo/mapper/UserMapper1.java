package com.steven.www.multidatasourcedemo.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.steven.www.multidatasourcedemo.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据源1配置， @DS注解用于指定数据源配置。这里指定数据源1.
 */
@DS(value = DatabaseConstant.TEST_DATABASE_1)
@Repository
public interface UserMapper1 {

    @Select("select * from user")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name")
    })
    List<User> getAll();

    @Insert("insert into user(id, name) values(#{id}, #{name})")
    void insert(User user);

}
