package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import java.awt.Rectangle;
import java.io.IOException;
import java.lang.reflect.Type;

public class RectangleSerializer
  implements ObjectSerializer
{
  public static final RectangleSerializer instance = new RectangleSerializer();

  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    Rectangle localRectangle = (Rectangle)paramObject1;
    if (localRectangle == null)
    {
      localSerializeWriter.writeNull();
      return;
    }
    char c = '{';
    if (localSerializeWriter.isEnabled(SerializerFeature.WriteClassName))
    {
      localSerializeWriter.write('{');
      localSerializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
      localSerializeWriter.writeString(Rectangle.class.getName());
      c = ',';
    }
    localSerializeWriter.writeFieldValue(c, "x", localRectangle.getX());
    localSerializeWriter.writeFieldValue(',', "y", localRectangle.getY());
    localSerializeWriter.writeFieldValue(',', "width", localRectangle.getWidth());
    localSerializeWriter.writeFieldValue(',', "height", localRectangle.getHeight());
    localSerializeWriter.write('}');
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.RectangleSerializer
 * JD-Core Version:    0.6.2
 */