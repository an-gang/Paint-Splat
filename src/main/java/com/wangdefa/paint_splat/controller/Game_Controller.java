package com.wangdefa.paint_splat.controller;

import com.wangdefa.paint_splat.entity.Game;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpSession;
import java.util.Random;


@RestController
public class Game_Controller {

    @RequestMapping("/test")
    public Game test() {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();

        System.out.println(session.getId());

        Random random = new Random();
        random.nextInt(300);

        return new Game(new String[]{"123", "3213213"}, "sadasd");
    }


    @RequestMapping("/test2")
    public String test2() {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();

        System.out.println(session.getId());

        Random random = new Random();
        random.nextInt(300);

        return "123";
    }
}
