package org.smardi.epi.measure;

import android.content.Context;
import android.os.Build;
import org.smardi.epi.Manage_SharedPreference;

public class PhoneInformation
{
  Build build = null;
  String device = null;
  Context mContext = null;
  Manage_SharedPreference mPref = null;
  
  public PhoneInformation(String paramString, Context paramContext)
  {
    this.mContext = paramContext;
    this.mPref = new Manage_SharedPreference(this.mContext);
    this.device = paramString;
  }
  
  public String getDevice()
  {
    return this.device;
  }
  
  public int getEpiZeroValue()
  {
    return (int)(this.mPref.getCalibration_a() * 560.0F + this.mPref.getCalibration_b());
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\measure\PhoneInformation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */