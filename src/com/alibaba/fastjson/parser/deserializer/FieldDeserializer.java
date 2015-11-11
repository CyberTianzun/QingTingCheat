package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public abstract class FieldDeserializer
{
  protected final Class<?> clazz;
  protected final FieldInfo fieldInfo;

  public FieldDeserializer(Class<?> paramClass, FieldInfo paramFieldInfo)
  {
    this.clazz = paramClass;
    this.fieldInfo = paramFieldInfo;
  }

  public int getFastMatchToken()
  {
    return 0;
  }

  public Field getField()
  {
    return this.fieldInfo.getField();
  }

  public Class<?> getFieldClass()
  {
    return this.fieldInfo.getFieldClass();
  }

  public Type getFieldType()
  {
    return this.fieldInfo.getFieldType();
  }

  public Method getMethod()
  {
    return this.fieldInfo.getMethod();
  }

  public abstract void parseField(DefaultJSONParser paramDefaultJSONParser, Object paramObject, Type paramType, Map<String, Object> paramMap);

  public void setValue(Object paramObject, int paramInt)
  {
    setValue(paramObject, Integer.valueOf(paramInt));
  }

  public void setValue(Object paramObject, long paramLong)
  {
    setValue(paramObject, Long.valueOf(paramLong));
  }

  public void setValue(Object paramObject1, Object paramObject2)
  {
    Method localMethod = this.fieldInfo.getMethod();
    if (localMethod != null)
    {
      try
      {
        if (!this.fieldInfo.isGetOnly())
          break label266;
        if (this.fieldInfo.getFieldClass() == AtomicInteger.class)
        {
          AtomicInteger localAtomicInteger = (AtomicInteger)localMethod.invoke(paramObject1, new Object[0]);
          if (localAtomicInteger == null)
            return;
          localAtomicInteger.set(((AtomicInteger)paramObject2).get());
          return;
        }
        if (this.fieldInfo.getFieldClass() == AtomicLong.class)
        {
          AtomicLong localAtomicLong = (AtomicLong)localMethod.invoke(paramObject1, new Object[0]);
          if (localAtomicLong == null)
            return;
          localAtomicLong.set(((AtomicLong)paramObject2).get());
          return;
        }
      }
      catch (Exception localException2)
      {
        throw new JSONException("set property error, " + this.fieldInfo.getName(), localException2);
      }
      if (this.fieldInfo.getFieldClass() == AtomicBoolean.class)
      {
        AtomicBoolean localAtomicBoolean = (AtomicBoolean)localMethod.invoke(paramObject1, new Object[0]);
        if (localAtomicBoolean != null)
          localAtomicBoolean.set(((AtomicBoolean)paramObject2).get());
      }
      else if (Map.class.isAssignableFrom(localMethod.getReturnType()))
      {
        Map localMap = (Map)localMethod.invoke(paramObject1, new Object[0]);
        if (localMap != null)
          localMap.putAll((Map)paramObject2);
      }
      else
      {
        Collection localCollection = (Collection)localMethod.invoke(paramObject1, new Object[0]);
        if (localCollection != null)
        {
          localCollection.addAll((Collection)paramObject2);
          return;
          label266: localMethod.invoke(paramObject1, new Object[] { paramObject2 });
        }
      }
    }
    else
    {
      Field localField = this.fieldInfo.getField();
      if (localField != null)
        try
        {
          localField.set(paramObject1, paramObject2);
          return;
        }
        catch (Exception localException1)
        {
          throw new JSONException("set property error, " + this.fieldInfo.getName(), localException1);
        }
    }
  }

  public void setValue(Object paramObject, String paramString)
  {
    setValue(paramObject, paramString);
  }

  public void setValue(Object paramObject, boolean paramBoolean)
  {
    setValue(paramObject, Boolean.valueOf(paramBoolean));
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.FieldDeserializer
 * JD-Core Version:    0.6.2
 */