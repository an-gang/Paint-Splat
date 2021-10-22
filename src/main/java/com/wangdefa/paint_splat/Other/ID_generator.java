package com.wangdefa.paint_splat.Other;

import java.util.ArrayList;
/**
 * 根据ID生成六位随机码
 * @param id ID
 * @return 随机码
 */
public class ID_generator {
    // 生成六位数id转string
    public static String generateID(){
        int id = (int)((Math.random()*9+1)*100000);
        String str = Integer.toString(id);
        return str;
    }

}
