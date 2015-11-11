package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import java.lang.reflect.Type;
import java.net.InetAddress;
import java.net.InetSocketAddress;

public class InetSocketAddressDeserializer
  implements ObjectDeserializer
{
  public static final InetSocketAddressDeserializer instance = new InetSocketAddressDeserializer();

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    if (localJSONLexer.token() == 8)
    {
      localJSONLexer.nextToken();
      return null;
    }
    paramDefaultJSONParser.accept(12);
    InetAddress localInetAddress = null;
    int i = 0;
    String str = localJSONLexer.stringVal();
    localJSONLexer.nextToken(17);
    if (str.equals("address"))
    {
      paramDefaultJSONParser.accept(17);
      localInetAddress = (InetAddress)paramDefaultJSONParser.parseObject(InetAddress.class);
    }
    while (true)
    {
      if (localJSONLexer.token() != 16)
        break label176;
      localJSONLexer.nextToken();
      break;
      if (str.equals("port"))
      {
        paramDefaultJSONParser.accept(17);
        if (localJSONLexer.token() != 2)
          throw new JSONException("port is not int");
        i = localJSONLexer.intValue();
        localJSONLexer.nextToken();
      }
      else
      {
        paramDefaultJSONParser.accept(17);
        paramDefaultJSONParser.parse();
      }
    }
    label176: paramDefaultJSONParser.accept(13);
    return new InetSocketAddress(localInetAddress, i);
  }

  public int getFastMatchToken()
  {
    return 12;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.InetSocketAddressDeserializer
 * JD-Core Version:    0.6.2
 */