package com.example.softex.barcopdesample.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.softex.barcopdesample.R;
import com.example.softex.barcopdesample.helper.GMailSender;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PdfActivity extends AppCompatActivity {

    Button bt_pdf;
    private ProgressDialog progressDialog;
    private PdfPCell cell;
    private Image bgImage;
    private String path;
    BaseColor myColor = WebColors.getRGBColor("#9E9E9E");
    BaseColor myColor1 = WebColors.getRGBColor("#757575");
    private File dir;
    private File file;
    SimpleDateFormat sdf;
    String t="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
       // bt_pdf = (Button) findViewById(R.id.pdf);
        sdf = new SimpleDateFormat("ddMMyyyyhhmmss");

        createDirectories();


                t=sdf.format(Calendar.getInstance().getTime());
               try {
                    createPDF();
                   String filepath=path+"/QRCodePDF" +t+ ".pdf";
                   sendMessage(filepath);
                   Intent i=new Intent(PdfActivity.this,MainActivity.class);
                   startActivity(i);
                   finish();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (DocumentException e) {
                    e.printStackTrace();
                }


    }
    private void createDirectories()
    {
        try{

            SimpleDateFormat sdf = new SimpleDateFormat("MMddyy");
           String s= sdf.format(Calendar.getInstance().getTime());

            path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/QRCodeScanner/PDF Files/"+s;
            dir = new File(path);
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

    private void sendMessage(final String filepath) {
        final ProgressDialog dialog = new ProgressDialog(PdfActivity.this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("shifnaprojects@gmail.com", "shifna@6810");
                   // sender.sendMail("EmailSender App", "this is an email", "shifnaprojects@gmail.com", "shifnashahida60@gmail.com");
                    String sub="PFA";
                    String body="report";
                    String from="shifnaprojects@gmail.com";
                    String to="shifnashahida60@gmail.com";
                    sender.sendmailwithattachment(sub,body,from,to,filepath);

                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("mylog", "Error: " + e.getMessage());
                    dialog.dismiss();
                }
            }
        });
        sender.start();
    }
    public void createPDF() throws FileNotFoundException, DocumentException {

        //create document file
        Document doc = new Document();
        try {



            Log.e("PDFCreator", "PDF Path: " + path);

            file = new File(dir, "QRCodePDF" + t+ ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter.getInstance(doc, fOut);

            //open the document
            doc.open();

//create table

           /*
           PdfPTable pt = new PdfPTable(3);
            pt.setWidthPercentage(100);
            float[] fl = new float[]{20, 45, 35};
            pt.setWidths(fl);

           cell = new PdfPCell();
            cell.setBorder(Rectangle.NO_BORDER);

            //set drawable in cell
            Drawable myImage = PdfActivity.this.getResources().getDrawable(R.mipmap.ic_launcher);
            Bitmap bitmap = ((BitmapDrawable) myImage).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] bitmapdata = stream.toByteArray();*/
            try {
               /* bgImage = Image.getInstance(bitmapdata);
                bgImage.setAbsolutePosition(330f, 642f);
                cell.addElement(bgImage);
                pt.addCell(cell);*/
               /* cell = new PdfPCell();
                cell.setBorder(Rectangle.NO_BORDER);
                cell.addElement(new Paragraph("Product Details"));
*/
               /* cell.addElement(new Paragraph(""));
                cell.addElement(new Paragraph(""));
                pt.addCell(cell);
                cell = new PdfPCell(new Paragraph(""));
                cell.setBorder(Rectangle.NO_BORDER);*/
            //    pt.addCell(cell);





                PdfPTable table = new PdfPTable(4);

                float[] columnWidth = new float[]{10, 30, 20,20};
                table.setWidths(columnWidth);


                cell = new PdfPCell(new Phrase("SI.NO"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Product ID"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Count"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Date"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);

                //table.setHeaderRows(3);
                cell = new PdfPCell();
                cell.setColspan(4);

                for (int i = 1; i <= 10; i++) {
                    table.addCell(String.valueOf(i));
                    table.addCell("Product ID " + i);
                    table.addCell("Count " + i);
                    table.addCell("Date " + i);

                }

                cell = new PdfPCell();
                cell.setColspan(4);
                table.addCell("Total Count");
                table.addCell("");
                table.addCell("");
                table.addCell("100");


                doc.add(table);
               // Toast.makeText(getApplicationContext(), "created PDF", Toast.LENGTH_LONG).show();

            } catch (DocumentException de) {
                Log.e("PDFCreator", "DocumentException:" + de);
            } catch (Exception e) {
                Log.e("PDFCreator", "ioException:" + e);
            } finally {
                doc.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onPause() {
        super.onPause();

        if(progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }


}






