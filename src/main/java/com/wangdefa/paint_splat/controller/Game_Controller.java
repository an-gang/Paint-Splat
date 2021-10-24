package com.wangdefa.paint_splat.controller;


import com.wangdefa.paint_splat.service.Game_Service_Interface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.Random;


@RestController
public class Game_Controller {

    @Autowired
    private Game_Service_Interface service;

    @RequestMapping("/test")
    @CrossOrigin("*")
    public int[] test() {
        HttpSession session = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest().getSession();

        System.out.println(session.getId());

        Random random = new Random();
        random.nextInt(300);

        return new int[]{random.nextInt(300), random.nextInt(300)};
    }


    @RequestMapping("/test2")
    public String test2() {

//        service.test();

        return "123";
    }





}
