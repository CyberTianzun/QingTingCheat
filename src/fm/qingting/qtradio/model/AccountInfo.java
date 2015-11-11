package fm.qingting.qtradio.model;

public class AccountInfo
{
  private int highlightRes;
  private String mTitle;
  private String mType;
  private String mUserName;
  private boolean mValid = false;
  private int normalRes;

  public int getHighlightRes()
  {
    return this.highlightRes;
  }

  public int getNormalRes()
  {
    return this.normalRes;
  }

  public int getRes()
  {
    if (this.mValid)
      return this.highlightRes;
    return this.normalRes;
  }

  public String getTitle()
  {
    return this.mTitle;
  }

  public String getType()
  {
    return this.mType;
  }

  public String getUserName()
  {
    return this.mUserName;
  }

  public boolean isValid()
  {
    return this.mValid;
  }

  public void setRes(int paramInt1, int paramInt2)
  {
    this.highlightRes = paramInt1;
    this.normalRes = paramInt2;
  }

  public void setTitle(String paramString)
  {
    this.mTitle = paramString;
  }

  public void setType(String paramString)
  {
    this.mType = paramString;
  }

  public void setUserName(String paramString)
  {
    this.mUserName = paramString;
  }

  public void setValid(boolean paramBoolean)
  {
    this.mValid = paramBoolean;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.AccountInfo
 * JD-Core Version:    0.6.2
 */