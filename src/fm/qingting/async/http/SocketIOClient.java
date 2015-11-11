package fm.qingting.async.http;

import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import fm.qingting.async.AsyncServer;
import fm.qingting.async.NullDataCallback;
import fm.qingting.async.callback.CompletedCallback;
import fm.qingting.async.future.Future;
import fm.qingting.async.future.SimpleFuture;
import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import org.json.JSONArray;
import org.json.JSONObject;

public class SocketIOClient
{
  CompletedCallback closedCallback;
  boolean connected;
  boolean disconnected;
  EventCallback eventCallback;
  Handler handler;
  int heartbeat;
  AsyncHttpClient httpClient;
  JSONCallback jsonCallback;
  String sessionUrl;
  StringCallback stringCallback;
  WebSocket webSocket;

  private SocketIOClient(Handler paramHandler, int paramInt, String paramString, AsyncHttpClient paramAsyncHttpClient)
  {
    this.handler = paramHandler;
    this.heartbeat = paramInt;
    this.sessionUrl = paramString;
    this.httpClient = paramAsyncHttpClient;
  }

  private void attach(final SocketIOConnectCallback paramSocketIOConnectCallback, final FutureImpl paramFutureImpl)
  {
    this.webSocket.setDataCallback(new NullDataCallback());
    this.webSocket.setClosedCallback(new CompletedCallback()
    {
      public void onCompleted(final Exception paramAnonymousException)
      {
        final boolean bool = SocketIOClient.this.disconnected;
        SocketIOClient.this.disconnected = true;
        SocketIOClient.this.webSocket = null;
        Runnable local1 = new Runnable()
        {
          public void run()
          {
            if (!SocketIOClient.this.connected)
            {
              localSocketIOConnectCallback = SocketIOClient.6.this.val$callback;
              if (paramAnonymousException == null)
              {
                localException2 = new Exception("connection failed");
                localSocketIOConnectCallback.onConnectCompleted(localException2, null);
              }
            }
            while ((bool) || (SocketIOClient.this.closedCallback == null))
              while (true)
              {
                SocketIOClient.SocketIOConnectCallback localSocketIOConnectCallback;
                return;
                Exception localException2 = paramAnonymousException;
              }
            CompletedCallback localCompletedCallback = SocketIOClient.this.closedCallback;
            if (paramAnonymousException == null);
            for (Exception localException1 = new Exception("connection failed"); ; localException1 = paramAnonymousException)
            {
              localCompletedCallback.onCompleted(localException1);
              return;
            }
          }
        };
        if (SocketIOClient.this.handler != null)
        {
          AsyncServer.post(SocketIOClient.this.handler, local1);
          return;
        }
        local1.run();
      }
    });
    this.webSocket.setStringCallback(new WebSocket.StringCallback()
    {
      public void onStringAvailable(String paramAnonymousString)
      {
        try
        {
          arrayOfString = paramAnonymousString.split(":", 4);
          switch (Integer.parseInt(arrayOfString[0]))
          {
          default:
            throw new Exception("unknown code");
          case 6:
          case 8:
          case 0:
          case 1:
          case 2:
          case 3:
          case 4:
          case 5:
          case 7:
          }
        }
        catch (Exception localException)
        {
          String[] arrayOfString;
          SocketIOClient.this.webSocket.close();
          if (!SocketIOClient.this.connected)
            SocketIOClient.reportError(paramFutureImpl, SocketIOClient.this.handler, paramSocketIOConnectCallback, localException);
          do
          {
            final String str2;
            final JSONArray localJSONArray;
            do
            {
              final JSONObject localJSONObject2;
              do
              {
                final String str5;
                do
                {
                  do
                  {
                    return;
                    if (!SocketIOClient.this.connected)
                      throw new Exception("received disconnect before client connect");
                    SocketIOClient.this.disconnected = true;
                    SocketIOClient.this.webSocket.close();
                  }
                  while (SocketIOClient.this.closedCallback == null);
                  if (SocketIOClient.this.handler != null)
                  {
                    AsyncServer.post(SocketIOClient.this.handler, new Runnable()
                    {
                      public void run()
                      {
                        SocketIOClient.this.closedCallback.onCompleted(null);
                      }
                    });
                    return;
                  }
                  SocketIOClient.this.closedCallback.onCompleted(null);
                  return;
                  if (SocketIOClient.this.connected)
                    throw new Exception("received duplicate connect event");
                  if (!paramFutureImpl.setComplete(SocketIOClient.this))
                    throw new Exception("request canceled");
                  SocketIOClient.this.connected = true;
                  SocketIOClient.this.setupHeartbeat();
                  paramSocketIOConnectCallback.onConnectCompleted(null, SocketIOClient.this);
                  return;
                  SocketIOClient.this.webSocket.send("2::");
                  return;
                  if (!SocketIOClient.this.connected)
                    throw new Exception("received message before client connect");
                  String str4 = arrayOfString[1];
                  str5 = arrayOfString[3];
                  if (!"".equals(str4))
                    SocketIOClient.this.webSocket.send(String.format("6:::%s", new Object[] { str4 }));
                }
                while (SocketIOClient.this.stringCallback == null);
                if (SocketIOClient.this.handler != null)
                {
                  AsyncServer.post(SocketIOClient.this.handler, new Runnable()
                  {
                    public void run()
                    {
                      SocketIOClient.this.stringCallback.onString(str5);
                    }
                  });
                  return;
                }
                SocketIOClient.this.stringCallback.onString(str5);
                return;
                if (!SocketIOClient.this.connected)
                  throw new Exception("received message before client connect");
                String str3 = arrayOfString[1];
                localJSONObject2 = new JSONObject(arrayOfString[3]);
                if (!"".equals(str3))
                  SocketIOClient.this.webSocket.send(String.format("6:::%s", new Object[] { str3 }));
              }
              while (SocketIOClient.this.jsonCallback == null);
              if (SocketIOClient.this.handler != null)
              {
                AsyncServer.post(SocketIOClient.this.handler, new Runnable()
                {
                  public void run()
                  {
                    SocketIOClient.this.jsonCallback.onJSON(localJSONObject2);
                  }
                });
                return;
              }
              SocketIOClient.this.jsonCallback.onJSON(localJSONObject2);
              return;
              if (!SocketIOClient.this.connected)
                throw new Exception("received message before client connect");
              String str1 = arrayOfString[1];
              JSONObject localJSONObject1 = new JSONObject(arrayOfString[3]);
              str2 = localJSONObject1.getString("name");
              localJSONArray = localJSONObject1.optJSONArray("args");
              if (!"".equals(str1))
                SocketIOClient.this.webSocket.send(String.format("6:::%s", new Object[] { str1 }));
            }
            while (SocketIOClient.this.eventCallback == null);
            if (SocketIOClient.this.handler != null)
            {
              AsyncServer.post(SocketIOClient.this.handler, new Runnable()
              {
                public void run()
                {
                  SocketIOClient.this.eventCallback.onEvent(str2, localJSONArray);
                }
              });
              return;
            }
            SocketIOClient.this.eventCallback.onEvent(str2, localJSONArray);
            return;
            throw new Exception(paramAnonymousString);
            SocketIOClient.this.disconnected = true;
          }
          while (SocketIOClient.this.closedCallback == null);
          SocketIOClient.this.closedCallback.onCompleted(localException);
        }
      }
    });
  }

  public static Future<SocketIOClient> connect(final AsyncHttpClient paramAsyncHttpClient, final SocketIORequest paramSocketIORequest, final SocketIOConnectCallback paramSocketIOConnectCallback)
  {
    if (Looper.myLooper() == null);
    for (final Handler localHandler = null; ; localHandler = new Handler())
    {
      FutureImpl localFutureImpl = new FutureImpl(null);
      paramSocketIORequest.setHandler(null);
      localFutureImpl.setParent(paramAsyncHttpClient.executeString(paramSocketIORequest, new AsyncHttpClient.StringCallback()
      {
        public void onCompleted(Exception paramAnonymousException, AsyncHttpResponse paramAnonymousAsyncHttpResponse, String paramAnonymousString)
        {
          if (paramAnonymousException != null)
          {
            SocketIOClient.reportError(this.val$ret, localHandler, paramSocketIOConnectCallback, paramAnonymousException);
            return;
          }
          String str1;
          int i;
          try
          {
            String[] arrayOfString = paramAnonymousString.split(":");
            str1 = arrayOfString[0];
            boolean bool = "".equals(arrayOfString[1]);
            i = 0;
            if (!bool)
              i = 1000 * (Integer.parseInt(arrayOfString[1]) / 2);
            if (!new HashSet(Arrays.asList(arrayOfString[3].split(","))).contains("websocket"))
              throw new Exception("websocket not supported");
          }
          catch (Exception localException)
          {
            SocketIOClient.reportError(this.val$ret, localHandler, paramSocketIOConnectCallback, localException);
            return;
          }
          String str2 = paramSocketIORequest.getUri().toString() + "websocket/" + str1 + "/";
          new SocketIOClient(localHandler, i, str2, paramAsyncHttpClient, null).reconnect(paramSocketIOConnectCallback, this.val$ret);
        }
      }));
      return localFutureImpl;
    }
  }

  public static Future<SocketIOClient> connect(AsyncHttpClient paramAsyncHttpClient, String paramString, SocketIOConnectCallback paramSocketIOConnectCallback)
  {
    return connect(paramAsyncHttpClient, new SocketIORequest(paramString), paramSocketIOConnectCallback);
  }

  private void emitRaw(int paramInt, String paramString)
  {
    WebSocket localWebSocket = this.webSocket;
    Object[] arrayOfObject = new Object[2];
    arrayOfObject[0] = Integer.valueOf(paramInt);
    arrayOfObject[1] = paramString;
    localWebSocket.send(String.format("%d:::%s", arrayOfObject));
  }

  private Future<SocketIOClient> reconnect(SocketIOConnectCallback paramSocketIOConnectCallback)
  {
    FutureImpl localFutureImpl = new FutureImpl(null);
    reconnect(paramSocketIOConnectCallback, localFutureImpl);
    return localFutureImpl;
  }

  private void reconnect(final SocketIOConnectCallback paramSocketIOConnectCallback, final FutureImpl paramFutureImpl)
  {
    if (isConnected())
    {
      this.httpClient.getServer().post(new Runnable()
      {
        public void run()
        {
          paramFutureImpl.setComplete(new Exception("already connected"));
        }
      });
      return;
    }
    this.connected = false;
    this.disconnected = false;
    paramFutureImpl.setParent(this.httpClient.websocket(this.sessionUrl, null, new AsyncHttpClient.WebSocketConnectCallback()
    {
      public void onCompleted(Exception paramAnonymousException, WebSocket paramAnonymousWebSocket)
      {
        if (paramAnonymousException != null)
        {
          SocketIOClient.reportError(paramFutureImpl, SocketIOClient.this.handler, paramSocketIOConnectCallback, paramAnonymousException);
          return;
        }
        SocketIOClient.this.webSocket = paramAnonymousWebSocket;
        SocketIOClient.this.attach(paramSocketIOConnectCallback, paramFutureImpl);
      }
    }));
  }

  private static void reportError(FutureImpl paramFutureImpl, Handler paramHandler, SocketIOConnectCallback paramSocketIOConnectCallback, final Exception paramException)
  {
    if (!paramFutureImpl.setComplete(paramException));
    do
    {
      return;
      if (paramHandler != null)
      {
        AsyncServer.post(paramHandler, new Runnable()
        {
          public void run()
          {
            if (this.val$callback != null)
              this.val$callback.onConnectCompleted(paramException, null);
          }
        });
        return;
      }
    }
    while (paramSocketIOConnectCallback == null);
    paramSocketIOConnectCallback.onConnectCompleted(paramException, null);
  }

  public void disconnect()
  {
    this.webSocket.setStringCallback(null);
    this.webSocket.setDataCallback(null);
    this.webSocket.setClosedCallback(null);
    this.webSocket.close();
    this.webSocket = null;
  }

  public void emit(String paramString)
  {
    emitRaw(3, paramString);
  }

  public void emit(String paramString, JSONArray paramJSONArray)
  {
    JSONObject localJSONObject = new JSONObject();
    try
    {
      localJSONObject.put("name", paramString);
      localJSONObject.put("args", paramJSONArray);
      emitRaw(5, localJSONObject.toString());
      return;
    }
    catch (Exception localException)
    {
    }
  }

  public void emit(JSONObject paramJSONObject)
  {
    emitRaw(4, paramJSONObject.toString());
  }

  public CompletedCallback getClosedCallback()
  {
    return this.closedCallback;
  }

  public EventCallback getEventCallback()
  {
    return this.eventCallback;
  }

  public JSONCallback getJSONCallback()
  {
    return this.jsonCallback;
  }

  public StringCallback getStringCallback()
  {
    return this.stringCallback;
  }

  public boolean isConnected()
  {
    return (this.connected) && (!this.disconnected) && (this.webSocket != null) && (this.webSocket.isOpen());
  }

  public void setClosedCallback(CompletedCallback paramCompletedCallback)
  {
    this.closedCallback = paramCompletedCallback;
  }

  public void setEventCallback(EventCallback paramEventCallback)
  {
    this.eventCallback = paramEventCallback;
  }

  public void setJSONCallback(JSONCallback paramJSONCallback)
  {
    this.jsonCallback = paramJSONCallback;
  }

  public void setStringCallback(StringCallback paramStringCallback)
  {
    this.stringCallback = paramStringCallback;
  }

  void setupHeartbeat()
  {
    new Runnable()
    {
      public void run()
      {
        if ((SocketIOClient.this.heartbeat <= 0) || (SocketIOClient.this.disconnected) || (!SocketIOClient.this.connected) || (this.val$ws != SocketIOClient.this.webSocket) || (this.val$ws == null) || (!this.val$ws.isOpen()))
          return;
        SocketIOClient.this.webSocket.send("2:::");
        SocketIOClient.this.webSocket.getServer().postDelayed(this, SocketIOClient.this.heartbeat);
      }
    }
    .run();
  }

  public static abstract interface EventCallback
  {
    public abstract void onEvent(String paramString, JSONArray paramJSONArray);
  }

  private static class FutureImpl extends SimpleFuture<SocketIOClient>
  {
  }

  public static abstract interface JSONCallback
  {
    public abstract void onJSON(JSONObject paramJSONObject);
  }

  public static abstract interface SocketIOConnectCallback
  {
    public abstract void onConnectCompleted(Exception paramException, SocketIOClient paramSocketIOClient);
  }

  public static class SocketIORequest extends AsyncHttpPost
  {
    String channel;

    public SocketIORequest(String paramString)
    {
      super();
      this.channel = Uri.parse(paramString).getPath();
      if (TextUtils.isEmpty(this.channel))
        this.channel = null;
    }

    public String getChannel()
    {
      return this.channel;
    }
  }

  public static abstract interface StringCallback
  {
    public abstract void onString(String paramString);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.http.SocketIOClient
 * JD-Core Version:    0.6.2
 */