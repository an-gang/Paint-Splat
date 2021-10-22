package com.wangdefa.paint_splat.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Game {
    private String id;
    private Date startTime;
    private int step;
    private long timeSpan = 0;
    private String[] players = new String[4];
    private ArrayList<double[]> boardPositions = new ArrayList<>();
    private HashMap<String, Paint> paints;

    public Game() {
        init();
    }

    private void init() {

    }


}
