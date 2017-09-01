package com.example.goodluck.a4u;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class categoryItem5 extends Activity {
    ListView list5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item5);
        list5 = (ListView) findViewById(R.id.rasplistView);
        list5.setAdapter(new vivzAdapter6(this));
    }
}

class singleRasp{
    String tittle;
    String description;
    int image;

    singleRasp(String tittle,String description,int image){
        this.tittle = tittle;
        this.description = description;
        this.image = image;
    }
}

class vivzAdapter6 extends BaseAdapter{
    ArrayList<singleRasp> list5;
    Context context;
    vivzAdapter6(Context c){
        context = c;
        list5 = new ArrayList<singleRasp>();

        Resources res = c.getResources();
        String[] rasp = res.getStringArray(R.array.rasp);
        String[] rasp_price = res.getStringArray(R.array.rasp_price);
        int[] images ={R.drawable.rasp, R.drawable.raspberry};

        for(int i=0; i<2; i++){
            list5.add(new singleRasp(rasp[i],rasp_price[i],images[i]));
        }
    }

    @Override
    public int getCount() {
        return list5.size();
    }

    @Override
    public Object getItem(int i) {
        return list5.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row =inflater.inflate(R.layout.single_rasp,viewGroup,false);
        TextView tittle = (TextView) row.findViewById(R.id.rasp);
        TextView description = (TextView) row.findViewById(R.id.raspDesc);
        ImageView image = (ImageView) row.findViewById(R.id.raspImage);

        singleRasp temp =list5.get(i);
        tittle.setText(temp.tittle);
        description.setText(temp.description);
        image.setImageResource(temp.image);
        return row;
    }
}