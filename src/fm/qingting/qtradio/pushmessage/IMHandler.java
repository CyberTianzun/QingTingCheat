package fm.qingting.qtradio.pushmessage;

import android.content.Context;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import fm.qingting.qtradio.data.IMDatabaseDS;
import fm.qingting.qtradio.im.IMContacts;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.im.message.IMMessage;
import fm.qingting.qtradio.model.GlobalCfg;
import fm.qingting.utils.ProcessDetect;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class IMHandler
  implements MessagePump.IRecvPushMsgListener
{
  private static final String IM = "qingting:im";
  private static IMHandler _instance;
  private long SEND_NOTIFICATION_INTERVAL = 2000L;
  private boolean loginSuccess = true;
  private String mAlias;
  private Context mContext;
  private String mLastFromId = null;
  private String mMsgType = "pullmsg";
  private int mRecvMsgCnt = 0;
  private long mRecvMsgTime = 0L;
  private Map<String, Integer> mapUnReadCnt = new HashMap();
  private long msgSeq = -1L;

  private boolean canHandle(String paramString)
  {
    if (paramString == null);
    while (!paramString.equalsIgnoreCase("qingting:im"))
      return false;
    return ProcessDetect.processExists(this.mContext.getPackageName() + ":local", null);
  }

  private boolean canHandle(String paramString, int paramInt)
  {
    if (paramString == null);
    do
    {
      return false;
      if (paramInt == 0)
        return true;
    }
    while ((paramInt != 1) || (!IMContacts.getInstance().hasWatchedGroup(paramString)));
    return true;
  }

  public static IMHandler getInstance()
  {
    if (_instance == null)
      _instance = new IMHandler();
    return _instance;
  }

  private void handleIM(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")));
    while (true)
    {
      return;
      try
      {
        JSONObject localJSONObject = (JSONObject)JSON.parse(paramString);
        String str1 = localJSONObject.getString("event");
        if (str1 != null)
        {
          if (this.msgSeq == -1L)
            this.msgSeq = GlobalCfg.getInstance(this.mContext).getMsgSeq();
          if (str1.equalsIgnoreCase("peer"))
          {
            if ((this.loginSuccess) && (canHandle(localJSONObject.getString("from"), 0)))
              recvMsg(0, null, localJSONObject.getJSONArray("body"));
          }
          else if ((str1.equalsIgnoreCase("group")) && (this.loginSuccess))
          {
            String str2 = localJSONObject.getString("to");
            if (canHandle(str2, 1))
            {
              recvMsg(1, str2, localJSONObject.getJSONArray("body"));
              return;
            }
          }
        }
      }
      catch (Exception localException)
      {
      }
    }
  }

  private boolean handleMsg(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return false;
    try
    {
      JSONObject localJSONObject = (JSONObject)JSON.parse(paramString);
      if (localJSONObject != null)
      {
        String str = localJSONObject.getString("msg");
        if (str != null)
        {
          handleIM(str);
          return true;
        }
      }
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private void log(String paramString)
  {
  }

  private void recvMsg(int paramInt, String paramString, JSONArray paramJSONArray)
  {
    int i = 0;
    if (paramJSONArray != null);
    while (true)
    {
      try
      {
        if (paramJSONArray.size() == 1)
        {
          IMMessage localIMMessage1 = new IMMessage();
          IMMessage.parseData(paramJSONArray.getJSONObject(0), localIMMessage1);
          localIMMessage1.chatType = paramInt;
          if (localIMMessage1 != null)
          {
            this.msgSeq = (1L + this.msgSeq);
            localIMMessage1.msgSeq = this.msgSeq;
            this.mRecvMsgCnt = (1 + this.mRecvMsgCnt);
            if (localIMMessage1.isGroupMsg())
            {
              if ((localIMMessage1.mFromGroupId == null) || (localIMMessage1.mFromGroupId.equalsIgnoreCase("")))
                localIMMessage1.mFromGroupId = paramString;
              if (localIMMessage1.mGroupName == null)
              {
                GroupInfo localGroupInfo = IMContacts.getInstance().getWatchedGroup(localIMMessage1.mFromGroupId);
                if (localGroupInfo != null)
                  localIMMessage1.mGroupName = localGroupInfo.groupName;
              }
              else
              {
                IMDatabaseDS.getInstance().appendGroupMessage(localIMMessage1, true);
                Integer localInteger3 = (Integer)this.mapUnReadCnt.get(localIMMessage1.mFromGroupId);
                if (localInteger3 == null)
                {
                  this.mapUnReadCnt.put(localIMMessage1.mFromGroupId, Integer.valueOf(1));
                  storeUnReadMsgCnt();
                  sendNotification(localIMMessage1);
                  return;
                }
                Integer localInteger4 = Integer.valueOf(1 + localInteger3.intValue());
                this.mapUnReadCnt.put(localIMMessage1.mFromGroupId, localInteger4);
                continue;
              }
            }
            else
            {
              IMDatabaseDS.getInstance().appendPrivateMessage(localIMMessage1, true);
              Integer localInteger1 = (Integer)this.mapUnReadCnt.get(localIMMessage1.mFromID);
              if (localInteger1 == null)
              {
                this.mapUnReadCnt.put(localIMMessage1.mFromID, Integer.valueOf(1));
                storeUnReadMsgCnt();
                continue;
              }
              Integer localInteger2 = Integer.valueOf(1 + localInteger1.intValue());
              this.mapUnReadCnt.put(localIMMessage1.mFromID, localInteger2);
              continue;
            }
          }
        }
        else if (paramJSONArray.size() > 1)
        {
          ArrayList localArrayList = new ArrayList();
          if (i < paramJSONArray.size())
          {
            IMMessage localIMMessage2 = new IMMessage();
            IMMessage.parseData(paramJSONArray.getJSONObject(i), localIMMessage2);
            localIMMessage2.chatType = paramInt;
            this.msgSeq = (1L + this.msgSeq);
            localIMMessage2.msgSeq = this.msgSeq;
            if (localIMMessage2 == null)
              break label491;
            localArrayList.add(localIMMessage2);
            break label491;
          }
          if (localArrayList.size() > 0)
          {
            if (!((IMMessage)localArrayList.get(0)).isGroupMsg())
            {
              IMDatabaseDS.getInstance().appendListPrivateMessage(localArrayList, true);
              this.mRecvMsgCnt += localArrayList.size();
              return;
            }
            IMDatabaseDS.getInstance().appendListGroupMessage(localArrayList, true);
            continue;
          }
        }
        return;
      }
      catch (Exception localException)
      {
        return;
      }
      label491: i++;
    }
  }

  private void sendNotification(IMMessage paramIMMessage)
  {
    if (paramIMMessage == null)
      return;
    long l = System.currentTimeMillis();
    if (l - this.mRecvMsgTime < this.SEND_NOTIFICATION_INTERVAL)
    {
      this.mRecvMsgTime = l;
      return;
    }
    this.mRecvMsgTime = l;
    String str1 = "";
    if (paramIMMessage.isGroupMsg())
    {
      String str7 = paramIMMessage.mFromGroupId;
      if (str7 != null)
      {
        if ((this.mLastFromId != null) && (!str7.equalsIgnoreCase(this.mLastFromId)))
          break label185;
        if (paramIMMessage.mGroupName == null)
          paramIMMessage.mGroupName = "蜻蜓fm";
        String str8 = paramIMMessage.mGroupName;
        String str9 = str8 + "发来";
        String str10 = str9 + String.valueOf(this.mRecvMsgCnt);
        str1 = str10 + "消息";
        this.mLastFromId = str7;
      }
    }
    while (true)
    {
      MessageNotification.sendIMNotification(paramIMMessage, this.mContext, str1);
      return;
      label185: String str11 = "您收到" + String.valueOf(this.mRecvMsgCnt);
      str1 = str11 + "消息";
      this.mLastFromId = "#";
      continue;
      String str2 = paramIMMessage.mFromID;
      if (str2 != null)
        if ((this.mLastFromId == null) || (str2.equalsIgnoreCase(this.mLastFromId)))
        {
          String str3 = paramIMMessage.mFromName;
          String str4 = str3 + "发来";
          String str5 = str4 + String.valueOf(this.mRecvMsgCnt);
          str1 = str5 + "消息";
          this.mLastFromId = str2;
        }
        else
        {
          String str6 = "蜻蜓fm收到" + String.valueOf(this.mRecvMsgCnt);
          str1 = str6 + "消息";
          this.mLastFromId = "#";
        }
    }
  }

  private void storeUnReadMsgCnt()
  {
    if (this.mapUnReadCnt.size() > 0)
    {
      Iterator localIterator = this.mapUnReadCnt.entrySet().iterator();
      Object localObject1 = "";
      Object localObject2 = "";
      int i = 0;
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        int j = ((Integer)localEntry.getValue()).intValue();
        String str1 = (String)localObject2 + (String)localEntry.getKey();
        String str2 = (String)localObject1 + String.valueOf(j);
        if (i != -1 + this.mapUnReadCnt.size())
        {
          str1 = str1 + "_";
          str2 = str2 + "_";
        }
        i++;
        localObject1 = str2;
        localObject2 = str1;
      }
      GlobalCfg.getInstance(this.mContext).setUnReadCnt((String)localObject1);
      GlobalCfg.getInstance(this.mContext).setUnReadID((String)localObject2);
    }
  }

  public void clearMsgCnt()
  {
    this.mLastFromId = null;
    this.mRecvMsgCnt = 0;
    this.mRecvMsgTime = 0L;
    initUnReadMsg();
  }

  public void init(Context paramContext)
  {
    MessagePump.getInstance().registerRecvMsg(this);
    this.mContext = paramContext;
  }

  public void initUnReadMsg()
  {
    String str1 = GlobalCfg.getInstance(this.mContext).getUnReadID();
    String str2 = GlobalCfg.getInstance(this.mContext).getUnReadCnt();
    String[] arrayOfString1;
    if ((str1 != null) && (str2 != null))
    {
      arrayOfString1 = str1.split("_");
      if (arrayOfString1 != null)
        break label43;
    }
    while (true)
    {
      return;
      label43: String[] arrayOfString2 = str2.split("_");
      if ((arrayOfString2 != null) && (arrayOfString2.length == arrayOfString1.length))
        for (int i = 0; i < arrayOfString1.length; i++)
          this.mapUnReadCnt.put(arrayOfString1[i], Integer.valueOf(arrayOfString2[i]));
    }
  }

  public boolean onRecvPushMsg(PushMessage paramPushMessage, int paramInt)
  {
    if (paramPushMessage == null);
    while ((paramInt != 0) || (!canHandle(paramPushMessage.mTopic)))
      return false;
    return handleMsg(paramPushMessage.mMessage);
  }

  public void setAlias(String paramString)
  {
    this.mAlias = paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.pushmessage.IMHandler
 * JD-Core Version:    0.6.2
 */