package susan.bysj.nust.org.bean;

public class Table
{
	private int tableId;

	/**
	 * 餐桌名称，如1号桌、301等
	 */
	private String name;

	private int num;

	private int leastCost;

	/**
	 * 状态，0：空闲；1：使用中
	 */
	private int state;

	private String bz;

	public int getTableId()
	{
		return tableId;
	}

	public void setTableId(int tableId)
	{
		this.tableId = tableId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getNum()
	{
		return num;
	}

	public void setNum(int num)
	{
		this.num = num;
	}

	public int getLeastCost()
	{
		return leastCost;
	}

	public void setLeastCost(int leastCost)
	{
		this.leastCost = leastCost;
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
