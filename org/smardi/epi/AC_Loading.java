package org.smardi.epi;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.SurfaceView;
import android.widget.ImageView;
import java.util.ArrayList;
import org.smardi.epi.measure.AC_MeasureExplain;
import org.smardi.epi.setting.AC_Help_tutorial;

public class AC_Loading
  extends Activity
{
  int SHOW_TIME = 3;
  int animCount = 1;
  CountDownTimer cTimer;
  boolean isTimerStop = false;
  ArrayList<Integer> listDrawables;
  ImageView loadingImageView;
  Manage_SharedPreference mSharedPreference;
  SurfaceView surfaceView;
  
  private void movetoNextActivity()
  {
    Intent localIntent;
    if (this.mSharedPreference.getUserName().equals("")) {
      localIntent = new Intent(this, AC_Help_tutorial.class);
    }
    for (;;)
    {
      startActivity(localIntent);
      finish();
      return;
      localIntent = new Intent(this, AC_MeasureExplain.class);
      localIntent.putExtra("tutorial", true);
    }
  }
  
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903047);
    this.loadingImageView = ((ImageView)findViewById(2131296302));
    this.mSharedPreference = new Manage_SharedPreference(this);
    this.listDrawables = new ArrayList();
    this.listDrawables.add(Integer.valueOf(2130837669));
    this.listDrawables.add(Integer.valueOf(2130837670));
    this.listDrawables.add(Integer.valueOf(2130837681));
    this.listDrawables.add(Integer.valueOf(2130837683));
    this.listDrawables.add(Integer.valueOf(2130837684));
    this.listDrawables.add(Integer.valueOf(2130837685));
    this.listDrawables.add(Integer.valueOf(2130837686));
    this.listDrawables.add(Integer.valueOf(2130837687));
    this.listDrawables.add(Integer.valueOf(2130837688));
    this.listDrawables.add(Integer.valueOf(2130837689));
    this.listDrawables.add(Integer.valueOf(2130837671));
    this.listDrawables.add(Integer.valueOf(2130837672));
    this.listDrawables.add(Integer.valueOf(2130837673));
    this.listDrawables.add(Integer.valueOf(2130837674));
    this.listDrawables.add(Integer.valueOf(2130837675));
    this.listDrawables.add(Integer.valueOf(2130837676));
    this.listDrawables.add(Integer.valueOf(2130837677));
    this.listDrawables.add(Integer.valueOf(2130837678));
    this.listDrawables.add(Integer.valueOf(2130837679));
    this.listDrawables.add(Integer.valueOf(2130837680));
    this.listDrawables.add(Integer.valueOf(2130837682));
    this.cTimer = new CountDownTimer(this.SHOW_TIME * 1000, this.SHOW_TIME * 1000 / 100)
    {
      public void onFinish()
      {
        if (!AC_Loading.this.isTimerStop) {
          AC_Loading.this.movetoNextActivity();
        }
      }
      
      public void onTick(long paramAnonymousLong)
      {
        AC_Loading localAC_Loading = AC_Loading.this;
        localAC_Loading.animCount += 1;
        if (AC_Loading.this.animCount < 21) {
          AC_Loading.this.loadingImageView.setImageDrawable(AC_Loading.this.getResources().getDrawable(((Integer)AC_Loading.this.listDrawables.get(AC_Loading.this.animCount)).intValue()));
        }
      }
    }.start();
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    this.isTimerStop = true;
    this.cTimer.cancel();
  }
  
  protected void onResume()
  {
    super.onResume();
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\AC_Loading.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */