package com.flotandroidchart.flot.data;

public class FormatData
{
  public Integer defaultValue;
  public Boolean number;
  public Boolean required;
  public String xy;
  
  public FormatData(String paramString, Boolean paramBoolean1, Boolean paramBoolean2, Integer paramInteger)
  {
    this.xy = paramString;
    this.number = paramBoolean1;
    this.required = paramBoolean2;
    this.defaultValue = paramInteger;
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\flot\data\FormatData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */