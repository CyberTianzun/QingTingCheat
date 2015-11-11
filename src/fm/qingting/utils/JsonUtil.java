package fm.qingting.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

public class JsonUtil
{
  private static Gson gson = null;

  static
  {
    if (gson == null)
      gson = new Gson();
  }

  public static Object getJsonValue(String paramString1, String paramString2)
  {
    Map localMap = jsonToMap(paramString1);
    Object localObject = null;
    if (localMap != null)
    {
      int i = localMap.size();
      localObject = null;
      if (i > 0)
        localObject = localMap.get(paramString2);
    }
    return localObject;
  }

  public static Object jsonToBean(String paramString, Class<?> paramClass)
  {
    Gson localGson = gson;
    Object localObject = null;
    if (localGson != null)
      localObject = gson.fromJson(paramString, paramClass);
    return localObject;
  }

  public static <T> T jsonToBeanDateSerializer(String paramString1, Class<T> paramClass, String paramString2)
  {
    gson = new GsonBuilder().registerTypeAdapter(Date.class, new JsonDeserializer()
    {
      public Date deserialize(JsonElement paramAnonymousJsonElement, Type paramAnonymousType, JsonDeserializationContext paramAnonymousJsonDeserializationContext)
        throws JsonParseException
      {
        SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(this.val$pattern);
        String str = paramAnonymousJsonElement.getAsString();
        try
        {
          Date localDate = (Date)localSimpleDateFormat.parse(str);
          return localDate;
        }
        catch (java.text.ParseException localParseException1)
        {
          localParseException1.printStackTrace();
          return null;
        }
        catch (android.net.ParseException localParseException)
        {
          while (true)
            localParseException.printStackTrace();
        }
      }
    }).setDateFormat(paramString2).create();
    Gson localGson = gson;
    Object localObject = null;
    if (localGson != null)
      localObject = gson.fromJson(paramString1, paramClass);
    return localObject;
  }

  public static List<?> jsonToList(String paramString)
  {
    Gson localGson = gson;
    List localList = null;
    if (localGson != null)
    {
      Type localType = new TypeToken()
      {
      }
      .getType();
      localList = (List)gson.fromJson(paramString, localType);
    }
    return localList;
  }

  public static List<?> jsonToList(String paramString, Type paramType)
  {
    Gson localGson = gson;
    List localList = null;
    if (localGson != null)
      localList = (List)gson.fromJson(paramString, paramType);
    return localList;
  }

  public static Map<?, ?> jsonToMap(String paramString)
  {
    Gson localGson = gson;
    Map localMap = null;
    if (localGson != null)
    {
      Type localType = new TypeToken()
      {
      }
      .getType();
      localMap = (Map)gson.fromJson(paramString, localType);
    }
    return localMap;
  }

  public static String objectToJson(Object paramObject)
  {
    Gson localGson = gson;
    String str = null;
    if (localGson != null)
      str = gson.toJson(paramObject);
    return str;
  }

  public static String objectToJsonDateSerializer(Object paramObject, String paramString)
  {
    gson = new GsonBuilder().registerTypeHierarchyAdapter(Date.class, new JsonSerializer()
    {
      public JsonElement serialize(Date paramAnonymousDate, Type paramAnonymousType, JsonSerializationContext paramAnonymousJsonSerializationContext)
      {
        return new JsonPrimitive(new SimpleDateFormat(this.val$dateformat).format(paramAnonymousDate));
      }
    }).setDateFormat(paramString).create();
    Gson localGson = gson;
    String str = null;
    if (localGson != null)
      str = gson.toJson(paramObject);
    return str;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.utils.JsonUtil
 * JD-Core Version:    0.6.2
 */