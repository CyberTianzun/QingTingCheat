package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import java.awt.Font;
import java.io.IOException;
import java.lang.reflect.Type;

public class FontSerializer
  implements ObjectSerializer
{
  public static final FontSerializer instance = new FontSerializer();

  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    Font localFont = (Font)paramObject1;
    if (localFont == null)
    {
      localSerializeWriter.writeNull();
      return;
    }
    char c = '{';
    if (localSerializeWriter.isEnabled(SerializerFeature.WriteClassName))
    {
      localSerializeWriter.write('{');
      localSerializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
      localSerializeWriter.writeString(Font.class.getName());
      c = ',';
    }
    localSerializeWriter.writeFieldValue(c, "name", localFont.getName());
    localSerializeWriter.writeFieldValue(',', "style", localFont.getStyle());
    localSerializeWriter.writeFieldValue(',', "size", localFont.getSize());
    localSerializeWriter.write('}');
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.FontSerializer
 * JD-Core Version:    0.6.2
 */