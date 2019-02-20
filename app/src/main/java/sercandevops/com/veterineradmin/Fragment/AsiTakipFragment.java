package sercandevops.com.veterineradmin.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sercandevops.com.veterineradmin.Adapters.PetAsiTakipAdapter;
import sercandevops.com.veterineradmin.Model.PetAsiTakipModel;
import sercandevops.com.veterineradmin.R;
import sercandevops.com.veterineradmin.RestApi.ManagerAll;
import sercandevops.com.veterineradmin.Utils.ChangeFragments;
import sercandevops.com.veterineradmin.Utils.Warnings;


public class AsiTakipFragment extends Fragment {


    private View view;
    private DateFormat format;
    private Date date;
    ChangeFragments changeFragments;
    ImageView imgAsiBack;

    private RecyclerView asiTakipRecylcerview;
    private  List<PetAsiTakipModel> list;
    private PetAsiTakipAdapter petAsiTakipAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_asi_takip, container, false);
        tanimlama();
        TiklamaDurumu();
        return view;

    }

    public void tanimlama() {
        format = new SimpleDateFormat("dd/MM/yyyy");
        date = Calendar.getInstance().getTime();
        String today = format.format(date);

        imgAsiBack = view.findViewById(R.id.imgAsiBack);
        asiTakipRecylcerview = (RecyclerView)view.findViewById(R.id.recylerview_AsiTakip);
        list = new ArrayList<>();


        changeFragments = new ChangeFragments(getContext());
        Log.i("bugun",today);
        AsiListele(today);
    }
    private void TiklamaDurumu()
    {
        imgAsiBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.changeParameters(new HomeFragment(),"homeAsiTAG");
            }
        });
    }

    public void AsiListele(String tarih)
    {
        Call<List<PetAsiTakipModel>> req = ManagerAll.getInstance().getAsiTakip(tarih);
        req.enqueue(new Callback<List<PetAsiTakipModel>>() {
            @Override
            public void onResponse(Call<List<PetAsiTakipModel>> call, Response<List<PetAsiTakipModel>> response) {

                if(response.body().get(0).isTf())
                {
                    list = response.body();
                    petAsiTakipAdapter = new PetAsiTakipAdapter(getContext(),list,getActivity());
                    RecyclerView.LayoutManager ly = new GridLayoutManager(getContext(),1);

                    asiTakipRecylcerview.setLayoutManager(ly);
                    asiTakipRecylcerview.setAdapter(petAsiTakipAdapter);

                    Toast.makeText(getContext(),"Bugun :"+response.body().size()+" pete aşı yapılacaktır.",Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(getContext(),"Bugun Yokk",Toast.LENGTH_SHORT).show();
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<PetAsiTakipModel>> call, Throwable t) {
                Toast.makeText(getContext(),Warnings.internetProblemtext,Toast.LENGTH_SHORT).show();
            }
        });
    }



}
