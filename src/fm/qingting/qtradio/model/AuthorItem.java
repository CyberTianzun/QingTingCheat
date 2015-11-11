package fm.qingting.qtradio.model;

import java.util.ArrayList;
import java.util.List;

public class AuthorItem
{
  public String avatar;
  public String bgphoto;
  public int diggCount;
  public int id;
  public List<SocialInfo> lstSocialInfo = new ArrayList();
  public String name;
  public String title;
  public String type;
  public int updatetime;
  public String wid;

  public void assign(AuthorItem paramAuthorItem)
  {
    if (paramAuthorItem == null);
    while (true)
    {
      return;
      this.name = paramAuthorItem.name;
      this.wid = paramAuthorItem.wid;
      this.id = paramAuthorItem.id;
      this.type = paramAuthorItem.type;
      this.updatetime = paramAuthorItem.updatetime;
      this.title = paramAuthorItem.title;
      this.avatar = paramAuthorItem.avatar;
      this.diggCount = paramAuthorItem.diggCount;
      this.bgphoto = paramAuthorItem.bgphoto;
      if ((paramAuthorItem.lstSocialInfo != null) && (paramAuthorItem.lstSocialInfo.size() > 0))
      {
        this.lstSocialInfo.clear();
        for (int i = 0; i < paramAuthorItem.lstSocialInfo.size(); i++)
        {
          SocialInfo localSocialInfo = new SocialInfo();
          localSocialInfo.assign((SocialInfo)paramAuthorItem.lstSocialInfo.get(i));
          this.lstSocialInfo.add(localSocialInfo);
        }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.AuthorItem
 * JD-Core Version:    0.6.2
 */