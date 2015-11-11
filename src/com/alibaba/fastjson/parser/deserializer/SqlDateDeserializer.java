package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONScanner;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;

public class SqlDateDeserializer extends AbstractDateDeserializer
  implements ObjectDeserializer
{
  public static final SqlDateDeserializer instance = new SqlDateDeserializer();

  protected <T> T cast(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject1, Object paramObject2)
  {
    if (paramObject2 == null);
    String str;
    do
    {
      return null;
      if ((paramObject2 instanceof java.util.Date));
      for (java.sql.Date localDate2 = new java.sql.Date(((java.util.Date)paramObject2).getTime()); ; localDate2 = new java.sql.Date(((Number)paramObject2).longValue()))
      {
        return localDate2;
        if (!(paramObject2 instanceof Number))
          break;
      }
      if (!(paramObject2 instanceof String))
        break;
      str = (String)paramObject2;
    }
    while (str.length() == 0);
    JSONScanner localJSONScanner = new JSONScanner(str);
    try
    {
      long l2;
      if (localJSONScanner.scanISO8601DateIfMatch())
      {
        long l3 = localJSONScanner.getCalendar().getTimeInMillis();
        l2 = l3;
      }
      while (true)
      {
        return new java.sql.Date(l2);
        DateFormat localDateFormat = paramDefaultJSONParser.getDateFormat();
        try
        {
          java.sql.Date localDate1 = new java.sql.Date(localDateFormat.parse(str).getTime());
          return localDate1;
        }
        catch (ParseException localParseException)
        {
          long l1 = Long.parseLong(str);
          l2 = l1;
        }
      }
    }
    finally
    {
      localJSONScanner.close();
    }
    throw new JSONException("parse error : " + paramObject2);
  }

  public int getFastMatchToken()
  {
    return 2;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.SqlDateDeserializer
 * JD-Core Version:    0.6.2
 */