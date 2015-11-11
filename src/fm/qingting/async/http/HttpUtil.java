package fm.qingting.async.http;

import fm.qingting.async.AsyncServer;
import fm.qingting.async.DataEmitter;
import fm.qingting.async.FilteredDataEmitter;
import fm.qingting.async.callback.CompletedCallback;
import fm.qingting.async.http.filter.ChunkedInputFilter;
import fm.qingting.async.http.filter.ContentLengthFilter;
import fm.qingting.async.http.filter.GZIPInputFilter;
import fm.qingting.async.http.filter.InflaterInputFilter;
import fm.qingting.async.http.libcore.RawHeaders;
import fm.qingting.async.http.server.UnknownRequestBody;

public class HttpUtil
{
  public static AsyncHttpRequestBody getBody(DataEmitter paramDataEmitter, CompletedCallback paramCompletedCallback, RawHeaders paramRawHeaders)
  {
    int i = 0;
    String str1 = paramRawHeaders.get("Content-Type");
    if (str1 != null)
    {
      String[] arrayOfString = str1.split(";");
      for (int j = 0; j < arrayOfString.length; j++)
        arrayOfString[j] = arrayOfString[j].trim();
      int k = arrayOfString.length;
      while (i < k)
      {
        String str2 = arrayOfString[i];
        if ("application/x-www-form-urlencoded".equals(str2))
          return new UrlEncodedFormBody();
        if ("application/json".equals(str2))
          return new JSONObjectBody();
        if ("text/plain".equals(str2))
          return new StringBody();
        if ("multipart/form-data".equals(str2))
          return new MultipartFormDataBody(str1, arrayOfString);
        i++;
      }
    }
    return new UnknownRequestBody(str1);
  }

  public static DataEmitter getBodyDecoder(DataEmitter paramDataEmitter, RawHeaders paramRawHeaders, boolean paramBoolean)
  {
    try
    {
      int j = Integer.parseInt(paramRawHeaders.get("Content-Length"));
      i = j;
      if (-1 != i)
        if (i < 0)
        {
          EndEmitter localEndEmitter3 = EndEmitter.create(paramDataEmitter.getServer(), new Exception("not using chunked encoding, and no content-length found."));
          localEndEmitter3.setDataEmitter(paramDataEmitter);
          paramDataEmitter = localEndEmitter3;
          return paramDataEmitter;
        }
    }
    catch (Exception localException)
    {
      do
      {
        int i;
        while (true)
          i = -1;
        if (i == 0)
        {
          EndEmitter localEndEmitter2 = EndEmitter.create(paramDataEmitter.getServer(), null);
          localEndEmitter2.setDataEmitter(paramDataEmitter);
          return localEndEmitter2;
        }
        ContentLengthFilter localContentLengthFilter = new ContentLengthFilter(i);
        localContentLengthFilter.setDataEmitter(paramDataEmitter);
        paramDataEmitter = localContentLengthFilter;
        while ("gzip".equals(paramRawHeaders.get("Content-Encoding")))
        {
          GZIPInputFilter localGZIPInputFilter = new GZIPInputFilter();
          localGZIPInputFilter.setDataEmitter(paramDataEmitter);
          return localGZIPInputFilter;
          if ("chunked".equalsIgnoreCase(paramRawHeaders.get("Transfer-Encoding")))
          {
            ChunkedInputFilter localChunkedInputFilter = new ChunkedInputFilter();
            localChunkedInputFilter.setDataEmitter(paramDataEmitter);
            paramDataEmitter = localChunkedInputFilter;
          }
          else if ((paramBoolean) || (paramRawHeaders.getStatusLine().contains("HTTP/1.1")))
          {
            EndEmitter localEndEmitter1 = EndEmitter.create(paramDataEmitter.getServer(), null);
            localEndEmitter1.setDataEmitter(paramDataEmitter);
            return localEndEmitter1;
          }
        }
      }
      while (!"deflate".equals(paramRawHeaders.get("Content-Encoding")));
      InflaterInputFilter localInflaterInputFilter = new InflaterInputFilter();
      localInflaterInputFilter.setDataEmitter(paramDataEmitter);
      return localInflaterInputFilter;
    }
  }

  private static class EndEmitter extends FilteredDataEmitter
  {
    public static EndEmitter create(AsyncServer paramAsyncServer, final Exception paramException)
    {
      EndEmitter localEndEmitter = new EndEmitter();
      paramAsyncServer.post(new Runnable()
      {
        public void run()
        {
          this.val$ret.report(paramException);
        }
      });
      return localEndEmitter;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.HttpUtil
 * JD-Core Version:    0.6.2
 */