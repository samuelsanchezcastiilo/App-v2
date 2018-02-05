package com.example.sistemas.appmolinotransporte;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;



import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebProductFragment extends Fragment {


    public WebProductFragment() {
        // Required empty public constructor
    }
    WebView webView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_web_product, container, false);
        ButterKnife.bind(this,view);
        webView =  (WebView)view.findViewById(R.id.contenFactura);
        webView.loadUrl("http://192.168.100.74/Transporte/WebServicePHP/FacturaPdf/ViewPdf.php");
        WebSettings webSettings =  webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());

        //192.168.100.74/Transporte/WebServicePHP/FacturaPdf/1517.pdf
        return view;
    }

}
