package com.google.android.apps.analytics;

class CustomVariableBuffer
{
  private CustomVariable[] customVariables = new CustomVariable[5];

  private void throwOnInvalidIndex(int paramInt)
  {
    if ((paramInt < 1) || (paramInt > 5))
      throw new IllegalArgumentException("Index must be between 1 and 5 inclusive.");
  }

  public void clearCustomVariableAt(int paramInt)
  {
    throwOnInvalidIndex(paramInt);
    this.customVariables[(paramInt - 1)] = null;
  }

  public CustomVariable[] getCustomVariableArray()
  {
    return (CustomVariable[])this.customVariables.clone();
  }

  public CustomVariable getCustomVariableAt(int paramInt)
  {
    throwOnInvalidIndex(paramInt);
    return this.customVariables[(paramInt - 1)];
  }

  public boolean hasCustomVariables()
  {
    for (int i = 0; ; i++)
    {
      int j = this.customVariables.length;
      boolean bool = false;
      if (i < j)
      {
        if (this.customVariables[i] != null)
          bool = true;
      }
      else
        return bool;
    }
  }

  public boolean isIndexAvailable(int paramInt)
  {
    throwOnInvalidIndex(paramInt);
    return this.customVariables[(paramInt - 1)] == null;
  }

  public void setCustomVariable(CustomVariable paramCustomVariable)
  {
    throwOnInvalidIndex(paramCustomVariable.getIndex());
    this.customVariables[(-1 + paramCustomVariable.getIndex())] = paramCustomVariable;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.analytics.CustomVariableBuffer
 * JD-Core Version:    0.6.2
 */