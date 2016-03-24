package com.flotandroidchart.flot.data;

public class NearItemData
{
  public int dataIndex;
  public double[] datapoint;
  public int pageX;
  public int pageY;
  public SeriesData series;
  public int seriesIndex;
  
  public NearItemData(double[] paramArrayOfDouble, int paramInt1, SeriesData paramSeriesData, int paramInt2)
  {
    this.datapoint = paramArrayOfDouble;
    this.dataIndex = paramInt1;
    this.series = paramSeriesData;
    this.seriesIndex = paramInt2;
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\flot\data\NearItemData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */