package com.flotandroidchart.flot.options;

public class ColorHelper
{
  public int _a;
  public int _b;
  public int _g;
  public int _r;
  
  public ColorHelper(int paramInt)
  {
    this._r = (paramInt >> 16 & 0xFF);
    this._g = (paramInt >> 8 & 0xFF);
    this._b = (paramInt & 0xFF);
    this._a = 255;
  }
  
  public ColorHelper(int paramInt1, int paramInt2, int paramInt3)
  {
    this._r = paramInt1;
    this._g = paramInt2;
    this._b = paramInt3;
    this._a = 255;
    normalize();
  }
  
  public ColorHelper(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this._r = paramInt1;
    this._g = paramInt2;
    this._b = paramInt3;
    this._a = paramInt4;
    normalize();
  }
  
  public ColorHelper add(String paramString, int paramInt)
  {
    if (paramString.indexOf('r') != -1) {
      this._r += paramInt;
    }
    if (paramString.indexOf('g') != -1) {
      this._g += paramInt;
    }
    if (paramString.indexOf('b') != -1) {
      this._b += paramInt;
    }
    return normalize();
  }
  
  public double clamp(double paramDouble1, double paramDouble2, double paramDouble3)
  {
    if (paramDouble2 < paramDouble1) {
      return paramDouble1;
    }
    if (paramDouble2 > paramDouble3) {
      return paramDouble3;
    }
    return paramDouble2;
  }
  
  public ColorHelper normalize()
  {
    this._r = ((int)clamp(0.0D, this._r, 255.0D));
    this._g = ((int)clamp(0.0D, this._g, 255.0D));
    this._b = ((int)clamp(0.0D, this._b, 255.0D));
    this._a = ((int)clamp(0.0D, this._a, 255.0D));
    return this;
  }
  
  public int rgb()
  {
    return this._r << 16 | this._g << 8 | this._b;
  }
  
  public ColorHelper scale(String paramString, double paramDouble)
  {
    if (paramString.indexOf('r') != -1) {
      this._r = ((int)(this._r * paramDouble));
    }
    if (paramString.indexOf('g') != -1) {
      this._g = ((int)(this._g * paramDouble));
    }
    if (paramString.indexOf('b') != -1) {
      this._b = ((int)(this._b * paramDouble));
    }
    return normalize();
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\com\flotandroidchart\flot\options\ColorHelper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */