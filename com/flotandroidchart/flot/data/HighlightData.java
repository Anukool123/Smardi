package com.flotandroidchart.flot.data;

public class HighlightData
{
  public String auto;
  public int dataIndex;
  public double[] point;
  public SeriesData series;
  
  public HighlightData(SeriesData paramSeriesData, double[] paramArrayOfDouble, String paramString, int paramInt)
  {
    this.series = paramSeriesData;
    this.point = paramArrayOfDouble;
    this.auto = paramString;
    this.dataIndex = paramInt;
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\flot\data\HighlightData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */