package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class ByteSerializer
  implements ObjectSerializer
{
  public static ByteSerializer instance = new ByteSerializer();

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
    localSerializeWriter.write('B');
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.ByteSerializer
 * JD-Core Version:    0.6.2
 */