package fm.qingting.qtradio.controller.im;

import android.content.Context;
import android.text.TextUtils;
import fm.qingting.framework.controller.ViewController;
import fm.qingting.framework.model.INavigationBarListener;
import fm.qingting.framework.model.NavigationBarItem;
import fm.qingting.qtradio.controller.ControllerManager;
import fm.qingting.qtradio.im.IMAgent;
import fm.qingting.qtradio.im.IMContacts;
import fm.qingting.qtradio.im.LatestMessages;
import fm.qingting.qtradio.im.info.GroupInfo;
import fm.qingting.qtradio.im.message.IMMessage;
import fm.qingting.qtradio.log.LogModule;
import fm.qingting.qtradio.logger.QTLogger;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.view.im.ImConversationsView;
import fm.qingting.qtradio.view.navigation.NavigationBarTopView;
import fm.qingting.utils.QTMSGManage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ImConversationsController extends ViewController
  implements INavigationBarListener
{
  private NavigationBarTopView mBarTopView;
  private List<Object> mResultList;
  private ImConversationsView mainView;

  public ImConversationsController(Context paramContext)
  {
    super(paramContext);
    this.controllerName = "conversations";
    this.mainView = new ImConversationsView(paramContext);
    attachView(this.mainView);
    this.mBarTopView = new NavigationBarTopView(paramContext);
    this.mBarTopView.setTitleItem(new NavigationBarItem("消息"));
    this.mBarTopView.setLeftItem(0);
    setNavigationBar(this.mBarTopView);
    this.mBarTopView.setBarListener(this);
    LatestMessages.resetBaseTime();
  }

  private boolean needSort()
  {
    if ((this.mResultList == null) || (this.mResultList.size() == 0));
    while (true)
    {
      return false;
      Object localObject = this.mResultList.get(0);
      String str;
      if ((localObject instanceof GroupInfo))
        str = ((GroupInfo)localObject).groupId;
      while (str != null)
      {
        if (!TextUtils.equals(LatestMessages.getLatestContactId(), str));
        for (boolean bool = true; ; bool = false)
        {
          return bool;
          if (!(localObject instanceof UserInfo))
            break label88;
          str = ((UserInfo)localObject).userKey;
          break;
        }
        label88: str = null;
      }
    }
  }

  private void sortList()
  {
    Collections.sort(this.mResultList, new Comparator()
    {
      public int compare(Object paramAnonymousObject1, Object paramAnonymousObject2)
      {
        String str1;
        if ((paramAnonymousObject1 instanceof GroupInfo))
        {
          str1 = ((GroupInfo)paramAnonymousObject1).groupId;
          if (!(paramAnonymousObject2 instanceof GroupInfo))
            break label62;
        }
        IMMessage localIMMessage1;
        IMMessage localIMMessage2;
        label62: for (String str2 = ((GroupInfo)paramAnonymousObject2).groupId; ; str2 = ((UserInfo)paramAnonymousObject2).userKey)
        {
          localIMMessage1 = LatestMessages.getMessage(str1);
          localIMMessage2 = LatestMessages.getMessage(str2);
          if (localIMMessage1 != null)
            break label74;
          return 1;
          str1 = ((UserInfo)paramAnonymousObject1).userKey;
          break;
        }
        label74: if (localIMMessage2 == null)
          return -1;
        if (localIMMessage1.publish > localIMMessage2.publish)
          return -1;
        return 1;
      }
    });
  }

  public void config(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("setData"))
    {
      this.mResultList = new ArrayList();
      List localList3 = IMContacts.getInstance().getRecentGroupContacts();
      List localList4 = IMContacts.getInstance().getRecentUserContacts();
      if (localList3 != null)
        this.mResultList.addAll(localList3);
      if (localList4 != null)
        this.mResultList.addAll(localList4);
      sortList();
      this.mainView.update(paramString, this.mResultList);
      QTMSGManage.getInstance().sendStatistcsMessage("imconversation");
      String str = QTLogger.getInstance().buildEnterIMLog(1);
      if (str != null)
        LogModule.getInstance().send("IMUI", str);
    }
    do
    {
      return;
      if (paramString.equalsIgnoreCase("resetList"))
      {
        if (((Boolean)paramObject).booleanValue())
          sortList();
        this.mainView.update(paramString, null);
        return;
      }
      if (paramString.equalsIgnoreCase("resetData"))
      {
        this.mResultList.clear();
        List localList1 = IMContacts.getInstance().getRecentGroupContacts();
        List localList2 = IMContacts.getInstance().getRecentUserContacts();
        if (localList1 != null)
          this.mResultList.addAll(localList1);
        if (localList2 != null)
          this.mResultList.addAll(localList2);
        sortList();
        this.mainView.update(paramString, this.mResultList);
        return;
      }
    }
    while (!paramString.equalsIgnoreCase("sortListIfNeed"));
    if (needSort())
      sortList();
    this.mainView.update("resetList", null);
  }

  public void controllerDidPopped()
  {
    this.mainView.close(false);
    super.controllerDidPopped();
  }

  public void controllerDidPushed()
  {
    IMAgent.getInstance().clearNotificationMsg();
    this.mainView.close(false);
    super.controllerDidPushed();
  }

  public void onItemClick(int paramInt)
  {
    if (paramInt == 2)
      ControllerManager.getInstance().popLastController();
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.controller.im.ImConversationsController
 * JD-Core Version:    0.6.2
 */