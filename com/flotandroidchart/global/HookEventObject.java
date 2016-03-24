package com.flotandroidchart.global;

import com.flotandroidchart.flot.FlotDraw;

public class HookEventObject
{
  public FlotDraw fd;
  public Object[] hookParam;
  
  public HookEventObject(FlotDraw paramFlotDraw, Object[] paramArrayOfObject)
  {
    this.fd = paramFlotDraw;
    this.hookParam = paramArrayOfObject;
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\global\HookEventObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */