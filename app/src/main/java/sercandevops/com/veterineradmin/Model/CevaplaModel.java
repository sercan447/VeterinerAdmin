package sercandevops.com.veterineradmin.Model;

public class CevaplaModel {

    private String text;
    private boolean tf;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isTf() {
        return tf;
    }

    public void setTf(boolean tf) {
        this.tf = tf;
    }


    @Override
    public String toString() {
        return "CevaplaModel{" +
                "text='" + text + '\'' +
                ", tf=" + tf +
                '}';
    }
}
