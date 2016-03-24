package org.smardi.epi.graph;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;

public class GraphView
  extends View
{
  ArrayList<Integer> listAmp;
  float zoom = 35000.0F;
  
  public GraphView(Context paramContext)
  {
    super(paramContext);
  }
  
  public GraphView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  public GraphView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    super.onDraw(paramCanvas);
    Path localPath = new Path();
    Paint localPaint = new Paint();
    localPaint.setStrokeWidth(2.0F);
    localPaint.setColor(-65536);
    localPaint.setStyle(Paint.Style.STROKE);
    localPath.addRect(0.0F, 600.0F, 480.0F, 700.0F, Path.Direction.CW);
    paramCanvas.drawPath(localPath, localPaint);
    localPaint.setStrokeWidth(1.0F);
    localPaint.setColor(-16711936);
    localPath.reset();
    int j;
    int i;
    if (this.listAmp != null)
    {
      j = 0;
      i = 0;
      if (i < this.listAmp.size()) {
        break label142;
      }
      this.zoom = j;
      this.zoom = 40000.0F;
      i = 0;
    }
    for (;;)
    {
      if (i >= this.listAmp.size())
      {
        paramCanvas.drawPath(localPath, localPaint);
        return;
        label142:
        int k = j;
        if (((Integer)this.listAmp.get(i)).intValue() > j) {
          k = ((Integer)this.listAmp.get(i)).intValue();
        }
        i += 1;
        j = k;
        break;
      }
      float f = ((Integer)this.listAmp.get(i)).intValue() / this.zoom * 100.0F;
      if (i == 0) {
        localPath.moveTo(480 - this.listAmp.size() + i, 700.0F - f);
      }
      localPath.lineTo(480 - this.listAmp.size() + i, 700.0F - f);
      i += 1;
    }
  }
  
  public void setListAmp(ArrayList<Integer> paramArrayList)
  {
    this.listAmp = paramArrayList;
    invalidate();
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\graph\GraphView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */