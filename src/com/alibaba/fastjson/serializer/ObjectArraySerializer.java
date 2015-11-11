package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class ObjectArraySerializer
  implements ObjectSerializer
{
  public static final ObjectArraySerializer instance = new ObjectArraySerializer();

  public final void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    Object[] arrayOfObject = (Object[])paramObject1;
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
    int i = arrayOfObject.length;
    int j = i - 1;
    if (j == -1)
    {
      localSerializeWriter.append("[]");
      return;
    }
    SerialContext localSerialContext = paramJSONSerializer.getContext();
    paramJSONSerializer.setContext(localSerialContext, paramObject1, paramObject2);
    Object localObject1 = null;
    ObjectSerializer localObjectSerializer = null;
    while (true)
    {
      int k;
      Object localObject4;
      try
      {
        localSerializeWriter.append('[');
        if (localSerializeWriter.isEnabled(SerializerFeature.PrettyFormat))
        {
          paramJSONSerializer.incrementIndent();
          paramJSONSerializer.println();
          int m = 0;
          if (m < i)
          {
            if (m != 0)
            {
              localSerializeWriter.write(',');
              paramJSONSerializer.println();
            }
            paramJSONSerializer.write(arrayOfObject[m]);
            m++;
            continue;
          }
          paramJSONSerializer.decrementIdent();
          paramJSONSerializer.println();
          localSerializeWriter.write(']');
          return;
        }
        k = 0;
        if (k >= j)
          break label306;
        localObject4 = arrayOfObject[k];
        if (localObject4 == null)
        {
          localSerializeWriter.append("null,");
        }
        else if (paramJSONSerializer.containsReference(localObject4))
        {
          paramJSONSerializer.writeReference(localObject4);
          localSerializeWriter.append(',');
        }
      }
      finally
      {
        paramJSONSerializer.setContext(localSerialContext);
      }
      Class localClass = localObject4.getClass();
      if (localClass == localObject1)
      {
        localObjectSerializer.write(paramJSONSerializer, localObject4, null, null);
      }
      else
      {
        localObject1 = localClass;
        localObjectSerializer = paramJSONSerializer.getObjectWriter(localClass);
        localObjectSerializer.write(paramJSONSerializer, localObject4, null, null);
        continue;
        label306: Object localObject3 = arrayOfObject[j];
        if (localObject3 == null)
        {
          localSerializeWriter.append("null]");
          paramJSONSerializer.setContext(localSerialContext);
          return;
        }
        if (paramJSONSerializer.containsReference(localObject3))
          paramJSONSerializer.writeReference(localObject3);
        while (true)
        {
          localSerializeWriter.append(']');
          break;
          paramJSONSerializer.writeWithFieldName(localObject3, Integer.valueOf(j));
        }
        k++;
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.ObjectArraySerializer
 * JD-Core Version:    0.6.2
 */