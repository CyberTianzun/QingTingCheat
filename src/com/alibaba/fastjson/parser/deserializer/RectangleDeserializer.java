package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import java.awt.Rectangle;
import java.lang.reflect.Type;

public class RectangleDeserializer
  implements ObjectDeserializer
{
  public static final RectangleDeserializer instance = new RectangleDeserializer();

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    if (localJSONLexer.token() == 8)
    {
      localJSONLexer.nextToken();
      return null;
    }
    if ((localJSONLexer.token() != 12) && (localJSONLexer.token() != 16))
      throw new JSONException("syntax error");
    localJSONLexer.nextToken();
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    String str;
    label276: 
    while (true)
    {
      if (localJSONLexer.token() == 13)
      {
        localJSONLexer.nextToken();
        return new Rectangle(i, j, k, m);
      }
      int n;
      if (localJSONLexer.token() == 4)
      {
        str = localJSONLexer.stringVal();
        localJSONLexer.nextTokenWithColon(2);
        if (localJSONLexer.token() != 2)
          break label217;
        n = localJSONLexer.intValue();
        localJSONLexer.nextToken();
        if (!str.equalsIgnoreCase("x"))
          break label227;
        i = n;
      }
      while (true)
      {
        if (localJSONLexer.token() != 16)
          break label276;
        localJSONLexer.nextToken(4);
        break;
        throw new JSONException("syntax error");
        label217: throw new JSONException("syntax error");
        label227: if (str.equalsIgnoreCase("y"))
        {
          j = n;
        }
        else if (str.equalsIgnoreCase("width"))
        {
          k = n;
        }
        else
        {
          if (!str.equalsIgnoreCase("height"))
            break label278;
          m = n;
        }
      }
    }
    label278: throw new JSONException("syntax error, " + str);
  }

  public int getFastMatchToken()
  {
    return 12;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.RectangleDeserializer
 * JD-Core Version:    0.6.2
 */