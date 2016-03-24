package com.flotandroidchart.flot.data;

import com.flotandroidchart.flot.options.Axies;
import com.flotandroidchart.flot.options.Series;
import java.lang.reflect.Array;
import java.util.Vector;

public class SeriesData
{
  public Axies axes = new Axies();
  private double[][] data;
  public Datapoints datapoints = new Datapoints();
  public String label = null;
  public Series series = new Series();
  
  public double[][] getData()
  {
    return this.data;
  }
  
  public void setData(Vector<PointData> paramVector)
  {
    if ((paramVector != null) && (paramVector.size() > 0))
    {
      int i = paramVector.size();
      this.data = ((double[][])Array.newInstance(Double.TYPE, new int[] { i, 2 }));
      i = 0;
      for (;;)
      {
        if (i >= paramVector.size()) {
          return;
        }
        this.data[i][0] = ((PointData)paramVector.get(i)).x;
        this.data[i][1] = ((PointData)paramVector.get(i)).y;
        i += 1;
      }
    }
    this.data = null;
  }
  
  public void setData(double[][] paramArrayOfDouble)
  {
    if ((paramArrayOfDouble != null) && (paramArrayOfDouble.length > 0) && (paramArrayOfDouble[0].length == 2))
    {
      this.data = paramArrayOfDouble;
      return;
    }
    this.data = null;
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\flot\data\SeriesData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */