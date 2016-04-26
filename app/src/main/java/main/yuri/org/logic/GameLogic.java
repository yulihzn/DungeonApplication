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
    private static final int SIZE = 5;
    private GameGridModel[][] data = new GameGridModel[SIZE][SIZE];
    private Random random = new Random();
    private int[]pos = {SIZE/2,SIZE/2};
    private GameGridModel player;
    private int enemyCount = 0;

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
        enemyCount = 0;
        list_game.clear();
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
                //墙壁
                if(i==0||j==0||i == SIZE-1 || j == SIZE -1){
                    gameGridModel.setType(1);
                    gameGridModel.setActorModel(ActorModelFactory.getInstance().getW());
                }
                data[i][j] = gameGridModel;
                if(gameGridModel.getType() == 1 && !gameGridModel.getActorModel().getName().equals("W") ){
                    enemyCount++;
                }
            }
        }
        setGameList(SIZE/2,SIZE/2);
        if(data[SIZE/2][SIZE/2].getType() == 1 && !data[SIZE/2][SIZE/2].getActorModel().getName().equals("W")){
            enemyCount -= 1;
        }
        if(enemyCount == 0){
            initData();
        }

    }
    public void setGameList(int index_x,int index_y){
        if(!(index_x - 1 >= 0 && index_x+1 < SIZE && index_y-1 >= 0 && index_y + 1 < SIZE)){
            return;
        }
        GameGridModel tempPlay = null;
        if(player != null){
            tempPlay = player;
            player.setType(0);
        }
        pos[0] = index_x;
        pos[1] = index_y;
        list_game.clear();
        list_game.add(data[index_x-1][index_y-1]);
        list_game.add(data[index_x][index_y-1]);
        list_game.add(data[index_x+1][index_y-1]);
        list_game.add(data[index_x-1][index_y]);
        player = data[index_x][index_y];
        player.setType(1);
        player.setActorModel(ActorModelFactory.getInstance().getI());
        if(tempPlay != null){
            player.setActorModel(tempPlay.getActorModel());
            tempPlay = null;
        }
        list_game.add(player);
        list_game.add(data[index_x+1][index_y]);
        list_game.add(data[index_x-1][index_y+1]);
        list_game.add(data[index_x][index_y+1]);
        list_game.add(data[index_x+1][index_y+1]);
    }
    public void addMsg(String msg,int color){
        if(list_msg.size() > 5000){
            list_msg.clear();
        }
        list_msg.add(0,new MsgModel(msg,color));
    }

    public void getItemClicked(int position){

    }

    public int getEnemyCount() {
        return enemyCount;
    }

    public void setEnemyCount(int enemyCount) {
        this.enemyCount = enemyCount;
    }

    public List<GameGridModel> getList_game() {
        return list_game;
    }

    public List<MsgModel> getList_msg() {
        return list_msg;
    }

    public GameGridModel getPlayer() {
        return player;
    }
}
