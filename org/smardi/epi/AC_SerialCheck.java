package org.smardi.epi;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import org.smardi.epi.measure.AC_MeasureMain;

public class AC_SerialCheck
  extends Activity
{
  ImageButton btn_back;
  ImageButton btn_next;
  EditText m_serial;
  
  private void changeFont()
  {
    Typeface localTypeface = Typeface.createFromAsset(getAssets(), "fonts/MuseoSans_700.otf");
    this.m_serial.setTypeface(localTypeface);
  }
  
  private void registButtonEvents()
  {
    this.btn_back.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        AC_SerialCheck.this.finish();
      }
    });
    this.btn_next.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (AC_SerialCheck.this.checkSerialNumber())
        {
          paramAnonymousView = new Intent(AC_SerialCheck.this, AC_MeasureMain.class);
          AC_SerialCheck.this.startActivity(paramAnonymousView);
        }
      }
    });
  }
  
  protected boolean checkSerialNumber()
  {
    return true;
  }
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(2130903057);
    this.m_serial = ((EditText)findViewById(2131296372));
    this.btn_back = ((ImageButton)findViewById(2131296287));
    this.btn_next = ((ImageButton)findViewById(2131296288));
    changeFont();
    registButtonEvents();
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\AC_SerialCheck.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */