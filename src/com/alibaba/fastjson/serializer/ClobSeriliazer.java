package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.sql.Clob;
import java.sql.SQLException;

public class ClobSeriliazer
  implements ObjectSerializer
{
  public static final ClobSeriliazer instance = new ClobSeriliazer();

  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    if (paramObject1 == null);
    Reader localReader;
    StringWriter localStringWriter;
    try
    {
      paramJSONSerializer.writeNull();
      return;
      localReader = ((Clob)paramObject1).getCharacterStream();
      localStringWriter = new StringWriter();
      char[] arrayOfChar = new char[1024];
      while (true)
      {
        int i = localReader.read(arrayOfChar);
        if (i == -1)
          break;
        localStringWriter.write(arrayOfChar, 0, i);
      }
    }
    catch (SQLException localSQLException)
    {
      throw new IOException("write clob error", localSQLException);
    }
    localReader.close();
    paramJSONSerializer.write(localStringWriter.toString());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.ClobSeriliazer
 * JD-Core Version:    0.6.2
 */