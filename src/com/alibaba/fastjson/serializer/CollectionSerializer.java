package com.alibaba.fastjson.serializer;

public class CollectionSerializer
  implements ObjectSerializer
{
  public static final CollectionSerializer instance = new CollectionSerializer();

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
    //   57: istore 22
    //   59: aconst_null
    //   60: astore 7
    //   62: iload 22
    //   64: ifeq +17 -> 81
    //   67: aload 4
    //   69: checkcast 52	java/lang/reflect/ParameterizedType
    //   72: invokeinterface 56 1 0
    //   77: iconst_0
    //   78: aaload
    //   79: astore 7
    //   81: aload_2
    //   82: checkcast 58	java/util/Collection
    //   85: astore 8
    //   87: aload_1
    //   88: invokevirtual 62	com/alibaba/fastjson/serializer/JSONSerializer:getContext	()Lcom/alibaba/fastjson/serializer/SerialContext;
    //   91: astore 9
    //   93: aload_1
    //   94: aload 9
    //   96: aload_2
    //   97: aload_3
    //   98: invokevirtual 66	com/alibaba/fastjson/serializer/JSONSerializer:setContext	(Lcom/alibaba/fastjson/serializer/SerialContext;Ljava/lang/Object;Ljava/lang/Object;)V
    //   101: aload_1
    //   102: getstatic 49	com/alibaba/fastjson/serializer/SerializerFeature:WriteClassName	Lcom/alibaba/fastjson/serializer/SerializerFeature;
    //   105: invokevirtual 50	com/alibaba/fastjson/serializer/JSONSerializer:isEnabled	(Lcom/alibaba/fastjson/serializer/SerializerFeature;)Z
    //   108: ifeq +21 -> 129
    //   111: ldc 68
    //   113: aload 8
    //   115: invokevirtual 72	java/lang/Object:getClass	()Ljava/lang/Class;
    //   118: if_acmpne +86 -> 204
    //   121: aload 5
    //   123: ldc 74
    //   125: invokevirtual 78	com/alibaba/fastjson/serializer/SerializeWriter:append	(Ljava/lang/CharSequence;)Lcom/alibaba/fastjson/serializer/SerializeWriter;
    //   128: pop
    //   129: aload 5
    //   131: bipush 91
    //   133: invokevirtual 81	com/alibaba/fastjson/serializer/SerializeWriter:append	(C)Lcom/alibaba/fastjson/serializer/SerializeWriter;
    //   136: pop
    //   137: aload 8
    //   139: invokeinterface 85 1 0
    //   144: astore 12
    //   146: iconst_0
    //   147: istore 13
    //   149: aload 12
    //   151: invokeinterface 91 1 0
    //   156: ifeq +178 -> 334
    //   159: aload 12
    //   161: invokeinterface 95 1 0
    //   166: astore 16
    //   168: iload 13
    //   170: iconst_1
    //   171: iadd
    //   172: istore 17
    //   174: iload 13
    //   176: ifeq +11 -> 187
    //   179: aload 5
    //   181: bipush 44
    //   183: invokevirtual 81	com/alibaba/fastjson/serializer/SerializeWriter:append	(C)Lcom/alibaba/fastjson/serializer/SerializeWriter;
    //   186: pop
    //   187: aload 16
    //   189: ifnonnull +36 -> 225
    //   192: aload 5
    //   194: invokevirtual 46	com/alibaba/fastjson/serializer/SerializeWriter:writeNull	()V
    //   197: iload 17
    //   199: istore 13
    //   201: goto -52 -> 149
    //   204: ldc 97
    //   206: aload 8
    //   208: invokevirtual 72	java/lang/Object:getClass	()Ljava/lang/Class;
    //   211: if_acmpne -82 -> 129
    //   214: aload 5
    //   216: ldc 99
    //   218: invokevirtual 78	com/alibaba/fastjson/serializer/SerializeWriter:append	(Ljava/lang/CharSequence;)Lcom/alibaba/fastjson/serializer/SerializeWriter;
    //   221: pop
    //   222: goto -93 -> 129
    //   225: aload 16
    //   227: invokevirtual 72	java/lang/Object:getClass	()Ljava/lang/Class;
    //   230: astore 18
    //   232: aload 18
    //   234: ldc 101
    //   236: if_acmpne +23 -> 259
    //   239: aload 5
    //   241: aload 16
    //   243: checkcast 101	java/lang/Integer
    //   246: invokevirtual 105	java/lang/Integer:intValue	()I
    //   249: invokevirtual 109	com/alibaba/fastjson/serializer/SerializeWriter:writeInt	(I)V
    //   252: iload 17
    //   254: istore 13
    //   256: goto -107 -> 149
    //   259: aload 18
    //   261: ldc 111
    //   263: if_acmpne +41 -> 304
    //   266: aload 5
    //   268: aload 16
    //   270: checkcast 111	java/lang/Long
    //   273: invokevirtual 115	java/lang/Long:longValue	()J
    //   276: invokevirtual 119	com/alibaba/fastjson/serializer/SerializeWriter:writeLong	(J)V
    //   279: aload 5
    //   281: getstatic 49	com/alibaba/fastjson/serializer/SerializerFeature:WriteClassName	Lcom/alibaba/fastjson/serializer/SerializerFeature;
    //   284: invokevirtual 38	com/alibaba/fastjson/serializer/SerializeWriter:isEnabled	(Lcom/alibaba/fastjson/serializer/SerializerFeature;)Z
    //   287: ifeq +81 -> 368
    //   290: aload 5
    //   292: bipush 76
    //   294: invokevirtual 122	com/alibaba/fastjson/serializer/SerializeWriter:write	(C)V
    //   297: iload 17
    //   299: istore 13
    //   301: goto -152 -> 149
    //   304: aload_1
    //   305: aload 18
    //   307: invokevirtual 126	com/alibaba/fastjson/serializer/JSONSerializer:getObjectWriter	(Ljava/lang/Class;)Lcom/alibaba/fastjson/serializer/ObjectSerializer;
    //   310: aload_1
    //   311: aload 16
    //   313: iload 17
    //   315: iconst_1
    //   316: isub
    //   317: invokestatic 130	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
    //   320: aload 7
    //   322: invokeinterface 132 5 0
    //   327: iload 17
    //   329: istore 13
    //   331: goto -182 -> 149
    //   334: aload 5
    //   336: bipush 93
    //   338: invokevirtual 81	com/alibaba/fastjson/serializer/SerializeWriter:append	(C)Lcom/alibaba/fastjson/serializer/SerializeWriter;
    //   341: pop
    //   342: aload_1
    //   343: aload 9
    //   345: invokevirtual 135	com/alibaba/fastjson/serializer/JSONSerializer:setContext	(Lcom/alibaba/fastjson/serializer/SerialContext;)V
    //   348: return
    //   349: astore 10
    //   351: aload_1
    //   352: aload 9
    //   354: invokevirtual 135	com/alibaba/fastjson/serializer/JSONSerializer:setContext	(Lcom/alibaba/fastjson/serializer/SerialContext;)V
    //   357: aload 10
    //   359: athrow
    //   360: astore 10
    //   362: iload 13
    //   364: pop
    //   365: goto -14 -> 351
    //   368: iload 17
    //   370: istore 13
    //   372: goto -223 -> 149
    //
    // Exception table:
    //   from	to	target	type
    //   129	146	349	finally
    //   179	187	349	finally
    //   192	197	349	finally
    //   225	232	349	finally
    //   239	252	349	finally
    //   266	297	349	finally
    //   304	327	349	finally
    //   149	168	360	finally
    //   334	342	360	finally
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.alibaba.fastjson.serializer.CollectionSerializer
 * JD-Core Version:    0.6.2
 */