package com.example.softex.barcopdesample.activity;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.softex.barcopdesample.R;
import com.example.softex.barcopdesample.dataClass.PDF;
import com.example.softex.barcopdesample.dataClass.Student;
import com.example.softex.barcopdesample.dataClass.StudentRepo;
import com.example.softex.barcopdesample.helper.GMailSender;
import com.example.softex.barcopdesample.helper.SharedPrefManager;
import com.example.softex.barcopdesample.helper.appConstants;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.itextpdf.text.DocumentException;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity implements  View.OnClickListener {
    int c=0;
    public static final int SUCCESS_RETURN_CODE = 1;

    int count=0;
    SimpleDateFormat  sdf,sdf1;
    Button scan_button,generate_pdf,change_password,logout,manual;
    private String path;
    private File dir;
    ProgressDialog dialog;
    AutoCompleteTextView tv_vehicleNo;
    AutoCompleteTextView tv_refNo;
    String vehicleNo="";
    StudentRepo repo;
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    String refNo="";
    PDF p;
    int flag=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scan_button= (Button) findViewById(R.id.scan_button);
        logout= (Button) findViewById(R.id.logout);
        change_password= (Button) findViewById(R.id.password);
        generate_pdf= (Button) findViewById(R.id.report);
        manual= (Button) findViewById(R.id.manual);
        tv_refNo= (AutoCompleteTextView) findViewById(R.id.refno);
        dialog = new ProgressDialog(MainActivity.this);
        p=new PDF();
        tv_vehicleNo=(AutoCompleteTextView)findViewById(R.id.vehicleNo);
        sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf1 = new SimpleDateFormat("HH:mm:ss a");
        repo=new StudentRepo(getApplicationContext());
        createDirectories();
        setAutoCompleteTextView();
        setAutoCompleteRef(tv_refNo);
        sp= PreferenceManager.getDefaultSharedPreferences(this);
        ed=sp.edit();
        SharedPrefManager.getInstance(getApplicationContext()).setScan("empty");

        scan_button.setOnClickListener(this);
        generate_pdf.setOnClickListener(this);
        change_password.setOnClickListener(this);
        manual.setOnClickListener(this);
        logout.setOnClickListener(this);



    }




    private void setAutoCompleteTextView() {

        List<Student> recydata=repo.getAllVehhicle();
        String vehicleNo[]=new String[recydata.size()];
        for(int i=0;i<recydata.size();i++)
        {
            Student s=recydata.get(i);
            String v=s.getVehicleNo();
            vehicleNo[i]=v;

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, vehicleNo);
        tv_vehicleNo.setThreshold(1);//will start working from first character
        tv_vehicleNo.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView


        /////////////////////////////////////////////////////////
    }
    private void setAutoCompleteRef(AutoCompleteTextView tv_refNo)
    {
        String date=sdf.format(Calendar.getInstance().getTime());
        List<Student> data=repo.getReffNo(date);

        if(data.size()!=0)
        {
            String refNo[]=new String[data.size()];
            for(int i=0;i<data.size();i++)
            {
                Student s=data.get(i);
                String r=s.getRefNo();
                refNo[i]=r;

            }
            ArrayAdapter<String> adapter1 = new ArrayAdapter<String>
                    (this, android.R.layout.select_dialog_item, refNo);
            tv_refNo.setThreshold(1);//will start working from first character
            tv_refNo.setAdapter(adapter1);//setting the adapter data into the AutoCompleteTextView
        }
    }



    public class SendMailTask extends AsyncTask {

        private Activity sendMailActivity;
        private ProgressDialog statusDialog;

        public SendMailTask(Activity activity) {
            sendMailActivity = activity;

        }

        protected void onPreExecute() {
            statusDialog = new ProgressDialog(sendMailActivity);
            statusDialog.setMessage("Getting ready...");
            statusDialog.setIndeterminate(false);
            statusDialog.setCancelable(false);
            statusDialog.show();
        }

        @Override
        protected Object doInBackground(Object... args) {

            StudentRepo repo=new StudentRepo(getApplicationContext());
            final String email=repo.getEmail().trim();
            final String myemail[]=email.split(",");


            try {
                Log.i("SendMailTask", "About to instantiate GMail...");
                publishProgress("Processing input....");
                String filepath=args[0].toString();


                GMailSender sender = new GMailSender("hycountapp@gmail.com", "Softex#2018");
                String sub="PFA";
                String body="report";
                String from="hycountapp@gmail.com";

                publishProgress("Sending email....");

               // sender.sendmailwithattachment(sub,body,from,email,filepath);
                sender.sendmailwithattachment(sub,body,from,myemail[0],filepath);
                sender.sendmailwithattachment(sub,body,from,myemail[1],filepath);



                publishProgress("Email Sent.");
                // Toast.makeText(getApplicationContext(), "Mail Sent", Toast.LENGTH_SHORT).show();

                Log.i("SendMailTask", "Mail Sent.");
            } catch (Exception e) {
                publishProgress(e.getMessage());
                Log.e("SendMailTask", e.getMessage(), e);
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Object... values) {
            statusDialog.setMessage(values[0].toString());

        }

        @Override
        public void onPostExecute(Object result) {
            statusDialog.dismiss();
            Toast.makeText(sendMailActivity, "Email Send Successfully", Toast.LENGTH_SHORT).show();

        }

    }





    public void dialogDismiss()
    {
        if(dialog != null) {
            dialog.dismiss();
            dialog = null;
        }
    }
    private void createDirectories()
    {
        try{

            SimpleDateFormat sdf = new SimpleDateFormat("MMddyy");
            String s= sdf.format(Calendar.getInstance().getTime());

            path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/QRCodeScanner/PDF Files/"+s;
            dir = new File(path);
            SharedPrefManager.getInstance(getApplicationContext()).setPath(path);

            if (!dir.exists()) {
                dir.mkdirs();
                Log.d("FOLDER", "SUCCESS");
            }
            else
            {
                Log.d("FOLDER", "FOLDER EXISTS");
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void scanNow(){

        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scan a Qrcode");
        integrator.setCaptureActivity(TorchOnCaptureActivity.class);

        integrator.setOrientationLocked(false);
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
      //  finish();

    }
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        StudentRepo repo = new StudentRepo(this);
        Student student = new Student();


        if (scanningResult != null) {
            SharedPrefManager.getInstance(getApplicationContext()).setScan( scanningResult.getContents());
            flag=1;
            String date=sdf.format(Calendar.getInstance().getTime());
            String time=sdf1.format(Calendar.getInstance().getTime());
            String scanContent = scanningResult.getContents();
            String format = scanningResult.getFormatName();
            count=repo.CheckAlreadyExist(scanContent,date,vehicleNo,refNo);
            SharedPrefManager.getInstance(getApplicationContext()).setCount(0);

            if(count==0)
            {
                SharedPrefManager.getInstance(getApplicationContext()).setCount(1);
                student.content=scanContent;
                student.format=format;
                student.count=1;
                student.date=date;
                student.time=time;
                student.vehicleNo=vehicleNo;
                student.refNo=refNo;
                int id=repo.insert(student);

            }
            else
            {

                count=count+1;
                c++;
                SharedPrefManager.getInstance(getApplication()).setCount(count);
                student=new Student();
                repo.update(count,scanContent,time,vehicleNo,refNo);
            }
            int id=SharedPrefManager.getInstance(getApplicationContext()).getId();
            if(id!=0)
            {
                    scanNow();

            }
            else
                {
                finish();
                c=0;
            }

        }
        else
        {
            flag=0;
            Toast toast = Toast.makeText(getApplicationContext(),"No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
            finish();
        }
    }

    @Override
    public void onBackPressed() {

        logout();
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPrefManager.getInstance(getApplicationContext()).logout();
                        Intent i=new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(i);
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();


        alert.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED);
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.RED);

            }
        });
        alert.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.scan_button:
                String time=sdf1.format(Calendar.getInstance().getTime());
                SharedPrefManager.getInstance(getApplicationContext()).setStartTime(time);
                ed.putString("starttime",time);
                ed.commit();
                vehicleNo=tv_vehicleNo.getText().toString().trim();
                 refNo=tv_refNo.getText().toString().trim();
                int id=SharedPrefManager.getInstance(getApplicationContext()).getId();
                if(id!=0)
                {
                    if(vehicleNo.equals("")|| refNo.equals(""))
                    {
                        Toast.makeText(MainActivity.this, "Please Enter all details", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        SharedPrefManager.getInstance(getApplicationContext()).setRef(refNo);

                     //  releaseCamera();
                       scanNow();


                    }
                }
                else{
                    finish();;
                }
                break;

            case R.id.report:
                report();

                break;

             case R.id.manual:
                manual();
                break;

            case R.id.password:
                changePassword();
                break;

            case R.id.logout:
                logout();
                break;

            default:
                break;
        }
    }

    private void manual() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_manual_count,null);
        builder.setView(dialogView);
        Button cancel = (Button) dialogView.findViewById(R.id.cancel);
        Button submit = (Button) dialogView.findViewById(R.id.submit);

        final AutoCompleteTextView et_vehNo=(AutoCompleteTextView)dialogView.findViewById(R.id.vehNo);
        final AutoCompleteTextView et_refNo=(AutoCompleteTextView)dialogView.findViewById(R.id.refNo);
        final EditText et_count=(EditText)dialogView.findViewById(R.id.count);

        setAutoCompleteRef(et_refNo);
        List<Student> recydata=repo.getAllVehhicleNo();
        String vehicleNo[]=new String[recydata.size()];
        for(int i=0;i<recydata.size();i++)
        {
            Student s=recydata.get(i);
            String v=s.getVehicleNo();
            vehicleNo[i]=v;

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, vehicleNo);
        et_vehNo.setThreshold(1);//will start working from first character
        et_vehNo.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView




        final AlertDialog dialog = builder.create();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                dialog.dismiss();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                String vehNo=et_vehNo.getText().toString().trim();
                String refNo=et_refNo.getText().toString().trim();
                String count=et_count.getText().toString().trim();
                String date=sdf.format(Calendar.getInstance().getTime());
                String time=sdf1.format(Calendar.getInstance().getTime());
                if(vehNo.equals("")||refNo.equals("")||count.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Enter all field", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String msg=repo.CheckManualCountExist(vehNo,refNo,date);
                    if(msg.equals("exist"))
                    {
                        Toast.makeText(MainActivity.this, "Already Added", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        repo.manualCount(count,vehNo,refNo,date,time);
                    }

                    dialog.dismiss();
                }

            }
        });
        dialog.show();


    }

    private void changePassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_change_password,null);
        builder.setView(dialogView);
        Button cancel = (Button) dialogView.findViewById(R.id.cancel);
        Button submit = (Button) dialogView.findViewById(R.id.submit);
        final EditText et_oldPassword=(EditText)dialogView.findViewById(R.id.oldPass);
        final EditText et_newPassword=(EditText)dialogView.findViewById(R.id.newPass);
        final AlertDialog dialog = builder.create();
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                dialog.dismiss();

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                String newPass=et_newPassword.getText().toString().trim();
                String oldPass=et_oldPassword.getText().toString().trim();
                String prefpass=SharedPrefManager.getInstance(getApplicationContext()).getPass();
                if(newPass.equals("")||oldPass.equals(""))
                {
                    Toast.makeText(MainActivity.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                }
                else if(!prefpass.equals(oldPass))
                {
                    Toast.makeText(MainActivity.this, "Incorrect Old Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    repo.updatePass(oldPass,newPass,"User");
                    Toast.makeText(MainActivity.this, "Password Successfully Changed", Toast.LENGTH_SHORT).show();
                    logout();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }


    private void report()
    {

             String pdftime=sdf.format(Calendar.getInstance().getTime());
             List<Student>  barcodeList=repo.getAllList(pdftime);
            try
            {
                p.createPDF(path,pdftime,barcodeList);
                String filepath=path+"/QRCodePDF" +pdftime+ ".pdf";
                if( haveNetworkConnection())
                {
                    //sendMessage(filepath);
                    String email=repo.getEmail().trim();
                    if(email.equals(""))
                    {
                        Toast.makeText(this, "No emailId yet set", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        new SendMailTask(MainActivity.this).execute(filepath);
                    }

                }
                else
                {
                    Toast.makeText(MainActivity.this, "Please turn on your mobile data or wifi", Toast.LENGTH_SHORT).show();
                }


            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            dialog.dismiss();

    }




    private boolean haveNetworkConnection()
    {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();


    }

}
