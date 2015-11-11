package weibo4android.http;

import java.io.PrintStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import weibo4android.Configuration;

public class OAuth
  implements Serializable
{
  private static final boolean DEBUG = false;
  private static final String HMAC_SHA1 = "HmacSHA1";
  private static final PostParameter OAUTH_SIGNATURE_METHOD = new PostParameter("oauth_signature_method", "HMAC-SHA1");
  private static Random RAND = new Random();
  static final long serialVersionUID = -4368426677157998618L;
  private String consumerKey = "";
  private String consumerSecret;

  public OAuth(String paramString1, String paramString2)
  {
    setConsumerKey(paramString1);
    setConsumerSecret(paramString2);
  }

  public static String constructRequestURL(String paramString)
  {
    int i = paramString.indexOf("?");
    if (-1 != i)
      paramString = paramString.substring(0, i);
    int j = paramString.indexOf("/", 8);
    String str = paramString.substring(0, j).toLowerCase();
    int k = str.indexOf(":", 8);
    if (-1 != k)
      if ((!str.startsWith("http://")) || (!str.endsWith(":80")))
        break label103;
    for (str = str.substring(0, k); ; str = str.substring(0, k))
      label103: 
      do
        return str + paramString.substring(j);
      while ((!str.startsWith("https://")) || (!str.endsWith(":443")));
  }

  public static String encode(String paramString)
  {
    try
    {
      String str2 = URLEncoder.encode(paramString, "UTF-8");
      str1 = str2;
      StringBuffer localStringBuffer = new StringBuffer(str1.length());
      int i = 0;
      if (i < str1.length())
      {
        char c = str1.charAt(i);
        if (c == '*')
          localStringBuffer.append("%2A");
        while (true)
        {
          i++;
          break;
          if (c == '+')
          {
            localStringBuffer.append("%20");
          }
          else if ((c == '%') && (i + 1 < str1.length()) && (str1.charAt(i + 1) == '7') && (str1.charAt(i + 2) == 'E'))
          {
            localStringBuffer.append('~');
            i += 2;
          }
          else
          {
            localStringBuffer.append(c);
          }
        }
      }
      return localStringBuffer.toString();
    }
    catch (UnsupportedEncodingException localUnsupportedEncodingException)
    {
      while (true)
        String str1 = null;
    }
  }

  public static String encodeParameters(List<PostParameter> paramList)
  {
    return encodeParameters(paramList, "&", false);
  }

  public static String encodeParameters(List<PostParameter> paramList, String paramString, boolean paramBoolean)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      PostParameter localPostParameter = (PostParameter)localIterator.next();
      if (localStringBuffer.length() != 0)
      {
        if (paramBoolean)
          localStringBuffer.append("\"");
        localStringBuffer.append(paramString);
      }
      localStringBuffer.append(encode(localPostParameter.name)).append("=");
      if (paramBoolean)
        localStringBuffer.append("\"");
      localStringBuffer.append(encode(localPostParameter.value));
    }
    if ((localStringBuffer.length() != 0) && (paramBoolean))
      localStringBuffer.append("\"");
    return localStringBuffer.toString();
  }

  private void log(String paramString)
  {
    if (DEBUG)
      System.out.println("[" + new Date() + "]" + paramString);
  }

  private void log(String paramString1, String paramString2)
  {
    if (DEBUG)
      log(paramString1 + paramString2);
  }

  public static String normalizeAuthorizationHeaders(List<PostParameter> paramList)
  {
    Collections.sort(paramList);
    return encodeParameters(paramList);
  }

  public static String normalizeRequestParameters(List<PostParameter> paramList)
  {
    Collections.sort(paramList);
    return encodeParameters(paramList);
  }

  public static String normalizeRequestParameters(PostParameter[] paramArrayOfPostParameter)
  {
    return normalizeRequestParameters(toParamList(paramArrayOfPostParameter));
  }

  private void parseGetParameters(String paramString, List<PostParameter> paramList)
  {
    int i = 0;
    int j = paramString.indexOf("?");
    String[] arrayOfString1;
    if (-1 != j)
      arrayOfString1 = paramString.substring(j + 1).split("&");
    while (true)
    {
      try
      {
        int k = arrayOfString1.length;
        if (i < k)
        {
          String[] arrayOfString2 = arrayOfString1[i].split("=");
          if (arrayOfString2.length == 2)
            paramList.add(new PostParameter(URLDecoder.decode(arrayOfString2[0], "UTF-8"), URLDecoder.decode(arrayOfString2[1], "UTF-8")));
          else
            paramList.add(new PostParameter(URLDecoder.decode(arrayOfString2[0], "UTF-8"), ""));
        }
      }
      catch (UnsupportedEncodingException localUnsupportedEncodingException)
      {
      }
      return;
      i++;
    }
  }

  public static List<PostParameter> toParamList(PostParameter[] paramArrayOfPostParameter)
  {
    ArrayList localArrayList = new ArrayList(paramArrayOfPostParameter.length);
    localArrayList.addAll(Arrays.asList(paramArrayOfPostParameter));
    return localArrayList;
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    OAuth localOAuth;
    do
    {
      return true;
      if (!(paramObject instanceof OAuth))
        return false;
      localOAuth = (OAuth)paramObject;
      if (this.consumerKey != null)
      {
        if (this.consumerKey.equals(localOAuth.consumerKey));
      }
      else
        while (localOAuth.consumerKey != null)
          return false;
      if (this.consumerSecret == null)
        break;
    }
    while (this.consumerSecret.equals(localOAuth.consumerSecret));
    while (true)
    {
      return false;
      if (localOAuth.consumerSecret == null)
        break;
    }
  }

  String generateAuthorizationHeader(String paramString1, String paramString2, PostParameter[] paramArrayOfPostParameter, String paramString3, String paramString4, OAuthToken paramOAuthToken)
  {
    if (paramArrayOfPostParameter == null)
      paramArrayOfPostParameter = new PostParameter[0];
    ArrayList localArrayList1 = new ArrayList(5);
    localArrayList1.add(new PostParameter("oauth_consumer_key", this.consumerKey));
    localArrayList1.add(OAUTH_SIGNATURE_METHOD);
    localArrayList1.add(new PostParameter("oauth_timestamp", paramString4));
    localArrayList1.add(new PostParameter("oauth_nonce", paramString3));
    localArrayList1.add(new PostParameter("oauth_version", "1.0"));
    if (paramOAuthToken != null)
      localArrayList1.add(new PostParameter("oauth_token", paramOAuthToken.getToken()));
    ArrayList localArrayList2 = new ArrayList(localArrayList1.size() + paramArrayOfPostParameter.length);
    localArrayList2.addAll(localArrayList1);
    localArrayList2.addAll(toParamList(paramArrayOfPostParameter));
    parseGetParameters(paramString2, localArrayList2);
    StringBuffer localStringBuffer = new StringBuffer(paramString1).append("&").append(encode(constructRequestURL(paramString2))).append("&");
    localStringBuffer.append(encode(normalizeRequestParameters(localArrayList2)));
    String str1 = localStringBuffer.toString();
    log("OAuth base string:", str1);
    String str2 = generateSignature(str1, paramOAuthToken);
    log("OAuth signature:", str2);
    localArrayList1.add(new PostParameter("oauth_signature", str2));
    return "OAuth " + encodeParameters(localArrayList1, ",", true);
  }

  String generateAuthorizationHeader(String paramString1, String paramString2, PostParameter[] paramArrayOfPostParameter, OAuthToken paramOAuthToken)
  {
    long l = System.currentTimeMillis() / 1000L;
    return generateAuthorizationHeader(paramString1, paramString2, paramArrayOfPostParameter, String.valueOf(l + RAND.nextInt()), String.valueOf(l), paramOAuthToken);
  }

  String generateSignature(String paramString)
  {
    return generateSignature(paramString, null);
  }

  String generateSignature(String paramString, OAuthToken paramOAuthToken)
  {
    try
    {
      Mac localMac = Mac.getInstance("HmacSHA1");
      if (paramOAuthToken == null);
      SecretKeySpec localSecretKeySpec;
      for (Object localObject = new SecretKeySpec((encode(this.consumerSecret) + "&").getBytes(), "HmacSHA1"); ; localObject = localSecretKeySpec)
      {
        localMac.init((Key)localObject);
        byte[] arrayOfByte2 = localMac.doFinal(paramString.getBytes());
        arrayOfByte1 = arrayOfByte2;
        return new BASE64Encoder().encode(arrayOfByte1);
        if (paramOAuthToken.getSecretKeySpec() == null)
          paramOAuthToken.setSecretKeySpec(new SecretKeySpec((encode(this.consumerSecret) + "&" + encode(paramOAuthToken.getTokenSecret())).getBytes(), "HmacSHA1"));
        localSecretKeySpec = paramOAuthToken.getSecretKeySpec();
      }
    }
    catch (InvalidKeyException localInvalidKeyException)
    {
      while (true)
      {
        localInvalidKeyException.printStackTrace();
        arrayOfByte1 = null;
      }
    }
    catch (NoSuchAlgorithmException localNoSuchAlgorithmException)
    {
      while (true)
        byte[] arrayOfByte1 = null;
    }
  }

  public int hashCode()
  {
    if (this.consumerKey != null);
    for (int i = this.consumerKey.hashCode(); ; i = 0)
    {
      int j = i * 31;
      String str = this.consumerSecret;
      int k = 0;
      if (str != null)
        k = this.consumerSecret.hashCode();
      return j + k;
    }
  }

  public void setConsumerKey(String paramString)
  {
    if (paramString != null);
    while (true)
    {
      this.consumerKey = paramString;
      return;
      paramString = "";
    }
  }

  public void setConsumerSecret(String paramString)
  {
    if (paramString != null);
    while (true)
    {
      this.consumerSecret = paramString;
      return;
      paramString = "";
    }
  }

  public String toString()
  {
    return "OAuth{consumerKey='" + this.consumerKey + '\'' + ", consumerSecret='" + this.consumerSecret + '\'' + '}';
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.http.OAuth
 * JD-Core Version:    0.6.2
 */