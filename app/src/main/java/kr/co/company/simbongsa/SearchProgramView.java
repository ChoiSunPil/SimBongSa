package kr.co.company.simbongsa;

import android.content.Context;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by BHY on 2018. 2. 16..
 */

public class SearchProgramView extends LinearLayout {
    TextView textView_title;
    TextView textView_insti;
    TextView textView_period;

    public SearchProgramView(Context context) {
        super(context);
        init(context);
    }

    public SearchProgramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.layout_searchprogram,this,true);

        textView_title = (TextView) findViewById(R.id.textview_title_SearchProgram);
        textView_insti = (TextView) findViewById(R.id.textview_insti_SearchProgram);
        textView_period = (TextView) findViewById(R.id.textview_period_SearchProgram);

    }

    public void setTitle(String s) {
        textView_title.setText(s);
    }

    public void setInsti(String s) {
        textView_insti.setText(s);
    }

    public void setPeriod(String s) {
        textView_period.setText(s);
    }
}
