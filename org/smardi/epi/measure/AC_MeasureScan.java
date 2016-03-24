package org.smardi.epi.measure;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Date;
import org.smardi.epi.Manage_SharedPreference;
import org.smardi.epi.device.Devices;
import org.smardi.epi.record.AC_RecordMain;
import org.smardi.epi.setting.AC_SettingMain;
import org.smardi.epi.user.AC_UserMain;

public class AC_MeasureScan
  extends Activity
{
  private static final int RECORDER_AUDIO_ENCODING = 2;
  private static final int RECORDER_CHANNELS = 12;
  private static final int RECORDER_SAMPLERATE = 8000;
  public static short[] buffer;
  public static long dt;
  public static long t1;
  public static long t2;
  private final int CRITERION_COUNT_DETECTED = 2;
  private boolean D = false;
  private final int DATA_COLLECT_TIME = 3000;
  private final int DATA_COLLECT_TIME_C = 1500;
  private final int SCAN_DONE = 2;
  private final int SCAN_FAIL = 3;
  private final int SCAN_PREPARE = -1;
  private final int SCAN_READY = 0;
  private final int SCAN_SCANNING = 1;
  private final int STATE_CONNECTION_COMPLETE = 4;
  private final int STATE_CONNECTION_EARPHONE_CHECKED = 1;
  private final int STATE_CONNECTION_EARPHONE_NONE = 0;
  private final int STATE_CONNECTION_VALUE_CHECKED = 3;
  private final int STATE_CONNECTION_VALUE_CHECKING = 2;
  private final int WHAT_SCAN_DISABLE = -100;
  private final int WHAT_SCAN_ENABLE = 100;
  private AlertDialog alert;
  AlertDialog.Builder alertBuilder;
  AlertDialog.Builder alertMicCrazy = null;
  AnimationDrawable aniFrame;
  public int avg;
  Button btn_alert_button1;
  Button btn_alert_button2;
  ImageButton btn_back;
  ImageButton btn_scan;
  private int bufferSize = 0;
  public int buflen;
  CountDownTimer cTimer_tutorial = null;
  private ProgressDialog checkingDialog;
  private int countDetected = 0;
  public float db;
  DialogInterface.OnClickListener dialogOnClickListener = new DialogInterface.OnClickListener()
  {
    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
    {
      if (paramAnonymousInt == -1)
      {
        AC_MeasureScan.this.time_ConnectionCheckStart = new Date().getTime();
        AC_MeasureScan.this.alert.dismiss();
        AC_MeasureScan.this.checkingDialog.show();
        return;
      }
      AC_MeasureScan.this.alert.dismiss();
      AC_MeasureScan.this.goBack();
    }
  };
  int hasMicrophone = 0;
  int headSetState = 0;
  public int i;
  boolean isCheckAverage = false;
  boolean isCheckingStart = false;
  boolean isDoneChecking = false;
  boolean isEpiConnected = false;
  boolean isEpiContactToFace = false;
  private boolean isRecording = false;
  boolean isUserWantToExit = false;
  long lastBackPressTime = 0L;
  ArrayList<Integer> listMeasureValues = new ArrayList();
  ArrayList<Button> listPoint;
  ArrayList<Drawable> listTutorialDrawable = null;
  private ArrayList<Integer> list_MIC_data = new ArrayList();
  private ArrayList<Long> list_MIC_time = new ArrayList();
  RelativeLayout loadingScreen;
  BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (paramAnonymousIntent.getAction().equals("android.intent.action.HEADSET_PLUG"))
      {
        AC_MeasureScan.this.headSetState = paramAnonymousIntent.getIntExtra("state", 0);
        AC_MeasureScan.this.hasMicrophone = paramAnonymousIntent.getIntExtra("microphone", 0);
        Log.e("Epi", "MIC:" + AC_MeasureScan.this.hasMicrophone);
        AC_MeasureScan.this.checkConnectionWithEpi();
      }
    }
  };
  int mCountScanIncreament = 0;
  Devices mDevices = null;
  Handler mHandler = new Handler(new Handler.Callback()
  {
    ArrayList<Integer> listCheckingValues = new ArrayList();
    long time_micZero = 0L;
    
    public boolean handleMessage(Message paramAnonymousMessage)
    {
      long l1 = new Date().getTime();
      int i;
      if (paramAnonymousMessage.what == 0)
      {
        i = Integer.parseInt(paramAnonymousMessage.obj.toString());
        if (i > AC_MeasureScan.this.oldMeasuredValue) {
          AC_MeasureScan.this.oldMeasuredValue = i;
        }
        if ((i == 0) && (this.time_micZero == 0L))
        {
          this.time_micZero = l1;
          Log.e("DDD", "v:" + this.time_micZero);
          if ((0L < this.time_micZero) && (2000L < l1 - this.time_micZero))
          {
            AC_MeasureScan.this.isRecording = false;
            if (AC_MeasureScan.this.alertMicCrazy == null)
            {
              AC_MeasureScan.this.alertMicCrazy = new AlertDialog.Builder(AC_MeasureScan.this);
              AC_MeasureScan.this.alertMicCrazy.setTitle(AC_MeasureScan.this.getString(2131230787));
              AC_MeasureScan.this.alertMicCrazy.setMessage(AC_MeasureScan.this.getString(2131230788) + "\n" + AC_MeasureScan.this.getString(2131230789));
              AC_MeasureScan.this.alertMicCrazy.setPositiveButton(AC_MeasureScan.this.getString(2131230768), new DialogInterface.OnClickListener()
              {
                public void onClick(DialogInterface paramAnonymous2DialogInterface, int paramAnonymous2Int)
                {
                  AC_MeasureScan.this.goBack();
                }
              });
              AC_MeasureScan.this.alertMicCrazy.show();
            }
          }
          if ((AC_MeasureScan.this.list_MIC_time.size() > 0) && (l1 - ((Long)AC_MeasureScan.this.list_MIC_time.get(0)).longValue() > 3000L))
          {
            AC_MeasureScan.this.list_MIC_time.remove(0);
            AC_MeasureScan.this.list_MIC_data.remove(0);
          }
          AC_MeasureScan.this.list_MIC_time.add(Long.valueOf(l1));
          AC_MeasureScan.this.list_MIC_data.add(Integer.valueOf(Math.abs(i)));
          if (AC_MeasureScan.this.stateEpiScan == 0)
          {
            if (i <= AC_MeasureScan.this.mDevices.getMicValue(3.0D)) {
              break label876;
            }
            if (AC_MeasureScan.this.time_overThreshold == 0L) {
              AC_MeasureScan.this.time_overThreshold = new Date().getTime();
            }
            label415:
            l1 = new Date().getTime();
            long l2 = AC_MeasureScan.this.time_overThreshold;
            if ((AC_MeasureScan.this.time_overThreshold > 0L) && (l1 - l2 > 700L))
            {
              AC_MeasureScan.this.txt_explain.setText(AC_MeasureScan.this.getString(2131230763));
              AC_MeasureScan.this.txt_loading_status.setText("Scanning...");
              AC_MeasureScan.this.loadingScreen.setVisibility(0);
              AC_MeasureScan.this.isCheckingStart = true;
              AC_MeasureScan.this.time_CheckingStart = new Date().getTime();
              if (AC_MeasureScan.this.stateEpiConnection == 1)
              {
                AC_MeasureScan.this.aniFrame.start();
                AC_MeasureScan.this.isEpiContactToFace = true;
                AC_MeasureScan.this.time_EpiContactToFace = new Date().getTime();
                AC_MeasureScan.this.stateEpiConnection = 4;
                AC_MeasureScan.this.stateEpiScan = 1;
              }
            }
          }
          if (AC_MeasureScan.this.stateEpiConnection == 4)
          {
            this.listCheckingValues.add(Integer.valueOf(AC_MeasureScan.this.oldMeasuredValue));
            if (!AC_MeasureScan.this.isDoneChecking)
            {
              if (new Date().getTime() - AC_MeasureScan.this.time_EpiContactToFace <= 700L) {
                break label1123;
              }
              i = AC_MeasureScan.this.getAverage(this.listCheckingValues);
              this.listCheckingValues = new ArrayList();
              if (AC_MeasureScan.this.mode_measure != 1) {
                break label1051;
              }
              AC_MeasureScan.this.isDoneChecking = true;
              AC_MeasureScan.this.stateEpiConnection = 1;
              AC_MeasureScan.this.btn_scan.setEnabled(true);
              AC_MeasureScan.this.btn_scan.setImageDrawable(AC_MeasureScan.this.getResources().getDrawable(2130837607));
            }
          }
          switch (AC_MeasureScan.this.mPref.getDetailIndex())
          {
          default: 
            label772:
            Log.e("Epi", AC_MeasureScan.this.mPref.getDetailIndex() + " Value:" + i);
            label812:
            if (AC_MeasureScan.this.D) {
              AC_MeasureScan.this.m_volume.setText("v:" + AC_MeasureScan.this.oldMeasuredValue);
            }
            AC_MeasureScan.this.oldMeasuredValue = 0;
          }
        }
      }
      for (;;)
      {
        return false;
        if (i <= 0) {
          break;
        }
        this.time_micZero = 0L;
        break;
        label876:
        AC_MeasureScan.this.time_overThreshold = 0L;
        break label415;
        AC_MeasureScan.this.mPref.setDetailIndex(1);
        AC_MeasureScan.this.mPref.setDetailResults(1, i);
        AC_MeasureScan.this.initDetailScan(2);
        break label772;
        AC_MeasureScan.this.mPref.setDetailIndex(2);
        AC_MeasureScan.this.mPref.setDetailResults(2, i);
        AC_MeasureScan.this.initDetailScan(3);
        break label772;
        AC_MeasureScan.this.mPref.setDetailIndex(0);
        AC_MeasureScan.this.mPref.setDetailResults(3, i);
        paramAnonymousMessage = new Intent(AC_MeasureScan.this, AC_MeasureResult.class);
        paramAnonymousMessage.putExtra("resultValue", AC_MeasureScan.this.mPref.getDetailResult());
        paramAnonymousMessage.putExtra("mode", 1);
        AC_MeasureScan.this.startActivity(paramAnonymousMessage);
        AC_MeasureScan.this.finish();
        AC_MeasureScan.this.overridePendingTransition(2130968580, 2130968581);
        break label772;
        label1051:
        AC_MeasureScan.this.isDoneChecking = true;
        paramAnonymousMessage = new Intent(AC_MeasureScan.this, AC_MeasureResult.class);
        paramAnonymousMessage.putExtra("resultValue", i);
        paramAnonymousMessage.putExtra("mode", 0);
        AC_MeasureScan.this.startActivity(paramAnonymousMessage);
        AC_MeasureScan.this.finish();
        AC_MeasureScan.this.overridePendingTransition(2130968580, 2130968581);
        break label812;
        label1123:
        l1 = new Date().getTime();
        int j = 100 - (int)((AC_MeasureScan.this.time_EpiContactToFace + 600L - l1) * 100L / 600L);
        i = j;
        if (j > 100) {
          i = 100;
        }
        AC_MeasureScan.this.txt_percent.setText(i + "%");
        break label812;
        if (paramAnonymousMessage.what == -100)
        {
          AC_MeasureScan.this.btn_scan.setEnabled(false);
          AC_MeasureScan.this.btn_scan.setImageDrawable(AC_MeasureScan.this.getResources().getDrawable(2130837566));
        }
        else if (paramAnonymousMessage.what == 100)
        {
          AC_MeasureScan.this.btn_scan.setEnabled(true);
          AC_MeasureScan.this.txt_explain.setText(2131230723);
          AC_MeasureScan.this.btn_scan.setImageDrawable(AC_MeasureScan.this.getResources().getDrawable(2130837607));
        }
      }
    }
  });
  Manage_SharedPreference mPref;
  TextView m_connected;
  View m_layout;
  TextView m_max_tangent;
  TextView m_tangent;
  TextView m_volume;
  ArrayList<String> measureValues = new ArrayList();
  int mode_measure = -1;
  int oldAVG = 0;
  int oldMeasuredValue = 0;
  long oldTime = 0L;
  View.OnClickListener onClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      case 2131296301: 
      default: 
        return;
      case 2131296287: 
        AC_MeasureScan.this.goBack();
        return;
      case 2131296348: 
        AC_MeasureScan.this.txt_explain.setText(AC_MeasureScan.this.getString(2131230724));
        AC_MeasureScan.this.btn_scan.setEnabled(false);
        AC_MeasureScan.this.btn_scan.setImageDrawable(AC_MeasureScan.this.getResources().getDrawable(2130837566));
        AC_MeasureScan.this.stateEpiScan = 0;
        return;
      case 2131296313: 
        paramAnonymousView = new Intent(AC_MeasureScan.this, AC_RecordMain.class);
        AC_MeasureScan.this.startActivity(paramAnonymousView);
        AC_MeasureScan.this.finish();
        AC_MeasureScan.this.overridePendingTransition(2130968580, 2130968581);
        return;
      case 2131296314: 
        paramAnonymousView = new Intent(AC_MeasureScan.this, AC_UserMain.class);
        AC_MeasureScan.this.startActivity(paramAnonymousView);
        AC_MeasureScan.this.finish();
        AC_MeasureScan.this.overridePendingTransition(2130968580, 2130968581);
        return;
      case 2131296315: 
        paramAnonymousView = new Intent(AC_MeasureScan.this, AC_SettingMain.class);
        AC_MeasureScan.this.startActivity(paramAnonymousView);
        AC_MeasureScan.this.finish();
        AC_MeasureScan.this.overridePendingTransition(2130968580, 2130968581);
        return;
      case 2131296265: 
        AC_MeasureScan.this.time_ConnectionCheckStart = new Date().getTime();
        AC_MeasureScan.this.alert.dismiss();
        AC_MeasureScan.this.checkingDialog.show();
        return;
      }
      AC_MeasureScan.this.goBack();
    }
  };
  public int peak;
  String pointExplain = "";
  AudioRecord recorder;
  int recordingTangent = 0;
  private Thread recordingThread = null;
  int recordingValues = 0;
  public int samp;
  int scanPoint = 0;
  ImageView scan_image = null;
  private int stateEpiConnection = 0;
  private int stateEpiScan = -1;
  public int sum;
  ImageButton tab_record;
  ImageButton tab_setting;
  ImageButton tab_user;
  long time_CheckingStart = 0L;
  long time_ConnectionCheckStart = 0L;
  long time_EpiConnected = 0L;
  long time_EpiContactToFace = 0L;
  long time_EpiNotConnected = 0L;
  long time_LastMeasured = 0L;
  long time_overThreshold = 0L;
  private long time_record_start = 0L;
  Toast toast = null;
  TextView txt_Subtitle;
  TextView txt_explain;
  TextView txt_loading_status;
  TextView txt_percent;
  TextView txt_point;
  TextView txt_scanning;
  
  public static short byteArrayToShort(byte[] paramArrayOfByte, int paramInt, boolean paramBoolean)
  {
    if (paramArrayOfByte.length < paramInt + 2) {
      return -1;
    }
    int j;
    if (paramBoolean) {
      j = paramArrayOfByte[(paramInt + 1)];
    }
    for (paramInt = paramArrayOfByte[(paramInt + 0)];; paramInt = paramArrayOfByte[(paramInt + 1)])
    {
      return (short)(paramInt << 8 | j & 0xFF);
      j = paramArrayOfByte[(paramInt + 0)];
    }
  }
  
  private void changeFont()
  {
    Typeface localTypeface = Typeface.createFromAsset(getAssets(), "fonts/Daum_Regular.ttf");
    this.txt_explain.setTypeface(localTypeface);
    this.txt_Subtitle.setTypeface(localTypeface);
    this.txt_percent.setTypeface(localTypeface);
    this.txt_scanning.setTypeface(localTypeface);
  }
  
  private void checkConnectionWithEpi()
  {
    AudioManager localAudioManager = (AudioManager)getSystemService("audio");
    Log.i("smardi.Epi", "Wire connection:" + localAudioManager.isWiredHeadsetOn());
    if ((this.headSetState == 1) && (this.hasMicrophone == 1))
    {
      this.stateEpiConnection = 1;
      if (!this.isRecording)
      {
        this.isRecording = true;
        startRecording();
      }
      if (this.alert.isShowing())
      {
        this.alert.dismiss();
        if (this.isCheckingStart) {
          goBack();
        }
      }
    }
    do
    {
      do
      {
        return;
        this.stateEpiConnection = 0;
        this.list_MIC_data = new ArrayList();
        this.list_MIC_time = new ArrayList();
      } while (this.alert.isShowing());
      this.alert.show();
    } while (!this.isRecording);
    this.isRecording = false;
    this.recorder.stop();
    this.recorder.release();
  }
  
  private void checkEpiConnection()
  {
    this.time_ConnectionCheckStart = new Date().getTime();
    this.checkingDialog = ProgressDialog.show(this, "Connecting..", "Checking connection with Epi.\nPlease wait...", true, false);
    Log.e("smardi.Epi", "checkEpiConnection()");
  }
  
  private int getAverage(ArrayList<Integer> paramArrayList)
  {
    int k = 0;
    int j = 0;
    for (;;)
    {
      if (j >= paramArrayList.size()) {
        return k / paramArrayList.size();
      }
      k += ((Integer)paramArrayList.get(j)).intValue();
      j += 1;
    }
  }
  
  private int getAverageOfSqure(ArrayList<Integer> paramArrayList, int paramInt)
  {
    int k = 0;
    int j = 0;
    for (;;)
    {
      if (j >= paramArrayList.size()) {
        return k / paramArrayList.size();
      }
      k += (int)Math.sqrt(Math.pow(((Integer)paramArrayList.get(j)).intValue(), 2.0D) - Math.pow(paramInt, 2.0D));
      j += 1;
    }
  }
  
  private int getMaxValue(ArrayList<Integer> paramArrayList)
  {
    int k = 0;
    int j = 0;
    for (;;)
    {
      if (j >= paramArrayList.size()) {
        return k;
      }
      int m = k;
      if (k < Math.abs(((Integer)paramArrayList.get(j)).intValue())) {
        m = Math.abs(((Integer)paramArrayList.get(j)).intValue());
      }
      j += 1;
      k = m;
    }
  }
  
  private double getValue_R(ArrayList<Double> paramArrayList)
  {
    double d1 = 0.0D;
    int j = 0;
    double d2;
    if (j >= paramArrayList.size())
    {
      d2 = d1 / paramArrayList.size();
      d1 = 0.0D;
      j = 0;
    }
    for (;;)
    {
      if (j >= paramArrayList.size())
      {
        return Math.sqrt(d1) / (paramArrayList.size() - 1);
        d1 += ((Double)paramArrayList.get(j)).doubleValue();
        j += 1;
        break;
      }
      d1 += Math.pow(((Double)paramArrayList.get(j)).doubleValue() - d2, 2.0D);
      j += 1;
    }
  }
  
  private void goBack()
  {
    if (this.mode_measure == 1) {}
    for (Intent localIntent = new Intent(this, AC_MeasureMain.class);; localIntent = new Intent(this, AC_MeasureExplain.class))
    {
      startActivity(localIntent);
      finish();
      overridePendingTransition(2130968580, 2130968581);
      return;
    }
  }
  
  private void initComponents()
  {
    this.txt_explain = ((TextView)findViewById(2131296309));
    if (this.mode_measure == 1) {
      this.txt_explain.setText(getString(2131230764));
    }
    this.txt_point = ((TextView)findViewById(2131296353));
    this.txt_percent = ((TextView)findViewById(2131296342));
    this.txt_scanning = ((TextView)findViewById(2131296344));
    this.txt_loading_status = ((TextView)findViewById(2131296344));
    this.txt_Subtitle = ((TextView)findViewById(2131296308));
    this.m_volume = ((TextView)findViewById(2131296349));
    this.m_tangent = ((TextView)findViewById(2131296350));
    this.m_max_tangent = ((TextView)findViewById(2131296351));
    this.m_connected = ((TextView)findViewById(2131296352));
    this.btn_back = ((ImageButton)findViewById(2131296287));
    this.btn_scan = ((ImageButton)findViewById(2131296348));
    this.tab_record = ((ImageButton)findViewById(2131296313));
    this.tab_user = ((ImageButton)findViewById(2131296314));
    this.tab_setting = ((ImageButton)findViewById(2131296315));
    this.loadingScreen = ((RelativeLayout)findViewById(2131296301));
    this.scan_image = ((ImageView)findViewById(2131296346));
    this.alertBuilder = new AlertDialog.Builder(this);
    this.m_layout = ((LayoutInflater)getSystemService("layout_inflater")).inflate(2130903041, (ViewGroup)findViewById(2131296261));
    this.alertBuilder.setView(this.m_layout);
    this.alert = this.alertBuilder.create();
    this.btn_alert_button1 = ((Button)this.m_layout.findViewById(2131296265));
    this.btn_alert_button2 = ((Button)this.m_layout.findViewById(2131296266));
    this.mPref = new Manage_SharedPreference(this);
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("android.intent.action.HEADSET_PLUG");
    registerReceiver(this.mBroadcastReceiver, localIntentFilter);
  }
  
  private void initScanExplainDrawable()
  {
    this.listTutorialDrawable = new ArrayList();
    if (this.mPref.getUserGender().equals("Male"))
    {
      this.listTutorialDrawable.add(getResources().getDrawable(2130837717));
      this.listTutorialDrawable.add(getResources().getDrawable(2130837718));
      if (this.mode_measure == 0)
      {
        this.listTutorialDrawable.add(getResources().getDrawable(2130837721));
        return;
      }
      switch (this.mPref.getScanningPoint())
      {
      case 2: 
      default: 
        this.listTutorialDrawable.add(getResources().getDrawable(2130837721));
        return;
      case 0: 
        this.listTutorialDrawable.add(getResources().getDrawable(2130837722));
        return;
      case 1: 
        this.listTutorialDrawable.add(getResources().getDrawable(2130837720));
        return;
      }
      this.listTutorialDrawable.add(getResources().getDrawable(2130837719));
      return;
    }
    this.listTutorialDrawable.add(getResources().getDrawable(2130837711));
    this.listTutorialDrawable.add(getResources().getDrawable(2130837712));
    if (this.mode_measure == 0)
    {
      this.listTutorialDrawable.add(getResources().getDrawable(2130837715));
      return;
    }
    switch (this.mPref.getScanningPoint())
    {
    case 2: 
    default: 
      this.listTutorialDrawable.add(getResources().getDrawable(2130837715));
      return;
    case 0: 
      this.listTutorialDrawable.add(getResources().getDrawable(2130837716));
      return;
    case 1: 
      this.listTutorialDrawable.add(getResources().getDrawable(2130837714));
      return;
    }
    this.listTutorialDrawable.add(getResources().getDrawable(2130837713));
  }
  
  private ArrayList<Double> normalizeData(ArrayList<Integer> paramArrayList)
  {
    int k = 0;
    ArrayList localArrayList = new ArrayList();
    int j = 0;
    double d;
    if (j >= paramArrayList.size())
    {
      d = 100.0D / k;
      j = 0;
    }
    for (;;)
    {
      if (j >= paramArrayList.size())
      {
        return localArrayList;
        int n = ((Integer)paramArrayList.get(j)).intValue();
        int m = k;
        if (k < n) {
          m = n;
        }
        j += 1;
        k = m;
        break;
      }
      localArrayList.add(Double.valueOf(((Integer)paramArrayList.get(j)).intValue() * d));
      j += 1;
    }
  }
  
  private void prepare_loading_bar(boolean paramBoolean)
  {
    this.aniFrame = ((AnimationDrawable)((ImageView)findViewById(2131296343)).getBackground());
  }
  
  private void registButtonEvents()
  {
    this.btn_back.setOnClickListener(this.onClickListener);
    this.btn_scan.setOnClickListener(this.onClickListener);
    this.loadingScreen.setOnClickListener(this.onClickListener);
    this.tab_record.setOnClickListener(this.onClickListener);
    this.tab_user.setOnClickListener(this.onClickListener);
    this.tab_setting.setOnClickListener(this.onClickListener);
    this.btn_alert_button1.setOnClickListener(this.onClickListener);
    this.btn_alert_button2.setOnClickListener(this.onClickListener);
    this.alert.setOnCancelListener(new DialogInterface.OnCancelListener()
    {
      public void onCancel(DialogInterface paramAnonymousDialogInterface)
      {
        AC_MeasureScan.this.alert.show();
      }
    });
  }
  
  private void startRecording()
  {
    this.recorder = new AudioRecord(1, 8000, 12, 2, this.bufferSize);
    if (!this.mPref.getIsCalibrationCompleted())
    {
      this.txt_explain.setText(2131230785);
      this.mHandler.sendEmptyMessage(-100);
    }
    this.time_record_start = new Date().getTime();
    this.recorder.startRecording();
    this.isRecording = true;
    this.recordingThread = new Thread(new Runnable()
    {
      public void run()
      {
        AC_MeasureScan.this.writeAudioDataToFile();
      }
    }, "AudioRecorder Thread");
    this.recordingThread.start();
  }
  
  private void startTutorialAnimation()
  {
    this.cTimer_tutorial = new CountDownTimer(Long.MAX_VALUE, 500L)
    {
      Drawable animDrawable = null;
      int totCount = 0;
      
      public void onFinish() {}
      
      public void onTick(long paramAnonymousLong)
      {
        if (this.totCount % 6 < 3)
        {
          this.animDrawable = ((Drawable)AC_MeasureScan.this.listTutorialDrawable.get(this.totCount % 3));
          AC_MeasureScan.this.scan_image.setImageDrawable(this.animDrawable);
        }
        this.totCount += 1;
      }
    }.start();
  }
  
  private void writeAudioDataToFile()
  {
    byte[] arrayOfByte = new byte[this.bufferSize];
    if (!this.isRecording) {
      return;
    }
    int k = this.recorder.read(arrayOfByte, 0, this.bufferSize);
    ShortBuffer localShortBuffer = ByteBuffer.wrap(arrayOfByte).asShortBuffer();
    buffer = new short[localShortBuffer.remaining()];
    int j = 0;
    int m = localShortBuffer.remaining();
    for (;;)
    {
      if (j >= m)
      {
        if (-3 == k) {
          break;
        }
        calcamplitude();
        break;
      }
      buffer[j] = byteArrayToShort(arrayOfByte, j * 2, false);
      j += 1;
    }
  }
  
  void calcamplitude()
  {
    this.sum = 0;
    this.peak = 0;
    this.i = 0;
    if (this.i >= this.buflen)
    {
      this.avg = (this.sum / this.buflen);
      if ((this.time_record_start > 0L) && (!this.mPref.getIsCalibrationCompleted()))
      {
        if (new Date().getTime() - this.time_record_start >= 1500L) {
          break label189;
        }
        this.list_MIC_data.add(Integer.valueOf(this.peak));
      }
    }
    for (;;)
    {
      Message localMessage = new Message();
      localMessage.what = 0;
      localMessage.obj = String.valueOf(this.peak);
      this.mHandler.sendMessage(localMessage);
      return;
      this.samp = Math.abs(buffer[this.i]);
      if (this.samp > this.peak) {
        this.peak = this.samp;
      }
      this.sum += this.samp;
      this.i += 1;
      break;
      label189:
      this.time_record_start = -1L;
      this.mDevices.calibrationUnknownDevice(this.list_MIC_data);
      this.mHandler.sendEmptyMessage(100);
    }
  }
  
  protected void initDetailScan(int paramInt)
  {
    switch (paramInt)
    {
    }
    for (;;)
    {
      this.loadingScreen.setVisibility(8);
      this.isCheckingStart = false;
      this.isDoneChecking = false;
      return;
      this.txt_explain.setText(getString(2131230765));
      continue;
      this.txt_explain.setText(getString(2131230766));
    }
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903054);
    this.mDevices = new Devices(Build.DEVICE, Build.MODEL, this);
    this.mode_measure = getIntent().getIntExtra("mode", 0);
    initComponents();
    initScanExplainDrawable();
    this.isEpiConnected = false;
    prepare_loading_bar(true);
    changeFont();
    registButtonEvents();
    this.bufferSize = AudioRecord.getMinBufferSize(8000, 12, 2);
    buffer = new short[this.bufferSize];
    this.buflen = (this.bufferSize / 2);
    startTutorialAnimation();
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    Log.e("Epi", "onDestroy");
    if (this.isRecording)
    {
      this.isRecording = false;
      this.recorder.stop();
      this.recorder.release();
      Log.e("Epi", "release");
    }
    this.alert.dismiss();
    this.alert = null;
    unregisterReceiver(this.mBroadcastReceiver);
    if (this.isUserWantToExit) {
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
    goBack();
    return true;
  }
  
  protected void onPause()
  {
    super.onPause();
    Log.e("Epi", "onPause");
    if (this.isRecording)
    {
      this.isRecording = false;
      this.recorder.stop();
      this.recorder.release();
      Log.e("Epi", "release");
    }
  }
  
  protected void onResume()
  {
    super.onResume();
    checkConnectionWithEpi();
    if ((!this.isRecording) && (this.stateEpiConnection == 1))
    {
      startRecording();
      this.isRecording = true;
    }
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\measure\AC_MeasureScan.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */