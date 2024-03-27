package com.example.demobtlltnc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.demobtlltnc.adapter.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private ViewPagerAdapter viewPagerAdapter;
    private ViewPager viewPager;
    private BottomNavigationView bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        viewPager = findViewById(R.id.viewPager);
        bottom = findViewById(R.id.bottomNavi);

        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 4);
        viewPager.setAdapter(viewPagerAdapter);

        //chuyen fragment khi nhan doi e
        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.menu_doixe ){
                    viewPager.setCurrentItem(0);
                }else if(item.getItemId() == R.id.menu_taixe){
                    viewPager.setCurrentItem(1);
                }else if(item.getItemId() == R.id.menu_chuyendi){
                    viewPager.setCurrentItem(2);
                }else if(item.getItemId() == R.id.menu_doanhso){
                    viewPager.setCurrentItem(3);
                }
                return true;
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        bottom.getMenu().getItem(0).setChecked(true);
                        break;
                    case 1:
                        bottom.getMenu().getItem(1).setChecked(true);
                        break;
                    case 2:
                        bottom.getMenu().getItem(2).setChecked(true);
                        break;
                    case 3:
                        bottom.getMenu().getItem(3).setChecked(true);
                        break;
                    default:
                        bottom.getMenu().getItem(0).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

}