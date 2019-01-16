package sercandevops.com.veterineradmin.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
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
import sercandevops.com.veterineradmin.Model.KampanyaModel;
import sercandevops.com.veterineradmin.Model.KampanyaSilModel;
import sercandevops.com.veterineradmin.R;
import sercandevops.com.veterineradmin.RestApi.BaseURL;
import sercandevops.com.veterineradmin.RestApi.ManagerAll;
import sercandevops.com.veterineradmin.Utils.Warnings;

public class KampanyaAdapter extends RecyclerView.Adapter<KampanyaAdapter.MyViewHolder> {

    Context context;
    List<KampanyaModel> kampanyaModelList;
    Activity activity;
    View  v;

    public KampanyaAdapter(Context context, List<KampanyaModel> kampanyaModelList,Activity activity) {
        this.context = context;
        this.kampanyaModelList = kampanyaModelList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        v = LayoutInflater.from(context).inflate(R.layout.kampanyalayout,viewGroup,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tv_kampanyaBilgi.setText(kampanyaModelList.get(i).getBaslik());
        myViewHolder.tv_kampanyaBaslik.setText(kampanyaModelList.get(i).getBilgi());
        Picasso.get().load(BaseURL.URL+kampanyaModelList.get(i).getResim().toString()).resize(200,200).into(myViewHolder.img_kampanya);

        myViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogKampanyaDelete(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return kampanyaModelList.size();
    }

    public void AlertDialogKampanyaDelete(final int position)
    {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.alertkampanyasil,null);


        AlertDialog.Builder builer = new AlertDialog.Builder(context);
        builer.setView(view);
        builer.setCancelable(true);

        final AlertDialog alertDialog = builer.create();
        //component tanımmlama

        Button btnKampanyaSil = view.findViewById(R.id.btnKampanyaSil);
        Button btnKampanyaCikis = view.findViewById(R.id.btnKampanyaCikis);


        btnKampanyaSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            KampanyaSil(kampanyaModelList.get(position).getId(),position);
            }
        });

        btnKampanyaCikis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }//FUNC

    public void KampanyaSil(String id, final int position)
    {
        Call<KampanyaSilModel> req = ManagerAll.getInstance().KampanyaSil(id);
        req.enqueue(new Callback<KampanyaSilModel>() {
            @Override
            public void onResponse(Call<KampanyaSilModel> call, Response<KampanyaSilModel> response) {

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
            public void onFailure(Call<KampanyaSilModel> call, Throwable t) {
                Toast.makeText(context,Warnings.internetProblemtext,Toast.LENGTH_SHORT).show();
            }
        });

    }//FUNC

    public void deleteToList(int position)
    {
        kampanyaModelList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView tv_kampanyaBaslik,tv_kampanyaBilgi;
        CircleImageView img_kampanya;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_kampanyaBaslik = itemView.findViewById(R.id.tv_kampanyaBaslik);
            tv_kampanyaBilgi = itemView.findViewById(R.id.tv_kampanyaBilgi);
            img_kampanya = (CircleImageView)itemView.findViewById(R.id.img_kampanya);
            cardView = (CardView)itemView.findViewById(R.id.cardviewKampanya);

        }
    }//CLASS
}
