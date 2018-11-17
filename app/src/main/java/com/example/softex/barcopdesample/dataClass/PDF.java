package com.example.softex.barcopdesample.dataClass;

import android.util.Log;

import com.example.softex.barcopdesample.helper.SharedPrefManager;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.html.WebColors;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by softex on 09-Feb-18.
 */

public class PDF {
    private PdfPCell cell;
    BaseColor myColor1 = WebColors.getRGBColor("#757575");
    private File dir;
    private File file;
    SimpleDateFormat sdf;

    public void createPDF(String path, String t, List<Student> barcodeList) throws FileNotFoundException, DocumentException {

        //create document file
        int l=barcodeList.size();
        Log.e("length",""+l);
        int sum=0,manual=0;
        Document doc = new Document();
        try {



            Log.e("PDFCreator", "PDF Path: " + path);
            dir = new File(path);
            file = new File(dir, "QRCodePDF" + t+ ".pdf");
            FileOutputStream fOut = new FileOutputStream(file);
            PdfWriter.getInstance(doc, fOut);
            doc.open();

            try {

                PdfPTable table = new PdfPTable(6);
                float[] columnWidth = new float[]{20, 20, 20,20,20,20};
                table.setWidths(columnWidth);
                cell = new PdfPCell(new Phrase("SI.NO"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Refference No"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Date"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Vehicle NO"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Count"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);
                cell = new PdfPCell(new Phrase("Manual Count"));
                cell.setBackgroundColor(myColor1);
                table.addCell(cell);


                //table.setHeaderRows(3);
                cell = new PdfPCell();
                cell.setColspan(6);
                int j=1;
                for (int i = 0; i <=l-1; i++)
                {

                    Student barcode = barcodeList.get(i);
                    Log.e("count ",""+barcode.getCount());
                    Log.e("vehicleNo ",""+barcode.getVehicleNo());
                    Log.e("RefNo ",""+barcode.getRefNo());

                    table.addCell(String.valueOf(j));
                    table.addCell(barcode.getContent());
                    table.addCell( barcode.getDate());
                    table.addCell( barcode.getVehicleNo());
                    table.addCell(""+ barcode.getCount());
                    table.addCell(""+ barcode.getManualcount());

                    sum=sum+barcode.getCount();
                    manual=manual+barcode.getManualcount();
                    j=j+1;
                }

                cell = new PdfPCell();
                cell.setColspan(6);
                table.addCell("Total Count");
                table.addCell("");
                table.addCell("");
                table.addCell("");
                table.addCell(String.valueOf(""+sum));
                table.addCell(String.valueOf(""+manual));

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
}
