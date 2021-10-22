package com.wangdefa.paint_splat.Other;




public class ID_generator {
    // 生成六位数id转string
    public static String generateID() {
        //直接截取时间戳毫秒后六位
        long id = System.currentTimeMillis();
        String result = Long.toString(id);
        result = result.substring(result.length() - 6);

        return result;
    }

}
