package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import java.awt.Font;
import java.lang.reflect.Type;

public class FontDeserializer
  implements ObjectDeserializer
{
  public static final FontDeserializer instance = new FontDeserializer();

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    if (localJSONLexer.token() == 8)
    {
      localJSONLexer.nextToken(16);
      return null;
    }
    if ((localJSONLexer.token() != 12) && (localJSONLexer.token() != 16))
      throw new JSONException("syntax error");
    localJSONLexer.nextToken();
    int i = 0;
    int j = 0;
    String str1 = null;
    String str2;
    label308: 
    while (true)
    {
      if (localJSONLexer.token() == 13)
      {
        localJSONLexer.nextToken();
        return new Font(str1, j, i);
      }
      if (localJSONLexer.token() == 4)
      {
        str2 = localJSONLexer.stringVal();
        localJSONLexer.nextTokenWithColon(2);
        if (!str2.equalsIgnoreCase("name"))
          break label220;
        if (localJSONLexer.token() != 4)
          break label210;
        str1 = localJSONLexer.stringVal();
        localJSONLexer.nextToken();
      }
      while (true)
      {
        if (localJSONLexer.token() != 16)
          break label308;
        localJSONLexer.nextToken(4);
        break;
        throw new JSONException("syntax error");
        label210: throw new JSONException("syntax error");
        label220: if (str2.equalsIgnoreCase("style"))
        {
          if (localJSONLexer.token() == 2)
          {
            j = localJSONLexer.intValue();
            localJSONLexer.nextToken();
          }
          else
          {
            throw new JSONException("syntax error");
          }
        }
        else
        {
          if (!str2.equalsIgnoreCase("size"))
            break label320;
          if (localJSONLexer.token() != 2)
            break label310;
          i = localJSONLexer.intValue();
          localJSONLexer.nextToken();
        }
      }
    }
    label310: throw new JSONException("syntax error");
    label320: throw new JSONException("syntax error, " + str2);
  }

  public int getFastMatchToken()
  {
    return 12;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.FontDeserializer
 * JD-Core Version:    0.6.2
 */