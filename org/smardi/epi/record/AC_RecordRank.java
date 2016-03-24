package org.smardi.epi.record;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.smardi.epi.DB_Adapter;
import org.smardi.epi.measure.AC_MeasureMain;
import org.smardi.epi.setting.AC_SettingMain;
import org.smardi.epi.user.AC_UserMain;

public class AC_RecordRank
  extends Activity
{
  ImageButton btn_back;
  private DB_Adapter db_Adapter;
  String mJSONmsg;
  View.OnClickListener onClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      default: 
        return;
      case 2131296287: 
        AC_RecordRank.this.gotoRecordMain();
        return;
      case 2131296367: 
        paramAnonymousView = new Intent(AC_RecordRank.this, AC_MeasureMain.class);
        AC_RecordRank.this.startActivity(paramAnonymousView);
        AC_RecordRank.this.finish();
        AC_RecordRank.this.overridePendingTransition(2130968580, 2130968581);
        return;
      case 2131296314: 
        paramAnonymousView = new Intent(AC_RecordRank.this, AC_UserMain.class);
        AC_RecordRank.this.startActivity(paramAnonymousView);
        AC_RecordRank.this.finish();
        AC_RecordRank.this.overridePendingTransition(2130968580, 2130968581);
        return;
      }
      paramAnonymousView = new Intent(AC_RecordRank.this, AC_SettingMain.class);
      AC_RecordRank.this.startActivity(paramAnonymousView);
      AC_RecordRank.this.finish();
      AC_RecordRank.this.overridePendingTransition(2130968580, 2130968581);
    }
  };
  ImageButton tab_measure;
  ImageButton tab_setting;
  ImageButton tab_user;
  private WebView webView;
  
  private String getSavedData()
  {
    int i = 0;
    String str1 = "[{";
    Cursor localCursor1 = this.db_Adapter.fetchAllRecordNotUploaded();
    for (;;)
    {
      if (!localCursor1.moveToNext()) {
        return str1;
      }
      int j = i + 1;
      String str2 = "";
      i = 0;
      int k = localCursor1.getInt(localCursor1.getColumnIndex("uid"));
      int m = localCursor1.getInt(localCursor1.getColumnIndex("uid_scan_position"));
      Cursor localCursor2 = this.db_Adapter.getUserList(localCursor1.getInt(localCursor1.getColumnIndex("uid_user_information")));
      if (localCursor2.moveToNext())
      {
        str2 = localCursor2.getString(localCursor2.getColumnIndex("user_gender"));
        i = Integer.parseInt(localCursor2.getString(localCursor2.getColumnIndex("user_birthday")).substring(0, 4));
      }
      int n = localCursor1.getInt(localCursor1.getColumnIndex("scan_value"));
      long l = localCursor1.getLong(localCursor1.getColumnIndex("scan_time"));
      double d1 = localCursor1.getDouble(localCursor1.getColumnIndex("location_x"));
      double d2 = localCursor1.getDouble(localCursor1.getColumnIndex("location_y"));
      str1 = new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(new StringBuilder(String.valueOf(str1)).append("\"uid\":\"").append(k).append("\",").toString())).append("\"gender\":\"").append(str2).append("\",").toString())).append("\"position\":\"").append(m).append("\",").toString())).append("\"date\":\"").append(l).append("\",").toString())).append("\"value\":\"").append(n).append("\",").toString())).append("\"birthyear\":\"").append(i).append("\",").toString())).append("\"location_x\":\"").append(d1).append("\",").toString() + "\"location_y\":\"" + d2 + "\"";
      if (j < localCursor1.getCount())
      {
        str1 = str1 + "},{";
        i = j;
      }
      else
      {
        str1 = str1 + "}]";
        i = j;
      }
    }
  }
  
  private void gotoRecordMain()
  {
    startActivity(new Intent(this, AC_RecordMain.class));
    finish();
    overridePendingTransition(2130968580, 2130968581);
  }
  
  private void initComponents()
  {
    this.tab_measure = ((ImageButton)findViewById(2131296367));
    this.tab_setting = ((ImageButton)findViewById(2131296315));
    this.tab_user = ((ImageButton)findViewById(2131296314));
    this.btn_back = ((ImageButton)findViewById(2131296287));
  }
  
  private void registButtonEvents()
  {
    this.btn_back.setOnClickListener(this.onClickListener);
    this.tab_measure.setOnClickListener(this.onClickListener);
    this.tab_user.setOnClickListener(this.onClickListener);
    this.tab_setting.setOnClickListener(this.onClickListener);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903056);
    this.db_Adapter = new DB_Adapter(this);
    this.db_Adapter.open();
    this.mJSONmsg = getSavedData();
    sendJSONData();
    Object localObject = "";
    int i = 0;
    if (i >= 4)
    {
      this.webView = ((WebView)findViewById(2131296370));
      this.webView.loadUrl("http://www.smardi.or.kr/smardiProduct/Epi/viewGraph.php?userData=" + (String)localObject);
      this.webView.getSettings().setJavaScriptEnabled(true);
      this.webView.setScrollBarStyle(0);
      this.webView.setWebChromeClient(new WebChromeClient()
      {
        public boolean onJsAlert(WebView paramAnonymousWebView, String paramAnonymousString1, String paramAnonymousString2, final JsResult paramAnonymousJsResult)
        {
          new AlertDialog.Builder(AC_RecordRank.this).setTitle("AlertDialog").setMessage(paramAnonymousString2).setPositiveButton(17039370, new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
            {
              paramAnonymousJsResult.confirm();
            }
          }).setCancelable(false).create().show();
          return true;
        }
      });
      initComponents();
      registButtonEvents();
      return;
    }
    Cursor localCursor = this.db_Adapter.fetchAllRecordByScanningPoint(String.valueOf(i));
    paramBundle = (Bundle)localObject;
    if (localCursor.moveToLast()) {
      if (i <= 0) {
        break label212;
      }
    }
    label212:
    for (paramBundle = localObject + "," + localCursor.getString(localCursor.getColumnIndex("scan_value"));; paramBundle = localObject + localCursor.getString(localCursor.getColumnIndex("scan_value")))
    {
      i += 1;
      localObject = paramBundle;
      break;
    }
  }
  
  protected void onDestroy()
  {
    this.db_Adapter.close();
    super.onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    switch (paramInt)
    {
    }
    for (;;)
    {
      return super.onKeyDown(paramInt, paramKeyEvent);
      if (this.webView.canGoBack())
      {
        this.webView.goBack();
        return false;
      }
      gotoRecordMain();
    }
  }
  
  public void sendJSONData()
  {
    try
    {
      Object localObject1 = (HttpURLConnection)new URL("http://www.smardi.or.kr/smardiProduct/Epi/collectScanData.php").openConnection();
      ((HttpURLConnection)localObject1).setDefaultUseCaches(false);
      ((HttpURLConnection)localObject1).setDoInput(true);
      ((HttpURLConnection)localObject1).setDoOutput(true);
      ((HttpURLConnection)localObject1).setRequestMethod("POST");
      ((HttpURLConnection)localObject1).setRequestProperty("content-type", "application/x-www-form-urlencoded");
      Object localObject2 = new StringBuffer();
      ((StringBuffer)localObject2).append("data").append("=").append(this.mJSONmsg);
      Object localObject3 = new PrintWriter(new OutputStreamWriter(((HttpURLConnection)localObject1).getOutputStream(), "UTF-8"));
      ((PrintWriter)localObject3).write(((StringBuffer)localObject2).toString());
      ((PrintWriter)localObject3).flush();
      localObject1 = new BufferedReader(new InputStreamReader(((HttpURLConnection)localObject1).getInputStream(), "UTF-8"));
      localObject2 = new StringBuilder();
      localObject3 = ((BufferedReader)localObject1).readLine();
      int i;
      if (localObject3 == null)
      {
        localObject1 = ((StringBuilder)localObject2).toString();
        localObject2 = ((String)localObject1).split(",");
        i = 0;
      }
      for (;;)
      {
        if (i >= localObject2.length - 1)
        {
          ((TextView)findViewById(2131296371)).setText((CharSequence)localObject1);
          return;
          ((StringBuilder)localObject2).append(localObject3 + "\n");
          break;
        }
        this.db_Adapter.updateResultDataUploading(Integer.parseInt(localObject2[i]));
        i += 1;
      }
      return;
    }
    catch (IOException localIOException)
    {
      return;
    }
    catch (MalformedURLException localMalformedURLException) {}
  }
  
  private class DraptWebViewClient
    extends WebViewClient
  {
    private DraptWebViewClient() {}
    
    public boolean shouldOverrideUrlLoading(WebView paramWebView, String paramString)
    {
      paramWebView.loadUrl(paramString);
      return true;
    }
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\record\AC_RecordRank.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */