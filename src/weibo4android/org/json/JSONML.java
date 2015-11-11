package weibo4android.org.json;

import java.util.Iterator;

public class JSONML
{
  private static Object parse(XMLTokener paramXMLTokener, boolean paramBoolean, JSONArray paramJSONArray)
    throws JSONException
  {
    while (true)
    {
      Object localObject1 = paramXMLTokener.nextContent();
      if (localObject1 == XML.LT)
      {
        Object localObject2 = paramXMLTokener.nextToken();
        Object localObject6;
        if ((localObject2 instanceof Character))
        {
          if (localObject2 == XML.SLASH)
          {
            localObject6 = paramXMLTokener.nextToken();
            if (!(localObject6 instanceof String))
              throw new JSONException("Expected a closing name instead of '" + localObject6 + "'.");
            if (paramXMLTokener.nextToken() != XML.GT)
              throw paramXMLTokener.syntaxError("Misshaped close tag");
          }
          else
          {
            if (localObject2 == XML.BANG)
            {
              int i = paramXMLTokener.next();
              if (i == 45)
              {
                if (paramXMLTokener.next() == '-')
                  paramXMLTokener.skipPast("-->");
                paramXMLTokener.back();
                continue;
              }
              if (i == 91)
              {
                if ((paramXMLTokener.nextToken().equals("CDATA")) && (paramXMLTokener.next() == '['))
                {
                  if (paramJSONArray == null)
                    continue;
                  paramJSONArray.put(paramXMLTokener.nextCDATA());
                  continue;
                }
                throw paramXMLTokener.syntaxError("Expected 'CDATA['");
              }
              int j = 1;
              label245: 
              while (true)
              {
                Object localObject7 = paramXMLTokener.nextMeta();
                if (localObject7 == null)
                  throw paramXMLTokener.syntaxError("Missing '>' after '<!'.");
                if (localObject7 == XML.LT)
                  j++;
                while (true)
                {
                  if (j > 0)
                    break label245;
                  break;
                  if (localObject7 == XML.GT)
                    j--;
                }
              }
            }
            if (localObject2 == XML.QUEST)
            {
              paramXMLTokener.skipPast("?>");
              continue;
            }
            throw paramXMLTokener.syntaxError("Misshaped tag");
          }
        }
        else
        {
          if (!(localObject2 instanceof String))
            throw paramXMLTokener.syntaxError("Bad tagName '" + localObject2 + "'.");
          String str1 = (String)localObject2;
          JSONArray localJSONArray = new JSONArray();
          JSONObject localJSONObject = new JSONObject();
          Object localObject3;
          if (paramBoolean)
          {
            localJSONArray.put(str1);
            if (paramJSONArray != null)
              paramJSONArray.put(localJSONArray);
            localObject3 = null;
          }
          while (true)
          {
            if (localObject3 == null)
              localObject3 = paramXMLTokener.nextToken();
            if (localObject3 == null)
            {
              throw paramXMLTokener.syntaxError("Misshaped tag");
              localJSONObject.put("tagName", str1);
              if (paramJSONArray == null)
                break;
              paramJSONArray.put(localJSONObject);
              break;
            }
            if (!(localObject3 instanceof String))
            {
              if ((paramBoolean) && (localJSONObject.length() > 0))
                localJSONArray.put(localJSONObject);
              if (localObject3 != XML.SLASH)
                break label585;
              if (paramXMLTokener.nextToken() == XML.GT)
                break label567;
              throw paramXMLTokener.syntaxError("Misshaped tag");
            }
            String str2 = (String)localObject3;
            if ((!paramBoolean) && ((str2 == "tagName") || (str2 == "childNode")))
              throw paramXMLTokener.syntaxError("Reserved attribute.");
            Object localObject4 = paramXMLTokener.nextToken();
            if (localObject4 == XML.EQ)
            {
              Object localObject5 = paramXMLTokener.nextToken();
              if (!(localObject5 instanceof String))
                throw paramXMLTokener.syntaxError("Missing value");
              localJSONObject.accumulate(str2, JSONObject.stringToValue((String)localObject5));
              localObject3 = null;
            }
            else
            {
              localJSONObject.accumulate(str2, "");
              localObject3 = localObject4;
            }
          }
          label567: if (paramJSONArray == null)
          {
            if (paramBoolean)
            {
              localObject6 = localJSONArray;
              return localObject6;
            }
            return localJSONObject;
            label585: if (localObject3 != XML.GT)
              throw paramXMLTokener.syntaxError("Misshaped tag");
            String str3 = (String)parse(paramXMLTokener, paramBoolean, localJSONArray);
            if (str3 != null)
            {
              if (!str3.equals(str1))
                throw paramXMLTokener.syntaxError("Mismatched '" + str1 + "' and '" + str3 + "'");
              if ((!paramBoolean) && (localJSONArray.length() > 0))
                localJSONObject.put("childNodes", localJSONArray);
              if (paramJSONArray == null)
              {
                if (paramBoolean)
                  return localJSONArray;
                return localJSONObject;
              }
            }
          }
        }
      }
      else if (paramJSONArray != null)
      {
        if ((localObject1 instanceof String))
          localObject1 = JSONObject.stringToValue((String)localObject1);
        paramJSONArray.put(localObject1);
      }
    }
  }

  public static JSONArray toJSONArray(String paramString)
    throws JSONException
  {
    return toJSONArray(new XMLTokener(paramString));
  }

  public static JSONArray toJSONArray(XMLTokener paramXMLTokener)
    throws JSONException
  {
    return (JSONArray)parse(paramXMLTokener, true, null);
  }

  public static JSONObject toJSONObject(String paramString)
    throws JSONException
  {
    return toJSONObject(new XMLTokener(paramString));
  }

  public static JSONObject toJSONObject(XMLTokener paramXMLTokener)
    throws JSONException
  {
    return (JSONObject)parse(paramXMLTokener, false, null);
  }

  public static String toString(JSONArray paramJSONArray)
    throws JSONException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str1 = paramJSONArray.getString(0);
    XML.noSpace(str1);
    String str2 = XML.escape(str1);
    localStringBuffer.append('<');
    localStringBuffer.append(str2);
    Object localObject1 = paramJSONArray.opt(1);
    if ((localObject1 instanceof JSONObject))
    {
      JSONObject localJSONObject = (JSONObject)localObject1;
      Iterator localIterator = localJSONObject.keys();
      while (localIterator.hasNext())
      {
        String str3 = localIterator.next().toString();
        XML.noSpace(str3);
        String str4 = localJSONObject.optString(str3);
        if (str4 != null)
        {
          localStringBuffer.append(' ');
          localStringBuffer.append(XML.escape(str3));
          localStringBuffer.append('=');
          localStringBuffer.append('"');
          localStringBuffer.append(XML.escape(str4));
          localStringBuffer.append('"');
        }
      }
    }
    for (int i = 1; ; i = 2)
    {
      int j = paramJSONArray.length();
      if (i >= j)
      {
        localStringBuffer.append('/');
        localStringBuffer.append('>');
        return localStringBuffer.toString();
      }
      localStringBuffer.append('>');
      int k = i;
      label277: label323: 
      while (true)
      {
        Object localObject2 = paramJSONArray.get(k);
        k++;
        if (localObject2 != null)
        {
          if (!(localObject2 instanceof String))
            break label277;
          localStringBuffer.append(XML.escape(localObject2.toString()));
        }
        while (true)
        {
          if (k < j)
            break label323;
          localStringBuffer.append('<');
          localStringBuffer.append('/');
          localStringBuffer.append(str2);
          localStringBuffer.append('>');
          break;
          if ((localObject2 instanceof JSONObject))
            localStringBuffer.append(toString((JSONObject)localObject2));
          else if ((localObject2 instanceof JSONArray))
            localStringBuffer.append(toString((JSONArray)localObject2));
        }
      }
    }
  }

  public static String toString(JSONObject paramJSONObject)
    throws JSONException
  {
    StringBuffer localStringBuffer = new StringBuffer();
    String str1 = paramJSONObject.optString("tagName");
    if (str1 == null)
      return XML.escape(paramJSONObject.toString());
    XML.noSpace(str1);
    String str2 = XML.escape(str1);
    localStringBuffer.append('<');
    localStringBuffer.append(str2);
    Iterator localIterator = paramJSONObject.keys();
    while (localIterator.hasNext())
    {
      String str3 = localIterator.next().toString();
      if ((!str3.equals("tagName")) && (!str3.equals("childNodes")))
      {
        XML.noSpace(str3);
        String str4 = paramJSONObject.optString(str3);
        if (str4 != null)
        {
          localStringBuffer.append(' ');
          localStringBuffer.append(XML.escape(str3));
          localStringBuffer.append('=');
          localStringBuffer.append('"');
          localStringBuffer.append(XML.escape(str4));
          localStringBuffer.append('"');
        }
      }
    }
    JSONArray localJSONArray = paramJSONObject.optJSONArray("childNodes");
    if (localJSONArray == null)
    {
      localStringBuffer.append('/');
      localStringBuffer.append('>');
    }
    while (true)
    {
      return localStringBuffer.toString();
      localStringBuffer.append('>');
      int i = localJSONArray.length();
      int j = 0;
      if (j < i)
      {
        Object localObject = localJSONArray.get(j);
        if (localObject != null)
        {
          if (!(localObject instanceof String))
            break label263;
          localStringBuffer.append(XML.escape(localObject.toString()));
        }
        while (true)
        {
          j++;
          break;
          label263: if ((localObject instanceof JSONObject))
            localStringBuffer.append(toString((JSONObject)localObject));
          else if ((localObject instanceof JSONArray))
            localStringBuffer.append(toString((JSONArray)localObject));
        }
      }
      localStringBuffer.append('<');
      localStringBuffer.append('/');
      localStringBuffer.append(str2);
      localStringBuffer.append('>');
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     weibo4android.org.json.JSONML
 * JD-Core Version:    0.6.2
 */