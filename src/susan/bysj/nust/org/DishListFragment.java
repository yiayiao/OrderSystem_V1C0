package susan.bysj.nust.org;

import java.util.List;

import net.tsz.afinal.FinalDb;
import susan.bysj.nust.org.adapter.DishItemArrayAdapter;
import susan.bysj.nust.org.bean.Dish;
import susan.bysj.nust.org.utils.MyApplication;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

@SuppressLint("NewApi")
public class DishListFragment extends Fragment
{
	private ListView dishList;
	private FinalDb finalDb;
	private List<Dish> dishItems;
	private DishItemArrayAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_dish, container, false);
		this.finalDb = ((MyApplication) getActivity().getApplication()).getFinalDb();
		this.dishList = (ListView) rootView.findViewById(R.id.dish_list);
		initDishList();
		return rootView;
	}

	private void initDishList()
	{
		this.dishItems = finalDb.findAll(Dish.class);
		Log.d("OrderSystem", "############### now dish count : " + this.dishItems.size() + " ################");
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
	}

	public void refreshDishList()
	{
		if (adapter == null)
		{
			initDishList();
		}
		else
		{
			this.dishItems.clear();
			this.dishItems.addAll(finalDb.findAll(Dish.class));
			this.dishItems = finalDb.findAll(Dish.class);
			Log.d("OrderSystem", "############### now dish count : " + this.dishItems.size() + " ################");
			this.adapter.notifyDataSetChanged();
		}
	}
}
