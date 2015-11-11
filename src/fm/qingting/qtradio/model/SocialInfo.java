package fm.qingting.qtradio.model;

public class SocialInfo
{
  public String provider;
  public String socialId;

  public void assign(SocialInfo paramSocialInfo)
  {
    if (paramSocialInfo == null)
      return;
    this.provider = paramSocialInfo.provider;
    this.socialId = paramSocialInfo.socialId;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.SocialInfo
 * JD-Core Version:    0.6.2
 */