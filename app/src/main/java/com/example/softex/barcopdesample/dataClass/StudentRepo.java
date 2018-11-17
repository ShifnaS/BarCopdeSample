package com.example.softex.barcopdesample.dataClass;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.softex.barcopdesample.helper.DBHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class StudentRepo {
    private DBHelper dbHelper;
    Context context;
    public StudentRepo(Context context) {
        dbHelper = new DBHelper(context);
        this.context=context;
    }

    public void login()
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Student.KEY_username, "admin");
        values.put(Student.KEY_password, "admin");

        // Inserting Row
        db.insert(Student.TABLE_admin, null, values);
        db.close(); // Closing database connection
    }

    public int getAdminCount() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int count = (int) DatabaseUtils.queryNumEntries(db, Student.TABLE_admin);
        db.close();
        return count;
    }

    public int getdelaycount() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int count = (int) DatabaseUtils.queryNumEntries(db, "tbl_delay");
        db.close();
        return count;
    }

    public String getdelay() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT * FROM tbl_delay";
        String delay="";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                delay=cursor.getString(cursor.getColumnIndex("delay"));

            } while (cursor.moveToNext());
        }
        Log.e("delay","************ "+delay);
        cursor.close();
        db.close();
        return delay;
    }

    public void updateDelay(String delay)
    {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("delay", delay);
        //   db.update(Student.TABLE_email, values, null, null);
        db.update("tbl_delay", values, null,null);
        db.close();

    }

    public void adddelay(String delay)
    {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("delay", delay);
        // Inserting Row
        db.insert("tbl_delay", null, values);
        db.close(); // Closing database connection

    }

    public void addVehicleNo(String vehicleNo)
    {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Student.KEY_vehicleNo, vehicleNo);
        // Inserting Row
        db.insert(Student.TABLE_vehicle, null, values);
        db.close(); // Closing database connection

    }

    public void addUser(String username,String password)
    {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Student.KEY_username, username);
        values.put(Student.KEY_password, password);
        db.insert(Student.TABLE_user, null, values);
        db.close(); // Closing database connection

    }
    public int getEmailCount() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        int count = (int) DatabaseUtils.queryNumEntries(db, Student.TABLE_email);
        db.close();
        return count;
    }
    public void setEmail(String email)
    {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Student.KEY_email, email);
       // db.update(Student.TABLE_admin, values, null, null);
        db.insert(Student.TABLE_email, null, values);
        db.close();

    }
    public void updateEmail(String email,String oldemail)
    {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Student.KEY_email, email);
      //   db.update(Student.TABLE_email, values, null, null);
        db.update(Student.TABLE_email, values, Student.KEY_email + "= ?", new String[]{String.valueOf(oldemail)});
        //db.insert(Student.TABLE_email, null, values);
        db.close();

    }
    public String getEmail()
    {
        String email ="";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
       // String selectQuery = "SELECT * FROM "+Student.TABLE_email+" Limit 2";
        String selectQuery = "SELECT * FROM email";

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String e=cursor.getString(cursor.getColumnIndex(Student.KEY_email));
                email=e+","+email;

            } while (cursor.moveToNext());
        }
        Log.e("my email id",""+email);
        cursor.close();
        db.close();
        return email;
    }
    public String CheckVehhicleAlreadyExist(String vehicleNo) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + Student.TABLE_vehicle + " WHERE " +Student.KEY_vehicleNo+ "=?";
        String msg = "";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(vehicleNo)});
        if (cursor.moveToFirst()) {
            do {
                msg="exist";
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return msg;
    }
    public String CheckUserAlreadyExist(String email,String pass) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + Student.TABLE_user + " WHERE " +Student.KEY_username+ "=? AND "+Student.KEY_password+ "=?";
        String msg = "";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(email),String.valueOf(pass)});
        if (cursor.moveToFirst()) {
            do {
                msg="exist";
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return msg;
    }
    public int adminlogin(String username,String password,String role)
    {
        String selectQuery="";
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        if(role.equals("Admin"))
        {
             selectQuery = "SELECT  * FROM "+Student.TABLE_admin+" WHERE " +
                    Student.KEY_username + "=? and "+
                    Student.KEY_password  +"=?";
        }
        else if(role.equals("User"))
        {
            selectQuery = "SELECT  * FROM "+Student.TABLE_user+" WHERE " +
                    Student.KEY_username + "=? and "+
                    Student.KEY_password  +"=?";
        }


        int id = 0;
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(username),String.valueOf(password)});
        if (cursor.moveToFirst()) {
            do
            {
                id=cursor.getInt(cursor.getColumnIndex(Student.KEY_ID));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return id;
    }


    public int insert(Student student) {
        long student_Id=0;
        //Open connection to write data
        if(student.content!=null)
        {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(Student.KEY_count, student.count);
            values.put(Student.KEY_format, student.format);
            values.put(Student.KEY_content, student.content);
            values.put(Student.KEY_date, student.date);
            values.put(Student.KEY_time, student.time);
            values.put(Student.KEY_vehicleNo, student.vehicleNo);
            values.put(Student.KEY_refNo, student.refNo);


            // Inserting Row
            student_Id = db.insert(Student.TABLE, null, values);
            db.close(); // Closing database connection
        }

        return (int) student_Id;
    }
    public int CheckAlreadyExist(String content,String date,String vehicleNO,String refNo) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  " +
                Student.KEY_count  +
                " FROM " + Student.TABLE
                + " WHERE " +
                Student.KEY_content + "=? and "+
                Student.KEY_date  +"=? and "+
                Student.KEY_vehicleNo  +"=? and "+
                Student.KEY_refNo  +"=?" ;
        int count = 0;
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(content),String.valueOf(date),String.valueOf(vehicleNO),String.valueOf(refNo)});
        if (cursor.moveToFirst()) {
            do {
                count=cursor.getInt(cursor.getColumnIndex(Student.KEY_count));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return count;
    }
    public void update(int count,String content, String time,String vehNo,String RefNo) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(Student.KEY_count, count);
       // values.put(Student.KEY_time, time);
        db.update(Student.TABLE, values, Student.KEY_content + "= ? and vehicleNo=? and refNo=?", new String[]{String.valueOf(content),String.valueOf(vehNo),String.valueOf(RefNo)});
        db.close();
    }


    public void updatePass(String oldPass,String newPass,String role) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Student.KEY_password, newPass);

        if(role.equals("Admin"))
        {
            db.update(Student.TABLE_admin, values, Student.KEY_password + "= ?", new String[]{String.valueOf(oldPass)});
        }
        else if(role.equals("User"))
        {
            db.update(Student.TABLE_user, values, Student.KEY_password + "= ?", new String[]{String.valueOf(oldPass)});
        }
        db.close();
    }

    public void updateUsername(String oldPass,String username) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Student.KEY_username, username);
        db.update(Student.TABLE_user, values, Student.KEY_password + "= ?", new String[]{String.valueOf(oldPass)});
        db.close();
    }



  /*  public void delete(int student_Id) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // It's a good practice to use parameter ?, instead of concatenate string
        db.delete(Student.TABLE, Student.KEY_ID + "= ?", new String[]{String.valueOf(student_Id)});
        db.close(); // Closing database connection
    }*/


    public List<Student> getAllVehhicle() {
        Student student;
        List<Student> barcodeList  = new ArrayList<>();
       // String [] vehicle=new String[];
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String q="select * from "+ Student.TABLE_vehicle;
        Cursor cursor = db.rawQuery(q, null);
        if (cursor.moveToFirst()) {
            try
            {
                do {
                    student=new Student();
                    String vehicleNo=cursor.getString(cursor.getColumnIndex(Student.KEY_vehicleNo));
                    Log.e("vehicleNo","********* "+vehicleNo);
                    student.setVehicleNo(vehicleNo);
                    barcodeList.add(student) ;
                } while (cursor.moveToNext());
            }
            catch (Exception e)
            {
            }
        }
        cursor.close();
        db.close();
        return barcodeList;
    }


    public List<Student> getAllVehhicleNo() {
        Student student;
        List<Student> barcodeList  = new ArrayList<>();
        // String [] vehicle=new String[];
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String q="select distinct vehicleNo from "+ Student.TABLE;

        Cursor cursor = db.rawQuery(q, null);
        if (cursor.moveToFirst()) {
            try
            {
                do {
                    student=new Student();
                    String vehicleNo=cursor.getString(cursor.getColumnIndex(Student.KEY_vehicleNo));
                    Log.e("vehicleNo","********* "+vehicleNo);
                    student.setVehicleNo(vehicleNo);
                    barcodeList.add(student) ;
                } while (cursor.moveToNext());
            }
            catch (Exception e)
            {
            }
        }
        cursor.close();
        db.close();
        return barcodeList;
    }

    public List<Student> getReffNo(String date) {
        Student student;
        List<Student> barcodeList  = new ArrayList<>();
        // String [] vehicle=new String[];
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String q="select distinct refNo from "+ Student.TABLE+" where "+ Student.KEY_date+"=? ";

        Cursor cursor = db.rawQuery(q, new String[]{String.valueOf(date)});
        if (cursor.moveToFirst()) {
            try
            {
                do {
                    student=new Student();
                    String refNo=cursor.getString(cursor.getColumnIndex(Student.KEY_refNo));
                    Log.e("RefNo","********* "+refNo);
                    student.setRefNo(refNo);
                    barcodeList.add(student) ;
                } while (cursor.moveToNext());
            }
            catch (Exception e)
            {
            }
        }
        cursor.close();
        db.close();
        return barcodeList;
    }


    public List<Student> getAllList(String cdate) {
        Student student;
        List<Student> barcodeList  = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery=" SELECT vehicleNo, refNo, sum(count) mycount, date from barcode where date = ? GROUP BY vehicleNo,refNo";

       // SELECT albumid,sum(milliseconds) length,sum(bytes) size FROM tracks GROUP BY  albumid;
        Cursor cursor = db.rawQuery(selectQuery,  new String[]{String.valueOf(cdate)});
        if (cursor.moveToFirst()) {

           try
           {
               do {
                   student=new Student();
                   int count=cursor.getInt(cursor.getColumnIndex("mycount"));
                   String date=cursor.getString(cursor.getColumnIndex(Student.KEY_date));
                   String refNo=cursor.getString(cursor.getColumnIndex(Student.KEY_refNo));
                   String vehicleNo=cursor.getString(cursor.getColumnIndex(Student.KEY_vehicleNo));

                   String q="SELECT  * FROM manualCount WHERE vehNo=? and refNo=? and date=?";
                   Cursor cursor1 = db.rawQuery(q, new String[]{String.valueOf(vehicleNo),String.valueOf(refNo),String.valueOf(cdate)});
                   if (cursor1.moveToFirst())
                   {
                        try
                        {
                            do
                            {
                                int mcount=cursor1.getInt(cursor1.getColumnIndex("count"));
                               // Toast.makeText(context, "mcount "+mcount, Toast.LENGTH_SHORT).show();
                                student.setManualcount(mcount);
                            } while (cursor1.moveToNext());

                        }
                        catch (Exception e)
                        {
                            System.out.println(e);
                        }
                   }
                   student.setContent(refNo);
                   student.setCount(count);
                   student.setDate(date);
                   student.setVehicleNo(vehicleNo);
                   barcodeList.add(student) ;

               } while (cursor.moveToNext());
           }
           catch (Exception e)
           {
               System.out.println(e);
           }
        }

        cursor.close();
        db.close();
        return barcodeList;

    }
    public List<Student> getAllLists(String startTime, String endTime,String date1) {
        Student student;
        List<Student> barcodeList  = new ArrayList<Student>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String selectQuery="SELECT SUM(count) as count ,content,date,vehicleNo,refNo from barcode" +
                " where  time BETWEEN ? and ? and date= ? GROUP BY date,content,vehicleNo,refNo ";

        Cursor cursor = db.rawQuery(selectQuery,  new String[]{String.valueOf(startTime),String.valueOf(endTime),String.valueOf(date1)});
        if (cursor.moveToFirst()) {

            try
            {
                do {
                    student=new Student();
                   // int id=cursor.getInt(cursor.getColumnIndex(Student.KEY_ID));
                    int count=cursor.getInt(cursor.getColumnIndex("count"));
                    String date=cursor.getString(cursor.getColumnIndex(Student.KEY_date));
                    String content=cursor.getString(cursor.getColumnIndex(Student.KEY_refNo));
                    String vehicleNo=cursor.getString(cursor.getColumnIndex(Student.KEY_vehicleNo));


                  //  Log.e("id",""+id);
                    Log.e("count",""+count);
                    Log.e("date",""+date);
                    Log.e("content",""+content);
                    Log.e("vehicleNo",""+vehicleNo);

                    student.setContent(content);
                   // student.setStudent_ID(id);
                    student.setCount(count);
                    student.setDate(date);
                    student.setVehicleNo(vehicleNo);
                    barcodeList.add(student) ;

                } while (cursor.moveToNext());
            }
            catch (Exception e)
            {

            }
        }

        cursor.close();
        db.close();
        return barcodeList;

    }
    public List<Student> getAllListByDate(String from,String to) {

        Student student;
        List<Student> barcodeList  = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
       // String selectQuery = "SELECT * FROM " +Student.TABLE + " WHERE " + Student.KEY_date+ " >=? and "+Student.KEY_date  +" <=?";

        String selectQuery="SELECT SUM(count) as count ,content,date,vehicleNo,refNo from barcode" +
                " where  date BETWEEN ? and ? GROUP BY date,content,vehicleNo,refNo ";

        Cursor cursor = db.rawQuery(selectQuery,  new String[]{String.valueOf(from),String.valueOf(to)});
        if (cursor.moveToFirst()) {

            try
            {
                do {
                    student=new Student();

                   // int id=cursor.getInt(cursor.getColumnIndex(Student.KEY_ID));
                    int count=cursor.getInt(cursor.getColumnIndex(Student.KEY_count));
                    String date=cursor.getString(cursor.getColumnIndex(Student.KEY_date));
                    String refNo=cursor.getString(cursor.getColumnIndex(Student.KEY_refNo));
                   // String time=cursor.getString(cursor.getColumnIndex(Student.KEY_time));
                    String vehicle=cursor.getString(cursor.getColumnIndex(Student.KEY_vehicleNo));
                    String q="SELECT  * FROM manualCount WHERE vehNo=? and refNo=? and  date=?";
                    Cursor cursor1 = db.rawQuery(q, new String[]{String.valueOf(vehicle),String.valueOf(refNo),String.valueOf(date)});
                    if (cursor1.moveToFirst())
                    {
                        try
                        {
                            do
                            {
                                int mcount=cursor1.getInt(cursor1.getColumnIndex("count"));
                               // Toast.makeText(context, "mcount "+mcount, Toast.LENGTH_SHORT).show();
                                student.setManualcount(mcount);
                            } while (cursor1.moveToNext());

                        }
                        catch (Exception e)
                        {
                            System.out.println(e);
                        }
                    }

                    student.setContent(refNo);
                    //student.setStudent_ID(id);
                    student.setCount(count);
                    student.setDate(date);
                   // student.setTime(time);
                    student.setVehicleNo(vehicle);

                    barcodeList.add(student) ;



                } while (cursor.moveToNext());
            }
            catch (Exception e)
            {

            }
        }

        cursor.close();
        db.close();
        return barcodeList;

    }

    public List<Student> getAllListByVehicleNo(String vehicleNo,String from,String to) {

        Student student;
        List<Student> barcodeList  = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
       // String q="select * from "+ Student.TABLE + " where " + Student.KEY_vehicleNo +" =?";
        String selectQuery="SELECT SUM(count) as count ,content,date,vehicleNo,refNo from barcode" +
                " where " + Student.KEY_vehicleNo  +" =? and date BETWEEN ? and ? GROUP BY date,content,vehicleNo,refNo ";
        Cursor cursor = db.rawQuery(selectQuery,  new String[]{String.valueOf(vehicleNo),String.valueOf(from),String.valueOf(to)});
        if (cursor.moveToFirst()) {

            try
            {
                do {
                    student=new Student();

                  //  int id=cursor.getInt(cursor.getColumnIndex(Student.KEY_ID));
                    int count=cursor.getInt(cursor.getColumnIndex(Student.KEY_count));
                    String date=cursor.getString(cursor.getColumnIndex(Student.KEY_date));
                    String refNo=cursor.getString(cursor.getColumnIndex(Student.KEY_refNo));
                    //String time=cursor.getString(cursor.getColumnIndex(Student.KEY_time));
                    String vehicle=cursor.getString(cursor.getColumnIndex(Student.KEY_vehicleNo));
                    String q="SELECT  * FROM manualCount WHERE vehNo=? and refNo=? and date=?";
                    Cursor cursor1 = db.rawQuery(q, new String[]{String.valueOf(vehicle),String.valueOf(refNo),String.valueOf(date)});
                    if (cursor1.moveToFirst())
                    {
                        try
                        {
                            do
                            {
                                int mcount=cursor1.getInt(cursor1.getColumnIndex("count"));
                               // Toast.makeText(context, "mcount "+mcount, Toast.LENGTH_SHORT).show();
                                student.setManualcount(mcount);
                            } while (cursor1.moveToNext());

                        }
                        catch (Exception e)
                        {
                            System.out.println(e);
                        }
                    }

                    student.setContent(refNo);
                   // student.setStudent_ID(id);
                    student.setCount(count);
                    student.setDate(date);
                   // student.setTime(time);
                    student.setVehicleNo(vehicle);

                    barcodeList.add(student) ;



                } while (cursor.moveToNext());
            }
            catch (Exception e)
            {

            }
        }

        cursor.close();
        db.close();
        return barcodeList;

    }
    public void manualCount(String count,String vehNo,String refNo,String date,String time)
    {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("count",count);
        values.put("vehNo", vehNo);
        values.put("refNo",refNo);
        values.put("date",date );
        values.put("time", time);
         // Inserting Row
        db.insert("manualCount", null, values);
        db.close(); // Closing database connection
    }

    public String CheckManualCountExist(String vehNo,String refNo,String date) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String selectQuery = "SELECT  * FROM manualCount WHERE vehNo=? and refNo=? and date=?";
        String msg = "";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{String.valueOf(vehNo),String.valueOf(refNo),String.valueOf(date)});
        if (cursor.moveToFirst()) {
            do {
                msg="exist";
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return msg;
    }
}