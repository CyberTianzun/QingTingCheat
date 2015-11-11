package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import java.awt.Point;
import java.lang.reflect.Type;

public class PointDeserializer
  implements ObjectDeserializer
{
  public static final PointDeserializer instance = new PointDeserializer();

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
    String str;
    label262: label277: 
    while (true)
    {
      if (localJSONLexer.token() == 13)
      {
        localJSONLexer.nextToken();
        return new Point(i, j);
      }
      int k;
      if (localJSONLexer.token() == 4)
      {
        str = localJSONLexer.stringVal();
        if (JSON.DEFAULT_TYPE_KEY.equals(str))
        {
          paramDefaultJSONParser.acceptType("java.awt.Point");
        }
        else
        {
          localJSONLexer.nextTokenWithColon(2);
          if (localJSONLexer.token() == 2)
          {
            k = localJSONLexer.intValue();
            localJSONLexer.nextToken();
            if (!str.equalsIgnoreCase("x"))
              break label262;
            i = k;
          }
        }
      }
      else
      {
        while (true)
        {
          if (localJSONLexer.token() != 16)
            break label277;
          localJSONLexer.nextToken(4);
          break;
          throw new JSONException("syntax error");
          throw new JSONException("syntax error : " + localJSONLexer.tokenName());
          if (!str.equalsIgnoreCase("y"))
            break label279;
          j = k;
        }
      }
    }
    label279: throw new JSONException("syntax error, " + str);
  }

  public int getFastMatchToken()
  {
    return 12;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.PointDeserializer
 * JD-Core Version:    0.6.2
 */