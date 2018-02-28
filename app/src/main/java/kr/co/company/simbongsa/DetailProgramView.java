package kr.co.company.simbongsa;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;


/**
 * Created by BHY on 2018. 2. 27..
 */

public class DetailProgramView extends LinearLayout {
    TextView textView_key;
    TextView textView_value;

    public DetailProgramView(Context context) {
        super(context);
        init(context);
    }

    public DetailProgramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.layout_detailprogram,this,true);

        textView_key = (TextView) findViewById(R.id.textview_key);
        textView_value = (TextView) findViewById(R.id.textview_value);

    }

    public void setTextView_key(String key) {
        textView_key.setText(key + ": ");
    }

    public void setTextView_value(String value) {
        textView_value.setText(value);
    }
}
