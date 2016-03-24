package org.smardi.epi.measure;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;
import org.smardi.epi.Manage_SharedPreference;
import org.smardi.epi.record.AC_RecordMain;
import org.smardi.epi.setting.AC_SettingMain;
import org.smardi.epi.user.AC_UserMain;

public class AC_MeasureMain
  extends Activity
{
  private boolean D = false;
  final long DOUBLE_CLICK_INTERVAL = 500L;
  ImageButton btn_back;
  ImageButton btn_next;
  ImageButton btn_next_arrow = null;
  CountDownTimer cTimer = null;
  CountDownTimer cTimer_Next = null;
  long doubleClickTime = 500L;
  boolean isUserWantToExit = false;
  long lastBackPressTime = 0L;
  long lastClickedPointTime = 0L;
  int lastSelectedPoint = -1;
  ImageView layout_face = null;
  ArrayList<Drawable> listArrow = null;
  ArrayList<Button> listPoint;
  Manage_SharedPreference mPref;
  View.OnClickListener onClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      long l = new Date().getTime();
      switch (paramAnonymousView.getId())
      {
      }
      while ((0 != 0) && (l - AC_MeasureMain.this.lastClickedPointTime < AC_MeasureMain.this.doubleClickTime))
      {
        AC_MeasureMain.this.gotoNext();
        return;
        AC_MeasureMain.this.goBack();
        continue;
        AC_MeasureMain.this.gotoNext();
        continue;
        AC_MeasureMain.this.gotoNext();
        continue;
        paramAnonymousView = new Intent(AC_MeasureMain.this, AC_RecordMain.class);
        AC_MeasureMain.this.startActivity(paramAnonymousView);
        AC_MeasureMain.this.finish();
        AC_MeasureMain.this.overridePendingTransition(2130968580, 2130968581);
        continue;
        paramAnonymousView = new Intent(AC_MeasureMain.this, AC_UserMain.class);
        AC_MeasureMain.this.startActivity(paramAnonymousView);
        AC_MeasureMain.this.finish();
        AC_MeasureMain.this.overridePendingTransition(2130968580, 2130968581);
        continue;
        paramAnonymousView = new Intent(AC_MeasureMain.this, AC_SettingMain.class);
        AC_MeasureMain.this.startActivity(paramAnonymousView);
        AC_MeasureMain.this.finish();
        AC_MeasureMain.this.overridePendingTransition(2130968580, 2130968581);
      }
      AC_MeasureMain.this.lastClickedPointTime = l;
    }
  };
  View.OnTouchListener onTouchListener = new View.OnTouchListener()
  {
    public boolean onTouch(View paramAnonymousView, MotionEvent paramAnonymousMotionEvent)
    {
      switch (paramAnonymousView.getId())
      {
      }
      label258:
      label380:
      label407:
      for (;;)
      {
        return false;
        LinearLayout localLinearLayout = (LinearLayout)AC_MeasureMain.this.findViewById(2131296322);
        int i = (int)(paramAnonymousMotionEvent.getRawX() - localLinearLayout.getLeft());
        int j = (int)(paramAnonymousMotionEvent.getRawY() - localLinearLayout.getTop());
        int k = paramAnonymousView.getMeasuredWidth();
        int m = paramAnonymousView.getMeasuredHeight();
        if (AC_MeasureMain.this.D)
        {
          Log.e("Epi", "IMG - w:" + k + " h:" + m);
          Log.e("Epi", "TCH - x:" + i + " y:" + j);
          Log.e("Epi", "LOC - x:" + localLinearLayout.getLeft() + " y:" + localLinearLayout.getTop());
        }
        float f1 = i / k;
        float f2 = j / m;
        if ((0.396D < f1) && (f1 < 0.604D))
        {
          if ((0.3985D < f2) && (f2 < 0.5675D)) {
            AC_MeasureMain.this.changeScanningPoint(1);
          }
        }
        else if ((0.586D < f2) && (f2 < 0.755D))
        {
          if ((0.25D >= f1) || (f1 >= 0.4585D)) {
            break label380;
          }
          AC_MeasureMain.this.changeScanningPoint(3);
        }
        for (;;)
        {
          if (!AC_MeasureMain.this.D) {
            break label407;
          }
          Log.e("Epi", "X:" + f1 + " Y:" + f2);
          break;
          if ((0.7785D >= f2) || (f2 >= 0.9475D)) {
            break label258;
          }
          AC_MeasureMain.this.changeScanningPoint(4);
          break label258;
          if ((0.5395D < f1) && (f1 < 0.748D)) {
            AC_MeasureMain.this.changeScanningPoint(2);
          }
        }
      }
    }
  };
  Button point_chin;
  Button point_forehead;
  Button point_leftcheek;
  Button point_rightcheek;
  int scanPoint = 0;
  ImageButton tab_record;
  ImageButton tab_setting;
  ImageButton tab_user;
  long time_lastSelectedPoint = 0L;
  Toast toast = null;
  TextView txt_explain;
  TextView txt_subTitle;
  
  private void changeFaceAnimation(ImageView paramImageView)
  {
    this.cTimer = new CountDownTimer(Long.MAX_VALUE, 500L)
    {
      Drawable faceDrawable = null;
      long totalCount = 0L;
      
      public void onFinish() {}
      
      public void onTick(long paramAnonymousLong)
      {
        if (AC_MeasureMain.this.mPref.getUserGender().equals("Male")) {
          switch ((int)(this.totalCount % 7L))
          {
          default: 
            this.faceDrawable = AC_MeasureMain.this.getResources().getDrawable(2130837636);
          }
        }
        for (;;)
        {
          this.val$faceLayout.setImageDrawable(this.faceDrawable);
          this.totalCount += 1L;
          return;
          this.faceDrawable = AC_MeasureMain.this.getResources().getDrawable(2130837637);
          continue;
          this.faceDrawable = AC_MeasureMain.this.getResources().getDrawable(2130837638);
          continue;
          this.faceDrawable = AC_MeasureMain.this.getResources().getDrawable(2130837639);
          continue;
          this.faceDrawable = AC_MeasureMain.this.getResources().getDrawable(2130837640);
          continue;
          switch ((int)(this.totalCount % 7L))
          {
          default: 
            this.faceDrawable = AC_MeasureMain.this.getResources().getDrawable(2130837627);
            break;
          case 3: 
            this.faceDrawable = AC_MeasureMain.this.getResources().getDrawable(2130837628);
            break;
          case 4: 
            this.faceDrawable = AC_MeasureMain.this.getResources().getDrawable(2130837629);
            break;
          case 5: 
            this.faceDrawable = AC_MeasureMain.this.getResources().getDrawable(2130837630);
            break;
          case 6: 
            this.faceDrawable = AC_MeasureMain.this.getResources().getDrawable(2130837631);
          }
        }
      }
    }.start();
  }
  
  private void changeFont()
  {
    Typeface localTypeface = Typeface.createFromAsset(getAssets(), "fonts/Daum_Regular.ttf");
    this.txt_explain.setTypeface(localTypeface);
    this.txt_subTitle.setTypeface(localTypeface);
  }
  
  private void changeScanningPoint(int paramInt)
  {
    int i = 0;
    if (i >= this.listPoint.size()) {
      return;
    }
    Drawable localDrawable;
    label116:
    long l;
    if (paramInt - 1 == i)
    {
      this.mPref.setScanningPoint(paramInt - 1);
      if (this.D) {
        Log.e("smardi.Epi", "selected position:" + this.mPref.getScanningPoint());
      }
      localDrawable = null;
      if (!this.mPref.getUserGender().equals("Male")) {
        break label276;
      }
      switch (paramInt)
      {
      default: 
        this.txt_explain.setText(getString(2131230794) + this.mPref.getScanningPointExplain());
        this.cTimer.cancel();
        this.layout_face.setImageDrawable(localDrawable);
        refreshCountDownTimer();
        l = new Date().getTime();
        if ((l - this.lastClickedPointTime < 500L) && (paramInt == this.lastSelectedPoint)) {
          gotoNext();
        }
        break;
      }
    }
    for (;;)
    {
      this.lastSelectedPoint = paramInt;
      i += 1;
      break;
      localDrawable = getResources().getDrawable(2130837644);
      break label116;
      localDrawable = getResources().getDrawable(2130837642);
      break label116;
      localDrawable = getResources().getDrawable(2130837643);
      break label116;
      localDrawable = getResources().getDrawable(2130837641);
      break label116;
      switch (paramInt)
      {
      default: 
        break;
      case 1: 
        localDrawable = getResources().getDrawable(2130837635);
        break;
      case 2: 
        localDrawable = getResources().getDrawable(2130837633);
        break;
      case 3: 
        localDrawable = getResources().getDrawable(2130837634);
        break;
      case 4: 
        label276:
        localDrawable = getResources().getDrawable(2130837632);
        break;
        this.lastClickedPointTime = l;
      }
    }
  }
  
  private boolean exitApp()
  {
    if (this.lastBackPressTime < System.currentTimeMillis() - 2000L)
    {
      this.toast = Toast.makeText(this, "Press back again to close Epi", 2000);
      this.toast.show();
      this.lastBackPressTime = System.currentTimeMillis();
      return false;
    }
    if (this.toast != null) {
      this.toast.cancel();
    }
    this.isUserWantToExit = true;
    finish();
    return true;
  }
  
  private void goBack()
  {
    startActivity(new Intent(this, AC_MeasureExplain.class));
    finish();
    overridePendingTransition(2130968580, 2130968581);
  }
  
  private void gotoNext()
  {
    Intent localIntent = new Intent(this, AC_MeasureScan.class);
    localIntent.putExtra("scanPoint", this.scanPoint);
    localIntent.putExtra("mode", 1);
    startActivity(localIntent);
    finish();
    overridePendingTransition(2130968580, 2130968581);
  }
  
  private void initFace()
  {
    getResources().getDrawable(2130837636);
    if (this.mPref.getUserGender().equals("Male")) {}
    for (Drawable localDrawable = getResources().getDrawable(2130837636);; localDrawable = getResources().getDrawable(2130837627))
    {
      this.layout_face.setImageDrawable(localDrawable);
      changeFaceAnimation(this.layout_face);
      return;
    }
  }
  
  private void refreshCountDownTimer()
  {
    if (this.cTimer_Next == null)
    {
      this.cTimer_Next = new CountDownTimer(Long.MAX_VALUE, 800L)
      {
        private void changeBtn()
        {
          AC_MeasureMain.this.btn_next_arrow.setVisibility(0);
          if (AC_MeasureMain.this.btn_next_arrow.getDrawable().equals(AC_MeasureMain.this.listArrow.get(0)))
          {
            AC_MeasureMain.this.btn_next_arrow.setImageDrawable((Drawable)AC_MeasureMain.this.listArrow.get(1));
            return;
          }
          AC_MeasureMain.this.btn_next_arrow.setImageDrawable((Drawable)AC_MeasureMain.this.listArrow.get(0));
        }
        
        public void onFinish()
        {
          changeBtn();
          AC_MeasureMain.this.cTimer_Next.cancel();
          AC_MeasureMain.this.refreshCountDownTimer();
        }
        
        public void onTick(long paramAnonymousLong)
        {
          changeBtn();
        }
      };
      this.cTimer_Next.start();
    }
  }
  
  private void registButtonEvents()
  {
    this.btn_back.setOnClickListener(this.onClickListener);
    this.btn_next.setOnClickListener(this.onClickListener);
    this.layout_face.setOnTouchListener(this.onTouchListener);
    this.tab_record.setOnClickListener(this.onClickListener);
    this.tab_user.setOnClickListener(this.onClickListener);
    this.tab_setting.setOnClickListener(this.onClickListener);
    this.btn_next_arrow.setOnClickListener(this.onClickListener);
  }
  
  private void setUserName()
  {
    String str = this.mPref.getUserName();
    this.txt_explain.setText(str + getString(2131230722));
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903051);
    this.btn_back = ((ImageButton)findViewById(2131296287));
    this.btn_next = ((ImageButton)findViewById(2131296288));
    this.point_forehead = ((Button)findViewById(2131296325));
    this.point_leftcheek = ((Button)findViewById(2131296327));
    this.point_rightcheek = ((Button)findViewById(2131296326));
    this.point_chin = ((Button)findViewById(2131296328));
    this.layout_face = ((ImageView)findViewById(2131296323));
    this.txt_explain = ((TextView)findViewById(2131296309));
    this.txt_subTitle = ((TextView)findViewById(2131296308));
    this.tab_record = ((ImageButton)findViewById(2131296313));
    this.tab_user = ((ImageButton)findViewById(2131296314));
    this.tab_setting = ((ImageButton)findViewById(2131296315));
    this.listPoint = new ArrayList();
    this.listPoint.add(this.point_forehead);
    this.listPoint.add(this.point_leftcheek);
    this.listPoint.add(this.point_rightcheek);
    this.listPoint.add(this.point_chin);
    this.btn_next_arrow = ((ImageButton)findViewById(2131296310));
    this.mPref = new Manage_SharedPreference(this);
    this.mPref.setDetailIndex(0);
    setUserName();
    initFace();
    changeFont();
    registButtonEvents();
    this.listArrow = new ArrayList();
    this.listArrow.add(getResources().getDrawable(2130837529));
    this.listArrow.add(getResources().getDrawable(2130837530));
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    this.cTimer.cancel();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramInt)
    {
    default: 
      return super.onKeyDown(paramInt, paramKeyEvent);
    }
    goBack();
    return false;
  }
  
  protected void onResume()
  {
    super.onResume();
    setUserName();
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\measure\AC_MeasureMain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */