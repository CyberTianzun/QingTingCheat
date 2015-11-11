package cn.com.iresearch.mapptracker.fm;

import android.content.SharedPreferences.Editor;
import android.util.Log;
import cn.com.iresearch.mapptracker.fm.dao.MATMessage;
import cn.com.iresearch.mapptracker.fm.util.f;

final class e extends Thread
{
  e(IRMonitor paramIRMonitor, String paramString1, String paramString2)
  {
  }

  public final void run()
  {
    while (true)
    {
      try
      {
        if (IRMonitor.d != null)
          IRMonitor.d.preSend();
        MATMessage localMATMessage = f.a(IRMonitor.d(IRMonitor.a()), this.b, this.c);
        if (localMATMessage == null)
          return;
        SharedPreferences.Editor localEditor = IRMonitor.e(this.a);
        if (localMATMessage.flag)
        {
          bool = false;
          localEditor.putBoolean("isFirstRun", bool).commit();
          if (localMATMessage.flag)
          {
            if (IRMonitor.d != null)
              IRMonitor.d.sendSuccess();
            String str2 = "send" + " session " + "data success!";
            if (IRMonitor.c)
              Log.e("MAT_SESSION", str2);
          }
          else
          {
            if (IRMonitor.d != null)
              IRMonitor.d.sendFail(localMATMessage.msg);
            String str1 = "send" + " session " + "data fail!: " + localMATMessage.msg;
            if (IRMonitor.c)
              Log.e("MAT_SESSION", str1);
          }
          return;
        }
      }
      catch (Exception localException)
      {
        return;
      }
      boolean bool = true;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.e
 * JD-Core Version:    0.6.2
 */