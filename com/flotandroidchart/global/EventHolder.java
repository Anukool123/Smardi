package com.flotandroidchart.global;

import java.util.Enumeration;
import java.util.Vector;

public class EventHolder
{
  private Vector<FlotEventListener> _listener = new Vector();
  
  public void addEventListener(FlotEventListener paramFlotEventListener)
  {
    this._listener.add(paramFlotEventListener);
  }
  
  public void dispatchEvent(String paramString, FlotEvent paramFlotEvent)
  {
    Enumeration localEnumeration = this._listener.elements();
    for (;;)
    {
      if (!localEnumeration.hasMoreElements()) {
        return;
      }
      FlotEventListener localFlotEventListener = (FlotEventListener)localEnumeration.nextElement();
      if (localFlotEventListener.Name() == paramString) {
        localFlotEventListener.execute(paramFlotEvent);
      }
    }
  }
  
  public void removeEventListener(FlotEventListener paramFlotEventListener)
  {
    this._listener.remove(paramFlotEventListener);
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\global\EventHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */