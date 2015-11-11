package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class BooleanArraySerializer
  implements ObjectSerializer
{
  public static BooleanArraySerializer instance = new BooleanArraySerializer();

  public final void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
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
    boolean[] arrayOfBoolean = (boolean[])paramObject1;
    localSerializeWriter.write('[');
    for (int i = 0; i < arrayOfBoolean.length; i++)
    {
      if (i != 0)
        localSerializeWriter.write(',');
      localSerializeWriter.write(arrayOfBoolean[i]);
    }
    localSerializeWriter.write(']');
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.BooleanArraySerializer
 * JD-Core Version:    0.6.2
 */