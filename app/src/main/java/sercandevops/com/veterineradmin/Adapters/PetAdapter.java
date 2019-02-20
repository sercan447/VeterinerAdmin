package sercandevops.com.veterineradmin.Adapters;

import android.Manifest;
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
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import sercandevops.com.veterineradmin.Fragment.KullaniciPetFragment;
import sercandevops.com.veterineradmin.Model.AsiEkle;
import sercandevops.com.veterineradmin.Model.KullaniciSil;
import sercandevops.com.veterineradmin.Model.KullanicilarModel;
import sercandevops.com.veterineradmin.Model.PetModel;
import sercandevops.com.veterineradmin.Model.PetSil;
import sercandevops.com.veterineradmin.R;
import sercandevops.com.veterineradmin.RestApi.BaseURL;
import sercandevops.com.veterineradmin.RestApi.ManagerAll;
import sercandevops.com.veterineradmin.Utils.ChangeFragments;
import sercandevops.com.veterineradmin.Utils.Warnings;

public class PetAdapter extends RecyclerView.Adapter<PetAdapter.MyViewHolder> {

    Context context;
    List<PetModel> petModelList;
    Activity activity;
    View  v;
    ChangeFragments changeFragments;
    String musid;
    String tarih;

    public PetAdapter(Context context, List<PetModel> petModelList, Activity activity,String musid) {
        this.context = context;
        this.petModelList = petModelList;
        this.activity = activity;
        this.musid = musid;

        changeFragments = new ChangeFragments(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        v = LayoutInflater.from(context).inflate(R.layout.userpetitemlayot,viewGroup,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {

            myViewHolder.tv_petBilgiText.setText(petModelList.get(i).getPetisim().toString());
            myViewHolder.tv_petBilgiText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertPetSil(i);
                }
            });
            myViewHolder.tv_petNameText.setText("Bu petin türü :"+petModelList.get(i).getPettur().toString()+
                                                "cinsi :"+petModelList.get(i).getPetcins().toString()+" dir."
                    +petModelList.get(i).getPetisim().toString()+" isimli pet aşı eklemek için tıklayın.");

                    Picasso.get().load(BaseURL.URL +petModelList.get(i).getPetresim())
                                        .fit().centerCrop().into(myViewHolder.img_petImage);

        myViewHolder.cardview_userpet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                asiEkleAlert(petModelList.get(i).getPetid().toString());
            }
        });
    }

    public void asiEkleAlert(final String petid)
    {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.asieklelayout,null);

        CalendarView calendarView = view.findViewById(R.id.asiEkleTakvim);
        final EditText ed_Asiname = view.findViewById(R.id.ed_asiEkleAsiName);
        Button btnasiEkle = view.findViewById(R.id.btnAsiEkle);


        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog dialog = alert.create();

         calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
             @Override
             public void onSelectedDayChange( @NonNull CalendarView view, int year, int month, int dayOfMonth) {
                 Log.e("TAGLARIM",year+"-"+month+"-"+dayOfMonth);

                 DateFormat inputformat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSX", Locale.US);
                 DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                 tarih = dayOfMonth + "/" + (month+1) + "/" + year;

                 try {
                     Date date = inputformat.parse(tarih);
                     tarih = format.format(tarih).toString();

                 } catch (ParseException e) {
                     e.printStackTrace();
                 }

             }
         });
        dialog.show();


        btnasiEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String asiName = ed_Asiname.getText().toString();
                if(!tarih.equals("") && !asiName.equals(""))
                {
                    AddAsi(musid,petid,asiName,tarih,dialog);
                }else
                {
                    Toast.makeText(context,"Eksik veri girdiniz lütfen tamamlayınız.", Toast.LENGTH_SHORT).show();
                }
            }
        });//click

    }//asiEkleAlert



    public void AlertPetSil(final int position)
    {
        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.petsillayout,null );
        Button petTamambtn = view.findViewById(R.id.btnPetSil);
        Button petCikisbtn = view.findViewById(R.id.btnPetCikis);

        final android.support.v7.app.AlertDialog.Builder alert = new android.support.v7.app.AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final android.support.v7.app.AlertDialog alertDialog = alert.create();

        petTamambtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                petSil(petModelList.get(position).getPetid(),position);
                deleteToList(position);
                alertDialog.cancel();
            }
        });
        petCikisbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });

        alertDialog.show();
    }//fnc
    public void petSil(String id ,int position)
    {
        Call<PetSil> req = ManagerAll.getInstance().petSil(id);
        req.enqueue(new Callback<PetSil>() {
            @Override
            public void onResponse(Call<PetSil> call, Response<PetSil> response) {
                if(response.body().isTf())
                {
                    Toast.makeText(  context,response.body().getText().toString(),Toast.LENGTH_LONG).show();
                }else
                {
                    Toast.makeText(  context,response.body().getText().toString(),Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<PetSil> call, Throwable t) {
                Toast.makeText(  context, Warnings.internetProblemtext,Toast.LENGTH_LONG).show();
            }
        });
    }
    public void AddAsi(String musid, String petid, String asiName, String tarih, final AlertDialog alertDialog)
    {
        final Call<AsiEkle> req = ManagerAll.getInstance().asiEkle(musid,petid,asiName,tarih);
        req.enqueue(new Callback<AsiEkle>() {
            @Override
            public void onResponse(Call<AsiEkle> call, Response<AsiEkle> response) {
                if (response.body().isTf())
                {

                }else {

                }
                alertDialog.cancel();
            }

            @Override
            public void onFailure(Call<AsiEkle> call, Throwable t) {

                alertDialog.cancel();
            }
        });

    }//func

    @Override
    public int getItemCount() {
        return petModelList.size();
    }


    public void deleteToList(int position) {
        petModelList.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder
    {

        CardView cardview_userpet;
        CircleImageView img_petImage;
        TextView tv_petBilgiText,tv_petNameText;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            cardview_userpet = (CardView)itemView.findViewById(R.id.cardview_userpet);
            img_petImage = (CircleImageView)itemView.findViewById(R.id.img_petImage);
            tv_petBilgiText = (TextView) itemView.findViewById(R.id.tv_petBilgiText);
            tv_petNameText = (TextView) itemView.findViewById(R.id.tv_petNameText);



        }
    }//CLASS
}
