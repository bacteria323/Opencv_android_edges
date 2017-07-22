package com.hollow_pebble.liveedges;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;

public class MainActivity extends AppCompatActivity implements CameraBridgeViewBase.CvCameraViewListener2 {

    //A Tag to filter the log messages
    private static final String TAG = "MainActivity";

    //A class used to implement the interaction between OpenCV and the device camera.
    private CameraBridgeViewBase mOpenCvCameraView;

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
                            mOpenCvCameraView.enableView();
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
    }

    @Override
    protected void onResume() {
        super.onResume();

        //Call the async initialization and pass the callback object we
        //created later, and chose which version of OpenCV library to //load.

        OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_11, this, mLoaderCallback);
    }

    @Override
    public void onCameraViewStarted(int width, int height) {

    }

    @Override
    public void onCameraViewStopped() {

    }

    @Override
    public Mat onCameraFrame(CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
        return null;
    }
}
