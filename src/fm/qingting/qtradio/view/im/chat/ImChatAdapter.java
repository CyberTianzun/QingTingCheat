package fm.qingting.qtradio.view.im.chat;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import fm.qingting.framework.adapter.ILayoutParamsBuilder;
import fm.qingting.framework.adapter.IReusableCollection;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.view.IView;
import fm.qingting.qtradio.view.chatroom.chatlist.ChatItem;
import fm.qingting.qtradio.view.chatroom.chatlist.IChatAdapterIViewFactory;
import java.util.List;

public class ImChatAdapter extends BaseAdapter
{
  protected ILayoutParamsBuilder builder;
  protected List<ChatItem> data;
  protected IEventHandler eventHandler;
  protected IChatAdapterIViewFactory factory;
  protected String idKey;

  public ImChatAdapter(List<ChatItem> paramList, IChatAdapterIViewFactory paramIChatAdapterIViewFactory)
  {
    this.data = paramList;
    this.factory = paramIChatAdapterIViewFactory;
  }

  public void build(List<ChatItem> paramList, IChatAdapterIViewFactory paramIChatAdapterIViewFactory)
  {
    this.factory = paramIChatAdapterIViewFactory;
    setData(paramList);
  }

  public void clear()
  {
    this.data.clear();
    notifyDataSetChanged();
  }

  public int getCount()
  {
    if (this.data == null)
      return 0;
    try
    {
      int i = this.data.size();
      return i;
    }
    catch (Exception localException)
    {
    }
    return 1;
  }

  public List<ChatItem> getData()
  {
    return this.data;
  }

  public ChatItem getItem(int paramInt)
  {
    if (this.data == null);
    while (this.data.size() <= paramInt)
      return null;
    return (ChatItem)this.data.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public int getItemViewType(int paramInt)
  {
    ChatItem localChatItem = getItem(paramInt);
    if (localChatItem == null)
      return 32;
    return (0xF0 & localChatItem.type) >> 4;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    ChatItem localChatItem = getItem(paramInt);
    if (paramView == null)
      if ((paramViewGroup == null) || (!(paramViewGroup instanceof IReusableCollection)))
        break label143;
    label143: for (IView localIView = (IView)((IReusableCollection)paramViewGroup).getReusableItem(null); ; localIView = null)
    {
      if (localIView == null)
        localIView = this.factory.createView(0xF0 & localChatItem.type);
      paramView = localIView.getView();
      paramView.setTag(localIView);
      while (true)
      {
        localIView.setEventHandler(null);
        if (localChatItem != null)
          localIView.update("content", localChatItem);
        if (this.builder != null)
        {
          ViewGroup.LayoutParams localLayoutParams = this.builder.getLayoutParams();
          if (localLayoutParams != null)
            paramView.setLayoutParams(localLayoutParams);
        }
        return paramView;
        localIView = (IView)paramView.getTag();
      }
    }
  }

  public int getViewTypeCount()
  {
    return 3;
  }

  public void setData(List<ChatItem> paramList)
  {
    this.data = paramList;
    notifyDataSetChanged();
  }

  public void setEventHandler(IEventHandler paramIEventHandler)
  {
    this.eventHandler = paramIEventHandler;
  }

  public void setFactory(IChatAdapterIViewFactory paramIChatAdapterIViewFactory)
  {
    this.factory = paramIChatAdapterIViewFactory;
  }

  public void setIDKey(String paramString)
  {
    this.idKey = paramString;
  }

  public void setLayoutParamsBuilder(ILayoutParamsBuilder paramILayoutParamsBuilder)
  {
    this.builder = paramILayoutParamsBuilder;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.im.chat.ImChatAdapter
 * JD-Core Version:    0.6.2
 */