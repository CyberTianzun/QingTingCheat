package com.alibaba.fastjson.util;

import com.alibaba.fastjson.annotation.JSONField;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class FieldInfo
  implements Comparable<FieldInfo>
{
  private final Class<?> declaringClass;
  private final Field field;
  private final Class<?> fieldClass;
  private final Type fieldType;
  private boolean getOnly = false;
  private final Method method;
  private final String name;

  public FieldInfo(String paramString, Class<?> paramClass1, Class<?> paramClass2, Type paramType, Field paramField)
  {
    this.name = paramString;
    this.declaringClass = paramClass1;
    this.fieldClass = paramClass2;
    this.fieldType = paramType;
    this.method = null;
    this.field = paramField;
    if (paramField != null)
      paramField.setAccessible(true);
  }

  public FieldInfo(String paramString, Method paramMethod, Field paramField)
  {
    this(paramString, paramMethod, paramField, null, null);
  }

  public FieldInfo(String paramString, Method paramMethod, Field paramField, Class<?> paramClass, Type paramType)
  {
    this.name = paramString;
    this.method = paramMethod;
    this.field = paramField;
    if (paramMethod != null)
      paramMethod.setAccessible(true);
    if (paramField != null)
      paramField.setAccessible(true);
    Class localClass;
    Type localType1;
    if (paramMethod != null)
      if (paramMethod.getParameterTypes().length == 1)
      {
        localClass = paramMethod.getParameterTypes()[0];
        localType1 = paramMethod.getGenericParameterTypes()[0];
      }
    for (this.declaringClass = paramMethod.getDeclaringClass(); ; this.declaringClass = paramField.getDeclaringClass())
    {
      if ((paramClass == null) || (localClass != Object.class) || (!(localType1 instanceof TypeVariable)))
        break label175;
      Type localType3 = getInheritGenericType(paramClass, (TypeVariable)localType1);
      if (localType3 == null)
        break label175;
      this.fieldClass = TypeUtils.getClass(localType3);
      this.fieldType = localType3;
      return;
      localClass = paramMethod.getReturnType();
      localType1 = paramMethod.getGenericReturnType();
      this.getOnly = true;
      break;
      localClass = paramField.getType();
      localType1 = paramField.getGenericType();
    }
    label175: Type localType2 = getFieldType(paramClass, paramType, localType1);
    if (localType2 != localType1)
    {
      if (!(localType2 instanceof ParameterizedType))
        break label221;
      localClass = TypeUtils.getClass(localType2);
    }
    while (true)
    {
      this.fieldType = localType2;
      this.fieldClass = localClass;
      return;
      label221: if ((localType2 instanceof Class))
        localClass = TypeUtils.getClass(localType2);
    }
  }

  public static Type getFieldType(Class<?> paramClass, Type paramType1, Type paramType2)
  {
    if ((paramClass == null) || (paramType1 == null))
      return paramType2;
    if (!(paramType1 instanceof ParameterizedType))
      return paramType2;
    if ((paramType2 instanceof TypeVariable))
    {
      ParameterizedType localParameterizedType3 = (ParameterizedType)paramType1;
      TypeVariable localTypeVariable2 = (TypeVariable)paramType2;
      for (int m = 0; m < paramClass.getTypeParameters().length; m++)
        if (paramClass.getTypeParameters()[m].getName().equals(localTypeVariable2.getName()))
          return localParameterizedType3.getActualTypeArguments()[m];
    }
    if ((paramType2 instanceof ParameterizedType))
    {
      ParameterizedType localParameterizedType1 = (ParameterizedType)paramType2;
      Type[] arrayOfType = localParameterizedType1.getActualTypeArguments();
      int i = 0;
      for (int j = 0; j < arrayOfType.length; j++)
      {
        Type localType = arrayOfType[j];
        if ((localType instanceof TypeVariable))
        {
          TypeVariable localTypeVariable1 = (TypeVariable)localType;
          if ((paramType1 instanceof ParameterizedType))
          {
            ParameterizedType localParameterizedType2 = (ParameterizedType)paramType1;
            for (int k = 0; k < paramClass.getTypeParameters().length; k++)
              if (paramClass.getTypeParameters()[k].getName().equals(localTypeVariable1.getName()))
              {
                arrayOfType[j] = localParameterizedType2.getActualTypeArguments()[k];
                i = 1;
              }
          }
        }
      }
      if (i != 0)
        return new ParameterizedTypeImpl(arrayOfType, localParameterizedType1.getOwnerType(), localParameterizedType1.getRawType());
    }
    return paramType2;
  }

  public static Type getInheritGenericType(Class<?> paramClass, TypeVariable<?> paramTypeVariable)
  {
    GenericDeclaration localGenericDeclaration = paramTypeVariable.getGenericDeclaration();
    Type localType;
    do
    {
      localType = paramClass.getGenericSuperclass();
      if (localType == null);
      while (true)
      {
        return null;
        if (!(localType instanceof ParameterizedType))
          break;
        ParameterizedType localParameterizedType = (ParameterizedType)localType;
        if (localParameterizedType.getRawType() != localGenericDeclaration)
          break;
        TypeVariable[] arrayOfTypeVariable = localGenericDeclaration.getTypeParameters();
        Type[] arrayOfType = localParameterizedType.getActualTypeArguments();
        for (int i = 0; i < arrayOfTypeVariable.length; i++)
          if (arrayOfTypeVariable[i] == paramTypeVariable)
            return arrayOfType[i];
      }
      paramClass = TypeUtils.getClass(localType);
    }
    while (localType != null);
    return null;
  }

  public int compareTo(FieldInfo paramFieldInfo)
  {
    return this.name.compareTo(paramFieldInfo.name);
  }

  public Object get(Object paramObject)
    throws IllegalAccessException, InvocationTargetException
  {
    if (this.method != null)
      return this.method.invoke(paramObject, new Object[0]);
    return this.field.get(paramObject);
  }

  public <T extends Annotation> T getAnnotation(Class<T> paramClass)
  {
    Method localMethod = this.method;
    Annotation localAnnotation = null;
    if (localMethod != null)
      localAnnotation = this.method.getAnnotation(paramClass);
    if ((localAnnotation == null) && (this.field != null))
      localAnnotation = this.field.getAnnotation(paramClass);
    return localAnnotation;
  }

  public Class<?> getDeclaringClass()
  {
    return this.declaringClass;
  }

  public Field getField()
  {
    return this.field;
  }

  public Class<?> getFieldClass()
  {
    return this.fieldClass;
  }

  public Type getFieldType()
  {
    return this.fieldType;
  }

  public String getFormat()
  {
    JSONField localJSONField = (JSONField)getAnnotation(JSONField.class);
    String str = null;
    if (localJSONField != null)
    {
      str = localJSONField.format();
      if (str.trim().length() == 0)
        str = null;
    }
    return str;
  }

  public Method getMethod()
  {
    return this.method;
  }

  public String getName()
  {
    return this.name;
  }

  public boolean isGetOnly()
  {
    return this.getOnly;
  }

  public void set(Object paramObject1, Object paramObject2)
    throws IllegalAccessException, InvocationTargetException
  {
    if (this.method != null)
    {
      this.method.invoke(paramObject1, new Object[] { paramObject2 });
      return;
    }
    this.field.set(paramObject1, paramObject2);
  }

  public void setAccessible(boolean paramBoolean)
    throws SecurityException
  {
    if (this.method != null)
    {
      this.method.setAccessible(paramBoolean);
      return;
    }
    this.field.setAccessible(paramBoolean);
  }

  public String toString()
  {
    return this.name;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.util.FieldInfo
 * JD-Core Version:    0.6.2
 */