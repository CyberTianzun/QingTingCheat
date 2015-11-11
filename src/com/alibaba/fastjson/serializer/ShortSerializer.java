package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class ShortSerializer
  implements ObjectSerializer
{
  public static ShortSerializer instance = new ShortSerializer();

  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    if ((Number)paramObject1 == null)
      if (localSerializeWriter.isEnabled(SerializerFeature.WriteNullNumberAsZero))
        localSerializeWriter.write('0');
    do
    {
      return;
      localSerializeWriter.writeNull();
      return;
      localSerializeWriter.writeInt(((Number)paramObject1).shortValue());
    }
    while (!paramJSONSerializer.isEnabled(SerializerFeature.WriteClassName));
    localSerializeWriter.write('S');
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.ShortSerializer
 * JD-Core Version:    0.6.2
 */