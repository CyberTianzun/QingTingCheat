package com.alibaba.fastjson.parser.deserializer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.util.TypeUtils;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ThrowableDeserializer extends JavaBeanDeserializer
{
  public ThrowableDeserializer(ParserConfig paramParserConfig, Class<?> paramClass)
  {
    super(paramParserConfig, paramClass);
  }

  private Throwable createException(String paramString, Throwable paramThrowable, Class<?> paramClass)
    throws Exception
  {
    Object localObject1 = null;
    Object localObject2 = null;
    Object localObject3 = null;
    Constructor[] arrayOfConstructor = paramClass.getConstructors();
    int i = arrayOfConstructor.length;
    int j = 0;
    if (j < i)
    {
      Constructor localConstructor = arrayOfConstructor[j];
      if (localConstructor.getParameterTypes().length == 0)
        localObject1 = localConstructor;
      while (true)
      {
        j++;
        break;
        if ((localConstructor.getParameterTypes().length == 1) && (localConstructor.getParameterTypes()[0] == String.class))
          localObject2 = localConstructor;
        else if ((localConstructor.getParameterTypes().length == 2) && (localConstructor.getParameterTypes()[0] == String.class) && (localConstructor.getParameterTypes()[1] == Throwable.class))
          localObject3 = localConstructor;
      }
    }
    if (localObject3 != null)
      return (Throwable)localObject3.newInstance(new Object[] { paramString, paramThrowable });
    if (localObject2 != null)
      return (Throwable)localObject2.newInstance(new Object[] { paramString });
    if (localObject1 != null)
      return (Throwable)localObject1.newInstance(new Object[0]);
    return null;
  }

  public <T> T deserialze(DefaultJSONParser paramDefaultJSONParser, Type paramType, Object paramObject)
  {
    JSONLexer localJSONLexer = paramDefaultJSONParser.getLexer();
    Object localObject2;
    if (localJSONLexer.token() == 8)
    {
      localJSONLexer.nextToken();
      localObject2 = null;
      return localObject2;
    }
    label44: Throwable localThrowable;
    Object localObject1;
    String str1;
    StackTraceElement[] arrayOfStackTraceElement;
    HashMap localHashMap;
    label110: String str2;
    if (paramDefaultJSONParser.getResolveStatus() == 2)
    {
      paramDefaultJSONParser.setResolveStatus(0);
      localThrowable = null;
      localObject1 = null;
      if (paramType != null)
      {
        boolean bool1 = paramType instanceof Class;
        localObject1 = null;
        if (bool1)
        {
          Class localClass = (Class)paramType;
          boolean bool2 = Throwable.class.isAssignableFrom(localClass);
          localObject1 = null;
          if (bool2)
            localObject1 = localClass;
        }
      }
      str1 = null;
      arrayOfStackTraceElement = null;
      localHashMap = new HashMap();
      str2 = localJSONLexer.scanSymbol(paramDefaultJSONParser.getSymbolTable());
      if (str2 != null)
        break label229;
      if (localJSONLexer.token() != 13)
        break label204;
      localJSONLexer.nextToken(16);
      if (localObject1 != null)
        break label449;
      localObject2 = new Exception(str1, localThrowable);
    }
    while (arrayOfStackTraceElement != null)
    {
      ((Throwable)localObject2).setStackTrace(arrayOfStackTraceElement);
      return localObject2;
      if (localJSONLexer.token() == 12)
        break label44;
      throw new JSONException("syntax error");
      label204: if ((localJSONLexer.token() == 16) && (localJSONLexer.isEnabled(Feature.AllowArbitraryCommas)))
        break label110;
      label229: localJSONLexer.nextTokenWithColon(4);
      if (JSON.DEFAULT_TYPE_KEY.equals(str2))
        if (localJSONLexer.token() == 4)
        {
          localObject1 = TypeUtils.loadClass(localJSONLexer.stringVal());
          localJSONLexer.nextToken(16);
        }
      while (true)
      {
        if (localJSONLexer.token() != 13)
          break label447;
        localJSONLexer.nextToken(16);
        break;
        throw new JSONException("syntax error");
        if ("message".equals(str2))
        {
          if (localJSONLexer.token() == 8);
          for (str1 = null; ; str1 = localJSONLexer.stringVal())
          {
            localJSONLexer.nextToken();
            break;
            if (localJSONLexer.token() != 4)
              break label372;
          }
          label372: throw new JSONException("syntax error");
        }
        if ("cause".equals(str2))
          localThrowable = (Throwable)deserialze(paramDefaultJSONParser, null, "cause");
        else if ("stackTrace".equals(str2))
          arrayOfStackTraceElement = (StackTraceElement[])paramDefaultJSONParser.parseObject([Ljava.lang.StackTraceElement.class);
        else
          localHashMap.put(str2, paramDefaultJSONParser.parse());
      }
      label447: break label110;
      try
      {
        label449: localObject2 = createException(str1, localThrowable, (Class)localObject1);
        if (localObject2 != null)
          continue;
        Exception localException2 = new Exception(str1, localThrowable);
        localObject2 = localException2;
      }
      catch (Exception localException1)
      {
        throw new JSONException("create instance error", localException1);
      }
    }
  }

  public int getFastMatchToken()
  {
    return 12;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.parser.deserializer.ThrowableDeserializer
 * JD-Core Version:    0.6.2
 */