package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicLongArray;

public class AtomicLongArraySerializer
  implements ObjectSerializer
{
  public static final AtomicLongArraySerializer instance = new AtomicLongArraySerializer();

  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    if (paramObject1 == null)
    {
      if (localSerializeWriter.isEnabled(SerializerFeature.WriteNullListAsEmpty))
      {
        localSerializeWriter.write("[]");
        return;
      }
      localSerializeWriter.writeNull();
      return;
    }
    AtomicLongArray localAtomicLongArray = (AtomicLongArray)paramObject1;
    int i = localAtomicLongArray.length();
    localSerializeWriter.append('[');
    for (int j = 0; j < i; j++)
    {
      long l = localAtomicLongArray.get(j);
      if (j != 0)
        localSerializeWriter.write(',');
      localSerializeWriter.writeLong(l);
    }
    localSerializeWriter.append(']');
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.AtomicLongArraySerializer
 * JD-Core Version:    0.6.2
 */