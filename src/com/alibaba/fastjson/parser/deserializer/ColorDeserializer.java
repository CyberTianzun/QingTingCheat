package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import java.awt.Color;
import java.lang.reflect.Type;

public class ColorDeserializer
  implements ObjectDeserializer
{
  public static final ColorDeserializer instance = new ColorDeserializer();

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    if ((localJSONLexer.token() != 12) && (localJSONLexer.token() != 16))
      throw new JSONException("syntax error");
    localJSONLexer.nextToken();
    int i = 0;
    int j = 0;
    int k = 0;
    int m = 0;
    String str;
    label196: label206: label255: 
    while (true)
    {
      if (localJSONLexer.token() == 13)
      {
        localJSONLexer.nextToken();
        return new Color(i, j, k, m);
      }
      int n;
      if (localJSONLexer.token() == 4)
      {
        str = localJSONLexer.stringVal();
        localJSONLexer.nextTokenWithColon(2);
        if (localJSONLexer.token() != 2)
          break label196;
        n = localJSONLexer.intValue();
        localJSONLexer.nextToken();
        if (!str.equalsIgnoreCase("r"))
          break label206;
        i = n;
      }
      while (true)
      {
        if (localJSONLexer.token() != 16)
          break label255;
        localJSONLexer.nextToken(4);
        break;
        throw new JSONException("syntax error");
        throw new JSONException("syntax error");
        if (str.equalsIgnoreCase("g"))
        {
          j = n;
        }
        else if (str.equalsIgnoreCase("b"))
        {
          k = n;
        }
        else
        {
          if (!str.equalsIgnoreCase("alpha"))
            break label257;
          m = n;
        }
      }
    }
    label257: throw new JSONException("syntax error, " + str);
  }

  public int getFastMatchToken()
  {
    return 12;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.ColorDeserializer
 * JD-Core Version:    0.6.2
 */