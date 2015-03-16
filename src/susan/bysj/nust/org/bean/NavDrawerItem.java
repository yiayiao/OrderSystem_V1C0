package susan.bysj.nust.org.bean;

public class NavDrawerItem
{
	private String title;
	private int icon;

	// �û��˵������������������Ϊ����0ʱ��������ʾ����
	private int count;

	public NavDrawerItem(String title, int icon)
	{
		this.title = title;
		this.icon = icon;
	}

	public NavDrawerItem(String title, int icon, int count)
	{
		this.title = title;
		this.icon = icon;
		this.count = count;
	}

	public int getIcon()
	{
		return this.icon;
	}

	public CharSequence getTitle()
	{
		return this.title;
	}

	public int getCount()
	{
		return this.count;
	}

	public boolean isCounterVisibility()
	{
		return (this.count > 0);
	}
}
