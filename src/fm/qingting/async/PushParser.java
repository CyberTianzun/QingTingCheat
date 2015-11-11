package fm.qingting.async;

import fm.qingting.async.callback.DataCallback;
import java.lang.reflect.Method;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

public class PushParser
{
  static Hashtable<Class, Method> mTable;
  private ArrayList<Object> mArgs = new ArrayList();
  private TapCallback mCallback;
  DataEmitter mEmitter;
  int mNeeded = 0;
  DataEmitterReader mReader;
  private LinkedList<Object> mWaiting = new LinkedList();
  ByteOrder order = ByteOrder.BIG_ENDIAN;

  static
  {
    if (!PushParser.class.desiredAssertionStatus());
    for (boolean bool = true; ; bool = false)
    {
      $assertionsDisabled = bool;
      mTable = new Hashtable();
      return;
    }
  }

  public PushParser(DataEmitter paramDataEmitter)
  {
    this.mEmitter = paramDataEmitter;
    this.mReader = new DataEmitterReader();
    this.mEmitter.setDataCallback(this.mReader);
  }

  static Method getTap(TapCallback paramTapCallback)
  {
    Method localMethod1 = (Method)mTable.get(paramTapCallback.getClass());
    if (localMethod1 != null)
      return localMethod1;
    for (Method localMethod2 : paramTapCallback.getClass().getMethods())
      if ("tap".equals(localMethod2.getName()))
      {
        mTable.put(paramTapCallback.getClass(), localMethod2);
        return localMethod2;
      }
    if (!$assertionsDisabled)
      throw new AssertionError();
    return null;
  }

  public PushParser noop()
  {
    this.mWaiting.add(Object.class);
    return this;
  }

  public PushParser order(ByteOrder paramByteOrder)
  {
    this.order = paramByteOrder;
    return this;
  }

  public ByteOrder order()
  {
    return this.order;
  }

  public PushParser readBuffer(int paramInt)
  {
    if (paramInt != -1)
      this.mNeeded = (paramInt + this.mNeeded);
    BufferWaiter localBufferWaiter = new BufferWaiter();
    localBufferWaiter.length = paramInt;
    this.mWaiting.add(localBufferWaiter);
    return this;
  }

  public PushParser readByte()
  {
    this.mNeeded = (1 + this.mNeeded);
    this.mWaiting.add(Byte.TYPE);
    return this;
  }

  public PushParser readInt()
  {
    this.mNeeded = (4 + this.mNeeded);
    this.mWaiting.add(Integer.TYPE);
    return this;
  }

  public PushParser readLenBuffer()
  {
    readInt();
    BufferWaiter localBufferWaiter = new BufferWaiter();
    localBufferWaiter.length = -1;
    this.mWaiting.add(localBufferWaiter);
    return this;
  }

  public PushParser readLong()
  {
    this.mNeeded = (8 + this.mNeeded);
    this.mWaiting.add(Long.TYPE);
    return this;
  }

  public PushParser readShort()
  {
    this.mNeeded = (2 + this.mNeeded);
    this.mWaiting.add(Short.TYPE);
    return this;
  }

  public PushParser readString()
  {
    readInt();
    StringWaiter localStringWaiter = new StringWaiter();
    localStringWaiter.length = -1;
    this.mWaiting.add(localStringWaiter);
    return this;
  }

  Exception stack()
  {
    try
    {
      throw new Exception();
    }
    catch (Exception localException)
    {
      return localException;
    }
  }

  public void tap(TapCallback paramTapCallback)
  {
    assert (this.mCallback == null);
    assert (this.mWaiting.size() > 0);
    this.mCallback = paramTapCallback;
    new DataCallback()
    {
      static
      {
        if (!PushParser.class.desiredAssertionStatus());
        for (boolean bool = true; ; bool = false)
        {
          $assertionsDisabled = bool;
          return;
        }
      }

      // ERROR //
      public void onDataAvailable(DataEmitter paramAnonymousDataEmitter, ByteBufferList paramAnonymousByteBufferList)
      {
        // Byte code:
        //   0: aload_2
        //   1: ifnull +15 -> 16
        //   4: aload_2
        //   5: aload_0
        //   6: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   9: getfield 45	fm/qingting/async/PushParser:order	Ljava/nio/ByteOrder;
        //   12: invokevirtual 50	fm/qingting/async/ByteBufferList:order	(Ljava/nio/ByteOrder;)Lfm/qingting/async/ByteBufferList;
        //   15: pop
        //   16: aload_0
        //   17: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   20: invokestatic 54	fm/qingting/async/PushParser:access$000	(Lfm/qingting/async/PushParser;)Ljava/util/LinkedList;
        //   23: invokevirtual 60	java/util/LinkedList:size	()I
        //   26: ifle +20 -> 46
        //   29: aload_0
        //   30: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   33: invokestatic 54	fm/qingting/async/PushParser:access$000	(Lfm/qingting/async/PushParser;)Ljava/util/LinkedList;
        //   36: invokevirtual 64	java/util/LinkedList:peek	()Ljava/lang/Object;
        //   39: astore 9
        //   41: aload 9
        //   43: ifnonnull +57 -> 100
        //   46: aload_0
        //   47: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   50: invokestatic 68	fm/qingting/async/PushParser:access$100	(Lfm/qingting/async/PushParser;)Ljava/util/ArrayList;
        //   53: invokevirtual 74	java/util/ArrayList:toArray	()[Ljava/lang/Object;
        //   56: astore 5
        //   58: aload_0
        //   59: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   62: invokestatic 68	fm/qingting/async/PushParser:access$100	(Lfm/qingting/async/PushParser;)Ljava/util/ArrayList;
        //   65: invokevirtual 77	java/util/ArrayList:clear	()V
        //   68: aload_0
        //   69: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   72: invokestatic 81	fm/qingting/async/PushParser:access$200	(Lfm/qingting/async/PushParser;)Lfm/qingting/async/TapCallback;
        //   75: astore 6
        //   77: aload_0
        //   78: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   81: aconst_null
        //   82: invokestatic 85	fm/qingting/async/PushParser:access$202	(Lfm/qingting/async/PushParser;Lfm/qingting/async/TapCallback;)Lfm/qingting/async/TapCallback;
        //   85: pop
        //   86: aload 6
        //   88: invokestatic 89	fm/qingting/async/PushParser:getTap	(Lfm/qingting/async/TapCallback;)Ljava/lang/reflect/Method;
        //   91: aload 6
        //   93: aload 5
        //   95: invokevirtual 95	java/lang/reflect/Method:invoke	(Ljava/lang/Object;[Ljava/lang/Object;)Ljava/lang/Object;
        //   98: pop
        //   99: return
        //   100: aload 9
        //   102: getstatic 101	java/lang/Integer:TYPE	Ljava/lang/Class;
        //   105: if_acmpne +79 -> 184
        //   108: aload_0
        //   109: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   112: invokestatic 68	fm/qingting/async/PushParser:access$100	(Lfm/qingting/async/PushParser;)Ljava/util/ArrayList;
        //   115: aload_2
        //   116: invokevirtual 104	fm/qingting/async/ByteBufferList:getInt	()I
        //   119: invokestatic 108	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
        //   122: invokevirtual 112	java/util/ArrayList:add	(Ljava/lang/Object;)Z
        //   125: pop
        //   126: aload_0
        //   127: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   130: astore 35
        //   132: aload 35
        //   134: bipush 252
        //   136: aload 35
        //   138: getfield 116	fm/qingting/async/PushParser:mNeeded	I
        //   141: iadd
        //   142: putfield 116	fm/qingting/async/PushParser:mNeeded	I
        //   145: aload_0
        //   146: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   149: invokestatic 54	fm/qingting/async/PushParser:access$000	(Lfm/qingting/async/PushParser;)Ljava/util/LinkedList;
        //   152: invokevirtual 119	java/util/LinkedList:remove	()Ljava/lang/Object;
        //   155: pop
        //   156: goto -140 -> 16
        //   159: astore_3
        //   160: getstatic 25	fm/qingting/async/PushParser$1:$assertionsDisabled	Z
        //   163: ifne +582 -> 745
        //   166: aload_0
        //   167: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   170: getfield 116	fm/qingting/async/PushParser:mNeeded	I
        //   173: ifne +572 -> 745
        //   176: new 121	java/lang/AssertionError
        //   179: dup
        //   180: invokespecial 122	java/lang/AssertionError:<init>	()V
        //   183: athrow
        //   184: aload 9
        //   186: getstatic 125	java/lang/Short:TYPE	Ljava/lang/Class;
        //   189: if_acmpne +43 -> 232
        //   192: aload_0
        //   193: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   196: invokestatic 68	fm/qingting/async/PushParser:access$100	(Lfm/qingting/async/PushParser;)Ljava/util/ArrayList;
        //   199: aload_2
        //   200: invokevirtual 128	fm/qingting/async/ByteBufferList:getShort	()I
        //   203: invokestatic 108	java/lang/Integer:valueOf	(I)Ljava/lang/Integer;
        //   206: invokevirtual 112	java/util/ArrayList:add	(Ljava/lang/Object;)Z
        //   209: pop
        //   210: aload_0
        //   211: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   214: astore 33
        //   216: aload 33
        //   218: bipush 254
        //   220: aload 33
        //   222: getfield 116	fm/qingting/async/PushParser:mNeeded	I
        //   225: iadd
        //   226: putfield 116	fm/qingting/async/PushParser:mNeeded	I
        //   229: goto -84 -> 145
        //   232: aload 9
        //   234: getstatic 131	java/lang/Byte:TYPE	Ljava/lang/Class;
        //   237: if_acmpne +42 -> 279
        //   240: aload_0
        //   241: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   244: invokestatic 68	fm/qingting/async/PushParser:access$100	(Lfm/qingting/async/PushParser;)Ljava/util/ArrayList;
        //   247: aload_2
        //   248: invokevirtual 135	fm/qingting/async/ByteBufferList:get	()B
        //   251: invokestatic 138	java/lang/Byte:valueOf	(B)Ljava/lang/Byte;
        //   254: invokevirtual 112	java/util/ArrayList:add	(Ljava/lang/Object;)Z
        //   257: pop
        //   258: aload_0
        //   259: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   262: astore 31
        //   264: aload 31
        //   266: iconst_m1
        //   267: aload 31
        //   269: getfield 116	fm/qingting/async/PushParser:mNeeded	I
        //   272: iadd
        //   273: putfield 116	fm/qingting/async/PushParser:mNeeded	I
        //   276: goto -131 -> 145
        //   279: aload 9
        //   281: getstatic 141	java/lang/Long:TYPE	Ljava/lang/Class;
        //   284: if_acmpne +43 -> 327
        //   287: aload_0
        //   288: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   291: invokestatic 68	fm/qingting/async/PushParser:access$100	(Lfm/qingting/async/PushParser;)Ljava/util/ArrayList;
        //   294: aload_2
        //   295: invokevirtual 145	fm/qingting/async/ByteBufferList:getLong	()J
        //   298: invokestatic 148	java/lang/Long:valueOf	(J)Ljava/lang/Long;
        //   301: invokevirtual 112	java/util/ArrayList:add	(Ljava/lang/Object;)Z
        //   304: pop
        //   305: aload_0
        //   306: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   309: astore 29
        //   311: aload 29
        //   313: bipush 248
        //   315: aload 29
        //   317: getfield 116	fm/qingting/async/PushParser:mNeeded	I
        //   320: iadd
        //   321: putfield 116	fm/qingting/async/PushParser:mNeeded	I
        //   324: goto -179 -> 145
        //   327: aload 9
        //   329: ldc 4
        //   331: if_acmpne +18 -> 349
        //   334: aload_0
        //   335: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   338: invokestatic 68	fm/qingting/async/PushParser:access$100	(Lfm/qingting/async/PushParser;)Ljava/util/ArrayList;
        //   341: aconst_null
        //   342: invokevirtual 112	java/util/ArrayList:add	(Ljava/lang/Object;)Z
        //   345: pop
        //   346: goto -201 -> 145
        //   349: aload 9
        //   351: instanceof 150
        //   354: ifeq +162 -> 516
        //   357: aload 9
        //   359: checkcast 150	fm/qingting/async/PushParser$UntilWaiter
        //   362: astore 20
        //   364: new 47	fm/qingting/async/ByteBufferList
        //   367: dup
        //   368: invokespecial 151	fm/qingting/async/ByteBufferList:<init>	()V
        //   371: astore 21
        //   373: iconst_1
        //   374: istore 22
        //   376: aload_2
        //   377: invokevirtual 152	fm/qingting/async/ByteBufferList:size	()I
        //   380: ifle +71 -> 451
        //   383: aload_2
        //   384: invokevirtual 155	fm/qingting/async/ByteBufferList:remove	()Ljava/nio/ByteBuffer;
        //   387: astore 24
        //   389: aload 24
        //   391: invokevirtual 161	java/nio/ByteBuffer:mark	()Ljava/nio/Buffer;
        //   394: pop
        //   395: iconst_0
        //   396: istore 26
        //   398: aload 24
        //   400: invokevirtual 164	java/nio/ByteBuffer:remaining	()I
        //   403: ifle +22 -> 425
        //   406: aload 24
        //   408: invokevirtual 165	java/nio/ByteBuffer:get	()B
        //   411: aload 20
        //   413: getfield 169	fm/qingting/async/PushParser$UntilWaiter:value	B
        //   416: if_icmpeq +387 -> 803
        //   419: iconst_1
        //   420: istore 22
        //   422: goto +370 -> 792
        //   425: aload 24
        //   427: invokevirtual 172	java/nio/ByteBuffer:reset	()Ljava/nio/Buffer;
        //   430: pop
        //   431: iload 22
        //   433: ifne +65 -> 498
        //   436: aload_2
        //   437: iconst_0
        //   438: aload 24
        //   440: invokevirtual 175	fm/qingting/async/ByteBufferList:add	(ILjava/nio/ByteBuffer;)V
        //   443: aload_2
        //   444: aload 21
        //   446: iload 26
        //   448: invokevirtual 178	fm/qingting/async/ByteBufferList:get	(Lfm/qingting/async/ByteBufferList;I)V
        //   451: aload 20
        //   453: getfield 182	fm/qingting/async/PushParser$UntilWaiter:callback	Lfm/qingting/async/callback/DataCallback;
        //   456: ifnull +16 -> 472
        //   459: aload 20
        //   461: getfield 182	fm/qingting/async/PushParser$UntilWaiter:callback	Lfm/qingting/async/callback/DataCallback;
        //   464: aload_1
        //   465: aload 21
        //   467: invokeinterface 183 3 0
        //   472: iload 22
        //   474: ifne +34 -> 508
        //   477: aload_0
        //   478: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   481: astore 23
        //   483: aload 23
        //   485: iconst_m1
        //   486: aload 23
        //   488: getfield 116	fm/qingting/async/PushParser:mNeeded	I
        //   491: iadd
        //   492: putfield 116	fm/qingting/async/PushParser:mNeeded	I
        //   495: goto -350 -> 145
        //   498: aload 21
        //   500: aload 24
        //   502: invokevirtual 186	fm/qingting/async/ByteBufferList:add	(Ljava/nio/ByteBuffer;)V
        //   505: goto -129 -> 376
        //   508: new 41	java/lang/Exception
        //   511: dup
        //   512: invokespecial 187	java/lang/Exception:<init>	()V
        //   515: athrow
        //   516: aload 9
        //   518: instanceof 189
        //   521: ifne +11 -> 532
        //   524: aload 9
        //   526: instanceof 191
        //   529: ifeq +202 -> 731
        //   532: aload 9
        //   534: checkcast 189	fm/qingting/async/PushParser$BufferWaiter
        //   537: astore 12
        //   539: aload 12
        //   541: getfield 194	fm/qingting/async/PushParser$BufferWaiter:length	I
        //   544: istore 13
        //   546: iload 13
        //   548: iconst_m1
        //   549: if_icmpne +82 -> 631
        //   552: aload_0
        //   553: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   556: invokestatic 68	fm/qingting/async/PushParser:access$100	(Lfm/qingting/async/PushParser;)Ljava/util/ArrayList;
        //   559: iconst_m1
        //   560: aload_0
        //   561: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   564: invokestatic 68	fm/qingting/async/PushParser:access$100	(Lfm/qingting/async/PushParser;)Ljava/util/ArrayList;
        //   567: invokevirtual 195	java/util/ArrayList:size	()I
        //   570: iadd
        //   571: invokevirtual 198	java/util/ArrayList:get	(I)Ljava/lang/Object;
        //   574: checkcast 97	java/lang/Integer
        //   577: invokevirtual 201	java/lang/Integer:intValue	()I
        //   580: istore 13
        //   582: aload_0
        //   583: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   586: invokestatic 68	fm/qingting/async/PushParser:access$100	(Lfm/qingting/async/PushParser;)Ljava/util/ArrayList;
        //   589: iconst_m1
        //   590: aload_0
        //   591: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   594: invokestatic 68	fm/qingting/async/PushParser:access$100	(Lfm/qingting/async/PushParser;)Ljava/util/ArrayList;
        //   597: invokevirtual 195	java/util/ArrayList:size	()I
        //   600: iadd
        //   601: invokevirtual 203	java/util/ArrayList:remove	(I)Ljava/lang/Object;
        //   604: pop
        //   605: aload 12
        //   607: iload 13
        //   609: putfield 194	fm/qingting/async/PushParser$BufferWaiter:length	I
        //   612: aload_0
        //   613: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   616: astore 15
        //   618: aload 15
        //   620: iload 13
        //   622: aload 15
        //   624: getfield 116	fm/qingting/async/PushParser:mNeeded	I
        //   627: iadd
        //   628: putfield 116	fm/qingting/async/PushParser:mNeeded	I
        //   631: aload_2
        //   632: invokevirtual 204	fm/qingting/async/ByteBufferList:remaining	()I
        //   635: iload 13
        //   637: if_icmpge +11 -> 648
        //   640: new 41	java/lang/Exception
        //   643: dup
        //   644: invokespecial 187	java/lang/Exception:<init>	()V
        //   647: athrow
        //   648: iload 13
        //   650: ifle +136 -> 786
        //   653: iload 13
        //   655: newarray byte
        //   657: astore 16
        //   659: aload_2
        //   660: aload 16
        //   662: invokevirtual 207	fm/qingting/async/ByteBufferList:get	([B)V
        //   665: aload_0
        //   666: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   669: astore 17
        //   671: aload 17
        //   673: aload 17
        //   675: getfield 116	fm/qingting/async/PushParser:mNeeded	I
        //   678: iload 13
        //   680: isub
        //   681: putfield 116	fm/qingting/async/PushParser:mNeeded	I
        //   684: aload 9
        //   686: instanceof 191
        //   689: ifeq +26 -> 715
        //   692: aload_0
        //   693: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   696: invokestatic 68	fm/qingting/async/PushParser:access$100	(Lfm/qingting/async/PushParser;)Ljava/util/ArrayList;
        //   699: new 209	java/lang/String
        //   702: dup
        //   703: aload 16
        //   705: invokespecial 211	java/lang/String:<init>	([B)V
        //   708: invokevirtual 112	java/util/ArrayList:add	(Ljava/lang/Object;)Z
        //   711: pop
        //   712: goto -567 -> 145
        //   715: aload_0
        //   716: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   719: invokestatic 68	fm/qingting/async/PushParser:access$100	(Lfm/qingting/async/PushParser;)Ljava/util/ArrayList;
        //   722: aload 16
        //   724: invokevirtual 112	java/util/ArrayList:add	(Ljava/lang/Object;)Z
        //   727: pop
        //   728: goto -583 -> 145
        //   731: getstatic 25	fm/qingting/async/PushParser$1:$assertionsDisabled	Z
        //   734: ifne -589 -> 145
        //   737: new 121	java/lang/AssertionError
        //   740: dup
        //   741: invokespecial 122	java/lang/AssertionError:<init>	()V
        //   744: athrow
        //   745: aload_0
        //   746: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   749: getfield 215	fm/qingting/async/PushParser:mReader	Lfm/qingting/async/DataEmitterReader;
        //   752: aload_0
        //   753: getfield 29	fm/qingting/async/PushParser$1:this$0	Lfm/qingting/async/PushParser;
        //   756: getfield 116	fm/qingting/async/PushParser:mNeeded	I
        //   759: aload_0
        //   760: invokevirtual 221	fm/qingting/async/DataEmitterReader:read	(ILfm/qingting/async/callback/DataCallback;)V
        //   763: return
        //   764: astore 4
        //   766: getstatic 25	fm/qingting/async/PushParser$1:$assertionsDisabled	Z
        //   769: ifne +11 -> 780
        //   772: new 121	java/lang/AssertionError
        //   775: dup
        //   776: invokespecial 122	java/lang/AssertionError:<init>	()V
        //   779: athrow
        //   780: aload 4
        //   782: invokevirtual 224	java/lang/Exception:printStackTrace	()V
        //   785: return
        //   786: aconst_null
        //   787: astore 16
        //   789: goto -124 -> 665
        //   792: iload 22
        //   794: ifeq -369 -> 425
        //   797: iinc 26 1
        //   800: goto -402 -> 398
        //   803: iconst_0
        //   804: istore 22
        //   806: goto -14 -> 792
        //
        // Exception table:
        //   from	to	target	type
        //   4	16	159	java/lang/Exception
        //   16	41	159	java/lang/Exception
        //   100	145	159	java/lang/Exception
        //   145	156	159	java/lang/Exception
        //   184	229	159	java/lang/Exception
        //   232	276	159	java/lang/Exception
        //   279	324	159	java/lang/Exception
        //   334	346	159	java/lang/Exception
        //   349	373	159	java/lang/Exception
        //   376	395	159	java/lang/Exception
        //   398	419	159	java/lang/Exception
        //   425	431	159	java/lang/Exception
        //   436	451	159	java/lang/Exception
        //   451	472	159	java/lang/Exception
        //   477	495	159	java/lang/Exception
        //   498	505	159	java/lang/Exception
        //   508	516	159	java/lang/Exception
        //   516	532	159	java/lang/Exception
        //   532	546	159	java/lang/Exception
        //   552	631	159	java/lang/Exception
        //   631	648	159	java/lang/Exception
        //   653	665	159	java/lang/Exception
        //   665	712	159	java/lang/Exception
        //   715	728	159	java/lang/Exception
        //   731	745	159	java/lang/Exception
        //   46	99	764	java/lang/Exception
      }
    };
  }

  public PushParser until(byte paramByte, DataCallback paramDataCallback)
  {
    UntilWaiter localUntilWaiter = new UntilWaiter();
    localUntilWaiter.value = paramByte;
    localUntilWaiter.callback = paramDataCallback;
    this.mWaiting.add(localUntilWaiter);
    this.mNeeded = (1 + this.mNeeded);
    return this;
  }

  static class BufferWaiter
  {
    int length;
  }

  static class StringWaiter extends PushParser.BufferWaiter
  {
  }

  static class UntilWaiter
  {
    DataCallback callback;
    byte value;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.async.PushParser
 * JD-Core Version:    0.6.2
 */