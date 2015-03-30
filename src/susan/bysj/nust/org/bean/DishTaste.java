package susan.bysj.nust.org.bean;

import net.tsz.afinal.annotation.sqlite.Id;
import net.tsz.afinal.annotation.sqlite.Table;

/**
 * dish_taste：菜品口味表，如不辣、微辣、中辣、bt辣；
 * 
 * @author Mike
 */
@Table(name = "dish_taste")
public class DishTaste implements IGetVersionAndId
{
	@Id(column = "id")
	private int id;

	private int dishTasteId;

	private String version;

	private int dishId;

	private String tasteId;

	public int getServerId()
	{
		return dishTasteId;
	}

	public int getDishTasteId()
	{
		return dishTasteId;
	}

	public void setDishTasteId(int dishTasteId)
	{
		this.dishTasteId = dishTasteId;
	}

	public int getDishId()
	{
		return dishId;
	}

	public void setDishId(int dishId)
	{
		this.dishId = dishId;
	}

	public String getTasteId()
	{
		return tasteId;
	}

	public void setTasteId(String tasteId)
	{
		this.tasteId = tasteId;
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
