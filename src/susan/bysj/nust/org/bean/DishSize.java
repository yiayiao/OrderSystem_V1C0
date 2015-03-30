package susan.bysj.nust.org.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * dish_size：菜品尺寸表，如大份、中份、小份；
 * 
 * @author Susan
 */
@Table(name = "dish_size")
public class DishSize implements IGetVersionAndId
{ 
	@Id(column = "id")
	private int id;

	private int dishSizeId;

	private String version;

	/**
	 * 对应一个Dish在服务器端的id dishId
	 */
	private int dishId;

	private String sizeName;

	private float oldPrice;

	private float nowPrice;

	public int getServerId()
	{
		return dishSizeId;
	}

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

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

}
