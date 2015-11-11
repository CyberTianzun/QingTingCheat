package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicIntegerArray;

public class AtomicIntegerArraySerializer
  implements ObjectSerializer
{
  public static final AtomicIntegerArraySerializer instance = new AtomicIntegerArraySerializer();

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
    AtomicIntegerArray localAtomicIntegerArray = (AtomicIntegerArray)paramObject1;
    int i = localAtomicIntegerArray.length();
    localSerializeWriter.append('[');
    for (int j = 0; j < i; j++)
    {
      int k = localAtomicIntegerArray.get(j);
      if (j != 0)
        localSerializeWriter.write(',');
      localSerializeWriter.writeInt(k);
    }
    localSerializeWriter.append(']');
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.AtomicIntegerArraySerializer
 * JD-Core Version:    0.6.2
 */