package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicLong;

public class LongDeserializer
  implements ObjectDeserializer
{
  public static final LongDeserializer instance = new LongDeserializer();

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    long l;
    if (localJSONLexer.token() == 2)
    {
      l = localJSONLexer.longValue();
      localJSONLexer.nextToken(16);
    }
    Object localObject1;
    for (Object localObject2 = Long.valueOf(l); ; localObject2 = TypeUtils.castToLong(localObject1))
    {
      if (paramType == AtomicLong.class)
        localObject2 = new AtomicLong(((Long)localObject2).longValue());
      return localObject2;
      localObject1 = paramDefaultJSONParser.parse();
      if (localObject1 == null)
        return null;
    }
  }

  public int getFastMatchToken()
  {
    return 2;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.LongDeserializer
 * JD-Core Version:    0.6.2
 */