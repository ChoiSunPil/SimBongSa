package kr.co.company.simbongsa;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import org.json.JSONObject;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class DetailProgramActivity extends AppCompatActivity {

    String url;
    DetailProgramData detailProgramData;

    TextView textView_title;
    TextView textView_content;
    TextView textView_period;
    TextView textView_period2;
    TextView textView_time;
    GridView gridView_data1;
    GridView gridView_data2;
    TextView textView_admin;

    HashMap<String,String> map;
    String[] s = {"progrmBgnde", "progrmEndde", "actBeginTm", "actEndTm", "noticeBgnde", "noticeEndde"};
    String[] data1 = {"rcritNmpr" , "actWkdy" , "appTotal" , "srvcClCode" , "adultPosblAt" , "yngbgsPosblAt" , "familyPosblAt" , "pbsvntPosblAt" , "grpPosblAt" , "mnnstNm" , "nanmmbyNm" , "actPlace"};
    String[] data2 = {"telno", "fxnum", "postAdres", "email"};

    DetailProgramAdapter adapter;
    DetailProgramAdapter adapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_program);

        Intent intent = getIntent();
        url = intent.getStringExtra("detailUrl");
        detailProgramData = new DetailProgramData();

        textView_title = (TextView) findViewById(R.id.textview_title_DetailProgram);
        textView_content = (TextView) findViewById(R.id.textview_content_DetailProgram);
        textView_period = (TextView) findViewById(R.id.textview_period_DetailProgram);
        textView_period2 = (TextView) findViewById(R.id.textview_period2_DetailProgram);
        textView_time = (TextView) findViewById(R.id.textview_time_DetailProgram);
        textView_admin = (TextView) findViewById(R.id.textview_admin_DetailProgram);
        gridView_data1 = (GridView) findViewById(R.id.gridview_data_DetailProgram);
        gridView_data2 = (GridView) findViewById(R.id.gridview_data2_DetailProgram);
        adapter = new DetailProgramAdapter();
        adapter2 = new DetailProgramAdapter();

        for(int i=0;i<data1.length;i++){
            adapter.addItem(data1[i]);
        }
        for(int i=0;i<data2.length;i++){
            adapter2.addItem(data2[i]);
        }
        new getJsonTask().execute();

    }

    private class getJsonTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... strings) {
            return getJson();
        }

        @Override
        protected void onPostExecute(Integer check) {
            Log.d("dkdk", check.toString());
            String tmp;

            textView_title.setText(detailProgramData.getData("progrmSj"));
            textView_content.setText("\n\n"+detailProgramData.getData("progrmCn")+"\n\n");
            tmp = detailProgramData.getData("progrmBgnde") + " ~ " + detailProgramData.getData("progrmEndde");
            textView_period.setText(tmp);
            tmp = detailProgramData.getData("noticeBgnde") + " ~ " + detailProgramData.getData("noticeEndde");
            textView_period2.setText(tmp);
            tmp = detailProgramData.getData("actBeginTm") + " ~ " + detailProgramData.getData("actEndTm");
            textView_time.setText(tmp);
            textView_admin.setText("담당자명: "+detailProgramData.getData("nanmmbyNmAdmn"));

            gridView_data1.setAdapter(adapter);
            gridView_data2.setAdapter(adapter2);
        }
    }

    public int getJson(){
        StringBuffer stringBuffer = new StringBuffer();



        Log.d("url" ,"url : "+url);
        try{

            String jsonPage = getDataFromUrl(url);
            Log.d("pp", "for문 전");
            JSONObject jsonObject = new JSONObject(jsonPage);
            Log.d("pp", "for문 전2");

            JSONObject jsonObject1 = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONObject("item");
            Log.d("pp", "for문 전3");

            Iterator iterator = jsonObject1.keys();
            while(iterator.hasNext()){
                String key = iterator.next().toString();
                Log.d("value", key);
                detailProgramData.setData(key,jsonObject1.getString(key));
            }
        }catch (Exception e){
            Log.e("error","error:getJson()");
        }
        return 0;
    }

    public String getDataFromUrl(String jUrl) {
        BufferedReader bufferedReader = null;
        HttpURLConnection httpURLConnection = null;

        StringBuffer stringBuffer = new StringBuffer();

        try {
            URL url = new URL(jUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream contentStream = httpURLConnection.getInputStream();

            bufferedReader = new BufferedReader(new InputStreamReader(contentStream, "UTF-8"));
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                Log.d("line:", line);
                stringBuffer.append(line);
            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //자원해제
            try {
                bufferedReader.close();
                httpURLConnection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return stringBuffer.toString();
    }

    private class DetailProgramAdapter extends BaseAdapter {
        private ArrayList<String> items = new ArrayList<String>();

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int i) {
            return items.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            DetailProgramView detailProgramView = (DetailProgramView) view;
            if(detailProgramView==null){
                detailProgramView = new DetailProgramView(getApplicationContext());
            }
            map=detailProgramData.getMap();

            String key = items.get(i);

            detailProgramView.setTextView_key(detailProgramData.changeName(key));
            detailProgramView.setTextView_value(map.get(key));
            Log.d("value" , key);

            return detailProgramView;
        }

        void addItem(String item){
            items.add(item);
        }
    }
}
