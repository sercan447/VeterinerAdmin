package sercandevops.com.veterineradmin.Model;

public class KampanyaModel
{
	private String bilgi;
	private String resim;
	private boolean tf;
	private String id;
	private String baslik;

	public void setBilgi(String bilgi){
		this.bilgi = bilgi;
	}

	public String getBilgi(){
		return bilgi;
	}

	public void setResim(String resim){
		this.resim = resim;
	}

	public String getResim(){
		return resim;
	}

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setBaslik(String baslik){
		this.baslik = baslik;
	}

	public String getBaslik(){
		return baslik;
	}

	@Override
 	public String toString(){
		return 
			"KampanyaModel{" + 
			"bilgi = '" + bilgi + '\'' + 
			",resim = '" + resim + '\'' + 
			",tf = '" + tf + '\'' + 
			",id = '" + id + '\'' + 
			",baslik = '" + baslik + '\'' + 
			"}";
		}
}
