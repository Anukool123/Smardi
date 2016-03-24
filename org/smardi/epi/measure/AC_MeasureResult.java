package org.smardi.epi.measure;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import org.smardi.epi.DB_Adapter;
import org.smardi.epi.Manage_SharedPreference;
import org.smardi.epi.device.Devices;
import org.smardi.epi.record.AC_RecordMain;
import org.smardi.epi.setting.AC_SettingMain;
import org.smardi.epi.user.AC_UserMain;

public class AC_MeasureResult
  extends Activity
{
  private boolean D = false;
  private final int MAX_VALUE = 2500;
  private String TAG = "";
  private final int TWO_MINUTES = 120000;
  ImageButton btn_back;
  ImageButton btn_save;
  ImageButton btn_tip;
  CountDownTimer cTimer_graphAnim = null;
  CountDownTimer cTimer_numberAnim = null;
  AlphaAnimation fadeIn;
  AlphaAnimation fadeOut;
  private int indexofGraphImage = 0;
  ArrayList<Drawable> listResultDrawable = new ArrayList();
  ArrayList<Double> listResultPoint = new ArrayList();
  ArrayList<TextView> listResultText = new ArrayList();
  private int mCount = 0;
  DB_Adapter mDb_Adapter;
  Devices mDevices = null;
  private LocationManager mLocManager;
  private LocationProvider mLocProvider;
  private Location mLocation;
  LocationListener mLocationListener = new LocationListener()
  {
    public void onLocationChanged(Location paramAnonymousLocation)
    {
      Object localObject = AC_MeasureResult.this;
      ((AC_MeasureResult)localObject).mCount += 1;
      localObject = String.format("수신회수:%d\n위도:%f\n경도:%f\n고도:%f", new Object[] { Integer.valueOf(AC_MeasureResult.this.mCount), Double.valueOf(paramAnonymousLocation.getLatitude()), Double.valueOf(paramAnonymousLocation.getLongitude()), Double.valueOf(paramAnonymousLocation.getAltitude()) });
      if (AC_MeasureResult.this.D) {
        Log.i("smardi.Epi", (String)localObject);
      }
      if (AC_MeasureResult.this.isBetterLocation(paramAnonymousLocation, AC_MeasureResult.this.mLocation)) {
        AC_MeasureResult.this.mLocation = paramAnonymousLocation;
      }
    }
    
    public void onProviderDisabled(String paramAnonymousString) {}
    
    public void onProviderEnabled(String paramAnonymousString) {}
    
    public void onStatusChanged(String paramAnonymousString, int paramAnonymousInt, Bundle paramAnonymousBundle) {}
  };
  Manage_SharedPreference mPref;
  private int mResultPointValue = 0;
  int measureMode = -1;
  View.OnClickListener onClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      default: 
        return;
      case 2131296287: 
        AC_MeasureResult.this.goBack();
        return;
      case 2131296335: 
        AC_MeasureResult.this.btn_save.setImageDrawable(AC_MeasureResult.this.getResources().getDrawable(2130837564));
        if (AC_MeasureResult.this.mLocation == null) {}
        for (long l = AC_MeasureResult.this.mDb_Adapter.addRecord(AC_MeasureResult.this.mResultPointValue, AC_MeasureResult.this.mPref.getUserUID(), AC_MeasureResult.this.mPref.getScanningPoint(), 0.0D, 0.0D);; l = AC_MeasureResult.this.mDb_Adapter.addRecord(AC_MeasureResult.this.mResultPointValue, AC_MeasureResult.this.mPref.getUserUID(), AC_MeasureResult.this.mPref.getScanningPoint(), AC_MeasureResult.this.mLocation.getLongitude(), AC_MeasureResult.this.mLocation.getLatitude()))
        {
          Log.e("smardi.Epi", "Saved! uid:" + l);
          Log.e("smardi.Epi", "Saved! scan position:" + AC_MeasureResult.this.mPref.getScanningPoint());
          return;
        }
      case 2131296313: 
        paramAnonymousView = new Intent(AC_MeasureResult.this, AC_RecordMain.class);
        AC_MeasureResult.this.startActivity(paramAnonymousView);
        AC_MeasureResult.this.finish();
        AC_MeasureResult.this.overridePendingTransition(2130968580, 2130968581);
        return;
      case 2131296314: 
        paramAnonymousView = new Intent(AC_MeasureResult.this, AC_UserMain.class);
        AC_MeasureResult.this.startActivity(paramAnonymousView);
        AC_MeasureResult.this.finish();
        AC_MeasureResult.this.overridePendingTransition(2130968580, 2130968581);
        return;
      }
      paramAnonymousView = new Intent(AC_MeasureResult.this, AC_SettingMain.class);
      AC_MeasureResult.this.startActivity(paramAnonymousView);
      AC_MeasureResult.this.finish();
      AC_MeasureResult.this.overridePendingTransition(2130968580, 2130968581);
    }
  };
  ScrollView resultExplainScroll;
  LinearLayout resultExplainWrap;
  ImageView result_graph;
  ImageView result_num_1;
  ImageView result_num_2;
  ImageButton tab_record;
  ImageButton tab_setting;
  ImageButton tab_user;
  TextView txt_detail_recommand;
  TextView txt_result_explain_1;
  TextView txt_result_explain_2;
  TextView txt_result_explain_3;
  TextView txt_subTitle;
  
  private void changeFont()
  {
    Typeface localTypeface1 = Typeface.createFromAsset(getAssets(), "fonts/Daum_Regular.ttf");
    Typeface.createFromAsset(getAssets(), "fonts/MuseoSans_500.otf");
    Typeface localTypeface2 = Typeface.createFromAsset(getAssets(), "fonts/MuseoSans_700.otf");
    this.txt_subTitle.setTypeface(localTypeface1);
    this.txt_result_explain_1.setTypeface(localTypeface1);
    this.txt_result_explain_2.setTypeface(localTypeface2);
    this.txt_result_explain_3.setTypeface(localTypeface2);
    this.txt_detail_recommand.setTypeface(localTypeface1);
  }
  
  private int convertDPtoPX(double paramDouble)
  {
    DisplayMetrics localDisplayMetrics = new DisplayMetrics();
    getWindowManager().getDefaultDisplay().getMetrics(localDisplayMetrics);
    return (int)(localDisplayMetrics.density * paramDouble + 0.5D);
  }
  
  private Typeface getFont()
  {
    return Typeface.createFromAsset(getAssets(), "fonts/Daum_Regular.ttf");
  }
  
  private void goBack()
  {
    if (this.measureMode == 1) {}
    for (Intent localIntent = new Intent(this, AC_MeasureMain.class);; localIntent = new Intent(this, AC_MeasureExplain.class))
    {
      startActivity(localIntent);
      finish();
      overridePendingTransition(2130968580, 2130968581);
      return;
    }
  }
  
  private boolean isSameProvider(String paramString1, String paramString2)
  {
    if (paramString1 == null) {
      return paramString2 == null;
    }
    return paramString1.equals(paramString2);
  }
  
  private void makeNumbers(int paramInt)
  {
    int i = (int)Math.floor(paramInt / 10.0D);
    setNumber(this.result_num_1, i);
    setNumber(this.result_num_2, paramInt % 10);
  }
  
  private void makeResultPoint()
  {
    int i = getIntent().getIntExtra("resultValue", 0);
    this.mResultPointValue = ((int)this.mDevices.getCorneometerValue(i));
    if (this.mResultPointValue < 0)
    {
      Toast.makeText(this, getString(2131230786), 1000).show();
      this.mResultPointValue = 0;
    }
  }
  
  private void registButtonEvents()
  {
    this.btn_back.setOnClickListener(this.onClickListener);
    this.btn_save.setOnClickListener(this.onClickListener);
    this.tab_record.setOnClickListener(this.onClickListener);
    this.tab_user.setOnClickListener(this.onClickListener);
    this.tab_setting.setOnClickListener(this.onClickListener);
  }
  
  private void resultProcessing()
  {
    makeNumbers(this.mResultPointValue);
  }
  
  private void setNumber(ImageView paramImageView, int paramInt)
  {
    Drawable localDrawable = null;
    switch (paramInt)
    {
    }
    for (;;)
    {
      paramImageView.setImageDrawable(localDrawable);
      return;
      localDrawable = getResources().getDrawable(2130837707);
      continue;
      localDrawable = getResources().getDrawable(2130837696);
      continue;
      localDrawable = getResources().getDrawable(2130837697);
      continue;
      localDrawable = getResources().getDrawable(2130837698);
      continue;
      localDrawable = getResources().getDrawable(2130837699);
      continue;
      localDrawable = getResources().getDrawable(2130837700);
      continue;
      localDrawable = getResources().getDrawable(2130837701);
      continue;
      localDrawable = getResources().getDrawable(2130837702);
      continue;
      localDrawable = getResources().getDrawable(2130837703);
      continue;
      localDrawable = getResources().getDrawable(2130837704);
      continue;
      localDrawable = getResources().getDrawable(2130837705);
    }
  }
  
  private void setResultDrawable()
  {
    this.listResultDrawable.add(getResources().getDrawable(2130837645));
    this.listResultDrawable.add(getResources().getDrawable(2130837655));
    this.listResultDrawable.add(getResources().getDrawable(2130837659));
    this.listResultDrawable.add(getResources().getDrawable(2130837663));
    this.listResultDrawable.add(getResources().getDrawable(2130837646));
    this.listResultDrawable.add(getResources().getDrawable(2130837648));
    this.listResultDrawable.add(getResources().getDrawable(2130837649));
    this.listResultDrawable.add(getResources().getDrawable(2130837650));
    this.listResultDrawable.add(getResources().getDrawable(2130837651));
    this.listResultDrawable.add(getResources().getDrawable(2130837652));
    this.listResultDrawable.add(getResources().getDrawable(2130837653));
    this.listResultDrawable.add(getResources().getDrawable(2130837654));
    this.listResultDrawable.add(getResources().getDrawable(2130837656));
    this.listResultDrawable.add(getResources().getDrawable(2130837657));
    this.listResultDrawable.add(getResources().getDrawable(2130837658));
    this.listResultDrawable.add(getResources().getDrawable(2130837660));
    this.listResultDrawable.add(getResources().getDrawable(2130837661));
    this.listResultDrawable.add(getResources().getDrawable(2130837662));
    this.listResultDrawable.add(getResources().getDrawable(2130837664));
    this.listResultDrawable.add(getResources().getDrawable(2130837665));
    this.listResultDrawable.add(getResources().getDrawable(2130837647));
    this.listResultPoint.add(Double.valueOf(0.0D));
    this.listResultPoint.add(Double.valueOf(2.5D));
    this.listResultPoint.add(Double.valueOf(5.0D));
    this.listResultPoint.add(Double.valueOf(7.5D));
    this.listResultPoint.add(Double.valueOf(10.0D));
    this.listResultPoint.add(Double.valueOf(12.5D));
    this.listResultPoint.add(Double.valueOf(15.0D));
    this.listResultPoint.add(Double.valueOf(17.5D));
    this.listResultPoint.add(Double.valueOf(20.0D));
    this.listResultPoint.add(Double.valueOf(22.5D));
    this.listResultPoint.add(Double.valueOf(25.0D));
    this.listResultPoint.add(Double.valueOf(27.5D));
    this.listResultPoint.add(Double.valueOf(30.0D));
    this.listResultPoint.add(Double.valueOf(35.0D));
    this.listResultPoint.add(Double.valueOf(40.0D));
    this.listResultPoint.add(Double.valueOf(50.0D));
    this.listResultPoint.add(Double.valueOf(60.0D));
    this.listResultPoint.add(Double.valueOf(70.0D));
    this.listResultPoint.add(Double.valueOf(80.0D));
    this.listResultPoint.add(Double.valueOf(90.0D));
    this.listResultPoint.add(Double.valueOf(100.0D));
  }
  
  private void setResultExplainMessage()
  {
    Object localObject1 = (String[])null;
    localObject1 = (String[])null;
    int i;
    label32:
    Object localObject2;
    if (this.mResultPointValue < 11)
    {
      localObject1 = getResources().getStringArray(2131099649);
      i = 0;
      if (i == 0) {
        break label332;
      }
      localObject2 = localObject1[(i - 1)];
      if (this.mResultPointValue > 30) {
        break label361;
      }
      if (Math.random() >= 0.5D) {
        break label347;
      }
      localObject1 = getResources().getStringArray(2131099656);
      label72:
      i = 0;
      label74:
      if (i == 0) {
        break label422;
      }
      localObject2 = (localObject2 + "\n" + localObject1[(i - 1)]).split("\\n+");
      i = 2;
      label115:
      if (i < localObject2.length) {
        break label437;
      }
      localObject1 = new String[2];
      localObject1[0] = localObject2[0];
      localObject1[1] = localObject2[1];
      int j = localObject1.length;
      i = 0;
      if (i < j) {
        break label475;
      }
      i = 0;
    }
    for (;;)
    {
      if (i >= this.listResultText.size())
      {
        localObject1 = this.mPref.getUserName();
        this.txt_subTitle.setText(localObject1 + getString(2131230737));
        return;
        if (this.mResultPointValue < 21)
        {
          localObject1 = getResources().getStringArray(2131099650);
          break;
        }
        if (this.mResultPointValue < 31)
        {
          localObject1 = getResources().getStringArray(2131099651);
          break;
        }
        if (this.mResultPointValue < 41)
        {
          localObject1 = getResources().getStringArray(2131099652);
          break;
        }
        if (this.mResultPointValue < 51)
        {
          localObject1 = getResources().getStringArray(2131099653);
          break;
        }
        if (this.mResultPointValue < 71)
        {
          localObject1 = getResources().getStringArray(2131099654);
          break;
        }
        localObject1 = getResources().getStringArray(2131099655);
        break;
        label332:
        i = (int)Math.ceil(Math.random() * localObject1.length);
        break label32;
        label347:
        localObject1 = getResources().getStringArray(2131099658);
        break label72;
        label361:
        if (this.mResultPointValue <= 70)
        {
          localObject1 = getResources().getStringArray(2131099656);
          break label72;
        }
        if (Math.random() < 0.5D)
        {
          localObject1 = getResources().getStringArray(2131099656);
          break label72;
        }
        localObject1 = getResources().getStringArray(2131099657);
        break label72;
        label422:
        i = (int)Math.ceil(Math.random() * localObject1.length);
        break label74;
        label437:
        localObject2[1] = (localObject2[1] + "\n" + localObject2[i]);
        i += 1;
        break label115;
        label475:
        localObject2 = new TextView(this);
        ((TextView)localObject2).setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
        if (i < localObject1.length)
        {
          ((TextView)localObject2).setText(localObject1[i]);
          label511:
          ((TextView)localObject2).setBackgroundResource(2130837624);
          ((TextView)localObject2).setLineSpacing(0.0F, 1.3F);
          if (i != 0) {
            break label667;
          }
          ((TextView)localObject2).setTextColor(Color.rgb(88, 89, 91));
        }
        for (;;)
        {
          ((TextView)localObject2).setPadding(convertDPtoPX(8.0D), convertDPtoPX(5.0D), convertDPtoPX(8.0D), convertDPtoPX(5.0D));
          ((TextView)localObject2).setTextSize(2, 18.0F);
          ((TextView)localObject2).setTypeface(getFont());
          ((TextView)localObject2).setLineSpacing(0.0F, 1.6F);
          if (this.D) {
            Log.e(this.TAG, "line:" + ((TextView)localObject2).getLineCount());
          }
          this.resultExplainWrap.addView((View)localObject2);
          this.listResultText.add(localObject2);
          i += 1;
          break;
          ((TextView)localObject2).setText("");
          break label511;
          label667:
          ((TextView)localObject2).setTextColor(Color.rgb(120, 121, 123));
        }
      }
      ((TextView)this.listResultText.get(i)).setAnimation(this.fadeOut);
      i += 1;
    }
  }
  
  private void startGraphAnimation()
  {
    int i = 0;
    for (;;)
    {
      if (i >= this.listResultPoint.size()) {}
      for (;;)
      {
        this.cTimer_graphAnim = new CountDownTimer(5000L, 40L)
        {
          final int STATE_END = 3;
          final int STATE_FIRST = 0;
          final int STATE_SECOND = 1;
          final int STATE_THIRD = 2;
          int countAnim = -1;
          int graphState = 0;
          int increment = 1;
          
          public void onFinish() {}
          
          public void onTick(long paramAnonymousLong)
          {
            switch (this.graphState)
            {
            }
            for (;;)
            {
              AC_MeasureResult.this.result_graph.setImageDrawable((Drawable)AC_MeasureResult.this.listResultDrawable.get(this.countAnim + this.increment));
              this.countAnim += this.increment;
              return;
              this.increment = 1;
              if (this.countAnim == AC_MeasureResult.this.listResultDrawable.size() - 1)
              {
                this.graphState = 1;
                this.increment = 0;
                continue;
                this.increment = -1;
                if (this.countAnim == 0)
                {
                  this.graphState = 2;
                  this.increment = 0;
                  continue;
                  this.increment = 1;
                  if (this.countAnim == AC_MeasureResult.this.indexofGraphImage)
                  {
                    this.graphState = 3;
                    this.increment = 0;
                    continue;
                    AC_MeasureResult.this.cTimer_graphAnim.cancel();
                  }
                }
              }
            }
          }
        }.start();
        return;
        if (this.mResultPointValue > ((Double)this.listResultPoint.get(i)).doubleValue()) {
          break;
        }
        this.indexofGraphImage = i;
      }
      i += 1;
    }
  }
  
  private void startNumberAnimation()
  {
    this.cTimer_numberAnim = new CountDownTimer(2000L, 50L)
    {
      int totCount = 0;
      
      public void onFinish()
      {
        AC_MeasureResult.this.resultProcessing();
        AC_MeasureResult.this.txt_result_explain_1.setAnimation(AC_MeasureResult.this.fadeOut);
        int i = 0;
        for (;;)
        {
          if (i >= AC_MeasureResult.this.listResultText.size()) {
            return;
          }
          ((TextView)AC_MeasureResult.this.listResultText.get(i)).startAnimation(AC_MeasureResult.this.fadeIn);
          i += 1;
        }
      }
      
      public void onTick(long paramAnonymousLong)
      {
        this.totCount += 7;
        AC_MeasureResult.this.makeNumbers(this.totCount % 100);
      }
    }.start();
  }
  
  public int convertSPtoPX(float paramFloat)
  {
    return (int)(paramFloat * getResources().getDisplayMetrics().scaledDensity);
  }
  
  protected boolean isBetterLocation(Location paramLocation1, Location paramLocation2)
  {
    if (paramLocation2 == null) {
      return true;
    }
    long l = paramLocation1.getTime() - paramLocation2.getTime();
    int j;
    int k;
    if (l > 120000L)
    {
      j = 1;
      if (l >= -120000L) {
        break label63;
      }
      k = 1;
      label41:
      if (l <= 0L) {
        break label69;
      }
    }
    label63:
    label69:
    for (int i = 1;; i = 0)
    {
      if (j == 0) {
        break label74;
      }
      return true;
      j = 0;
      break;
      k = 0;
      break label41;
    }
    label74:
    if (k != 0) {
      return false;
    }
    int m = (int)(paramLocation1.getAccuracy() - paramLocation2.getAccuracy());
    if (m > 0)
    {
      j = 1;
      if (m >= 0) {
        break label147;
      }
      k = 1;
      label109:
      if (m <= 200) {
        break label153;
      }
    }
    boolean bool;
    label147:
    label153:
    for (m = 1;; m = 0)
    {
      bool = isSameProvider(paramLocation1.getProvider(), paramLocation2.getProvider());
      if (k == 0) {
        break label159;
      }
      return true;
      j = 0;
      break;
      k = 0;
      break label109;
    }
    label159:
    if ((i != 0) && (j == 0)) {
      return true;
    }
    return (i != 0) && (m == 0) && (bool);
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903053);
    this.txt_result_explain_1 = ((TextView)findViewById(2131296338));
    this.txt_result_explain_2 = ((TextView)findViewById(2131296340));
    this.txt_result_explain_3 = ((TextView)findViewById(2131296341));
    this.resultExplainScroll = ((ScrollView)findViewById(2131296337));
    this.resultExplainWrap = ((LinearLayout)findViewById(2131296339));
    this.txt_detail_recommand = ((TextView)findViewById(2131296336));
    this.txt_subTitle = ((TextView)findViewById(2131296308));
    this.result_num_1 = ((ImageView)findViewById(2131296332));
    this.result_num_2 = ((ImageView)findViewById(2131296333));
    this.btn_back = ((ImageButton)findViewById(2131296287));
    this.btn_save = ((ImageButton)findViewById(2131296335));
    this.btn_tip = ((ImageButton)findViewById(2131296288));
    this.tab_record = ((ImageButton)findViewById(2131296313));
    this.tab_user = ((ImageButton)findViewById(2131296314));
    this.tab_setting = ((ImageButton)findViewById(2131296315));
    this.result_graph = ((ImageView)findViewById(2131296330));
    this.mDb_Adapter = new DB_Adapter(this);
    this.mDb_Adapter.open();
    this.mPref = new Manage_SharedPreference(this);
    this.mDevices = new Devices(Build.DEVICE, Build.MODEL, this);
    this.fadeOut = new AlphaAnimation(1.0F, 0.0F);
    this.fadeOut.setDuration(0L);
    this.fadeOut.setFillAfter(true);
    this.fadeIn = new AlphaAnimation(0.0F, 1.0F);
    this.fadeIn.setDuration(500L);
    this.fadeIn.setFillAfter(true);
    this.measureMode = getIntent().getIntExtra("mode", -1);
    if (this.measureMode == 0)
    {
      this.btn_save.setVisibility(8);
      this.txt_detail_recommand.setVisibility(0);
    }
    this.mLocManager = ((LocationManager)getSystemService("location"));
    paramBundle = new Criteria();
    paramBundle.setAccuracy(0);
    paramBundle.setPowerRequirement(0);
    paramBundle.setAltitudeRequired(false);
    paramBundle.setCostAllowed(true);
    try
    {
      this.mLocProvider = this.mLocManager.getProvider(this.mLocManager.getBestProvider(paramBundle, true));
      if (this.D) {
        Log.i("smardi.Epi", "Provider:" + this.mLocProvider.getName());
      }
      this.mLocation = this.mLocManager.getLastKnownLocation(this.mLocProvider.getName());
      if (this.mLocation == null) {
        this.mLocation = new Location(this.mLocProvider.getName());
      }
      paramBundle = String.format("수신회수:%d\n위도:%f\n경도:%f\n고도:%f", new Object[] { Integer.valueOf(this.mCount), Double.valueOf(this.mLocation.getLatitude()), Double.valueOf(this.mLocation.getLongitude()), Double.valueOf(this.mLocation.getAltitude()) });
      if (this.D) {
        Log.i("smardi.Epi", paramBundle);
      }
    }
    catch (Exception paramBundle)
    {
      for (;;)
      {
        if (this.D) {
          Log.e("smardi.Epi", "Error occured at loading location information");
        }
      }
    }
    changeFont();
    setResultDrawable();
    registButtonEvents();
    makeResultPoint();
    setResultExplainMessage();
    startNumberAnimation();
    startGraphAnimation();
  }
  
  protected void onDestroy()
  {
    this.mDb_Adapter.close();
    if (this.cTimer_graphAnim != null) {
      this.cTimer_graphAnim.cancel();
    }
    super.onDestroy();
  }
  
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if (paramInt == 4) {
      goBack();
    }
    return super.onKeyDown(paramInt, paramKeyEvent);
  }
  
  protected void onPause()
  {
    super.onPause();
    this.mLocManager.removeUpdates(this.mLocationListener);
  }
  
  protected void onResume()
  {
    super.onResume();
    if (this.mLocProvider != null) {
      this.mLocManager.requestLocationUpdates("network", 3000L, 10.0F, this.mLocationListener);
    }
  }
  
  public float pixelsToSp(Context paramContext, Float paramFloat)
  {
    float f = getResources().getDisplayMetrics().scaledDensity;
    return paramFloat.floatValue() / f;
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\measure\AC_MeasureResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */