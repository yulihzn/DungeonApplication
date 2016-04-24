package main.yuri.org.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.RecyclerView.State;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import main.yuri.org.Factory.ActorModelFactory;
import main.yuri.org.Factory.ItemModelFactory;
import main.yuri.org.adapter.GameRecyclerViewAdapter;
import main.yuri.org.adapter.MsgRecyclerViewAdapter;
import main.yuri.org.dungeonlord.R;
import main.yuri.org.logic.GameMap;
import main.yuri.org.model.ActorModel;
import main.yuri.org.model.GameGridModel;
import main.yuri.org.model.ItemModel;
import main.yuri.org.model.MsgModel;
import main.yuri.org.view.CustomToast;

public class GameActivity extends AppCompatActivity {
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
	private FrameLayout fl_msg;

	private DrawerLayout mDrawerLayout;
	private NavigationView mNavigationView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		initView();
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
		Random random = new Random();
		random.setSeed(System.currentTimeMillis());
		datas.clear();
		for(int i = 0;i < 9;i++){
			GameGridModel gameGridModel = new GameGridModel();
			gameGridModel.setType(random.nextInt(3));
			gameGridModel.setIndex(i);
			switch (gameGridModel.getType()){
				case 1:
					gameGridModel.setActorModel(ActorModelFactory.getInstance().getA());
					break;
				case 2:
					gameGridModel.setItemModel(ItemModelFactory.getInstance().getHAD());
					break;
			}
			if(i == 4){
				gameGridModel.setType(1);
				gameGridModel.setActorModel(ActorModelFactory.getInstance().getI());
			}
			datas.add(gameGridModel);
		}
		adapter.setData(datas);
		adapter.notifyDataSetChanged();
	}
	private void initView() {
		initMsgLayout();
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
		initToolBar();
		initMsgBoard();
		adapter = new GameRecyclerViewAdapter(this);
		adapter.setData(datas);
		mRcv = (RecyclerView) findViewById(R.id.rcv_test);

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
		tv_level = (TextView) findViewById(R.id.textView_level);
		tv_level.setText("level"+level);
		adapter.setOnItemClickListener(new GameRecyclerViewAdapter.onItemClickListener() {
			private int count = 0;
			@Override
			public void onItemClick(View view, int position) {
				addMsg(count+++"点！");
				adapter.notifyItemChanged(position);

			}
		});

	}

	private void initMsgLayout() {
		fl_msg = (FrameLayout)findViewById(R.id.fl_msg);

	}

	/**消息栏初始化**/
	private void initMsgBoard() {
		adapter_msg = new MsgRecyclerViewAdapter(this);
		adapter_msg.setList(list_msg);
		rv_msg = (RecyclerView) findViewById(R.id.rcv_msg);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
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

	}

	private void addMsg(String msg){
		adapter_msg.addData(0,new MsgModel(msg));
		rv_msg.scrollToPosition(0);
		CustomToast.showToast(getApplicationContext(),msg, Toast.LENGTH_LONG);
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
}
