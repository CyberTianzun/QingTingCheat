package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicReference;

public class ReferenceSerializer
  implements ObjectSerializer
{
  public static final ReferenceSerializer instance = new ReferenceSerializer();

  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    if ((paramObject1 instanceof AtomicReference));
    for (Object localObject = ((AtomicReference)paramObject1).get(); ; localObject = ((Reference)paramObject1).get())
    {
      paramJSONSerializer.write(localObject);
      return;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.ReferenceSerializer
 * JD-Core Version:    0.6.2
 */