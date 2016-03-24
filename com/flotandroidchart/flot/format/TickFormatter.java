package com.flotandroidchart.flot.format;

import com.flotandroidchart.flot.data.AxisData;

public class TickFormatter
{
  public double format(double paramDouble, AxisData paramAxisData)
  {
    return roundDouble(paramDouble, paramAxisData.tickDecimals);
  }
  
  public String formatNumber(double paramDouble, AxisData paramAxisData)
  {
    if (paramAxisData.tickDecimals == 0) {
      return String.valueOf((int)paramDouble);
    }
    return String.format("%." + paramAxisData.tickDecimals + "f", new Object[] { Double.valueOf(paramDouble) });
  }
  
  public double roundDouble(double paramDouble, int paramInt)
  {
    return (int)(Math.pow(10.0D, paramInt) * paramDouble) / Math.pow(10.0D, paramInt);
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\flot\format\TickFormatter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */