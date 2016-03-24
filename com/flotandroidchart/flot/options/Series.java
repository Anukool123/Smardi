package com.flotandroidchart.flot.options;

public class Series
{
  public SeriesBars bars = new SeriesBars();
  public int color = -1;
  public SeriesLines lines = new SeriesLines();
  public SeriesPoints points = new SeriesPoints();
  public int shadowSize = 3;
  
  public void defaultLinesShow()
  {
    if ((!this.lines.showSet.booleanValue()) && (!this.points.show.booleanValue()) && (!this.bars.show.booleanValue())) {
      this.lines.setShow(Boolean.valueOf(true));
    }
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\flot\options\Series.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */