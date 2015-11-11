package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

public class BooleanDeserializer
  implements ObjectDeserializer
{
  public static final BooleanDeserializer instance = new BooleanDeserializer();

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    Object localObject2;
    if (localJSONLexer.token() == 6)
    {
      localJSONLexer.nextToken(16);
      localObject2 = Boolean.TRUE;
    }
    while (true)
    {
      if (paramType == AtomicBoolean.class)
        localObject2 = new AtomicBoolean(((Boolean)localObject2).booleanValue());
      return localObject2;
      if (localJSONLexer.token() == 7)
      {
        localJSONLexer.nextToken(16);
        localObject2 = Boolean.FALSE;
      }
      else if (localJSONLexer.token() == 2)
      {
        int i = localJSONLexer.intValue();
        localJSONLexer.nextToken(16);
        if (i == 1)
          localObject2 = Boolean.TRUE;
        else
          localObject2 = Boolean.FALSE;
      }
      else
      {
        Object localObject1 = paramDefaultJSONParser.parse();
        if (localObject1 == null)
          return null;
        localObject2 = TypeUtils.castToBoolean(localObject1);
      }
    }
  }

  public int getFastMatchToken()
  {
    return 6;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.BooleanDeserializer
 * JD-Core Version:    0.6.2
 */