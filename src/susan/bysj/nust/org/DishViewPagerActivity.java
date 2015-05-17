package susan.bysj.nust.org;

import java.util.LinkedList;
import java.util.List;

import net.tsz.afinal.FinalDb;
import susan.bysj.nust.org.adapter.DishPriceAdapter;
import susan.bysj.nust.org.adapter.DishTasteAdapter;
import susan.bysj.nust.org.adapter.MyPagerAdapter;
import susan.bysj.nust.org.bean.Dish;
import susan.bysj.nust.org.bean.DishSize;
import susan.bysj.nust.org.bean.DishTaste;
import susan.bysj.nust.org.bean.OrderDish;
import susan.bysj.nust.org.utils.AsyncImageLoader;
import susan.bysj.nust.org.utils.MyApplication;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class DishViewPagerActivity extends Activity
{
	private ViewPager viewPager;
	private Button buttonBackHome;

	private FinalDb finalDb;
	private AsyncImageLoader asyncImageLoader;
	private DishTaste dishTasteLastChoosed;
	private DishSize dishSizeLastChoosed;
	private MyApplication myApplication;

	// viewpager的标题, 一个viewpager的指示器，效果就是一个横的粗的下划线
	private PagerTabStrip pagerTabStrip;

	// 把需要滑动的页卡添加到这个list中
	private List<View> dishViewList;
	private List<Dish> dishDataList;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dish_viewpager);

		finalDb = ((MyApplication) getApplication()).getFinalDb();
		asyncImageLoader = ((MyApplication) getApplication()).getAsyncImageLoader();
		viewPager = (ViewPager) findViewById(R.id.viewpager);
		pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagertab);
		myApplication = (MyApplication) getApplication();

		getActionBar().hide();
		initView();
		initBackHomeBtn();
	}

	private void initBackHomeBtn()
	{
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
	}

	private void initView()
	{
		// 设置滑动页面的Tab
		pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.red));
		pagerTabStrip.setDrawFullUnderline(false);
		pagerTabStrip.setTextSpacing(0);

		// 初始化滑动页面的数据和页面View
		dishDataList = finalDb.findAll(Dish.class);
		dishViewList = new LinkedList<View>();
		for (Dish dish : dishDataList)
		{
			dishViewList.add(generateView(dish));
		}

		// 设置页面Adapter
		PagerAdapter pagerAdapter = new MyPagerAdapter(dishViewList, dishDataList);
		viewPager.setAdapter(pagerAdapter);
		int currentItem = this.getIntent().getExtras().getInt("currentItem");
		viewPager.setCurrentItem(currentItem);
	}

	/**
	 * 初始化pageViewer中的每个page，即菜品详情页面
	 */
	private View generateView(final Dish dish)
	{
		final ViewPagerHolder holder = new ViewPagerHolder();
		LayoutInflater mInflater = LayoutInflater.from(this);
		View view = mInflater.inflate(R.layout.activity_dish_detail, null);

		// 设置菜品详情大图
		holder.dishPicBig = (ImageView) view.findViewById(R.id.dish_pic_big);
		holder.dishPicBig.setImageBitmap(getBitmap("https://www.baidu.com/img/bdlogo.png"));

		// 设置菜品详细信息，如果该菜品不是推荐菜品，隐藏推荐图片
		holder.dishDetailView = (TextView) view.findViewById(R.id.dish_detail);
		holder.dishDetailView.setText(dish.getDetail());
		holder.dishRecommandView = (ImageView) view.findViewById(R.id.dish_recommend);
		if (dish.getRecommend() == 0)
		{
			holder.dishRecommandView.setVisibility(View.GONE);
		}

		// 初始化菜品的size选择框
		List<DishSize> dishSizeList = finalDb.findAllByWhere(DishSize.class, "dishId = " + dish.getDishId());
		holder.dishPriceAdapter = new DishPriceAdapter(this, R.layout.adapter_dish_price, dishSizeList);
		holder.dishPriceGridView = (GridView) view.findViewById(R.id.dish_price_view);
		holder.dishPriceGridView.setAdapter(holder.dishPriceAdapter);
		holder.dishPriceGridView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				changeDishSizeChoose(holder.dishPriceAdapter, arg2);
			}
		});

		// 初始化菜品的taste多选框
		List<DishTaste> dishTasteList = finalDb.findAllByWhere(DishTaste.class, "dishId = " + dish.getDishId());
		holder.dishTasteAdapter = new DishTasteAdapter(this, R.layout.adapter_dish_taste, dishTasteList);
		holder.dishTasteGridView = (GridView) view.findViewById(R.id.dish_taste_view);
		holder.dishTasteGridView.setAdapter(holder.dishTasteAdapter);
		holder.dishTasteGridView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3)
			{
				changeDishTasteChoose(holder.dishTasteAdapter, arg2);
			}
		});

		// 初始化确认购买按钮
		holder.buyBtn = (ImageButton) view.findViewById(R.id.dish_add_to_order);
		holder.buyBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
				if (dishSizeLastChoosed == null)
				{
					Toast.makeText(DishViewPagerActivity.this, "请选择菜品大小", Toast.LENGTH_SHORT).show();
					return;
				}

				if (dishTasteLastChoosed == null)
				{
					Toast.makeText(DishViewPagerActivity.this, "请选择菜品口味", Toast.LENGTH_SHORT).show();
					return;
				}

				Log.d("OrderSystem", "dishName : " + dish.getDishName() + " dishSize : " + dishSizeLastChoosed.getDishSizeId() + " dishTaste : "
				        + dishTasteLastChoosed.getDishTasteId());
				OrderDish orderDish = new OrderDish();
				orderDish.setDishId(dish.getDishId());
				orderDish.setDishSizeId(dishSizeLastChoosed.getDishSizeId());
				orderDish.setDishTasteId(dishTasteLastChoosed.getDishTasteId());
				orderDish.setNum(1);
				myApplication.addOrder(orderDish);
				
				Toast.makeText(DishViewPagerActivity.this, "菜品被成功添加至订单", Toast.LENGTH_SHORT).show();
			}
		});

		return view;
	}

	private void changeDishSizeChoose(DishPriceAdapter dishSizedapter, int position)
	{
		DishSize dishSize = dishSizedapter.getItem(position);
		if (dishSize.equals(dishSizeLastChoosed))
		{
			return;
		}

		if (dishSizeLastChoosed != null)
		{
			dishSizeLastChoosed.setChoosed(false);
		}
		dishSize.setChoosed(true);
		dishSizeLastChoosed = dishSize;
		dishSizedapter.notifyDataSetChanged();
	}

	private void changeDishTasteChoose(DishTasteAdapter dishTasteAdapter, int position)
	{
		DishTaste dishTaste = dishTasteAdapter.getItem(position);
		if (dishTaste.equals(dishTasteLastChoosed))
		{
			return;
		}

		if (dishTasteLastChoosed != null)
		{
			dishTasteLastChoosed.setChoosed(false);
		}
		dishTaste.setChoosed(true);
		dishTasteLastChoosed = dishTaste;
		dishTasteAdapter.notifyDataSetChanged();
	}

	private Bitmap bitmapRet;

	private Bitmap getBitmap(String imgUrl)
	{
		// 下载图片，第二个参数是否缓存至内存中
		asyncImageLoader.downloadImage(imgUrl, true, new AsyncImageLoader.ImageCallback()
		{
			@Override
			public void onImageLoaded(Bitmap bitmap, String imageUrl)
			{
				if (bitmap != null)
				{
					bitmapRet = bitmap;
				}
				else
				{
					// 下载失败，设置默认图片
				}

			}
		});
		return bitmapRet;
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

		private ImageButton buyBtn;
	}

}