package org.smardi.epi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class PowerConnectionReceiver
  extends BroadcastReceiver
{
  private static boolean isPowerConnected = false;
  static int plugged = -1;
  Context mContext = null;
  
  public boolean isPowerConnected()
  {
    return (isPowerConnected) || (plugged == 1) || (plugged == 2);
  }
  
  public void onReceive(Context paramContext, Intent paramIntent)
  {
    this.mContext = paramContext;
    paramContext = paramIntent.getAction();
    plugged = paramIntent.getIntExtra("status", -1);
    if (paramContext.equals("android.intent.action.ACTION_POWER_CONNECTED")) {
      isPowerConnected = true;
    }
    while (!paramContext.equals("android.intent.action.ACTION_POWER_DISCONNECTED")) {
      return;
    }
    isPowerConnected = false;
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\PowerConnectionReceiver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */