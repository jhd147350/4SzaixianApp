package com.jhd.fourszaixian.ui.append;

/** 
 * Canvas练习  
 *    自已画饼图，实现出来后觉得也算实用. 
 *    
 * author:xiongchuanliang 
 * date:2014-4-6 
 * -jhd-以上来自参考http://blog.csdn.net/xcl168/article/details/23115147
 */

import java.util.concurrent.CountDownLatch;

import android.R.integer;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;

@SuppressLint("NewApi")
public class PieView extends View {
	long count=0;

	public PieView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		// 解决4.1版本 以下canvas.drawTextOnPath()不显示问题
		this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

		// 屏幕信息
		DisplayMetrics dm = getResources().getDisplayMetrics();
		ScrHeight = dm.heightPixels;
		ScrWidth = dm.widthPixels;

		// 设置边缘特殊效果
		BlurMaskFilter PaintBGBlur = new BlurMaskFilter(1,
				BlurMaskFilter.Blur.INNER);

		arrPaintArc = new Paint[5];
		// Resources res = this.getResources();
		for (int i = 0; i < 5; i++) {
			arrPaintArc[i] = new Paint();
			// arrPaintArc[i].setColor(res.getColor(colors[i] ));
			arrPaintArc[i].setARGB(255, arrColorRgb[i][0], arrColorRgb[i][1],
					arrColorRgb[i][2]);
			arrPaintArc[i].setStyle(Paint.Style.FILL);
			arrPaintArc[i].setStrokeWidth(4);
			arrPaintArc[i].setMaskFilter(PaintBGBlur);
		}
		//Log.e("jhdd", "");

		PaintText = new Paint();
		PaintText.setColor(Color.BLUE);
		PaintText.setTextSize(22);
		// PaintText.setTypeface(Typeface.DEFAULT_BOLD);
	}

	private int ScrHeight;
	private int ScrWidth;

	private Paint[] arrPaintArc;
	private Paint PaintText = null;
	/*
	 * final int[] colors = new int[]{ R.color.red, R.color.white,
	 * R.color.green, R.color.yellow, R.color.blue, };
	 */

	// RGB颜色数组  //jhd依次是加油、停车、过路、维保、其他
	private final int arrColorRgb[][] = { { 226, 76, 96 }, { 121, 193, 111 },
			{ 222, 223, 96 }, { 54, 186, 207 }, { 232, 158, 80 } };

	// 演示用的比例,实际使用中，即为外部传入的比例参数
	//100是个满园
	private float arrPer[] = new float[] { 20f, 20f, 20f, 20f, 20f };

	//jhd提供外部方法改变绘制百分比
	public void setPer(float arrper[]) {
		this.arrPer=arrper;
		System.out.println("setPer:"+arrper.toString());
		for(float temp:arrper){
			System.out.println("--"+temp);
		}
		invalidate();
		
	}
	public PieView(Context context) {
		super(context);

		// 解决4.1版本 以下canvas.drawTextOnPath()不显示问题
		this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

		
		//jhd 不能拿屏幕信息当作这个园的计算方式，应该拿当前控件的高宽计算
		// 屏幕信息
		DisplayMetrics dm = getResources().getDisplayMetrics();
		ScrHeight = dm.heightPixels;
		ScrWidth = dm.widthPixels;

		// 设置边缘特殊效果
		BlurMaskFilter PaintBGBlur = new BlurMaskFilter(1,
				BlurMaskFilter.Blur.INNER);

		arrPaintArc = new Paint[5];
		// Resources res = this.getResources();
		for (int i = 0; i < 5; i++) {
			arrPaintArc[i] = new Paint();
			// arrPaintArc[i].setColor(res.getColor(colors[i] ));
			arrPaintArc[i].setARGB(255, arrColorRgb[i][0], arrColorRgb[i][1],
					arrColorRgb[i][2]);
			arrPaintArc[i].setStyle(Paint.Style.FILL);
			arrPaintArc[i].setStrokeWidth(4);
			arrPaintArc[i].setMaskFilter(PaintBGBlur);
		}
		//Log.e("jhdd", "");

		PaintText = new Paint();
		PaintText.setColor(Color.BLUE);
		PaintText.setTextSize(22);
		// PaintText.setTypeface(Typeface.DEFAULT_BOLD);
	}

	public void onDraw(Canvas canvas) {
		//jhd 以控件画布的大小计算圆的高宽
		ScrHeight=canvas.getHeight();
		ScrWidth=canvas.getWidth();
		
		System.out.println("pie onDraw:"+count);
		count++;
		// 画布背景
		canvas.drawColor(Color.WHITE);

		float cirX = ScrWidth / 2;
		float cirY = ScrHeight / 2;
		float radius = ScrHeight / 2;// 150;
		// 先画个圆确定下显示位置
		// canvas.drawCircle(cirX,cirY,radius,PaintArcRed);

		float arcLeft = cirX - radius;
		float arcTop = cirY - radius;
		float arcRight = cirX + radius;
		float arcBottom = cirY + radius;
		RectF arcRF0 = new RectF(arcLeft, arcTop, arcRight, arcBottom);

		Path pathArc = new Path();
		// x,y,半径 ,CW
		pathArc.addCircle(cirX, cirY, radius, Direction.CW);
		// 绘出饼图大轮廓
		//canvas.drawPath(pathArc, arrPaintArc[0]);

		float CurrPer = 0f; // 偏移角度
		float Percentage = 0f; // 当前所占比例

		//int scrOffsetW = ScrWidth - 200;
	//	int scrOffsetH = ScrHeight - 300;
		//int scrOffsetT = 40;

		// Resources res = this.getResources();
		int i = 0;
		for (i = 0; i < 5; i++) // 注意循环次数噢
		{
			// 将百分比转换为饼图显示角度
			Percentage = 360 * (arrPer[i] / 100);
			Percentage = (float) (Math.round(Percentage * 100)) / 100;

			// 在饼图中显示所占比例
			canvas.drawArc(arcRF0, CurrPer, Percentage, true,
					arrPaintArc[i]);

			// 当前颜色
			//canvas.drawRect(scrOffsetW, scrOffsetH + i * scrOffsetT,
			//		scrOffsetW + 60, scrOffsetH - 30 + i * scrOffsetT,
			//		arrPaintArc[i + 2]);
			// 当前比例
			//canvas.drawText(String.valueOf(arrPer[i]) + "%", scrOffsetW + 70,
			//		scrOffsetH + i * scrOffsetT, PaintText);
			// 下次的起始角度
			CurrPer += Percentage;
		}

		// 最末尾比例说明
		//canvas.drawRect(scrOffsetW, scrOffsetH + i * scrOffsetT,
		//		scrOffsetW + 60, scrOffsetH - 30 + i * scrOffsetT,
		//		arrPaintArc[0]);

		//canvas.drawText(String.valueOf(arrPer[i]) + "%", scrOffsetW + 70,
		//		scrOffsetH + i * scrOffsetT, PaintText);

		//jhd-不要这些信息
		// Demo的作者信息
		//canvas.drawText("author:xcl", 70, scrOffsetH + i + 1 * scrOffsetT,
		//		PaintText);
		//canvas.drawText("date:2014-4-7", 70, scrOffsetH + i * scrOffsetT,
		//		PaintText);

	}
}