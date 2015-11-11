package org.android.agoo.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.umeng.message.proguard.Q;
import org.android.agoo.service.IElectionService;
import org.android.agoo.service.IElectionService.Stub;

class BaseIntentService$1
  implements ServiceConnection
{
  BaseIntentService$1(BaseIntentService paramBaseIntentService)
  {
  }

  public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
  {
    Q.c("BaseIntentService", "electionConnection pack[" + paramComponentName.getPackageName() + "]");
    try
    {
      Context localContext = this.a.getApplicationContext();
      BaseIntentService.a(this.a, IElectionService.Stub.asInterface(paramIBinder));
      if (BaseIntentService.a(this.a) != null)
      {
        BaseIntentService.a(this.a).election(localContext.getPackageName(), AgooSettings.getAgooReleaseTime(), "token");
        BaseIntentService.a(this.a, localContext);
      }
      return;
    }
    catch (Throwable localThrowable)
    {
      Q.d("BaseIntentService", "onServiceConnected", localThrowable);
    }
  }

  public void onServiceDisconnected(ComponentName paramComponentName)
  {
    Q.c("BaseIntentService", "electionDisconnected pack[" + paramComponentName.getPackageName() + "]");
    BaseIntentService.a(this.a, null);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.android.agoo.client.BaseIntentService.1
 * JD-Core Version:    0.6.2
 */