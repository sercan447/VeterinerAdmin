package sercandevops.com.veterineradmin.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sercandevops.com.veterineradmin.Adapters.VeterinerSoruAdapter;
import sercandevops.com.veterineradmin.Model.SoruModel;
import sercandevops.com.veterineradmin.R;
import sercandevops.com.veterineradmin.RestApi.ManagerAll;
import sercandevops.com.veterineradmin.Utils.ChangeFragments;


public class SorularFragment extends Fragment {


    View v;
    RecyclerView recyclerView_Sorular;
    List<SoruModel> list;
    private VeterinerSoruAdapter veterinerSoruAdapter;
    ChangeFragments changeFragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_sorular, container, false);

        tanimlama();
        istekAt();


        return v;
    }

    public void tanimlama()
    {
        recyclerView_Sorular = (RecyclerView)v.findViewById(R.id.recylerview_Sorular);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        recyclerView_Sorular.setLayoutManager(layoutManager);

        changeFragments = new ChangeFragments(getContext());
        list = new ArrayList<>();
    }

    public void istekAt()
    {
        final Call<List<SoruModel>> req = ManagerAll.getInstance().getSorular();
        req.enqueue(new Callback<List<SoruModel>>() {
            @Override
            public void onResponse(Call<List<SoruModel>> call, Response<List<SoruModel>> response) {

                if(response.body().get(0).isTf())
                {
                    list = response.body();
                    veterinerSoruAdapter = new VeterinerSoruAdapter(getContext(),list,getActivity());
                    recyclerView_Sorular.setAdapter(veterinerSoruAdapter);

                }else
                {
                    changeFragments.change(new HomeFragment());
                }
            }

            @Override
            public void onFailure(Call<List<SoruModel>> call, Throwable t) {


            }
        });
    }//FUNC


}
