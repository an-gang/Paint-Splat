package com.wangdefa.paint_splat.controller;

import com.wangdefa.paint_splat.entity.Game;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


import javax.servlet.http.HttpSession;


@RestController
public class Game_Controller {

    @RequestMapping("/test")
    public Game test() {
        HttpSession session = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession();

        System.out.println(session.getId());

        return new Game(new String[]{"123", "1230321300"}, "test");
    }
}
