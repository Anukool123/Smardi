package com.flotandroidchart.flot.data;

public class MarkingData
{
  public int color = 0;
  public int lineWidth = 0;
  public RangeData xaxis = new RangeData();
  public RangeData yaxis = new RangeData();
  
  public MarkingData(int paramInt1, int paramInt2, RangeData paramRangeData1, RangeData paramRangeData2)
  {
    this.color = paramInt1;
    this.lineWidth = paramInt2;
    if (paramRangeData1 != null) {
      this.xaxis = paramRangeData1;
    }
    if (paramRangeData2 != null) {
      this.yaxis = paramRangeData2;
    }
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\flot\data\MarkingData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */