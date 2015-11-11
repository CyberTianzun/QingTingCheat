package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.TreeSet;

public class CollectionDeserializer
  implements ObjectDeserializer
{
  public static final CollectionDeserializer instance = new CollectionDeserializer();

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    if (paramDefaultJSONParser.getLexer().token() == 8)
    {
      paramDefaultJSONParser.getLexer().nextToken(16);
      return null;
    }
    Class localClass = getRawClass(paramType);
    Object localObject1;
    if (localClass == AbstractCollection.class)
    {
      localObject1 = new ArrayList();
      if (!(paramType instanceof ParameterizedType))
        break label216;
    }
    label216: for (Object localObject2 = ((ParameterizedType)paramType).getActualTypeArguments()[0]; ; localObject2 = Object.class)
      while (true)
      {
        paramDefaultJSONParser.parseArray((Type)localObject2, (Collection)localObject1, paramObject);
        return localObject1;
        if (localClass.isAssignableFrom(HashSet.class))
        {
          localObject1 = new HashSet();
          break;
        }
        if (localClass.isAssignableFrom(LinkedHashSet.class))
        {
          localObject1 = new LinkedHashSet();
          break;
        }
        if (localClass.isAssignableFrom(TreeSet.class))
        {
          localObject1 = new TreeSet();
          break;
        }
        if (localClass.isAssignableFrom(ArrayList.class))
        {
          localObject1 = new ArrayList();
          break;
        }
        try
        {
          localObject1 = (Collection)localClass.newInstance();
        }
        catch (Exception localException)
        {
          throw new JSONException("create instane error, class " + localClass.getName());
        }
      }
  }

  public int getFastMatchToken()
  {
    return 14;
  }

  public Class<?> getRawClass(Type paramType)
  {
    if ((paramType instanceof Class))
      return (Class)paramType;
    if ((paramType instanceof ParameterizedType))
      return getRawClass(((ParameterizedType)paramType).getRawType());
    throw new JSONException("TODO");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.CollectionDeserializer
 * JD-Core Version:    0.6.2
 */