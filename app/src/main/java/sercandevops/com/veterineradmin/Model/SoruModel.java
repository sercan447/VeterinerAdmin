package sercandevops.com.veterineradmin.Model;

public class SoruModel{
	private String musid;
	private boolean tf;
	private String soruId;
	private String telefon;
	private String kadi;
	private String soru;

	public void setMusid(String musid){
		this.musid = musid;
	}

	public String getMusid(){
		return musid;
	}

	public void setTf(boolean tf){
		this.tf = tf;
	}

	public boolean isTf(){
		return tf;
	}

	public void setSoruId(String soruId){
		this.soruId = soruId;
	}

	public String getSoruId(){
		return soruId;
	}

	public void setTelefon(String telefon){
		this.telefon = telefon;
	}

	public String getTelefon(){
		return telefon;
	}

	public void setKadi(String kadi){
		this.kadi = kadi;
	}

	public String getKadi(){
		return kadi;
	}

	public void setSoru(String soru){
		this.soru = soru;
	}

	public String getSoru(){
		return soru;
	}

	@Override
 	public String toString(){
		return 
			"SoruModel{" + 
			"musid = '" + musid + '\'' + 
			",tf = '" + tf + '\'' + 
			",soruId = '" + soruId + '\'' + 
			",telefon = '" + telefon + '\'' + 
			",kadi = '" + kadi + '\'' + 
			",soru = '" + soru + '\'' + 
			"}";
		}
}
