package org.smardi.epi.measure;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Process;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import org.smardi.epi.Manage_SharedPreference;
import org.smardi.epi.PowerConnectionReceiver;
import org.smardi.epi.device.Devices;
import org.smardi.epi.record.AC_RecordMain;
import org.smardi.epi.setting.AC_SettingMain;
import org.smardi.epi.setting.Dialog_Calibration;
import org.smardi.epi.user.AC_UserMain;

public class AC_MeasureExplain
  extends Activity
{
  public static final int MODE_DETAIL = 1;
  public static final int MODE_FAST = 0;
  private boolean D = true;
  Intent batteryStatus = null;
  ImageButton btn_back;
  Button btn_detail_measure;
  Button btn_fast_measure;
  ImageButton btn_next;
  ImageButton btn_next_arrow;
  CountDownTimer cTimer = null;
  boolean isUserWantToExit = false;
  long lastBackPressTime = 0L;
  ArrayList<Drawable> listArrow = null;
  ArrayList<View> list_Tutorial = null;
  PowerConnectionReceiver mPowerConnectionReceiver = null;
  Manage_SharedPreference mPref;
  private int measureMode = -1;
  View.OnClickListener onClickListener = new View.OnClickListener()
  {
    private void showNextArrow()
    {
      if (AC_MeasureExplain.this.btn_next_arrow.getVisibility() == 8) {
        AC_MeasureExplain.this.btn_next_arrow.setVisibility(0);
      }
    }
    
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      case 2131296288: 
      default: 
        return;
      case 2131296287: 
        AC_MeasureExplain.this.exitApp();
        return;
      case 2131296317: 
        AC_MeasureExplain.this.measureMode = 0;
        AC_MeasureExplain.this.moveToNext();
        return;
      case 2131296316: 
        AC_MeasureExplain.this.measureMode = 1;
        AC_MeasureExplain.this.moveToNext();
        return;
      case 2131296310: 
        AC_MeasureExplain.this.moveToNext();
        return;
      case 2131296313: 
        paramAnonymousView = new Intent(AC_MeasureExplain.this, AC_RecordMain.class);
        AC_MeasureExplain.this.startActivity(paramAnonymousView);
        AC_MeasureExplain.this.finish();
        AC_MeasureExplain.this.overridePendingTransition(2130968580, 2130968581);
        return;
      case 2131296314: 
        paramAnonymousView = new Intent(AC_MeasureExplain.this, AC_UserMain.class);
        AC_MeasureExplain.this.startActivity(paramAnonymousView);
        AC_MeasureExplain.this.finish();
        AC_MeasureExplain.this.overridePendingTransition(2130968580, 2130968581);
        return;
      case 2131296315: 
        paramAnonymousView = new Intent(AC_MeasureExplain.this, AC_SettingMain.class);
        AC_MeasureExplain.this.startActivity(paramAnonymousView);
        AC_MeasureExplain.this.finish();
        AC_MeasureExplain.this.overridePendingTransition(2130968580, 2130968581);
        return;
      }
      AC_MeasureExplain.this.showTutorial(false);
    }
  };
  ImageButton tab_record;
  ImageButton tab_setting;
  ImageButton tab_user;
  Toast toast = null;
  TextView tutorial_background;
  ImageView tutorial_detail;
  ImageView tutorial_explain;
  ImageView tutorial_fast;
  TextView txt_explain;
  TextView txt_subTitle;
  
  private void changeFont()
  {
    Typeface localTypeface = Typeface.createFromAsset(getAssets(), "fonts/Daum_Regular.ttf");
    this.txt_subTitle.setTypeface(localTypeface);
    this.txt_explain.setTypeface(localTypeface);
  }
  
  private boolean exitApp()
  {
    if (this.lastBackPressTime < System.currentTimeMillis() - 2000L)
    {
      this.toast = Toast.makeText(this, getResources().getString(2131230759), 2000);
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
  
  private void moveToNext()
  {
    if (this.mPowerConnectionReceiver.isPowerConnected()) {
      Toast.makeText(getApplicationContext(), getString(2131230783), 1).show();
    }
    while (new Devices(Build.DEVICE, Build.MODEL, this).isCalibratedDevice())
    {
      moveToNext(this.measureMode);
      return;
      Log.e("EPI", this.mPowerConnectionReceiver.isPowerConnected());
    }
    if (this.mPref.getCalibration_a() == -1.0F) {
      this.mPref.getCalibration_b();
    }
    moveToNext(this.measureMode);
  }
  
  private void moveToNext(int paramInt)
  {
    switch (paramInt)
    {
    }
    for (;;)
    {
      finish();
      overridePendingTransition(2130968580, 2130968581);
      return;
      Intent localIntent = new Intent(this, AC_MeasureScan.class);
      localIntent.putExtra("mode", 0);
      startActivity(localIntent);
      continue;
      localIntent = new Intent(this, AC_MeasureMain.class);
      localIntent.putExtra("mode", 1);
      startActivity(localIntent);
    }
  }
  
  private void refreshCountDownTimer()
  {
    if (this.cTimer == null)
    {
      this.cTimer = new CountDownTimer(Long.MAX_VALUE, 800L)
      {
        private void changeBtn()
        {
          if (AC_MeasureExplain.this.btn_next_arrow.getDrawable().equals(AC_MeasureExplain.this.listArrow.get(0)))
          {
            AC_MeasureExplain.this.btn_next_arrow.setImageDrawable((Drawable)AC_MeasureExplain.this.listArrow.get(1));
            return;
          }
          AC_MeasureExplain.this.btn_next_arrow.setImageDrawable((Drawable)AC_MeasureExplain.this.listArrow.get(0));
        }
        
        public void onFinish()
        {
          changeBtn();
          AC_MeasureExplain.this.cTimer.cancel();
          AC_MeasureExplain.this.refreshCountDownTimer();
        }
        
        public void onTick(long paramAnonymousLong)
        {
          changeBtn();
        }
      };
      this.cTimer.start();
    }
  }
  
  private void registButtonEvents()
  {
    this.btn_back.setOnClickListener(this.onClickListener);
    this.btn_next.setOnClickListener(this.onClickListener);
    this.btn_fast_measure.setOnClickListener(this.onClickListener);
    this.btn_detail_measure.setOnClickListener(this.onClickListener);
    this.btn_next_arrow.setOnClickListener(this.onClickListener);
    this.tab_record.setOnClickListener(this.onClickListener);
    this.tab_user.setOnClickListener(this.onClickListener);
    this.tab_setting.setOnClickListener(this.onClickListener);
    this.tutorial_background.setOnClickListener(this.onClickListener);
  }
  
  private void showAlertDialogForCalibration()
  {
    startActivityForResult(new Intent(this, Dialog_Calibration.class), 1);
  }
  
  private void showTutorial(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.tutorial_background.setVisibility(0);
      this.tutorial_detail.setVisibility(0);
      this.tutorial_fast.setVisibility(0);
      this.tutorial_explain.setVisibility(0);
      return;
    }
    this.tutorial_background.setVisibility(8);
    this.tutorial_detail.setVisibility(8);
    this.tutorial_fast.setVisibility(8);
    this.tutorial_explain.setVisibility(8);
  }
  
  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    switch (paramInt1)
    {
    }
    do
    {
      return;
    } while (paramIntent.getAction() != "OKOK");
    Toast.makeText(this, getString(2131230784), 1000).show();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903050);
    this.btn_back = ((ImageButton)findViewById(2131296287));
    this.btn_next = ((ImageButton)findViewById(2131296288));
    this.btn_fast_measure = ((Button)findViewById(2131296317));
    this.btn_detail_measure = ((Button)findViewById(2131296316));
    if (getString(2131230720).equals("ko"))
    {
      this.btn_fast_measure.setBackgroundDrawable(getResources().getDrawable(2130837585));
      this.btn_detail_measure.setBackgroundDrawable(getResources().getDrawable(2130837582));
      this.tab_record = ((ImageButton)findViewById(2131296313));
      this.tab_user = ((ImageButton)findViewById(2131296314));
      this.tab_setting = ((ImageButton)findViewById(2131296315));
      this.txt_subTitle = ((TextView)findViewById(2131296308));
      this.txt_explain = ((TextView)findViewById(2131296309));
      this.tutorial_background = ((TextView)findViewById(2131296318));
      this.tutorial_fast = ((ImageView)findViewById(2131296319));
      this.tutorial_detail = ((ImageView)findViewById(2131296320));
      this.tutorial_explain = ((ImageView)findViewById(2131296321));
      if (!getString(2131230720).equals("ko")) {
        break label495;
      }
      this.tutorial_fast.setImageDrawable(getResources().getDrawable(2130837754));
      this.tutorial_detail.setImageDrawable(getResources().getDrawable(2130837750));
      this.tutorial_explain.setImageDrawable(getResources().getDrawable(2130837752));
    }
    for (;;)
    {
      this.btn_next_arrow = ((ImageButton)findViewById(2131296310));
      this.mPref = new Manage_SharedPreference(this);
      this.mPref.setDetailIndex(0);
      changeFont();
      registButtonEvents();
      this.listArrow = new ArrayList();
      this.listArrow.add(getResources().getDrawable(2130837529));
      this.listArrow.add(getResources().getDrawable(2130837530));
      showTutorial(getIntent().getBooleanExtra("tutorial", false));
      this.mPowerConnectionReceiver = new PowerConnectionReceiver();
      this.batteryStatus = registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
      this.mPowerConnectionReceiver.onReceive(this, this.batteryStatus);
      return;
      this.btn_fast_measure.setBackgroundDrawable(getResources().getDrawable(2130837586));
      this.btn_detail_measure.setBackgroundDrawable(getResources().getDrawable(2130837583));
      break;
      label495:
      this.tutorial_fast.setImageDrawable(getResources().getDrawable(2130837755));
      this.tutorial_detail.setImageDrawable(getResources().getDrawable(2130837751));
      this.tutorial_explain.setImageDrawable(getResources().getDrawable(2130837753));
    }
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    if (this.isUserWantToExit) {
      Process.killProcess(Process.myPid());
    }
    if (this.cTimer != null) {
      this.cTimer.cancel();
    }
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramInt)
    {
    default: 
      return super.onKeyDown(paramInt, paramKeyEvent);
    }
    return exitApp();
  }
  
  protected void onPause()
  {
    super.onPause();
    if (this.cTimer != null) {
      this.cTimer.cancel();
    }
  }
  
  protected void onResume()
  {
    super.onResume();
    this.mPowerConnectionReceiver = new PowerConnectionReceiver();
    this.batteryStatus = registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
    this.mPowerConnectionReceiver.onReceive(this, this.batteryStatus);
    if (this.cTimer != null) {
      this.cTimer.start();
    }
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\measure\AC_MeasureExplain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */