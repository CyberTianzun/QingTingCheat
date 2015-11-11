package com.taobao.newxp;

public enum UFPResType
{
  static
  {
    // Byte code:
    //   0: new 16	com/taobao/newxp/UFPResType$1
    //   3: dup
    //   4: ldc 17
    //   6: iconst_0
    //   7: invokespecial 21	com/taobao/newxp/UFPResType$1:<init>	(Ljava/lang/String;I)V
    //   10: putstatic 23	com/taobao/newxp/UFPResType:APP	Lcom/taobao/newxp/UFPResType;
    //   13: new 25	com/taobao/newxp/UFPResType$2
    //   16: dup
    //   17: ldc 26
    //   19: iconst_1
    //   20: invokespecial 27	com/taobao/newxp/UFPResType$2:<init>	(Ljava/lang/String;I)V
    //   23: putstatic 29	com/taobao/newxp/UFPResType:TB_ITEM	Lcom/taobao/newxp/UFPResType;
    //   26: new 31	com/taobao/newxp/UFPResType$3
    //   29: dup
    //   30: ldc 32
    //   32: iconst_2
    //   33: invokespecial 33	com/taobao/newxp/UFPResType$3:<init>	(Ljava/lang/String;I)V
    //   36: putstatic 35	com/taobao/newxp/UFPResType:TUAN	Lcom/taobao/newxp/UFPResType;
    //   39: new 37	com/taobao/newxp/UFPResType$4
    //   42: dup
    //   43: ldc 38
    //   45: iconst_3
    //   46: invokespecial 39	com/taobao/newxp/UFPResType$4:<init>	(Ljava/lang/String;I)V
    //   49: putstatic 41	com/taobao/newxp/UFPResType:LOTTERY	Lcom/taobao/newxp/UFPResType;
    //   52: iconst_4
    //   53: anewarray 2	com/taobao/newxp/UFPResType
    //   56: astore_0
    //   57: aload_0
    //   58: iconst_0
    //   59: getstatic 23	com/taobao/newxp/UFPResType:APP	Lcom/taobao/newxp/UFPResType;
    //   62: aastore
    //   63: aload_0
    //   64: iconst_1
    //   65: getstatic 29	com/taobao/newxp/UFPResType:TB_ITEM	Lcom/taobao/newxp/UFPResType;
    //   68: aastore
    //   69: aload_0
    //   70: iconst_2
    //   71: getstatic 35	com/taobao/newxp/UFPResType:TUAN	Lcom/taobao/newxp/UFPResType;
    //   74: aastore
    //   75: aload_0
    //   76: iconst_3
    //   77: getstatic 41	com/taobao/newxp/UFPResType:LOTTERY	Lcom/taobao/newxp/UFPResType;
    //   80: aastore
    //   81: aload_0
    //   82: putstatic 43	com/taobao/newxp/UFPResType:$VALUES	[Lcom/taobao/newxp/UFPResType;
    //   85: return
  }

  public static UFPResType fromString(String paramString)
  {
    for (UFPResType localUFPResType : values())
      if (localUFPResType.toString().equals(paramString))
        return localUFPResType;
    return null;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.taobao.newxp.UFPResType
 * JD-Core Version:    0.6.2
 */