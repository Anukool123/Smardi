package org.smardi.epi;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;
import org.smardi.epi.measure.AC_MeasureMain;

public class AC_CreateMain
  extends Activity
{
  static final String TAG = "epi";
  AlertDialog.Builder alert;
  ImageButton btn_back;
  ImageButton btn_check;
  ImageButton btn_next;
  Calendar calDateTime = Calendar.getInstance();
  DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
  {
    public void onDateSet(DatePicker paramAnonymousDatePicker, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      AC_CreateMain.this.calDateTime.set(1, paramAnonymousInt1);
      AC_CreateMain.this.calDateTime.set(2, paramAnonymousInt2);
      AC_CreateMain.this.calDateTime.set(5, paramAnonymousInt3);
      Object localObject = Integer.toString(paramAnonymousInt2 + 1);
      paramAnonymousDatePicker = (DatePicker)localObject;
      if (paramAnonymousInt2 + 1 < 10) {
        paramAnonymousDatePicker = "0" + (String)localObject;
      }
      String str = Integer.toString(paramAnonymousInt3);
      localObject = str;
      if (paramAnonymousInt3 < 10) {
        localObject = "0" + str;
      }
      str = Integer.toString(paramAnonymousInt1) + "-" + paramAnonymousDatePicker + "-" + (String)localObject;
      AC_CreateMain.this.m_birth.setText(str);
      AC_CreateMain.this.user_birthday = (Integer.toString(paramAnonymousInt1) + paramAnonymousDatePicker + (String)localObject);
    }
  };
  DB_Adapter dbAdapter;
  boolean isIDChecked = false;
  Manage_SharedPreference mSharedPreference;
  TextView m_birth;
  EditText m_email;
  ImageButton m_female;
  EditText m_id;
  ImageButton m_male;
  EditText m_name;
  EditText m_password;
  EditText m_password2;
  ProgressDialog pd;
  String user_birthday = "";
  String user_email = "";
  String user_gender = "";
  String user_id = "";
  String user_name = "";
  String user_password = "";
  String user_password2 = "";
  
  private void changeFont()
  {
    Typeface localTypeface = Typeface.createFromAsset(getAssets(), "fonts/MuseoSans_700.otf");
    this.m_id.setTypeface(localTypeface);
    this.m_name.setTypeface(localTypeface);
    this.m_email.setTypeface(localTypeface);
    this.m_birth.setTypeface(localTypeface);
    this.m_password.setTypeface(localTypeface);
    this.m_password2.setTypeface(localTypeface);
  }
  
  private void changeIDCheckButton(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.m_id.setEnabled(false);
      this.btn_check.setImageDrawable(getResources().getDrawable(2130837603));
      this.isIDChecked = true;
      return;
    }
    this.m_id.setEnabled(true);
    this.btn_check.setImageDrawable(getResources().getDrawable(2130837599));
    this.isIDChecked = false;
  }
  
  private void registButtonEvents()
  {
    this.m_birth.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        new DatePickerDialog(AC_CreateMain.this, AC_CreateMain.this.dateSetListener, AC_CreateMain.this.calDateTime.get(1), AC_CreateMain.this.calDateTime.get(2), AC_CreateMain.this.calDateTime.get(5)).show();
      }
    });
    this.m_male.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        AC_CreateMain.this.m_male.setImageDrawable(AC_CreateMain.this.getResources().getDrawable(2130837621));
        AC_CreateMain.this.m_female.setImageDrawable(AC_CreateMain.this.getResources().getDrawable(2130837612));
        AC_CreateMain.this.user_gender = "Male";
      }
    });
    this.m_female.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        AC_CreateMain.this.m_male.setImageDrawable(AC_CreateMain.this.getResources().getDrawable(2130837618));
        AC_CreateMain.this.m_female.setImageDrawable(AC_CreateMain.this.getResources().getDrawable(2130837615));
        AC_CreateMain.this.user_gender = "Female";
      }
    });
    this.btn_check.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (!AC_CreateMain.this.isIDChecked)
        {
          AC_CreateMain.this.checkID();
          return;
        }
        AC_CreateMain.this.changeIDCheckButton(false);
      }
    });
    this.btn_back.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        AC_CreateMain.this.finish();
      }
    });
    this.btn_next.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (AC_CreateMain.this.checkImformation())
        {
          int j = AC_CreateMain.this.dbAdapter.getOwnerUid(AC_CreateMain.this.user_id);
          int i = j;
          if (j == 0)
          {
            i = AC_CreateMain.this.dbAdapter.getMaxOwnerUid() + 1;
            AC_CreateMain.this.dbAdapter.addOwner(AC_CreateMain.this.user_id, AC_CreateMain.this.user_email);
            AC_CreateMain.this.dbAdapter.addUser(i, AC_CreateMain.this.user_name, AC_CreateMain.this.user_gender, AC_CreateMain.this.user_birthday);
          }
          AC_CreateMain.this.mSharedPreference.setOwnerUID(i);
          AC_CreateMain.this.mSharedPreference.setOwnerID(AC_CreateMain.this.user_id);
          AC_CreateMain.this.mSharedPreference.setOwnerEmail(AC_CreateMain.this.user_email);
          AC_CreateMain.this.mSharedPreference.setOwnerName(AC_CreateMain.this.user_name);
          AC_CreateMain.this.mSharedPreference.setUserName(AC_CreateMain.this.user_name);
          AC_CreateMain.this.mSharedPreference.setUserGender(AC_CreateMain.this.user_gender);
          AC_CreateMain.this.mSharedPreference.setUserBirthday(AC_CreateMain.this.user_birthday);
          paramAnonymousView = new Intent(AC_CreateMain.this, AC_MeasureMain.class);
          AC_CreateMain.this.startActivity(paramAnonymousView);
          AC_CreateMain.this.finish();
        }
      }
    });
  }
  
  protected boolean checkID()
  {
    this.user_id = this.m_id.getText().toString();
    try
    {
      this.pd = ProgressDialog.show(this, "Checking...", "Please wait.");
      this.pd.setCancelable(true);
      String str = URLEncoder.encode("user_id", "UTF-8") + "=" + URLEncoder.encode(this.user_id, "UTF-8");
      Object localObject1 = new URL("http://www.smardi.or.kr/product/idCheck.php").openConnection();
      ((URLConnection)localObject1).setDoOutput(true);
      Object localObject2 = new OutputStreamWriter(((URLConnection)localObject1).getOutputStream());
      ((OutputStreamWriter)localObject2).write(str);
      ((OutputStreamWriter)localObject2).flush();
      localObject1 = new BufferedReader(new InputStreamReader(((URLConnection)localObject1).getInputStream()));
      for (str = "";; str = str + (String)localObject2)
      {
        localObject2 = ((BufferedReader)localObject1).readLine();
        if (localObject2 == null)
        {
          this.alert.setTitle("Warning!");
          this.alert.setMessage(str.replace("<", "\n"));
          this.pd.dismiss();
          if (!str.equals("")) {
            break;
          }
          changeIDCheckButton(true);
          Toast.makeText(getApplicationContext(), "ID checking is OK", 1000).show();
          return true;
        }
      }
      this.alert.show();
      return false;
    }
    catch (Exception localException)
    {
      this.pd.dismiss();
      Log.e("epi", localException.getMessage());
      localException.printStackTrace();
    }
    return false;
  }
  
  protected boolean checkImformation()
  {
    if (!this.isIDChecked) {
      Toast.makeText(this, "Please check your ID.", 1000).show();
    }
    for (;;)
    {
      return false;
      this.user_id = this.m_id.getText().toString();
      this.user_password = this.m_password.getText().toString();
      this.user_password2 = this.m_password2.getText().toString();
      this.user_name = this.m_name.getText().toString();
      this.user_email = this.m_email.getText().toString();
      this.pd = ProgressDialog.show(this, "Loading...", "Please wait.");
      try
      {
        String str = new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(URLEncoder.encode("user_id", "UTF-8"))).append("=").append(URLEncoder.encode(this.user_id, "UTF-8")).toString())).append("&").append(URLEncoder.encode("user_password", "UTF-8")).append("=").append(URLEncoder.encode(this.user_password, "UTF-8")).toString())).append("&").append(URLEncoder.encode("user_password2", "UTF-8")).append("=").append(URLEncoder.encode(this.user_password2, "UTF-8")).toString())).append("&").append(URLEncoder.encode("user_name", "UTF-8")).append("=").append(URLEncoder.encode(this.user_name, "UTF-8")).toString())).append("&").append(URLEncoder.encode("user_gender", "UTF-8")).append("=").append(URLEncoder.encode(this.user_gender, "UTF-8")).toString())).append("&").append(URLEncoder.encode("user_email", "UTF-8")).append("=").append(URLEncoder.encode(this.user_email, "UTF-8")).toString() + "&" + URLEncoder.encode("user_birthday", "UTF-8") + "=" + URLEncoder.encode(this.user_birthday, "UTF-8");
        Log.e("epi", str);
        Object localObject1 = new URL("http://www.smardi.or.kr/product/regist.php").openConnection();
        ((URLConnection)localObject1).setDoOutput(true);
        Object localObject2 = new OutputStreamWriter(((URLConnection)localObject1).getOutputStream());
        ((OutputStreamWriter)localObject2).write(str);
        ((OutputStreamWriter)localObject2).flush();
        localObject1 = new BufferedReader(new InputStreamReader(((URLConnection)localObject1).getInputStream()));
        for (str = "";; str = str + (String)localObject2)
        {
          localObject2 = ((BufferedReader)localObject1).readLine();
          if (localObject2 == null)
          {
            this.alert.setTitle("Warning!");
            this.alert.setMessage(str.replace("<", "\n"));
            this.pd.dismiss();
            if (!str.equals("")) {
              break;
            }
            return true;
          }
        }
        return false;
      }
      catch (Exception localException)
      {
        this.alert.setTitle("Error!");
        this.alert.setMessage("Cannot connect to server!");
        this.alert.show();
        Log.e("epi", "IOException: " + localException.getMessage());
        this.pd.dismiss();
        localException.printStackTrace();
        this.alert.show();
      }
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903044);
    this.dbAdapter = new DB_Adapter(this);
    this.dbAdapter.open();
    this.mSharedPreference = new Manage_SharedPreference(this);
    this.alert = new AlertDialog.Builder(this);
    this.alert.setPositiveButton("Close", new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
      }
    });
    this.m_id = ((EditText)findViewById(2131296289));
    this.m_password = ((EditText)findViewById(2131296291));
    this.m_password2 = ((EditText)findViewById(2131296292));
    this.m_name = ((EditText)findViewById(2131296293));
    this.m_email = ((EditText)findViewById(2131296297));
    this.m_birth = ((TextView)findViewById(2131296296));
    this.m_male = ((ImageButton)findViewById(2131296294));
    this.m_female = ((ImageButton)findViewById(2131296295));
    this.btn_check = ((ImageButton)findViewById(2131296290));
    this.btn_back = ((ImageButton)findViewById(2131296287));
    this.btn_next = ((ImageButton)findViewById(2131296288));
    changeFont();
    registButtonEvents();
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    this.dbAdapter.close();
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\AC_CreateMain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */