package susan.bysj.nust.org.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import android.util.Log;
import susan.bysj.nust.org.bean.Dish;
import susan.bysj.nust.org.bean.ServerItem;

public class ServerUtils
{
	private String serverUrl;
	List<ServerItem> itemVersions;

	public ServerUtils(String serverUrl)
	{
		this.serverUrl = serverUrl;
	}

	public <T> List<ServerItem> queryAll(Class<T> theClass)
	{
		itemVersions = new LinkedList<ServerItem>();
		// TODO
		if (theClass.getSimpleName().equals("Dish"))
		{
			itemVersions.add(new ServerItem(1, "version02"));
			itemVersions.add(new ServerItem(2, "version03"));
			itemVersions.add(new ServerItem(3, "version02"));
		}

		return itemVersions;
	}

	public <T> T queryItem(int serverId, Class<T> theClass)
	{
		if (theClass.getSimpleName().equals("Dish"))
		{
			Dish dish = new Dish();
			if (serverId == 1)
			{
				dish.setDishId(1);
				dish.setDishName("回锅肉");
				dish.setDetail("非常好吃的一道川菜");
				dish.setOldPrice(7f);
				dish.setNowPrice(5.5f);
			}
			else if (serverId == 2)
			{
				dish.setDishId(2);
				dish.setDishName("酸菜鱼");
				dish.setDetail("这酸爽，难以想象");
				dish.setOldPrice(20f);
				dish.setNowPrice(16f);
			}
			else if (serverId == 3)
			{
				dish.setDishId(3);
				dish.setDishName("鱼香肉丝");
				dish.setDetail("家常小炒，美味非凡");
				dish.setNowPrice(8f);
			}
			return (T) dish;
		}

		return null;
	}

	public <T> void update(T item, int serverId, Class<T> theClass)
	{
		T serverItem = queryItem(serverId, theClass);
		copyProperty(item, serverItem, theClass);
	}

	public <T> void copyProperty(T item1, T item2, Class<T> theClass)
	{
		for (Field field : theClass.getDeclaredFields())
		{
			if (field.getName().equals("id"))
			{
				continue;
			}

			try
			{
				field.setAccessible(true);
				Object obj = field.get(item2);
				field.set(item1, obj);
			}
			catch (IllegalAccessException | IllegalArgumentException e)
			{
				Log.e("OrderSystem", theClass.getName() + " class is not standard");
				e.printStackTrace();
			}
		}
		Log.d("OrderSystem", "update one item of class " + theClass.getName());
	}
}
