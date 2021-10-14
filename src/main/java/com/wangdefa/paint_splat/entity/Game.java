package com.wangdefa.paint_splat.entity;

public class Game {
    private String[] arrTest;
    private String test;

    public Game(String[] arrTest, String test) {
        this.arrTest = arrTest;
        this.test = test;
    }

    public String[] getArrTest() {
        return arrTest;
    }

    public void setArrTest(String[] arrTest) {
        this.arrTest = arrTest;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }
}
