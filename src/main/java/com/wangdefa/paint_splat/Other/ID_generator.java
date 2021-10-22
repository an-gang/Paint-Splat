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
        //直接截取时间戳毫秒后六位
        int id = System.currentTimeMillis();
        String result = Integer.toString(id).substring(s.length()-6,s.length());//或者事

        return result;
    }

}
