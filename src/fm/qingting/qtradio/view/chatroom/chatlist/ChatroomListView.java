package fm.qingting.qtradio.view.chatroom.chatlist;

import android.content.Context;
import android.widget.ListAdapter;
import fm.qingting.framework.adapter.ItemParam;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.utils.BitmapResourceCache;
import fm.qingting.framework.view.IView;
import fm.qingting.framework.view.ListViewImpl;
import java.util.ArrayList;
import java.util.List;

public class ChatroomListView extends ListViewImpl
  implements IEventHandler
{
  private ChatAdapter mAdapter;
  private IChatAdapterIViewFactory mFactory;
  private boolean mFirst = true;

  public ChatroomListView(Context paramContext, final boolean paramBoolean)
  {
    super(paramContext);
    setBackgroundColor(-1118482);
    this.mFactory = new IChatAdapterIViewFactory()
    {
      public IView createView(int paramAnonymousInt)
      {
        switch (paramAnonymousInt)
        {
        default:
          return null;
        case 16:
          return new ChatroomItemLeftView(ChatroomListView.this.getContext(), this.val$hash, paramBoolean);
        case 0:
          return new ChatroomItemRightView(ChatroomListView.this.getContext(), this.val$hash);
        case 32:
        }
        return new ChatroomTimestampView(ChatroomListView.this.getContext());
      }
    };
    this.mAdapter = new ChatAdapter(new ArrayList(), this.mFactory);
    this.mAdapter.setEventHandler(this);
    setVerticalScrollBarEnabled(false);
    setVerticalFadingEdgeEnabled(false);
    setCacheColorHint(0);
    setSelector(17170445);
    setDivider(null);
    setAdapter(this.mAdapter);
  }

  private boolean lastIsMine(List<ChatItem> paramList)
  {
    if (paramList == null);
    while (paramList.size() == 0)
      return false;
    ChatItem localChatItem = (ChatItem)paramList.get(-1 + paramList.size());
    if ((localChatItem.type == 3) || (localChatItem.type == 2) || (localChatItem.type == 4) || (localChatItem.type == 1));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public void close(boolean paramBoolean)
  {
    BitmapResourceCache.getInstance().clearResourceCacheOfOne(this, 0);
    super.close(paramBoolean);
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
    if (paramString.equalsIgnoreCase("itemCallback"))
    {
      localItemParam = (ItemParam)paramObject2;
      str = localItemParam.type;
      if (str.equalsIgnoreCase("copyPop"))
        dispatchActionEvent(str, localItemParam.param);
    }
    while (!paramString.equalsIgnoreCase("select"))
    {
      String str;
      do
      {
        ItemParam localItemParam;
        return;
        if (str.equalsIgnoreCase("select"))
        {
          dispatchActionEvent("reply", localItemParam);
          return;
        }
      }
      while (!str.equalsIgnoreCase("selectBlank"));
      dispatchActionEvent("selectBlack", null);
      return;
    }
    dispatchActionEvent(paramString, paramObject2);
  }

  public void scrollToPosition(int paramInt1, int paramInt2)
  {
    if ((paramInt1 < 0) || (paramInt1 > -1 + getAdapter().getCount()))
      paramInt1 = -1 + getAdapter().getCount();
    setSelectionFromTop(paramInt1, paramInt2);
    try
    {
      layoutChildren();
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      localIllegalStateException.printStackTrace();
    }
  }

  public void update(String paramString, Object paramObject)
  {
    int k;
    if (paramString.equalsIgnoreCase("setData"))
    {
      List localList = (List)paramObject;
      if (getLastVisiblePosition() == -1 + getCount())
      {
        k = 1;
        boolean bool = lastIsMine(localList);
        this.mAdapter.setData(localList);
        if ((this.mFirst) || (k != 0) || (bool))
        {
          this.mFirst = false;
          setSelection(-1 + getAdapter().getCount());
        }
      }
    }
    int i;
    do
    {
      do
      {
        return;
        k = 0;
        break;
        if (paramString.equalsIgnoreCase("scroll"))
        {
          setSelection(-1 + getAdapter().getCount());
          return;
        }
        if (paramString.equalsIgnoreCase("scrollToBottom"))
        {
          int j = ((Integer)paramObject).intValue();
          if ((j < 0) || (j > -2 + getAdapter().getCount()))
            j = -2 + getAdapter().getCount();
          setSelection(j);
          layoutChildren();
          return;
        }
      }
      while (!paramString.equalsIgnoreCase("scrollToFirstIndexAtMe"));
      i = ((Integer)paramObject).intValue();
    }
    while ((i < 0) || (i >= this.mAdapter.getCount()));
    setSelection(i);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.chatroom.chatlist.ChatroomListView
 * JD-Core Version:    0.6.2
 */