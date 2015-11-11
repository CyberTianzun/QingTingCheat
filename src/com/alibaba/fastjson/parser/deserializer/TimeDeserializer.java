package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONScanner;
import java.lang.reflect.Type;
import java.sql.Time;
import java.util.Calendar;

public class TimeDeserializer
  implements ObjectDeserializer
{
  public static final TimeDeserializer instance = new TimeDeserializer();

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    Object localObject;
    if (localJSONLexer.token() == 16)
    {
      localJSONLexer.nextToken(4);
      if (localJSONLexer.token() != 4)
        throw new JSONException("syntax error");
      localJSONLexer.nextTokenWithColon(2);
      if (localJSONLexer.token() != 2)
        throw new JSONException("syntax error");
      long l2 = localJSONLexer.longValue();
      localJSONLexer.nextToken(13);
      if (localJSONLexer.token() != 13)
        throw new JSONException("syntax error");
      localJSONLexer.nextToken(16);
      localObject = new Time(l2);
    }
    do
    {
      return localObject;
      localObject = paramDefaultJSONParser.parse();
      if (localObject == null)
        return null;
    }
    while ((localObject instanceof Time));
    if ((localObject instanceof Number))
      return new Time(((Number)localObject).longValue());
    if ((localObject instanceof String))
    {
      String str = (String)localObject;
      if (str.length() == 0)
        return null;
      JSONScanner localJSONScanner = new JSONScanner(str);
      if (localJSONScanner.scanISO8601DateIfMatch());
      for (long l1 = localJSONScanner.getCalendar().getTimeInMillis(); ; l1 = Long.parseLong(str))
      {
        localJSONScanner.close();
        return new Time(l1);
      }
    }
    throw new JSONException("parse error");
  }

  public int getFastMatchToken()
  {
    return 2;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.TimeDeserializer
 * JD-Core Version:    0.6.2
 */