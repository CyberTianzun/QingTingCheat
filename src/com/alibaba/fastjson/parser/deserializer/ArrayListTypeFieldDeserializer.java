package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.JSONToken;
import com.alibaba.fastjson.parser.ParseContext;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.FieldInfo;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ArrayListTypeFieldDeserializer extends FieldDeserializer
{
  private ObjectDeserializer deserializer;
  private int itemFastMatchToken;
  private final Type itemType;

  public ArrayListTypeFieldDeserializer(ParserConfig paramParserConfig, Class<?> paramClass, FieldInfo paramFieldInfo)
  {
    super(paramClass, paramFieldInfo);
    if ((getFieldType() instanceof ParameterizedType))
    {
      this.itemType = ((ParameterizedType)getFieldType()).getActualTypeArguments()[0];
      return;
    }
    this.itemType = Object.class;
  }

  public int getFastMatchToken()
  {
    return 14;
  }

  public final void parseArray(DefaultJSONParser paramDefaultJSONParser, Type paramType, Collection paramCollection)
  {
    Type localType = this.itemType;
    ObjectDeserializer localObjectDeserializer = this.deserializer;
    TypeVariable localTypeVariable;
    ParameterizedType localParameterizedType;
    Class localClass;
    int j;
    int k;
    int m;
    if (((localType instanceof TypeVariable)) && ((paramType instanceof ParameterizedType)))
    {
      localTypeVariable = (TypeVariable)localType;
      localParameterizedType = (ParameterizedType)paramType;
      boolean bool = localParameterizedType.getRawType() instanceof Class;
      localClass = null;
      if (bool)
        localClass = (Class)localParameterizedType.getRawType();
      j = -1;
      if (localClass != null)
      {
        k = 0;
        m = localClass.getTypeParameters().length;
      }
    }
    JSONLexer localJSONLexer;
    while (true)
    {
      if (k < m)
      {
        if (localClass.getTypeParameters()[k].getName().equals(localTypeVariable.getName()))
          j = k;
      }
      else
      {
        if (j != -1)
        {
          localType = localParameterizedType.getActualTypeArguments()[j];
          if (!localType.equals(this.itemType))
            localObjectDeserializer = paramDefaultJSONParser.getConfig().getDeserializer(localType);
        }
        localJSONLexer = paramDefaultJSONParser.getLexer();
        if (localJSONLexer.token() == 14)
          break;
        String str = "exepct '[', but " + JSONToken.name(localJSONLexer.token());
        if (paramType != null)
          str = str + ", type : " + paramType;
        throw new JSONException(str);
      }
      k++;
    }
    if (localObjectDeserializer == null)
    {
      localObjectDeserializer = paramDefaultJSONParser.getConfig().getDeserializer(localType);
      this.deserializer = localObjectDeserializer;
      this.itemFastMatchToken = this.deserializer.getFastMatchToken();
    }
    localJSONLexer.nextToken(this.itemFastMatchToken);
    for (int i = 0; ; i++)
    {
      if (localJSONLexer.isEnabled(Feature.AllowArbitraryCommas))
        while (localJSONLexer.token() == 16)
          localJSONLexer.nextToken();
      if (localJSONLexer.token() == 15)
      {
        localJSONLexer.nextToken(16);
        return;
      }
      paramCollection.add(localObjectDeserializer.deserialze(paramDefaultJSONParser, localType, Integer.valueOf(i)));
      paramDefaultJSONParser.checkListResolve(paramCollection);
      if (localJSONLexer.token() == 16)
        localJSONLexer.nextToken(this.itemFastMatchToken);
    }
  }

  public void parseField(DefaultJSONParser paramDefaultJSONParser, Object paramObject, Type paramType, Map<String, Object> paramMap)
  {
    if (paramDefaultJSONParser.getLexer().token() == 8)
    {
      setValue(paramObject, null);
      return;
    }
    ArrayList localArrayList = new ArrayList();
    ParseContext localParseContext = paramDefaultJSONParser.getContext();
    paramDefaultJSONParser.setContext(localParseContext, paramObject, this.fieldInfo.getName());
    parseArray(paramDefaultJSONParser, paramType, localArrayList);
    paramDefaultJSONParser.setContext(localParseContext);
    if (paramObject == null)
    {
      paramMap.put(this.fieldInfo.getName(), localArrayList);
      return;
    }
    setValue(paramObject, localArrayList);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.ArrayListTypeFieldDeserializer
 * JD-Core Version:    0.6.2
 */