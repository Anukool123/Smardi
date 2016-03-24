package com.flotandroidchart.global;

import java.util.EventObject;

public class FlotEvent
  extends EventObject
{
  public static final String CANVAS_REPAINT = "repaint";
  public static final String HOOK_BINDEVENTS = "bindEvents";
  public static final String HOOK_DRAW = "draw";
  public static final String HOOK_DRAWOVERLAY = "drawOverlay";
  public static final String HOOK_PROCESSDATAPOINTS = "processDatapoints";
  public static final String HOOK_PROCESSOPTIONS = "processOptions";
  public static final String HOOK_PROCESSRAWDATA = "processRawData";
  public static final String MOUSE_CLICK = "click";
  public static final String MOUSE_HOVER = "hover";
  private static final long serialVersionUID = 1L;
  
  public FlotEvent(Object paramObject)
  {
    super(paramObject);
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\global\FlotEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */