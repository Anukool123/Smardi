package org.smardi.epi.setting;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AC_About
  extends Activity
{
  Button btn_exit;
  
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    setContentView(2130903058);
    this.btn_exit = ((Button)findViewById(2131296373));
    this.btn_exit.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        AC_About.this.finish();
      }
    });
  }
}


/* Location:              D:\Sarah\epi skin moisture\dex2jar-2.0\dex2jar-2.0\classes-dex2jar.jar!\org\smardi\epi\setting\AC_About.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */