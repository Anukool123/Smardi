package org.smardi.epi.setting;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import org.smardi.epi.Manage_SharedPreference;
import org.smardi.epi.device.Devices;
import org.smardi.epi.measure.AC_MeasureExplain;
import org.smardi.epi.record.AC_RecordMain;
import org.smardi.epi.user.AC_UserMain;

public class AC_SettingMain
  extends Activity
{
  ImageButton btn_back;
  RelativeLayout btn_setting_1;
  RelativeLayout btn_setting_2;
  RelativeLayout btn_setting_3;
  RelativeLayout btn_setting_calibration;
  RelativeLayout btn_setting_shop;
  Devices mDevices = null;
  Manage_SharedPreference mPref = null;
  View.OnClickListener onClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      case 2131296376: 
      default: 
        return;
      case 2131296287: 
        AC_SettingMain.this.goBack();
        return;
      case 2131296367: 
        AC_SettingMain.this.goBack();
        return;
      case 2131296313: 
        AC_SettingMain.this.goRecord();
        return;
      case 2131296314: 
        AC_SettingMain.this.goUser();
        return;
      case 2131296374: 
        AC_SettingMain.this.startActivity(new Intent(AC_SettingMain.this, AC_Help_tutorial.class));
        return;
      case 2131296380: 
        AC_SettingMain.this.popupAbout();
        return;
      case 2131296378: 
        AC_SettingMain.this.startActivityForResult(new Intent(AC_SettingMain.this, Dialog_Calibration.class), 1);
        return;
      }
      paramAnonymousView = new Intent("android.intent.action.VIEW", Uri.parse("http://www.smardishop.com"));
      AC_SettingMain.this.startActivity(paramAnonymousView);
    }
  };
  ImageButton tab_measure;
  ImageButton tab_record;
  ImageButton tab_user;
  TextView txt_setting_1;
  TextView txt_setting_2;
  TextView txt_setting_3;
  TextView txt_setting_4;
  TextView txt_setting_5;
  TextView txt_subTitle;
  
  private void changeFont()
  {
    Typeface localTypeface = Typeface.createFromAsset(getAssets(), "fonts/Daum_Regular.ttf");
    this.txt_subTitle.setTypeface(localTypeface);
    this.txt_setting_1.setTypeface(localTypeface);
    this.txt_setting_2.setTypeface(localTypeface);
    this.txt_setting_3.setTypeface(localTypeface);
    this.txt_setting_4.setTypeface(localTypeface);
    this.txt_setting_5.setTypeface(localTypeface);
  }
  
  private void goBack()
  {
    startActivity(new Intent(this, AC_MeasureExplain.class));
    finish();
    overridePendingTransition(2130968580, 2130968581);
  }
  
  private void goRecord()
  {
    startActivity(new Intent(this, AC_RecordMain.class));
    finish();
    overridePendingTransition(2130968580, 2130968581);
  }
  
  private void goUser()
  {
    startActivity(new Intent(this, AC_UserMain.class));
    finish();
    overridePendingTransition(2130968580, 2130968581);
  }
  
  private void initComponent()
  {
    this.mPref = new Manage_SharedPreference(this);
    this.mDevices = new Devices(Build.DEVICE, Build.MODEL, this);
    this.txt_subTitle = ((TextView)findViewById(2131296308));
    this.txt_setting_1 = ((TextView)findViewById(2131296375));
    this.txt_setting_2 = ((TextView)findViewById(2131296377));
    this.txt_setting_3 = ((TextView)findViewById(2131296379));
    this.txt_setting_4 = ((TextView)findViewById(2131296381));
    this.txt_setting_5 = ((TextView)findViewById(2131296383));
    this.btn_setting_1 = ((RelativeLayout)findViewById(2131296374));
    registOnClickEvent(this.btn_setting_1);
    this.btn_setting_2 = ((RelativeLayout)findViewById(2131296376));
    registOnClickEvent(this.btn_setting_2);
    this.btn_setting_3 = ((RelativeLayout)findViewById(2131296380));
    registOnClickEvent(this.btn_setting_3);
    this.btn_setting_calibration = ((RelativeLayout)findViewById(2131296378));
    registOnClickEvent(this.btn_setting_calibration);
    this.btn_setting_calibration.setVisibility(8);
    this.btn_setting_shop = ((RelativeLayout)findViewById(2131296382));
    registOnClickEvent(this.btn_setting_shop);
    this.btn_back = ((ImageButton)findViewById(2131296287));
    registOnClickEvent(this.btn_back);
    this.tab_measure = ((ImageButton)findViewById(2131296367));
    registOnClickEvent(this.tab_measure);
    this.tab_record = ((ImageButton)findViewById(2131296313));
    registOnClickEvent(this.tab_record);
    this.tab_user = ((ImageButton)findViewById(2131296314));
    registOnClickEvent(this.tab_user);
    changeFont();
  }
  
  private void popupAbout()
  {
    startActivity(new Intent(this, AC_About.class));
  }
  
  private void registOnClickEvent(View paramView)
  {
    paramView.setOnClickListener(this.onClickListener);
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
    } while ((paramIntent == null) || (!paramIntent.getAction().equals("OKOK")));
    Toast.makeText(this, "등록이 완료되었습니다.", 1000).show();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903059);
    initComponent();
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
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\setting\AC_SettingMain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */