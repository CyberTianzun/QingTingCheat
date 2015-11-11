package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal..Gson.Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.JsonReaderInternalAccess;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class MapTypeAdapterFactory
  implements TypeAdapterFactory
{
  private final boolean complexMapKeySerialization;
  private final ConstructorConstructor constructorConstructor;

  public MapTypeAdapterFactory(ConstructorConstructor paramConstructorConstructor, boolean paramBoolean)
  {
    this.constructorConstructor = paramConstructorConstructor;
    this.complexMapKeySerialization = paramBoolean;
  }

  private TypeAdapter<?> getKeyAdapter(Gson paramGson, Type paramType)
  {
    if ((paramType == Boolean.TYPE) || (paramType == Boolean.class))
      return TypeAdapters.BOOLEAN_AS_STRING;
    return paramGson.getAdapter(TypeToken.get(paramType));
  }

  private static <T> JsonElement toJsonTree(TypeAdapter<T> paramTypeAdapter, T paramT)
  {
    try
    {
      JsonTreeWriter localJsonTreeWriter = new JsonTreeWriter();
      localJsonTreeWriter.setLenient(true);
      paramTypeAdapter.write(localJsonTreeWriter, paramT);
      JsonElement localJsonElement = localJsonTreeWriter.get();
      return localJsonElement;
    }
    catch (IOException localIOException)
    {
      throw new JsonIOException(localIOException);
    }
  }

  public <T> TypeAdapter<T> create(Gson paramGson, TypeToken<T> paramTypeToken)
  {
    Type localType = paramTypeToken.getType();
    if (!Map.class.isAssignableFrom(paramTypeToken.getRawType()))
      return null;
    Type[] arrayOfType = .Gson.Types.getMapKeyAndValueTypes(localType, .Gson.Types.getRawType(localType));
    TypeAdapter localTypeAdapter1 = getKeyAdapter(paramGson, arrayOfType[0]);
    TypeAdapter localTypeAdapter2 = paramGson.getAdapter(TypeToken.get(arrayOfType[1]));
    ObjectConstructor localObjectConstructor = this.constructorConstructor.getConstructor(paramTypeToken);
    return new Adapter(paramGson, arrayOfType[0], localTypeAdapter1, arrayOfType[1], localTypeAdapter2, localObjectConstructor);
  }

  private final class Adapter<K, V> extends TypeAdapter<Map<K, V>>
  {
    private final ObjectConstructor<? extends Map<K, V>> constructor;
    private final TypeAdapter<K> keyTypeAdapter;
    private final TypeAdapter<V> valueTypeAdapter;

    public Adapter(Type paramTypeAdapter, TypeAdapter<K> paramType1, Type paramTypeAdapter1, TypeAdapter<V> paramObjectConstructor, ObjectConstructor<? extends Map<K, V>> arg6)
    {
      this.keyTypeAdapter = new TypeAdapterRuntimeTypeWrapper(paramTypeAdapter, paramTypeAdapter1, paramType1);
      TypeAdapter localTypeAdapter;
      this.valueTypeAdapter = new TypeAdapterRuntimeTypeWrapper(paramTypeAdapter, localTypeAdapter, paramObjectConstructor);
      Object localObject;
      this.constructor = localObject;
    }

    private String keyToString(JsonElement paramJsonElement)
    {
      if (paramJsonElement.isJsonPrimitive())
      {
        JsonPrimitive localJsonPrimitive = paramJsonElement.getAsJsonPrimitive();
        if (localJsonPrimitive.isNumber())
          return String.valueOf(localJsonPrimitive.getAsNumber());
        if (localJsonPrimitive.isBoolean())
          return Boolean.toString(localJsonPrimitive.getAsBoolean());
        if (localJsonPrimitive.isString())
          return localJsonPrimitive.getAsString();
        throw new AssertionError();
      }
      if (paramJsonElement.isJsonNull())
        return "null";
      throw new AssertionError();
    }

    public Map<K, V> read(JsonReader paramJsonReader)
      throws IOException
    {
      JsonToken localJsonToken = paramJsonReader.peek();
      if (localJsonToken == JsonToken.NULL)
      {
        paramJsonReader.nextNull();
        return null;
      }
      Map localMap = (Map)this.constructor.construct();
      if (localJsonToken == JsonToken.BEGIN_ARRAY)
      {
        paramJsonReader.beginArray();
        while (paramJsonReader.hasNext())
        {
          paramJsonReader.beginArray();
          Object localObject2 = this.keyTypeAdapter.read(paramJsonReader);
          if (localMap.put(localObject2, this.valueTypeAdapter.read(paramJsonReader)) != null)
            throw new JsonSyntaxException("duplicate key: " + localObject2);
          paramJsonReader.endArray();
        }
        paramJsonReader.endArray();
        return localMap;
      }
      paramJsonReader.beginObject();
      while (paramJsonReader.hasNext())
      {
        JsonReaderInternalAccess.INSTANCE.promoteNameToValue(paramJsonReader);
        Object localObject1 = this.keyTypeAdapter.read(paramJsonReader);
        if (localMap.put(localObject1, this.valueTypeAdapter.read(paramJsonReader)) != null)
          throw new JsonSyntaxException("duplicate key: " + localObject1);
      }
      paramJsonReader.endObject();
      return localMap;
    }

    public void write(JsonWriter paramJsonWriter, Map<K, V> paramMap)
      throws IOException
    {
      if (paramMap == null)
      {
        paramJsonWriter.nullValue();
        return;
      }
      if (!MapTypeAdapterFactory.this.complexMapKeySerialization)
      {
        paramJsonWriter.beginObject();
        Iterator localIterator2 = paramMap.entrySet().iterator();
        while (localIterator2.hasNext())
        {
          Map.Entry localEntry2 = (Map.Entry)localIterator2.next();
          paramJsonWriter.name(String.valueOf(localEntry2.getKey()));
          this.valueTypeAdapter.write(paramJsonWriter, localEntry2.getValue());
        }
        paramJsonWriter.endObject();
        return;
      }
      int i = 0;
      ArrayList localArrayList1 = new ArrayList(paramMap.size());
      ArrayList localArrayList2 = new ArrayList(paramMap.size());
      Iterator localIterator1 = paramMap.entrySet().iterator();
      if (localIterator1.hasNext())
      {
        Map.Entry localEntry1 = (Map.Entry)localIterator1.next();
        JsonElement localJsonElement = MapTypeAdapterFactory.toJsonTree(this.keyTypeAdapter, localEntry1.getKey());
        localArrayList1.add(localJsonElement);
        localArrayList2.add(localEntry1.getValue());
        if ((localJsonElement.isJsonArray()) || (localJsonElement.isJsonObject()));
        for (int m = 1; ; m = 0)
        {
          i |= m;
          break;
        }
      }
      if (i != 0)
      {
        paramJsonWriter.beginArray();
        for (int k = 0; k < localArrayList1.size(); k++)
        {
          paramJsonWriter.beginArray();
          Streams.write((JsonElement)localArrayList1.get(k), paramJsonWriter);
          this.valueTypeAdapter.write(paramJsonWriter, localArrayList2.get(k));
          paramJsonWriter.endArray();
        }
        paramJsonWriter.endArray();
        return;
      }
      paramJsonWriter.beginObject();
      for (int j = 0; j < localArrayList1.size(); j++)
      {
        paramJsonWriter.name(keyToString((JsonElement)localArrayList1.get(j)));
        this.valueTypeAdapter.write(paramJsonWriter, localArrayList2.get(j));
      }
      paramJsonWriter.endObject();
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.google.gson.internal.bind.MapTypeAdapterFactory
 * JD-Core Version:    0.6.2
 */