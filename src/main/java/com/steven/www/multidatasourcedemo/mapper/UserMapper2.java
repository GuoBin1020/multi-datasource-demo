package com.steven.www.multidatasourcedemo.mapper;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.steven.www.multidatasourcedemo.entity.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@DS(value = DatabaseConstant.TEST_DATABASE_2)
@Repository
public interface UserMapper2 {

    @Select("select * from user")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name")
    })
    List<User> getAll();

    @Insert("insert into user(id, name) values(#{id}, #{name})")
    void insert(User user);

}
