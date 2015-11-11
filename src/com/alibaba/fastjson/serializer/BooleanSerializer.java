package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class BooleanSerializer
  implements ObjectSerializer
{
  public static final BooleanSerializer instance = new BooleanSerializer();

  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    Boolean localBoolean = (Boolean)paramObject1;
    if (localBoolean == null)
    {
      if (localSerializeWriter.isEnabled(SerializerFeature.WriteNullBooleanAsFalse))
      {
        localSerializeWriter.write("false");
        return;
      }
      localSerializeWriter.writeNull();
      return;
    }
    if (localBoolean.booleanValue())
    {
      localSerializeWriter.write("true");
      return;
    }
    localSerializeWriter.write("false");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.BooleanSerializer
 * JD-Core Version:    0.6.2
 */