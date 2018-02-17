package com.example.sistemas.appmolinotransporte.Productos.View;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.sistemas.appmolinotransporte.MainActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Set;

/**
 * Created by sistemas on 15/02/18.
 */

public class HtmlData extends AsyncTask<Void,Void,Void> {

    private  String url;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }



    @Override
    protected Void doInBackground(Void... params) {
        try {
            Document document = Jsoup.connect("http://192.168.100.74/Transporte/facturas-despachos/TRANSPORTE.html").get();
            document.title();
            Log.e("pagina",document.title());
            String el;
            Elements element = document.getElementsByAttribute("22");
            Elements elements = document.getElementsByClass("s16");
            for(Element e : elements){
                Log.e("producto",e.ownText());
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;



    }
}



