package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.util.Map;

public class LongFieldDeserializer extends FieldDeserializer
{
  private final ObjectDeserializer fieldValueDeserilizer;

  public LongFieldDeserializer(ParserConfig paramParserConfig, Class<?> paramClass, FieldInfo paramFieldInfo)
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
    long l;
    if (localJSONLexer.token() == 2)
    {
      l = localJSONLexer.longValue();
      localJSONLexer.nextToken(16);
      if (paramObject == null)
        paramMap.put(this.fieldInfo.getName(), Long.valueOf(l));
    }
    Object localObject;
    while (true)
    {
      return;
      setValue(paramObject, l);
      return;
      if (localJSONLexer.token() == 8)
      {
        localObject = null;
        localJSONLexer.nextToken(16);
      }
      while ((localObject != null) || (getFieldClass() != Long.TYPE))
      {
        if (paramObject != null)
          break label141;
        paramMap.put(this.fieldInfo.getName(), localObject);
        return;
        localObject = TypeUtils.castToLong(paramDefaultJSONParser.parse());
      }
    }
    label141: setValue(paramObject, localObject);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.LongFieldDeserializer
 * JD-Core Version:    0.6.2
 */