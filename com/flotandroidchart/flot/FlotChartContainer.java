package com.flotandroidchart.flot;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import com.flotandroidchart.global.EventHolder;
import com.flotandroidchart.global.FlotEvent;
import com.flotandroidchart.timer.FPSTimer;

public class FlotChartContainer
  extends SurfaceView
  implements SurfaceHolder.Callback
{
  private static final int INVALID_POINTER_ID = -1;
  private static final long serialVersionUID = 1L;
  private FlotDraw _fd;
  private boolean bMoved = false;
  private DrawThread drawThread;
  private int mActivePointerId = -1;
  private boolean mGameIsRunning;
  private float mLastTouchX;
  private float mLastTouchY;
  
  public FlotChartContainer(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init(null);
  }
  
  public FlotChartContainer(Context paramContext, FlotDraw paramFlotDraw)
  {
    super(paramContext);
    init(paramFlotDraw);
  }
  
  public void init(FlotDraw paramFlotDraw)
  {
    this._fd = paramFlotDraw;
    if (this.drawThread == null)
    {
      paramFlotDraw = getHolder();
      paramFlotDraw.addCallback(this);
      this.drawThread = new DrawThread(paramFlotDraw, getContext(), this._fd);
      setOnTouchListener(new View.OnTouchListener()
      {
        public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
        {
          switch (paramAnonymousMotionEvent.getAction() & 0xFF)
          {
          }
          for (;;)
          {
            return true;
            float f1 = paramAnonymousMotionEvent.getX();
            float f2 = paramAnonymousMotionEvent.getY();
            FlotChartContainer.this.mLastTouchX = f1;
            FlotChartContainer.this.mLastTouchY = f2;
            FlotChartContainer.this.mActivePointerId = paramAnonymousMotionEvent.getPointerId(0);
            FlotChartContainer.this.bMoved = false;
            continue;
            int i = paramAnonymousMotionEvent.findPointerIndex(FlotChartContainer.this.mActivePointerId);
            f1 = paramAnonymousMotionEvent.getX(i);
            f2 = paramAnonymousMotionEvent.getY(i);
            FlotChartContainer.this.mLastTouchX = f1;
            FlotChartContainer.this.mLastTouchY = f2;
            continue;
            FlotChartContainer.this.mActivePointerId = -1;
            if ((FlotChartContainer.this._fd != null) && (!FlotChartContainer.this.bMoved))
            {
              FlotChartContainer.this._fd.getEventHolder().dispatchEvent("hover", new FlotEvent(paramAnonymousMotionEvent));
              continue;
              FlotChartContainer.this.mActivePointerId = -1;
              continue;
              i = (paramAnonymousMotionEvent.getAction() & 0xFF00) >> 8;
              if (paramAnonymousMotionEvent.getPointerId(i) == FlotChartContainer.this.mActivePointerId)
              {
                Log.d("onTouch", "taged;");
                if (i != 0) {}
              }
            }
          }
        }
      });
    }
    for (;;)
    {
      setFocusable(true);
      return;
      this.drawThread.setDrawable(this._fd);
    }
  }
  
  public void setDrawData(FlotDraw paramFlotDraw)
  {
    init(paramFlotDraw);
  }
  
  public void surfaceChanged(SurfaceHolder paramSurfaceHolder, int paramInt1, int paramInt2, int paramInt3) {}
  
  public void surfaceCreated(SurfaceHolder paramSurfaceHolder)
  {
    if (!this.mGameIsRunning)
    {
      this.drawThread.setRunning(true);
      this.drawThread.start();
      this.mGameIsRunning = true;
      return;
    }
    this.drawThread.onResume();
  }
  
  public void surfaceDestroyed(SurfaceHolder paramSurfaceHolder)
  {
    int i = 1;
    this.drawThread.setRunning(false);
    Log.e("smardi.Epi", "SurfaceDestroyed");
    for (;;)
    {
      if (i == 0) {
        return;
      }
      try
      {
        this.drawThread.join();
        this.drawThread.interrupt();
        i = 0;
      }
      catch (InterruptedException paramSurfaceHolder)
      {
        Log.e("smardi.Epi", "SurfaceDestroyed: " + paramSurfaceHolder.getMessage());
      }
    }
  }
  
  class DrawThread
    extends Thread
  {
    private FlotDraw _fd;
    private Object mPauseLock = new Object();
    private boolean mPaused;
    private boolean mRun = false;
    private SurfaceHolder mSurfaceHolder;
    FPSTimer timer;
    
    public DrawThread(SurfaceHolder paramSurfaceHolder, Context paramContext, FlotDraw paramFlotDraw)
    {
      this.mSurfaceHolder = paramSurfaceHolder;
      this._fd = paramFlotDraw;
    }
    
    protected void doDraw(Canvas paramCanvas)
    {
      if (paramCanvas != null)
      {
        Rect localRect = new Rect();
        paramCanvas.getClipBounds(localRect);
        if (this._fd != null)
        {
          paramCanvas.save();
          paramCanvas.translate(localRect.left, localRect.top);
          this._fd.draw(paramCanvas, localRect.width(), localRect.height());
          paramCanvas.restore();
        }
      }
    }
    
    public void onPause()
    {
      synchronized (this.mPauseLock)
      {
        this.mPaused = true;
        Log.d("Draw Thread", "Draw Thread Paused");
        return;
      }
    }
    
    public void onResume()
    {
      synchronized (this.mPauseLock)
      {
        this.mPaused = false;
        this.mPauseLock.notifyAll();
        Log.e("smardi.Epi", "onResume()");
        return;
      }
    }
    
    public void run()
    {
      int j = 0;
      long l1 = System.currentTimeMillis();
      boolean bool = true;
      if ((this._fd != null) && (this._fd.getOptions() != null)) {
        this.timer = new FPSTimer(this._fd.getOptions().fps);
      }
      do
      {
        while (!this.mRun)
        {
          return;
          this.timer = new FPSTimer(60);
        }
      } while (!this.mRun);
      ??? = null;
      int i = j;
      if (bool)
      {
        i = j;
        if (this._fd == null) {}
      }
      label298:
      for (;;)
      {
        try
        {
          Canvas localCanvas = this.mSurfaceHolder.lockCanvas(null);
          ??? = localCanvas;
          SurfaceHolder localSurfaceHolder = this.mSurfaceHolder;
          ??? = localCanvas;
          try
          {
            Log.d("Draw", "ss");
            doDraw(localCanvas);
            j += 1;
            i = j;
            if (localCanvas != null)
            {
              this.mSurfaceHolder.unlockCanvasAndPost(localCanvas);
              i = j;
            }
            bool = this.timer.elapsed();
            long l3 = System.currentTimeMillis();
            long l2 = l1;
            j = i;
            if (l3 - l1 > 1000L)
            {
              Log.d("KZK", "FPS=" + i * 1000 / (l3 - l1));
              j = 0;
              l2 = l3;
            }
            synchronized (this.mPauseLock)
            {
              if (this.mPaused) {
                break label298;
              }
              l1 = l2;
            }
            localObject3 = finally;
          }
          finally
          {
            ??? = localObject2;
          }
          try
          {
            this.mPauseLock.wait();
          }
          catch (InterruptedException localInterruptedException) {}
        }
        finally
        {
          if (??? != null) {
            this.mSurfaceHolder.unlockCanvasAndPost((Canvas)???);
          }
        }
      }
    }
    
    public void setDrawable(FlotDraw paramFlotDraw)
    {
      this._fd = paramFlotDraw;
      if ((this._fd != null) && (this._fd.getOptions() != null))
      {
        this.timer = new FPSTimer(this._fd.getOptions().fps);
        return;
      }
      this.timer = new FPSTimer(60);
    }
    
    public void setRunning(boolean paramBoolean)
    {
      this.mRun = paramBoolean;
      Log.e("smardi.Epi", "setRunning:" + this.mRun);
    }
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\flot\FlotChartContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */