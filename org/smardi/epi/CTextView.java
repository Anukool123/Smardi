package org.smardi.epi;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CTextView
  extends TextView
{
  private int mAvailableWidth = 0;
  private List<String> mCutStr = new ArrayList();
  private Paint mPaint;
  
  public CTextView(Context paramContext)
  {
    super(paramContext);
  }
  
  public CTextView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
  }
  
  private int setTextInfo(String paramString, int paramInt1, int paramInt2)
  {
    this.mPaint = getPaint();
    this.mPaint.setColor(getTextColors().getDefaultColor());
    this.mPaint.setTextSize(getTextSize());
    int i = paramInt2;
    int j = i;
    if (paramInt1 > 0)
    {
      this.mAvailableWidth = (paramInt1 - getPaddingLeft() - getPaddingRight());
      this.mCutStr.clear();
      String str = paramString;
      do
      {
        j = this.mPaint.breakText(str, true, this.mAvailableWidth, null);
        paramInt1 = i;
        paramString = str;
        if (j > 0)
        {
          this.mCutStr.add(str.substring(0, j));
          str = str.substring(j);
          paramInt1 = i;
          paramString = str;
          if (paramInt2 == 0)
          {
            paramInt1 = i + getLineHeight();
            paramString = str;
          }
        }
        i = paramInt1;
        str = paramString;
      } while (j > 0);
      j = paramInt1;
    }
    return j + (getPaddingTop() + getPaddingBottom());
  }
  
  protected void onDraw(Canvas paramCanvas)
  {
    float f = getPaddingTop() + getLineHeight();
    Iterator localIterator = this.mCutStr.iterator();
    for (;;)
    {
      if (!localIterator.hasNext()) {
        return;
      }
      paramCanvas.drawText((String)localIterator.next(), getPaddingLeft(), f, this.mPaint);
      f += getLineHeight();
    }
  }
  
  protected void onMeasure(int paramInt1, int paramInt2)
  {
    super.onMeasure(paramInt1, paramInt2);
    int j = View.MeasureSpec.getSize(paramInt1);
    paramInt2 = View.MeasureSpec.getSize(paramInt2);
    int i = setTextInfo(getText().toString(), j, paramInt2);
    paramInt1 = paramInt2;
    if (paramInt2 == 0) {
      paramInt1 = i;
    }
    setMeasuredDimension(j, paramInt1);
  }
  
  protected void onSizeChanged(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    if (paramInt1 != paramInt3) {
      setTextInfo(getText().toString(), paramInt1, paramInt2);
    }
  }
  
  protected void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    setTextInfo(paramCharSequence.toString(), getWidth(), getHeight());
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\CTextView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */