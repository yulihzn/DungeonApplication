package main.yuri.org.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import main.yuri.org.dungeonlord.R;
import main.yuri.org.model.MsgModel;

public class MsgRecyclerViewAdapter extends RecyclerView.Adapter<MsgRecyclerViewAdapter.ViewHolder> {
	private List<MsgModel> list;
	private Context context;

	public void setList(List<MsgModel> list) {
		this.list = list;
	}

	public MsgRecyclerViewAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public int getItemCount() {
		if (list != null) {
			return list.size();
		}
		return 0;
	}

	public static class ViewHolder extends RecyclerView.ViewHolder {
		public ViewHolder(View itemView) {
			super(itemView);
			tv_msg = (TextView) itemView.findViewById(R.id.textView_msg);
		}
		public TextView tv_msg;
	}

	@Override
	public void onBindViewHolder(final ViewHolder arg0, final int arg1) {
		arg0.tv_msg.setTextColor(list.get(arg1).getColor());
		arg0.tv_msg.setText(list.get(arg1).getMsg());
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
		View v = LayoutInflater.from(arg0.getContext()).inflate(R.layout.item_msg, arg0,false);
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

	public void addData(int position,MsgModel msgModel) {
		list.add(position,msgModel);
		notifyItemInserted(position);
	}

	public void removeData(int position) {
		list.remove(position);
		notifyItemRemoved(position);
	}

}
