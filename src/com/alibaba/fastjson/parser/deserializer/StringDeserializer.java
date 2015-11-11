package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import java.lang.reflect.Type;

public class StringDeserializer
  implements ObjectDeserializer
{
  public static final StringDeserializer instance = new StringDeserializer();

  public static <T> T deserialze(DefaultJSONParser paramDefaultJSONParser)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    if (localJSONLexer.token() == 4)
    {
      String str2 = localJSONLexer.stringVal();
      localJSONLexer.nextToken(16);
      return str2;
    }
    if (localJSONLexer.token() == 2)
    {
      String str1 = localJSONLexer.numberString();
      localJSONLexer.nextToken(16);
      return str1;
    }
    Object localObject = paramDefaultJSONParser.parse();
    if (localObject == null)
      return null;
    return localObject.toString();
  }

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    return deserialze(paramDefaultJSONParser);
  }

  public int getFastMatchToken()
  {
    return 4;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.StringDeserializer
 * JD-Core Version:    0.6.2
 */