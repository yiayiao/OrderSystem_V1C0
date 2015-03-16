package susan.bysj.nust.org.adapter;

import java.util.List;

import susan.bysj.nust.org.R;
import susan.bysj.nust.org.bean.DishItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DishItemArrayAdapter extends ArrayAdapter<DishItem>
{
	private int resourceId;

	public DishItemArrayAdapter(Context context, int textViewResourceId, List<DishItem> objects)
	{
		super(context, textViewResourceId, objects);
		this.resourceId = textViewResourceId;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
        String inflater = Context.LAYOUT_INFLATER_SERVICE;   
        LayoutInflater vi = (LayoutInflater)getContext().getSystemService(inflater); 
        LinearLayout userListItem = new LinearLayout(getContext()); 
        
        //这里也可以写成  vi.inflate(resourceId, null, true), 具体哪个好，暂不清楚
        View dishItemView = vi.inflate(resourceId, userListItem, true); 
		
		ImageView dishImage = (ImageView) dishItemView.findViewById(R.id.dish_img);
		TextView dishName = (TextView) dishItemView.findViewById(R.id.dish_name);
		TextView dishPrice = (TextView) dishItemView.findViewById(R.id.dish_price);
		TextView dishDesc = (TextView) dishItemView.findViewById(R.id.dish_desc);

		DishItem dishItem = getItem(position);
		dishImage.setImageResource(R.drawable.huiguorou);
		dishName.setText(dishItem.getDishName());
		dishPrice.setText(dishItem.getDishPrice());
		dishDesc.setText(dishItem.getDishDesc());

		return dishItemView;
	}
}
