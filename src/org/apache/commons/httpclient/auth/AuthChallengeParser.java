package org.apache.commons.httpclient.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.util.ParameterParser;

public final class AuthChallengeParser
{
  public static Map extractParams(String paramString)
    throws MalformedChallengeException
  {
    if (paramString == null)
      throw new IllegalArgumentException("Challenge may not be null");
    int i = paramString.indexOf(' ');
    if (i == -1)
      throw new MalformedChallengeException("Invalid challenge: " + paramString);
    HashMap localHashMap = new HashMap();
    List localList = new ParameterParser().parse(paramString.substring(i + 1, paramString.length()), ',');
    for (int j = 0; ; j++)
    {
      if (j >= localList.size())
        return localHashMap;
      NameValuePair localNameValuePair = (NameValuePair)localList.get(j);
      localHashMap.put(localNameValuePair.getName().toLowerCase(), localNameValuePair.getValue());
    }
  }

  public static String extractScheme(String paramString)
    throws MalformedChallengeException
  {
    if (paramString == null)
      throw new IllegalArgumentException("Challenge may not be null");
    int i = paramString.indexOf(' ');
    if (i == -1);
    for (String str = paramString; str.equals(""); str = paramString.substring(0, i))
      throw new MalformedChallengeException("Invalid challenge: " + paramString);
    return str.toLowerCase();
  }

  public static Map parseChallenges(Header[] paramArrayOfHeader)
    throws MalformedChallengeException
  {
    if (paramArrayOfHeader == null)
      throw new IllegalArgumentException("Array of challenges may not be null");
    HashMap localHashMap = new HashMap(paramArrayOfHeader.length);
    for (int i = 0; ; i++)
    {
      if (i >= paramArrayOfHeader.length)
        return localHashMap;
      String str = paramArrayOfHeader[i].getValue();
      localHashMap.put(extractScheme(str), str);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.apache.commons.httpclient.auth.AuthChallengeParser
 * JD-Core Version:    0.6.2
 */