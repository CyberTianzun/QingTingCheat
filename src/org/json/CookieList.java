package org.json;

import java.util.Iterator;

public class CookieList
{
  public static JSONObject toJSONObject(String paramString)
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    JSONTokener localJSONTokener = new JSONTokener(paramString);
    while (localJSONTokener.more())
    {
      String str = Cookie.unescape(localJSONTokener.nextTo('='));
      localJSONTokener.next('=');
      localJSONObject.put(str, Cookie.unescape(localJSONTokener.nextTo(';')));
      localJSONTokener.next();
    }
    return localJSONObject;
  }

  public static String toString(JSONObject paramJSONObject)
    throws JSONException
  {
    int i = 0;
    Iterator localIterator = paramJSONObject.keys();
    StringBuffer localStringBuffer = new StringBuffer();
    while (localIterator.hasNext())
    {
      String str = localIterator.next().toString();
      if (!paramJSONObject.isNull(str))
      {
        if (i != 0)
          localStringBuffer.append(';');
        localStringBuffer.append(Cookie.escape(str));
        localStringBuffer.append("=");
        localStringBuffer.append(Cookie.escape(paramJSONObject.getString(str)));
        i = 1;
      }
    }
    return localStringBuffer.toString();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.json.CookieList
 * JD-Core Version:    0.6.2
 */