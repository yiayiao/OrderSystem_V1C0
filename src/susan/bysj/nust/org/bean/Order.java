package susan.bysj.nust.org.bean;

public class Order
{
	private int orderId;

	private int tableId;

	private float totalPrice;

	private float packPrice;

	private float realPrice;

	/**
	 * 下单时间，格式化的日期字符串，'yyyyMMddhh24mmss'
	 */
	private String orderTime;

	/**
	 * 结算时间，格式化的日期字符串，'yyyyMMddhh24mmss'		
	 */
	private String settleTime;

	private int state;

	private String bz;

	public int getOrderId()
	{
		return orderId;
	}

	public void setOrderId(int orderId)
	{
		this.orderId = orderId;
	}

	public int getTableId()
	{
		return tableId;
	}

	public void setTableId(int tableId)
	{
		this.tableId = tableId;
	}

	public float getTotalPrice()
	{
		return totalPrice;
	}

	public void setTotalPrice(float totalPrice)
	{
		this.totalPrice = totalPrice;
	}

	public float getPackPrice()
	{
		return packPrice;
	}

	public void setPackPrice(float packPrice)
	{
		this.packPrice = packPrice;
	}

	public float getRealPrice()
	{
		return realPrice;
	}

	public void setRealPrice(float realPrice)
	{
		this.realPrice = realPrice;
	}

	public String getOrderTime()
	{
		return orderTime;
	}

	public void setOrderTime(String orderTime)
	{
		this.orderTime = orderTime;
	}

	public String getSettleTime()
	{
		return settleTime;
	}

	public void setSettleTime(String settleTime)
	{
		this.settleTime = settleTime;
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
