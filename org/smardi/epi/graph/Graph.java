package org.smardi.epi.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Window;
import android.view.WindowManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Graph
{
  final float DEFAULT_HEIGHT = 300.0F;
  final float DEFAULT_ORI_X = 0.0F;
  final float DEFAULT_ORI_Y = 50.0F;
  final float DEFAULT_WIDTH = 600.0F;
  ArrayList<Integer> arrayGraphColor = new ArrayList();
  ArrayList<ArrayList> arrayGraphData = new ArrayList();
  ArrayList<String> arrayGraphTitle = new ArrayList();
  AxisX axisX;
  AxisY axisY;
  int colorLine = 0;
  int colorPoint = 0;
  Context context;
  int dataCount = 0;
  ArrayList<GraphData> graphDatas = new ArrayList();
  int graphMarginTop = DPtoPX(10.0F);
  public Origin origin;
  DisplayMetrics outMetrics = new DisplayMetrics();
  public Size size;
  public int unitType = 0;
  Window window;
  
  public Graph(Window paramWindow)
  {
    this.window = paramWindow;
    paramWindow.getWindowManager().getDefaultDisplay().getMetrics(this.outMetrics);
    this.origin = new Origin(0.0F, 50.0F);
    this.size = new Size(600.0F, 300.0F);
    this.arrayGraphData = new ArrayList();
    this.arrayGraphColor = new ArrayList();
  }
  
  public Graph(Window paramWindow, int paramInt1, int paramInt2)
  {
    this.window = paramWindow;
    paramWindow.getWindowManager().getDefaultDisplay().getMetrics(this.outMetrics);
    this.origin = new Origin(0.0F, 50.0F);
    this.size = new Size(paramInt1, paramInt2);
    this.arrayGraphData = new ArrayList();
    this.arrayGraphColor = new ArrayList();
  }
  
  public Graph(Window paramWindow, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.window = paramWindow;
    paramWindow.getWindowManager().getDefaultDisplay().getMetrics(this.outMetrics);
    this.origin = new Origin(paramInt1, paramInt2);
    this.size = new Size(paramInt3, paramInt4);
  }
  
  public Graph(Window paramWindow, int paramInt1, int paramInt2, int paramInt3, int paramInt4, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    this.window = paramWindow;
    paramWindow.getWindowManager().getDefaultDisplay().getMetrics(this.outMetrics);
    this.origin = new Origin(paramInt1, paramInt2);
    this.size = new Size(paramInt3, paramInt4);
    this.axisX = new AxisX();
    this.axisY = new AxisY();
    this.axisX.setMin(paramFloat1);
    this.axisX.setMax(paramFloat2);
    this.axisY.setMin(paramFloat3);
    this.axisY.setMax(paramFloat4);
    this.graphMarginTop = DPtoPX(10.0F);
    this.arrayGraphData = new ArrayList();
    this.arrayGraphColor = new ArrayList();
  }
  
  private void makeAxixX(int paramInt, float paramFloat1, float paramFloat2, Canvas paramCanvas)
  {
    Paint localPaint = new Paint();
    localPaint.setColor(-7829368);
    localPaint.setStrokeWidth(1.0F);
    int i = 0;
    if (i * paramInt > this.size.px_width - this.origin.px_x) {
      return;
    }
    float f1;
    float f2;
    float f3;
    float f4;
    float f5;
    float f6;
    float f7;
    float f8;
    float f9;
    if (i % 5 == 0)
    {
      f1 = this.origin.px_x;
      f2 = i * paramInt;
      f3 = this.size.px_height;
      f4 = this.origin.px_y;
      f5 = this.graphMarginTop;
      f6 = this.origin.px_x;
      f7 = i * paramInt;
      f8 = this.size.px_height;
      f9 = this.origin.px_y;
      paramCanvas.drawLine(f2 + f1, f5 + (f3 - f4), f7 + f6, this.graphMarginTop + (f8 - f9 + 10.0F), localPaint);
      localPaint.setTextAlign(Paint.Align.CENTER);
      localPaint.setAntiAlias(true);
      paramCanvas.drawText(String.format("%.1f", new Object[] { Float.valueOf((i * paramInt - this.axisX.getAutoscaleMargin()) / paramFloat2 + paramFloat1) }), this.origin.px_x + i * paramInt, this.size.px_height - this.origin.px_y + 25.0F + this.graphMarginTop, localPaint);
      localPaint.setAntiAlias(false);
    }
    for (;;)
    {
      i += 1;
      break;
      f1 = this.origin.px_x;
      f2 = i * paramInt;
      f3 = this.size.px_height;
      f4 = this.origin.px_y;
      f5 = this.graphMarginTop;
      f6 = this.origin.px_x;
      f7 = i * paramInt;
      f8 = this.size.px_height;
      f9 = this.origin.px_y;
      paramCanvas.drawLine(f2 + f1, f5 + (f3 - f4), f7 + f6, this.graphMarginTop + (f8 - f9 + 5.0F), localPaint);
    }
  }
  
  private void makeAxixY(int paramInt, float paramFloat1, float paramFloat2, Canvas paramCanvas, boolean paramBoolean)
  {
    Paint localPaint = new Paint();
    localPaint.setColor(-7829368);
    localPaint.setStrokeWidth(1.0F);
    int i = 0;
    if (i * paramInt > this.size.px_height - this.origin.px_y) {
      return;
    }
    localPaint.setAlpha(200);
    float f1;
    float f2;
    float f3;
    float f4;
    float f5;
    float f6;
    float f7;
    float f8;
    float f9;
    float f10;
    if (i % 5 == 0)
    {
      f1 = this.origin.px_x;
      f2 = this.size.px_height;
      f3 = this.origin.px_y;
      f4 = paramInt * i;
      f5 = this.graphMarginTop;
      f6 = this.origin.px_x;
      f7 = this.size.px_height;
      f8 = this.origin.px_y;
      f9 = paramInt * i;
      paramCanvas.drawLine(f1, f5 + (f2 - f3 - f4), -10.0F + f6, this.graphMarginTop + (f7 - f8 - f9), localPaint);
      if (paramBoolean)
      {
        f1 = this.origin.px_x;
        f2 = this.size.px_height;
        f3 = this.origin.px_y;
        f4 = paramInt * i;
        f5 = this.graphMarginTop;
        f6 = this.size.px_width;
        f7 = this.origin.px_x;
        f8 = this.size.px_height;
        f9 = this.origin.px_y;
        f10 = paramInt * i;
        paramCanvas.drawLine(f1, f5 + (f2 - f3 - f4), f7 + f6, this.graphMarginTop + (f8 - f9 - f10), localPaint);
      }
      localPaint.setTextAlign(Paint.Align.RIGHT);
      localPaint.setAlpha(255);
      localPaint.setAntiAlias(true);
      paramCanvas.drawText(String.format("%.1f", new Object[] { Float.valueOf((i * paramInt - this.axisY.getAutoscaleMargin()) / paramFloat2 + paramFloat1) }), this.origin.px_x - 15.0F, this.size.px_height - this.origin.px_y - i * paramInt + 4.0F + this.graphMarginTop, localPaint);
      localPaint.setAntiAlias(false);
    }
    for (;;)
    {
      i += 1;
      break;
      f1 = this.origin.px_x;
      f2 = this.size.px_height;
      f3 = this.origin.px_y;
      f4 = paramInt * i;
      f5 = this.graphMarginTop;
      f6 = this.origin.px_x;
      f7 = this.size.px_height;
      f8 = this.origin.px_y;
      f9 = paramInt * i;
      paramCanvas.drawLine(f1, f5 + (f2 - f3 - f4), -5.0F + f6, this.graphMarginTop + (f7 - f8 - f9), localPaint);
      if (paramBoolean)
      {
        localPaint.setAlpha(30);
        f1 = this.origin.px_x;
        f2 = this.size.px_height;
        f3 = this.origin.px_y;
        f4 = paramInt * i;
        f5 = this.graphMarginTop;
        f6 = this.size.px_width;
        f7 = this.origin.px_x;
        f8 = this.size.px_height;
        f9 = this.origin.px_y;
        f10 = paramInt * i;
        paramCanvas.drawLine(f1, f5 + (f2 - f3 - f4), f7 + f6, this.graphMarginTop + (f8 - f9 - f10), localPaint);
      }
    }
  }
  
  private void makeIndexAtGraph(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, Canvas paramCanvas, int paramInt)
  {
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(true);
    int i = 0;
    if (i >= this.graphDatas.size()) {
      return;
    }
    if (paramInt == 0) {
      localPaint.setColor(Color.rgb(71, 187, 255));
    }
    for (;;)
    {
      int j = Math.round(((float)((GraphData)this.graphDatas.get(i)).x - paramFloat1) * paramFloat3 + paramFloat5);
      int k = (int)(this.size.px_height - this.origin.px_y - Math.round((((GraphData)this.graphDatas.get(i)).y - paramFloat2) * paramFloat4 + paramFloat6) + this.graphMarginTop - 20.0F);
      Paint.FontMetrics localFontMetrics = localPaint.getFontMetrics();
      String str = Math.round(((GraphData)this.graphDatas.get(i)).y);
      float f = localPaint.measureText(str);
      paramCanvas.drawText(str, this.origin.px_x + j - f / 2.0F, k - (localFontMetrics.ascent + localFontMetrics.descent) / 2.0F, localPaint);
      i += 1;
      break;
      localPaint.setColor(paramInt);
    }
  }
  
  private void makeLabelX(String paramString, Canvas paramCanvas)
  {
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(true);
    localPaint.setTextAlign(Paint.Align.CENTER);
    localPaint.setTextSize(18.0F);
    localPaint.setColor(Color.rgb(14, 132, 204));
    paramCanvas.drawText(paramString, (this.size.px_width + this.origin.px_x) / 2.0F, this.size.px_height - this.origin.px_y + 50.0F + this.graphMarginTop, localPaint);
  }
  
  private void makeLabelY(String paramString, Canvas paramCanvas)
  {
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(true);
    localPaint.setTextAlign(Paint.Align.CENTER);
    localPaint.setTextSize(18.0F);
    localPaint.setColor(Color.rgb(14, 132, 204));
    paramCanvas.translate(this.origin.px_x - 50.0F, (this.size.px_height - this.origin.px_y) / 2.0F + this.graphMarginTop);
    paramCanvas.rotate(-90.0F);
    paramCanvas.drawText(paramString, 0.0F, 0.0F, localPaint);
    paramCanvas.restore();
  }
  
  private void makeLineAtGraph(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, Canvas paramCanvas, int paramInt)
  {
    Paint localPaint = new Paint();
    localPaint.setStrokeWidth(2.0F);
    localPaint.setAntiAlias(true);
    int i = 0;
    if (i >= this.graphDatas.size()) {
      return;
    }
    if (paramInt == 0) {
      localPaint.setColor(Color.rgb(88, 89, 91));
    }
    for (;;)
    {
      int j = Math.round(((float)((GraphData)this.graphDatas.get(i)).x - paramFloat1) * paramFloat3 + paramFloat5);
      int k = (int)(this.size.px_height - this.origin.px_y - Math.round((((GraphData)this.graphDatas.get(i)).y - paramFloat2) * paramFloat4 + paramFloat6) + this.graphMarginTop);
      float f1 = this.origin.px_x;
      float f2 = j;
      float f3 = k;
      float f4 = this.origin.px_x;
      float f5 = j;
      float f6 = this.size.px_height;
      float f7 = this.origin.px_y;
      paramCanvas.drawLine(f2 + f1, f3, f5 + f4, this.graphMarginTop + (f6 - f7), localPaint);
      i += 1;
      break;
      localPaint.setColor(paramInt);
    }
  }
  
  private void makeNumberAtPoint(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, Canvas paramCanvas, int paramInt)
  {
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(true);
    int i = 0;
    if (i >= this.graphDatas.size()) {
      return;
    }
    if (paramInt == 0) {
      localPaint.setColor(Color.rgb(88, 89, 91));
    }
    for (;;)
    {
      int j = Math.round(((float)((GraphData)this.graphDatas.get(i)).x - paramFloat1) * paramFloat3 + paramFloat5);
      int k = (int)(this.size.px_height - this.origin.px_y - Math.round((((GraphData)this.graphDatas.get(i)).y - paramFloat2) * paramFloat4 + paramFloat6) + this.graphMarginTop);
      paramCanvas.drawCircle(this.origin.px_x + j, k, 5.0F, localPaint);
      localPaint.setColor(-1);
      paramCanvas.drawCircle(this.origin.px_x + j, k, 3.0F, localPaint);
      i += 1;
      break;
      localPaint.setColor(paramInt);
    }
  }
  
  private void makePointAtGraph(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, Canvas paramCanvas, int paramInt)
  {
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(true);
    int i = 0;
    if (i >= this.graphDatas.size()) {
      return;
    }
    label52:
    int j;
    int k;
    Paint.FontMetrics localFontMetrics;
    Object localObject;
    if (paramInt == 0)
    {
      localPaint.setColor(Color.rgb(71, 187, 255));
      localPaint.setTextSize(16.0F);
      j = Math.round(((float)((GraphData)this.graphDatas.get(i)).x - paramFloat1) * paramFloat3 + paramFloat5);
      k = (int)(this.size.px_height - this.origin.px_y) + 30;
      localFontMetrics = localPaint.getFontMetrics();
      if (this.unitType != 1) {
        break label234;
      }
      localObject = new SimpleDateFormat("MM/dd");
      label136:
      localObject = ((SimpleDateFormat)localObject).format(new Date(((GraphData)this.graphDatas.get(i)).x));
      paramFloat2 = localPaint.measureText((String)localObject);
      if (i != 0) {
        break label249;
      }
      paramCanvas.drawText((String)localObject, this.origin.px_x + j, k - (localFontMetrics.ascent + localFontMetrics.descent) / 2.0F, localPaint);
    }
    for (;;)
    {
      i += 1;
      break;
      localPaint.setColor(paramInt);
      break label52;
      label234:
      localObject = new SimpleDateFormat("HH:mm");
      break label136;
      label249:
      if (i == this.graphDatas.size() - 1) {
        paramCanvas.drawText((String)localObject, this.origin.px_x + j - paramFloat2, k - (localFontMetrics.ascent + localFontMetrics.descent) / 2.0F, localPaint);
      } else {
        paramCanvas.drawText((String)localObject, this.origin.px_x + j - paramFloat2 / 2.0F, k - (localFontMetrics.ascent + localFontMetrics.descent) / 2.0F, localPaint);
      }
    }
  }
  
  public int DPtoPX(float paramFloat)
  {
    return Math.round(this.outMetrics.density * paramFloat);
  }
  
  public void addData(long paramLong, float paramFloat)
  {
    GraphData localGraphData = new GraphData(paramLong, paramFloat);
    this.graphDatas.add(localGraphData);
  }
  
  public void addData(GraphData paramGraphData)
  {
    this.graphDatas.add(paramGraphData);
  }
  
  public void addDataArray(ArrayList<GraphData> paramArrayList, int paramInt, String paramString)
  {
    this.arrayGraphData.add(paramArrayList);
    this.arrayGraphColor.add(Integer.valueOf(paramInt));
    this.arrayGraphTitle.add(paramString);
  }
  
  public void makeGraph(Canvas paramCanvas, boolean paramBoolean1, boolean paramBoolean2)
  {
    Paint localPaint = new Paint();
    float f4 = 0.0F;
    float f2 = 0.0F;
    float f3 = 0.0F;
    float f1 = 0.0F;
    int i = 0;
    float f5;
    float f6;
    float f7;
    float f8;
    float f9;
    if (i >= this.arrayGraphData.size())
    {
      localPaint.setAntiAlias(true);
      localPaint.setColor(Color.rgb(88, 89, 91));
      localPaint.setStyle(Paint.Style.STROKE);
      localPaint.setStrokeWidth(1.0F);
      f5 = this.origin.px_x;
      f6 = this.size.px_height;
      f7 = this.origin.px_y;
      f8 = this.graphMarginTop;
      f9 = this.size.px_width;
      float f10 = this.size.px_height;
      float f11 = this.origin.px_y;
      paramCanvas.drawLine(f5, f8 + (f6 - f7), f9, this.graphMarginTop + (f10 - f11), localPaint);
      if (this.axisX.getMin() != Float.NEGATIVE_INFINITY)
      {
        f4 = this.axisX.getMin();
        paramCanvas.drawText(String.valueOf(f4), 30.0F, 30.0F, localPaint);
      }
      if (this.axisX.getMax() != Float.POSITIVE_INFINITY) {
        f3 = this.axisX.getMax();
      }
      if (this.axisY.getMin() != Float.NEGATIVE_INFINITY)
      {
        f2 = this.axisY.getMin();
        this.axisY.setAutoscaleMargin(0);
      }
      if (this.axisY.getMax() != Float.POSITIVE_INFINITY)
      {
        f1 = this.axisY.getMax();
        this.axisY.setAutoscaleMargin(0);
      }
      f8 = this.size.px_width;
      f9 = this.origin.px_x;
      f10 = this.axisX.getAutoscaleMargin() * 2;
      f5 = this.size.px_height;
      f6 = this.origin.px_y;
      f7 = this.axisY.getAutoscaleMargin() * 2;
      f3 = (f8 - f9 - f10) / (f3 - f4);
      f1 = (f5 - f6 - f7) / (f1 - f2);
      f5 = this.axisX.getAutoscaleMargin();
      f6 = this.axisY.getAutoscaleMargin();
      localPaint.setStrokeWidth(1.0F);
      localPaint.setColor(-7829368);
      i = 0;
    }
    for (;;)
    {
      if (i >= this.arrayGraphData.size())
      {
        return;
        this.graphDatas = ((ArrayList)this.arrayGraphData.get(i));
        int j = 0;
        f9 = f1;
        f7 = f3;
        f5 = f2;
        f6 = f4;
        if (j >= this.graphDatas.size())
        {
          i += 1;
          f4 = f6;
          f2 = f5;
          f3 = f7;
          f1 = f9;
          break;
        }
        f2 = (float)((GraphData)this.graphDatas.get(j)).x;
        f1 = ((GraphData)this.graphDatas.get(j)).y;
        if (j == 0)
        {
          f4 = f2;
          f8 = f2;
          f2 = f1;
          f5 = f1;
        }
        for (;;)
        {
          j += 1;
          f6 = f8;
          f7 = f4;
          f9 = f2;
          break;
          f3 = f6;
          if (f6 > f2) {
            f3 = f2;
          }
          f6 = f7;
          if (f7 < f2) {
            f6 = f2;
          }
          f7 = f5;
          if (f5 > f1) {
            f7 = f1;
          }
          f8 = f3;
          f5 = f7;
          f4 = f6;
          f2 = f9;
          if (f9 < f1)
          {
            f8 = f3;
            f5 = f7;
            f4 = f6;
            f2 = f1;
          }
        }
      }
      this.graphDatas = ((ArrayList)this.arrayGraphData.get(i));
      makeLineAtGraph(f4, f2, f3, f1, f5, f6, paramCanvas, ((Integer)this.arrayGraphColor.get(i)).intValue());
      makePointAtGraph(f4, f2, f3, f1, f5, f6, paramCanvas, ((Integer)this.arrayGraphColor.get(i)).intValue());
      makeNumberAtPoint(f4, f2, f3, f1, f5, f6, paramCanvas, ((Integer)this.arrayGraphColor.get(i)).intValue());
      makeIndexAtGraph(f4, f2, f3, f1, f5, f6, paramCanvas, ((Integer)this.arrayGraphColor.get(i)).intValue());
      i += 1;
    }
  }
  
  public void removeAllData()
  {
    this.graphDatas = new ArrayList();
  }
  
  public void removeDataAtIndex(int paramInt)
  {
    if (paramInt < this.graphDatas.size()) {
      this.graphDatas.remove(paramInt);
    }
  }
  
  public void setColorLine(int paramInt)
  {
    this.colorLine = paramInt;
  }
  
  public void setColorPoint(int paramInt)
  {
    this.colorPoint = paramInt;
  }
  
  public void setOrigin(float paramFloat1, float paramFloat2)
  {
    this.origin.dp_x = paramFloat1;
    this.origin.dp_y = paramFloat2;
    this.origin.px_x = DPtoPX(paramFloat1);
    this.origin.px_y = DPtoPX(paramFloat2);
  }
  
  public void setSize(float paramFloat1, float paramFloat2)
  {
    this.size.dp_width = paramFloat1;
    this.size.dp_height = paramFloat2;
    this.size.px_width = DPtoPX(paramFloat1);
    this.size.px_height = DPtoPX(paramFloat2);
  }
  
  public class AxisX
  {
    private int autoscaleMargin;
    private float max;
    private float min;
    private String mode;
    private int tickSize;
    private ArrayList<Graph.Ticks> ticks;
    
    public AxisX()
    {
      setMode(null);
      setMin(Float.NEGATIVE_INFINITY);
      setMax(Float.POSITIVE_INFINITY);
      setAutoscaleMargin(10);
      setTicks(null);
      setTickSize(0);
    }
    
    public int getAutoscaleMargin()
    {
      return this.autoscaleMargin;
    }
    
    public float getMax()
    {
      return this.max;
    }
    
    public float getMin()
    {
      return this.min;
    }
    
    public String getMode()
    {
      return this.mode;
    }
    
    public int getTickSize()
    {
      return this.tickSize;
    }
    
    public ArrayList<Graph.Ticks> getTicks()
    {
      return this.ticks;
    }
    
    public void setAutoscaleMargin(int paramInt)
    {
      this.autoscaleMargin = paramInt;
    }
    
    public void setMax(float paramFloat)
    {
      this.max = paramFloat;
    }
    
    public void setMin(float paramFloat)
    {
      this.min = paramFloat;
    }
    
    public void setMode(String paramString)
    {
      this.mode = paramString;
    }
    
    public void setTickSize(int paramInt)
    {
      this.tickSize = paramInt;
    }
    
    public void setTicks(ArrayList<Graph.Ticks> paramArrayList)
    {
      this.ticks = paramArrayList;
    }
  }
  
  public class AxisY
  {
    private int autoscaleMargin;
    private float max;
    private float min;
    private String mode;
    private int tickSize;
    private ArrayList<Graph.Ticks> ticks;
    
    public AxisY()
    {
      setMode(null);
      setMin(Float.NEGATIVE_INFINITY);
      setMax(Float.POSITIVE_INFINITY);
      setAutoscaleMargin(10);
      setTicks(null);
      setTickSize(0);
    }
    
    public int getAutoscaleMargin()
    {
      return this.autoscaleMargin;
    }
    
    public float getMax()
    {
      return this.max;
    }
    
    public float getMin()
    {
      return this.min;
    }
    
    public String getMode()
    {
      return this.mode;
    }
    
    public int getTickSize()
    {
      return this.tickSize;
    }
    
    public ArrayList<Graph.Ticks> getTicks()
    {
      return this.ticks;
    }
    
    public void setAutoscaleMargin(int paramInt)
    {
      this.autoscaleMargin = paramInt;
    }
    
    public void setMax(float paramFloat)
    {
      this.max = paramFloat;
    }
    
    public void setMin(float paramFloat)
    {
      this.min = paramFloat;
    }
    
    public void setMode(String paramString)
    {
      this.mode = paramString;
    }
    
    public void setTickSize(int paramInt)
    {
      this.tickSize = paramInt;
    }
    
    public void setTicks(ArrayList<Graph.Ticks> paramArrayList)
    {
      this.ticks = paramArrayList;
    }
  }
  
  public class Legend
  {
    private int backgroundColor;
    private int labelBoxBorderColor;
    private int margin;
    private String position;
    private boolean show;
    
    public Legend()
    {
      setShow(false);
      setLabelBoxBorderColor(-7829368);
      setPosition("nw");
      setMargin(10);
      setBackgroundColor(Color.argb(125, 255, 255, 255));
    }
    
    int getBackgroundColor()
    {
      return this.backgroundColor;
    }
    
    int getLabelBoxBorderColor()
    {
      return this.labelBoxBorderColor;
    }
    
    int getMargin()
    {
      return this.margin;
    }
    
    String getPosition()
    {
      return this.position;
    }
    
    boolean isShow()
    {
      return this.show;
    }
    
    void setBackgroundColor(int paramInt)
    {
      this.backgroundColor = paramInt;
    }
    
    void setLabelBoxBorderColor(int paramInt)
    {
      this.labelBoxBorderColor = paramInt;
    }
    
    void setMargin(int paramInt)
    {
      this.margin = paramInt;
    }
    
    void setPosition(String paramString)
    {
      this.position = paramString;
    }
    
    void setShow(boolean paramBoolean)
    {
      this.show = paramBoolean;
    }
  }
  
  public class Origin
  {
    float dp_x;
    float dp_y;
    float px_x;
    float px_y;
    
    public Origin(float paramFloat1, float paramFloat2)
    {
      this.dp_x = paramFloat1;
      this.dp_y = paramFloat2;
      this.px_x = Graph.this.DPtoPX(paramFloat1);
      this.px_y = Graph.this.DPtoPX(paramFloat2);
      Graph.this.graphMarginTop = Graph.this.DPtoPX(10.0F);
    }
  }
  
  public class Size
  {
    float dp_height;
    float dp_width;
    float px_height;
    float px_width;
    
    public Size(float paramFloat1, float paramFloat2)
    {
      this.dp_width = paramFloat1;
      this.dp_height = paramFloat2;
      this.px_width = Graph.this.DPtoPX(paramFloat1);
      this.px_height = Graph.this.DPtoPX(paramFloat2);
    }
  }
  
  public class Ticks
  {
    float XorY;
    String value;
    
    public Ticks(float paramFloat, String paramString)
    {
      this.XorY = paramFloat;
      this.value = paramString;
    }
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\graph\Graph.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */