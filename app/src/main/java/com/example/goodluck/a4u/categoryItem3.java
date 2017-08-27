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

public class categoryItem3 extends Activity {
    ListView list3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_item3);
        list3= (ListView) findViewById(R.id.lcdlistView);
        list3.setAdapter(new vivzAdapter4(this));
  }
}
class  Singlelcd{
    String tittle;
    String description;
    int image;

    Singlelcd(String tittle,String description, int image){

        this.tittle= tittle;
        this.description=description;
        this.image=image;

    }
}

class vivzAdapter4 extends BaseAdapter{
    ArrayList<Singlelcd> list3;
    Context context;
    vivzAdapter4(Context c){
        context=c;
        list3=new ArrayList<Singlelcd>();

        Resources res =c.getResources();
        String[] lcddesc=res.getStringArray(R.array.lcd);
        String[] lcdprice= res.getStringArray(R.array.lcd_price);
        int[] images={R.drawable.lcdtwogreen, R.drawable.lcddisplay, R.drawable.lcdfourgreen, R.drawable.lcdfourblue};
        for(int i=0; i<4; i++){
            list3.add(new Singlelcd(lcddesc[i],lcdprice[i],images[i]));

        }
    }
    @Override
    public int getCount() {
        return list3.size();
    }

    @Override
    public Object getItem(int i) {
        return list3.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View row =inflater.inflate(R.layout.single_lcd,viewGroup,false);
        TextView tittle = (TextView) row.findViewById(R.id.lcdDesc);
        TextView description = (TextView) row.findViewById(R.id.lcdPrice);
        ImageView image= (ImageView) row.findViewById(R.id.lcdImage);

        Singlelcd temp= list3.get(i);
        tittle.setText(temp.tittle);
        description.setText(temp.description);
        image.setImageResource(temp.image);

        return row;
    }
}
