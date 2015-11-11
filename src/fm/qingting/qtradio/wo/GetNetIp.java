package fm.qingting.qtradio.wo;

import android.os.AsyncTask;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetNetIp extends AsyncTask<Object, Void, String>
{
  IHttpAsyncTaskListener callback;
  private Object executeParams = null;

  public GetNetIp(IHttpAsyncTaskListener paramIHttpAsyncTaskListener)
  {
    this.callback = paramIHttpAsyncTaskListener;
  }

  public static String getIp()
  {
    ArrayList localArrayList1 = retrieveIps(getRequest("http://iframe.ip138.com/ic.asp"));
    if (localArrayList1.size() > 0)
      return (String)localArrayList1.get(0);
    ArrayList localArrayList2 = retrieveIps(getRequest("http://httpbin.org/ip"));
    if (localArrayList2.size() > 0)
      return (String)localArrayList2.get(0);
    ArrayList localArrayList3 = retrieveIps(getRequest("http://www.net.cn/static/customercare/yourip.asp"));
    if (localArrayList3.size() > 0)
      return (String)localArrayList3.get(0);
    return "";
  }

  protected static String getRequest(String paramString)
  {
    if (paramString == null)
      return "";
    if (paramString.equals(""))
      return "";
    try
    {
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL(paramString).openConnection();
      if (localHttpURLConnection.getResponseCode() == 200)
      {
        localInputStream = localHttpURLConnection.getInputStream();
        BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(localInputStream, "utf-8"));
        localStringBuilder = new StringBuilder();
        while (true)
        {
          String str1 = localBufferedReader.readLine();
          if (str1 == null)
            break;
          localStringBuilder.append(str1 + "\n");
        }
      }
    }
    catch (MalformedURLException localMalformedURLException)
    {
      InputStream localInputStream;
      StringBuilder localStringBuilder;
      localMalformedURLException.printStackTrace();
      return null;
      String str2 = localStringBuilder.toString();
      localInputStream.close();
      return str2;
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }

  protected static ArrayList<String> retrieveIps(String paramString)
  {
    ArrayList localArrayList;
    try
    {
      Matcher localMatcher = Pattern.compile("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}").matcher(paramString);
      localArrayList = new ArrayList();
      while (localMatcher.find())
        localArrayList.add(localMatcher.group());
    }
    catch (Exception localException)
    {
      localArrayList = new ArrayList();
    }
    return localArrayList;
  }

  protected String doInBackground(Object[] paramArrayOfObject)
  {
    this.executeParams = paramArrayOfObject[0];
    return getIp();
  }

  protected void onPostExecute(String paramString)
  {
    this.callback.onGetResult(this.executeParams, paramString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.wo.GetNetIp
 * JD-Core Version:    0.6.2
 */