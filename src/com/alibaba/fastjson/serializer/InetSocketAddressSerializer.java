package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class InetSocketAddressSerializer
  implements ObjectSerializer
{
  public static InetSocketAddressSerializer instance = new InetSocketAddressSerializer();

  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    if (paramObject1 == null)
    {
      paramJSONSerializer.writeNull();
      return;
    }
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    InetSocketAddress localInetSocketAddress = (InetSocketAddress)paramObject1;
    InetAddress localInetAddress = localInetSocketAddress.getAddress();
    localSerializeWriter.write('{');
    if (localInetAddress != null)
    {
      localSerializeWriter.writeFieldName("address");
      paramJSONSerializer.write(localInetAddress);
      localSerializeWriter.write(',');
    }
    localSerializeWriter.writeFieldName("port");
    localSerializeWriter.writeInt(localInetSocketAddress.getPort());
    localSerializeWriter.write('}');
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.InetSocketAddressSerializer
 * JD-Core Version:    0.6.2
 */