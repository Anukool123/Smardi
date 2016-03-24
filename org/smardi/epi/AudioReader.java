package org.smardi.epi;

import android.media.AudioRecord;
import android.util.Log;
import java.lang.reflect.Array;

public class AudioReader
{
  private static final String TAG = "WindMeter";
  private AudioRecord audioInput;
  private int inputBlockSize = 0;
  private short[][] inputBuffer = null;
  private int inputBufferIndex = 0;
  private int inputBufferWhich = 0;
  private Listener inputListener = null;
  private Thread readerThread = null;
  private boolean running = false;
  private long sleepTime = 0L;
  
  private void readDone(short[] paramArrayOfShort)
  {
    this.inputListener.onReadComplete(paramArrayOfShort);
  }
  
  private void readError(int paramInt)
  {
    this.inputListener.onReadError(paramInt);
  }
  
  private void readerRun()
  {
    int i = 200;
    for (;;)
    {
      if (i > 0) {}
      try
      {
        j = this.audioInput.getState();
        if (j != 1) {
          break label53;
        }
      }
      catch (InterruptedException localInterruptedException1)
      {
        int j;
        for (;;) {}
      }
      if (this.audioInput.getState() == 1) {
        break;
      }
      Log.e("WindMeter", "Audio reader failed to initialize");
      readError(1);
      this.running = false;
      return;
      label53:
      Thread.sleep(50L);
      i -= 50;
    }
    for (;;)
    {
      long l1;
      try
      {
        Log.i("WindMeter", "Reader: Start Recording");
        this.audioInput.startRecording();
        boolean bool = this.running;
        if (!bool) {
          return;
        }
        l1 = System.currentTimeMillis();
        if (!this.running) {
          continue;
        }
        j = this.inputBlockSize;
        int k = this.inputBlockSize - this.inputBufferIndex;
        i = j;
        if (j > k) {
          i = k;
        }
        short[] arrayOfShort1 = this.inputBuffer[this.inputBufferWhich];
        j = this.inputBufferIndex;
        try
        {
          j = this.audioInput.read(arrayOfShort1, j, i);
          i = 0;
          if (!this.running) {
            continue;
          }
        }
        finally {}
        if (j >= 0) {
          break label284;
        }
      }
      finally
      {
        Log.i("WindMeter", "Reader: Stop Recording");
        if (this.audioInput.getState() == 3) {
          this.audioInput.stop();
        }
      }
      Log.e("WindMeter", "Audio read failed: error " + j);
      readError(2);
      this.running = false;
      continue;
      label284:
      j = this.inputBufferIndex + j;
      if (j >= this.inputBlockSize)
      {
        this.inputBufferWhich = ((this.inputBufferWhich + 1) % 2);
        this.inputBufferIndex = 0;
        i = 1;
        if (i != 0)
        {
          readDone(arrayOfShort2);
          long l2 = System.currentTimeMillis();
          long l3 = this.sleepTime;
          l2 = l3 - (l2 - l1);
          l1 = l2;
          if (l2 < 5L) {
            l1 = 5L;
          }
        }
      }
      try
      {
        arrayOfShort2.wait(l1);
        continue;
        this.inputBufferIndex = j;
      }
      catch (InterruptedException localInterruptedException2)
      {
        for (;;) {}
      }
    }
  }
  
  public void startReader(int paramInt1, int paramInt2, Listener paramListener)
  {
    Log.i("WindMeter", "Reader: Start Thread");
    try
    {
      this.audioInput = new AudioRecord(1, paramInt1, 2, 2, AudioRecord.getMinBufferSize(paramInt1, 2, 2) * 2);
      this.inputBlockSize = paramInt2;
      this.sleepTime = ((1000.0F / (paramInt1 / paramInt2)));
      paramInt1 = this.inputBlockSize;
      this.inputBuffer = ((short[][])Array.newInstance(Short.TYPE, new int[] { 2, paramInt1 }));
      this.inputBufferWhich = 0;
      this.inputBufferIndex = 0;
      this.inputListener = paramListener;
      this.running = true;
      this.readerThread = new Thread(new Runnable()
      {
        public void run()
        {
          AudioReader.this.readerRun();
        }
      }, "Audio Reader");
      this.readerThread.start();
      return;
    }
    finally {}
  }
  
  /* Error */
  public void stopReader()
  {
    // Byte code:
    //   0: ldc 13
    //   2: ldc -80
    //   4: invokestatic 98	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   7: pop
    //   8: aload_0
    //   9: monitorenter
    //   10: aload_0
    //   11: iconst_0
    //   12: putfield 47	org/smardi/epi/AudioReader:running	Z
    //   15: aload_0
    //   16: monitorexit
    //   17: aload_0
    //   18: getfield 49	org/smardi/epi/AudioReader:readerThread	Ljava/lang/Thread;
    //   21: ifnull +10 -> 31
    //   24: aload_0
    //   25: getfield 49	org/smardi/epi/AudioReader:readerThread	Ljava/lang/Thread;
    //   28: invokevirtual 179	java/lang/Thread:join	()V
    //   31: aload_0
    //   32: aconst_null
    //   33: putfield 49	org/smardi/epi/AudioReader:readerThread	Ljava/lang/Thread;
    //   36: aload_0
    //   37: monitorenter
    //   38: aload_0
    //   39: getfield 69	org/smardi/epi/AudioReader:audioInput	Landroid/media/AudioRecord;
    //   42: ifnull +15 -> 57
    //   45: aload_0
    //   46: getfield 69	org/smardi/epi/AudioReader:audioInput	Landroid/media/AudioRecord;
    //   49: invokevirtual 182	android/media/AudioRecord:release	()V
    //   52: aload_0
    //   53: aconst_null
    //   54: putfield 69	org/smardi/epi/AudioReader:audioInput	Landroid/media/AudioRecord;
    //   57: aload_0
    //   58: monitorexit
    //   59: ldc 13
    //   61: ldc -72
    //   63: invokestatic 98	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   66: pop
    //   67: return
    //   68: astore_1
    //   69: aload_0
    //   70: monitorexit
    //   71: aload_1
    //   72: athrow
    //   73: astore_1
    //   74: aload_0
    //   75: monitorexit
    //   76: aload_1
    //   77: athrow
    //   78: astore_1
    //   79: goto -48 -> 31
    // Local variable table:
    //   start	length	slot	name	signature
    //   0	82	0	this	AudioReader
    //   68	4	1	localObject1	Object
    //   73	4	1	localObject2	Object
    //   78	1	1	localInterruptedException	InterruptedException
    // Exception table:
    //   from	to	target	type
    //   10	17	68	finally
    //   69	71	68	finally
    //   38	57	73	finally
    //   57	59	73	finally
    //   74	76	73	finally
    //   17	31	78	java/lang/InterruptedException
  }
  
  public static abstract class Listener
  {
    public static final int ERR_INIT_FAILED = 1;
    public static final int ERR_OK = 0;
    public static final int ERR_READ_FAILED = 2;
    
    public abstract void onReadComplete(short[] paramArrayOfShort);
    
    public abstract void onReadError(int paramInt);
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\AudioReader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */