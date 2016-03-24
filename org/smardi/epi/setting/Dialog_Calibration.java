package org.smardi.epi.setting;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import java.nio.ByteBuffer;
import java.nio.ShortBuffer;
import java.util.ArrayList;
import java.util.Date;
import org.smardi.epi.Manage_SharedPreference;
import org.smardi.epi.device.Devices;

public class Dialog_Calibration
  extends Activity
{
  private static final int RECORDER_AUDIO_ENCODING = 2;
  private static final int RECORDER_CHANNELS = 12;
  private static final int RECORDER_SAMPLERATE = 8000;
  public static short[] buffer;
  public static long dt;
  public static long t1;
  public static long t2;
  private final boolean D = false;
  private final long DATA_COLLECT_TIME = 2000L;
  private final int STATE_CONNECTION_COMPLETE = 4;
  private final int STATE_CONNECTION_EARPHONE_CHECKED = 1;
  private final int STATE_CONNECTION_EARPHONE_NONE = 0;
  private final int STATE_CONNECTION_VALUE_CHECKED = 3;
  private final int STATE_CONNECTION_VALUE_CHECKING = 2;
  private AlertDialog alert;
  AlertDialog.Builder alertBuilder;
  public int avg;
  Button btn_alert_button2 = null;
  Button btn_calibration = null;
  Button btn_cancel = null;
  private int bufferSize = 0;
  public int buflen;
  public float db;
  int hasMicrophone = 0;
  int headSetState = 0;
  public int i;
  boolean isCheckingStart = false;
  private boolean isReadyForCheckConnection = false;
  private boolean isRecording = false;
  private ArrayList<Integer> list_MIC_data = new ArrayList();
  BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver()
  {
    public void onReceive(Context paramAnonymousContext, Intent paramAnonymousIntent)
    {
      if (paramAnonymousIntent.getAction().equals("android.intent.action.HEADSET_PLUG"))
      {
        Dialog_Calibration.this.headSetState = paramAnonymousIntent.getIntExtra("state", 0);
        Dialog_Calibration.this.hasMicrophone = paramAnonymousIntent.getIntExtra("microphone", 0);
        if (Dialog_Calibration.this.isReadyForCheckConnection) {
          Dialog_Calibration.this.checkConnectionWithEpi();
        }
      }
    }
  };
  Devices mDevices = null;
  Manage_SharedPreference mPref = null;
  View m_layout;
  View.OnClickListener onClickListener = new View.OnClickListener()
  {
    public void onClick(View paramAnonymousView)
    {
      switch (paramAnonymousView.getId())
      {
      default: 
        return;
      case 2131296298: 
        Dialog_Calibration.this.isReadyForCheckConnection = true;
        Dialog_Calibration.this.checkConnectionWithEpi();
        return;
      case 2131296299: 
        Dialog_Calibration.this.finish();
        return;
      }
      Dialog_Calibration.this.finish();
    }
  };
  public int peak;
  AudioRecord recorder;
  private Thread recordingThread = null;
  public int samp;
  private int stateEpiConnection = 0;
  public int sum;
  private long time_record_start = 0L;
  
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
          finish();
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
      } while (this.alert.isShowing());
      this.alert.show();
    } while (!this.isRecording);
    this.isRecording = false;
    this.recorder.stop();
    this.recorder.release();
  }
  
  private void initComponent()
  {
    this.mPref = new Manage_SharedPreference(this);
    this.btn_calibration = ((Button)findViewById(2131296298));
    this.btn_calibration.setOnClickListener(this.onClickListener);
    this.btn_cancel = ((Button)findViewById(2131296299));
    this.btn_cancel.setOnClickListener(this.onClickListener);
    this.btn_alert_button2 = ((Button)this.m_layout.findViewById(2131296266));
    this.btn_alert_button2.setOnClickListener(this.onClickListener);
  }
  
  private void startRecording()
  {
    this.recorder = new AudioRecord(1, 8000, 12, 2, this.bufferSize);
    this.recorder.startRecording();
    this.time_record_start = new Date().getTime();
    this.isRecording = true;
    this.recordingThread = new Thread(new Runnable()
    {
      public void run()
      {
        Dialog_Calibration.this.writeAudioDataToFile();
      }
    }, "AudioRecorder Thread");
    this.recordingThread.start();
  }
  
  private void stopRecording()
  {
    if (this.isRecording)
    {
      this.isRecording = false;
      this.recorder.stop();
      this.recorder.release();
    }
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
    for (this.i = 0;; this.i += 1)
    {
      if (this.i >= this.buflen)
      {
        this.avg = (this.sum / this.buflen);
        if ((this.time_record_start <= 0L) || (new Date().getTime() - this.time_record_start >= 2000L)) {
          break;
        }
        this.list_MIC_data.add(Integer.valueOf(this.peak));
        return;
      }
      this.samp = Math.abs(buffer[this.i]);
      if (this.samp > this.peak) {
        this.peak = this.samp;
      }
      this.sum += this.samp;
    }
    this.mDevices.calibrationUnknownDevice(this.list_MIC_data);
    stopRecording();
    Intent localIntent = new Intent();
    localIntent.setAction("OKOK");
    setResult(0, localIntent);
    finish();
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    setContentView(2130903045);
    this.mDevices = new Devices(Build.DEVICE, Build.MODEL, this);
    this.bufferSize = AudioRecord.getMinBufferSize(8000, 12, 2);
    buffer = new short[this.bufferSize];
    this.buflen = (this.bufferSize / 2);
    this.alertBuilder = new AlertDialog.Builder(this);
    this.m_layout = ((LayoutInflater)getSystemService("layout_inflater")).inflate(2130903041, (ViewGroup)findViewById(2131296261));
    this.alertBuilder.setView(this.m_layout);
    this.alert = this.alertBuilder.create();
    initComponent();
    paramBundle = new IntentFilter();
    paramBundle.addAction("android.intent.action.HEADSET_PLUG");
    registerReceiver(this.mBroadcastReceiver, paramBundle);
  }
  
  protected void onDestroy()
  {
    super.onDestroy();
    unregisterReceiver(this.mBroadcastReceiver);
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\setting\Dialog_Calibration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */