package com.wangdefa.paint_splat.service;

import com.wangdefa.paint_splat.entity.Game;
import org.springframework.stereotype.Service;

@Service
public class Game_Service implements Game_Service_Interface {
    @Override
    public Game test() {

        System.out.println("service");


        return null;
    }
}
