package susan.bysj.nust.org;

import java.util.LinkedList;
import java.util.List;

import net.tsz.afinal.FinalDb;
import susan.bysj.nust.org.adapter.DishItemArrayAdapter;
import susan.bysj.nust.org.bean.Dish;
import susan.bysj.nust.org.bean.OrderDish;
import susan.bysj.nust.org.utils.MyApplication;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

@SuppressLint("NewApi")
public class DishListFragment extends Fragment
{
	private ListView dishList;
	private FinalDb finalDb;
	private List<Dish> dishItems;
	private DishItemArrayAdapter adapter;
	private MyApplication myApplication;
	private ProgressBar circleProgressPar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_dish, container, false);
		this.dishList = (ListView) rootView.findViewById(R.id.dish_list);
		this.myApplication = (MyApplication) getActivity().getApplication();
		this.finalDb = myApplication.getFinalDb();
		this.circleProgressPar = (ProgressBar) rootView.findViewById(R.id.circle_progress_bar);
		this.dishItems = new LinkedList<>();
		initDishList(-1);
		return rootView;
	}

	private void initDishList(int dishTypeChoosed)
	{
		Log.d("OrderSystem", "Now dish count : " + this.dishItems.size());
		adapter = new DishItemArrayAdapter(this.getActivity().getApplicationContext(), R.layout.adapter_dish_item, dishItems);
		dishList.setAdapter(adapter);
		dishList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent intent = new Intent();
				intent.setClass(getActivity(), DishViewPagerActivity.class);
				intent.putExtra("currentItem", position);
				getActivity().startActivity(intent);
				Log.d("item on click", "position at : " + position);
			}
		});
		new MyTask().execute(-1);
	}

	public void refreshDishList(int dishTypeChoosed)
	{
		new MyTask().execute(dishTypeChoosed);
	}
	
	private List<Dish> getDishItems(int dishTypeChoosed)
	{
		if(dishTypeChoosed == -1)
		{
			return finalDb.findAll(Dish.class);
		}
		else
		{
			return finalDb.findAllByWhere(Dish.class, "dishTypeId = " + dishTypeChoosed);
		}
	}
	
	// 异步刷新listView，避免卡顿
	private class MyTask extends AsyncTask<Integer, OrderDish, String>
	{
		@Override
		protected void onPreExecute()
		{
			circleProgressPar.setVisibility(View.VISIBLE);
			dishList.setVisibility(View.GONE);
		}

		@Override
		protected String doInBackground(Integer... params)
		{
			try
			{
				int dishTypeChoosed = params[0].intValue();
				dishItems.clear();
				dishItems.addAll(getDishItems(dishTypeChoosed));
				Thread.sleep(250);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(OrderDish... progresses)
		{
		}

		@Override
		protected void onPostExecute(String result)
		{
			adapter.notifyDataSetChanged();
			circleProgressPar.setVisibility(View.GONE);
			dishList.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onCancelled()
		{
		}
	}
}
