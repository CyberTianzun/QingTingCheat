package com.tencent.weibo.sdk.android.api.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.weibo.sdk.android.api.util.BackGroudSeletor;
import com.tencent.weibo.sdk.android.model.Firend;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class FriendAdapter extends BaseExpandableListAdapter
{
  private Map<String, List<Firend>> child;
  private Context ctx;
  private List<String> group;

  public FriendAdapter(Context paramContext, List<String> paramList, Map<String, List<Firend>> paramMap)
  {
    this.ctx = paramContext;
    this.group = paramList;
    this.child = paramMap;
  }

  public Object getChild(int paramInt1, int paramInt2)
  {
    return ((List)this.child.get(getGroup(paramInt1))).get(paramInt2);
  }

  public Map<String, List<Firend>> getChild()
  {
    return this.child;
  }

  public long getChildId(int paramInt1, int paramInt2)
  {
    return paramInt2;
  }

  public View getChildView(final int paramInt1, final int paramInt2, boolean paramBoolean, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
    {
      LinearLayout localLinearLayout1 = new LinearLayout(this.ctx);
      new LinearLayout.LayoutParams(-1, -2);
      localLinearLayout1.setOrientation(0);
      localLinearLayout1.setGravity(16);
      localLinearLayout1.setPadding(15, 0, 10, 0);
      localLinearLayout1.setBackgroundDrawable(BackGroudSeletor.getdrawble("childbg_", this.ctx));
      ImageView localImageView = new ImageView(this.ctx);
      localImageView.setId(4502);
      localImageView.setLayoutParams(new LinearLayout.LayoutParams(45, 45));
      localImageView.setImageDrawable(BackGroudSeletor.getdrawble("logo", this.ctx));
      LinearLayout localLinearLayout2 = new LinearLayout(this.ctx);
      localLinearLayout2.setPadding(10, 0, 0, 0);
      localLinearLayout2.setGravity(16);
      localLinearLayout2.setOrientation(1);
      TextView localTextView1 = new TextView(this.ctx);
      TextView localTextView2 = new TextView(this.ctx);
      localTextView1.setTextSize(18.0F);
      localTextView1.setId(4500);
      localTextView1.setTextColor(Color.parseColor("#32769b"));
      localTextView2.setTextSize(12.0F);
      localTextView2.setId(4501);
      localTextView2.setTextColor(Color.parseColor("#a6c6d5"));
      localTextView1.setText(((Firend)getChild(paramInt1, paramInt2)).getNick());
      localTextView2.setText(((Firend)getChild(paramInt1, paramInt2)).getName());
      localLinearLayout2.addView(localTextView1);
      localLinearLayout2.addView(localTextView2);
      localLinearLayout1.setBackgroundDrawable(BackGroudSeletor.getdrawble("childbg_", this.ctx));
      localLinearLayout1.addView(localImageView);
      localLinearLayout1.addView(localLinearLayout2);
      paramView = localLinearLayout1;
      paramView.setTag(localLinearLayout1);
    }
    while (true)
    {
      final View localView = paramView;
      if (((Firend)getChild(paramInt1, paramInt2)).getHeadurl() == null)
        break;
      AsyncTask local1 = new AsyncTask()
      {
        protected Bitmap doInBackground(String[] paramAnonymousArrayOfString)
        {
          try
          {
            Log.d("image urel", ((Firend)FriendAdapter.this.getChild(paramInt1, paramInt2)).getHeadurl());
            URL localURL = new URL(((Firend)FriendAdapter.this.getChild(paramInt1, paramInt2)).getHeadurl());
            String str = localURL.openConnection().getHeaderField(0);
            if (str.indexOf("200") < 0)
              Log.d("图片文件不存在或路径错误，错误代码：", str);
            Bitmap localBitmap = BitmapFactory.decodeStream(localURL.openStream());
            return localBitmap;
          }
          catch (MalformedURLException localMalformedURLException)
          {
            localMalformedURLException.printStackTrace();
            return null;
          }
          catch (IOException localIOException)
          {
            localIOException.printStackTrace();
          }
          return null;
        }

        protected void onPostExecute(Bitmap paramAnonymousBitmap)
        {
          ImageView localImageView = (ImageView)((LinearLayout)localView.getTag()).findViewById(4502);
          if (paramAnonymousBitmap == null)
            localImageView.setImageDrawable(BackGroudSeletor.getdrawble("logo", FriendAdapter.this.ctx));
          while (true)
          {
            super.onPostExecute(paramAnonymousBitmap);
            return;
            localImageView.setImageBitmap(paramAnonymousBitmap);
          }
        }

        protected void onPreExecute()
        {
          super.onPreExecute();
        }
      };
      String[] arrayOfString = new String[1];
      arrayOfString[0] = ((Firend)getChild(paramInt1, paramInt2)).getHeadurl();
      local1.execute(arrayOfString);
      return paramView;
      LinearLayout localLinearLayout3 = (LinearLayout)paramView.getTag();
      TextView localTextView3 = (TextView)localLinearLayout3.findViewById(4500);
      TextView localTextView4 = (TextView)localLinearLayout3.findViewById(4501);
      ((ImageView)((LinearLayout)localLinearLayout3.getTag()).findViewById(4502)).setImageDrawable(BackGroudSeletor.getdrawble("logo", this.ctx));
      localTextView3.setText(((Firend)getChild(paramInt1, paramInt2)).getNick());
      localTextView4.setText(((Firend)getChild(paramInt1, paramInt2)).getName());
    }
    ((ImageView)((LinearLayout)paramView.getTag()).findViewById(4502)).setImageDrawable(BackGroudSeletor.getdrawble("logo", this.ctx));
    return paramView;
  }

  public int getChildrenCount(int paramInt)
  {
    return ((List)this.child.get(this.group.get(paramInt))).size();
  }

  public Object getGroup(int paramInt)
  {
    return this.group.get(paramInt);
  }

  public List<String> getGroup()
  {
    return this.group;
  }

  public int getGroupCount()
  {
    return this.group.size();
  }

  public long getGroupId(int paramInt)
  {
    return paramInt;
  }

  public View getGroupView(int paramInt, boolean paramBoolean, View paramView, ViewGroup paramViewGroup)
  {
    if (paramView == null)
    {
      LinearLayout localLinearLayout = new LinearLayout(this.ctx);
      TextView localTextView = new TextView(this.ctx);
      localLinearLayout.setPadding(30, 0, 0, 0);
      localLinearLayout.setGravity(16);
      localTextView.setTextSize(18.0F);
      localTextView.setTextColor(-1);
      localTextView.setWidth(40);
      localTextView.setText(getGroup(paramInt).toString().toUpperCase());
      localLinearLayout.addView(localTextView);
      localLinearLayout.setBackgroundDrawable(BackGroudSeletor.getdrawble("groupbg_", this.ctx));
      localTextView.setTag(Integer.valueOf(paramInt));
      localLinearLayout.setTag(localTextView);
      return localLinearLayout;
    }
    ((TextView)paramView.getTag()).setText(getGroup(paramInt).toString().toUpperCase());
    return paramView;
  }

  public boolean hasStableIds()
  {
    return true;
  }

  public boolean isChildSelectable(int paramInt1, int paramInt2)
  {
    return true;
  }

  public void setChild(Map<String, List<Firend>> paramMap)
  {
    this.child = paramMap;
  }

  public void setGroup(List<String> paramList)
  {
    this.group = paramList;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.api.adapter.FriendAdapter
 * JD-Core Version:    0.6.2
 */