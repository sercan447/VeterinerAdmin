package sercandevops.com.veterineradmin.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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


    }
}
