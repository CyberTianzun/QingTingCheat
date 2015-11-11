package fm.qingting.qtradio.room;

public class TellSongAction extends Action
{
  private String message;
  private String roomId;
  private int roomType;
  private UserInfo user;

  public TellSongAction()
  {
    this.actionType = 14;
  }

  public int getActionType()
  {
    return this.actionType;
  }

  public String getMessage()
  {
    return this.message;
  }

  public String getRoomId()
  {
    return this.roomId;
  }

  public int getRoomType()
  {
    return this.roomType;
  }

  public UserInfo getUser()
  {
    return this.user;
  }

  public void setContentInfo(int paramInt, String paramString1, UserInfo paramUserInfo, String paramString2)
  {
    this.roomType = paramInt;
    this.roomId = paramString1;
    this.user = paramUserInfo;
    this.message = paramString2;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.room.TellSongAction
 * JD-Core Version:    0.6.2
 */