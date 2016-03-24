package com.flotandroidchart.flot;

import com.flotandroidchart.flot.options.AxisOption;
import com.flotandroidchart.flot.options.BackgroundCanvas;
import com.flotandroidchart.flot.options.Grid;
import com.flotandroidchart.flot.options.Legend;
import com.flotandroidchart.flot.options.Series;
import java.util.Hashtable;

public class Options
{
  public BackgroundCanvas canvas = new BackgroundCanvas();
  public int[] colors = { 15581760, 11524344, 13323083, 5089101, 9715949 };
  public int fps = 100;
  public Grid grid = new Grid();
  public Hashtable<String, Object> hooks = new Hashtable();
  public Legend legend = new Legend();
  public Series series = new Series();
  public AxisOption x2axis = new AxisOption();
  public AxisOption xaxis = new AxisOption();
  public AxisOption y2axis = new AxisOption();
  public AxisOption yaxis = new AxisOption();
  
  public Options()
  {
    this.yaxis.autoscaleMargin = 0.02D;
    this.y2axis.autoscaleMargin = 0.02D;
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\flot\Options.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */