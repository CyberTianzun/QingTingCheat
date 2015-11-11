package fm.qingting.qtradio.logger;

import android.util.Log;
import com.lmax.disruptor.EventHandler;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class LogEventHandler
  implements EventHandler<ValueEvent>
{
  private static final String _content_ = "content";
  private static final String _log_thread_id_ = "_log_thread_id_12432543jndslnfdsj";
  private static final String _type_ = "type";
  private static boolean isStart = false;

  public void onEvent(ValueEvent paramValueEvent, long paramLong, boolean paramBoolean)
    throws Exception
  {
    if (!isStart)
    {
      isStart = true;
      System.setProperty("_log_thread_id_12432543jndslnfdsj", String.valueOf(Thread.currentThread().getId()));
    }
    String str1 = paramValueEvent.getType();
    String str2 = paramValueEvent.getValue();
    DefaultHttpClient localDefaultHttpClient = new DefaultHttpClient();
    try
    {
      HttpPost localHttpPost = new HttpPost(ConfigHelper.getPostURL());
      ArrayList localArrayList = new ArrayList();
      localArrayList.add(new BasicNameValuePair("type", str1));
      localArrayList.add(new BasicNameValuePair("content", str2));
      localHttpPost.setEntity(new UrlEncodedFormEntity(localArrayList, "UTF-8"));
      HttpResponse localHttpResponse = localDefaultHttpClient.execute(localHttpPost);
      int i = localHttpResponse.getStatusLine().getStatusCode();
      String str3 = localHttpResponse.getStatusLine().getReasonPhrase();
      Log.w("QTLogger", "type:" + str1 + ",Respnse: " + i + " " + str3);
      EntityUtils.toString(localHttpResponse.getEntity(), "UTF-8").substring(1);
      return;
    }
    catch (Exception localException)
    {
      localException.printStackTrace();
      return;
    }
    catch (OutOfMemoryError localOutOfMemoryError)
    {
    }
    finally
    {
      localDefaultHttpClient.getConnectionManager().shutdown();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.logger.LogEventHandler
 * JD-Core Version:    0.6.2
 */