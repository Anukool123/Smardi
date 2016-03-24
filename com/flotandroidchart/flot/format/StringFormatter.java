package com.flotandroidchart.flot.format;

import com.flotandroidchart.flot.data.SeriesData;

public abstract interface StringFormatter
{
  public abstract String format(String paramString, SeriesData paramSeriesData);
  
  public abstract String formatNumber(double paramDouble, SeriesData paramSeriesData);
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\flot\format\StringFormatter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */