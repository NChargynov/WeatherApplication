package com.geektech.weatherapplication.ui.onboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.geektech.weatherapplication.R;
import com.geektech.weatherapplication.data.PreferenceHelper;
import com.geektech.weatherapplication.ui.base.BaseActivity;
import com.geektech.weatherapplication.ui.main.MainActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;

import static com.geektech.weatherapplication.R.id.btn_next;
import static com.geektech.weatherapplication.R.id.tab_layout;
import static com.geektech.weatherapplication.R.id.viewPager;

public class OnBoardActivity extends BaseActivity {

    @BindView(viewPager)
    ViewPager pager;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(tab_layout)
    TabLayout tabLayout;
    @BindView(btn_next)
    Button buttonNext;

    private MenuItem menuItem;
    private int currentPage;

    @Override
    public int getViewId() {
        return R.layout.activity_on_board;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        initViewPagerAdapter();
        listeners();
    }

    private void listeners() {
        buttonNext.setOnClickListener(v -> {
            if (pager.getCurrentItem() != 3) {
                pager.setCurrentItem(currentPage + 1);
            } else {
                startActivity(new Intent(this, MainActivity.class));
                PreferenceHelper.setIsShown(true);
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_skip, menu);
        menuItem = menu.findItem(R.id.menu_skip);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_skip:
                startActivity(new Intent(this, MainActivity.class));
                PreferenceHelper.setIsShown(true);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<OnBoardItem> getItems() {
        ArrayList<OnBoardItem> onBoardItems = new ArrayList<>();
        onBoardItems.add(new OnBoardItem(getString(R.string.titleOne), R.drawable.girl));
        onBoardItems.add(new OnBoardItem(getString(R.string.tilteTwo), R.drawable.update));
        onBoardItems.add(new OnBoardItem(getString(R.string.titleThree), R.drawable.delete));
        onBoardItems.add(new OnBoardItem(getString(R.string.titleFour), R.drawable.thankyou));
        return onBoardItems;
    }

    private void initViewPagerAdapter() {
        OnBoardAdapter adapter = new OnBoardAdapter();
        pager.setAdapter(adapter);
        adapter.update(getItems());
        init();
        tabLayout.setupWithViewPager(pager, true);
    }

    private void init() {
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                if (position == 3) {
                    buttonNext.setText(getString(R.string.start));
                    menuItem.setTitle(getString(R.string.readt));
                } else {
                    buttonNext.setText(getString(R.string.next));
                    menuItem.setTitle(getString(R.string.skip));
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
