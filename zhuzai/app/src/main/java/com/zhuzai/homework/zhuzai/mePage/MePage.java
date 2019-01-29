package com.zhuzai.homework.zhuzai.mePage;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.media.Image;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zhuzai.homework.zhuzai.BaseFragment;
import com.zhuzai.homework.zhuzai.MainActivity;
import com.zhuzai.homework.zhuzai.R;
import com.zhuzai.homework.zhuzai.bean.Chat;
import com.zhuzai.homework.zhuzai.bean.User_Draw;
import com.zhuzai.homework.zhuzai.messagePage.model.ChatMessage;
import com.zhuzai.homework.zhuzai.utils.NetworkUtils;

import java.lang.annotation.Target;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MePage extends BaseFragment {

    private ImageView imageView;
    private ImageView imageViewHead;
    private ImageView word_cloud_view;
    private ImageView line_view;
    private String my_user_id;

    //    public static MainActivity mainactivity;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.activity_me_page, container, false);
        imageView=view.findViewById(R.id.me_page_bg);
        imageViewHead=view.findViewById(R.id.mypage_headshot);
        word_cloud_view = view.findViewById(R.id.word_cloud);
        line_view = view.findViewById(R.id.line);
        //取得IMEI
//        TelephonyManager TelephonyMgr = (TelephonyManager) this.getContext().getSystemService(TELEPHONY_SERVICE);
//        my_user_id = TelephonyMgr.getDeviceId();
        my_user_id = "865873038279224";
        initView();
        getUserDraw();
        return view;
    }
    private void initView() {
        imageView.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             Log.d(":", "OnClickListener");
                                             //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
                                             AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                             //    设置Title的图标
//                builder.setIcon(R.drawable.ic_launcher);
                                             //    设置Title的内容
                                             builder.setTitle("改变背景");
                                             //    设置Content来显示一个信息
                                             builder.setMessage("确定改变背景吗？");
                                             //    设置一个PositiveButton
                                             builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                                 @Override
                                                 public void onClick(DialogInterface dialog, int which) {
                                                     Log.d(":", "sss确定");
                                                     Resources res=getResources();
                                                     int count=(int)(1+Math.random()*(4-1+1));
                                                     String countString=String.valueOf(count);
                                                     Log.d("countString",countString);
                                                     String imgname = "mypage_bg_a" + countString;
                                                     System.out.print("imgname"+imgname);
                                                     int Res=  res.getIdentifier(imgname,"drawable","com.zhuzai.homework.zhuzai");
                                                     Log.d("int", String.valueOf(Res));
                                                     Log.d("img",imgname);
                                                     imageView.setImageDrawable(getResources().getDrawable(Res));
                                                 }
                                             });
                                             //    设置一个NegativeButton
                                             builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                 @Override
                                                 public void onClick(DialogInterface dialog, int which) {
                                                 }
                                             });
                                             //    显示出该对话框
                                             builder.show();
                                         }
                                     }
        );
        imageViewHead.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {

                                                 AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                                                 //    设置Title的图标
//                builder.setIcon(R.drawable.ic_launcher);
                                                 //    设置Title的内容
                                                 builder1.setTitle("改变头像");
                                                 //    设置Content来显示一个信息
                                                 builder1.setMessage("确定改变头像吗？");
                                                 //    设置一个PositiveButton
                                                 builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                                                     @Override
                                                     public void onClick(DialogInterface dialog, int which) {
                                                         Log.d(":", "sss确定");
                                                         Resources res=getResources();
                                                         int count=(int)(1+Math.random()*(8-1+1));
                                                         String countString=String.valueOf(count);
                                                         Log.d("countString",countString);
                                                         String imgname = "mypage_headshot_a" + countString;
                                                         System.out.print("imgname"+imgname);
                                                         int Res=  res.getIdentifier(imgname,"drawable","com.zhuzai.homework.zhuzai");
                                                         Log.d("int", String.valueOf(Res));
                                                         Log.d("img",imgname);
                                                         imageViewHead.setImageDrawable(getResources().getDrawable(Res));
                                                     }});
                                                 //    设置一个NegativeButton
                                                 builder1.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                     @Override
                                                     public void onClick(DialogInterface dialog, int which) { }});
                                                 //    显示出该对话框
                                                 builder1.show();
                                             }
                                         }
        );
    }
    private void getUserDraw(){
        NetworkUtils.getResponseWithRetrofitAsync_User_Draw(new Callback<User_Draw>() {
            @Override public void onResponse(Call<User_Draw> call, Response<User_Draw> response) {
                //接收到返回值，开始进行处理。
                try{
                    User_Draw user_draw = response.body();
                    String word_cloud_url = user_draw.getWord_cloud_url();
                    String line_url = user_draw.getLine_url();
                    Glide.with(getContext()).load(word_cloud_url).into(word_cloud_view);
                    Glide.with(getContext()).load(line_url).into(line_view);
                } catch (Exception e){
                    Log.d("zsf", "get me error!");
                    e.printStackTrace();
                }

            }

            @Override public void onFailure(Call<User_Draw> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        },  my_user_id);
    }
}