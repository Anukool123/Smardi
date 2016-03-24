package com.flotandroidchart.flot.plugins;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.view.MotionEvent;
import com.flotandroidchart.flot.FlotDraw;
import com.flotandroidchart.flot.IPlugin;
import com.flotandroidchart.flot.data.AxisData;
import com.flotandroidchart.flot.data.RectOffset;
import com.flotandroidchart.flot.format.DoubleFormatter;
import com.flotandroidchart.flot.options.Axies;
import com.flotandroidchart.global.EventHolder;
import com.flotandroidchart.global.FlotEvent;
import com.flotandroidchart.global.FlotEventListener;
import com.flotandroidchart.global.HookEventObject;

public class CrossHairPlugin
  implements IPlugin
{
  public int color = -861274112;
  CrossHair crosshair = null;
  public int lineWidth = 1;
  public String mode = null;
  FlotDraw plot = null;
  
  public CrossHairPlugin()
  {
    this(null, -861274112, 1);
  }
  
  public CrossHairPlugin(String paramString, int paramInt1, int paramInt2)
  {
    this.mode = paramString;
    this.color = paramInt1;
    this.lineWidth = paramInt2;
  }
  
  public void clearCrosshair()
  {
    setCrosshair(null);
  }
  
  public void init(FlotDraw paramFlotDraw)
  {
    this.crosshair = new CrossHair();
    this.plot = paramFlotDraw;
    this.plot.getEventHolder().addEventListener(new FlotEventListener()
    {
      public String Name()
      {
        return "hover";
      }
      
      public void execute(FlotEvent paramAnonymousFlotEvent)
      {
        if (CrossHairPlugin.this.crosshair.locked) {}
        do
        {
          do
          {
            return;
          } while (!(paramAnonymousFlotEvent.getSource() instanceof MotionEvent));
          paramAnonymousFlotEvent = (MotionEvent)paramAnonymousFlotEvent.getSource();
        } while (paramAnonymousFlotEvent == null);
        RectOffset localRectOffset = CrossHairPlugin.this.plot.getPlotOffset();
        CrossHairPlugin.this.crosshair.x = ((int)Math.max(0.0F, Math.min(paramAnonymousFlotEvent.getX() - localRectOffset.left, CrossHairPlugin.this.plot.width())));
        CrossHairPlugin.this.crosshair.y = ((int)Math.max(0.0F, Math.min(paramAnonymousFlotEvent.getY() - localRectOffset.top, CrossHairPlugin.this.plot.height())));
        CrossHairPlugin.this.plot.redraw();
      }
    });
    this.plot.addHookListener(new FlotEventListener()
    {
      public String Name()
      {
        return "drawOverlay";
      }
      
      public void execute(FlotEvent paramAnonymousFlotEvent)
      {
        if (CrossHairPlugin.this.mode == null) {}
        do
        {
          do
          {
            do
            {
              return;
            } while (!(paramAnonymousFlotEvent.getSource() instanceof HookEventObject));
            paramAnonymousFlotEvent = (HookEventObject)paramAnonymousFlotEvent.getSource();
          } while ((paramAnonymousFlotEvent == null) || (paramAnonymousFlotEvent.hookParam.length <= 0));
          paramAnonymousFlotEvent = (Canvas)paramAnonymousFlotEvent.hookParam[0];
        } while (paramAnonymousFlotEvent == null);
        Object localObject = CrossHairPlugin.this.plot.getPlotOffset();
        paramAnonymousFlotEvent.save();
        paramAnonymousFlotEvent.translate(((RectOffset)localObject).left, ((RectOffset)localObject).top);
        if (CrossHairPlugin.this.crosshair.x != -1)
        {
          localObject = new Paint();
          ((Paint)localObject).setColor(CrossHairPlugin.this.color);
          ((Paint)localObject).setStyle(Paint.Style.STROKE);
          ((Paint)localObject).setStrokeWidth(CrossHairPlugin.this.lineWidth);
          if (CrossHairPlugin.this.mode.indexOf('x') != -1) {
            paramAnonymousFlotEvent.drawLine(CrossHairPlugin.this.crosshair.x, 0.0F, CrossHairPlugin.this.crosshair.x, CrossHairPlugin.this.plot.height(), (Paint)localObject);
          }
          if (CrossHairPlugin.this.mode.indexOf('y') != -1) {
            paramAnonymousFlotEvent.drawLine(0.0F, CrossHairPlugin.this.crosshair.y, CrossHairPlugin.this.plot.width(), CrossHairPlugin.this.crosshair.y, (Paint)localObject);
          }
        }
        paramAnonymousFlotEvent.restore();
      }
    });
  }
  
  public void lockCrosshair(Pos paramPos)
  {
    if (paramPos != null) {
      setCrosshair(paramPos);
    }
    this.crosshair.locked = true;
  }
  
  public void setCrosshair(Pos paramPos)
  {
    if (paramPos == null)
    {
      this.crosshair.x = -1;
      this.plot.redraw();
      return;
    }
    Axies localAxies = this.plot.getAxes();
    CrossHair localCrossHair = this.crosshair;
    if (paramPos.x != Integer.MIN_VALUE)
    {
      d = localAxies.xaxis.p2c.format(paramPos.x);
      label63:
      localCrossHair.x = ((int)Math.max(0.0D, Math.min(d, this.plot.width())));
      localCrossHair = this.crosshair;
      if (paramPos.y == Integer.MIN_VALUE) {
        break label166;
      }
    }
    label166:
    for (double d = localAxies.yaxis.p2c.format(paramPos.y);; d = localAxies.y2axis.p2c.format(paramPos.y2))
    {
      localCrossHair.y = ((int)Math.max(0.0D, Math.min(d, this.plot.height())));
      break;
      d = localAxies.x2axis.p2c.format(paramPos.x2);
      break label63;
    }
  }
  
  public void unlockCrosshair()
  {
    this.crosshair.locked = false;
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\flot\plugins\CrossHairPlugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */