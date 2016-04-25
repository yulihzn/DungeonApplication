package main.yuri.org.adapter;

import java.util.List;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import main.yuri.org.dungeonlord.R;
import main.yuri.org.model.GameGridModel;

public class GameRecyclerViewAdapter extends RecyclerView.Adapter<GameRecyclerViewAdapter.ViewHolder> {
	private List<GameGridModel> datas;
	private Context context;

	public void setData(List<GameGridModel> datas) {
		this.datas = datas;
	}

	public GameRecyclerViewAdapter(Context context) {
		super();
		this.context = context;
	}
	private ObjectAnimator oa;
	private void startAnimator(View view){
		PropertyValuesHolder a1 = PropertyValuesHolder.ofFloat("alpha",0.7f,0f);
		PropertyValuesHolder a2 = PropertyValuesHolder.ofFloat("scaleX",0.9f,1f);
		PropertyValuesHolder a3 = PropertyValuesHolder.ofFloat("scaleY",0.9f,1f);
		oa = ObjectAnimator.ofPropertyValuesHolder(view,a1,a2,a3);
		oa.setDuration(500);
		view.setBackgroundColor(Color.WHITE);
		oa.start();
	}


	@Override
	public int getItemCount() {
		if (datas != null) {
			return datas.size();
		}
		return 0;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public ViewHolder(View itemView) {
			super(itemView);
			tv_actor = (TextView) itemView.findViewById(R.id.textView_actor);
			tv_msg = (TextView) itemView.findViewById(R.id.textView_msg);
			iv_performer = (ImageView) itemView.findViewById(R.id.imageView_performer);
			cv_content = (CardView) itemView.findViewById(R.id.layout_cardView);
		}
		public TextView tv_actor;
		public CardView cv_content;
		public ImageView iv_performer;
		public TextView tv_msg;
	}

	@Override
	public void onBindViewHolder(final ViewHolder arg0, final int arg1) {
		//0 空 1 怪 2 物
		String name = "";
		int color = Color.WHITE;
		String msg = "";
		if(datas.size() > 0){
			switch (datas.get(arg1).getType()){
				case 0:
					name = "";
					break;
				case 1:
					name = datas.get(arg1).getActorModel().getName();
					color = datas.get(arg1).getActorModel().getColor();
					break;
				case 2:
					name = datas.get(arg1).getItemModel().getName();
					color = datas.get(arg1).getItemModel().getColor();
					break;
			}
		}
		arg0.tv_actor.setText(name);
		arg0.tv_actor.setTextColor(color);
		arg0.itemView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(onItemClickListener != null){
					startAnimator(arg0.tv_msg);
					onItemClickListener.onItemClick(v, arg1);

				}
				
			}
		});
		arg0.itemView.setOnTouchListener(new ItemTouchListener(arg0));
		arg0.itemView.setOnKeyListener(new ItemKeyListener(arg0));
	}

	class ItemTouchListener implements View.OnTouchListener{
		ViewHolder viewHolder;
		public ItemTouchListener(ViewHolder viewHolder) {
			this.viewHolder = viewHolder;
		}
		@Override
		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction()){
				case MotionEvent.ACTION_DOWN:
					break;
			}
			return false;
		}
	}
	class ItemKeyListener implements View.OnKeyListener{
		ViewHolder viewHolder;
		public ItemKeyListener(ViewHolder viewHolder) {
			this.viewHolder = viewHolder;
		}

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			boolean isDpad = false;
			int index = 0;
			if(KeyEvent.ACTION_DOWN == event.getAction()){
				switch (keyCode){
					case KeyEvent.KEYCODE_DPAD_UP:
						index = 1;
						isDpad = true;
						break;
					case KeyEvent.KEYCODE_DPAD_DOWN:
						index = 7;
						isDpad = true;
						break;
					case KeyEvent.KEYCODE_DPAD_LEFT:
						index = 3;
						isDpad = true;
						break;
					case KeyEvent.KEYCODE_DPAD_RIGHT:
						index = 5;
						isDpad = true;
						break;
				}
			}
			if(isDpad && onItemClickListener != null){
				startAnimator(viewHolder.tv_msg);
				onItemClickListener.onItemClick(v, index);

			}
			return isDpad;
		}
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		View v = LayoutInflater.from(arg0.getContext()).inflate(R.layout.item_gamegrid, arg0,false);
		DisplayMetrics dm = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dm);
		ViewGroup.LayoutParams lp = v.getLayoutParams();
		int offset = (int)context.getResources().getDimension(R.dimen.bottom_offset);
		offset = 0;
		int w = dm.widthPixels/3 - offset;
		if(dm.widthPixels > dm.heightPixels){
			w = dm.heightPixels/3 - offset;
		}
		lp.width = w;
		lp.height = w;
		v.setLayoutParams(lp);
		ViewHolder holder = new ViewHolder(v);
		return holder;
	}
	
	public void setOnItemClickListener(onItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public interface onItemClickListener {
		public void onItemClick(View view, int position);
	}
	private onItemClickListener onItemClickListener;

}
