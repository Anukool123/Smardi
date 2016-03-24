package org.smardi.epi.record;

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
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Process;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableLayout.LayoutParams;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.smardi.epi.DB_Adapter;
import org.smardi.epi.Manage_SharedPreference;
import org.smardi.epi.graph.MyView;
import org.smardi.epi.measure.AC_MeasureExplain;
import org.smardi.epi.measure.AC_MeasureMain;
import org.smardi.epi.setting.AC_SettingMain;
import org.smardi.epi.user.AC_UserMain;

public class AC_RecordMain
  extends Activity
{
  private boolean D = false;
  private String[] DATETYPE_ARRAY = null;
  private final int DATETYPE_DAYLY = 1;
  private final int DATETYPE_MONTHLY = 0;
  private final int DISPLAY_LAST_24HOURS = 1;
  private final int DISPLAY_LAST_30DAYS = 0;
  private final int DISPLAY_SPECIPIC_DAY = 3;
  private final int DISPLAY_SPECIPIC_MONTH = 2;
  private final long DOUBLE_CLICK_TIME = 500L;
  private final int RESULTVIEW_CHART = 0;
  private final int RESULTVIEW_GRAPH = 1;
  ImageButton btn_back;
  ImageButton btn_chart_graph;
  ImageButton btn_ranking;
  private int dataCount = 0;
  DB_Adapter dbAdapter;
  private int displayInformationType = -1;
  LinearLayout graphLayout;
  HorizontalScrollView graphScroll = null;
  private boolean isDrawnGraph = false;
  boolean isPause = false;
  boolean isUserWantToExit = false;
  long lastBackPressTime = 0L;
  long lastClickedNameTime = 0L;
  Manage_SharedPreference mPref;
  View.OnClickListener onClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      default: 
        return;
      case 2131296287: 
        AC_RecordMain.this.gotoMeasureExplain();
        return;
      case 2131296357: 
        AC_RecordMain.this.showUserChoiceDialog();
        return;
      case 2131296358: 
        AC_RecordMain.this.showPlaceChoiceDialog();
        return;
      case 2131296359: 
        AC_RecordMain.this.showDateTypeChoiceDialog();
        return;
      }
      label266:
      label510:
      label520:
      label527:
      label534:
      for (;;)
      {
        int k;
        int i;
        int m;
        int j;
        try
        {
          paramAnonymousView = new DatePickerDialog(AC_RecordMain.this, AC_RecordMain.this.onDateSetListener, AC_RecordMain.this.selectedYear, AC_RecordMain.this.selectedMonth - 1, AC_RecordMain.this.selectedDay);
          if (AC_RecordMain.this.selectedDateType == 0)
          {
            arrayOfField = paramAnonymousView.getClass().getDeclaredFields();
            k = arrayOfField.length;
            i = 0;
            break label510;
          }
          paramAnonymousView.show();
          return;
        }
        catch (IllegalArgumentException paramAnonymousView)
        {
          Field[] arrayOfField;
          paramAnonymousView.printStackTrace();
          return;
          Object localObject1 = arrayOfField[i];
          if (!((Field)localObject1).getName().equals("mDatePicker")) {
            break label527;
          }
          ((Field)localObject1).setAccessible(true);
          DatePicker localDatePicker = (DatePicker)((Field)localObject1).get(paramAnonymousView);
          localObject1 = ((Field)localObject1).getType().getDeclaredFields();
          m = localObject1.length;
          j = 0;
          break label520;
          Object localObject2 = localObject1[j];
          if ("mDayPicker".equals(((Field)localObject2).getName()))
          {
            ((Field)localObject2).setAccessible(true);
            new Object();
            ((View)((Field)localObject2).get(localDatePicker)).setVisibility(8);
          }
          j += 1;
        }
        catch (IllegalAccessException paramAnonymousView)
        {
          paramAnonymousView.printStackTrace();
          return;
        }
        if (!AC_RecordMain.this.mPref.getAskedLocationPermission())
        {
          paramAnonymousView = new AlertDialog.Builder(AC_RecordMain.this);
          paramAnonymousView.setTitle("위치정보 사용 승인");
          paramAnonymousView.setMessage("\"Epi\"에서 현재 위치 정보를 사용하고자 합니다.");
          paramAnonymousView.setNegativeButton("취소", new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
            {
              AC_RecordMain.this.mPref.setGetLocationPermission(false);
              AC_RecordMain.this.mPref.setAskedLocationPermission(true);
              AC_RecordMain.this.gotoOnlineRanking();
            }
          });
          paramAnonymousView.setPositiveButton("승인", new DialogInterface.OnClickListener()
          {
            public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
            {
              AC_RecordMain.this.mPref.setGetLocationPermission(true);
              AC_RecordMain.this.mPref.setAskedLocationPermission(true);
              AC_RecordMain.this.gotoOnlineRanking();
            }
          });
          paramAnonymousView.show();
          return;
        }
        AC_RecordMain.this.gotoOnlineRanking();
        return;
        AC_RecordMain.this.graphScroll.fullScroll(66);
        switch (AC_RecordMain.this.selectedResultView)
        {
        }
        for (;;)
        {
          AC_RecordMain.this.changeResultView();
          return;
          AC_RecordMain.this.selectedResultView = 1;
          continue;
          AC_RecordMain.this.selectedResultView = 0;
        }
        AC_RecordMain.this.gotoMeasureExplain();
        return;
        AC_RecordMain.this.gotoUserMain();
        return;
        AC_RecordMain.this.gotoSettingMain();
        return;
        for (;;)
        {
          if (i < k) {
            break label534;
          }
          break;
          if (j < m) {
            break label266;
          }
          i += 1;
        }
      }
    }
  };
  DatePickerDialog.OnDateSetListener onDateSetListener = new DatePickerDialog.OnDateSetListener()
  {
    public void onDateSet(DatePicker paramAnonymousDatePicker, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
    {
      AC_RecordMain.this.selectedYear = paramAnonymousInt1;
      AC_RecordMain.this.selectedMonth = (paramAnonymousInt2 + 1);
      AC_RecordMain.this.selectedDay = paramAnonymousInt3;
      switch (AC_RecordMain.this.displayInformationType)
      {
      }
      for (;;)
      {
        AC_RecordMain.this.loadData_from_Database_for_Table_and_Graph();
        return;
        AC_RecordMain.this.displayInformationType = 2;
        continue;
        AC_RecordMain.this.displayInformationType = 3;
      }
    }
  };
  DisplayMetrics outMetrics = new DisplayMetrics();
  ScrollView scrollResult;
  private int selectedDateType = 0;
  private int selectedDay = 0;
  private int selectedMonth = 0;
  private int selectedResultView = 1;
  private int selectedScanPlace = 0;
  private int selectedUserUID = 0;
  private int selectedYear = 0;
  ImageButton tab_measure;
  ImageButton tab_setting;
  ImageButton tab_user;
  TableLayout table_resultList;
  Toast toast;
  TextView txt_date;
  TextView txt_panel_date;
  TextView txt_place;
  TextView txt_subTitle;
  TextView txt_userName;
  
  private void changeFont()
  {
    Typeface localTypeface = Typeface.createFromAsset(getAssets(), "fonts/Daum_Regular.ttf");
    this.txt_panel_date.setTypeface(localTypeface);
    this.txt_subTitle.setTypeface(localTypeface);
    this.txt_userName.setTypeface(localTypeface);
    this.txt_place.setTypeface(localTypeface);
    this.txt_date.setTypeface(localTypeface);
  }
  
  private int convertDPtoPX(double paramDouble)
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    return (int)(localDisplayMetrics.density * paramDouble + 0.5D);
  }
  
  private void displaySelectedTime()
  {
    switch (this.selectedDateType)
    {
    default: 
      return;
    case 0: 
      this.txt_panel_date.setText(this.selectedYear + "-" + this.selectedMonth);
      return;
    }
    this.txt_panel_date.setText(this.selectedYear + "-" + this.selectedMonth + "-" + this.selectedDay);
  }
  
  private void drawMyGraph(int paramInt1, int paramInt2, float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4, ArrayList<ArrayList<float[]>> paramArrayList, ArrayList<Integer> paramArrayList1, ArrayList<String> paramArrayList2, Cursor paramCursor)
  {
    this.graphLayout.removeAllViews();
    Object localObject = (LinearLayout.LayoutParams)this.graphLayout.getLayoutParams();
    paramArrayList = new HorizontalScrollView(this);
    paramArrayList.setLayoutParams(new TableRow.LayoutParams(-1, ((LinearLayout.LayoutParams)localObject).height));
    paramArrayList.setFadingEdgeLength(0);
    this.graphLayout.addView(paramArrayList);
    paramArrayList1 = new LinearLayout(this);
    paramArrayList1.setLayoutParams(new TableRow.LayoutParams((int)(paramInt1 * this.outMetrics.density), -2));
    paramArrayList1.setOrientation(0);
    paramArrayList1.setPadding(0, 0, 0, 0);
    paramArrayList.addView(paramArrayList1);
    long l2 = 0L;
    long l1 = 0L;
    paramArrayList2 = new Date();
    paramInt2 = new GregorianCalendar(this.selectedYear, this.selectedMonth, 1).getActualMaximum(5);
    paramInt1 = -1;
    long l3;
    switch (this.displayInformationType)
    {
    default: 
      l3 = 0L;
      switch (paramInt1)
      {
      default: 
        label224:
        paramInt2 = (int)((l1 - l2) / l3 * 50L);
        paramArrayList2 = new MyView(this, getWindow(), paramInt2, (int)(((LinearLayout.LayoutParams)localObject).height / this.outMetrics.density * 1.1D) - (int)(15.0F * this.outMetrics.density), paramFloat1, paramFloat2, paramFloat3, paramFloat4);
        paramArrayList2.setLayoutParams(new TableRow.LayoutParams(Math.round(paramInt2 * this.outMetrics.density), ((LinearLayout.LayoutParams)localObject).height));
        switch (paramInt1)
        {
        default: 
          label352:
          localObject = new ArrayList();
        }
        break;
      }
      break;
    }
    long l4;
    for (;;)
    {
      if (!paramCursor.moveToNext())
      {
        paramInt2 = 0;
        paramArrayList2.makeGraphData();
        l4 = 0L;
        if (l4 * l3 + l2 <= l1 + l3) {
          break label812;
        }
        paramArrayList2.saveGraphData(-7829368, "");
        if (this.D)
        {
          paramInt1 = 0;
          if (paramInt1 < 1) {
            break label956;
          }
        }
        paramArrayList1.addView(paramArrayList2);
        this.graphScroll = paramArrayList;
        new CountDownTimer(100L, 10L)
        {
          public void onFinish()
          {
            AC_RecordMain.this.graphScroll.fullScroll(66);
          }
          
          public void onTick(long paramAnonymousLong) {}
        }.start();
        return;
        l1 = new Date(paramArrayList2.getYear(), paramArrayList2.getMonth(), paramArrayList2.getDate(), 23, 59, 59).getTime() + 1000L;
        l2 = l1 - 2592000000L;
        paramInt1 = 0;
        break;
        l2 = new Date(this.selectedYear - 1900, this.selectedMonth - 1, 1).getTime();
        l1 = new Date(this.selectedYear - 1900, this.selectedMonth - 1, paramInt2, 23, 59, 59).getTime() + 1000L;
        paramInt1 = 0;
        break;
        l1 = new Date(paramArrayList2.getYear(), paramArrayList2.getMonth(), paramArrayList2.getDate(), paramArrayList2.getHours() + 1, 0, 0).getTime();
        l2 = l1 - 86400000L;
        paramInt1 = 2;
        break;
        l2 = new Date(this.selectedYear - 1900, this.selectedMonth - 1, this.selectedDay, 0, 0, 0).getTime();
        l1 = new Date(this.selectedYear - 1900, this.selectedMonth - 1, this.selectedDay + 1, 0, 0, 0).getTime();
        paramInt1 = 2;
        break;
        l3 = 86400000L;
        break label224;
        l3 = 600000L;
        break label224;
        l3 = 3600000L;
        break label224;
        paramArrayList2.setXUnit(1);
        break label352;
        paramArrayList2.setXUnit(2);
        break label352;
        paramArrayList2.setXUnit(2);
        break label352;
      }
      databaseData localdatabaseData = new databaseData();
      localdatabaseData.setTime(paramCursor.getLong(paramCursor.getColumnIndex("scan_time")));
      localdatabaseData.setValue(paramCursor.getInt(paramCursor.getColumnIndex("scan_value")));
      ((ArrayList)localObject).add(localdatabaseData);
    }
    label812:
    int i = 0;
    int j = 0;
    int k = 0;
    while ((paramInt2 < ((ArrayList)localObject).size()) && (((databaseData)((ArrayList)localObject).get(paramInt2)).getTime() < l4 * l3 + l2))
    {
      j += ((databaseData)((ArrayList)localObject).get(paramInt2)).getValue();
      paramInt2 += 1;
      i += 1;
    }
    if (i > 0) {
      k = j / i;
    }
    if (l4 > 0L)
    {
      if (paramInt1 != 1) {
        break label935;
      }
      paramArrayList2.addGraphData((l4 - 1L) * l3 + l2, k);
    }
    for (;;)
    {
      l4 += 1L;
      break;
      label935:
      paramArrayList2.addGraphData((l4 - 1L) * l3 + l2, k);
    }
    label956:
    paramArrayList2.makeGraphData();
    paramInt2 = 0;
    for (;;)
    {
      if (paramInt2 >= 100)
      {
        paramArrayList2.saveGraphData(-12303292, "Graph" + paramInt1);
        paramInt1 += 1;
        break;
      }
      paramArrayList2.addGraphData(paramInt2, (float)(Math.random() * 100.0D));
      paramInt2 += 1;
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
  
  private Typeface getFontDaum()
  {
    return Typeface.createFromAsset(getAssets(), "fonts/Daum_Regular.ttf");
  }
  
  private long getTimestamp(String paramString)
  {
    try
    {
      long l = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(paramString).getTime();
      return l;
    }
    catch (ParseException paramString)
    {
      paramString.printStackTrace();
    }
    return 0L;
  }
  
  private void gotoMeasureExplain()
  {
    startActivity(new Intent(this, AC_MeasureExplain.class));
    finish();
    overridePendingTransition(2130968580, 2130968581);
  }
  
  private void gotoMeasureMain()
  {
    startActivity(new Intent(this, AC_MeasureMain.class));
    finish();
    overridePendingTransition(2130968580, 2130968581);
  }
  
  private void gotoOnlineRanking()
  {
    if (isOnline())
    {
      gotoRanking();
      return;
    }
    Toast.makeText(this, "Internet connection needed!", 1000).show();
    ((Vibrator)getSystemService("vibrator")).vibrate(500L);
  }
  
  private void gotoRanking()
  {
    startActivity(new Intent(this, AC_RecordRank.class));
    finish();
    overridePendingTransition(2130968580, 2130968581);
  }
  
  private void gotoSettingMain()
  {
    startActivity(new Intent(this, AC_SettingMain.class));
    finish();
    overridePendingTransition(2130968580, 2130968581);
  }
  
  private void gotoUserMain()
  {
    startActivity(new Intent(this, AC_UserMain.class));
    finish();
    overridePendingTransition(2130968580, 2130968581);
  }
  
  private void initComponents()
  {
    this.DATETYPE_ARRAY = new String[] { getString(2131230730), getString(2131230732) };
    this.mPref = new Manage_SharedPreference(this);
    this.dbAdapter = new DB_Adapter(this);
    this.dbAdapter.open();
    this.txt_userName = ((TextView)findViewById(2131296357));
    this.txt_place = ((TextView)findViewById(2131296358));
    this.txt_date = ((TextView)findViewById(2131296359));
    this.txt_subTitle = ((TextView)findViewById(2131296308));
    this.txt_panel_date = ((TextView)findViewById(2131296361));
    this.tab_measure = ((ImageButton)findViewById(2131296367));
    this.tab_setting = ((ImageButton)findViewById(2131296315));
    this.tab_user = ((ImageButton)findViewById(2131296314));
    this.btn_ranking = ((ImageButton)findViewById(2131296365));
    this.btn_chart_graph = ((ImageButton)findViewById(2131296366));
    this.table_resultList = ((TableLayout)findViewById(2131296363));
    this.scrollResult = ((ScrollView)findViewById(2131296362));
    this.graphLayout = ((LinearLayout)findViewById(2131296364));
    this.btn_back = ((ImageButton)findViewById(2131296287));
  }
  
  private void initData()
  {
    this.selectedUserUID = this.mPref.getUserUID();
    this.selectedScanPlace = this.mPref.getScanningPoint();
    this.selectedDateType = 0;
  }
  
  private void initSelectedTime()
  {
    Calendar localCalendar = Calendar.getInstance();
    this.selectedYear = localCalendar.get(1);
    this.selectedMonth = (localCalendar.get(2) + 1);
    this.selectedDay = localCalendar.get(5);
  }
  
  private void initText()
  {
    this.txt_userName.setText(this.mPref.getUserName());
    this.txt_place.setText(this.mPref.getScanningPointExplain());
    this.txt_date.setText(getString(2131230730));
  }
  
  private void loadData_from_Database_for_Table_and_Graph()
  {
    displaySelectedTime();
    Cursor localCursor = null;
    switch (this.displayInformationType)
    {
    default: 
      loadTable(localCursor);
      switch (this.displayInformationType)
      {
      }
      break;
    }
    for (;;)
    {
      drawMyGraph(2000, 200, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, 0.0F, 110.0F, null, null, null, localCursor);
      return;
      localCursor = this.dbAdapter.fetchAllRecordByUserAndScanningPointLast30days(this.selectedUserUID, this.selectedScanPlace);
      break;
      localCursor = this.dbAdapter.fetchAllRecordByUserAndScanningPointLast24hours(this.selectedUserUID, this.selectedScanPlace);
      break;
      localCursor = this.dbAdapter.fetchAllRecordByUserAndScanningPointAtSomeMonth(this.selectedUserUID, this.selectedScanPlace, this.selectedYear, this.selectedMonth);
      break;
      localCursor = this.dbAdapter.fetchAllRecordByUserAndScanningPointAtDay(this.selectedUserUID, this.selectedScanPlace, this.selectedYear, this.selectedMonth, this.selectedDay);
      break;
      localCursor = this.dbAdapter.fetchAllRecordByUserAndScanningPointLast30days(this.selectedUserUID, this.selectedScanPlace);
      continue;
      localCursor = this.dbAdapter.fetchAllRecordByUserAndScanningPointLast24hours(this.selectedUserUID, this.selectedScanPlace);
      continue;
      localCursor = this.dbAdapter.fetchAllRecordByUserAndScanningPointAtSomeMonth(this.selectedUserUID, this.selectedScanPlace, this.selectedYear, this.selectedMonth);
      continue;
      localCursor = this.dbAdapter.fetchAllRecordByUserAndScanningPointAtDay(this.selectedUserUID, this.selectedScanPlace, this.selectedYear, this.selectedMonth, this.selectedDay);
    }
  }
  
  private void loadTable(Cursor paramCursor)
  {
    Object localObject1 = new SimpleDateFormat("MM-dd HH:mm");
    getWindowManager().getDefaultDisplay().getMetrics(this.outMetrics);
    float f = this.outMetrics.density;
    this.table_resultList.removeAllViews();
    new TableRow(this);
    new TextView(this);
    new TextView(this);
    int i = 0;
    for (;;)
    {
      if (!paramCursor.moveToNext())
      {
        this.dataCount = paramCursor.getCount();
        if (this.dataCount < 0) {
          i = this.dataCount;
        }
        this.dataCount = i;
        if (i == 0)
        {
          this.dataCount = 0;
          paramCursor = new TableRow(this);
          paramCursor.setGravity(17);
          paramCursor.setLayoutParams(new TableRow.LayoutParams(-1, -2));
          localObject1 = new TextView(this);
          ((TextView)localObject1).setText("None");
          ((TextView)localObject1).setGravity(17);
          ((TextView)localObject1).setTextColor(-16777216);
          ((TextView)localObject1).setLayoutParams(new TableRow.LayoutParams((int)(f * 129.2525D), (int)(f * 29.98125D)));
          ((TextView)localObject1).setTypeface(getFontDaum());
          localObject2 = new TextView(this);
          ((TextView)localObject2).setText("None");
          ((TextView)localObject2).setGravity(17);
          ((TextView)localObject2).setTextColor(-16777216);
          ((TextView)localObject2).setLayoutParams(new TableRow.LayoutParams((int)(f * 102.6025D), (int)(f * 29.98125D)));
          ((TextView)localObject2).setTypeface(getFontDaum());
          paramCursor.addView((View)localObject1);
          paramCursor.addView((View)localObject2);
          this.table_resultList.addView(paramCursor, new TableLayout.LayoutParams(-1, -2));
        }
        return;
      }
      Object localObject2 = new TableRow(this);
      ((TableRow)localObject2).setGravity(17);
      ((TableRow)localObject2).setLayoutParams(new TableRow.LayoutParams(-1, -2));
      TextView localTextView1 = new TextView(this);
      localTextView1.setText(((SimpleDateFormat)localObject1).format(new Date(paramCursor.getLong(2))));
      localTextView1.setGravity(17);
      localTextView1.setTextColor(-16777216);
      localTextView1.setLayoutParams(new TableRow.LayoutParams((int)(f * 129.2525D), (int)(f * 29.98125D)));
      localTextView1.setTypeface(getFontDaum());
      TextView localTextView2 = new TextView(this);
      localTextView2.setText(paramCursor.getLong(1) + "%");
      localTextView2.setGravity(17);
      localTextView2.setTextColor(-16777216);
      localTextView2.setLayoutParams(new TableRow.LayoutParams((int)(f * 102.6025D), (int)(f * 29.98125D)));
      localTextView2.setTypeface(getFontDaum());
      ((TableRow)localObject2).setBackgroundResource(2130837624);
      ((TableRow)localObject2).addView(localTextView1);
      ((TableRow)localObject2).addView(localTextView2);
      this.table_resultList.addView((View)localObject2, new TableLayout.LayoutParams(-1, -2));
    }
  }
  
  private void registButtonEvents()
  {
    this.btn_back.setOnClickListener(this.onClickListener);
    this.btn_ranking.setOnClickListener(this.onClickListener);
    this.btn_chart_graph.setOnClickListener(this.onClickListener);
    this.txt_userName.setOnClickListener(this.onClickListener);
    this.txt_place.setOnClickListener(this.onClickListener);
    this.txt_date.setOnClickListener(this.onClickListener);
    this.txt_panel_date.setOnClickListener(this.onClickListener);
    this.tab_measure.setOnClickListener(this.onClickListener);
    this.tab_setting.setOnClickListener(this.onClickListener);
    this.tab_user.setOnClickListener(this.onClickListener);
  }
  
  protected void changeResultView()
  {
    switch (this.selectedResultView)
    {
    default: 
      return;
    case 0: 
      this.scrollResult.setVisibility(0);
      this.graphLayout.setVisibility(8);
      this.btn_chart_graph.setImageDrawable(getResources().getDrawable(2130837587));
      return;
    }
    this.scrollResult.setVisibility(8);
    this.graphLayout.setVisibility(0);
    this.btn_chart_graph.setImageDrawable(getResources().getDrawable(2130837581));
  }
  
  public boolean isOnline()
  {
    NetworkInfo localNetworkInfo = ((ConnectivityManager)getSystemService("connectivity")).getActiveNetworkInfo();
    return (localNetworkInfo != null) && (localNetworkInfo.isConnectedOrConnecting());
  }
  
  public void moveScrollView(HorizontalScrollView paramHorizontalScrollView)
  {
    int j = (int)(paramHorizontalScrollView.getScrollX() - 1.0D);
    Log.e("ZAZA", "pos:" + paramHorizontalScrollView.getMeasuredWidth());
    int i = j;
    if (j <= 0) {
      i = paramHorizontalScrollView.getMeasuredWidth();
    }
    paramHorizontalScrollView.scrollTo(i, 0);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903055);
    initComponents();
    initSelectedTime();
    displaySelectedTime();
    changeFont();
    registButtonEvents();
    initText();
    initData();
    changeResultView();
    this.displayInformationType = 0;
    loadData_from_Database_for_Table_and_Graph();
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
    default: 
      return super.onKeyDown(paramInt, paramKeyEvent);
    }
    gotoMeasureExplain();
    return false;
  }
  
  protected void onPause()
  {
    super.onPause();
    this.isPause = true;
  }
  
  protected void onResume()
  {
    super.onResume();
    if (this.isPause)
    {
      Intent localIntent = getIntent();
      finish();
      startActivity(localIntent);
      this.isPause = false;
    }
  }
  
  protected void showDateTypeChoiceDialog()
  {
    String[] arrayOfString = this.DATETYPE_ARRAY;
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle("Please choose place.");
    localBuilder.setNegativeButton(getString(2131230769), new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        paramAnonymousDialogInterface.dismiss();
      }
    });
    localBuilder.setItems(arrayOfString, new DialogInterface.OnClickListener()
    {
      public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
      {
        AC_RecordMain.this.txt_date.setText(AC_RecordMain.this.DATETYPE_ARRAY[paramAnonymousInt]);
        AC_RecordMain.this.selectedDateType = paramAnonymousInt;
        switch (AC_RecordMain.this.selectedDateType)
        {
        }
        for (;;)
        {
          AC_RecordMain.this.initSelectedTime();
          AC_RecordMain.this.loadData_from_Database_for_Table_and_Graph();
          return;
          AC_RecordMain.this.displayInformationType = 0;
          continue;
          AC_RecordMain.this.displayInformationType = 1;
        }
      }
    });
    localBuilder.show();
  }
  
  protected void showPlaceChoiceDialog()
  {
    String[] arrayOfString = new String[4];
    int i = 0;
    for (;;)
    {
      if (i >= arrayOfString.length)
      {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setTitle("Please choose place.");
        localBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            paramAnonymousDialogInterface.dismiss();
          }
        });
        localBuilder.setItems(arrayOfString, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            AC_RecordMain.this.txt_place.setText(AC_RecordMain.this.mPref.getScanningPointExplain(paramAnonymousInt));
            AC_RecordMain.this.selectedScanPlace = paramAnonymousInt;
            AC_RecordMain.this.loadData_from_Database_for_Table_and_Graph();
          }
        });
        localBuilder.show();
        return;
      }
      arrayOfString[i] = this.mPref.getScanningPointExplain(i);
      i += 1;
    }
  }
  
  protected void showUserChoiceDialog()
  {
    final ArrayList localArrayList1 = new ArrayList();
    final ArrayList localArrayList2 = new ArrayList();
    Object localObject = this.dbAdapter.getUserList(this.mPref.getOwnerUID());
    int i;
    if (!((Cursor)localObject).moveToNext())
    {
      localObject = new String[localArrayList1.size()];
      i = 0;
    }
    for (;;)
    {
      if (i >= localObject.length)
      {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setTitle("Please choose username.");
        localBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            paramAnonymousDialogInterface.dismiss();
          }
        });
        localBuilder.setItems((CharSequence[])localObject, new DialogInterface.OnClickListener()
        {
          public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            AC_RecordMain.this.txt_userName.setText((CharSequence)localArrayList1.get(paramAnonymousInt));
            AC_RecordMain.this.selectedUserUID = ((Integer)localArrayList2.get(paramAnonymousInt)).intValue();
            AC_RecordMain.this.loadData_from_Database_for_Table_and_Graph();
          }
        });
        localBuilder.show();
        return;
        localArrayList1.add(((Cursor)localObject).getString(0));
        localArrayList2.add(Integer.valueOf(((Cursor)localObject).getInt(3)));
        if (!this.D) {
          break;
        }
        Log.e("smardi.Epi", "cursor:" + ((Cursor)localObject).getString(0));
        break;
      }
      localObject[i] = ((String)localArrayList1.get(i));
      i += 1;
    }
  }
  
  private class databaseData
  {
    private long time = 0L;
    private int value = 0;
    
    public databaseData()
    {
      this.time = 0L;
      this.value = 0;
    }
    
    public databaseData(long paramLong, int paramInt)
    {
      this.time = paramLong;
      this.value = paramInt;
    }
    
    public long getTime()
    {
      return this.time;
    }
    
    public int getValue()
    {
      return this.value;
    }
    
    public void setTime(long paramLong)
    {
      this.time = paramLong;
    }
    
    public void setValue(int paramInt)
    {
      this.value = paramInt;
    }
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\record\AC_RecordMain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */