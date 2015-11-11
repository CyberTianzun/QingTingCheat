package fm.qingting.qtradio.voice;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.StringTokenizer;
import org.json.JSONException;
import org.json.JSONObject;

public class VoiceRecognizer
{
  private static final String APP_KEY = "Exwb1LH6g4qKAXVjQHYr73au";
  private static final String APP_SECRET = "Exx7lBdM0DAvM3l6X8eyrGQoTkTvIQsu";
  private static final String SERVER_URL = "http://vop.baidu.com/server_api";
  private static final String TOKEN_URL = "https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials&client_id=Exwb1LH6g4qKAXVjQHYr73au&client_secret=Exx7lBdM0DAvM3l6X8eyrGQoTkTvIQsu";
  private static String mToken;

  public static ArrayList<String> recognize(byte[] paramArrayOfByte)
    throws MalformedURLException, IOException, JSONException
  {
    Object localObject = null;
    if (paramArrayOfByte == null);
    while (true)
    {
      return localObject;
      boolean bool = token();
      localObject = null;
      if (bool)
      {
        HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL("http://vop.baidu.com/server_api?cuid=22&token=" + mToken).openConnection();
        localHttpURLConnection.setRequestMethod("POST");
        localHttpURLConnection.setRequestProperty("Content-Type", "audio/pcm; rate=8000");
        localHttpURLConnection.setDoInput(true);
        localHttpURLConnection.setDoOutput(true);
        DataOutputStream localDataOutputStream = new DataOutputStream(localHttpURLConnection.getOutputStream());
        localDataOutputStream.write(paramArrayOfByte);
        localDataOutputStream.flush();
        localDataOutputStream.close();
        String str = new JSONObject(request(localHttpURLConnection)).getString("result");
        localObject = new ArrayList();
        StringTokenizer localStringTokenizer = new StringTokenizer(str.substring(2, -3 + str.length()), "，");
        while (localStringTokenizer.hasMoreElements())
          ((ArrayList)localObject).add(localStringTokenizer.nextToken());
      }
    }
  }

  private static String request(HttpURLConnection paramHttpURLConnection)
    throws UnsupportedEncodingException, IOException
  {
    String str = "";
    if (paramHttpURLConnection.getResponseCode() == 200)
    {
      InputStream localInputStream = paramHttpURLConnection.getInputStream();
      ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
      byte[] arrayOfByte = new byte[1024];
      while (true)
      {
        int i = localInputStream.read(arrayOfByte, 0, arrayOfByte.length);
        if (i == -1)
          break;
        localByteArrayOutputStream.write(arrayOfByte, 0, i);
      }
      str = new String(localByteArrayOutputStream.toByteArray(), "UTF-8");
      localByteArrayOutputStream.close();
      localInputStream.close();
    }
    return str;
  }

  private static boolean token()
    throws UnsupportedEncodingException, IOException, JSONException
  {
    if (mToken == null)
    {
      String str = request((HttpURLConnection)new URL("https://openapi.baidu.com/oauth/2.0/token?grant_type=client_credentials&client_id=Exwb1LH6g4qKAXVjQHYr73au&client_secret=Exx7lBdM0DAvM3l6X8eyrGQoTkTvIQsu").openConnection());
      if (!str.equals(""))
        mToken = new JSONObject(str).getString("access_token");
    }
    return mToken != null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.voice.VoiceRecognizer
 * JD-Core Version:    0.6.2
 */