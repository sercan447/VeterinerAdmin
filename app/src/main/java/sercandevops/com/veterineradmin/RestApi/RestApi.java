package sercandevops.com.veterineradmin.RestApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sercandevops.com.veterineradmin.Model.AsiEkle;
import sercandevops.com.veterineradmin.Model.AsiOnaylaModel;
import sercandevops.com.veterineradmin.Model.CevaplaModel;
import sercandevops.com.veterineradmin.Model.KampanyaEkleModel;
import sercandevops.com.veterineradmin.Model.KampanyaModel;
import sercandevops.com.veterineradmin.Model.KampanyaSilModel;
import sercandevops.com.veterineradmin.Model.KullaniciSil;
import sercandevops.com.veterineradmin.Model.KullanicilarModel;
import sercandevops.com.veterineradmin.Model.PetAsiTakipModel;
import sercandevops.com.veterineradmin.Model.PetEkle;
import sercandevops.com.veterineradmin.Model.PetModel;
import sercandevops.com.veterineradmin.Model.PetSil;
import sercandevops.com.veterineradmin.Model.SoruModel;


public interface RestApi {


    @GET("/veterinerapp/kampanyaIdli.php")
    Call<List<KampanyaModel>> getKampanya();

    @POST("/veterinerapp/kampanyaekle.php")
    @FormUrlEncoded
    Call<KampanyaEkleModel> KampanyaEkle(@Field("baslik") String baslik,@Field("bilgi") String bilgi,@Field("resim") String resim);

    @FormUrlEncoded
    @POST("/veterinerapp/kampanyasil.php")
    Call<KampanyaSilModel> SilKampanya(@Field("id")String id);


    @FormUrlEncoded
    @POST("/veterinerapp/veterinerasitakip.php")
    Call<List<PetAsiTakipModel>> getAsiTakip(@Field("tarih")String tarih);

    @FormUrlEncoded
    @POST("/veterinerapp/asionayla.php")
    Call<AsiOnaylaModel> asiOnayla(@Field("id")String petasiid);

    @FormUrlEncoded
    @POST("/veterinerapp/asiptal.php")
    Call<AsiOnaylaModel> asiIptal(@Field("id")String petasiid);


    @GET("/veterinerapp/sorular.php")
    Call<List<SoruModel>> getSorular();

    @POST("/veterinerapp/cevapla.php")
    @FormUrlEncoded
        Call<CevaplaModel> soruCevapla(@Field("musid") String musid, @Field("soruid") String soruid, @Field("text") String text);

    @GET("/veterinerapp/kullanicilar.php")
    Call<List<KullanicilarModel>> getKullanicilar();


    @FormUrlEncoded
    @POST("/veterinerapp/petlerim.php")
    Call<List<PetModel>> getPets(@Field("mus_id")String musid);

    @FormUrlEncoded
    @POST("/veterinerapp/petekle.php")
    Call<PetEkle> petEkle(@Field("musid")String musid,@Field("isim")String isim, @Field("tur")String tur,
                          @Field("cins")String cins, @Field("resim")String resim);

    @FormUrlEncoded
    @POST("/veterinerapp/asiekle.php")
    Call<AsiEkle> asiEkle(@Field("musid")String musid, @Field("petid")String petid, @Field("name")String name,
                          @Field("tarih")String tarih);


    @FormUrlEncoded
    @POST("/veterinerapp/kullanicisil.php")
    Call<KullaniciSil> kullaniciSil(@Field("kul_id")String kul_id);

    @FormUrlEncoded
    @POST("/veterinerapp/petsil.php")
    Call<PetSil> petSil(@Field("pet_id")String pet_id);
}
