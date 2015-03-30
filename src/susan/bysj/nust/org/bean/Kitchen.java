package susan.bysj.nust.org.bean;

import net.tsz.afinal.annotation.sqlite.Id;

/**
 * kitchen：厨房终端表；
 * 
 * @author Susan
 */
public class Kitchen implements IGetVersionAndId
{
	@Id(column = "id")
	private int id;

	private String version;

	private int kitchenId;

	private String name;

	public int getServerId()
	{
		return kitchenId;
	}

	public int getKitchenId()
	{
		return kitchenId;
	}

	public void setKitchenId(int kitchenId)
	{
		this.kitchenId = kitchenId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
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
