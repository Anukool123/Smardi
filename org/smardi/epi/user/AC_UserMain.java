package org.smardi.epi.user;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Process;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Date;
import org.smardi.epi.DB_Adapter;
import org.smardi.epi.Manage_SharedPreference;
import org.smardi.epi.measure.AC_MeasureExplain;
import org.smardi.epi.record.AC_RecordMain;
import org.smardi.epi.setting.AC_SettingMain;

public class AC_UserMain
  extends Activity
{
  final int NAME_BOTTOM = 2;
  final int NAME_MIDDLE = 1;
  final int NAME_TOP = 0;
  ImageButton btn_back;
  ImageButton btn_next;
  ImageButton btn_userAdd;
  ImageButton btn_userChange;
  ImageButton btn_userDelete;
  DB_Adapter dbAdapter;
  long doubleClickTime = 500L;
  boolean isUserWantToExit = false;
  long lastBackPressTime = 0L;
  long lastClickedNameTime = 0L;
  LinearLayout linear_userlist;
  ArrayList<TextView> listName;
  ArrayList<Button> listPoint;
  ArrayList<ArrayList<String>> listUser;
  Manage_SharedPreference mSharedPreference;
  View.OnClickListener onClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      }
      int i;
      for (;;)
      {
        if (paramAnonymousView.getId() > 999)
        {
          i = 0;
          if (i < AC_UserMain.this.listName.size()) {
            break;
          }
        }
        return;
        AC_UserMain.this.gotoBack();
        continue;
        AC_UserMain.this.gotoMeasure();
        continue;
        if (AC_UserMain.this.selectedNameID < 0)
        {
          Toast.makeText(AC_UserMain.this, AC_UserMain.this.getString(2131230770), 1000).show();
        }
        else
        {
          Object localObject = new Intent(AC_UserMain.this, AC_UserChange.class);
          ((Intent)localObject).putExtra("user_uid", Integer.parseInt((String)((ArrayList)AC_UserMain.this.listUser.get(AC_UserMain.this.selectedNameID)).get(3)));
          AC_UserMain.this.startActivity((Intent)localObject);
          AC_UserMain.this.overridePendingTransition(2130968580, 2130968581);
          continue;
          if (AC_UserMain.this.selectedNameID >= 0)
          {
            if (AC_UserMain.this.selectedName.equals(AC_UserMain.this.mSharedPreference.getUserName()))
            {
              Toast.makeText(AC_UserMain.this, AC_UserMain.this.getString(2131230772), 1000).show();
            }
            else
            {
              localObject = new AlertDialog.Builder(AC_UserMain.this);
              ((AlertDialog.Builder)localObject).setTitle(AC_UserMain.this.getString(2131230773) + " " + AC_UserMain.this.mSharedPreference.getUserName());
              ((AlertDialog.Builder)localObject).setMessage(AC_UserMain.this.getString(2131230774));
              ((AlertDialog.Builder)localObject).setNegativeButton(AC_UserMain.this.getString(2131230769), new DialogInterface.OnClickListener()
              {
                public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                {
                  paramAnonymous2DialogInterface.dismiss();
                }
              });
              ((AlertDialog.Builder)localObject).setPositiveButton(AC_UserMain.this.getString(2131230767), new DialogInterface.OnClickListener()
              {
                public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                {
                  paramAnonymous2Int = Integer.parseInt((String)((ArrayList)AC_UserMain.this.listUser.get(AC_UserMain.this.selectedNameID)).get(3));
                  AC_UserMain.this.dbAdapter.deleteUser(paramAnonymous2Int);
                  Toast.makeText(AC_UserMain.this, "Deleted.", 1000).show();
                  AC_UserMain.this.makeUserList();
                }
              });
              ((AlertDialog.Builder)localObject).show();
            }
          }
          else
          {
            Toast.makeText(AC_UserMain.this, AC_UserMain.this.getString(2131230775), 1000).show();
            continue;
            localObject = new Intent(AC_UserMain.this, AC_UserAdd.class);
            AC_UserMain.this.startActivity((Intent)localObject);
            AC_UserMain.this.overridePendingTransition(2130968580, 2130968581);
            continue;
            localObject = new Intent(AC_UserMain.this, AC_MeasureExplain.class);
            AC_UserMain.this.startActivity((Intent)localObject);
            AC_UserMain.this.finish();
            AC_UserMain.this.overridePendingTransition(2130968580, 2130968581);
            continue;
            localObject = new Intent(AC_UserMain.this, AC_RecordMain.class);
            AC_UserMain.this.startActivity((Intent)localObject);
            AC_UserMain.this.finish();
            AC_UserMain.this.overridePendingTransition(2130968580, 2130968581);
            continue;
            localObject = new Intent(AC_UserMain.this, AC_SettingMain.class);
            AC_UserMain.this.startActivity((Intent)localObject);
            AC_UserMain.this.finish();
            AC_UserMain.this.overridePendingTransition(2130968580, 2130968581);
          }
        }
      }
      long l;
      if (paramAnonymousView.getId() == i + 1000)
      {
        AC_UserMain.this.selectedName = ((TextView)AC_UserMain.this.listName.get(i)).getText().toString();
        l = new Date().getTime();
        if ((AC_UserMain.this.selectedNameID == i) && (l - AC_UserMain.this.lastClickedNameTime < AC_UserMain.this.doubleClickTime))
        {
          AC_UserMain.this.gotoMeasure();
          label698:
          ((TextView)AC_UserMain.this.listName.get(i)).setTextColor(Color.parseColor("#9E0E10"));
        }
      }
      for (;;)
      {
        i += 1;
        break;
        AC_UserMain.this.selectedNameID = i;
        AC_UserMain.this.lastClickedNameTime = l;
        break label698;
        ((TextView)AC_UserMain.this.listName.get(i)).setTextColor(Color.parseColor("#4c483f"));
      }
    }
  };
  int scanPoint = 0;
  String selectedName = "";
  int selectedNameID = -1;
  ImageButton tab_measure;
  ImageButton tab_record;
  ImageButton tab_setting;
  Toast toast;
  TextView txt_explain;
  TextView txt_subTitle;
  
  private void changeFont()
  {
    Typeface.createFromAsset(getAssets(), "fonts/MuseoSans_700.otf");
  }
  
  private int convertDPtoPX(double paramDouble)
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    return (int)(localDisplayMetrics.density * paramDouble + 0.5D);
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
  
  private Typeface getFont()
  {
    return Typeface.createFromAsset(getAssets(), "fonts/Daum_Regular.ttf");
  }
  
  private TextView getNameTD(String paramString, int paramInt1, int paramInt2)
  {
    Typeface.createFromAsset(getAssets(), "fonts/MuseoSans_700.otf");
    int i = 0;
    int j = 0;
    Drawable localDrawable = getResources().getDrawable(2130837511);
    TextView localTextView = new TextView(getApplicationContext());
    switch (paramInt1)
    {
    default: 
      paramInt1 = j;
      localTextView.setLayoutParams(new LinearLayout.LayoutParams(i, paramInt1, 0.0F));
      localTextView.setBackgroundDrawable(localDrawable);
      localTextView.setGravity(17);
      if (this.mSharedPreference.getUserName().equals(paramString)) {
        localTextView.setText(Html.fromHtml("<b>" + paramString + "</b>"));
      }
      break;
    }
    for (;;)
    {
      localTextView.setTypeface(getFont());
      localTextView.setTextColor(Color.parseColor("#4c483f"));
      localTextView.setTextSize(14.0F);
      localTextView.setTypeface(getFont());
      localTextView.setId(paramInt2);
      localTextView.setOnClickListener(this.onClickListener);
      this.listName.add(localTextView);
      return localTextView;
      i = convertDPtoPX(231.855D);
      paramInt1 = convertDPtoPX(39.30875D);
      localDrawable = getResources().getDrawable(2130837512);
      break;
      i = convertDPtoPX(231.855D);
      paramInt1 = convertDPtoPX(39.30875D);
      localDrawable = getResources().getDrawable(2130837511);
      break;
      i = convertDPtoPX(231.855D);
      paramInt1 = convertDPtoPX(37.97625D);
      localDrawable = getResources().getDrawable(2130837510);
      break;
      localTextView.setText(paramString);
    }
  }
  
  private void gotoBack()
  {
    startActivity(new Intent(this, AC_MeasureExplain.class));
    finish();
    overridePendingTransition(2130968580, 2130968581);
  }
  
  private void gotoMeasure()
  {
    if (this.selectedNameID >= 0)
    {
      String str1 = (String)((ArrayList)this.listUser.get(this.selectedNameID)).get(0);
      String str2 = (String)((ArrayList)this.listUser.get(this.selectedNameID)).get(1);
      String str3 = (String)((ArrayList)this.listUser.get(this.selectedNameID)).get(2);
      String str4 = (String)((ArrayList)this.listUser.get(this.selectedNameID)).get(3);
      this.mSharedPreference.setUserName(str1);
      this.mSharedPreference.setUserGender(str2);
      this.mSharedPreference.setUserBirthday(str3);
      this.mSharedPreference.setUserUID(Integer.parseInt(str4));
      startActivity(new Intent(this, AC_MeasureExplain.class));
      finish();
      overridePendingTransition(2130968580, 2130968581);
      return;
    }
    Toast.makeText(this, getString(2131230775), 1000).show();
  }
  
  private void makeUserList()
  {
    Cursor localCursor = this.dbAdapter.getUserList(this.mSharedPreference.getOwnerUID());
    this.listUser = new ArrayList();
    int i;
    for (;;)
    {
      if (!localCursor.moveToNext())
      {
        this.listName = new ArrayList();
        this.linear_userlist = ((LinearLayout)findViewById(2131296389));
        this.linear_userlist.removeAllViews();
        i = 0;
        if (i < this.listUser.size()) {
          break;
        }
        if (this.listUser.size() == 1) {
          this.linear_userlist.getChildAt(0).setBackgroundDrawable(getResources().getDrawable(2130837506));
        }
        return;
      }
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(localCursor.getString(0));
      localArrayList.add(localCursor.getString(1));
      localArrayList.add(localCursor.getString(2));
      localArrayList.add(localCursor.getString(3));
      this.listUser.add(localArrayList);
    }
    if (i == 0) {
      this.linear_userlist.addView(getNameTD((String)((ArrayList)this.listUser.get(i)).get(0), 0, i + 1000));
    }
    for (;;)
    {
      i += 1;
      break;
      if ((i > 0) && (i < this.listUser.size() - 1)) {
        this.linear_userlist.addView(getNameTD((String)((ArrayList)this.listUser.get(i)).get(0), 1, i + 1000));
      } else {
        this.linear_userlist.addView(getNameTD((String)((ArrayList)this.listUser.get(i)).get(0), 2, i + 1000));
      }
    }
  }
  
  private void registButtonEvents()
  {
    this.btn_back.setOnClickListener(this.onClickListener);
    this.btn_next.setOnClickListener(this.onClickListener);
    this.tab_measure.setOnClickListener(this.onClickListener);
    this.tab_record.setOnClickListener(this.onClickListener);
    this.tab_setting.setOnClickListener(this.onClickListener);
    this.btn_userChange.setOnClickListener(this.onClickListener);
    this.btn_userDelete.setOnClickListener(this.onClickListener);
    this.btn_userAdd.setOnClickListener(this.onClickListener);
  }
  
  private void setExplainText()
  {
    this.txt_explain.setText(getString(2131230771));
    this.txt_explain.setTypeface(getFont());
    this.txt_subTitle.setTypeface(getFont());
  }
  
  private void setUserName() {}
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903062);
    this.dbAdapter = new DB_Adapter(this);
    this.dbAdapter.open();
    this.btn_back = ((ImageButton)findViewById(2131296287));
    this.btn_next = ((ImageButton)findViewById(2131296288));
    this.txt_explain = ((TextView)findViewById(2131296309));
    this.txt_subTitle = ((TextView)findViewById(2131296308));
    this.btn_userChange = ((ImageButton)findViewById(2131296390));
    this.btn_userDelete = ((ImageButton)findViewById(2131296391));
    this.btn_userAdd = ((ImageButton)findViewById(2131296392));
    this.tab_measure = ((ImageButton)findViewById(2131296367));
    this.tab_record = ((ImageButton)findViewById(2131296313));
    this.tab_setting = ((ImageButton)findViewById(2131296315));
    this.mSharedPreference = new Manage_SharedPreference(this);
    makeUserList();
    registButtonEvents();
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    this.dbAdapter.close();
    if (this.isUserWantToExit)
    {
      moveTaskToBack(true);
      Process.killProcess(Process.myPid());
    }
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramInt)
    {
    }
    for (;;)
    {
      return super.onKeyDown(paramInt, paramKeyEvent);
      gotoBack();
    }
  }
  
  protected void onResume()
  {
    super.onResume();
    this.selectedNameID = -1;
    setExplainText();
    makeUserList();
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\user\AC_UserMain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */