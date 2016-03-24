package com.flotandroidchart.flot.format;

import com.flotandroidchart.flot.data.AxisData;
import com.flotandroidchart.flot.data.TickData;
import java.util.Vector;

public class TickGenerator
{
  protected double floorInBase(double paramDouble1, double paramDouble2)
  {
    return Math.floor(paramDouble1 / paramDouble2) * paramDouble2;
  }
  
  public Vector<TickData> generator(AxisData paramAxisData)
  {
    Vector localVector = new Vector();
    double d4 = floorInBase(paramAxisData.min, paramAxisData.tickSize);
    int i = 0;
    double d1 = NaN.0D;
    double d2 = d1;
    double d3 = d4 + i * paramAxisData.tickSize;
    if (paramAxisData.tickFormatter == null) {}
    for (String str = String.valueOf(d3);; str = paramAxisData.tickFormatter.formatNumber(d3, paramAxisData))
    {
      localVector.add(new TickData(d3, str));
      i += 1;
      if (d3 < paramAxisData.max)
      {
        d1 = d3;
        if (d3 != d2) {
          break;
        }
      }
      return localVector;
    }
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\flot\format\TickGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */