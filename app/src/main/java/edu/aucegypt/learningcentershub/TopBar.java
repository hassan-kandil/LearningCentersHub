package edu.aucegypt.learningcentershub;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import java.util.Arrays;

import edu.aucegypt.learningcentershub.ui.login.LoginActivity;

public class TopBar extends Fragment implements View.OnClickListener {
    public static  Button button;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.topbar, container, false);
        Toolbar myToolbar = view.findViewById(R.id.topbar2);
        ((AppCompatActivity) getActivity()).setSupportActionBar(myToolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
         button = view.findViewById(R.id.button);
        if (Admin_home.class == getContext().getClass())
        {
            button.setText("Log Out");
        }
        if(LoginActivity.status) button.setText("Log out");
        button.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        if (((Button) view).getText().toString().equals("Login")) {
            Intent i = new Intent("edu.aucegypt.learningcentershub.LOGIN");
            startActivity(i);

        }
        else  if (((Button) view).getText().toString().equals("Log out")) {
            Intent i = new Intent("edu.aucegypt.learningcentershub.MAIN_ACTIVITY");
            startActivity(i);
            rvadapter3.cname.clear();
            rvadapter3.cid.clear();
            Arrays.fill(rvadapter3.message, null);
            for( int j = 0; j < 10; j++ )
                Arrays.fill(rvadapter3.message2[j], null);
            Admin_home.lcid = "";
            Arrays.fill(Admin_home.message, null);
            LoginActivity.status = !LoginActivity.status;
            MyAccount_frag.cleanup();
            ((Button)view).setText("Login");
        }
        if(LoginActivity.status) button.setText("Log out");

    }
}
