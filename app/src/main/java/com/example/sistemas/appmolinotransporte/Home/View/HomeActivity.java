package com.example.sistemas.appmolinotransporte.Home.View;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemas.appmolinotransporte.DespachoConductores.Interface.UpdateDataDespachado;
import com.example.sistemas.appmolinotransporte.DespachoConductores.View.NotifiFragment;
import com.example.sistemas.appmolinotransporte.DriverAccept.View.FragmentFirm;
import com.example.sistemas.appmolinotransporte.Home.Interface.ApiServer;
import com.example.sistemas.appmolinotransporte.Home.Model.ConductorModel;
import com.example.sistemas.appmolinotransporte.Ispectiones.Images.View.FragmentInspectionTwo;
import com.example.sistemas.appmolinotransporte.Ispectiones.IspectionInocuidad.View.FragmentInspection;
import com.example.sistemas.appmolinotransporte.Ispectiones.ServiceData;
import com.example.sistemas.appmolinotransporte.R;
import com.example.sistemas.appmolinotransporte.Productos.View.WebProductFragment;
import com.google.firebase.iid.FirebaseInstanceId;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.BottomBarTab;
import com.roughike.bottombar.OnTabSelectListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {


    BottomBar bottomBar;


    FragmentInspectionTwo fragmentInspectionTwo;
    FragmentInspection fragmentInspection;
    NotifiFragment notifiFragment;
    FragmentTransaction ft;
    WebProductFragment webProductFragment;
    FragmentFirm fragmentFirm;

    private static  int newnoitifi;
    private BottomBarTab notification;
//private boolean abierta;

    private static  String ID_FACTURA;
    private static  String ID_CEDULA;


    @BindView(R.id.DateInspection)
    TextView dateInpection;
    @BindView(R.id.hourStart)
    TextView hourStart;
    @BindView(R.id.consecutiveNumber)
    TextView consecutiveNumber;
    @BindView(R.id.nameDriver)
    TextView nameDriver;
    @BindView(R.id.dniDriver)
    TextView dniDriver;
    @BindView(R.id.numberPlaca)
    TextView numberPlaca;
    @BindView(R.id.buttonPlaca)
    RelativeLayout placabuton;
    /**con esta variable  facturaVerificate se verifica
     *que  si el bundle viene vacion consulte con la factura
     * que esta almacenada en cache*/
    private  String facturaVerificate;
    final Context context = this;

//CondutorViewFragmnet condutorViewFragmnet;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bottomBar = (BottomBar) findViewById(R.id.bottombar);
        notification = (BottomBarTab) findViewById(R.id.notification);
        notification = bottomBar.getTabWithId(R.id.notification);
        fragmentFirm = new FragmentFirm();
        ButterKnife.bind(this);
        fragmentInspectionTwo = new FragmentInspectionTwo();
        webProductFragment = new WebProductFragment();
        fragmentInspection = new FragmentInspection();
        notifiFragment = new NotifiFragment();
        bottomBar.setDefaultTab(R.id.tab_truck_images);
        prueba(newnoitifi);
        token();
        placabuton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
                alertDialog.setTitle("Finalizar")
                        .setMessage("Terminar este Cargue")
                        .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(getFacturaFinal() != null)updateCargueFinish(getFacturaFinal());
                                else Toast.makeText(getApplicationContext(),"Inicie un Cargue",Toast.LENGTH_LONG).show();

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                alertDialog.show();
            }


        });

        Bundle parametro = this.getIntent().getExtras();
        if(parametro != null){
            facturaVerificate = parametro.getString("factura");
            //getDataFactura(facturaCache());
        }
        if (facturaVerificate == null)
        {
            getDataFactura(facturaCache());
        }else {
            getDataFactura(facturaVerificate);
        }

        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(int tabId) {
                if (tabId == R.id.tab_driver) {
                    ft = getSupportFragmentManager().beginTransaction();
                    if (fragmentFirm.isAdded()) {
                        ft.show(fragmentFirm);
                    } else {
                        ft.add(R.id.contentFragment, fragmentFirm);
                    }
                    hideFragment(ft, fragmentInspection);
                    hideFragment(ft, fragmentInspectionTwo);
                    hideFragment(ft, notifiFragment);
                    hideFragment(ft,webProductFragment);
                    ft.commit();
                }

                if (tabId == R.id.tab_truck_images) {
                    ft = getSupportFragmentManager().beginTransaction();

                    if (fragmentInspectionTwo.isAdded()) {
                        ft.show(fragmentInspectionTwo);
                    } else {
                        ft.add(R.id.contentFragment, fragmentInspectionTwo);
                    }
                    hideFragment(ft, fragmentFirm);
                    hideFragment(ft, fragmentInspection);
                    hideFragment(ft, notifiFragment);
                    hideFragment(ft,webProductFragment);
                    ft.commit();
                }
                if (tabId == R.id.tab_verification) {
                    ft = getSupportFragmentManager().beginTransaction();
                    if (fragmentInspection.isAdded()) {
                        ft.show(fragmentInspection);
                    } else {
                        ft.add(R.id.contentFragment, fragmentInspection);
                    }
                    hideFragment(ft, fragmentFirm);
                    hideFragment(ft, fragmentInspectionTwo);
                    hideFragment(ft, notifiFragment);
                    hideFragment(ft,webProductFragment);
                    ft.commit();
                }
                if (tabId == R.id.notification) {
                    ft = getSupportFragmentManager().beginTransaction();
                    if (notifiFragment.isAdded())
                    {
                        ft.show(notifiFragment);
                        notifiFragment.loadData();
                    }
                    else
                    {
                        ft.add(R.id.contentFragment, notifiFragment);
                    }
                    hideFragment(ft,fragmentFirm);
                    hideFragment(ft,fragmentInspectionTwo);
                    hideFragment(ft,fragmentInspection);
                    hideFragment(ft,webProductFragment);
                    ft.commit();
                }
                if (tabId == R.id.webSearch) {
                    ft = getSupportFragmentManager().beginTransaction();
                    if (webProductFragment.isAdded())
                    {
                        ft.show(webProductFragment);
                        webProductFragment.refreshLayout();
                        webProductFragment.getProductos(getFacturaFinal());
                        //notifiFragment.loadData();
                    }else {
                        ft.add(R.id.contentFragment, webProductFragment);
                    }

                    hideFragment(ft,fragmentFirm);
                    hideFragment(ft,fragmentInspectionTwo);
                    hideFragment(ft,fragmentInspection);
                    hideFragment(ft,notifiFragment);
                    ft.commit();
                }
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

        prueba(newnoitifi);
    }
    @Override
    protected void onPause() {
        super.onPause();
        //showBadgeDot(newnoitifi,notification);
        prueba(newnoitifi);
    }
    protected boolean sihay(){
        ++newnoitifi;
        System.out.println("contador de notificaciones"+newnoitifi);
        return true;
    }
    public void prueba(int count)
    {
        System.out.println("------+++al inicio del metodo---++++");
        BottomBarTab tab = bottomBar.getTabWithId(R.id.notification);

        if (tab != null)
        {
            tab.setBadgeCount(count);
        }
        if (tab == null)
        {
            tab = bottomBar.getTabWithId(R.id.notification);
            tab.setBadgeCount(count);
        }
    }
    private void hideFragment(FragmentTransaction ft, Fragment f) {
        if (f.isAdded()) {
            ft.hide(f);
        }
    }

    public void token()
    {
        String token = FirebaseInstanceId.getInstance().getToken();
        System.out.println(token);
    }
    public  void SaveStatusFactura(String factura)
    {
        SharedPreferences sharedPreferences = getSharedPreferences("prefrencias", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("factura",factura);
        editor.commit();
    }
    public String facturaCache()
    {
        SharedPreferences sharedPreferences =  getSharedPreferences("prefrencias",Context.MODE_PRIVATE);
        String facturaCache = sharedPreferences.getString("factura","1212");
        return facturaCache;

    }



    public  void setDataDriver(ConductorModel conductorModel)
    {
        dateInpection.setText(conductorModel.getFecha());
        hourStart.setText(conductorModel.getHourStart());
        consecutiveNumber.setText(conductorModel.getConsecutivo());
        nameDriver.setText(conductorModel.getName());
        dniDriver.setText(conductorModel.getCedula());
        numberPlaca.setText(conductorModel.getPlaca());
        SaveStatusFactura(conductorModel.getConsecutivo());

    }
    public void  SetFacturaFinal(String factura)
    {
        ID_FACTURA =factura;
    }
    public void setCedulaFinal(String cedula)
    {
        ID_CEDULA = cedula;
    }
    public  String  getFacturaFinal()
    {
        return  ID_FACTURA;
    }
    public  String getCedulaFinal()
    {
        return ID_CEDULA;
    }


    public  void getDataFactura(String factura){
        Retrofit retrofit =  new Retrofit.Builder()
                .baseUrl("http://192.168.119.30/Transporte/WebServicePHP/Notification/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ServiceData serviceData = retrofit.create(ServiceData.class);

        Call<List<ConductorModel>> call =  serviceData.getConductor(factura);

        call.enqueue(new Callback<List<ConductorModel>>() {
            @Override
            public void onResponse(Call<List<ConductorModel>> call, Response<List<ConductorModel>> response) {
                Log.e("factura",response.body().get(0).getCedula());
                if (response.isSuccessful())
                {
                    setCedulaFinal(response.body().get(0).getCedula());
                    SetFacturaFinal(response.body().get(0).getConsecutivo());
                    setDataDriver(response.body().get(0));
                }
                //Toast.makeText(getContext(),"valor"+response.body().getCedula(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<List<ConductorModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"error de conexion",Toast.LENGTH_LONG).show();

            }
        });

    }
    public void updateCargueFinish(final String consecutivo)
    {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Guardando Cargue");
        progressDialog.show();


        OkHttpClient okHttpClient =  new OkHttpClient();
        Retrofit.Builder reBuilder = new Retrofit.Builder()
                .baseUrl("http://192.168.119.30/Transporte/WebServicePHP/Cargues/")
                .client(okHttpClient);
        Retrofit retrofit = reBuilder.build();
        ApiServer apiServer = retrofit.create(ApiServer.class);
        apiServer.updateCargueDespachando(consecutivo).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    progressDialog.dismiss();
                    Intent intent =  new Intent(getApplicationContext(),HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
       progressDialog.dismiss();
                Toast.makeText(getApplicationContext(),"Error revise su conexión intentelo de nuevo",Toast.LENGTH_SHORT).show();
            }
        });




    }

}





//ConductorModel conductorModel =  new ConductorModel(response.body().get(0).getCedula(),response.body().get(0).getName()
//,response.body().get(0).getApellido().toString(),response.body().get(0).getPlaca().toString(),response.body().get(0).getEstado()
//     ,response.body().get(0).getHourStart()
//


/*
*    public boolean comprobarActivityALaVista(
            Context context, String nombreClase){

        // Obtenemos nuestro manejador de activitys
        ActivityManager am = (ActivityManager)
                context.getSystemService(Context.ACTIVITY_SERVICE);
        // obtenemos la informacion de la tarea que se esta ejecutando
        // actualmente
        List< ActivityManager.RunningTaskInfo > taskInfo =
                am.getRunningTasks(1);
        // Creamos una variable donde vamos a almacenar
        // la activity que se encuentra a la vista
        String nombreClaseActual = null;

        try{
            // Creamos la variable donde vamos a guardar el objeto
            // del que vamos a tomar el nombre
            ComponentName componentName = null;
            // si pudimos obtener la tarea actual, vamos a intentar cargar
            // nuestro objeto
            if(taskInfo != null && taskInfo.get(0) != null){
                componentName = taskInfo.get(0).topActivity;
            }
            // Si pudimos cargar nuestro objeto, vamos a obtener
            // el nombre con el que vamos a comparar
            if(componentName != null){
                nombreClaseActual = componentName.getClassName();
            }

        }catch (NullPointerException e){

            Log.e("ssd", "Error al tomar el nombre de la clase actual " + e);
            return false;
        }

        // devolvemos el resultado de la comparacion
        Log.e("clase",nombreClaseActual);
        return nombreClase.equals(nombreClaseActual);
    }*/

