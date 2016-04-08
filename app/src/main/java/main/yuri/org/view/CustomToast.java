package main.yuri.org.view;

import java.util.ArrayList;
import java.util.List;

import android.R;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 
 * @ClassName: CustomToast
 * @Description: 保证同时只有一个toast出现
 * @author hezhengnan
 * @date 2015-8-31 上午11:29:20
 * 
 */
public class CustomToast {
	private static Toast mToast;
	private static List<String> list = new ArrayList<String>();
	private static CountDownTimer timer = new CountDownTimer(1000,1000) {
		
		@Override
		public void onTick(long millisUntilFinished) {
			
		}
		
		@Override
		public void onFinish() {
			
		}
	};

	public static void showToast(Context mContext, String text, int duration) {
		if(list.size()>20){
			List<String> l = new ArrayList<String>();
			l.addAll(list.subList(list.size()/2, list.size()));
			list.clear();
			list.addAll(l);
		}
		list.add(0,text);
		int size = 4;
		size = size >= list.size()?list.size()-1:4;
		String msg = "";
		for (int i = size; i >= 0; i--) {
			if(msg.equals("")){
				msg = list.get(i);
			}else{
				msg = list.get(i) + "\n" + msg;
			}
		}
		Log.i("msg", msg);
		TextView tv = new TextView(mContext);
		tv.setBackgroundColor(mContext.getResources().getColor(
				R.color.holo_orange_light));
		tv.setTextColor(mContext.getResources().getColor(R.color.white));
		tv.setPadding(20, 20, 20, 20);
		tv.setText(getMsg(mContext));
		if (mToast == null) {
			mToast = new Toast(mContext);
		}
		mToast.setView(tv);
		
		mToast.show();
	}
	private static SpannableStringBuilder getMsg(Context mContext){
		SpannableStringBuilder builder = new SpannableStringBuilder();
		int size = 4;
		size = size >= list.size()?list.size()-1:4;
		SpannableString str;
		int[] color = {
				mContext.getResources().getColor(R.color.holo_green_dark)
				,mContext.getResources().getColor(R.color.holo_blue_bright)
				,mContext.getResources().getColor(R.color.holo_blue_dark)
				,mContext.getResources().getColor(R.color.holo_blue_light)
				,mContext.getResources().getColor(R.color.holo_green_light)};
		String msg = "";
		for (int i = 0; i <= size; i++) {
			String s = "";
			if(msg.equals("")){
				msg = list.get(i);
				s = list.get(i);
				str = new SpannableString(s);
			}else{
				msg = list.get(i) + "\n" + msg;
				s = "\n"+list.get(i);
				str = new SpannableString(s);
			}
			str.setSpan(new ForegroundColorSpan(color[i]), 0, s.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
			builder.append(str);
		}
		return builder;
	}

	public static void showToast(Context mContext, int resId, int duration) {
		showToast(mContext, mContext.getResources().getString(resId), duration);
	}
}
