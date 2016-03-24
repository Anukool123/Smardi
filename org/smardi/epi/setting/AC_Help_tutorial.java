package org.smardi.epi.setting;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;
import java.util.ArrayList;
import org.smardi.epi.Manage_SharedPreference;
import org.smardi.epi.measure.AC_MeasureExplain;
import org.smardi.epi.user.AC_UserAdd;

public class AC_Help_tutorial
  extends Activity
{
  private Animation appear_left;
  private Animation appear_right;
  private Animation disappear_left;
  private Animation disappear_right;
  private ArrayList<Integer> listTuto;
  private ArrayList<ImageView> listView;
  private Manage_SharedPreference mPref;
  private int m_nPreTouchPosX = 0;
  private ViewFlipper m_viewFlipper;
  View.OnTouchListener onTouchListener = new View.OnTouchListener()
  {
    public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
    {
      if (paramAnonymousMotionEvent.getAction() == 0) {
        AC_Help_tutorial.this.m_nPreTouchPosX = ((int)paramAnonymousMotionEvent.getX());
      }
      int i;
      if (paramAnonymousMotionEvent.getAction() == 1)
      {
        i = (int)paramAnonymousMotionEvent.getX();
        if (i >= AC_Help_tutorial.this.m_nPreTouchPosX) {
          break label175;
        }
        if (AC_Help_tutorial.this.pageCount >= AC_Help_tutorial.this.m_viewFlipper.getChildCount() - 1) {
          break label83;
        }
        AC_Help_tutorial.this.MoveNextView();
      }
      for (;;)
      {
        AC_Help_tutorial.this.m_nPreTouchPosX = i;
        return true;
        label83:
        paramAnonymousView = new AlertDialog.Builder(AC_Help_tutorial.this);
        paramAnonymousView.setTitle(AC_Help_tutorial.this.getString(2131230746));
        paramAnonymousView.setMessage(AC_Help_tutorial.this.getString(2131230747));
        paramAnonymousView.setPositiveButton(AC_Help_tutorial.this.getString(2131230768), new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            AC_Help_tutorial.this.finish();
            if (AC_Help_tutorial.this.mPref.getUserName().equals(""))
            {
              AC_Help_tutorial.this.startActivity(new Intent(AC_Help_tutorial.this, AC_UserAdd.class));
              return;
            }
            if (AC_Help_tutorial.this.mPref.getCheckedTutorial())
            {
              AC_Help_tutorial.this.finish();
              return;
            }
            if (AC_Help_tutorial.this.mPref.getCheckedTutorial())
            {
              AC_Help_tutorial.this.startActivity(new Intent(AC_Help_tutorial.this, AC_SettingMain.class));
              return;
            }
            AC_Help_tutorial.this.startActivity(new Intent(AC_Help_tutorial.this, AC_MeasureExplain.class));
            AC_Help_tutorial.this.mPref.setCheckedTutorial(true);
          }
        });
        paramAnonymousView.setNegativeButton(AC_Help_tutorial.this.getString(2131230769), new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            paramAnonymous2DialogInterface.dismiss();
          }
        });
        paramAnonymousView.show();
        continue;
        label175:
        if ((i > AC_Help_tutorial.this.m_nPreTouchPosX) && (AC_Help_tutorial.this.pageCount > 0)) {
          AC_Help_tutorial.this.MovewPreviousView();
        }
      }
    }
  };
  private int pageCount = 0;
  
  private void MoveNextView()
  {
    loadImage(this.pageCount + 1);
    if (this.pageCount > 0) {
      removeImage(this.pageCount - 1);
    }
    this.m_viewFlipper.setInAnimation(this.appear_right);
    this.m_viewFlipper.setOutAnimation(this.disappear_left);
    this.m_viewFlipper.showNext();
    this.pageCount += 1;
  }
  
  private void MovewPreviousView()
  {
    loadImage(this.pageCount - 1);
    if (this.pageCount < this.m_viewFlipper.getChildCount() - 1) {
      removeImage(this.pageCount + 1);
    }
    this.m_viewFlipper.setInAnimation(this.appear_left);
    this.m_viewFlipper.setOutAnimation(this.disappear_right);
    this.m_viewFlipper.showPrevious();
    this.pageCount -= 1;
  }
  
  private void loadImage(int paramInt)
  {
    ((ImageView)this.listView.get(paramInt)).setImageDrawable(getResources().getDrawable(((Integer)this.listTuto.get(paramInt)).intValue()));
  }
  
  private void removeImage(int paramInt)
  {
    ((ImageView)this.listView.get(paramInt)).setImageDrawable(null);
  }
  
  private void setListTutorial()
  {
    this.listTuto = new ArrayList();
    if (getString(2131230720).equals("ko"))
    {
      this.listTuto.add(Integer.valueOf(2130837723));
      this.listTuto.add(Integer.valueOf(2130837725));
      this.listTuto.add(Integer.valueOf(2130837727));
      this.listTuto.add(Integer.valueOf(2130837729));
    }
    for (;;)
    {
      ((ImageView)this.m_viewFlipper.getChildAt(0)).setImageDrawable(getResources().getDrawable(((Integer)this.listTuto.get(0)).intValue()));
      return;
      this.listTuto.add(Integer.valueOf(2130837724));
      this.listTuto.add(Integer.valueOf(2130837726));
      this.listTuto.add(Integer.valueOf(2130837728));
      this.listTuto.add(Integer.valueOf(2130837730));
    }
  }
  
  private void setListView()
  {
    this.listView = new ArrayList();
    int i = 0;
    for (;;)
    {
      if (i >= this.m_viewFlipper.getChildCount()) {
        return;
      }
      this.listView.add((ImageView)this.m_viewFlipper.getChildAt(i));
      i += 1;
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903040);
    this.appear_right = AnimationUtils.loadAnimation(this, 2130968577);
    this.appear_left = AnimationUtils.loadAnimation(this, 2130968576);
    this.disappear_right = AnimationUtils.loadAnimation(this, 2130968579);
    this.disappear_left = AnimationUtils.loadAnimation(this, 2130968578);
    this.m_viewFlipper = ((ViewFlipper)findViewById(2131296256));
    this.m_viewFlipper.setOnTouchListener(this.onTouchListener);
    this.mPref = new Manage_SharedPreference(this);
    setListTutorial();
    setListView();
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\setting\AC_Help_tutorial.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */