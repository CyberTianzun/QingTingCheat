package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import java.awt.Point;
import java.io.IOException;
import java.lang.reflect.Type;

public class PointSerializer
  implements ObjectSerializer
{
  public static final PointSerializer instance = new PointSerializer();

  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    Point localPoint = (Point)paramObject1;
    if (localPoint == null)
    {
      localSerializeWriter.writeNull();
      return;
    }
    char c = '{';
    if (localSerializeWriter.isEnabled(SerializerFeature.WriteClassName))
    {
      localSerializeWriter.write('{');
      localSerializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
      localSerializeWriter.writeString(Point.class.getName());
      c = ',';
    }
    localSerializeWriter.writeFieldValue(c, "x", localPoint.getX());
    localSerializeWriter.writeFieldValue(',', "y", localPoint.getY());
    localSerializeWriter.write('}');
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.PointSerializer
 * JD-Core Version:    0.6.2
 */