package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.math.BigDecimal;

public class NumberDeserializer
  implements ObjectDeserializer
{
  public static final NumberDeserializer instance = new NumberDeserializer();

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    Object localObject2;
    if (localJSONLexer.token() == 2)
      if ((paramType == Double.TYPE) || (paramType == Double.class))
      {
        String str2 = localJSONLexer.numberString();
        localJSONLexer.nextToken(16);
        localObject2 = Double.valueOf(Double.parseDouble(str2));
      }
    do
    {
      return localObject2;
      long l = localJSONLexer.longValue();
      localJSONLexer.nextToken(16);
      if ((paramType == Short.TYPE) || (paramType == Short.class))
        return Short.valueOf((short)(int)l);
      if ((paramType == Byte.TYPE) || (paramType == Byte.class))
        return Byte.valueOf((byte)(int)l);
      if ((l >= -2147483648L) && (l <= 2147483647L))
        return Integer.valueOf((int)l);
      return Long.valueOf(l);
      if (localJSONLexer.token() != 3)
        break;
      if ((paramType == Double.TYPE) || (paramType == Double.class))
      {
        String str1 = localJSONLexer.numberString();
        localJSONLexer.nextToken(16);
        return Double.valueOf(Double.parseDouble(str1));
      }
      localObject2 = localJSONLexer.decimalValue();
      localJSONLexer.nextToken(16);
      if ((paramType == Short.TYPE) || (paramType == Short.class))
        return Short.valueOf(((BigDecimal)localObject2).shortValue());
    }
    while ((paramType != Byte.TYPE) && (paramType != Byte.class));
    return Byte.valueOf(((BigDecimal)localObject2).byteValue());
    Object localObject1 = paramDefaultJSONParser.parse();
    if (localObject1 == null)
      return null;
    if ((paramType == Double.TYPE) || (paramType == Double.class))
      return TypeUtils.castToDouble(localObject1);
    if ((paramType == Short.TYPE) || (paramType == Short.class))
      return TypeUtils.castToShort(localObject1);
    if ((paramType == Byte.TYPE) || (paramType == Byte.class))
      return TypeUtils.castToByte(localObject1);
    return TypeUtils.castToBigDecimal(localObject1);
  }

  public int getFastMatchToken()
  {
    return 2;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.NumberDeserializer
 * JD-Core Version:    0.6.2
 */