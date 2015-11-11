package cn.com.iresearch.mapptracker.fm;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import cn.com.iresearch.mapptracker.fm.dao.EventInfo;
import cn.com.iresearch.mapptracker.fm.dao.SessionInfo;
import cn.com.iresearch.mapptracker.fm.dao.b;
import cn.com.iresearch.mapptracker.fm.util.DataProvider;
import cn.com.iresearch.mapptracker.fm.util.c;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class IRMonitor
{
  public static String a = "test_android";
  public static String b = "";
  public static boolean c = false;
  public static IRCallBack d = null;
  static int e = 0;
  private static IRMonitor h;
  private cn.com.iresearch.mapptracker.fm.a.a f;
  private Context g;
  private SharedPreferences i = null;
  private SharedPreferences.Editor j;
  private b k = null;
  private boolean l = true;
  private boolean m = false;
  private long n = 0L;
  private long o = 0L;
  private int p = 0;
  private int q = 0;
  private List<String> r = new ArrayList();
  private Map<String, EventInfo> s = new HashMap();
  private String t = null;
  private boolean u = false;

  private void a(Context paramContext)
  {
    try
    {
      if (this.k == null)
        this.k = new b();
      new a(this, paramContext).start();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  private void a(boolean paramBoolean)
  {
    try
    {
      if ((this.i == null) || (this.j == null))
      {
        this.i = h.g.getSharedPreferences("MATSharedPreferences", 0);
        this.j = this.i.edit();
      }
      if ((this.t == null) || ("".equals(this.t)))
        this.t = h.g.getPackageName();
      if (this.f == null)
        this.f = cn.com.iresearch.mapptracker.fm.a.a.a(h.g, "_ire");
      if ((this.r.isEmpty()) || (this.r == null))
        this.r = cn.com.iresearch.mapptracker.fm.util.f.b(h.g, this.t);
      if (this.k == null)
        a(h.g);
      if (!paramBoolean)
      {
        this.p = this.i.getInt("sPage_Count", 0);
        this.q = this.i.getInt("event_Count", 0);
      }
      return;
    }
    catch (Exception localException)
    {
      if (c)
        Log.e("MAT_SESSION", "内部初始化失败");
      localException.printStackTrace();
    }
  }

  private static JSONObject b(EventInfo paramEventInfo)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      String str = paramEventInfo.getLabel();
      if (str == null)
        str = "";
      localJSONObject.put("label", str);
      localJSONObject.put("event_id", paramEventInfo.getEvent_id().replace(str, ""));
      localJSONObject.put("duration", paramEventInfo.getDuration());
      localJSONObject.put("open_count", paramEventInfo.getOpen_count());
      localJSONObject.put("start_time", paramEventInfo.getStart_time());
      localJSONObject.put("end_time", paramEventInfo.getEnd_time());
      localJSONObject.put("event_params", paramEventInfo.getEvent_params());
      return localJSONObject;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return localJSONObject;
  }

  private static JSONObject b(SessionInfo paramSessionInfo)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("start_time", paramSessionInfo.getStart_time());
      localJSONObject.put("end_time", paramSessionInfo.getEnd_time());
      localJSONObject.put("duration", paramSessionInfo.getDuration());
      localJSONObject.put("page_name", paramSessionInfo.getPage_name());
      localJSONObject.put("inapp", paramSessionInfo.getInapp());
      localJSONObject.put("sessionid", paramSessionInfo.getSessionid());
      return localJSONObject;
    }
    catch (JSONException localJSONException)
    {
      localJSONException.printStackTrace();
    }
    return localJSONObject;
  }

  private void b(Context paramContext)
  {
    try
    {
      JSONObject localJSONObject = new JSONObject();
      JSONArray localJSONArray = new JSONArray();
      localJSONObject.put("header", cn.com.iresearch.mapptracker.fm.util.f.e(paramContext));
      localJSONObject.put("page_list", localJSONArray);
      localJSONObject.put("event_list", localJSONArray);
      localJSONObject.put("lat", "");
      localJSONObject.put("lng", "");
      localJSONObject.put("open_count", "0");
      localJSONObject.put("page_count", "0");
      localJSONObject.put("run_time", "0");
      String str1 = this.k.a();
      String str2 = localJSONObject.toString();
      if (cn.com.iresearch.mapptracker.fm.util.f.b(h.g))
        new e(this, str2, str1).start();
      return;
    }
    catch (JSONException localJSONException)
    {
      if (c)
        Log.e("MAT_SESSION", "初始化Session失败");
      localJSONException.printStackTrace();
    }
  }

  private static void b(Object paramObject)
  {
    if (paramObject != null);
    try
    {
      if (h != null)
      {
        SharedPreferences.Editor localEditor = h.j;
        if (localEditor != null)
          break label28;
      }
      while (true)
      {
        return;
        try
        {
          label28: h.f.a(paramObject);
          IRMonitor localIRMonitor = h;
          localIRMonitor.p = (1 + localIRMonitor.p);
          h.j.putInt("sPage_Count", h.p).commit();
        }
        catch (Exception localException)
        {
          localException.printStackTrace();
        }
      }
    }
    finally
    {
    }
  }

  private boolean b()
  {
    try
    {
      long l1 = this.i.getLong("daysend", 0L);
      String str = cn.com.iresearch.mapptracker.fm.util.f.a();
      if (!"".equals(str))
      {
        long l2 = Long.valueOf(str).longValue();
        if (l2 - l1 > 0L)
          return true;
      }
    }
    catch (NumberFormatException localNumberFormatException)
    {
      localNumberFormatException.printStackTrace();
    }
    return false;
  }

  public static IRMonitor getInstance(Context paramContext)
  {
    if (h == null);
    try
    {
      if (h == null)
      {
        IRMonitor localIRMonitor = new IRMonitor();
        h = localIRMonitor;
        localIRMonitor.g = paramContext.getApplicationContext();
      }
      h.g = paramContext;
      return h;
    }
    finally
    {
    }
  }

  public void Init(String paramString1, String paramString2, boolean paramBoolean)
  {
    if (this.i != null)
      return;
    a = paramString1;
    if (paramString2 == null)
      paramString2 = cn.com.iresearch.mapptracker.fm.util.f.d(h.g);
    b = paramString2;
    c = paramBoolean;
    this.i = h.g.getSharedPreferences("MATSharedPreferences", 0);
    this.j = this.i.edit();
    a(h.g);
    if (this.i.getBoolean("isFirstRun", true))
      b(h.g);
    a(true);
  }

  public void Init(String paramString1, String paramString2, boolean paramBoolean, IRCallBack paramIRCallBack)
  {
    if (this.i != null)
      return;
    a = paramString1;
    if (paramString2 == null)
      paramString2 = cn.com.iresearch.mapptracker.fm.util.f.d(h.g);
    b = paramString2;
    c = paramBoolean;
    d = paramIRCallBack;
    this.i = h.g.getSharedPreferences("MATSharedPreferences", 0);
    this.j = this.i.edit();
    a(h.g);
    if (this.i.getBoolean("isFirstRun", true))
      b(h.g);
    a(true);
  }

  public String getMyUid()
  {
    return cn.com.iresearch.mapptracker.fm.util.f.g(this.g);
  }

  public String getSDKVer()
  {
    return "2.3.2";
  }

  public String getVVUid()
  {
    String str1 = "";
    try
    {
      String str3 = DataProvider.getVVUid();
      if (!TextUtils.isEmpty(str3))
      {
        str1 = c.a(str3, getMyUid());
        String str4 = "VVUID=" + str1;
        if (c)
          Log.d("MAT_SESSION", str4);
      }
      return str1;
    }
    catch (UnsatisfiedLinkError localUnsatisfiedLinkError)
    {
      localUnsatisfiedLinkError.printStackTrace();
      try
      {
        String str2 = c.a("mvcv1RTK", getMyUid());
        return str2;
      }
      catch (Exception localException2)
      {
        localException2.printStackTrace();
        return str1;
      }
    }
    catch (Exception localException1)
    {
      localException1.printStackTrace();
    }
    return str1;
  }

  public void onEvent(String paramString)
  {
    onEvent(paramString, "");
  }

  public void onEvent(String paramString1, String paramString2)
  {
    onEvent(paramString1, paramString2, null);
  }

  public void onEvent(String paramString1, String paramString2, Map<String, String> paramMap)
  {
    try
    {
      EventInfo localEventInfo = new EventInfo();
      localEventInfo.setEvent_id(paramString1 + paramString2);
      localEventInfo.setLabel(paramString2);
      localEventInfo.setStart_time(System.currentTimeMillis() / 1000L);
      localEventInfo.setEnd_time(0L);
      localEventInfo.setDuration(0L);
      localEventInfo.setOpen_count(0L);
      localEventInfo.setEvent_params(cn.com.iresearch.mapptracker.fm.util.f.a(paramMap));
      new f(true, localEventInfo, paramString1 + paramString2).start();
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
    }
  }

  public void onEventEnd(String paramString1, String paramString2)
  {
    try
    {
      EventInfo localEventInfo = (EventInfo)this.s.get(paramString1 + paramString2);
      if ((localEventInfo != null) && (localEventInfo.eventisStart))
      {
        long l1 = System.currentTimeMillis() / 1000L;
        localEventInfo.setEnd_time(l1);
        localEventInfo.setDuration(l1 - localEventInfo.getStart_time());
        if (localEventInfo.eventisStart)
          localEventInfo.open_count = (1L + localEventInfo.open_count);
        localEventInfo.eventisStart = false;
        localEventInfo.setLabel(paramString2);
        localEventInfo.setStart_time(localEventInfo.first_start_time);
        this.s.put(paramString1 + paramString2, localEventInfo);
        String str = "event_id= " + paramString1 + paramString2 + "的事件结束! start_time:" + localEventInfo.first_start_time + " end_time:" + localEventInfo.getEnd_time() + " duration:" + localEventInfo.getDuration() + " 事件发生次数:" + localEventInfo.getOpen_count();
        if (c)
          Log.e("MAT_EVENT", str);
        new f(false, localEventInfo, paramString1 + paramString2).start();
        return;
      }
      if (c)
      {
        Log.e("MAT_EVENT", "请先调用onEventStart!");
        return;
      }
    }
    catch (Exception localException)
    {
      if (c)
        Log.e("MAT_EVENT", "事件结束保存失败!");
      localException.printStackTrace();
    }
  }

  public void onEventStart(String paramString1, String paramString2, Map<String, String> paramMap)
  {
    try
    {
      EventInfo localEventInfo1 = (EventInfo)this.s.get(paramString1 + paramString2);
      long l1 = System.currentTimeMillis() / 1000L;
      if (localEventInfo1 != null)
      {
        if (localEventInfo1.event_id.equals(paramString1 + paramString2))
        {
          localEventInfo1.setEvent_params(cn.com.iresearch.mapptracker.fm.util.f.a(paramMap));
          localEventInfo1.setStart_time(l1);
          localEventInfo1.eventisStart = true;
          String str1 = "event_id= " + paramString1 + paramString2 + "的事件开始! start_time:" + localEventInfo1.first_start_time;
          if (c)
            Log.e("MAT_EVENT", str1);
          this.s.put(paramString1 + paramString2, localEventInfo1);
        }
      }
      else
      {
        EventInfo localEventInfo2 = new EventInfo();
        localEventInfo2.event_id = (paramString1 + paramString2);
        localEventInfo2.setEvent_params(cn.com.iresearch.mapptracker.fm.util.f.a(paramMap));
        localEventInfo2.setStart_time(l1);
        localEventInfo2.first_start_time = l1;
        localEventInfo2.eventisStart = true;
        String str2 = "event_id= " + paramString1 + paramString2 + "的事件开始(第一次)! ,start_time: " + localEventInfo2.first_start_time;
        if (c)
          Log.e("MAT_EVENT", str2);
        this.s.put(paramString1 + paramString2, localEventInfo2);
        return;
      }
    }
    catch (Exception localException)
    {
      if (c)
        Log.e("MAT_EVENT", "事件开启失败:\n");
      localException.printStackTrace();
    }
  }

  public void onPause()
  {
    if (!"main".equals(Thread.currentThread().getName()))
      if (c)
        Log.e("MAT_SESSION", "非main线程return");
    do
    {
      return;
      if (Looper.myLooper() == Looper.getMainLooper())
        break;
    }
    while (!c);
    Log.e("MAT_SESSION", "非主线程return");
    return;
    while (true)
    {
      long l1;
      SessionInfo localSessionInfo;
      String str4;
      try
      {
        h.m = false;
        if (this.l)
          break;
        this.l = true;
        this.o = (System.currentTimeMillis() / 1000L);
        l1 = this.o - this.n;
        if (l1 < 1L)
          break;
        localSessionInfo = new SessionInfo();
        localSessionInfo.setStart_time(this.n);
        localSessionInfo.setEnd_time(this.o);
        localSessionInfo.setDuration(l1);
        localSessionInfo.setSessionid(5L * (63529L + Long.parseLong(cn.com.iresearch.mapptracker.fm.util.f.a())) + (int)Math.round(10000.0D + 89999.0D * Math.random()));
        Context localContext = h.g.getApplicationContext();
        if ("".equals(h.t))
        {
          str1 = cn.com.iresearch.mapptracker.fm.util.f.c(h.g);
          if (cn.com.iresearch.mapptracker.fm.util.f.c(localContext, str1))
            break label451;
          localSessionInfo.setInapp(0L);
          str4 = cn.com.iresearch.mapptracker.fm.util.f.a(h.g.getApplicationContext(), 1);
          if (this.r.contains(str4))
            break label398;
          str4 = cn.com.iresearch.mapptracker.fm.util.f.a(h.g.getApplicationContext(), 2);
          String str6 = "通过激活最近运行程序到后台,前一个Activity: " + str4 + " 运行时间:" + l1 + "s";
          if (c)
            Log.e("MAT_SESSION", str6);
          localSessionInfo.setPage_name(str4);
          if (this.p > this.k.g())
            break;
          new d(this, localSessionInfo).start();
          return;
        }
      }
      catch (Exception localException)
      {
        if (c)
          Log.e("MAT_SESSION", "onPause逻辑失败");
        localException.printStackTrace();
        return;
      }
      String str1 = h.t;
      continue;
      label398: String str5 = "按HOME键去后台,前一个Activity: " + str4 + " 运行时间:" + l1 + "s";
      if (c)
      {
        Log.e("MAT_SESSION", str5);
        continue;
        label451: String str2 = cn.com.iresearch.mapptracker.fm.util.f.a(h.g.getApplicationContext(), 0);
        localSessionInfo.setPage_name(str2);
        localSessionInfo.setInapp(1L);
        String str3 = "在本app中跳转,前一个Activity: " + str2 + " 运行时间:" + l1 + "s";
        if (c)
          Log.e("MAT_SESSION", str3);
      }
    }
  }

  // ERROR //
  public void onResume()
  {
    // Byte code:
    //   0: ldc_w 544
    //   3: invokestatic 550	java/lang/Thread:currentThread	()Ljava/lang/Thread;
    //   6: invokevirtual 553	java/lang/Thread:getName	()Ljava/lang/String;
    //   9: invokevirtual 147	java/lang/String:equals	(Ljava/lang/Object;)Z
    //   12: ifne +4 -> 16
    //   15: return
    //   16: invokestatic 561	android/os/Looper:myLooper	()Landroid/os/Looper;
    //   19: invokestatic 564	android/os/Looper:getMainLooper	()Landroid/os/Looper;
    //   22: if_acmpne -7 -> 15
    //   25: aload_0
    //   26: iconst_0
    //   27: putfield 65	cn/com/iresearch/mapptracker/fm/IRMonitor:l	Z
    //   30: aload_0
    //   31: iconst_0
    //   32: invokespecial 406	cn/com/iresearch/mapptracker/fm/IRMonitor:a	(Z)V
    //   35: aload_0
    //   36: invokestatic 469	java/lang/System:currentTimeMillis	()J
    //   39: ldc2_w 470
    //   42: ldiv
    //   43: putfield 69	cn/com/iresearch/mapptracker/fm/IRMonitor:n	J
    //   46: aload_0
    //   47: getfield 71	cn/com/iresearch/mapptracker/fm/IRMonitor:o	J
    //   50: lconst_0
    //   51: lcmp
    //   52: ifne +20 -> 72
    //   55: aload_0
    //   56: aload_0
    //   57: getfield 61	cn/com/iresearch/mapptracker/fm/IRMonitor:i	Landroid/content/SharedPreferences;
    //   60: ldc_w 641
    //   63: lconst_0
    //   64: invokeinterface 323 4 0
    //   69: putfield 71	cn/com/iresearch/mapptracker/fm/IRMonitor:o	J
    //   72: aload_0
    //   73: getfield 69	cn/com/iresearch/mapptracker/fm/IRMonitor:n	J
    //   76: aload_0
    //   77: getfield 71	cn/com/iresearch/mapptracker/fm/IRMonitor:o	J
    //   80: lsub
    //   81: invokestatic 645	java/lang/Math:abs	(J)J
    //   84: lstore_3
    //   85: bipush 60
    //   87: aload_0
    //   88: getfield 63	cn/com/iresearch/mapptracker/fm/IRMonitor:k	Lcn/com/iresearch/mapptracker/fm/dao/b;
    //   91: invokevirtual 647	cn/com/iresearch/mapptracker/fm/dao/b:e	()I
    //   94: imul
    //   95: i2l
    //   96: lstore 5
    //   98: lload_3
    //   99: ldc2_w 648
    //   102: lcmp
    //   103: ifle +15 -> 118
    //   106: aload_0
    //   107: getfield 89	cn/com/iresearch/mapptracker/fm/IRMonitor:u	Z
    //   110: ifeq +8 -> 118
    //   113: aload_0
    //   114: iconst_0
    //   115: putfield 89	cn/com/iresearch/mapptracker/fm/IRMonitor:u	Z
    //   118: aload_0
    //   119: getfield 89	cn/com/iresearch/mapptracker/fm/IRMonitor:u	Z
    //   122: ifne +182 -> 304
    //   125: aload_0
    //   126: invokespecial 651	cn/com/iresearch/mapptracker/fm/IRMonitor:b	()Z
    //   129: ifeq +175 -> 304
    //   132: aload_0
    //   133: iconst_1
    //   134: putfield 89	cn/com/iresearch/mapptracker/fm/IRMonitor:u	Z
    //   137: getstatic 93	cn/com/iresearch/mapptracker/fm/IRMonitor:h	Lcn/com/iresearch/mapptracker/fm/IRMonitor;
    //   140: getfield 127	cn/com/iresearch/mapptracker/fm/IRMonitor:g	Landroid/content/Context;
    //   143: invokevirtual 388	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   146: astore 27
    //   148: new 195	org/json/JSONObject
    //   151: dup
    //   152: invokespecial 196	org/json/JSONObject:<init>	()V
    //   155: astore 28
    //   157: new 268	org/json/JSONArray
    //   160: dup
    //   161: invokespecial 269	org/json/JSONArray:<init>	()V
    //   164: astore 29
    //   166: aload 28
    //   168: ldc_w 271
    //   171: aload 27
    //   173: invokestatic 274	cn/com/iresearch/mapptracker/fm/util/f:e	(Landroid/content/Context;)Lorg/json/JSONObject;
    //   176: invokevirtual 207	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   179: pop
    //   180: aload 28
    //   182: ldc_w 276
    //   185: aload 29
    //   187: invokevirtual 207	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   190: pop
    //   191: aload 28
    //   193: ldc_w 278
    //   196: aload 29
    //   198: invokevirtual 207	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   201: pop
    //   202: aload 28
    //   204: ldc_w 280
    //   207: ldc_w 652
    //   210: invokevirtual 207	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   213: pop
    //   214: aload 28
    //   216: ldc_w 282
    //   219: ldc_w 654
    //   222: invokevirtual 207	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   225: pop
    //   226: aload 28
    //   228: ldc 227
    //   230: ldc_w 284
    //   233: invokevirtual 207	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   236: pop
    //   237: aload 28
    //   239: ldc_w 286
    //   242: ldc_w 284
    //   245: invokevirtual 207	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   248: pop
    //   249: aload 28
    //   251: ldc_w 288
    //   254: ldc_w 284
    //   257: invokevirtual 207	org/json/JSONObject:put	(Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   260: pop
    //   261: aload_0
    //   262: getfield 63	cn/com/iresearch/mapptracker/fm/IRMonitor:k	Lcn/com/iresearch/mapptracker/fm/dao/b;
    //   265: invokevirtual 290	cn/com/iresearch/mapptracker/fm/dao/b:a	()Ljava/lang/String;
    //   268: astore 40
    //   270: aload 28
    //   272: invokevirtual 293	org/json/JSONObject:toString	()Ljava/lang/String;
    //   275: astore 41
    //   277: getstatic 93	cn/com/iresearch/mapptracker/fm/IRMonitor:h	Lcn/com/iresearch/mapptracker/fm/IRMonitor;
    //   280: getfield 127	cn/com/iresearch/mapptracker/fm/IRMonitor:g	Landroid/content/Context;
    //   283: invokestatic 296	cn/com/iresearch/mapptracker/fm/util/f:b	(Landroid/content/Context;)Z
    //   286: ifeq +18 -> 304
    //   289: new 656	cn/com/iresearch/mapptracker/fm/c
    //   292: dup
    //   293: aload_0
    //   294: aload 41
    //   296: aload 40
    //   298: invokespecial 657	cn/com/iresearch/mapptracker/fm/c:<init>	(Lcn/com/iresearch/mapptracker/fm/IRMonitor;Ljava/lang/String;Ljava/lang/String;)V
    //   301: invokevirtual 658	cn/com/iresearch/mapptracker/fm/c:start	()V
    //   304: lload_3
    //   305: lload 5
    //   307: lcmp
    //   308: iflt +369 -> 677
    //   311: aload_0
    //   312: getfield 71	cn/com/iresearch/mapptracker/fm/IRMonitor:o	J
    //   315: lconst_0
    //   316: lcmp
    //   317: ifeq +360 -> 677
    //   320: new 433	java/lang/StringBuilder
    //   323: dup
    //   324: ldc_w 660
    //   327: invokespecial 437	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   330: lload_3
    //   331: invokevirtual 520	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   334: ldc_w 662
    //   337: invokevirtual 441	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   340: lload 5
    //   342: invokevirtual 520	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   345: ldc_w 664
    //   348: invokevirtual 441	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   351: invokevirtual 442	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   354: astore 9
    //   356: getstatic 52	cn/com/iresearch/mapptracker/fm/IRMonitor:c	Z
    //   359: ifeq +11 -> 370
    //   362: ldc 183
    //   364: aload 9
    //   366: invokestatic 190	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   369: pop
    //   370: getstatic 93	cn/com/iresearch/mapptracker/fm/IRMonitor:h	Lcn/com/iresearch/mapptracker/fm/IRMonitor;
    //   373: getfield 153	cn/com/iresearch/mapptracker/fm/IRMonitor:f	Lcn/com/iresearch/mapptracker/fm/a/a;
    //   376: ldc 248
    //   378: invokevirtual 667	cn/com/iresearch/mapptracker/fm/a/a:b	(Ljava/lang/Class;)Ljava/util/List;
    //   381: astore 12
    //   383: new 82	java/util/HashMap
    //   386: dup
    //   387: invokespecial 83	java/util/HashMap:<init>	()V
    //   390: astore 13
    //   392: aload 12
    //   394: invokeinterface 671 1 0
    //   399: astore 14
    //   401: aload 14
    //   403: invokeinterface 676 1 0
    //   408: ifne +168 -> 576
    //   411: aload 13
    //   413: invokeinterface 680 1 0
    //   418: invokeinterface 683 1 0
    //   423: astore 21
    //   425: aload 21
    //   427: invokeinterface 676 1 0
    //   432: ifeq -417 -> 15
    //   435: aload 21
    //   437: invokeinterface 687 1 0
    //   442: checkcast 689	java/util/Map$Entry
    //   445: astore 22
    //   447: getstatic 93	cn/com/iresearch/mapptracker/fm/IRMonitor:h	Lcn/com/iresearch/mapptracker/fm/IRMonitor;
    //   450: getfield 127	cn/com/iresearch/mapptracker/fm/IRMonitor:g	Landroid/content/Context;
    //   453: invokevirtual 388	android/content/Context:getApplicationContext	()Landroid/content/Context;
    //   456: pop
    //   457: aload 22
    //   459: invokeinterface 692 1 0
    //   464: checkcast 162	java/util/List
    //   467: astore 24
    //   469: aload 22
    //   471: invokeinterface 695 1 0
    //   476: checkcast 143	java/lang/String
    //   479: astore 25
    //   481: getstatic 93	cn/com/iresearch/mapptracker/fm/IRMonitor:h	Lcn/com/iresearch/mapptracker/fm/IRMonitor;
    //   484: getfield 67	cn/com/iresearch/mapptracker/fm/IRMonitor:m	Z
    //   487: ifne -62 -> 425
    //   490: new 697	cn/com/iresearch/mapptracker/fm/b
    //   493: dup
    //   494: aload 24
    //   496: aload 25
    //   498: invokespecial 700	cn/com/iresearch/mapptracker/fm/b:<init>	(Ljava/util/List;Ljava/lang/String;)V
    //   501: invokevirtual 701	cn/com/iresearch/mapptracker/fm/b:start	()V
    //   504: goto -79 -> 425
    //   507: astore 10
    //   509: getstatic 52	cn/com/iresearch/mapptracker/fm/IRMonitor:c	Z
    //   512: ifeq +12 -> 524
    //   515: ldc 183
    //   517: ldc_w 703
    //   520: invokestatic 190	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   523: pop
    //   524: aload 10
    //   526: invokevirtual 116	java/lang/Exception:printStackTrace	()V
    //   529: return
    //   530: astore_1
    //   531: getstatic 52	cn/com/iresearch/mapptracker/fm/IRMonitor:c	Z
    //   534: ifeq +12 -> 546
    //   537: ldc 183
    //   539: ldc_w 705
    //   542: invokestatic 190	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   545: pop
    //   546: aload_1
    //   547: invokevirtual 116	java/lang/Exception:printStackTrace	()V
    //   550: return
    //   551: astore 30
    //   553: getstatic 52	cn/com/iresearch/mapptracker/fm/IRMonitor:c	Z
    //   556: ifeq +12 -> 568
    //   559: ldc 183
    //   561: ldc_w 707
    //   564: invokestatic 190	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   567: pop
    //   568: aload 30
    //   570: invokevirtual 246	org/json/JSONException:printStackTrace	()V
    //   573: goto -269 -> 304
    //   576: aload 14
    //   578: invokeinterface 687 1 0
    //   583: checkcast 248	cn/com/iresearch/mapptracker/fm/dao/SessionInfo
    //   586: astore 15
    //   588: aload 15
    //   590: invokevirtual 266	cn/com/iresearch/mapptracker/fm/dao/SessionInfo:getSessionid	()Ljava/lang/String;
    //   593: iconst_0
    //   594: bipush 251
    //   596: aload 15
    //   598: invokevirtual 266	cn/com/iresearch/mapptracker/fm/dao/SessionInfo:getSessionid	()Ljava/lang/String;
    //   601: invokevirtual 710	java/lang/String:length	()I
    //   604: iadd
    //   605: invokevirtual 714	java/lang/String:substring	(II)Ljava/lang/String;
    //   608: astore 16
    //   610: aload 13
    //   612: aload 16
    //   614: invokeinterface 717 2 0
    //   619: ifeq +26 -> 645
    //   622: aload 13
    //   624: aload 16
    //   626: invokeinterface 502 2 0
    //   631: checkcast 162	java/util/List
    //   634: aload 15
    //   636: invokeinterface 720 2 0
    //   641: pop
    //   642: goto -241 -> 401
    //   645: new 77	java/util/ArrayList
    //   648: dup
    //   649: invokespecial 78	java/util/ArrayList:<init>	()V
    //   652: astore 17
    //   654: aload 17
    //   656: aload 15
    //   658: invokevirtual 721	java/util/ArrayList:add	(Ljava/lang/Object;)Z
    //   661: pop
    //   662: aload 13
    //   664: aload 16
    //   666: aload 17
    //   668: invokeinterface 513 3 0
    //   673: pop
    //   674: goto -273 -> 401
    //   677: new 433	java/lang/StringBuilder
    //   680: dup
    //   681: ldc_w 660
    //   684: invokespecial 437	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   687: lload_3
    //   688: invokevirtual 520	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   691: ldc_w 723
    //   694: invokevirtual 441	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   697: lload 5
    //   699: invokevirtual 520	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   702: ldc_w 725
    //   705: invokevirtual 441	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   708: invokevirtual 442	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   711: astore 7
    //   713: getstatic 52	cn/com/iresearch/mapptracker/fm/IRMonitor:c	Z
    //   716: ifeq -701 -> 15
    //   719: ldc 183
    //   721: aload 7
    //   723: invokestatic 190	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;)I
    //   726: pop
    //   727: return
    //
    // Exception table:
    //   from	to	target	type
    //   370	401	507	java/lang/Exception
    //   401	425	507	java/lang/Exception
    //   425	504	507	java/lang/Exception
    //   576	642	507	java/lang/Exception
    //   645	674	507	java/lang/Exception
    //   25	72	530	java/lang/Exception
    //   72	98	530	java/lang/Exception
    //   106	118	530	java/lang/Exception
    //   118	148	530	java/lang/Exception
    //   148	304	530	java/lang/Exception
    //   311	370	530	java/lang/Exception
    //   509	524	530	java/lang/Exception
    //   524	529	530	java/lang/Exception
    //   553	568	530	java/lang/Exception
    //   568	573	530	java/lang/Exception
    //   677	727	530	java/lang/Exception
    //   148	304	551	org/json/JSONException
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     cn.com.iresearch.mapptracker.fm.IRMonitor
 * JD-Core Version:    0.6.2
 */