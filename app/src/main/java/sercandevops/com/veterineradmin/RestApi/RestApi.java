package sercandevops.com.veterineradmin.RestApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import sercandevops.com.veterineradmin.Model.AsiOnaylaModel;
import sercandevops.com.veterineradmin.Model.KampanyaEkleModel;
import sercandevops.com.veterineradmin.Model.KampanyaModel;
import sercandevops.com.veterineradmin.Model.KampanyaSilModel;
import sercandevops.com.veterineradmin.Model.PetAsiTakipModel;


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
}
