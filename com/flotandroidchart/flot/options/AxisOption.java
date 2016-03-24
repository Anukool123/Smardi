package com.flotandroidchart.flot.options;

import com.flotandroidchart.flot.format.DoubleFormatter;
import com.flotandroidchart.flot.format.TickFormatter;

public class AxisOption
{
  public double autoscaleMargin = NaN.0D;
  public DoubleFormatter inverseTransform = null;
  public int labelHeight = -1;
  public int labelWidth = -1;
  public double max = NaN.0D;
  public double min = NaN.0D;
  public double minTickSize = Double.MIN_VALUE;
  public String mode = null;
  public String[] monthNames = null;
  public int tickDecimals = -1;
  public TickFormatter tickFormatter = null;
  public double tickSize = Double.MIN_VALUE;
  public Object ticks = null;
  public String timeformat = null;
  public DoubleFormatter transform = null;
  public Boolean twelveHourClock = Boolean.valueOf(false);
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\flot\options\AxisOption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */