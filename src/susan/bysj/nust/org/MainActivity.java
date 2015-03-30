package susan.bysj.nust.org;

import java.util.LinkedList;
import java.util.List;

import susan.bysj.nust.org.adapter.NavDrawerItemAdapter;
import susan.bysj.nust.org.bean.model.NavDrawerItem;
import susan.bysj.nust.org.utils.Updater;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity
{
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private Fragment[] fragments;
	private int positionNow = -1;

	private Button showDrawerButton;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		showDrawerButton = (Button) findViewById(R.id.show_drawer_btn);
		showDrawerButton.getBackground().setAlpha(120);
		fragments = new Fragment[3];

		getActionBar().hide();

		// 更新菜品信息
		new Updater(this).update();
		initDrawerView();
	}

	public void refresh()
	{
		((DishListFragment) this.fragments[0]).refreshDishList();
	}

	private void initDrawerView()
	{
		String[] navDrawerItemsTitles = getResources().getStringArray(R.array.nav_drawer_items);
		TypedArray navDrawerItemsIcons = getResources().obtainTypedArray(R.array.nav_drawer_icons);

		List<NavDrawerItem> navDrawerItems = new LinkedList<NavDrawerItem>();
		for (int i = 0; i < navDrawerItemsTitles.length; i++)
		{
			navDrawerItems.add(new NavDrawerItem(navDrawerItemsTitles[i], navDrawerItemsIcons.getResourceId(i, -1)));
		}
		navDrawerItemsIcons.recycle();

		NavDrawerItemAdapter adapter = new NavDrawerItemAdapter(getApplicationContext(), navDrawerItems);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());
		showDrawerButton.setOnClickListener(new ShowDrawerClickListener());

		displayView(0);
	}

	private class ShowDrawerClickListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
			if (!mDrawerLayout.isDrawerOpen(mDrawerList))
			{
				mDrawerLayout.openDrawer(mDrawerList);
			}
			else
			{
				mDrawerLayout.closeDrawer(mDrawerList);
			}
		}
	}

	private class SlideMenuClickListener implements ListView.OnItemClickListener
	{
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
			displayView(position);
		}
	}

	private void displayView(int position)
	{
		if (position == positionNow)
		{
			mDrawerLayout.closeDrawer(mDrawerList);
			return;
		}
		positionNow = position;

		Fragment fragment = getFragment(position);
		if (fragment != null)
		{
			FragmentManager fragmentManager = getFragmentManager();
			fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();

			mDrawerList.setItemChecked(position, true);
			mDrawerList.setSelection(position);
			mDrawerLayout.closeDrawer(mDrawerList);
		}
		else
		{
			Log.e("MainActivity", "Error occur when creating fragment");
		}
	}

	private Fragment getFragment(int position)
	{
		if (this.fragments[position] != null)
		{
			return this.fragments[position];
		}

		switch (position)
		{
			case 0:
				return (this.fragments[position] = new DishListFragment());
			case 1:
				return (this.fragments[position] = new OrderListFragment());
			case 2:
				return (this.fragments[position] = new AboutFragment());
		}
		return null;
	}
}