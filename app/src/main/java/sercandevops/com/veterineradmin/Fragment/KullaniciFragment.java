package sercandevops.com.veterineradmin.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sercandevops.com.veterineradmin.Adapters.UserAdapter;
import sercandevops.com.veterineradmin.Model.KullanicilarModel;
import sercandevops.com.veterineradmin.R;
import sercandevops.com.veterineradmin.RestApi.ManagerAll;
import sercandevops.com.veterineradmin.Utils.ChangeFragments;

public class KullaniciFragment extends Fragment {

    View view;

    RecyclerView recyclerView;
    List<KullanicilarModel> kullanicilarModelList;
    UserAdapter userAdapter;
    ImageView imageview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_kullanici, container, false);

            Tanimlama();

        return view;
    }
    public void Tanimlama()
    {
        imageview = view.findViewById(R.id.imgKullaBack);
        recyclerView = view.findViewById(R.id.recylerview_Kullanicilar);
        kullanicilarModelList = new ArrayList<>();

        getKullanicilar();

        RecyclerView.LayoutManager lyt = new GridLayoutManager(getContext(),1);
        recyclerView.setLayoutManager(lyt);

        final ChangeFragments changeFragments = new ChangeFragments(getActivity());

        imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeFragments.changeParameters(new HomeFragment(),"homeKullTAG");
            }
        });

    }

    public void getKullanicilar()
    {
        Call<List<KullanicilarModel>> req = ManagerAll.getInstance().getKullanicilar();

        req.enqueue(new Callback<List<KullanicilarModel>>() {
            @Override
            public void onResponse(Call<List<KullanicilarModel>> call, Response<List<KullanicilarModel>> response) {
                kullanicilarModelList = response.body();

                userAdapter = new UserAdapter(getContext(),kullanicilarModelList,getActivity(),1);
                recyclerView.setAdapter(userAdapter);
                Log.i("INSANN",response.body().toString());

            }

            @Override
            public void onFailure(Call<List<KullanicilarModel>> call, Throwable t) {
                    Log.i("INSANN",t.toString());
            }
        });
    }

}
