package sercandevops.com.veterineradmin.Utils;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import sercandevops.com.veterineradmin.R;

public class ChangeFragments {

    private Context context;

    public ChangeFragments(Context context)
    {
        this.context = context;
    }

    public void change(Fragment fragment)
    {
        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFramelayout,fragment,"fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }


    public void changeParameters(Fragment fragment,String petId)
    {
        Bundle bundle = new Bundle();
        bundle.putString("petId",petId);

        fragment.setArguments(bundle);

        ((FragmentActivity)context).getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.mainFramelayout,fragment,"fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }
}
