package fm.qingting.qtradio.push.log;

import android.content.Context;
import android.os.Bundle;
import fm.qingting.qtradio.abtest.ABTest;
import fm.qingting.qtradio.abtest.ABTestItem;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.push.bean.PushBean;
import fm.qingting.qtradio.push.bean.PushType;

public class NDPushLog
{
  public static final String NDPushable = "NDPushable";
  public static final String NDPusheClicked = "NDPusheClicked";
  public static final String NDPushedOneItem = "NDPushedOneItem";
  public static final String ProgramNotMatchError = "ProgramNotMatchError";

  private static String getCommonLogWithPushType(Context paramContext)
  {
    return ABTest.getCommonLogWithPushType(paramContext, new ABTestItem[0]);
  }

  public static void sendNDClickLog(Bundle paramBundle, int paramInt, PushType paramPushType, Context paramContext)
  {
    String str1 = getCommonLogWithPushType(paramContext);
    String str2 = str1 + "\"" + paramBundle.getString("categoryid") + "\"";
    String str3 = str2 + ",\"" + paramBundle.getString("parentid") + "\"";
    String str4 = str3 + ",\"" + paramBundle.getString("channelid") + "\"";
    String str5 = str4 + ",\"" + paramBundle.getString("channelname") + "\"";
    String str6 = str5 + ",\"" + paramBundle.getString("programid") + "\"";
    String str7 = str6 + ",\"" + paramBundle.getString("program_name") + "\"";
    String str8 = str7 + ",\"" + paramInt + "\"";
    String str9 = str8 + ",\"" + PushType.getPushType(paramPushType) + "\"";
    LogModule.getInstance().send("NDPusheClicked", str9);
  }

  public static void sendNDPushedLog(PushBean paramPushBean, Context paramContext)
  {
    String str1 = getCommonLogWithPushType(paramContext);
    String str2 = str1 + "\"" + paramPushBean.catId + "\"";
    String str3 = str2 + ",\"" + paramPushBean.parentId + "\"";
    String str4 = str3 + ",\"" + paramPushBean.channelId + "\"";
    String str5 = str4 + ",\"" + paramPushBean.name + "\"";
    String str6 = str5 + ",\"" + paramPushBean.pid + "\"";
    String str7 = str6 + ",\"" + paramPushBean.pname + "\"";
    String str8 = str7 + ",\"" + PushType.getPushType(paramPushBean.push_type) + "\"";
    LogModule.getInstance().send("NDPushedOneItem", str8);
  }

  public static void sendProgramNotMatchError(PushBean paramPushBean, Context paramContext)
  {
    String str1 = getCommonLogWithPushType(paramContext);
    String str2 = str1 + "\"" + paramPushBean.catId + "\"";
    String str3 = str2 + ",\"" + paramPushBean.parentId + "\"";
    String str4 = str3 + ",\"" + paramPushBean.channelId + "\"";
    String str5 = str4 + ",\"" + paramPushBean.name + "\"";
    String str6 = str5 + ",\"" + paramPushBean.pid + "\"";
    String str7 = str6 + ",\"" + paramPushBean.pname + "\"";
    String str8 = str7 + ",\"" + PushType.getPushType(paramPushBean.push_type) + "\"";
    LogModule.getInstance().send("ProgramNotMatchError", str8);
  }

  public static void sendPushableLog(int paramInt, boolean paramBoolean, PushType paramPushType, Context paramContext)
  {
    boolean bool = true;
    String str1 = getCommonLogWithPushType(paramContext);
    String str2 = str1 + "\"" + paramInt + "\"";
    StringBuilder localStringBuilder = new StringBuilder().append(str2).append(",\"");
    if (paramBoolean == bool);
    while (true)
    {
      String str3 = bool + "\"";
      String str4 = str3 + ",\"" + PushType.getPushType(paramPushType) + "\"";
      LogModule.getInstance().send("NDPushable", str4);
      return;
      bool = false;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.push.log.NDPushLog
 * JD-Core Version:    0.6.2
 */