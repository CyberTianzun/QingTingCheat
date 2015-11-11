package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.List;

public class JavaObjectDeserializer
  implements ObjectDeserializer
{
  public static final JavaObjectDeserializer instance = new JavaObjectDeserializer();

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    if ((paramType instanceof GenericArrayType))
    {
      Type localType = ((GenericArrayType)paramType).getGenericComponentType();
      if ((localType instanceof TypeVariable))
        localType = ((TypeVariable)localType).getBounds()[0];
      ArrayList localArrayList = new ArrayList();
      paramDefaultJSONParser.parseArray(localType, localArrayList);
      if ((localType instanceof Class))
      {
        Object[] arrayOfObject = (Object[])Array.newInstance((Class)localType, localArrayList.size());
        localArrayList.toArray(arrayOfObject);
        return arrayOfObject;
      }
      return localArrayList.toArray();
    }
    return paramDefaultJSONParser.parse(paramObject);
  }

  public int getFastMatchToken()
  {
    return 12;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.JavaObjectDeserializer
 * JD-Core Version:    0.6.2
 */