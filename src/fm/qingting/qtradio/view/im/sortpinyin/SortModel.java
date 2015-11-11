package fm.qingting.qtradio.view.im.sortpinyin;

import fm.qingting.qtradio.room.UserInfo;

public class SortModel
{
  public UserInfo dtc;
  public String sortLetters;

  public SortModel(UserInfo paramUserInfo, String paramString)
  {
    this.dtc = paramUserInfo;
    this.sortLetters = paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.im.sortpinyin.SortModel
 * JD-Core Version:    0.6.2
 */