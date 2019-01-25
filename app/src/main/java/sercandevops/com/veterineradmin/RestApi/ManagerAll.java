package sercandevops.com.veterineradmin.RestApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import sercandevops.com.veterineradmin.Model.AsiOnaylaModel;
import sercandevops.com.veterineradmin.Model.CevaplaModel;
import sercandevops.com.veterineradmin.Model.KampanyaEkleModel;
import sercandevops.com.veterineradmin.Model.KampanyaModel;
import sercandevops.com.veterineradmin.Model.KampanyaSilModel;
import sercandevops.com.veterineradmin.Model.KullanicilarModel;
import sercandevops.com.veterineradmin.Model.PetAsiTakipModel;
import sercandevops.com.veterineradmin.Model.PetModel;
import sercandevops.com.veterineradmin.Model.SoruModel;

public class ManagerAll extends BaseManager {

        private static ManagerAll ourInstance = new ManagerAll();

        public static synchronized ManagerAll getInstance()
        {
            return ourInstance;
        }


        public  Call<List<KampanyaModel>> getKampanya()
        {
            Call<List<KampanyaModel>> x = getRestApi().getKampanya();

            return x;
        }

    public  Call<KampanyaEkleModel> KampanyaEkle(String baslik,  String bilgi,  String resim)
    {
        Call<KampanyaEkleModel> x = getRestApi().KampanyaEkle(baslik, bilgi, resim);

        return x;
    }

    public  Call<KampanyaSilModel> KampanyaSil(String id)
    {
        Call<KampanyaSilModel> x = getRestApi().SilKampanya(id);

        return x;
    }

    public  Call<List<PetAsiTakipModel>> getAsiTakip(String tarih)
    {
        Call<List<PetAsiTakipModel>> x = getRestApi().getAsiTakip(tarih);

        return x;
    }


    public  Call<List<PetModel>> getPets(String musid)
    {
        Call<List<PetModel>> x = getRestApi().getPets(musid);

        return x;
    }

    public  Call<AsiOnaylaModel> asiOnayla(String id)
    {
        Call<AsiOnaylaModel> x = getRestApi().asiOnayla(id);

        return x;
    }

    public  Call<AsiOnaylaModel> asiIptal(String id)
    {
        Call<AsiOnaylaModel> x = getRestApi().asiIptal(id);

        return x;
    }

    public  Call<List<SoruModel>> getSorular()
    {
        Call<List<SoruModel>> x = getRestApi().getSorular();

        return x;
    }

    public  Call<CevaplaModel> soruCevapla(String musid, String soruid, String text)
    {
        Call<CevaplaModel> x = getRestApi().soruCevapla(musid, soruid, text);

        return x;
    }

    public  Call<List<KullanicilarModel>> getKullanicilar()
    {
        Call<List<KullanicilarModel>> x = getRestApi().getKullanicilar();

        return x;
    }

}
