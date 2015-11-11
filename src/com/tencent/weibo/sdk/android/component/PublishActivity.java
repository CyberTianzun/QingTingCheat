package com.tencent.weibo.sdk.android.component;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore.Images.Media;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import com.tencent.weibo.sdk.android.api.WeiboAPI;
import com.tencent.weibo.sdk.android.api.util.BackGroudSeletor;
import com.tencent.weibo.sdk.android.api.util.Util;
import com.tencent.weibo.sdk.android.model.AccountModel;
import com.tencent.weibo.sdk.android.model.ModelResult;
import com.tencent.weibo.sdk.android.network.HttpCallback;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class PublishActivity extends Activity
  implements View.OnClickListener, HttpCallback
{
  private String accessToken;
  private ImageButton button_camera;
  private ImageButton button_conversation;
  private Button button_esc;
  private ImageButton button_friend;
  private ImageButton button_location;
  private Button button_send;
  private Context context;
  private ProgressDialog dialog;
  private EditText editText_text;
  private String edstring = "";
  private FrameLayout frameLayout_big;
  private FrameLayout frameLayout_icon;
  private Handler handler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      super.handleMessage(paramAnonymousMessage);
      int i = paramAnonymousMessage.what;
      if (i == 5)
      {
        PublishActivity.this.frameLayout_big.setVisibility(0);
        PublishActivity.this.frameLayout_icon.setVisibility(0);
      }
      do
      {
        return;
        if (i == 0)
        {
          PublishActivity.this.popupWindow.showAsDropDown(PublishActivity.this.layout_set);
          InputMethodManager localInputMethodManager = (InputMethodManager)PublishActivity.this.getSystemService("input_method");
          Log.d("alive", localInputMethodManager.isActive());
          if (localInputMethodManager.isActive())
          {
            localInputMethodManager.hideSoftInputFromWindow(PublishActivity.this.editText_text.getWindowToken(), 0);
            Log.d("alive", localInputMethodManager.isActive());
          }
        }
        if (i == 10)
          PublishActivity.this.button_location.setBackgroundDrawable(BackGroudSeletor.getdrawble("dingwei_icon_hover2x", PublishActivity.this));
      }
      while (i != 15);
      Toast.makeText(PublishActivity.this, "定位失败", 0).show();
      PublishActivity.this.button_location.setBackgroundDrawable(BackGroudSeletor.getdrawble("dingwei_icon2x", PublishActivity.this));
    }
  };
  private ImageView imageView_big;
  private ImageView imageView_bound;
  private ImageView imageView_delete;
  private ImageView imageView_icon;
  private LinearLayout layout_big_delete;
  private LinearLayout layout_imagebound;
  private LinearLayout layout_set;
  private Map<String, String> location;
  private int[] lyout = new int[2];
  private Bitmap mBitmap = null;
  private Location mLocation;
  private PopupWindow popupWindow;
  private TextView textView_num;
  private LinearLayout viewroot;
  private WeiboAPI weiboAPI;

  private int[] getarea(int[] paramArrayOfInt)
  {
    int[] arrayOfInt = new int[2];
    if (paramArrayOfInt != null)
    {
      if ((paramArrayOfInt[0] <= paramArrayOfInt[1]) || (paramArrayOfInt[0] < 300))
        break label83;
      arrayOfInt[0] = 300;
      arrayOfInt[1] = ((int)(300.0F * (paramArrayOfInt[1] / paramArrayOfInt[0])));
    }
    while (true)
    {
      Log.d("myarea", arrayOfInt[0] + "....." + arrayOfInt[1]);
      return arrayOfInt;
      label83: if ((paramArrayOfInt[0] > paramArrayOfInt[1]) && (paramArrayOfInt[0] < 300))
      {
        arrayOfInt[0] = paramArrayOfInt[0];
        arrayOfInt[1] = paramArrayOfInt[1];
      }
      else if ((paramArrayOfInt[0] < paramArrayOfInt[1]) && (paramArrayOfInt[1] >= 300))
      {
        arrayOfInt[0] = ((int)(300.0F * (paramArrayOfInt[0] / paramArrayOfInt[1])));
        arrayOfInt[1] = 300;
      }
      else if ((paramArrayOfInt[0] < paramArrayOfInt[1]) && (paramArrayOfInt[0] < 300))
      {
        arrayOfInt[0] = paramArrayOfInt[0];
        arrayOfInt[1] = paramArrayOfInt[1];
      }
      else if ((paramArrayOfInt[0] == paramArrayOfInt[1]) && (paramArrayOfInt[0] >= 300))
      {
        arrayOfInt[0] = 300;
        arrayOfInt[1] = 300;
      }
      else if ((paramArrayOfInt[0] == paramArrayOfInt[1]) && (paramArrayOfInt[0] < 300))
      {
        arrayOfInt[0] = paramArrayOfInt[0];
        arrayOfInt[1] = paramArrayOfInt[1];
      }
    }
  }

  private View initview()
  {
    LinearLayout localLinearLayout1 = new LinearLayout(this);
    this.viewroot = localLinearLayout1;
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(-1, -1);
    FrameLayout.LayoutParams localLayoutParams2 = new FrameLayout.LayoutParams(-2, -2);
    RelativeLayout.LayoutParams localLayoutParams3 = new RelativeLayout.LayoutParams(-2, -2);
    this.viewroot.setLayoutParams(localLayoutParams1);
    this.viewroot.setOrientation(1);
    LinearLayout.LayoutParams localLayoutParams4 = new LinearLayout.LayoutParams(-1, -2);
    RelativeLayout.LayoutParams localLayoutParams5 = new RelativeLayout.LayoutParams(-1, -2);
    RelativeLayout localRelativeLayout = new RelativeLayout(this);
    localRelativeLayout.setLayoutParams(localLayoutParams5);
    localRelativeLayout.setBackgroundDrawable(BackGroudSeletor.getdrawble("up_bg2x", getApplication()));
    localRelativeLayout.setGravity(0);
    Button localButton1 = new Button(this);
    this.button_esc = localButton1;
    localLayoutParams3.addRule(9, -1);
    localLayoutParams3.addRule(15, -1);
    localLayoutParams3.topMargin = 10;
    localLayoutParams3.leftMargin = 10;
    localLayoutParams3.bottomMargin = 10;
    this.button_esc.setLayoutParams(localLayoutParams3);
    this.button_esc.setText("取消");
    this.button_esc.setClickable(true);
    this.button_esc.setId(5001);
    String[] arrayOfString1 = { "quxiao_btn2x", "quxiao_btn_hover" };
    this.button_esc.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(arrayOfString1, this));
    Button localButton2 = new Button(this);
    this.button_send = localButton2;
    RelativeLayout.LayoutParams localLayoutParams6 = new RelativeLayout.LayoutParams(-2, -2);
    localLayoutParams6.addRule(11, -1);
    localLayoutParams6.addRule(15, -1);
    localLayoutParams6.topMargin = 10;
    localLayoutParams6.rightMargin = 10;
    localLayoutParams6.bottomMargin = 10;
    this.button_send.setLayoutParams(localLayoutParams6);
    LinearLayout localLinearLayout2 = new LinearLayout(this);
    LinearLayout.LayoutParams localLayoutParams7 = new LinearLayout.LayoutParams(-2, -2, 1.0F);
    localLinearLayout2.setLayoutParams(localLayoutParams7);
    this.button_send.setText("发送");
    this.button_send.setClickable(true);
    this.button_send.setId(5002);
    String[] arrayOfString2 = { "sent_btn_22x", "sent_btn_hover" };
    this.button_send.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(arrayOfString2, this));
    localRelativeLayout.addView(this.button_esc);
    localRelativeLayout.addView(this.button_send);
    LinearLayout localLinearLayout3 = new LinearLayout(this);
    localLinearLayout3.setLayoutParams(localLayoutParams4);
    localLinearLayout3.setLayoutParams(localLayoutParams4);
    localLinearLayout3.setOrientation(1);
    localLinearLayout3.setBackgroundColor(-1);
    localLinearLayout3.requestFocus();
    EditText localEditText = new EditText(this);
    this.editText_text = localEditText;
    this.editText_text.setBackgroundColor(-1);
    this.editText_text.setMaxLines(4);
    this.editText_text.setMinLines(4);
    this.editText_text.setMinEms(4);
    this.editText_text.setMaxEms(4);
    this.editText_text.setFocusable(true);
    this.editText_text.requestFocus();
    this.editText_text.setText(this.edstring);
    this.editText_text.setSelection(this.edstring.length());
    this.editText_text.setScrollbarFadingEnabled(true);
    this.editText_text.setGravity(48);
    this.editText_text.setMovementMethod(ScrollingMovementMethod.getInstance());
    this.editText_text.setId(5003);
    FrameLayout localFrameLayout1 = new FrameLayout(this);
    this.frameLayout_icon = localFrameLayout1;
    this.frameLayout_icon.setLayoutParams(localLayoutParams2);
    LinearLayout localLinearLayout4 = new LinearLayout(this);
    localLinearLayout4.setGravity(21);
    localLinearLayout4.setLayoutParams(new LinearLayout.LayoutParams(54, 45));
    localLinearLayout4.setPadding(0, 0, 2, 0);
    ImageView localImageView1 = new ImageView(this);
    this.imageView_icon = localImageView1;
    this.imageView_icon.setId(5004);
    ImageView localImageView2 = new ImageView(this);
    this.imageView_bound = localImageView2;
    this.imageView_bound.setId(5005);
    this.imageView_bound.setLayoutParams(new LinearLayout.LayoutParams(54, 45));
    this.imageView_icon.setLayoutParams(new LinearLayout.LayoutParams(33, 33));
    this.imageView_bound.setImageDrawable(BackGroudSeletor.getdrawble("composeimageframe", this));
    this.frameLayout_icon.setVisibility(8);
    localLinearLayout4.addView(this.imageView_icon);
    this.frameLayout_icon.addView(localLinearLayout4);
    this.frameLayout_icon.addView(this.imageView_bound);
    localLinearLayout3.addView(this.editText_text);
    localLinearLayout3.addView(this.frameLayout_icon);
    LinearLayout localLinearLayout5 = new LinearLayout(this);
    this.layout_set = localLinearLayout5;
    this.layout_set.setLayoutParams(localLayoutParams4);
    this.layout_set.setBackgroundDrawable(BackGroudSeletor.getdrawble("icon_bg2x", this));
    this.layout_set.setOrientation(0);
    this.layout_set.setGravity(16);
    this.layout_set.setPadding(10, 0, 30, 0);
    LinearLayout localLinearLayout6 = new LinearLayout(this);
    localLinearLayout6.setOrientation(0);
    localLinearLayout6.setLayoutParams(localLayoutParams7);
    LinearLayout localLinearLayout7 = new LinearLayout(this);
    localLinearLayout7.setGravity(1);
    localLinearLayout7.setLayoutParams(localLayoutParams7);
    LinearLayout localLinearLayout8 = new LinearLayout(this);
    localLinearLayout8.setGravity(1);
    localLinearLayout8.setLayoutParams(localLayoutParams7);
    LinearLayout localLinearLayout9 = new LinearLayout(this);
    localLinearLayout9.setGravity(1);
    localLinearLayout9.setLayoutParams(localLayoutParams7);
    LinearLayout localLinearLayout10 = new LinearLayout(this);
    localLinearLayout10.setGravity(1);
    localLinearLayout10.setLayoutParams(localLayoutParams7);
    ImageButton localImageButton1 = new ImageButton(this);
    this.button_friend = localImageButton1;
    this.button_friend.setLayoutParams(localLayoutParams2);
    this.button_friend.setId(5006);
    ImageButton localImageButton2 = new ImageButton(this);
    this.button_conversation = localImageButton2;
    this.button_conversation.setLayoutParams(localLayoutParams2);
    this.button_conversation.setId(5007);
    ImageButton localImageButton3 = new ImageButton(this);
    this.button_camera = localImageButton3;
    this.button_camera.setLayoutParams(localLayoutParams2);
    this.button_camera.setId(5008);
    ImageButton localImageButton4 = new ImageButton(this);
    this.button_location = localImageButton4;
    this.button_location.setLayoutParams(localLayoutParams2);
    this.button_location.setId(5009);
    this.button_friend.setBackgroundDrawable(BackGroudSeletor.getdrawble("haoyou_icon2x", this));
    String[] arrayOfString3 = { "huati_icon2x", "huati_icon_hover2x" };
    this.button_conversation.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(arrayOfString3, this));
    String[] arrayOfString4 = { "pic_icon2x", "pic_icon_hover2x" };
    this.button_camera.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(arrayOfString4, this));
    String[] arrayOfString5 = { "dingwei_icon2x", "dingwei_icon_hover2x" };
    this.button_location.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(arrayOfString5, this));
    localLinearLayout7.addView(this.button_friend);
    localLinearLayout6.addView(localLinearLayout7);
    localLinearLayout8.addView(this.button_conversation);
    localLinearLayout6.addView(localLinearLayout8);
    localLinearLayout9.addView(this.button_camera);
    localLinearLayout6.addView(localLinearLayout9);
    localLinearLayout10.addView(this.button_location);
    localLinearLayout6.addView(localLinearLayout10);
    TextView localTextView = new TextView(this);
    this.textView_num = localTextView;
    this.textView_num.setText("140");
    this.textView_num.setTextColor(Color.parseColor("#999999"));
    this.textView_num.setGravity(5);
    this.textView_num.setLayoutParams(localLayoutParams7);
    this.textView_num.setId(5010);
    this.textView_num.setWidth(40);
    LinearLayout localLinearLayout11 = new LinearLayout(this);
    localLinearLayout11.setLayoutParams(localLayoutParams7);
    localLinearLayout11.setGravity(21);
    localLinearLayout11.addView(this.textView_num);
    this.layout_set.addView(localLinearLayout6);
    this.layout_set.addView(localLinearLayout11);
    LinearLayout localLinearLayout12 = new LinearLayout(this);
    LinearLayout.LayoutParams localLayoutParams8 = new LinearLayout.LayoutParams(-1, -2, 1.0F);
    localLinearLayout12.setLayoutParams(localLayoutParams8);
    localLinearLayout12.setGravity(17);
    localLinearLayout12.setBackgroundDrawable(BackGroudSeletor.getdrawble("bg", this));
    FrameLayout localFrameLayout2 = new FrameLayout(this);
    this.frameLayout_big = localFrameLayout2;
    FrameLayout.LayoutParams localLayoutParams9 = new FrameLayout.LayoutParams(-2, -2);
    this.frameLayout_big.setLayoutParams(localLayoutParams9);
    this.frameLayout_big.setPadding(10, 10, 0, 0);
    LinearLayout localLinearLayout13 = new LinearLayout(this);
    this.layout_imagebound = localLinearLayout13;
    this.layout_imagebound.setPadding(2, 2, 2, 2);
    this.layout_imagebound.setBackgroundDrawable(BackGroudSeletor.getdrawble("pic_biankuang2x", this));
    LinearLayout localLinearLayout14 = new LinearLayout(this);
    this.layout_big_delete = localLinearLayout14;
    LinearLayout.LayoutParams localLayoutParams10 = new LinearLayout.LayoutParams(10 + getarea(this.lyout)[0], 10 + getarea(this.lyout)[1]);
    this.layout_big_delete.setLayoutParams(localLayoutParams10);
    this.layout_imagebound.setGravity(17);
    this.layout_imagebound.setId(5011);
    this.layout_imagebound.setLayoutParams(new LinearLayout.LayoutParams(getarea(this.lyout)[0], getarea(this.lyout)[1]));
    ImageView localImageView3 = new ImageView(this);
    this.imageView_big = localImageView3;
    this.imageView_big.setId(5012);
    this.layout_imagebound.addView(this.imageView_big);
    ImageView localImageView4 = new ImageView(this);
    this.imageView_delete = localImageView4;
    this.imageView_delete.setId(5013);
    this.imageView_delete.setLayoutParams(new LinearLayout.LayoutParams(-2, -2));
    this.imageView_delete.setImageDrawable(BackGroudSeletor.getdrawble("close", this));
    this.layout_big_delete.addView(this.imageView_delete);
    this.frameLayout_big.addView(this.layout_imagebound);
    this.frameLayout_big.addView(this.layout_big_delete);
    this.frameLayout_big.setVisibility(8);
    localLinearLayout12.addView(this.frameLayout_big);
    this.viewroot.addView(localRelativeLayout);
    this.viewroot.addView(localLinearLayout3);
    this.viewroot.addView(this.layout_set);
    this.viewroot.addView(localLinearLayout12);
    return this.viewroot;
  }

  private void setonclick()
  {
    this.button_esc.setOnClickListener(this);
    this.button_send.setOnClickListener(this);
    this.editText_text.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable)
      {
        try
        {
          PublishActivity.this.edstring = paramAnonymousEditable.toString();
          String str = 140 - paramAnonymousEditable.toString().getBytes("gbk").length / 2;
          Log.d("contentafter", str);
          PublishActivity.this.textView_num.setText(str);
          return;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          localUnsupportedEncodingException.printStackTrace();
        }
      }

      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
      }

      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        try
        {
          Log.d("contentafter", paramAnonymousCharSequence.toString().getBytes("gbk").length);
          return;
        }
        catch (UnsupportedEncodingException localUnsupportedEncodingException)
        {
          localUnsupportedEncodingException.printStackTrace();
        }
      }
    });
    this.imageView_bound.setOnClickListener(this);
    this.imageView_delete.setOnClickListener(this);
    this.button_friend.setOnClickListener(this);
    this.button_conversation.setOnClickListener(this);
    this.button_camera.setOnClickListener(this);
    this.button_location.setOnClickListener(this);
  }

  private View showView()
  {
    LinearLayout localLinearLayout1 = new LinearLayout(this);
    localLinearLayout1.setLayoutParams(new LinearLayout.LayoutParams(-1, -2));
    localLinearLayout1.setBackgroundDrawable(BackGroudSeletor.getdrawble("bg", this));
    localLinearLayout1.setOrientation(1);
    localLinearLayout1.setPadding(50, 50, 50, 50);
    localLinearLayout1.setGravity(17);
    localLinearLayout1.requestFocus();
    LinearLayout.LayoutParams localLayoutParams1 = new LinearLayout.LayoutParams(-1, -2);
    LinearLayout localLinearLayout2 = new LinearLayout(this);
    localLinearLayout2.setLayoutParams(localLayoutParams1);
    localLinearLayout2.setPadding(0, 0, 0, 0);
    LinearLayout localLinearLayout3 = new LinearLayout(this);
    localLinearLayout3.setLayoutParams(localLayoutParams1);
    localLinearLayout3.setPadding(0, 10, 0, 30);
    new LinearLayout(this);
    LinearLayout.LayoutParams localLayoutParams2 = new LinearLayout.LayoutParams(-1, -2);
    Button localButton1 = new Button(this);
    localButton1.setId(5014);
    localButton1.setOnClickListener(this);
    localButton1.setLayoutParams(localLayoutParams2);
    localButton1.setText("拍照");
    String[] arrayOfString = { "btn1_", "btn1_hover_" };
    localButton1.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(arrayOfString, this));
    Button localButton2 = new Button(this);
    localButton2.setId(5015);
    localButton2.setOnClickListener(this);
    localButton2.setLayoutParams(localLayoutParams2);
    localButton2.setText("相册");
    localButton2.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(arrayOfString, this));
    Button localButton3 = new Button(this);
    localButton3.setId(5016);
    localButton3.setOnClickListener(this);
    localButton3.setLayoutParams(localLayoutParams2);
    localButton3.setText("取消");
    localButton3.setBackgroundDrawable(BackGroudSeletor.createBgByImageIds(new String[] { "btn2_", "btn1_hover_" }, this));
    localLinearLayout3.addView(localButton2);
    localLinearLayout1.addView(localButton1);
    localLinearLayout1.addView(localLinearLayout3);
    localLinearLayout1.addView(localButton3);
    return localLinearLayout1;
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    super.onActivityResult(paramInt1, paramInt2, paramIntent);
    Cursor localCursor;
    String str;
    if ((paramInt1 == 1000) && (paramInt2 == -1) && (paramIntent != null))
    {
      Uri localUri = paramIntent.getData();
      String[] arrayOfString = { "_data" };
      localCursor = getContentResolver().query(localUri, arrayOfString, null, null, null);
      localCursor.moveToFirst();
      str = localCursor.getString(localCursor.getColumnIndex(arrayOfString[0]));
      Log.d("path", str);
      new int[2];
    }
    do
    {
      try
      {
        FileInputStream localFileInputStream = new FileInputStream(str);
        BitmapFactory.Options localOptions = new BitmapFactory.Options();
        localOptions.inJustDecodeBounds = false;
        localOptions.inSampleSize = 6;
        Bitmap localBitmap2 = BitmapFactory.decodeStream(localFileInputStream, null, localOptions);
        ByteArrayOutputStream localByteArrayOutputStream2 = new ByteArrayOutputStream();
        localBitmap2.compress(Bitmap.CompressFormat.JPEG, 100, localByteArrayOutputStream2);
        this.mBitmap = localBitmap2;
        this.lyout[0] = localBitmap2.getWidth();
        this.lyout[1] = localBitmap2.getHeight();
        setContentView(initview());
        setonclick();
        this.imageView_icon.setImageDrawable(new BitmapDrawable(localBitmap2));
        this.imageView_big.setImageDrawable(new BitmapDrawable(localBitmap2));
        this.frameLayout_icon.setVisibility(0);
        this.frameLayout_big.setVisibility(0);
        localCursor.close();
        if ((this.popupWindow != null) && (this.popupWindow.isShowing()))
        {
          this.popupWindow.dismiss();
          new Timer().schedule(new TimerTask()
          {
            public void run()
            {
              InputMethodManager localInputMethodManager = (InputMethodManager)PublishActivity.this.getSystemService("input_method");
              Log.d("mks", localInputMethodManager.isActive());
              localInputMethodManager.toggleSoftInput(0, 2);
              Message localMessage = PublishActivity.this.handler.obtainMessage();
              localMessage.what = 5;
              PublishActivity.this.handler.sendMessage(localMessage);
            }
          }
          , 100L);
        }
        return;
      }
      catch (FileNotFoundException localFileNotFoundException)
      {
        while (true)
          localFileNotFoundException.printStackTrace();
      }
      catch (IOException localIOException)
      {
        while (true)
          localIOException.printStackTrace();
      }
      if ((paramInt1 == 2000) && (paramInt2 == -1) && (paramIntent != null))
      {
        if ((this.popupWindow != null) && (this.popupWindow.isShowing()))
          this.popupWindow.dismiss();
        Bitmap localBitmap1 = (Bitmap)paramIntent.getExtras().get("data");
        sendBroadcast(new Intent("android.intent.action.MEDIA_MOUNTED", Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        ByteArrayOutputStream localByteArrayOutputStream1 = new ByteArrayOutputStream();
        localBitmap1.compress(Bitmap.CompressFormat.PNG, 100, localByteArrayOutputStream1);
        this.mBitmap = localBitmap1;
        this.lyout[0] = localBitmap1.getWidth();
        this.lyout[1] = localBitmap1.getHeight();
        setContentView(initview());
        setonclick();
        this.imageView_icon.setImageDrawable(new BitmapDrawable(localBitmap1));
        this.imageView_big.setImageDrawable(new BitmapDrawable(localBitmap1));
        this.frameLayout_icon.setVisibility(0);
        this.frameLayout_big.setVisibility(0);
        return;
      }
      if ((paramInt1 == 5007) && (paramInt2 == -1) && (paramIntent != null))
      {
        this.edstring += paramIntent.getStringExtra("conversation");
        this.editText_text.setText(this.edstring);
        this.editText_text.setSelection(this.edstring.length());
        return;
      }
    }
    while ((paramInt1 != 5006) || (paramInt2 != -1) || (paramIntent == null));
    this.edstring = (this.edstring + "@" + paramIntent.getStringExtra("firend"));
    this.editText_text.setText(this.edstring);
    this.editText_text.setSelection(this.edstring.length());
  }

  public void onClick(View paramView)
  {
    final InputMethodManager localInputMethodManager = (InputMethodManager)getSystemService("input_method");
    switch (paramView.getId())
    {
    case 5003:
    case 5004:
    case 5010:
    case 5011:
    case 5012:
    default:
    case 5001:
    case 5002:
    case 5005:
    case 5006:
    case 5007:
    case 5008:
    case 5009:
    case 5013:
    case 5014:
    case 5015:
    case 5016:
    }
    do
    {
      do
      {
        String str;
        double d1;
        double d2;
        do
        {
          return;
          localInputMethodManager.hideSoftInputFromWindow(this.editText_text.getWindowToken(), 0);
          finish();
          return;
          str = this.editText_text.getText().toString();
          if (("".equals(str)) && (this.frameLayout_icon.getVisibility() == 8))
          {
            Toast.makeText(this, "无内容发送", 0).show();
            return;
          }
          if ((this.dialog != null) && (!this.dialog.isShowing()))
            this.dialog.show();
          if (Integer.parseInt(this.textView_num.getText().toString()) < 0)
          {
            Toast.makeText(this, "请重新输入少于140个字的内容", 0).show();
            return;
          }
          d1 = 0.0D;
          d2 = 0.0D;
          if (this.mLocation != null)
          {
            d1 = this.mLocation.getLongitude();
            d2 = this.mLocation.getLatitude();
          }
          if (!this.frameLayout_icon.isShown())
          {
            this.weiboAPI.addWeibo(this.context, str, "json", d1, d2, 0, 0, this, null, 4);
            return;
          }
        }
        while (this.frameLayout_icon.getVisibility() != 0);
        this.weiboAPI.addPic(this.context, str, "json", d1, d2, this.mBitmap, 0, 0, this, null, 4);
        return;
        localInputMethodManager.toggleSoftInput(0, 2);
        return;
        localInputMethodManager.hideSoftInputFromWindow(this.editText_text.getWindowToken(), 0);
        Intent localIntent4 = new Intent();
        localIntent4.setClass(this, FriendActivity.class);
        startActivityForResult(localIntent4, 5006);
        return;
        localInputMethodManager.hideSoftInputFromWindow(this.editText_text.getWindowToken(), 0);
        Intent localIntent3 = new Intent();
        localIntent3.setClass(this, ConversationActivity.class);
        startActivityForResult(localIntent3, 5007);
        return;
        if ((this.popupWindow == null) || (!this.popupWindow.isShowing()))
          break;
        this.popupWindow.dismiss();
      }
      while (!localInputMethodManager.isActive());
      localInputMethodManager.toggleSoftInput(0, 2);
      return;
      this.popupWindow = new PopupWindow(showView(), -1, -1);
      this.popupWindow.setTouchable(true);
      new Timer().schedule(new TimerTask()
      {
        public void run()
        {
          Message localMessage = PublishActivity.this.handler.obtainMessage();
          localMessage.what = 0;
          PublishActivity.this.handler.sendMessage(localMessage);
        }
      }
      , 500L);
      return;
      new Thread(new Runnable()
      {
        public void run()
        {
          Looper.prepare();
          Message localMessage = PublishActivity.this.handler.obtainMessage();
          localMessage.what = 15;
          if (PublishActivity.this.mLocation == null)
          {
            PublishActivity.this.mLocation = Util.getLocation(PublishActivity.this.context);
            if (PublishActivity.this.mLocation != null)
              localMessage.what = 10;
          }
          PublishActivity.this.handler.sendMessage(localMessage);
          Looper.loop();
        }
      }).start();
      return;
      this.frameLayout_icon.setVisibility(4);
      this.frameLayout_big.setVisibility(8);
      return;
      this.edstring = this.editText_text.getText().toString();
      Intent localIntent2 = new Intent("android.media.action.IMAGE_CAPTURE");
      startActivityForResult(localIntent2, 2000);
      return;
      this.edstring = this.editText_text.getText().toString();
      Intent localIntent1 = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
      startActivityForResult(localIntent1, 1000);
      return;
    }
    while ((this.popupWindow == null) || (!this.popupWindow.isShowing()));
    this.popupWindow.dismiss();
    this.editText_text.requestFocus();
    new Timer().schedule(new TimerTask()
    {
      public void run()
      {
        if (localInputMethodManager.isActive())
          localInputMethodManager.toggleSoftInput(0, 2);
      }
    }
    , 100L);
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    this.accessToken = Util.getSharePersistent(getApplicationContext(), "ACCESS_TOKEN");
    if ((this.accessToken == null) || ("".equals(this.accessToken)))
    {
      Toast.makeText(this, "请先授权", 0).show();
      finish();
      return;
    }
    this.context = getApplicationContext();
    this.weiboAPI = new WeiboAPI(new AccountModel(this.accessToken));
    this.lyout[0] = BackGroudSeletor.getdrawble("test2x", this).getMinimumWidth();
    this.lyout[1] = BackGroudSeletor.getdrawble("test2x", this).getMinimumHeight();
    LinearLayout localLinearLayout = (LinearLayout)initview();
    this.dialog = new ProgressDialog(this);
    this.dialog.setMessage("正在发送请稍后......");
    setContentView(localLinearLayout);
    setonclick();
    new Timer().schedule(new TimerTask()
    {
      public void run()
      {
        ((InputMethodManager)PublishActivity.this.getSystemService("input_method")).showSoftInput(PublishActivity.this.editText_text, 2);
      }
    }
    , 400L);
  }

  public void onResult(Object paramObject)
  {
    if ((this.dialog != null) && (this.dialog.isShowing()))
      this.dialog.dismiss();
    ModelResult localModelResult;
    if (paramObject != null)
    {
      localModelResult = (ModelResult)paramObject;
      if (localModelResult.isExpires())
        Toast.makeText(this, localModelResult.getError_message(), 0).show();
    }
    else
    {
      return;
    }
    if (localModelResult.isSuccess())
    {
      Toast.makeText(this, "发送成功", 4000).show();
      Log.d("发送成功", paramObject.toString());
      finish();
      return;
    }
    Toast.makeText(this, ((ModelResult)paramObject).getError_message(), 4000).show();
  }

  protected void onResume()
  {
    super.onResume();
    final InputMethodManager localInputMethodManager = (InputMethodManager)getSystemService("input_method");
    if ((this.popupWindow != null) && (this.popupWindow.isShowing()))
    {
      Log.d("mkl", localInputMethodManager.isActive());
      localInputMethodManager.hideSoftInputFromWindow(this.editText_text.getWindowToken(), 0);
    }
    while (this.location != null)
    {
      this.button_location.setBackgroundDrawable(BackGroudSeletor.getdrawble("dingwei_icon_hover2x", this));
      return;
      new Timer().schedule(new TimerTask()
      {
        public void run()
        {
          localInputMethodManager.showSoftInput(PublishActivity.this.editText_text, 2);
        }
      }
      , 400L);
    }
    this.button_location.setBackgroundDrawable(BackGroudSeletor.getdrawble("dingwei_icon2x", this));
  }

  public Bitmap zoomImage(Bitmap paramBitmap, double paramDouble1, double paramDouble2)
  {
    float f1 = paramBitmap.getWidth();
    float f2 = paramBitmap.getHeight();
    Matrix localMatrix = new Matrix();
    localMatrix.postScale((float)paramDouble1 / f1, (float)paramDouble2 / f2);
    return Bitmap.createBitmap(paramBitmap, 0, 0, (int)f1, (int)f2, localMatrix, true);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.tencent.weibo.sdk.android.component.PublishActivity
 * JD-Core Version:    0.6.2
 */