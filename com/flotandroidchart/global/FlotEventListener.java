package com.flotandroidchart.global;

import java.util.EventListener;

public abstract interface FlotEventListener
  extends EventListener
{
  public abstract String Name();
  
  public abstract void execute(FlotEvent paramFlotEvent);
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\global\FlotEventListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */