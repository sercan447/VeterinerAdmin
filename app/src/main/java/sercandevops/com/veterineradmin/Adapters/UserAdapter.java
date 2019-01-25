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
import android.widget.TextView;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sercandevops.com.veterineradmin.Fragment.KullaniciPetFragment;
import sercandevops.com.veterineradmin.Model.CevaplaModel;
import sercandevops.com.veterineradmin.Model.KullanicilarModel;
import sercandevops.com.veterineradmin.Model.SoruModel;
import sercandevops.com.veterineradmin.R;
import sercandevops.com.veterineradmin.RestApi.ManagerAll;
import sercandevops.com.veterineradmin.Utils.ChangeFragments;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    Context context;
    List<KullanicilarModel> kullanicilarModelList;
    Activity activity;
    View  v;
    ChangeFragments changeFragments;

    public UserAdapter(Context context, List<KullanicilarModel> kullanicilarModelList, Activity activity) {
        this.context = context;
        this.kullanicilarModelList = kullanicilarModelList;
        this.activity = activity;

        changeFragments = new ChangeFragments(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        v = LayoutInflater.from(context).inflate(R.layout.kullanicilayout,viewGroup,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tv_kullaniciText.setText(kullanicilarModelList.get(i).getKadi());

        myViewHolder.btnAramaYap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arama(kullanicilarModelList.get(i).getTelefon());
            }
        });

        myViewHolder.btnPetler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeFragments.changeParameters(new KullaniciPetFragment(), kullanicilarModelList.get(i).getId().toString());
            }
        });
    }

    @Override
    public int getItemCount() {
        return kullanicilarModelList.size();
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



    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        CardView cardview_kullanicilar;
        CircleImageView img_kullaniciImage1;

        TextView tv_kullaniciText;
        Button btnPetler,btnAramaYap;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardview_kullanicilar = (CardView)itemView.findViewById(R.id.cardview_kullanicilar);
            img_kullaniciImage1 = (CircleImageView)itemView.findViewById(R.id.img_kullaniciImage1);
            tv_kullaniciText = (TextView) itemView.findViewById(R.id.tv_kullaniciText);
            btnPetler = (Button) itemView.findViewById(R.id.btnPetEkle);
            btnAramaYap = (Button) itemView.findViewById(R.id.btnAramaYap);

        }
    }//CLASS
}
