package susan.bysj.nust.org.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * dish_type：菜品类型表
 * 
 * @author Susan
 */
@Table(name = "dish_type")
public class DishType implements IGetVersionAndId
{
	@Id(column = "id")
	private int id;

	private int dishTypeId;

	private String version;

	private String typeName;

	public int getServerId()
	{
		return dishTypeId;
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

	public String getTypeName()
	{
		return typeName;
	}

	public void setTypeName(String typeName)
	{
		this.typeName = typeName;
	}

}
