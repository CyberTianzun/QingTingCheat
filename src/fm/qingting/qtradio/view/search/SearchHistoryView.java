package fm.qingting.qtradio.view.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.model.RootNode;
import fm.qingting.qtradio.search.SearchHotKeyword;
import fm.qingting.qtradio.search.SearchNode;
import fm.qingting.utils.QTMSGManage;
import java.util.List;

public class SearchHistoryView extends LinearLayout
{
  private LabelContainer mHotContainer;
  private LayoutInflater mLayoutInflater;
  private SearchViewNew mListener;
  private LabelContainer mRecentsContainer;

  public SearchHistoryView(Context paramContext, SearchViewNew paramSearchViewNew)
  {
    super(paramContext);
    this.mListener = paramSearchViewNew;
    this.mLayoutInflater = LayoutInflater.from(paramContext);
    this.mLayoutInflater.inflate(2130903060, this);
    View localView = findViewById(2131230856);
    ((TextView)localView.findViewById(2131230861)).setText("最近搜索");
    TextView localTextView = (TextView)localView.findViewById(2131230862);
    localTextView.setText("清除记录");
    localTextView.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        InfoManager.getInstance().root().mSearchNode.clearKeywords();
        SearchHistoryView.this.setRecents();
      }
    });
    this.mRecentsContainer = ((LabelContainer)findViewById(2131230857));
    setRecents();
    ((TextView)findViewById(2131230858).findViewById(2131230861)).setText("热门搜索");
    this.mHotContainer = ((LabelContainer)findViewById(2131230859));
    setHot();
  }

  public boolean dispatchTouchEvent(MotionEvent paramMotionEvent)
  {
    if ((paramMotionEvent.getAction() == 0) && (this.mListener != null))
      this.mListener.closeKeyBoard();
    return super.dispatchTouchEvent(paramMotionEvent);
  }

  protected void setHot()
  {
    List localList = InfoManager.getInstance().root().mSearchNode.getHotKeywords();
    this.mHotContainer.removeAllViews();
    if ((localList != null) && (localList.size() > 0))
    {
      for (int i = 0; i < localList.size(); i++)
      {
        RelativeLayout localRelativeLayout = (RelativeLayout)this.mLayoutInflater.inflate(2130903061, null);
        Button localButton = (Button)localRelativeLayout.findViewById(2131230860);
        localButton.setText(((SearchHotKeyword)localList.get(i)).keyword);
        localButton.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            String str = (String)((Button)paramAnonymousView).getText();
            SearchHistoryView.this.mListener.submitQuery(str);
            QTMSGManage.getInstance().sendStatistcsMessage("search_clickhotkeyword");
          }
        });
        this.mHotContainer.addView(localRelativeLayout);
      }
      findViewById(2131230858).setVisibility(0);
      this.mHotContainer.setVisibility(0);
      return;
    }
    InfoManager.getInstance().loadHotSearchKeywords(this.mListener);
    findViewById(2131230858).setVisibility(8);
    this.mHotContainer.setVisibility(8);
  }

  protected void setRecents()
  {
    List localList = InfoManager.getInstance().root().mSearchNode.getRecentKeywords();
    this.mRecentsContainer.removeAllViews();
    if ((localList != null) && (localList.size() > 0))
    {
      for (int i = 0; i < localList.size(); i++)
      {
        RelativeLayout localRelativeLayout = (RelativeLayout)this.mLayoutInflater.inflate(2130903061, null);
        Button localButton = (Button)localRelativeLayout.findViewById(2131230860);
        localButton.setText((String)localList.get(i));
        localButton.setOnClickListener(new View.OnClickListener()
        {
          public void onClick(View paramAnonymousView)
          {
            QTMSGManage.getInstance().sendStatistcsMessage("search_recent");
            String str = (String)((Button)paramAnonymousView).getText();
            SearchHistoryView.this.mListener.submitQuery(str);
          }
        });
        this.mRecentsContainer.addView(localRelativeLayout);
      }
      findViewById(2131230856).setVisibility(0);
      this.mRecentsContainer.setVisibility(0);
      return;
    }
    findViewById(2131230856).setVisibility(8);
    this.mRecentsContainer.setVisibility(8);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.search.SearchHistoryView
 * JD-Core Version:    0.6.2
 */