package com.flotandroidchart.timer;

public class FPSTimer
{
  private long mCur;
  private int mFPS;
  private double mSecPerFrame;
  private double mSecTiming;
  
  public FPSTimer(int paramInt)
  {
    this.mFPS = paramInt;
    reset();
  }
  
  public boolean elapsed()
  {
    long l1 = System.currentTimeMillis();
    long l2 = this.mCur;
    this.mCur = l1;
    this.mSecTiming += (l1 - l2) / 1000.0D;
    this.mSecTiming -= this.mSecPerFrame;
    if (this.mSecTiming > 0.0D)
    {
      if (this.mSecTiming > this.mSecPerFrame)
      {
        reset();
        return true;
      }
      return false;
    }
    try
    {
      Thread.sleep((-this.mSecTiming * 1000.0D));
      return true;
    }
    catch (InterruptedException localInterruptedException) {}
    return true;
  }
  
  public void reset()
  {
    this.mSecPerFrame = (1.0D / this.mFPS);
    this.mCur = System.currentTimeMillis();
    this.mSecTiming = 0.0D;
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\timer\FPSTimer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */