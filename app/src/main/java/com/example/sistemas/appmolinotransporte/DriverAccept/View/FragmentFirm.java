package com.example.sistemas.appmolinotransporte.DriverAccept.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.example.sistemas.appmolinotransporte.HomeActivity;
import com.example.sistemas.appmolinotransporte.R;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFirm extends Fragment {

    private CheckBox acepto;
    private static  final int IMG_LIENZO = 1;
    private ImageView imageViewLienzo;
    private Button bndguardar;
    private RelativeLayout relativeLayout;
    private RelativeLayout relativeLayoutload;
    Uri uri;
    private  boolean guardado = false;

    public FragmentFirm() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_firma, container, false);
        ButterKnife.bind(this,view);
        imageViewLienzo = (ImageView) view.findViewById(R.id.imgFirmj);
        acepto = (CheckBox) view.findViewById(R.id.ckeckAcepto);
        bndguardar = (Button) view.findViewById(R.id.btnguarda);
        relativeLayout = (RelativeLayout) view.findViewById(R.id.sucesFull);
        relativeLayoutload = (RelativeLayout) view.findViewById(R.id.susesLoad);
        relativeLayout.setVisibility(View.INVISIBLE);
        relativeLayoutload.setVisibility(View.VISIBLE);
        final HomeActivity homeActivity =  new HomeActivity();

        System.out.println("conductor"+ homeActivity.getFacturaFinal());
        System.out.println("cedula"+ homeActivity.getCedulaFinal());
        showFirm();
        bndguardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Guardar")
                        .setMessage("Si la firma es correcta por favor guarde")
                        .setPositiveButton("continuar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (acepto.isChecked())
                                {
                                    if (homeActivity.getFacturaFinal() == null && homeActivity.getCedulaFinal() == null)
                                    {
                                        Toast.makeText(getContext(),"Seleccione un Cargue",Toast.LENGTH_LONG).show();
                                    }else
                                    {
                                        saveFormServer(homeActivity.getFacturaFinal(),homeActivity.getCedulaFinal());
                                    }

                                }else {
                                    Toast.makeText(getContext(),"Error! el conductor debe aceptar los terminos",Toast.LENGTH_LONG).show();
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
        });
        return view;
    }

    public void showFirm()
    {
        acepto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (acepto.isChecked() == true){
                    Intent intent =  new Intent(getActivity(), LienzoImg.class);
                    startActivityForResult(intent,IMG_LIENZO);

                }else imageViewLienzo.setImageBitmap(null);
            }
        });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMG_LIENZO && resultCode == Activity.RESULT_OK)
        {
            uri = Uri.parse(data.getStringExtra("URI"));
            Picasso.with(getContext()).load(uri).into(imageViewLienzo);
            acepto.setEnabled(true);

        }else {
                acepto.setChecked(false);

        }
    }
    public boolean saveDataServerFirm()
    {
        if (guardado == true)
        {
            return true;
        }
        return false;
    }

    private String getPath(Uri uri) {

        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContext().getContentResolver().query(uri, projection, null, null,null);

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }


    public void saveFormServer(String idfactura, String idConductor)
    {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Gurdando la Firma");
        progressDialog.show();
        File file = new File(uri.getPath());
        OkHttpClient okHttpClient =  new OkHttpClient();
        Retrofit.Builder reBuilder = new Retrofit.Builder()
                .baseUrl("http://192.168.119.30/Transporte/WebServicePHP/SaveToPostImgDriverAcepto/")
                .client(okHttpClient);
        Retrofit retrofit = reBuilder.build();
        ServiceDatos serviceDatos = retrofit.create(ServiceDatos.class);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("uploadedfile",file.getName(),requestBody);
        System.out.println("valor "+ idfactura);
        System.out.println("valor cedula"+  idConductor);
        RequestBody factura = RequestBody.create(MultipartBody.FORM,idfactura);
        RequestBody cedula = RequestBody.create(MultipartBody.FORM,idConductor);
        Call<ResponseBody> call = serviceDatos.postImage(body,factura,cedula);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.e(TAG,response.toString());
                progressDialog.dismiss();
                if(response.isSuccessful())
                {
                    relativeLayoutload.setVisibility(View.INVISIBLE);
                    relativeLayout.setVisibility(View.VISIBLE);
                    guardado = true;
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, t.toString());

            }
        });


    }

}
