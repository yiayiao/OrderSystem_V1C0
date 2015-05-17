package susan.bysj.nust.org.utils;

import java.util.LinkedList;
import java.util.List;

import susan.bysj.nust.org.bean.Order;
import susan.bysj.nust.org.bean.OrderDish;
import net.tsz.afinal.FinalDb;
import android.app.Application;

public class MyApplication extends Application
{
	private FinalDb finalDb;// 数据库全局变量

	private AsyncImageLoader asyncImageLoader;

	private String serverUrl = "http://localhost:8080/updateUrl";

	private List<OrderDish> orderList;

	private Order order;

	public void addOrder(OrderDish orderDish)
	{
		if (orderList == null)
		{
			orderList = new LinkedList<>();
		}
		orderList.add(orderDish);
	}

	public void removeOrder(OrderDish orderDish)
	{
		for (OrderDish orderDish2 : this.orderList)
		{
			if (orderDish2.getDishId() == orderDish.getDishId())
			{
				this.orderList.remove(orderDish2);
				break;
			}
		}
	}

	public FinalDb getFinalDb()
	{
		return finalDb;
	}

	public void setFinalDb(FinalDb finalDb)
	{
		this.finalDb = finalDb;
	}

	public String getServerUrl()
	{
		return serverUrl;
	}

	public void setServerUrl(String serverUrl)
	{
		this.serverUrl = serverUrl;
	}

	public AsyncImageLoader getAsyncImageLoader()
	{
		return asyncImageLoader;
	}

	public void setAsyncImageLoader(AsyncImageLoader asyncImageLoader)
	{
		this.asyncImageLoader = asyncImageLoader;
	}

	public List<OrderDish> getOrderList()
	{
		return orderList;
	}

	public void setOrderList(List<OrderDish> orderList)
	{
		this.orderList = orderList;
	}

	public Order getOrder()
	{
		return order;
	}

	public void setOrder(Order order)
	{
		this.order = order;
	}

}
