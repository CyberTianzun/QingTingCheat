package fm.qingting.qtradio.view.settingviews;

public class SettingItem
{
  private Object mData;
  private String mId = "";
  private String mName = "";
  private String mSubInfo;
  private SettingType mType;

  public SettingItem(String paramString1, SettingType paramSettingType, String paramString2)
  {
    this.mName = paramString1;
    this.mType = paramSettingType;
    this.mId = paramString2;
  }

  public SettingItem(String paramString1, SettingType paramSettingType, String paramString2, String paramString3)
  {
    this(paramString1, paramSettingType, paramString2);
    this.mSubInfo = paramString3;
  }

  public SettingItem(String paramString1, SettingType paramSettingType, String paramString2, String paramString3, Object paramObject)
  {
    this(paramString1, paramSettingType, paramString2, paramString3);
    this.mData = paramObject;
  }

  public Object getData()
  {
    return this.mData;
  }

  public String getId()
  {
    return this.mId;
  }

  public String getName()
  {
    return this.mName;
  }

  public String getSubInfo()
  {
    return this.mSubInfo;
  }

  public SettingType getType()
  {
    return this.mType;
  }

  public void setSubInfo(String paramString)
  {
    this.mSubInfo = paramString;
  }

  public static enum SettingType
  {
    static
    {
      check = new SettingType("check", 1);
      select = new SettingType("select", 2);
      SettingType[] arrayOfSettingType = new SettingType[3];
      arrayOfSettingType[0] = switcher;
      arrayOfSettingType[1] = check;
      arrayOfSettingType[2] = select;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.settingviews.SettingItem
 * JD-Core Version:    0.6.2
 */