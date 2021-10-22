package com.wangdefa.paint_splat.Other;

import java.util.ArrayList;

public class ID_generator {
    // 生成六位数id转string
    public static String generateID(){
        int id = (int)((Math.random()*9+1)*100000);
        String str = Integer.toString(id);
        return str;
    }

}
