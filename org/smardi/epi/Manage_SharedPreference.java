package org.smardi.epi;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Manage_SharedPreference
{
  public final int POINT_CHIN = 3;
  public final int POINT_FOREHEAD = 0;
  public final int POINT_LEFT_CHEEK = 1;
  public final int POINT_RIGHT_CHEEK = 2;
  private Context mContext;
  private SharedPreferences.Editor mEditor;
  private SharedPreferences mPref;
  final String preferenceName = "smardi.Epi";
  
  public Manage_SharedPreference(Context paramContext)
  {
    this.mContext = paramContext;
    this.mPref = this.mContext.getSharedPreferences("smardi.Epi", 0);
    this.mEditor = this.mPref.edit();
  }
  
  public boolean getAskedLocationPermission()
  {
    return this.mPref.getBoolean("askedLocationPermission", false);
  }
  
  public float getCalibration_a()
  {
    return this.mPref.getFloat("calibration_a", -1.0F);
  }
  
  public float getCalibration_b()
  {
    return this.mPref.getFloat("calibration_b", -1.0F);
  }
  
  public boolean getCheckedTutorial()
  {
    return this.mPref.getBoolean("checkedTutorial", false);
  }
  
  public int getDetailIndex()
  {
    return this.mPref.getInt("detailIndex", 0);
  }
  
  public int getDetailResult()
  {
    int[] arrayOfInt = new int[3];
    arrayOfInt[0] = this.mPref.getInt("DetailResult_1", 0);
    arrayOfInt[1] = this.mPref.getInt("DetailResult_2", 0);
    arrayOfInt[2] = this.mPref.getInt("DetailResult_3", 0);
    return (arrayOfInt[0] + arrayOfInt[1] + arrayOfInt[2]) / 3;
  }
  
  public boolean getGetLocationPermission()
  {
    return this.mPref.getBoolean("locationPermission", false);
  }
  
  public boolean getIsCalibrationCompleted()
  {
    return this.mPref.getBoolean("isCalibrationCompleted", true);
  }
  
  public String getOwnerEmail()
  {
    return this.mPref.getString("ownerEmail", "");
  }
  
  public String getOwnerName()
  {
    return this.mPref.getString("ownerName", "");
  }
  
  public int getOwnerUID()
  {
    return this.mPref.getInt("ownerUID", 0);
  }
  
  public int getScanZeroPoint()
  {
    return this.mPref.getInt("scanZeroPoint", 0);
  }
  
  public int getScanningPoint()
  {
    return this.mPref.getInt("scanningPoint", 0);
  }
  
  public String getScanningPointExplain()
  {
    switch (this.mPref.getInt("scanningPoint", 0))
    {
    default: 
      return "";
    case 0: 
      return this.mContext.getString(2131230726);
    case 1: 
      return this.mContext.getString(2131230728);
    case 2: 
      return this.mContext.getString(2131230727);
    }
    return this.mContext.getString(2131230729);
  }
  
  public String getScanningPointExplain(int paramInt)
  {
    switch (paramInt)
    {
    default: 
      return "";
    case 0: 
      return this.mContext.getString(2131230726);
    case 1: 
      return this.mContext.getString(2131230728);
    case 2: 
      return this.mContext.getString(2131230727);
    }
    return this.mContext.getString(2131230729);
  }
  
  public String getUserBirthday()
  {
    return this.mPref.getString("userBirthday", "2012-01-01");
  }
  
  public String getUserGender()
  {
    return this.mPref.getString("userGender", "Male");
  }
  
  public String getUserName()
  {
    return this.mPref.getString("userName", "");
  }
  
  public int getUserUID()
  {
    return this.mPref.getInt("userUID", 1);
  }
  
  public int getZeroValue()
  {
    return this.mPref.getInt("zeroValue", -1);
  }
  
  public void setAskedLocationPermission(boolean paramBoolean)
  {
    this.mEditor.putBoolean("askedLocationPermission", paramBoolean);
    this.mEditor.commit();
  }
  
  public void setCalibration_a(float paramFloat)
  {
    this.mEditor.putFloat("calibration_a", paramFloat);
    this.mEditor.commit();
  }
  
  public void setCalibration_b(float paramFloat)
  {
    this.mEditor.putFloat("calibration_b", paramFloat);
    this.mEditor.commit();
  }
  
  public void setCheckedTutorial(boolean paramBoolean)
  {
    this.mEditor.putBoolean("checkedTutorial", paramBoolean);
    this.mEditor.commit();
  }
  
  public void setDetailIndex(int paramInt)
  {
    this.mEditor.putInt("detailIndex", paramInt);
    this.mEditor.commit();
  }
  
  public void setDetailResults(int paramInt1, int paramInt2)
  {
    switch (paramInt1)
    {
    }
    for (;;)
    {
      this.mEditor.commit();
      return;
      this.mEditor.putInt("DetailResult_1", paramInt2);
      continue;
      this.mEditor.putInt("DetailResult_2", paramInt2);
      continue;
      this.mEditor.putInt("DetailResult_3", paramInt2);
    }
  }
  
  public void setGetLocationPermission(boolean paramBoolean)
  {
    this.mEditor.putBoolean("locationPermission", paramBoolean);
    this.mEditor.commit();
  }
  
  public void setIsCalibrationCompleted(boolean paramBoolean)
  {
    this.mEditor.putBoolean("isCalibrationCompleted", paramBoolean);
    this.mEditor.commit();
  }
  
  public void setOwnerEmail(String paramString)
  {
    this.mEditor.putString("ownerEmail", paramString);
    this.mEditor.commit();
  }
  
  public void setOwnerID(String paramString)
  {
    this.mEditor.putString("ownerID", paramString);
    this.mEditor.commit();
  }
  
  public void setOwnerName(String paramString)
  {
    this.mEditor.putString("ownerName", paramString);
    this.mEditor.commit();
  }
  
  public void setOwnerUID(int paramInt)
  {
    this.mEditor.putInt("ownerUID", paramInt);
    this.mEditor.commit();
  }
  
  public void setScanZeroPoint(int paramInt)
  {
    this.mEditor.putInt("scanZeroPoint", paramInt);
    this.mEditor.commit();
  }
  
  public void setScanningPoint(int paramInt)
  {
    this.mEditor.putInt("scanningPoint", paramInt);
    this.mEditor.commit();
  }
  
  public void setUserBirthday(String paramString)
  {
    this.mEditor.putString("userBirthday", paramString);
    this.mEditor.commit();
  }
  
  public void setUserGender(String paramString)
  {
    this.mEditor.putString("userGender", paramString);
    this.mEditor.commit();
  }
  
  public void setUserName(String paramString)
  {
    this.mEditor.putString("userName", paramString);
    this.mEditor.commit();
  }
  
  public void setUserUID(int paramInt)
  {
    this.mEditor.putInt("userUID", paramInt);
    this.mEditor.commit();
  }
  
  public void setZeroValue(int paramInt)
  {
    this.mEditor.putInt("zeroValue", paramInt);
    this.mEditor.commit();
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\Manage_SharedPreference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */