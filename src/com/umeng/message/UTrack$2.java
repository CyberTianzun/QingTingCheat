package com.umeng.message;

import com.umeng.common.message.Log;
import java.util.ArrayList;

class UTrack$2
  implements Runnable
{
  UTrack$2(UTrack paramUTrack)
  {
  }

  public void run()
  {
    try
    {
      ArrayList localArrayList;
      for (Object localObject2 = MsgLogStore.getInstance(UTrack.a(this.a)).getMsgLogs(1); ((ArrayList)localObject2).size() > 0; localObject2 = localArrayList)
      {
        MsgLogStore.MsgLog localMsgLog = (MsgLogStore.MsgLog)((ArrayList)localObject2).get(0);
        UTrack.a(this.a, localMsgLog.msgId, localMsgLog.actionType, localMsgLog.time);
        localArrayList = MsgLogStore.getInstance(UTrack.a(this.a)).getMsgLogs(1);
      }
      return;
    }
    catch (Throwable localThrowable)
    {
      localThrowable.printStackTrace();
      return;
    }
    finally
    {
      Log.c(UTrack.a(), "sendCachedMsgLog finished, clear cacheLogSending flag");
      UTrack.a(false);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.message.UTrack.2
 * JD-Core Version:    0.6.2
 */