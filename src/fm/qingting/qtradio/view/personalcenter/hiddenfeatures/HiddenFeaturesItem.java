package fm.qingting.qtradio.view.personalcenter.hiddenfeatures;

public class HiddenFeaturesItem
{
  private String mButtonText;
  private String mId = "";
  private String mName = "";
  private String mSubInfo;
  private HiddenFeaturesType mType;

  public HiddenFeaturesItem(String paramString1, HiddenFeaturesType paramHiddenFeaturesType, String paramString2, String paramString3)
  {
    this.mName = paramString1;
    this.mType = paramHiddenFeaturesType;
    this.mId = paramString2;
    this.mButtonText = paramString3;
  }

  public HiddenFeaturesItem(String paramString1, HiddenFeaturesType paramHiddenFeaturesType, String paramString2, String paramString3, String paramString4)
  {
    this(paramString1, paramHiddenFeaturesType, paramString2, paramString3);
    this.mSubInfo = paramString4;
  }

  public String getButtonText()
  {
    return this.mButtonText;
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

  public HiddenFeaturesType getType()
  {
    return this.mType;
  }

  public void setSubInfo(String paramString)
  {
    this.mSubInfo = paramString;
  }

  public static enum HiddenFeaturesType
  {
    static
    {
      NONE = new HiddenFeaturesType("NONE", 2);
      HiddenFeaturesType[] arrayOfHiddenFeaturesType = new HiddenFeaturesType[3];
      arrayOfHiddenFeaturesType[0] = BUTTON;
      arrayOfHiddenFeaturesType[1] = SWITCHER;
      arrayOfHiddenFeaturesType[2] = NONE;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.personalcenter.hiddenfeatures.HiddenFeaturesItem
 * JD-Core Version:    0.6.2
 */