package fm.qingting.qtradio.push.bean;

public enum PushType
{
  static
  {
    Download = new PushType("Download", 1);
    ContentUpdate = new PushType("ContentUpdate", 2);
    ResumeProgram = new PushType("ResumeProgram", 3);
    PushType[] arrayOfPushType = new PushType[4];
    arrayOfPushType[0] = Novel;
    arrayOfPushType[1] = Download;
    arrayOfPushType[2] = ContentUpdate;
    arrayOfPushType[3] = ResumeProgram;
  }

  public static int getPushType(PushType paramPushType)
  {
    switch (1.$SwitchMap$fm$qingting$qtradio$push$bean$PushType[paramPushType.ordinal()])
    {
    default:
      return -1;
    case 1:
      return 1;
    case 2:
      return 2;
    case 3:
      return 3;
    case 4:
    }
    return 4;
  }

  public static boolean isPush(String paramString)
  {
    return (paramString.equalsIgnoreCase(Novel.name())) || (paramString.equalsIgnoreCase(Download.name())) || (paramString.equalsIgnoreCase(ContentUpdate.name())) || (paramString.equalsIgnoreCase(ResumeProgram.name()));
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.push.bean.PushType
 * JD-Core Version:    0.6.2
 */