package com.example.softex.barcopdesample.dataClass;

/**
 * Created by softex on 29-Jan-18.
 */

public class Student {
    // Labels table name
    public static final String TABLE = "barcode";
    public static final String TABLE_admin = "admin";
    public static final String TABLE_vehicle = "vehicle";
    public static final String TABLE_email = "email";

    public static final String TABLE_user = "user";
    // Labels Table Columns names
    public static final String KEY_ID = "ID";
    public static final String KEY_content = "content";
    public static final String KEY_format = "format";
    public static final String KEY_count = "count";
    public static final String KEY_date = "date";
    public static final String KEY_time = "time";
    public static final String KEY_username= "username";
    public static final String KEY_password = "password";
    public static final String KEY_vehicleNo= "vehicleNo";
    public static final String KEY_email= "emailId";
    public static final String KEY_refNo= "refNo";
    // property help us to keep data
    public int student_ID;
    public String content;
    public String format;
    public int count;
    public String date;
    public String time;
    public String vehicleNo;
    public int manualcount;
    public String refNo;
    public int getManualcount() {
        return manualcount;
    }

    public void setManualcount(int manualcount) {
        this.manualcount = manualcount;
    }



    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }




    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Student(int student_ID, String content, String format, int count, String date) {
        this.student_ID = student_ID;
        this.content = content;
        this.format = format;
        this.count = count;
        this.date = date;
    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Student()
    {

    }



    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getStudent_ID() {
        return student_ID;
    }

    public void setStudent_ID(int student_ID) {
        this.student_ID = student_ID;
    }


}
