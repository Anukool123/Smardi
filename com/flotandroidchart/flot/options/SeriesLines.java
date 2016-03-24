package com.flotandroidchart.flot.options;

public class SeriesLines
{
  private Boolean _show = Boolean.valueOf(false);
  public Boolean fill = Boolean.valueOf(false);
  public Object fillColor = new Integer(16711680);
  public int lineWidth = 2;
  public Boolean showSet = Boolean.valueOf(false);
  public Boolean steps = Boolean.valueOf(false);
  
  public Boolean getShow()
  {
    return this._show;
  }
  
  public void setShow(Boolean paramBoolean)
  {
    this.showSet = Boolean.valueOf(true);
    this._show = paramBoolean;
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\flot\options\SeriesLines.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */