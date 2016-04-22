package main.yuri.org.model;

public class GameGridModel {
    private String msg = "";//消息提示
    private int type = 0;//类别 0是空，1是角色，2是物品
    private int column = 0;
    private int row = 0;
    private int index = 0;//array index 0-8
    private ActorModel actorModel = new ActorModel();
    private ItemModel itemModel = new ItemModel();

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ActorModel getActorModel() {
        return actorModel;
    }

    public void setActorModel(ActorModel actorModel) {
        this.actorModel = actorModel;
    }

    public ItemModel getItemModel() {
        return itemModel;
    }

    public void setItemModel(ItemModel itemModel) {
        this.itemModel = itemModel;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

}
