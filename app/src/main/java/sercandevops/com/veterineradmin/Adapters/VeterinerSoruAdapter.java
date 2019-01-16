package sercandevops.com.veterineradmin.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sercandevops.com.veterineradmin.Model.AsiOnaylaModel;
import sercandevops.com.veterineradmin.Model.PetAsiTakipModel;
import sercandevops.com.veterineradmin.Model.SoruModel;
import sercandevops.com.veterineradmin.R;
import sercandevops.com.veterineradmin.RestApi.BaseURL;
import sercandevops.com.veterineradmin.RestApi.ManagerAll;
import sercandevops.com.veterineradmin.Utils.Warnings;

public class VeterinerSoruAdapter extends RecyclerView.Adapter<VeterinerSoruAdapter.MyViewHolder> {

    Context context;
    List<SoruModel> soruModelList;
    Activity activity;
    View  v;

    public VeterinerSoruAdapter(Context context, List<SoruModel> soruModelList, Activity activity) {
        this.context = context;
        this.soruModelList = soruModelList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        v = LayoutInflater.from(context).inflate(R.layout.sorularlayout,viewGroup,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

        myViewHolder.tv_soruyazanKisi.setText(soruModelList.get(i).getKadi());
        myViewHolder.tv_soruIcerik.setText(soruModelList.get(i).getSoru());

        myViewHolder.btn_soruCevapla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogSoruCEVAPLA();
            }
        });

        myViewHolder.btn_soruSoranKisiAra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                arama(soruModelList.get(i).getTelefon());
            }
        });
    }

    @Override
    public int getItemCount() {
        return soruModelList.size();
    }


    public void deleteToList(int position)
    {
        soruModelList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    public void arama(String numara)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:"+numara));
        activity.startActivity(intent);
    }


    public void AlertDialogSoruCEVAPLA()
    {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.alertcevapla,null);

        final EditText ed_soruCevap = view.findViewById(R.id.ed_soruCevap);
        MaterialButton btn_soruCevap = view.findViewById(R.id.btn_soruCevap);


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(view);
        builder.setCancelable(true);

        final AlertDialog alertDialog = builder.create();

        btn_soruCevap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cevapText = ed_soruCevap.getText().toString();
                SoruCevapla(cevapText,"",alertDialog);
            }
        });

        alertDialog.show();

    }//FUNC

    public void SoruCevapla(String cevapText,String soruId,AlertDialog alertDialog)
    {



    }//FUNC

    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv_soruyazanKisi,tv_soruIcerik;
        Button btn_soruCevapla,btn_soruSoranKisiAra;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_soruyazanKisi = itemView.findViewById(R.id.tv_soruyazanKisi);
            tv_soruIcerik = itemView.findViewById(R.id.tv_soruIcerik);
            btn_soruCevapla = itemView.findViewById(R.id.btn_soruCevapla);
            btn_soruSoranKisiAra = itemView.findViewById(R.id.btn_soruSoranKisiAra);

        }
    }//CLASS
}
