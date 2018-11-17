package com.example.softex.barcopdesample.activity;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.example.softex.barcopdesample.R;
import com.example.softex.barcopdesample.dataClass.StudentRepo;
import com.example.softex.barcopdesample.helper.CameraFlash;
import com.example.softex.barcopdesample.helper.SharedPrefManager;
import com.journeyapps.barcodescanner.CameraPreview;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class WaitingActivity extends AppCompatActivity {
    StudentRepo repo;
    String delay="2000";
   // CameraFlash cam;
   // private Camera mCamera;
   // private CameraPreview mPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);
        repo=new StudentRepo(getApplicationContext());
        delay=repo.getdelay();
       // cam=new CameraFlash(getApplicationContext());

         //   cam.getCamera();
         //   cam.turnOnFlash();


        Intent i=getIntent();
      //  int m=i.getIntExtra("flashh",0);
       // Toast.makeText(this, "flashh "+m, Toast.LENGTH_SHORT).show();
        SharedPrefManager.getInstance(getApplicationContext()).setFlash("stop");
        SharedPrefManager.getInstance(getApplicationContext()).setScan("notscan");





        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            public void run() {


                finish();
            }
        };
        timer.schedule(task, Long.parseLong(delay));
        //3000
    }

  /*  private void releaseCamera() {

        // stop and release camera
        if (mCamera != null) {

            mCamera.release();

            mCamera = null;

        }

    }*/






}
