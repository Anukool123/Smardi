package org.smardi.epi.device;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;
import org.smardi.epi.Manage_SharedPreference;

public class Devices
{
  private final boolean D = true;
  private final String TAG = "Deivces";
  ArrayList<Calibration> list_Calibrations = new ArrayList();
  Context mContext = null;
  String mDeviceName = null;
  String mModelName = null;
  Manage_SharedPreference mPref = null;
  
  public Devices(String paramString1, String paramString2, Context paramContext)
  {
    this.mDeviceName = paramString1;
    this.mModelName = paramString2;
    this.mContext = paramContext;
    this.mPref = new Manage_SharedPreference(paramContext);
    loadCalibrateData();
  }
  
  private double convertCorneometerToGalaxyS2(double paramDouble)
  {
    int[] arrayOfInt1 = new int[11];
    arrayOfInt1[1] = 10;
    arrayOfInt1[2] = 20;
    arrayOfInt1[3] = 30;
    arrayOfInt1[4] = 40;
    arrayOfInt1[5] = 50;
    arrayOfInt1[6] = 60;
    arrayOfInt1[7] = 70;
    arrayOfInt1[8] = 80;
    arrayOfInt1[9] = 90;
    arrayOfInt1[10] = 99;
    int[] arrayOfInt2 = new int[11];
    int[] tmp79_77 = arrayOfInt2;
    tmp79_77[0] = 'Ȱ';
    int[] tmp85_79 = tmp79_77;
    tmp85_79[1] = 'ɘ';
    int[] tmp91_85 = tmp85_79;
    tmp91_85[2] = 'ˮ';
    int[] tmp97_91 = tmp91_85;
    tmp97_91[3] = '΄';
    int[] tmp103_97 = tmp97_91;
    tmp103_97[4] = 'Ұ';
    int[] tmp109_103 = tmp103_97;
    tmp109_103[5] = 'ٲ';
    int[] tmp115_109 = tmp109_103;
    tmp115_109[6] = 'ஸ';
    int[] tmp122_115 = tmp115_109;
    tmp122_115[7] = 'ᝰ';
    int[] tmp129_122 = tmp122_115;
    tmp129_122[8] = '⫸';
    int[] tmp136_129 = tmp129_122;
    tmp136_129[9] = '丠';
    int[] tmp143_136 = tmp136_129;
    tmp143_136[10] = '紀';
    tmp143_136;
    int j = -1;
    int i = 0;
    for (;;)
    {
      if (i >= tmp79_77.length - 1) {
        i = j;
      }
      for (;;)
      {
        j = i;
        if (i < 0) {
          j = 0;
        }
        return j;
        if (tmp79_77[(tmp79_77.length - 1)] < paramDouble)
        {
          i = 99;
        }
        else
        {
          if ((tmp79_77[i] >= paramDouble) || (paramDouble > tmp79_77[(i + 1)])) {
            break;
          }
          double d1 = tmp79_77[i];
          double d2 = tmp79_77[(i + 1)];
          double d3 = tmp85_79[i];
          i = (int)Math.round((tmp85_79[(i + 1)] - d3) / (d2 - d1) * (paramDouble - d1) + d3);
        }
      }
      i += 1;
    }
  }
  
  private double convertGalaxyS2toCorneometer(double paramDouble)
  {
    int[] arrayOfInt1 = new int[11];
    int[] tmp8_6 = arrayOfInt1;
    tmp8_6[0] = 'Ȱ';
    int[] tmp14_8 = tmp8_6;
    tmp14_8[1] = 'ɘ';
    int[] tmp20_14 = tmp14_8;
    tmp20_14[2] = 'ˮ';
    int[] tmp26_20 = tmp20_14;
    tmp26_20[3] = '΄';
    int[] tmp32_26 = tmp26_20;
    tmp32_26[4] = 'Ұ';
    int[] tmp38_32 = tmp32_26;
    tmp38_32[5] = 'ٲ';
    int[] tmp44_38 = tmp38_32;
    tmp44_38[6] = 'ஸ';
    int[] tmp51_44 = tmp44_38;
    tmp51_44[7] = 'ᝰ';
    int[] tmp58_51 = tmp51_44;
    tmp58_51[8] = '⫸';
    int[] tmp65_58 = tmp58_51;
    tmp65_58[9] = '丠';
    int[] tmp72_65 = tmp65_58;
    tmp72_65[10] = '紀';
    tmp72_65;
    int[] arrayOfInt2 = new int[11];
    arrayOfInt2[1] = 10;
    arrayOfInt2[2] = 20;
    arrayOfInt2[3] = 30;
    arrayOfInt2[4] = 40;
    arrayOfInt2[5] = 50;
    arrayOfInt2[6] = 60;
    arrayOfInt2[7] = 70;
    arrayOfInt2[8] = 80;
    arrayOfInt2[9] = 90;
    arrayOfInt2[10] = 99;
    int j = -1;
    int i = 0;
    for (;;)
    {
      if (i >= tmp8_6.length - 1) {
        i = j;
      }
      for (;;)
      {
        j = i;
        if (i == -1) {
          j = 0;
        }
        return j;
        if ((tmp8_6[i] < paramDouble) && (paramDouble <= tmp8_6[(i + 1)]))
        {
          double d1 = tmp8_6[i];
          double d2 = tmp8_6[(i + 1)];
          double d3 = arrayOfInt2[i];
          i = (int)Math.round((arrayOfInt2[(i + 1)] - d3) / (d2 - d1) * (paramDouble - d1) + d3);
        }
        else
        {
          if (tmp8_6[(tmp8_6.length - 1)] >= paramDouble) {
            break;
          }
          i = 99;
        }
      }
      i += 1;
    }
  }
  
  private void loadCalibrateData()
  {
    this.list_Calibrations = new ArrayList();
    this.list_Calibrations.add(new Calibration("SHV-E160S", 0.30824971D, 24.3703822D));
    this.list_Calibrations.add(new Calibration("SHW-E420S", 1.62635935D, -50.02675D));
    this.list_Calibrations.add(new Calibration("SHV-E210L", 3.0466D, 4900.5D));
    this.list_Calibrations.add(new Calibration("SHV-E210K", 0.3348D, 46.36D));
    this.list_Calibrations.add(new Calibration("SHV-E210S", 0.3348D, 46.36D));
    this.list_Calibrations.add(new Calibration("SHW-M440S", 0.37890395D, 50.02675D));
    this.list_Calibrations.add(new Calibration("SHW-M250S", 1.0D, 0.0D));
    this.list_Calibrations.add(new Calibration("SHV-E110S", 0.32243968D, 19.5965815D));
    this.list_Calibrations.add(new Calibration("SHV-E120S", 0.31747439D, 20.3654282D));
    this.list_Calibrations.add(new Calibration("LG-F160K", 0.19012734D, 37.8713654D));
    this.list_Calibrations.add(new Calibration("LG-F120L", 0.22164741D, 14.0891137D));
    this.list_Calibrations.add(new Calibration("KU-5400", 0.1066D, 16.24D));
    this.list_Calibrations.add(new Calibration("LG-LU6200", 3.975D, 299.5D));
    this.list_Calibrations.add(new Calibration("LG-F200L", 1.399D, 96.34D));
    this.list_Calibrations.add(new Calibration("LG-F100L", 0.2134D, 13.28D));
    this.list_Calibrations.add(new Calibration("LG-P720", 0.1106D, 20.81D));
    this.list_Calibrations.add(new Calibration("LG-F180L", 0.03844D, 7.21D));
    this.list_Calibrations.add(new Calibration("LG-SU660", 0.3906D, 99.51D));
    this.list_Calibrations.add(new Calibration("SHV-E250S", 0.5803586189D, -245.03039916063D));
    this.list_Calibrations.add(new Calibration("SHV-E250K", 0.5064112786D, -287.4950466807D));
    this.list_Calibrations.add(new Calibration("SHV-E250L", 0.57374D, -238.9D));
    this.list_Calibrations.add(new Calibration("SHW-M480K", 2.8923641982D, 1512.2689134428D));
    this.list_Calibrations.add(new Calibration("SHW-M480S", 2.8923641982D, 1512.2689134428D));
    this.list_Calibrations.add(new Calibration("SHW-M480L", 2.8923641982D, 1512.2689134428D));
    this.list_Calibrations.add(new Calibration("SHV-E230S", 2.8923641982D, 1512.2689134428D));
    this.list_Calibrations.add(new Calibration("SHV-E230K", 2.8923641982D, 1512.2689134428D));
    this.list_Calibrations.add(new Calibration("SHV-E230L", 2.8923641982D, 1512.2689134428D));
    this.list_Calibrations.add(new Calibration("SHV-E300S", 0.255988D, -193.713D));
    this.list_Calibrations.add(new Calibration("SHV-E300K", 0.255988D, -193.713D));
    this.list_Calibrations.add(new Calibration("SHV-E300L", 0.255988D, -193.713D));
    this.list_Calibrations.add(new Calibration("LG-F240L", 0.0167164D, 4.17433D));
    this.list_Calibrations.add(new Calibration("LG-F240S", 0.0167164D, 4.17433D));
    this.list_Calibrations.add(new Calibration("LG-F240K", 0.0167164D, 4.17433D));
    this.list_Calibrations.add(new Calibration("SHW-M380W", 2.18475D, 303.254D));
    this.list_Calibrations.add(new Calibration("SM-N9006", 4.34023D, -464.636D));
    this.list_Calibrations.add(new Calibration("SM-N900L", 4.34023D, -464.636D));
    this.list_Calibrations.add(new Calibration("SM-N900K", 4.34023D, -464.636D));
    this.list_Calibrations.add(new Calibration("SM-N900S", 4.34023D, -464.636D));
    Log.e(this.mModelName, this.mModelName);
    int i = 0;
    for (;;)
    {
      if (i >= this.list_Calibrations.size())
      {
        if (((!this.mPref.getIsCalibrationCompleted()) || (this.mPref.getCalibration_a() != -1.0F)) && (this.mPref.getCalibration_b() != -1.0F)) {
          break;
        }
        this.mPref.setIsCalibrationCompleted(false);
        return;
      }
      if (this.mModelName.equals(((Calibration)this.list_Calibrations.get(i)).getModel()))
      {
        this.mPref.setCalibration_a((float)((Calibration)this.list_Calibrations.get(i)).getA());
        this.mPref.setCalibration_b((float)((Calibration)this.list_Calibrations.get(i)).getB());
      }
      i += 1;
    }
    this.mPref.setIsCalibrationCompleted(true);
  }
  
  public void calibrationUnknownDevice(ArrayList<Integer> paramArrayList)
  {
    int k = 0;
    int i = 0;
    int j = 0;
    for (;;)
    {
      if (j >= paramArrayList.size())
      {
        j = k;
        if (paramArrayList.size() > 0) {
          j = (int)(i / paramArrayList.size() * 1.05D);
        }
        float f1 = (float)(0.0015963881D * j - 0.0143716944D);
        float f2 = j - 560.0F * f1;
        this.mPref.setCalibration_a(f1);
        this.mPref.setCalibration_b(f2);
        this.mPref.setZeroValue(j);
        Log.e("Deivces", "average:" + j + " a:" + f1 + " b:" + f2);
        return;
      }
      i += ((Integer)paramArrayList.get(j)).intValue();
      j += 1;
    }
  }
  
  public double getCorneometerValue(int paramInt)
  {
    if ((this.mPref.getCalibration_a() == -1.0F) || (this.mPref.getCalibration_b() == -1.0F))
    {
      paramInt = 0;
      for (;;)
      {
        if (paramInt >= this.list_Calibrations.size()) {}
        for (;;)
        {
          Log.e("Deivces", "model:" + this.mModelName);
          return -1.0D;
          Log.e("Deivces", "list:" + ((Calibration)this.list_Calibrations.get(paramInt)).getModel() + "Phone:" + this.mModelName);
          if (!this.mModelName.equals(((Calibration)this.list_Calibrations.get(paramInt)).getModel())) {
            break;
          }
          d1 = ((Calibration)this.list_Calibrations.get(paramInt)).getA();
          d2 = ((Calibration)this.list_Calibrations.get(paramInt)).getB();
          this.mPref.setCalibration_a((float)d1);
          this.mPref.setCalibration_b((float)d2);
        }
        paramInt += 1;
      }
    }
    double d1 = this.mPref.getCalibration_a();
    double d3 = this.mPref.getCalibration_b();
    double d2 = (paramInt - d3) / d1;
    Log.e("Deivces", "model:" + this.mModelName);
    Log.e("Deivces", "a:" + d1);
    Log.e("Deivces", "b:" + d3);
    Log.e("Deivces", "measured:" + paramInt);
    Log.e("Deivces", "galaxyS2:" + d2);
    if ((!this.mModelName.equals("LG-F240L")) && (!this.mModelName.equals("LG-F240K")))
    {
      d1 = d2;
      if (!this.mModelName.equals("LG-F240S")) {}
    }
    else
    {
      d1 = d2 + 1000.0D;
    }
    return convertGalaxyS2toCorneometer(d1);
  }
  
  public int getMicValue(double paramDouble)
  {
    if ((this.mPref.getCalibration_a() == -1.0F) || (this.mPref.getCalibration_b() == -1.0F))
    {
      int i = 0;
      for (;;)
      {
        if (i >= this.list_Calibrations.size()) {}
        for (;;)
        {
          Log.e("Deivces", "model:" + this.mModelName);
          return -1;
          Log.e("Deivces", "list:" + ((Calibration)this.list_Calibrations.get(i)).getModel() + "Phone:" + this.mModelName);
          if (!this.mModelName.equals(((Calibration)this.list_Calibrations.get(i)).getModel())) {
            break;
          }
          paramDouble = ((Calibration)this.list_Calibrations.get(i)).getA();
          d1 = ((Calibration)this.list_Calibrations.get(i)).getB();
          this.mPref.setCalibration_a((float)paramDouble);
          this.mPref.setCalibration_b((float)d1);
        }
        i += 1;
      }
    }
    double d1 = this.mPref.getCalibration_a();
    double d2 = this.mPref.getCalibration_b();
    return (int)(convertCorneometerToGalaxyS2(paramDouble) * d1 + d2);
  }
  
  public int getZeroValue()
  {
    return this.mPref.getZeroValue();
  }
  
  public boolean isCalibratedDevice()
  {
    int i = 0;
    for (;;)
    {
      if (i >= this.list_Calibrations.size()) {
        return false;
      }
      if (this.mModelName.equals(((Calibration)this.list_Calibrations.get(i)).getModel()))
      {
        i = (int)(this.mPref.getCalibration_a() * 560.0F + this.mPref.getCalibration_b());
        this.mPref.setZeroValue(i);
        return true;
      }
      i += 1;
    }
  }
  
  public boolean isCalibratedDevice(String paramString)
  {
    int i = 0;
    for (;;)
    {
      if (i >= this.list_Calibrations.size()) {
        return false;
      }
      if (paramString.equals(((Calibration)this.list_Calibrations.get(i)).getModel())) {
        return true;
      }
      i += 1;
    }
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\device\Devices.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */