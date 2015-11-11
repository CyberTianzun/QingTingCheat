package org.android.agoo.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.ServiceConnection;
import android.os.IBinder;
import com.umeng.message.proguard.Q;
import org.android.agoo.service.IMessageService.Stub;

class BaseIntentService$3
  implements ServiceConnection
{
  BaseIntentService$3(BaseIntentService paramBaseIntentService)
  {
  }

  public void onServiceConnected(ComponentName paramComponentName, IBinder paramIBinder)
  {
    Q.c("BaseIntentService", "messageConnected pack[" + paramComponentName.getPackageName() + "]");
    BaseIntentService.a(this.a, true);
    BaseIntentService.a(this.a, IMessageService.Stub.asInterface(paramIBinder));
    Context localContext = this.a.getApplicationContext();
    BaseIntentService.b(this.a, localContext);
    BaseIntentService.c(this.a, localContext);
  }

  public void onServiceDisconnected(ComponentName paramComponentName)
  {
    Q.c("BaseIntentService", "messageDisconnected pack[" + paramComponentName.getPackageName() + "]");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.android.agoo.client.BaseIntentService.3
 * JD-Core Version:    0.6.2
 */