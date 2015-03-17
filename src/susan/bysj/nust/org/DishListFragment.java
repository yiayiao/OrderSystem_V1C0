package susan.bysj.nust.org;

import java.util.LinkedList;
import java.util.List;

import susan.bysj.nust.org.adapter.DishItemArrayAdapter;
import susan.bysj.nust.org.bean.DishItem;
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
	private List<DishItem> dishItems;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.fragment_dish, container, false);
		this.dishList = (ListView) rootView.findViewById(R.id.dish_list);
		init();
		return rootView;
	}

	public void init()
	{
		this.dishItems = new LinkedList<DishItem>();
		dishItems.add(testGenerateDishItem());
		dishItems.add(testGenerateDishItem());
		dishItems.add(testGenerateDishItem());

		DishItemArrayAdapter adapter = new DishItemArrayAdapter(this.getActivity().getApplicationContext(), R.layout.item_list_dish, dishItems);
		dishList.setAdapter(adapter);
		dishList.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				Intent intent = new Intent();
				intent.setClass(getActivity(), DishViewPagerActivity.class);
				getActivity().startActivity(intent);
				Log.d("item on click", "position at : " + position);
			}
		});
	}

	private DishItem testGenerateDishItem()
	{
		DishItem dishItem = new DishItem("haha", "xixi", "nono");

		return dishItem;
	}

}
