package susan.bysj.nust.org.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import net.tsz.afinal.FinalDb;
import susan.bysj.nust.org.MainActivity;
import susan.bysj.nust.org.bean.Dish;
import susan.bysj.nust.org.bean.DishSize;
import susan.bysj.nust.org.bean.DishTaste;
import susan.bysj.nust.org.bean.DishType;
import susan.bysj.nust.org.bean.ServerItem;
import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;

public class Updater
{
	private FinalDb finalDb;
	private ServerUtils serverUtils;
	private MyApplication myApplication;
	private Activity activity;

	public Updater(Activity activity)
	{
		this.activity = activity;
		myApplication = (MyApplication) activity.getApplication();
		finalDb = myApplication.getFinalDb();
		serverUtils = new ServerUtils(myApplication.getServerUrl());
	}

	public void update()
	{
		new UpdateTask().execute();
	}

	private class UpdateTask extends AsyncTask<Void, Void, Void>
	{
		List<ServerItem> serverData;

		private void updateData()
		{
			try
			{
				updateItem(Dish.class);
				updateItem(DishSize.class);
				updateItem(DishTaste.class);
				updateItem(DishType.class);
			}
			catch (NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e)
			{
				Log.e("OrderSystem", "A class is not standard");
				e.printStackTrace();
			}
		}

		private <T> void updateItem(Class<T> theClass) throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException,
		        InvocationTargetException
		{
			serverData = serverUtils.queryAll(theClass);
			List<T> localItems = finalDb.findAll(theClass);
			Method getServerId = theClass.getMethod("getServerId");
			int serverId;
			Method getVersion = theClass.getMethod("getVersion");
			String version;
			boolean flag = false;

			for (T localItem : localItems)
			{
				flag = false;
				serverId = (int) getServerId.invoke(localItem);
				for (ServerItem item : serverData)
				{
					if (serverId == item.getServerId())
					{
						flag = true;
					}
				}
				if (!flag)
				{
					finalDb.delete(localItem);
				}
			}

			for (ServerItem item : serverData)
			{
				flag = false;
				for (T localItem : localItems)
				{
					serverId = (int) getServerId.invoke(localItem);
					if (serverId == item.getServerId())
					{
						flag = true;
						version = (String) getVersion.invoke(localItem);
						if (!item.getVersion().equals(version))
						{
							serverUtils.update(localItem, serverId, theClass);
							finalDb.update(localItem);
						}
					}
				}
				if (!flag)
				{
					T serverItem = serverUtils.queryItem(item.getServerId(), theClass);
					finalDb.save(serverItem);
				}
			}

			Log.d("OrderSystem", "the " + theClass.getSimpleName() + " has item: " + finalDb.findAll(theClass).size());
		}

		@Override
		protected Void doInBackground(Void... params)
		{
			updateData();
			return null;
		}

		@Override
		protected void onPostExecute(Void result)
		{
			super.onPostExecute(result);
			((MainActivity) activity).refreshData();
		}

		@Override
		protected void onProgressUpdate(Void... values)
		{
		}
	}

}
