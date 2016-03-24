package com.flotandroidchart.flot.data;

public class RangeData
{
  public AxisData axis = null;
  public double from = NaN.0D;
  public double to = NaN.0D;
  
  public RangeData() {}
  
  public RangeData(double paramDouble1, double paramDouble2)
  {
    this.from = paramDouble1;
    this.to = paramDouble2;
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\flot\data\RangeData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */