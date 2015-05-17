package susan.bysj.nust.org;

import java.util.LinkedList;
import java.util.List;

import susan.bysj.nust.org.adapter.DishOrdersArrayAdapter;
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
public class OrderListFragment extends Fragment
{
	private MyApplication myApplication;
	private DishOrdersArrayAdapter adapter;
	private ListView dishOrderList;
	private List<OrderDish> dishOrders;
	private ProgressBar circleProgressPar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		myApplication = (MyApplication) getActivity().getApplication();
		if (myApplication.getOrderList() == null)
		{
			View rootView = inflater.inflate(R.layout.fragment_order_empty, container, false);
			return rootView;
		}

		View rootView = inflater.inflate(R.layout.fragment_order, container, false);
		dishOrderList = (ListView) rootView.findViewById(R.id.dish_order_list);
		circleProgressPar = (ProgressBar) rootView.findViewById(R.id.circle_progress_bar);
		dishOrders = new LinkedList<>();
		initDishList();
		return rootView;
	}

	private void initDishList()
	{
		adapter = new DishOrdersArrayAdapter(this.getActivity().getApplicationContext(), R.layout.adapter_dish_order, dishOrders, myApplication.getFinalDb());
		dishOrderList.setAdapter(adapter);
		new MyTask().execute();
	}

	// 异步加载ListView，避免卡顿
	private class MyTask extends AsyncTask<String, OrderDish, String>
	{
		@Override
		protected void onPreExecute()
		{
			circleProgressPar.setVisibility(View.VISIBLE);
			dishOrderList.setVisibility(View.GONE);
		}

		@Override
		protected String doInBackground(String... params)
		{
			try
			{
				dishOrders.addAll(myApplication.getOrderList());
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
			dishOrderList.setOnItemClickListener(new OnItemClickListener()
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

			circleProgressPar.setVisibility(View.GONE);
			dishOrderList.setVisibility(View.VISIBLE);
		}

		@Override
		protected void onCancelled()
		{
		}
	}
}
