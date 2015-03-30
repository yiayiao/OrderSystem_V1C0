package susan.bysj.nust.org.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

@Table(name = "dish")
public class Dish implements IGetVersionAndId
{
	@Id(column = "id")
	private int id;

	/**
	 * 该bean在服务器端的Id
	 */
	private int dishId;

	/**
	 * 如果该version与服务器上是不一致的，则意味着这是需要跟新的
	 */
	private String version;

	private int dishTypeId;

	private String dishName;

	private float oldPrice;

	private float nowPrice;

	/**
	 * 单位，如：份，500g等
	 */
	private String unit;

	private String detail;

	/**
	 * 库存数量，-1：不限；
	 */
	private int num;

	/**
	 * 是否本店特色菜（推荐菜）；1：是；0：否
	 */
	private int recommend;

	/**
	 * 菜品图片地址，如：/pic/1233.jpg;
	 */
	private String picUrl;

	/**
	 * 菜品在本地的路径
	 */
	private String picLocalUrl;

	/**
	 * 状态：1：正常；2：缺货；3：其他；
	 */
	private int state;

	/**
	 * 备注
	 */
	private String bz;

	public Dish()
	{
	}

	public Dish(String name, String desc, float price)
	{
		this.dishName = name;
		this.detail = desc;
		this.nowPrice = price;
	}

	public int getServerId()
	{
		return dishId;
	}

	public int getDishId()
	{
		return dishId;
	}

	public void setDishId(int dishId)
	{
		this.dishId = dishId;
	}

	public int getDishTypeId()
	{
		return dishTypeId;
	}

	public void setDishTypeId(int dishTypeId)
	{
		this.dishTypeId = dishTypeId;
	}

	public String getDishName()
	{
		return dishName;
	}

	public void setDishName(String dishName)
	{
		this.dishName = dishName;
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

	public String getUnit()
	{
		return unit;
	}

	public void setUnit(String unit)
	{
		this.unit = unit;
	}

	public String getDetail()
	{
		return detail;
	}

	public void setDetail(String detail)
	{
		this.detail = detail;
	}

	public int getNum()
	{
		return num;
	}

	public void setNum(int num)
	{
		this.num = num;
	}

	public int getRecommend()
	{
		return recommend;
	}

	public void setRecommend(int recommend)
	{
		this.recommend = recommend;
	}

	public String getPicUrl()
	{
		return picUrl;
	}

	public void setPicUrl(String picUrl)
	{
		this.picUrl = picUrl;
	}

	public String getPicLocalUrl()
	{
		return picLocalUrl;
	}

	public void setPicLocalUrl(String picLocalUrl)
	{
		this.picLocalUrl = picLocalUrl;
	}

	public int getState()
	{
		return state;
	}

	public void setState(int state)
	{
		this.state = state;
	}

	public String getBz()
	{
		return bz;
	}

	public void setBz(String bz)
	{
		this.bz = bz;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getVersion()
	{
		return version;
	}

	public void setVersion(String version)
	{
		this.version = version;
	}
}
