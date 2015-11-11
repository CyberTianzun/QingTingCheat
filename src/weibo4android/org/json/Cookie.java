package weibo4android.org.json;

public class Cookie
{
  public static String escape(String paramString)
  {
    String str = paramString.trim();
    StringBuffer localStringBuffer = new StringBuffer();
    int i = str.length();
    int j = 0;
    if (j < i)
    {
      char c = str.charAt(j);
      if ((c < ' ') || (c == '+') || (c == '%') || (c == '=') || (c == ';'))
      {
        localStringBuffer.append('%');
        localStringBuffer.append(Character.forDigit((char)(0xF & c >>> '\004'), 16));
        localStringBuffer.append(Character.forDigit((char)(c & 0xF), 16));
      }
      while (true)
      {
        j++;
        break;
        localStringBuffer.append(c);
      }
    }
    return localStringBuffer.toString();
  }

  public static JSONObject toJSONObject(String paramString)
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    JSONTokener localJSONTokener = new JSONTokener(paramString);
    localJSONObject.put("name", localJSONTokener.nextTo('='));
    localJSONTokener.next('=');
    localJSONObject.put("value", localJSONTokener.nextTo(';'));
    localJSONTokener.next();
    if (localJSONTokener.more())
    {
      String str = unescape(localJSONTokener.nextTo("=;"));
      Object localObject;
      if (localJSONTokener.next() != '=')
        if (str.equals("secure"))
          localObject = Boolean.TRUE;
      while (true)
      {
        localJSONObject.put(str, localObject);
        break;
        throw localJSONTokener.syntaxError("Missing '=' in cookie parameter.");
        localObject = unescape(localJSONTokener.nextTo(';'));
        localJSONTokener.next();
      }
    }
    return localJSONObject;
  }

  public static String toString(JSONObject paramJSONObject)
    throws JSONException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    localStringBuffer.append(escape(paramJSONObject.getString("name")));
    localStringBuffer.append("=");
    localStringBuffer.append(escape(paramJSONObject.getString("value")));
    if (paramJSONObject.has("expires"))
    {
      localStringBuffer.append(";expires=");
      localStringBuffer.append(paramJSONObject.getString("expires"));
    }
    if (paramJSONObject.has("domain"))
    {
      localStringBuffer.append(";domain=");
      localStringBuffer.append(escape(paramJSONObject.getString("domain")));
    }
    if (paramJSONObject.has("path"))
    {
      localStringBuffer.append(";path=");
      localStringBuffer.append(escape(paramJSONObject.getString("path")));
    }
    if (paramJSONObject.optBoolean("secure"))
      localStringBuffer.append(";secure");
    return localStringBuffer.toString();
  }

  public static String unescape(String paramString)
  {
    int i = paramString.length();
    StringBuffer localStringBuffer = new StringBuffer();
    int j = 0;
    if (j < i)
    {
      char c = paramString.charAt(j);
      if (c == '+')
        c = ' ';
      while (true)
      {
        localStringBuffer.append(c);
        j++;
        break;
        if ((c == '%') && (j + 2 < i))
        {
          int k = JSONTokener.dehexchar(paramString.charAt(j + 1));
          int m = JSONTokener.dehexchar(paramString.charAt(j + 2));
          if ((k >= 0) && (m >= 0))
          {
            c = (char)(m + k * 16);
            j += 2;
          }
        }
      }
    }
    return localStringBuffer.toString();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.org.json.Cookie
 * JD-Core Version:    0.6.2
 */