package com.alibaba.fastjson.serializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.util.IOUtils;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateSerializer
  implements ObjectSerializer
{
  public static final DateSerializer instance = new DateSerializer();

  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    if (paramObject1 == null)
    {
      localSerializeWriter.writeNull();
      return;
    }
    if ((localSerializeWriter.isEnabled(SerializerFeature.WriteClassName)) && (paramObject1.getClass() != paramType))
    {
      if (paramObject1.getClass() == Date.class)
      {
        localSerializeWriter.write("new Date(");
        localSerializeWriter.writeLongAndChar(((Date)paramObject1).getTime(), ')');
        return;
      }
      localSerializeWriter.write('{');
      localSerializeWriter.writeFieldName(JSON.DEFAULT_TYPE_KEY);
      paramJSONSerializer.write(paramObject1.getClass().getName());
      localSerializeWriter.writeFieldValue(',', "val", ((Date)paramObject1).getTime());
      localSerializeWriter.write('}');
      return;
    }
    Date localDate = (Date)paramObject1;
    if (localSerializeWriter.isEnabled(SerializerFeature.WriteDateUseDateFormat))
    {
      Object localObject = paramJSONSerializer.getDateFormat();
      if (localObject == null)
        localObject = new SimpleDateFormat(JSON.DEFFAULT_DATE_FORMAT);
      localSerializeWriter.writeString(((DateFormat)localObject).format(localDate));
      return;
    }
    long l = localDate.getTime();
    if (paramJSONSerializer.isEnabled(SerializerFeature.UseISO8601DateFormat))
    {
      int i;
      int j;
      int k;
      int m;
      int n;
      int i1;
      char[] arrayOfChar;
      if (paramJSONSerializer.isEnabled(SerializerFeature.UseSingleQuotes))
      {
        localSerializeWriter.append('\'');
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTimeInMillis(l);
        i = localCalendar.get(1);
        j = 1 + localCalendar.get(2);
        k = localCalendar.get(5);
        m = localCalendar.get(11);
        n = localCalendar.get(12);
        i1 = localCalendar.get(13);
        int i2 = localCalendar.get(14);
        if (i2 == 0)
          break label390;
        arrayOfChar = "0000-00-00T00:00:00.000".toCharArray();
        IOUtils.getChars(i2, 23, arrayOfChar);
        IOUtils.getChars(i1, 19, arrayOfChar);
        IOUtils.getChars(n, 16, arrayOfChar);
        IOUtils.getChars(m, 13, arrayOfChar);
        IOUtils.getChars(k, 10, arrayOfChar);
        IOUtils.getChars(j, 7, arrayOfChar);
        IOUtils.getChars(i, 4, arrayOfChar);
      }
      while (true)
      {
        localSerializeWriter.write(arrayOfChar);
        if (!paramJSONSerializer.isEnabled(SerializerFeature.UseSingleQuotes))
          break label504;
        localSerializeWriter.append('\'');
        return;
        localSerializeWriter.append('"');
        break;
        label390: if ((i1 == 0) && (n == 0) && (m == 0))
        {
          arrayOfChar = "0000-00-00".toCharArray();
          IOUtils.getChars(k, 10, arrayOfChar);
          IOUtils.getChars(j, 7, arrayOfChar);
          IOUtils.getChars(i, 4, arrayOfChar);
        }
        else
        {
          arrayOfChar = "0000-00-00T00:00:00".toCharArray();
          IOUtils.getChars(i1, 19, arrayOfChar);
          IOUtils.getChars(n, 16, arrayOfChar);
          IOUtils.getChars(m, 13, arrayOfChar);
          IOUtils.getChars(k, 10, arrayOfChar);
          IOUtils.getChars(j, 7, arrayOfChar);
          IOUtils.getChars(i, 4, arrayOfChar);
        }
      }
      label504: localSerializeWriter.append('"');
      return;
    }
    localSerializeWriter.writeLong(l);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.DateSerializer
 * JD-Core Version:    0.6.2
 */