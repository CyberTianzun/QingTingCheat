package cn.com.iresearch.mapptracker.fm;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import cn.com.iresearch.mapptracker.fm.dao.b;
import cn.com.iresearch.mapptracker.fm.util.f;

final class a extends Thread
{
  a(IRMonitor paramIRMonitor, Context paramContext)
  {
  }

  public final void run()
  {
    while (true)
    {
      try
      {
        long l1 = IRMonitor.a(this.a).getLong("ConfigExpireTime", System.currentTimeMillis() / 1000L);
        long l2;
        if (!IRMonitor.a(this.a).getString("Pd", "").equals(""))
        {
          l2 = System.currentTimeMillis() / 1000L;
          if (l1 - l2 <= 0L)
            break label274;
          i = 1;
          if (i != 0)
          {
            String str1 = "configTime:" + l1 + " > currentTime:" + l2 + " 使用上次配置";
            if (!IRMonitor.c)
              break label265;
            Log.e("MAT_SESSION", str1);
            break label265;
          }
        }
        else
        {
          if (!f.b(this.b))
            break label249;
          IRMonitor.a(this.a, f.a(this.b, IRMonitor.b(this.a).d() + IRMonitor.a));
          if (IRMonitor.b(this.a) != null)
            break label273;
          IRMonitor.c(this.a);
          return;
        }
        String str2 = "configTime:" + l1 + " <= currentTime:" + l2 + " 获取新的配置";
        if (!IRMonitor.c)
          break label265;
        Log.e("MAT_SESSION", str2);
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      label249: IRMonitor.c(this.a);
      return;
      label265: 
      while (i != 0)
      {
        IRMonitor.c(this.a);
        return;
      }
      continue;
      label273: return;
      label274: int i = 0;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.a
 * JD-Core Version:    0.6.2
 */