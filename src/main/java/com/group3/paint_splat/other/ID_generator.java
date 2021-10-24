package com.group3.paint_splat.other;

import java.util.Random;

public class ID_generator {
    // 生成六位数id转string
    public static String generateID() {
        Random random = new Random();
        String id = Integer.toString(random.nextInt(1000000));
        while (id.length() < 6) {
            id = "0" + id;
        }
        return id;
    }

}
