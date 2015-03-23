package susan.bysj.nust.org.bean;

/**
 * dish_taste：菜品口味表，如不辣、微辣、中辣、bt辣；
 * 
 * @author Mike
 */
public class DishTaste
{
	private int dishTasteId;

	private int dishId;

	private String tasteId;

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

}
