package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import java.awt.Color;
import java.io.IOException;
import java.lang.reflect.Type;

public class ColorSerializer
  implements ObjectSerializer
{
  public static final ColorSerializer instance = new ColorSerializer();

  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    Color localColor = (Color)paramObject1;
    if (localColor == null)
    {
      localSerializeWriter.writeNull();
      return;
    }
    char c = '{';
    if (localSerializeWriter.isEnabled(SerializerFeature.WriteClassName))
    {
      localSerializeWriter.write('{');
      localSerializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
      localSerializeWriter.writeString(Color.class.getName());
      c = ',';
    }
    localSerializeWriter.writeFieldValue(c, "r", localColor.getRed());
    localSerializeWriter.writeFieldValue(',', "g", localColor.getGreen());
    localSerializeWriter.writeFieldValue(',', "b", localColor.getBlue());
    if (localColor.getAlpha() > 0)
      localSerializeWriter.writeFieldValue(',', "alpha", localColor.getAlpha());
    localSerializeWriter.write('}');
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.ColorSerializer
 * JD-Core Version:    0.6.2
 */