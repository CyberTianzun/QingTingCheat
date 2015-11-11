package com.umeng.fb.model;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import com.umeng.fb.net.FbClient;
import com.umeng.fb.util.DeviceConfig;
import com.umeng.fb.util.Helper;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Conversation
{
  private static final String TAG = Conversation.class.getName();
  private static ExecutorService executor = Executors.newSingleThreadExecutor();
  private String appkey;
  private String id;
  private Context mContext;
  private Map<String, Reply> replyIdMap;
  List<Reply> replyList = new ArrayList();
  private String user_id;

  public Conversation(Context paramContext)
  {
    this.mContext = paramContext;
    this.appkey = DeviceConfig.getAppkey(this.mContext);
    this.id = Helper.generateFeedbackId(this.mContext);
    this.user_id = DeviceConfig.getDeviceIdUmengMD5(this.mContext);
    this.replyIdMap = new ConcurrentHashMap();
  }

  Conversation(String paramString, JSONArray paramJSONArray, Context paramContext)
    throws JSONException
  {
    this.mContext = paramContext;
    this.appkey = DeviceConfig.getAppkey(this.mContext);
    this.id = paramString;
    this.user_id = DeviceConfig.getDeviceIdUmengMD5(this.mContext);
    this.replyIdMap = new HashMap();
    if ((paramJSONArray == null) || (paramJSONArray.length() < 1))
      return;
    for (int i = 0; i < paramJSONArray.length(); i++)
    {
      JSONObject localJSONObject = paramJSONArray.getJSONObject(i);
      String str = localJSONObject.getString("type");
      Object localObject;
      if (Reply.TYPE.NEW_FEEDBACK.toString().equals(str))
        localObject = new UserTitleReply(localJSONObject);
      while (localObject == null)
      {
        throw new JSONException("Failed to create Conversation using given JSONArray: " + paramJSONArray + " at element " + i + ": " + localJSONObject);
        if (Reply.TYPE.USER_REPLY.toString().equals(str))
        {
          localObject = new UserReply(localJSONObject);
        }
        else
        {
          boolean bool = Reply.TYPE.DEV_REPLY.toString().equals(str);
          localObject = null;
          if (bool)
            localObject = new DevReply(localJSONObject);
        }
      }
      if (!this.replyIdMap.containsKey(((Reply)localObject).reply_id))
        this.replyIdMap.put(((Reply)localObject).reply_id, localObject);
    }
    commitChange();
  }

  private void commitChange()
  {
    Store.getInstance(this.mContext).saveCoversation(this);
  }

  public void addUserReply(String paramString)
  {
    if (this.replyIdMap.size() < 1);
    for (Object localObject = new UserTitleReply(paramString, this.appkey, this.user_id, this.id); ; localObject = new UserReply(paramString, this.appkey, this.user_id, this.id))
    {
      if (!this.replyIdMap.containsKey(((Reply)localObject).reply_id))
        this.replyIdMap.put(((Reply)localObject).reply_id, localObject);
      commitChange();
      return;
    }
  }

  public String getId()
  {
    return this.id;
  }

  public List<Reply> getReplyList()
  {
    try
    {
      this.replyList.clear();
      this.replyList.addAll(this.replyIdMap.values());
      Collections.sort(this.replyList);
      List localList = this.replyList;
      return localList;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void sync(SyncListener paramSyncListener)
  {
    Runnable local1 = new Runnable()
    {
      public void run()
      {
        ArrayList localArrayList1 = new ArrayList();
        ArrayList localArrayList2 = new ArrayList();
        Date localDate = null;
        String str = "";
        Iterator localIterator1 = Conversation.this.replyIdMap.entrySet().iterator();
        while (localIterator1.hasNext())
        {
          Reply localReply2 = (Reply)((Map.Entry)localIterator1.next()).getValue();
          if (((localReply2 instanceof UserReply)) || ((localReply2 instanceof UserTitleReply)))
          {
            if (localReply2.status == Reply.STATUS.NOT_SENT)
              localArrayList1.add(localReply2);
          }
          else if (((localReply2 instanceof DevReply)) && ((localDate == null) || (localDate.compareTo(localReply2.getDatetime()) < 0)))
          {
            localDate = localReply2.getDatetime();
            str = localReply2.reply_id;
          }
        }
        localArrayList2.add(Conversation.this.id);
        Iterator localIterator2 = localArrayList1.iterator();
        while (localIterator2.hasNext())
        {
          Reply localReply1 = (Reply)localIterator2.next();
          boolean bool = new FbClient(Conversation.this.mContext).sendReply(localReply1);
          if (bool == true)
          {
            Message localMessage2 = Message.obtain();
            localMessage2.what = 2;
            localMessage2.obj = localReply1;
            if (bool);
            for (int i = 1; ; i = 0)
            {
              localMessage2.arg1 = i;
              this.val$handler.sendMessage(localMessage2);
              break;
            }
          }
        }
        List localList = new FbClient(Conversation.this.mContext).getDevReply(localArrayList2, str, Conversation.this.appkey);
        Message localMessage1 = Message.obtain();
        localMessage1.what = 1;
        Conversation.MessageWrapper localMessageWrapper = new Conversation.MessageWrapper();
        localMessageWrapper.devReplyListRetrieved = localList;
        localMessageWrapper.userReplyListToSend = localArrayList1;
        localMessage1.obj = localMessageWrapper;
        this.val$handler.sendMessage(localMessage1);
      }
    };
    executor.execute(local1);
  }

  JSONArray toJson()
  {
    JSONArray localJSONArray = new JSONArray();
    Iterator localIterator = this.replyIdMap.entrySet().iterator();
    while (localIterator.hasNext())
      localJSONArray.put(((Reply)((Map.Entry)localIterator.next()).getValue()).toJson());
    return localJSONArray;
  }

  static class MessageWrapper
  {
    List<DevReply> devReplyListRetrieved;
    List<Reply> userReplyListToSend;
  }

  class SyncHandler extends Handler
  {
    static final int CALLBACK = 1;
    static final int UPDATE_SEND_USER_REPLY_RESULT = 2;
    Conversation.SyncListener mListener;

    public SyncHandler(Conversation.SyncListener arg2)
    {
      Object localObject;
      this.mListener = localObject;
    }

    public void handleMessage(Message paramMessage)
    {
      int i = 1;
      if (paramMessage.what == 2)
      {
        Reply localReply2 = (Reply)paramMessage.obj;
        if (paramMessage.arg1 == i)
          if (i != 0)
            localReply2.status = Reply.STATUS.SENT;
      }
      List localList1;
      List localList2;
      do
      {
        do
        {
          return;
          i = 0;
          break;
        }
        while (paramMessage.what != i);
        Conversation.MessageWrapper localMessageWrapper = (Conversation.MessageWrapper)paramMessage.obj;
        localList1 = localMessageWrapper.devReplyListRetrieved;
        localList2 = localMessageWrapper.userReplyListToSend;
        if (localList1 != null)
        {
          Iterator localIterator = localList1.iterator();
          while (localIterator.hasNext())
          {
            Reply localReply1 = (Reply)localIterator.next();
            if (Conversation.this.replyIdMap.containsKey(localReply1.reply_id))
              localIterator.remove();
            else
              Conversation.this.replyIdMap.put(localReply1.reply_id, localReply1);
          }
        }
        Conversation.this.commitChange();
      }
      while (this.mListener == null);
      this.mListener.onReceiveDevReply(localList1);
      this.mListener.onSendUserReply(localList2);
    }
  }

  public static abstract interface SyncListener
  {
    public abstract void onReceiveDevReply(List<DevReply> paramList);

    public abstract void onSendUserReply(List<Reply> paramList);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.fb.model.Conversation
 * JD-Core Version:    0.6.2
 */