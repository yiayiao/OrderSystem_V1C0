package susan.bysj.nust.org;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class DishViewPagerActivity extends Activity
{
	private ViewPager viewPager; // viewpager
	private Button buttonBackHome;

	// viewpager的标题
	private PagerTabStrip pagerTabStrip;// 一个viewpager的指示器，效果就是一个横的粗的下划线
	private List<View> viewList;// 把需要滑动的页卡添加到这个list中
	private List<String> titleList;// viewpager的标题

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dish_viewpager);

		viewPager = (ViewPager) findViewById(R.id.viewpager);
		pagerTabStrip = (PagerTabStrip) findViewById(R.id.pagertab);
		buttonBackHome = (Button) findViewById(R.id.back_home_btn);
		buttonBackHome.getBackground().setAlpha(100);

		buttonBackHome.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View arg0)
			{
//				Intent intent = new Intent();
//				intent.setClass(DishViewPagerActivity.this, MainActivity.class);
//				startActivity(intent);
				DishViewPagerActivity.this.finish();
			}
		});

		getActionBar().hide();
		initView();
	}

	/*
	 * 在这里需要说明一下，在上面的图片中我们看到了，PagerTabStrip，PagerTitleStrip，他们其实是viewpager的一个指示器，
	 * 前者效果就是一个横的粗的下划线
	 * ，后者用来显示各个页卡的标题，当然而这也可以共存。在使用他们的时候需要注意，看下面的布局文件，要在android.support
	 * .v4.view.ViewPager里面添加
	 * android.support.v4.view.PagerTabStrip以及android.support
	 * .v4.view.PagerTitleStrip
	 */

	private LayoutInflater mInflater;

	public class ViewPagerHolder
	{
		private TextView mWarning;
		private TextView mTitle;
		private TextView mAuthor;
		private TextView mData;
		private WebView mContent;
		private LinearLayout mpintLayout;
	}

	private View setView()
	{
		// TODO Auto-generated method stub
		// final ViewPagerHolder holder = new ViewPagerHolder();
		View view = mInflater.inflate(R.layout.view_dish_page, null);
		// holder.mTitle = (TextView) view.findViewById(R.id.m_name);
		// holder.mAuthor = (TextView)
		// view.findViewById(R.id.text_news_detail_author);
		// holder.mData = (TextView)
		// view.findViewById(R.id.text_news_detail_data);
		// holder.mGallery = (Gallery) view.findViewById(R.id.gallery);
		// holder.mGallery.setVisibility(View.GONE);

		// holder.mContent = (WebView)
		// view.findViewById(R.id.text_news_datails_content);
		// holder.mContent.setBackgroundColor(Color.parseColor("#ebebeb"));

		return view;
	}

	private void initView()
	{
		mInflater = LayoutInflater.from(this);

		pagerTabStrip.setTabIndicatorColor(getResources().getColor(R.color.red));
		// pagerTabStrip.setTextColor(getResources().getColor(R.color.darkGrey));
		pagerTabStrip.setDrawFullUnderline(false);
		pagerTabStrip.setTextSpacing(0);

		viewList = new ArrayList<View>();// 将要分页显示的View装入数组中
		viewList.add(setView());
		viewList.add(setView());
		viewList.add(setView());

		titleList = new ArrayList<String>();// 每个页面的Title数据
		titleList.add("回锅肉");
		titleList.add("鱼香肉丝");
		titleList.add("酸菜鱼");

		PagerAdapter pagerAdapter = new MyPagerAdapter();
		viewPager.setAdapter(pagerAdapter);
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
			return titleList.get(position);
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