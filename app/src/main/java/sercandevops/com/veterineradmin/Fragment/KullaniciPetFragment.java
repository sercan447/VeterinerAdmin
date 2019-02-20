package sercandevops.com.veterineradmin.Fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sercandevops.com.veterineradmin.Adapters.PetAdapter;
import sercandevops.com.veterineradmin.Model.PetEkle;
import sercandevops.com.veterineradmin.Model.PetModel;
import sercandevops.com.veterineradmin.R;
import sercandevops.com.veterineradmin.RestApi.ManagerAll;
import sercandevops.com.veterineradmin.Utils.ChangeFragments;
import sercandevops.com.veterineradmin.Utils.Warnings;

public class KullaniciPetFragment extends Fragment {

    public KullaniciPetFragment() {

    }

    private String musId;
    private View v;
    private ChangeFragments changeFragments;

    private RecyclerView userPetListRecy;
    private ImageView img_Resimyok;
    private TextView tv_PetUyariMesaji;
    private Button btn_petEkle;

    private List<PetModel> petModelList;
    private PetAdapter petAdapter;

    Bitmap bitmap;
    ImageView img_petResim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_kullanici_pet, container, false);

        userPetListRecy = (RecyclerView)v.findViewById(R.id.userPetlistRecView);
        RecyclerView.LayoutManager lyt = new GridLayoutManager(getContext(),2);
        userPetListRecy.setLayoutManager(lyt);

        img_Resimyok = (ImageView) v.findViewById(R.id.img_petEkleResimYok);
        tv_PetUyariMesaji = (TextView) v.findViewById(R.id.tv_bilgiUyari);
        btn_petEkle = (Button)v.findViewById(R.id.btnPetlerInPetEkle);

        bitmap = null;

        tanimla();
        getPets(musId);
        click();

        return v;
    }

    public void tanimla()
    {
        //gelen petId degil. Musteriye ait ID cekiyoruz suan
        musId = getArguments().getString("petId");
        Log.i("TAGS",""+musId);

        changeFragments = new ChangeFragments(getContext());
    }

    public void getPets(String id)
    {
        final Call<List<PetModel>> req = ManagerAll.getInstance().getPets(id);
        req.enqueue(new Callback<List<PetModel>>() {
            @Override
            public void onResponse(Call<List<PetModel>> call, Response<List<PetModel>> response) {
                if(response.body().get(0).isTf())
                {

                    img_Resimyok.setVisibility(View.GONE);
                    tv_PetUyariMesaji.setVisibility(View.GONE);
                    userPetListRecy.setVisibility(View.VISIBLE);

                    petModelList = response.body();
                    petAdapter = new PetAdapter(getContext(),petModelList,getActivity(),musId);

                    userPetListRecy.setAdapter(petAdapter);

                    Toast.makeText(getContext(),"Kullanıcıya ait pet sayısı :."+response.body().size(),Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(getContext(),"Kullanıcıya ait pet bulunamamıştır.",Toast.LENGTH_SHORT).show();

                    img_Resimyok.setVisibility(View.VISIBLE);
                    tv_PetUyariMesaji.setVisibility(View.VISIBLE);
                    userPetListRecy.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<PetModel>> call, Throwable t) {
                Toast.makeText(getContext(),Warnings.internetProblemtext,Toast.LENGTH_SHORT).show();

            }
        });
    }//FUNC

    public void click()
    {
        btn_petEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PetEkleAlert();
            }
        });
    }//FUNC
    public void PetEkleAlert()
    {
        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.peteklelayout,null);

        final EditText ed_petisim = view.findViewById(R.id.ed_petEkleName);
        final EditText ed_pettur = view.findViewById(R.id.ed_petEkleTur);
        final EditText ed_petcins = view.findViewById(R.id.ed_petEkleCins);
        img_petResim = view.findViewById(R.id.img_petResim);


        Button btnekle = view.findViewById(R.id.btn_petEkle);
        Button btnresim = view.findViewById(R.id.btn_PetresimEkle);


        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);

        final AlertDialog alertDialog = alert.create();


        btnekle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                petEkle(musId,ed_petisim.getText().toString(),ed_petcins.getText().toString(),
                                    ed_pettur.getText().toString(),imageToString(),alertDialog);

            }
        });

        btnresim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GaleriAc();
            }
        });

        alertDialog.show();

    }//FUNC

    void GaleriAc()
    {
        bitmap =  BitmapFactory.decodeResource(getResources(),R.drawable.kedimama);
        img_petResim.setImageBitmap(bitmap);
        img_petResim.setVisibility(View.VISIBLE);

        /*
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);

        startActivityForResult(intent,777);
        */

    }//func
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

        public void petEkle(String musid, String isim, String tur, String cins, final String resim, final AlertDialog dialog)
        {
            Call<PetEkle> req = ManagerAll.getInstance().petEkle(musid,isim,tur,cins,resim);
            req.enqueue(new Callback<PetEkle>() {
                @Override
                public void onResponse(Call<PetEkle> call, Response<PetEkle> response) {

                    if (response.body().isTf())
                    {
                        Toast.makeText(getContext(),"BAŞARILI PET KAYDI",Toast.LENGTH_SHORT).show();
                        getPets(musId);
                        dialog.cancel();
                    }else
                    {
                        Toast.makeText(getContext(),"kayıt olmamıs",Toast.LENGTH_SHORT).show();

                        dialog.cancel();
                    }
                }
                @Override
                public void onFailure(Call<PetEkle> call, Throwable t) {
                    Toast.makeText(getContext(),Warnings.internetProblemtext,Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                }
            });
        }//CLASS

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
    }//FUNC



}
