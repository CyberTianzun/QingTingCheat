package fm.qingting.qtradio.view.virtualchannels;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputFilter.LengthFilter;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import fm.qingting.framework.event.IEventHandler;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.model.InfoManager;
import fm.qingting.qtradio.room.SnsInfo;
import fm.qingting.qtradio.room.UserInfo;
import fm.qingting.qtradio.social.UserProfile;

public class UploadVoiceInputFrameView extends ViewGroupViewImpl
  implements IEventHandler
{
  private static final int mNameMaxLength = 30;
  private final ViewLayout btnLayout = this.standardLayout.createChildLT(100, 55, 375, 102, ViewLayout.SCALE_FLAG_SLTCW);
  private TextView countText;
  private final ViewLayout editLayout = this.standardLayout.createChildLT(470, 90, 5, 5, ViewLayout.SCALE_FLAG_SLTCW);
  private EditText editText;
  private Button sendButton;
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(480, 160, 480, 160, 0, 0, ViewLayout.FILL);
  private final ViewLayout textLayout = this.standardLayout.createChildLT(480, 60, 0, 100, ViewLayout.SCALE_FLAG_SLTCW);

  public UploadVoiceInputFrameView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(Color.parseColor("#f4f4f4"));
    this.sendButton = new Button(paramContext);
    this.sendButton.setBackgroundResource(2130837900);
    this.sendButton.setText("发布");
    this.sendButton.setTextColor(Color.parseColor("#f1f4f1"));
    this.sendButton.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        UploadVoiceInputFrameView.this.update("doSend", null);
      }
    });
    addView(this.sendButton);
    this.editText = new EditText(paramContext);
    Object localObject = "我的录音";
    try
    {
      String str1 = InfoManager.getInstance().getUserProfile().getUserInfo().snsInfo.sns_name;
      if ((str1 != null) && (!str1.equalsIgnoreCase("")))
      {
        String str2 = str1 + "的录音";
        localObject = str2;
      }
      this.editText.setHint("请输入录音名称... 例如：" + (String)localObject);
      this.editText.setText((CharSequence)localObject);
      this.editText.setGravity(51);
      this.editText.setHintTextColor(SkinManager.getTextColorSubInfo());
      this.editText.setBackgroundResource(2130837899);
      this.editText.clearFocus();
      EditText localEditText = this.editText;
      InputFilter[] arrayOfInputFilter = new InputFilter[1];
      arrayOfInputFilter[0] = new InputFilter.LengthFilter(30);
      localEditText.setFilters(arrayOfInputFilter);
      this.editText.addTextChangedListener(new TextWatcher()
      {
        public void afterTextChanged(Editable paramAnonymousEditable)
        {
        }

        public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
        {
        }

        public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
        {
          TextView localTextView = UploadVoiceInputFrameView.this.countText;
          Object[] arrayOfObject = new Object[2];
          arrayOfObject[0] = Integer.valueOf(paramAnonymousCharSequence.length());
          arrayOfObject[1] = Integer.valueOf(30);
          localTextView.setText(String.format("%d / %d", arrayOfObject));
        }
      });
      addView(this.editText);
      this.countText = new TextView(paramContext);
      this.countText.setTextColor(-16777216);
      this.countText.setGravity(19);
      this.countText.setPadding(10, 0, 0, 0);
      TextView localTextView = this.countText;
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = Integer.valueOf(((String)localObject).length());
      arrayOfObject[1] = Integer.valueOf(30);
      localTextView.setText(String.format("%d / %d", arrayOfObject));
      addView(this.countText);
      return;
    }
    catch (Exception localException)
    {
      while (true)
        localException.printStackTrace();
    }
  }

  public boolean dispatchKeyEventPreIme(KeyEvent paramKeyEvent)
  {
    int i = paramKeyEvent.getKeyCode();
    if (((i == 82) || (i == 4)) && (paramKeyEvent.getAction() == 1))
    {
      dispatchActionEvent("cancelSend", null);
      return true;
    }
    return false;
  }

  public void onEvent(Object paramObject1, String paramString, Object paramObject2)
  {
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    this.editLayout.layoutView(this.editText);
    this.textLayout.layoutView(this.countText);
    this.btnLayout.layoutView(this.sendButton);
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    int i = View.MeasureSpec.getSize(paramInt1);
    int j = View.MeasureSpec.getSize(paramInt2);
    this.standardLayout.scaleToBounds(i, j);
    this.editLayout.scaleToBounds(this.standardLayout);
    this.textLayout.scaleToBounds(this.standardLayout);
    this.btnLayout.scaleToBounds(this.standardLayout);
    this.editLayout.measureView(this.editText);
    this.textLayout.measureView(this.countText);
    this.btnLayout.measureView(this.sendButton);
    this.editText.setTextSize(0, 0.3F * this.editLayout.height);
    this.countText.setTextSize(0, 0.4F * this.textLayout.height);
    this.sendButton.setTextSize(0, 0.5F * this.btnLayout.height);
    setMeasuredDimension(this.standardLayout.width, this.standardLayout.height);
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("show"))
    {
      this.editText.requestFocus();
      ((InputMethodManager)getContext().getSystemService("input_method")).showSoftInput(this.editText, 0);
    }
    if (paramString.equalsIgnoreCase("hide"))
      ((InputMethodManager)getContext().getSystemService("input_method")).hideSoftInputFromWindow(getApplicationWindowToken(), 0);
    if (paramString.equalsIgnoreCase("doSend"))
    {
      String str = this.editText.getText().toString().trim();
      if (str.length() > 0)
        dispatchActionEvent("doSend", str);
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.virtualchannels.UploadVoiceInputFrameView
 * JD-Core Version:    0.6.2
 */