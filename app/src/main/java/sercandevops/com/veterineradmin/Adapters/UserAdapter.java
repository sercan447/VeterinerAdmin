package sercandevops.com.veterineradmin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sercandevops.com.veterineradmin.Fragment.KullaniciPetFragment;
import sercandevops.com.veterineradmin.Model.CevaplaModel;
import sercandevops.com.veterineradmin.Model.KullaniciSil;
import sercandevops.com.veterineradmin.Model.KullanicilarModel;
import sercandevops.com.veterineradmin.Model.SoruModel;
import sercandevops.com.veterineradmin.R;
import sercandevops.com.veterineradmin.RestApi.ManagerAll;
import sercandevops.com.veterineradmin.Utils.ChangeFragments;
import sercandevops.com.veterineradmin.Utils.Warnings;

public class UserAdapter extends RecyclerView.Adapter {


    Context context;
    List<KullanicilarModel> kullanicilarModelList;
    Activity activity;
    View  v;
    ChangeFragments changeFragments;
    int gorunum =0;

    public UserAdapter(Context context, List<KullanicilarModel> kullanicilarModelList, Activity activity,int gorunum) {
        this.context = context;
        this.kullanicilarModelList = kullanicilarModelList;
        this.activity = activity;
        this.gorunum = gorunum;

        changeFragments = new ChangeFragments(context);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        switch (i)
        {
            case 1:
                v = LayoutInflater.from(context).inflate(R.layout.kullanicilayout,viewGroup,false);
                return new MyViewHolder(v);
            case 2:
                v = LayoutInflater.from(context).inflate(R.layout.kullanicilayout2,viewGroup,false);
                return new MyViewHolder2(v);
        }


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder myViewHolder, final int i) {

        switch (gorunum)
        {
            case 1:

                ((MyViewHolder)myViewHolder).tv_kullaniciText.setText(kullanicilarModelList.get(i).getKadi());

                ((MyViewHolder)myViewHolder).btnAramaYap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arama(kullanicilarModelList.get(i).getTelefon());
                    }
                });

                ((MyViewHolder)myViewHolder).btnPetler.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        changeFragments.changeParameters(new KullaniciPetFragment(), kullanicilarModelList.get(i).getId().toString());
                    }
                });
                ((MyViewHolder)myViewHolder).lienarlayout_kullanici.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertKullanici(Integer.parseInt(kullanicilarModelList.get(i).getId()));
                    }
                });

                break;
            case 2:
                break;
        }

    }

    @Override
    public int getItemCount() {
        return kullanicilarModelList.size();
    }

    @Override
    public int getItemViewType(int position) {

        switch (gorunum)
        {
            case 1:
                return 1;
            case 2:
                return 2;
        }

        return -1;
    }

    public void deleteToList(int position)
    {
        kullanicilarModelList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void arama(String numara)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:"+numara));
        activity.startActivity(intent);
    }

        public void AlertKullanici(final int position)
        {
            LayoutInflater layoutInflater = activity.getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.kullanici_sil,null );
            Button userTamambtn = view.findViewById(R.id.btnUserSil);
            Button userCikisbtn = view.findViewById(R.id.btnUserCikis);

            final AlertDialog.Builder alert = new AlertDialog.Builder(context);
            alert.setView(view);
            alert.setCancelable(true);
            final AlertDialog alertDialog = alert.create();

            userTamambtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    kullaniciSil(kullanicilarModelList.get(position).getId(),position);
                    deleteToList(position);
                    alertDialog.cancel();
                }
            });
            userCikisbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.cancel();
                }
            });

            alertDialog.show();
        }//fnc

    public void kullaniciSil(String id ,int position)
    {
        Call<KullaniciSil> req = ManagerAll.getInstance().kullaniciSil(id);
        req.enqueue(new Callback<KullaniciSil>() {
            @Override
            public void onResponse(Call<KullaniciSil> call, Response<KullaniciSil> response) {
                if(response.body().isTf())
                {
                    Toast.makeText(  context,response.body().getText().toString(),Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(  context,response.body().getText().toString(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<KullaniciSil> call, Throwable t) {
                Toast.makeText(  context, Warnings.internetProblemtext,Toast.LENGTH_LONG).show();
            }
        });
    }//FUNC


    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        CardView cardview_kullanicilar;
        CircleImageView img_kullaniciImage1;

        TextView tv_kullaniciText;
        Button btnPetler,btnAramaYap;
        LinearLayout lienarlayout_kullanici;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardview_kullanicilar = (CardView)itemView.findViewById(R.id.cardview_kullanicilar);
            img_kullaniciImage1 = (CircleImageView)itemView.findViewById(R.id.img_kullaniciImage1);
            tv_kullaniciText = (TextView) itemView.findViewById(R.id.tv_kullaniciText);
            btnPetler = (Button) itemView.findViewById(R.id.btnPetEkle);
            btnAramaYap = (Button) itemView.findViewById(R.id.btnAramaYap);
            lienarlayout_kullanici = itemView.findViewById(R.id.lienarlayout_kullanici);

        }
    }//CLASS

    public class MyViewHolder2 extends RecyclerView.ViewHolder
    {

        CardView cardview_kullanicilar;
        CircleImageView img_kullaniciImage1;

        TextView tv_kullaniciText;
        Button btnPetler,btnAramaYap;
        LinearLayout lienarlayout_kullanici;


        public MyViewHolder2(@NonNull View itemView) {
            super(itemView);

            cardview_kullanicilar = (CardView)itemView.findViewById(R.id.cardview_kullanicilar);
            img_kullaniciImage1 = (CircleImageView)itemView.findViewById(R.id.img_kullaniciImage1);
            tv_kullaniciText = (TextView) itemView.findViewById(R.id.tv_kullaniciText);
            btnPetler = (Button) itemView.findViewById(R.id.btnPetEkle);
            btnAramaYap = (Button) itemView.findViewById(R.id.btnAramaYap);
            lienarlayout_kullanici = itemView.findViewById(R.id.lienarlayout_kullanici);

        }
    }//CLASS
}
