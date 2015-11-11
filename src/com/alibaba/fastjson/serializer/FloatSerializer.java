package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class FloatSerializer
  implements ObjectSerializer
{
  public static FloatSerializer instance = new FloatSerializer();

  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    if (paramObject1 == null)
      if (paramJSONSerializer.isEnabled(SerializerFeature.WriteNullNumberAsZero))
        localSerializeWriter.write('0');
    do
    {
      return;
      localSerializeWriter.writeNull();
      return;
      float f = ((Float)paramObject1).floatValue();
      if (Float.isNaN(f))
      {
        localSerializeWriter.writeNull();
        return;
      }
      if (Float.isInfinite(f))
      {
        localSerializeWriter.writeNull();
        return;
      }
      String str = Float.toString(f);
      if (str.endsWith(".0"))
        str = str.substring(0, -2 + str.length());
      localSerializeWriter.write(str);
    }
    while (!paramJSONSerializer.isEnabled(SerializerFeature.WriteClassName));
    localSerializeWriter.write('F');
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.FloatSerializer
 * JD-Core Version:    0.6.2
 */