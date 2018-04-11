package com.chitty.wechatmomentsdemo.views;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chitty.wechatmomentsdemo.R;

/**
 *
 * @author 开源中国
 *
 */
public class EmptyLayout extends LinearLayout implements View.OnClickListener {
	//网络没有连接
	public static final int NETWORK_ERROR = 1;
	//正在加载数据
	public static final int NETWORK_LOADING = 2;
	//没有数据
	public static final int NODATA = 3;
	//加载成功 不显示emptylayout了
	public static final int HIDE_LAYOUT = 4;
	public static final int NODATA_ENABLE_CLICK = 5;

	public static final int EMPTY_PROMPT = 6;
	public static final int NO_NETWORK = 7;
    public static final int EMPTY_PROMPT_INVITE = 8;

	private ProgressBar animProgress;
	private boolean clickEnable = true;
	private final Context context;
	public ImageView img;
	private OnClickListener listener;
	private int mErrorState;
	private String strNoDataContent = "";
	private TextView tv;

	public EmptyLayout(Context context) {
		super(context);
		this.context = context;
		init();
	}

	public EmptyLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		init();
	}

	private void init() {
		View view = View.inflate(context, R.layout.view_error_layout, null);
		img = (ImageView) view.findViewById(R.id.img_error_layout);
		tv = (TextView) view.findViewById(R.id.tv_error_layout);
		RelativeLayout mLayout = (RelativeLayout) view.findViewById(R.id.pageerrLayout);
		animProgress = (ProgressBar) view.findViewById(R.id.animProgress);
		setBackgroundColor(-1);
		setOnClickListener(this);
		img.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (clickEnable) {
					// setErrorType(NETWORK_LOADING);
					if (listener != null)
						listener.onClick(v);
				}
			}
		});
		addView(view);
		changeErrorLayoutBgMode(context);
	}

	public void changeErrorLayoutBgMode(Context context1) {
		// mLayout.setBackgroundColor(SkinsUtil.getColor(context1,
		// "bgcolor01"));
		// tv.setTextColor(SkinsUtil.getColor(context1, "textcolor05"));
	}

	public void dismiss() {
		mErrorState = HIDE_LAYOUT;
		setVisibility(View.GONE);
	}

	public int getErrorState() {
		return mErrorState;
	}

	public boolean isLoadError() {
		return mErrorState == NETWORK_ERROR;
	}

	public boolean isLoading() {
		return mErrorState == NETWORK_LOADING;
	}

	@Override
	public void onClick(View v) {
		if (clickEnable) {
			// setErrorType(NETWORK_LOADING);
			if (listener != null)
				listener.onClick(v);
		}
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		onSkinChanged();
	}

	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
	}

	public void onSkinChanged() {
	}

	public void setErrorMessage(String msg) {
		tv.setText(msg);
	}

	/**
	 * 新添设置背景
	 * @param imgResource 图片的id
	 * @param msg  图片下面的textView显示的文字
	 *
	 */
	public void setErrorImag(int imgResource,String msg) {
		try {
			img.setBackgroundResource(imgResource);
			tv.setText(msg);
		} catch (Exception e) {
		}
	}

	public void setErrorType(int i) {
		setVisibility(View.VISIBLE);
		switch (i) {
			case NETWORK_ERROR:
				mErrorState = NETWORK_ERROR;
				if (isConnectivity(context)) {
					tv.setText(R.string.error_view_load_error_click_to_refresh);
					img.setBackgroundResource(R.mipmap.pagefailed_bg);// pagefailed_bg
				} else {
					tv.setText(R.string.error_view_network_error_click_to_refresh);
					img.setBackgroundResource(R.mipmap.page_icon_network);// page_icon_network
				}
				img.setVisibility(View.VISIBLE);
				animProgress.setVisibility(View.GONE);
				clickEnable = true;
				break;
			case NETWORK_LOADING:
				mErrorState = NETWORK_LOADING;
				animProgress.setVisibility(View.VISIBLE);
				img.setVisibility(View.GONE);
				tv.setText(R.string.error_view_loading);
				clickEnable = false;
				break;
			case NODATA:
				mErrorState = NODATA;
				img.setBackgroundResource(R.mipmap.page_icon_empty);// page_icon_empty
				img.setVisibility(View.VISIBLE);
				animProgress.setVisibility(View.GONE);
				setTvNoDataContent();
				clickEnable = true;
				break;
			case HIDE_LAYOUT:
				setVisibility(View.GONE);
				break;
			case NODATA_ENABLE_CLICK:
				mErrorState = NODATA_ENABLE_CLICK;
				img.setBackgroundResource(R.mipmap.page_icon_empty);// page_icon_empty
				img.setVisibility(View.VISIBLE);
				animProgress.setVisibility(View.GONE);
				setTvNoDataContent();
				clickEnable = true;
				break;
			case EMPTY_PROMPT:// 自定义提示文案
//				mErrorState = EMPTY_PROMPT;
				img.setVisibility(View.GONE);
				animProgress.setVisibility(View.GONE);
				tv.setText(strNoDataContent);
				tv.setTextSize(24);
				tv.setTextColor(getResources().getColor(R.color.gray_white));
				TextPaint tp = tv.getPaint();
				tp.setFakeBoldText(true);//设置粗体
				clickEnable = false;
				break;
			case NO_NETWORK:// 无网络
//				mErrorState = NO_NETWORK;
				img.setVisibility(View.GONE);
				animProgress.setVisibility(View.GONE);
				tv.setText(getResources().getString(R.string.network_unable));
				tv.setTextSize(24);
				tv.setTextColor(getResources().getColor(R.color.gray_white));
				TextPaint tp3 = tv.getPaint();
				tp3.setFakeBoldText(true);//设置粗体
				clickEnable = false;
				break;
		default:
			break;
		}
	}

	public void setNoDataContent(String noDataContent) {
		strNoDataContent = noDataContent;
	}

	public void setOnLayoutClickListener(OnClickListener listener) {
		this.listener = listener;
	}

	public void setTvNoDataContent() {
		if (!strNoDataContent.equals(""))
			tv.setText(strNoDataContent);
		else
			tv.setText(R.string.error_view_no_data);
	}

	@Override
	public void setVisibility(int visibility) {
		if (visibility == View.GONE)
			mErrorState = HIDE_LAYOUT;
		super.setVisibility(visibility);
	}

	/**
	 *
	 * 描述：是否有网络连接.androidbase中AbWifiUtil中的方法
	 * @param context
	 * @return
	 */
	public static boolean isConnectivity(Context context) {

		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return ((connectivityManager.getActiveNetworkInfo() != null && connectivityManager
				.getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || telephonyManager
				.getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
	}

}
