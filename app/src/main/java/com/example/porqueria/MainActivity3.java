package com.example.porqueria;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity3 extends AppCompatActivity {

    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "/Percentile generate" + ".pdf");
    int drawableTemp, drawableTemp2, drawableTemp3, drawableTemp4;

    public void openDownloadsFolder() {
        Uri path = Uri.parse(Environment.getExternalStorageDirectory() + "/");
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(path, "*/*");
        startActivity(intent);
    }

    double x_weight, y_weight, x_height, y_height,y_HfW, x_HfW, x_HC, y_HC, x_IMC, y_IMC;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //import buttons from xml, without 2= girls; with 2 = boys;
        TextView button_height_and_weight_percentile = findViewById(R.id.button_height_and_weight_percentile);
        TextView button_weight_for_height_percentile = findViewById(R.id.button_weight_for_height_percentile);
        TextView button_IMC_percentile = findViewById(R.id.button_IMC_percentile);
        TextView button_PC_percentile = findViewById(R.id.button_PC_percentile);

        // select image if age over 2 or under, girl or boy for height & weight percentile
        if (Porqueria.addTotalMonths >= 24 && Porqueria.addGender.equals("Masculin")) {
            drawableTemp = R.drawable.height_and_weight_boys_over_2;
        }
        if (Porqueria.addTotalMonths >= 24 && Porqueria.addGender.equals("Feminin")) {
            drawableTemp = R.drawable.height_and_weight_girls_over_2;
        }
        if (Porqueria.addTotalMonths < 24 && Porqueria.addGender.equals("Feminin")) {
            drawableTemp = R.drawable.height_and_weight_girls_under_2;
        }
        if (Porqueria.addTotalMonths < 24 && Porqueria.addGender.equals("Masculin")) {
            drawableTemp = R.drawable.length_weight_boys_under_2;
        }
        // set point coordinates according to gender and age
        if (Porqueria.addTotalMonths >= 24) {
            x_weight = 795 + 163 * (Porqueria.addYears - 2) + 13.58 * Porqueria.addMonths;
            y_weight = (5139 - 16.3 * (Porqueria.addWeight - 10));
            x_height = 795 + 163 * (Porqueria.addYears - 2) + 13.58 * Porqueria.addMonths;
            y_height = 4100 - 32.6 * (Porqueria.addHeight - 80);
        }
        if (Porqueria.addTotalMonths < 24) { // !!!!!!!!!!!!!!!!!!!!!!!!! aici de completat
            x_weight = 795 + 163 * (Porqueria.addYears - 2) + 13.58 * Porqueria.addMonths;
            y_weight = (5139 - 16.3 * (Porqueria.addWeight - 10));
            x_height = 795 + 163 * (Porqueria.addYears - 2) + 13.58 * Porqueria.addMonths;
            y_height = 4100 - 32.6 * (Porqueria.addHeight - 80);
        }

        // boys, height, weight
        button_height_and_weight_percentile.setOnClickListener(view -> {
            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), drawableTemp);

            PdfDocument pdfDocument = new PdfDocument();
            Paint myPaint = new Paint();
            Paint pointHeight = new Paint();
            Paint pointWeight = new Paint();
            pointWeight.setColor(getColor(R.color.design_default_color_error));
            pointHeight.setColor(getColor(R.color.design_default_color_error));
            PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(bitmap1.getWidth(), bitmap1.getHeight(), 1).create();
            PdfDocument.Page page = pdfDocument.startPage(myPageInfo);
            Canvas canvas = page.getCanvas();
            Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), false);
            canvas.drawBitmap(bitmap2, 0, 0, myPaint);
            bitmap2.getScaledWidth(canvas);
            bitmap2.getScaledHeight(canvas);
            canvas.drawCircle((float) x_weight, (float) y_weight, 20, pointWeight);
            canvas.drawCircle((float) x_height, (float) y_height, 20, pointHeight);
            pdfDocument.finishPage(page);

            try {
                pdfDocument.writeTo(new FileOutputStream(file));
                Toast.makeText(MainActivity3.this, "PDF generated in Downloads", Toast.LENGTH_SHORT).show();
                //openDownloadsFolder();

            } catch (IOException e) {
                e.printStackTrace();
            }
            pdfDocument.close();
        });

        // select image for age and sex
        if (Porqueria.addGender.equals("Feminin")){
            drawableTemp2 = R.drawable.weight_to_length_girl;}
        if (Porqueria.addGender.equals("Masculin")){
            drawableTemp2 = R.drawable.weight_to_length_boys;}

        button_weight_for_height_percentile.setOnClickListener(view -> {

            if (Porqueria.addHeight >= 80 && Porqueria.addHeight <=120
            && Porqueria.addWeight > 8 && Porqueria.addWeight <26) {

                Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), drawableTemp2);

                PdfDocument pdfDocument = new PdfDocument();
                Paint myPaint = new Paint();
                Paint pointHeight = new Paint();
                Paint pointWeight = new Paint();
                pointWeight.setColor(getColor(R.color.design_default_color_error));
                pointHeight.setColor(getColor(R.color.design_default_color_error));
                PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(bitmap1.getWidth(), bitmap1.getHeight(), 1).create();
                PdfDocument.Page page = pdfDocument.startPage(myPageInfo);
                Canvas canvas = page.getCanvas();
                Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), false);
                canvas.drawBitmap(bitmap2, 0, 0, myPaint);
                bitmap2.getScaledWidth(canvas);
                bitmap2.getScaledHeight(canvas);
                canvas.drawCircle((float) x_HfW, (float) y_HfW, 20, pointWeight);
                pdfDocument.finishPage(page);

                try {
                    pdfDocument.writeTo(new FileOutputStream(file));
                    Toast.makeText(MainActivity3.this, "PDF generated in Downloads", Toast.LENGTH_SHORT).show();
                    //openDownloadsFolder();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                pdfDocument.close();
            }
            else Toast.makeText(MainActivity3.this, "Greutatea trebuie sa fie intre 8 si 26 kg si talie intre 80 si 120 cm", Toast.LENGTH_SHORT).show();

        });

        // select image for age and sex, head circumference
        if (Porqueria.addGender.equals("Feminin")){
            drawableTemp3 = R.drawable.head_circumference_girls_under_2;}
        if (Porqueria.addGender.equals("Masculin")){
            drawableTemp3 = R.drawable.head_circumference_girls_under_2;}

        // select coordinates
        x_HC = 0;
        y_HC = 0;

        button_PC_percentile.setOnClickListener(view -> {
            if (Porqueria.addTotalMonths <=24) {
                Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), drawableTemp3);

                PdfDocument pdfDocument = new PdfDocument();
                Paint myPaint = new Paint();
                Paint pointHeight = new Paint();
                Paint pointWeight = new Paint();
                pointWeight.setColor(getColor(R.color.design_default_color_error));
                pointHeight.setColor(getColor(R.color.design_default_color_error));
                PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(bitmap1.getWidth(), bitmap1.getHeight(), 1).create();
                PdfDocument.Page page = pdfDocument.startPage(myPageInfo);
                Canvas canvas = page.getCanvas();
                Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), false);
                canvas.drawBitmap(bitmap2, 0, 0, myPaint);
                bitmap2.getScaledWidth(canvas);
                bitmap2.getScaledHeight(canvas);
                canvas.drawCircle((float) x_HC, (float) y_HC, 20, pointWeight);
                pdfDocument.finishPage(page);

                try {
                    pdfDocument.writeTo(new FileOutputStream(file));
                    Toast.makeText(MainActivity3.this, "PDF generated in Downloads", Toast.LENGTH_SHORT).show();
                    //openDownloadsFolder();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                pdfDocument.close();
            }
            else Toast.makeText(MainActivity3.this, "Varsta copilului trebuie sa fie sub 2 ani", Toast.LENGTH_SHORT).show();

        });

        // select image for age and sex, head circumference
        if (Porqueria.addGender.equals("Feminin")){
            drawableTemp4 = R.drawable.imc_girls_over_2;}
        if (Porqueria.addGender.equals("Masculin")){
            drawableTemp4 = R.drawable.imc_boys_over_2;}

        // select coordinates
        x_IMC = 0;
        y_IMC = 0;

        button_IMC_percentile.setOnClickListener(view -> {
            if (Porqueria.addTotalMonths > 24) {
                Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), drawableTemp4);

                PdfDocument pdfDocument = new PdfDocument();
                Paint myPaint = new Paint();
                Paint pointHeight = new Paint();
                Paint pointWeight = new Paint();
                pointWeight.setColor(getColor(R.color.design_default_color_error));
                pointHeight.setColor(getColor(R.color.design_default_color_error));
                PdfDocument.PageInfo myPageInfo = new PdfDocument.PageInfo.Builder(bitmap1.getWidth(), bitmap1.getHeight(), 1).create();
                PdfDocument.Page page = pdfDocument.startPage(myPageInfo);
                Canvas canvas = page.getCanvas();
                Bitmap bitmap2 = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), false);
                canvas.drawBitmap(bitmap2, 0, 0, myPaint);
                bitmap2.getScaledWidth(canvas);
                bitmap2.getScaledHeight(canvas);
                canvas.drawCircle((float) x_IMC, (float) y_IMC, 20, pointWeight);
                pdfDocument.finishPage(page);

                try {
                    pdfDocument.writeTo(new FileOutputStream(file));
                    Toast.makeText(MainActivity3.this, "PDF generated in Downloads", Toast.LENGTH_SHORT).show();
                    //openDownloadsFolder();

                } catch (IOException e) {
                    e.printStackTrace();
                }
                pdfDocument.close();
            }
            else Toast.makeText(MainActivity3.this, "Varsta copilului trebuie sa fie peste 2 ani", Toast.LENGTH_SHORT).show();

        });

        //goes back to the first activity
        TextView back1 = findViewById(R.id.back1);
        back1.setOnClickListener(v -> finish());

    }

}


// saving file locally on the phone =---> asta merge
                /*
                Drawable drawable = getResources().getDrawable(R.drawable.height_and_weight_girls_over_2);
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                ImagePath = MediaStore.Images.Media.insertImage(
                        getContentResolver(),
                        bitmap,
                        "demo_image",
                        "demo_image"
                );
                URI = Uri.parse(ImagePath);
                Toast.makeText(MainActivity4.this, "Image Saved Successfully", Toast.LENGTH_LONG).show();
                }
                */