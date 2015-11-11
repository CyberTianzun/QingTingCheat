package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.Type;
import java.util.Map;

public class StringFieldDeserializer extends FieldDeserializer
{
  private final ObjectDeserializer fieldValueDeserilizer;

  public StringFieldDeserializer(ParserConfig paramParserConfig, Class<?> paramClass, FieldInfo paramFieldInfo)
  {
    super(paramClass, paramFieldInfo);
    this.fieldValueDeserilizer = paramParserConfig.getDeserializer(paramFieldInfo);
  }

  public int getFastMatchToken()
  {
    return this.fieldValueDeserilizer.getFastMatchToken();
  }

  public void parseField(DefaultJSONParser paramDefaultJSONParser, Object paramObject, Type paramType, Map<String, Object> paramMap)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    String str;
    if (localJSONLexer.token() == 4)
    {
      str = localJSONLexer.stringVal();
      localJSONLexer.nextToken(16);
    }
    while (paramObject == null)
    {
      paramMap.put(this.fieldInfo.getName(), str);
      return;
      Object localObject = paramDefaultJSONParser.parse();
      if (localObject == null)
        str = null;
      else
        str = localObject.toString();
    }
    setValue(paramObject, str);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.StringFieldDeserializer
 * JD-Core Version:    0.6.2
 */