package com.tiancaijiazu.app.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.tiancaijiazu.app.R;
import com.tiancaijiazu.app.activitys.shopping_activity.ShoppingCarActivity;
import com.tiancaijiazu.app.beans.Shopping_carBean;
import com.tiancaijiazu.app.fragments.views.NiceImageView;

import java.math.BigDecimal;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019/5/17/017.
 */

public class RlvAdapter_shop_car extends RecyclerView.Adapter {

    private TextView mZongShu;
    private TextView mMone;
    public List<Shopping_carBean.ResultBean> mData;
    private ShoppingCarActivity mContext;
    private int sum = 1;
    private setOnClickLisiter mLisiter;
    private onClickLisiterJia mLisiterJia;
    private onClickLisiterJian mLisiterJian;
//    private onClickLisiterKong mLisiterKong;
    private boolean isbo;
    private onClickLisiterDelete mLisiterDelete;

    public RlvAdapter_shop_car(ShoppingCarActivity shoppingCarActivity, List<Shopping_carBean.ResultBean> resultBeans, TextView money, TextView zongSum) {
        this.mContext = shoppingCarActivity;
        this.mData = resultBeans;
        this.mMone = money;
        this.mZongShu = zongSum;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shop_car_item, viewGroup, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        //viewHolder.setIsRecyclable(false);
        final ViewHolder holder = (ViewHolder) viewHolder;
        holder.mMoney.setText(mData.get(i).getOldPrice() + "");
        holder.mCheckbox.setChecked(isbo);
        Log.i("yx", "onBindViewHolder: " + mData.get(i));
        holder.mTitleName.setText(mData.get(i).getProductName());
        holder.mQianName.setText(mData.get(i).getProductName() + "/" + mData.get(i).getSpecName());
        holder.mSum.setText(mData.get(i).getQuantity() + "");
        Glide.with(mContext).load(mData.get(i).getPicUri()).into(holder.mImage);
//        holder.mSum.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int i1, int i2, int i3) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//                boolean checked = holder.mCheckbox.isChecked();
//                if (checked) {
//                    String text = String.valueOf(holder.mSum.getText());
//                    if (!text.equals("")) {
//                        int i1 = Integer.parseInt(text);
//                        String text1= String.valueOf(holder.mMoney.getText());
//                        double i2 = Double.parseDouble(text1);
//                        mMone.setText((int) (i1*i2)+"");
//                        mData.get(i).setQuantity(sum);
//                        Log.i("MyPi4", "onTextChanged: "+i1);
//                        mLisiterKong.onClickerKong(i, mData, i1);
//                        mLisiter.onClickGx(holder.mSum, i, mData, checked, i1);
//                    }
//                }
//            }
//        });

//        holder.mSum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                boolean hasFocus = true;
//                if (hasFocus) {
//                    holder.mSum.requestFocus(); //获取焦点,光标出现
//                    holder.mSum.setFocusableInTouchMode(true);
//                    holder.mSum.setFocusable(true);
//                    holder.mSum.setCursorVisible(true);
//                } else {
//                    holder.mSum.clearFocus();
//                    holder.mSum.setCursorVisible(false);
//                }
//            }
//        });



        holder.mJia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = String.valueOf(holder.mSum.getText());
                int i1 = Integer.parseInt(text);
                String text1 = String.valueOf(mMone.getText());
                double i2 = Float.parseFloat(text1);
                i2 =  (i2 - mData.get(i).getOldPrice() * i1);
                sum = i1 + 1;
                holder.mSum.setText("" + sum);
                boolean checked = holder.mCheckbox.isChecked();
                if (checked) {
                    Log.i("suan", i2+"onClick: "+mData.get(i).getOldPrice()+"---"+sum);
                    i2 = (i2+mData.get(i).getOldPrice() * sum);
                    Log.i("suan", "onClick: "+i2);
                    double round = round(i2, 2);
                    mMone.setText(round + "");
                }
                mData.get(i).setQuantity(sum);
                if (mLisiterJia != null) {
                    mLisiterJia.onClickerJia(v, i, mData, sum);
                }
            }
        });
        holder.mJian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = String.valueOf(holder.mSum.getText());
                int i1 = Integer.parseInt(text);
                String text1 = String.valueOf(mMone.getText());
                double i2 = Float.parseFloat(text1);
                i2 =  (i2 - mData.get(i).getOldPrice() * i1);
                if (i1 > 1) {
                    sum = i1 - 1;
                    boolean checked = holder.mCheckbox.isChecked();
                    if (checked) {
                        i2 =  (i2 + mData.get(i).getOldPrice() * sum);
                        double round = round(i2, 2);

                        mMone.setText(round + "");
                    }
                } else {
                    Toast.makeText(mContext, "该宝贝不能减少了哟～", Toast.LENGTH_SHORT).show();
                }
                holder.mSum.setText("" + sum);
                mData.get(i).setQuantity(sum);
                if (mLisiterJian != null) {
                    mLisiterJian.onClickerJian(v, i, mData, sum);
                }
            }
        });
        holder.mCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = holder.mCheckbox.isChecked();

                if (mLisiter != null) {
                    String text = String.valueOf(holder.mSum.getText());
                    if (text.equals("")) {
                        Toast.makeText(mContext, "商品数量不能为空!", Toast.LENGTH_SHORT).show();
                        holder.mCheckbox.setChecked(false);
                    }else {
                        int i1 = Integer.parseInt(text);
                        if (i1 < 1) {
                            Toast.makeText(mContext, "商品数量不能为0!", Toast.LENGTH_SHORT).show();
                            holder.mCheckbox.setChecked(false);
                        }else {
                            mLisiter.onClickGx(v, i, mData, isChecked, i1);
                        }
                    }
                }
            }
        });
        holder.mGuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = holder.mCheckbox.isChecked();
                if (checked) {
                    String text = String.valueOf(holder.mSum.getText());
                    int i1 = Integer.parseInt(text);
                    String text1 = String.valueOf(mMone.getText());
                    double i2 = Float.parseFloat(text1);
                    i2 =  (i2 - i1 * mData.get(i).getOldPrice());
                    mMone.setText(i2 + "");
                }
                if (mLisiterDelete != null) {
                    mLisiterDelete.onClickerDelete(v, i, mData, checked);
                }
                mData.remove(i);
                notifyItemRemoved(i);
                notifyItemRangeChanged(i, mData.size() - i);
                mZongShu.setText("共 " + mData.size() + " 件商品");
            }
        });

    }

    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal ne = new BigDecimal("1");
        return b.divide(ne, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void addData(List<Shopping_carBean.ResultBean> result2) {
        if (mData != null) {
            mData.clear();
        }

        mData.addAll(result2);
        notifyDataSetChanged();
    }

    public void addIsBo(boolean b) {
        this.isbo = b;
        notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.checkbox)
        CheckBox mCheckbox;
        @BindView(R.id.image)
        NiceImageView mImage;
        @BindView(R.id.title_name)
        TextView mTitleName;
        @BindView(R.id.qian_name)
        TextView mQianName;
        @BindView(R.id.fuhao)
        TextView mFuhao;
        @BindView(R.id.money)
        TextView mMoney;
        @BindView(R.id.delete)
        TextView mDelete;
        @BindView(R.id.jian)
        ImageView mJian;
        @BindView(R.id.sum)
        EditText mSum;
        @BindView(R.id.jia)
        ImageView mJia;
        @BindView(R.id.guan)
        ImageView mGuan;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            mDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.delete:
                    boolean checked = mCheckbox.isChecked();
                    int position = getAdapterPosition();
                    if (checked) {
                        double integer = mData.get(position).getOldPrice();
                        String text = String.valueOf(mSum.getText());
                        int i1 = Integer.parseInt(text);
                        String text1 = String.valueOf(mMone.getText());
                        double i2 = Double.parseDouble(text1);
                        Log.i("yx", i2 + "onClick: " + i1 + "==" + integer);
                        i2 = i2 - i1 * integer;
                        Log.i("yx", "onClick: " + i2);
                        mMone.setText(i2 + "");
                    }
                    if (mLisiterDelete != null) {
                        mLisiterDelete.onClickerDelete(v, position, mData, checked);
                    }
                    mData.remove(position);
                    notifyItemRemoved(position);
                    mZongShu.setText("共 " + mData.size() + " 件商品");
                    break;
            }
        }
    }

    public interface setOnClickLisiter {
        void onClickGx(View view, int position, List<Shopping_carBean.ResultBean> mData, boolean isbo, int sum);
    }

    public void OnsetOnClickLisiter(setOnClickLisiter lisiter) {
        this.mLisiter = lisiter;
    }


    public interface onClickLisiterJia {
        void onClickerJia(View view, int position, List<Shopping_carBean.ResultBean> mData, int sum);
    }

    public void setOnClickLisiterJia(onClickLisiterJia lisiterJia) {
        this.mLisiterJia = lisiterJia;
    }

    public interface onClickLisiterJian {
        void onClickerJian(View view, int position, List<Shopping_carBean.ResultBean> mData, int sum);
    }

    public void setOnClickLisiterJian(onClickLisiterJian lisiterJian) {
        this.mLisiterJian = lisiterJian;
    }

    public interface onClickLisiterDelete {
        void onClickerDelete(View view, int position, List<Shopping_carBean.ResultBean> mData, boolean isbo);
    }

    public void setOnClickLisiterDelete(onClickLisiterDelete lisiterDelete) {
        this.mLisiterDelete = lisiterDelete;
    }
//    public interface onClickLisiterKong {
//        void onClickerKong(int position, List<Shopping_carBean.ResultBean> mData, int sum);
//    }
//
//    public void setOnClickLisiterKong(onClickLisiterKong lisiterKong) {
//        this.mLisiterKong = lisiterKong;
//    }
}
