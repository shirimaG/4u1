package com.example.goodluck.a4u;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class categoryItem extends Activity implements AdapterView.OnItemClickListener {

    ListView list0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item);

        list0 = (ListView) findViewById(R.id.listView0);
        list0.setAdapter(new VivzAdapter2(this));
        list0.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(this,"Please wait, still under development "+position,Toast.LENGTH_SHORT).show();
    }
}

class singleArd{
    String tittle;
    String description;
    int image;

    singleArd(String tittle, String description, int image){
        this.tittle = tittle;
        this.description= description;
        this.image =image;
    }
}

class VivzAdapter2 extends BaseAdapter{

    ArrayList<singleArd> list0;

    Context context;
    VivzAdapter2(Context c){
        context =c;
        list0 = new ArrayList<singleArd>();

        Resources res =c.getResources();
        String[] arduino = res.getStringArray(R.array.arduino);
        String[] ard_price= res.getStringArray(R.array.ard_price);
        int[] images ={R.drawable.arduinomega,R.drawable.arduinonano,R.drawable.arduinorth,R.drawable.arduinouno};

        for(int i=0; i<4; i++){
            list0.add(new singleArd(arduino[i],ard_price[i],images[i]));
        }
    }

    @Override
    public int getCount() {
        return list0.size();
    }

    @Override
    public Object getItem(int i) {

        return list0.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row=inflater.inflate(R.layout.singl_ard,viewGroup,false);
        TextView tittle = (TextView) row.findViewById(R.id.arDesc);
        TextView description = (TextView) row.findViewById(R.id.ardPrice);
        ImageView image = (ImageView) row.findViewById(R.id.imageView12);

        singleArd temp = list0.get(i);
        tittle.setText(temp.tittle);
        description.setText(temp.description);
        image.setImageResource(temp.image);
        return row;
    }
}