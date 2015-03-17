package susan.bysj.nust.org.bean;

public class DishItem
{
	private String dishId;

	private String dishName;

	private int dishIcon;

	private String dishPrice;

	private String dishDesc;

	public DishItem(String dishName, String dishPrice, String dishDesc)
	{
		this.dishName = dishName;
		this.dishPrice = dishPrice;
		this.dishDesc = dishDesc;
	}

	public String getDishName()
	{
		return dishName;
	}

	public void setDishName(String dishName)
	{
		this.dishName = dishName;
	}

	public int getDishIcon()
	{
		return dishIcon;
	}

	public void setDishIcon(int dishIcon)
	{
		this.dishIcon = dishIcon;
	}

	public String getDishPrice()
	{
		return dishPrice;
	}

	public void setDishPrice(String dishPrice)
	{
		this.dishPrice = dishPrice;
	}

	public String getDishDesc()
	{
		return dishDesc;
	}

	public void setDishDesc(String dishDesc)
	{
		this.dishDesc = dishDesc;
	}

}
