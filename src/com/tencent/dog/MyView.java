package com.tencent.dog;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import static com.tencent.dog.MainActivity.绘制开关3;
import static com.tencent.dog.MainActivity.人物射线3;
import static com.tencent.dog.MainActivity.人物骨骼3;
import static com.tencent.dog.MainActivity.人物信息3;
import static com.tencent.dog.MainActivity.附近人数3;
import static com.tencent.dog.MainActivity.背敌提示3;
import static com.tencent.dog.MainActivity.人物名字3;
import static com.tencent.dog.MainActivity.人物距离3;
import static com.tencent.dog.MainActivity.地图输出;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import android.os.SystemClock;
import static android.graphics.Typeface.BOLD;
import android.graphics.*;

public class MyView extends View {
	
	Paint 背敌画笔1 = new Paint();
    Paint 背敌文字 = new Paint();
	Paint 人数颜色 = new Paint();
	Paint 射线显示 = new Paint();
	Paint 血条边框 = new Paint();
	Paint 血条颜色 = new Paint();
	Paint 距离颜色 = new Paint();
	Paint 名字颜色 = new Paint();
	Paint 人机名字 = new Paint();
	Paint 距离颜色描边 = new Paint();
	Paint 人机名字描边 = new Paint();
	Paint 名字颜色描边 = new Paint();
	Paint 骨骼画笔 = new Paint();
	Paint 圆圈颜色 = new Paint();
	
	float x = 0;
    float y = 0;
    float w = 0;
    float h = 0;
    float M = 0;
    float hp = 0;
    float db = 0;
    float pd=0;
    float dx=0;
    float ggtzy = 0;
    float ggtgd = 0;
    float ggsbzy = 0;
    float ggsbsx = 0;
    float ggpgzy = 0;
    float ggpgsx = 0;
    float ggzjzy = 0;
    float ggzjsx = 0;
    float ggyjzy = 0;
    float ggyjsx = 0;
    float ggzszzy = 0;
    float ggzszsx = 0;
    float ggysjzy = 0;
    float ggysjsx = 0;
    float ggzswzy = 0;
    float ggzswsx = 0;
    float ggyswzy = 0;
    float ggyswsx = 0;
    float ggzdtzy = 0;
    float ggzdtsx = 0;
    float ggydtzy = 0;
    float ggydtsx = 0;
    float ggzxgzy = 0;
    float ggzxgsx = 0;
    float ggyxgzy = 0;
    float ggyxgsx = 0;
    float ggzjwzy = 0;
    float ggzjwsx = 0;
    float ggyjwzy = 0;
    float rss = 0;
	float ggyjwsx = 0;
	String name;
	float length;
	private void init()
	{
		Typeface font1 = Typeface.create(Typeface.DEFAULT_BOLD, BOLD);//加载字体
		人数颜色.setTextSize(45);//字体大0小
		人数颜色.setDither(true);
		人数颜色.setFakeBoldText(true);
		人数颜色.setStrokeWidth(3);
		人数颜色.setColor(Color.parseColor("#FFFFFF"));
		人数颜色.setTypeface(font1);//设置字体
		人数颜色.setShadowLayer(12,0,0,0xFFFF0000);
		人数颜色.setAntiAlias(true);

		骨骼画笔.setColor(Color.parseColor("#00DF00"));
		骨骼画笔.setStyle(Paint.Style.STROKE);
		骨骼画笔.setStrokeWidth(2f);
		骨骼画笔.setAntiAlias(true);
		骨骼画笔.setDither(true);  

		射线显示.setColor(Color.parseColor("#FFFFFF"));
		射线显示.setStyle(Paint.Style.STROKE);
		射线显示.setStrokeWidth(2);//线条宽度
		射线显示.setDither(true);
		射线显示.setAntiAlias(true);

		血条边框.setColor(Color.parseColor("#000000"));
		血条边框.setStyle(Paint.Style.STROKE);
		血条边框.setStrokeWidth(2);
		血条边框.setDither(true);  
		血条边框.setAntiAlias(true);

		血条颜色.setColor(Color.parseColor("#00FF00"));
		血条颜色.setStrokeWidth(10);
		血条颜色.setAlpha(140);
		血条颜色.setDither(true);  
		血条颜色.setAntiAlias(true);

		距离颜色.setColor(Color.parseColor("#F4C200"));
		距离颜色.setStrokeWidth(10);
		距离颜色.setTextSize(25);
		距离颜色.setFakeBoldText(true);
		距离颜色.setTypeface(font1);
		距离颜色.setDither(true);  
		距离颜色.setAntiAlias(true);
		距离颜色.setFakeBoldText(true);

		人机名字.setColor(Color.parseColor("#FFFFFF"));
		人机名字.setStrokeWidth(100);
		人机名字.setTextSize(23);
		人机名字.setFakeBoldText(true);
		人机名字.setTypeface(font1);
		人机名字.setDither(true);  
		人机名字.setAntiAlias(true);

		名字颜色.setColor(Color.parseColor("#FFFFFF"));
		名字颜色.setStrokeWidth(100);
		名字颜色.setTextSize(22);
		名字颜色.setFakeBoldText(true);
		名字颜色.setTypeface(font1);
		名字颜色.setDither(true);  
		名字颜色.setAntiAlias(true);
		名字颜色.setFakeBoldText(true);

		背敌画笔1.setAntiAlias(true);
		背敌画笔1.setColor(Color.parseColor("#ff0000"));
		背敌画笔1.setAlpha(100);
		背敌画笔1.setDither(true);  
		背敌画笔1.setAntiAlias(true);

		背敌文字.setTypeface(font1);
		背敌文字.setAntiAlias(true);
		背敌文字.setColor(Color.parseColor("#FFFFFF"));
		背敌文字.setTextSize(30);
		背敌文字.setFakeBoldText(true);
		背敌文字.setTextAlign(Paint.Align.CENTER);
		背敌文字.setDither(true);  
		背敌文字.setAntiAlias(true);
		背敌文字.setFakeBoldText(true);

		距离颜色描边.setColor(Color.parseColor("#000000"));
		距离颜色描边.setStrokeWidth(3);
		距离颜色描边.setTextSize(25);
		距离颜色描边.setAlpha(200);
		距离颜色描边.setStyle(Paint.Style.STROKE);
		距离颜色描边.setFakeBoldText(true);
		距离颜色描边.setDither(true);
		距离颜色描边.setTypeface(font1);
		距离颜色描边.setAntiAlias(true);

		人机名字描边.setColor(Color.parseColor("#000000"));
		人机名字描边.setStrokeWidth(3);
		人机名字描边.setTextSize(23);
		人机名字描边.setAlpha(200);
		人机名字描边.setStyle(Paint.Style.STROKE);
		人机名字描边.setFakeBoldText(true);
		人机名字描边.setDither(true);
		人机名字描边.setTypeface(font1);
		人机名字描边.setAntiAlias(true);

		名字颜色描边.setColor(Color.parseColor("#000000"));
		名字颜色描边.setStrokeWidth(3);
		名字颜色描边.setTextSize(22);
		名字颜色描边.setAlpha(200);
		名字颜色描边.setStyle(Paint.Style.STROKE);
		名字颜色描边.setTypeface(font1);
		名字颜色描边.setFakeBoldText(true);
		名字颜色描边.setDither(true);
		名字颜色描边.setAntiAlias(true);
		
		圆圈颜色.setColor(Color.parseColor("#FF0000"));
		圆圈颜色.setStyle(Paint.Style.STROKE);
		圆圈颜色.setStrokeWidth(2);
		圆圈颜色.setDither(true);
		圆圈颜色.setAntiAlias(true);

	}
	
	public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
	
	public static String getFileContent(File file)
	{
        String content = "";
        if (!file.isDirectory())
		{
            try
			{
                InputStream instream = new FileInputStream(file);
                if (instream != null)
				{
                    InputStreamReader inputreader
						= new InputStreamReader(instream, "UTF-8");
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line = "";
                    while ((line = buffreader.readLine()) != null)
					{
                        content += line;
                    }
                    instream.close();//关闭输入流
                }
            }
			catch (FileNotFoundException e)
			{
            }
			catch (IOException e)
			{
            }
        }
        return content;
    }
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		init();
        int 屏幕宽= getResources().getDisplayMetrics().widthPixels;//实际宽
		int rs=0;
		File file = new File("/sdcard/b.log");//坐标输出必须和cpp的路径一致
		String fileContent = getFileContent(file);
		String[] split = fileContent.split(";");
		for (int i = 0; i < split.length; i++)
		{
			float x1=Float.parseFloat(地图输出);
			String[] zb = split[i].split(",");
			try
			{
				rss = Float.parseFloat(zb[0]);//x
				x = Float.parseFloat(zb[1]);//x
				y = Float.parseFloat(zb[2]);//y
				w = Float.parseFloat(zb[3]);//y
				h = Float.parseFloat(zb[4]);//高
				hp = Integer.parseInt(zb[5]);//血量
				M = Integer.parseInt(zb[6]);//距离
				pd = Float.parseFloat(zb[7]);//人机
				dx = Float.parseFloat(zb[8]);//人机
				db = Integer.parseInt(zb[9]);//队编
				name = (zb[10]);//名字 
				ggtzy = Float.parseFloat(zb[11]);//骨骼头左右
				ggtgd = Float.parseFloat(zb[12]);//骨骼头高度 
				ggsbzy = Float.parseFloat(zb[13]);//骨骼胸部左右
				ggsbsx = Float.parseFloat(zb[14]);//骨骼胸部上下
				ggpgzy = Float.parseFloat(zb[15]);//骨骼盆骨左右
				ggpgsx = Float.parseFloat(zb[16]);//骨骼盆骨上下
				ggzjzy = Float.parseFloat(zb[17]);//骨骼左肩左右
				ggzjsx = Float.parseFloat(zb[18]);//骨骼左肩上下
				ggyjzy = Float.parseFloat(zb[19]);//骨骼右肩左右
				ggyjsx = Float.parseFloat(zb[20]);//骨骼右肩上下
				ggzszzy = Float.parseFloat(zb[21]);//骨骼左手肘左右
				ggzszsx = Float.parseFloat(zb[22]);//骨骼左手肘上下
				ggysjzy = Float.parseFloat(zb[23]);//骨骼右手肘左右
				ggysjsx = Float.parseFloat(zb[24]);//骨骼右手肘上下
				ggzswzy = Float.parseFloat(zb[25]);//骨骼左手腕左右
				ggzswsx = Float.parseFloat(zb[26]);//骨骼左手腕上下
				ggyswzy = Float.parseFloat(zb[27]);//骨骼右手腕左右
				ggyswsx = Float.parseFloat(zb[28]);//骨骼右手腕上下
				ggzdtzy = Float.parseFloat(zb[29]);//骨骼左大腿左右
				ggzdtsx = Float.parseFloat(zb[30]);//骨骼左大腿上下
				ggydtzy = Float.parseFloat(zb[31]);//骨骼右大腿左右
				ggydtsx = Float.parseFloat(zb[32]);//骨骼右大腿上下
				ggzxgzy = Float.parseFloat(zb[33]);//骨骼左膝盖左右
				ggzxgsx = Float.parseFloat(zb[34]);//骨骼左膝盖上下
				ggyxgzy = Float.parseFloat(zb[35]);//骨骼右膝盖左右
				ggyxgsx = Float.parseFloat(zb[36]);//骨骼右膝盖上下
				ggzjwzy = Float.parseFloat(zb[37]);//骨骼左脚腕左右
				ggzjwsx = Float.parseFloat(zb[38]);//骨骼左脚腕上下
				ggyjwzy = Float.parseFloat(zb[39]);//骨骼右脚腕左右
				ggyjwsx = Float.parseFloat(zb[40]);//骨骼右脚
			}
			catch (Exception v)
			{
				v.printStackTrace();
			}
			int zy = (int) db;
			int m = (int) M;
			
			if (hp>0 && m < 500) {
				rs++;				
			}
			
			if(m < 500 &&m > 0 && h>0){
				if(人物射线3.equals("true")){
					canvas.drawLine(屏幕宽/2+45,134,ggtzy+x1,ggtgd,射线显示);
				}
				if(人物骨骼3.equals("true")){
					canvas.drawLines(new float[]{ggtzy + x1, ggtgd, ggsbzy + x1, ggsbsx,ggsbzy + x1, ggsbsx, ggpgzy + x1, ggpgsx,ggsbzy + x1, ggsbsx, ggzjzy + x1, ggzjsx,ggzjzy + x1, ggzjsx, ggzszzy + x1, ggzszsx,ggzszzy + x1, ggzszsx, ggzswzy + x1, ggzswsx,ggsbzy + x1, ggsbsx, ggyjzy + x1, ggyjsx,ggyjzy + x1, ggyjsx, ggysjzy + x1, ggysjsx,ggysjzy + x1, ggysjsx, ggyswzy + x1, ggyswsx,ggpgzy + x1, ggpgsx, ggzdtzy + x1, ggzdtsx,ggzdtzy + x1, ggzdtsx, ggzxgzy + x1, ggzxgsx,ggzxgzy + x1, ggzxgsx, ggzjwzy + x1, ggzjwsx,ggpgzy + x1, ggpgsx, ggydtzy + x1, ggydtsx,ggydtzy + x1, ggydtsx, ggyxgzy + x1, ggyxgsx,ggyxgzy + x1, ggyxgsx, ggyjwzy + x1, ggyjwsx},骨骼画笔);
				}
				
				if(人物信息3.equals("true")){
					RectF rectF = new RectF(ggtzy-80+x1, ggtgd-19, ggtzy+80+x1, ggtgd-30);
					canvas.drawRoundRect(rectF,55,55,血条边框);
					canvas.drawLine(ggtzy-80+x1, ggtgd-25, ggtzy-80+x1+ hp / 100 * 160, ggtgd - 25, 血条颜色);
				}
				
				if(人物名字3.equals("true")){
					if(pd==0)
					{
						canvas.drawText("Robot" , ggtzy+x1 , ggtgd-38, 人机名字描边);
						canvas.drawText("Robot" , ggtzy+x1 ,ggtgd-38, 人机名字);
					}
					else
					{
						canvas.drawText(zy+" · "+name , ggtzy+x1 , ggtgd-38, 名字颜色描边);
						canvas.drawText(zy+" · "+name , ggtzy+x1 , ggtgd-38, 名字颜色);
					}
				}
				
				if(人物距离3.equals("true")){
					canvas.drawText(m+"M" , ggtzy+x1 , ggzjwsx+29, 距离颜色描边);
					canvas.drawText(m+"M" , ggtzy+x1 , ggzjwsx+29, 距离颜色);
				}
			}
			if (背敌提示3.equals("true")){

				if (x+w/2+w/2<0){
					canvas.drawCircle(0,y,70,背敌画笔1);
					canvas.drawText(m+"M",30,y+8,背敌文字);
				}
				else if (x + w / 2 - w / 2 >屏幕宽)
				{
					canvas.drawCircle(屏幕宽,y,70,背敌画笔1);
					canvas.drawText(m+"M",屏幕宽-30,y+8,背敌文字);
				}
				else if (y+w<0)
				{
					canvas.drawCircle(x+w/2,0,60,背敌画笔1);
					canvas.drawText(m+"M",x+w/2,35,背敌文字);
				}
			}
		}
		if(附近人数3.equals("true")){
			length = 人数颜色.measureText(rs+"");
			canvas.drawText(rs+"" , 屏幕宽 / 2+45 - length / 2, 133, 人数颜色);
		}
        this.invalidate();
	}
}

