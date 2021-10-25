package com.group3.paint_splat.entity;

public class Paint {
    private String player;
    private double[] position;

    public Paint(String player, double[] position) {
        this.player = player;
        this.position = position;
    }

    public String getPlayer() {
        return player;
    }

    public double[] getPosition() {
        return position;
    }


}
