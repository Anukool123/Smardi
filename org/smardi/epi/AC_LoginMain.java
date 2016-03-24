package org.smardi.epi;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.Editable;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import org.smardi.epi.measure.AC_MeasureMain;

public class AC_LoginMain
  extends Activity
{
  AlertDialog.Builder alert;
  ImageButton btn_create;
  ImageButton btn_login;
  DB_Adapter dbAdapter;
  EditText edit_id;
  EditText edit_password;
  long lastBackPressTime = 0L;
  Manage_SharedPreference mSharedPreference;
  ProgressDialog pd;
  Typeface tf;
  Toast toast;
  String userID;
  String userPW;
  
  private void changeFont()
  {
    this.tf = Typeface.createFromAsset(getAssets(), "fonts/MuseoSans_700.otf");
    this.edit_id.setTypeface(this.tf);
    this.edit_password.setTypeface(this.tf);
  }
  
  private void registButtonEvents()
  {
    this.btn_login.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        AC_LoginMain.this.userID = AC_LoginMain.this.edit_id.getText().toString();
        AC_LoginMain.this.userPW = AC_LoginMain.this.edit_password.getText().toString();
        if (AC_LoginMain.this.login(AC_LoginMain.this.userID, AC_LoginMain.this.userPW))
        {
          paramAnonymousView = new Intent(AC_LoginMain.this, AC_MeasureMain.class);
          AC_LoginMain.this.startActivity(paramAnonymousView);
          AC_LoginMain.this.finish();
        }
      }
    });
    this.btn_create.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        paramAnonymousView = new Intent(AC_LoginMain.this, AC_CreateMain.class);
        AC_LoginMain.this.startActivity(paramAnonymousView);
      }
    });
  }
  
  protected boolean login(String paramString1, String paramString2)
  {
    for (;;)
    {
      Object localObject3;
      int i;
      String str2;
      Object localObject4;
      try
      {
        this.pd = ProgressDialog.show(this, "Checking...", "Please wait.");
        paramString2 = new StringBuilder(String.valueOf(URLEncoder.encode("user_id", "UTF-8"))).append("=").append(URLEncoder.encode(paramString1, "UTF-8")).toString() + "&" + URLEncoder.encode("user_password", "UTF-8") + "=" + URLEncoder.encode(paramString2, "UTF-8");
        localObject1 = new URL("http://www.smardi.or.kr/product/login.php").openConnection();
        ((URLConnection)localObject1).setDoOutput(true);
        localObject2 = new OutputStreamWriter(((URLConnection)localObject1).getOutputStream());
        ((OutputStreamWriter)localObject2).write(paramString2);
        ((OutputStreamWriter)localObject2).flush();
        localObject1 = new BufferedReader(new InputStreamReader(((URLConnection)localObject1).getInputStream()));
        paramString2 = "";
        localObject2 = ((BufferedReader)localObject1).readLine();
        String str1;
        String[] arrayOfString1;
        if (localObject2 == null)
        {
          this.pd.dismiss();
          this.mSharedPreference.setOwnerID(paramString1);
          this.alert.setMessage(paramString2.replace("<", "\n"));
          localObject3 = "";
          localObject2 = "";
          localObject1 = "";
          str1 = "";
          arrayOfString1 = paramString2.split(",");
          if (arrayOfString1.length <= 1) {
            continue;
          }
          i = 0;
          paramString2 = (String)localObject3;
          if (i >= arrayOfString1.length)
          {
            this.mSharedPreference.setOwnerName(paramString2);
            this.mSharedPreference.setUserName(paramString2);
            this.mSharedPreference.setUserBirthday((String)localObject2);
            this.mSharedPreference.setUserGender((String)localObject1);
            this.mSharedPreference.setOwnerEmail(str1);
            int j = this.dbAdapter.getOwnerUid(paramString1);
            i = j;
            if (j == 0)
            {
              i = this.dbAdapter.getMaxOwnerUid() + 1;
              this.dbAdapter.addOwner(paramString1, str1);
              this.dbAdapter.addUser(i, paramString2, (String)localObject1, (String)localObject2);
            }
            this.mSharedPreference.setOwnerUID(i);
            return true;
          }
        }
        else
        {
          paramString2 = paramString2 + (String)localObject2;
          continue;
        }
        String[] arrayOfString2 = arrayOfString1[i].split(":");
        if (arrayOfString2[0].equals("user_name"))
        {
          str2 = arrayOfString2[1];
          localObject3 = localObject2;
          localObject4 = localObject1;
        }
        else if (arrayOfString2[0].equals("birthday"))
        {
          localObject3 = arrayOfString2[1];
          localObject4 = localObject1;
          str2 = paramString2;
        }
        else if (arrayOfString2[0].equals("gender"))
        {
          localObject4 = arrayOfString2[1];
          localObject3 = localObject2;
          str2 = paramString2;
        }
        else
        {
          localObject3 = localObject2;
          localObject4 = localObject1;
          str2 = paramString2;
          if (arrayOfString2[0].equals("email"))
          {
            str1 = arrayOfString2[1];
            localObject3 = localObject2;
            localObject4 = localObject1;
            str2 = paramString2;
            break label567;
            this.alert.setTitle("Warning!");
            this.alert.show();
            return false;
          }
        }
      }
      catch (Exception paramString1)
      {
        this.alert.setTitle("Error!");
        this.alert.setMessage("Cannot connect to server!");
        this.alert.show();
        paramString1.printStackTrace();
        return false;
      }
      label567:
      i += 1;
      Object localObject2 = localObject3;
      Object localObject1 = localObject4;
      paramString2 = str2;
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903048);
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
    this.edit_id = ((EditText)findViewById(2131296289));
    this.edit_password = ((EditText)findViewById(2131296291));
    this.btn_login = ((ImageButton)findViewById(2131296303));
    this.btn_create = ((ImageButton)findViewById(2131296304));
    changeFont();
    registButtonEvents();
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    this.dbAdapter.close();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramInt)
    {
    }
    for (;;)
    {
      return super.onKeyDown(paramInt, paramKeyEvent);
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
      finish();
    }
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\AC_LoginMain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */