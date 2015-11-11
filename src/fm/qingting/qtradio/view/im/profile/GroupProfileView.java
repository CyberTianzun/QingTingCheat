package fm.qingting.qtradio.view.im.profile;

import android.content.Context;
import android.view.View.MeasureSpec;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.qtradio.im.IMContacts;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.social.CloudCenter;
import fm.qingting.qtradio.social.CloudCenter.OnLoginEventListerner;

public class GroupProfileView extends ViewGroupViewImpl
  implements IEventHandler
{
  private UserActionView mBottomView;
  private GroupProfileScrollView mScrollView;

  public GroupProfileView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(SkinManager.getBackgroundColor());
    this.mScrollView = new GroupProfileScrollView(paramContext);
    addView(this.mScrollView);
    this.mBottomView = new UserActionView(paramContext);
    this.mBottomView.update("setData", "发送消息");
    this.mBottomView.setEventHandler(this);
    addView(this.mBottomView);
  }

  public void close(boolean paramBoolean)
  {
    this.mScrollView.close(paramBoolean);
    this.mBottomView.close(paramBoolean);
    super.close(paramBoolean);
  }

  public void onEvent(Object paramObject1, final String paramString, Object paramObject2)
  {
    if (CloudCenter.getInstance().isLogin())
    {
      if (paramString.equalsIgnoreCase("useraction"))
        dispatchActionEvent(paramString, null);
      return;
    }
    CloudCenter.OnLoginEventListerner local1 = new CloudCenter.OnLoginEventListerner()
    {
      public void onLoginFailed(int paramAnonymousInt)
      {
      }

      public void onLoginSuccessed(int paramAnonymousInt)
      {
        if (paramString.equalsIgnoreCase("useraction"))
          GroupProfileView.this.dispatchActionEvent(paramString, null);
      }
    };
    EventDispacthManager.getInstance().dispatchAction("showLogin", local1);
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.mScrollView.layout(0, 0, paramInt3 - paramInt1, paramInt4 - paramInt2 - this.mBottomView.getMeasuredHeight());
    this.mBottomView.layout(0, paramInt4 - paramInt2 - this.mBottomView.getMeasuredHeight(), paramInt3 - paramInt1, paramInt4 - paramInt2);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.mScrollView.measure(paramInt1, paramInt2);
    this.mBottomView.measure(paramInt1, paramInt2);
    setMeasuredDimension(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
  }

  public void update(String paramString, Object paramObject)
  {
    String str;
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mScrollView.update(paramString, paramObject);
      GroupInfo localGroupInfo = (GroupInfo)paramObject;
      boolean bool = IMContacts.getInstance().hasWatchedGroup(localGroupInfo.groupId);
      UserActionView localUserActionView = this.mBottomView;
      if (bool)
      {
        str = "发送消息";
        localUserActionView.update("setData", str);
      }
    }
    do
    {
      return;
      str = "加入群组";
      break;
      if (paramString.equalsIgnoreCase("setUsers"))
      {
        this.mScrollView.update(paramString, paramObject);
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("setJoined"));
    this.mBottomView.update("setData", "发送消息");
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.im.profile.GroupProfileView
 * JD-Core Version:    0.6.2
 */