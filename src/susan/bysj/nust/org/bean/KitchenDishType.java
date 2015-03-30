package susan.bysj.nust.org.bean;

import net.tsz.afinal.annotation.sqlite.Id;

public class KitchenDishType implements IGetVersionAndId
{
	@Id(column = "id")
	private int id;

	private String version;

	/**
	 * 服务器端的Id
	 */
	private int kitchenDishTypeId;

	private int kitchenId;

	private int dishTypeId;

	public int getKitchenDishTypeId()
	{
		return kitchenDishTypeId;
	}

	public void setKitchenDishTypeId(int kitchenDishTypeId)
	{
		this.kitchenDishTypeId = kitchenDishTypeId;
	}

	public int getKitchenId()
	{
		return kitchenId;
	}

	public void setKitchenId(int kitchenId)
	{
		this.kitchenId = kitchenId;
	}

	public int getDishTypeId()
	{
		return dishTypeId;
	}

	public void setDishTypeId(int dishTypeId)
	{
		this.dishTypeId = dishTypeId;
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

	@Override
	public int getServerId()
	{
		return kitchenDishTypeId;
	}

}
