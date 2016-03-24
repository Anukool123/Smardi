package org.smardi.epi.user;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import org.smardi.epi.DB_Adapter;
import org.smardi.epi.Manage_SharedPreference;
import org.smardi.epi.measure.AC_MeasureExplain;
import org.smardi.epi.record.AC_RecordMain;
import org.smardi.epi.setting.AC_SettingMain;

public class AC_UserChange
  extends Activity
{
  String birthday;
  ImageButton btn_back;
  ImageButton btn_next;
  Calendar calDateTime = Calendar.getInstance();
  DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
  {
    public void onDateSet(DatePicker paramAnonymousDatePicker, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      AC_UserChange.this.calDateTime.set(1, paramAnonymousInt1);
      AC_UserChange.this.calDateTime.set(2, paramAnonymousInt2);
      AC_UserChange.this.calDateTime.set(5, paramAnonymousInt3);
      String str2 = Integer.toString(paramAnonymousInt1) + "-" + Integer.toString(paramAnonymousInt2 + 1) + "-" + Integer.toString(paramAnonymousInt3);
      paramAnonymousDatePicker = "";
      if (paramAnonymousInt2 + 1 < 10) {
        paramAnonymousDatePicker = "0" + (paramAnonymousInt2 + 1);
      }
      if (paramAnonymousInt3 < 10) {}
      for (String str1 = "0" + paramAnonymousInt3;; str1 = paramAnonymousInt3)
      {
        AC_UserChange.this.birthday = (paramAnonymousInt1 + paramAnonymousDatePicker + str1);
        AC_UserChange.this.m_birth.setText(str2);
        return;
      }
    }
  };
  DB_Adapter db_Adapter;
  String gender;
  Manage_SharedPreference mPref;
  TextView m_birth;
  ImageButton m_female;
  ImageButton m_male;
  EditText m_name;
  ImageButton tab_measure;
  ImageButton tab_record;
  ImageButton tab_setting;
  TextView txt_subTitle;
  int user_uid;
  
  private void changeFont()
  {
    Typeface localTypeface = Typeface.createFromAsset(getAssets(), "fonts/Daum_Regular.ttf");
    this.txt_subTitle.setTypeface(localTypeface);
    this.m_name.setTypeface(localTypeface);
    this.m_birth.setTypeface(localTypeface);
  }
  
  private void loadUserInformation()
  {
    this.user_uid = getIntent().getIntExtra("user_uid", 0);
    Cursor localCursor = this.db_Adapter.fetchUser(this.user_uid);
    if (localCursor.moveToNext())
    {
      this.m_name.setText(localCursor.getString(1));
      this.m_birth.setText(localCursor.getInt(localCursor.getColumnIndex("user_birthday")));
      this.birthday = this.m_birth.getText().toString();
      this.gender = localCursor.getString(localCursor.getColumnIndex("user_gender"));
      if (this.gender.equals("Male"))
      {
        this.m_male.setImageDrawable(getResources().getDrawable(2130837621));
        this.m_female.setImageDrawable(getResources().getDrawable(2130837612));
      }
    }
    else
    {
      return;
    }
    this.m_male.setImageDrawable(getResources().getDrawable(2130837618));
    this.m_female.setImageDrawable(getResources().getDrawable(2130837615));
  }
  
  private void registButtonEvents()
  {
    this.m_birth.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        new DatePickerDialog(AC_UserChange.this, AC_UserChange.this.dateSetListener, AC_UserChange.this.calDateTime.get(1), AC_UserChange.this.calDateTime.get(2), AC_UserChange.this.calDateTime.get(5)).show();
      }
    });
    this.m_male.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (AC_UserChange.this.getString(2131230720).equals("en"))
        {
          AC_UserChange.this.m_male.setImageDrawable(AC_UserChange.this.getResources().getDrawable(2130837622));
          AC_UserChange.this.m_female.setImageDrawable(AC_UserChange.this.getResources().getDrawable(2130837613));
        }
        for (;;)
        {
          AC_UserChange.this.gender = "Male";
          return;
          if (AC_UserChange.this.getString(2131230720).equals("ja"))
          {
            AC_UserChange.this.m_male.setImageDrawable(AC_UserChange.this.getResources().getDrawable(2130837623));
            AC_UserChange.this.m_female.setImageDrawable(AC_UserChange.this.getResources().getDrawable(2130837614));
          }
          else
          {
            AC_UserChange.this.m_male.setImageDrawable(AC_UserChange.this.getResources().getDrawable(2130837621));
            AC_UserChange.this.m_female.setImageDrawable(AC_UserChange.this.getResources().getDrawable(2130837612));
          }
        }
      }
    });
    this.m_female.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (AC_UserChange.this.getString(2131230720).equals("en"))
        {
          AC_UserChange.this.m_male.setImageDrawable(AC_UserChange.this.getResources().getDrawable(2130837619));
          AC_UserChange.this.m_female.setImageDrawable(AC_UserChange.this.getResources().getDrawable(2130837616));
        }
        for (;;)
        {
          AC_UserChange.this.gender = "Female";
          return;
          if (AC_UserChange.this.getString(2131230720).equals("ja"))
          {
            AC_UserChange.this.m_male.setImageDrawable(AC_UserChange.this.getResources().getDrawable(2130837620));
            AC_UserChange.this.m_female.setImageDrawable(AC_UserChange.this.getResources().getDrawable(2130837617));
          }
          else
          {
            AC_UserChange.this.m_male.setImageDrawable(AC_UserChange.this.getResources().getDrawable(2130837618));
            AC_UserChange.this.m_female.setImageDrawable(AC_UserChange.this.getResources().getDrawable(2130837615));
          }
        }
      }
    });
    this.btn_back.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        AC_UserChange.this.finish();
        AC_UserChange.this.overridePendingTransition(2130968580, 2130968581);
      }
    });
    this.btn_next.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (AC_UserChange.this.checkImformation())
        {
          AC_UserChange.this.changeUserToDatabase();
          AC_UserChange.this.finish();
        }
      }
    });
    this.tab_measure.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = new Intent(AC_UserChange.this, AC_MeasureExplain.class);
        AC_UserChange.this.startActivity(paramAnonymousView);
        AC_UserChange.this.finish();
        AC_UserChange.this.overridePendingTransition(2130968580, 2130968581);
      }
    });
    this.tab_record.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = new Intent(AC_UserChange.this, AC_RecordMain.class);
        AC_UserChange.this.startActivity(paramAnonymousView);
        AC_UserChange.this.finish();
        AC_UserChange.this.overridePendingTransition(2130968580, 2130968581);
      }
    });
    this.tab_setting.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = new Intent(AC_UserChange.this, AC_SettingMain.class);
        AC_UserChange.this.startActivity(paramAnonymousView);
        AC_UserChange.this.finish();
        AC_UserChange.this.overridePendingTransition(2130968580, 2130968581);
      }
    });
  }
  
  protected void addUserToDatabase()
  {
    String str1 = this.m_name.getText().toString();
    String str2 = this.gender;
    String str3 = this.birthday;
    int i = this.mPref.getOwnerUID();
    this.db_Adapter.addUser(i, str1, str2, str3);
  }
  
  protected void changeUserToDatabase()
  {
    String str1 = this.m_name.getText().toString();
    String str2 = this.gender;
    String str3 = this.birthday;
    this.mPref.setUserName(str1);
    this.mPref.setUserGender(str2);
    this.mPref.setUserBirthday(str3);
    this.db_Adapter.changeUser(this.user_uid, str1, str2, str3);
  }
  
  protected boolean checkImformation()
  {
    String str1 = this.m_name.getText().toString();
    String str2 = this.gender;
    String str3 = this.birthday;
    if (str1.equals(""))
    {
      Toast.makeText(this, "Please input  yourname.", 1000).show();
      return false;
    }
    if (str2.equals(""))
    {
      Toast.makeText(this, "Please select your gender.", 1000).show();
      return false;
    }
    if (str3.equals(""))
    {
      Toast.makeText(this, "Please input your birthday.", 1000).show();
      return false;
    }
    return true;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903061);
    this.db_Adapter = new DB_Adapter(this);
    this.db_Adapter.open();
    this.txt_subTitle = ((TextView)findViewById(2131296308));
    this.m_name = ((EditText)findViewById(2131296293));
    this.m_birth = ((TextView)findViewById(2131296296));
    this.m_male = ((ImageButton)findViewById(2131296294));
    this.m_female = ((ImageButton)findViewById(2131296295));
    if (getString(2131230720).equals("en"))
    {
      this.m_male.setImageDrawable(getResources().getDrawable(2130837619));
      this.m_female.setImageDrawable(getResources().getDrawable(2130837613));
    }
    this.btn_back = ((ImageButton)findViewById(2131296287));
    this.btn_next = ((ImageButton)findViewById(2131296288));
    this.tab_measure = ((ImageButton)findViewById(2131296367));
    this.tab_record = ((ImageButton)findViewById(2131296313));
    this.tab_setting = ((ImageButton)findViewById(2131296315));
    this.mPref = new Manage_SharedPreference(this);
    changeFont();
    registButtonEvents();
    loadUserInformation();
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    this.db_Adapter.close();
    finish();
    overridePendingTransition(2130968580, 2130968581);
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramInt)
    {
    }
    for (;;)
    {
      return super.onKeyDown(paramInt, paramKeyEvent);
      finish();
      overridePendingTransition(2130968580, 2130968581);
    }
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\user\AC_UserChange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */