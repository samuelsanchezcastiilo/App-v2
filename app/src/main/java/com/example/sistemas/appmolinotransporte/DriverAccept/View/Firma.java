package com.example.sistemas.appmolinotransporte.DriverAccept.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import 	android.graphics.Canvas;
import 	android.graphics.Paint;
import android.graphics.Color;
import android.graphics.Path;



/**
 * Created by developer on 18/11/17.
 */

 public class Firma extends View {
    private Bitmap canvasBitmap;
    private Path miPath;
    private static  Paint paint;
    private Canvas drawCanvas;
    private Paint canvasPaint;



    public Firma(Context context, AttributeSet attrs) {
        super(context, attrs);
        lienzoPintar();
    }

    private void lienzoPintar()
    {
        miPath = new Path();
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setColor(Color.BLACK);
        canvasPaint = new Paint(Paint.DITHER_FLAG);
    }

        public  void onDraw(Canvas canvas)
        {
            canvas.drawBitmap(canvasBitmap,0,0,canvasPaint);
            canvas.drawPath(miPath,paint);

        }

    protected void onSizeChanged(int w, int h, int oldw, int oldh){
    super.onSizeChanged(w,h,oldw,oldh);
        canvasBitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);


    }

        public boolean onTouchEvent(MotionEvent event)
        {
            float touchX = event.getX();
            float touchY = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    miPath.moveTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_MOVE:
                    miPath.lineTo(touchX, touchY);
                    break;
                case MotionEvent.ACTION_UP:
                    miPath.lineTo(touchX, touchY);
                    drawCanvas.drawPath(miPath, paint);
                    miPath.reset();
                    break;
                default:
                    return false;
            }
            invalidate();
            return true;
        }

    public void NuevoDibujo(){
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();

    }
}
