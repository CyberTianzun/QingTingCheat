package com.tencent.weibo.sdk.android.api.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.weibo.sdk.android.api.util.BackGroudSeletor;
import java.util.List;

public class ConversationAdapter extends BaseAdapter
{
  private Context ct;
  private List<String> cvlist;

  public ConversationAdapter(Context paramContext, List<String> paramList)
  {
    this.ct = paramContext;
    this.cvlist = paramList;
  }

  public int getCount()
  {
    return this.cvlist.size();
  }

  public List<String> getCvlist()
  {
    return this.cvlist;
  }

  public Object getItem(int paramInt)
  {
    return this.cvlist.get(paramInt);
  }

  public long getItemId(int paramInt)
  {
    return paramInt;
  }

  public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
    {
      LinearLayout localLinearLayout = new LinearLayout(this.ct);
      new LinearLayout.LayoutParams(-1, -2);
      localLinearLayout.setBackgroundDrawable(BackGroudSeletor.getdrawble("topic_", this.ct));
      localLinearLayout.setGravity(16);
      localLinearLayout.setPadding(10, 0, 10, 0);
      TextView localTextView = new TextView(this.ct);
      localTextView.setTextColor(Color.parseColor("#108ece"));
      localTextView.setText(getItem(paramInt).toString());
      localTextView.setGravity(16);
      localTextView.setTextSize(18.0F);
      localLinearLayout.addView(localTextView);
      localLinearLayout.setTag(localTextView);
      return localLinearLayout;
    }
    ((TextView)paramView.getTag()).setText(getItem(paramInt).toString());
    return paramView;
  }

  public void setCvlist(List<String> paramList)
  {
    this.cvlist = paramList;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.api.adapter.ConversationAdapter
 * JD-Core Version:    0.6.2
 */