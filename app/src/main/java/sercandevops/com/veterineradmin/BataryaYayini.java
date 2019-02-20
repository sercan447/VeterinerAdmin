package sercandevops.com.veterineradmin;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.Toast;

public class BataryaYayini extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
        Toast.makeText(context,"batarya "+level, Toast.LENGTH_SHORT).show();

        if(level == 20)
        {
            AlertDialog.Builder builder =new  AlertDialog.Builder(context);
            builder.setTitle("mesaj static yayın");
            builder.setMessage("şarja takınız static ");
            builder.show();
        }
    }
}
