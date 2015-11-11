package fm.qingting.download.qtradiodownload.network.http.conn;

import fm.qingting.download.qtradiodownload.network.http.HttpRequest;
import fm.qingting.qtradio.wo.WoApiRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

public abstract class HttpConnection
  implements Runnable
{
  protected static final int CONNECTION_STATE_CONNECTED = 1;
  protected static final int CONNECTION_STATE_CONNECTING = 0;
  protected static final int CONNECTION_STATE_FINISH = 2;
  private static final int CONNECTION_TIMEOUT = 3000;
  private static final String ErrorType = "DownloadError";
  private static final String TAG = "HttpConnection";
  private static boolean isViaWoProxy = false;
  protected HttpURLConnection conn = null;
  protected InputStream in = null;
  protected HttpConnectionListener listener = null;
  private String name = "";
  protected HttpRequest request = null;

  public static boolean getIsViaWoProxy()
  {
    return isViaWoProxy;
  }

  public static void setIsViaWoProxy(boolean paramBoolean)
  {
    isViaWoProxy = paramBoolean;
  }

  public void close()
  {
    try
    {
      if (this.in != null)
      {
        this.in.close();
        this.in = null;
      }
      return;
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
      return;
    }
    finally
    {
      if (this.conn != null)
      {
        this.conn.disconnect();
        this.conn = null;
      }
    }
  }

  public HttpURLConnection connect()
    throws MalformedURLException, IOException, NullPointerException
  {
    if (isViaWoProxy);
    try
    {
      String str1 = WoApiRequest.getProxyServer();
      if (str1 != null)
      {
        String str2 = WoApiRequest.getProxyPortString();
        System.getProperties().put("http.proxySet", "true");
        System.getProperties().put("http.proxyHost", str1);
        System.getProperties().put("http.proxyPort", str2);
        Authenticator.setDefault(new Authenticator()
        {
          protected PasswordAuthentication getPasswordAuthentication()
          {
            return new PasswordAuthentication(WoApiRequest.getAppKey(), WoApiRequest.getAppSecret().toCharArray());
          }
        });
      }
      while (true)
      {
        label65: URL localURL = new URL(this.request.getUrl());
        try
        {
          this.conn = ((HttpURLConnection)localURL.openConnection());
          this.conn.setRequestMethod(this.request.getMethod());
          if ("POST".equalsIgnoreCase(this.request.getMethod()))
          {
            this.conn.setDoInput(true);
            this.conn.setDoOutput(true);
          }
          HashMap localHashMap = this.request.getHeaders();
          if (localHashMap != null)
          {
            Iterator localIterator = localHashMap.entrySet().iterator();
            while (localIterator.hasNext())
            {
              Map.Entry localEntry = (Map.Entry)localIterator.next();
              this.conn.setRequestProperty((String)localEntry.getKey(), (String)localEntry.getValue());
              continue;
              System.getProperties().put("http.proxySet", "false");
            }
          }
        }
        catch (IOException localIOException1)
        {
          localIOException1.printStackTrace();
          throw localIOException1;
        }
      }
      this.conn.setConnectTimeout(3000);
      this.conn.setReadTimeout(3000);
      try
      {
        this.conn.connect();
        return this.conn;
      }
      catch (IOException localIOException2)
      {
        localIOException2.printStackTrace();
        throw localIOException2;
      }
      catch (NullPointerException localNullPointerException)
      {
        throw localNullPointerException;
      }
    }
    catch (Exception localException)
    {
      break label65;
    }
  }

  public InputStream getInputStream()
    throws IOException
  {
    this.in = this.conn.getInputStream();
    return this.in;
  }

  public String getName()
  {
    return this.name;
  }

  public abstract void handleConnectingFail();

  public abstract void handleConnectionFinished();

  public abstract void handlePart();

  public abstract void handleResponse(InputStream paramInputStream)
    throws IOException;

  // ERROR //
  public void run()
  {
    // Byte code:
    //   0: iconst_1
    //   1: istore_1
    //   2: aload_0
    //   3: invokevirtual 214	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:connect	()Ljava/net/HttpURLConnection;
    //   6: pop
    //   7: aload_0
    //   8: getfield 48	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:listener	Lfm/qingting/download/qtradiodownload/network/http/conn/HttpConnectionListener;
    //   11: ifnull +12 -> 23
    //   14: aload_0
    //   15: getfield 48	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:listener	Lfm/qingting/download/qtradiodownload/network/http/conn/HttpConnectionListener;
    //   18: invokeinterface 219 1 0
    //   23: aload_0
    //   24: getfield 46	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:request	Lfm/qingting/download/qtradiodownload/network/http/HttpRequest;
    //   27: invokevirtual 223	fm/qingting/download/qtradiodownload/network/http/HttpRequest:getData	()[B
    //   30: ifnull +25 -> 55
    //   33: aload_0
    //   34: getfield 46	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:request	Lfm/qingting/download/qtradiodownload/network/http/HttpRequest;
    //   37: invokevirtual 223	fm/qingting/download/qtradiodownload/network/http/HttpRequest:getData	()[B
    //   40: arraylength
    //   41: ifle +14 -> 55
    //   44: aload_0
    //   45: aload_0
    //   46: getfield 46	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:request	Lfm/qingting/download/qtradiodownload/network/http/HttpRequest;
    //   49: invokevirtual 223	fm/qingting/download/qtradiodownload/network/http/HttpRequest:getData	()[B
    //   52: invokevirtual 227	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:sendData	([B)V
    //   55: aload_0
    //   56: getfield 50	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:conn	Ljava/net/HttpURLConnection;
    //   59: invokevirtual 231	java/net/HttpURLConnection:getResponseCode	()I
    //   62: istore 12
    //   64: iload 12
    //   66: sipush 206
    //   69: if_icmpne +389 -> 458
    //   72: aload_0
    //   73: aload_0
    //   74: invokevirtual 232	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:getInputStream	()Ljava/io/InputStream;
    //   77: invokevirtual 234	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:handleResponse	(Ljava/io/InputStream;)V
    //   80: iconst_0
    //   81: istore_1
    //   82: aload_0
    //   83: invokevirtual 235	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:close	()V
    //   86: iload_1
    //   87: ifne +7 -> 94
    //   90: aload_0
    //   91: invokevirtual 237	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:handlePart	()V
    //   94: aload_0
    //   95: invokevirtual 239	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:handleConnectionFinished	()V
    //   98: return
    //   99: astore 6
    //   101: aload 6
    //   103: invokevirtual 240	java/net/MalformedURLException:printStackTrace	()V
    //   106: invokestatic 246	fm/qingting/qtradio/log/LogModule:getInstance	()Lfm/qingting/qtradio/log/LogModule;
    //   109: ldc 19
    //   111: new 248	java/lang/StringBuilder
    //   114: dup
    //   115: invokespecial 249	java/lang/StringBuilder:<init>	()V
    //   118: ldc 251
    //   120: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   123: aload_0
    //   124: getfield 46	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:request	Lfm/qingting/download/qtradiodownload/network/http/HttpRequest;
    //   127: invokevirtual 126	fm/qingting/download/qtradiodownload/network/http/HttpRequest:getUrl	()Ljava/lang/String;
    //   130: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   133: invokevirtual 258	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   136: invokevirtual 261	fm/qingting/qtradio/log/LogModule:send	(Ljava/lang/String;Ljava/lang/String;)V
    //   139: invokestatic 266	fm/qingting/qtradio/logger/QTLogger:getInstance	()Lfm/qingting/qtradio/logger/QTLogger;
    //   142: new 248	java/lang/StringBuilder
    //   145: dup
    //   146: invokespecial 249	java/lang/StringBuilder:<init>	()V
    //   149: ldc_w 268
    //   152: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   155: aload_0
    //   156: getfield 46	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:request	Lfm/qingting/download/qtradiodownload/network/http/HttpRequest;
    //   159: invokevirtual 126	fm/qingting/download/qtradiodownload/network/http/HttpRequest:getUrl	()Ljava/lang/String;
    //   162: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   165: invokevirtual 258	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   168: invokevirtual 271	fm/qingting/qtradio/logger/QTLogger:addDownloadExceptionLog	(Ljava/lang/String;)V
    //   171: aload_0
    //   172: getfield 48	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:listener	Lfm/qingting/download/qtradiodownload/network/http/conn/HttpConnectionListener;
    //   175: ifnull +15 -> 190
    //   178: aload_0
    //   179: getfield 48	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:listener	Lfm/qingting/download/qtradiodownload/network/http/conn/HttpConnectionListener;
    //   182: bipush 101
    //   184: aconst_null
    //   185: invokeinterface 275 3 0
    //   190: aload_0
    //   191: invokevirtual 235	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:close	()V
    //   194: aload_0
    //   195: invokevirtual 277	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:handleConnectingFail	()V
    //   198: return
    //   199: astore 5
    //   201: aload 5
    //   203: invokevirtual 71	java/io/IOException:printStackTrace	()V
    //   206: invokestatic 246	fm/qingting/qtradio/log/LogModule:getInstance	()Lfm/qingting/qtradio/log/LogModule;
    //   209: ldc 19
    //   211: new 248	java/lang/StringBuilder
    //   214: dup
    //   215: invokespecial 249	java/lang/StringBuilder:<init>	()V
    //   218: ldc_w 279
    //   221: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   224: aload_0
    //   225: getfield 46	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:request	Lfm/qingting/download/qtradiodownload/network/http/HttpRequest;
    //   228: invokevirtual 126	fm/qingting/download/qtradiodownload/network/http/HttpRequest:getUrl	()Ljava/lang/String;
    //   231: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   234: ldc_w 281
    //   237: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   240: aload 5
    //   242: invokevirtual 284	java/io/IOException:getMessage	()Ljava/lang/String;
    //   245: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   248: invokevirtual 258	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   251: invokevirtual 261	fm/qingting/qtradio/log/LogModule:send	(Ljava/lang/String;Ljava/lang/String;)V
    //   254: invokestatic 266	fm/qingting/qtradio/logger/QTLogger:getInstance	()Lfm/qingting/qtradio/logger/QTLogger;
    //   257: new 248	java/lang/StringBuilder
    //   260: dup
    //   261: invokespecial 249	java/lang/StringBuilder:<init>	()V
    //   264: ldc_w 286
    //   267: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   270: aload_0
    //   271: getfield 46	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:request	Lfm/qingting/download/qtradiodownload/network/http/HttpRequest;
    //   274: invokevirtual 126	fm/qingting/download/qtradiodownload/network/http/HttpRequest:getUrl	()Ljava/lang/String;
    //   277: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   280: ldc_w 288
    //   283: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   286: aload 5
    //   288: invokevirtual 284	java/io/IOException:getMessage	()Ljava/lang/String;
    //   291: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   294: invokevirtual 258	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   297: invokevirtual 271	fm/qingting/qtradio/logger/QTLogger:addDownloadExceptionLog	(Ljava/lang/String;)V
    //   300: aload_0
    //   301: getfield 48	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:listener	Lfm/qingting/download/qtradiodownload/network/http/conn/HttpConnectionListener;
    //   304: ifnull +15 -> 319
    //   307: aload_0
    //   308: getfield 48	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:listener	Lfm/qingting/download/qtradiodownload/network/http/conn/HttpConnectionListener;
    //   311: bipush 102
    //   313: aconst_null
    //   314: invokeinterface 275 3 0
    //   319: aload_0
    //   320: invokevirtual 235	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:close	()V
    //   323: aload_0
    //   324: invokevirtual 277	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:handleConnectingFail	()V
    //   327: return
    //   328: astore 4
    //   330: invokestatic 246	fm/qingting/qtradio/log/LogModule:getInstance	()Lfm/qingting/qtradio/log/LogModule;
    //   333: ldc 19
    //   335: new 248	java/lang/StringBuilder
    //   338: dup
    //   339: invokespecial 249	java/lang/StringBuilder:<init>	()V
    //   342: ldc_w 290
    //   345: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   348: aload_0
    //   349: getfield 46	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:request	Lfm/qingting/download/qtradiodownload/network/http/HttpRequest;
    //   352: invokevirtual 126	fm/qingting/download/qtradiodownload/network/http/HttpRequest:getUrl	()Ljava/lang/String;
    //   355: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   358: invokevirtual 258	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   361: invokevirtual 261	fm/qingting/qtradio/log/LogModule:send	(Ljava/lang/String;Ljava/lang/String;)V
    //   364: invokestatic 266	fm/qingting/qtradio/logger/QTLogger:getInstance	()Lfm/qingting/qtradio/logger/QTLogger;
    //   367: new 248	java/lang/StringBuilder
    //   370: dup
    //   371: invokespecial 249	java/lang/StringBuilder:<init>	()V
    //   374: ldc_w 292
    //   377: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   380: aload_0
    //   381: getfield 46	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:request	Lfm/qingting/download/qtradiodownload/network/http/HttpRequest;
    //   384: invokevirtual 126	fm/qingting/download/qtradiodownload/network/http/HttpRequest:getUrl	()Ljava/lang/String;
    //   387: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   390: ldc_w 288
    //   393: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   396: aload 4
    //   398: invokevirtual 293	java/lang/Exception:getMessage	()Ljava/lang/String;
    //   401: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   404: invokevirtual 258	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   407: invokevirtual 271	fm/qingting/qtradio/logger/QTLogger:addDownloadExceptionLog	(Ljava/lang/String;)V
    //   410: aload_0
    //   411: getfield 48	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:listener	Lfm/qingting/download/qtradiodownload/network/http/conn/HttpConnectionListener;
    //   414: ifnull +15 -> 429
    //   417: aload_0
    //   418: getfield 48	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:listener	Lfm/qingting/download/qtradiodownload/network/http/conn/HttpConnectionListener;
    //   421: bipush 105
    //   423: aconst_null
    //   424: invokeinterface 275 3 0
    //   429: aload_0
    //   430: invokevirtual 235	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:close	()V
    //   433: aload_0
    //   434: invokevirtual 277	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:handleConnectingFail	()V
    //   437: return
    //   438: astore_2
    //   439: iconst_0
    //   440: istore_1
    //   441: aload_2
    //   442: astore_3
    //   443: iload_1
    //   444: ifeq +12 -> 456
    //   447: aload_0
    //   448: invokevirtual 235	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:close	()V
    //   451: aload_0
    //   452: invokevirtual 277	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:handleConnectingFail	()V
    //   455: return
    //   456: aload_3
    //   457: athrow
    //   458: aload_0
    //   459: getfield 48	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:listener	Lfm/qingting/download/qtradiodownload/network/http/conn/HttpConnectionListener;
    //   462: ifnull -380 -> 82
    //   465: aload_0
    //   466: getfield 48	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:listener	Lfm/qingting/download/qtradiodownload/network/http/conn/HttpConnectionListener;
    //   469: bipush 103
    //   471: new 248	java/lang/StringBuilder
    //   474: dup
    //   475: invokespecial 249	java/lang/StringBuilder:<init>	()V
    //   478: iload 12
    //   480: invokevirtual 296	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   483: ldc 42
    //   485: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   488: invokevirtual 258	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   491: invokeinterface 275 3 0
    //   496: goto -414 -> 82
    //   499: astore 11
    //   501: aload 11
    //   503: invokevirtual 71	java/io/IOException:printStackTrace	()V
    //   506: invokestatic 246	fm/qingting/qtradio/log/LogModule:getInstance	()Lfm/qingting/qtradio/log/LogModule;
    //   509: ldc 19
    //   511: new 248	java/lang/StringBuilder
    //   514: dup
    //   515: invokespecial 249	java/lang/StringBuilder:<init>	()V
    //   518: ldc_w 298
    //   521: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   524: aload 11
    //   526: invokevirtual 284	java/io/IOException:getMessage	()Ljava/lang/String;
    //   529: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   532: invokevirtual 258	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   535: invokevirtual 261	fm/qingting/qtradio/log/LogModule:send	(Ljava/lang/String;Ljava/lang/String;)V
    //   538: invokestatic 266	fm/qingting/qtradio/logger/QTLogger:getInstance	()Lfm/qingting/qtradio/logger/QTLogger;
    //   541: new 248	java/lang/StringBuilder
    //   544: dup
    //   545: invokespecial 249	java/lang/StringBuilder:<init>	()V
    //   548: ldc_w 300
    //   551: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   554: aload 11
    //   556: invokevirtual 284	java/io/IOException:getMessage	()Ljava/lang/String;
    //   559: invokevirtual 255	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   562: invokevirtual 258	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   565: invokevirtual 271	fm/qingting/qtradio/logger/QTLogger:addDownloadExceptionLog	(Ljava/lang/String;)V
    //   568: aload_0
    //   569: getfield 48	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:listener	Lfm/qingting/download/qtradiodownload/network/http/conn/HttpConnectionListener;
    //   572: ifnull +15 -> 587
    //   575: aload_0
    //   576: getfield 48	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:listener	Lfm/qingting/download/qtradiodownload/network/http/conn/HttpConnectionListener;
    //   579: bipush 104
    //   581: aconst_null
    //   582: invokeinterface 275 3 0
    //   587: aload_0
    //   588: invokevirtual 235	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:close	()V
    //   591: aload_0
    //   592: invokevirtual 239	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:handleConnectionFinished	()V
    //   595: return
    //   596: astore 10
    //   598: aload_0
    //   599: getfield 48	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:listener	Lfm/qingting/download/qtradiodownload/network/http/conn/HttpConnectionListener;
    //   602: ifnull +15 -> 617
    //   605: aload_0
    //   606: getfield 48	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:listener	Lfm/qingting/download/qtradiodownload/network/http/conn/HttpConnectionListener;
    //   609: bipush 105
    //   611: aconst_null
    //   612: invokeinterface 275 3 0
    //   617: aload_0
    //   618: invokevirtual 235	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:close	()V
    //   621: aload_0
    //   622: invokevirtual 239	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:handleConnectionFinished	()V
    //   625: return
    //   626: astore 8
    //   628: iconst_0
    //   629: istore_1
    //   630: aload 8
    //   632: astore 9
    //   634: aload_0
    //   635: invokevirtual 235	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:close	()V
    //   638: iload_1
    //   639: ifne +7 -> 646
    //   642: aload_0
    //   643: invokevirtual 237	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:handlePart	()V
    //   646: aload_0
    //   647: invokevirtual 239	fm/qingting/download/qtradiodownload/network/http/conn/HttpConnection:handleConnectionFinished	()V
    //   650: aload 9
    //   652: athrow
    //   653: astore 9
    //   655: goto -21 -> 634
    //   658: astore_3
    //   659: goto -216 -> 443
    //
    // Exception table:
    //   from	to	target	type
    //   2	7	99	java/net/MalformedURLException
    //   2	7	199	java/io/IOException
    //   2	7	328	java/lang/Exception
    //   2	7	438	finally
    //   101	171	438	finally
    //   330	410	438	finally
    //   23	55	499	java/io/IOException
    //   55	64	499	java/io/IOException
    //   72	80	499	java/io/IOException
    //   458	496	499	java/io/IOException
    //   23	55	596	java/lang/Exception
    //   55	64	596	java/lang/Exception
    //   72	80	596	java/lang/Exception
    //   458	496	596	java/lang/Exception
    //   23	55	626	finally
    //   55	64	626	finally
    //   72	80	626	finally
    //   458	496	653	finally
    //   501	587	653	finally
    //   598	617	653	finally
    //   171	190	658	finally
    //   201	319	658	finally
    //   410	429	658	finally
  }

  protected void sendData(byte[] paramArrayOfByte)
    throws IOException
  {
    OutputStream localOutputStream = this.conn.getOutputStream();
    localOutputStream.write(paramArrayOfByte);
    localOutputStream.flush();
    localOutputStream.close();
  }

  public void setName(String paramString)
  {
    this.name = paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.download.qtradiodownload.network.http.conn.HttpConnection
 * JD-Core Version:    0.6.2
 */