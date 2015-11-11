package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class MapSerializer
  implements ObjectSerializer
{
  public static MapSerializer instance = new MapSerializer();

  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    if (paramObject1 == null)
    {
      localSerializeWriter.writeNull();
      return;
    }
    Object localObject1 = (Map)paramObject1;
    if ((localSerializeWriter.isEnabled(SerializerFeature.SortField)) && (!(localObject1 instanceof SortedMap)) && (!(localObject1 instanceof LinkedHashMap)));
    try
    {
      TreeMap localTreeMap = new TreeMap((Map)localObject1);
      localObject1 = localTreeMap;
      label64: if (paramJSONSerializer.containsReference(paramObject1))
      {
        paramJSONSerializer.writeReference(paramObject1);
        return;
      }
      SerialContext localSerialContext = paramJSONSerializer.getContext();
      paramJSONSerializer.setContext(localSerialContext, paramObject1, paramObject2);
      while (true)
      {
        Object localObject3;
        ObjectSerializer localObjectSerializer;
        int i;
        Object localObject4;
        Object localObject5;
        try
        {
          localSerializeWriter.write('{');
          paramJSONSerializer.incrementIndent();
          localObject3 = null;
          localObjectSerializer = null;
          i = 1;
          if (localSerializeWriter.isEnabled(SerializerFeature.WriteClassName))
          {
            localSerializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
            localSerializeWriter.writeString(paramObject1.getClass().getName());
            i = 0;
          }
          Iterator localIterator = ((Map)localObject1).entrySet().iterator();
          if (!localIterator.hasNext())
            break;
          Map.Entry localEntry = (Map.Entry)localIterator.next();
          localObject4 = localEntry.getValue();
          localObject5 = localEntry.getKey();
          if ((localObject5 == null) || ((localObject5 instanceof String)))
          {
            String str1 = (String)localObject5;
            if ((!FilterUtils.applyName(paramJSONSerializer, paramObject1, str1)) || (!FilterUtils.apply(paramJSONSerializer, paramObject1, str1, localObject4)))
              continue;
            String str2 = FilterUtils.processKey(paramJSONSerializer, paramObject1, str1, localObject4);
            localObject4 = FilterUtils.processValue(paramJSONSerializer, paramObject1, str2, localObject4);
            if ((localObject4 == null) && (!paramJSONSerializer.isEnabled(SerializerFeature.WriteMapNullValue)))
              continue;
            if (i == 0)
              localSerializeWriter.write(',');
            if (localSerializeWriter.isEnabled(SerializerFeature.PrettyFormat))
              paramJSONSerializer.println();
            localSerializeWriter.writeFieldName(str2, true);
            if (localObject4 != null)
              break label369;
            localSerializeWriter.writeNull();
            i = 0;
            continue;
          }
        }
        finally
        {
          paramJSONSerializer.setContext(localSerialContext);
        }
        if (i == 0)
          localSerializeWriter.write(',');
        paramJSONSerializer.write(localObject5);
        localSerializeWriter.write(':');
        continue;
        label369: Class localClass = localObject4.getClass();
        if (localClass == localObject3)
        {
          localObjectSerializer.write(paramJSONSerializer, localObject4, localObject5, null);
          i = 0;
        }
        else
        {
          localObject3 = localClass;
          localObjectSerializer = paramJSONSerializer.getObjectWriter(localClass);
          localObjectSerializer.write(paramJSONSerializer, localObject4, localObject5, null);
          i = 0;
        }
      }
      paramJSONSerializer.setContext(localSerialContext);
      paramJSONSerializer.decrementIdent();
      if ((localSerializeWriter.isEnabled(SerializerFeature.PrettyFormat)) && (((Map)localObject1).size() > 0))
        paramJSONSerializer.println();
      localSerializeWriter.write('}');
      return;
    }
    catch (Exception localException)
    {
      break label64;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.MapSerializer
 * JD-Core Version:    0.6.2
 */