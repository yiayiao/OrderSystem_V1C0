package susan.bysj.nust.org.adapter;

import java.util.List;

import susan.bysj.nust.org.bean.Dish;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class MyPagerAdapter extends PagerAdapter
{
	private List<View> viewList;
	
	private List<Dish> dishList;
	
	public MyPagerAdapter(List<View> viewList, List<Dish> dishDataList)
	{
		this.viewList = viewList;
		this.dishList = dishDataList;
	}
	
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
