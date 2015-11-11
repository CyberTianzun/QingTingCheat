package cn.com.iresearch.mapptracker.fm;

import android.content.SharedPreferences.Editor;
import android.util.Log;
import cn.com.iresearch.mapptracker.fm.a.a;
import cn.com.iresearch.mapptracker.fm.dao.EventInfo;
import cn.com.iresearch.mapptracker.fm.dao.b;
import java.util.List;

final class f extends Thread
{
  f(boolean paramBoolean, EventInfo paramEventInfo, String paramString)
  {
  }

  public final void run()
  {
    try
    {
      if (this.a)
      {
        if (IRMonitor.i(IRMonitor.a()) <= IRMonitor.b(IRMonitor.a()).g())
        {
          IRMonitor localIRMonitor2 = IRMonitor.a();
          IRMonitor.a(localIRMonitor2, 1 + IRMonitor.i(localIRMonitor2));
          IRMonitor.e(IRMonitor.a()).putInt("event_Count", IRMonitor.i(IRMonitor.a()));
          IRMonitor.e(IRMonitor.a()).commit();
          IRMonitor.f(IRMonitor.a()).a(this.b);
          String str3 = "保存event_id= " + this.c + " 的事件";
          if (!IRMonitor.c)
            return;
          Log.e("MAT_EVENT", str3);
          return;
        }
        if (!IRMonitor.c)
          return;
        Log.e("MAT_EVENT", "Event记录数过多,存储失败");
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    List localList1 = IRMonitor.f(IRMonitor.a()).a(EventInfo.class, "event_id='" + this.c + "'");
    int i = 0;
    if (localList1 != null)
      i = localList1.size();
    if (i == 0)
    {
      if (IRMonitor.i(IRMonitor.a()) <= IRMonitor.b(IRMonitor.a()).g())
      {
        IRMonitor localIRMonitor1 = IRMonitor.a();
        IRMonitor.a(localIRMonitor1, 1 + IRMonitor.i(localIRMonitor1));
        IRMonitor.e(IRMonitor.a()).putInt("event_Count", IRMonitor.i(IRMonitor.a()));
        IRMonitor.e(IRMonitor.a()).commit();
        IRMonitor.f(IRMonitor.a()).a(this.b);
        String str1 = "新增加event_id= " + this.c + " 的事件";
        if (IRMonitor.c)
          Log.e("MAT_EVENT", str1);
      }
      else if (IRMonitor.c)
      {
        Log.e("MAT_EVENT", "Event记录数过多,存储失败");
      }
    }
    else
    {
      long l1 = this.b.getDuration();
      long l2 = this.b.getOpen_count();
      List localList2 = IRMonitor.f(IRMonitor.a()).a(EventInfo.class, "event_id='" + this.c + "'");
      if ((localList2 != null) && (localList2.size() > 0))
      {
        long l3 = l1 + ((EventInfo)localList2.get(0)).getDuration();
        long l4 = l2 + ((EventInfo)localList2.get(0)).getOpen_count();
        this.b.setDuration(l3);
        this.b.setOpen_count(l4);
      }
      IRMonitor.f(IRMonitor.a()).a(this.b, "event_id='" + this.c + "'");
      String str2 = "更新event_id= " + this.c + " 的事件";
      if (IRMonitor.c)
        Log.e("MAT_EVENT", str2);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.f
 * JD-Core Version:    0.6.2
 */