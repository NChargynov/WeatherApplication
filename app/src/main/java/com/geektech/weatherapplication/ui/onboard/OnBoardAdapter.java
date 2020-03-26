package com.geektech.weatherapplication.ui.onboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.geektech.weatherapplication.R;

import java.util.ArrayList;

public class OnBoardAdapter extends PagerAdapter {

    ArrayList<OnBoardItem> onBoardItems;

    public OnBoardAdapter() {
        onBoardItems = new ArrayList<>();
    }

    public void update(ArrayList<OnBoardItem> onBoardItems){
        this.onBoardItems = onBoardItems;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return onBoardItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        
        TextView textOnBoard;
        ImageView image;

        LayoutInflater inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView =inflater.inflate(R.layout.item_onboard, container, false);

        textOnBoard = itemView.findViewById(R.id.text_onBoard);
        image = itemView.findViewById(R.id.img_onboard);

        textOnBoard.setText(onBoardItems.get(position).getTitle());
        image.setImageResource(onBoardItems.get(position).getImage());

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
