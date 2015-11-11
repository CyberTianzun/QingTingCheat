package cn.com.iresearch.mapptracker.fm;

import android.content.SharedPreferences.Editor;
import android.util.Log;
import cn.com.iresearch.mapptracker.fm.dao.MATMessage;
import cn.com.iresearch.mapptracker.fm.util.f;

final class c extends Thread
{
  c(IRMonitor paramIRMonitor, String paramString1, String paramString2)
  {
  }

  public final void run()
  {
    try
    {
      if (IRMonitor.d != null)
        IRMonitor.d.preSend();
      MATMessage localMATMessage = f.a(IRMonitor.d(IRMonitor.a()), this.b, this.c);
      if (localMATMessage == null)
        return;
      boolean bool;
      if (localMATMessage.flag)
      {
        if (IRMonitor.c)
          Log.e("MAT_SESSION", "send---dayData---data success!");
        SharedPreferences.Editor localEditor = IRMonitor.e(this.a);
        if (localMATMessage.flag)
        {
          bool = false;
          localEditor.putBoolean("isFirstRun", bool).commit();
          String str2 = f.a();
          if (!"".equals(str2))
          {
            long l = Long.valueOf(str2).longValue();
            IRMonitor.e(this.a).putLong("daysend", l);
          }
          if (IRMonitor.d != null)
            IRMonitor.d.sendSuccess();
        }
      }
      while (true)
      {
        IRMonitor.k(this.a);
        return;
        bool = true;
        break;
        if (IRMonitor.d != null)
          IRMonitor.d.sendFail(localMATMessage.msg);
        String str1 = "send---dayData---data fail!: " + localMATMessage.msg;
        if (IRMonitor.c)
          Log.e("MAT_SESSION", str1);
      }
    }
    catch (Exception localException)
    {
      while (true)
        IRMonitor.d.sendFail(localException.getMessage());
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.c
 * JD-Core Version:    0.6.2
 */