package com.example.softex.barcopdesample.activity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.softex.barcopdesample.R;
import com.example.softex.barcopdesample.adapter.BarcodeAdapter;
import com.example.softex.barcopdesample.dataClass.Student;
import com.example.softex.barcopdesample.dataClass.StudentRepo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ViewAllAdminActivity extends AppCompatActivity implements  View.OnClickListener {
    private RecyclerView recyclerView;
    private BarcodeAdapter mAdapter;
    SimpleDateFormat sdf,sdf1;
    AutoCompleteTextView tv_vehicleNo;
    StudentRepo repo;
    Button view,clear;
    DatePickerDialog datePickerDialog;
    EditText et_from,et_to;
    String from_date="",to_date="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all_admin);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        sdf = new SimpleDateFormat("dd-MM-yyyy");
        tv_vehicleNo=(AutoCompleteTextView)findViewById(R.id.vehicleNo);
        view= (Button) findViewById(R.id.view);
        clear= (Button) findViewById(R.id.clear);
        et_from=(EditText)findViewById(R.id.from);
        et_to=(EditText)findViewById(R.id.to);

        repo=new StudentRepo(getApplicationContext());
        String pdftime=sdf.format(Calendar.getInstance().getTime());

        List<Student>  barcodeList=repo.getAllList(pdftime);
        setRecyclerView(barcodeList);

        setAutoCompleteTextView();

        view.setOnClickListener(this);
        clear.setOnClickListener(this);
        et_from.setOnClickListener(this);
        et_to.setOnClickListener(this);


        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        int month=mMonth+1;
        String date1=mDay + "-" + month + "-" + mYear;

        String date=changeDateFormat(date1);
        et_from.setText(date);
        et_to.setText(date);
    }
    private void setRecyclerView(List<Student> barcodeList) {

        if(barcodeList.size()!=0)
        {
            mAdapter = new BarcodeAdapter(barcodeList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
        }
        else
        {
            Toast.makeText(this, "There is No data matched with your search", Toast.LENGTH_SHORT).show();
        }

    }
    private void setAutoCompleteTextView() {

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
        tv_vehicleNo.setThreshold(1);//will start working from first character
        tv_vehicleNo.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.view:
                String vehicleNo=tv_vehicleNo.getText().toString().trim();
                String from=et_from.getText().toString().toString().trim();
                String to=et_to.getText().toString().toString().trim();
                String from1=changeDateFormat(from);
                String to1=changeDateFormat(to);
                if(from.equals("") || to.equals(""))
                {
                    Toast.makeText(this, "Enter any Field", Toast.LENGTH_SHORT).show();
                }
                else  if(vehicleNo.equals(""))
                {

                    List<Student>  barcodeList=repo.getAllListByDate(from1, to1);
                    setRecyclerView(barcodeList);
                }
                else
                {
                    List<Student>  barcodeList=repo.getAllListByVehicleNo(vehicleNo,from1, to1);
                    setRecyclerView(barcodeList);

                }


                break;

            case R.id.clear:
                finish();
                startActivity(getIntent());
                break;

            case R.id.from:
                // do your code
                getDate(et_from);
                break;

            case R.id.to:
                getDate(et_to);
                break;



            default:
                break;
        }
    }

    private String changeDateFormat(String dateFormat) {
        String date="",month="",day="";
        String dateArray[]=dateFormat.split("-");
        int d=Integer.parseInt(dateArray[0]);
        int m=Integer.parseInt(dateArray[1]);
        String year=dateArray[2];
        if(m<10)
        {
            month="0"+m;
        }
        else
        {
            month=""+m;
        }
        if(d<10)
        {
            day="0"+d;
        }
        else
        {
            day=""+d;
        }
        date=day+"-"+month+"-"+year;
        return date;
    }

    public void getDate(final EditText editText) {
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day

        //dd-MM-yyyy
        // date picker dialog
        datePickerDialog = new DatePickerDialog(ViewAllAdminActivity.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        int month=monthOfYear+1;

                        editText.setText(dayOfMonth + "-"
                                + month + "-" + year);

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }
}
