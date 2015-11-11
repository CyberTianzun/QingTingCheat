package fm.qingting.qtradio.model;

public class Program
{
  public boolean available = true;
  public String description = "";
  public int duration;
  public int index = -1;
  public String name;
  public String programId;
  public String source;

  public Program clone()
  {
    Program localProgram = new Program();
    localProgram.name = this.name;
    localProgram.description = this.description;
    localProgram.duration = this.duration;
    localProgram.source = this.source;
    localProgram.programId = this.programId;
    localProgram.index = this.index;
    return localProgram;
  }

  public boolean equals(Object paramObject)
  {
    boolean bool1;
    if (this == paramObject)
      bool1 = true;
    Program localProgram;
    int i;
    label42: int j;
    label53: int k;
    label72: int m;
    label83: int n;
    label102: int i1;
    label113: 
    do
    {
      do
      {
        do
        {
          boolean bool2;
          do
          {
            do
            {
              return bool1;
              bool1 = false;
            }
            while (paramObject == null);
            bool2 = paramObject instanceof Program;
            bool1 = false;
          }
          while (!bool2);
          localProgram = (Program)paramObject;
          if (this.name != null)
            break;
          i = 1;
          if (localProgram.name != null)
            break label316;
          j = 1;
          bool1 = false;
        }
        while (i != j);
        if (this.description != null)
          break label322;
        k = 1;
        if (localProgram.description != null)
          break label328;
        m = 1;
        bool1 = false;
      }
      while (k != m);
      if (this.source != null)
        break label334;
      n = 1;
      if (localProgram.source != null)
        break label340;
      i1 = 1;
      bool1 = false;
    }
    while (n != i1);
    int i2;
    if (this.programId == null)
    {
      i2 = 1;
      label132: if (localProgram.programId != null)
        break label352;
    }
    label316: label322: label328: label334: label340: label352: for (int i3 = 1; ; i3 = 0)
    {
      bool1 = false;
      if (i2 != i3)
        break;
      if (this.name != null)
      {
        boolean bool8 = this.name.equalsIgnoreCase(localProgram.name);
        bool1 = false;
        if (!bool8)
          break;
      }
      if (this.description != null)
      {
        boolean bool7 = this.description.equalsIgnoreCase(localProgram.description);
        bool1 = false;
        if (!bool7)
          break;
      }
      if (this.source != null)
      {
        boolean bool6 = this.source.equalsIgnoreCase(localProgram.source);
        bool1 = false;
        if (!bool6)
          break;
      }
      if (this.programId != null)
      {
        boolean bool5 = this.programId.equalsIgnoreCase(localProgram.programId);
        bool1 = false;
        if (!bool5)
          break;
      }
      int i4 = this.duration;
      int i5 = localProgram.duration;
      bool1 = false;
      if (i4 != i5)
        break;
      boolean bool3 = this.available;
      boolean bool4 = localProgram.available;
      bool1 = false;
      if (bool3 != bool4)
        break;
      return true;
      i = 0;
      break label42;
      j = 0;
      break label53;
      k = 0;
      break label72;
      m = 0;
      break label83;
      n = 0;
      break label102;
      i1 = 0;
      break label113;
      i2 = 0;
      break label132;
    }
  }

  public void setProgram(Program paramProgram)
  {
    this.name = paramProgram.name;
    this.description = paramProgram.description;
    this.duration = paramProgram.duration;
    this.source = paramProgram.source;
    this.programId = paramProgram.programId;
    this.index = paramProgram.index;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.Program
 * JD-Core Version:    0.6.2
 */