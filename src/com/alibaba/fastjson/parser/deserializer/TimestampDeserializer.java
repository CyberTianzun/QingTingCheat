package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class TimestampDeserializer extends AbstractDateDeserializer
  implements ObjectDeserializer
{
  public static final TimestampDeserializer instance = new TimestampDeserializer();

  protected <T> T cast(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject1, Object paramObject2)
  {
    if (paramObject2 == null);
    String str;
    do
    {
      return null;
      if ((paramObject2 instanceof Date))
        return new Timestamp(((Date)paramObject2).getTime());
      if ((paramObject2 instanceof Number))
        return new Timestamp(((Number)paramObject2).longValue());
      if (!(paramObject2 instanceof String))
        break;
      str = (String)paramObject2;
    }
    while (str.length() == 0);
    DateFormat localDateFormat = paramDefaultJSONParser.getDateFormat();
    try
    {
      Timestamp localTimestamp = new Timestamp(localDateFormat.parse(str).getTime());
      return localTimestamp;
    }
    catch (ParseException localParseException)
    {
      return new Timestamp(Long.parseLong(str));
    }
    throw new JSONException("parse error");
  }

  public int getFastMatchToken()
  {
    return 2;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.TimestampDeserializer
 * JD-Core Version:    0.6.2
 */