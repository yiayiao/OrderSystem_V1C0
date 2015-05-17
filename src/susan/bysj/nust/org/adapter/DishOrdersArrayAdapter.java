package susan.bysj.nust.org.adapter;

import java.util.List;

import net.tsz.afinal.FinalDb;
import susan.bysj.nust.org.OrderListFragment;
import susan.bysj.nust.org.R;
import susan.bysj.nust.org.bean.Dish;
import susan.bysj.nust.org.bean.DishSize;
import susan.bysj.nust.org.bean.DishTaste;
import susan.bysj.nust.org.bean.OrderDish;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressLint("Recycle")
public class DishOrdersArrayAdapter extends ArrayAdapter<OrderDish>
{
	private int resourceId;
	private FinalDb finalDb;
	private View selectedView;
	private int selectedPosition;
	private Animation animation; // 删除时候的动画
	private boolean delFlag;
	private OrderListFragment orderListFragment;

	public DishOrdersArrayAdapter(Context context, int textViewResourceId, List<OrderDish> objects, FinalDb finalDb, OrderListFragment orderListFragment)
	{
		super(context, textViewResourceId, objects);
		this.resourceId = textViewResourceId;
		this.finalDb = finalDb;
		this.orderListFragment = orderListFragment;
		animation = AnimationUtils.loadAnimation(context, R.anim.push_out); // 用xml获取一个动画
	}

	@Override
	public View getView(final int position, View dishItemView, ViewGroup parent)
	{
		ViewHolder holder = null;
		if (dishItemView == null)
		{
			dishItemView = LayoutInflater.from(getContext()).inflate(resourceId, null);
			holder = new ViewHolder();
			holder.dishImage = (ImageView) dishItemView.findViewById(R.id.dish_img);
			holder.dishNameLabel = (TextView) dishItemView.findViewById(R.id.dish_name_label);
			holder.dishNameLabel2 = (TextView) dishItemView.findViewById(R.id.dish_name_label2);
			holder.dishSizeLabel = (TextView) dishItemView.findViewById(R.id.dish_size_label);
			holder.dishTasteLabel = (TextView) dishItemView.findViewById(R.id.dish_taste_label);
			holder.dishDescLabel = (TextView) dishItemView.findViewById(R.id.dish_desc_label);
			holder.button = (TextView) dishItemView.findViewById(R.id.del_order_btn);
			holder.controlView = (View) dishItemView.findViewById(R.id.order_control_panel);
			holder.dishPriceLabel = (TextView) dishItemView.findViewById(R.id.dish_price_label);
			dishItemView.setTag(holder);
		}
		else
		{
			holder = (ViewHolder) dishItemView.getTag();
		}

		OrderDish dishOrder = getItem(position);
		DishSize dishSize = finalDb.findAllByWhere(DishSize.class, "dishSizeId = " + dishOrder.getDishSizeId()).get(0);
		DishTaste dishTaste = finalDb.findAllByWhere(DishTaste.class, "dishTasteId = " + dishOrder.getDishTasteId()).get(0);
		Dish dish = finalDb.findAllByWhere(Dish.class, "dishId = " + dishOrder.getDishId()).get(0);

		holder.dishImage.setImageResource(R.drawable.huiguorou);
		holder.dishNameLabel.setText(dish.getDishName());
		holder.dishNameLabel2.setText(dish.getDishName());
		holder.dishSizeLabel.setText(dishSize.getSizeName());
		holder.dishTasteLabel.setText(dishTaste.getTaste());
		holder.dishDescLabel.setText(dish.getDetail());
		holder.dishPriceLabel.setText("￥" + dishSize.getNowPrice());
		// 非常坑爹，因为listview的缓存，需要在每次初始化之前把可能改变的每个listitem的值设置为最初的
		holder.controlView.setVisibility(View.GONE);
		dishItemView.setBackgroundColor(Color.parseColor("#EFEFEF"));

		// 捕获ontouch
		dishItemView.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View view, MotionEvent event)
			{
				Log.d("OrderSystem", "----------DishOrdesArrayAdapter order list touched------------");
				switch (event.getAction())
				{
					case MotionEvent.ACTION_DOWN:
						selectedView = view;
						selectedPosition = position;
						break;
					default:
						break;
				}
				return false;
			}
		});

		holder.button.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View arg0, MotionEvent event)
			{
				Log.d("OrderSystem", "----------holder.button order list touched------------");
				switch (event.getAction())
				{
					case MotionEvent.ACTION_DOWN:
						delFlag = true;
						break;
					default:
						break;
				}
				return false;
			}
		});

		return dishItemView;
	}

	static class ViewHolder
	{
		ImageView dishImage;
		TextView dishNameLabel;
		TextView dishNameLabel2;
		TextView dishSizeLabel;
		TextView dishTasteLabel;
		TextView dishDescLabel;
		TextView dishPriceLabel;
		View controlView;
		TextView button;
	}

	public void deleteItem()
	{
		selectedView.startAnimation(animation); // 给view设置动画
		animation.setAnimationListener(new AnimationListener()
		{
			@Override
			public void onAnimationStart(Animation animation)
			{
			}

			@Override
			public void onAnimationRepeat(Animation animation)
			{
			}

			@Override
			public void onAnimationEnd(Animation animation)
			{
				orderListFragment.removeData(selectedPosition);
				//data.remove(selectedPosition); // 动画执行完毕，把数据源里面相应数据删除
				notifyDataSetChanged();
			}
		});
		delFlag = false;
	}

	public boolean getDelFlag()
	{
		return this.delFlag;
	}

	public View getSelectedView()
	{
		return this.selectedView;
	}

	public void showControlPanel(View selectedView, boolean flag)
	{
		ViewHolder viewHolder = (ViewHolder) selectedView.getTag();
		viewHolder.controlView.setVisibility(flag ? View.VISIBLE : View.GONE);
		// viewHolder.visible = flag;
	}
}
