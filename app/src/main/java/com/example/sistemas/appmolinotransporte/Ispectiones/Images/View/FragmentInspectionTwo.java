package com.example.sistemas.appmolinotransporte.Ispectiones.Images.View;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.opengl.Visibility;
import android.os.Bundle;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;

import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.example.sistemas.appmolinotransporte.HomeActivity;
import com.example.sistemas.appmolinotransporte.R;
import butterknife.BindView;
import butterknife.BindViews;
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
public class FragmentInspectionTwo extends Fragment {


    /*Para la inyecion de vista utilzamos la libreria butternike la cual nos
    * simplifica gran parte del trabajo de momento iniciaremos inflando lo elementos
    * de manera inplicita y ordenadamente desde los Linerlaoyout*/





    ImageButton photoBefore;
    ImageButton photoDuring;
    ImageButton photoAfter;
    LinearLayout containerBefore;
    LinearLayout containerAfter;
    LinearLayout containerDuring;
    @BindViews({R.id.imgsaveBefore,R.id.imgsaveduring,R.id.imgsaveAfter})
    List<ImageView> imgsave;
    @BindViews({R.id.bodyBefore,R.id.bodyDuring,R.id.bodyAfter})
    List<RelativeLayout> bodys;

    @BindView(R.id.saveImagesBefore)
    ImageButton saveImagesBefore;
    @BindView(R.id.saveImagesDuring)
    ImageButton saveImagesDuring;
    @BindView(R.id.saveImagesAfter)
    ImageButton saveImagesAfter;

    String URL_IMAGE = "";
    private String photoPathTemp = "";
    File photo = null;

    private List<File> imagesBefore = new ArrayList<>();
    private List<File> imageDunring =  new ArrayList<>();
    private List<File> imagesAfter = new ArrayList<>();

    private  int contadorImageBefore = 0;
    private  int contadorImaDuring = 0;
    private  int contadorImageAfter = 0;

    //private static final int IMAGE_PICK = 5;
    private static final int ACTION_BEFORE = 1;
    private static final int ACTION_DURING = 2;
    private static final int ACTION_AFTER = 3;
    private boolean registreBefore;
    private boolean registreduring;
    private boolean registreAfter;

    private String factura;
    public FragmentInspectionTwo() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inspection_two, container, false);
         ButterKnife.bind(this, view);
        photoBefore = (ImageButton) view.findViewById(R.id.newPhotoCaptureBefore);
        photoDuring = (ImageButton) view.findViewById(R.id.newPhotoCaptureDuring);
        photoAfter = (ImageButton) view.findViewById(R.id.newPhotoCaptureAfter);
        containerBefore = (LinearLayout) view.findViewById(R.id.imgContentAntes);
        containerDuring = (LinearLayout) view.findViewById(R.id.imgContentDuring);
        containerAfter = (LinearLayout) view.findViewById(R.id.imgContentAfeter);
        HomeActivity homeActivity = new HomeActivity();
        factura = homeActivity.getFacturaFinal();
        choseFromCamera(photoBefore);
        choseFromCamera(photoDuring);
        choseFromCamera(photoAfter);
        sendData(saveImagesBefore);
        sendData(saveImagesDuring);
        sendData(saveImagesAfter);
        return view;
    }
    public void choseFromCamera(final ImageButton imageButton){
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (intent.resolveActivity(getActivity().getPackageManager()) != null)
                {
                    photo = null;
                    try
                    {
                        photo = createTemporaryFile();
                        photo.delete();
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    if(photo != null)
                    {
                        Uri photoUri = FileProvider.getUriForFile(getContext(),"com.example.sistemas.appmolinotransporte",photo);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT,photoUri);
                        if (imageButton == photoBefore)
                        {
                            startActivityForResult(intent,ACTION_BEFORE);
                        }else if (imageButton ==photoDuring)
                        {
                            startActivityForResult(intent,ACTION_DURING);
                        }else {
                            startActivityForResult(intent,ACTION_AFTER);
                        }
                    }
                }
            }
        });

    }

    private File createTemporaryFile()
            throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date());
        String imagefileName =  timeStamp+factura;
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photo = File.createTempFile(imagefileName,".jpg",storageDir);
        photoPathTemp = "file:" +photo.getAbsolutePath();
        return  photo;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ACTION_BEFORE && resultCode == Activity.RESULT_OK)
        {
            loadFileImage(containerBefore);

        }else  if (requestCode == ACTION_DURING && resultCode == Activity.RESULT_OK)
        {
            loadFileImage(containerDuring);
        }else if (requestCode == ACTION_AFTER && resultCode == Activity.RESULT_OK)
        {
            loadFileImage(containerAfter);
        }

    }
    private void loadFileImage(LinearLayout container)
    {
        URL_IMAGE = photo.getAbsolutePath();
        Bitmap image2 = BitmapFactory.decodeFile(photo.getAbsolutePath());
        BitmapProcessor bm2 = new BitmapProcessor(image2, 400 , 360);
        addImage(container,bm2.getBitmap());
    }
    private void addImage(final LinearLayout layoutInflater, final Bitmap image) {
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View v = inflater.inflate(R.layout.imagepicture, null, false);
        final FrameLayout frameLayout = (FrameLayout)v.findViewById(R.id.contimgframe);
        final ImageView imgselect  = (ImageView) v.findViewById(R.id.imgselect);
        final ImageButton delete = (ImageButton)v.findViewById(R.id.delete);
        addFileImage(layoutInflater,photo);
        imgselect.setImageBitmap(image);
        imgselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getContext(),"tocada" +URL_IMAGE,Toast.LENGTH_SHORT).show();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 imgselect.setImageBitmap(null);
                 Uri uri =  null;
                 imgselect.setImageURI(uri);
                 File photo =new File(uri.getPath());
                 deleteFileImage(layoutInflater,photo);
            }
        });
        layoutInflater.addView(v);
    }
    public void addFileImage(LinearLayout layoutInflater, File photo)
    {
        if (layoutInflater ==containerBefore)
        {
            imagesBefore.add(contadorImageBefore++,photo);



        }else if(layoutInflater ==containerDuring)
        {
            imageDunring.add(contadorImaDuring++,photo);


        }else {
            imagesAfter.add(contadorImageAfter++,photo);
        }
    }
    public void deleteFileImage(LinearLayout layoutInflater, File photo)
    {
        if (layoutInflater ==containerBefore)
        {
            imagesBefore.remove(photo);
        }else if(layoutInflater ==containerDuring)
        {
            imageDunring.remove(photo);
        }else {
            imagesAfter.remove(photo);

        }

    }
/*@OnClick(R.id.be)
public void enviar()
{
    saveFormServer(imagesBefore,"1212","antes");
}*/
public void sendData(final ImageButton imageButton)
{
    imageButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (factura == null)
            {
                HomeActivity homeActivity = new HomeActivity();
                factura = homeActivity.getFacturaFinal();
            }
            if (factura == null)
            {
                Toast.makeText(getContext(),"Selecione un cargue por favor",Toast.LENGTH_SHORT).show();
                return;
            }

            if (imageButton == saveImagesBefore)
            {
                if (imagesBefore.size() == 0)
                {
                    Toast toast = Toast.makeText(getContext(),"no hay fotos para guardar",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }else {
                    uploadServer(imagesBefore,factura,"Antes");
                }
            }else if (imageButton == saveImagesDuring)
            {
                if (imageDunring.size() == 0)
                {
                    Toast toast = Toast.makeText(getContext(),"no hay fotos para guardar",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }else {
                    uploadServer(imageDunring,factura,"Durante");
                }

            }else
            {
                if (imagesAfter.size() ==0)
                {
                    Toast toast = Toast.makeText(getContext(),"no hay fotos para guardar",Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                else {
                    uploadServer(imagesAfter,factura,"Despues");
                }
            }
        }
    });
}

public boolean registreFull()
{
    if (registreAfter && registreBefore && registreduring)
    {
        return  true;
    }
  return false;
}

int sizeList ;
    public void uploadServer(final List<File> file , String numberFactura, final String tiempo) {
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Gurdando Fotos");
        progressDialog.show();
        OkHttpClient okHttpClient =  new OkHttpClient();
        Retrofit.Builder reBuilder = new Retrofit.Builder()
                .baseUrl("http://192.168.119.30/Transporte/WebServicePHP/SaveToEvidenImage/")
                .client(okHttpClient);
        Retrofit retrofit = reBuilder.build();
        DataImgServer dataImgServer = retrofit.create(DataImgServer.class);
        sizeList = file.size();
        for ( int i = 0; i < file.size() ;i++)
        {
            final int contador = i;
            RequestBody factura = RequestBody.create(MultipartBody.FORM,numberFactura);
            RequestBody estado = RequestBody.create(MultipartBody.FORM,tiempo);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file.get(i));
            MultipartBody.Part body = MultipartBody.Part.createFormData("uploadedfile",file.get(i).getName(),requestBody);
            Call<ResponseBody> call = dataImgServer.posDateImage(body,estado,factura);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    Log.e(TAG,response.toString());
                    if(response.isSuccessful() && contador <= sizeList)
                    {
                        progressDialog.dismiss();
                        if (tiempo.equalsIgnoreCase("Antes"))
                        {
                            bodys.get(0).setVisibility(View.INVISIBLE);
                            imgsave.get(0).setImageDrawable(getResources().getDrawable(R.drawable.ic_save_server_image));
                            registreBefore = true;
                        }
                        if (tiempo.equalsIgnoreCase("Durante"))
                        {
                            bodys.get(1).setVisibility(View.INVISIBLE);
                            imgsave.get(1).setImageDrawable(getResources().getDrawable(R.drawable.ic_save_server_image));
                            registreduring = true;
                        }
                        if(tiempo.equalsIgnoreCase("Despues"))
                        {
                            bodys.get(2).setVisibility(View.INVISIBLE);
                            imgsave.get(2).setImageDrawable(getResources().getDrawable(R.drawable.ic_save_server_image));
                            registreAfter = true;
                        }
                    }
                    //file.remove(file.get(contador));
                }
                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Log.e(TAG, t.toString());
                    Toast.makeText(getContext(),"no se ha podido guardar la evidencia fotografica de "+ tiempo,Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();

                }
            });

        }
























       /* final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Gurdando Datos");
        progressDialog.show();

        OkHttpClient okHttpClient = new OkHttpClient();
        Retrofit.Builder reBuilder = new Retrofit.Builder()
                .baseUrl("http://192.168.100.74/Transporte/WebServicePHP/SaveToEvidenImage/")
                .client(okHttpClient);
        Retrofit retrofit = reBuilder.build();
        DataImgServer dataImgServer = retrofit.create(DataImgServer.class);
        //ServiceDatos serviceDatos = retrofit.create(ServiceDatos.class);
        RequestBody factura = RequestBody.create(MultipartBody.FORM, numberfactura);
        RequestBody estado = RequestBody.create(MultipartBody.FORM, tiempo);
        for (int i = 0; i < file.size(); i++) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file.get(i));
            MultipartBody.Part body = MultipartBody.Part.createFormData("uploadedfile", file.get(i).getName(), requestBody);
        Log.e("iniciando--------",body.body().toString());
       Log.e("imagenenviada",file.get(i).toString());

        Call<ResponseBody> call = dataImgServer.posDateImage(body,factura,estado);
        call.enqueue(new Callback<ResponseBody>() {
          @Override
          public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
              Log.e("mierda",response.toString());
              if (response.isSuccessful())
              {
                  progressDialog.dismiss();
              }
          }

          @Override
          public void onFailure(Call<ResponseBody> call, Throwable t) {
              Log.e(TAG, t.toString());
              progressDialog.dismiss();
          }
      });
    }

*/

    }

}







/*if (requestCode == ACTION_BEFORE || requestCode == ACTION_DURING ||requestCode ==ACTION_AFTER
                && resultCode == Activity.RESULT_OK)
        {
            switch (requestCode) {
                case IMAGE_PICK:
                    Uri selectedImage = data.getData();
                    String [] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    cursor.moveToFirst();
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    String filePath = cursor.getString(columnIndex);
                    cursor.close();
                    URL_IMAGE =filePath;
                    Bitmap image = BitmapFactory.decodeFile(URL_IMAGE);
                    BitmapProcessor bm = new BitmapProcessor(image, 400 , 360);
                    //addImage(bm.getBitmap());
                    break;
                case IMAGE_CAPTURE:

                    break;
                default:
                    break;
            }

        }*/
