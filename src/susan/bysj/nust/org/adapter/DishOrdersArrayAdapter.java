package susan.bysj.nust.org.adapter;

import java.util.List;

import net.tsz.afinal.FinalDb;
import susan.bysj.nust.org.R;
import susan.bysj.nust.org.bean.Dish;
import susan.bysj.nust.org.bean.DishSize;
import susan.bysj.nust.org.bean.DishTaste;
import susan.bysj.nust.org.bean.OrderDish;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DishOrdersArrayAdapter extends ArrayAdapter<OrderDish>
{
	private int resourceId;
	private FinalDb finalDb;

	private float downX; // 点下时候获取的x坐标
	private float upX; // 手指离开时候的x坐标
	private Button button; // 用于执行删除的button

	private Animation animation; // 删除时候的动画
	private View view;

	private List<OrderDish> data;

	public DishOrdersArrayAdapter(Context context, int textViewResourceId, List<OrderDish> objects, FinalDb finalDb)
	{
		super(context, textViewResourceId, objects);
		this.resourceId = textViewResourceId;
		this.finalDb = finalDb;

		data = objects;
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
			holder.dishSizeLabel = (TextView) dishItemView.findViewById(R.id.dish_size_label);
			holder.dishTasteLabel = (TextView) dishItemView.findViewById(R.id.dish_taste_label);
			holder.dishDescLabel = (TextView) dishItemView.findViewById(R.id.dish_desc_label);
			holder.button = (Button) dishItemView.findViewById(R.id.del_order_btn);
			holder.view = (View) dishItemView.findViewById(R.id.order_control_panel);
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
		holder.dishSizeLabel.setText(dishSize.getSizeName());
		holder.dishTasteLabel.setText(dishTaste.getTaste());
		holder.dishDescLabel.setText(dish.getDetail());

		dishItemView.setOnTouchListener(new OnTouchListener()
		{
			@Override
			public boolean onTouch(View v, MotionEvent event)
			{
				final ViewHolder holder = (ViewHolder) v.getTag(); //
				switch (event.getAction())
				{
					case MotionEvent.ACTION_DOWN: // 手指按下
						downX = event.getX(); // 获取手指x坐标
//						if (button != null)
//						{
//							button.setVisibility(View.GONE); // 影藏显示出来的button
//						}
						break;
					case MotionEvent.ACTION_UP: // 手指离开
						upX = event.getX(); // 获取x坐标值
						break;
				}

//				if (holder.button != null)
//				{
//					if (Math.abs(downX - upX) > 35)
//					{ // 2次坐标的绝对值如果大于35，就认为是左右滑动
//						holder.button.setVisibility(View.VISIBLE); // 显示删除button
//						button = holder.button; // 赋值给全局button，一会儿用
//						view = v; // 得到itemview，在上面加动画
//						return true; // 终止事件
//					}
//					return false; // 释放事件，使onitemClick可以执行
//				}
				
				if (holder.view != null)
				{
					if (Math.abs(downX - upX) > 35)
					{ 
//						// 2次坐标的绝对值如果大于35，就认为是左右滑动
//						holder.button.setVisibility(View.VISIBLE); // 显示删除button
//						button = holder.button; // 赋值给全局button，一会儿用
//						view = v; // 得到itemview，在上面加动画
						
						holder.view.setVisibility(View.VISIBLE);
						return true; // 终止事件
					}
					else if(Math.abs(upX - downX) > 35)
					{
						holder.view.setVisibility(View.GONE);
						return true; // 终止事件
					}
					return false; // 释放事件，使onitemClick可以执行
				}
				return false;
			}
		});

		holder.button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				if (button != null)
				{
					button.setVisibility(View.GONE); // 点击删除按钮后，影藏按钮
					deleteItem(view, position); // 删除数据，加动画
				}
			}
		});

		return dishItemView;
	}

	public void deleteItem(View view, final int position)
	{
		view.startAnimation(animation); // 给view设置动画
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
			{ // 动画执行完毕，把数据源里面相应数据删除
				data.remove(position);
				notifyDataSetChanged();
			}
		});
	}

	static class ViewHolder
	{
		ImageView dishImage;
		TextView dishNameLabel;
		TextView dishSizeLabel;
		TextView dishTasteLabel;
		TextView dishDescLabel;
		
		View view;
		Button button;
	}
}
