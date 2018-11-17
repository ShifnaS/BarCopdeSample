package com.example.softex.barcopdesample.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.softex.barcopdesample.R;
import com.example.softex.barcopdesample.dataClass.Student;
import com.example.softex.barcopdesample.dataClass.StudentRepo;
import com.example.softex.barcopdesample.helper.SharedPrefManager;

import java.util.List;

public class AdminHomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt_email,bt_vehicle,bt_user,bt_password,bt_logout,bt_delay,bt_viewall;
    StudentRepo repo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
        bt_email=(Button)findViewById(R.id.email);
        bt_vehicle=(Button)findViewById(R.id.vehicle);
        bt_user=(Button)findViewById(R.id.user);
        bt_password=(Button)findViewById(R.id.password);
        bt_logout=(Button)findViewById(R.id.logout);
        bt_viewall=(Button)findViewById(R.id.viewAll);
        bt_delay=(Button)findViewById(R.id.delay);

        bt_email.setOnClickListener(this);
        bt_vehicle.setOnClickListener(this);
        bt_user.setOnClickListener(this);
        bt_password.setOnClickListener(this);
        bt_logout.setOnClickListener(this);
        bt_viewall.setOnClickListener(this);
        bt_delay.setOnClickListener(this);

        repo=new StudentRepo(getApplicationContext());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.email:
                showSetEmail();
                break;

            case R.id.vehicle:
               showAddVehicle();
                break;

            case R.id.user:
                // do your code
                showAddUser();
                break;

            case R.id.password:
                // do your code
                showPassword();
                break;

            case R.id.logout:
               logout();
                break;

            case R.id.viewAll:
               Intent i=new Intent(getApplicationContext(),ViewAllAdminActivity.class);
                startActivity(i);
                break;
            case R.id.delay:
               delay();
                break;

            default:
                break;
        }
    }

    private void delay() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_add_delay,null);
        builder.setView(dialogView);
        Button cancel = (Button) dialogView.findViewById(R.id.cancel);
        Button submit = (Button) dialogView.findViewById(R.id.submit);
        final EditText et_delay=(EditText)dialogView.findViewById(R.id.delaytime);
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
                String delay=et_delay.getText().toString().trim();
                if(delay.equals(""))
                {
                    Toast.makeText(AdminHomeActivity.this, "Please Enter a time in milli seconds", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int count=  repo.getdelaycount();
                    if(count==0)
                    {
                        repo.adddelay(delay);
                        dialog.dismiss();
                        Toast.makeText(AdminHomeActivity.this, "Successfully Added", Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        repo.updateDelay(delay);
                        dialog.dismiss();
                        Toast.makeText(AdminHomeActivity.this, "Successfully Updated", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
        dialog.show();
    }

    private void logout() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        SharedPrefManager.getInstance(getApplicationContext()).logout();
                        Intent i=new Intent(AdminHomeActivity.this,LoginActivity.class);
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


    private void showSetEmail() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_set_email,null);
        builder.setView(dialogView);
        Button cancel = (Button) dialogView.findViewById(R.id.cancel);
        Button submit = (Button) dialogView.findViewById(R.id.submit);
        final EditText et_email=(EditText)dialogView.findViewById(R.id.editTextEmail);
        final EditText et_email1=(EditText)dialogView.findViewById(R.id.email);
        final AlertDialog dialog = builder.create();
        final int c=repo.getEmailCount();
        String myemail=repo.getEmail();
        final String ema[]=myemail.split(",");
        if(c!=0)
        {

            et_email.setText(ema[1]);
            et_email1.setText(ema[0]);
        }

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

                String email=et_email.getText().toString().trim();
                String email1=et_email1.getText().toString().trim();
                if(email.equals("")||email1.equals(""))
                {
                    Toast.makeText(AdminHomeActivity.this, "Enter all field", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    if(c==0)
                    {
                        repo.setEmail(email);
                        repo.setEmail(email1);
                    }
                    else if(c==2)
                    {
                        repo.updateEmail(email,ema[1]);
                        repo.updateEmail(email1,ema[0]);
                    }

                    dialog.dismiss();
                }

            }
        });
        dialog.show();
    }
    private void showAddVehicle() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_add_vehicle,null);
        builder.setView(dialogView);
        Button cancel = (Button) dialogView.findViewById(R.id.cancel);
        Button submit = (Button) dialogView.findViewById(R.id.submit);
        final EditText et_vehicleNo=(EditText)dialogView.findViewById(R.id.vehicleNo);
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
                String vehicleNo=et_vehicleNo.getText().toString().trim();
                if(vehicleNo.equals(""))
                {
                    Toast.makeText(AdminHomeActivity.this, "Please Enter a Vehicle No", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String msg=repo.CheckVehhicleAlreadyExist(vehicleNo);
                    if(!msg.equals("exist"))
                    {
                        repo.addVehicleNo(vehicleNo);
                        List<Student> l=repo.getAllVehhicle();
                        dialog.dismiss();
                       // Student barcode = l.get(0);
                       // Toast.makeText(AdminHomeActivity.this, ""+barcode.getVehicleNo(), Toast.LENGTH_SHORT).show();
                    }
                    else
                    {
                        Toast.makeText(AdminHomeActivity.this, "Vehicle No Alreay Exist", Toast.LENGTH_SHORT).show();
                    }

                }



            }
        });
        dialog.show();
    }

    private void showPassword() {
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
                    Toast.makeText(AdminHomeActivity.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                }
                else if(!prefpass.equals(oldPass))
                {
                    Toast.makeText(AdminHomeActivity.this, "Incorrect Old Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    repo.updatePass(oldPass,newPass,"Admin");
                    Toast.makeText(AdminHomeActivity.this, "Password Successfully Changed", Toast.LENGTH_SHORT).show();
                    logout();
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
    }

    private void showAddUser() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_add_user,null);
        builder.setView(dialogView);
        Button cancel = (Button) dialogView.findViewById(R.id.cancel);
        Button submit = (Button) dialogView.findViewById(R.id.submit);
        final EditText et_username=(EditText)dialogView.findViewById(R.id.username);
        final EditText et_password=(EditText)dialogView.findViewById(R.id.password);
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
                String usernanme=et_username.getText().toString().trim();
                String password=et_password.getText().toString().trim();
                if(usernanme.equals("")||password.equals(""))
                {
                    Toast.makeText(AdminHomeActivity.this, "Enter All Fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String msg=repo.CheckUserAlreadyExist(usernanme,password);
                    if(!msg.equals("exist"))
                    {
                        repo.addUser(usernanme,password);
                        dialog.dismiss();
                    }
                    else
                    {
                        Toast.makeText(AdminHomeActivity.this, "Username or Password Already Exist", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        logout();
    }
}
