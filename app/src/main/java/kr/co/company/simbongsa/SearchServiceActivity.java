package kr.co.company.simbongsa;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputContentInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SearchServiceActivity extends AppCompatActivity {

    Button button;
    TextView textView;
    ListView listView;
    SearchProgramAdapter adapter;
    EditText editText;
    Button button_pre;
    Button button_next;
    TextView textView_pageNo;

    Spinner spinner_area;
    Spinner spinner_cate;
    CheckBox checkBox_adult;
    CheckBox checkBox_teen;
    EditText editText_start;
    EditText editText_end;

    String page;
    String totalcount;
    int max;
    String startDay=null;
    String endDay=null;
    String word=null;
    String cate=null;
    String guGun=null;

    SearchTool searchTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_service);

        button = (Button) findViewById(R.id.button_getdata_SearchService);
        textView = (TextView) findViewById(R.id.textview_data_SearchService);
        listView = (ListView) findViewById(R.id.listview_searchservice);
        editText = (EditText) findViewById(R.id.edittext_SearchService);
        button_next = (Button) findViewById(R.id.button_next_SearchService);
        button_pre = (Button) findViewById(R.id.button_pre_SearchService);
        textView_pageNo= (TextView) findViewById(R.id.textview_pageno_SearchService);
        spinner_area = (Spinner) findViewById(R.id.spinner_SearchService);
        spinner_cate = (Spinner) findViewById(R.id.spinner_SearchService2);
        checkBox_adult = (CheckBox) findViewById(R.id.checkbox_adult_SearchService);
        checkBox_teen = (CheckBox) findViewById(R.id.checkbox_teen_SearchService);
        editText_start = (EditText) findViewById(R.id.edittext_start_SearchService);
        editText_end = (EditText) findViewById(R.id.edittext_end_SearchService);

        adapter = new SearchProgramAdapter();

        ArrayAdapter<CharSequence> adapter_spinner = ArrayAdapter.createFromResource(this,
                R.array.gugun, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner_area.setAdapter(adapter_spinner);
        spinner_area.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                guGun = (String)adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        ArrayAdapter<CharSequence> adapter_spinner2 = ArrayAdapter.createFromResource(this,
                R.array.cate, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter_spinner2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner_cate.setAdapter(adapter_spinner2);
        spinner_cate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cate = (String)adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchTool = new SearchTool();
                if(!editText_start.equals("")) {
                    searchTool.setStartDay(editText_start.getText().toString());
                }
                if(!editText_end.equals("")) {
                    searchTool.setEndDay(editText_end.getText().toString());
                }
                searchTool.setCate(cate);
                searchTool.setGuGun(guGun);
                if(!editText.equals("")) {
                    searchTool.setWord(editText.getText().toString());
                }
                if(!(checkBox_adult.isChecked()&&checkBox_teen.isChecked())){
                    if(checkBox_adult.isChecked()){
                        searchTool.setAdultYorN("Y");
                    }
                    else if(checkBox_teen.isChecked()){
                        searchTool.setTeenagerYorN("Y");
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"둘 중 한개는 선택해야합니다",Toast.LENGTH_SHORT).show();
                        checkBox_adult.setChecked(true);
                    }
                }
                searchTool.makeProgramUrl();
                new getJsonTask().execute();
            }
        });

        button_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchTool!=null){
                    Log.d("value" , "page : " + page);
                    searchTool.prePageUrl(Integer.parseInt(page));
                    new getJsonTask().execute();
                }
            }
        });

        button_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(searchTool!=null){
                    if(((Integer.parseInt(totalcount))%10)>0) {
                        max = ((Integer.parseInt(totalcount)) / 10) + 1;
                    }
                    else{
                        max = ((Integer.parseInt(totalcount)) / 10);
                    }
                    Log.d("value" , "max : " + max + "page : " + page );
                    searchTool.nextPageUrl(Integer.parseInt(page),max);
                    new getJsonTask().execute();
                }
            }
        });
    }

    private class getJsonTask extends AsyncTask<String, Void, Integer> {
        @Override
        protected Integer doInBackground(String... strings) {
            adapter.clearItems();
            publishProgress();
            return getJson();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            button_next.setEnabled(false);
            button_pre.setEnabled(false);
            button.setEnabled(false);
        }

        @Override
        protected void onPostExecute(Integer check) {
            Log.d("dkdk", check.toString());
            if(check==-1){
                textView.setText("검색결과가 없습니다.");
                listView.setAdapter(adapter);
                button_next.setEnabled(true);
                button_pre.setEnabled(true);
                button.setEnabled(true);
                return;
            }
            textView.setText("");
            listView.setAdapter(adapter);
            textView_pageNo.setText(page);
            button_next.setEnabled(true);
            button_pre.setEnabled(true);
            button.setEnabled(true);
        }
    }

    public int getJson(){
        StringBuffer stringBuffer = new StringBuffer();



        Log.d("url" ,"url : "+searchTool.getSearchUrl());
        try{

            String jsonPage = getDataFromUrl(searchTool.getSearchUrl());
            Log.d("pp", "for문 전");
            JSONObject jsonObject = new JSONObject(jsonPage);
            Log.d("pp", "for문 전2");
            page = jsonObject.getJSONObject("response").getJSONObject("body").getString("pageNo");
            totalcount = jsonObject.getJSONObject("response").getJSONObject("body").getString("totalCount");
            if(totalcount.equals("0")) {
                return -1;
            }
                JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");
                Log.d("pp", "for문 전3");


                for(int i=0;i<jsonArray.length();i++){
                    jsonObject = jsonArray.getJSONObject(i);

                    String programTilte = jsonObject.getString("progrmSj");
                    String programStart = jsonObject.getString("progrmBgnde");
                    String programEnd = jsonObject.getString("progrmEndde");
                    String programNo = jsonObject.getString("progrmRegistNo");
                    String programsido = jsonObject.getString("sidoCd");
                    String programInsti = jsonObject.getString("nanmmbyNm");
                    String programState = jsonObject.getString("progrmSttusSe");

                    Log.d("program", programTilte+programNo);

                    adapter.addItem(new SearchProgramData(Integer.parseInt(programNo), programTilte, programInsti, programStart, programEnd, Integer.parseInt(programState)));

                    stringBuffer.append("<"+programTilte+">\n");
                    stringBuffer.append("프로그램 번호: " + programNo+"\n");
                    stringBuffer.append("지역: " + programsido + "\n");
                    stringBuffer.append("프로그램 기간: " + programStart + " ~ " + programEnd + "\n\n" );

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


    private class SearchProgramAdapter extends BaseAdapter {
        private ArrayList<SearchProgramData> items = new ArrayList<>();

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
            SearchProgramView searchProgramView = (SearchProgramView) view;
            if(searchProgramView==null){
                searchProgramView = new SearchProgramView(getApplicationContext());
            }
            SearchProgramData item = items.get(i);
            searchProgramView.setTitle(item.getTitle());
            searchProgramView.setInsti(item.getInsti());
            searchProgramView.setPeriod(item.getStart()+"~"+item.getEnd());

            return searchProgramView;
        }


        void addItem(SearchProgramData item){
            items.add(item);
        }
        void clearItems(){
            items.clear();
        }
    }
}