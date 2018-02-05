package com.example.sistemas.appmolinotransporte.DriverAccept.View;

import android.app.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;


import java.io.File;
import java.io.FileOutputStream;

import java.io.OutputStream;


import java.util.UUID;
import android.graphics.Bitmap;

import com.example.sistemas.appmolinotransporte.R;

import butterknife.ButterKnife;




public class LienzoImg extends Activity {


    ImageButton delete;
    ImageButton saveImg;
    private  String imgSave;
    private  static  Firma firma;
    private String RUTA;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lienzo_img);
        ButterKnife.bind(this);
        firma =   (Firma)findViewById(R.id.firma);
        delete =  (ImageButton) findViewById(R.id.borrar);
        saveImg = (ImageButton)findViewById(R.id.guardar);

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImgLiezo();
            }
        });
        saveImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImgLienzo();
            }
        });
    }
    public void deleteImgLiezo()
    {
        firma.NuevoDibujo();
    }
    public  void saveImgLienzo()
    {
        String nameimg = UUID.randomUUID().toString()+".png";
          firma.buildDrawingCache();
          Bitmap bitmap = firma.getDrawingCache();
        OutputStream fileOutStream = null;

        try {
            File file = new File(Environment. getExternalStorageDirectory()+File.separator+Environment.DIRECTORY_PICTURES);
            file.mkdirs();
            File directorioImagenes = new File(file, nameimg);
            uri = Uri.fromFile(directorioImagenes);
            imgSave = uri.toString();
            Log.e("ruta de archivo ",imgSave);
            fileOutStream = new FileOutputStream(directorioImagenes);

        } catch (Exception e) {
            Log.e("ERROR!", e.getMessage());
        }
        try {
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutStream);
            fileOutStream.flush();
            fileOutStream.close();
        } catch (Exception e) {
            Log.e("ERROR!", e.getMessage());
        }
            if(uri!=null){
                Log.e("enlinezon de la firma",uri.getPath());
                Intent intent = new Intent();
                intent.putExtra("URI",uri.toString());
                setResult(RESULT_OK,intent);
                finish();

            }
         else {
            Toast.makeText(this, "Firme Antes De guardar" , Toast.LENGTH_SHORT).show();
        }

        firma.destroyDrawingCache();
    }

    /*private String getCurrentDateAndTime() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH-mm-­ss");
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }*/

}
