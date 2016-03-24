package com.flotandroidchart.flot;

import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader.TileMode;
import android.util.Log;
import android.view.MotionEvent;
import com.flotandroidchart.flot.data.AxisData;
import com.flotandroidchart.flot.data.Datapoints;
import com.flotandroidchart.flot.data.FormatData;
import com.flotandroidchart.flot.data.HighlightData;
import com.flotandroidchart.flot.data.MarkingData;
import com.flotandroidchart.flot.data.NearItemData;
import com.flotandroidchart.flot.data.RangeData;
import com.flotandroidchart.flot.data.RectOffset;
import com.flotandroidchart.flot.data.SeriesData;
import com.flotandroidchart.flot.data.SpecData;
import com.flotandroidchart.flot.data.TickData;
import com.flotandroidchart.flot.format.DoubleFormatter;
import com.flotandroidchart.flot.format.DrawFormatter;
import com.flotandroidchart.flot.format.StringFormatter;
import com.flotandroidchart.flot.format.TickFormatter;
import com.flotandroidchart.flot.format.TickGenerator;
import com.flotandroidchart.flot.format.TooltipFormatter;
import com.flotandroidchart.flot.options.Axies;
import com.flotandroidchart.flot.options.AxisOption;
import com.flotandroidchart.flot.options.BackgroundCanvas;
import com.flotandroidchart.flot.options.ColorHelper;
import com.flotandroidchart.flot.options.Grid;
import com.flotandroidchart.flot.options.Legend;
import com.flotandroidchart.flot.options.Series;
import com.flotandroidchart.flot.options.SeriesBars;
import com.flotandroidchart.flot.options.SeriesLines;
import com.flotandroidchart.flot.options.SeriesPoints;
import com.flotandroidchart.global.EventHolder;
import com.flotandroidchart.global.FlotEvent;
import com.flotandroidchart.global.FlotEventListener;
import com.flotandroidchart.global.HookEventObject;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TimeZone;
import java.util.Vector;

public class FlotDraw
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Axies axes;
  private int canvasHeight;
  private int canvasWidth;
  private EventHolder eventHolder;
  private Canvas grap = null;
  private Canvas grapOverlay = null;
  private Paint gridLabelPaint;
  private Vector<HighlightData> highlights = new Vector();
  private EventHolder hookHolder;
  private Paint legendLabelPaint;
  private Options options;
  private int plotHeight;
  private RectOffset plotOffset = new RectOffset();
  private int plotWidth;
  private Vector<SeriesData> series;
  Vector<SpecData> spec = new Vector();
  Hashtable<String, Double> timeUnitSize = new Hashtable();
  
  public FlotDraw(Vector<SeriesData> paramVector, Options paramOptions, IPlugin[] paramArrayOfIPlugin)
  {
    this.series = paramVector;
    if (paramOptions == null) {}
    for (this.options = new Options();; this.options = paramOptions)
    {
      this.axes = new Axies();
      this.axes.xaxis = new AxisData();
      this.axes.yaxis = new AxisData();
      this.axes.x2axis = new AxisData();
      this.axes.y2axis = new AxisData();
      this.hookHolder = new EventHolder();
      this.eventHolder = new EventHolder();
      this.gridLabelPaint = getAnti();
      this.gridLabelPaint.setTextSize(10.0F);
      this.gridLabelPaint.setColor(getTranColor(this.options.grid.labelColor));
      this.legendLabelPaint = new Paint(this.gridLabelPaint);
      this.legendLabelPaint.setColor(getTranColor(this.options.legend.labelColor));
      this.eventHolder.addEventListener(new FlotEventListener()
      {
        public String Name()
        {
          return "hover";
        }
        
        public void execute(FlotEvent paramAnonymousFlotEvent)
        {
          if ((paramAnonymousFlotEvent.getSource() instanceof MotionEvent))
          {
            paramAnonymousFlotEvent = (MotionEvent)paramAnonymousFlotEvent.getSource();
            if (paramAnonymousFlotEvent != null) {
              FlotDraw.this.executeHighlight(Name(), paramAnonymousFlotEvent, FlotDraw.this.options.grid.hoverable.booleanValue());
            }
          }
        }
      });
      this.eventHolder.addEventListener(new FlotEventListener()
      {
        public String Name()
        {
          return "click";
        }
        
        public void execute(FlotEvent paramAnonymousFlotEvent) {}
      });
      initPlugins(paramArrayOfIPlugin);
      parseOptions(this.options);
      setData(this.series);
      return;
    }
  }
  
  private void addLabels(AxisData paramAxisData, DrawFormatter paramDrawFormatter)
  {
    int i = 0;
    if (i >= paramAxisData.ticks.size()) {
      return;
    }
    TickData localTickData = (TickData)paramAxisData.ticks.get(i);
    if ((localTickData.label == null) || (localTickData.v < paramAxisData.min) || (localTickData.v > paramAxisData.max)) {}
    for (;;)
    {
      i += 1;
      break;
      paramDrawFormatter.draw(paramAxisData, localTickData);
    }
  }
  
  private void draw()
  {
    Grid localGrid = this.options.grid;
    if ((localGrid.show.booleanValue()) && (!localGrid.aboveData.booleanValue())) {
      drawGrid();
    }
    int i = 0;
    for (;;)
    {
      if (i >= this.series.size())
      {
        this.hookHolder.dispatchEvent("draw", new FlotEvent(new HookEventObject(this, new Object[] { this.grap })));
        if ((localGrid.show.booleanValue()) && (localGrid.aboveData.booleanValue())) {
          drawGrid();
        }
        if (this.options.grid.show.booleanValue()) {
          insertLabels();
        }
        insertLegend();
        return;
      }
      drawSeries((SeriesData)this.series.get(i));
      i += 1;
    }
  }
  
  private void drawBar(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, float paramFloat5, float paramFloat6, AxisData paramAxisData1, AxisData paramAxisData2, SeriesData paramSeriesData, Canvas paramCanvas)
  {
    int n;
    int k;
    int i1;
    int m;
    float f1;
    float f2;
    float f3;
    int i;
    int j;
    if (paramSeriesData.series.bars.horizontal.booleanValue())
    {
      n = 1;
      k = 1;
      i1 = 1;
      m = 0;
      f1 = paramFloat3;
      f2 = paramFloat1;
      f3 = paramFloat2 + paramFloat4;
      paramFloat5 = paramFloat2 + paramFloat5;
      paramFloat2 = paramFloat5;
      i = i1;
      j = n;
      paramFloat4 = f1;
      paramFloat3 = f2;
      paramFloat1 = f3;
      if (f2 < f1)
      {
        paramFloat3 = f1;
        paramFloat4 = f2;
        m = 1;
        k = 0;
        paramFloat1 = f3;
        j = n;
        i = i1;
        paramFloat2 = paramFloat5;
      }
      if ((paramFloat3 >= paramAxisData1.min) && (paramFloat4 <= paramAxisData1.max) && (paramFloat1 >= paramAxisData2.min) && (paramFloat2 <= paramAxisData2.max)) {
        break label238;
      }
    }
    label238:
    do
    {
      return;
      j = 1;
      n = 1;
      i1 = 1;
      i = 0;
      f2 = paramFloat1 + paramFloat4;
      f3 = paramFloat1 + paramFloat5;
      paramFloat5 = paramFloat3;
      f1 = paramFloat2;
      paramFloat2 = paramFloat5;
      m = i1;
      k = n;
      paramFloat4 = f2;
      paramFloat3 = f3;
      paramFloat1 = f1;
      if (f1 >= paramFloat5) {
        break;
      }
      paramFloat1 = paramFloat5;
      paramFloat2 = f1;
      i = 1;
      j = 0;
      m = i1;
      k = n;
      paramFloat4 = f2;
      paramFloat3 = f3;
      break;
      paramFloat5 = paramFloat4;
      if (paramFloat4 < paramAxisData1.min)
      {
        paramFloat5 = (float)paramAxisData1.min;
        m = 0;
      }
      paramFloat4 = paramFloat3;
      if (paramFloat3 > paramAxisData1.max)
      {
        paramFloat4 = (float)paramAxisData1.max;
        k = 0;
      }
      paramFloat3 = paramFloat2;
      if (paramFloat2 < paramAxisData2.min)
      {
        paramFloat3 = (float)paramAxisData2.min;
        i = 0;
      }
      paramFloat2 = paramFloat1;
      if (paramFloat1 > paramAxisData2.max)
      {
        paramFloat2 = (float)paramAxisData2.max;
        j = 0;
      }
      paramFloat1 = (float)paramAxisData1.p2c.format(paramFloat5);
      paramFloat3 = (float)paramAxisData2.p2c.format(paramFloat3);
      paramFloat4 = (float)paramAxisData1.p2c.format(paramFloat4);
      paramFloat2 = (float)paramAxisData2.p2c.format(paramFloat2);
      paramAxisData1 = new Path();
      paramAxisData2 = getFillStyle(paramSeriesData.series.bars.fill.booleanValue(), paramSeriesData.series.bars.fillColor, paramSeriesData.series.color, paramFloat3, paramFloat2);
      if ((paramAxisData2 != null) && (paramSeriesData.series.bars.fill.booleanValue()))
      {
        paramAxisData1.moveTo(paramFloat1, paramFloat3);
        paramAxisData1.lineTo(paramFloat1, paramFloat2);
        paramAxisData1.lineTo(paramFloat4, paramFloat2);
        paramAxisData1.lineTo(paramFloat4, paramFloat3);
        paramAxisData2.setStyle(Paint.Style.FILL);
        paramCanvas.drawPath(paramAxisData1, paramAxisData2);
        paramAxisData1.reset();
      }
    } while ((m == 0) && (k == 0) && (j == 0) && (i == 0));
    paramAxisData2 = getAnti();
    paramAxisData2.setStrokeWidth(paramSeriesData.series.bars.barWidth);
    paramAxisData2.setColor(getTranColor(paramSeriesData.series.color));
    paramAxisData2.setStyle(Paint.Style.STROKE);
    paramAxisData1.moveTo(paramFloat1, paramFloat3 + paramFloat6);
    if (m != 0)
    {
      paramAxisData1.lineTo(paramFloat1, paramFloat2 + paramFloat6);
      if (j == 0) {
        break label683;
      }
      paramAxisData1.lineTo(paramFloat4, paramFloat2 + paramFloat6);
      label629:
      if (k == 0) {
        break label697;
      }
      paramAxisData1.lineTo(paramFloat4, paramFloat3 + paramFloat6);
      label645:
      if (i == 0) {
        break label711;
      }
      paramAxisData1.lineTo(paramFloat1, paramFloat3 + paramFloat6);
    }
    for (;;)
    {
      paramCanvas.drawPath(paramAxisData1, paramAxisData2);
      return;
      paramAxisData1.moveTo(paramFloat1, paramFloat2 + paramFloat6);
      break;
      label683:
      paramAxisData1.moveTo(paramFloat4, paramFloat2 + paramFloat6);
      break label629;
      label697:
      paramAxisData1.moveTo(paramFloat4, paramFloat3 + paramFloat6);
      break label645;
      label711:
      paramAxisData1.moveTo(paramFloat1, paramFloat3 + paramFloat6);
    }
  }
  
  private void drawBarHighlight(SeriesData paramSeriesData, double[] paramArrayOfDouble, Canvas paramCanvas)
  {
    int i = paramSeriesData.series.color;
    paramSeriesData.series.color |= 0x80000000;
    if (paramSeriesData.series.bars.align.equals("left")) {}
    for (float f = 0.0F;; f = -paramSeriesData.series.bars.barWidth / 2.0F)
    {
      drawBar((float)paramArrayOfDouble[0], (float)paramArrayOfDouble[1], 0.0F, f, f + paramSeriesData.series.bars.barWidth, 0.0F, paramSeriesData.axes.xaxis, paramSeriesData.axes.yaxis, paramSeriesData, paramCanvas);
      paramSeriesData.series.color = i;
      return;
    }
  }
  
  private void drawCenteredString(String paramString, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, Canvas paramCanvas)
  {
    paramCanvas.drawText(paramString, paramFloat2 + (paramFloat3 - this.gridLabelPaint.measureText(paramString)) / 2.0F, paramFloat1 + 5.0F, this.gridLabelPaint);
  }
  
  private void drawGrid()
  {
    this.grap.save();
    this.grap.translate(this.plotOffset.left, this.plotOffset.top);
    if (this.options.grid.backgroundColor != null)
    {
      localObject1 = getColorOrGradient(this.options.grid.backgroundColor, this.plotHeight, 0.0D, 16777215);
      ((Paint)localObject1).setStyle(Paint.Style.FILL);
      drawRect(this.grap, 0.0F, 0.0F, this.plotWidth, this.plotHeight, (Paint)localObject1);
    }
    Object localObject1 = this.options.grid.markings;
    if (localObject1 != null) {
      localObject1 = ((Vector)localObject1).iterator();
    }
    Object localObject2;
    Object localObject3;
    int i;
    for (;;)
    {
      if (!((Iterator)localObject1).hasNext())
      {
        localObject1 = getAnti();
        ((Paint)localObject1).setColor(getTranColor(this.options.grid.tickColor));
        ((Paint)localObject1).setStyle(Paint.Style.STROKE);
        ((Paint)localObject1).setStrokeWidth(1.0F);
        localObject2 = new Path();
        localObject3 = this.axes.xaxis;
        if (((AxisData)localObject3).used.booleanValue())
        {
          i = 0;
          if (i < ((AxisData)localObject3).ticks.size()) {
            break;
          }
        }
        localObject3 = this.axes.yaxis;
        if (((AxisData)localObject3).used.booleanValue())
        {
          i = 0;
          if (i < ((AxisData)localObject3).ticks.size()) {
            break label1136;
          }
        }
        localObject3 = this.axes.x2axis;
        if (((AxisData)localObject3).used.booleanValue())
        {
          i = 0;
          if (i < ((AxisData)localObject3).ticks.size()) {
            break label1239;
          }
        }
        localObject3 = this.axes.y2axis;
        if (((AxisData)localObject3).used.booleanValue())
        {
          i = 0;
          if (i < ((AxisData)localObject3).ticks.size()) {
            break label1342;
          }
        }
        this.grap.drawPath((Path)localObject2, (Paint)localObject1);
        if (this.options.grid.borderWidth > 0)
        {
          localObject1 = getAnti();
          ((Paint)localObject1).setColor(getTranColor(this.options.grid.borderColor));
          ((Paint)localObject1).setStrokeWidth(this.options.grid.borderWidth);
          ((Paint)localObject1).setStyle(Paint.Style.STROKE);
          i = this.options.grid.borderWidth;
          drawRect(this.grap, -i / 2, -i / 2, this.plotWidth + i, this.plotHeight + i, (Paint)localObject1);
        }
        this.grap.restore();
        return;
      }
      localObject2 = (MarkingData)((Iterator)localObject1).next();
      localObject3 = ((MarkingData)localObject2).xaxis;
      RangeData localRangeData = ((MarkingData)localObject2).yaxis;
      double d4 = 0.0D;
      double d3 = 0.0D;
      double d5 = 0.0D;
      double d6 = 0.0D;
      double d2;
      if (localObject3 != null)
      {
        ((RangeData)localObject3).axis = this.axes.xaxis;
        d2 = ((RangeData)localObject3).from;
        d3 = ((RangeData)localObject3).to;
        d1 = d2;
        if (Double.isNaN(d2)) {
          d1 = ((RangeData)localObject3).axis.min;
        }
        d2 = d3;
        if (Double.isNaN(d3)) {
          d2 = ((RangeData)localObject3).axis.max;
        }
        Log.d("FlotDraw", "line" + ((RangeData)localObject3).from + ((RangeData)localObject3).to);
        d3 = d2;
        d4 = d1;
      }
      d1 = d6;
      if (localRangeData != null)
      {
        localRangeData.axis = this.axes.yaxis;
        d1 = localRangeData.from;
        d6 = localRangeData.to;
        d2 = d1;
        if (Double.isNaN(d1)) {
          d2 = localRangeData.axis.min;
        }
        d5 = d2;
        d1 = d6;
        if (Double.isNaN(d6))
        {
          d1 = localRangeData.axis.max;
          d5 = d2;
        }
      }
      if ((d3 >= ((RangeData)localObject3).axis.min) && (d4 <= ((RangeData)localObject3).axis.max) && (d1 >= localRangeData.axis.min) && (d5 <= localRangeData.axis.max))
      {
        d2 = Math.max(d4, ((RangeData)localObject3).axis.min);
        d3 = Math.min(d3, ((RangeData)localObject3).axis.max);
        d4 = Math.max(d5, localRangeData.axis.min);
        d1 = Math.min(d1, localRangeData.axis.max);
        if ((d2 != d3) || (d4 != d1))
        {
          d2 = ((RangeData)localObject3).axis.p2c.format(d2);
          d3 = ((RangeData)localObject3).axis.p2c.format(d3);
          d4 = localRangeData.axis.p2c.format(d4);
          d1 = localRangeData.axis.p2c.format(d1);
          localObject3 = getAnti();
          if (((MarkingData)localObject2).color == 0)
          {
            ((Paint)localObject3).setColor(getTranColor(this.options.grid.markingsColor));
            label904:
            if ((d2 != d3) && (d4 != d1)) {
              break label1003;
            }
            if (((MarkingData)localObject2).lineWidth > 0) {
              break label989;
            }
            ((Paint)localObject3).setStrokeWidth(this.options.grid.markingsLineWidth);
          }
          for (;;)
          {
            ((Paint)localObject3).setStyle(Paint.Style.STROKE);
            this.grap.drawLine((float)d2, (float)d4, (float)d3, (float)d1, (Paint)localObject3);
            break;
            ((Paint)localObject3).setColor(getTranColor(((MarkingData)localObject2).color));
            break label904;
            label989:
            ((Paint)localObject3).setStrokeWidth(((MarkingData)localObject2).lineWidth);
          }
          label1003:
          ((Paint)localObject3).setStyle(Paint.Style.FILL);
          this.grap.drawRect((float)d2, (float)d1, (float)d3, (float)d4, (Paint)localObject3);
        }
      }
    }
    double d1 = ((TickData)((AxisData)localObject3).ticks.get(i)).v;
    if ((d1 < ((AxisData)localObject3).datamin) || (d1 > ((AxisData)localObject3).datamax)) {}
    for (;;)
    {
      i += 1;
      break;
      ((Path)localObject2).moveTo((float)(Math.floor(((AxisData)localObject3).p2c.format(d1)) + 0.5D), 0.0F);
      ((Path)localObject2).lineTo((float)(Math.floor(((AxisData)localObject3).p2c.format(d1)) + 0.5D), this.plotHeight);
    }
    label1136:
    d1 = ((TickData)((AxisData)localObject3).ticks.get(i)).v;
    if ((d1 < ((AxisData)localObject3).datamin) || (d1 > ((AxisData)localObject3).datamax)) {}
    for (;;)
    {
      i += 1;
      break;
      ((Path)localObject2).moveTo(0.0F, (float)(Math.floor(((AxisData)localObject3).p2c.format(d1)) + 0.5D));
      ((Path)localObject2).lineTo(this.plotWidth, (float)(Math.floor(((AxisData)localObject3).p2c.format(d1)) + 0.5D));
    }
    label1239:
    d1 = ((TickData)((AxisData)localObject3).ticks.get(i)).v;
    if ((d1 < ((AxisData)localObject3).datamin) || (d1 > ((AxisData)localObject3).datamax)) {}
    for (;;)
    {
      i += 1;
      break;
      ((Path)localObject2).moveTo((float)(Math.floor(((AxisData)localObject3).p2c.format(d1)) + 0.5D), -5.0F);
      ((Path)localObject2).lineTo((float)(Math.floor(((AxisData)localObject3).p2c.format(d1)) + 0.5D), 5.0F);
    }
    label1342:
    d1 = ((TickData)((AxisData)localObject3).ticks.get(i)).v;
    if ((d1 < ((AxisData)localObject3).datamin) || (d1 > ((AxisData)localObject3).datamax)) {}
    for (;;)
    {
      i += 1;
      break;
      ((Path)localObject2).moveTo(this.plotWidth - 5, (float)(Math.floor(((AxisData)localObject3).p2c.format(d1)) + 0.5D));
      ((Path)localObject2).lineTo(this.plotWidth + 5, (float)(Math.floor(((AxisData)localObject3).p2c.format(d1)) + 0.5D));
    }
  }
  
  private void drawPointHighlight(SeriesData paramSeriesData, double[] paramArrayOfDouble, Canvas paramCanvas)
  {
    if ((paramSeriesData == null) || (paramArrayOfDouble == null) || (paramArrayOfDouble.length < 2)) {}
    double d1;
    double d2;
    AxisData localAxisData;
    do
    {
      return;
      d1 = paramArrayOfDouble[0];
      d2 = paramArrayOfDouble[1];
      paramArrayOfDouble = paramSeriesData.axes.xaxis;
      localAxisData = paramSeriesData.axes.yaxis;
    } while ((d1 < paramArrayOfDouble.min) || (d1 > paramArrayOfDouble.max) || (d2 < localAxisData.min) || (d2 > localAxisData.max));
    int i = paramSeriesData.series.points.radius + paramSeriesData.series.points.lineWidth / 2;
    Paint localPaint = getAnti();
    localPaint.setStrokeWidth(i);
    localPaint.setColor(paramSeriesData.series.color | 0x80000000);
    float f = 1.5F * i;
    paramCanvas.drawArc(getRectF((int)(paramArrayOfDouble.p2c.format(d1) - f), (int)(localAxisData.p2c.format(d2) - f), (int)(2.0F * f), (int)(2.0F * f)), 0.0F, 360.0F, false, localPaint);
  }
  
  private void drawRect(Canvas paramCanvas, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, Paint paramPaint)
  {
    paramCanvas.drawRect(paramFloat1, paramFloat2, paramFloat1 + paramFloat3, paramFloat2 + paramFloat4, paramPaint);
  }
  
  private void drawRightString(String paramString, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    float f1 = paramInt2;
    float f2 = paramInt3;
    float f3 = this.gridLabelPaint.measureText(paramString);
    float f4 = paramInt4 / 2 + paramInt1;
    this.grap.drawText(paramString, f1 + (f2 - f3), f4, this.gridLabelPaint);
  }
  
  private void drawSeries(SeriesData paramSeriesData)
  {
    if (paramSeriesData.series.lines.getShow().booleanValue()) {
      drawSeriesLines(paramSeriesData);
    }
    if (paramSeriesData.series.bars.show.booleanValue()) {
      drawSeriesBars(paramSeriesData);
    }
    if (paramSeriesData.series.points.show.booleanValue()) {
      drawSeriesPoints(paramSeriesData);
    }
  }
  
  private void drawSeriesBars(SeriesData paramSeriesData)
  {
    this.grap.save();
    this.grap.translate(this.plotOffset.left, this.plotOffset.top);
    if (paramSeriesData.series.bars.align.equals("left")) {}
    for (float f = 0.0F;; f = -paramSeriesData.series.bars.barWidth / 2.0F)
    {
      plotBars(paramSeriesData, paramSeriesData.datapoints, f, f + paramSeriesData.series.bars.barWidth, 0.0F, paramSeriesData.axes.xaxis, paramSeriesData.axes.yaxis);
      this.grap.restore();
      return;
    }
  }
  
  private void drawSeriesLines(SeriesData paramSeriesData)
  {
    this.grap.save();
    this.grap.translate(this.plotOffset.left, this.plotOffset.top);
    int i = paramSeriesData.series.lines.lineWidth;
    int j = paramSeriesData.series.shadowSize;
    if ((i > 0) && (j > 0))
    {
      localPaint = getAnti();
      localPaint.setColor(419430400);
      localPaint.setStrokeWidth(paramSeriesData.series.lines.lineWidth);
      localPaint.setStyle(Paint.Style.STROKE);
      plotLine(paramSeriesData.datapoints, Math.sin(0.17453292519943295D) * (i / 2 + j / 2), Math.cos(0.17453292519943295D) * (i / 2 + j / 2), paramSeriesData.axes.xaxis, paramSeriesData.axes.yaxis, localPaint);
      localPaint.setStrokeWidth(i / 2);
      plotLine(paramSeriesData.datapoints, Math.sin(0.17453292519943295D) * (i / 2 + j / 4), Math.cos(0.17453292519943295D) * (i / 2 + j / 4), paramSeriesData.axes.xaxis, paramSeriesData.axes.yaxis, localPaint);
    }
    Paint localPaint = getFillStyle(this.options.series.lines.fill.booleanValue(), this.options.series.lines.fillColor, paramSeriesData.series.color, 0.0D, this.plotHeight);
    if ((this.options.series.lines.fill.booleanValue()) && (localPaint != null))
    {
      localPaint.setStyle(Paint.Style.FILL);
      plotLineArea(paramSeriesData.datapoints, paramSeriesData.axes.xaxis, paramSeriesData.axes.yaxis, localPaint);
    }
    if (i > 0)
    {
      localPaint = getAnti();
      localPaint.setColor(getTranColor(paramSeriesData.series.color));
      localPaint.setStrokeWidth(paramSeriesData.series.lines.lineWidth);
      localPaint.setStyle(Paint.Style.STROKE);
      plotLine(paramSeriesData.datapoints, 0.0D, 0.0D, paramSeriesData.axes.xaxis, paramSeriesData.axes.yaxis, localPaint);
    }
    this.grap.restore();
  }
  
  private void drawSeriesPoints(SeriesData paramSeriesData)
  {
    this.grap.save();
    this.grap.translate(this.plotOffset.left, this.plotOffset.top);
    int i = paramSeriesData.series.lines.lineWidth;
    int k = paramSeriesData.series.shadowSize;
    int j = paramSeriesData.series.points.radius;
    if ((i > 0) && (k > 0))
    {
      k /= 2;
      localPaint = getAnti();
      localPaint.setStrokeWidth(k);
      localPaint.setColor(419430400);
      localPaint.setStyle(Paint.Style.STROKE);
      plotPoints(false, paramSeriesData, paramSeriesData.datapoints, j, k + k / 2.0F, 180.0F, paramSeriesData.axes.xaxis, paramSeriesData.axes.yaxis, localPaint);
      localPaint.setColor(838860800);
      plotPoints(false, paramSeriesData, paramSeriesData.datapoints, j, k / 2.0F, 180.0F, paramSeriesData.axes.xaxis, paramSeriesData.axes.yaxis, localPaint);
    }
    Paint localPaint = getAnti();
    localPaint.setStrokeWidth(i);
    localPaint.setColor(getTranColor(paramSeriesData.series.color));
    localPaint.setStyle(Paint.Style.STROKE);
    plotPoints(true, paramSeriesData, paramSeriesData.datapoints, j, 0.0F, 360.0F, paramSeriesData.axes.xaxis, paramSeriesData.axes.yaxis, localPaint);
    this.grap.restore();
  }
  
  private boolean equals(double paramDouble1, double paramDouble2)
  {
    if (paramDouble1 == paramDouble2) {}
    while (Math.abs(paramDouble1 - paramDouble2) < 1.0E-5D) {
      return true;
    }
    return false;
  }
  
  private void executeHighlight(String paramString, MotionEvent paramMotionEvent, boolean paramBoolean)
  {
    double d1 = paramMotionEvent.getX() - this.plotOffset.left;
    double d2 = paramMotionEvent.getY() - this.plotOffset.top;
    long l = System.currentTimeMillis();
    paramMotionEvent = findNearbyItem(d1, d2, paramBoolean);
    Log.d("findNearbyItem", System.currentTimeMillis() - l);
    int i;
    int j;
    if (this.options.grid.autoHighlight.booleanValue())
    {
      i = 0;
      j = 0;
    }
    for (;;)
    {
      if (j >= this.highlights.size())
      {
        if (paramMotionEvent != null)
        {
          highlight(paramMotionEvent.series, paramMotionEvent.datapoint, paramString, paramMotionEvent.dataIndex);
          i = 1;
        }
        if (i != 0) {
          redraw();
        }
        return;
      }
      HighlightData localHighlightData = (HighlightData)this.highlights.get(j);
      int k = i;
      if (localHighlightData != null)
      {
        k = i;
        if (localHighlightData.auto.equals(paramString)) {
          if ((paramMotionEvent != null) && (localHighlightData.series == paramMotionEvent.series))
          {
            k = i;
            if (localHighlightData.point == paramMotionEvent.datapoint) {}
          }
          else
          {
            unhighlight(localHighlightData.series, localHighlightData.point);
            k = 1;
          }
        }
      }
      j += 1;
      i = k;
    }
  }
  
  private Paint getAnti()
  {
    Paint localPaint = new Paint();
    localPaint.setAntiAlias(true);
    return localPaint;
  }
  
  private Paint getColorOrGradient(Object paramObject, double paramDouble1, double paramDouble2, int paramInt)
  {
    Paint localPaint = getAnti();
    if ((paramObject instanceof Integer))
    {
      localPaint.setColor(getTranColor(((Integer)paramObject).intValue()));
      return localPaint;
    }
    float[] arrayOfFloat;
    int[] arrayOfInt;
    int i;
    if ((paramObject instanceof int[]))
    {
      paramObject = (int[])paramObject;
      arrayOfFloat = new float[paramObject.length];
      arrayOfInt = new int[paramObject.length];
      i = 0;
    }
    for (;;)
    {
      if (i >= paramObject.length)
      {
        localPaint.setShader(new LinearGradient(0.0F, (float)paramDouble2, 0.0F, (float)paramDouble1, arrayOfInt, arrayOfFloat, Shader.TileMode.MIRROR));
        localPaint.setColor(getTranColor(paramInt));
        return localPaint;
      }
      arrayOfFloat[i] = (i / (paramObject.length - 1.0F));
      arrayOfInt[i] = getTranColor(paramObject[i]);
      i += 1;
    }
  }
  
  private Paint getFillStyle(boolean paramBoolean, Object paramObject, int paramInt, double paramDouble1, double paramDouble2)
  {
    if (!paramBoolean) {
      return null;
    }
    if (paramObject != null) {
      return getColorOrGradient(paramObject, paramDouble1, paramDouble2, paramInt);
    }
    paramObject = getAnti();
    ((Paint)paramObject).setColor(0x66000000 | paramInt);
    return (Paint)paramObject;
  }
  
  private RectF getRectF(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4)
  {
    return new RectF(paramFloat1, paramFloat2, paramFloat1 + paramFloat3, paramFloat2 + paramFloat4);
  }
  
  private int getTranColor(int paramInt)
  {
    if (paramInt >> 24 != 0) {
      return paramInt;
    }
    return paramInt | 0xFF000000;
  }
  
  private void initPlugins(IPlugin[] paramArrayOfIPlugin)
  {
    int i;
    if ((paramArrayOfIPlugin != null) && (paramArrayOfIPlugin.length > 0)) {
      i = 0;
    }
    for (;;)
    {
      if (i >= paramArrayOfIPlugin.length) {
        return;
      }
      paramArrayOfIPlugin[i].init(this);
      i += 1;
    }
  }
  
  private void insertLabels()
  {
    int i = this.options.grid.labelMargin;
    int j = this.options.grid.borderWidth;
    if (this.axes.xaxis.used.booleanValue()) {
      addLabels(this.axes.xaxis, new DrawFormatter()
      {
        public void draw(AxisData paramAnonymousAxisData, TickData paramAnonymousTickData)
        {
          FlotDraw.this.drawCenteredString(paramAnonymousTickData.label, FlotDraw.this.plotOffset.top + FlotDraw.this.plotHeight + this.val$margin, (int)Math.round(FlotDraw.this.plotOffset.left + paramAnonymousAxisData.p2c.format(paramAnonymousTickData.v) - paramAnonymousAxisData.labelWidth / 2.0D), (int)paramAnonymousAxisData.labelWidth, (int)paramAnonymousAxisData.labelHeight, FlotDraw.this.grap);
        }
      });
    }
    if (this.axes.yaxis.used.booleanValue()) {
      addLabels(this.axes.yaxis, new DrawFormatter()
      {
        public void draw(AxisData paramAnonymousAxisData, TickData paramAnonymousTickData)
        {
          FlotDraw.this.drawRightString(paramAnonymousTickData.label, (int)Math.round(FlotDraw.this.plotOffset.top + paramAnonymousAxisData.p2c.format(paramAnonymousTickData.v) - paramAnonymousAxisData.labelHeight / 2.0D), 0, (int)paramAnonymousAxisData.labelWidth, (int)paramAnonymousAxisData.labelHeight);
        }
      });
    }
  }
  
  private void insertLegend()
  {
    if (!this.options.legend.show.booleanValue()) {}
    float f1;
    int j;
    do
    {
      return;
      f1 = 0.0F;
      i = 0;
      j = 0;
      if (j < this.series.size()) {
        break;
      }
    } while (i == 0);
    float f2 = f1 + 20.0F;
    label70:
    int k;
    if (this.legendLabelPaint.getTextSize() > 16.0F)
    {
      f1 = this.legendLabelPaint.getTextSize();
      f1 += 4.0F;
      j = Math.min(i, this.options.legend.noColumns);
      k = i / this.options.legend.noColumns;
      if (i % this.options.legend.noColumns != 0) {
        break label456;
      }
    }
    Object localObject1;
    Object localObject3;
    Object localObject2;
    label456:
    for (int i = 0;; i = 1)
    {
      i = k + i;
      this.grap.save();
      this.grap.translate(this.plotOffset.left + this.plotWidth - j * f2 - 10.0F, this.plotOffset.top + 10);
      localObject1 = getFillStyle(true, this.options.legend.backgroundColor, -1, i * f1, 0.0D);
      if (localObject1 != null)
      {
        ((Paint)localObject1).setStyle(Paint.Style.FILL);
        drawRect(this.grap, 0.0F, 0.0F, j * f2, f1 * i, (Paint)localObject1);
      }
      localObject3 = getAnti();
      ((Paint)localObject3).setColor(getTranColor(this.options.legend.labelBoxBorderColor));
      ((Paint)localObject3).setStrokeWidth(1.0F);
      ((Paint)localObject3).setStyle(Paint.Style.STROKE);
      i = 0;
      if (i < this.series.size()) {
        break label462;
      }
      this.grap.restore();
      return;
      localObject3 = (SeriesData)this.series.get(j);
      if (localObject3 == null)
      {
        f2 = f1;
        k = i;
      }
      for (;;)
      {
        j += 1;
        i = k;
        f1 = f2;
        break;
        localObject2 = ((SeriesData)localObject3).label;
        localObject1 = localObject2;
        if (this.options.legend.labelFormatter != null) {
          localObject1 = this.options.legend.labelFormatter.format((String)localObject2, (SeriesData)localObject3);
        }
        k = i;
        f2 = f1;
        if (localObject1 != null)
        {
          k = i;
          f2 = f1;
          if (((String)localObject1).length() != 0)
          {
            k = (int)this.legendLabelPaint.measureText((String)localObject1);
            f2 = f1;
            if (k > f1) {
              f2 = k;
            }
            k = i + 1;
          }
        }
      }
      f1 = 16.0F;
      break label70;
    }
    label462:
    SeriesData localSeriesData = (SeriesData)this.series.get(i);
    if (localSeriesData == null) {}
    for (;;)
    {
      i += 1;
      break;
      localObject2 = localSeriesData.label;
      localObject1 = localObject2;
      if (this.options.legend.labelFormatter != null) {
        localObject1 = this.options.legend.labelFormatter.format((String)localObject2, localSeriesData);
      }
      if ((localObject1 != null) && (((String)localObject1).length() != 0))
      {
        localObject2 = this.grap;
        float f3 = i % j;
        float f4 = (f1 - 10.0F) / 2.0F;
        drawRect((Canvas)localObject2, f2 * f3, i / j * f1 + f4, 14.0F, 10.0F, (Paint)localObject3);
        localObject2 = getAnti();
        ((Paint)localObject2).setColor(getTranColor(localSeriesData.series.color));
        ((Paint)localObject2).setStyle(Paint.Style.FILL);
        drawRect(this.grap, 2.0F + i % j * f2, (f1 - 10.0F) / 2.0F + i / j * f1 + 2.0F, 10.0F, 6.0F, (Paint)localObject2);
        this.grap.drawText((String)localObject1, 18.0F + i % j * f2, f1 / 2.0F + 5.0F + i / j * f1, this.legendLabelPaint);
      }
    }
  }
  
  private void measureLabels(AxisData paramAxisData, AxisOption paramAxisOption)
  {
    int m = 1;
    paramAxisData.labelWidth = paramAxisOption.labelWidth;
    paramAxisData.labelHeight = paramAxisOption.labelHeight;
    if ((paramAxisData == this.axes.xaxis) || (paramAxisData == this.axes.x2axis)) {
      if (paramAxisData.labelWidth == -1.0D)
      {
        j = this.canvasWidth;
        if (paramAxisData.ticks.size() > 0)
        {
          i = paramAxisData.ticks.size();
          paramAxisData.labelWidth = (j / i);
        }
      }
      else if (paramAxisData.labelHeight == -1.0D)
      {
        paramAxisData.labelHeight = this.gridLabelPaint.getTextSize();
      }
    }
    do
    {
      do
      {
        return;
        i = 1;
        break;
      } while ((paramAxisData.labelWidth != -1.0D) && (paramAxisData.labelHeight != -1.0D));
      j = 0;
      i = 0;
      if (i < paramAxisData.ticks.size()) {
        break label220;
      }
      if (paramAxisData.labelWidth == -1.0D) {
        paramAxisData.labelWidth = j;
      }
    } while (paramAxisData.labelHeight != -1.0D);
    int j = this.canvasHeight;
    int i = m;
    if (paramAxisData.ticks.size() > 0) {
      i = paramAxisData.ticks.size();
    }
    paramAxisData.labelHeight = (j / i);
    return;
    label220:
    int k;
    if (paramAxisData.ticks.get(i) == null) {
      k = j;
    }
    for (;;)
    {
      i += 1;
      j = k;
      break;
      int n = (int)this.gridLabelPaint.measureText(((TickData)paramAxisData.ticks.get(i)).label);
      k = j;
      if (n > j) {
        k = n;
      }
    }
  }
  
  private void plotBars(SeriesData paramSeriesData, Datapoints paramDatapoints, float paramFloat1, float paramFloat2, float paramFloat3, AxisData paramAxisData1, AxisData paramAxisData2)
  {
    ArrayList localArrayList = paramDatapoints.points;
    int j = paramDatapoints.pointsize;
    int i = 0;
    if (i >= localArrayList.size()) {
      return;
    }
    if ((Double.isNaN(((Double)localArrayList.get(i)).doubleValue())) || (localArrayList.get(i) == null) || (localArrayList.get(i + 1) == null) || (localArrayList.get(i + 2) == null)) {}
    for (;;)
    {
      i += j;
      break;
      drawBar(((Double)localArrayList.get(i)).floatValue(), ((Double)localArrayList.get(i + 1)).floatValue(), ((Double)localArrayList.get(i + 2)).floatValue(), paramFloat1, paramFloat2, paramFloat3, paramAxisData1, paramAxisData2, paramSeriesData, this.grap);
    }
  }
  
  private void plotLine(Datapoints paramDatapoints, double paramDouble1, double paramDouble2, AxisData paramAxisData1, AxisData paramAxisData2, Paint paramPaint)
  {
    Path localPath = new Path();
    ArrayList localArrayList = paramDatapoints.points;
    int j = paramDatapoints.pointsize;
    double d10 = Double.MIN_VALUE;
    double d9 = Double.MIN_VALUE;
    int i = j;
    if (i >= localArrayList.size())
    {
      this.grap.drawPath(localPath, paramPaint);
      return;
    }
    double d11 = d10;
    double d12 = d9;
    if (localArrayList.get(i - j) != null)
    {
      d11 = d10;
      d12 = d9;
      if (localArrayList.get(i) != null)
      {
        d11 = d10;
        d12 = d9;
        if (localArrayList.get(i - j + 1) != null)
        {
          if (localArrayList.get(i + 1) != null) {
            break label157;
          }
          d12 = d9;
          d11 = d10;
        }
      }
    }
    label157:
    double d1;
    double d3;
    double d4;
    double d2;
    label337:
    label415:
    label493:
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                i += j;
                d10 = d11;
                d9 = d12;
                break;
                d5 = ((Double)localArrayList.get(i - j)).doubleValue();
                d6 = ((Double)localArrayList.get(i - j + 1)).doubleValue();
                d7 = ((Double)localArrayList.get(i)).doubleValue();
                d8 = ((Double)localArrayList.get(i + 1)).doubleValue();
                d11 = d10;
                d12 = d9;
              } while (Double.isNaN(d5));
              d11 = d10;
              d12 = d9;
            } while (Double.isNaN(d7));
            if ((d6 > d8) || (d6 >= paramAxisData2.min)) {
              break label670;
            }
            d11 = d10;
            d12 = d9;
          } while (d8 < paramAxisData2.min);
          d1 = d5 + (paramAxisData2.min - d6) / (d8 - d6) * (d7 - d5);
          d3 = paramAxisData2.min;
          d4 = d8;
          d2 = d7;
          if ((d3 < d4) || (d3 <= paramAxisData2.max)) {
            break label783;
          }
          d11 = d10;
          d12 = d9;
        } while (d4 > paramAxisData2.max);
        d5 = d1 + (paramAxisData2.max - d3) / (d4 - d3) * (d2 - d1);
        d7 = paramAxisData2.max;
        d8 = d4;
        d6 = d2;
        if ((d5 > d6) || (d5 >= paramAxisData1.min)) {
          break label896;
        }
        d11 = d10;
        d12 = d9;
      } while (d6 < paramAxisData1.min);
      d3 = d7 + (paramAxisData1.min - d5) / (d6 - d5) * (d8 - d7);
      d1 = paramAxisData1.min;
      d4 = d8;
      d2 = d6;
      if ((d1 < d2) || (d1 <= paramAxisData1.max)) {
        break label1009;
      }
      d11 = d10;
      d12 = d9;
    } while (d2 > paramAxisData1.max);
    double d8 = d3 + (paramAxisData1.max - d1) / (d2 - d1) * (d4 - d3);
    double d7 = paramAxisData1.max;
    double d6 = d4;
    double d5 = d2;
    for (;;)
    {
      if ((d7 != d10) || (d8 != d9)) {
        localPath.moveTo((float)(paramAxisData1.p2c.format(d7) + paramDouble1), (float)(paramAxisData2.p2c.format(d8) + paramDouble2));
      }
      d11 = d5;
      d12 = d6;
      localPath.lineTo((float)(paramAxisData1.p2c.format(d5) + paramDouble1), (float)(paramAxisData2.p2c.format(d6) + paramDouble2));
      break;
      label670:
      d1 = d5;
      d2 = d7;
      d3 = d6;
      d4 = d8;
      if (d8 > d6) {
        break label337;
      }
      d1 = d5;
      d2 = d7;
      d3 = d6;
      d4 = d8;
      if (d8 >= paramAxisData2.min) {
        break label337;
      }
      d11 = d10;
      d12 = d9;
      if (d6 < paramAxisData2.min) {
        break;
      }
      d2 = (paramAxisData2.min - d6) / (d8 - d6) * (d7 - d5) + d5;
      d4 = paramAxisData2.min;
      d1 = d5;
      d3 = d6;
      break label337;
      label783:
      d5 = d1;
      d6 = d2;
      d7 = d3;
      d8 = d4;
      if (d4 < d3) {
        break label415;
      }
      d5 = d1;
      d6 = d2;
      d7 = d3;
      d8 = d4;
      if (d4 <= paramAxisData2.max) {
        break label415;
      }
      d11 = d10;
      d12 = d9;
      if (d3 > paramAxisData2.max) {
        break;
      }
      d6 = (paramAxisData2.max - d3) / (d4 - d3) * (d2 - d1) + d1;
      d8 = paramAxisData2.max;
      d5 = d1;
      d7 = d3;
      break label415;
      label896:
      d1 = d5;
      d2 = d6;
      d3 = d7;
      d4 = d8;
      if (d6 > d5) {
        break label493;
      }
      d1 = d5;
      d2 = d6;
      d3 = d7;
      d4 = d8;
      if (d6 >= paramAxisData1.min) {
        break label493;
      }
      d11 = d10;
      d12 = d9;
      if (d5 < paramAxisData1.min) {
        break;
      }
      d4 = (paramAxisData1.min - d5) / (d6 - d5) * (d8 - d7) + d7;
      d2 = paramAxisData1.min;
      d1 = d5;
      d3 = d7;
      break label493;
      label1009:
      d7 = d1;
      d5 = d2;
      d8 = d3;
      d6 = d4;
      if (d2 >= d1)
      {
        d7 = d1;
        d5 = d2;
        d8 = d3;
        d6 = d4;
        if (d2 > paramAxisData1.max)
        {
          d11 = d10;
          d12 = d9;
          if (d1 > paramAxisData1.max) {
            break;
          }
          d6 = (paramAxisData1.max - d1) / (d2 - d1) * (d4 - d3) + d3;
          d5 = paramAxisData1.max;
          d7 = d1;
          d8 = d3;
        }
      }
    }
  }
  
  private void plotLineArea(Datapoints paramDatapoints, AxisData paramAxisData1, AxisData paramAxisData2, Paint paramPaint)
  {
    ArrayList localArrayList = paramDatapoints.points;
    int m = paramDatapoints.pointsize;
    double d11 = Math.min(Math.max(0.0D, paramAxisData2.min), paramAxisData2.max);
    double d9 = 0.0D;
    int i = 0;
    paramDatapoints = new Path();
    int k = m;
    if (k >= localArrayList.size())
    {
      if (i != 0)
      {
        paramDatapoints.lineTo((float)paramAxisData1.p2c.format(d9), (float)paramAxisData2.p2c.format(d11));
        this.grap.drawPath(paramDatapoints, paramPaint);
      }
      return;
    }
    int j = i;
    double d1 = d9;
    if (localArrayList.get(k - m) != null)
    {
      j = i;
      d1 = d9;
      if (localArrayList.get(k - m + 1) != null)
      {
        j = i;
        d1 = d9;
        if (localArrayList.get(k) != null)
        {
          if (localArrayList.get(k + 1) != null) {
            break label201;
          }
          d1 = d9;
          j = i;
        }
      }
    }
    label201:
    double d7;
    double d8;
    double d10;
    double d5;
    double d3;
    double d6;
    double d4;
    for (;;)
    {
      k += m;
      i = j;
      d9 = d1;
      break;
      d2 = ((Double)localArrayList.get(k - m)).doubleValue();
      d7 = ((Double)localArrayList.get(k - m + 1)).doubleValue();
      d8 = ((Double)localArrayList.get(k)).doubleValue();
      d10 = ((Double)localArrayList.get(k + 1)).doubleValue();
      if ((i != 0) && (!Double.isNaN(d2)) && (Double.isNaN(d8)))
      {
        paramDatapoints.lineTo((float)paramAxisData1.p2c.format(d9), (float)paramAxisData2.p2c.format(d11));
        paramDatapoints.close();
        this.grap.drawPath(paramDatapoints, paramPaint);
        paramDatapoints.reset();
        j = 0;
        d1 = d9;
      }
      else
      {
        j = i;
        d1 = d9;
        if (!Double.isNaN(d2))
        {
          j = i;
          d1 = d9;
          if (!Double.isNaN(d8))
          {
            if ((d2 <= d8) && (d2 < paramAxisData1.min))
            {
              j = i;
              d1 = d9;
              if (d8 < paramAxisData1.min) {
                continue;
              }
              d5 = d7 + (paramAxisData1.min - d2) / (d8 - d2) * (d10 - d7);
              d3 = paramAxisData1.min;
              d6 = d10;
              d4 = d8;
              label454:
              if ((d3 < d4) || (d3 <= paramAxisData1.max)) {
                break label760;
              }
              j = i;
              d1 = d9;
              if (d4 > paramAxisData1.max) {
                continue;
              }
              d7 = d5 + (paramAxisData1.max - d3) / (d4 - d3) * (d6 - d5);
              d2 = paramAxisData1.max;
              d8 = d6;
              d1 = d4;
            }
            for (;;)
            {
              j = i;
              if (i == 0)
              {
                paramDatapoints.moveTo((float)paramAxisData1.p2c.format(d2), (float)paramAxisData2.p2c.format(d11));
                j = 1;
              }
              if ((d7 < paramAxisData2.max) || (d8 < paramAxisData2.max)) {
                break label869;
              }
              paramDatapoints.lineTo((float)paramAxisData1.p2c.format(d2), (float)paramAxisData2.p2c.format(paramAxisData2.max));
              paramDatapoints.lineTo((float)paramAxisData1.p2c.format(d1), (float)paramAxisData2.p2c.format(paramAxisData2.max));
              break;
              d3 = d2;
              d4 = d8;
              d5 = d7;
              d6 = d10;
              if (d8 > d2) {
                break label454;
              }
              d3 = d2;
              d4 = d8;
              d5 = d7;
              d6 = d10;
              if (d8 >= paramAxisData1.min) {
                break label454;
              }
              j = i;
              d1 = d9;
              if (d2 < paramAxisData1.min) {
                break;
              }
              d6 = (paramAxisData1.min - d2) / (d8 - d2) * (d10 - d7) + d7;
              d4 = paramAxisData1.min;
              d3 = d2;
              d5 = d7;
              break label454;
              label760:
              d2 = d3;
              d1 = d4;
              d7 = d5;
              d8 = d6;
              if (d4 >= d3)
              {
                d2 = d3;
                d1 = d4;
                d7 = d5;
                d8 = d6;
                if (d4 > paramAxisData1.max)
                {
                  j = i;
                  d1 = d9;
                  if (d3 > paramAxisData1.max) {
                    break;
                  }
                  d8 = (paramAxisData1.max - d3) / (d4 - d3) * (d6 - d5) + d5;
                  d1 = paramAxisData1.max;
                  d2 = d3;
                  d7 = d5;
                }
              }
            }
            label869:
            if ((d7 > paramAxisData2.min) || (d8 > paramAxisData2.min)) {
              break label952;
            }
            paramDatapoints.lineTo((float)paramAxisData1.p2c.format(d2), (float)paramAxisData2.p2c.format(paramAxisData2.min));
            paramDatapoints.lineTo((float)paramAxisData1.p2c.format(d1), (float)paramAxisData2.p2c.format(paramAxisData2.min));
          }
        }
      }
    }
    label952:
    if ((d7 <= d8) && (d7 < paramAxisData2.min) && (d8 >= paramAxisData2.min))
    {
      d5 = d2 + (paramAxisData2.min - d7) / (d8 - d7) * (d1 - d2);
      d3 = paramAxisData2.min;
      d4 = d8;
      d6 = d1;
      label1018:
      d9 = d6;
      d10 = d5;
      if ((d3 < d4) || (d3 <= paramAxisData2.max) || (d4 > paramAxisData2.max)) {
        break label1437;
      }
      d6 = d10 + (paramAxisData2.max - d3) / (d4 - d3) * (d9 - d10);
      d7 = paramAxisData2.max;
      d8 = d4;
      d5 = d9;
      label1092:
      if (d6 != d2)
      {
        if (d7 > paramAxisData2.min) {
          break label1554;
        }
        d3 = paramAxisData2.min;
        label1116:
        paramDatapoints.lineTo((float)paramAxisData1.p2c.format(d2), (float)paramAxisData2.p2c.format(d3));
        paramDatapoints.lineTo((float)paramAxisData1.p2c.format(d6), (float)paramAxisData2.p2c.format(d3));
      }
      paramDatapoints.lineTo((float)paramAxisData1.p2c.format(d6), (float)paramAxisData2.p2c.format(d7));
      paramDatapoints.lineTo((float)paramAxisData1.p2c.format(d5), (float)paramAxisData2.p2c.format(d8));
      if (d5 != d1) {
        if (d8 > paramAxisData2.min) {
          break label1563;
        }
      }
    }
    label1437:
    label1554:
    label1563:
    for (double d2 = paramAxisData2.min;; d2 = paramAxisData2.max)
    {
      paramDatapoints.lineTo((float)paramAxisData1.p2c.format(d5), (float)paramAxisData2.p2c.format(d2));
      paramDatapoints.lineTo((float)paramAxisData1.p2c.format(d1), (float)paramAxisData2.p2c.format(d2));
      d1 = Math.max(d5, d1);
      break;
      d5 = d2;
      d6 = d1;
      d3 = d7;
      d4 = d8;
      if (d8 > d7) {
        break label1018;
      }
      d5 = d2;
      d6 = d1;
      d3 = d7;
      d4 = d8;
      if (d8 >= paramAxisData2.min) {
        break label1018;
      }
      d5 = d2;
      d6 = d1;
      d3 = d7;
      d4 = d8;
      if (d7 < paramAxisData2.min) {
        break label1018;
      }
      d6 = (paramAxisData2.min - d7) / (d8 - d7) * (d1 - d2) + d2;
      d4 = paramAxisData2.min;
      d5 = d2;
      d3 = d7;
      break label1018;
      d6 = d10;
      d5 = d9;
      d7 = d3;
      d8 = d4;
      if (d4 < d3) {
        break label1092;
      }
      d6 = d10;
      d5 = d9;
      d7 = d3;
      d8 = d4;
      if (d4 <= paramAxisData2.max) {
        break label1092;
      }
      d6 = d10;
      d5 = d9;
      d7 = d3;
      d8 = d4;
      if (d3 > paramAxisData2.max) {
        break label1092;
      }
      d5 = (paramAxisData2.max - d3) / (d4 - d3) * (d9 - d10) + d10;
      d8 = paramAxisData2.max;
      d6 = d10;
      d7 = d3;
      break label1092;
      d3 = paramAxisData2.max;
      break label1116;
    }
  }
  
  private void plotPoints(boolean paramBoolean, SeriesData paramSeriesData, Datapoints paramDatapoints, float paramFloat1, float paramFloat2, float paramFloat3, AxisData paramAxisData1, AxisData paramAxisData2, Paint paramPaint)
  {
    ArrayList localArrayList = paramDatapoints.points;
    int j = paramDatapoints.pointsize;
    int i = 0;
    if (i >= localArrayList.size()) {
      return;
    }
    if ((localArrayList.get(i) == null) || (localArrayList.get(i + 1) == null)) {}
    for (;;)
    {
      i += j;
      break;
      double d1 = ((Double)localArrayList.get(i)).doubleValue();
      double d2 = ((Double)localArrayList.get(i + 1)).doubleValue();
      if ((!Double.isNaN(d1)) && (d1 >= paramAxisData1.min) && (d1 <= paramAxisData1.max) && (d2 >= paramAxisData2.min) && (d2 <= paramAxisData2.max))
      {
        paramDatapoints = getFillStyle(paramSeriesData.series.points.fill.booleanValue(), paramSeriesData.series.points.fillColor, paramSeriesData.series.color, 0.0D, 0.0D);
        if ((paramBoolean) && (paramSeriesData.series.points.fill.booleanValue()) && (paramDatapoints != null))
        {
          paramDatapoints.setStyle(Paint.Style.FILL);
          this.grap.drawArc(getRectF((float)(paramAxisData1.p2c.format(d1) - paramFloat1), (float)(paramAxisData2.p2c.format(d2) + paramFloat2 - paramFloat1), 2.0F * paramFloat1, 2.0F * paramFloat1), 180.0F, paramFloat3, false, paramDatapoints);
          paramDatapoints = getAnti();
          paramDatapoints.setColor(getTranColor(paramSeriesData.series.color));
          paramDatapoints.setStrokeWidth(paramSeriesData.series.lines.lineWidth);
          paramDatapoints.setStyle(Paint.Style.STROKE);
          this.grap.drawArc(getRectF((float)(paramAxisData1.p2c.format(d1) - paramFloat1), (float)(paramAxisData2.p2c.format(d2) + paramFloat2 - paramFloat1), 2.0F * paramFloat1, 2.0F * paramFloat1), 180.0F, paramFloat3, false, paramDatapoints);
        }
        else
        {
          this.grap.drawArc(getRectF((float)(paramAxisData1.p2c.format(d1) - paramFloat1), (float)(paramAxisData2.p2c.format(d2) + paramFloat2 - paramFloat1), 2.0F * paramFloat1, 2.0F * paramFloat1), 180.0F, paramFloat3, false, paramPaint);
        }
      }
    }
  }
  
  private void prepareTickGeneration(AxisData paramAxisData, final AxisOption paramAxisOption)
  {
    double d1 = -1.0D;
    if ((paramAxisOption.ticks instanceof Integer)) {
      d1 = ((Integer)paramAxisOption.ticks).intValue();
    }
    double d2;
    label100:
    Object localObject1;
    if (d1 > 0.0D)
    {
      d2 = (paramAxisData.max - paramAxisData.min) / d1;
      if ((paramAxisOption.mode == null) || (!paramAxisOption.mode.equals("time"))) {
        break label568;
      }
      d1 = 0.0D;
      if (paramAxisOption.minTickSize != Double.MIN_VALUE) {
        d1 = paramAxisOption.minTickSize;
      }
      i = 0;
      if (i < this.spec.size() - 1) {
        break label341;
      }
      d1 = ((SpecData)this.spec.get(i)).i0;
      localObject1 = ((SpecData)this.spec.get(i)).i1;
      if (((String)localObject1).equals("year"))
      {
        d3 = Math.pow(10.0D, Math.floor(Math.log(d2 / ((Double)this.timeUnitSize.get("year")).doubleValue()) / 2.302585D));
        d1 = d2 / ((Double)this.timeUnitSize.get("year")).doubleValue() / d3;
        if (d1 >= 1.5D) {
          break label531;
        }
        d1 = 1.0D;
      }
    }
    Object localObject2;
    for (;;)
    {
      d1 *= d3;
      d2 = paramAxisOption.tickSize;
      paramAxisData.specSize = new SpecData(d1, (String)localObject1);
      localObject2 = new TickGenerator()
      {
        public Vector<TickData> generator(AxisData paramAnonymousAxisData)
        {
          Vector localVector = new Vector();
          double d1 = paramAnonymousAxisData.specSize.i0;
          String str = paramAnonymousAxisData.specSize.i1;
          Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
          localCalendar.setTimeInMillis(paramAnonymousAxisData.min);
          double d2 = d1 * ((Double)FlotDraw.this.timeUnitSize.get(str)).doubleValue();
          label139:
          long l1;
          label147:
          long l3;
          long l4;
          if (str.equals("second"))
          {
            localCalendar.set(13, (int)floorInBase(localCalendar.get(13), d1));
            localCalendar.set(14, 0);
            if (d2 < ((Double)FlotDraw.this.timeUnitSize.get("minute")).doubleValue()) {
              break label453;
            }
            localCalendar.set(13, 0);
            long l2 = 0L;
            l1 = -1L;
            l3 = l1;
            l4 = localCalendar.getTimeInMillis();
            localVector.add(new TickData(l4, paramAnonymousAxisData.tickFormatter.formatNumber(l4, paramAnonymousAxisData)));
            if (!str.equals("month")) {
              break label614;
            }
            if (d1 >= 1.0D) {
              break label595;
            }
            localCalendar.set(5, 1);
            l1 = localCalendar.getTimeInMillis();
            localCalendar.set(2, localCalendar.get(2) + 1);
            long l5 = localCalendar.getTimeInMillis();
            localCalendar.setTimeInMillis((l4 + l2 * ((Double)FlotDraw.this.timeUnitSize.get("hour")).doubleValue() + (l5 - l1) * d1));
            l2 = localCalendar.get(10);
            localCalendar.set(10, 0);
          }
          for (;;)
          {
            if (l4 < paramAnonymousAxisData.max)
            {
              l1 = l4;
              if (l4 != l3) {
                break label147;
              }
            }
            return localVector;
            if (str.equals("minute"))
            {
              localCalendar.set(12, (int)floorInBase(localCalendar.get(12), d1));
              break;
            }
            if (str.equals("hour"))
            {
              localCalendar.set(10, (int)floorInBase(localCalendar.get(10), d1));
              break;
            }
            if (str.equals("month"))
            {
              localCalendar.set(2, (int)floorInBase(localCalendar.get(2), d1));
              break;
            }
            if (!str.equals("year")) {
              break;
            }
            localCalendar.set(1, (int)floorInBase(localCalendar.get(1), d1));
            break;
            label453:
            if (d2 >= ((Double)FlotDraw.this.timeUnitSize.get("hour")).doubleValue())
            {
              localCalendar.set(12, 0);
              break label139;
            }
            if (d2 >= ((Double)FlotDraw.this.timeUnitSize.get("day")).doubleValue())
            {
              localCalendar.set(10, 0);
              break label139;
            }
            if (d2 >= ((Double)FlotDraw.this.timeUnitSize.get("day")).doubleValue() * 4.0D)
            {
              localCalendar.set(5, 1);
              break label139;
            }
            if (d2 < ((Double)FlotDraw.this.timeUnitSize.get("year")).doubleValue()) {
              break label139;
            }
            localCalendar.set(2, 0);
            break label139;
            label595:
            localCalendar.set(2, (int)(localCalendar.get(2) + d1));
            continue;
            label614:
            if (str == "year") {
              localCalendar.set(1, (int)(localCalendar.get(1) + d1));
            } else {
              localCalendar.setTimeInMillis((l4 + d2));
            }
          }
        }
      };
      localObject1 = new TickFormatter()
      {
        public String formatDate(Calendar paramAnonymousCalendar, String paramAnonymousString, String[] paramAnonymousArrayOfString)
        {
          Object localObject = "";
          int j = 0;
          int m = paramAnonymousCalendar.get(10);
          int k;
          String[] arrayOfString;
          int i;
          if (m < 12)
          {
            k = 1;
            arrayOfString = paramAnonymousArrayOfString;
            if (paramAnonymousArrayOfString == null)
            {
              arrayOfString = new String[12];
              arrayOfString[0] = "Jan";
              arrayOfString[1] = "Feb";
              arrayOfString[2] = "Mar";
              arrayOfString[3] = "Apr";
              arrayOfString[4] = "May";
              arrayOfString[5] = "Jun";
              arrayOfString[6] = "Jul";
              arrayOfString[7] = "Aug";
              arrayOfString[8] = "Sep";
              arrayOfString[9] = "Oct";
              arrayOfString[10] = "Nov";
              arrayOfString[11] = "Dec";
            }
            i = m;
            if (paramAnonymousString.matches(".*(%p|%P).*"))
            {
              if (m <= 12) {
                break label165;
              }
              i = m - 12;
            }
          }
          for (;;)
          {
            m = 0;
            if (m < paramAnonymousString.length()) {
              break label181;
            }
            return (String)localObject;
            k = 0;
            break;
            label165:
            i = m;
            if (m == 0) {
              i = 12;
            }
          }
          label181:
          char c = paramAnonymousString.charAt(m);
          paramAnonymousArrayOfString = "";
          if (j != 0) {
            switch (c)
            {
            default: 
              label288:
              paramAnonymousArrayOfString = localObject + paramAnonymousArrayOfString;
              j = 0;
            }
          }
          for (;;)
          {
            m += 1;
            localObject = paramAnonymousArrayOfString;
            break;
            paramAnonymousArrayOfString = i;
            break label288;
            paramAnonymousArrayOfString = leftPad(i);
            break label288;
            paramAnonymousArrayOfString = leftPad(paramAnonymousCalendar.get(12));
            break label288;
            paramAnonymousArrayOfString = leftPad(paramAnonymousCalendar.get(13));
            break label288;
            paramAnonymousArrayOfString = paramAnonymousCalendar.get(5);
            break label288;
            paramAnonymousArrayOfString = paramAnonymousCalendar.get(2) + 1;
            break label288;
            paramAnonymousArrayOfString = paramAnonymousCalendar.get(1);
            break label288;
            paramAnonymousArrayOfString = arrayOfString[paramAnonymousCalendar.get(2)];
            break label288;
            if (k != 0) {}
            for (paramAnonymousArrayOfString = "am";; paramAnonymousArrayOfString = "pm") {
              break;
            }
            if (k != 0) {}
            for (paramAnonymousArrayOfString = "AM";; paramAnonymousArrayOfString = "PM") {
              break;
            }
            if (c == '%')
            {
              j = 1;
              paramAnonymousArrayOfString = (String[])localObject;
            }
            else
            {
              paramAnonymousArrayOfString = localObject + c;
            }
          }
        }
        
        public String formatNumber(double paramAnonymousDouble, AxisData paramAnonymousAxisData)
        {
          Calendar localCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
          if (paramAnonymousAxisData.specSize != null)
          {
            localCalendar.setTimeInMillis(paramAnonymousDouble);
            if (paramAxisOption.timeformat != null) {
              return formatDate(localCalendar, paramAxisOption.timeformat, paramAxisOption.monthNames);
            }
            paramAnonymousDouble = paramAnonymousAxisData.specSize.i0 * ((Double)FlotDraw.this.timeUnitSize.get(paramAnonymousAxisData.specSize.i1)).doubleValue();
            double d = paramAnonymousAxisData.max - paramAnonymousAxisData.min;
            if (paramAxisOption.twelveHourClock.booleanValue())
            {
              paramAnonymousAxisData = "%p";
              if (paramAnonymousDouble >= ((Double)FlotDraw.this.timeUnitSize.get("minute")).doubleValue()) {
                break label175;
              }
              paramAnonymousAxisData = "%h:%M:%S" + paramAnonymousAxisData;
            }
            for (;;)
            {
              return formatDate(localCalendar, paramAnonymousAxisData, paramAxisOption.monthNames);
              paramAnonymousAxisData = "";
              break;
              label175:
              if (paramAnonymousDouble < ((Double)FlotDraw.this.timeUnitSize.get("day")).doubleValue())
              {
                if (d < 2.0D * ((Double)FlotDraw.this.timeUnitSize.get("day")).doubleValue()) {
                  paramAnonymousAxisData = "%h:%M" + paramAnonymousAxisData;
                } else {
                  paramAnonymousAxisData = "%b %d %h:%M" + paramAnonymousAxisData;
                }
              }
              else if (paramAnonymousDouble < ((Double)FlotDraw.this.timeUnitSize.get("month")).doubleValue()) {
                paramAnonymousAxisData = "%b %d";
              } else if (paramAnonymousDouble < ((Double)FlotDraw.this.timeUnitSize.get("year")).doubleValue())
              {
                if (d < ((Double)FlotDraw.this.timeUnitSize.get("year")).doubleValue()) {
                  paramAnonymousAxisData = "%b";
                } else {
                  paramAnonymousAxisData = "%b %y";
                }
              }
              else {
                paramAnonymousAxisData = "%y";
              }
            }
          }
          return "";
        }
        
        public String leftPad(int paramAnonymousInt)
        {
          String str2 = paramAnonymousInt;
          String str1 = str2;
          if (str2.length() == 1) {
            str1 = "0" + str2;
          }
          return str1;
        }
      };
      paramAxisData.tickSize = d1;
      paramAxisData.tickGenerator = ((TickGenerator)localObject2);
      if (paramAxisOption.tickFormatter == null) {
        break label843;
      }
      paramAxisData.tickFormatter = paramAxisOption.tickFormatter;
      return;
      if ((paramAxisData == this.axes.xaxis) || (paramAxisData == this.axes.yaxis))
      {
        d1 = 0.3D * Math.sqrt(this.canvasWidth);
        break;
      }
      d1 = 0.3D * Math.sqrt(this.canvasHeight);
      break;
      label341:
      if ((this.spec.get(i) == null) || (this.spec.get(i + 1) == null)) {}
      while ((d2 >= (((SpecData)this.spec.get(i)).i0 * ((Double)this.timeUnitSize.get(((SpecData)this.spec.get(i)).i1)).doubleValue() + ((SpecData)this.spec.get(i + 1)).i0 * ((Double)this.timeUnitSize.get(((SpecData)this.spec.get(i + 1)).i1)).doubleValue()) / 2.0D) || (((SpecData)this.spec.get(i)).i0 * ((Double)this.timeUnitSize.get(((SpecData)this.spec.get(i)).i1)).doubleValue() < d1))
      {
        i += 1;
        break;
      }
      break label100;
      label531:
      if (d1 < 3.0D) {
        d1 = 2.0D;
      } else if (d1 < 7.5D) {
        d1 = 5.0D;
      } else {
        d1 = 10.0D;
      }
    }
    label568:
    int k = paramAxisOption.tickDecimals;
    int j = (int)-Math.floor(Math.log(d2) / 2.302585D);
    int i = j;
    if (k != -1)
    {
      i = j;
      if (j > k) {
        i = k;
      }
    }
    double d3 = Math.pow(10.0D, -i);
    double d4 = d2 / d3;
    if (d4 < 1.5D)
    {
      d1 = 1.0D;
      j = i;
      label649:
      d2 = d1 * d3;
      d1 = d2;
      if (paramAxisOption.minTickSize != Double.MIN_VALUE)
      {
        d1 = d2;
        if (d2 < paramAxisOption.minTickSize) {
          d1 = paramAxisOption.minTickSize;
        }
      }
      if (paramAxisOption.tickSize != Double.MIN_VALUE) {
        d1 = paramAxisOption.tickSize;
      }
      if (k == -1) {
        break label836;
      }
    }
    for (;;)
    {
      paramAxisData.tickDecimals = Math.max(0, k);
      localObject2 = new TickGenerator();
      localObject1 = new TickFormatter();
      break;
      if (d4 < 3.0D)
      {
        d2 = 2.0D;
        j = i;
        d1 = d2;
        if (d4 <= 2.25D) {
          break label649;
        }
        if (k != -1)
        {
          j = i;
          d1 = d2;
          if (i + 1 > k) {
            break label649;
          }
        }
        d1 = 2.5D;
        j = i + 1;
        break label649;
      }
      if (d4 < 7.5D)
      {
        d1 = 5.0D;
        j = i;
        break label649;
      }
      d1 = 10.0D;
      j = i;
      break label649;
      label836:
      k = j;
    }
    label843:
    paramAxisData.tickFormatter = ((TickFormatter)localObject1);
  }
  
  private void setGridSpacing()
  {
    int i = this.options.grid.borderWidth;
    int j = 0;
    for (;;)
    {
      if (j >= this.series.size())
      {
        this.plotOffset.reset(i, i, i, i);
        j = this.options.grid.labelMargin + this.options.grid.borderWidth;
        if (this.axes.xaxis.labelWidth > 0.0D) {
          this.plotOffset.bottom = ((int)Math.max(i, this.axes.xaxis.labelHeight + j));
        }
        if (this.axes.yaxis.labelWidth > 0.0D) {
          this.plotOffset.left = ((int)Math.max(i, this.axes.yaxis.labelWidth + j));
        }
        if (this.axes.x2axis.labelHeight > 0.0D) {
          this.plotOffset.top = ((int)Math.max(i, this.axes.x2axis.labelHeight + j));
        }
        if (this.axes.y2axis.labelWidth > 0.0D) {
          this.plotOffset.right = ((int)Math.max(i, this.axes.y2axis.labelWidth + j));
        }
        this.plotWidth = (this.canvasWidth - this.plotOffset.left - this.plotOffset.right);
        this.plotHeight = (this.canvasHeight - this.plotOffset.bottom - this.plotOffset.top);
        return;
      }
      int k = ((SeriesData)this.series.get(j)).series.points.radius;
      i = Math.max(i, (((SeriesData)this.series.get(j)).series.points.lineWidth / 2 + k) * 2);
      j += 1;
    }
  }
  
  private void setRange(AxisData paramAxisData, AxisOption paramAxisOption)
  {
    double d7 = paramAxisOption.max;
    double d8 = paramAxisOption.min;
    double d6 = paramAxisOption.autoscaleMargin;
    double d4;
    double d1;
    label41:
    double d9;
    label64:
    double d3;
    double d2;
    double d5;
    if (!Double.isNaN(d7))
    {
      d4 = d7;
      if (Double.isNaN(d8)) {
        break label138;
      }
      d1 = d8;
      d9 = d4 - d1;
      if (d9 != 0.0D) {
        break label154;
      }
      if (d4 != 0.0D) {
        break label146;
      }
      d6 = 1.0D;
      d3 = d1;
      if (Double.isNaN(d8)) {
        d3 = d1 - d6;
      }
      if (!Double.isNaN(d7))
      {
        d2 = d4;
        d5 = d3;
        if (Double.isNaN(d8)) {}
      }
      else
      {
        d2 = d4 + d6;
        d5 = d3;
      }
    }
    for (;;)
    {
      paramAxisData.max = d2;
      paramAxisData.min = d5;
      return;
      d4 = paramAxisData.datamax;
      break;
      label138:
      d1 = paramAxisData.datamin;
      break label41;
      label146:
      d6 = 0.01D;
      break label64;
      label154:
      d2 = d4;
      d5 = d1;
      if (!Double.isNaN(d6))
      {
        d3 = d1;
        if (Double.isNaN(d8))
        {
          d1 -= d9 * d6;
          d3 = d1;
          if (d1 < 0.0D)
          {
            d3 = d1;
            if (paramAxisData.datamin != Double.MIN_VALUE)
            {
              d3 = d1;
              if (paramAxisData.datamin >= 0.0D) {
                d3 = 0.0D;
              }
            }
          }
        }
        d2 = d4;
        d5 = d3;
        if (Double.isNaN(d7))
        {
          d1 = d4 + d9 * d6;
          d2 = d1;
          d5 = d3;
          if (d1 > 0.0D)
          {
            d2 = d1;
            d5 = d3;
            if (paramAxisData.datamax != Double.MAX_VALUE)
            {
              d2 = d1;
              d5 = d3;
              if (paramAxisData.datamax <= 0.0D)
              {
                d2 = 0.0D;
                d5 = d3;
              }
            }
          }
        }
      }
    }
  }
  
  private void setTicks(AxisData paramAxisData, AxisOption paramAxisOption)
  {
    if (!paramAxisData.used.booleanValue())
    {
      label10:
      return;
    }
    else
    {
      if (paramAxisOption.ticks != null) {
        break label129;
      }
      paramAxisData.ticks = paramAxisData.tickGenerator.generator(paramAxisData);
    }
    for (;;)
    {
      if ((!Double.isNaN(paramAxisOption.autoscaleMargin)) && (paramAxisData.ticks.size() > 0))
      {
        if (Double.isNaN(paramAxisOption.min)) {
          paramAxisData.min = Math.min(paramAxisData.min, ((TickData)paramAxisData.ticks.get(0)).v);
        }
        if (!Double.isNaN(paramAxisOption.max)) {
          break label10;
        }
        paramAxisData.max = Math.max(paramAxisData.max, ((TickData)paramAxisData.ticks.get(paramAxisData.ticks.size() - 1)).v);
        return;
        label129:
        if (!(paramAxisOption.ticks instanceof Integer)) {
          break label167;
        }
        if (((Integer)paramAxisOption.ticks).intValue() <= 0) {
          continue;
        }
        paramAxisData.ticks = paramAxisData.tickGenerator.generator(paramAxisData);
        continue;
      }
      break label10;
      label167:
      if (!(paramAxisOption.ticks instanceof Vector)) {
        break;
      }
      paramAxisData.ticks = new Vector();
      Vector localVector = (Vector)paramAxisOption.ticks;
      if (localVector == null) {
        break;
      }
      int i = 0;
      while (i < localVector.size())
      {
        Object localObject = (TickData)localVector.get(i);
        if (localObject != null)
        {
          double d = ((TickData)localObject).v;
          String str = ((TickData)localObject).label;
          if (str != null)
          {
            localObject = str;
            if (str.length() != 0) {}
          }
          else
          {
            localObject = paramAxisData.tickFormatter.formatNumber(d, paramAxisData);
          }
          paramAxisData.ticks.add(new TickData(d, (String)localObject));
        }
        i += 1;
      }
    }
  }
  
  private void setTransformationHelpers(AxisData paramAxisData, final AxisOption paramAxisOption)
  {
    DoubleFormatter local7 = new DoubleFormatter()
    {
      public double format(double paramAnonymousDouble)
      {
        return paramAnonymousDouble;
      }
    };
    final Object localObject;
    if (paramAxisOption.transform != null)
    {
      localObject = paramAxisOption.transform;
      paramAxisOption = paramAxisOption.inverseTransform;
      if ((paramAxisData != this.axes.xaxis) && (paramAxisData != this.axes.x2axis)) {
        break label184;
      }
      d1 = this.plotWidth / (((DoubleFormatter)localObject).format(paramAxisData.max) - ((DoubleFormatter)localObject).format(paramAxisData.min));
      paramAxisData.scale = d1;
      d2 = ((DoubleFormatter)localObject).format(paramAxisData.min);
      if (localObject != local7) {
        break label147;
      }
    }
    label147:
    for (paramAxisData.p2c = new DoubleFormatter()
        {
          public double format(double paramAnonymousDouble)
          {
            return (paramAnonymousDouble - d2) * this.val$s;
          }
        };; paramAxisData.p2c = new DoubleFormatter()
        {
          public double format(double paramAnonymousDouble)
          {
            return (localObject.format(paramAnonymousDouble) - d2) * this.val$s;
          }
        })
    {
      if (paramAxisOption != null) {
        break label167;
      }
      paramAxisData.c2p = new DoubleFormatter()
      {
        public double format(double paramAnonymousDouble)
        {
          return d2 + paramAnonymousDouble / this.val$s;
        }
      };
      return;
      localObject = local7;
      break;
    }
    label167:
    paramAxisData.c2p = new DoubleFormatter()
    {
      public double format(double paramAnonymousDouble)
      {
        return paramAxisOption.format(d2 + paramAnonymousDouble / this.val$s);
      }
    };
    return;
    label184:
    final double d1 = this.plotHeight / (((DoubleFormatter)localObject).format(paramAxisData.max) - ((DoubleFormatter)localObject).format(paramAxisData.min));
    paramAxisData.scale = d1;
    final double d2 = ((DoubleFormatter)localObject).format(paramAxisData.max);
    if (localObject == local7) {}
    for (paramAxisData.p2c = new DoubleFormatter()
        {
          public double format(double paramAnonymousDouble)
          {
            return (d2 - paramAnonymousDouble) * this.val$s;
          }
        }; paramAxisOption == null; paramAxisData.p2c = new DoubleFormatter()
        {
          public double format(double paramAnonymousDouble)
          {
            return (d2 - d1.format(paramAnonymousDouble)) * this.val$s;
          }
        })
    {
      paramAxisData.c2p = new DoubleFormatter()
      {
        public double format(double paramAnonymousDouble)
        {
          return d2 - paramAnonymousDouble / this.val$s;
        }
      };
      return;
    }
    paramAxisData.c2p = new DoubleFormatter()
    {
      public double format(double paramAnonymousDouble)
      {
        return paramAxisOption.format(d2 - paramAnonymousDouble / this.val$s);
      }
    };
  }
  
  private void updateAxis(AxisData paramAxisData, double paramDouble1, double paramDouble2)
  {
    if ((Double.isNaN(paramAxisData.datamin)) || (paramDouble1 < paramAxisData.datamin)) {
      paramAxisData.datamin = paramDouble1;
    }
    if ((Double.isNaN(paramAxisData.datamax)) || (paramDouble2 > paramAxisData.datamax)) {
      paramAxisData.datamax = paramDouble2;
    }
  }
  
  public void addHookListener(FlotEventListener paramFlotEventListener)
  {
    this.hookHolder.addEventListener(paramFlotEventListener);
  }
  
  public AxisData axisSpecToRealAxis(Axies paramAxies, String paramString)
  {
    Class localClass = paramAxies.getClass();
    Axies localAxies2 = null;
    Axies localAxies3 = null;
    Axies localAxies4 = null;
    Object localObject = null;
    Axies localAxies1 = null;
    try
    {
      paramAxies = (AxisData)localClass.getDeclaredField(paramString).get(paramAxies);
      localObject = paramAxies;
      if (paramAxies == null)
      {
        localAxies1 = paramAxies;
        localAxies2 = paramAxies;
        localAxies3 = paramAxies;
        localAxies4 = paramAxies;
        localObject = paramAxies;
        paramAxies = (AxisData)this.axes.getClass().getDeclaredField(paramString).get(this.axes);
        localObject = paramAxies;
      }
      return (AxisData)localObject;
    }
    catch (SecurityException paramAxies)
    {
      paramAxies = paramAxies;
      localObject = localAxies1;
      paramAxies.printStackTrace();
      return localAxies1;
    }
    catch (NoSuchFieldException paramAxies)
    {
      paramAxies = paramAxies;
      localObject = localAxies2;
      paramAxies.printStackTrace();
      return localAxies2;
    }
    catch (IllegalArgumentException paramAxies)
    {
      paramAxies = paramAxies;
      localObject = localAxies3;
      paramAxies.printStackTrace();
      return localAxies3;
    }
    catch (IllegalAccessException paramAxies)
    {
      paramAxies = paramAxies;
      localObject = localAxies4;
      paramAxies.printStackTrace();
      return localAxies4;
    }
    finally {}
    return (AxisData)localObject;
  }
  
  public void draw(Canvas paramCanvas, int paramInt1, int paramInt2)
  {
    this.grap = paramCanvas;
    this.canvasWidth = paramInt1;
    this.canvasHeight = paramInt2;
    if (this.options.canvas.fill)
    {
      paramCanvas = getFillStyle(true, this.options.canvas.fillColor, -1, this.canvasHeight, 0.0D);
      if (paramCanvas != null)
      {
        paramCanvas.setStyle(Paint.Style.FILL);
        drawRect(this.grap, 0.0F, 0.0F, this.canvasWidth, this.canvasHeight, paramCanvas);
      }
    }
    setupGrid();
    draw();
    drawOverlay(this.grap);
    this.grap = null;
  }
  
  public void drawOverlay(Canvas paramCanvas)
  {
    this.grapOverlay = paramCanvas;
    this.grapOverlay.save();
    this.grapOverlay.translate(this.plotOffset.left, this.plotOffset.top);
    int i = 0;
    for (;;)
    {
      if (i >= this.highlights.size())
      {
        this.grapOverlay.restore();
        this.hookHolder.dispatchEvent("drawOverlay", new FlotEvent(new HookEventObject(this, new Object[] { this.grapOverlay })));
        this.grapOverlay = null;
        return;
      }
      paramCanvas = (HighlightData)this.highlights.get(i);
      if (paramCanvas != null) {
        break;
      }
      i += 1;
    }
    Object localObject1 = paramCanvas.series;
    Object localObject2 = ((SeriesData)localObject1).axes.xaxis;
    Object localObject3 = ((SeriesData)localObject1).axes.yaxis;
    float f2 = (float)((AxisData)localObject2).p2c.format(paramCanvas.point[0]);
    float f1 = (float)((AxisData)localObject3).p2c.format(paramCanvas.point[1]);
    label245:
    int k;
    label260:
    label275:
    label358:
    float f3;
    float f4;
    int m;
    label394:
    float f5;
    label416:
    float f6;
    if (paramCanvas.series.series.bars.show.booleanValue())
    {
      f1 = (float)((AxisData)localObject3).p2c.format(paramCanvas.point[1] / 2.0D);
      drawBarHighlight(paramCanvas.series, paramCanvas.point, this.grapOverlay);
      if (f2 <= this.plotWidth / 2) {
        break label767;
      }
      k = 1;
      if (f1 <= this.plotHeight / 2) {
        break label773;
      }
      j = 1;
      localObject2 = String.format("%.2f", new Object[] { Double.valueOf(paramCanvas.point[0]) });
      localObject3 = String.format("%.2f", new Object[] { Double.valueOf(paramCanvas.point[1]) });
      if (this.options.grid.tooltipFormatter == null) {
        break label779;
      }
      paramCanvas = this.options.grid.tooltipFormatter.format((SeriesData)localObject1, paramCanvas.dataIndex);
      f3 = this.gridLabelPaint.measureText(paramCanvas);
      f4 = this.gridLabelPaint.getTextSize();
      localObject1 = new Path();
      if (k == 0) {
        break label818;
      }
      m = -1;
      f5 = f2 + m * (30.0F + f3);
      if (j == 0) {
        break label824;
      }
      m = -1;
      f6 = f1 + m * (20.0F + f4);
      ((Path)localObject1).moveTo(f5, f6);
      if (k == 0) {
        break label830;
      }
      m = 1;
      label447:
      float f7 = f5 + m * (20.0F + f3);
      ((Path)localObject1).lineTo(f7, f6);
      if (j == 0) {
        break label836;
      }
      m = 1;
      label479:
      float f8 = f6 + m * (10.0F + f4);
      ((Path)localObject1).lineTo(f7, f8);
      if (k == 0) {
        break label842;
      }
      m = -1;
      label510:
      ((Path)localObject1).lineTo(f7 + m * 10, f8);
      ((Path)localObject1).lineTo(f2, f1);
      if (k == 0) {
        break label848;
      }
      m = 1;
      label541:
      ((Path)localObject1).lineTo(f5 + m * 10, f8);
      ((Path)localObject1).lineTo(f5, f8);
      ((Path)localObject1).close();
      localObject2 = getFillStyle(true, this.options.grid.tooltipFillColor, 16777063, 40.0F + f4, 0.0D);
      if (localObject2 != null)
      {
        ((Paint)localObject2).setStyle(Paint.Style.FILL);
        this.grapOverlay.drawPath((Path)localObject1, (Paint)localObject2);
      }
      localObject2 = getAnti();
      ((Paint)localObject2).setStrokeWidth(1.0F);
      ((Paint)localObject2).setStyle(Paint.Style.STROKE);
      ((Paint)localObject2).setColor(getTranColor(this.options.grid.tooltipColor));
      this.grapOverlay.drawPath((Path)localObject1, (Paint)localObject2);
      if (k == 0) {
        break label854;
      }
      k = 0;
      label681:
      f1 = k;
      if (j == 0) {
        break label860;
      }
    }
    label767:
    label773:
    label779:
    label818:
    label824:
    label830:
    label836:
    label842:
    label848:
    label854:
    label860:
    for (int j = 0;; j = -1)
    {
      drawCenteredString(paramCanvas, (int)(j * (10.0F + f4) + f6 + f4 / 2.0F), (int)(f5 + f1 * (20.0F + f3)), f3 + 20.0F, f4 + 10.0F, this.grapOverlay);
      break;
      drawPointHighlight(paramCanvas.series, paramCanvas.point, this.grapOverlay);
      break label245;
      k = 0;
      break label260;
      j = 0;
      break label275;
      paramCanvas = "(" + (String)localObject2 + "," + (String)localObject3 + ")";
      break label358;
      m = 1;
      break label394;
      m = 1;
      break label416;
      m = -1;
      break label447;
      m = -1;
      break label479;
      m = 1;
      break label510;
      m = -1;
      break label541;
      k = -1;
      break label681;
    }
  }
  
  public void fillInSeriesOptions()
  {
    int i = 0;
    Vector localVector;
    int j;
    if (i >= this.series.size())
    {
      int m = this.series.size();
      localVector = new Vector();
      i = 0;
      j = 0;
      if (localVector.size() >= m)
      {
        j = 0;
        i = 0;
        if (i < this.series.size()) {
          break label394;
        }
      }
    }
    else
    {
      localObject = (SeriesData)this.series.get(i);
      if (localObject == null) {}
      for (;;)
      {
        i += 1;
        break;
        if ((!((SeriesData)localObject).series.lines.getShow().booleanValue()) && (this.options.series.lines.getShow().booleanValue())) {
          ((SeriesData)localObject).series.lines.setShow(this.options.series.lines.getShow());
        }
        if ((!((SeriesData)localObject).series.points.show.booleanValue()) && (this.options.series.points.show.booleanValue())) {
          ((SeriesData)localObject).series.points.show = this.options.series.points.show;
        }
        if ((!((SeriesData)localObject).series.bars.show.booleanValue()) && (this.options.series.bars.show.booleanValue())) {
          ((SeriesData)localObject).series.bars.show = this.options.series.bars.show;
        }
      }
    }
    if (this.options.colors.length == i)
    {
      localObject = new ColorHelper(100, 100, 100);
      label287:
      if (j % 2 != 1) {
        break label388;
      }
    }
    label388:
    for (int k = -1;; k = 1)
    {
      ((ColorHelper)localObject).scale("rgb", 1.0D + k * Math.ceil(j / 2) * 0.2D);
      localVector.add(Integer.valueOf(((ColorHelper)localObject).rgb()));
      k = i + 1;
      i = k;
      if (k < this.options.colors.length) {
        break;
      }
      i = 0;
      j += 1;
      break;
      localObject = new ColorHelper(this.options.colors[i]);
      break label287;
    }
    label394:
    Object localObject = (SeriesData)this.series.get(i);
    if (localObject == null) {}
    for (;;)
    {
      i += 1;
      break;
      if (((SeriesData)localObject).series.color == -1) {
        ((SeriesData)localObject).series.color = ((Integer)localVector.get(j)).intValue();
      }
      j += 1;
      ((SeriesData)localObject).series.defaultLinesShow();
      ((SeriesData)localObject).axes.xaxis = axisSpecToRealAxis(((SeriesData)localObject).axes, "xaxis");
      ((SeriesData)localObject).axes.yaxis = axisSpecToRealAxis(((SeriesData)localObject).axes, "yaxis");
    }
  }
  
  public NearItemData findNearbyItem(double paramDouble1, double paramDouble2, boolean paramBoolean)
  {
    int k = this.options.grid.mouseActiveRadius;
    double d1 = k * k + 1;
    Object localObject1 = (int[])null;
    int i = 0;
    int j;
    if (i >= this.series.size())
    {
      if ((localObject1 != null) && (localObject1.length == 2))
      {
        i = localObject1[0];
        j = localObject1[1];
        return new NearItemData(((SeriesData)this.series.get(i)).getData()[j], j, (SeriesData)this.series.get(i), i);
      }
    }
    else
    {
      SeriesData localSeriesData = (SeriesData)this.series.get(i);
      Object localObject3 = localObject1;
      double d3 = d1;
      if (localSeriesData != null)
      {
        if (paramBoolean) {
          break label166;
        }
        d3 = d1;
        localObject3 = localObject1;
      }
      label166:
      AxisData localAxisData;
      ArrayList localArrayList;
      int m;
      double d4;
      double d5;
      double d6;
      double d7;
      Object localObject2;
      double d2;
      float f;
      label378:
      label403:
      do
      {
        do
        {
          do
          {
            i += 1;
            localObject1 = localObject3;
            d1 = d3;
            break;
            localObject3 = localSeriesData.axes.xaxis;
            localAxisData = localSeriesData.axes.yaxis;
            localArrayList = localSeriesData.datapoints.points;
            m = localSeriesData.datapoints.pointsize;
            d4 = ((AxisData)localObject3).c2p.format(paramDouble1);
            d5 = localAxisData.c2p.format(paramDouble2);
            d6 = k / ((AxisData)localObject3).scale;
            d7 = k / localAxisData.scale;
            if (!localSeriesData.series.lines.getShow().booleanValue())
            {
              localObject2 = localObject1;
              d2 = d1;
              if (!localSeriesData.series.points.show.booleanValue()) {}
            }
            else
            {
              j = 0;
              if (j < localArrayList.size()) {
                break label481;
              }
              d2 = d1;
              localObject2 = localObject1;
            }
            localObject3 = localObject2;
            d3 = d2;
          } while (!localSeriesData.series.bars.show.booleanValue());
          localObject3 = localObject2;
          d3 = d2;
        } while (localObject2 != null);
        if (!localSeriesData.series.bars.align.equals("left")) {
          break label741;
        }
        f = 0.0F;
        d1 = f;
        d6 = d1 + localSeriesData.series.bars.barWidth;
        j = 0;
        localObject3 = localObject2;
        d3 = d2;
      } while (j >= localArrayList.size());
      localObject1 = localObject2;
      if (localArrayList.get(j) != null)
      {
        localObject1 = localObject2;
        if (localArrayList.get(j + 1) != null)
        {
          if (localArrayList.get(j + 2) != null) {
            break label760;
          }
          localObject1 = localObject2;
        }
      }
      label481:
      label537:
      double d8;
      label741:
      label760:
      do
      {
        do
        {
          do
          {
            do
            {
              j += m;
              localObject2 = localObject1;
              break label403;
              localObject2 = localObject1;
              d2 = d1;
              if (localArrayList.get(j) != null)
              {
                if (localArrayList.get(j + 1) != null) {
                  break label537;
                }
                d2 = d1;
                localObject2 = localObject1;
              }
              for (;;)
              {
                j += m;
                localObject1 = localObject2;
                d1 = d2;
                break;
                d8 = ((Double)localArrayList.get(j)).doubleValue();
                d3 = ((Double)localArrayList.get(j + 1)).doubleValue();
                localObject2 = localObject1;
                d2 = d1;
                if (d8 - d4 <= d6)
                {
                  localObject2 = localObject1;
                  d2 = d1;
                  if (d8 - d4 >= -d6)
                  {
                    localObject2 = localObject1;
                    d2 = d1;
                    if (d3 - d5 <= d7)
                    {
                      localObject2 = localObject1;
                      d2 = d1;
                      if (d3 - d5 >= -d7)
                      {
                        d2 = Math.abs(((AxisData)localObject3).p2c.format(d8) - paramDouble1);
                        d3 = Math.abs(localAxisData.p2c.format(d3) - paramDouble2);
                        d3 = d2 * d2 + d3 * d3;
                        localObject2 = localObject1;
                        d2 = d1;
                        if (d3 <= d1)
                        {
                          d2 = d3;
                          localObject2 = new int[2];
                          localObject2[0] = i;
                          localObject2[1] = (j / m);
                        }
                      }
                    }
                  }
                }
              }
              f = -localSeriesData.series.bars.barWidth / 2.0F;
              break label378;
              d3 = ((Double)localArrayList.get(j)).doubleValue();
              d7 = ((Double)localArrayList.get(j + 1)).doubleValue();
              d8 = ((Double)localArrayList.get(j + 2)).doubleValue();
              if (!localSeriesData.series.bars.horizontal.booleanValue()) {
                break label913;
              }
              localObject1 = localObject2;
            } while (d4 > Math.max(d8, d3));
            localObject1 = localObject2;
          } while (d4 < Math.min(d8, d3));
          localObject1 = localObject2;
        } while (d5 < d7 + d1);
        localObject1 = localObject2;
      } while (d5 > d7 + d6);
      for (;;)
      {
        localObject1 = new int[2];
        localObject1[0] = i;
        localObject1[1] = (j / m);
        break;
        label913:
        localObject1 = localObject2;
        if (d4 < d3 + d1) {
          break;
        }
        localObject1 = localObject2;
        if (d4 > d3 + d6) {
          break;
        }
        localObject1 = localObject2;
        if (d5 < Math.min(d8, d7)) {
          break;
        }
        localObject1 = localObject2;
        if (d5 > Math.max(d8, d7)) {
          break;
        }
      }
    }
    return null;
  }
  
  public Axies getAxes()
  {
    return this.axes;
  }
  
  public Canvas getCanvas()
  {
    return this.grap;
  }
  
  public Canvas getCanvasOverlay()
  {
    return this.grapOverlay;
  }
  
  public Vector<SeriesData> getData()
  {
    return this.series;
  }
  
  public EventHolder getEventHolder()
  {
    return this.eventHolder;
  }
  
  public Options getOptions()
  {
    return this.options;
  }
  
  public Object getPlaceholder()
  {
    return new Object();
  }
  
  public RectOffset getPlotOffset()
  {
    return this.plotOffset;
  }
  
  public int height()
  {
    return this.plotHeight;
  }
  
  public void highlight(SeriesData paramSeriesData, double[] paramArrayOfDouble, String paramString, int paramInt)
  {
    int i = indexOfHighlight(paramSeriesData, paramArrayOfDouble);
    if (i == -1) {
      this.highlights.add(new HighlightData(paramSeriesData, paramArrayOfDouble, paramString, paramInt));
    }
    while ((paramString != null) && (paramString.length() != 0)) {
      return;
    }
    ((HighlightData)this.highlights.get(i)).auto = "false";
  }
  
  public int indexOfHighlight(SeriesData paramSeriesData, double[] paramArrayOfDouble)
  {
    int i = 0;
    for (;;)
    {
      int j;
      if (i >= this.highlights.size()) {
        j = -1;
      }
      HighlightData localHighlightData;
      do
      {
        return j;
        localHighlightData = (HighlightData)this.highlights.get(i);
        if ((localHighlightData == null) || (localHighlightData.series != paramSeriesData) || (localHighlightData.point.length <= 1) || (paramArrayOfDouble.length <= 1) || (!equals(localHighlightData.point[0], paramArrayOfDouble[0]))) {
          break;
        }
        j = i;
      } while (equals(localHighlightData.point[1], paramArrayOfDouble[1]));
      i += 1;
    }
  }
  
  public Object offset()
  {
    return this.plotOffset;
  }
  
  public void parseOptions(Options paramOptions)
  {
    this.hookHolder.dispatchEvent("processOptions", new FlotEvent(new HookEventObject(this, new Object[] { paramOptions })));
  }
  
  public void pointOffset() {}
  
  public void processData()
  {
    int i = 0;
    if (i >= this.series.size())
    {
      j = 0;
      label18:
      if (j < this.series.size()) {
        break label164;
      }
      i = 0;
    }
    double d1;
    double d3;
    double d2;
    double d4;
    label164:
    Object localObject2;
    int k;
    for (;;)
    {
      if (i >= this.series.size())
      {
        d1 = Double.MAX_VALUE;
        d3 = Double.MIN_VALUE;
        d2 = Double.MAX_VALUE;
        d4 = Double.MIN_VALUE;
        i = 0;
        if (i < this.series.size()) {
          break label937;
        }
        return;
        localSeriesData = (SeriesData)this.series.get(i);
        localSeriesData.datapoints = new Datapoints();
        this.hookHolder.dispatchEvent("processRawData", new FlotEvent(new HookEventObject(this, new Object[] { localSeriesData, localSeriesData.getData(), localSeriesData.datapoints })));
        i += 1;
        break;
        localSeriesData = (SeriesData)this.series.get(j);
        localObject1 = localSeriesData.getData();
        if (localSeriesData.datapoints.format.size() == 0)
        {
          localSeriesData.datapoints.format.add(new FormatData("x", Boolean.valueOf(true), Boolean.valueOf(true), null));
          localSeriesData.datapoints.format.add(new FormatData("y", Boolean.valueOf(true), Boolean.valueOf(true), null));
          if (localSeriesData.series.bars.show.booleanValue()) {
            localSeriesData.datapoints.format.add(new FormatData("y", Boolean.valueOf(true), Boolean.valueOf(true), new Integer(0)));
          }
        }
        if (localSeriesData.datapoints.pointsize != 0)
        {
          j += 1;
          break label18;
        }
        if (localSeriesData.datapoints.pointsize == 0) {
          localSeriesData.datapoints.pointsize = localSeriesData.datapoints.format.size();
        }
        int i1 = localSeriesData.datapoints.pointsize;
        localObject2 = localSeriesData.datapoints.points;
        label423:
        AxisData localAxisData;
        Object localObject4;
        label469:
        boolean bool;
        if ((localSeriesData.series.lines.getShow().booleanValue()) && (localSeriesData.series.lines.steps.booleanValue()))
        {
          k = 1;
          localAxisData = localSeriesData.axes.xaxis;
          Object localObject3 = localSeriesData.axes.yaxis;
          localObject4 = Boolean.valueOf(true);
          ((AxisData)localObject3).used = ((Boolean)localObject4);
          localAxisData.used = ((Boolean)localObject4);
          i = 0;
          m = 0;
          if (m < localObject1.length)
          {
            localAxisData = localObject1[m];
            if ((localAxisData == null) || (localAxisData.length == 0)) {
              break label567;
            }
            bool = false;
            label498:
            localObject3 = Boolean.valueOf(bool);
            if (!((Boolean)localObject3).booleanValue())
            {
              n = 0;
              if (n < i1) {
                break label573;
              }
            }
            if (!((Boolean)localObject3).booleanValue()) {
              break label673;
            }
            n = 0;
            label534:
            if (n < i1) {
              break label653;
            }
            n = i;
          }
        }
        label567:
        label573:
        label653:
        label673:
        do
        {
          do
          {
            do
            {
              do
              {
                do
                {
                  m += 1;
                  i = n + i1;
                  break label469;
                  break;
                  k = 0;
                  break label423;
                  bool = true;
                  break label498;
                  if (n < localAxisData.length)
                  {
                    d1 = localAxisData[n];
                    localObject4 = (FormatData)localSeriesData.datapoints.format.get(n);
                    ((ArrayList)localObject2).add(i + n, new Double(d1));
                  }
                  for (;;)
                  {
                    n += 1;
                    break;
                    ((ArrayList)localObject2).add(i + n, new Double(0.0D));
                  }
                  ((ArrayList)localObject2).add(i + n, null);
                  n += 1;
                  break label534;
                  n = i;
                } while (k == 0);
                n = i;
              } while (i <= 0);
              n = i;
            } while (((ArrayList)localObject2).get(i - i1) == null);
            n = i;
          } while (((Double)((ArrayList)localObject2).get(i - i1)).equals(((ArrayList)localObject2).get(i)));
          n = i;
        } while (((Double)((ArrayList)localObject2).get(i - i1 + 1)).equals(((ArrayList)localObject2).get(i + 1)));
        int n = 0;
        for (;;)
        {
          if (n >= i1)
          {
            ((ArrayList)localObject2).set(i + 1, new Double(((Double)((ArrayList)localObject2).get(i - i1 + 1)).doubleValue()));
            n = i + i1;
            break;
          }
          ((ArrayList)localObject2).add(i + i1 + n, new Double(((Double)((ArrayList)localObject2).get(i + n)).doubleValue()));
          n += 1;
        }
      }
      localSeriesData = (SeriesData)this.series.get(i);
      this.hookHolder.dispatchEvent("processDatapoints", new FlotEvent(new HookEventObject(this, new Object[] { localSeriesData, localSeriesData.datapoints })));
      i += 1;
    }
    label937:
    SeriesData localSeriesData = (SeriesData)this.series.get(i);
    Object localObject1 = localSeriesData.datapoints.points;
    int m = localSeriesData.datapoints.pointsize;
    int j = 0;
    label974:
    double d7;
    double d8;
    double d6;
    double d5;
    float f;
    if (j >= ((ArrayList)localObject1).size())
    {
      d7 = d1;
      d8 = d3;
      d6 = d2;
      d5 = d4;
      if (localSeriesData.series.bars.show.booleanValue())
      {
        if (!localSeriesData.series.bars.align.equals("left")) {
          break label1432;
        }
        f = 0.0F;
        label1038:
        d5 = f;
        if (!localSeriesData.series.bars.horizontal.booleanValue()) {
          break label1451;
        }
        d6 = d2 + d5;
        d5 = d4 + (localSeriesData.series.bars.barWidth + d5);
        d8 = d3;
        d7 = d1;
      }
    }
    for (;;)
    {
      updateAxis(localSeriesData.axes.xaxis, d7, d8);
      updateAxis(localSeriesData.axes.yaxis, d6, d5);
      i += 1;
      d1 = d7;
      d3 = d8;
      d2 = d6;
      d4 = d5;
      break;
      if (((ArrayList)localObject1).get(j) == null)
      {
        d6 = d2;
        d7 = d3;
        d8 = d1;
        j += m;
        d1 = d8;
        d3 = d7;
        d2 = d6;
        break label974;
      }
      k = 0;
      double d11;
      for (d5 = d4;; d5 = d11)
      {
        d8 = d1;
        d7 = d3;
        d6 = d2;
        d4 = d5;
        if (k >= m) {
          break;
        }
        d4 = ((Double)((ArrayList)localObject1).get(j + k)).doubleValue();
        localObject2 = (FormatData)localSeriesData.datapoints.format.get(k);
        d8 = d1;
        double d9 = d3;
        double d10 = d2;
        d11 = d5;
        if (localObject2 != null)
        {
          d6 = d1;
          d7 = d3;
          if (((FormatData)localObject2).xy.equals("x"))
          {
            d8 = d1;
            if (d4 < d1) {
              d8 = d4;
            }
            d6 = d8;
            d7 = d3;
            if (d4 > d3)
            {
              d7 = d4;
              d6 = d8;
            }
          }
          d8 = d6;
          d9 = d7;
          d10 = d2;
          d11 = d5;
          if (((FormatData)localObject2).xy.equals("y"))
          {
            d1 = d2;
            if (d4 < d2) {
              d1 = d4;
            }
            d8 = d6;
            d9 = d7;
            d10 = d1;
            d11 = d5;
            if (d4 > d5)
            {
              d11 = d4;
              d10 = d1;
              d9 = d7;
              d8 = d6;
            }
          }
        }
        k += 1;
        d1 = d8;
        d3 = d9;
        d2 = d10;
      }
      label1432:
      f = -localSeriesData.series.bars.barWidth / 2.0F;
      break label1038;
      label1451:
      d7 = d1 + d5;
      d8 = d3 + (localSeriesData.series.bars.barWidth + d5);
      d6 = d2;
      d5 = d4;
    }
  }
  
  public void redraw()
  {
    this.eventHolder.dispatchEvent("repaint", new FlotEvent(this));
  }
  
  public void removeHookListener(FlotEventListener paramFlotEventListener)
  {
    this.hookHolder.removeEventListener(paramFlotEventListener);
  }
  
  public void setData(Vector<SeriesData> paramVector)
  {
    this.series = paramVector;
    fillInSeriesOptions();
    processData();
    this.spec.add(new SpecData(1.0D, "second"));
    this.spec.add(new SpecData(2.0D, "second"));
    this.spec.add(new SpecData(5.0D, "second"));
    this.spec.add(new SpecData(10.0D, "second"));
    this.spec.add(new SpecData(30.0D, "second"));
    this.spec.add(new SpecData(1.0D, "minute"));
    this.spec.add(new SpecData(2.0D, "minute"));
    this.spec.add(new SpecData(5.0D, "minute"));
    this.spec.add(new SpecData(10.0D, "minute"));
    this.spec.add(new SpecData(30.0D, "minute"));
    this.spec.add(new SpecData(1.0D, "hour"));
    this.spec.add(new SpecData(2.0D, "hour"));
    this.spec.add(new SpecData(4.0D, "hour"));
    this.spec.add(new SpecData(8.0D, "hour"));
    this.spec.add(new SpecData(12.0D, "hour"));
    this.spec.add(new SpecData(1.0D, "day"));
    this.spec.add(new SpecData(2.0D, "day"));
    this.spec.add(new SpecData(3.0D, "day"));
    this.spec.add(new SpecData(0.25D, "month"));
    this.spec.add(new SpecData(0.5D, "month"));
    this.spec.add(new SpecData(1.0D, "month"));
    this.spec.add(new SpecData(2.0D, "month"));
    this.spec.add(new SpecData(3.0D, "month"));
    this.spec.add(new SpecData(6.0D, "month"));
    this.spec.add(new SpecData(1.0D, "year"));
    this.timeUnitSize.put("second", new Double(1000.0D));
    this.timeUnitSize.put("minute", new Double(60000.0D));
    this.timeUnitSize.put("hour", new Double(3600000.0D));
    this.timeUnitSize.put("day", new Double(8.64E7D));
    this.timeUnitSize.put("month", new Double(-1.702967296E9D));
    this.timeUnitSize.put("year", new Double(3.1556951999999996E10D));
  }
  
  public void setEventHolder(EventHolder paramEventHolder)
  {
    this.eventHolder = paramEventHolder;
  }
  
  public void setupGrid()
  {
    Hashtable localHashtable = new Hashtable();
    localHashtable.put(this.axes.xaxis, this.options.xaxis);
    localHashtable.put(this.axes.yaxis, this.options.yaxis);
    localHashtable.put(this.axes.x2axis, this.options.x2axis);
    localHashtable.put(this.axes.y2axis, this.options.y2axis);
    Object localObject1 = localHashtable.keys();
    if (!((Enumeration)localObject1).hasMoreElements())
    {
      if ((this.grap == null) || (!this.options.grid.show.booleanValue())) {
        break label232;
      }
      localObject1 = localHashtable.keys();
      label126:
      if (((Enumeration)localObject1).hasMoreElements()) {
        break label180;
      }
      setGridSpacing();
      label139:
      localObject1 = localHashtable.keys();
    }
    for (;;)
    {
      if (!((Enumeration)localObject1).hasMoreElements())
      {
        return;
        localObject2 = (AxisData)((Enumeration)localObject1).nextElement();
        setRange((AxisData)localObject2, (AxisOption)localHashtable.get(localObject2));
        break;
        label180:
        localObject2 = (AxisData)((Enumeration)localObject1).nextElement();
        prepareTickGeneration((AxisData)localObject2, (AxisOption)localHashtable.get(localObject2));
        setTicks((AxisData)localObject2, (AxisOption)localHashtable.get(localObject2));
        measureLabels((AxisData)localObject2, (AxisOption)localHashtable.get(localObject2));
        break label126;
        label232:
        localObject1 = this.plotOffset;
        localObject2 = this.plotOffset;
        RectOffset localRectOffset = this.plotOffset;
        this.plotOffset.bottom = 0;
        localRectOffset.top = 0;
        ((RectOffset)localObject2).right = 0;
        ((RectOffset)localObject1).left = 0;
        this.plotWidth = this.canvasWidth;
        this.plotHeight = this.canvasHeight;
        break label139;
      }
      Object localObject2 = (AxisData)((Enumeration)localObject1).nextElement();
      setTransformationHelpers((AxisData)localObject2, (AxisOption)localHashtable.get(localObject2));
    }
  }
  
  public void triggerRedrawOverlay() {}
  
  public void unhighlight(SeriesData paramSeriesData, double[] paramArrayOfDouble)
  {
    if ((paramSeriesData == null) && (paramArrayOfDouble == null)) {
      this.highlights.clear();
    }
    int i;
    do
    {
      return;
      i = indexOfHighlight(paramSeriesData, paramArrayOfDouble);
    } while (i == -1);
    this.highlights.remove(i);
  }
  
  public int width()
  {
    return this.plotWidth;
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\flot\FlotDraw.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */