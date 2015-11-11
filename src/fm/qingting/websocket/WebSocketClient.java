package fm.qingting.websocket;

import android.os.Handler;
import android.os.HandlerThread;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URI;
import java.security.KeyManagementException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import javax.net.SocketFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpResponseException;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.BasicNameValuePair;

public class WebSocketClient
{
  private static final String TAG = "WebSocketClient";
  private static TrustManager[] sTrustManagers;
  private List<BasicNameValuePair> mExtraHeaders;
  private Handler mHandler;
  private HandlerThread mHandlerThread;
  private Listener mListener;
  private HybiParser mParser;
  private final Object mSendLock = new Object();
  private Socket mSocket;
  private Thread mThread;
  private URI mURI;

  public WebSocketClient(URI paramURI, Listener paramListener, List<BasicNameValuePair> paramList)
  {
    this.mURI = paramURI;
    this.mListener = paramListener;
    this.mExtraHeaders = paramList;
    this.mParser = new HybiParser(this);
    this.mHandlerThread = new HandlerThread("websocket-thread");
    this.mHandlerThread.start();
    this.mHandler = new Handler(this.mHandlerThread.getLooper());
  }

  private String createSecret()
  {
    byte[] arrayOfByte = new byte[16];
    for (int i = 0; i < 16; i++)
      arrayOfByte[i] = ((byte)(int)(256.0D * Math.random()));
    return Base64.encodeToString(arrayOfByte, 0).trim();
  }

  private String createSecretValidation(String paramString)
  {
    try
    {
      MessageDigest localMessageDigest = MessageDigest.getInstance("SHA-1");
      localMessageDigest.update((paramString + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes());
      String str = Base64.encodeToString(localMessageDigest.digest(), 0).trim();
      return str;
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      throw new RuntimeException(localNoSuchAlgorithmException);
    }
  }

  private SSLSocketFactory getSSLSocketFactory()
    throws NoSuchAlgorithmException, KeyManagementException
  {
    SSLContext localSSLContext = SSLContext.getInstance("TLS");
    localSSLContext.init(null, sTrustManagers, null);
    return localSSLContext.getSocketFactory();
  }

  private void log(String paramString)
  {
    Log.e("WebSocketClient", paramString);
  }

  private Header parseHeader(String paramString)
  {
    return BasicLineParser.parseHeader(paramString, new BasicLineParser());
  }

  private StatusLine parseStatusLine(String paramString)
  {
    if (TextUtils.isEmpty(paramString))
      return null;
    return BasicLineParser.parseStatusLine(paramString, new BasicLineParser());
  }

  private String readLine(HybiParser.HappyDataInputStream paramHappyDataInputStream)
    throws IOException
  {
    int i = paramHappyDataInputStream.read();
    if (i == -1)
      return null;
    StringBuilder localStringBuilder = new StringBuilder("");
    while (i != 10)
    {
      if (i != 13)
        localStringBuilder.append((char)i);
      i = paramHappyDataInputStream.read();
      if (i == -1)
        return null;
    }
    return localStringBuilder.toString();
  }

  public static void setTrustManagers(TrustManager[] paramArrayOfTrustManager)
  {
    sTrustManagers = paramArrayOfTrustManager;
  }

  public void connect()
  {
    if ((this.mThread != null) && (this.mThread.isAlive()))
      return;
    this.mThread = new Thread(new Runnable()
    {
      public void run()
      {
        String str1;
        int i;
        String str2;
        String str3;
        label106: String str4;
        label128: label543: label558: label566: HybiParser.HappyDataInputStream localHappyDataInputStream;
        try
        {
          str1 = WebSocketClient.this.createSecret();
          if (WebSocketClient.this.mURI.getPort() != -1)
          {
            i = WebSocketClient.this.mURI.getPort();
            if (!TextUtils.isEmpty(WebSocketClient.this.mURI.getPath()))
              break label543;
            str2 = "/";
            if (TextUtils.isEmpty(WebSocketClient.this.mURI.getQuery()))
              break label840;
            str3 = str2 + "?" + WebSocketClient.this.mURI.getQuery();
            if (!WebSocketClient.this.mURI.getScheme().equals("wss"))
              break label854;
            str4 = "https";
            URI localURI = new URI(str4, "//" + WebSocketClient.this.mURI.getHost(), null);
            WebSocketClient.this.log("ready to create msocket");
            if (!WebSocketClient.this.mURI.getScheme().equals("wss"))
              break label558;
            localObject = WebSocketClient.this.getSSLSocketFactory();
            WebSocketClient.access$402(WebSocketClient.this, ((SocketFactory)localObject).createSocket(WebSocketClient.this.mURI.getHost(), i));
            localPrintWriter = new PrintWriter(WebSocketClient.this.mSocket.getOutputStream());
            localPrintWriter.print("GET " + str3 + " HTTP/1.1\r\n");
            localPrintWriter.print("Upgrade: websocket\r\n");
            localPrintWriter.print("Connection: Upgrade\r\n");
            localPrintWriter.print("Host: " + WebSocketClient.this.mURI.getHost() + "\r\n");
            localPrintWriter.print("Origin: " + localURI.toString() + "\r\n");
            localPrintWriter.print("Sec-WebSocket-Key: " + str1 + "\r\n");
            localPrintWriter.print("Sec-WebSocket-Version: 13\r\n");
            if (WebSocketClient.this.mExtraHeaders == null)
              break label566;
            Iterator localIterator = WebSocketClient.this.mExtraHeaders.iterator();
            while (localIterator.hasNext())
            {
              NameValuePair localNameValuePair = (NameValuePair)localIterator.next();
              Object[] arrayOfObject = new Object[2];
              arrayOfObject[0] = localNameValuePair.getName();
              arrayOfObject[1] = localNameValuePair.getValue();
              localPrintWriter.print(String.format("%s: %s\r\n", arrayOfObject));
            }
          }
        }
        catch (EOFException localEOFException)
        {
          PrintWriter localPrintWriter;
          while (true)
          {
            Log.e("WebSocketClient", "WebSocket EOF!", localEOFException);
            WebSocketClient.this.mListener.onDisconnect(0, "EOF");
            return;
            if (!WebSocketClient.this.mURI.getScheme().equals("wss"))
              break;
            i = 443;
            continue;
            str2 = WebSocketClient.this.mURI.getPath();
            continue;
            Object localObject = SocketFactory.getDefault();
          }
          localPrintWriter.print("\r\n");
          localPrintWriter.flush();
          localHappyDataInputStream = new HybiParser.HappyDataInputStream(WebSocketClient.this.mSocket.getInputStream());
          localStatusLine = WebSocketClient.this.parseStatusLine(WebSocketClient.access$600(WebSocketClient.this, localHappyDataInputStream));
          if (localStatusLine == null)
            throw new HttpException("Received no reply from server.");
        }
        catch (SSLException localSSLException)
        {
          StatusLine localStatusLine;
          Log.e("WebSocketClient", "Websocket SSL error!", localSSLException);
          WebSocketClient.this.mListener.onDisconnect(0, "SSL");
          return;
          if (localStatusLine.getStatusCode() != 101)
            throw new HttpResponseException(localStatusLine.getStatusCode(), localStatusLine.getReasonPhrase());
        }
        catch (Exception localException)
        {
          WebSocketClient.this.log("mthread.run,catch an exception");
          WebSocketClient.this.mListener.onError(localException);
          return;
        }
        int j = 0;
        while (true)
        {
          String str5 = WebSocketClient.this.readLine(localHappyDataInputStream);
          if (!TextUtils.isEmpty(str5))
          {
            Header localHeader = WebSocketClient.this.parseHeader(str5);
            if (localHeader.getName().equals("Sec-WebSocket-Accept"))
              if (!WebSocketClient.this.createSecretValidation(str1).equals(localHeader.getValue().trim()))
                throw new HttpException("Bad Sec-WebSocket-Accept header value.");
          }
          else
          {
            if (j == 0)
              throw new HttpException("No Sec-WebSocket-Accept header.");
            WebSocketClient.this.mListener.onConnect();
            WebSocketClient.this.mParser.start(localHappyDataInputStream);
            return;
            label840: str3 = str2;
            break label106;
            i = 80;
            break;
            label854: str4 = "http";
            break label128;
            j = 1;
          }
        }
      }
    });
    this.mThread.start();
  }

  public void disconnect()
  {
    if (this.mSocket != null)
      this.mHandler.post(new Runnable()
      {
        public void run()
        {
          try
          {
            if (WebSocketClient.this.mSocket != null)
              WebSocketClient.this.mSocket.close();
            WebSocketClient.access$402(WebSocketClient.this, null);
            return;
          }
          catch (IOException localIOException)
          {
            Log.d("WebSocketClient", "Error while disconnecting", localIOException);
            WebSocketClient.this.mListener.onError(localIOException);
          }
        }
      });
  }

  public Listener getListener()
  {
    return this.mListener;
  }

  public void send(String paramString)
  {
    sendFrame(this.mParser.frame(paramString));
  }

  public void send(byte[] paramArrayOfByte)
  {
    sendFrame(this.mParser.frame(paramArrayOfByte));
  }

  void sendFrame(final byte[] paramArrayOfByte)
  {
    this.mHandler.post(new Runnable()
    {
      public void run()
      {
        try
        {
          synchronized (WebSocketClient.this.mSendLock)
          {
            if (WebSocketClient.this.mSocket == null)
              throw new IllegalStateException("Socket not connected");
          }
        }
        catch (Exception localException)
        {
          WebSocketClient.this.mListener.onError(localException);
          return;
        }
        OutputStream localOutputStream = WebSocketClient.this.mSocket.getOutputStream();
        localOutputStream.write(paramArrayOfByte);
        localOutputStream.flush();
      }
    });
  }

  public static abstract interface Listener
  {
    public abstract void onConnect();

    public abstract void onDisconnect(int paramInt, String paramString);

    public abstract void onError(Exception paramException);

    public abstract void onMessage(String paramString);

    public abstract void onMessage(byte[] paramArrayOfByte);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.websocket.WebSocketClient
 * JD-Core Version:    0.6.2
 */