package kr.co.company.simbongsa;

import java.util.HashMap;

/**
 * Created by BHY on 2018. 2. 27..
 */

public class DetailProgramData {


    HashMap<String, String> map;

    public DetailProgramData() {
        map = new HashMap<>();
    }

    public void setData(String key, String value) {
        map.put(key, value);
    }

    public String getData(String key){
        return map.get(key);
    }

    public HashMap<String, String> getMap() {
        return map;
    }
}
