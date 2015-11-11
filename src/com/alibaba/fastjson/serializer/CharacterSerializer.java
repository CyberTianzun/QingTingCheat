package com.alibaba.fastjson.serializer;

import java.io.IOException;
import java.lang.reflect.Type;

public class CharacterSerializer
  implements ObjectSerializer
{
  public static final CharacterSerializer instance = new CharacterSerializer();

  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, Type paramType)
    throws IOException
  {
    SerializeWriter localSerializeWriter = paramJSONSerializer.getWriter();
    Character localCharacter = (Character)paramObject1;
    if (localCharacter == null)
    {
      localSerializeWriter.writeString("");
      return;
    }
    if (localCharacter.charValue() == 0)
    {
      localSerializeWriter.writeString("");
      return;
    }
    localSerializeWriter.writeString(localCharacter.toString());
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.CharacterSerializer
 * JD-Core Version:    0.6.2
 */