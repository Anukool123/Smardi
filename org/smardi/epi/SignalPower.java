package org.smardi.epi;

public final class SignalPower
{
  private static final float FUDGE = 0.6F;
  private static final float MAX_16_BIT = 32768.0F;
  
  public static final void biasAndRange(short[] paramArrayOfShort, int paramInt1, int paramInt2, float[] paramArrayOfFloat)
  {
    int k = 32767;
    int j = 32768;
    int n = 0;
    int i = paramInt1;
    for (;;)
    {
      if (i >= paramInt1 + paramInt2)
      {
        float f1 = n / paramInt2;
        float f2 = k;
        f2 = Math.abs(j - f1 - (f2 + f1)) / 2.0F;
        paramArrayOfFloat[0] = f1;
        paramArrayOfFloat[1] = f2;
        return;
      }
      int m = paramArrayOfShort[i];
      int i1 = n + m;
      n = k;
      if (m < k) {
        n = m;
      }
      k = j;
      if (m > j) {
        k = m;
      }
      i += 1;
      j = k;
      k = n;
      n = i1;
    }
  }
  
  public static final double calculatePowerDb(short[] paramArrayOfShort, int paramInt1, int paramInt2)
  {
    double d2 = 0.0D;
    double d1 = 0.0D;
    int i = 0;
    for (;;)
    {
      if (i >= paramInt2) {
        return Math.log10((d1 - d2 * d2 / paramInt2) / paramInt2 / 1.073741824E9D) * 10.0D + 0.6000000238418579D;
      }
      long l = paramArrayOfShort[(paramInt1 + i)];
      d2 += l;
      d1 += l * l;
      i += 1;
    }
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\SignalPower.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */