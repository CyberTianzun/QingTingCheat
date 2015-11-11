package weibo4android.org.json;

import java.util.Iterator;

public class HTTP
{
  public static final String CRLF = "\r\n";

  public static JSONObject toJSONObject(String paramString)
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    HTTPTokener localHTTPTokener = new HTTPTokener(paramString);
    String str1 = localHTTPTokener.nextToken();
    if (str1.toUpperCase().startsWith("HTTP"))
    {
      localJSONObject.put("HTTP-Version", str1);
      localJSONObject.put("Status-Code", localHTTPTokener.nextToken());
      localJSONObject.put("Reason-Phrase", localHTTPTokener.nextTo('\000'));
      localHTTPTokener.next();
    }
    while (localHTTPTokener.more())
    {
      String str2 = localHTTPTokener.nextTo(':');
      localHTTPTokener.next(':');
      localJSONObject.put(str2, localHTTPTokener.nextTo('\000'));
      localHTTPTokener.next();
      continue;
      localJSONObject.put("Method", str1);
      localJSONObject.put("Request-URI", localHTTPTokener.nextToken());
      localJSONObject.put("HTTP-Version", localHTTPTokener.nextToken());
    }
    return localJSONObject;
  }

  public static String toString(JSONObject paramJSONObject)
    throws JSONException
  {
    Iterator localIterator = paramJSONObject.keys();
    StringBuffer localStringBuffer = new StringBuffer();
    if ((paramJSONObject.has("Status-Code")) && (paramJSONObject.has("Reason-Phrase")))
    {
      localStringBuffer.append(paramJSONObject.getString("HTTP-Version"));
      localStringBuffer.append(' ');
      localStringBuffer.append(paramJSONObject.getString("Status-Code"));
      localStringBuffer.append(' ');
      localStringBuffer.append(paramJSONObject.getString("Reason-Phrase"));
    }
    while (true)
    {
      localStringBuffer.append("\r\n");
      while (localIterator.hasNext())
      {
        String str = localIterator.next().toString();
        if ((!str.equals("HTTP-Version")) && (!str.equals("Status-Code")) && (!str.equals("Reason-Phrase")) && (!str.equals("Method")) && (!str.equals("Request-URI")) && (!paramJSONObject.isNull(str)))
        {
          localStringBuffer.append(str);
          localStringBuffer.append(": ");
          localStringBuffer.append(paramJSONObject.getString(str));
          localStringBuffer.append("\r\n");
        }
      }
      if ((!paramJSONObject.has("Method")) || (!paramJSONObject.has("Request-URI")))
        break;
      localStringBuffer.append(paramJSONObject.getString("Method"));
      localStringBuffer.append(' ');
      localStringBuffer.append('"');
      localStringBuffer.append(paramJSONObject.getString("Request-URI"));
      localStringBuffer.append('"');
      localStringBuffer.append(' ');
      localStringBuffer.append(paramJSONObject.getString("HTTP-Version"));
    }
    throw new JSONException("Not enough material for an HTTP header.");
    localStringBuffer.append("\r\n");
    return localStringBuffer.toString();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.org.json.HTTP
 * JD-Core Version:    0.6.2
 */