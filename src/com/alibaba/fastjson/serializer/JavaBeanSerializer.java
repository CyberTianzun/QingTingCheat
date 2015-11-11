package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class JavaBeanSerializer
  implements ObjectSerializer
{
  private final FieldSerializer[] getters;
  private final FieldSerializer[] sortedGetters;

  public JavaBeanSerializer(Class<?> paramClass)
  {
    this(paramClass, (Map)null);
  }

  public JavaBeanSerializer(Class<?> paramClass, Map<String, String> paramMap)
  {
    ArrayList localArrayList1 = new ArrayList();
    Iterator localIterator1 = TypeUtils.computeGetters(paramClass, paramMap, false).iterator();
    while (localIterator1.hasNext())
      localArrayList1.add(createFieldSerializer((FieldInfo)localIterator1.next()));
    this.getters = ((FieldSerializer[])localArrayList1.toArray(new FieldSerializer[localArrayList1.size()]));
    ArrayList localArrayList2 = new ArrayList();
    Iterator localIterator2 = TypeUtils.computeGetters(paramClass, paramMap, true).iterator();
    while (localIterator2.hasNext())
      localArrayList2.add(createFieldSerializer((FieldInfo)localIterator2.next()));
    this.sortedGetters = ((FieldSerializer[])localArrayList2.toArray(new FieldSerializer[localArrayList2.size()]));
  }

  public JavaBeanSerializer(Class<?> paramClass, String[] paramArrayOfString)
  {
    this(paramClass, createAliasMap(paramArrayOfString));
  }

  static Map<String, String> createAliasMap(String[] paramArrayOfString)
  {
    HashMap localHashMap = new HashMap();
    int i = paramArrayOfString.length;
    for (int j = 0; j < i; j++)
    {
      String str = paramArrayOfString[j];
      localHashMap.put(str, str);
    }
    return localHashMap;
  }

  public FieldSerializer createFieldSerializer(FieldInfo paramFieldInfo)
  {
    if (paramFieldInfo.getFieldClass() == Number.class)
      return new NumberFieldSerializer(paramFieldInfo);
    return new ObjectFieldSerializer(paramFieldInfo);
  }

  public FieldSerializer[] getGetters()
  {
    return this.getters;
  }

  protected boolean isWriteClassName(JSONSerializer paramJSONSerializer, Object paramObject1, Type paramType, Object paramObject2)
  {
    return paramJSONSerializer.isWriteClassName(paramType, paramObject1);
  }

  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    if (paramObject1 == null)
    {
      localSerializeWriter.writeNull();
      return;
    }
    if (paramJSONSerializer.containsReference(paramObject1))
    {
      writeReference(paramJSONSerializer, paramObject1);
      return;
    }
    FieldSerializer[] arrayOfFieldSerializer;
    if (localSerializeWriter.isEnabled(SerializerFeature.SortField))
      arrayOfFieldSerializer = this.sortedGetters;
    SerialContext localSerialContext;
    boolean bool1;
    label80: char c2;
    label89: int i;
    label179: char c3;
    int j;
    label197: int k;
    FieldSerializer localFieldSerializer;
    label249: Object localObject3;
    while (true)
    {
      localSerialContext = paramJSONSerializer.getContext();
      paramJSONSerializer.setContext(localSerialContext, paramObject1, paramObject2);
      bool1 = paramJSONSerializer.isWriteAsArray(paramObject1, paramType);
      char c1;
      if (bool1)
      {
        c1 = '[';
        if (!bool1)
          break label271;
        c2 = ']';
      }
      try
      {
        localSerializeWriter.append(c1);
        if ((arrayOfFieldSerializer.length > 0) && (localSerializeWriter.isEnabled(SerializerFeature.PrettyFormat)))
        {
          paramJSONSerializer.incrementIndent();
          paramJSONSerializer.println();
        }
        boolean bool2 = isWriteClassName(paramJSONSerializer, paramObject1, paramType, paramObject2);
        i = 0;
        if (!bool2)
          break label578;
        Class localClass = paramObject1.getClass();
        i = 0;
        if (localClass == paramType)
          break label578;
        localSerializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
        paramJSONSerializer.write(paramObject1.getClass());
        i = 1;
        break label578;
        if (FilterUtils.writeBefore(paramJSONSerializer, paramObject1, c3) == ',')
        {
          j = 1;
          break label590;
          if (k >= arrayOfFieldSerializer.length)
            break label602;
          localFieldSerializer = arrayOfFieldSerializer[k];
          if (!paramJSONSerializer.isEnabled(SerializerFeature.SkipTransientField))
            break label290;
          Field localField = localFieldSerializer.getField();
          if (localField == null)
            break label290;
          boolean bool3 = Modifier.isTransient(localField.getModifiers());
          if (!bool3)
            break label290;
        }
        label271: label278: label290: Object localObject2;
        String str;
        do
        {
          do
          {
            do
            {
              k++;
              break label197;
              arrayOfFieldSerializer = this.getters;
              break;
              c1 = '{';
              break label80;
              c2 = '}';
              break label89;
              c3 = '\000';
              break label179;
              j = 0;
              break label590;
            }
            while (!FilterUtils.applyName(paramJSONSerializer, paramObject1, localFieldSerializer.getName()));
            localObject2 = localFieldSerializer.getPropertyValue(paramObject1);
          }
          while (!FilterUtils.apply(paramJSONSerializer, paramObject1, localFieldSerializer.getName(), localObject2));
          str = FilterUtils.processKey(paramJSONSerializer, paramObject1, localFieldSerializer.getName(), localObject2);
          localObject3 = FilterUtils.processValue(paramJSONSerializer, paramObject1, localFieldSerializer.getName(), localObject2);
        }
        while ((localObject3 == null) && (!bool1) && (!localFieldSerializer.isWriteNull()) && (!paramJSONSerializer.isEnabled(SerializerFeature.WriteMapNullValue)));
        if (j != 0)
        {
          localSerializeWriter.append(',');
          if (localSerializeWriter.isEnabled(SerializerFeature.PrettyFormat))
            paramJSONSerializer.println();
        }
        if (str != localFieldSerializer.getName())
        {
          if (!bool1)
            localSerializeWriter.writeFieldName(str);
          paramJSONSerializer.write(localObject3);
          break label596;
        }
        if (localObject2 != localObject3)
          if (!bool1)
            localFieldSerializer.writePrefix(paramJSONSerializer);
      }
      catch (Exception localException)
      {
        JSONException localJSONException = new JSONException("write javaBean error", localException);
        throw localJSONException;
      }
      finally
      {
        paramJSONSerializer.setContext(localSerialContext);
      }
    }
    if (!bool1)
      localFieldSerializer.writeProperty(paramJSONSerializer, localObject3);
    else
      localFieldSerializer.writeValue(paramJSONSerializer, localObject3);
    while (true)
    {
      label524: FilterUtils.writeAfter(paramJSONSerializer, paramObject1, c4);
      if ((arrayOfFieldSerializer.length > 0) && (localSerializeWriter.isEnabled(SerializerFeature.PrettyFormat)))
      {
        paramJSONSerializer.decrementIdent();
        paramJSONSerializer.println();
      }
      localSerializeWriter.append(c2);
      paramJSONSerializer.setContext(localSerialContext);
      return;
      label578: label590: label596: label602: 
      do
      {
        c4 = '\000';
        break label524;
        if (i == 0)
          break label278;
        c3 = ',';
        break;
        k = 0;
        break label197;
        j = 1;
        break label249;
      }
      while (j == 0);
      char c4 = ',';
    }
  }

  public void writeReference(JSONSerializer paramJSONSerializer, Object paramObject)
  {
    paramJSONSerializer.writeReference(paramObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.JavaBeanSerializer
 * JD-Core Version:    0.6.2
 */