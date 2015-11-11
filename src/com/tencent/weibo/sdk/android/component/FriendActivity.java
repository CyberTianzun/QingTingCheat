package com.tencent.weibo.sdk.android.component;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import com.tencent.weibo.sdk.android.api.PublishWeiBoAPI;
import com.tencent.weibo.sdk.android.api.adapter.FriendAdapter;
import com.tencent.weibo.sdk.android.api.util.BackGroudSeletor;
import com.tencent.weibo.sdk.android.api.util.FirendCompare;
import com.tencent.weibo.sdk.android.api.util.HypyUtil;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.Firend;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpCallback;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FriendActivity extends Activity
  implements LetterListView.OnTouchingLetterChangedListener, HttpCallback
{
  private FriendAdapter adapter;
  private Map<String, List<Firend>> child = new HashMap();
  private ProgressDialog dialog;
  private EditText editText;
  private List<String> group = new ArrayList();
  private List<String> groups = new ArrayList();
  private LetterListView letterListView;
  private ExpandableListView listView;
  private Map<String, List<Firend>> newchild = new HashMap();
  private List<String> newgourp = new ArrayList();
  private int[] nums;
  private TextView textView;

  private void ex(final List<String> paramList, final Map<String, List<Firend>> paramMap)
  {
    for (int i = 0; ; i++)
    {
      if (i >= paramList.size())
      {
        this.listView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener()
        {
          public void onGroupExpand(int paramAnonymousInt)
          {
          }
        });
        this.listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
        {
          public boolean onGroupClick(ExpandableListView paramAnonymousExpandableListView, View paramAnonymousView, int paramAnonymousInt, long paramAnonymousLong)
          {
            return true;
          }
        });
        this.listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {
          public boolean onChildClick(ExpandableListView paramAnonymousExpandableListView, View paramAnonymousView, int paramAnonymousInt1, int paramAnonymousInt2, long paramAnonymousLong)
          {
            Intent localIntent = new Intent();
            localIntent.setClass(FriendActivity.this, PublishActivity.class);
            localIntent.putExtra("firend", ((Firend)((List)paramMap.get(paramList.get(paramAnonymousInt1))).get(paramAnonymousInt2)).getNick());
            FriendActivity.this.setResult(-1, localIntent);
            FriendActivity.this.finish();
            return true;
          }
        });
        this.listView.setOnScrollListener(new AbsListView.OnScrollListener()
        {
          public void onScroll(AbsListView paramAnonymousAbsListView, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
          {
            Log.d("first", paramAnonymousInt1);
            for (int i = 0; ; i++)
            {
              if (i >= paramList.size())
                return;
              if ((i == 0) && (paramAnonymousInt1 >= 0) && (paramAnonymousInt1 < FriendActivity.this.nums[i]))
              {
                FriendActivity.this.textView.setText(((String)paramList.get(i)).toUpperCase());
                return;
              }
              if ((paramAnonymousInt1 < FriendActivity.this.nums[i]) && (paramAnonymousInt1 >= FriendActivity.this.nums[(i - 1)]))
              {
                FriendActivity.this.textView.setText(((String)paramList.get(i)).toUpperCase());
                return;
              }
            }
          }

          public void onScrollStateChanged(AbsListView paramAnonymousAbsListView, int paramAnonymousInt)
          {
          }
        });
        return;
      }
      this.listView.expandGroup(i);
    }
  }

  private void getJsonData(JSONObject paramJSONObject)
  {
    ArrayList localArrayList1 = new ArrayList();
    int i;
    try
    {
      JSONArray localJSONArray = paramJSONObject.getJSONObject("data").getJSONArray("info");
      for (int j = 0; ; j++)
      {
        if (j >= localJSONArray.length())
        {
          Collections.sort(localArrayList1, new FirendCompare());
          i = 0;
          if (i < localArrayList1.size())
            break;
          return;
        }
        Firend localFirend = new Firend();
        localFirend.setNick(((JSONObject)localJSONArray.get(j)).getString("nick"));
        localFirend.setName(((JSONObject)localJSONArray.get(j)).getString("name"));
        localFirend.setHeadurl(((JSONObject)localJSONArray.get(j)).getString("headurl").replaceAll("\\/", "/") + "/180");
        localArrayList1.add(localFirend);
      }
    }
    catch (JSONException localJSONException)
    {
      while (true)
        localJSONException.printStackTrace();
    }
    if (this.child.get(HypyUtil.cn2py(((Firend)localArrayList1.get(i)).getNick()).substring(0, 1).toUpperCase()) != null)
      ((List)this.child.get(HypyUtil.cn2py(((Firend)localArrayList1.get(i)).getNick()).substring(0, 1).toUpperCase())).add((Firend)localArrayList1.get(i));
    while (true)
    {
      i++;
      break;
      Log.d("group", HypyUtil.cn2py(((Firend)localArrayList1.get(i)).getNick()).substring(0, 1));
      this.group.add(HypyUtil.cn2py(((Firend)localArrayList1.get(i)).getNick()).substring(0, 1).toUpperCase());
      ArrayList localArrayList2 = new ArrayList();
      localArrayList2.add((Firend)localArrayList1.get(i));
      this.child.put(HypyUtil.cn2py(((Firend)localArrayList1.get(i)).getNick()).substring(0, 1).toUpperCase(), localArrayList2);
    }
  }

  private void getdate()
  {
    if (this.dialog == null)
    {
      this.dialog = new ProgressDialog(this);
      this.dialog.setMessage("请稍后...");
      this.dialog.show();
    }
    new PublishWeiBoAPI(new AccountModel(Util.getSharePersistent(getApplicationContext(), "ACCESS_TOKEN"))).mutual_list(this, this, null, 0, 0, 0, 10);
  }

  private View initview()
  {
    LinearLayout localLinearLayout1 = new LinearLayout(this);
    LinearLayout.LayoutParams localLayoutParams = new LinearLayout.LayoutParams(-1, -1);
    localLinearLayout1.setLayoutParams(localLayoutParams);
    localLinearLayout1.setOrientation(1);
    RelativeLayout localRelativeLayout1 = new RelativeLayout(this);
    localRelativeLayout1.setLayoutParams(new RelativeLayout.LayoutParams(-1, -2));
    localRelativeLayout1.setGravity(0);
    localRelativeLayout1.setBackgroundDrawable(BackGroudSeletor.getdrawble("up_bg2x", this));
    RelativeLayout.LayoutParams localLayoutParams1 = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams1.addRule(15);
    localLayoutParams1.addRule(9, -1);
    localLayoutParams1.addRule(15, -1);
    localLayoutParams1.topMargin = 10;
    localLayoutParams1.leftMargin = 10;
    localLayoutParams1.bottomMargin = 10;
    Button localButton = new Button(this);
    localButton.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(new String[] { "return_btn2x", "return_btn_hover" }, this));
    localButton.setText("  返回");
    localButton.setLayoutParams(localLayoutParams1);
    View.OnClickListener local1 = new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        FriendActivity.this.finish();
      }
    };
    localButton.setOnClickListener(local1);
    TextView localTextView1 = new TextView(this);
    localTextView1.setText("好友列表");
    localTextView1.setTextColor(-1);
    localTextView1.setTextSize(24.0F);
    RelativeLayout.LayoutParams localLayoutParams2 = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams2.addRule(13, -1);
    localTextView1.setLayoutParams(localLayoutParams2);
    localRelativeLayout1.addView(localTextView1);
    localRelativeLayout1.addView(localButton);
    localLinearLayout1.addView(localRelativeLayout1);
    LinearLayout localLinearLayout2 = new LinearLayout(this);
    localLinearLayout2.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
    localLinearLayout2.setOrientation(0);
    localLinearLayout2.setGravity(17);
    EditText localEditText1 = new EditText(this);
    this.editText = localEditText1;
    this.editText.setLayoutParams(new LinearLayout.LayoutParams(-1, -2, 1.0F));
    this.editText.setPadding(20, 0, 10, 0);
    this.editText.setBackgroundDrawable(BackGroudSeletor.getdrawble("searchbg_", this));
    this.editText.setCompoundDrawablesWithIntrinsicBounds(BackGroudSeletor.getdrawble("search_", this), null, null, null);
    this.editText.setHint("搜索");
    this.editText.setTextSize(18.0F);
    EditText localEditText2 = this.editText;
    TextWatcher local2 = new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable)
      {
        FriendActivity.this.search(paramAnonymousEditable.toString());
      }

      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
      }

      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
      }
    };
    localEditText2.addTextChangedListener(local2);
    localLinearLayout2.addView(this.editText);
    localLinearLayout1.addView(localLinearLayout2);
    ExpandableListView localExpandableListView = new ExpandableListView(this);
    this.listView = localExpandableListView;
    new FrameLayout.LayoutParams(-1, -1);
    this.listView.setLayoutParams(localLayoutParams);
    this.listView.setGroupIndicator(null);
    LinearLayout localLinearLayout3 = new LinearLayout(this);
    localLinearLayout3.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
    TextView localTextView2 = new TextView(this);
    this.textView = localTextView2;
    localLinearLayout3.setPadding(30, 0, 0, 0);
    localLinearLayout3.setGravity(16);
    this.textView.setTextSize(18.0F);
    this.textView.setTextColor(-1);
    this.textView.setText("常用联系人");
    localLinearLayout3.addView(this.textView);
    localLinearLayout3.setBackgroundColor(Color.parseColor("#b0bac3"));
    LetterListView localLetterListView = new LetterListView(this, this.group);
    this.letterListView = localLetterListView;
    this.letterListView.setOnTouchingLetterChangedListener(this);
    RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(50, -1);
    RelativeLayout localRelativeLayout2 = new RelativeLayout(this);
    RelativeLayout.LayoutParams localLayoutParams4 = new RelativeLayout.LayoutParams(-1, -2);
    localLayoutParams3.addRule(11);
    this.letterListView.setLayoutParams(localLayoutParams3);
    localRelativeLayout2.setLayoutParams(localLayoutParams4);
    localRelativeLayout2.addView(this.listView);
    localRelativeLayout2.addView(localLinearLayout3);
    localRelativeLayout2.addView(this.letterListView);
    localLinearLayout1.addView(localRelativeLayout2);
    return localLinearLayout1;
  }

  private int totle(int paramInt)
  {
    if (paramInt == 0)
      return 1 + ((List)this.child.get(this.group.get(paramInt))).size();
    return 1 + ((List)this.child.get(this.group.get(paramInt))).size() + totle(paramInt - 1);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    LinearLayout localLinearLayout = (LinearLayout)initview();
    getdate();
    setContentView(localLinearLayout);
  }

  public void onResult(Object paramObject)
  {
    if ((this.dialog != null) && (this.dialog.isShowing()))
      this.dialog.dismiss();
    if ((paramObject != null) && (((ModelResult)paramObject).isSuccess()))
    {
      getJsonData((JSONObject)((ModelResult)paramObject).getObj());
      this.nums = new int[this.group.size()];
    }
    for (int i = 0; ; i++)
    {
      if (i >= this.nums.length)
      {
        this.letterListView.setB(this.group);
        this.adapter = new FriendAdapter(this, this.group, this.child);
        this.listView.setAdapter(this.adapter);
        ex(this.group, this.child);
        Log.d("发送成功", paramObject.toString());
        return;
      }
      this.nums[i] = totle(i);
    }
  }

  protected void onStop()
  {
    super.onStop();
    if ((this.dialog != null) && (this.dialog.isShowing()))
      this.dialog.dismiss();
  }

  public void onTouchingLetterChanged(int paramInt)
  {
    this.listView.setSelectedGroup(paramInt);
  }

  public void search(String paramString)
  {
    this.newgourp.clear();
    this.newchild.clear();
    int i = 0;
    if (i >= this.group.size())
    {
      Log.d("size", this.newgourp.size() + "---" + this.newchild.size());
      this.nums = new int[this.newgourp.size()];
    }
    for (int k = 0; ; k++)
    {
      if (k >= this.nums.length)
      {
        this.letterListView.setB(this.newgourp);
        this.adapter.setChild(this.newchild);
        this.adapter.setGroup(this.newgourp);
        this.adapter.notifyDataSetChanged();
        ex(this.newgourp, this.newchild);
        return;
        int j = 0;
        if (j >= ((List)this.child.get(this.group.get(i))).size())
        {
          i++;
          break;
        }
        if (((Firend)((List)this.child.get(this.group.get(i))).get(j)).getNick().contains(paramString))
        {
          if (this.newchild.get(this.group.get(i)) != null)
            break label365;
          ArrayList localArrayList = new ArrayList();
          localArrayList.add((Firend)((List)this.child.get(this.group.get(i))).get(j));
          this.newchild.put((String)this.group.get(i), localArrayList);
          this.newgourp.add((String)this.group.get(i));
        }
        while (true)
        {
          j++;
          break;
          label365: ((List)this.newchild.get(this.group.get(i))).add((Firend)((List)this.child.get(this.group.get(i))).get(j));
        }
      }
      this.nums[k] = totle(k);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.component.FriendActivity
 * JD-Core Version:    0.6.2
 */