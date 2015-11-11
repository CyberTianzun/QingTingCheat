package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.Type;

public class JSONArrayDeserializer
  implements ObjectDeserializer
{
  public static final JSONArrayDeserializer instance = new JSONArrayDeserializer();

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONArray localJSONArray = new JSONArray();
    paramDefaultJSONParser.parseArray(localJSONArray);
    return localJSONArray;
  }

  public int getFastMatchToken()
  {
    return 14;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.JSONArrayDeserializer
 * JD-Core Version:    0.6.2
 */