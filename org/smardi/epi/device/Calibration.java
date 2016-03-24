package org.smardi.epi.device;

class Calibration
{
  private double a = 0.0D;
  private double b = 0.0D;
  private String model = null;
  
  public Calibration(String paramString, double paramDouble1, double paramDouble2)
  {
    this.model = paramString;
    this.a = paramDouble1;
    this.b = paramDouble2;
  }
  
  public double getA()
  {
    return this.a;
  }
  
  public double getB()
  {
    return this.b;
  }
  
  public String getModel()
  {
    return this.model;
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\device\Calibration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */