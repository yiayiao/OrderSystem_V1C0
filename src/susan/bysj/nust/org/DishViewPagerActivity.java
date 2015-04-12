package susan.bysj.nust.org;

import java.util.LinkedList;
import java.util.List;

import net.tsz.afinal.FinalDb;
import susan.bysj.nust.org.adapter.DishPriceAdapter;
import susan.bysj.nust.org.adapter.DishTasteAdapter;
import susan.bysj.nust.org.bean.Dish;
import susan.bysj.nust.org.bean.DishSize;
import susan.bysj.nust.org.bean.DishTaste;
import susan.bysj.nust.org.utils.AsyncImageLoader;
import susan.bysj.nust.org.utils.MyApplication;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("NewApi")
public class DishViewPagerActivity extends Activity
{
	private ViewPager viewPager;
	private Button buttonBackHome;
	private LayoutInflater mInflater;
	private FinalDb finalDb;
	private AsyncImageLoader asyncImageLoader;
	private int lastSizeChoosed = -1;
	private int lastTasteChoosed = -1;

	// viewpager的标题, 一个viewpager的指示器，效果就是一个横的粗的下划线
	private PagerTabStrip pagerTabStrip;

	// 把需要滑动的页卡添加到这个list中
	private List<View> viewList;
	private List<Dish> dishList;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dish_viewpager);

		finalDb = ((MyApplication) getApplication()).getFinalDb();
		asyncImageLoader = ((MyApplication) getApplication()).getAsyncImageLoader();
		mInflater = LayoutInflater.from(this);
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagertab);
		buttonBackHome = (Button) findViewById(R.id.back_home_btn);
		buttonBackHome.getBackground().setAlpha(100);

		buttonBackHome.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				DishViewPagerActivity.this.finish();
			}
		});

		getActionBar().hide();
		int currentItem = this.getIntent().getExtras().getInt("currentItem");
		initView(currentItem);
	}

	private void initView(int currentItem)
	{
		pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.red));
		pagerTabStrip.setDrawFullUnderline(false);
		pagerTabStrip.setTextSpacing(0);

		dishList = finalDb.findAll(Dish.class);
		viewList = new LinkedList<View>();
		for (Dish dish : dishList)
		{
			viewList.add(generateView(dish));
		}

		PagerAdapter pagerAdapter = new MyPagerAdapter();
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(currentItem);
	}

	/**
	 * 初始化pageViewer中的每个page，即菜品详情页面
	 * 
	 * @param dish
	 */
	private View generateView(Dish dish)
	{
		final ViewPagerHolder holder = new ViewPagerHolder();
		View view = mInflater.inflate(R.layout.activity_dish_detail, null);
		holder.dishPicBig = (ImageView) view.findViewById(R.id.dish_pic_big);
		setBitmap(holder.dishPicBig, "https://www.baidu.com/img/bdlogo.png");
		holder.dishDetailView = (TextView) view.findViewById(R.id.dish_detail);
		holder.dishDetailView.setText(dish.getDetail());
		holder.dishRecommandView = (ImageView) view.findViewById(R.id.dish_recommend);
		if (dish.getRecommend() == 0)
		{
			holder.dishRecommandView.setVisibility(View.GONE);
		}

		List<DishSize> dishSizeList = finalDb.findAllByWhere(DishSize.class, "dishId = " + dish.getDishId());
		holder.dishPriceAdapter = new DishPriceAdapter(this, R.layout.adapter_dish_price, dishSizeList);
		holder.dishPriceGridView = (GridView) view.findViewById(R.id.dish_price_view);
		holder.dishPriceGridView.setAdapter(holder.dishPriceAdapter);
		holder.dishPriceGridView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				if (lastSizeChoosed != arg2)
				{
					if (lastSizeChoosed >= 0)
					{
						changeSizeRadioImg(holder.dishPriceAdapter, lastSizeChoosed, false);
					}
					lastSizeChoosed = arg2;
					changeSizeRadioImg(holder.dishPriceAdapter, arg2, true);
				}
			}
		});

		List<DishTaste> dishTasteList = finalDb.findAllByWhere(DishTaste.class, "dishId = " + dish.getDishId());
		holder.dishTasteAdapter = new DishTasteAdapter(this, R.layout.adapter_dish_taste, dishTasteList);
		holder.dishTasteGridView = (GridView) view.findViewById(R.id.dish_taste_view);
		holder.dishTasteGridView.setAdapter(holder.dishTasteAdapter);
		holder.dishTasteGridView.setOnItemClickListener(
		new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				if (lastTasteChoosed != arg2)
				{
					if (lastTasteChoosed >= 0)
					{
						changeTasteRadioImg(holder.dishTasteAdapter, lastTasteChoosed, false);
					}
					lastTasteChoosed = arg2;
					changeTasteRadioImg(holder.dishTasteAdapter, arg2, true);
				}
			}
		});

		return view;
	}

	private Bitmap setBitmap(final ImageView iv, String imgUrl)
	{
		// 下载图片，第二个参数是否缓存至内存中
		asyncImageLoader.downloadImage(imgUrl, true/* false */, new AsyncImageLoader.ImageCallback()
		{
			@Override
			public void onImageLoaded(Bitmap bitmap, String imageUrl)
			{
				if (bitmap != null)
				{
					iv.setImageBitmap(bitmap);
				}
				else
				{
					// 下载失败，设置默认图片
				}
			}
		});
		return null;
	}

	private void changeTasteRadioImg(DishTasteAdapter adapter, int selectedItem, boolean choosed)
	{
		DishTaste dishTaste = adapter.getItem(selectedItem);
		dishTaste.setChoosed(choosed);
		adapter.notifyDataSetChanged();
	}

	private void changeSizeRadioImg(DishPriceAdapter dishPriceItemAdapter, int selectedItem, boolean choosed)
	{
		DishSize dishSize = dishPriceItemAdapter.getItem(selectedItem);
		dishSize.setChoosed(choosed);
		dishPriceItemAdapter.notifyDataSetChanged();
	}

	public class ViewPagerHolder
	{
		private ImageView dishPicBig;
		private TextView dishDetailView;
		private ImageView dishRecommandView;
		private GridView dishPriceGridView;
		private DishPriceAdapter dishPriceAdapter;
		private GridView dishTasteGridView;
		private DishTasteAdapter dishTasteAdapter;

	}

	class MyPagerAdapter extends PagerAdapter
	{
		@Override
		public boolean isViewFromObject(View view, Object obj)
		{
			return view == obj;
		}

		@Override
		public int getCount()
		{
			return viewList.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object)
		{
			ViewPager viewPager = ((ViewPager) container);
			viewPager.removeView(viewList.get(position));
		}

		@Override
		public int getItemPosition(Object object)
		{
			return super.getItemPosition(object);
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			return dishList.get(position).getDishName();
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position)
		{
			ViewPager viewPager = ((ViewPager) container);
			viewPager.addView(viewList.get(position));
			return viewList.get(position);
		}
	}
}