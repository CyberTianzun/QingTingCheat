package fm.qingting.qtradio.im.message;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.room.UserInfo;
import java.util.ArrayList;
import java.util.List;

public class IMMessage
{
  public int chatType;
  public String mFromAvatar = "";
  public String mFromGroupId = "";
  public String mFromID = "";
  public String mFromName = "";
  public String mGender = "n";
  public String mGroupName = "";
  public String mImage;
  public int mLike;
  public String mMessage = "";
  public String mMsgId = "";
  public String mToUserId = "";
  public long msgSeq = -1L;
  public long publish;

  public static JSONObject buildBarrage(String paramString1, String paramString2, UserInfo paramUserInfo)
  {
    if (paramUserInfo == null);
    while ((paramString1 == null) && (paramString2 == null))
      return null;
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("msg", paramString1);
      localJSONObject.put("sendTime", Long.valueOf(System.currentTimeMillis() / 1000L));
      return localJSONObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static String buildGroupInfo(String paramString1, String paramString2, int paramInt)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("groupDesc", paramString1);
      localJSONObject.put("ownerId", paramString2);
      localJSONObject.put("approve", Integer.valueOf(paramInt));
      label35: return localJSONObject.toString();
    }
    catch (Exception localException)
    {
      break label35;
    }
  }

  public static JSONObject buildIMMessage(String paramString, UserInfo paramUserInfo, GroupInfo paramGroupInfo)
  {
    if ((paramString == null) || (paramUserInfo == null) || (paramString.equalsIgnoreCase("")) || (paramGroupInfo == null))
      return null;
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("msg", paramString);
      localJSONObject.put("sendTime", Long.valueOf(System.currentTimeMillis() / 1000L));
      localJSONObject.put("groupName", paramGroupInfo.groupName);
      return localJSONObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static JSONObject buildIMMessage(String paramString, UserInfo paramUserInfo1, UserInfo paramUserInfo2)
  {
    if ((paramString == null) || (paramUserInfo1 == null) || (paramString.equalsIgnoreCase("")) || (paramUserInfo2 == null))
      return null;
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("msg", paramString);
      localJSONObject.put("sendTime", Long.valueOf(System.currentTimeMillis() / 1000L));
      return localJSONObject;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
    return null;
  }

  public static List<IMMessage> parseData(List<String> paramList)
  {
    if ((paramList == null) || (paramList.size() == 0))
      return null;
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; i < paramList.size(); i++)
    {
      IMMessage localIMMessage = new IMMessage();
      if (parseData((String)paramList.get(i), localIMMessage))
        localArrayList.add(localIMMessage);
    }
    return localArrayList;
  }

  public static boolean parseData(JSONObject paramJSONObject, IMMessage paramIMMessage)
  {
    if ((paramJSONObject == null) || (paramIMMessage == null))
      return false;
    try
    {
      String str = paramJSONObject.getString("msg");
      long l = paramJSONObject.getLongValue("sendTime");
      if (l == 0L)
        l = paramJSONObject.getLongValue("publish");
      paramIMMessage.mToUserId = paramJSONObject.getString("toUserId");
      paramIMMessage.mMessage = str;
      paramIMMessage.publish = l;
      return true;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  public static boolean parseData(IMMessage paramIMMessage1, IMMessage paramIMMessage2)
  {
    if ((paramIMMessage1 == null) || (paramIMMessage2 == null))
      return false;
    return parseData(paramIMMessage1.mMessage, paramIMMessage2);
  }

  public static boolean parseData(String paramString, IMMessage paramIMMessage)
  {
    if ((paramString == null) || (paramIMMessage == null))
      return false;
    while (true)
    {
      String str2;
      try
      {
        JSONObject localJSONObject = (JSONObject)JSON.parse(paramString);
        String str1 = localJSONObject.getString("msg");
        str2 = localJSONObject.getString("fromUserId");
        if (str2 == null)
        {
          str3 = localJSONObject.getString("userId");
          int i = localJSONObject.getIntValue("chatType");
          String str4 = localJSONObject.getString("groupId");
          long l = localJSONObject.getLongValue("sendTime");
          if (l == 0L)
            l = localJSONObject.getLongValue("publish");
          String str5 = localJSONObject.getString("groupName");
          paramIMMessage.mToUserId = localJSONObject.getString("toUserId");
          paramIMMessage.mFromAvatar = localJSONObject.getString("fromAvatar");
          if (paramIMMessage.mFromAvatar == null)
            paramIMMessage.mFromAvatar = localJSONObject.getString("userAvatar");
          paramIMMessage.mMessage = str1;
          paramIMMessage.mFromID = str3;
          paramIMMessage.chatType = i;
          paramIMMessage.mFromGroupId = str4;
          paramIMMessage.publish = l;
          paramIMMessage.mGroupName = str5;
          return true;
        }
      }
      catch (Exception localException)
      {
        return false;
      }
      String str3 = str2;
    }
  }

  public static boolean parseEvent(String paramString, IMMessage paramIMMessage)
  {
    if ((paramString == null) || (paramIMMessage == null))
      return false;
    try
    {
      JSONObject localJSONObject = (JSONObject)JSON.parse(paramString);
      if (localJSONObject != null)
      {
        String str1 = localJSONObject.getString("from");
        String str2 = localJSONObject.getString("to");
        String str3 = localJSONObject.getString("event");
        paramIMMessage.mFromID = str1;
        paramIMMessage.mFromGroupId = str2;
        paramIMMessage.mFromName = localJSONObject.getString("fromName");
        paramIMMessage.mGender = localJSONObject.getString("fromGender");
        paramIMMessage.mMsgId = localJSONObject.getString("id");
        JSONArray localJSONArray = localJSONObject.getJSONArray("body");
        if (localJSONArray != null)
        {
          parseData(localJSONArray.getJSONObject(0), paramIMMessage);
          if (paramIMMessage != null)
            if (str3.equalsIgnoreCase("peer"))
            {
              paramIMMessage.chatType = 0;
              paramIMMessage.mFromID = str1;
              paramIMMessage.mToUserId = str2;
            }
            else if (str3.equalsIgnoreCase("group"))
            {
              paramIMMessage.chatType = 1;
              paramIMMessage.mFromGroupId = str2;
              paramIMMessage.mFromID = str1;
            }
        }
      }
    }
    catch (Exception localException)
    {
    }
    return false;
    return true;
  }

  public GroupInfo buildGroupInfo()
  {
    GroupInfo localGroupInfo = new GroupInfo();
    localGroupInfo.groupId = this.mFromGroupId;
    localGroupInfo.groupName = this.mGroupName;
    return localGroupInfo;
  }

  public UserInfo buildUserInfo()
  {
    UserInfo localUserInfo = new UserInfo();
    localUserInfo.userKey = this.mFromID;
    localUserInfo.snsInfo.sns_name = this.mFromName;
    localUserInfo.snsInfo.sns_avatar = this.mFromAvatar;
    localUserInfo.snsInfo.sns_gender = this.mGender;
    return localUserInfo;
  }

  public boolean isBarrage()
  {
    return (this.chatType == 2) || (this.chatType == 3);
  }

  public boolean isGroupMsg()
  {
    return this.chatType == 1;
  }

  public boolean isPGCBarrage()
  {
    return this.chatType == 3;
  }

  public boolean isUGCBarrage()
  {
    return this.chatType == 2;
  }

  public String toString()
  {
    return JSON.toJSONString(this);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.im.message.IMMessage
 * JD-Core Version:    0.6.2
 */