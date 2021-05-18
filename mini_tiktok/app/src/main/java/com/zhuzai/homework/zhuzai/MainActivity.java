package com.mini_tiktok.homework.mini_tiktok;

import android.Manifest;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mini_tiktok.homework.mini_tiktok.homePage.HomePage;
import com.mini_tiktok.homework.mini_tiktok.mePage.MePage;
import com.mini_tiktok.homework.mini_tiktok.messagePage.MessagePage;
import com.mini_tiktok.homework.mini_tiktok.recommendPage.RecommendPage;

import com.mini_tiktok.homework.mini_tiktok.recordsPage.CustomCameraActivity;
import com.mini_tiktok.homework.mini_tiktok.recordsPage.FaceDetect_MainActivity;

import com.mini_tiktok.homework.mini_tiktok.recordsPage.FaceDetect_MainActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.HEAD;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    private ViewPager viewPager;
    private TextView firstPage;
    private TextView about;
    private TextView recommend;
    private ImageView takePic;
    private TextView massage;
    private Button video;
    private List<Fragment> list;
    private FragmentPagerAdapter myPagerAdpter;
    private double exitTime = 0.1;
    private Bundle bundle;
    private GestureDetector gd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bundle = this.getIntent().getExtras();
        gd = new GestureDetector(this,new OnDoubleClick());
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);


        initView();
        initEvent();
        setSelected(0);
    }

    private void initView(){
        viewPager = findViewById(R.id.view_pager);
        firstPage = findViewById(R.id.tab_firstpage);
        about = findViewById(R.id.tab_aboutme);
        recommend = findViewById(R.id.tab_recommend);
        massage = findViewById(R.id.tab_massage);
        takePic = findViewById(R.id.tab_video);
        //四个页面用Fragment进行传递
        Fragment homePageFragment = new HomePage();
        Fragment recommendPageFragment = new RecommendPage();
        Fragment messagePageFragment = new MessagePage();
        Fragment mePageFragment = new MePage();
        list = new ArrayList<>();
        list.add(homePageFragment);
        list.add(recommendPageFragment);
        list.add(messagePageFragment);
        list.add(mePageFragment);

        myPagerAdpter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return list.size();
            }

            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }
        };
        viewPager.setAdapter(myPagerAdpter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

//        if(bundle.getInt("page") == 3)
//            viewPager.setCurrentItem(bundle.getInt("page"),false);
    }

    private void initEvent()
    {
        firstPage.setOnClickListener(this);
        recommend.setOnClickListener(this);
        about.setOnClickListener(this);
        massage.setOnClickListener(this);
        takePic.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        initTab();
        switch (view.getId()){
            case R.id.tab_firstpage:
                firstPage.setTextColor(getResources().getColor(R.color.tab_press));
                viewPager.setCurrentItem(0,false);
                break;
            case R.id.tab_recommend:
                recommend.setTextColor(getResources().getColor(R.color.tab_press));
                viewPager.setCurrentItem(1,false);
                break;
            case R.id.tab_video:
                Intent it = new Intent(this, FaceDetect_MainActivity.class);
                startActivity(it);
                break;
            case R.id.tab_massage:
                massage.setTextColor(getResources().getColor(R.color.tab_press));
                viewPager.setCurrentItem(2,false);
                break;
            case R.id.tab_aboutme:
                about.setTextColor(getResources().getColor(R.color.tab_press));
                viewPager.setCurrentItem(3,false);
                break;
            default:break;
        }
    }
    private  void initTab(){
        firstPage.setTextColor(getResources().getColor(R.color.tab_unpress));
        recommend.setTextColor(getResources().getColor(R.color.tab_unpress));
        massage.setTextColor(getResources().getColor(R.color.tab_unpress));
        about.setTextColor(getResources().getColor(R.color.tab_unpress));
    }
    private void setSelected(int i){
        viewPager.setCurrentItem(i,false);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction () == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime)>2000) {

                Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }
            else {
                finish();
                System.exit(0);
            }
        }
        return super.onKeyDown(keyCode,event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("touch_phore","touch_phore");
        return gd.onTouchEvent(event);
    }
}
