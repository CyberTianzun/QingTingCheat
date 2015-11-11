package fm.qingting.qtradio.pushmessage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import fm.qingting.qtradio.model.GlobalCfg;
import java.util.ArrayList;
import java.util.List;

public class MessagePump
{
  private static MessagePump _instance;
  private boolean mAlias = false;
  private Context mContext;
  private boolean mGlobalPush = false;
  private List<IRecvPushMsgListener> mLstRecvPushMsgListeners = new ArrayList();
  private MsgReceiver mMsgReceiver;
  private boolean mRecvPushMsgBak = false;

  private void dispatchRecvMsg(PushMessage paramPushMessage, int paramInt)
  {
    if (paramPushMessage == null);
    while (true)
    {
      return;
      for (int i = 0; (i < this.mLstRecvPushMsgListeners.size()) && (!((IRecvPushMsgListener)this.mLstRecvPushMsgListeners.get(i)).onRecvPushMsg(paramPushMessage, paramInt)); i++);
    }
  }

  public static MessagePump getInstance()
  {
    if (_instance == null)
      _instance = new MessagePump();
    return _instance;
  }

  private void initMsgReceiver()
  {
    if (this.mContext == null)
      return;
    this.mMsgReceiver = new MsgReceiver();
    IntentFilter localIntentFilter = new IntentFilter();
    localIntentFilter.addAction("fm.qingting.qtradio.GEXIN_MESSAGE");
    localIntentFilter.addAction("fm.qingting.qtradio.GEXIN_MESSAGE_BAK");
    this.mContext.registerReceiver(this.mMsgReceiver, localIntentFilter);
  }

  private void log(String paramString)
  {
  }

  private void releaseMsgReceiver()
  {
    try
    {
      if (this.mMsgReceiver != null)
      {
        this.mContext.unregisterReceiver(this.mMsgReceiver);
        this.mMsgReceiver = null;
      }
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void init(Context paramContext, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.mContext = paramContext;
    initMsgReceiver();
    this.mGlobalPush = paramBoolean1;
    this.mAlias = paramBoolean2;
  }

  public void registerRecvMsg(IRecvPushMsgListener paramIRecvPushMsgListener)
  {
    if (paramIRecvPushMsgListener == null)
      return;
    for (int i = 0; ; i++)
    {
      if (i >= this.mLstRecvPushMsgListeners.size())
        break label40;
      if (this.mLstRecvPushMsgListeners.get(i) == paramIRecvPushMsgListener)
        break;
    }
    label40: this.mLstRecvPushMsgListeners.add(paramIRecvPushMsgListener);
  }

  public void release()
  {
    this.mLstRecvPushMsgListeners.clear();
    releaseMsgReceiver();
  }

  public static abstract interface IRecvPushMsgListener
  {
    public abstract boolean onRecvPushMsg(PushMessage paramPushMessage, int paramInt);
  }

  class MsgReceiver extends BroadcastReceiver
  {
    MsgReceiver()
    {
    }

    public void onReceive(Context paramContext, Intent paramIntent)
    {
      label4: if (paramIntent == null)
        break label4;
      Bundle localBundle;
      do
      {
        do
          return;
        while ((paramIntent.getAction() == null) || ((!paramIntent.getAction().equalsIgnoreCase("fm.qingting.qtradio.GEXIN_MESSAGE")) && (!paramIntent.getAction().equalsIgnoreCase("fm.qingting.qtradio.GEXIN_MESSAGE_BAK"))));
        localBundle = paramIntent.getExtras();
        if (localBundle == null)
          break;
        String str1 = localBundle.getString("type");
        if (str1 == null)
          break;
        switch (Integer.valueOf(str1).intValue())
        {
        case 1:
        default:
          return;
        case 0:
        case 2:
        }
      }
      while ((paramIntent.getAction().equalsIgnoreCase("fm.qingting.qtradio.GEXIN_MESSAGE_BAK")) && (MessagePump.this.mRecvPushMsgBak));
      MessagePump.access$002(MessagePump.this, true);
      String str3 = localBundle.getString("msg");
      String str4 = localBundle.getString("topic");
      String str5 = localBundle.getString("alias");
      PushMessage localPushMessage2 = new PushMessage();
      localPushMessage2.mAlias = str5;
      localPushMessage2.mTopic = str4;
      localPushMessage2.mMessage = str3;
      if ((str3 != null) && (!str3.contains("qingting:startService")))
      {
        String str6 = GlobalCfg.getInstance(MessagePump.this.mContext).getRecvPushMsgTask();
        if ((str6 != null) && (str3.equalsIgnoreCase(str6)))
        {
          PushMessageLog.sendGetuiMsgFromServiceLog(MessagePump.this.mContext, MessagePump.this.mAlias, MessagePump.this.mGlobalPush);
          return;
        }
        GlobalCfg.getInstance(MessagePump.this.mContext).setRecvPushMsgTask(str3);
      }
      MessagePump.this.dispatchRecvMsg(localPushMessage2, 0);
      PushMessageLog.sendGetuiMsgFromServiceLog(MessagePump.this.mContext, MessagePump.this.mAlias, MessagePump.this.mGlobalPush);
      return;
      String str2 = localBundle.getString("reg");
      PushMessage localPushMessage1 = new PushMessage();
      localPushMessage1.mRegId = str2;
      MessagePump.this.dispatchRecvMsg(localPushMessage1, 2);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.pushmessage.MessagePump
 * JD-Core Version:    0.6.2
 */