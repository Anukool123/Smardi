package org.smardi.epi.user;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Calendar;
import org.smardi.epi.DB_Adapter;
import org.smardi.epi.Manage_SharedPreference;
import org.smardi.epi.measure.AC_MeasureExplain;
import org.smardi.epi.record.AC_RecordMain;
import org.smardi.epi.setting.AC_SettingMain;

public class AC_UserAdd
  extends Activity
{
  String birthday = null;
  ImageButton btn_back;
  ImageButton btn_next;
  Calendar calDateTime = Calendar.getInstance();
  DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
  {
    public void onDateSet(DatePicker paramAnonymousDatePicker, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      AC_UserAdd.this.calDateTime.set(1, paramAnonymousInt1);
      AC_UserAdd.this.calDateTime.set(2, paramAnonymousInt2);
      AC_UserAdd.this.calDateTime.set(5, paramAnonymousInt3);
      String str2 = Integer.toString(paramAnonymousInt1) + "-" + Integer.toString(paramAnonymousInt2 + 1) + "-" + Integer.toString(paramAnonymousInt3);
      paramAnonymousDatePicker = "";
      if (paramAnonymousInt2 + 1 < 10) {
        paramAnonymousDatePicker = "0" + (paramAnonymousInt2 + 1);
      }
      if (paramAnonymousInt3 < 10) {}
      for (String str1 = "0" + paramAnonymousInt3;; str1 = paramAnonymousInt3)
      {
        AC_UserAdd.this.birthday = (paramAnonymousInt1 + paramAnonymousDatePicker + str1);
        AC_UserAdd.this.m_birth.setText(str2);
        return;
      }
    }
  };
  DB_Adapter db_Adapter;
  String gender = null;
  boolean isFirstUserAdd = false;
  Manage_SharedPreference mPref;
  TextView m_birth;
  ImageButton m_female;
  ImageButton m_male;
  EditText m_name;
  ImageButton tab_measure;
  LinearLayout tab_menu_wrap;
  ImageButton tab_record;
  ImageButton tab_setting;
  ImageView tab_user;
  TextView txt_explain_firstRegist;
  TextView txt_subTitle;
  
  private void changeFont()
  {
    Typeface localTypeface = Typeface.createFromAsset(getAssets(), "fonts/Daum_Regular.ttf");
    Log.e("aa", "tf:" + localTypeface);
    Log.e("aa", "subTitle:" + this.txt_subTitle);
    this.txt_subTitle.setTypeface(localTypeface);
    this.txt_explain_firstRegist.setTypeface(localTypeface);
    this.m_name.setTypeface(localTypeface);
    this.m_birth.setTypeface(localTypeface);
  }
  
  private void registButtonEvents()
  {
    this.m_birth.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        new DatePickerDialog(AC_UserAdd.this, AC_UserAdd.this.dateSetListener, AC_UserAdd.this.calDateTime.get(1), AC_UserAdd.this.calDateTime.get(2), AC_UserAdd.this.calDateTime.get(5)).show();
      }
    });
    this.m_male.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (AC_UserAdd.this.getString(2131230720).equals("en"))
        {
          AC_UserAdd.this.m_male.setImageDrawable(AC_UserAdd.this.getResources().getDrawable(2130837622));
          AC_UserAdd.this.m_female.setImageDrawable(AC_UserAdd.this.getResources().getDrawable(2130837613));
        }
        for (;;)
        {
          AC_UserAdd.this.gender = "Male";
          return;
          AC_UserAdd.this.m_male.setImageDrawable(AC_UserAdd.this.getResources().getDrawable(2130837621));
          AC_UserAdd.this.m_female.setImageDrawable(AC_UserAdd.this.getResources().getDrawable(2130837612));
        }
      }
    });
    this.m_female.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (AC_UserAdd.this.getString(2131230720).equals("en"))
        {
          AC_UserAdd.this.m_male.setImageDrawable(AC_UserAdd.this.getResources().getDrawable(2130837619));
          AC_UserAdd.this.m_female.setImageDrawable(AC_UserAdd.this.getResources().getDrawable(2130837616));
        }
        for (;;)
        {
          AC_UserAdd.this.gender = "Female";
          return;
          AC_UserAdd.this.m_male.setImageDrawable(AC_UserAdd.this.getResources().getDrawable(2130837618));
          AC_UserAdd.this.m_female.setImageDrawable(AC_UserAdd.this.getResources().getDrawable(2130837615));
        }
      }
    });
    this.btn_back.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        AC_UserAdd.this.finish();
        AC_UserAdd.this.overridePendingTransition(2130968580, 2130968581);
      }
    });
    this.btn_next.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        boolean bool = AC_UserAdd.this.checkImformation();
        paramAnonymousView = new AlertDialog.Builder(AC_UserAdd.this);
        paramAnonymousView.setTitle(2131230754).setMessage(2131230755).setPositiveButton(2131230756, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            AC_UserAdd.this.addUserToDatabase();
            if (AC_UserAdd.this.isFirstUserAdd) {
              AC_UserAdd.this.startActivity(new Intent(AC_UserAdd.this, AC_MeasureExplain.class));
            }
            for (;;)
            {
              AC_UserAdd.this.finish();
              AC_UserAdd.this.overridePendingTransition(2130968580, 2130968581);
              return;
              AC_UserAdd.this.startActivity(new Intent(AC_UserAdd.this, AC_UserMain.class));
            }
          }
        }).setNegativeButton(2131230757, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
          {
            paramAnonymous2DialogInterface.dismiss();
          }
        });
        if (bool) {
          paramAnonymousView.show();
        }
      }
    });
    this.tab_measure.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = new Intent(AC_UserAdd.this, AC_MeasureExplain.class);
        AC_UserAdd.this.startActivity(paramAnonymousView);
        AC_UserAdd.this.finish();
        AC_UserAdd.this.overridePendingTransition(2130968580, 2130968581);
      }
    });
    this.tab_record.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = new Intent(AC_UserAdd.this, AC_RecordMain.class);
        AC_UserAdd.this.startActivity(paramAnonymousView);
        AC_UserAdd.this.finish();
        AC_UserAdd.this.overridePendingTransition(2130968580, 2130968581);
      }
    });
    this.tab_setting.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = new Intent(AC_UserAdd.this, AC_SettingMain.class);
        AC_UserAdd.this.startActivity(paramAnonymousView);
        AC_UserAdd.this.finish();
        AC_UserAdd.this.overridePendingTransition(2130968580, 2130968581);
      }
    });
  }
  
  protected void addUserToDatabase()
  {
    String str1 = this.m_name.getText().toString();
    String str2 = this.gender;
    String str3 = this.birthday;
    int i = this.mPref.getOwnerUID();
    long l = this.db_Adapter.addUser(i, str1, str2, str3);
    Log.e("smardi.Epi", "addedUserUid:" + l);
    this.mPref.setUserBirthday(str3);
    this.mPref.setUserGender(str2);
    this.mPref.setUserName(str1);
    this.mPref.setUserUID((int)l);
  }
  
  protected boolean checkImformation()
  {
    String str1 = this.m_name.getText().toString();
    String str2 = this.gender;
    Object localObject = this.birthday;
    localObject = this.db_Adapter.getUserList(this.mPref.getOwnerUID());
    ArrayList localArrayList1 = new ArrayList();
    int i;
    if (!((Cursor)localObject).moveToNext()) {
      i = 0;
    }
    for (;;)
    {
      if (i >= localArrayList1.size())
      {
        if (!str1.equals("")) {
          break label217;
        }
        Toast.makeText(this, getString(2131230750), 1000).show();
        return false;
        ArrayList localArrayList2 = new ArrayList();
        localArrayList2.add(((Cursor)localObject).getString(0));
        localArrayList2.add(((Cursor)localObject).getString(1));
        localArrayList2.add(((Cursor)localObject).getString(2));
        localArrayList2.add(((Cursor)localObject).getString(3));
        localArrayList1.add(localArrayList2);
        break;
      }
      if (str1.equals(((ArrayList)localArrayList1.get(i)).get(0)))
      {
        Toast.makeText(this, getString(2131230749), 1000).show();
        return false;
      }
      i += 1;
    }
    label217:
    if (str2 == null)
    {
      Toast.makeText(this, getString(2131230751), 1000).show();
      return false;
    }
    if ("1984-03-18" == null) {}
    return true;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903060);
    this.db_Adapter = new DB_Adapter(this);
    this.db_Adapter.open();
    this.mPref = new Manage_SharedPreference(this);
    this.m_name = ((EditText)findViewById(2131296293));
    this.m_birth = ((TextView)findViewById(2131296296));
    this.m_male = ((ImageButton)findViewById(2131296294));
    this.m_female = ((ImageButton)findViewById(2131296295));
    if (getString(2131230720).equals("en"))
    {
      this.m_male.setImageDrawable(getResources().getDrawable(2130837619));
      this.m_female.setImageDrawable(getResources().getDrawable(2130837613));
    }
    for (;;)
    {
      this.btn_back = ((ImageButton)findViewById(2131296287));
      this.btn_next = ((ImageButton)findViewById(2131296288));
      this.txt_subTitle = ((TextView)findViewById(2131296308));
      this.tab_menu_wrap = ((LinearLayout)findViewById(2131296386));
      this.tab_measure = ((ImageButton)findViewById(2131296367));
      this.tab_record = ((ImageButton)findViewById(2131296313));
      this.tab_user = ((ImageView)findViewById(2131296314));
      this.tab_setting = ((ImageButton)findViewById(2131296315));
      this.txt_explain_firstRegist = ((TextView)findViewById(2131296385));
      changeFont();
      registButtonEvents();
      if (!this.mPref.getUserName().equals("")) {
        break;
      }
      this.isFirstUserAdd = true;
      this.tab_menu_wrap.setVisibility(8);
      this.txt_explain_firstRegist.setText(getString(2131230748));
      this.btn_back.setImageDrawable(getResources().getDrawable(2130837584));
      return;
      if (getString(2131230720).equals("ja"))
      {
        this.m_male.setImageDrawable(getResources().getDrawable(2130837620));
        this.m_female.setImageDrawable(getResources().getDrawable(2130837614));
      }
    }
    this.txt_explain_firstRegist.setText(getString(2131230758));
    this.btn_back.setImageDrawable(getResources().getDrawable(2130837578));
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    this.db_Adapter.close();
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


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\user\AC_UserAdd.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */