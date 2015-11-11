package weibo4android;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import weibo4android.http.HTMLEntity;
import weibo4android.http.Response;
import weibo4android.org.json.JSONException;
import weibo4android.org.json.JSONObject;

public class WeiboResponse
  implements Serializable
{
  private static final boolean IS_DALVIK = false;
  private static Map<String, SimpleDateFormat> formatMap = new HashMap();
  private static final long serialVersionUID = 3519962197957449562L;
  private transient int rateLimitLimit = -1;
  private transient int rateLimitRemaining = -1;
  private transient long rateLimitReset = -1L;

  public WeiboResponse()
  {
  }

  public WeiboResponse(Response paramResponse)
  {
    String str1 = paramResponse.getResponseHeader("X-RateLimit-Limit");
    if (str1 != null)
      this.rateLimitLimit = Integer.parseInt(str1);
    String str2 = paramResponse.getResponseHeader("X-RateLimit-Remaining");
    if (str2 != null)
      this.rateLimitRemaining = Integer.parseInt(str2);
    String str3 = paramResponse.getResponseHeader("X-RateLimit-Reset");
    if (str3 != null)
      this.rateLimitReset = Long.parseLong(str3);
  }

  protected static void ensureRootNodeNameIs(String paramString, Document paramDocument)
    throws WeiboException
  {
    Element localElement = paramDocument.getDocumentElement();
    if (!paramString.equals(localElement.getNodeName()))
      throw new WeiboException("Unexpected root node name:" + localElement.getNodeName() + ". Expected:" + paramString + ". Check the availability of the Weibo API at http://open.t.sina.com.cn/");
  }

  protected static void ensureRootNodeNameIs(String paramString, Element paramElement)
    throws WeiboException
  {
    if (!paramString.equals(paramElement.getNodeName()))
      throw new WeiboException("Unexpected root node name:" + paramElement.getNodeName() + ". Expected:" + paramString + ". Check the availability of the Weibo API at http://open.t.sina.com.cn/.");
  }

  protected static void ensureRootNodeNameIs(String[] paramArrayOfString, Element paramElement)
    throws WeiboException
  {
    String str1 = paramElement.getNodeName();
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
      if (paramArrayOfString[j].equals(str1))
        return;
    Object localObject = "";
    int k = 0;
    while (k < paramArrayOfString.length)
    {
      if (k != 0)
        localObject = (String)localObject + " or ";
      String str2 = (String)localObject + paramArrayOfString[k];
      k++;
      localObject = str2;
    }
    throw new WeiboException("Unexpected root node name:" + paramElement.getNodeName() + ". Expected:" + (String)localObject + ". Check the availability of the Weibo API at http://open.t.sina.com.cn/.");
  }

  protected static boolean getBoolean(String paramString, JSONObject paramJSONObject)
    throws JSONException
  {
    String str = paramJSONObject.getString(paramString);
    if ((str == null) || ("".equals(str)) || ("null".equals(str)))
      return false;
    return Boolean.valueOf(str).booleanValue();
  }

  protected static boolean getChildBoolean(String paramString, Element paramElement)
  {
    return Boolean.valueOf(getTextContent(paramString, paramElement)).booleanValue();
  }

  protected static Date getChildDate(String paramString, Element paramElement)
    throws WeiboException
  {
    return getChildDate(paramString, paramElement, "EEE MMM d HH:mm:ss z yyyy");
  }

  protected static Date getChildDate(String paramString1, Element paramElement, String paramString2)
    throws WeiboException
  {
    return parseDate(getChildText(paramString1, paramElement), paramString2);
  }

  protected static int getChildInt(String paramString, Element paramElement)
  {
    String str = getTextContent(paramString, paramElement);
    if ((str == null) || ("".equals(str)) || ("null".equals(paramString)))
      return -1;
    return Integer.valueOf(str).intValue();
  }

  protected static long getChildLong(String paramString, Element paramElement)
  {
    String str = getTextContent(paramString, paramElement);
    if ((str == null) || ("".equals(str)) || ("null".equals(paramString)))
      return -1L;
    return Long.valueOf(str).longValue();
  }

  protected static String getChildText(String paramString, Element paramElement)
  {
    return HTMLEntity.unescape(getTextContent(paramString, paramElement));
  }

  protected static int getInt(String paramString, JSONObject paramJSONObject)
    throws JSONException
  {
    String str = paramJSONObject.getString(paramString);
    if ((str == null) || ("".equals(str)) || ("null".equals(str)))
      return -1;
    return Integer.parseInt(str);
  }

  protected static long getLong(String paramString, JSONObject paramJSONObject)
    throws JSONException
  {
    String str = paramJSONObject.getString(paramString);
    if ((str == null) || ("".equals(str)) || ("null".equals(str)))
      return -1L;
    return Long.parseLong(str);
  }

  // ERROR //
  protected static String getString(String paramString, JSONObject paramJSONObject, boolean paramBoolean)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aload_1
    //   3: aload_0
    //   4: invokevirtual 129	weibo4android/org/json/JSONObject:getString	(Ljava/lang/String;)Ljava/lang/String;
    //   7: astore 5
    //   9: aload 5
    //   11: astore_3
    //   12: iload_2
    //   13: ifeq +14 -> 27
    //   16: aload_3
    //   17: ldc 192
    //   19: invokestatic 198	java/net/URLDecoder:decode	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   22: astore 7
    //   24: aload 7
    //   26: astore_3
    //   27: aload_3
    //   28: areturn
    //   29: astore 4
    //   31: aload_3
    //   32: areturn
    //   33: astore 6
    //   35: aload_3
    //   36: areturn
    //
    // Exception table:
    //   from	to	target	type
    //   2	9	29	weibo4android/org/json/JSONException
    //   16	24	29	weibo4android/org/json/JSONException
    //   16	24	33	java/io/UnsupportedEncodingException
  }

  protected static String getTextContent(String paramString, Element paramElement)
  {
    NodeList localNodeList = paramElement.getElementsByTagName(paramString);
    if (localNodeList.getLength() > 0)
    {
      Node localNode = localNodeList.item(0).getFirstChild();
      if (localNode != null)
      {
        String str = localNode.getNodeValue();
        if (str != null)
          return str;
        return "";
      }
    }
    return "";
  }

  protected static boolean isRootNodeNilClasses(Document paramDocument)
  {
    String str = paramDocument.getDocumentElement().getNodeName();
    return ("nil-classes".equals(str)) || ("nilclasses".equals(str));
  }

  protected static Date parseDate(String paramString1, String paramString2)
    throws WeiboException
  {
    if ((paramString1 == null) || ("".equals(paramString1)))
      return null;
    SimpleDateFormat localSimpleDateFormat1 = (SimpleDateFormat)formatMap.get(paramString2);
    SimpleDateFormat localSimpleDateFormat2;
    if (localSimpleDateFormat1 == null)
    {
      localSimpleDateFormat2 = new SimpleDateFormat(paramString2, Locale.ENGLISH);
      localSimpleDateFormat2.setTimeZone(TimeZone.getTimeZone("GMT"));
      formatMap.put(paramString2, localSimpleDateFormat2);
    }
    while (true)
    {
      try
      {
        synchronized (localSimpleDateFormat2)
        {
          Date localDate = ???.parse(paramString1);
          return localDate;
        }
      }
      catch (ParseException localParseException)
      {
        throw new WeiboException("Unexpected format(" + paramString1 + ") returned from sina.com.cn");
      }
      ??? = localSimpleDateFormat1;
    }
  }

  public int getRateLimitLimit()
  {
    return this.rateLimitLimit;
  }

  public int getRateLimitRemaining()
  {
    return this.rateLimitRemaining;
  }

  public long getRateLimitReset()
  {
    return this.rateLimitReset;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.WeiboResponse
 * JD-Core Version:    0.6.2
 */