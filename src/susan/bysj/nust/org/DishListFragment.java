package susan.bysj.nust.org;

import java.util.LinkedList;

import susan.bysj.nust.org.adapter.DishItemArrayAdapter;
import susan.bysj.nust.org.bean.DishItem;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.handmark.pulltorefresh.extras.listfragment.PullToRefreshListFragment;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

/**
 * @author Susan
 * ����2015��3��12��
 * 1. fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit() �����  onCreateView�����Գ�ʼ�����ݻ��Ƿ��ڹ��캯���бȽϺ�
 * 2. ����fragment��ʱ�򣬸о���Ļ�������ر�����������һ�£��²�ԭ��a.android �ⷽ�治��;b.�ֻ�����;c,PullToRefreshListFragment���С���ʱ�����Ż���
 * 3. ʹ�� http://blog.csdn.net/ljtyzhr/article/details/40736525 ��������  private View rootView; ����Fragment view, ȥ��onDestroyView��������2��һЩ
 */

/**
 * @author Susan �˵��б�
 */
@SuppressLint("NewApi")
public class DishListFragment extends Fragment implements OnRefreshListener<ListView>
{
	private FragmentManager fragmentManager;
	private DishItemArrayAdapter adapter;

	private PullToRefreshListFragment mPullRefreshListFragment;
	private PullToRefreshListView mPullRefreshListView;

	LinkedList<DishItem> dishItems = new LinkedList<DishItem>();

	private static View rootView = null;

	public DishListFragment()
	{
		dishItems.add(testGenerateDishItem());
		dishItems.add(testGenerateDishItem());
		dishItems.add(testGenerateDishItem());
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		//View rootView = null;
		try
		{
			if (rootView == null)
			{
				rootView = inflater.inflate(R.layout.fragment_dish, container, false);
			}
			ViewGroup parent = (ViewGroup) rootView.getParent();
			if (parent != null)
			{
				parent.removeView(rootView);
			}
			//rootView = inflater.inflate(R.layout.fragment_dish, container, false);
		}
		catch (InflateException e)
		{
			Log.e("OrderSystem", "DishListFragment onCreateView exception, e = " + e);
		}

		initDishFragment();
		return rootView;
	}

	private void initDishFragment()
	{
		FragmentActivity fragmentActivity = (FragmentActivity) getActivity();
		fragmentManager = fragmentActivity.getSupportFragmentManager();
		mPullRefreshListFragment = (PullToRefreshListFragment) fragmentManager.findFragmentById(R.id.frag_ptr_list);
		mPullRefreshListView = mPullRefreshListFragment.getPullToRefreshListView();
		mPullRefreshListView.setOnRefreshListener(this);
		
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
            public void onItemClick(AdapterView<?> arg0, View view, int position, long id)
            {
				DishItem dishItem = dishItems.get(position - 1);
				
				Log.d("debug on item click", "name :" + dishItem.getDishName() + " desc: " + dishItem.getDishDesc());
            }
		});

		adapter = new DishItemArrayAdapter(this.getActivity().getApplicationContext(), R.layout.item_list_dish, dishItems);
		mPullRefreshListView.getRefreshableView().setAdapter(adapter);
		mPullRefreshListFragment.setListShown(true);
	}

//	@Override
//	public void onDestroyView()
//	{
//		super.onDestroyView();
//		if (mPullRefreshListFragment != null && fragmentManager != null)
//		{
//			fragmentManager.beginTransaction().remove(mPullRefreshListFragment).commit();
//		}
//	}

	@Override
	public void onRefresh(PullToRefreshBase<ListView> refreshView)
	{
		new GetDataTask().execute();
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]>
	{
		@Override
		protected String[] doInBackground(Void... params)
		{
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException e)
			{
			}
			return null;
		}

		@Override
		protected void onPostExecute(String[] result)
		{
			dishItems.addFirst(testGenerateDishItem());
			adapter.notifyDataSetChanged();

			mPullRefreshListView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}

	private DishItem testGenerateDishItem()
	{
		DishItem dishItem = new DishItem("haha", "xixi", "nono");
		return dishItem;
	}

}
