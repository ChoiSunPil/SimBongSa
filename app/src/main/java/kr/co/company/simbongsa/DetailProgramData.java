package kr.co.company.simbongsa;

import java.util.HashMap;

/**
 * Created by BHY on 2018. 2. 27..
 */

public class DetailProgramData {


    HashMap<String, String> map;
    HashMap<String, String> name;

    public DetailProgramData() {
        map = new HashMap<>();
        name = new HashMap<>();
        init();
    }

    public void setData(String key, String value) {
        map.put(key, value);
    }

    public String getData(String key) {
        return map.get(key);
    }

    public HashMap<String, String> getMap() {
        return map;
    }

    public String changeName(String key){
        return name.get(key);
    }
    public void init() {
        name.put("progrmBgnde", "봉사시작일자");
        name.put("progrmEndde", "봉사종료일자");
        name.put("actBeginTm", "봉사시작시간");
        name.put("actEndTm", "봉사종료시간");
        name.put("noticeBgnde", "모집시작일");
        name.put("noticeEndde", "모집종료일");
        name.put("rcritNmpr", "모집인원");
        name.put("actWkdy", "활동요일");
        name.put("appTotal", "신청인원수");
        name.put("srvcClCode", "봉사분야");
        name.put("adultPosblAt", "성인가능여부");
        name.put("yngbgsPosblAt", "청소년가능여부");
        name.put("familyPosblAt", "가족가능여부");
        name.put("pbsvntPosblAt", "공무원가능여부");
        name.put("grpPosblAt", "단체가능여부");
        name.put("mnnstNm", "모집기관(주관기관명)");
        name.put("nanmmbyNm", "등록기관(나눔주체명)");
        name.put("actPlace", "봉사장소");
        name.put("nanmmbyNmAdmn", "담당자명");
        name.put("telno", "전화번호");
        name.put("fxnum", "FAX번호");
        name.put("postAdres", "담당자 주소");
        name.put("email", "이메일");
        name.put("progrmCn", "내용");
    }
}
