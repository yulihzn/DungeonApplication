package main.yuri.org.model;

import android.graphics.Color;

/**
 * Created by Administrator on 2016/4/24 0024.
 */
public class MsgModel {
    public MsgModel() {
    }

    public MsgModel(String msg) {
        this.msg = msg;
    }
    public MsgModel(String msg,int color) {
        this.msg = msg;
        this.color = color;
    }

    private String msg = "";
    private int id = 0;
    private int color = Color.WHITE;

    public int getColor() {
        return color;
    }

    public MsgModel setColor(int color) {
        this.color = color;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public MsgModel setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
