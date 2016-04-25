package main.yuri.org.logic;

import java.util.List;
import java.util.Random;

import main.yuri.org.Factory.ActorModelFactory;
import main.yuri.org.Factory.ItemModelFactory;
import main.yuri.org.model.GameGridModel;
import main.yuri.org.model.MsgModel;

/**
 * Created by BanditCat on 2016/4/25.
 */
public class GameLogic {
    private List<GameGridModel> list_game;
    private List<MsgModel> list_msg;
    private static final int SIZE = 16;
    private GameGridModel[][] data = new GameGridModel[SIZE][SIZE];
    private Random random = new Random();
    private int[]pos = {SIZE/2,SIZE/2};

    public int[] getPos() {
        return pos;
    }

    public GameLogic(List<GameGridModel> list_game, List<MsgModel> list_msg) {
        this.list_game = list_game;
        this.list_msg = list_msg;
    }
    /**数据初始化**/
    public void initData(){
        /**方块数据**/
        random.setSeed(System.currentTimeMillis());
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                GameGridModel gameGridModel = new GameGridModel();
                gameGridModel.setType(random.nextInt(3));
                gameGridModel.setIndex(i*16+j);
                switch (gameGridModel.getType()){
                    case 1:
                        gameGridModel.setActorModel(ActorModelFactory.getInstance().getA());
                        break;
                    case 2:
                        gameGridModel.setItemModel(ItemModelFactory.getInstance().getHAD());
                        break;
                }
                data[i][j] = gameGridModel;
            }
        }
        setGameList(SIZE/2,SIZE/2);

    }
    public void setGameList(int index_x,int index_y){
        if(!(index_x - 1 > 0 && index_x+1 < SIZE-1 && index_y-1 > 0 && index_y + 1 < SIZE-1)){
            return;
        }
        pos[0] = index_x;
        pos[1] = index_y;
        list_game.clear();
        GameGridModel gameGridModel = null;
        list_game.add(data[index_x-1][index_y-1]);
        list_game.add(data[index_x][index_y-1]);
        list_game.add(data[index_x+1][index_y-1]);
        list_game.add(data[index_x-1][index_y]);
        gameGridModel = data[index_x][index_y];
        gameGridModel.setType(1);
        gameGridModel.setActorModel(ActorModelFactory.getInstance().getI());
        list_game.add(gameGridModel);
        list_game.add(data[index_x+1][index_y]);
        list_game.add(data[index_x-1][index_y+1]);
        list_game.add(data[index_x][index_y+1]);
        list_game.add(data[index_x+1][index_y+1]);
    }
    public void addMsg(String msg,String color){

    }

    public void getItemClicked(int position){

    }
}
