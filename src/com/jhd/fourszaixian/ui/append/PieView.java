package com.jhd.fourszaixian.ui.append;

/** 
 * Canvas��ϰ  
 *    ���ѻ���ͼ��ʵ�ֳ��������Ҳ��ʵ��. 
 *    
 * author:xiongchuanliang 
 * date:2014-4-6 
 * -jhd-�������Բο�http://blog.csdn.net/xcl168/article/details/23115147
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
		// ���4.1�汾 ����canvas.drawTextOnPath()����ʾ����
		this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

		// ��Ļ��Ϣ
		DisplayMetrics dm = getResources().getDisplayMetrics();
		ScrHeight = dm.heightPixels;
		ScrWidth = dm.widthPixels;

		// ���ñ�Ե����Ч��
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

	// RGB��ɫ����  //jhd�����Ǽ��͡�ͣ������·��ά��������
	private final int arrColorRgb[][] = { { 226, 76, 96 }, { 121, 193, 111 },
			{ 222, 223, 96 }, { 54, 186, 207 }, { 232, 158, 80 } };

	// ��ʾ�õı���,ʵ��ʹ���У���Ϊ�ⲿ����ı�������
	//100�Ǹ���԰
	private float arrPer[] = new float[] { 20f, 20f, 20f, 20f, 20f };

	//jhd�ṩ�ⲿ�����ı���ưٷֱ�
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

		// ���4.1�汾 ����canvas.drawTextOnPath()����ʾ����
		this.setLayerType(View.LAYER_TYPE_SOFTWARE, null);

		
		//jhd ��������Ļ��Ϣ�������԰�ļ��㷽ʽ��Ӧ���õ�ǰ�ؼ��ĸ߿����
		// ��Ļ��Ϣ
		DisplayMetrics dm = getResources().getDisplayMetrics();
		ScrHeight = dm.heightPixels;
		ScrWidth = dm.widthPixels;

		// ���ñ�Ե����Ч��
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
		//jhd �Կؼ������Ĵ�С����Բ�ĸ߿�
		ScrHeight=canvas.getHeight();
		ScrWidth=canvas.getWidth();
		
		System.out.println("pie onDraw:"+count);
		count++;
		// ��������
		canvas.drawColor(Color.WHITE);

		float cirX = ScrWidth / 2;
		float cirY = ScrHeight / 2;
		float radius = ScrHeight / 2;// 150;
		// �Ȼ���Բȷ������ʾλ��
		// canvas.drawCircle(cirX,cirY,radius,PaintArcRed);

		float arcLeft = cirX - radius;
		float arcTop = cirY - radius;
		float arcRight = cirX + radius;
		float arcBottom = cirY + radius;
		RectF arcRF0 = new RectF(arcLeft, arcTop, arcRight, arcBottom);

		Path pathArc = new Path();
		// x,y,�뾶 ,CW
		pathArc.addCircle(cirX, cirY, radius, Direction.CW);
		// �����ͼ������
		//canvas.drawPath(pathArc, arrPaintArc[0]);

		float CurrPer = 0f; // ƫ�ƽǶ�
		float Percentage = 0f; // ��ǰ��ռ����

		//int scrOffsetW = ScrWidth - 200;
	//	int scrOffsetH = ScrHeight - 300;
		//int scrOffsetT = 40;

		// Resources res = this.getResources();
		int i = 0;
		for (i = 0; i < 5; i++) // ע��ѭ��������
		{
			// ���ٷֱ�ת��Ϊ��ͼ��ʾ�Ƕ�
			Percentage = 360 * (arrPer[i] / 100);
			Percentage = (float) (Math.round(Percentage * 100)) / 100;

			// �ڱ�ͼ����ʾ��ռ����
			canvas.drawArc(arcRF0, CurrPer, Percentage, true,
					arrPaintArc[i]);

			// ��ǰ��ɫ
			//canvas.drawRect(scrOffsetW, scrOffsetH + i * scrOffsetT,
			//		scrOffsetW + 60, scrOffsetH - 30 + i * scrOffsetT,
			//		arrPaintArc[i + 2]);
			// ��ǰ����
			//canvas.drawText(String.valueOf(arrPer[i]) + "%", scrOffsetW + 70,
			//		scrOffsetH + i * scrOffsetT, PaintText);
			// �´ε���ʼ�Ƕ�
			CurrPer += Percentage;
		}

		// ��ĩβ����˵��
		//canvas.drawRect(scrOffsetW, scrOffsetH + i * scrOffsetT,
		//		scrOffsetW + 60, scrOffsetH - 30 + i * scrOffsetT,
		//		arrPaintArc[0]);

		//canvas.drawText(String.valueOf(arrPer[i]) + "%", scrOffsetW + 70,
		//		scrOffsetH + i * scrOffsetT, PaintText);

		//jhd-��Ҫ��Щ��Ϣ
		// Demo��������Ϣ
		//canvas.drawText("author:xcl", 70, scrOffsetH + i + 1 * scrOffsetT,
		//		PaintText);
		//canvas.drawText("date:2014-4-7", 70, scrOffsetH + i * scrOffsetT,
		//		PaintText);

	}
}