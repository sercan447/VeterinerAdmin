package sercandevops.com.veterineradmin.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sercandevops.com.veterineradmin.Adapters.KampanyaAdapter;
import sercandevops.com.veterineradmin.Model.KampanyaEkleModel;
import sercandevops.com.veterineradmin.Model.KampanyaModel;
import sercandevops.com.veterineradmin.Model.KampanyaSilModel;
import sercandevops.com.veterineradmin.R;
import sercandevops.com.veterineradmin.RestApi.ManagerAll;
import sercandevops.com.veterineradmin.Utils.Warnings;

public class KampanyaFragment extends Fragment {

    private View view;
    private RecyclerView recyclerViewKampanya;
    private List<KampanyaModel> kampanyaModels;
    private KampanyaAdapter kampanyaAdapter;
    private MaterialButton btnKampanyaEkleAlertdialog;

    private ImageView img_kampanyaResim;
   private Bitmap bitmap = null;
   private String resimKodla = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view =  inflater.inflate(R.layout.fragment_kampanya, container, false);

        Tanimla();
        getKampanya();
        OpenKampanyaEklAlertDilaog();

        return view;
    }


        public void Tanimla()
        {
            btnKampanyaEkleAlertdialog = (MaterialButton)view.findViewById(R.id.btnKampanyaEkleAlertdialog);
            recyclerViewKampanya = (RecyclerView)view.findViewById(R.id.recylerKampanya);
            RecyclerView.LayoutManager eng = new GridLayoutManager(getContext(),1);
            recyclerViewKampanya.setLayoutManager(eng);
            kampanyaModels = new ArrayList<>();


        }
        public void OpenKampanyaEklAlertDilaog()
        {
            btnKampanyaEkleAlertdialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openKampanyaAlert();
                }
            });
        }

    public void getKampanya()
    {
        final Call<List<KampanyaModel>> req = ManagerAll.getInstance().getKampanya();

        req.enqueue(new Callback<List<KampanyaModel>>() {
            @Override
            public void onResponse(Call<List<KampanyaModel>> call, Response<List<KampanyaModel>> response) {

                if(response.body().get(0).isTf())
                {
                   // Log.i("tavla",response.body().toString());
                    kampanyaModels = response.body();

                    kampanyaAdapter = new KampanyaAdapter(getContext(),kampanyaModels,getActivity());
                    recyclerViewKampanya.setAdapter(kampanyaAdapter);

                }else
                {
                   // Log.i("tavla","veri gelmiyor");
                }
            }
            @Override
            public void onFailure(Call<List<KampanyaModel>> call, Throwable t) {

            }
        });
    }//FUNC

    public void openKampanyaAlert()
    {
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.alertkampanyainsert,null);

        final EditText ed_kampanyaBaslik = (EditText)view.findViewById(R.id.ed_kampanyaBaslik);
        final EditText ed_kampanyaIcerik = (EditText)view.findViewById(R.id.ed_kampanyaIcerik);

         img_kampanyaResim = (ImageView)view.findViewById(R.id.img_kampanyaResim);

        Button btn_kampanyaEkle = (Button)view.findViewById(R.id.btn_kampanyaEkle);
        Button btn_kampanyaresimEkle = (Button)view.findViewById(R.id.btn_kampanyaresimEkle);

         final AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);

        final AlertDialog alertDialog = alert.create();

        btn_kampanyaEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!imageToString().equals("") && !ed_kampanyaBaslik.getText().toString().equals("")
                                                && !ed_kampanyaIcerik.getText().toString().equals(""))
                {
                    resimKodla = imageToString();
                        kampanyaEkle(ed_kampanyaBaslik.getText().toString(),ed_kampanyaIcerik.getText().toString(),resimKodla,alertDialog);
                        ed_kampanyaBaslik.setText("");
                        ed_kampanyaIcerik.setText("");

                }else
                {
                    Toast.makeText(getContext(),"BOŞ ALAN MEVCUT",Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                }
            }//onclick
        });

        btn_kampanyaresimEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(getContext(),"e",Toast.LENGTH_SHORT).show();
                GaleriAc();
            }
        });



        alertDialog.show();

    }//FUNC

    void GaleriAc()
    {
        bitmap =  BitmapFactory.decodeResource(getResources(),R.drawable.kedimama);
        img_kampanyaResim.setImageBitmap(bitmap);
        img_kampanyaResim.setVisibility(View.VISIBLE);

        /*
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent,777);
        */
    }//func


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 777 && resultCode == Activity.RESULT_OK && data != null)
        {
            Uri path = data.getData();

            try
            {
                /*
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),path);
                img_kampanyaResim.setImageBitmap(bitmap);
                img_kampanyaResim.setVisibility(View.VISIBLE);
                */
            }catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }


    public String imageToString()
    {
        if(bitmap != null)
        {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
            byte[] byt = byteArrayOutputStream.toByteArray();
            String imageString = Base64.encodeToString(byt,Base64.DEFAULT);

            return imageString;
         }
         return "";
    }//FUNC
    public void kampanyaEkle(String baslik,String bilgi,String imageString, final AlertDialog alertDialog)
    {

        Call<KampanyaEkleModel> req = ManagerAll.getInstance().KampanyaEkle(baslik,bilgi,imageString);
        req.enqueue(new Callback<KampanyaEkleModel>() {
            @Override
            public void onResponse(Call<KampanyaEkleModel> call, Response<KampanyaEkleModel> response) {

                if(response.body().isTf())
                {
                    getKampanya();
                    alertDialog.cancel();

                }else
                {
                    Toast.makeText(getContext(),"Ekleme  problem istf : false",Toast.LENGTH_SHORT).show();
                    alertDialog.cancel();
                }

            }

            @Override
            public void onFailure(Call<KampanyaEkleModel> call, Throwable t) {
                Toast.makeText(getContext(),Warnings.internetProblemtext,Toast.LENGTH_SHORT).show();
                Log.i("kampanyaeklehata",t.toString());
            }
        });

    }//FUNX


}
