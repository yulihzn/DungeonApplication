package main.yuri.org.model;

/**
 * Created by Administrator on 2016/4/24 0024.
 */
public class MsgModel {
    public MsgModel() {
    }

    public MsgModel(String msg) {
        this.msg = msg;
    }

    String msg = "";
    int id = 0;

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
