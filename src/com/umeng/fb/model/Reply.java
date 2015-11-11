package com.umeng.fb.model;

import com.umeng.fb.util.Helper;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Reply
  implements Comparable<Reply>
{
  private static final String JSON_KEY_APPKEY = "appkey";
  private static final String JSON_KEY_CONTENT = "content";
  private static final String JSON_KEY_DATE_TIME = "datetime";
  private static final String JSON_KEY_FEEDBACK_ID = "feedback_id";
  private static final String JSON_KEY_REPLY_ID = "reply_id";
  private static final String JSON_KEY_STATUS = "status";
  private static final String JSON_KEY_TYPE = "type";
  private static final String JSON_KEY_USER_ID = "user_id";
  private static final String TAG = Reply.class.getName();
  protected String appkey;
  protected String content;
  protected Date datetime;
  protected String feedback_id;
  protected String reply_id;
  protected STATUS status;
  protected TYPE type;
  protected String user_id;

  Reply(String paramString1, String paramString2, String paramString3, String paramString4, TYPE paramTYPE)
  {
    this.content = paramString1;
    this.reply_id = Helper.generateReplyID();
    this.appkey = paramString2;
    this.user_id = paramString3;
    this.feedback_id = paramString4;
    this.type = paramTYPE;
    this.datetime = new Date();
    this.status = STATUS.NOT_SENT;
  }

  // ERROR //
  Reply(JSONObject paramJSONObject)
    throws JSONException
  {
    // Byte code:
    //   0: aload_0
    //   1: invokespecial 50	java/lang/Object:<init>	()V
    //   4: aload_0
    //   5: aload_1
    //   6: ldc 14
    //   8: ldc 88
    //   10: invokevirtual 94	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   13: putfield 52	com/umeng/fb/model/Reply:content	Ljava/lang/String;
    //   16: aload_0
    //   17: aload_1
    //   18: ldc 23
    //   20: ldc 88
    //   22: invokevirtual 94	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   25: putfield 59	com/umeng/fb/model/Reply:reply_id	Ljava/lang/String;
    //   28: aload_0
    //   29: aload_1
    //   30: ldc 11
    //   32: ldc 88
    //   34: invokevirtual 94	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   37: putfield 61	com/umeng/fb/model/Reply:appkey	Ljava/lang/String;
    //   40: aload_0
    //   41: aload_1
    //   42: ldc 32
    //   44: ldc 88
    //   46: invokevirtual 94	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   49: putfield 63	com/umeng/fb/model/Reply:user_id	Ljava/lang/String;
    //   52: aload_0
    //   53: aload_1
    //   54: ldc 20
    //   56: ldc 88
    //   58: invokevirtual 94	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   61: putfield 65	com/umeng/fb/model/Reply:feedback_id	Ljava/lang/String;
    //   64: aload_0
    //   65: aload_1
    //   66: ldc 29
    //   68: invokevirtual 98	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   71: invokestatic 104	com/umeng/fb/model/Reply$TYPE:get	(Ljava/lang/String;)Lcom/umeng/fb/model/Reply$TYPE;
    //   74: putfield 67	com/umeng/fb/model/Reply:type	Lcom/umeng/fb/model/Reply$TYPE;
    //   77: aload_0
    //   78: new 106	java/text/SimpleDateFormat
    //   81: dup
    //   82: ldc 108
    //   84: invokespecial 111	java/text/SimpleDateFormat:<init>	(Ljava/lang/String;)V
    //   87: aload_1
    //   88: ldc 17
    //   90: invokevirtual 98	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   93: invokevirtual 115	java/text/SimpleDateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
    //   96: putfield 72	com/umeng/fb/model/Reply:datetime	Ljava/util/Date;
    //   99: aload_0
    //   100: aload_1
    //   101: ldc 26
    //   103: getstatic 77	com/umeng/fb/model/Reply$STATUS:NOT_SENT	Lcom/umeng/fb/model/Reply$STATUS;
    //   106: invokevirtual 118	com/umeng/fb/model/Reply$STATUS:toString	()Ljava/lang/String;
    //   109: invokevirtual 94	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   112: invokestatic 121	com/umeng/fb/model/Reply$STATUS:get	(Ljava/lang/String;)Lcom/umeng/fb/model/Reply$STATUS;
    //   115: putfield 79	com/umeng/fb/model/Reply:status	Lcom/umeng/fb/model/Reply$STATUS;
    //   118: return
    //   119: astore_2
    //   120: new 82	org/json/JSONException
    //   123: dup
    //   124: aload_2
    //   125: invokevirtual 124	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   128: invokespecial 125	org/json/JSONException:<init>	(Ljava/lang/String;)V
    //   131: athrow
    //   132: astore_3
    //   133: aload_0
    //   134: new 106	java/text/SimpleDateFormat
    //   137: dup
    //   138: invokespecial 126	java/text/SimpleDateFormat:<init>	()V
    //   141: aload_1
    //   142: ldc 17
    //   144: invokevirtual 98	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   147: invokevirtual 115	java/text/SimpleDateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
    //   150: putfield 72	com/umeng/fb/model/Reply:datetime	Ljava/util/Date;
    //   153: goto -54 -> 99
    //   156: astore 4
    //   158: aload 4
    //   160: invokevirtual 129	java/text/ParseException:printStackTrace	()V
    //   163: getstatic 46	com/umeng/fb/model/Reply:TAG	Ljava/lang/String;
    //   166: new 131	java/lang/StringBuilder
    //   169: dup
    //   170: invokespecial 132	java/lang/StringBuilder:<init>	()V
    //   173: ldc 134
    //   175: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   178: aload_1
    //   179: ldc 17
    //   181: ldc 88
    //   183: invokevirtual 94	org/json/JSONObject:optString	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   186: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   189: ldc 140
    //   191: invokevirtual 138	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   194: invokevirtual 141	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   197: invokestatic 147	com/umeng/fb/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)V
    //   200: aload_0
    //   201: new 69	java/util/Date
    //   204: dup
    //   205: invokespecial 70	java/util/Date:<init>	()V
    //   208: putfield 72	com/umeng/fb/model/Reply:datetime	Ljava/util/Date;
    //   211: goto -112 -> 99
    //
    // Exception table:
    //   from	to	target	type
    //   64	77	119	java/lang/Exception
    //   77	99	132	java/text/ParseException
    //   133	153	156	java/text/ParseException
  }

  public int compareTo(Reply paramReply)
  {
    return this.datetime.compareTo(paramReply.datetime);
  }

  public String getContent()
  {
    return this.content;
  }

  public Date getDatetime()
  {
    return this.datetime;
  }

  public STATUS getStatus()
  {
    return this.status;
  }

  public JSONObject toJson()
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("content", this.content);
      localJSONObject.put("reply_id", this.reply_id);
      localJSONObject.put("appkey", this.appkey);
      localJSONObject.put("user_id", this.user_id);
      localJSONObject.put("feedback_id", this.feedback_id);
      localJSONObject.put("type", this.type);
      localJSONObject.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US).format(this.datetime));
      localJSONObject.put("status", this.status.toString());
      return localJSONObject;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return null;
  }

  public static enum STATUS
  {
    private final String mText;

    static
    {
      NOT_SENT = new STATUS("NOT_SENT", 1, "not_sent");
      SENT = new STATUS("SENT", 2, "sent");
      STATUS[] arrayOfSTATUS = new STATUS[3];
      arrayOfSTATUS[0] = SENDING;
      arrayOfSTATUS[1] = NOT_SENT;
      arrayOfSTATUS[2] = SENT;
    }

    private STATUS(String paramString)
    {
      this.mText = paramString;
    }

    public static STATUS get(String paramString)
    {
      if (SENDING.toString().equals(paramString))
        return SENDING;
      if (NOT_SENT.toString().equals(paramString))
        return NOT_SENT;
      if (SENT.toString().equals(paramString))
        return SENT;
      throw new RuntimeException(paramString + "Cannot convert " + paramString + " to enum " + STATUS.class.getName());
    }

    public String toString()
    {
      return this.mText;
    }
  }

  public static enum TYPE
  {
    private final String mText;

    static
    {
      DEV_REPLY = new TYPE("DEV_REPLY", 1, "dev_reply");
      USER_REPLY = new TYPE("USER_REPLY", 2, "user_reply");
      TYPE[] arrayOfTYPE = new TYPE[3];
      arrayOfTYPE[0] = NEW_FEEDBACK;
      arrayOfTYPE[1] = DEV_REPLY;
      arrayOfTYPE[2] = USER_REPLY;
    }

    private TYPE(String paramString)
    {
      this.mText = paramString;
    }

    public static TYPE get(String paramString)
    {
      if (NEW_FEEDBACK.toString().equals(paramString))
        return NEW_FEEDBACK;
      if (DEV_REPLY.toString().equals(paramString))
        return DEV_REPLY;
      if (USER_REPLY.toString().equals(paramString))
        return USER_REPLY;
      throw new RuntimeException(paramString + "Cannot convert " + paramString + " to enum " + TYPE.class.getName());
    }

    public String toString()
    {
      return this.mText;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.umeng.fb.model.Reply
 * JD-Core Version:    0.6.2
 */