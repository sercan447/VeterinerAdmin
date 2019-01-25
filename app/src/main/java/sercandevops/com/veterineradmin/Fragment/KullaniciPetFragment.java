package sercandevops.com.veterineradmin.Fragment;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sercandevops.com.veterineradmin.Model.PetModel;
import sercandevops.com.veterineradmin.R;
import sercandevops.com.veterineradmin.RestApi.ManagerAll;
import sercandevops.com.veterineradmin.Utils.ChangeFragments;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        v = inflater.inflate(R.layout.fragment_kullanici_pet, container, false);

        userPetListRecy = (RecyclerView)v.findViewById(R.id.userPetlistRecView);
        img_Resimyok = (ImageView) v.findViewById(R.id.img_petEkleResimYok);
        tv_PetUyariMesaji = (TextView) v.findViewById(R.id.tv_bilgiUyari);
        btn_petEkle = (Button)v.findViewById(R.id.btnPetlerInPetEkle);

        tanimla();
        getPets(musId);

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
                    userPetListRecy.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(),"Kullanıcıya ait pet sayısı :."+response.body().size(),Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(getContext(),"Kullanıcıya ait pet bulunamamıştır.",Toast.LENGTH_SHORT).show();
                    img_Resimyok.setVisibility(View.VISIBLE);
                    tv_PetUyariMesaji.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<List<PetModel>> call, Throwable t) {

            }
        });
    }



}
