package com.google.gson;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.MalformedJsonException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

public final class JsonParser
{
  // ERROR //
  public JsonElement parse(JsonReader paramJsonReader)
    throws JsonIOException, JsonSyntaxException
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 26	com/google/gson/stream/JsonReader:isLenient	()Z
    //   4: istore_2
    //   5: aload_1
    //   6: iconst_1
    //   7: invokevirtual 30	com/google/gson/stream/JsonReader:setLenient	(Z)V
    //   10: aload_1
    //   11: invokestatic 34	com/google/gson/internal/Streams:parse	(Lcom/google/gson/stream/JsonReader;)Lcom/google/gson/JsonElement;
    //   14: astore 8
    //   16: aload_1
    //   17: iload_2
    //   18: invokevirtual 30	com/google/gson/stream/JsonReader:setLenient	(Z)V
    //   21: aload 8
    //   23: areturn
    //   24: astore 7
    //   26: new 20	com/google/gson/JsonParseException
    //   29: dup
    //   30: new 36	java/lang/StringBuilder
    //   33: dup
    //   34: invokespecial 37	java/lang/StringBuilder:<init>	()V
    //   37: ldc 39
    //   39: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   42: aload_1
    //   43: invokevirtual 46	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   46: ldc 48
    //   48: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   51: invokevirtual 52	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   54: aload 7
    //   56: invokespecial 55	com/google/gson/JsonParseException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   59: athrow
    //   60: astore 4
    //   62: aload_1
    //   63: iload_2
    //   64: invokevirtual 30	com/google/gson/stream/JsonReader:setLenient	(Z)V
    //   67: aload 4
    //   69: athrow
    //   70: astore 6
    //   72: new 20	com/google/gson/JsonParseException
    //   75: dup
    //   76: new 36	java/lang/StringBuilder
    //   79: dup
    //   80: invokespecial 37	java/lang/StringBuilder:<init>	()V
    //   83: ldc 39
    //   85: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: aload_1
    //   89: invokevirtual 46	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   92: ldc 48
    //   94: invokevirtual 43	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   97: invokevirtual 52	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   100: aload 6
    //   102: invokespecial 55	com/google/gson/JsonParseException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   105: athrow
    //   106: astore_3
    //   107: aload_3
    //   108: invokevirtual 59	com/google/gson/JsonParseException:getCause	()Ljava/lang/Throwable;
    //   111: instanceof 61
    //   114: ifeq +16 -> 130
    //   117: getstatic 67	com/google/gson/JsonNull:INSTANCE	Lcom/google/gson/JsonNull;
    //   120: astore 5
    //   122: aload_1
    //   123: iload_2
    //   124: invokevirtual 30	com/google/gson/stream/JsonReader:setLenient	(Z)V
    //   127: aload 5
    //   129: areturn
    //   130: aload_3
    //   131: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   10	16	24	java/lang/StackOverflowError
    //   10	16	60	finally
    //   26	60	60	finally
    //   72	106	60	finally
    //   107	122	60	finally
    //   130	132	60	finally
    //   10	16	70	java/lang/OutOfMemoryError
    //   10	16	106	com/google/gson/JsonParseException
  }

  public JsonElement parse(Reader paramReader)
    throws JsonIOException, JsonSyntaxException
  {
    JsonElement localJsonElement;
    try
    {
      JsonReader localJsonReader = new JsonReader(paramReader);
      localJsonElement = parse(localJsonReader);
      if ((!localJsonElement.isJsonNull()) && (localJsonReader.peek() != JsonToken.END_DOCUMENT))
        throw new JsonSyntaxException("Did not consume the entire document.");
    }
    catch (MalformedJsonException localMalformedJsonException)
    {
      throw new JsonSyntaxException(localMalformedJsonException);
    }
    catch (IOException localIOException)
    {
      throw new JsonIOException(localIOException);
    }
    catch (NumberFormatException localNumberFormatException)
    {
      throw new JsonSyntaxException(localNumberFormatException);
    }
    return localJsonElement;
  }

  public JsonElement parse(String paramString)
    throws JsonSyntaxException
  {
    return parse(new StringReader(paramString));
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.google.gson.JsonParser
 * JD-Core Version:    0.6.2
 */