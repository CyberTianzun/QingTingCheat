package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.math.BigDecimal;

public class BigDecimalSerializer
  implements ObjectSerializer
{
  public static final BigDecimalSerializer instance = new BigDecimalSerializer();

  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    if (paramObject1 == null)
      if (localSerializeWriter.isEnabled(SerializerFeature.WriteNullNumberAsZero))
        localSerializeWriter.write('0');
    BigDecimal localBigDecimal;
    do
    {
      return;
      localSerializeWriter.writeNull();
      return;
      localBigDecimal = (BigDecimal)paramObject1;
      localSerializeWriter.write(localBigDecimal.toString());
    }
    while ((!localSerializeWriter.isEnabled(SerializerFeature.WriteClassName)) || (paramType == BigDecimal.class) || (localBigDecimal.scale() != 0));
    localSerializeWriter.write('.');
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.BigDecimalSerializer
 * JD-Core Version:    0.6.2
 */