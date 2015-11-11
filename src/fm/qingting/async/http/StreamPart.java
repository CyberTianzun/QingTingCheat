package fm.qingting.async.http;

import fm.qingting.async.DataSink;
import fm.qingting.async.Util;
import fm.qingting.async.callback.CompletedCallback;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.http.NameValuePair;

public abstract class StreamPart extends Part
{
  public StreamPart(String paramString, int paramInt, List<NameValuePair> paramList)
  {
    super(paramString, paramInt, paramList);
  }

  protected abstract InputStream getInputStream()
    throws IOException;

  public void write(DataSink paramDataSink, CompletedCallback paramCompletedCallback)
  {
    try
    {
      Util.pump(getInputStream(), paramDataSink, paramCompletedCallback);
      return;
    }
    catch (Exception localException)
    {
      paramCompletedCallback.onCompleted(localException);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.StreamPart
 * JD-Core Version:    0.6.2
 */