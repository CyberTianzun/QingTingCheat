package cn.com.iresearch.mapptracker.fm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import cn.com.iresearch.mapptracker.fm.a.a;
import cn.com.iresearch.mapptracker.fm.dao.EventInfo;
import cn.com.iresearch.mapptracker.fm.dao.MATMessage;
import cn.com.iresearch.mapptracker.fm.dao.SessionInfo;
import cn.com.iresearch.mapptracker.fm.util.f;
import java.io.PrintStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

final class b extends Thread
{
  b(List paramList, String paramString)
  {
  }

  public final void run()
  {
    long l1;
    long l2;
    long l3;
    try
    {
      IRMonitor.a(IRMonitor.a(), true);
      if (IRMonitor.d != null)
        IRMonitor.d.preSend();
      l1 = 0L;
      l2 = IRMonitor.a(IRMonitor.a()).getLong("open_count", 1L);
      l3 = 1L;
      if (IRMonitor.d(IRMonitor.a()) == null)
      {
        IRMonitor.a(IRMonitor.a(), false);
        return;
      }
      if (f.b(IRMonitor.d(IRMonitor.a())))
        break label147;
      long l4 = 1L + l2;
      IRMonitor.e(IRMonitor.a()).putLong("open_count", l4).commit();
      if (IRMonitor.c)
        Log.d("MAT_SESSION", "网络未打开！");
      IRMonitor.a(IRMonitor.a(), false);
      return;
    }
    catch (Exception localException)
    {
      if (IRMonitor.c)
        Log.e("MAT_SESSION", "数据发送失败，发生异常");
      localException.printStackTrace();
    }
    IRMonitor.a(IRMonitor.a(), false);
    return;
    label147: JSONObject localJSONObject1 = new JSONObject();
    JSONArray localJSONArray1 = new JSONArray();
    long l5 = this.a.size();
    label206: int i;
    List localList;
    JSONArray localJSONArray2;
    int j;
    if (0L == l5)
    {
      if (IRMonitor.c)
        Log.e("MAT_SESSION", "Session数据为空");
      IRMonitor.a(IRMonitor.a(), false);
      return;
      if (i >= l5)
      {
        localList = IRMonitor.f(IRMonitor.a()).b(EventInfo.class);
        localJSONArray2 = new JSONArray();
        j = 0;
        label240: if (j < localList.size())
          break label496;
        localJSONObject1.put("event_list", localJSONArray2);
        localJSONObject1.put("header", f.f(IRMonitor.d(IRMonitor.a())));
        localJSONObject1.put("open_count", l2);
        localJSONObject1.put("page_count", l3);
        localJSONObject1.put("run_time", l1);
        localJSONObject1.put("page_list", localJSONArray1);
        localJSONObject1.put("lat", "");
        if (!f.b())
          break label1075;
      }
    }
    label1049: label1069: label1075: for (String str2 = "1"; ; str2 = "0")
    {
      localJSONObject1.put("lng", str2);
      String str3 = localJSONObject1.toString();
      if ((1 == IRMonitor.b(IRMonitor.a()).f()) && (!f.a(IRMonitor.d(IRMonitor.a()))))
      {
        if (IRMonitor.c)
          Log.d("MAT_SESSION", "当前发送模式为只在wifi下发送，当前网络wifi不可用，故发送取消！");
        long l7 = 1L + l2;
        IRMonitor.e(IRMonitor.a()).putLong("open_count", l7).commit();
        IRMonitor.a(IRMonitor.a(), false);
        return;
        SessionInfo localSessionInfo = (SessionInfo)this.a.get(i);
        long l8 = localSessionInfo.getDuration();
        if (0L != l8)
        {
          l1 += l8;
          if (0L == localSessionInfo.inapp)
            l3 += 1L;
          localJSONArray1.put(i, IRMonitor.a(localSessionInfo));
          break label1049;
          label496: EventInfo localEventInfo1 = (EventInfo)localList.get(j);
          if (localEventInfo1.eventisStart)
            break label1069;
          JSONObject localJSONObject2 = IRMonitor.a(localEventInfo1);
          String str1 = localEventInfo1.getEvent_params();
          JSONObject localJSONObject3 = null;
          if (str1 != null)
            localJSONObject3 = new JSONObject(str1);
          localJSONObject2.put("event_params", localJSONObject3);
          localJSONArray2.put(j, localJSONObject2);
          break label1069;
        }
      }
      else
      {
        new MATMessage();
        Context localContext = IRMonitor.d(IRMonitor.a());
        MATMessage localMATMessage = f.a(localContext, str3, IRMonitor.b(IRMonitor.a()).a());
        System.out.println("send all data");
        if (localMATMessage.isFlag())
        {
          IRMonitor.e = 1 + IRMonitor.e;
          if (IRMonitor.c)
            Log.e("MAT_SESSION", "send data success!");
          String str5 = "send data num:" + IRMonitor.e;
          if (IRMonitor.c)
            Log.e("MAT_SESSION", str5);
          IRMonitor.g(IRMonitor.a());
          IRMonitor.e(IRMonitor.a()).putInt("sPage_Count", IRMonitor.h(IRMonitor.a()));
          IRMonitor.a(IRMonitor.a(), 0);
          IRMonitor.e(IRMonitor.a()).putInt("event_Count", IRMonitor.i(IRMonitor.a()));
          IRMonitor.e(IRMonitor.a()).putLong("open_count", 1L);
          IRMonitor.e(IRMonitor.a()).commit();
          IRMonitor.f(IRMonitor.a()).a(SessionInfo.class);
          IRMonitor.f(IRMonitor.a()).a(EventInfo.class);
          HashMap localHashMap = new HashMap();
          Iterator localIterator;
          if (IRMonitor.j(IRMonitor.a()) != null)
            localIterator = IRMonitor.j(IRMonitor.a()).values().iterator();
          while (true)
          {
            if (!localIterator.hasNext())
            {
              IRMonitor.j(IRMonitor.a()).clear();
              IRMonitor.j(IRMonitor.a()).putAll(localHashMap);
              localHashMap.clear();
              if (IRMonitor.d == null)
                break;
              IRMonitor.d.sendSuccess();
              break;
            }
            EventInfo localEventInfo2 = (EventInfo)localIterator.next();
            if (localEventInfo2.eventisStart)
            {
              localHashMap.put(localEventInfo2.getEvent_id(), localEventInfo2);
              String str6 = "未结束的事件:" + localEventInfo2.getEvent_id();
              if (IRMonitor.c)
                Log.d("MAT_EVENT", str6);
            }
          }
        }
        if (IRMonitor.d != null)
          IRMonitor.d.sendFail(localMATMessage.msg);
        long l6 = 1L + l2;
        IRMonitor.e(IRMonitor.a()).putLong("open_count", l6).commit();
        String str4 = "数据发送失败，open_count++ " + localMATMessage.msg;
        if (!IRMonitor.c)
          break;
        Log.e("MAT_SESSION", str4);
        break;
        i = 0;
        break label206;
      }
      long l9 = l3;
      long l10 = l1;
      i++;
      l1 = l10;
      l3 = l9;
      break label206;
      j++;
      break label240;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.b
 * JD-Core Version:    0.6.2
 */