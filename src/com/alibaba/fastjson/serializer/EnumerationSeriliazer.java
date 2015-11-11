package com.alibaba.fastjson.serializer;

public class EnumerationSeriliazer
  implements ObjectSerializer
{
  public static EnumerationSeriliazer instance = new EnumerationSeriliazer();

  // ERROR //
  public void write(JSONSerializer paramJSONSerializer, Object paramObject1, Object paramObject2, java.lang.reflect.Type paramType)
    throws java.io.IOException
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 26	com/alibaba/fastjson/serializer/JSONSerializer:getWriter	()Lcom/alibaba/fastjson/serializer/SerializeWriter;
    //   4: astore 5
    //   6: aload_2
    //   7: ifnonnull +28 -> 35
    //   10: aload 5
    //   12: getstatic 32	com/alibaba/fastjson/serializer/SerializerFeature:WriteNullListAsEmpty	Lcom/alibaba/fastjson/serializer/SerializerFeature;
    //   15: invokevirtual 38	com/alibaba/fastjson/serializer/SerializeWriter:isEnabled	(Lcom/alibaba/fastjson/serializer/SerializerFeature;)Z
    //   18: ifeq +11 -> 29
    //   21: aload 5
    //   23: ldc 40
    //   25: invokevirtual 43	com/alibaba/fastjson/serializer/SerializeWriter:write	(Ljava/lang/String;)V
    //   28: return
    //   29: aload 5
    //   31: invokevirtual 46	com/alibaba/fastjson/serializer/SerializeWriter:writeNull	()V
    //   34: return
    //   35: aload_1
    //   36: getstatic 49	com/alibaba/fastjson/serializer/SerializerFeature:WriteClassName	Lcom/alibaba/fastjson/serializer/SerializerFeature;
    //   39: invokevirtual 50	com/alibaba/fastjson/serializer/JSONSerializer:isEnabled	(Lcom/alibaba/fastjson/serializer/SerializerFeature;)Z
    //   42: istore 6
    //   44: aconst_null
    //   45: astore 7
    //   47: iload 6
    //   49: ifeq +32 -> 81
    //   52: aload 4
    //   54: instanceof 52
    //   57: istore 18
    //   59: aconst_null
    //   60: astore 7
    //   62: iload 18
    //   64: ifeq +17 -> 81
    //   67: aload 4
    //   69: checkcast 52	java/lang/reflect/ParameterizedType
    //   72: invokeinterface 56 1 0
    //   77: iconst_0
    //   78: aaload
    //   79: astore 7
    //   81: aload_2
    //   82: checkcast 58	java/util/Enumeration
    //   85: astore 8
    //   87: aload_1
    //   88: invokevirtual 62	com/alibaba/fastjson/serializer/JSONSerializer:getContext	()Lcom/alibaba/fastjson/serializer/SerialContext;
    //   91: astore 9
    //   93: aload_1
    //   94: aload 9
    //   96: aload_2
    //   97: aload_3
    //   98: invokevirtual 66	com/alibaba/fastjson/serializer/JSONSerializer:setContext	(Lcom/alibaba/fastjson/serializer/SerialContext;Ljava/lang/Object;Ljava/lang/Object;)V
    //   101: aload 5
    //   103: bipush 91
    //   105: invokevirtual 70	com/alibaba/fastjson/serializer/SerializeWriter:append	(C)Lcom/alibaba/fastjson/serializer/SerializeWriter;
    //   108: pop
    //   109: iconst_0
    //   110: istore 12
    //   112: aload 8
    //   114: invokeinterface 74 1 0
    //   119: ifeq +81 -> 200
    //   122: aload 8
    //   124: invokeinterface 78 1 0
    //   129: astore 15
    //   131: iload 12
    //   133: iconst_1
    //   134: iadd
    //   135: istore 16
    //   137: iload 12
    //   139: ifeq +11 -> 150
    //   142: aload 5
    //   144: bipush 44
    //   146: invokevirtual 70	com/alibaba/fastjson/serializer/SerializeWriter:append	(C)Lcom/alibaba/fastjson/serializer/SerializeWriter;
    //   149: pop
    //   150: aload 15
    //   152: ifnonnull +15 -> 167
    //   155: aload 5
    //   157: invokevirtual 46	com/alibaba/fastjson/serializer/SerializeWriter:writeNull	()V
    //   160: iload 16
    //   162: istore 12
    //   164: goto -52 -> 112
    //   167: aload_1
    //   168: aload 15
    //   170: invokevirtual 82	java/lang/Object:getClass	()Ljava/lang/Class;
    //   173: invokevirtual 86	com/alibaba/fastjson/serializer/JSONSerializer:getObjectWriter	(Ljava/lang/Class;)Lcom/alibaba/fastjson/serializer/ObjectSerializer;
    //   176: aload_1
    //   177: aload 15
    //   179: iload 16
    //   181: iconst_1
    //   182: isub
    //   183: invokestatic 92	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   186: aload 7
    //   188: invokeinterface 94 5 0
    //   193: iload 16
    //   195: istore 12
    //   197: goto -85 -> 112
    //   200: aload 5
    //   202: bipush 93
    //   204: invokevirtual 70	com/alibaba/fastjson/serializer/SerializeWriter:append	(C)Lcom/alibaba/fastjson/serializer/SerializeWriter;
    //   207: pop
    //   208: aload_1
    //   209: aload 9
    //   211: invokevirtual 97	com/alibaba/fastjson/serializer/JSONSerializer:setContext	(Lcom/alibaba/fastjson/serializer/SerialContext;)V
    //   214: return
    //   215: astore 10
    //   217: aload_1
    //   218: aload 9
    //   220: invokevirtual 97	com/alibaba/fastjson/serializer/JSONSerializer:setContext	(Lcom/alibaba/fastjson/serializer/SerialContext;)V
    //   223: aload 10
    //   225: athrow
    //   226: astore 10
    //   228: iload 12
    //   230: pop
    //   231: goto -14 -> 217
    //
    // Exception table:
    //   from	to	target	type
    //   101	109	215	finally
    //   142	150	215	finally
    //   155	160	215	finally
    //   167	193	215	finally
    //   112	131	226	finally
    //   200	208	226	finally
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.EnumerationSeriliazer
 * JD-Core Version:    0.6.2
 */