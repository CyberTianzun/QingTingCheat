package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.Type;
import java.util.TimeZone;

public class TimeZoneDeserializer
  implements ObjectDeserializer
{
  public static final TimeZoneDeserializer instance = new TimeZoneDeserializer();

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    String str = (String)paramDefaultJSONParser.parse();
    if (str == null)
      return null;
    return TimeZone.getTimeZone(str);
  }

  public int getFastMatchToken()
  {
    return 4;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.TimeZoneDeserializer
 * JD-Core Version:    0.6.2
 */