package com.wangdefa.paint_splat.controller;

import com.wangdefa.paint_splat.entity.Game;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Game_Controller {

    @RequestMapping("/test")
    public Game test() {
        Game game = new Game(new String[]{"123", "321"}, "test");
        return game;
    }
}
