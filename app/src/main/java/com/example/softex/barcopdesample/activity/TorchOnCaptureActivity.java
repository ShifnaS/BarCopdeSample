package com.example.softex.barcopdesample.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.softex.barcopdesample.R;
import com.example.softex.barcopdesample.helper.CameraFlash;
import com.example.softex.barcopdesample.helper.SharedPrefManager;
import com.example.softex.barcopdesample.helper.appConstants;
import com.journeyapps.barcodescanner.CaptureManager;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.text.SimpleDateFormat;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by RajinderPal on 1/29/2017.
 */

public class TorchOnCaptureActivity extends Activity {
    private CaptureManager capture;
    private DecoratedBarcodeView barcodeScannerView;
    private View turnflashOn, turnflashOff;
    private boolean cameraFlashOn = false;
    Button bt_finish;
    SimpleDateFormat sdf,sdf1,sdf2;
    String mmm="";
    SharedPreferences sp,sp1;
    SharedPreferences.Editor ed;
    TextView tv_count,tv_ref;
    boolean f=true;
    int finish=0;
    int count=0;
    int m=0;
    CameraFlash cam;
    String flash="";
     int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        barcodeScannerView = initializeContent();
        TorchEventListener torchEventListener = new TorchEventListener(this);
        barcodeScannerView.setTorchListener(torchEventListener);
        bt_finish= (Button) findViewById(R.id.finish);
        tv_count=(TextView)findViewById(R.id.count);
        tv_ref=(TextView)findViewById(R.id.ref);
        sdf1 = new SimpleDateFormat("HH:mm:ss a");
        sdf = new SimpleDateFormat("ddMMyyyyhhmmss");
        sdf2 = new SimpleDateFormat("dd-MM-yyyy");
        sp= PreferenceManager.getDefaultSharedPreferences(this);
        sp1= PreferenceManager.getDefaultSharedPreferences(this);
        ed=sp.edit();
        barcodeScannerView.setTorchOff();
        count=SharedPrefManager.getInstance(getApplicationContext()).getCountt();
        tv_count.setText("COUNT: "+count);
        String refNo=SharedPrefManager.getInstance(getApplicationContext()).getRef();
        tv_ref.setText("REF NO: "+refNo);

            //releaseCamera();
            capture = new CaptureManager(this, barcodeScannerView);
            capture.initializeFromIntent(getIntent(), savedInstanceState);
            capture.decode();


        String scan=SharedPrefManager.getInstance(getApplicationContext()).getScan();


        try {

            if (scan .equals("empty"))
            {
                Toast.makeText(this, "null "+scan, Toast.LENGTH_SHORT).show();
                barcodeScannerView.setTorchOff();
                flag=1;
            }
            else
            {
                Toast.makeText(this, "not null "+scan, Toast.LENGTH_SHORT).show();
                barcodeScannerView.setTorchOn();
                flag=2;
            }
        }
        catch (Exception e)
        {

        }

        if(flag==2)
        {
            SharedPrefManager.getInstance(getApplicationContext()).setScan("empty");

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    barcodeScannerView.setTorchOff();
                    Intent i=new Intent(getApplicationContext(),WaitingActivity.class);
                    startActivity(i);
                }
            }, 1000);


        }


        bt_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(getApplicationContext()).setCount(0);
                // SharedPrefManager.getInstance(getApplicationContext()).setRef("");
                SharedPrefManager.getInstance(getApplicationContext()).setScan("empty");
                flag=0;
                Intent i=new Intent(TorchOnCaptureActivity.this,MainActivity.class);
                startActivity(i);
                finish();

            }
        });



    }


    protected DecoratedBarcodeView initializeContent() {
        setContentView(R.layout.capture_flash);
        //setContentView(com.google.zxing.client.android.R.layout.zxing_capture);
        return (DecoratedBarcodeView)findViewById(com.google.zxing.client.android.R.id.zxing_barcode_scanner);
    }

    @Override
    protected void onResume() {
        super.onResume();
        capture.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        capture.onPause();
        SharedPrefManager.getInstance(getApplicationContext()).setScan("empty");

    }
    @Override
    public void onStop() {
        super.onStop();
        SharedPrefManager.getInstance(getApplicationContext()).setScan("empty");


    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        capture.onDestroy();
        SharedPrefManager.getInstance(getApplicationContext()).setScan("empty");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        capture.onSaveInstanceState(outState);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        capture.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



    public void toggleFlash(View view){
        if(cameraFlashOn){
            barcodeScannerView.setTorchOff();
        }else{
            barcodeScannerView.setTorchOn();
        }
    }



    class TorchEventListener implements DecoratedBarcodeView.TorchListener{
        private TorchOnCaptureActivity activity;

        TorchEventListener(TorchOnCaptureActivity activity){
            this.activity = activity;
        }

        @Override
        public void onTorchOn() {
            this.activity.cameraFlashOn = true;
           // this.activity.updateView();
        }

        @Override
        public void onTorchOff() {
            this.activity.cameraFlashOn = false;
          //  this.activity.updateView();
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPrefManager.getInstance(getApplicationContext()).setScan("empty");
        SharedPrefManager.getInstance(getApplicationContext()).setCount(0);
        Intent i=new Intent(TorchOnCaptureActivity.this,MainActivity.class);
        startActivity(i);
        finish();

    }

}
