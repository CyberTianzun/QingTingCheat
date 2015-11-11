package fm.qingting.qtradio.model;

import java.util.ArrayList;

public class OnDemandProgramInfo
{
  public AlbumElement albumElement = new AlbumElement();
  public ArrayList<OnDemandProgramElement> lstOnDemandProgramElements = new ArrayList();
  public String title = null;

  public void addOnDemandProgramElement(OnDemandProgramElement paramOnDemandProgramElement)
  {
    this.lstOnDemandProgramElements.add(paramOnDemandProgramElement);
  }

  public ArrayList<OnDemandProgramElement> getOnDemandProgramList()
  {
    return this.lstOnDemandProgramElements;
  }

  public String getTitle()
  {
    return this.title;
  }

  public void setAlbumElement(AlbumElement paramAlbumElement)
  {
    this.albumElement.setAlbumElement(paramAlbumElement);
  }

  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
}

/* Location:           /Users/zhangxun-xy/Downloads/qingting2/classes_dex2jar.jar
 * Qualified Name:     fm.qingting.qtradio.model.OnDemandProgramInfo
 * JD-Core Version:    0.6.2
 */