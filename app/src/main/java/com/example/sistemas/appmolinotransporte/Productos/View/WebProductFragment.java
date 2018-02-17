package com.example.sistemas.appmolinotransporte.Productos.View;



import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemas.appmolinotransporte.Home.View.HomeActivity;
import com.example.sistemas.appmolinotransporte.Productos.Interface.ApiData;
import com.example.sistemas.appmolinotransporte.Productos.Model.ProducModel;
import com.example.sistemas.appmolinotransporte.R;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class WebProductFragment extends Fragment {


    public WebProductFragment() {
        // Required empty public constructor
    }
    @BindView(R.id.destinoCargue)
    TextView destino;
    @BindView(R.id.newWebview)
    LinearLayout conteWebview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_web_product, container, false);
        ButterKnife.bind(this,view);
        HomeActivity homeActivity = new HomeActivity();
        getProductos(homeActivity.getFacturaFinal());
        return view;
    }



private void addAutoWebviw(String factura,String url) {
    LayoutInflater inflater = LayoutInflater.from(getActivity());
    View v = inflater.inflate(R.layout.webviewproducts, null, false);
    final WebView webView =    (WebView)v.findViewById(R.id.contenFactura);
    final  TextView textView = (TextView)v.findViewById(R.id.numeroFacturaProducto);
    webView.loadUrl(url);
    WebSettings webSettings =  webView.getSettings();
    webSettings.setJavaScriptEnabled(true);
    textView.setText(factura);
    webView.setWebViewClient(new WebViewClient());
    conteWebview.addView(v);
}

public void refreshLayout(){
        conteWebview.removeAllViews();
}


        public  void getProductos(String factura){
            final ProgressDialog progressDialog;
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Obteniendo Facturas");
            progressDialog.show();
            Retrofit retrofit =  new Retrofit.Builder()
                    .baseUrl("http://192.168.119.30/Transporte/WebServicePHP/Productos/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            ApiData apiData = retrofit.create(ApiData.class);
            Call<List<ProducModel>> call =  apiData.getProductos(factura);
            call.enqueue(new Callback<List<ProducModel>>() {
                @Override
                public void onResponse(Call<List<ProducModel>> call, Response<List<ProducModel>> response) {
                    if (response.isSuccessful())
                    {
                        progressDialog.dismiss();
                        destino.setText(response.body().get(0).getDestino());
                        for (int i = 0; i <response.body().size() ; i++) {
                            addAutoWebviw(response.body().get(i).getConsecutivo(),response.body().get(i).getFactura());;
                        }
                    }
                }
                @Override
                public void onFailure(Call<List<ProducModel>> call, Throwable t) {
                    progressDialog.dismiss();
                    Toast.makeText(getContext(),"Ocurrio un error revise su conexión",Toast.LENGTH_LONG).show();

                }
            });
        }


}
