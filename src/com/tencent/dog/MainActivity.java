package com.tencent.dog;
import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.graphics.*;
import java.net.*;
import java.io.*;
import okhttp3.*;
import java.util.concurrent.*;
import android.content.*;
import com.alibaba.fastjson.*;
import java.util.*;
import java.text.*;
import RC4;
import android.provider.*;
import android.net.*;
import android.content.pm.*;
import android.*;
import android.util.*;
import java.math.*;
import android.widget.CompoundButton.*;
import com.tencent.dog.YZ.*;

public class MainActivity extends Activity {
    private ViewClickFun viewClickFun=new ViewClickFun();
    private static Button sendButton=null;
    private static Button undoButton=null;
    private static EditText signEdit=null;
	private boolean 是否已经打开悬浮窗=false;
    private ImageButton mbutton;
    private boolean 悬浮球 = false;
    private boolean 悬浮窗 = false;
    private int mTouchStartX, mTouchStartY;//手指按下时坐标
    private int 按下X, 按下Y;
    private boolean isMove = false;
    private boolean 移动=false;
    private WindowManager mwindow;
    private WindowManager.LayoutParams lparam;
    private WindowManager mwMenu;
    private WindowManager.LayoutParams mparam;
    private LayoutInflater inflater;
    private View dis;
    private View displayMenu;
	private static int 屏幕宽=0,屏幕高=0;
	private static String 地图="0";
	private static String 地图输出="0";
	private Switch 绘制按钮;
	private boolean 注入绘制按钮状态;
	private static String 绘制开关3="false",人物名字3="false",人物距离3="false",人物射线3="false",人物骨骼3="false",人物信息3="false",附近人数3="false",背敌提示3="false";
	private CheckBox 人物名字2,人物距离2,人物射线2,人物骨骼2,人物信息2,附近人数2,背敌提示2;
	private TextView 时间;
    private boolean 人物名字1,人物距离1,人物射线1,人物骨骼1,人物信息1,附近人数1,背敌提示1;
    EditText edittext;
	private String 模式="ROOT";
	
	/*******************悬浮窗权限**********/
	public void 检查悬浮窗权限()
	{
		if (!Settings.canDrawOverlays(this))//如果不可以绘制悬浮窗
		{
			Toast.makeText(this, "请开启悬浮窗权限", Toast.LENGTH_LONG).show();
			startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName())), 0);
		}
	}
	/*******************状态栏美化**********/
	public void 状态栏(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		}
	}
	/*******************调用模式**********/
	public void 模式()//弹出弹窗，，选择模式的弹窗，选择模式的弹窗
	{
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

		alertDialog.setTitle("模式选择");
		alertDialog.setMessage("请选择你运行本程序的模式，请慎重考虑");
		alertDialog.setCancelable(false);
		alertDialog.setPositiveButton("ROOT", new DialogInterface.OnClickListener(){                              
				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					模式="ROOT";
					MyToast.showLong(MainActivity.this,"当前处于"+模式+"模式");
				}
			});
		alertDialog.setNegativeButton("虚拟机", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					模式="虚拟机";
					MyToast.showLong(MainActivity.this,"当前处于"+模式+"模式");
				}
			});
		alertDialog.setNeutralButton("框架", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					模式="框架";
					MyToast.showLong(MainActivity.this,"当前处于"+模式+"模式");
				}
			});
		alertDialog.show();//展示弹窗
	}

	/*******************存储权限**********/
	public void 储存权限(){
		boolean isGranted = true;
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
            if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
			if (!isGranted) {
                this.requestPermissions(
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission
                        .ACCESS_FINE_LOCATION,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    102);
            }
        }
	}
	/*******************分辨率获取**********/
	
	public void 分辨率()
	{
		WindowManager windowManager = (WindowManager)this.getSystemService(WINDOW_SERVICE);
		Display display = windowManager.getDefaultDisplay();
		Point outPoint = new Point();
		display.getRealSize(outPoint);
		屏幕宽 = outPoint.x;
		屏幕高 = outPoint.y;
		写入("/sdcard/y",String.valueOf(屏幕宽));
		写入("/sdcard/x",String.valueOf(屏幕高));
		写入("/sdcard/b.log","");
	}

	public static String 屏幕k() {
        return String.valueOf(屏幕宽);
    }
	public static String 屏幕g() {
        return String.valueOf(屏幕高);
    }
	
	/*******************su执行**********/
	private void 执行(String shell) {
        String s=shell;
        try {
            Runtime.getRuntime().exec(s, null, null);//执行
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	/*******************文件写入**********/
	public static void 写入(String 路径,String 内容){
        try {
            FileWriter fw = new FileWriter(路径);
            fw.write(内容);
            fw.close();
        } catch (IOException e) {}
    }
	
    final Handler hander=new Handler(){
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            String content=(String)msg.obj;
            String con= BaseUtil.decode(content, "UTF-8");
            switch (msg.what) {
                case 0:
                    sendButton.setText("登录");
                    if (EggUtil.isJson(con)) {
                        JSONObject jsonO=JSON.parseObject(con);
                        String code=jsonO.getString("code");
                        String tips=jsonO.getString("msg");
                        if (code.equals("1")) {
                            JSONObject info=JSON.parseObject(jsonO.getString("cardinfo"));
                            String type=info.getString("type");
                            String card=info.getString("card");
                            String value=info.getString("number");
                            String endtime=info.getString("endtime");
                            JSONObject return_verify=JSON.parseObject(jsonO.getString("return_verify"));
                            JSONObject min=JSON.parseObject(return_verify.getString("minute"));
                            String dense_md5=min.getString("dense_md5");
                            String card_cache=MainActivity.this.getFilesDir().getPath() + "card_cache";
                            EggUtil.writeFile(card_cache, card);
                            SimpleDateFormat formatter= new SimpleDateFormat("yyyyMMddHHmm");
                            Date date = new Date(System.currentTimeMillis());
                            String t=formatter.format(date);
                            String md5_str=EasyConfig.APPID + EasyConfig.APIKEY + t;
                            if (EggUtil.getMD5(md5_str).equals(dense_md5)) {//返回值校验正常
                                Toast.makeText(MainActivity.this, "登录成功\n到期时间:" + endtime + "\n卡密类型:" + type + "，面值:" + value, Toast.LENGTH_SHORT).show();
								开启悬浮窗();
								时间.setText("剩余时间："+endtime);
							}
							else 
							{
                                Toast.makeText(MainActivity.this, "登录失败，数据异常", Toast.LENGTH_SHORT).show();
                            }
                        } 
						else 
						{
                            Toast.makeText(MainActivity.this, "登录失败:" + tips, Toast.LENGTH_SHORT).show();
                        }

                    } 
					else
					{
                        Toast.makeText(MainActivity.this, "登录失败，服务器异常", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case 1:
                    undoButton.setText("解绑");
                    if (EggUtil.isJson(con)) {
                        JSONObject jsonO=JSON.parseObject(con);
                        String code=jsonO.getString("code");
                        String tips=jsonO.getString("msg");
                        if (code.equals("1")) {
                            Toast.makeText(MainActivity.this, "解绑成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "解绑失败:" + tips, Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(MainActivity.this, "失败，服务器异常", Toast.LENGTH_SHORT).show();
                    }

                    break;

                default:
            }
        }
    };

    private void giao() {
        String card_cache=MainActivity.this.getFilesDir().getPath() + "card_cache";
        String card=EggUtil.readFile(card_cache);
        //Toast.makeText(MainActivity.this, card, Toast.LENGTH_SHORT).show();
        signEdit = findViewById(R.id.sign_edit);
        signEdit.setText(card);
    }

    private void use_frequency() {
        final String url=EasyConfig.HOST + "/use_frequency";
        Thread thread=new Thread(new Runnable(){		
                @Override
                public void run() {
                    // TODO: Implement this method
                    try {

                        OkHttpClient okHttp = new OkHttpClient.Builder()
                            .connectTimeout(15L, TimeUnit.SECONDS)
                            .readTimeout(15L, TimeUnit.SECONDS)
                            .writeTimeout(15L, TimeUnit.SECONDS)
                            .build();
                        Request req=new Request.Builder()
                            .url(url)
                            .build();

                        Response response=okHttp.newCall(req).execute();

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        thread.start();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		状态栏();
		setContentView(R.layout.main);
        signEdit = findViewById(R.id.sign_edit);
		inflater = inflater.from(this);	
		检查悬浮窗权限();
		储存权限();
		模式();
		分辨率();
		edittext=findViewById(R.id.sign_edit);
		edittext.setText(读取("/sdcard/km.txt"));
		时间 = findViewById(R.id.时间);
		写入("/sdcard/b.log","");
		写入("/sdcard/stop","");
		开启悬浮窗();
		initUi();
        initClick();
        giao();
        use_frequency();
		displayMenu = inflater.inflate(R.layout.xf, null);
		dis = displayMenu.findViewById(R.id.menu);
		//-------------------拖动条分割线---------------
    }
	
    private void initUi() {
        if (Build.VERSION.SDK_INT > 21) {
            Window win=this.getWindow();
            win.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            win.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            win.setStatusBarColor(0xDF00AAE1);
            win.getDecorView().setSystemUiVisibility(0);
        }
    }

    private void initClick() {
        MainActivity.sendButton = findViewById(R.id.send_button);
        MainActivity.sendButton.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1) {
                    // TODO: Implement this method
                    MainActivity.sendButton.setText("正在登录...");
                    viewClickFun.sendButtonClick();
                }
            });
        MainActivity.undoButton = findViewById(R.id.undo_button);
        MainActivity.undoButton.setOnClickListener(new OnClickListener(){
                @Override
                public void onClick(View p1) {
                    MainActivity.undoButton.setText("正在解绑...");
                    viewClickFun.undoButtonClick();
                }
            });
    }

    private class ViewClickFun {
        public void sendButtonClick() {

            if (signEdit.getText().toString().equals("")) {
                Toast.makeText(MainActivity.this, "卡密不能为空", Toast.LENGTH_SHORT).show();
                sendButton.setText("登录");
                return;
            }
			String keySign=edittext.getText().toString();
			keySign=EggUtil.str2HexStr(BaseUtil.encode(signEdit.getText().toString(), "UTF-8"));
            String macid=EggUtil.str2HexStr(BaseUtil.encode(EggUtil.getid(MainActivity.this), "UTF-8"));
            final String url=EasyConfig.HOST + "/verify?card=" + keySign + "&mac=" + macid ;
            //Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();
            // TODO: Implement this method
            Thread thread=new Thread(new Runnable(){		
                    @Override
                    public void run() {
                        // TODO: Implement this method
                        try {
                            Message msg=new Message();
                            OkHttpClient okHttp = new OkHttpClient.Builder()
                                .connectTimeout(15L, TimeUnit.SECONDS)
                                .readTimeout(15L, TimeUnit.SECONDS)
                                .writeTimeout(15L, TimeUnit.SECONDS)
                                .build();
                            Request req=new Request.Builder()
                                .url(url)
                                .build();

                            Response response=okHttp.newCall(req).execute();
                            msg.what = 0;

                            if (response.isSuccessful()) {
                                msg.obj = response.body().string();

                            } else {
                                msg.obj = new String("网络错误");
                            }

                            hander.sendMessage(msg);

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            thread.start();
        }
        public void undoButtonClick() {

            if (signEdit.getText().toString().equals("")) {
                Toast.makeText(MainActivity.this, "卡密不能为空", Toast.LENGTH_SHORT).show();
                undoButton.setText("解绑");
                return;
            }
			String keySign=EggUtil.str2HexStr(BaseUtil.encode(signEdit.getText().toString(), "UTF-8"));
            String macid=EggUtil.str2HexStr(BaseUtil.encode(EggUtil.getid(MainActivity.this), "UTF-8"));
            final String url=EasyConfig.HOST + "/undomac?card=" + keySign + "&mac=" + macid ;
            //Toast.makeText(MainActivity.this, url, Toast.LENGTH_SHORT).show();
            // TODO: Implement this method
            Thread thread=new Thread(new Runnable(){		
                    @Override
                    public void run() {
                        // TODO: Implement this method
                        try {
                            Message msg=new Message();
                            OkHttpClient okHttp = new OkHttpClient.Builder()
                                .connectTimeout(15L, TimeUnit.SECONDS)
                                .readTimeout(15L, TimeUnit.SECONDS)
                                .writeTimeout(15L, TimeUnit.SECONDS)
                                .build();
                            Request req=new Request.Builder()
                                .url(url)
                                .build();

                            Response response=okHttp.newCall(req).execute();
                            msg.what = 1;

                            if (response.isSuccessful()) {
                                msg.obj = response.body().string();

                            } else {
                                msg.obj = new String("网络错误");
                            }

                            hander.sendMessage(msg);

                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            thread.start();
        }
    }
	private boolean 写出资源文件(String outPath, String fileName) throws IOException
	{
		File file = new File(outPath);
		if (!file.exists())
		{
			if (!file.mkdirs())
			{
				Log.e("--Method--", "copyAssetsSingleFile: cannot create directory.");
				return false;
			}
		}
		try
		{
			InputStream inputStream = getAssets().open(fileName);
			File outFile = new File(file, fileName);
			FileOutputStream fileOutputStream = new FileOutputStream(outFile);
			// Transfer bytes from inputStream to fileOutputStream
			byte[] buffer = new byte[1024];
			int byteRead;
			while (-1 != (byteRead = inputStream.read(buffer)))
			{
				fileOutputStream.write(buffer, 0, byteRead);
			}
			inputStream.close();
			fileOutputStream.flush();
			fileOutputStream.close();
			return true;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return false;
		}
	}

	public static String 读取(String strFilePath)
    {
        String path = strFilePath;
        String content = ""; //文件内容字符串
        //打开文件
        File file = new File(path);
        //如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory())
        {
        }
        else
        {
            try {
                InputStream instream = new FileInputStream(file); 
                if (instream != null) 
                {
                    InputStreamReader inputreader = new InputStreamReader(instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    //分行读取
                    while (( line = buffreader.readLine()) != null) {
                        content += line ;
                    }                
                    instream.close();
                }
            }
            catch (java.io.FileNotFoundException e) 
            {
            } 
            catch (IOException e) 
            {
            }
        }
        return content;
    }
	private void 开启悬浮窗()
	{
		mwindow = (WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		lparam = new WindowManager.LayoutParams();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
		{
            lparam.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
		else
		{
            lparam.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
		if (Settings.canDrawOverlays(this))
		{
			mbutton = new ImageButton(getApplicationContext());
			mbutton.setBackgroundResource(R.drawable.ic_launcher);///悬浮球的图片
			mbutton.setOnTouchListener(new OnTouchListener() 
				{
					@Override
					public boolean onTouch(View v, MotionEvent event) 
					{
						switch (event.getAction()) 
						{
							case MotionEvent.ACTION_DOWN:
								isMove = false;
								mTouchStartX = (int) event.getRawX();
								mTouchStartY = (int) event.getRawY();
								break;
							case MotionEvent.ACTION_MOVE:
								int nowX = (int) event.getRawX();
								int nowY = (int) event.getRawY();
								int movedX = nowX - mTouchStartX;
								int movedY = nowY - mTouchStartY;
								if (movedX > 5 || movedY > 5)
								{
									isMove = true;
								}
								mTouchStartX = nowX;
								mTouchStartY = nowY;
								lparam.x += movedX;
								lparam.y += movedY;
								mwindow.updateViewLayout(mbutton, lparam);
								break;
							case MotionEvent.ACTION_UP:
								break;
							case MotionEvent.ACTION_CANCEL:
								break;
						}
					    return isMove;
					}
				});
			mbutton.setOnClickListener(new OnClickListener()//悬浮球的单击事件
				{
					@Override
					public void onClick(View v)
					{
						加载窗口();//加载悬浮窗
						悬浮窗 = true;
						mwindow.removeView(mbutton);//关闭悬浮球
						悬浮球 = false;
					}
				});
			lparam.format = PixelFormat.RGBA_8888;
			lparam.gravity = Gravity.LEFT;
			lparam.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
			lparam.width = 110;
			//宽度
			lparam.height = 110;
			//高度
			if (mparam == null)
			{
				lparam.x = 300;//悬浮球起始x
				lparam.y = 0;//悬浮球起始y
			}
			else
			{
				lparam.x = mparam.x;
				lparam.y = mparam.y;//y
			}
			mwindow.addView(mbutton, lparam);//加载悬浮球
			悬浮球 = true;
		}
		else
		{
			Toast.makeText(this, "开启失败", Toast.LENGTH_LONG).show();
		}
		是否已经打开悬浮窗=true;
	}

	private void 加载窗口()
	{
		mwMenu = (WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
		mparam = new WindowManager.LayoutParams();
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
		{
            mparam.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        }
		else
		{
            mparam.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
		mparam.format = PixelFormat.RGBA_8888;
		mparam.gravity = Gravity.CENTER;
		mparam.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		mparam.width = WindowManager.LayoutParams.WRAP_CONTENT;
		//宽度
		mparam.height = WindowManager.LayoutParams.WRAP_CONTENT;
		//高度
		mparam.x = 0;//x
		mparam.y = 0;//y
		mwMenu.addView(dis, mparam);

		//悬浮窗长按监听
		LinearLayout ll1=displayMenu.findViewById(R.id.menu);
		ll1.setOnTouchListener(new OnTouchListener() 
			{
			    @Override
				public boolean onTouch(View v, MotionEvent event) 
				{
				    switch (event.getAction()) 
					{
					    case MotionEvent.ACTION_DOWN://单击
						    移动 = false;
						    按下X = (int) event.getRawX();
						    按下Y = (int) event.getRawY();
						    break;
						case MotionEvent.ACTION_MOVE://拖动
						    int nowX = (int) event.getRawX();
							int nowY = (int) event.getRawY();
							int movedX = nowX - 按下X;
							int movedY = nowY - 按下Y;
							if (movedX > 5 || movedY > 5)
							{
							    移动 = true;
				            }
					        按下X = nowX;
					        按下Y = nowY;
					        lparam.x += movedX;
					        lparam.y += movedY;
					        mwindow.updateViewLayout(displayMenu, mparam);
					        break;
					    case MotionEvent.ACTION_UP://抬起
						    break;
						case MotionEvent.ACTION_CANCEL:
						    break;
					}
					return 移动;
				}
			});
		

	    /*******************悬浮窗布局变量**********/
		ImageView 悬浮窗关闭=displayMenu.findViewById(R.id.close);
		/*******************物资开关变量**********/
		Button 偏移左=displayMenu.findViewById(R.id.偏移左);
		Button 偏移右=displayMenu.findViewById(R.id.偏移右);
		/*******************悬浮窗按钮**********/
		偏移左.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{

                    BigInteger c=new BigInteger(地图);
					地图 =Integer.toString(c.intValue() - 2);
                    地图输出 = 地图;
				}
			});

		偏移右.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{

                    BigInteger c=new BigInteger(地图);
					地图 =Integer.toString(c.intValue() + 2);
                    地图输出 = 地图;

				}
			});
		悬浮窗关闭.setOnClickListener(new OnClickListener(){
				@Override
				public void onClick(View v)
				{
					开启悬浮窗();//显示主悬浮窗
					mwMenu.removeView(dis);//移除菜单
					悬浮窗 = false;//菜单状态
				}
			});
		
		/*******************绘制开关按钮**********/
		绘制按钮=(Switch)displayMenu.findViewById(R.id.绘制开关);
		人物射线2=(CheckBox)displayMenu.findViewById(R.id.人物射线);
		人物骨骼2=(CheckBox)displayMenu.findViewById(R.id.人物骨骼);
		人物信息2=(CheckBox)displayMenu.findViewById(R.id.人物信息);
		附近人数2=(CheckBox)displayMenu.findViewById(R.id.附近人数);
		背敌提示2=(CheckBox)displayMenu.findViewById(R.id.背敌提示);
		人物名字2=(CheckBox)displayMenu.findViewById(R.id.人物名字);
		人物距离2=(CheckBox)displayMenu.findViewById(R.id.人物距离);
		
		绘制按钮.setOnCheckedChangeListener(new gn());
		人物射线2.setOnCheckedChangeListener(new gn());
		人物骨骼2.setOnCheckedChangeListener(new gn());
		人物信息2.setOnCheckedChangeListener(new gn());
		附近人数2.setOnCheckedChangeListener(new gn());
		背敌提示2.setOnCheckedChangeListener(new gn());
		人物名字2.setOnCheckedChangeListener(new gn());
		人物距离2.setOnCheckedChangeListener(new gn());
		
		/*******************其他设置**********/
		绘制按钮.setChecked(注入绘制按钮状态);
		人物射线2.setChecked(人物射线1);
		人物骨骼2.setChecked(人物骨骼1);
		人物信息2.setChecked(人物信息1);
		附近人数2.setChecked(附近人数1);
		背敌提示2.setChecked(背敌提示1);
		人物名字2.setChecked(人物名字1);
		人物距离2.setChecked(人物距离1);
	}

	class gn implements OnCheckedChangeListener
	{

		@Override
		public void onCheckedChanged(CompoundButton p1, boolean isChecked)
		{
			switch(p1.getId())
			{
				case R.id.绘制开关:
					{
						if (isChecked)
						{
							if(绘制开关3.equals("false")){
								注入绘制按钮状态=true;
								执行("rm -r /sdcard/stop");
								执行("mkdir /sdcard/dognb");
								写入("/sdcard/dognb/dognb","");
								if (模式.equals("虚拟机"))
								{
									执行("rm -r /sdcard/stop");//删除残留
									执行("chmod 777 " + getCacheDir() + "/assets/xxx");
									执行(getCacheDir() + "/assets/xxx");
									MyToast.showLong(MainActivity.this, "虚拟机注入成功");
								}
								else if (模式.equals("ROOT"))
								{
									执行("rm -r /sdcard/stop");//删除残留
									执行("chmod 777 " + getCacheDir() + "/assets/xxx");
									执行("su -c " + getCacheDir() + "/assets/xxx");
									MyToast.showLong(MainActivity.this, "ROOT注入成功");
								}
								else if (模式.equals("框架"))
								{
									执行("rm -r /sdcard/stop");//删除残留
									执行("chmod 777 " + getCacheDir() + "/assets/xxx");
									执行(getCacheDir() + "/assets/xxx");
									MyToast.showLong(MainActivity.this, "框架注入成功");
								}
								FloatService.ShowFloat(MainActivity.this);
								绘制开关3="true";
								Toast.makeText(MainActivity.this,"注入绘制",Toast.LENGTH_LONG).show();
							}
						}
						else
						{
							绘制开关3="false";
							写入("/sdcard/stop","");
							执行("rm -r /sdcard/dognb/dognb");
							FloatService.HideFloat();
							Toast.makeText(MainActivity.this,"关闭绘制",Toast.LENGTH_LONG).show();
							注入绘制按钮状态=false;
						}
					}
					break;
			
					case R.id.人物射线:
					{
						if(isChecked)
						{
							人物射线1=true;
							人物射线3="true";
						}
						else
						{
							人物射线1=false;
							人物射线3="false";
						}
					}
					break;
				case R.id.人物距离:
					{
						if(isChecked)
						{
							人物距离1=true;
							人物距离3="true";
						}
						else
						{
							人物距离1=false;
							人物距离3="false";
						}
					}
					break;
				case R.id.人物名字:
					{
						if(isChecked)
						{
							人物名字1=true;
							人物名字3="true";
						}
						else
						{
							人物名字1=false;
							人物名字3="false";
						}
					}
					break;
				case R.id.人物骨骼:
					{
						if(isChecked)
						{
							人物骨骼1=true;
							人物骨骼3="true";
						}
						else
						{
							人物骨骼1=false;
							人物骨骼3="false";
						}
					}
					break;
				case R.id.人物信息:
					{
						if(isChecked)
						{
							人物信息1=true;
							人物信息3="true";
						}
						else
						{
							人物信息1=false;
							人物信息3="false";
						}
					}
					break;
				case R.id.附近人数:
					{
						if(isChecked)
						{
							附近人数1=true;
							附近人数3="true";
						}
						else
						{
							附近人数1=false;
							附近人数3="false";
						}
					}
					break;
				case R.id.背敌提示:
					{
						if(isChecked)
						{
							背敌提示1=true;
							背敌提示3="true";
						}
						else
						{
							背敌提示1=false;
							背敌提示3="false";
						}
					}
					break;
}}}}
