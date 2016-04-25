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

    private String msg = "";
    private int id = 0;
    private int color = Color.WHITE;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
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
