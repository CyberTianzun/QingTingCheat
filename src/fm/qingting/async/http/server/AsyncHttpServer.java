package fm.qingting.async.http.server;

import android.content.Context;
import fm.qingting.async.AsyncSSLSocketWrapper;
import fm.qingting.async.AsyncServer;
import fm.qingting.async.AsyncServerSocket;
import fm.qingting.async.AsyncSocket;
import fm.qingting.async.Util;
import fm.qingting.async.callback.CompletedCallback;
import fm.qingting.async.callback.ListenCallback;
import fm.qingting.async.http.AsyncHttpRequestBody;
import fm.qingting.async.http.Multimap;
import fm.qingting.async.http.WebSocket;
import fm.qingting.async.http.WebSocketImpl;
import fm.qingting.async.http.libcore.RawHeaders;
import fm.qingting.async.http.libcore.RequestHeaders;
import fm.qingting.async.http.libcore.ResponseHeaders;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.net.ssl.SSLContext;

public class AsyncHttpServer
{
  private static Hashtable<Integer, String> mCodes;
  static Hashtable<String, String> mContentTypes;
  Hashtable<String, ArrayList<Pair>> mActions = new Hashtable();
  CompletedCallback mCompletedCallback;
  ListenCallback mListenCallback = new ListenCallback()
  {
    public void onAccepted(final AsyncSocket paramAnonymousAsyncSocket)
    {
      new AsyncHttpServerRequestImpl()
      {
        String fullPath;
        boolean hasContinued;
        AsyncHttpServer.Pair match;
        String path;
        boolean requestComplete;
        AsyncHttpServerResponseImpl res;
        boolean responseComplete;

        private void handleOnCompleted()
        {
          if ((this.requestComplete) && (this.responseComplete))
            AsyncHttpServer.1.this.onAccepted(paramAnonymousAsyncSocket);
        }

        public String getPath()
        {
          return this.path;
        }

        public Multimap getQuery()
        {
          String[] arrayOfString = this.fullPath.split("\\?", 2);
          if (arrayOfString.length < 2)
            return new Multimap();
          return Multimap.parseQuery(arrayOfString[1]);
        }

        public void onCompleted(Exception paramAnonymous2Exception)
        {
          if (this.res.getHeaders().getHeaders().getResponseCode() == 101);
          do
          {
            return;
            this.requestComplete = true;
            super.onCompleted(paramAnonymous2Exception);
            this.mSocket.setDataCallback(null);
            this.mSocket.pause();
            handleOnCompleted();
          }
          while (!getBody().readFullyOnRequest());
          this.match.callback.onRequest(this, this.res);
        }

        protected void onHeadersReceived()
        {
          RawHeaders localRawHeaders = getRawHeaders();
          if ((!this.hasContinued) && ("100-continue".equals(localRawHeaders.get("Expect"))))
          {
            pause();
            Util.writeAll(this.mSocket, "HTTP/1.1 100 Continue\r\n".getBytes(), new CompletedCallback()
            {
              public void onCompleted(Exception paramAnonymous3Exception)
              {
                AsyncHttpServer.1.1.this.resume();
                if (paramAnonymous3Exception != null)
                {
                  AsyncHttpServer.1.1.this.report(paramAnonymous3Exception);
                  return;
                }
                AsyncHttpServer.1.1.this.hasContinued = true;
                AsyncHttpServer.1.1.this.onHeadersReceived();
              }
            });
          }
          do
          {
            return;
            String[] arrayOfString = localRawHeaders.getStatusLine().split(" ");
            this.fullPath = arrayOfString[1];
            this.path = this.fullPath.split("\\?")[0];
            this.method = arrayOfString[0];
            synchronized (AsyncHttpServer.this.mActions)
            {
              ArrayList localArrayList = (ArrayList)AsyncHttpServer.this.mActions.get(this.method);
              if (localArrayList != null)
              {
                Iterator localIterator = localArrayList.iterator();
                while (localIterator.hasNext())
                {
                  AsyncHttpServer.Pair localPair = (AsyncHttpServer.Pair)localIterator.next();
                  Matcher localMatcher = localPair.regex.matcher(this.path);
                  if (localMatcher.matches())
                  {
                    this.mMatcher = localMatcher;
                    this.match = localPair;
                  }
                }
              }
              this.res = new AsyncHttpServerResponseImpl(paramAnonymousAsyncSocket, this)
              {
                protected void onEnd()
                {
                  this.mSocket.setEndCallback(null);
                  AsyncHttpServer.1.1.this.responseComplete = true;
                  AsyncHttpServer.1.1.this.handleOnCompleted();
                }
              };
              AsyncHttpServer.this.onRequest(this, this.res);
              if (this.match == null)
              {
                this.res.responseCode(404);
                this.res.end();
                return;
              }
            }
            if (!getBody().readFullyOnRequest())
            {
              this.match.callback.onRequest(this, this.res);
              return;
            }
          }
          while (!this.requestComplete);
          this.match.callback.onRequest(this, this.res);
        }
      }
      .setSocket(paramAnonymousAsyncSocket);
      paramAnonymousAsyncSocket.resume();
    }

    public void onCompleted(Exception paramAnonymousException)
    {
      AsyncHttpServer.this.report(paramAnonymousException);
    }

    public void onListening(AsyncServerSocket paramAnonymousAsyncServerSocket)
    {
      AsyncHttpServer.this.mListeners.add(paramAnonymousAsyncServerSocket);
    }
  };
  ArrayList<AsyncServerSocket> mListeners = new ArrayList();

  static
  {
    if (!AsyncHttpServer.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      mContentTypes = new Hashtable();
      mCodes = new Hashtable();
      mCodes.put(Integer.valueOf(200), "OK");
      mCodes.put(Integer.valueOf(101), "Switching Protocols");
      mCodes.put(Integer.valueOf(404), "Not Found");
      return;
    }
  }

  public AsyncHttpServer()
  {
    mContentTypes.put("js", "application/javascript");
    mContentTypes.put("json", "application/json");
    mContentTypes.put("png", "image/png");
    mContentTypes.put("jpg", "image/jpeg");
    mContentTypes.put("html", "text/html");
    mContentTypes.put("css", "text/css");
    mContentTypes.put("mp4", "video/mp4");
  }

  public static InputStream getAssetStream(Context paramContext, String paramString)
  {
    String str1 = paramContext.getPackageResourcePath();
    String str2 = "assets/" + paramString;
    try
    {
      ZipFile localZipFile = new ZipFile(str1);
      Enumeration localEnumeration = localZipFile.entries();
      while (localEnumeration.hasMoreElements())
      {
        ZipEntry localZipEntry = (ZipEntry)localEnumeration.nextElement();
        if (localZipEntry.getName().equals(str2))
        {
          InputStream localInputStream = localZipFile.getInputStream(localZipEntry);
          return localInputStream;
        }
      }
    }
    catch (Exception localException)
    {
    }
    return null;
  }

  public static String getContentType(String paramString)
  {
    int i = paramString.lastIndexOf(".");
    if (i != -1)
    {
      String str1 = paramString.substring(i + 1);
      String str2 = (String)mContentTypes.get(str1);
      if (str2 != null)
        return str2;
    }
    return "text/plain";
  }

  public static String getResponseCodeDescription(int paramInt)
  {
    String str = (String)mCodes.get(Integer.valueOf(paramInt));
    if (str == null)
      str = "Unknown";
    return str;
  }

  private void report(Exception paramException)
  {
    if (this.mCompletedCallback != null)
      this.mCompletedCallback.onCompleted(paramException);
  }

  public void addAction(String paramString1, String paramString2, HttpServerRequestCallback paramHttpServerRequestCallback)
  {
    Pair localPair = new Pair(null);
    localPair.regex = Pattern.compile("^" + paramString2);
    localPair.callback = paramHttpServerRequestCallback;
    synchronized (this.mActions)
    {
      ArrayList localArrayList = (ArrayList)this.mActions.get(paramString1);
      if (localArrayList == null)
      {
        localArrayList = new ArrayList();
        this.mActions.put(paramString1, localArrayList);
      }
      localArrayList.add(localPair);
      return;
    }
  }

  public void directory(Context paramContext, String paramString1, final String paramString2)
  {
    addAction("GET", paramString1, new HttpServerRequestCallback()
    {
      public void onRequest(AsyncHttpServerRequest paramAnonymousAsyncHttpServerRequest, final AsyncHttpServerResponse paramAnonymousAsyncHttpServerResponse)
      {
        String str = paramAnonymousAsyncHttpServerRequest.getMatcher().replaceAll("");
        InputStream localInputStream = AsyncHttpServer.getAssetStream(this.val$context, paramString2 + str);
        if (localInputStream == null)
        {
          paramAnonymousAsyncHttpServerResponse.responseCode(404);
          paramAnonymousAsyncHttpServerResponse.end();
          return;
        }
        paramAnonymousAsyncHttpServerResponse.responseCode(200);
        paramAnonymousAsyncHttpServerResponse.getHeaders().getHeaders().add("Content-Type", AsyncHttpServer.getContentType(str));
        Util.pump(localInputStream, paramAnonymousAsyncHttpServerResponse, new CompletedCallback()
        {
          public void onCompleted(Exception paramAnonymous2Exception)
          {
            paramAnonymousAsyncHttpServerResponse.end();
          }
        });
      }
    });
  }

  public void directory(String paramString, File paramFile)
  {
    directory(paramString, paramFile, false);
  }

  public void directory(String paramString, final File paramFile, final boolean paramBoolean)
  {
    assert (paramFile.isDirectory());
    addAction("GET", paramString, new HttpServerRequestCallback()
    {
      public void onRequest(AsyncHttpServerRequest paramAnonymousAsyncHttpServerRequest, final AsyncHttpServerResponse paramAnonymousAsyncHttpServerResponse)
      {
        String str = paramAnonymousAsyncHttpServerRequest.getMatcher().replaceAll("");
        File localFile1 = new File(paramFile, str);
        if ((localFile1.isDirectory()) && (paramBoolean))
        {
          ArrayList localArrayList1 = new ArrayList();
          ArrayList localArrayList2 = new ArrayList();
          File[] arrayOfFile = localFile1.listFiles();
          int i = arrayOfFile.length;
          int j = 0;
          if (j < i)
          {
            File localFile2 = arrayOfFile[j];
            if (localFile2.isDirectory())
              localArrayList1.add(localFile2);
            while (true)
            {
              j++;
              break;
              localArrayList2.add(localFile2);
            }
          }
          Comparator local1 = new Comparator()
          {
            public int compare(File paramAnonymous2File1, File paramAnonymous2File2)
            {
              return paramAnonymous2File1.getName().compareTo(paramAnonymous2File2.getName());
            }
          };
          Collections.sort(localArrayList1, local1);
          Collections.sort(localArrayList2, local1);
          localArrayList2.addAll(0, localArrayList1);
          return;
        }
        if (!localFile1.isFile())
        {
          paramAnonymousAsyncHttpServerResponse.responseCode(404);
          paramAnonymousAsyncHttpServerResponse.end();
          return;
        }
        try
        {
          FileInputStream localFileInputStream = new FileInputStream(localFile1);
          paramAnonymousAsyncHttpServerResponse.responseCode(200);
          Util.pump(localFileInputStream, paramAnonymousAsyncHttpServerResponse, new CompletedCallback()
          {
            public void onCompleted(Exception paramAnonymous2Exception)
            {
              paramAnonymousAsyncHttpServerResponse.end();
            }
          });
          return;
        }
        catch (Exception localException)
        {
          paramAnonymousAsyncHttpServerResponse.responseCode(404);
          paramAnonymousAsyncHttpServerResponse.end();
        }
      }
    });
  }

  public void get(String paramString, HttpServerRequestCallback paramHttpServerRequestCallback)
  {
    addAction("GET", paramString, paramHttpServerRequestCallback);
  }

  public CompletedCallback getErrorCallback()
  {
    return this.mCompletedCallback;
  }

  public ListenCallback getListenCallback()
  {
    return this.mListenCallback;
  }

  public void listen(int paramInt)
  {
    listen(AsyncServer.getDefault(), paramInt);
  }

  public void listen(AsyncServer paramAsyncServer, int paramInt)
  {
    paramAsyncServer.listen(null, paramInt, this.mListenCallback);
  }

  public void listenSecure(final int paramInt, final SSLContext paramSSLContext)
  {
    AsyncServer.getDefault().listen(null, paramInt, new ListenCallback()
    {
      public void onAccepted(AsyncSocket paramAnonymousAsyncSocket)
      {
        AsyncSSLSocketWrapper localAsyncSSLSocketWrapper = new AsyncSSLSocketWrapper(paramAnonymousAsyncSocket, null, paramInt, paramSSLContext, null, false);
        AsyncHttpServer.this.mListenCallback.onAccepted(localAsyncSSLSocketWrapper);
      }

      public void onCompleted(Exception paramAnonymousException)
      {
        AsyncHttpServer.this.mListenCallback.onCompleted(paramAnonymousException);
      }

      public void onListening(AsyncServerSocket paramAnonymousAsyncServerSocket)
      {
        AsyncHttpServer.this.mListenCallback.onListening(paramAnonymousAsyncServerSocket);
      }
    });
  }

  protected void onRequest(AsyncHttpServerRequest paramAsyncHttpServerRequest, AsyncHttpServerResponse paramAsyncHttpServerResponse)
  {
  }

  public void post(String paramString, HttpServerRequestCallback paramHttpServerRequestCallback)
  {
    addAction("POST", paramString, paramHttpServerRequestCallback);
  }

  public void setErrorCallback(CompletedCallback paramCompletedCallback)
  {
    this.mCompletedCallback = paramCompletedCallback;
  }

  public void stop()
  {
    if (this.mListeners != null)
    {
      Iterator localIterator = this.mListeners.iterator();
      while (localIterator.hasNext())
        ((AsyncServerSocket)localIterator.next()).stop();
    }
  }

  public void websocket(String paramString, final WebSocketRequestCallback paramWebSocketRequestCallback)
  {
    get(paramString, new HttpServerRequestCallback()
    {
      public void onRequest(AsyncHttpServerRequest paramAnonymousAsyncHttpServerRequest, AsyncHttpServerResponse paramAnonymousAsyncHttpServerResponse)
      {
        String str = paramAnonymousAsyncHttpServerRequest.getHeaders().getHeaders().get("Connection");
        int i = 0;
        String[] arrayOfString;
        int j;
        if (str != null)
        {
          arrayOfString = str.split(",");
          j = arrayOfString.length;
        }
        for (int k = 0; ; k++)
        {
          i = 0;
          if (k < j)
          {
            if ("Upgrade".equalsIgnoreCase(arrayOfString[k].trim()))
              i = 1;
          }
          else
          {
            if (("websocket".equals(paramAnonymousAsyncHttpServerRequest.getHeaders().getHeaders().get("Upgrade"))) && (i != 0))
              break;
            paramAnonymousAsyncHttpServerResponse.responseCode(404);
            paramAnonymousAsyncHttpServerResponse.end();
            return;
          }
        }
        paramWebSocketRequestCallback.onConnected(new WebSocketImpl(paramAnonymousAsyncHttpServerRequest, paramAnonymousAsyncHttpServerResponse), paramAnonymousAsyncHttpServerRequest.getHeaders());
      }
    });
  }

  private static class Pair
  {
    HttpServerRequestCallback callback;
    Pattern regex;
  }

  public static abstract interface WebSocketRequestCallback
  {
    public abstract void onConnected(WebSocket paramWebSocket, RequestHeaders paramRequestHeaders);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.server.AsyncHttpServer
 * JD-Core Version:    0.6.2
 */