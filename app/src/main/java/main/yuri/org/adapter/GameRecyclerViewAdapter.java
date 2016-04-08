package main.yuri.org.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import main.yuri.org.dungeonlord.R;
import main.yuri.org.model.GameGridModel;

public class GameRecyclerViewAdapter extends RecyclerView.Adapter<GameRecyclerViewAdapter.ViewHolder> {
	private List<GameGridModel> datas;
	private Context context;
//	private onItemClickListener onItemClickListener;

	public void setData(List<GameGridModel> datas) {
		this.datas = datas;
	}

	public GameRecyclerViewAdapter(Context context) {
		super();
		this.context = context;
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
			tv_msg = (TextView) itemView.findViewById(R.id.textView_msg);
			iv_content = (ImageView) itemView.findViewById(R.id.imageView_content);
			fl_gamegrid = (FrameLayout) itemView.findViewById(R.id.layout_gamegrid);
		}
		public TextView tv_msg;
		public FrameLayout fl_gamegrid;
		public ImageView iv_content;
	}

	@Override
	public void onBindViewHolder(ViewHolder arg0, final int arg1) {
		arg0.tv_msg.setText("");
		arg0.tv_msg.setBackgroundResource(android.R.color.darker_gray);
		if(datas.get(arg1).isActive()&&datas.get(arg1).getType()==0){
			arg0.tv_msg.setVisibility(View.INVISIBLE);
			arg0.iv_content.setVisibility(View.INVISIBLE);  
		}else if(datas.get(arg1).isActive()&&datas.get(arg1).getType()==1){
			arg0.iv_content.setVisibility(View.VISIBLE); 
			arg0.tv_msg.setVisibility(View.INVISIBLE);
			arg0.iv_content.setImageResource(R.drawable.ci);
			AnimationDrawable animationDrawable = (AnimationDrawable) arg0.iv_content.getDrawable();  
            animationDrawable.start();
		}else{
			arg0.tv_msg.setVisibility(View.VISIBLE);
		}
		arg0.itemView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(onItemClickListener != null){
					onItemClickListener.onItemClick(v, arg1);
				}
				
			}
		});
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup arg0, int arg1) {
		View v = LayoutInflater.from(arg0.getContext()).inflate(R.layout.item_gamegrid, arg0,false);
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
