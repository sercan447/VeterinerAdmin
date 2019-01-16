package sercandevops.com.veterineradmin.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sercandevops.com.veterineradmin.Model.AsiOnaylaModel;
import sercandevops.com.veterineradmin.Model.KampanyaModel;
import sercandevops.com.veterineradmin.Model.KampanyaSilModel;
import sercandevops.com.veterineradmin.Model.PetAsiTakipModel;
import sercandevops.com.veterineradmin.R;
import sercandevops.com.veterineradmin.RestApi.BaseURL;
import sercandevops.com.veterineradmin.RestApi.ManagerAll;
import sercandevops.com.veterineradmin.Utils.Warnings;

public class PetAsiTakipAdapter extends RecyclerView.Adapter<PetAsiTakipAdapter.MyViewHolder> {

    Context context;
    List<PetAsiTakipModel> asiTakipModelList;
    Activity activity;
    View  v;

    public PetAsiTakipAdapter(Context context, List<PetAsiTakipModel> asiTakipModelList, Activity activity) {
        this.context = context;
        this.asiTakipModelList = asiTakipModelList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        v = LayoutInflater.from(context).inflate(R.layout.asitakiplayout,viewGroup,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tv_asiTakipBaslik.setText(asiTakipModelList.get(i).getAsiisim());
        Picasso.get().load(BaseURL.URL+asiTakipModelList.get(i).getPetresim()).resize(200,200).into(myViewHolder.img_asiTakip);

        myViewHolder.btn_asiTakipAra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arama(asiTakipModelList.get(i).getTelefon());
            }
        });

        myViewHolder.btn_asitakipButn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asiOnayla(asiTakipModelList.get(i).getAsiid(),i);
            }
        });

        myViewHolder.btn_asiTakipCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asiIptal(asiTakipModelList.get(i).getAsiid(),i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return asiTakipModelList.size();
    }


    public void deleteToList(int position)
    {
        asiTakipModelList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void arama(String numara)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:"+numara));
        activity.startActivity(intent);
    }


    public void asiOnayla(String id, final int position)
    {
        Call<AsiOnaylaModel> req = ManagerAll.getInstance().asiOnayla(id);

        req.enqueue(new Callback<AsiOnaylaModel>() {
            @Override
                public void onResponse(Call<AsiOnaylaModel> call, Response<AsiOnaylaModel> response) {
                if(response.body().isTf())
                {
                  Toast.makeText(context,"onalandı.",Toast.LENGTH_SHORT).show();
                  deleteToList(position);
                }else
                {
                    Toast.makeText(context,"oluşmadı",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AsiOnaylaModel> call, Throwable t) {
                Toast.makeText(context,Warnings.internetProblemtext,Toast.LENGTH_SHORT).show();
                Log.i("hatalandikyine",t.toString());
            }
        });
    }//FUNC

    public void asiIptal(String id, final int position)
    {
        Call<AsiOnaylaModel> req = ManagerAll.getInstance().asiIptal(id);

        req.enqueue(new Callback<AsiOnaylaModel>() {
            @Override
            public void onResponse(Call<AsiOnaylaModel> call, Response<AsiOnaylaModel> response) {
                if(response.body().isTf())
                {
                    Toast.makeText(context,response.body().getText(),Toast.LENGTH_SHORT).show();
                    deleteToList(position);
                }else
                {
                    Toast.makeText(context,response.body().getText(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AsiOnaylaModel> call, Throwable t) {
                Toast.makeText(context,Warnings.internetProblemtext,Toast.LENGTH_SHORT).show();
                Log.i("hatalandikyine",t.toString());
            }
        });
    }//FUNC


    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv_asiTakipBaslik,tv_asiTakipBilgiText;
        CircleImageView img_asiTakip;
        Button btn_asitakipButn,btn_asiTakipCancel,btn_asiTakipAra;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_asiTakipBaslik = itemView.findViewById(R.id.tv_asiTakipBaslik);
            tv_asiTakipBilgiText = itemView.findViewById(R.id.tv_asiTakipBilgiText);

            img_asiTakip = (CircleImageView) itemView.findViewById(R.id.img_asiTakip);

            btn_asiTakipAra = itemView.findViewById(R.id.btn_asiTakipAra);
            btn_asiTakipCancel = itemView.findViewById(R.id.btn_asiTakipCancel);
            btn_asitakipButn = itemView.findViewById(R.id.btn_asitakipButn);

        }
    }//CLASS
}
