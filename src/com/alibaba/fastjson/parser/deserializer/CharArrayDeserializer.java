package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import java.lang.reflect.Type;

public class CharArrayDeserializer
  implements ObjectDeserializer
{
  public static final CharArrayDeserializer instance = new CharArrayDeserializer();

  public static <T> T deserialze(DefaultJSONParser paramDefaultJSONParser)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    if (localJSONLexer.token() == 4)
    {
      String str = localJSONLexer.stringVal();
      localJSONLexer.nextToken(16);
      return str.toCharArray();
    }
    if (localJSONLexer.token() == 2)
    {
      Number localNumber = localJSONLexer.integerValue();
      localJSONLexer.nextToken(16);
      return localNumber.toString().toCharArray();
    }
    Object localObject = paramDefaultJSONParser.parse();
    if (localObject == null)
      return null;
    return JSON.toJSONString(localObject).toCharArray();
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
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.CharArrayDeserializer
 * JD-Core Version:    0.6.2
 */