package main.yuri.org.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.Image;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import main.yuri.org.Factory.ActorModelFactory;
import main.yuri.org.Factory.ItemModelFactory;
import main.yuri.org.adapter.GameRecyclerViewAdapter;
import main.yuri.org.adapter.MsgRecyclerViewAdapter;
import main.yuri.org.dungeonlord.R;
import main.yuri.org.logic.GameLogic;
import main.yuri.org.logic.GameMap;
import main.yuri.org.model.ActorModel;
import main.yuri.org.model.GameGridModel;
import main.yuri.org.model.ItemModel;
import main.yuri.org.model.MsgModel;
import main.yuri.org.view.CustomToast;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
	private TextView tv_level;
	private GameRecyclerViewAdapter adapter;
	private MsgRecyclerViewAdapter adapter_msg;
	private List<MsgModel> list_msg = new ArrayList<MsgModel>();
	private List<GameGridModel> datas = new ArrayList<GameGridModel>();
	private static final int STATUS_FAILED = 100;
	private static final int STATUS_SUCCESS = 101;
	private static final int STATUS_ING = 102;
	private static final int COLUMN = 3;
	private int level = 1;

	private Toolbar mToolbar;
	private RecyclerView mRcv;
	private RecyclerView rv_msg;
	private TextView tv_msg;
	private ImageButton ib_msg;

	private DrawerLayout mDrawerLayout;
	private NavigationView mNavigationView;

	private GameLogic gameLogic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		initView();
		gameLogic = new GameLogic(datas,list_msg);
		loadData(GameMap.LEVEL_EASY);
	}
	private void initToolBar() {
		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		mToolbar.setTitle(" ");
		mToolbar.setSubtitle(" ");
		mToolbar.setNavigationIcon(R.mipmap.castle_head);
		setSupportActionBar(mToolbar);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mDrawerLayout.openDrawer(GravityCompat.START);
			}
		});
	}
	private void loadData(int level) {
		gameLogic.initData();
		adapter.notifyDataSetChanged();
	}
	private void addData(int position){
		int x=0,y = 0;
		if(position < 3){
			y = -1;
		}
		if(position <9 && position >= 6){
			y = 1;
		}
		if(position%3==0){
			x = -1;
		}
		if((position-2)%3==0){
			x = 1;
		}
		gameLogic.setGameList(gameLogic.getPos()[0]+x,gameLogic.getPos()[1]+y);
		adapter.notifyItemRangeChanged(0,9);

	}
	private void initView() {
		initMainLayout();
		initMsgLayout();
		initToolBar();
		initGameBoard();
		initMsgBoard();
	}

	private void initMainLayout() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
		mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
			@Override
			public void onDrawerSlide(View drawerView, float slideOffset) {

			}

			@Override
			public void onDrawerOpened(View drawerView) {
			}

			@Override
			public void onDrawerClosed(View drawerView) {
			}

			@Override
			public void onDrawerStateChanged(int newState) {

			}
		});
		mNavigationView = (NavigationView)findViewById(R.id.nav);
		mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
			@Override
			public boolean onNavigationItemSelected(MenuItem item) {
				item.setChecked(true);
				mDrawerLayout.closeDrawers();
				return false;
			}
		});
		tv_level = (TextView) findViewById(R.id.textView_level);
		tv_level.setText("level"+level);
	}

	private void initGameBoard() {
		adapter = new GameRecyclerViewAdapter(this);
		adapter.setData(datas);
		mRcv = (RecyclerView) findViewById(R.id.rcv_test);
		ViewGroup.LayoutParams lp =  mRcv.getLayoutParams();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		if(lp.height > lp.width){
			lp.height = lp.width;
		}else{
			lp.width = lp.height;
		}
		mRcv.setLayoutParams(lp);
		StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
		mRcv.setLayoutManager(gridLayoutManager);
		mRcv.setItemAnimator(new DefaultItemAnimator());
		mRcv.addItemDecoration(new ItemDecoration() {
			Paint paint = new Paint();
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
				super.getItemOffsets(outRect, view, parent, state);
			}
			@Override
			public void onDraw(Canvas c, RecyclerView parent, State state) {
				super.onDraw(c, parent, state);
			}
			@Override
			public void onDrawOver(Canvas c, RecyclerView parent, State state) {
				super.onDrawOver(c, parent, state);
				paint.setColor(Color.WHITE);
				paint.setStrokeWidth(1);
				for (int i = 0, size = parent.getChildCount(); i < size; i++) {
					View child = parent.getChildAt(i);
//	                    c.drawLine(child.getLeft(), child.getBottom(),
//	                            child.getRight(), child.getBottom(), paint);
//	                    c.drawLine(child.getLeft(), child.getTop(),
//	                    		child.getRight(), child.getTop(), paint);
//	                    c.drawLine(child.getRight(), child.getBottom(),
//	                    		child.getRight(), child.getTop(), paint);
//	                    c.drawLine(child.getLeft(), child.getBottom(),
//	                    		child.getLeft(), child.getTop(), paint);
				}
			}

		});
		mRcv.setAdapter(adapter);
		adapter.setOnItemClickListener(new GameRecyclerViewAdapter.onItemClickListener() {
			@Override
			public void onItemClick(View view, int position) {
				gameLogic(position);
				int[] location = new int[2];
				view.getLocationOnScreen(location);
				showBattleMsg(location[0]+view.getWidth()/5,location[1],position);
			}
		});
	}

	private void gameLogic(int position) {
		addMsg("");
		addData(position);
	}

	private void showBattleMsg(int x,int y,int position) {
		tv_msg.setVisibility(View.VISIBLE);
		tv_msg.setX(x);
		tv_msg.setY(y);
		tv_msg.setTextColor(Color.RED);
		if(position == 4){
			tv_msg.setTextColor(Color.WHITE);
		}
		String name = "";
		int color = Color.WHITE;
		switch (datas.get(position).getType()){
			case 0:
				name = "";
				break;
			case 1:
				name = datas.get(position).getActorModel().getName();
				color = datas.get(position).getActorModel().getColor();
				break;
			case 2:
				name = datas.get(position).getItemModel().getName();
				color = datas.get(position).getItemModel().getColor();
				break;
		}
		tv_msg.setTextColor(color);
		tv_msg.setText(name);


		PropertyValuesHolder b1 = PropertyValuesHolder.ofFloat("alpha",1f,0f);
		PropertyValuesHolder b2 = PropertyValuesHolder.ofFloat("scaleX",1f,1f);
		PropertyValuesHolder b3 = PropertyValuesHolder.ofFloat("scaleY",1f,1f);
		ObjectAnimator oc = ObjectAnimator.ofPropertyValuesHolder(tv_msg,b1,b2,b3);
		oc.setDuration(0);
		oc.start();
		PropertyValuesHolder a1 = PropertyValuesHolder.ofFloat("alpha",1f,0f);
		PropertyValuesHolder a2 = PropertyValuesHolder.ofFloat("scaleX",1f,1.5f);
		PropertyValuesHolder a3 = PropertyValuesHolder.ofFloat("scaleY",1f,1.5f);
		ObjectAnimator ob = ObjectAnimator.ofPropertyValuesHolder(tv_msg,a1,a2,a3);
		ob.setDuration(1000);
		ob.start();
	}

	private void initMsgLayout() {
		tv_msg = (TextView) findViewById(R.id.textView_msg);

	}

	/**消息栏初始化**/
	private void initMsgBoard() {
		adapter_msg = new MsgRecyclerViewAdapter(this);
		adapter_msg.setList(list_msg);
		rv_msg = (RecyclerView) findViewById(R.id.rcv_msg);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
		ViewGroup.LayoutParams lp =  rv_msg.getLayoutParams();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		lp.height = dm.heightPixels/6;
		lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
		rv_msg.setLayoutParams(lp);
		rv_msg.setLayoutManager(layoutManager);
		rv_msg.setHasFixedSize(true);
		rv_msg.setItemAnimator(new DefaultItemAnimator());
		rv_msg.addItemDecoration(new ItemDecoration() {
			Paint paint = new Paint();
			@Override
			public void getItemOffsets(Rect outRect, View view, RecyclerView parent, State state) {
				super.getItemOffsets(outRect, view, parent, state);
			}
			@Override
			public void onDraw(Canvas c, RecyclerView parent, State state) {
				super.onDraw(c, parent, state);
			}
			@Override
			public void onDrawOver(Canvas c, RecyclerView parent, State state) {
				super.onDrawOver(c, parent, state);
//				paint.setColor(Color.WHITE);
//				paint.setStrokeWidth(1);
//				for (int i = 0, size = parent.getChildCount(); i < size; i++) {
//					View child = parent.getChildAt(i);
//					c.drawLine(child.getLeft(), child.getBottom(),
//							child.getRight(), child.getBottom(), paint);
//				}
			}

		});
		rv_msg.setAdapter(adapter_msg);
		ib_msg = (ImageButton)findViewById(R.id.ib_msg);
		ib_msg.setOnClickListener(this);

	}

	private void addMsg(String msg){
		adapter_msg.addData(0,new MsgModel(msg));
		rv_msg.scrollToPosition(0);
		Snackbar.make(getCurrentFocus(),msg,Snackbar.LENGTH_SHORT).show();
	}


	protected void showEndDialog(final int status) {
		mRcv.setEnabled(false);
		String msg = "";
		String ok = "";
		String title = "";
		switch (status) {
		case STATUS_FAILED:
			ok = "召唤英灵";
			title = "你死了";
			msg = "您听到死神在耳边低语...";
			break;
		case STATUS_SUCCESS:
			ok = "加冕为王";
			title = "你击败了大魔王";
			msg = "这个地城属于你了，有太多想法在脑海里...";
			break;
		}

		CustomToast.showToast(getApplicationContext(), title+"\n"+msg, Toast.LENGTH_LONG);
		CountDownTimer counter = new CountDownTimer(1000, 1000) {
			@Override
			public void onTick(long millisUntilFinished) {
			}

			@Override
			public void onFinish() {
				mRcv.setEnabled(true);
				if(status == STATUS_FAILED){
					level = 1;
					tv_level.setText("level"+level);
					loadData(level);
				}else if(status == STATUS_SUCCESS){
					if(level++>= 4){
						level = 4;
					}
					tv_level.setText("level"+level);
					loadData(level);
				}

			}
		};
		counter.start();

	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()){
			case R.id.ib_msg:
				closeMsg(v);
				break;
		}
	}
	private void closeMsg(View view){
		PropertyValuesHolder a3 = PropertyValuesHolder.ofFloat("scaleY",1f,0f);
		if(rv_msg.getScaleY() == 0){
			a3 = PropertyValuesHolder.ofFloat("scaleY",0f,1f);
		}
		ObjectAnimator oa = ObjectAnimator.ofPropertyValuesHolder(rv_msg,a3);
		oa.setDuration(100);
		oa.start();
		oa.addListener(new Animator.AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
				rv_msg.setVisibility(View.VISIBLE);

			}

			@Override
			public void onAnimationEnd(Animator animation) {
				if(rv_msg.getScaleY() == 0){
					rv_msg.setVisibility(View.GONE);
				}else{
					rv_msg.setVisibility(View.VISIBLE);
				}

			}

			@Override
			public void onAnimationCancel(Animator animation) {

			}

			@Override
			public void onAnimationRepeat(Animator animation) {

			}
		});
	}
}
