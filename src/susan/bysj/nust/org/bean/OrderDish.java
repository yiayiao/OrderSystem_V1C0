package susan.bysj.nust.org.bean;

/**
 * @author Susan
 * 
 *         orders_dish：orders与dish的中间表，因为orders与dish是N:N的关系；
 *         在每一个终端，只保存当前的order与dish的对应关系
 */
public class OrderDish
{
	private int orderDishId;

	private int dishId;

	private int orderId;

	private int dishSizeId;

	private int dishTasteId;

	private float nowPrice;

	/**
	 * 数量
	 */
	private int num;

	/**
	 * 状态，0：未上桌；1：上桌
	 */
	private int state;

	private String bz;

	public int getOrderDishId()
	{
		return orderDishId;
	}

	public void setOrderDishId(int orderDishId)
	{
		this.orderDishId = orderDishId;
	}

	public int getDishId()
	{
		return dishId;
	}

	public void setDishId(int dishId)
	{
		this.dishId = dishId;
	}

	public int getOrderId()
	{
		return orderId;
	}

	public void setOrderId(int orderId)
	{
		this.orderId = orderId;
	}

	public int getDishSizeId()
	{
		return dishSizeId;
	}

	public void setDishSizeId(int dishSizeId)
	{
		this.dishSizeId = dishSizeId;
	}

	public int getDishTasteId()
	{
		return dishTasteId;
	}

	public void setDishTasteId(int dishTasteId)
	{
		this.dishTasteId = dishTasteId;
	}

	public float getNowPrice()
	{
		return nowPrice;
	}

	public void setNowPrice(float nowPrice)
	{
		this.nowPrice = nowPrice;
	}

	public int getNum()
	{
		return num;
	}

	public void setNum(int num)
	{
		this.num = num;
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

}
