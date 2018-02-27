package kr.co.company.simbongsa;

import android.util.Log;

import java.util.HashMap;

/**
 * Created by BHY on 2018. 2. 16..
 */

public class SearchTool {
    private static String serviceUrl = "http://openapi.1365.go.kr/openapi/service/rest/VolunteerPartcptnService/getVltrSearchWordList?&_type=json&schSido=6110000";

    private static String KEY = "&ServiceKey=sW0b8TII8dGI4HmtRlJyUFViPXzm74IibcPaLh3Bq79rqZSj0QNNxs%2BzjYVMpT%2BLvYMCPl%2FqIiBd9EyHQjj4Ww%3D%3D";

    private String searchUrl = serviceUrl + KEY;

    private static String start = "&progrmBgnde=";
    private String startDay = null;

    private static String end = "&progrmEndde=";
    private String endDay = null;

    private static String adult = "&adultPosblAt=";
    private String adultYorN = null;
    private static String teenager = "&yngbgsPosblAt=";
    private String teenagerYorN = null;

    private static String keyword = "&keyword=";
    private String word = null;
    private String schCateGu = "&schCateGu=all";

    private static String category = "&upperClCode=";
    private String cate = null;

    private static String areaGuGun = "&schSign1=";
    private String guGun = null;

    private static String pageNo = "&pageNo=";

    private HashMap<String, String> map = new HashMap<>();

    public SearchTool() {
        pushData();
    }

    public SearchTool(String startDay, String endDay, String word, String cate, String guGun, String adultYorN, String teenagerYorN) {
        pushData();
        searchUrl = serviceUrl;
        this.startDay = startDay;
        this.endDay = endDay;
        this.word = word;
        this.cate = cate;
        this.guGun = guGun;
        this.adultYorN = adultYorN;
        this.teenagerYorN = teenagerYorN;
    }

    public void makeProgramUrl() {
        StringBuffer sb = new StringBuffer(searchUrl);
        String tmp;
        Log.d("value", "startDay: " + startDay + " endDay: " + endDay);
        if (startDay != null) {
            sb.append(start);
            sb.append(startDay);
        }
        if (endDay != null) {
            sb.append(end);
            sb.append(endDay);
        }
        if (word != null) {
            sb.append(keyword);
            sb.append(word);
            sb.append(schCateGu);
        }
        tmp = map.get(cate);
        if (tmp != null) {
            sb.append(category);
            sb.append(tmp);
        }
        tmp = map.get(guGun);
        if (tmp != null) {
            sb.append(areaGuGun);
            sb.append(tmp);
        }
        if (adultYorN != null) {
            sb.append(adult);
            sb.append(adultYorN);
        }
        if (teenagerYorN != null) {
            sb.append(teenager);
            sb.append(teenagerYorN);
        }
        searchUrl = sb.toString();
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public void nextPageUrl(int num, int max) {
        String pre = pageNo + String.valueOf(num);
        Boolean check = searchUrl.contains(pre);
        Log.d("value", "max : " + max + " num : " + num + " pre : " + pre);
        if (num < max) {
            num = num + 1;
        }
        if (check) {
            Log.d("value", "pageNo+String.valueOf(num):" + pageNo + String.valueOf(num));
            searchUrl = searchUrl.replace(pre, pageNo + String.valueOf(num));
        } else {
            Log.d("value2", "pageNo+String.valueOf(num):" + pageNo + String.valueOf(num));
            searchUrl = searchUrl + pageNo + String.valueOf(num);
        }
        Log.d("value", "searchUrl : " + searchUrl);
    }

    public void prePageUrl(int num) {
        String pre = pageNo + String.valueOf(num);
        Boolean check = searchUrl.contains(pre);

        if (num > 1) {
            num = num - 1;
        }
        if (check) {
            Log.d("value", "pageNo+String.valueOf(num):" + pageNo + String.valueOf(num));
            searchUrl = searchUrl.replace(pre, pageNo + String.valueOf(num));
        } else {
            Log.d("value2", "pageNo+String.valueOf(num):" + pageNo + String.valueOf(num));
            searchUrl = searchUrl + pageNo + String.valueOf(num);
        }
        Log.d("value", "searchUrl : " + searchUrl);
    }

    public void setSchCateGu(int check) {
        if (check == 0) {
            schCateGu = "&schCateGu=all";
        } else if (check == 1) {
            schCateGu = "&schCateGu=progrmCn";
        } else if (check == 2) {
            schCateGu = "&schCateGu=progrmSj";
        }
    }

    public void setStartDay(String startDay) {
        this.startDay = startDay;
    }

    public void setEndDay(String endDay) {
        this.endDay = endDay;
    }

    public void setAdultYorN(String adultYorN) {
        this.adultYorN = adultYorN;
    }

    public void setTeenagerYorN(String teenagerYorN) {
        this.teenagerYorN = teenagerYorN;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    public void setGuGun(String guGun) {
        this.guGun = guGun;
    }

    public void pushData() {
        map.put("전체", null);

        map.put("생활편의지원", "0100");
        map.put("주거환경", "0200");
        map.put("상담", "0300");
        map.put("교육", "0400");
        map.put("보건의료", "0500");
        map.put("농어촌봉사", "0600");
        map.put("문화체육", "0700");
        map.put("환경보호", "0800");
        map.put("행정지원", "0900");
        map.put("안전예방", "1000");
        map.put("공익,인권", "1100");
        map.put("재해,재난", "1200");
        map.put("국제협력,해외봉사", "1300");
        map.put("멘토링", "1400");
        map.put("자원봉사교육", "1500");
        map.put("국제행사", "1600");
        map.put("기타", "1700");

        map.put("강동구", "3240000");
        map.put("송파구", "3230000");
        map.put("강남구", "3220000");
        map.put("서초구", "3210000");
        map.put("관악구", "3200000");
        map.put("동작구", "3190000");
        map.put("영등포구", "3180000");
        map.put("금천구", "3170000");
        map.put("구로구", "3160000");
        map.put("강서구", "3150000");
        map.put("양천구", "3140000");
        map.put("마포구", "3130000");
        map.put("서대문구", "3120000");
        map.put("은평구", "3110000");
        map.put("노원구", "3100000");
        map.put(" 도봉구", "3090000");
        map.put("강북구", "3080000");
        map.put(" 성북구", "3070000");
        map.put(" 중랑구", "3060000");
        map.put(" 동대문구", "3050000");
        map.put("광진구", "3040000");
        map.put(" 성동구", "3030000");
        map.put("용산구", "3020000");
        map.put(" 중구", "3010000");
        map.put("종로구", "3000000");
    }
}
