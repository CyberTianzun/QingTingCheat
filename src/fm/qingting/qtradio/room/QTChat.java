package fm.qingting.qtradio.room;

import android.content.Context;
import android.util.Base64;
import android.widget.Toast;
import fm.qingting.async.callback.CompletedCallback;
import fm.qingting.async.http.AsyncHttpClient;
import fm.qingting.async.http.SocketIOClient;
import fm.qingting.async.http.SocketIOClient.EventCallback;
import fm.qingting.async.http.SocketIOClient.JSONCallback;
import fm.qingting.async.http.SocketIOClient.SocketIOConnectCallback;
import fm.qingting.async.http.SocketIOClient.SocketIORequest;
import fm.qingting.async.http.SocketIOClient.StringCallback;
import fm.qingting.framework.utils.MobileState;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.Node;
import fm.qingting.qtradio.model.ProgramNode;
import fm.qingting.qtradio.model.QTLocation;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.utils.AppInfo;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.zip.Inflater;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class QTChat extends Chat
{
  public static QTChat _instance = null;
  private long MAX_ASK_SONG_INTERVAL = 60L;
  private SocketIOClient _socketClient;
  private boolean autoGet = false;
  private boolean autoJoin = false;
  private boolean autoLogin = false;
  private int connectRetry = 3;
  private String connectUrl = "http://chat.qingting.fm/";
  private boolean connected = false;
  private boolean connecting = false;
  private long enterLiveRoom = 0L;
  private long lastAskSongNameTime = 0L;
  private String lastGetHistoryRoomId = null;
  private boolean loginSuccess = false;
  private List<String> lstCheckIn = new ArrayList();
  private List<CustomData> lstFilteredData = new ArrayList();
  private int mAskForSongNameCnt = 0;
  private UserInfo mAskForSongNameUser;
  private CompletedCallback mCompletedCallback = new CompletedCallback()
  {
    public void onCompleted(Exception paramAnonymousException)
    {
      QTChat.this.onDisconnect();
    }
  };
  private Context mContext;
  private SocketIOClient.EventCallback mEventCallback = new SocketIOClient.EventCallback()
  {
    public void onEvent(String paramAnonymousString, JSONArray paramAnonymousJSONArray)
    {
      if ((paramAnonymousJSONArray == null) || (paramAnonymousString == null))
        return;
      try
      {
        if ((paramAnonymousString.equalsIgnoreCase("join")) || (paramAnonymousString.equalsIgnoreCase("get")))
        {
          QTChat.this.on(paramAnonymousString, (JSONArray)paramAnonymousJSONArray.get(0));
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      if (paramAnonymousString.equalsIgnoreCase("zget"))
      {
        QTChat.this.on(paramAnonymousString, (String)paramAnonymousJSONArray.get(0));
        return;
      }
      QTChat.this.on(paramAnonymousString, (JSONObject)paramAnonymousJSONArray.get(0));
    }
  };
  private SocketIOClient.JSONCallback mJsonCallback = new SocketIOClient.JSONCallback()
  {
    public void onJSON(JSONObject paramAnonymousJSONObject)
    {
    }
  };
  private SocketIOClient.StringCallback mStringCallback = new SocketIOClient.StringCallback()
  {
    public void onString(String paramAnonymousString)
    {
    }
  };
  private UserInfo mUser;
  private int maxHistoryRecords = 30;
  private int maxOnlineUsers = 1000;
  private int recordCnt = 0;
  private String roomId;

  private QTChat()
  {
    init();
  }

  // ERROR //
  private CustomData _parseMessageObject(JSONObject paramJSONObject)
  {
    // Byte code:
    //   0: aload_1
    //   1: ifnonnull +5 -> 6
    //   4: aconst_null
    //   5: areturn
    //   6: aload_1
    //   7: ldc 130
    //   9: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   12: astore 4
    //   14: aload 4
    //   16: ldc 138
    //   18: invokevirtual 144	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   21: ifeq +434 -> 455
    //   24: new 146	fm/qingting/qtradio/room/ChatData
    //   27: dup
    //   28: invokespecial 147	fm/qingting/qtradio/room/ChatData:<init>	()V
    //   31: astore 5
    //   33: new 132	org/json/JSONObject
    //   36: dup
    //   37: aload_1
    //   38: ldc 149
    //   40: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   43: invokespecial 152	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   46: astore 6
    //   48: aload 5
    //   50: aload 6
    //   52: ldc 154
    //   54: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   57: putfield 157	fm/qingting/qtradio/room/ChatData:content	Ljava/lang/String;
    //   60: invokestatic 163	fm/qingting/qtradio/model/SharedCfg:getInstance	()Lfm/qingting/qtradio/model/SharedCfg;
    //   63: aload 5
    //   65: getfield 157	fm/qingting/qtradio/room/ChatData:content	Ljava/lang/String;
    //   68: invokevirtual 166	fm/qingting/qtradio/model/SharedCfg:hitFilter	(Ljava/lang/String;)Z
    //   71: ifne -67 -> 4
    //   74: aload 5
    //   76: aload_1
    //   77: ldc 168
    //   79: invokevirtual 172	org/json/JSONObject:getLong	(Ljava/lang/String;)J
    //   82: putfield 175	fm/qingting/qtradio/room/ChatData:createTime	J
    //   85: aload 5
    //   87: aload_1
    //   88: ldc 177
    //   90: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   93: putfield 179	fm/qingting/qtradio/room/ChatData:roomId	Ljava/lang/String;
    //   96: aload 5
    //   98: aload_0
    //   99: new 132	org/json/JSONObject
    //   102: dup
    //   103: aload_1
    //   104: ldc 181
    //   106: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   109: invokespecial 152	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   112: invokespecial 185	fm/qingting/qtradio/room/QTChat:_parseUserObject	(Lorg/json/JSONObject;)Lfm/qingting/qtradio/room/UserInfo;
    //   115: putfield 188	fm/qingting/qtradio/room/ChatData:user	Lfm/qingting/qtradio/room/UserInfo;
    //   118: aload 6
    //   120: ldc 130
    //   122: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   125: astore 16
    //   127: aload 16
    //   129: ifnull +19 -> 148
    //   132: aload 16
    //   134: ldc 190
    //   136: invokevirtual 144	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   139: ifeq +226 -> 365
    //   142: aload 5
    //   144: iconst_2
    //   145: putfield 193	fm/qingting/qtradio/room/ChatData:conentType	I
    //   148: iconst_0
    //   149: istore 8
    //   151: aload 5
    //   153: aload_0
    //   154: new 132	org/json/JSONObject
    //   157: dup
    //   158: aload_1
    //   159: ldc 195
    //   161: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   164: invokespecial 152	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   167: invokespecial 185	fm/qingting/qtradio/room/QTChat:_parseUserObject	(Lorg/json/JSONObject;)Lfm/qingting/qtradio/room/UserInfo;
    //   170: putfield 198	fm/qingting/qtradio/room/ChatData:toUser	Lfm/qingting/qtradio/room/UserInfo;
    //   173: iload 8
    //   175: ifeq +26 -> 201
    //   178: aload 5
    //   180: aload_0
    //   181: new 132	org/json/JSONObject
    //   184: dup
    //   185: aload 6
    //   187: ldc 195
    //   189: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   192: invokespecial 152	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   195: invokespecial 185	fm/qingting/qtradio/room/QTChat:_parseUserObject	(Lorg/json/JSONObject;)Lfm/qingting/qtradio/room/UserInfo;
    //   198: putfield 198	fm/qingting/qtradio/room/ChatData:toUser	Lfm/qingting/qtradio/room/UserInfo;
    //   201: aload_0
    //   202: aload 6
    //   204: ldc 200
    //   206: invokevirtual 204	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   209: aload 5
    //   211: invokespecial 208	fm/qingting/qtradio/room/QTChat:_parseMetaObject	(Lorg/json/JSONObject;Lfm/qingting/qtradio/room/ChatData;)V
    //   214: aload 5
    //   216: aload_1
    //   217: ldc 210
    //   219: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   222: putfield 213	fm/qingting/qtradio/room/ChatData:msgId	Ljava/lang/String;
    //   225: aload 5
    //   227: ifnull +135 -> 362
    //   230: aload 5
    //   232: getfield 193	fm/qingting/qtradio/room/ChatData:conentType	I
    //   235: ifne +127 -> 362
    //   238: aload 5
    //   240: getfield 198	fm/qingting/qtradio/room/ChatData:toUser	Lfm/qingting/qtradio/room/UserInfo;
    //   243: ifnull +119 -> 362
    //   246: aload 5
    //   248: getfield 157	fm/qingting/qtradio/room/ChatData:content	Ljava/lang/String;
    //   251: ldc 215
    //   253: invokevirtual 218	java/lang/String:startsWith	(Ljava/lang/String;)Z
    //   256: ifne +106 -> 362
    //   259: new 220	java/lang/StringBuilder
    //   262: dup
    //   263: invokespecial 221	java/lang/StringBuilder:<init>	()V
    //   266: ldc 223
    //   268: invokevirtual 227	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   271: ldc 229
    //   273: invokevirtual 227	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   276: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   279: astore 12
    //   281: new 220	java/lang/StringBuilder
    //   284: dup
    //   285: invokespecial 221	java/lang/StringBuilder:<init>	()V
    //   288: aload 12
    //   290: invokevirtual 227	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   293: aload 5
    //   295: getfield 198	fm/qingting/qtradio/room/ChatData:toUser	Lfm/qingting/qtradio/room/UserInfo;
    //   298: getfield 239	fm/qingting/qtradio/room/UserInfo:snsInfo	Lfm/qingting/qtradio/room/SnsInfo;
    //   301: getfield 244	fm/qingting/qtradio/room/SnsInfo:sns_name	Ljava/lang/String;
    //   304: invokevirtual 227	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   307: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   310: astore 13
    //   312: new 220	java/lang/StringBuilder
    //   315: dup
    //   316: invokespecial 221	java/lang/StringBuilder:<init>	()V
    //   319: aload 13
    //   321: invokevirtual 227	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   324: ldc 246
    //   326: invokevirtual 227	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   329: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   332: astore 14
    //   334: aload 5
    //   336: new 220	java/lang/StringBuilder
    //   339: dup
    //   340: invokespecial 221	java/lang/StringBuilder:<init>	()V
    //   343: aload 14
    //   345: invokevirtual 227	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   348: aload 5
    //   350: getfield 157	fm/qingting/qtradio/room/ChatData:content	Ljava/lang/String;
    //   353: invokevirtual 227	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   356: invokevirtual 233	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   359: putfield 157	fm/qingting/qtradio/room/ChatData:content	Ljava/lang/String;
    //   362: aload 5
    //   364: areturn
    //   365: aload 16
    //   367: ldc 248
    //   369: invokevirtual 144	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   372: ifeq +32 -> 404
    //   375: aload 5
    //   377: iconst_1
    //   378: putfield 193	fm/qingting/qtradio/room/ChatData:conentType	I
    //   381: aload 5
    //   383: getfield 157	fm/qingting/qtradio/room/ChatData:content	Ljava/lang/String;
    //   386: ifnull -382 -> 4
    //   389: aload 5
    //   391: getfield 157	fm/qingting/qtradio/room/ChatData:content	Ljava/lang/String;
    //   394: ldc 250
    //   396: invokevirtual 144	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   399: ifeq -251 -> 148
    //   402: aconst_null
    //   403: areturn
    //   404: aload 16
    //   406: ldc 252
    //   408: invokevirtual 144	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   411: ifeq -263 -> 148
    //   414: aload 5
    //   416: iconst_3
    //   417: putfield 193	fm/qingting/qtradio/room/ChatData:conentType	I
    //   420: aload 5
    //   422: getfield 157	fm/qingting/qtradio/room/ChatData:content	Ljava/lang/String;
    //   425: ifnull -421 -> 4
    //   428: aload 5
    //   430: getfield 157	fm/qingting/qtradio/room/ChatData:content	Ljava/lang/String;
    //   433: ldc 250
    //   435: invokevirtual 144	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   438: istore 17
    //   440: iload 17
    //   442: ifeq -294 -> 148
    //   445: aconst_null
    //   446: areturn
    //   447: astore 9
    //   449: iconst_1
    //   450: istore 8
    //   452: goto -279 -> 173
    //   455: aload 4
    //   457: ldc 254
    //   459: invokevirtual 144	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   462: ifeq +82 -> 544
    //   465: new 256	fm/qingting/qtradio/room/EnterRoomData
    //   468: dup
    //   469: invokespecial 257	fm/qingting/qtradio/room/EnterRoomData:<init>	()V
    //   472: astore 18
    //   474: new 132	org/json/JSONObject
    //   477: dup
    //   478: aload_1
    //   479: ldc 149
    //   481: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   484: invokespecial 152	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   487: pop
    //   488: aload_0
    //   489: new 132	org/json/JSONObject
    //   492: dup
    //   493: aload_1
    //   494: ldc 181
    //   496: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   499: invokespecial 152	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   502: invokespecial 185	fm/qingting/qtradio/room/QTChat:_parseUserObject	(Lorg/json/JSONObject;)Lfm/qingting/qtradio/room/UserInfo;
    //   505: astore 20
    //   507: aload 20
    //   509: ifnull +10 -> 519
    //   512: aload 18
    //   514: aload 20
    //   516: putfield 258	fm/qingting/qtradio/room/EnterRoomData:user	Lfm/qingting/qtradio/room/UserInfo;
    //   519: aload 18
    //   521: aload_1
    //   522: ldc 177
    //   524: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   527: putfield 259	fm/qingting/qtradio/room/EnterRoomData:roomId	Ljava/lang/String;
    //   530: aload 18
    //   532: aload_1
    //   533: ldc 210
    //   535: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   538: putfield 260	fm/qingting/qtradio/room/EnterRoomData:msgId	Ljava/lang/String;
    //   541: aload 18
    //   543: areturn
    //   544: aload 4
    //   546: ldc_w 262
    //   549: invokevirtual 144	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   552: ifeq +139 -> 691
    //   555: new 146	fm/qingting/qtradio/room/ChatData
    //   558: dup
    //   559: invokespecial 147	fm/qingting/qtradio/room/ChatData:<init>	()V
    //   562: astore 22
    //   564: new 132	org/json/JSONObject
    //   567: dup
    //   568: aload_1
    //   569: ldc 149
    //   571: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   574: invokespecial 152	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   577: astore 23
    //   579: aload 22
    //   581: aload 23
    //   583: ldc 154
    //   585: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   588: putfield 157	fm/qingting/qtradio/room/ChatData:content	Ljava/lang/String;
    //   591: aload 22
    //   593: aload_0
    //   594: aload 23
    //   596: ldc 195
    //   598: invokevirtual 204	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   601: invokespecial 185	fm/qingting/qtradio/room/QTChat:_parseUserObject	(Lorg/json/JSONObject;)Lfm/qingting/qtradio/room/UserInfo;
    //   604: putfield 198	fm/qingting/qtradio/room/ChatData:toUser	Lfm/qingting/qtradio/room/UserInfo;
    //   607: aload 22
    //   609: aload 23
    //   611: ldc_w 264
    //   614: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   617: putfield 267	fm/qingting/qtradio/room/ChatData:replyedContent	Ljava/lang/String;
    //   620: aload_0
    //   621: aload 23
    //   623: ldc 200
    //   625: invokevirtual 204	org/json/JSONObject:getJSONObject	(Ljava/lang/String;)Lorg/json/JSONObject;
    //   628: aload 22
    //   630: invokespecial 208	fm/qingting/qtradio/room/QTChat:_parseMetaObject	(Lorg/json/JSONObject;Lfm/qingting/qtradio/room/ChatData;)V
    //   633: aload 22
    //   635: aload_1
    //   636: ldc 168
    //   638: invokevirtual 172	org/json/JSONObject:getLong	(Ljava/lang/String;)J
    //   641: putfield 175	fm/qingting/qtradio/room/ChatData:createTime	J
    //   644: aload 22
    //   646: aload_1
    //   647: ldc 177
    //   649: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   652: putfield 179	fm/qingting/qtradio/room/ChatData:roomId	Ljava/lang/String;
    //   655: aload 22
    //   657: aload_0
    //   658: new 132	org/json/JSONObject
    //   661: dup
    //   662: aload_1
    //   663: ldc 181
    //   665: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   668: invokespecial 152	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   671: invokespecial 185	fm/qingting/qtradio/room/QTChat:_parseUserObject	(Lorg/json/JSONObject;)Lfm/qingting/qtradio/room/UserInfo;
    //   674: putfield 188	fm/qingting/qtradio/room/ChatData:user	Lfm/qingting/qtradio/room/UserInfo;
    //   677: aload 22
    //   679: aload_1
    //   680: ldc 210
    //   682: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   685: putfield 213	fm/qingting/qtradio/room/ChatData:msgId	Ljava/lang/String;
    //   688: aload 22
    //   690: areturn
    //   691: aload 4
    //   693: ldc_w 269
    //   696: invokevirtual 144	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   699: ifeq +93 -> 792
    //   702: new 146	fm/qingting/qtradio/room/ChatData
    //   705: dup
    //   706: invokespecial 147	fm/qingting/qtradio/room/ChatData:<init>	()V
    //   709: astore 27
    //   711: aload 27
    //   713: new 132	org/json/JSONObject
    //   716: dup
    //   717: aload_1
    //   718: ldc 149
    //   720: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   723: invokespecial 152	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   726: ldc 154
    //   728: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   731: putfield 157	fm/qingting/qtradio/room/ChatData:content	Ljava/lang/String;
    //   734: aload 27
    //   736: aload_1
    //   737: ldc 168
    //   739: invokevirtual 172	org/json/JSONObject:getLong	(Ljava/lang/String;)J
    //   742: putfield 175	fm/qingting/qtradio/room/ChatData:createTime	J
    //   745: aload 27
    //   747: aload_1
    //   748: ldc 177
    //   750: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   753: putfield 179	fm/qingting/qtradio/room/ChatData:roomId	Ljava/lang/String;
    //   756: aload 27
    //   758: aload_0
    //   759: new 132	org/json/JSONObject
    //   762: dup
    //   763: aload_1
    //   764: ldc 181
    //   766: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   769: invokespecial 152	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   772: invokespecial 185	fm/qingting/qtradio/room/QTChat:_parseUserObject	(Lorg/json/JSONObject;)Lfm/qingting/qtradio/room/UserInfo;
    //   775: putfield 188	fm/qingting/qtradio/room/ChatData:user	Lfm/qingting/qtradio/room/UserInfo;
    //   778: aload 27
    //   780: aload_1
    //   781: ldc 210
    //   783: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   786: putfield 213	fm/qingting/qtradio/room/ChatData:msgId	Ljava/lang/String;
    //   789: aload 27
    //   791: areturn
    //   792: aload 4
    //   794: ldc_w 271
    //   797: invokevirtual 144	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   800: ifeq +93 -> 893
    //   803: new 146	fm/qingting/qtradio/room/ChatData
    //   806: dup
    //   807: invokespecial 147	fm/qingting/qtradio/room/ChatData:<init>	()V
    //   810: astore 29
    //   812: aload 29
    //   814: new 132	org/json/JSONObject
    //   817: dup
    //   818: aload_1
    //   819: ldc 149
    //   821: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   824: invokespecial 152	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   827: ldc 154
    //   829: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   832: putfield 157	fm/qingting/qtradio/room/ChatData:content	Ljava/lang/String;
    //   835: aload 29
    //   837: aload_1
    //   838: ldc 168
    //   840: invokevirtual 172	org/json/JSONObject:getLong	(Ljava/lang/String;)J
    //   843: putfield 175	fm/qingting/qtradio/room/ChatData:createTime	J
    //   846: aload 29
    //   848: aload_1
    //   849: ldc 177
    //   851: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   854: putfield 179	fm/qingting/qtradio/room/ChatData:roomId	Ljava/lang/String;
    //   857: aload 29
    //   859: aload_0
    //   860: new 132	org/json/JSONObject
    //   863: dup
    //   864: aload_1
    //   865: ldc 181
    //   867: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   870: invokespecial 152	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   873: invokespecial 185	fm/qingting/qtradio/room/QTChat:_parseUserObject	(Lorg/json/JSONObject;)Lfm/qingting/qtradio/room/UserInfo;
    //   876: putfield 188	fm/qingting/qtradio/room/ChatData:user	Lfm/qingting/qtradio/room/UserInfo;
    //   879: aload 29
    //   881: aload_1
    //   882: ldc 210
    //   884: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   887: putfield 213	fm/qingting/qtradio/room/ChatData:msgId	Ljava/lang/String;
    //   890: aload 29
    //   892: areturn
    //   893: aload 4
    //   895: ldc_w 273
    //   898: invokevirtual 144	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   901: ifeq +80 -> 981
    //   904: new 275	fm/qingting/qtradio/room/TopicData
    //   907: dup
    //   908: invokespecial 276	fm/qingting/qtradio/room/TopicData:<init>	()V
    //   911: astore 31
    //   913: aload 31
    //   915: aload_1
    //   916: ldc 177
    //   918: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   921: putfield 277	fm/qingting/qtradio/room/TopicData:roomId	Ljava/lang/String;
    //   924: aload 31
    //   926: new 132	org/json/JSONObject
    //   929: dup
    //   930: aload_1
    //   931: ldc_w 278
    //   934: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   937: invokespecial 152	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   940: ldc_w 280
    //   943: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   946: putfield 282	fm/qingting/qtradio/room/TopicData:topic	Ljava/lang/String;
    //   949: aload 31
    //   951: getfield 282	fm/qingting/qtradio/room/TopicData:topic	Ljava/lang/String;
    //   954: ifnull +16 -> 970
    //   957: aload 31
    //   959: getfield 282	fm/qingting/qtradio/room/TopicData:topic	Ljava/lang/String;
    //   962: ldc 250
    //   964: invokevirtual 144	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   967: ifeq +186 -> 1153
    //   970: aload 31
    //   972: ldc_w 284
    //   975: putfield 282	fm/qingting/qtradio/room/TopicData:topic	Ljava/lang/String;
    //   978: goto +175 -> 1153
    //   981: new 146	fm/qingting/qtradio/room/ChatData
    //   984: dup
    //   985: invokespecial 147	fm/qingting/qtradio/room/ChatData:<init>	()V
    //   988: astore 33
    //   990: aload 33
    //   992: new 132	org/json/JSONObject
    //   995: dup
    //   996: aload_1
    //   997: ldc 149
    //   999: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   1002: invokespecial 152	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   1005: ldc 154
    //   1007: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   1010: putfield 157	fm/qingting/qtradio/room/ChatData:content	Ljava/lang/String;
    //   1013: invokestatic 163	fm/qingting/qtradio/model/SharedCfg:getInstance	()Lfm/qingting/qtradio/model/SharedCfg;
    //   1016: aload 33
    //   1018: getfield 157	fm/qingting/qtradio/room/ChatData:content	Ljava/lang/String;
    //   1021: invokevirtual 166	fm/qingting/qtradio/model/SharedCfg:hitFilter	(Ljava/lang/String;)Z
    //   1024: ifne -1020 -> 4
    //   1027: aload 33
    //   1029: aload_1
    //   1030: ldc 168
    //   1032: invokevirtual 172	org/json/JSONObject:getLong	(Ljava/lang/String;)J
    //   1035: putfield 175	fm/qingting/qtradio/room/ChatData:createTime	J
    //   1038: aload 33
    //   1040: aload_1
    //   1041: ldc 177
    //   1043: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   1046: putfield 179	fm/qingting/qtradio/room/ChatData:roomId	Ljava/lang/String;
    //   1049: aload 33
    //   1051: aload_0
    //   1052: new 132	org/json/JSONObject
    //   1055: dup
    //   1056: aload_1
    //   1057: ldc 181
    //   1059: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   1062: invokespecial 152	org/json/JSONObject:<init>	(Ljava/lang/String;)V
    //   1065: invokespecial 185	fm/qingting/qtradio/room/QTChat:_parseUserObject	(Lorg/json/JSONObject;)Lfm/qingting/qtradio/room/UserInfo;
    //   1068: putfield 188	fm/qingting/qtradio/room/ChatData:user	Lfm/qingting/qtradio/room/UserInfo;
    //   1071: aload 33
    //   1073: aload_1
    //   1074: ldc 210
    //   1076: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   1079: putfield 213	fm/qingting/qtradio/room/ChatData:msgId	Ljava/lang/String;
    //   1082: aconst_null
    //   1083: areturn
    //   1084: astore 34
    //   1086: aconst_null
    //   1087: areturn
    //   1088: astore_3
    //   1089: aload_3
    //   1090: invokevirtual 287	org/json/JSONException:printStackTrace	()V
    //   1093: aconst_null
    //   1094: areturn
    //   1095: astore_2
    //   1096: aconst_null
    //   1097: areturn
    //   1098: astore 32
    //   1100: goto -151 -> 949
    //   1103: astore 30
    //   1105: goto -215 -> 890
    //   1108: astore 28
    //   1110: goto -321 -> 789
    //   1113: astore 26
    //   1115: goto -427 -> 688
    //   1118: astore 25
    //   1120: goto -487 -> 633
    //   1123: astore 24
    //   1125: goto -505 -> 620
    //   1128: astore 21
    //   1130: goto -589 -> 541
    //   1133: astore 11
    //   1135: goto -910 -> 225
    //   1138: astore 10
    //   1140: goto -926 -> 214
    //   1143: astore 15
    //   1145: goto -944 -> 201
    //   1148: astore 7
    //   1150: goto -1002 -> 148
    //   1153: aload 31
    //   1155: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   151	173	447	java/lang/Exception
    //   1071	1082	1084	java/lang/Exception
    //   6	118	1088	org/json/JSONException
    //   118	127	1088	org/json/JSONException
    //   132	148	1088	org/json/JSONException
    //   151	173	1088	org/json/JSONException
    //   178	201	1088	org/json/JSONException
    //   201	214	1088	org/json/JSONException
    //   214	225	1088	org/json/JSONException
    //   230	362	1088	org/json/JSONException
    //   365	402	1088	org/json/JSONException
    //   404	440	1088	org/json/JSONException
    //   455	507	1088	org/json/JSONException
    //   512	519	1088	org/json/JSONException
    //   519	530	1088	org/json/JSONException
    //   530	541	1088	org/json/JSONException
    //   544	607	1088	org/json/JSONException
    //   607	620	1088	org/json/JSONException
    //   620	633	1088	org/json/JSONException
    //   633	677	1088	org/json/JSONException
    //   677	688	1088	org/json/JSONException
    //   691	778	1088	org/json/JSONException
    //   778	789	1088	org/json/JSONException
    //   792	879	1088	org/json/JSONException
    //   879	890	1088	org/json/JSONException
    //   893	913	1088	org/json/JSONException
    //   913	949	1088	org/json/JSONException
    //   949	970	1088	org/json/JSONException
    //   970	978	1088	org/json/JSONException
    //   981	1071	1088	org/json/JSONException
    //   1071	1082	1088	org/json/JSONException
    //   6	118	1095	java/lang/Exception
    //   230	362	1095	java/lang/Exception
    //   455	507	1095	java/lang/Exception
    //   512	519	1095	java/lang/Exception
    //   519	530	1095	java/lang/Exception
    //   544	607	1095	java/lang/Exception
    //   633	677	1095	java/lang/Exception
    //   691	778	1095	java/lang/Exception
    //   792	879	1095	java/lang/Exception
    //   893	913	1095	java/lang/Exception
    //   949	970	1095	java/lang/Exception
    //   970	978	1095	java/lang/Exception
    //   981	1071	1095	java/lang/Exception
    //   913	949	1098	java/lang/Exception
    //   879	890	1103	java/lang/Exception
    //   778	789	1108	java/lang/Exception
    //   677	688	1113	java/lang/Exception
    //   620	633	1118	java/lang/Exception
    //   607	620	1123	java/lang/Exception
    //   530	541	1128	java/lang/Exception
    //   214	225	1133	java/lang/Exception
    //   201	214	1138	java/lang/Exception
    //   178	201	1143	java/lang/Exception
    //   118	127	1148	java/lang/Exception
    //   132	148	1148	java/lang/Exception
    //   365	402	1148	java/lang/Exception
    //   404	440	1148	java/lang/Exception
  }

  private void _parseMetaObject(JSONObject paramJSONObject, ChatData paramChatData)
  {
    if ((paramJSONObject == null) || (paramChatData == null))
      return;
    try
    {
      paramChatData.id = paramJSONObject.getString("id");
      paramChatData.commentid = paramJSONObject.getString("cid");
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private UserInfo _parseUserObject(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null)
      return null;
    UserInfo localUserInfo = new UserInfo();
    try
    {
      localUserInfo.userId = paramJSONObject.getString("user_id");
      localUserInfo.snsInfo.sns_id = paramJSONObject.getString("sns_id");
      localUserInfo.snsInfo.sns_avatar = paramJSONObject.getString("sns_avatar");
      localUserInfo.snsInfo.sns_name = paramJSONObject.getString("sns_name");
      localUserInfo.snsInfo.sns_site = paramJSONObject.getString("sns_site");
      localUserInfo.snsInfo.sns_gender = paramJSONObject.getString("sns_gender");
      localUserInfo.userKey = paramJSONObject.getString("qt_id");
      return localUserInfo;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return localUserInfo;
  }

  private void addToFilteredChatData(CustomData paramCustomData)
  {
    if (paramCustomData == null);
    while (!qualify((ChatData)paramCustomData))
      return;
    this.lstFilteredData.add(paramCustomData);
  }

  private void addToFilteredChatData(List<CustomData> paramList)
  {
    if ((paramList == null) || (paramList.size() == 0));
    while (true)
    {
      return;
      for (int i = 0; i < paramList.size(); i++)
        if (qualify((ChatData)paramList.get(i)))
          this.lstFilteredData.add(paramList.get(i));
    }
  }

  private boolean allowCheckIn(String paramString)
  {
    if (paramString == null)
      return false;
    for (int i = 0; ; i++)
    {
      if (i >= this.lstCheckIn.size())
        break label47;
      if (((String)this.lstCheckIn.get(i)).equalsIgnoreCase(paramString))
        break;
    }
    label47: return true;
  }

  private void connect(String paramString1, String paramString2)
  {
    setUrl(paramString1, paramString2);
    run();
  }

  private void connectedChatRoom()
  {
    if (!this.connected)
      return;
    try
    {
      JSONObject localJSONObject1 = new JSONObject();
      localJSONObject1.put("source", "android");
      localJSONObject1.put("userId", InfoManager.getInstance().getDeviceId());
      localJSONObject1.put("version", AppInfo.getCurrentInternalVersion(this.mContext));
      if ((this.mUser != null) && (this.mUser.snsInfo.sns_id != null) && (!this.mUser.snsInfo.sns_id.equalsIgnoreCase("")))
      {
        JSONObject localJSONObject2 = new JSONObject();
        localJSONObject2.putOpt("user_id", this.mUser.userId);
        localJSONObject2.putOpt("sns_id", this.mUser.snsInfo.sns_id);
        localJSONObject2.putOpt("sns_name", this.mUser.snsInfo.sns_name);
        localJSONObject2.putOpt("sns_avatar", this.mUser.snsInfo.sns_avatar);
        localJSONObject2.putOpt("sns_site", this.mUser.snsInfo.sns_site);
        localJSONObject2.putOpt("sns_gender", this.mUser.snsInfo.sns_gender);
        localJSONObject2.putOpt("qt_id", this.mUser.userKey);
        localJSONObject2.putOpt("source", this.mUser.snsInfo.source);
        localJSONObject1.put("user", localJSONObject2);
      }
      JSONArray localJSONArray = new JSONArray();
      localJSONArray.put(localJSONObject1);
      this._socketClient.emit("init", localJSONArray);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void enterLiveRoom()
  {
    if (this.mUser == null)
      return;
    try
    {
      JSONObject localJSONObject1 = new JSONObject();
      JSONObject localJSONObject2 = new JSONObject();
      JSONObject localJSONObject3 = new JSONObject();
      localJSONObject3.putOpt("user_id", this.mUser.userId);
      localJSONObject3.putOpt("sns_id", this.mUser.snsInfo.sns_id);
      localJSONObject3.putOpt("sns_name", this.mUser.snsInfo.sns_name);
      localJSONObject3.putOpt("sns_avatar", this.mUser.snsInfo.sns_avatar);
      localJSONObject3.putOpt("sns_site", this.mUser.snsInfo.sns_site);
      localJSONObject3.putOpt("sns_gender", this.mUser.snsInfo.sns_gender);
      localJSONObject3.putOpt("qt_id", this.mUser.userKey);
      localJSONObject3.putOpt("source", this.mUser.snsInfo.source);
      localJSONObject2.put("from", localJSONObject3);
      localJSONObject1.put("type", "enter");
      localJSONObject1.put("data", localJSONObject2);
      localJSONObject1.put("room", String.valueOf(this.roomId));
      JSONArray localJSONArray = new JSONArray();
      localJSONArray.put(localJSONObject1);
      this._socketClient.emit("send", localJSONArray);
      sendLiveRoomLog(3);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private String getCurrentBroadcasters()
  {
    Node localNode = InfoManager.getInstance().root().getCurrentPlayingNode();
    if ((localNode != null) && (localNode.nodeName.equalsIgnoreCase("program")))
      return ((ProgramNode)localNode).getBroadCasterNamesForAt();
    return null;
  }

  private String getCurrentCity()
  {
    QTLocation localQTLocation = InfoManager.getInstance().getCurrentLocation();
    String str = null;
    if (localQTLocation != null)
      str = localQTLocation.city;
    if (str == null)
      str = "火星";
    return str;
  }

  public static QTChat getInstance()
  {
    if (_instance == null)
      _instance = new QTChat();
    return _instance;
  }

  private void init()
  {
    Context localContext = InfoManager.getInstance().getContext();
    int i;
    if (localContext != null)
    {
      i = MobileState.getNetWorkType(localContext);
      if (i != 1)
        break label33;
      this.maxHistoryRecords = 100;
    }
    while (true)
    {
      this.mContext = localContext;
      return;
      label33: if (i == 2)
        this.maxHistoryRecords = 50;
    }
  }

  private boolean parseJoinEvent(JSONArray paramJSONArray)
  {
    if (paramJSONArray == null)
      return false;
    ArrayList localArrayList = new ArrayList();
    for (int i = 0; ; i++)
      if (i < paramJSONArray.length())
      {
        try
        {
          CustomData localCustomData2 = _parseMessageObject(new JSONObject((String)paramJSONArray.get(i)));
          if ((localCustomData2 == null) || (localCustomData2.type != 1) || ((((ChatData)localCustomData2).content != null) && (((ChatData)localCustomData2).content.length() > InfoManager.getInstance().getMaxWordsInLiveRoom())) || ((((ChatData)localCustomData2).content != null) && (((ChatData)localCustomData2).content.startsWith("求歌名"))))
            continue;
          if (((ChatData)localCustomData2).conentType == 1)
          {
            String str2 = localCustomData2.roomId;
            RoomDataCenter.getInstance().setSongList(1, localCustomData2, str2);
          }
          if ((RoomDataCenter.getInstance().hasRoomData(1, localCustomData2, localCustomData2.roomId)) || (RoomDataCenter.getInstance().isWelMessageButInValid(localCustomData2)) || (RoomDataCenter.getInstance().isPrivateMessageButInvalid(localCustomData2)))
            continue;
          localArrayList.add(localCustomData2);
        }
        catch (Exception localException)
        {
        }
      }
      else
      {
        String str1;
        CustomData localCustomData1;
        if (localArrayList.size() != 0)
        {
          str1 = ((CustomData)localArrayList.get(0)).roomId;
          Collections.sort(localArrayList, new ChatDataComparator());
          addToFilteredChatData(localArrayList);
          int j = this.lstFilteredData.size();
          if (j <= 0)
            break label325;
          localCustomData1 = (CustomData)this.lstFilteredData.get(j - 1);
          this.lstFilteredData.remove(j - 1);
        }
        while (true)
        {
          RoomDataCenter.getInstance().setQualifyData(localCustomData1);
          RoomDataCenter.getInstance().recvRoomData(1, localArrayList, str1);
          RoomDataCenter.getInstance().recvRoomEvent(1, "RLRJ");
          return true;
          label325: localCustomData1 = (CustomData)localArrayList.get(-1 + localArrayList.size());
        }
      }
  }

  private boolean parseLoginEvent(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null)
      return false;
    try
    {
      RoomDataCenter.getInstance().recvRoomEvent(1, "RLRL");
      return true;
    }
    catch (Exception localException)
    {
    }
    return false;
  }

  private boolean parseLogoutEvent(JSONObject paramJSONObject)
  {
    return true;
  }

  // ERROR //
  private boolean parseOnlineEvent(JSONObject paramJSONObject)
  {
    // Byte code:
    //   0: iconst_0
    //   1: istore_2
    //   2: aload_1
    //   3: ifnonnull +5 -> 8
    //   6: iconst_0
    //   7: ireturn
    //   8: new 106	java/util/ArrayList
    //   11: dup
    //   12: invokespecial 107	java/util/ArrayList:<init>	()V
    //   15: astore_3
    //   16: aconst_null
    //   17: astore 4
    //   19: aload_1
    //   20: ldc 177
    //   22: invokevirtual 136	org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   25: astore 4
    //   27: aload_1
    //   28: ldc_w 555
    //   31: invokevirtual 559	org/json/JSONObject:getInt	(Ljava/lang/String;)I
    //   34: istore 8
    //   36: iload 8
    //   38: istore 6
    //   40: aload_1
    //   41: ldc_w 561
    //   44: invokevirtual 565	org/json/JSONObject:getJSONArray	(Ljava/lang/String;)Lorg/json/JSONArray;
    //   47: astore 9
    //   49: aload 9
    //   51: invokevirtual 486	org/json/JSONArray:length	()I
    //   54: istore 10
    //   56: iload_2
    //   57: iload 10
    //   59: if_icmpge +75 -> 134
    //   62: aload_0
    //   63: aload 9
    //   65: iload_2
    //   66: invokevirtual 568	org/json/JSONArray:getJSONObject	(I)Lorg/json/JSONObject;
    //   69: invokespecial 185	fm/qingting/qtradio/room/QTChat:_parseUserObject	(Lorg/json/JSONObject;)Lfm/qingting/qtradio/room/UserInfo;
    //   72: astore 12
    //   74: aload 12
    //   76: ifnull +38 -> 114
    //   79: invokestatic 388	fm/qingting/qtradio/model/InfoManager:getInstance	()Lfm/qingting/qtradio/model/InfoManager;
    //   82: invokevirtual 391	fm/qingting/qtradio/model/InfoManager:getDeviceId	()Ljava/lang/String;
    //   85: astore 13
    //   87: aload 13
    //   89: ifnull +25 -> 114
    //   92: aload 13
    //   94: aload 12
    //   96: getfield 300	fm/qingting/qtradio/room/UserInfo:userId	Ljava/lang/String;
    //   99: invokevirtual 144	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   102: ifne +12 -> 114
    //   105: aload_3
    //   106: aload 12
    //   108: invokeinterface 354 2 0
    //   113: pop
    //   114: iinc 2 1
    //   117: goto -68 -> 49
    //   120: astore 5
    //   122: iconst_0
    //   123: istore 6
    //   125: aload 5
    //   127: astore 7
    //   129: aload 7
    //   131: invokevirtual 434	java/lang/Exception:printStackTrace	()V
    //   134: aload_3
    //   135: invokeinterface 359 1 0
    //   140: ifeq +48 -> 188
    //   143: aload 4
    //   145: ifnull +43 -> 188
    //   148: aload 4
    //   150: ldc 250
    //   152: invokevirtual 144	java/lang/String:equalsIgnoreCase	(Ljava/lang/String;)Z
    //   155: ifne +33 -> 188
    //   158: invokestatic 505	fm/qingting/qtradio/room/RoomDataCenter:getInstance	()Lfm/qingting/qtradio/room/RoomDataCenter;
    //   161: aload 4
    //   163: iload 6
    //   165: invokevirtual 572	fm/qingting/qtradio/room/RoomDataCenter:setOnlineUsersCnt	(Ljava/lang/String;I)V
    //   168: invokestatic 505	fm/qingting/qtradio/room/RoomDataCenter:getInstance	()Lfm/qingting/qtradio/room/RoomDataCenter;
    //   171: iconst_1
    //   172: aload_3
    //   173: aload 4
    //   175: invokevirtual 575	fm/qingting/qtradio/room/RoomDataCenter:recvOnlineUsers	(ILjava/util/List;Ljava/lang/String;)V
    //   178: invokestatic 505	fm/qingting/qtradio/room/RoomDataCenter:getInstance	()Lfm/qingting/qtradio/room/RoomDataCenter;
    //   181: iconst_1
    //   182: ldc_w 543
    //   185: invokevirtual 547	fm/qingting/qtradio/room/RoomDataCenter:recvRoomEvent	(ILjava/lang/String;)V
    //   188: iconst_1
    //   189: ireturn
    //   190: astore 7
    //   192: goto -63 -> 129
    //   195: astore 11
    //   197: goto -83 -> 114
    //
    // Exception table:
    //   from	to	target	type
    //   19	36	120	java/lang/Exception
    //   40	49	190	java/lang/Exception
    //   49	56	190	java/lang/Exception
    //   62	74	195	java/lang/Exception
    //   79	87	195	java/lang/Exception
    //   92	114	195	java/lang/Exception
  }

  private boolean parseRecvEvent(JSONObject paramJSONObject)
  {
    if (paramJSONObject == null)
      return false;
    try
    {
      CustomData localCustomData1 = _parseMessageObject(paramJSONObject);
      int i;
      CustomData localCustomData2;
      if ((localCustomData1 != null) && (localCustomData1.type == 1))
      {
        if ((((ChatData)localCustomData1).content != null) && (((ChatData)localCustomData1).content.length() > InfoManager.getInstance().getMaxWordsInLiveRoom()))
          return false;
        i = ((ChatData)localCustomData1).conentType;
        if (i != 2)
          if (i == 1)
          {
            String str4 = localCustomData1.roomId;
            RoomDataCenter.getInstance().setSongList(1, localCustomData1, str4);
            addToFilteredChatData(localCustomData1);
            int j = this.lstFilteredData.size();
            if (j <= 0)
              break label420;
            localCustomData2 = (CustomData)this.lstFilteredData.get(j - 1);
            this.lstFilteredData.remove(j - 1);
          }
      }
      while (true)
      {
        RoomDataCenter.getInstance().setQualifyData(localCustomData2);
        String str5 = localCustomData1.roomId;
        RoomDataCenter.getInstance().recvRoomData(1, localCustomData1, str5);
        break label418;
        if ((i != 3) || (!RoomDataCenter.getInstance().isPrivateMessageButInvalid(localCustomData1)))
          break;
        return false;
        if (i == 2)
        {
          if (((ChatData)localCustomData1).toUser != null)
          {
            if ((((ChatData)localCustomData1).user != null) && (this.mUser != null) && (((ChatData)localCustomData1).user.userId.equalsIgnoreCase(this.mUser.userId)))
              return false;
            if ((this.mAskForSongNameUser != null) && (this.mAskForSongNameUser.userId.equalsIgnoreCase(((ChatData)localCustomData1).toUser.userId)))
            {
              this.mAskForSongNameCnt = (1 + this.mAskForSongNameCnt);
              ((ChatData)localCustomData1).askForSongCnt = this.mAskForSongNameCnt;
            }
            this.mAskForSongNameUser = ((ChatData)localCustomData1).toUser;
          }
          while (true)
          {
            String str3 = localCustomData1.roomId;
            RoomDataCenter.getInstance().recvAskForSong(1, localCustomData1, str3);
            break;
            this.mAskForSongNameUser = ((ChatData)localCustomData1).user;
            this.mAskForSongNameCnt = 1;
            ((ChatData)localCustomData1).askForSongCnt = this.mAskForSongNameCnt;
          }
          if ((localCustomData1 != null) && (localCustomData1.type == 3))
          {
            String str2 = localCustomData1.roomId;
            RoomDataCenter.getInstance().recvUserEnter(1, localCustomData1, str2);
          }
          else if ((localCustomData1 != null) && (localCustomData1.type == 4))
          {
            String str1 = localCustomData1.roomId;
            RoomDataCenter.getInstance().recvRoomTopic(1, localCustomData1, str1);
          }
        }
        label418: return true;
        label420: localCustomData2 = localCustomData1;
      }
    }
    catch (Exception localException)
    {
      break label418;
    }
  }

  private boolean parseZGetEvent(String paramString)
  {
    if (paramString == null)
      return false;
    try
    {
      byte[] arrayOfByte1 = Base64.decode(paramString, 0);
      Inflater localInflater = new Inflater();
      localInflater.setInput(arrayOfByte1, 0, arrayOfByte1.length);
      byte[] arrayOfByte2 = new byte[arrayOfByte1.length];
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      while (true)
      {
        int i;
        if (!localInflater.finished())
        {
          i = localInflater.inflate(arrayOfByte2);
          if (i > 0);
        }
        else
        {
          localInflater.end();
          return parseJoinEvent(new JSONArray(new String(localByteArrayOutputStream.toByteArray())));
        }
        localByteArrayOutputStream.write(arrayOfByte2, 0, i);
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return false;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
    }
    return false;
  }

  private boolean qualify(ChatData paramChatData)
  {
    if (paramChatData == null);
    while ((paramChatData.content.contains("优听")) || (paramChatData.content.contains("youting")) || (paramChatData.content.contains("签到")) || (paramChatData.content.contains("献花")) || (paramChatData.content.contains("我听故我在")) || (paramChatData.content.contains("节目好精彩")) || (paramChatData.content.contains("我正在收听")) || (paramChatData.content.contains("兼职")) || (paramChatData.content.contains("顶一个")) || (paramChatData.content.contains("赞一个")) || (paramChatData.user.snsInfo.sns_avatar == null) || (paramChatData.user.snsInfo.sns_avatar.equalsIgnoreCase("")) || (paramChatData.content.length() > 15) || (paramChatData.content.length() < 10))
      return false;
    return true;
  }

  private void reset()
  {
    this.connected = false;
    this.connecting = false;
    this.autoJoin = false;
    this.autoLogin = false;
    this.autoGet = false;
    this.loginSuccess = false;
    this.recordCnt = 0;
    this.connectRetry = 3;
  }

  private void resetSocket()
  {
    if (this._socketClient != null)
    {
      this._socketClient.setClosedCallback(null);
      this._socketClient.setStringCallback(null);
      this._socketClient.setEventCallback(null);
      this._socketClient.setJSONCallback(null);
    }
  }

  private void run()
  {
    if ((this.connectUrl == null) || (this.connectUrl.equalsIgnoreCase("")))
      this.connectUrl = InfoManager.getInstance().chatServer;
    if (this.connecting)
      if (this.mContext == null);
    while ((this.connected) && (this._socketClient != null))
      return;
    try
    {
      this.connecting = true;
      SocketIOClient.SocketIORequest localSocketIORequest = new SocketIOClient.SocketIORequest(this.connectUrl);
      localSocketIORequest.setLogging("Socket.IO", 6);
      SocketIOClient.connect(AsyncHttpClient.getDefaultInstance(), localSocketIORequest, new SocketIOClient.SocketIOConnectCallback()
      {
        public void onConnectCompleted(Exception paramAnonymousException, SocketIOClient paramAnonymousSocketIOClient)
        {
          QTChat.access$002(QTChat.this, false);
          if (paramAnonymousSocketIOClient != null)
          {
            QTChat.this.resetSocket();
            QTChat.access$202(QTChat.this, paramAnonymousSocketIOClient);
            QTChat.this.onConnect();
            QTChat.this._socketClient.setClosedCallback(QTChat.this.mCompletedCallback);
            QTChat.this._socketClient.setStringCallback(QTChat.this.mStringCallback);
            QTChat.this._socketClient.setEventCallback(QTChat.this.mEventCallback);
            QTChat.this._socketClient.setJSONCallback(QTChat.this.mJsonCallback);
            return;
          }
          QTChat.access$202(QTChat.this, null);
          QTChat.this.onConnectFailure();
        }
      });
      return;
    }
    catch (Exception localException)
    {
    }
  }

  private void sendLiveRoomLog(int paramInt)
  {
  }

  private void setUrl(String paramString1, String paramString2)
  {
    if (paramString1 == null)
      return;
    this.connectUrl = paramString1;
    this.roomId = paramString2;
  }

  public boolean allowAskForSong()
  {
    long l = System.currentTimeMillis();
    if ((l - this.lastAskSongNameTime) / 1000L > this.MAX_ASK_SONG_INTERVAL)
    {
      this.lastAskSongNameTime = l;
      return true;
    }
    return false;
  }

  public void askForSongName(String paramString)
  {
    if (!this.connected)
    {
      connect(this.connectUrl, this.roomId);
      return;
    }
    try
    {
      if (!allowAskForSong())
      {
        Toast.makeText(this.mContext, "您求歌名的频率过快，请稍侯重试.", 1).show();
        return;
      }
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    JSONObject localJSONObject1 = new JSONObject();
    JSONObject localJSONObject2 = new JSONObject();
    localJSONObject1.put("type", "chat");
    JSONObject localJSONObject3 = new JSONObject();
    localJSONObject3.putOpt("user_id", this.mUser.userId);
    localJSONObject3.putOpt("sns_id", this.mUser.snsInfo.sns_id);
    localJSONObject3.putOpt("sns_name", this.mUser.snsInfo.sns_name);
    localJSONObject3.putOpt("sns_avatar", this.mUser.snsInfo.sns_avatar);
    localJSONObject3.putOpt("sns_site", this.mUser.snsInfo.sns_site);
    localJSONObject3.putOpt("sns_gender", this.mUser.snsInfo.sns_gender);
    localJSONObject3.putOpt("qt_id", this.mUser.userKey);
    localJSONObject3.putOpt("source", this.mUser.snsInfo.source);
    localJSONObject1.put("from", localJSONObject3);
    localJSONObject2.put("message", "求歌名.");
    localJSONObject2.put("type", "unknownsong");
    localJSONObject1.put("data", localJSONObject2);
    localJSONObject1.put("room", String.valueOf(this.roomId));
    JSONArray localJSONArray = new JSONArray();
    localJSONArray.put(localJSONObject1);
    this._socketClient.emit("send", localJSONArray);
  }

  public void askForSongNameTogether(String paramString)
  {
    if (!this.connected)
    {
      connect(this.connectUrl, this.roomId);
      return;
    }
    try
    {
      JSONObject localJSONObject1 = new JSONObject();
      JSONObject localJSONObject2 = new JSONObject();
      localJSONObject1.put("type", "chat");
      JSONObject localJSONObject3 = new JSONObject();
      localJSONObject3.putOpt("user_id", this.mUser.userId);
      localJSONObject3.putOpt("sns_id", this.mUser.snsInfo.sns_id);
      localJSONObject3.putOpt("sns_name", this.mUser.snsInfo.sns_name);
      localJSONObject3.putOpt("sns_avatar", this.mUser.snsInfo.sns_avatar);
      localJSONObject3.putOpt("sns_site", this.mUser.snsInfo.sns_site);
      localJSONObject3.putOpt("sns_gender", this.mUser.snsInfo.sns_gender);
      localJSONObject3.putOpt("qt_id", this.mUser.userKey);
      localJSONObject3.putOpt("source", this.mUser.snsInfo.source);
      localJSONObject1.put("from", localJSONObject3);
      if (this.mAskForSongNameUser != null)
      {
        JSONObject localJSONObject4 = new JSONObject();
        localJSONObject4.putOpt("user_id", this.mAskForSongNameUser.userId);
        localJSONObject4.putOpt("sns_id", this.mAskForSongNameUser.snsInfo.sns_id);
        localJSONObject4.putOpt("sns_name", this.mAskForSongNameUser.snsInfo.sns_name);
        localJSONObject4.putOpt("sns_avatar", this.mAskForSongNameUser.snsInfo.sns_avatar);
        localJSONObject4.putOpt("sns_site", this.mAskForSongNameUser.snsInfo.sns_site);
        localJSONObject4.putOpt("sns_gender", this.mAskForSongNameUser.snsInfo.sns_gender);
        localJSONObject4.putOpt("source", this.mAskForSongNameUser.snsInfo.source);
        localJSONObject2.put("to", localJSONObject4);
      }
      localJSONObject2.put("message", "同求歌名.");
      localJSONObject2.put("type", "unknownsong");
      localJSONObject1.put("data", localJSONObject2);
      localJSONObject1.put("room", String.valueOf(this.roomId));
      JSONArray localJSONArray = new JSONArray();
      localJSONArray.put(localJSONObject1);
      this._socketClient.emit("send", localJSONArray);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void checkIn(int paramInt, String paramString)
  {
    if (paramString == null)
      paramString = "";
    if (!this.connected)
      connect(this.connectUrl, this.roomId);
    while (true)
    {
      return;
      try
      {
        if (allowCheckIn(this.roomId))
        {
          this.lstCheckIn.add(this.roomId);
          JSONObject localJSONObject1 = new JSONObject();
          JSONObject localJSONObject2 = new JSONObject();
          localJSONObject1.put("type", "checkin");
          JSONObject localJSONObject3 = new JSONObject();
          localJSONObject3.putOpt("user_id", this.mUser.userId);
          localJSONObject3.putOpt("sns_id", this.mUser.snsInfo.sns_id);
          localJSONObject3.putOpt("sns_name", this.mUser.snsInfo.sns_name);
          localJSONObject3.putOpt("sns_avatar", this.mUser.snsInfo.sns_avatar);
          localJSONObject3.putOpt("sns_site", this.mUser.snsInfo.sns_site);
          localJSONObject3.putOpt("sns_gender", this.mUser.snsInfo.sns_gender);
          localJSONObject3.putOpt("qt_id", this.mUser.userKey);
          localJSONObject3.putOpt("source", this.mUser.snsInfo.source);
          localJSONObject1.put("from", localJSONObject3);
          String str1 = "我听故我在，我正在" + getCurrentCity();
          String str2 = str1 + "收听 ";
          String str3 = getCurrentBroadcasters();
          if ((str3 != null) && (!str3.equalsIgnoreCase("")))
          {
            String str4 = str2 + str3;
            str2 = str4 + " 主持的";
          }
          localJSONObject2.put("message", str2 + "\"" + paramString + "\"");
          localJSONObject1.put("data", localJSONObject2);
          localJSONObject1.put("room", String.valueOf(this.roomId));
          JSONArray localJSONArray = new JSONArray();
          localJSONArray.put(localJSONObject1);
          this._socketClient.emit("send", localJSONArray);
          sendLiveRoomLog(1);
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
      }
    }
  }

  public void exit()
  {
    if (this._socketClient != null);
    try
    {
      leave();
      this._socketClient.disconnect();
      reset();
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void flower(int paramInt, UserInfo paramUserInfo)
  {
    if (!this.connected)
      connect(this.connectUrl, this.roomId);
    while (true)
    {
      return;
      if (paramUserInfo != null)
        try
        {
          if (paramUserInfo.snsInfo.sns_name != null)
          {
            JSONObject localJSONObject1 = new JSONObject();
            JSONObject localJSONObject2 = new JSONObject();
            localJSONObject2.putOpt("user_id", this.mUser.userId);
            localJSONObject2.putOpt("sns_id", this.mUser.snsInfo.sns_id);
            localJSONObject2.putOpt("sns_name", this.mUser.snsInfo.sns_name);
            localJSONObject2.putOpt("sns_avatar", this.mUser.snsInfo.sns_avatar);
            localJSONObject2.putOpt("sns_site", this.mUser.snsInfo.sns_site);
            localJSONObject2.putOpt("sns_gender", this.mUser.snsInfo.sns_gender);
            localJSONObject2.putOpt("source", this.mUser.snsInfo.source);
            localJSONObject2.putOpt("qt_id", this.mUser.userKey);
            localJSONObject1.put("from", localJSONObject2);
            JSONObject localJSONObject3 = new JSONObject();
            localJSONObject1.put("type", "flower");
            String str = "节目好精彩，我不远万里在" + getCurrentCity();
            localJSONObject3.put("message", str + "向 @" + paramUserInfo.snsInfo.sns_name + " 献了一朵花");
            localJSONObject1.put("data", localJSONObject3);
            localJSONObject1.put("room", String.valueOf(this.roomId));
            JSONArray localJSONArray = new JSONArray();
            localJSONArray.put(localJSONObject1);
            this._socketClient.emit("send", localJSONArray);
            sendLiveRoomLog(2);
            return;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
    }
  }

  public void getHistory(String paramString1, String paramString2, int paramInt)
  {
    if ((paramString2 == null) || (paramString1 == null))
      return;
    if ((this.roomId != null) && (!this.roomId.equalsIgnoreCase(paramString2)))
    {
      this.lstFilteredData.clear();
      RoomDataCenter.getInstance().clearRoomTopic();
    }
    this.roomId = paramString2;
    this.connectUrl = paramString1;
    if (!this.connected)
    {
      connect(paramString1, paramString2);
      this.autoGet = true;
      return;
    }
    if (paramInt <= 0);
    try
    {
      paramInt = this.maxHistoryRecords;
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("room", String.valueOf(paramString2));
      localJSONObject.put("history", paramInt);
      JSONArray localJSONArray = new JSONArray();
      localJSONArray.put(localJSONObject);
      this._socketClient.emit("zget", localJSONArray);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void getOnlineFriends(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return;
    this.roomId = paramString;
    if (!this.connected)
    {
      connect(this.connectUrl, paramString);
      return;
    }
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("room", String.valueOf(paramString));
      localJSONObject.put("count", String.valueOf(this.maxOnlineUsers));
      JSONArray localJSONArray = new JSONArray();
      localJSONArray.put(localJSONObject);
      this._socketClient.emit("onlineList", localJSONArray);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void getTopic(String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return;
    this.roomId = paramString;
    if (!this.connected)
    {
      connect(this.connectUrl, paramString);
      return;
    }
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("room", String.valueOf(paramString));
      localJSONObject.put("type", "topic");
      JSONArray localJSONArray = new JSONArray();
      localJSONArray.put(localJSONObject);
      this._socketClient.emit("notify", localJSONArray);
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void join(String paramString1, String paramString2, int paramInt)
  {
    if ((paramString2 == null) || (paramString1 == null))
      return;
    this.connectUrl = paramString1;
    if ((this.roomId != null) && (!this.roomId.equalsIgnoreCase(paramString2)))
      this.lstFilteredData.clear();
    this.roomId = paramString2;
    this.recordCnt = 0;
    if (!this.connected)
    {
      connect(paramString1, paramString2);
      this.autoJoin = true;
      return;
    }
    while (true)
    {
      JSONObject localJSONObject;
      try
      {
        this.enterLiveRoom = System.currentTimeMillis();
        localJSONObject = new JSONObject();
        localJSONObject.put("room", String.valueOf(paramString2));
        if ((this.recordCnt == -1) || (this.recordCnt > this.maxHistoryRecords))
        {
          localJSONObject.put("history", this.maxHistoryRecords);
          JSONArray localJSONArray = new JSONArray();
          localJSONArray.put(localJSONObject);
          this._socketClient.emit("join", localJSONArray);
          sendLiveRoomLog(0);
          if (!this.loginSuccess)
            break;
          enterLiveRoom();
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      localJSONObject.put("history", this.recordCnt);
    }
  }

  public void leave()
  {
    try
    {
      if (this.enterLiveRoom == 0L)
        return;
      this.autoJoin = false;
      long l = (System.currentTimeMillis() - this.enterLiveRoom) / 1000L;
      this.enterLiveRoom = 0L;
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.put("room", String.valueOf(this.roomId));
      localJSONObject.put("duration", String.valueOf(l));
      JSONArray localJSONArray = new JSONArray();
      localJSONArray.put(localJSONObject);
      this._socketClient.emit("leave", localJSONArray);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void login(UserInfo paramUserInfo)
  {
    if (paramUserInfo == null)
      break label4;
    while (true)
    {
      label4: return;
      if ((!this.loginSuccess) || (!paramUserInfo.snsInfo.sns_id.equalsIgnoreCase(this.mUser.snsInfo.sns_id)) || (!paramUserInfo.snsInfo.sns_name.equalsIgnoreCase(this.mUser.snsInfo.sns_name)))
      {
        this.loginSuccess = false;
        this.autoLogin = true;
        this.mUser = paramUserInfo;
        RoomDataCenter.getInstance().setLoginUser(paramUserInfo);
        if (!this.connected)
        {
          connect(this.connectUrl, this.roomId);
          return;
        }
        if (paramUserInfo == null)
          break;
        try
        {
          if (paramUserInfo.userId != null)
          {
            JSONObject localJSONObject = new JSONObject();
            localJSONObject.putOpt("user_id", paramUserInfo.userId);
            localJSONObject.putOpt("sns_id", paramUserInfo.snsInfo.sns_id);
            localJSONObject.putOpt("sns_name", paramUserInfo.snsInfo.sns_name);
            localJSONObject.putOpt("sns_avatar", paramUserInfo.snsInfo.sns_avatar);
            localJSONObject.putOpt("sns_site", paramUserInfo.snsInfo.sns_site);
            localJSONObject.putOpt("sns_gender", paramUserInfo.snsInfo.sns_gender);
            localJSONObject.putOpt("qt_id", paramUserInfo.userKey);
            localJSONObject.putOpt("source", paramUserInfo.snsInfo.source);
            JSONArray localJSONArray = new JSONArray();
            localJSONArray.put(localJSONObject);
            this._socketClient.emit("login", localJSONArray);
            enterLiveRoom();
            return;
          }
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    }
  }

  public void logout(UserInfo paramUserInfo)
  {
    if (paramUserInfo == null)
      return;
    if (!this.connected)
    {
      connect(this.connectUrl, this.roomId);
      return;
    }
    try
    {
      JSONObject localJSONObject = new JSONObject();
      localJSONObject.putOpt("user_id", paramUserInfo.userId);
      localJSONObject.putOpt("sns_id", paramUserInfo.snsInfo.sns_id);
      localJSONObject.putOpt("sns_name", paramUserInfo.snsInfo.sns_name);
      localJSONObject.putOpt("sns_avatar", paramUserInfo.snsInfo.sns_avatar);
      localJSONObject.putOpt("sns_site", paramUserInfo.snsInfo.sns_site);
      JSONArray localJSONArray = new JSONArray();
      localJSONArray.put(localJSONObject);
      this._socketClient.emit("logout", localJSONArray);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void on(String paramString1, String paramString2)
  {
    if (paramString2 == null);
    while (!paramString1.equalsIgnoreCase("zget"))
      return;
    parseZGetEvent(paramString2);
  }

  public void on(String paramString, JSONArray paramJSONArray)
  {
    if (paramJSONArray == null);
    do
    {
      return;
      if (paramString.equalsIgnoreCase("join"))
      {
        parseJoinEvent(paramJSONArray);
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("get"));
    parseJoinEvent(paramJSONArray);
  }

  public void on(String paramString, JSONObject paramJSONObject)
  {
    if (paramJSONObject == null);
    do
    {
      return;
      if (paramString.equalsIgnoreCase("login"))
      {
        this.loginSuccess = true;
        parseLoginEvent(paramJSONObject);
        return;
      }
      if (paramString.equalsIgnoreCase("logout"))
      {
        parseLogoutEvent(paramJSONObject);
        return;
      }
      if (paramString.equalsIgnoreCase("receive"))
      {
        parseRecvEvent(paramJSONObject);
        return;
      }
      if (paramString.equalsIgnoreCase("onlinelist"))
      {
        parseOnlineEvent(paramJSONObject);
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("notify"));
    parseRecvEvent(paramJSONObject);
  }

  public void onConnect()
  {
    this.connected = true;
    this.connecting = false;
    connectedChatRoom();
    if (this.autoJoin)
      join(this.connectUrl, this.roomId, this.recordCnt);
    if (this.autoGet)
    {
      getHistory(this.connectUrl, this.roomId, this.recordCnt);
      this.autoGet = false;
    }
    if (!this.loginSuccess)
    {
      login(this.mUser);
      this.autoLogin = false;
    }
  }

  public void onConnectFailure()
  {
    this.connected = false;
    this.connecting = false;
    if (this._socketClient != null)
      this._socketClient.disconnect();
    if (this.connectRetry > 0)
      connect(this.connectUrl, this.roomId);
    while (true)
    {
      this.connectRetry = (-1 + this.connectRetry);
      return;
      if (this.mContext == null);
    }
  }

  public void onDisconnect()
  {
    this.connected = false;
    this.loginSuccess = false;
    this.connecting = false;
  }

  public void onMessage(String paramString)
  {
  }

  public void onMessage(JSONObject paramJSONObject)
  {
  }

  public void send(String paramString1, UserInfo paramUserInfo, String paramString2, int paramInt)
  {
    if (paramString1 == null)
      return;
    if (!this.connected)
    {
      connect(this.connectUrl, this.roomId);
      return;
    }
    while (true)
    {
      JSONObject localJSONObject1;
      JSONObject localJSONObject2;
      try
      {
        localJSONObject1 = new JSONObject();
        localJSONObject2 = new JSONObject();
        JSONObject localJSONObject3 = new JSONObject();
        localJSONObject3.putOpt("user_id", this.mUser.userId);
        localJSONObject3.putOpt("sns_id", this.mUser.snsInfo.sns_id);
        localJSONObject3.putOpt("sns_name", this.mUser.snsInfo.sns_name);
        localJSONObject3.putOpt("sns_avatar", this.mUser.snsInfo.sns_avatar);
        localJSONObject3.putOpt("sns_site", this.mUser.snsInfo.sns_site);
        localJSONObject3.putOpt("sns_gender", this.mUser.snsInfo.sns_gender);
        localJSONObject3.putOpt("qt_id", this.mUser.userKey);
        localJSONObject3.putOpt("source", this.mUser.snsInfo.source);
        localJSONObject1.put("from", localJSONObject3);
        if (paramUserInfo == null)
        {
          localJSONObject1.put("type", "chat");
          localJSONObject2.put("message", paramString1);
          if (paramInt == 3)
            localJSONObject2.put("type", "private");
          localJSONObject1.put("data", localJSONObject2);
          localJSONObject1.put("room", String.valueOf(this.roomId));
          JSONArray localJSONArray = new JSONArray();
          localJSONArray.put(localJSONObject1);
          this._socketClient.emit("send", localJSONArray);
          String str = InfoManager.getInstance().getWeiboIdByGroupId(this.roomId);
          if (str == null)
            break;
          WeiboChat.getInstance().comment(str, paramString1);
          return;
        }
      }
      catch (Exception localException)
      {
        localException.printStackTrace();
        return;
      }
      JSONObject localJSONObject4 = new JSONObject();
      localJSONObject4.putOpt("user_id", paramUserInfo.userId);
      localJSONObject4.putOpt("sns_id", paramUserInfo.snsInfo.sns_id);
      localJSONObject4.putOpt("sns_name", paramUserInfo.snsInfo.sns_name);
      localJSONObject4.putOpt("sns_avatar", paramUserInfo.snsInfo.sns_avatar);
      localJSONObject4.putOpt("sns_site", paramUserInfo.snsInfo.sns_site);
      localJSONObject4.putOpt("source", paramUserInfo.snsInfo.source);
      localJSONObject4.putOpt("qt_id", paramUserInfo.userKey);
      localJSONObject1.put("type", "chat");
      localJSONObject2.put("to", localJSONObject4);
      localJSONObject2.put("message", "" + paramString1);
    }
  }

  public void speakTo(UserInfo paramUserInfo, String paramString)
  {
    if (paramUserInfo == null)
      return;
    if (!this.connected)
    {
      connect(this.connectUrl, this.roomId);
      return;
    }
    try
    {
      JSONObject localJSONObject1 = new JSONObject();
      JSONObject localJSONObject2 = new JSONObject();
      localJSONObject1.put("type", "chat");
      localJSONObject2.put("message", "@" + paramUserInfo.snsInfo.sns_name + "," + paramString);
      localJSONObject1.put("data", localJSONObject2);
      localJSONObject1.put("room", String.valueOf(this.roomId));
      JSONArray localJSONArray = new JSONArray();
      localJSONArray.put(localJSONObject1);
      this._socketClient.emit("send", localJSONArray);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void tellSongName(UserInfo paramUserInfo, String paramString)
  {
    if (!this.connected)
      connect(this.connectUrl, this.roomId);
    while ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return;
    try
    {
      JSONObject localJSONObject1 = new JSONObject();
      JSONObject localJSONObject2 = new JSONObject();
      localJSONObject1.put("type", "chat");
      JSONObject localJSONObject3 = new JSONObject();
      localJSONObject3.putOpt("user_id", this.mUser.userId);
      localJSONObject3.putOpt("sns_id", this.mUser.snsInfo.sns_id);
      localJSONObject3.putOpt("sns_name", this.mUser.snsInfo.sns_name);
      localJSONObject3.putOpt("sns_avatar", this.mUser.snsInfo.sns_avatar);
      localJSONObject3.putOpt("sns_site", this.mUser.snsInfo.sns_site);
      localJSONObject3.putOpt("sns_gender", this.mUser.snsInfo.sns_gender);
      localJSONObject3.putOpt("qt_id", this.mUser.userKey);
      localJSONObject3.putOpt("source", this.mUser.snsInfo.source);
      localJSONObject1.put("from", localJSONObject3);
      if ((paramUserInfo != null) && (paramUserInfo.userId != null))
      {
        JSONObject localJSONObject4 = new JSONObject();
        localJSONObject4.putOpt("user_id", paramUserInfo.userId);
        localJSONObject4.putOpt("sns_id", paramUserInfo.snsInfo.sns_id);
        localJSONObject4.putOpt("sns_name", paramUserInfo.snsInfo.sns_name);
        localJSONObject4.putOpt("sns_avatar", paramUserInfo.snsInfo.sns_avatar);
        localJSONObject4.putOpt("sns_site", paramUserInfo.snsInfo.sns_site);
        localJSONObject4.putOpt("qt_id", paramUserInfo.userKey);
        localJSONObject4.putOpt("source", paramUserInfo.snsInfo.source);
        localJSONObject2.put("to", localJSONObject4);
      }
      localJSONObject2.put("type", "songname");
      localJSONObject2.put("message", paramString);
      localJSONObject1.put("data", localJSONObject2);
      localJSONObject1.put("room", String.valueOf(this.roomId));
      JSONArray localJSONArray = new JSONArray();
      localJSONArray.put(localJSONObject1);
      this._socketClient.emit("send", localJSONArray);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.room.QTChat
 * JD-Core Version:    0.6.2
 */