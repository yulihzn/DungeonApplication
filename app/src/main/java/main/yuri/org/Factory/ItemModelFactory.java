package main.yuri.org.Factory;

import android.graphics.Color;

import java.util.Random;

import main.yuri.org.model.ItemModel;

/**
 * Created by BanditCat on 2016/4/22.
 * 物品生成器
 */
public class ItemModelFactory {
    private static ItemModelFactory instance = new ItemModelFactory();
    public static ItemModelFactory getInstance(){
        return instance;
    }
    private Random random = new Random();
    private char[] cs = new char[26];
    private int color = Color.RED;
    private ItemModelFactory() {
        for (int i = 0; i < cs.length; i++) {
            cs[i]=(char) ('a'+i);
        }
        random.setSeed(System.currentTimeMillis());
    }
    private String getRandomCharacter(){
        return ""+cs[random.nextInt(26)];
    }
    /**初始化角色**/
    public ItemModel getInitActor(){
        ItemModel ItemModel = new ItemModel();
        return  ItemModel;
    }
    /**随机物品**/
    public ItemModel getRandomItem(){
        ItemModel ItemModel = new ItemModel();
        ItemModel.setName(getRandomCharacter());
        ItemModel.setAttackPoint(1+random.nextInt(10));
        ItemModel.setDefensePoint(1+random.nextInt(10));
        ItemModel.setHealthPoint(1+random.nextInt(10));
        ItemModel.setMagicPoint(1+random.nextInt(10));
        ItemModel.setSpeed(1+random.nextInt(2));
        ItemModel.setColor(color);
        return  ItemModel;
    }
    /**A加血**/
    public ItemModel getH(){
        ItemModel ItemModel = new ItemModel();
        ItemModel.setName("h");
        ItemModel.setAttackPoint(0);
        ItemModel.setDefensePoint(0);
        ItemModel.setHealthPoint(1);
        ItemModel.setMagicPoint(0);
        ItemModel.setSpeed(0);
        ItemModel.setColor(Color.GREEN);
        return  ItemModel;
    }
    /**A加攻**/
    public ItemModel getA(){
        ItemModel ItemModel = new ItemModel();
        ItemModel.setName("a");
        ItemModel.setAttackPoint(1);
        ItemModel.setDefensePoint(0);
        ItemModel.setHealthPoint(0);
        ItemModel.setMagicPoint(0);
        ItemModel.setSpeed(0);
        ItemModel.setColor(Color.GREEN);
        return  ItemModel;
    }
    /**A加防**/
    public ItemModel getD(){
        ItemModel ItemModel = new ItemModel();
        ItemModel.setName("a");
        ItemModel.setAttackPoint(0);
        ItemModel.setDefensePoint(1);
        ItemModel.setHealthPoint(0);
        ItemModel.setMagicPoint(0);
        ItemModel.setSpeed(0);
        ItemModel.setColor(Color.GREEN);
        return  ItemModel;
    }

    public ItemModel getHAD(){
        ItemModel ItemModel = new ItemModel();
        ItemModel.setMagicPoint(0);
        ItemModel.setSpeed(0);
        ItemModel.setColor(Color.GREEN);
        int type = random.nextInt(2);
        switch (type){
            case 0:
                ItemModel.setAttackPoint(1);
                ItemModel.setName("a");
                break;
            case 1:
                ItemModel.setHealthPoint(1);
                ItemModel.setName("h");
                break;
            case 2:
                ItemModel.setDefensePoint(1);
                ItemModel.setName("d");
                break;
        }
        return  ItemModel;
    }


}
