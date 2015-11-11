package org.json;

public class HTTPTokener extends JSONTokener
{
  public HTTPTokener(String paramString)
  {
    super(paramString);
  }

  public String nextToken()
    throws JSONException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    char c1;
    do
      c1 = next();
    while (Character.isWhitespace(c1));
    if ((c1 == '"') || (c1 == '\''))
      while (true)
      {
        c2 = next();
        if (c2 < ' ')
          throw syntaxError("Unterminated string.");
        if (c2 == c1)
          return localStringBuffer.toString();
        localStringBuffer.append(c2);
      }
    while ((c1 != 0) && (!Character.isWhitespace(c1)))
    {
      char c2;
      localStringBuffer.append(c1);
      c1 = next();
    }
    return localStringBuffer.toString();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.json.HTTPTokener
 * JD-Core Version:    0.6.2
 */