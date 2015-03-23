package susan.bysj.nust.org.bean;

/**
 * dish_size：菜品尺寸表，如大份、中份、小份；
 * 
 * @author Susan
 */
public class DishSize
{
	private int dishSizeId;

	private int dishId;

	private String sizeName;

	private float oldPrice;

	private float nowPrice;

	public int getDishSizeId()
	{
		return dishSizeId;
	}

	public void setDishSizeId(int dishSizeId)
	{
		this.dishSizeId = dishSizeId;
	}

	public int getDishId()
	{
		return dishId;
	}

	public void setDishId(int dishId)
	{
		this.dishId = dishId;
	}

	public String getSizeName()
	{
		return sizeName;
	}

	public void setSizeName(String sizeName)
	{
		this.sizeName = sizeName;
	}

	public float getOldPrice()
	{
		return oldPrice;
	}

	public void setOldPrice(float oldPrice)
	{
		this.oldPrice = oldPrice;
	}

	public float getNowPrice()
	{
		return nowPrice;
	}

	public void setNowPrice(float nowPrice)
	{
		this.nowPrice = nowPrice;
	}

}
