package org.smardi.epi.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;
import android.view.Window;
import java.util.ArrayList;

public class MyView
  extends View
{
  public static final int UNIT_10MIN = 2;
  public static final int UNIT_DAY = 1;
  Graph graph;
  ArrayList<GraphData> graphData;
  int height;
  float maxX;
  float maxY;
  float minX;
  float minY;
  int width;
  
  public MyView(Context paramContext, Window paramWindow)
  {
    super(paramContext);
    this.width = 300;
    this.height = 300;
    this.graph = new Graph(paramWindow);
  }
  
  public MyView(Context paramContext, Window paramWindow, int paramInt1, int paramInt2)
  {
    super(paramContext);
    this.width = paramInt1;
    this.height = paramInt2;
    this.graph = new Graph(paramWindow, 50, 50, this.width, this.height);
  }
  
  public MyView(Context paramContext, Window paramWindow, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    super(paramContext);
    this.width = paramInt1;
    this.height = paramInt2;
    this.minX = paramFloat1;
    this.maxX = paramFloat2;
    this.minY = paramFloat3;
    this.maxY = paramFloat4;
    this.graph = new Graph(paramWindow, 0, 25, this.width, this.height, paramFloat1, paramFloat2, paramFloat3, paramFloat4);
  }
  
  public void addGraphData(long paramLong, float paramFloat)
  {
    this.graphData.add(new GraphData(paramLong, paramFloat));
  }
  
  public void makeGraphData()
  {
    this.graphData = new ArrayList();
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    this.graph.setSize(this.width, this.height);
    this.graph.makeGraph(paramCanvas, true, true);
  }
  
  public void saveGraphData(int paramInt, String paramString)
  {
    this.graph.addDataArray(this.graphData, paramInt, paramString);
  }
  
  public void setXUnit(int paramInt)
  {
    this.graph.unitType = paramInt;
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\graph\MyView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */