package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressDeserializer
  implements ObjectDeserializer
{
  public static final InetAddressDeserializer instance = new InetAddressDeserializer();

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    String str = (String)paramDefaultJSONParser.parse();
    if (str == null);
    while (str.length() == 0)
      return null;
    try
    {
      InetAddress localInetAddress = InetAddress.getByName(str);
      return localInetAddress;
    }
    catch (UnknownHostException localUnknownHostException)
    {
      throw new JSONException("deserialize error", localUnknownHostException);
    }
  }

  public int getFastMatchToken()
  {
    return 4;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.InetAddressDeserializer
 * JD-Core Version:    0.6.2
 */