package com.steven.www.multidatasourcedemo.controller;

import com.steven.www.multidatasourcedemo.entity.User;
import com.steven.www.multidatasourcedemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * API接口
 */
@RestController
@RequestMapping("/user")
// 这个是Lombok工具的一个注解，等价于 private static final Logger log = LoggerFactory.getLogger(UserController.class);
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * 注入，推荐这种写法。 等价于：
     * @Autowired
     * private UserService usersService.
     * @param userService User的业务处理类。
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获得全部数据，使用Reactor方式编程。该代码是springboot webflux相关，响应式编程，与多数据源无关。
     * @return Flux user.
     */
    /*
        请求发送
        URL: http://localhost:8080/user/all
        Method: GET
     */
    @RequestMapping(method = RequestMethod.GET, path = "/all", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<User> getAllUser() {
        long startTime = System.currentTimeMillis();
        try {
            return Flux.fromStream(() -> userService.getAllUser().stream().peek(u -> {
                    try {
                        // 手动延迟，每个数据延迟一秒，如果不需要模拟延迟请注释。
                        TimeUnit.SECONDS.sleep(1);
                    } catch (Exception ex) {
                        log.error("用户处理失败", ex);
                    }
                })
            );
        } finally {
            // 为了验证在延迟的情况下，响应式编程是否可以不阻塞主线程。
            log.info("Process time: " + (System.currentTimeMillis() - startTime) + "ms");
        }
    }

    /**
     * 指定数据库插入数据
     * @param user User
     * @param database 数据库
     * @return 插入结果
     */
    /*
        请求发送
        URL: http://localhost:8080/user/add/test_database_1
        Method: POST
        Body: {"name": "数据库1-员工1"}
     */
    @RequestMapping(method = RequestMethod.POST, path = "/add/{database}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<String> insertUser(@RequestBody User user, @PathVariable String database) {
        return Mono.fromSupplier(() -> {
            try {
                userService.insertUser(user, database);
                return "新增成功";
            } catch (Exception ex) {
                log.info("数据插入失败。", ex);
                return "新增失败，原因：" + ex.getMessage();
            }
        });
    }

}
