package susan.bysj.nust.org.utils;

import java.lang.reflect.Field;
import java.util.LinkedList;
import java.util.List;

import susan.bysj.nust.org.bean.Dish;
import susan.bysj.nust.org.bean.DishSize;
import susan.bysj.nust.org.bean.DishTaste;
import susan.bysj.nust.org.bean.DishType;
import susan.bysj.nust.org.bean.ServerItem;
import android.util.Log;

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
		else if (theClass.getSimpleName().equals("DishSize"))
		{
			itemVersions.add(new ServerItem(1, "version02"));
			itemVersions.add(new ServerItem(2, "version03"));
			itemVersions.add(new ServerItem(3, "version02"));
			itemVersions.add(new ServerItem(4, "version02"));
			itemVersions.add(new ServerItem(5, "version03"));
			itemVersions.add(new ServerItem(6, "version02"));
			itemVersions.add(new ServerItem(7, "version02"));
			itemVersions.add(new ServerItem(8, "version02"));
			itemVersions.add(new ServerItem(9, "version03"));
		}
		else if (theClass.getSimpleName().equals("DishTaste"))
		{
			itemVersions.add(new ServerItem(1, "version02"));
			itemVersions.add(new ServerItem(2, "version03"));
			itemVersions.add(new ServerItem(3, "version02"));
			itemVersions.add(new ServerItem(4, "version02"));
			itemVersions.add(new ServerItem(5, "version03"));
			itemVersions.add(new ServerItem(6, "version02"));
			itemVersions.add(new ServerItem(7, "version02"));
			itemVersions.add(new ServerItem(8, "version02"));
			itemVersions.add(new ServerItem(9, "version03"));
		}
		else if (theClass.getSimpleName().equals("DishType"))
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
				dish.setDishTypeId(3);
			}
			else if (serverId == 2)
			{
				dish.setDishId(2);
				dish.setDishName("酸菜鱼");
				dish.setDetail("这酸爽，难以想象");
				dish.setRecommend(1);
				dish.setOldPrice(20f);
				dish.setNowPrice(16f);
				dish.setDishTypeId(3);
			}
			else if (serverId == 3)
			{
				dish.setDishId(3);
				dish.setDishName("鱼香肉丝");
				dish.setDetail("家常小炒，美味非凡");
				dish.setNowPrice(8f);
				dish.setDishTypeId(3);
			}
			return (T) dish;
		}
		else if (theClass.getSimpleName().equals("DishSize"))
		{
			DishSize dishSize = new DishSize();
			dishSize.setDishSizeId(serverId);
			switch (serverId)
			{
				case 1:
					setDish(dishSize, 1, 5f, 5f, "大份");
					break;
				case 2:
					setDish(dishSize, 1, 6f, 5f, "中份");
					break;
				case 3:
					setDish(dishSize, 1, 8f, 5f, "小份");
					break;
				case 4:
					setDish(dishSize, 2, 5f, 5f, "大份");
					break;
				case 5:
					setDish(dishSize, 2, 5f, 5f, "中份");
					break;
				case 6:
					setDish(dishSize, 2, 5f, 5f, "小份");
					break;
				case 7:
					setDish(dishSize, 3, 5f, 10f, "大份");
					break;
				case 8:
					setDish(dishSize, 3, 5f, 3f, "中份");
					break;
				case 9:
					setDish(dishSize, 3, 5f, 9f, "小份");
					break;
				default:
					break;
			}
			return (T) dishSize;
		}
		else if (theClass.getSimpleName().equals("DishTaste"))
		{
			DishTaste dishTaste = new DishTaste();
			dishTaste.setDishTasteId(serverId);
			switch (serverId)
			{
				case 1:
					dishTaste.setDishId(1);
					dishTaste.setTaste("微辣");
					break;
				case 2:
					dishTaste.setDishId(1);
					dishTaste.setTaste("中辣");
					break;
				case 3:
					dishTaste.setDishId(1);
					dishTaste.setTaste("麻辣");
					break;
				case 4:
					dishTaste.setDishId(2);
					dishTaste.setTaste("微辣");
					break;
				case 5:
					dishTaste.setDishId(2);
					dishTaste.setTaste("中辣");
					break;
				case 6:
					dishTaste.setDishId(2);
					dishTaste.setTaste("麻辣");
					break;
				case 7:
					dishTaste.setDishId(3);
					dishTaste.setTaste("微辣");
					break;
				case 8:
					dishTaste.setDishId(3);
					dishTaste.setTaste("中辣");
					break;
				case 9:
					dishTaste.setDishId(3);
					dishTaste.setTaste("麻辣");
					break;
				default:
					break;
			}

			return (T) dishTaste;
		}
		else if (theClass.getSimpleName().equals("DishType"))
		{
			DishType dishType = new DishType();
			dishType.setDishTypeId(serverId);
			switch (serverId)
			{
				case 1:
					dishType.setTypeName("糕点");
				//	dishType.setDishTypeId(1);
					break;
				case 2:
					dishType.setTypeName("汤羹");
				//	dishType.setDishTypeId(2);
					break;
				case 3:
					dishType.setTypeName("正菜");
				//	dishType.setDishTypeId(3);
					break;
			}
			
			return (T)dishType; 
		}
		
		
		return null;
	}

	private void setDish(DishSize dish, int dishId, float nowPrice, float oldPrice, String sizeName)
	{
		dish.setDishId(dishId);
		dish.setNowPrice(nowPrice);
		dish.setOldPrice(oldPrice);
		dish.setSizeName(sizeName);
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
