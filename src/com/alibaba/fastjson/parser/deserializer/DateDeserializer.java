package com.alibaba.fastjson.parser.deserializer;

public class DateDeserializer extends AbstractDateDeserializer
  implements ObjectDeserializer
{
  public static final DateDeserializer instance = new DateDeserializer();

  // ERROR //
  protected <T> T cast(com.alibaba.fastjson.parser.DefaultJSONParser paramDefaultJSONParser, java.lang.reflect.Type paramType, java.lang.Object paramObject1, java.lang.Object paramObject2)
  {
    // Byte code:
    //   0: aload 4
    //   2: ifnonnull +9 -> 11
    //   5: aconst_null
    //   6: astore 4
    //   8: aload 4
    //   10: areturn
    //   11: aload 4
    //   13: instanceof 22
    //   16: ifne -8 -> 8
    //   19: aload 4
    //   21: instanceof 24
    //   24: ifeq +19 -> 43
    //   27: new 22	java/util/Date
    //   30: dup
    //   31: aload 4
    //   33: checkcast 24	java/lang/Number
    //   36: invokevirtual 28	java/lang/Number:longValue	()J
    //   39: invokespecial 31	java/util/Date:<init>	(J)V
    //   42: areturn
    //   43: aload 4
    //   45: instanceof 33
    //   48: ifeq +106 -> 154
    //   51: aload 4
    //   53: checkcast 33	java/lang/String
    //   56: astore 5
    //   58: aload 5
    //   60: invokevirtual 37	java/lang/String:length	()I
    //   63: ifne +5 -> 68
    //   66: aconst_null
    //   67: areturn
    //   68: new 39	com/alibaba/fastjson/parser/JSONScanner
    //   71: dup
    //   72: aload 5
    //   74: invokespecial 42	com/alibaba/fastjson/parser/JSONScanner:<init>	(Ljava/lang/String;)V
    //   77: astore 6
    //   79: aload 6
    //   81: iconst_0
    //   82: invokevirtual 46	com/alibaba/fastjson/parser/JSONScanner:scanISO8601DateIfMatch	(Z)Z
    //   85: ifeq +21 -> 106
    //   88: aload 6
    //   90: invokevirtual 50	com/alibaba/fastjson/parser/JSONScanner:getCalendar	()Ljava/util/Calendar;
    //   93: invokevirtual 56	java/util/Calendar:getTime	()Ljava/util/Date;
    //   96: astore 11
    //   98: aload 6
    //   100: invokevirtual 59	com/alibaba/fastjson/parser/JSONScanner:close	()V
    //   103: aload 11
    //   105: areturn
    //   106: aload 6
    //   108: invokevirtual 59	com/alibaba/fastjson/parser/JSONScanner:close	()V
    //   111: aload_1
    //   112: invokevirtual 65	com/alibaba/fastjson/parser/DefaultJSONParser:getDateFormat	()Ljava/text/DateFormat;
    //   115: astore 8
    //   117: aload 8
    //   119: aload 5
    //   121: invokevirtual 71	java/text/DateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
    //   124: astore 10
    //   126: aload 10
    //   128: areturn
    //   129: astore 7
    //   131: aload 6
    //   133: invokevirtual 59	com/alibaba/fastjson/parser/JSONScanner:close	()V
    //   136: aload 7
    //   138: athrow
    //   139: astore 9
    //   141: new 22	java/util/Date
    //   144: dup
    //   145: aload 5
    //   147: invokestatic 77	java/lang/Long:parseLong	(Ljava/lang/String;)J
    //   150: invokespecial 31	java/util/Date:<init>	(J)V
    //   153: areturn
    //   154: new 79	com/alibaba/fastjson/JSONException
    //   157: dup
    //   158: ldc 81
    //   160: invokespecial 82	com/alibaba/fastjson/JSONException:<init>	(Ljava/lang/String;)V
    //   163: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   79	98	129	finally
    //   117	126	139	java/text/ParseException
  }

  public int getFastMatchToken()
  {
    return 2;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.DateDeserializer
 * JD-Core Version:    0.6.2
 */