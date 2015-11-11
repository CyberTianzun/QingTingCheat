package fm.qingting.qtradio.model;

import java.util.ArrayList;

public class ProgramCategory
{
  public String cid;
  public boolean hasChild;
  public String name;
  public ArrayList<ProgramCategory> subCategories;
  public String type;

  public ProgramCategory()
  {
  }

  public ProgramCategory(String paramString1, String paramString2, String paramString3)
  {
    this.cid = paramString1;
    this.name = paramString2;
    this.type = paramString3;
  }

  public ProgramCategory(String paramString1, String paramString2, String paramString3, boolean paramBoolean)
  {
    this.cid = paramString1;
    this.name = paramString2;
    this.type = paramString3;
    this.hasChild = paramBoolean;
  }

  public ProgramCategory clone()
  {
    ProgramCategory localProgramCategory = new ProgramCategory();
    localProgramCategory.cid = this.cid;
    localProgramCategory.name = this.name;
    localProgramCategory.type = this.type;
    localProgramCategory.hasChild = this.hasChild;
    localProgramCategory.subCategories = this.subCategories;
    return localProgramCategory;
  }

  public boolean equals(Object paramObject)
  {
    if (paramObject == null);
    label27: label37: label53: label63: label194: label196: label202: label206: 
    while (true)
    {
      return false;
      if ((paramObject instanceof ProgramCategory))
      {
        ProgramCategory localProgramCategory = (ProgramCategory)paramObject;
        int i;
        int j;
        int k;
        int m;
        int n;
        if (this.cid == null)
        {
          i = 1;
          if (localProgramCategory.cid != null)
            break label178;
          j = 1;
          if (i != j)
            break label182;
          if (this.name != null)
            break label184;
          k = 1;
          if (localProgramCategory.name != null)
            break label190;
          m = 1;
          if (k != m)
            break label194;
          if (this.type != null)
            break label196;
          n = 1;
          label80: if (localProgramCategory.type != null)
            break label202;
        }
        for (int i1 = 1; ; i1 = 0)
        {
          if ((n != i1) || (this.hasChild != localProgramCategory.hasChild) || ((this.cid != null) && (!this.cid.equalsIgnoreCase(localProgramCategory.cid))) || ((this.name != null) && (!this.name.equalsIgnoreCase(localProgramCategory.name))) || ((this.type != null) && (!this.type.equalsIgnoreCase(localProgramCategory.type))))
            break label206;
          return true;
          i = 0;
          break label27;
          j = 0;
          break label37;
          break;
          k = 0;
          break label53;
          m = 0;
          break label63;
          break;
          n = 0;
          break label80;
        }
      }
    }
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.ProgramCategory
 * JD-Core Version:    0.6.2
 */