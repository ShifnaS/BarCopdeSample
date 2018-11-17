package com.example.softex.barcopdesample.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import com.example.softex.barcopdesample.R;
import com.example.softex.barcopdesample.dataClass.StudentRepo;
import com.example.softex.barcopdesample.helper.DBHelper;
import com.example.softex.barcopdesample.helper.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    List<String> roleList;
    Spinner spinnerRole;
    Button bt_cancel,bt_submit;
    EditText et_username,et_password;
    private static final int EXTERNAL_STORAGE_PERMISSION_CONSTANT = 100;
    private static final int REQUEST_PERMISSION_SETTING = 101;
    private boolean sentToSettings = false;
    public static final int RequestPermissionCode = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spinnerRole=(Spinner)findViewById(R.id.spinner);
        bt_cancel=(Button)findViewById(R.id.cancel);
        bt_submit=(Button)findViewById(R.id.submit);
        et_username=(EditText)findViewById(R.id.username);
        et_password=(EditText)findViewById(R.id.password);
        roleList=new ArrayList<String>();
        if(checkPermission()){

           // Toast.makeText(LoginActivity.this, "All Permissions Granted Successfully", Toast.LENGTH_LONG).show();

        }
        else {

            requestPermission();
        }
        DBHelper obj=new DBHelper(LoginActivity.this);
        StudentRepo r=new StudentRepo(getApplicationContext());
        int count=r.getAdminCount();
        if(count==0)
        {
            r.login();
        }
        roleList.add("Role");
        roleList.add("Admin");
        roleList.add("User");

        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, roleList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRole.setAdapter(dataAdapter);

        bt_cancel.setOnClickListener(this);
        bt_submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

            switch (v.getId()) {

                case R.id.cancel:
                        et_password.setText("");
                        et_username.setText("");
                    break;

                case R.id.submit:
                        String username=et_username.getText().toString().trim();
                        String password=et_password.getText().toString().trim();
                        String role=spinnerRole.getSelectedItem().toString().trim();
                        StudentRepo repo = new StudentRepo(this);
                        if(username.equals("")||password.equals(""))
                        {
                            Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show();
                        }
                        else if(role.equals("Role"))
                        {
                            Toast.makeText(this, "Please Select any role", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            int id=repo.adminlogin(username,password,role);
                            if(id!=0 && role.equals("Admin"))
                            {
                                SharedPrefManager.getInstance(getApplicationContext()).setId(id);
                                SharedPrefManager.getInstance(getApplicationContext()).setPass(password);
                                Intent i=new Intent(LoginActivity.this,AdminHomeActivity.class);
                                startActivity(i);
                                finish();

                            }
                            else if(id!=0 && role.equals("User"))
                            {
                                SharedPrefManager.getInstance(getApplicationContext()).setId(id);
                                SharedPrefManager.getInstance(getApplicationContext()).setPass(password);
                                Intent i=new Intent(LoginActivity.this,MainActivity.class);
                                startActivity(i);
                                finish();

                            }
                            else
                            {
                                Toast.makeText(this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                            }
                        }


                    break;

                default:
                    break;
            }

    }
    private void requestPermission() {

        ActivityCompat.requestPermissions(LoginActivity.this, new String[]
                {
                        CAMERA,
                        WRITE_EXTERNAL_STORAGE,
                        READ_EXTERNAL_STORAGE
                }, RequestPermissionCode);



    }
    public boolean checkPermission() {

        int FirstPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int SecondPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int ThirdPermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);

        return FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == EXTERNAL_STORAGE_PERMISSION_CONSTANT) {
            if (grantResults.length > 0) {
                boolean CameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean StoragePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                boolean ReadPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                if (CameraPermission && StoragePermission && ReadPermission) {

                    Toast.makeText(LoginActivity.this, "Permission Granted", Toast.LENGTH_LONG).show();
                }
                else
                {

                        //Show Information about why you need the permission
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("Need Camera Permission");
                        builder.setMessage("This app needs Camera permission");
                        builder.setPositiveButton("Grant", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();


                                ActivityCompat.requestPermissions(LoginActivity.this, new String[]{CAMERA,WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_CONSTANT);


                            }
                        });
                        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        builder.show();

                }
            }

        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (sentToSettings) {
            if (ActivityCompat.checkSelfPermission(LoginActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                //Got Permission

            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
