package com.zhuzai.homework.zhuzai.recordsPage;

import android.Manifest;
import android.content.Intent;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.zhuzai.homework.zhuzai.R;
import com.zhuzai.homework.zhuzai.bean.PostVideoResponse;
import com.zhuzai.homework.zhuzai.network.IMiniDouyinService;
import com.zhuzai.homework.zhuzai.utils.ResourceUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.zhuzai.homework.zhuzai.recordsPage.utils.Utils.MEDIA_TYPE_IMAGE;
import static com.zhuzai.homework.zhuzai.recordsPage.utils.Utils.MEDIA_TYPE_VIDEO;
import static com.zhuzai.homework.zhuzai.recordsPage.utils.Utils.getOutputMediaFile;

public class CustomCameraActivity extends AppCompatActivity implements SurfaceHolder.Callback {

    private SurfaceView mSurfaceView;
    private Camera mCamera;

    private int CAMERA_TYPE = Camera.CameraInfo.CAMERA_FACING_BACK;

    private boolean isRecording = false;

    private int rotationDegree = 0;

    private SurfaceHolder surfaceHolder;

    private Button button;

    private Uri mSelectedImage;
    private Uri mSelectedVideo;
    private String takeVideo;
    private ProgressBar progressBar;
    Timer timer = new Timer();
    private int now=0;
    private TextView process;
    private int light=0;
    private Button light_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_record_start_record);
        ActivityCompat.requestPermissions(CustomCameraActivity.this, new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        mSurfaceView = findViewById(R.id.img);
        mCamera = getCamera(CAMERA_TYPE);
        progressBar = findViewById(R.id.progressBar);
        process = findViewById(R.id.process_time);
        light_btn = findViewById(R.id.btn_picture);

        //todo 给SurfaceHolder添加Callback
        surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.addCallback(this);

        findViewById(R.id.btn_picture).setOnClickListener(v -> {


            if(light==0){
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
                mCamera.setParameters(parameters);
                light=1;
                light_btn.setText("UNLIGHT");
            }
            else{
                light=0;
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
                mCamera.setParameters(parameters);
                light_btn.setText("LIGHT");

            }
        });

        button = findViewById(R.id.btn_record);
        findViewById(R.id.btn_record).setOnClickListener(v -> {
            //todo 录制，第一次点击是start，第二次点击是stop
            if (isRecording) {
                //todo 停止录制
                timer.cancel();
                now=0;
                stop();
            } else {
                //todo 录制
                isRecording=true;
                mMediaRecorder = new MediaRecorder();
                mCamera.unlock();
                mMediaRecorder.setCamera(mCamera);

                mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);
                mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
                mMediaRecorder.setProfile(CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH));

                takeVideo  = getOutputMediaFile(MEDIA_TYPE_VIDEO).toString();
                mMediaRecorder.setOutputFile(takeVideo);
                mMediaRecorder.setPreviewDisplay(mSurfaceView.getHolder().getSurface());
                mMediaRecorder.setOrientationHint(rotationDegree);
                button.setText("STOP");
                try {
                    mMediaRecorder.prepare();
                    mMediaRecorder.start();
                    timer.schedule(task,0,1000);
                } catch (IOException e) {
                    releaseMediaRecorder();
                    e.printStackTrace();
                }
            }
        });

        findViewById(R.id.btn_facing).setOnClickListener(v -> {
            //todo 切换前后摄像头
            if(CAMERA_TYPE==Camera.CameraInfo.CAMERA_FACING_BACK) {
                releaseCameraAndPreview();
                CAMERA_TYPE=Camera.CameraInfo.CAMERA_FACING_FRONT;
                mCamera = getCamera(CAMERA_TYPE);
                mCamera.stopPreview();
                startPreview(surfaceHolder);

            }
            else{
                releaseCameraAndPreview();
                CAMERA_TYPE=Camera.CameraInfo.CAMERA_FACING_BACK;
                mCamera = getCamera(CAMERA_TYPE);
                mCamera.stopPreview();
                startPreview(surfaceHolder);
            }

        });

        findViewById(R.id.btn_zoom).setOnClickListener(v -> {
            //todo 调焦，需要判断手机是否支持
            Camera.Parameters p = mCamera.getParameters();

            p.setZoom(5);
            mCamera.setParameters(p);
        });
    }

    TimerTask task = new TimerTask() {
        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    now++;
                    progressBar.setProgress(now);
                    if(now==10)
                    {
                        timer.cancel();
                        now=0;
                        stop();
                    }
                    process.setText(String.valueOf(now));
                }
            });
        }
    };

    private  void stop()
    {
        isRecording = false;
        mMediaRecorder.stop();
        mMediaRecorder.reset();
        mMediaRecorder.release();
        mMediaRecorder=null;
        System.out.println("finished");
        button.setText("RECORD");
        mCamera.lock();
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && null != data) {

            if (requestCode == 1) {
                mSelectedImage = data.getData();
                Log.d("URL_image",mSelectedImage.toString());
                mSelectedVideo = Uri.parse(takeVideo);
                Log.d("takevideo",takeVideo);
                Log.d("URL_video",mSelectedVideo.toString());

                if(mSelectedImage!=null)
                {
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://10.108.10.39:8080/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();

                    retrofit.create(IMiniDouyinService.class).createVideo("1121041953","haochengya",getMultipartFromUri("cover_image",mSelectedImage),getMultipartFromUri1("video",takeVideo)).
                            enqueue(new Callback<PostVideoResponse>() {
                                @Override public void onResponse(Call<PostVideoResponse> call, Response<PostVideoResponse> response) {
                                    if(response.body().isSuccess())
                                    {
                                        Log.d("success()", "onResponse() called with: call = [" + call + "], response = [" + response + "]");
                                    }
                                }

                                @Override public void onFailure(Call<PostVideoResponse> call, Throwable t) {
                                    Log.d("fail", "onFailure() called with: call = [" + call + "], t = [" + t + "]");
                                    t.printStackTrace();
                                }
                            });
                }

            }
        }
    }
    private MultipartBody.Part getMultipartFromUri(String name, Uri uri) {
        // if NullPointerException thrown, try to allow storage permission in system settings
        File f = new File(ResourceUtils.getRealPath(CustomCameraActivity.this, uri));
        Log.d("f.name",f.toString());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
        return MultipartBody.Part.createFormData(name, f.getName(), requestFile);
    }
    private MultipartBody.Part getMultipartFromUri1(String name, String uri) {
        // if NullPointerException thrown, try to allow storage permission in system settings
        File f = new File(uri);
        Log.d("f.name",f.toString());
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), f);
        return MultipartBody.Part.createFormData(name, f.getName(), requestFile);
    }

    public Camera getCamera(int position) {
        CAMERA_TYPE = position;
        if (mCamera != null) {
            releaseCameraAndPreview();
        }
        Camera cam = Camera.open(position);
        //todo 摄像头添加属性，例是否自动对焦，设置旋转方向等
        rotationDegree = getCameraDisplayOrientation(CAMERA_TYPE);
        cam.setDisplayOrientation(rotationDegree);
        Camera.Parameters parameters=cam.getParameters();
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
        return cam;
    }


    private static final int DEGREE_90 = 90;
    private static final int DEGREE_180 = 180;
    private static final int DEGREE_270 = 270;
    private static final int DEGREE_360 = 360;

    private int getCameraDisplayOrientation(int cameraId) {
        android.hardware.Camera.CameraInfo info =
                new android.hardware.Camera.CameraInfo();
        android.hardware.Camera.getCameraInfo(cameraId, info);
        int rotation = getWindowManager().getDefaultDisplay()
                .getRotation();
        int degrees = 0;
        switch (rotation) {
            case Surface.ROTATION_0:
                degrees = 0;
                break;
            case Surface.ROTATION_90:
                degrees = DEGREE_90;
                break;
            case Surface.ROTATION_180:
                degrees = DEGREE_180;
                break;
            case Surface.ROTATION_270:
                degrees = DEGREE_270;
                break;
            default:
                break;
        }

        int result;
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
            result = (info.orientation + degrees) % DEGREE_360;
            result = (DEGREE_360 - result) % DEGREE_360;  // compensate the mirror
        } else {  // back-facing
            result = (info.orientation - degrees + DEGREE_360) % DEGREE_360;
        }
        return result;
    }


    private void releaseCameraAndPreview() {
        //todo 释放camera资源
        if(mCamera!=null)
        {
            mCamera.stopPreview();
            mCamera.release();
            mCamera = null;
        }

    }

    Camera.Size size;

    private void startPreview(SurfaceHolder holder) {
        //todo 开始预览
        try {
            mCamera.setPreviewDisplay(holder);
            Camera.Parameters parameters = mCamera.getParameters();
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                //竖屏拍照时，需要设置旋转90度，否者看到的相机预览方向和界面方向不相同
                mCamera.setDisplayOrientation(90);
                parameters.setRotation(90);
            } else {
                mCamera.setDisplayOrientation(0);
                parameters.setRotation(0);
            }
            mCamera.setParameters(parameters);
            mCamera.startPreview();
        } catch (Exception e) {
        }
    }


    private MediaRecorder mMediaRecorder;

    private boolean prepareVideoRecorder() {
        //todo 准备MediaRecorder
        mMediaRecorder = new MediaRecorder();
        if(mMediaRecorder!=null)
            return true;
        else
            return false;
    }


    private void releaseMediaRecorder() {
        //todo 释放MediaRecorder
//        releaseMediaRecorder();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startPreview(holder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //todo 释放Camera和MediaRecorder资源
        releaseCameraAndPreview();
        releaseMediaRecorder();
    }


    private Camera.PictureCallback mPicture = (data, camera) -> {
        File pictureFile = getOutputMediaFile(MEDIA_TYPE_IMAGE);
        if (pictureFile == null) {
            return;
        }
        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            fos.write(data);
            fos.close();
        } catch (IOException e) {
            Log.d("mPicture", "Error accessing file: " + e.getMessage());
        }

        mCamera.startPreview();
    };


    private Camera.Size getOptimalPreviewSize(List<Camera.Size> sizes, int w, int h) {
        final double ASPECT_TOLERANCE = 0.1;
        double targetRatio = (double) h / w;

        if (sizes == null) return null;

        Camera.Size optimalSize = null;
        double minDiff = Double.MAX_VALUE;

        int targetHeight = Math.min(w, h);

        for (Camera.Size size : sizes) {
            double ratio = (double) size.width / size.height;
            if (Math.abs(ratio - targetRatio) > ASPECT_TOLERANCE) continue;
            if (Math.abs(size.height - targetHeight) < minDiff) {
                optimalSize = size;
                minDiff = Math.abs(size.height - targetHeight);
            }
        }

        if (optimalSize == null) {
            minDiff = Double.MAX_VALUE;
            for (Camera.Size size : sizes) {
                if (Math.abs(size.height - targetHeight) < minDiff) {
                    optimalSize = size;
                    minDiff = Math.abs(size.height - targetHeight);
                }
            }
        }
        return optimalSize;
    }
    class ThreadShow implements Runnable {

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
                Camera.Parameters parameters = mCamera.getParameters();
                parameters.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
                mCamera.setParameters(parameters);
                mCamera.takePicture(null,null,mPicture);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

}
