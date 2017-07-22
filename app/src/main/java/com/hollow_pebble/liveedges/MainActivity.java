package com.hollow_pebble.liveedges;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.WindowManager;
import android.widget.Button;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;

    //A Tag to filter the log messages
    private static final String TAG = "MainActivity";

    //A class used to implement the interaction between OpenCV and the device camera.
    private CameraBridgeViewBase mOpenCvCameraView;

    //Button cameraButton;

    //This is the callback object used when we initialize the OpenCV
    //library asynchronously
    private BaseLoaderCallback mLoaderCallback = new
            BaseLoaderCallback(this) {
                @Override
                //This is the callback method called once the OpenCV //manager is connected
                public void onManagerConnected(int status) {
                    switch (status) {
                        //Once the OpenCV manager is successfully connected we can enable the camera interaction with the defined OpenCV camera view
                        case LoaderCallbackInterface.SUCCESS:
                        {
                            Log.i(TAG, "OpenCV loaded successfully");
                            //mOpenCvCameraView.enableView();
                        } break;
                        default:
                        {
                            super.onManagerConnected(status);
                        } break; }
                } };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_main);

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.HelloVisionView);

        //Set the view as visible
        mOpenCvCameraView.setVisibility(SurfaceView.VISIBLE);
        //Register your activity as the callback object to handle //camera frames
        mOpenCvCameraView.setCvCameraViewListener(this);

        checkCameraPermissions();
    }

    private void checkCameraPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
            }
        }
        else {
            //takePhoto();
            mOpenCvCameraView.enableView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Call the async initialization and pass the callback object we
        //created later, and chose which version of OpenCV library to //load.

        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_13, this, mLoaderCallback);
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        //We're returning the colored frame as is to be rendered on
        //thescreen.
        return inputFrame.rgba();
        //return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    Log.e("Permission", "CAMERA GRANTED");
                    //takePhoto();
                    mOpenCvCameraView.enableView();
                }
                break;

            default: break;
        }
    }
}
