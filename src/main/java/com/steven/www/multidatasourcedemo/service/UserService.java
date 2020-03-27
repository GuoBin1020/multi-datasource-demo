package com.steven.www.multidatasourcedemo.service;

import com.steven.www.multidatasourcedemo.entity.User;
import com.steven.www.multidatasourcedemo.mapper.DatabaseConstant;
import com.steven.www.multidatasourcedemo.mapper.UserMapper1;
import com.steven.www.multidatasourcedemo.mapper.UserMapper2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserMapper1 userMapper1;
    private final UserMapper2 userMapper2;

    @Autowired
    public UserService(UserMapper1 userMapper1,
                       UserMapper2 userMapper2) {
        this.userMapper1 = userMapper1;
        this.userMapper2 = userMapper2;

    }

    /**
     * 查询所有数据源的数据
     * @return 所有数据源的数据
     */
    public List<User> getAllUser() {
        List<User> users = new ArrayList<>();
        // 数据源1
        users.addAll(userMapper1.getAll());
        // 数据源2
        users.addAll(userMapper2.getAll());
        return users;
    }

    // 插入数据到指定数据源
    public void insertUser(User user, String database) throws IllegalArgumentException {
        if (user == null) {
            throw new IllegalArgumentException("插入对象为null，插入数据失败。");
        }
        if (user.getId() == null) {
            user.setId(UUID.randomUUID().toString());
        }
        if (DatabaseConstant.TEST_DATABASE_1.equals(database)) {    // 指定数据源1
            userMapper1.insert(user);
        } else if (DatabaseConstant.TEST_DATABASE_2.equals(database)) { // 指定数据源2
            userMapper2.insert(user);
        } else {
            // 没有找到对应的数据源，报异常
            throw new IllegalArgumentException("没有找到对应的数据库，插入数据失败。");
        }
    }

}
