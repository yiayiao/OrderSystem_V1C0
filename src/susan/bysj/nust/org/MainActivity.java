package susan.bysj.nust.org;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import net.tsz.afinal.FinalDb;
import susan.bysj.nust.org.adapter.NavDrawerItemAdapter;
import susan.bysj.nust.org.bean.DishType;
import susan.bysj.nust.org.bean.model.NavDrawerItem;
import susan.bysj.nust.org.utils.AsyncImageLoader;
import susan.bysj.nust.org.utils.MyApplication;
import susan.bysj.nust.org.utils.Updater;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

@SuppressLint("NewApi")
public class MainActivity extends FragmentActivity
{
	private static final String OrderListFragment = null;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private Fragment[] fragments;
	private int positionNow = -1;
	private MyApplication myApplication;
	private Button showDrawerButton;
	private PopupWindow mPopupWindow;
	private TextView dishTypeText;
	private FinalDb finalDb;
	private TextView totalPrice;

	private View menuLayout;
	private View[] menuLayouts;

	private List<String> dishTypeList;
	private Hashtable<String, Integer> dishTypeCash;
	private ArrayAdapter<String> dishTypeAdapter;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myApplication = (MyApplication) getApplication();
		finalDb = FinalDb.create(this);
		myApplication.setFinalDb(finalDb);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);
		showDrawerButton = (Button) findViewById(R.id.show_drawer_btn);
		showDrawerButton.getBackground().setAlpha(120);
		fragments = new Fragment[3];
		dishTypeText = (TextView) findViewById(R.id.dish_type_text);
		totalPrice = (TextView) findViewById(R.id.total_price);

		menuLayout = (View) findViewById(R.id.menu_layout);
		menuLayouts = new View[3];
		menuLayouts[0] = (View) findViewById(R.id.menu_layout_0);
		menuLayouts[1] = (View) findViewById(R.id.menu_layout_1);
		menuLayouts[2] = (View) findViewById(R.id.menu_layout_2);

		// 更新菜品信息
		getActionBar().hide();
		new Updater(this).update();
		initDrawerView(); // 初始化抽屉式布局
		initPopupView(); // 初始化菜单列表的popupView
		initAsyncImageLoader();
	}

	// ## BEGIN : 初始化菜单弹出窗口
	private void initPopupView()
	{
		LayoutInflater layoutInflater = LayoutInflater.from(this);
		View popupWindow = layoutInflater.inflate(R.layout.popup_window, null);
		initPopupWindowData();
		setPopipWindowListView(popupWindow);
		initPopupWindow(popupWindow);
		initDishTypeTextListener();
	}

	private void initPopupWindowData()
	{
		List<DishType> dishTypes = finalDb.findAll(DishType.class);
		Log.d("OrderSystem", "dishType ++++++++++++++++ " + dishTypes.size());
		dishTypeList = new LinkedList<>();
		dishTypeCash = new Hashtable<>();
		for (DishType dishType : dishTypes)
		{
			dishTypeCash.put(dishType.getTypeName(), dishType.getDishTypeId());
			dishTypeList.add(dishType.getTypeName());
		}
	}

	private void setPopipWindowListView(View popupWindow)
	{
		dishTypeAdapter = new ArrayAdapter<String>(this, R.layout.spinner_item, R.id.contentTextView, dishTypeList);
		ListView listView = (ListView) popupWindow.findViewById(R.id.dish_type_spiner);
		listView.setAdapter(dishTypeAdapter);
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				Log.i("OrderSystem", "click +++++++++++++++++++++ " + arg2 + "   " + arg3);
				String str = dishTypeAdapter.getItem(arg2);
				dishTypeText.setText(str);
				int dishTypeChoosed = dishTypeCash.get(str);
				((DishListFragment) fragments[0]).refreshDishList(dishTypeChoosed);
				mPopupWindow.dismiss();
			}
		});
	}

	private void initPopupWindow(View popupWindow)
	{
		mPopupWindow = new PopupWindow(popupWindow, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
		mPopupWindow.setBackgroundDrawable(new ColorDrawable()); // 如果设置Width为LayoutParams.FILL_PARENT又没有调用这一句,边框留出空白
		mPopupWindow.setFocusable(true); // 里面的控件不能获取焦点,不设置默认的就是false
		mPopupWindow.setOutsideTouchable(true); // 触碰PopupWindow以外的布局可以获得触摸事件

		mPopupWindow.setOnDismissListener(new OnDismissListener()
		{
			@Override
			public void onDismiss()
			{
				// popupWindow消失时，恢复背景色
				mDrawerLayout.setBackgroundColor(Color.parseColor("#FFFFFF"));
				mDrawerLayout.setAlpha(1f);
			}
		});
		mPopupWindow.update();
	}

	private void initDishTypeTextListener()
	{
		dishTypeText.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				// popupWindow弹出时，让背景变灰，变透明
				mPopupWindow.showAsDropDown(menuLayout, 0, 2);
				mDrawerLayout.setAlpha(0.5f);
				mDrawerLayout.setBackgroundColor(Color.parseColor("#AAAAAA"));
			}
		});
	}

	// # ENG : 初始化菜单弹出窗口

	// #BEGIN 初始化抽屉式菜单 ###########################################
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

		showDrawerButton.setOnClickListener(new ShowDrawerClickListener());
		NavDrawerItemAdapter adapter = new NavDrawerItemAdapter(getApplicationContext(), navDrawerItems);
		mDrawerList.setAdapter(adapter);
		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

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
		Log.d("OrderSystem", "now show Fragment: " + position);
		if (position == positionNow)
		{
			mDrawerLayout.closeDrawer(mDrawerList);
			return;
		}
		positionNow = position;

		for (int i = 0; i < menuLayouts.length; i++)
		{
			menuLayouts[i].setVisibility(i == position ? View.VISIBLE : View.GONE);
		}

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
				this.fragments[position] = new OrderListFragment();
				((OrderListFragment) this.fragments[position]).setMainActivity(this);
				//this.totalPrice.setText("￥" + 0.0);
				return this.fragments[position];
			case 2:
				return (this.fragments[position] = new AboutFragment());
		}
		return null;
	}

	public void chooseDishClick(View view)
	{
		displayView(0);
	}

	// #END 初始化抽屉式菜单

	// 初始化图片同步设置
	private void initAsyncImageLoader()
	{
		AsyncImageLoader loader = new AsyncImageLoader(getApplicationContext());
		loader.setCache2File(true); // 将图片缓存至外部文件中
		loader.setCachedDir(this.getCacheDir().getAbsolutePath()); // 设置外部缓存文件夹
		myApplication.setAsyncImageLoader(loader);
	}

	// 数据在应用启动时进行异步刷新，刷新结束之后，需要调用这个方法，重新进行界面展示
	public void refreshData()
	{
		if (dishTypeAdapter != null)
		{
			// 刷新菜品种类
			initPopupWindowData();
			dishTypeAdapter.notifyDataSetChanged();
		}

		// 刷新菜单列表
		((DishListFragment) this.fragments[0]).refreshDishList(-1);
	}

	public void setTotalPrice(float price)
	{
		this.totalPrice.setText("￥" + price);
	}

}