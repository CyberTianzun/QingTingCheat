package org.json;

import java.util.Iterator;

public class XML
{
  public static final Character AMP = new Character('&');
  public static final Character APOS = new Character('\'');
  public static final Character BANG = new Character('!');
  public static final Character EQ = new Character('=');
  public static final Character GT = new Character('>');
  public static final Character LT = new Character('<');
  public static final Character QUEST = new Character('?');
  public static final Character QUOT = new Character('"');
  public static final Character SLASH = new Character('/');

  public static String escape(String paramString)
  {
    StringBuffer localStringBuffer = new StringBuffer();
    int i = 0;
    int j = paramString.length();
    if (i < j)
    {
      char c = paramString.charAt(i);
      switch (c)
      {
      default:
        localStringBuffer.append(c);
      case '&':
      case '<':
      case '>':
      case '"':
      }
      while (true)
      {
        i++;
        break;
        localStringBuffer.append("&amp;");
        continue;
        localStringBuffer.append("&lt;");
        continue;
        localStringBuffer.append("&gt;");
        continue;
        localStringBuffer.append("&quot;");
      }
    }
    return localStringBuffer.toString();
  }

  private static boolean parse(XMLTokener paramXMLTokener, JSONObject paramJSONObject, String paramString)
    throws JSONException
  {
    int i = 1;
    Object localObject1 = paramXMLTokener.nextToken();
    int j;
    if (localObject1 == BANG)
    {
      j = paramXMLTokener.next();
      if (j == 45)
        if (paramXMLTokener.next() == '-')
        {
          paramXMLTokener.skipPast("-->");
          i = 0;
        }
    }
    do
    {
      return i;
      paramXMLTokener.back();
      int k = i;
      while (true)
      {
        Object localObject7 = paramXMLTokener.nextMeta();
        if (localObject7 == null)
        {
          throw paramXMLTokener.syntaxError("Missing '>' after '<!'.");
          if (j != 91)
            break;
          if ((paramXMLTokener.nextToken().equals("CDATA")) && (paramXMLTokener.next() == '['))
          {
            String str4 = paramXMLTokener.nextCDATA();
            if (str4.length() > 0)
              paramJSONObject.accumulate("content", str4);
            return false;
          }
          throw paramXMLTokener.syntaxError("Expected 'CDATA['");
        }
        if (localObject7 == LT)
          k++;
        while (k <= 0)
        {
          return false;
          if (localObject7 == GT)
            k--;
        }
      }
      if (localObject1 == QUEST)
      {
        paramXMLTokener.skipPast("?>");
        return false;
      }
      if (localObject1 != SLASH)
        break;
      Object localObject6 = paramXMLTokener.nextToken();
      if (paramString == null)
        throw paramXMLTokener.syntaxError("Mismatched close tag" + localObject6);
      if (!localObject6.equals(paramString))
        throw paramXMLTokener.syntaxError("Mismatched " + paramString + " and " + localObject6);
    }
    while (paramXMLTokener.nextToken() == GT);
    throw paramXMLTokener.syntaxError("Misshaped close tag");
    if ((localObject1 instanceof Character))
      throw paramXMLTokener.syntaxError("Misshaped tag");
    String str1 = (String)localObject1;
    JSONObject localJSONObject = new JSONObject();
    Object localObject2 = null;
    while (true)
    {
      if (localObject2 == null)
        localObject2 = paramXMLTokener.nextToken();
      if (!(localObject2 instanceof String))
        break;
      String str3 = (String)localObject2;
      Object localObject4 = paramXMLTokener.nextToken();
      if (localObject4 == EQ)
      {
        Object localObject5 = paramXMLTokener.nextToken();
        if (!(localObject5 instanceof String))
          throw paramXMLTokener.syntaxError("Missing value");
        localJSONObject.accumulate(str3, localObject5);
        localObject2 = null;
      }
      else
      {
        localJSONObject.accumulate(str3, "");
        localObject2 = localObject4;
      }
    }
    if (localObject2 == SLASH)
    {
      if (paramXMLTokener.nextToken() != GT)
        throw paramXMLTokener.syntaxError("Misshaped tag");
      paramJSONObject.accumulate(str1, localJSONObject);
      return false;
    }
    if (localObject2 == GT)
    {
      Object localObject3;
      do
        while (true)
        {
          localObject3 = paramXMLTokener.nextContent();
          if (localObject3 == null)
          {
            if (str1 != null)
              throw paramXMLTokener.syntaxError("Unclosed tag " + str1);
            return false;
          }
          if (!(localObject3 instanceof String))
            break;
          String str2 = (String)localObject3;
          if (str2.length() > 0)
            localJSONObject.accumulate("content", str2);
        }
      while ((localObject3 != LT) || (!parse(paramXMLTokener, localJSONObject, str1)));
      if (localJSONObject.length() == 0)
        paramJSONObject.accumulate(str1, "");
      while (true)
      {
        return false;
        if ((localJSONObject.length() == i) && (localJSONObject.opt("content") != null))
          paramJSONObject.accumulate(str1, localJSONObject.opt("content"));
        else
          paramJSONObject.accumulate(str1, localJSONObject);
      }
    }
    throw paramXMLTokener.syntaxError("Misshaped tag");
  }

  public static JSONObject toJSONObject(String paramString)
    throws JSONException
  {
    JSONObject localJSONObject = new JSONObject();
    XMLTokener localXMLTokener = new XMLTokener(paramString);
    while ((localXMLTokener.more()) && (localXMLTokener.skipPast("<")))
      parse(localXMLTokener, localJSONObject, null);
    return localJSONObject;
  }

  public static String toString(Object paramObject)
    throws JSONException
  {
    return toString(paramObject, null);
  }

  public static String toString(Object paramObject, String paramString)
    throws JSONException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    if ((paramObject instanceof JSONObject))
    {
      if (paramString != null)
      {
        localStringBuffer.append('<');
        localStringBuffer.append(paramString);
        localStringBuffer.append('>');
      }
      JSONObject localJSONObject = (JSONObject)paramObject;
      Iterator localIterator = localJSONObject.keys();
      while (localIterator.hasNext())
      {
        String str3 = localIterator.next().toString();
        Object localObject2 = localJSONObject.get(str3);
        if ((localObject2 instanceof String))
          ((String)localObject2);
        if (str3.equals("content"))
        {
          if ((localObject2 instanceof JSONArray))
          {
            JSONArray localJSONArray3 = (JSONArray)localObject2;
            int n = localJSONArray3.length();
            for (int i1 = 0; i1 < n; i1++)
            {
              if (i1 > 0)
                localStringBuffer.append('\n');
              localStringBuffer.append(escape(localJSONArray3.get(i1).toString()));
            }
          }
          else
          {
            localStringBuffer.append(escape(localObject2.toString()));
          }
        }
        else if ((localObject2 instanceof JSONArray))
        {
          JSONArray localJSONArray2 = (JSONArray)localObject2;
          int k = localJSONArray2.length();
          for (int m = 0; m < k; m++)
            localStringBuffer.append(toString(localJSONArray2.get(m), str3));
        }
        else if (localObject2.equals(""))
        {
          localStringBuffer.append('<');
          localStringBuffer.append(str3);
          localStringBuffer.append("/>");
        }
        else
        {
          localStringBuffer.append(toString(localObject2, str3));
        }
      }
      if (paramString != null)
      {
        localStringBuffer.append("</");
        localStringBuffer.append(paramString);
        localStringBuffer.append('>');
      }
      return localStringBuffer.toString();
    }
    if ((paramObject instanceof JSONArray))
    {
      JSONArray localJSONArray1 = (JSONArray)paramObject;
      int i = localJSONArray1.length();
      int j = 0;
      if (j < i)
      {
        Object localObject1 = localJSONArray1.opt(j);
        if (paramString == null);
        for (String str2 = "array"; ; str2 = paramString)
        {
          localStringBuffer.append(toString(localObject1, str2));
          j++;
          break;
        }
      }
      return localStringBuffer.toString();
    }
    if (paramObject == null);
    for (String str1 = "null"; paramString == null; str1 = escape(paramObject.toString()))
      return "\"" + str1 + "\"";
    if (str1.length() == 0)
      return "<" + paramString + "/>";
    return "<" + paramString + ">" + str1 + "</" + paramString + ">";
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.json.XML
 * JD-Core Version:    0.6.2
 */