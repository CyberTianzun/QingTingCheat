package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DecimalFormat;

public class DoubleSerializer
  implements ObjectSerializer
{
  public static final DoubleSerializer instance = new DoubleSerializer();
  private DecimalFormat decimalFormat = null;

  public DoubleSerializer()
  {
  }

  public DoubleSerializer(String paramString)
  {
    this(new DecimalFormat(paramString));
  }

  public DoubleSerializer(DecimalFormat paramDecimalFormat)
  {
    this.decimalFormat = paramDecimalFormat;
  }

  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    if (paramObject1 == null)
    {
      if (paramJSONSerializer.isEnabled(SerializerFeature.WriteNullNumberAsZero))
      {
        localSerializeWriter.write('0');
        return;
      }
      localSerializeWriter.writeNull();
      return;
    }
    double d = ((Double)paramObject1).doubleValue();
    if (Double.isNaN(d))
    {
      localSerializeWriter.writeNull();
      return;
    }
    if (Double.isInfinite(d))
    {
      localSerializeWriter.writeNull();
      return;
    }
    if (this.decimalFormat == null)
    {
      str = Double.toString(d);
      if (!str.endsWith(".0"));
    }
    for (String str = str.substring(0, -2 + str.length()); ; str = this.decimalFormat.format(d))
    {
      localSerializeWriter.append(str);
      if (!paramJSONSerializer.isEnabled(SerializerFeature.WriteClassName))
        break;
      localSerializeWriter.write('D');
      return;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.DoubleSerializer
 * JD-Core Version:    0.6.2
 */