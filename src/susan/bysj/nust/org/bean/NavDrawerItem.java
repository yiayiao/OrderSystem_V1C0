package susan.bysj.nust.org.bean;

public class NavDrawerItem
{
	private String title;
	private int icon;

	// 用户菜单项计数，当把它设置为大于0时，它将显示出来
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
