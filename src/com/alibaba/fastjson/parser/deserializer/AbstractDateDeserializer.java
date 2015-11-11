package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONScanner;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.util.Calendar;

public abstract class AbstractDateDeserializer
  implements ObjectDeserializer
{
  protected abstract <T> T cast(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject1, Object paramObject2);

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    Object localObject;
    if (localJSONLexer.token() == 2)
    {
      localObject = Long.valueOf(localJSONLexer.longValue());
      localJSONLexer.nextToken(16);
    }
    while (true)
    {
      return cast(paramDefaultJSONParser, paramType, paramObject, localObject);
      if (localJSONLexer.token() == 4)
      {
        String str2 = localJSONLexer.stringVal();
        localObject = str2;
        localJSONLexer.nextToken(16);
        if (localJSONLexer.isEnabled(Feature.AllowISO8601DateFormat))
        {
          JSONScanner localJSONScanner = new JSONScanner(str2);
          if (localJSONScanner.scanISO8601DateIfMatch())
            localObject = localJSONScanner.getCalendar().getTime();
          localJSONScanner.close();
        }
      }
      else if (localJSONLexer.token() == 8)
      {
        localJSONLexer.nextToken();
        localObject = null;
      }
      else if (localJSONLexer.token() == 12)
      {
        localJSONLexer.nextToken();
        if (localJSONLexer.token() == 4)
        {
          String str1 = localJSONLexer.stringVal();
          if (JSON.DEFAULT_TYPE_KEY.equals(str1))
          {
            localJSONLexer.nextToken();
            paramDefaultJSONParser.accept(17);
            Class localClass = TypeUtils.loadClass(localJSONLexer.stringVal());
            if (localClass != null)
              paramType = localClass;
            paramDefaultJSONParser.accept(4);
            paramDefaultJSONParser.accept(16);
          }
          localJSONLexer.nextTokenWithColon(2);
          if (localJSONLexer.token() == 2)
          {
            long l = localJSONLexer.longValue();
            localJSONLexer.nextToken();
            localObject = Long.valueOf(l);
            paramDefaultJSONParser.accept(13);
          }
        }
        else
        {
          throw new JSONException("syntax error");
          throw new JSONException("syntax error : " + localJSONLexer.tokenName());
        }
      }
      else if (paramDefaultJSONParser.getResolveStatus() == 2)
      {
        paramDefaultJSONParser.setResolveStatus(0);
        paramDefaultJSONParser.accept(16);
        if (localJSONLexer.token() == 4)
        {
          if (!"val".equals(localJSONLexer.stringVal()))
            throw new JSONException("syntax error");
          localJSONLexer.nextToken();
          paramDefaultJSONParser.accept(17);
          localObject = paramDefaultJSONParser.parse();
          paramDefaultJSONParser.accept(13);
        }
        else
        {
          throw new JSONException("syntax error");
        }
      }
      else
      {
        localObject = paramDefaultJSONParser.parse();
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.AbstractDateDeserializer
 * JD-Core Version:    0.6.2
 */