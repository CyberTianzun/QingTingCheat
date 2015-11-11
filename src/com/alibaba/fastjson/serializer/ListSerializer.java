package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public final class ListSerializer
  implements ObjectSerializer
{
  public static final ListSerializer instance = new ListSerializer();

  public final void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    boolean bool1 = paramJSONSerializer.isEnabled(SerializerFeature.WriteClassName);
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    Type localType = null;
    if (bool1)
    {
      boolean bool2 = paramType instanceof ParameterizedType;
      localType = null;
      if (bool2)
        localType = ((ParameterizedType)paramType).getActualTypeArguments()[0];
    }
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
    List localList = (List)paramObject1;
    int i = localList.size();
    int j = i - 1;
    if (j == -1)
    {
      localSerializeWriter.append("[]");
      return;
    }
    SerialContext localSerialContext = paramJSONSerializer.getContext();
    paramJSONSerializer.setContext(localSerialContext, paramObject1, paramObject2);
    int m;
    if (i > 1)
    {
      try
      {
        if (!localSerializeWriter.isEnabled(SerializerFeature.PrettyFormat))
          break label311;
        localSerializeWriter.append('[');
        paramJSONSerializer.incrementIndent();
        m = 0;
        if (m >= i)
          break label288;
        if (m != 0)
          localSerializeWriter.append(',');
        paramJSONSerializer.println();
        Object localObject4 = localList.get(m);
        if (localObject4 != null)
          if (paramJSONSerializer.containsReference(localObject4))
          {
            paramJSONSerializer.writeReference(localObject4);
          }
          else
          {
            ObjectSerializer localObjectSerializer = paramJSONSerializer.getObjectWriter(localObject4.getClass());
            paramJSONSerializer.setContext(new SerialContext(localSerialContext, paramObject1, paramObject2));
            localObjectSerializer.write(paramJSONSerializer, localObject4, Integer.valueOf(m), localType);
          }
      }
      finally
      {
        paramJSONSerializer.setContext(localSerialContext);
      }
      paramJSONSerializer.getWriter().writeNull();
      break label695;
      label288: paramJSONSerializer.decrementIdent();
      paramJSONSerializer.println();
      localSerializeWriter.append(']');
      paramJSONSerializer.setContext(localSerialContext);
    }
    else
    {
      label311: localSerializeWriter.append('[');
    }
    for (int k = 0; ; k++)
      if (k < j)
      {
        Object localObject1 = localList.get(k);
        if (localObject1 == null)
        {
          localSerializeWriter.append("null,");
        }
        else
        {
          Class localClass1 = localObject1.getClass();
          if (localClass1 == Integer.class)
          {
            localSerializeWriter.writeIntAndChar(((Integer)localObject1).intValue(), ',');
          }
          else if (localClass1 == Long.class)
          {
            long l = ((Long)localObject1).longValue();
            if (bool1)
            {
              localSerializeWriter.writeLongAndChar(l, 'L');
              localSerializeWriter.write(',');
            }
            else
            {
              localSerializeWriter.writeLongAndChar(l, ',');
            }
          }
          else
          {
            paramJSONSerializer.setContext(new SerialContext(localSerialContext, paramObject1, paramObject2));
            if (paramJSONSerializer.containsReference(localObject1))
              paramJSONSerializer.writeReference(localObject1);
            while (true)
            {
              localSerializeWriter.append(',');
              break;
              paramJSONSerializer.getObjectWriter(localObject1.getClass()).write(paramJSONSerializer, localObject1, Integer.valueOf(k), localType);
            }
          }
        }
      }
      else
      {
        Object localObject3 = localList.get(j);
        if (localObject3 == null)
          localSerializeWriter.append("null]");
        while (true)
        {
          paramJSONSerializer.setContext(localSerialContext);
          return;
          Class localClass2 = localObject3.getClass();
          if (localClass2 == Integer.class)
          {
            localSerializeWriter.writeIntAndChar(((Integer)localObject3).intValue(), ']');
          }
          else
          {
            if (localClass2 != Long.class)
              break;
            if (bool1)
            {
              localSerializeWriter.writeLongAndChar(((Long)localObject3).longValue(), 'L');
              localSerializeWriter.write(']');
            }
            else
            {
              localSerializeWriter.writeLongAndChar(((Long)localObject3).longValue(), ']');
            }
          }
        }
        paramJSONSerializer.setContext(new SerialContext(localSerialContext, paramObject1, paramObject2));
        if (paramJSONSerializer.containsReference(localObject3))
          paramJSONSerializer.writeReference(localObject3);
        while (true)
        {
          localSerializeWriter.append(']');
          break;
          paramJSONSerializer.getObjectWriter(localObject3.getClass()).write(paramJSONSerializer, localObject3, Integer.valueOf(j), localType);
        }
        label695: m++;
        break;
      }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.ListSerializer
 * JD-Core Version:    0.6.2
 */