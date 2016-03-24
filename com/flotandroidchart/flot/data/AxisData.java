package com.flotandroidchart.flot.data;

import com.flotandroidchart.flot.format.DoubleFormatter;
import com.flotandroidchart.flot.format.TickFormatter;
import com.flotandroidchart.flot.format.TickGenerator;
import java.util.Vector;

public class AxisData
{
  public DoubleFormatter c2p = null;
  public double datamax = NaN.0D;
  public double datamin = NaN.0D;
  public double labelHeight = -1.0D;
  public double labelWidth = -1.0D;
  public double max = Double.MAX_VALUE;
  public double min = Double.MIN_VALUE;
  public DoubleFormatter p2c = null;
  public double scale = 1.0D;
  public SpecData specSize = null;
  public int tickDecimals = -1;
  public TickFormatter tickFormatter = null;
  public TickGenerator tickGenerator = null;
  public double tickSize = 0.0D;
  public Vector<TickData> ticks = new Vector();
  public Boolean used = Boolean.valueOf(false);
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\flot\data\AxisData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */