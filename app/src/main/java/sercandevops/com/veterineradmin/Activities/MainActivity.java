package sercandevops.com.veterineradmin.Activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import sercandevops.com.veterineradmin.BataryaYayini;
import sercandevops.com.veterineradmin.Fragment.HomeFragment;
import sercandevops.com.veterineradmin.R;
import sercandevops.com.veterineradmin.Utils.ChangeFragments;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ChangeFragments changeFragments = new ChangeFragments(MainActivity.this);

        HomeFragment homefg = new HomeFragment();

        changeFragments.change(homefg);

      //  this.registerReceiver(this.mBatInfoBroadcastReceiver,new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        BataryaYayini bataryaYayini = new BataryaYayini();

        IntentFilter ıntentFilter = new IntentFilter();
        ıntentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);

        registerReceiver(bataryaYayini,ıntentFilter);

    }

   /* private BroadcastReceiver mBatInfoBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL,0);
            Toast.makeText(context,"batarya "+level,Toast.LENGTH_SHORT).show();

            if(level == 20)
            {
                AlertDialog.Builder builder =new  AlertDialog.Builder(context);
                builder.setTitle("mesaj");
                builder.setMessage("şarja takınız");
                builder.show();
            }
        }
    };//class
    */

}
