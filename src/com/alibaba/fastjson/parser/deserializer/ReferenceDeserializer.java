package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicReference;

public class ReferenceDeserializer
  implements ObjectDeserializer
{
  public static final ReferenceDeserializer instance = new ReferenceDeserializer();

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    ParameterizedType localParameterizedType = (ParameterizedType)paramType;
    Object localObject = paramDefaultJSONParser.parseObject(localParameterizedType.getActualTypeArguments()[0]);
    Type localType = localParameterizedType.getRawType();
    if (localType == AtomicReference.class)
      return new AtomicReference(localObject);
    if (localType == WeakReference.class)
      return new WeakReference(localObject);
    if (localType == SoftReference.class)
      return new SoftReference(localObject);
    throw new UnsupportedOperationException(localType.toString());
  }

  public int getFastMatchToken()
  {
    return 12;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.ReferenceDeserializer
 * JD-Core Version:    0.6.2
 */