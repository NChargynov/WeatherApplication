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

import static com.geektech.weatherapplication.R.id.viewPager;

public class OnBoardActivity extends BaseActivity {

    private ViewPager pager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private Button buttonNext;
    private MenuItem menuSkip;
    private int currentPage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);
        setUpId();
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        initViewPagerAdapter();
        listeners();
    }

    private void setUpId() {
        pager = findViewById(viewPager);
        toolbar = findViewById(R.id.toolbar);
        tabLayout = findViewById(R.id.tab_layout);
        buttonNext = findViewById(R.id.btn_next);
        menuSkip = findViewById(R.id.menu_skip);
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
        onBoardItems.add(new OnBoardItem("В данном приложении вы можете учиться))", R.drawable.girl));
        onBoardItems.add(new OnBoardItem("В данном приложении вы можете обновлять))", R.drawable.update));
        onBoardItems.add(new OnBoardItem("В данном приложении вы можете удалять))", R.drawable.delete));
        onBoardItems.add(new OnBoardItem("Спсибо что вы с нами", R.drawable.thankyou));
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
                    buttonNext.setText("Начать");
                } else {
                    buttonNext.setText("Далее");
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
