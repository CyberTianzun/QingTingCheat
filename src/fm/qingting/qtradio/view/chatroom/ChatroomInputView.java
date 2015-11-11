package fm.qingting.qtradio.view.chatroom;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import fm.qingting.framework.manager.EventDispacthManager;
import fm.qingting.framework.view.ViewGroupViewImpl;
import fm.qingting.framework.view.ViewLayout;
import fm.qingting.qtradio.R.drawable;
import fm.qingting.qtradio.manager.SkinManager;
import fm.qingting.qtradio.social.CloudCenter;
import fm.qingting.qtradio.view.chatroom.expression.ExpressionItem;
import fm.qingting.utils.ExpressionUtil;
import fm.qingting.utils.QTMSGManage;
import fm.qingting.utils.ScreenType;
import java.lang.reflect.Field;

public class ChatroomInputView extends ViewGroupViewImpl
{
  private final ViewLayout editLayout = this.standardLayout.createChildLT(400, 79, 180, 0, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout expressionLayout = this.standardLayout.createChildLT(49, 49, 37, 30, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout inviteLayout = this.standardLayout.createChildLT(64, 49, 625, 30, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout lineLayout = this.standardLayout.createChildLT(572, 8, 15, 81, ViewLayout.SCALE_FLAG_SLTCW);
  private EditText mEditText;
  private Button mExpressionButton;
  private boolean mHasText = false;
  private Button mInviteButton;
  private ImageView mLineView;
  private Button mPlusButton;
  private TextView mSendView;
  private final ViewLayout plusLayout = this.standardLayout.createChildLT(48, 48, 117, 30, ViewLayout.SCALE_FLAG_SLTCW);
  private final ViewLayout standardLayout = ViewLayout.createViewLayoutWithBoundsLT(720, 106, 720, 106, 0, 0, ViewLayout.FILL);

  public ChatroomInputView(Context paramContext)
  {
    super(paramContext);
    setBackgroundColor(-1);
    View.OnClickListener local1 = new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        if (paramAnonymousView == ChatroomInputView.this.mSendView)
          if (ChatroomInputView.this.mHasText)
            ChatroomInputView.this.sendDiscuss();
        do
        {
          return;
          if (paramAnonymousView == ChatroomInputView.this.mExpressionButton)
          {
            ChatroomInputView.this.dispatchActionEvent("expression", null);
            QTMSGManage.getInstance().sendStatistcsMessage("chatroom_express");
            return;
          }
          if (paramAnonymousView == ChatroomInputView.this.mPlusButton)
          {
            ChatroomInputView.this.dispatchActionEvent("expand", null);
            QTMSGManage.getInstance().sendStatistcsMessage("chatroom_func");
            return;
          }
        }
        while (paramAnonymousView != ChatroomInputView.this.mInviteButton);
        ChatroomInputView.this.dispatchActionEvent("inviteFriends", null);
        QTMSGManage.getInstance().sendStatistcsMessage("chatroom_invitefriends");
      }
    };
    this.mExpressionButton = new Button(paramContext);
    this.mExpressionButton.setBackgroundResource(2130837576);
    addView(this.mExpressionButton);
    this.mExpressionButton.setOnClickListener(local1);
    this.mPlusButton = new Button(paramContext);
    this.mPlusButton.setBackgroundResource(2130837588);
    addView(this.mPlusButton);
    this.mPlusButton.setOnClickListener(local1);
    this.mInviteButton = new Button(paramContext);
    this.mInviteButton.setBackgroundResource(2130837586);
    addView(this.mInviteButton);
    this.mInviteButton.setOnClickListener(local1);
    this.mSendView = new TextView(paramContext);
    this.mSendView.setText("发送");
    this.mSendView.setTextColor(SkinManager.getTextColorNormal());
    this.mSendView.setGravity(17);
    addView(this.mSendView);
    this.mSendView.setVisibility(4);
    this.mSendView.setOnClickListener(local1);
    this.mLineView = new ImageView(paramContext);
    this.mLineView.setBackgroundResource(2130837582);
    this.mLineView.setScaleType(ImageView.ScaleType.FIT_XY);
    addView(this.mLineView);
    this.mEditText = new EditText(paramContext);
    this.mEditText.setBackgroundResource(0);
    this.mEditText.setGravity(19);
    this.mEditText.setTextColor(SkinManager.getTextColorNormal());
    this.mEditText.setMaxLines(3);
    this.mEditText.clearFocus();
    this.mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener()
    {
      public void onFocusChange(View paramAnonymousView, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
        {
          if (!CloudCenter.getInstance().isLogin())
          {
            EventDispacthManager.getInstance().dispatchAction("showLogin", null);
            ChatroomInputView.this.mEditText.clearFocus();
            return;
          }
          ChatroomInputView.this.mLineView.setImageResource(2130837583);
          return;
        }
        ChatroomInputView.this.mLineView.setImageResource(2130837582);
      }
    });
    this.mEditText.addTextChangedListener(new TextWatcher()
    {
      public void afterTextChanged(Editable paramAnonymousEditable)
      {
      }

      public void beforeTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
      }

      public void onTextChanged(CharSequence paramAnonymousCharSequence, int paramAnonymousInt1, int paramAnonymousInt2, int paramAnonymousInt3)
      {
        if (paramAnonymousCharSequence.length() > 0)
        {
          if (!ChatroomInputView.this.mHasText)
          {
            ChatroomInputView.this.mSendView.setVisibility(0);
            ChatroomInputView.this.mInviteButton.setVisibility(4);
          }
          ChatroomInputView.access$102(ChatroomInputView.this, true);
          return;
        }
        if (ChatroomInputView.this.mHasText)
        {
          ChatroomInputView.this.mSendView.setVisibility(4);
          ChatroomInputView.this.mInviteButton.setVisibility(0);
        }
        ChatroomInputView.access$102(ChatroomInputView.this, false);
      }
    });
    addView(this.mEditText);
  }

  private boolean closeKeyBoard()
  {
    return ((InputMethodManager)getContext().getSystemService("input_method")).hideSoftInputFromWindow(getApplicationWindowToken(), 0);
  }

  private int getDesiredHeight()
  {
    if (this.mEditText.getMeasuredHeight() > this.editLayout.height)
      return this.standardLayout.height + this.mEditText.getMeasuredHeight() - this.editLayout.height;
    return this.standardLayout.height;
  }

  private void openKeyBoard()
  {
    ((InputMethodManager)getContext().getSystemService("input_method")).showSoftInput(this.mEditText, 0);
  }

  private void sendDiscuss()
  {
    dispatchActionEvent("sendDiscuss", this.mEditText.getText().toString());
    this.mEditText.setText("");
    closeKeyBoard();
  }

  protected void onLayout(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4)
  {
    int i = this.mEditText.getMeasuredHeight();
    int j = this.editLayout.height;
    int k = 0;
    if (i > j)
      k = this.mEditText.getMeasuredHeight() - this.editLayout.height;
    this.mExpressionButton.layout(this.expressionLayout.leftMargin, k + this.expressionLayout.topMargin, this.expressionLayout.getRight(), k + this.expressionLayout.getBottom());
    this.mPlusButton.layout(this.plusLayout.leftMargin, k + this.plusLayout.topMargin, this.plusLayout.getRight(), k + this.plusLayout.getBottom());
    this.mInviteButton.layout(this.inviteLayout.leftMargin, k + this.inviteLayout.topMargin, this.inviteLayout.getRight(), k + this.inviteLayout.getBottom());
    this.mLineView.layout(this.lineLayout.leftMargin, k + this.lineLayout.topMargin, this.lineLayout.getRight(), k + this.lineLayout.getBottom());
    this.mSendView.layout(this.inviteLayout.leftMargin, k + this.inviteLayout.topMargin, this.inviteLayout.getRight(), k + this.inviteLayout.getBottom());
    this.mEditText.layout(this.editLayout.leftMargin, this.editLayout.topMargin, this.editLayout.getRight(), this.editLayout.topMargin + this.mEditText.getMeasuredHeight());
  }

  protected void onMeasure(int paramInt1, int paramInt2)
  {
    this.standardLayout.scaleToBounds(View.MeasureSpec.getSize(paramInt1), View.MeasureSpec.getSize(paramInt2));
    this.expressionLayout.scaleToBounds(this.standardLayout);
    this.inviteLayout.scaleToBounds(this.standardLayout);
    this.plusLayout.scaleToBounds(this.standardLayout);
    this.lineLayout.scaleToBounds(this.standardLayout);
    this.editLayout.scaleToBounds(this.standardLayout);
    this.expressionLayout.measureView(this.mExpressionButton);
    this.plusLayout.measureView(this.mPlusButton);
    this.lineLayout.measureView(this.mLineView);
    this.inviteLayout.measureView(this.mInviteButton);
    this.inviteLayout.measureView(this.mSendView);
    this.mSendView.setTextSize(0, SkinManager.getInstance().getMiddleTextSize());
    this.mEditText.setTextSize(0, SkinManager.getInstance().getMiddleTextSize());
    this.mEditText.setPadding(0, (int)(0.4F * this.editLayout.height), 0, 0);
    this.mEditText.measure(this.editLayout.getWidthMeasureSpec(), View.MeasureSpec.makeMeasureSpec(this.editLayout.height, 0));
    setMeasuredDimension(this.standardLayout.width, getDesiredHeight());
  }

  public void update(String paramString, Object paramObject)
  {
    if (paramString.equalsIgnoreCase("replyToUser"))
    {
      String str3 = (String)paramObject;
      this.mEditText.setText("@" + str3 + " ");
      this.mEditText.setSelection(this.mEditText.getText().length());
      this.mEditText.requestFocus();
      openKeyBoard();
    }
    int i1;
    do
    {
      return;
      if (paramString.equalsIgnoreCase("closeKeyboard"))
      {
        this.mEditText.clearFocus();
        closeKeyBoard();
        return;
      }
      if (paramString.equalsIgnoreCase("openKeyboard"))
      {
        this.mEditText.requestFocus();
        openKeyBoard();
        return;
      }
      if (paramString.equalsIgnoreCase("enterReportSn"))
      {
        this.mEditText.setHint("输入歌名...");
        this.mEditText.requestFocus();
        openKeyBoard();
        return;
      }
      if (paramString.equalsIgnoreCase("exitReport"))
      {
        this.mEditText.setHint("");
        this.mEditText.setText("");
        return;
      }
      if (!paramString.equalsIgnoreCase("delete"))
        break;
      i1 = this.mEditText.getSelectionStart();
    }
    while (i1 == 0);
    while (true)
    {
      int i2;
      while (true)
      {
        String str1;
        String str2;
        int i;
        Editable localEditable1;
        while (true)
        {
          Editable localEditable2;
          try
          {
            localEditable2 = this.mEditText.getEditableText();
            if (localEditable2.charAt(i1 - 1) == ']')
            {
              i2 = i1 - 2;
              if ((i2 > -1) && (localEditable2.charAt(i2) != '['))
                break label679;
              ExpressionSpan[] arrayOfExpressionSpan = (ExpressionSpan[])localEditable2.getSpans(i2, i1 - 1, ExpressionSpan.class);
              if (arrayOfExpressionSpan != null)
              {
                int i4 = 0;
                if (i4 < arrayOfExpressionSpan.length)
                {
                  localEditable2.removeSpan(arrayOfExpressionSpan[i4]);
                  i4++;
                  continue;
                }
                localEditable2.delete(i2, i1);
                return;
              }
            }
          }
          catch (Exception localException)
          {
            localException.printStackTrace();
            return;
          }
          int i3 = i1 - 1;
          localEditable2.delete(i3, i1);
          return;
          if (!paramString.equalsIgnoreCase("addExpression"))
            break;
          ExpressionItem localExpressionItem = (ExpressionItem)paramObject;
          str1 = localExpressionItem.getExpressionName();
          str2 = localExpressionItem.getExpressionIcon();
          i = this.mEditText.getSelectionStart();
          localEditable1 = this.mEditText.getEditableText();
          if ((i < 0) || (i >= localEditable1.length()))
          {
            localEditable1.append(str1);
            try
            {
              int j = Integer.parseInt(R.drawable.class.getDeclaredField(str2).get(null).toString());
              if (j == 0)
                break;
              int k = ScreenType.getEmojiSize();
              localEditable1.setSpan(new ExpressionSpan(getResources(), j, k, k, ExpressionUtil.getInstance().getOwnerId()), i, localEditable1.length(), 17);
              return;
            }
            catch (NumberFormatException localNumberFormatException1)
            {
              localNumberFormatException1.printStackTrace();
              return;
            }
            catch (SecurityException localSecurityException1)
            {
              localSecurityException1.printStackTrace();
              return;
            }
            catch (IllegalArgumentException localIllegalArgumentException1)
            {
              localIllegalArgumentException1.printStackTrace();
              return;
            }
            catch (NoSuchFieldException localNoSuchFieldException1)
            {
              localNoSuchFieldException1.printStackTrace();
              return;
            }
            catch (IllegalAccessException localIllegalAccessException1)
            {
              localIllegalAccessException1.printStackTrace();
              return;
            }
          }
        }
        localEditable1.insert(i, str1);
        try
        {
          int m = Integer.parseInt(R.drawable.class.getDeclaredField(str2).get(null).toString());
          if (m == 0)
            break;
          int n = (int)(0.1F * this.editLayout.width);
          localEditable1.setSpan(new ExpressionSpan(getResources(), m, n, n, ExpressionUtil.getInstance().getOwnerId()), i, i + str1.length(), 17);
          return;
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          localNumberFormatException2.printStackTrace();
          return;
        }
        catch (SecurityException localSecurityException2)
        {
          localSecurityException2.printStackTrace();
          return;
        }
        catch (IllegalArgumentException localIllegalArgumentException2)
        {
          localIllegalArgumentException2.printStackTrace();
          return;
        }
        catch (NoSuchFieldException localNoSuchFieldException2)
        {
          localNoSuchFieldException2.printStackTrace();
          return;
        }
        catch (IllegalAccessException localIllegalAccessException2)
        {
          localIllegalAccessException2.printStackTrace();
          return;
        }
      }
      label679: i2--;
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.view.chatroom.ChatroomInputView
 * JD-Core Version:    0.6.2
 */