package main.yuri.org.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.ActionBar;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v7.app.AlertDialog;
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
    private TextView tv_level, tv_actor;
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
    private ImageButton ib_msg;

    private FrameLayout fl_msg;

    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private GameLogic gameLogic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initView();
        gameLogic = new GameLogic(datas, list_msg);
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
        updatePlayerStatus();
    }

    private void updatePlayerStatus() {
        ActorModel am = gameLogic.getPlayer().getActorModel();
        tv_actor.setText(am.getName() + "\nH: " + am.getHealthPoint()
                + "    A: " + am.getAttackPoint()
                + "\nD: " + am.getDefensePoint()
                + "    S: " + am.getSpeed());
    }

    private void move(int position) {
        int x = 0, y = 0;
        if (position < 3) {
            y = -1;
        }
        if (position < 9 && position >= 6) {
            y = 1;
        }
        if (position % 3 == 0) {
            x = -1;
        }
        if ((position - 2) % 3 == 0) {
            x = 1;
        }
        gameLogic.setGameList(gameLogic.getPos()[0] + x, gameLogic.getPos()[1] + y);
        adapter.notifyItemRangeChanged(0, 9);

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
        mNavigationView = (NavigationView) findViewById(R.id.nav);
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return false;
            }
        });
        tv_level = (TextView) findViewById(R.id.textView_level);
        tv_level.setText("level" + level);
        tv_actor = (TextView) findViewById(R.id.textView_actor);
    }

    private void initGameBoard() {
        adapter = new GameRecyclerViewAdapter(this);
        adapter.setData(datas);
        mRcv = (RecyclerView) findViewById(R.id.rcv_test);
        ViewGroup.LayoutParams lp = mRcv.getLayoutParams();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        if (lp.height > lp.width) {
            lp.height = lp.width;
        } else {
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
            }
        });
    }

    private void gameLogic(int position) {
        String msg = "";
        int color = Color.WHITE;
        String floatingMsg = "";
        int floatingColor = Color.WHITE;
        String centerFloatingMsg = "";
        int centerFloatingColor = Color.WHITE;
        switch (datas.get(position).getType()) {
            case 0://空
                move(position);//移动
                break;
            case 1://人
                if (position != 4) {
                    attackActor(position);//攻击
                }
                break;
            case 2://物
                pickUpItem(position);
                break;
        }
        if (gameLogic.getEnemyCount() < 0) {
            showEndDialog(STATUS_SUCCESS);
        }
        updatePlayerStatus();


    }

    private void pickUpItem(int position) {
        ActorModel player = datas.get(4).getActorModel();
        ItemModel item = datas.get(position).getItemModel();
        pickCount(player, item);
        if (player.getHealthPoint() <= 0) {//如果玩家生命为0游戏结束
            showEndDialog(STATUS_FAILED);
        }
        datas.get(position).setType(0);//物品变成空地
        adapter.notifyItemChanged(position);
    }

    private void attackActor(int position) {
        ActorModel player = datas.get(4).getActorModel();
        ActorModel enemy = datas.get(position).getActorModel();
        attackCount(position);
        if (player.getHealthPoint() <= 0) {//如果玩家生命为0游戏结束
            showEndDialog(STATUS_FAILED);
        }
        if (enemy.getHealthPoint() <= 0) {
            datas.get(position).setType(0);//如果敌人生命为0，变成空地
            adapter.notifyItemChanged(position);
            gameLogic.setEnemyCount(gameLogic.getEnemyCount() - 1);
        }
    }

    private void GameOver() {
        Intent intent = getIntent();
        overridePendingTransition(0, 0);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);
    }

    /**
     * 依次是血量防御伤害和速度
     **/
    private void attackCount(int position) {
        ActorModel player = datas.get(4).getActorModel();
        ActorModel enemy = datas.get(position).getActorModel();
        int healthPoint1 = player.getHealthPoint();
        int healthPoint2 = enemy.getHealthPoint();
        int defensePoint1 = player.getDefensePoint();
        int defensePoint2 = enemy.getDefensePoint();
        int attackPoint1 = player.getAttackPoint();
        int attackPoint2 = enemy.getAttackPoint();
        int speed1 = player.getSpeed();
        int speed2 = enemy.getSpeed();
        int damage1 = attackPoint1 - defensePoint2;
        int damage2 = attackPoint2 - defensePoint1;
        if (damage1 < 0) {
            damage1 = 0;
        }
        if (damage2 < 0) {
            damage2 = 0;
        }
        if (speed1 > speed2) {
            healthPoint2 = healthPoint2 - damage1;
            updateMsgList(enemy.getName() + "遭受到" + damage1 + "点伤害", Color.YELLOW);
            showFloatingMsg(position, -damage1 + "", Color.RED);
            if (healthPoint2 > 0) {
                healthPoint1 = healthPoint1 - damage2;
                updateMsgList(player.getName() + "遭受到" + damage2 + "点伤害", Color.RED);
                showFloatingMsg(4, -damage2 + "", Color.RED);
            }
        } else {
            healthPoint1 = healthPoint1 - damage2;
            updateMsgList(player.getName() + "遭受到" + damage2 + "点伤害", Color.RED);
            showFloatingMsg(4, -damage2 + "", Color.RED);
            if (healthPoint1 > 0) {
                healthPoint2 = healthPoint2 - damage1;
                updateMsgList(enemy.getName() + "遭受到" + damage1 + "点伤害", Color.YELLOW);
                showFloatingMsg(position, -damage1 + "", Color.RED);
            }
        }
        player.setHealthPoint(healthPoint1);
        enemy.setHealthPoint(healthPoint2);
//		updateMsgList(msg,color);
//		showFloatingMsg(position,floatingMsg,floatingColor);
    }

    /**
     * 依次是血量防御伤害和速度
     **/
    private void pickCount(ActorModel player, ItemModel item) {
        int healthPoint1 = player.getHealthPoint();
        int healthPoint2 = item.getHealthPoint();
        int defensePoint1 = player.getDefensePoint();
        int defensePoint2 = item.getDefensePoint();
        int attackPoint1 = player.getAttackPoint();
        int attackPoint2 = item.getAttackPoint();
        int speed1 = player.getSpeed();
        int speed2 = item.getSpeed();
        player.setHealthPoint(healthPoint1 + healthPoint2);
        if (healthPoint2 != 0) {
            updateMsgList(player.getName() + "获得了" + healthPoint2 + "点血", Color.GREEN);
            showFloatingMsg(4, "+" + healthPoint2, Color.GREEN);
        }
        player.setAttackPoint(attackPoint1 + attackPoint2);
        if (attackPoint2 != 0) {
            updateMsgList(player.getName() + "获得了" + attackPoint2 + "点攻击力", Color.GREEN);
            showFloatingMsg(4, "+" + attackPoint2, Color.WHITE);

        }
        player.setDefensePoint(defensePoint1 + defensePoint2);
        if (defensePoint2 != 0) {
            updateMsgList(player.getName() + "获得了" + defensePoint2 + "点防御力", Color.GREEN);
            showFloatingMsg(4, "+" + defensePoint2, Color.YELLOW);
        }
        player.setSpeed(speed1 + speed2);
        if (speed2 != 0) {
            updateMsgList(player.getName() + "获得了" + speed2 + "点速度", Color.GREEN);
            showFloatingMsg(4, "+" + speed2, Color.BLUE);
        }

    }

    private void showFloatingMsg(int position, String msg, int color) {
        if (mRcv == null) {
            return;
        }
        int[] location = new int[2];
        mRcv.getLocationOnScreen(location);
        int itemWidth = mRcv.getWidth() / 3;
        int n = 0;
        int m = 0;
        if (position < 3) {
            m = 0;
        } else if (position >= 3 && position < 6) {
            m = 1;
        } else if (position < 9 && position >= 6) {
            m = 2;
        }
        if (position % 3 == 0) {
            n = 0;
        } else if ((position - 1) % 3 == 0) {
            n = 1;
        } else if ((position - 2) % 3 == 0) {
            n = 2;
        }
        int x = location[0] + itemWidth * n + itemWidth / 5;
        int y = location[1] + itemWidth * m;
        final TextView tv_msg = new TextView(this);
        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        tv_msg.setLayoutParams(lp);
        tv_msg.setTextSize(14);
        tv_msg.setVisibility(View.VISIBLE);
        fl_msg.addView(tv_msg);
        tv_msg.setX(x);
        tv_msg.setY(y);
        tv_msg.setTextColor(color);
        tv_msg.setText(msg);


        PropertyValuesHolder b1 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        PropertyValuesHolder b2 = PropertyValuesHolder.ofFloat("scaleX", 1f, 1f);
        PropertyValuesHolder b3 = PropertyValuesHolder.ofFloat("scaleY", 1f, 1f);
        ObjectAnimator oc = ObjectAnimator.ofPropertyValuesHolder(tv_msg, b1, b2, b3);
        oc.setDuration(0);
        oc.start();
        PropertyValuesHolder a1 = PropertyValuesHolder.ofFloat("alpha", 1f, 0f);
        PropertyValuesHolder a2 = PropertyValuesHolder.ofFloat("scaleX", 1f, 1.5f);
        PropertyValuesHolder a3 = PropertyValuesHolder.ofFloat("scaleY", 1f, 1.5f);
        ObjectAnimator ob = ObjectAnimator.ofPropertyValuesHolder(tv_msg, a1, a2, a3);
        ob.setDuration(1500);
        ob.start();
        ob.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                fl_msg.removeView(tv_msg);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void initMsgLayout() {
        fl_msg = (FrameLayout) findViewById(R.id.fl_msg);

    }

    /**
     * 消息栏初始化
     **/
    private void initMsgBoard() {
        adapter_msg = new MsgRecyclerViewAdapter(this);
        adapter_msg.setList(list_msg);
        rv_msg = (RecyclerView) findViewById(R.id.rcv_msg);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ViewGroup.LayoutParams lp = rv_msg.getLayoutParams();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        lp.height = dm.heightPixels / 6;
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
        ib_msg = (ImageButton) findViewById(R.id.ib_msg);
        ib_msg.setOnClickListener(this);

    }

    private void updateMsgList(String msg, int color) {
        if (msg.equals("")) {
            return;
        }
        gameLogic.addMsg(msg, color);
        adapter_msg.notifyItemRangeChanged(0,adapter_msg.getItemCount());
        rv_msg.scrollToPosition(0);
        if (gameLogic.getList_msg().size() > 0 && !gameLogic.getList_msg().get(0).getMsg().equals("")) {
            Snackbar.make(getCurrentFocus(), gameLogic.getList_msg().get(0).getMsg(), Snackbar.LENGTH_SHORT).show();
        }
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
                ok = "继续前进";
                title = "";
                msg = "此层地牢清除完成";
                break;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(msg);
        builder.setTitle(title);
        builder.setPositiveButton(ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mRcv.setEnabled(true);
                if (status == STATUS_FAILED) {
                    level = 1;
                    tv_level.setText("level" + level);
                    GameOver();
                } else if (status == STATUS_SUCCESS) {
                    level++;
                    tv_level.setText("level" + level);
                    loadData(level);
                }
                dialog.dismiss();
            }
        });
        builder.create().show();
        Snackbar.make(getCurrentFocus(), title + "\n" + msg, Snackbar.LENGTH_SHORT).show();
        CountDownTimer counter = new CountDownTimer(1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {


            }
        };
        counter.start();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ib_msg:
                closeMsg(v);
                break;
        }
    }

    private void closeMsg(View view) {
        PropertyValuesHolder a3 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0f);
        if (rv_msg.getScaleY() == 0) {
            a3 = PropertyValuesHolder.ofFloat("scaleY", 0f, 1f);
        }
        ObjectAnimator oa = ObjectAnimator.ofPropertyValuesHolder(rv_msg, a3);
        oa.setDuration(100);
        oa.start();
        oa.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                rv_msg.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (rv_msg.getScaleY() == 0) {
                    rv_msg.setVisibility(View.GONE);
                } else {
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
