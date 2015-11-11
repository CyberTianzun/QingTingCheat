package weibo4android.org.json;

public class CDL
{
  private static String getValue(JSONTokener paramJSONTokener)
    throws JSONException
  {
    char c;
    do
      c = paramJSONTokener.next();
    while ((c == ' ') || (c == '\t'));
    switch (c)
    {
    default:
      paramJSONTokener.back();
      return paramJSONTokener.nextTo(',');
    case '\000':
      return null;
    case '"':
    case '\'':
      return paramJSONTokener.nextString(c);
    case ',':
    }
    paramJSONTokener.back();
    return "";
  }

  public static JSONArray rowToJSONArray(JSONTokener paramJSONTokener)
    throws JSONException
  {
    JSONArray localJSONArray = new JSONArray();
    String str = getValue(paramJSONTokener);
    if ((str == null) || ((localJSONArray.length() == 0) && (str.length() == 0)))
      localJSONArray = null;
    char c;
    do
    {
      return localJSONArray;
      localJSONArray.put(str);
      do
      {
        c = paramJSONTokener.next();
        if (c == ',')
          break;
      }
      while (c == ' ');
    }
    while ((c == '\n') || (c == '\r') || (c == 0));
    throw paramJSONTokener.syntaxError("Bad character '" + c + "' (" + c + ").");
  }

  public static JSONObject rowToJSONObject(JSONArray paramJSONArray, JSONTokener paramJSONTokener)
    throws JSONException
  {
    JSONArray localJSONArray = rowToJSONArray(paramJSONTokener);
    if (localJSONArray != null)
      return localJSONArray.toJSONObject(paramJSONArray);
    return null;
  }

  public static String rowToString(JSONArray paramJSONArray)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    if (i < paramJSONArray.length())
    {
      if (i > 0)
        localStringBuffer.append(',');
      Object localObject = paramJSONArray.opt(i);
      String str;
      if (localObject != null)
      {
        str = localObject.toString();
        if (str.indexOf(',') < 0)
          break label119;
        if (str.indexOf('"') < 0)
          break label95;
        localStringBuffer.append('\'');
        localStringBuffer.append(str);
        localStringBuffer.append('\'');
      }
      while (true)
      {
        i++;
        break;
        label95: localStringBuffer.append('"');
        localStringBuffer.append(str);
        localStringBuffer.append('"');
        continue;
        label119: localStringBuffer.append(str);
      }
    }
    localStringBuffer.append('\n');
    return localStringBuffer.toString();
  }

  public static JSONArray toJSONArray(String paramString)
    throws JSONException
  {
    return toJSONArray(new JSONTokener(paramString));
  }

  public static JSONArray toJSONArray(JSONArray paramJSONArray, String paramString)
    throws JSONException
  {
    return toJSONArray(paramJSONArray, new JSONTokener(paramString));
  }

  public static JSONArray toJSONArray(JSONArray paramJSONArray, JSONTokener paramJSONTokener)
    throws JSONException
  {
    if ((paramJSONArray == null) || (paramJSONArray.length() == 0))
      return null;
    JSONArray localJSONArray = new JSONArray();
    while (true)
    {
      JSONObject localJSONObject = rowToJSONObject(paramJSONArray, paramJSONTokener);
      if (localJSONObject == null)
      {
        if (localJSONArray.length() == 0)
          break;
        return localJSONArray;
      }
      localJSONArray.put(localJSONObject);
    }
  }

  public static JSONArray toJSONArray(JSONTokener paramJSONTokener)
    throws JSONException
  {
    return toJSONArray(rowToJSONArray(paramJSONTokener), paramJSONTokener);
  }

  public static String toString(JSONArray paramJSONArray)
    throws JSONException
  {
    JSONObject localJSONObject = paramJSONArray.optJSONObject(0);
    if (localJSONObject != null)
    {
      JSONArray localJSONArray = localJSONObject.names();
      if (localJSONArray != null)
        return rowToString(localJSONArray) + toString(localJSONArray, paramJSONArray);
    }
    return null;
  }

  public static String toString(JSONArray paramJSONArray1, JSONArray paramJSONArray2)
    throws JSONException
  {
    if ((paramJSONArray1 == null) || (paramJSONArray1.length() == 0))
      return null;
    StringBuffer localStringBuffer = new StringBuffer();
    for (int i = 0; i < paramJSONArray2.length(); i++)
    {
      JSONObject localJSONObject = paramJSONArray2.optJSONObject(i);
      if (localJSONObject != null)
        localStringBuffer.append(rowToString(localJSONObject.toJSONArray(paramJSONArray1)));
    }
    return localStringBuffer.toString();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.org.json.CDL
 * JD-Core Version:    0.6.2
 */