package fm.qingting.qtradio.jd.data;

import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.utils.DeviceInfo;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.GZIPInputStream;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.json.JSONArray;
import org.json.JSONObject;

public class JDApi
{
  private static final String AD_URL = "http://bdsp.x.jd.com/adx/dragonfly";
  private static final int CONNECTIONS_MAX_PERROUTE = 15;
  private static final int CONNECTIONS_MAX_TOTAL = 15;
  private static final String HEADER_USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:22.0) Gecko/20100101 Firefox/22.0";
  private static final int SOCKET_BUFFER_SIZE = 16384;
  private static final int TIME_OUT_CONNECTION = 6000;
  private static final int TIME_OUT_CONNECTIONPOOL = 1000;
  private static final int TIME_OUT_SOCKET = 6000;
  private static ArrayList<CommodityInfo> mCommodityInfos;
  private static MainHandler mHandler;
  private static HttpClient mHttpClient;
  private static RequestParam mParam;

  private static void checkHttpClient()
  {
    if (mHttpClient == null)
    {
      BasicHttpParams localBasicHttpParams = new BasicHttpParams();
      HttpProtocolParams.setVersion(localBasicHttpParams, HttpVersion.HTTP_1_1);
      HttpProtocolParams.setContentCharset(localBasicHttpParams, "UTF-8");
      HttpProtocolParams.setUseExpectContinue(localBasicHttpParams, true);
      HttpProtocolParams.setUserAgent(localBasicHttpParams, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.8; rv:22.0) Gecko/20100101 Firefox/22.0");
      HttpConnectionParams.setStaleCheckingEnabled(localBasicHttpParams, false);
      HttpConnectionParams.setTcpNoDelay(localBasicHttpParams, true);
      HttpConnectionParams.setSocketBufferSize(localBasicHttpParams, 16384);
      HttpConnectionParams.setConnectionTimeout(localBasicHttpParams, 6000);
      HttpConnectionParams.setSoTimeout(localBasicHttpParams, 6000);
      ConnManagerParams.setMaxConnectionsPerRoute(localBasicHttpParams, new ConnPerRouteBean(15));
      ConnManagerParams.setMaxTotalConnections(localBasicHttpParams, 15);
      ConnManagerParams.setTimeout(localBasicHttpParams, 1000L);
      HttpClientParams.setRedirecting(localBasicHttpParams, false);
      SchemeRegistry localSchemeRegistry = new SchemeRegistry();
      localSchemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
      localSchemeRegistry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
      mHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(localBasicHttpParams, localSchemeRegistry), localBasicHttpParams);
      mHttpClient.getParams().setParameter("http.protocol.handle-redirects", Boolean.valueOf(true));
    }
  }

  private static String connect(HttpUriRequest paramHttpUriRequest)
    throws Exception
  {
    checkHttpClient();
    HttpResponse localHttpResponse = mHttpClient.execute(paramHttpUriRequest);
    InputStream localInputStream;
    if (localHttpResponse != null)
      if (localHttpResponse.getStatusLine().getStatusCode() == 200)
      {
        HttpEntity localHttpEntity = localHttpResponse.getEntity();
        if (localHttpEntity == null)
          break label184;
        localInputStream = localHttpEntity.getContent();
        Header localHeader = localHttpResponse.getFirstHeader("Content-Encoding");
        if ((localHeader == null) || (!localHeader.getValue().equalsIgnoreCase("gzip")))
          break label186;
      }
    label184: label186: for (Object localObject = new GZIPInputStream(localInputStream); ; localObject = localInputStream)
    {
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      byte[] arrayOfByte = new byte[1024];
      while (true)
      {
        int i = ((InputStream)localObject).read(arrayOfByte, 0, arrayOfByte.length);
        if (i == -1)
          break;
        localByteArrayOutputStream.write(arrayOfByte, 0, i);
      }
      String str = new String(localByteArrayOutputStream.toByteArray(), "UTF-8");
      localByteArrayOutputStream.close();
      ((InputStream)localObject).close();
      return str;
      MobclickAgent.onEvent(InfoManager.getInstance().getContext(), "jdfail", "recvError");
      return null;
    }
  }

  public static void feedback(final OnResponseListener paramOnResponseListener, String paramString)
  {
    if ((paramString == null) || (paramString.equalsIgnoreCase("")))
      return;
    if (mHandler == null)
      mHandler = new MainHandler();
    new Thread(new Runnable()
    {
      public void run()
      {
        if (JDApi.getRequest(this.val$url) == null);
        for (JDApi.ErrorCode localErrorCode = JDApi.ErrorCode.FAIL; ; localErrorCode = JDApi.ErrorCode.SUCCESS)
        {
          Response localResponse = new Response();
          localResponse.setListener(paramOnResponseListener);
          localResponse.setErrorCode(localErrorCode);
          JDApi.mHandler.sendMessage(JDApi.mHandler.obtainMessage(0, localResponse));
          return;
        }
      }
    }).start();
  }

  private static ErrorCode format(String paramString)
  {
    if (paramString == null)
      return ErrorCode.FAIL;
    try
    {
      if (mCommodityInfos == null)
        mCommodityInfos = new ArrayList();
      while (true)
      {
        JSONArray localJSONArray = new JSONObject(paramString).getJSONArray("data");
        for (int i = 0; i < localJSONArray.length(); i++)
        {
          CommodityInfo localCommodityInfo = new CommodityInfo();
          localCommodityInfo.fromJson(localJSONArray.getJSONObject(i));
          mCommodityInfos.add(localCommodityInfo);
        }
        mCommodityInfos.clear();
      }
    }
    catch (Exception localException)
    {
      return ErrorCode.FAIL;
    }
    ErrorCode localErrorCode = ErrorCode.SUCCESS;
    return localErrorCode;
  }

  private static String getRequest(String paramString)
  {
    if (paramString == null)
      return null;
    try
    {
      String str = connect(new HttpGet(paramString));
      return str;
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  private static String postRequest()
  {
    if (mParam == null)
      mParam = new RequestParam();
    mParam.setDeviceId(InfoManager.getInstance().getChangeUniqueId());
    if ((DeviceInfo.getChangeCnt() > 0) && (DeviceInfo.getChangeCnt() % 3 == 0))
      mParam.setGender("F");
    try
    {
      while (true)
      {
        HttpPost localHttpPost = new HttpPost("http://bdsp.x.jd.com/adx/dragonfly");
        StringEntity localStringEntity = new StringEntity(mParam.toString(), "UTF-8");
        localStringEntity.setContentEncoding("UTF-8");
        localStringEntity.setContentType("application/json");
        localHttpPost.setEntity(localStringEntity);
        String str2 = connect(localHttpPost);
        return str2;
        mParam.setGender("M");
      }
    }
    catch (Exception localException)
    {
      if (localException != null)
      {
        String str1 = String.valueOf(InfoManager.getInstance().getNetWorkType());
        if (localException.getMessage() != null)
          str1 = str1 + localException.getMessage();
        MobclickAgent.onEvent(InfoManager.getInstance().getContext(), "jdfail", str1);
      }
    }
    return null;
  }

  public static void request(OnResponseListener paramOnResponseListener)
  {
    if (mHandler == null)
      mHandler = new MainHandler();
    MobclickAgent.onEvent(InfoManager.getInstance().getContext(), "jdrequest");
    new Thread(new Runnable()
    {
      public void run()
      {
        JDApi.ErrorCode localErrorCode = JDApi.format(JDApi.access$000());
        Response localResponse = new Response();
        localResponse.setData(JDApi.mCommodityInfos);
        localResponse.setErrorCode(localErrorCode);
        localResponse.setListener(this.val$listener);
        JDApi.mHandler.sendMessage(JDApi.mHandler.obtainMessage(0, localResponse));
      }
    }).start();
  }

  public static void sendEventMessage(String paramString)
  {
    MobclickAgent.onEvent(InfoManager.getInstance().getContext(), paramString);
    TCAgent.onEvent(InfoManager.getInstance().getContext(), paramString);
  }

  public static enum ErrorCode
  {
    static
    {
      FAIL = new ErrorCode("FAIL", 1);
      ErrorCode[] arrayOfErrorCode = new ErrorCode[2];
      arrayOfErrorCode[0] = SUCCESS;
      arrayOfErrorCode[1] = FAIL;
    }
  }

  public class EventType
  {
    public static final String JDAD_CLICK = "JDADClick";
    public static final String JDAD_CLOSE = "JDADCLOSE";
    public static final String JDAD_EXPLOSURE = "JDADExplosure";
    public static final String JDAD_NOINTERSTING = "JDADNoIntersting";

    public EventType()
    {
    }
  }

  public static abstract interface OnResponseListener
  {
    public abstract void onResponse(Response paramResponse);
  }

  public class SwitchType
  {
    public static final String JDAD_CHANNEL = "JDADChannel";
    public static final String JDAD_POSITION = "JDADPosition";

    public SwitchType()
    {
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.jd.data.JDApi
 * JD-Core Version:    0.6.2
 */