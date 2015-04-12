package susan.bysj.nust.org.adapter;

import java.util.List;

import susan.bysj.nust.org.R;
import susan.bysj.nust.org.bean.DishSize;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DishPriceAdapter extends ArrayAdapter<DishSize>
{
	private int resourceId;

	public DishPriceAdapter(Context context, int resource, List<DishSize> objects)
	{
		super(context, resource, objects);
		this.resourceId = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		String inflater = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater vi = (LayoutInflater) getContext().getSystemService(inflater);
		LinearLayout userListItem = new LinearLayout(getContext());
		View dishPriceItemView = vi.inflate(resourceId, userListItem, true);

		ImageView priceRadio = (ImageView) dishPriceItemView.findViewById(R.id.dish_size_radio_img);
		TextView dishSizeName = (TextView) dishPriceItemView.findViewById(R.id.dish_size_name);
		TextView oldPrice = (TextView) dishPriceItemView.findViewById(R.id.dish_size_old_price);
		TextView nowPrice = (TextView) dishPriceItemView.findViewById(R.id.dish_size_now_price);

		DishSize dishSizeItem = getItem(position);
		priceRadio.setImageResource(dishSizeItem.getChoosed() ? R.drawable.btn_radio_on : R.drawable.btn_radio_off);
		dishSizeName.setText(dishSizeItem.getSizeName());
		oldPrice.setText("原价:" + dishSizeItem.getOldPrice());
		nowPrice.setText("现价:" + dishSizeItem.getNowPrice());

		return dishPriceItemView;
	}
}
