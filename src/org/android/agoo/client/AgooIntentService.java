package org.android.agoo.client;

import android.app.IntentService;
import android.os.Process;

public abstract class AgooIntentService extends IntentService
{
  public AgooIntentService(String paramString)
  {
    super(paramString);
  }

  public void onDestroy()
  {
    super.onDestroy();
    Process.sendSignal(Process.myPid(), 3);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.android.agoo.client.AgooIntentService
 * JD-Core Version:    0.6.2
 */