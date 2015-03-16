package susan.bysj.nust.org.adapter;

import java.util.List;

import susan.bysj.nust.org.R;
import susan.bysj.nust.org.bean.NavDrawerItem;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavDrawerItemAdapter extends BaseAdapter
{
	private Context context;

	private List<NavDrawerItem> navDrawerItems;

	public NavDrawerItemAdapter(Context context, List<NavDrawerItem> navDrawerItems)
	{
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	@Override
	public int getCount()
	{
		return navDrawerItems.size();
	}

	@Override
	public Object getItem(int arg0)
	{
		return navDrawerItems.get(arg0);
	}

	@Override
	public long getItemId(int arg0)
	{
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		if (convertView == null)
		{
			LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.item_list_drawer, null);
		}

		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
		TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
		TextView txtCount = (TextView) convertView.findViewById(R.id.counter);

		imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
		txtTitle.setText(navDrawerItems.get(position).getTitle());

		if (navDrawerItems.get(position).isCounterVisibility())
		{
			txtCount.setText(navDrawerItems.get(position).getCount());
		}
		else
		{
			txtCount.setVisibility(View.GONE);
		}

		return convertView;
	}

}
