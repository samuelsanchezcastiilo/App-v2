package com.example.sistemas.appmolinotransporte.Ispectiones.IspectionInocuidad.View;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sistemas.appmolinotransporte.ClienteServer;
import com.example.sistemas.appmolinotransporte.Home.View.HomeActivity;
import com.example.sistemas.appmolinotransporte.R;

import java.util.List;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
/**
 * @author samuel sanchez desarrollador de Aplicaciones moviles*/
/**
 * A simple {@link Fragment} subclass.
 */

public class FragmentInspection extends Fragment {


    @BindViews({R.id.barrido, R.id.detergentes, R.id.residuosSolidos, R.id.excrementos, R.id.Olores,
            R.id.productosQuimicos, R.id.combustible, R.id.fumigado,R.id.Infestacion})
    List<CheckBox> check;
    @BindViews({R.id.TipoParteDelantera, R.id.panelIzquierdo, R.id.panelDerecho, R.id.quintaRueda,
            R.id.puertaInteriores, R.id.pataMecanica, R.id.respiradores, R.id.pisos, R.id.vigas,
            R.id.parachoques, R.id.tanques, R.id.bateria, R.id.pasajeroTecho, R.id.cabina})
    List<TextView> tipoPuntosSuceptibles;
    @BindViews({R.id.estadoParteDelanteraE, R.id.estadoParteDelanteraM, R.id.estadoParteDelanteraNA,
            R.id.EstadoPanelIzquierdoN, R.id.EstadoPanelIzquierdoM, R.id.EstadoPanelIzquierdoNA,
            R.id.estadoPanelDerechoN, R.id.estadoPanelDerechoM, R.id.estadoPanelDerechoNA,
            R.id.estadoQuintaRuedaN, R.id.estadoQuintaRuedaM, R.id.estadoQuintaRuedaNA,
            R.id.estadoPuertaInterioresN, R.id.estadoPuertaInterioresM, R.id.estadoPuertaInterioresNA,
            R.id.estadopataMecanicaN, R.id.estadopataMecanicaM, R.id.estadopataMecanicaNA,
            R.id.estadoRespiradoresN, R.id.estadoRespiradoresM, R.id.estadoRespiradoresNa,
            R.id.estadoPisosN, R.id.estadoPisosM, R.id.estadoPisosNA,
            R.id.estadoVigasN, R.id.estadoVigasM, R.id.estadoVigasNA,
            R.id.estadoParachoquesN, R.id.estadoParachoquesM, R.id.estadoParachoquesNA,
            R.id.estadoTanquesN, R.id.estadoTanquesM, R.id.estadoTanquesNA,
            R.id.estadoBateriaN, R.id.estadoBateriaM, R.id.estadoBateriaNA,
            R.id.estadoPasajeroTechoN, R.id.estadoPasajeroTechoM, R.id.estadoPasajeroTechoNA,
            R.id.estadoCabinaN, R.id.estadoCabinaM, R.id.estadoCabinaNA})
    List<CheckBox> checkBoxesEstadosuceptibles;
    @BindViews({R.id.observacionParteDelantera, R.id.observacionesPanelIzquierdo, R.id.observacionesPanelDerecho,
            R.id.observacionesQuintaRueda, R.id.observacionesPuertaInteriores, R.id.observacionesPataMecanica,
            R.id.observacionesRespiradores, R.id.observacionesPisos, R.id.observacionesVigas, R.id.observacionesParachoques,
            R.id.observacionesTanques, R.id.observacionesBateria, R.id.observacionesPasajeroTecho,
            R.id.observacionesCabina})
    List<EditText> editTextObservacionesSuceptibles;

    @BindView(R.id.layoutSaveOk)
    LinearLayout layoutSaveOk;

    @BindView(R.id.layoutConten)
    LinearLayout layoutConten;

    public String[][] inocuida = new String[9][2];
    private String datosSuceptibles[][] = new String[14][3];
    private boolean guardado = false;
    private String factura;



    public FragmentInspection() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inspection, container, false);
        ButterKnife.bind(this, view);
        final HomeActivity homeActivity =  new HomeActivity();
        factura = homeActivity.getFacturaFinal();

        return view;
    }
    @OnClick(R.id.btnGuardar)
    public void confirmSendData() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Guardar")
                .setMessage("guardar ispeccion & calida incuidad")
                .setPositiveButton("continuar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (releadPuntosSuceptibles() != null)
                        {
                            System.out.println("adentro"+ factura);

                            if (factura!= null)
                            {
                                uploadServerIspection(factura,releadPuntosSuceptibles(),relaeadInocuidad());
                            }else
                                Toast.makeText(getContext(),"Seleccione un cargue para realizar los registros",Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        builder.create().show();
    }



    //meotdo para tomar los datos de inocuidad nos retorna los datos
    //que se van a registrar en el servidor
    public String[][] relaeadInocuidad() {
        for (int i = 0; i < inocuida.length; i++) {
            inocuida[i][0] = check.get(i).getText().toString();
            for (int j = 1; j < 2; j++) {
                if (check.get(i).isChecked()) inocuida[i][j] = "si";
                else inocuida[i][j] = "no";
            }
        }
        return inocuida;
    }
    //obtener los datos de inocuidad
   /* public void getDateInocuida() {
        relaeadInocuidad();
        for (int i = 0; i < inocuida.length; i++) {
            for (int j = 1; j < inocuida[0].length; j++) {
                Log.e("valores de inocuida", inocuida[i][j]);
            }
        }
    }*/
    public boolean validarChecks(){
        for (int i = 0; i < 41; ) {
            if (checkBoxesEstadosuceptibles.get(i).isChecked() && checkBoxesEstadosuceptibles.get(i + 1).isChecked()
                    && checkBoxesEstadosuceptibles.get(i + 2).isChecked()) {
                checkBoxesEstadosuceptibles.get(i).setError("error");
                return false;
            }
            if (checkBoxesEstadosuceptibles.get(i).isChecked() && checkBoxesEstadosuceptibles.get(i + 1).isChecked()) {
                checkBoxesEstadosuceptibles.get(i).setError("error");
                return false;
            }
            if (checkBoxesEstadosuceptibles.get(i).isChecked() && checkBoxesEstadosuceptibles.get(i + 2).isChecked()) {
                checkBoxesEstadosuceptibles.get(i).setError("error");
                return false;
            }
            if (checkBoxesEstadosuceptibles.get(i + 1).isChecked() && checkBoxesEstadosuceptibles.get(i + 2).isChecked()) {
                checkBoxesEstadosuceptibles.get(i + 2).setError("error");
                return false;
            }
            i += 3;
        }
        return true;
    }


    public boolean saveDataServerIspection()
    {
        if (guardado == true)
        {
            return true;
        }
        return false;
    }


    public String[] setDatosCheck(){

        String[] select = new String[14];
        if (validarChecks())
        {
            int conteo = 0;
            for (int i = 0; i < checkBoxesEstadosuceptibles.size(); i++)
            {
                if (checkBoxesEstadosuceptibles.get(i).isChecked()) {
                    select[conteo] = checkBoxesEstadosuceptibles.get(i).getText().toString();
                    Log.e("selecionado", select[conteo] + conteo);
                    conteo++;
                }
            }
        }
        return select;

    }







    public String[][] releadPuntosSuceptibles() {
        String [] checks =  setDatosCheck();

        for (int i = 0; i < checks.length ; i++) {

            if (checks[i] == null )
            {
                Toast toast;
                toast = Toast.makeText(getContext(),"Error! debe seleccionar una convencion almenos",Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();
                return null;

            }
        }
        for (int i = 0; i < datosSuceptibles.length; i++) {
            datosSuceptibles[i][0] = tipoPuntosSuceptibles.get(i).getText().toString();
            for (int j = 1; j < 2; j++) {
                datosSuceptibles[i][j] = checks[i];
                datosSuceptibles[i][j+1] = editTextObservacionesSuceptibles.get(i).getText().toString();
                if (datosSuceptibles[i][j+1].equals("") || datosSuceptibles[i][j+1].equals(" "))
                {
                    datosSuceptibles[i][j+1] = "sin observaciones";

                }
            }
        }
        return datosSuceptibles;
    }
    public void uploadServerIspection(String factura, final String[][] datos, String[][] datosIspection)
    {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Gurdando Informacion");
        progressDialog.show();
        OkHttpClient okHttpClient =  new OkHttpClient();
        Retrofit.Builder reBuilder = new Retrofit.Builder()
                .baseUrl("http://192.168.119.30/Transporte/WebServicePHP/SaveToEvidenImage/")
                .client(okHttpClient);
        Retrofit retrofit = reBuilder.build();
        ClienteServer clienteServer = retrofit.create(ClienteServer.class);


        for (int i = 0; i < datosIspection.length ; i++) {
            System.out.println(datosIspection[i][0]);
            for (int j = 1; j < 2  ; j++) {
                System.out.println(datosIspection[i][j]);
                clienteServer.Inocuidad(factura,datosIspection[i][0],datosIspection[i][j]).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(getContext(),"Ocurrio un error revise su conexión",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        return;
                    }
                });
            }
        }
        for ( int i = 0; i <datos.length ; i++) {
            for (int j = 1; j <2 ; j++) {

                clienteServer.Ispection(factura,datos[i][0],datos[i][j],datos[i][j+1]).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.e("sodosd",response.toString());
                        if (response.isSuccessful())
                    {
                        progressDialog.dismiss();
                        layoutConten.setVisibility(View.INVISIBLE);
                        layoutSaveOk.setVisibility(View.VISIBLE);
                        guardado = true;
                    }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t){
                        Toast.makeText(getContext(),"Ocurrio un error revise su conexión",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });
            }
        }
    }




}





























/*
*
     /* String  tipo  [][] =  new String[8][2];
        for (int i = 0; i < 8 ; i++)
        {
            tipo[i][0] = check.get(i).getText().toString();
            for (int j = 1; j < 2; j++)
            {

                //if (check.get(i).isChecked())tipo[i][j] = "si";
                 tipo[i][j] = "no";
            }
        }

        for (int i = 0; i < 7; i++)
        {
            System.out.println();
            for (int j = 0; j < 1; j++)
            {
                //Log.e("tipo",tipo[i][j]);
                System.out.println(tipo[i][j]);
            }
        }
        double acumulado ;
        double interes = 0.10;

        String tipo2 [][] = new String[8][2];

        for (int i = 0; i <8; i++)
        {
            tipo2[i][0]= check.get(i).getText().toString();

            for (int j = 1; j < 2; j++)
            {

                tipo2[i][j]="si";
            }

        }

        for (int i = 0; i < 8; i++)
        {
            System.out.println();
            for (int j = 0; j <2; j++)
            {
                System.out.printf("tipo2"+tipo2[i][j]);
                System.out.print (" ");
            }

        }*/

        /*for (int i = 0; i < check.size() ; i++) {

            for (int j = 0; j <1 ; j++) {
                if (check.get(i).isChecked())
                {
                    elementos[i][j] = check.get(i).getText().toString();
                }


            }

        }*/


   /* private void releadTwoForm()
    {

        for (int i = 0; i < ; i++)
        {

            for (int j = 0; j < ; j++)
            {

            }
        }
    }
*/



             /*for (int i = 0; i < 42;) {
            for (int j = 1; j < 3; j++) {

                if(checkBoxesEstadosuceptibles.get(i).isChecked())
                {
                    System.out.println(checkBoxesEstadosuceptibles.get(i).getText().toString()+":"+i);
                    if (checkBoxesEstadosuceptibles.get(i).isChecked() && checkBoxesEstadosuceptibles.get(j).isChecked())
                    {
                        checkBoxesEstadosuceptibles.get(i).setError("Error");
                    }
                }else {

                    if (checkBoxesEstadosuceptibles.get(j).isChecked() && checkBoxesEstadosuceptibles.get(j+1).isChecked())
                    {
                        checkBoxesEstadosuceptibles.get(j).setError("error");
                    }

                }
                break;

            }
        }*/
       /*if (checkBoxesEstadosuceptibles.get(0).isChecked() && checkBoxesEstadosuceptibles.get(1).isChecked()
        && checkBoxesEstadosuceptibles.get(2).isChecked())
        {
            checkBoxesEstadosuceptibles.get(1).setError("error");
        }if (checkBoxesEstadosuceptibles.get(0).isChecked() && checkBoxesEstadosuceptibles.get(1).isChecked())
    {
        checkBoxesEstadosuceptibles.get(0).setError("error");
    }if (checkBoxesEstadosuceptibles.get(1).isChecked() && checkBoxesEstadosuceptibles.get(2).isChecked())
    {
        checkBoxesEstadosuceptibles.get(0).setError("error");
    }*/

