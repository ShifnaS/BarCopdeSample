package com.example.softex.barcopdesample.helper;

import android.content.Context;
import android.hardware.Camera;

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

public class CameraFlash {
    public Camera camera;
    public boolean isFlashOn;
    private boolean hasFlash;
    Parameters params;
    MediaPlayer mp;
    Context context;

    public CameraFlash( Context context) {
        this.context=context;
    }

    public void hasFlashLight(Context context)
    {
        // First check if device is supporting flashlight or not
        hasFlash = context.getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!hasFlash) {
            // device doesn't support flash
            // Show alert message and close the application
            final AlertDialog alert = new AlertDialog.Builder(context)
                    .create();
            alert.setTitle("Error");
            alert.setMessage("Sorry, your device doesn't support flash light!");
            alert.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // closing the application
                   alert.dismiss();
                }
            });
            alert.show();
            return;
        }

    }

    private void releaseCamera() {

        // stop and release camera
        if (camera != null) {

            camera.release();

            camera = null;

        }

    }

    // Get the camera
    public void getCamera() {
        if (camera == null) {
            try {
                releaseCamera();
                camera = Camera.open();
                params = camera.getParameters();
            } catch (RuntimeException e) {
                Log.e("Camera Error. ", e.getMessage());
            }
        }
        else {
            releaseCamera();
        }
    }
    // Turning On flash
    public void turnOnFlash() {
        if (!isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
           // playSound();

            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_TORCH);
            camera.setParameters(params);
            camera.startPreview();
            isFlashOn = true;

            // changing button/switch image
           // toggleButtonImage();
        }

    }
    // Turning Off flash
    public void turnOffFlash() {
        if (isFlashOn) {
            if (camera == null || params == null) {
                return;
            }
            // play sound
           // playSound();

            params = camera.getParameters();
            params.setFlashMode(Parameters.FLASH_MODE_OFF);
            camera.setParameters(params);
            camera.stopPreview();
            isFlashOn = false;

            // changing button/switch image
          //  toggleButtonImage();
        }
    }
}
