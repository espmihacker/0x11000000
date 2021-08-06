package com.tencent.dog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.os.Build;
public class FloatService extends Service
{
	private static int 屏幕宽,屏幕高;

	public static void ShowFloat(Context context)
	{
		if (Instance == null)
		{
			Intent intent = new Intent(context, FloatService.class);
			context.startService(intent);
		}
	}

	public static void HideFloat()
	{
		if (Instance != null)
		{
			Instance.Hide();
		}
	}

	private static FloatService Instance;

	@Override
	public void onCreate()
	{
		super.onCreate();
		Instance = this;

		SetFloatView();	
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	public void Hide()
	{
		Instance = null;

		manager.removeView(mFloatLayout);	
		this.stopSelf();			
		this.onDestroy();
	}

	WindowManager manager;
	LinearLayout mFloatLayout;
	ImageView settings;
	ImageView search;
	WindowManager.LayoutParams params;

	private void SetFloatView()
	{
		LayoutInflater inflater = LayoutInflater.from(getApplication());
		mFloatLayout = (LinearLayout) inflater.inflate(R.layout.C, null);
		params = getParams();
		manager = (WindowManager) getApplication().getSystemService(getApplication().WINDOW_SERVICE);
		manager.addView(mFloatLayout, params);
	}

	private WindowManager.LayoutParams getParams()
	{
		WindowManager.LayoutParams wmParams = new WindowManager.LayoutParams();
        if (Integer.parseInt(Build.VERSION.SDK) >= 26) 
		    wmParams.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
		else
		wmParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;// 设置window type
        wmParams.format = PixelFormat.RGBA_8888;            // 设置图片格式，效果为背景透明	
		wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE // 设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）
			| WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
			| WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
			| WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
			| WindowManager.LayoutParams.FLAG_FULLSCREEN
			| WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
			| WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED;//硬件加速   

		wmParams.gravity = Gravity.LEFT | Gravity.TOP;
		wmParams.x = 0;
		wmParams.y = 0;
		wmParams.width = getResources().getDisplayMetrics().widthPixels; 
		wmParams.height =getResources().getDisplayMetrics().heightPixels;
		return wmParams;
		
	}
}
