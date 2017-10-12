package com.example.diycircleprogress;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by yangLiHai on 2016/8/16.
 */
public class LevelProgressView extends View {

    private RectF rectF;
    private double sweepRadius = 0;//扫过的角度（默认是0）
    boolean isRunning;//运行的控制器
    float radius = 1f;//每一帧画完之后的角度
    float starttPoint = 270f;//初始化的角度（默认在中间顶部
    private Paint mPaint;
    private int paintWidth = 2;//圆的宽度
    float finalRadius=0;
    double pi =3.14159265358979323846264338327950288419716939937510582097494459230781640628620899;//定义圆周率

    public LevelProgressView(Context context) {
        super(context);
        init();
    }

    public LevelProgressView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public LevelProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){

        mPaint = new Paint();
        mPaint.setColor(0xff1ecfff);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(paintWidth);
        mPaint.setAntiAlias(true);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.WHITE);

        if (sweepRadius==0) return;

        if (finalRadius!=0){
            //
            canvas.drawArc(rectF, starttPoint, finalRadius, false, mPaint);
            return;
        }
        canvas.drawArc(rectF, starttPoint, radius, false, mPaint);

        radius=radius+10f;

        if (radius >= sweepRadius+1f){
            //画圆结束
            finalRadius = radius-10f;
            isRunning = false;
            sweepRadius = finalRadius;//重新确切的告诉view扫过的角度
            getStarPos();
            if (listener!=null){
                listener.onDrawFinish(starX , starY);
            }
        }

        if (!isRunning) return;
        //刷新view，每50毫秒执行一次ondraw()
        postInvalidateDelayed(50);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        rectF = new RectF(paintWidth,paintWidth,getWidth()-paintWidth,getHeight()-paintWidth);
    }

    public void start(){
        finalRadius=0;
        radius=1f;
        isRunning = true;
        invalidate();

    }

    public void setSweepRadius(double sweepRadius){
        this.sweepRadius = sweepRadius;
    }




    private double starX;//等级星星的x坐标
    private double starY;//等级星星的y坐标

    /**
     * 得到画圈后的坐标点
     */
    public void getStarPos(){
        double r = (getWidth()-paintWidth*2)/2;//半径

        if (sweepRadius==0){
            starX = r;
            starY = 0;
        }else if (sweepRadius < 90d){
            starX = r + Math.sin(sweepRadius*pi/180)*r;
            starY = r - Math.cos(sweepRadius*pi/180)*r;
        }else if (sweepRadius == 90d){
            starX = r*2;
            starY = r;
        }else if (sweepRadius < 180d){
            starX = r+ Math.cos((sweepRadius-90d)*pi/180)*r;
            starY = r+ Math.sin((sweepRadius-90d)*pi/180)*r;
        }else if (sweepRadius == 180d){
            starX = r;
            starY = r*2;
        }else if (sweepRadius < 270d){
            starX = r - Math.sin((sweepRadius-180d)*pi/180)*r;
            starY = r + Math.cos((sweepRadius-180d)*pi/180)*r;
        }else if (sweepRadius == 270d){
            starX = 0;
            starY = r;
        }else if (sweepRadius < 360d){
            starX = r - Math.cos((sweepRadius-270d)*pi/180)*r;
            starY = r - Math.sin((sweepRadius-270d)*pi/180)*r;
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private OnDrawingFinishListener listener;
    public void setOnDrawingFinishListener(OnDrawingFinishListener onDrawingFinishListener){
        this.listener = onDrawingFinishListener;
    }
    public interface OnDrawingFinishListener{
        void onDrawFinish(double xPos, double yPos);
    }
}
