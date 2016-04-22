package main.yuri.org.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import android.util.Log;

import main.yuri.org.model.GameGridModel;

public class GameMap {
	private Random random = new Random(System.currentTimeMillis());
	private List<GameGridModel> list = new ArrayList<GameGridModel>();
	private int column=3,row=3;
	private int[][]typearray;
	private int level = 0;
	public static final int LEVEL_RANDOM = 0;
	public static final int LEVEL_EASY = 1;
	public static final int LEVEL_NORMAL = 2;
	public static final int LEVEL_HARD = 3;
	public static final int LEVEL_NIGHTMARE = 4;
	
	

	public GameMap() {
		initList(level);
	}
	public GameMap(int level){
		this.level = level;
		initList(level);
	}
	public void initList(int level){
		this.level = level;
		typearray = getTypeArray(level);
		list.clear();
		for (int i = 0; i < column; i++) {
			for (int j = 0; j < row; j++) {
				GameGridModel gameGridModel = new GameGridModel();
				gameGridModel.setType(typearray[i][j]);
				gameGridModel.setColumn(i);
				gameGridModel.setRow(j);
				if(gameGridModel.getType() == 1){
				}
				list.add(gameGridModel);
			}
		}
	}
	
	public List<GameGridModel> getList() {
		return list;
	}

	/**
	 * 0:普通1:陷阱
	 */
	private int[][] getTypeArray(int level){
		int[][] array = new int[column][row];
		for (int i = 0; i < column; i++) {
			for (int j = 0; j < row; j++) {
				array[i][j] = 0;
			}
		}
		switch (level) {
		case LEVEL_RANDOM://完全随机
			for (int i = 0; i < column; i++) {
				for (int j = 0; j < row; j++) {
					array[i][j] = random.nextInt(2);
				}
			}
			break;
		case LEVEL_EASY://每行有0-1个陷阱
			for (int i = 0; i < column; i++) {
				int j = random.nextInt(row);
				array[i][j]=random.nextInt(2);
			}
			break;
		case LEVEL_NORMAL://每行有0-2个陷阱
			for (int i = 0; i < column; i++) {
				int j = random.nextInt(row);
				int k = random.nextInt(row);
				array[i][j]=random.nextInt(2);
				array[i][k]=random.nextInt(2);
			}
			break;
		case LEVEL_HARD://每行1-2个陷阱
			for (int i = 0; i < column; i++) {
				int j = random.nextInt(row);
				int k = random.nextInt(row);
				array[i][j]=1;
				array[i][k]=random.nextInt(2);
			}
			break;
		case LEVEL_NIGHTMARE://每行2个陷阱
			for (int i = 0; i < column; i++) {
				int j = random.nextInt(row);
				int k = random.nextInt(row);
				while(k == j){
					k = random.nextInt(row);
				}
				array[i][j]=1;
				array[i][k]=1;
			}
			break;
		}
		for (int i = 0; i < column; i++) {
			Log.i("column", Arrays.toString(array[i]));
		}
		return array;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

}
