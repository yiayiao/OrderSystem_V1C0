package susan.bysj.nust.org.adapter;

import java.util.List;

import susan.bysj.nust.org.R;
import susan.bysj.nust.org.bean.DishTaste;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class DishTasteAdapter extends ArrayAdapter<DishTaste>
{
	private int resourceId;

	public DishTasteAdapter(Context context, int resource, List<DishTaste> objects)
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
		
		ImageView tasteRadio = (ImageView) dishPriceItemView.findViewById(R.id.dish_taste_radio_img);
		TextView dishTasteName = (TextView) dishPriceItemView.findViewById(R.id.dish_taste_name);

		DishTaste dishTaste = getItem(position);
		tasteRadio.setImageResource(dishTaste.isChoosed() ? R.drawable.btn_radio_on : R.drawable.btn_radio_off);
		dishTasteName.setText(dishTaste.getTaste());

		return dishPriceItemView;
	}
}