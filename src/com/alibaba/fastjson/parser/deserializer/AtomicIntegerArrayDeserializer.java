package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArrayDeserializer
  implements ObjectDeserializer
{
  public static final AtomicIntegerArrayDeserializer instance = new AtomicIntegerArrayDeserializer();

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    Object localObject;
    if (paramDefaultJSONParser.getLexer().token() == 8)
    {
      paramDefaultJSONParser.getLexer().nextToken(16);
      localObject = null;
    }
    while (true)
    {
      return localObject;
      JSONArray localJSONArray = new JSONArray();
      paramDefaultJSONParser.parseArray(localJSONArray);
      localObject = new AtomicIntegerArray(localJSONArray.size());
      for (int i = 0; i < localJSONArray.size(); i++)
        ((AtomicIntegerArray)localObject).set(i, localJSONArray.getInteger(i).intValue());
    }
  }

  public int getFastMatchToken()
  {
    return 14;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.AtomicIntegerArrayDeserializer
 * JD-Core Version:    0.6.2
 */