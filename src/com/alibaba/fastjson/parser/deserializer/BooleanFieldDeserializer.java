package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Type;
import java.util.Map;

public class BooleanFieldDeserializer extends FieldDeserializer
{
  public BooleanFieldDeserializer(ParserConfig paramParserConfig, Class<?> paramClass, FieldInfo paramFieldInfo)
  {
    super(paramClass, paramFieldInfo);
  }

  public int getFastMatchToken()
  {
    return 6;
  }

  public void parseField(DefaultJSONParser paramDefaultJSONParser, Object paramObject, Type paramType, Map<String, Object> paramMap)
  {
    int i = 1;
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    if (localJSONLexer.token() == 6)
    {
      localJSONLexer.nextToken(16);
      if (paramObject == null)
        paramMap.put(this.fieldInfo.getName(), Boolean.TRUE);
    }
    Boolean localBoolean;
    do
    {
      do
      {
        return;
        setValue(paramObject, i);
        return;
        if (localJSONLexer.token() == 2)
        {
          int j = localJSONLexer.intValue();
          localJSONLexer.nextToken(16);
          if (j == i);
          while (paramObject == null)
          {
            paramMap.put(this.fieldInfo.getName(), Boolean.valueOf(i));
            return;
            i = 0;
          }
          setValue(paramObject, i);
          return;
        }
        if (localJSONLexer.token() != 8)
          break;
        localJSONLexer.nextToken(16);
      }
      while ((getFieldClass() == Boolean.TYPE) || (paramObject == null));
      setValue(paramObject, null);
      return;
      if (localJSONLexer.token() == 7)
      {
        localJSONLexer.nextToken(16);
        if (paramObject == null)
        {
          paramMap.put(this.fieldInfo.getName(), Boolean.FALSE);
          return;
        }
        setValue(paramObject, false);
        return;
      }
      localBoolean = TypeUtils.castToBoolean(paramDefaultJSONParser.parse());
    }
    while ((localBoolean == null) && (getFieldClass() == Boolean.TYPE));
    if (paramObject == null)
    {
      paramMap.put(this.fieldInfo.getName(), localBoolean);
      return;
    }
    setValue(paramObject, localBoolean);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.BooleanFieldDeserializer
 * JD-Core Version:    0.6.2
 */