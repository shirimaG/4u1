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

public class categoryItem4 extends Activity {
    ListView list4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item4);
        list4= (ListView) findViewById(R.id.jumper_listView);
        list4.setAdapter(new vivzAdapter5(this));
    }
}

class singlejumper{
    String tittle;
    String description;
    int image;

    singlejumper(String tittle,String description,int image){
        this.tittle=tittle;
        this.description=description;
        this.image=image;
    }
}

class vivzAdapter5 extends BaseAdapter {
    ArrayList<singlejumper> list4;
    Context context;
    vivzAdapter5(Context c) {
        context = c;
        list4 = new ArrayList<singlejumper>();

        Resources res = c.getResources();
        String[] jumper = res.getStringArray(R.array.jumper);
        String[] jumper_price = res.getStringArray(R.array.jumper_price);
        int[] images = {R.drawable.imalemale, R.drawable.ifemalefemale, R.drawable.imalefemale};

        for (int i = 0; i < 3; i++) {
            list4.add(new singlejumper(jumper[i], jumper_price[i], images[i]));

        }
    }

    @Override
    public int getCount() {
        return list4.size();
    }

    @Override
    public Object getItem(int i) {
        return list4.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row =inflater.inflate(R.layout.single_jumper,viewGroup,false);
        TextView tittle = (TextView) row.findViewById(R.id.jumperDesc);
        TextView description = (TextView) row.findViewById(R.id.jumperPrice);
        ImageView image = (ImageView) row.findViewById(R.id.jumplerImage);

        singlejumper temp = list4.get(i);

        tittle.setText(temp.tittle);
        description.setText(temp.description);
        image.setImageResource(temp.image);
        return row;

    }
}