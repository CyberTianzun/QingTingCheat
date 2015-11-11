package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class ArraySerializer
  implements ObjectSerializer
{
  private final ObjectSerializer compObjectSerializer;
  private final Class<?> componentType;

  public ArraySerializer(Class<?> paramClass, ObjectSerializer paramObjectSerializer)
  {
    this.componentType = paramClass;
    this.compObjectSerializer = paramObjectSerializer;
  }

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
    Object[] arrayOfObject = (Object[])paramObject1;
    int i = arrayOfObject.length;
    SerialContext localSerialContext = paramJSONSerializer.getContext();
    paramJSONSerializer.setContext(localSerialContext, paramObject1, paramObject2);
    while (true)
    {
      int j;
      Object localObject2;
      try
      {
        localSerializeWriter.append('[');
        j = 0;
        if (j >= i)
          break label187;
        if (j != 0)
          localSerializeWriter.append(',');
        localObject2 = arrayOfObject[j];
        if (localObject2 == null)
          localSerializeWriter.append("null");
        else if (localObject2.getClass() == this.componentType)
          this.compObjectSerializer.write(paramJSONSerializer, localObject2, Integer.valueOf(j), null);
      }
      finally
      {
        paramJSONSerializer.setContext(localSerialContext);
      }
      paramJSONSerializer.getObjectWriter(localObject2.getClass()).write(paramJSONSerializer, localObject2, Integer.valueOf(j), null);
      break label202;
      label187: localSerializeWriter.append(']');
      paramJSONSerializer.setContext(localSerialContext);
      return;
      label202: j++;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.ArraySerializer
 * JD-Core Version:    0.6.2
 */