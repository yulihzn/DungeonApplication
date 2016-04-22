package main.yuri.org.Factory;

import android.graphics.Color;

import java.util.Random;

import main.yuri.org.model.ActorModel;

/**
 * Created by BanditCat on 2016/4/22.
 * 角色生成器
 */
public class ActorModelFactory {
    private static ActorModelFactory instance = new ActorModelFactory();
    public static ActorModelFactory getInstance(){
        return instance;
    }
    private Random random = new Random();
    private char[] cs = new char[26];
    private int color = Color.RED;
    private ActorModelFactory() {
        for (int i = 0; i < cs.length; i++) {
            cs[i]=(char) ('A'+i);
        }
        random.setSeed(System.currentTimeMillis());
    }
    private String getRandomCharacter(){
        return ""+cs[random.nextInt(26)];
    }
    /**初始化角色**/
    public ActorModel getInitActor(){
        ActorModel actorModel = new ActorModel();
        return  actorModel;
    }
    /**随机角色**/
    public ActorModel getRandomActor(){
        ActorModel actorModel = new ActorModel();
        actorModel.setName(getRandomCharacter());
        actorModel.setAttackPoint(1+random.nextInt(10));
        actorModel.setDefensePoint(1+random.nextInt(10));
        actorModel.setHealthPoint(1+random.nextInt(10));
        actorModel.setMagicPoint(1+random.nextInt(10));
        actorModel.setSpeed(1+random.nextInt(2));
        actorModel.setColor(color);
        return  actorModel;
    }
    /**指定名字的角色**/
    public ActorModel getActor(String name){
        ActorModel actorModel = new ActorModel();
        actorModel.setName(name);
        actorModel.setAttackPoint(1+random.nextInt(10));
        actorModel.setDefensePoint(1+random.nextInt(10));
        actorModel.setHealthPoint(1+random.nextInt(10));
        actorModel.setMagicPoint(1+random.nextInt(10));
        actorModel.setSpeed(1+random.nextInt(2));
        actorModel.setColor(color);
        return  actorModel;
    }
    /**玩家角色**/
    public ActorModel getI(){
        ActorModel actorModel = new ActorModel();
        actorModel.setName("I");
        actorModel.setAttackPoint(10);
        actorModel.setDefensePoint(10);
        actorModel.setHealthPoint(10);
        actorModel.setMagicPoint(10);
        actorModel.setSpeed(1);
        actorModel.setColor(Color.WHITE);
        return  actorModel;
    }
    /**怪物角色**/
    public ActorModel getA(){
        ActorModel actorModel = new ActorModel();
        actorModel.setName("A");
        actorModel.setAttackPoint(1);
        actorModel.setDefensePoint(0);
        actorModel.setHealthPoint(1);
        actorModel.setMagicPoint(1);
        actorModel.setSpeed(1);
        actorModel.setColor(Color.RED);
        return  actorModel;
    }
    /**墙**/
    public ActorModel getW(){
        ActorModel actorModel = new ActorModel();
        actorModel.setName("W");
        actorModel.setAttackPoint(0);
        actorModel.setDefensePoint(9999);
        actorModel.setHealthPoint(9999);
        actorModel.setMagicPoint(0);
        actorModel.setSpeed(0);
        actorModel.setColor(Color.LTGRAY);
        return  actorModel;
    }


}
