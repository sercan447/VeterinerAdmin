
package sercandevops.com.veterineradmin.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sercandevops.com.veterineradmin.Model.KampanyaModel;
import sercandevops.com.veterineradmin.R;
import sercandevops.com.veterineradmin.RestApi.ManagerAll;
import sercandevops.com.veterineradmin.Utils.ChangeFragments;


public class HomeFragment extends Fragment {


    private View v;
    private LinearLayout kampanyaLayout,asiTakipLayout,linearlayout_Sorular,linearKullanicilar;
    private ChangeFragments changeFragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v =  inflater.inflate(R.layout.fragment_home, container, false);
        tanimla();
        clickToLayout();

        return v;
    }

    public void  tanimla()
    {
        kampanyaLayout = v.findViewById(R.id.layoutKampanya);
        changeFragments = new ChangeFragments(getContext());
        asiTakipLayout = v.findViewById(R.id.linearlayout_askitakip);
        linearlayout_Sorular = v.findViewById(R.id.linearlayout_Sorular);
        linearKullanicilar = v.findViewById(R.id.linearKullanicilar);


    }

    public void clickToLayout()
    {
        kampanyaLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new KampanyaFragment());
            }
        });

        asiTakipLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new AsiTakipFragment());
            }
        });

        linearlayout_Sorular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new SorularFragment());
            }
        });
        linearKullanicilar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragments.change(new KullaniciFragment());
            }
        });
    }







}
