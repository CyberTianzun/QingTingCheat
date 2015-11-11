package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicInteger;

public class IntegerDeserializer
  implements ObjectDeserializer
{
  public static final IntegerDeserializer instance = new IntegerDeserializer();

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    Object localObject;
    if (localJSONLexer.token() == 8)
    {
      localJSONLexer.nextToken(16);
      localObject = null;
    }
    while (true)
    {
      return localObject;
      if (localJSONLexer.token() == 2)
      {
        int i = localJSONLexer.intValue();
        localJSONLexer.nextToken(16);
        localObject = Integer.valueOf(i);
      }
      while (paramType == AtomicInteger.class)
      {
        return new AtomicInteger(((Integer)localObject).intValue());
        if (localJSONLexer.token() == 3)
        {
          BigDecimal localBigDecimal = localJSONLexer.decimalValue();
          localJSONLexer.nextToken(16);
          localObject = Integer.valueOf(localBigDecimal.intValue());
        }
        else
        {
          localObject = TypeUtils.castToInt(paramDefaultJSONParser.parse());
        }
      }
    }
  }

  public int getFastMatchToken()
  {
    return 2;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.IntegerDeserializer
 * JD-Core Version:    0.6.2
 */