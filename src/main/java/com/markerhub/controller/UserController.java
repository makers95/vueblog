package com.markerhub.controller;


import com.markerhub.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 关注公众号：MarkerHub
 * @since 2021-04-09
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/index")
    public Object index(){
        return  userService.getById(1L);
    }

}
