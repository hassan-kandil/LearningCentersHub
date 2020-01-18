package edu.aucegypt.learningcentershub;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.ArrayList;

public class user_info_frag extends Fragment {
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.user_info, container, false);

        listView = view.findViewById(R.id.user_info_detail);
        String [] data = getResources().getStringArray(R.array.user_info);
        ArrayList<String> Data = new ArrayList<String>();
        for (int i = 0 ; i<data.length;i++) {
            Data.add(data[i]);
        }
        listView.setAdapter(new rowAdapter2(getContext(), Data));

        return view;

    }
}

class rowAdapter2 extends ArrayAdapter<String> {
    public rowAdapter2(Context context, ArrayList<String> row ){
        super(context,0,row);

    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String row = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row2, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.row_text2);
        textView.setText(row);
        TextView textView2 = (TextView) convertView.findViewById(R.id.row_text3);
        textView2.setText(row);
        return convertView;
    }
}