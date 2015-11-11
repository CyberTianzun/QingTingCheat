package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class ArrayDeserializer
  implements ObjectDeserializer
{
  public static final ArrayDeserializer instance = new ArrayDeserializer();

  private <T> T toObjectArray(DefaultJSONParser paramDefaultJSONParser, Class<?> paramClass, JSONArray paramJSONArray)
  {
    if (paramJSONArray == null)
      return null;
    int i = paramJSONArray.size();
    Object localObject1 = Array.newInstance(paramClass, i);
    int j = 0;
    if (j < i)
    {
      Object localObject2 = paramJSONArray.get(j);
      if (localObject2 == paramJSONArray)
        Array.set(localObject1, j, localObject1);
      while (true)
      {
        j++;
        break;
        if (paramClass.isArray())
        {
          if (paramClass.isInstance(localObject2));
          for (Object localObject4 = localObject2; ; localObject4 = toObjectArray(paramDefaultJSONParser, paramClass, (JSONArray)localObject2))
          {
            Array.set(localObject1, j, localObject4);
            break;
          }
        }
        boolean bool = localObject2 instanceof JSONArray;
        Object localObject3 = null;
        if (bool)
        {
          int k = 0;
          JSONArray localJSONArray = (JSONArray)localObject2;
          int m = localJSONArray.size();
          for (int n = 0; n < m; n++)
            if (localJSONArray.get(n) == paramJSONArray)
            {
              localJSONArray.set(j, localObject1);
              k = 1;
            }
          localObject3 = null;
          if (k != 0)
            localObject3 = localJSONArray.toArray();
        }
        if (localObject3 == null)
          localObject3 = TypeUtils.cast(localObject2, paramClass, paramDefaultJSONParser.getConfig());
        Array.set(localObject1, j, localObject3);
      }
    }
    paramJSONArray.setRelatedArray(localObject1);
    paramJSONArray.setComponentType(paramClass);
    return localObject1;
  }

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    if (localJSONLexer.token() == 8)
    {
      localJSONLexer.nextToken(16);
      return null;
    }
    if (localJSONLexer.token() == 4)
    {
      byte[] arrayOfByte = localJSONLexer.bytesValue();
      localJSONLexer.nextToken(16);
      return arrayOfByte;
    }
    Type localType1;
    Object localObject;
    if ((paramType instanceof GenericArrayType))
    {
      localType1 = ((GenericArrayType)paramType).getGenericComponentType();
      if ((localType1 instanceof TypeVariable))
      {
        TypeVariable localTypeVariable = (TypeVariable)localType1;
        Type localType2 = paramDefaultJSONParser.getContext().getType();
        if ((localType2 instanceof ParameterizedType))
        {
          ParameterizedType localParameterizedType = (ParameterizedType)localType2;
          Type localType3 = localParameterizedType.getRawType();
          boolean bool = localType3 instanceof Class;
          Type localType4 = null;
          if (bool)
          {
            TypeVariable[] arrayOfTypeVariable = ((Class)localType3).getTypeParameters();
            for (int i = 0; i < arrayOfTypeVariable.length; i++)
              if (arrayOfTypeVariable[i].getName().equals(localTypeVariable.getName()))
                localType4 = localParameterizedType.getActualTypeArguments()[i];
          }
          if ((localType4 instanceof Class))
            localObject = (Class)localType4;
        }
      }
    }
    while (true)
    {
      JSONArray localJSONArray = new JSONArray();
      paramDefaultJSONParser.parseArray((Type)localObject, localJSONArray, paramObject);
      return toObjectArray(paramDefaultJSONParser, (Class)localObject, localJSONArray);
      localObject = Object.class;
      continue;
      localObject = Object.class;
      continue;
      localObject = (Class)localType1;
      continue;
      localObject = ((Class)paramType).getComponentType();
    }
  }

  public int getFastMatchToken()
  {
    return 14;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.ArrayDeserializer
 * JD-Core Version:    0.6.2
 */