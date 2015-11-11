package org.jdom.filter;

import org.jdom.CDATA;
import org.jdom.Comment;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.EntityRef;
import org.jdom.ProcessingInstruction;
import org.jdom.Text;

public class ContentFilter extends AbstractFilter
{
  public static final int CDATA = 2;
  public static final int COMMENT = 8;
  private static final String CVS_ID = "@(#) $RCSfile: ContentFilter.java,v $ $Revision: 1.15 $ $Date: 2007/11/10 05:29:00 $ $Name: jdom_1_1_1 $";
  public static final int DOCTYPE = 128;
  public static final int DOCUMENT = 64;
  public static final int ELEMENT = 1;
  public static final int ENTITYREF = 32;
  public static final int PI = 16;
  public static final int TEXT = 4;
  private int filterMask;

  public ContentFilter()
  {
    setDefaultMask();
  }

  public ContentFilter(int paramInt)
  {
    setFilterMask(paramInt);
  }

  public ContentFilter(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      setDefaultMask();
      return;
    }
    this.filterMask &= (0xFFFFFFFF ^ this.filterMask);
  }

  public boolean equals(Object paramObject)
  {
    if (this == paramObject);
    ContentFilter localContentFilter;
    do
    {
      return true;
      if (!(paramObject instanceof ContentFilter))
        return false;
      localContentFilter = (ContentFilter)paramObject;
    }
    while (this.filterMask == localContentFilter.filterMask);
    return false;
  }

  public int getFilterMask()
  {
    return this.filterMask;
  }

  public int hashCode()
  {
    return this.filterMask;
  }

  public boolean matches(Object paramObject)
  {
    if ((paramObject instanceof Element))
      if ((0x1 & this.filterMask) == 0);
    do
    {
      do
      {
        do
        {
          do
          {
            do
            {
              do
              {
                do
                {
                  return true;
                  return false;
                  if (!(paramObject instanceof CDATA))
                    break;
                }
                while ((0x2 & this.filterMask) != 0);
                return false;
                if (!(paramObject instanceof Text))
                  break;
              }
              while ((0x4 & this.filterMask) != 0);
              return false;
              if (!(paramObject instanceof Comment))
                break;
            }
            while ((0x8 & this.filterMask) != 0);
            return false;
            if (!(paramObject instanceof ProcessingInstruction))
              break;
          }
          while ((0x10 & this.filterMask) != 0);
          return false;
          if (!(paramObject instanceof EntityRef))
            break;
        }
        while ((0x20 & this.filterMask) != 0);
        return false;
        if (!(paramObject instanceof Document))
          break;
      }
      while ((0x40 & this.filterMask) != 0);
      return false;
      if (!(paramObject instanceof DocType))
        break;
    }
    while ((0x80 & this.filterMask) != 0);
    return false;
    return false;
  }

  public void setCDATAVisible(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.filterMask = (0x2 | this.filterMask);
      return;
    }
    this.filterMask = (0xFFFFFFFD & this.filterMask);
  }

  public void setCommentVisible(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.filterMask = (0x8 | this.filterMask);
      return;
    }
    this.filterMask = (0xFFFFFFF7 & this.filterMask);
  }

  public void setDefaultMask()
  {
    this.filterMask = 255;
  }

  public void setDocTypeVisible(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.filterMask = (0x80 | this.filterMask);
      return;
    }
    this.filterMask = (0xFFFFFF7F & this.filterMask);
  }

  public void setDocumentContent()
  {
    this.filterMask = 153;
  }

  public void setElementContent()
  {
    this.filterMask = 63;
  }

  public void setElementVisible(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.filterMask = (0x1 | this.filterMask);
      return;
    }
    this.filterMask = (0xFFFFFFFE & this.filterMask);
  }

  public void setEntityRefVisible(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.filterMask = (0x20 | this.filterMask);
      return;
    }
    this.filterMask = (0xFFFFFFDF & this.filterMask);
  }

  public void setFilterMask(int paramInt)
  {
    setDefaultMask();
    this.filterMask = (paramInt & this.filterMask);
  }

  public void setPIVisible(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.filterMask = (0x10 | this.filterMask);
      return;
    }
    this.filterMask = (0xFFFFFFEF & this.filterMask);
  }

  public void setTextVisible(boolean paramBoolean)
  {
    if (paramBoolean)
    {
      this.filterMask = (0x4 | this.filterMask);
      return;
    }
    this.filterMask = (0xFFFFFFFB & this.filterMask);
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     org.jdom.filter.ContentFilter
 * JD-Core Version:    0.6.2
 */