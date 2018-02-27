package kr.co.company.simbongsa;

/**
 * Created by BHY on 2018. 2. 16..
 */

public class SearchProgramData {
    private Integer RegistNo;
    private String title;
    private String insti;
    private String start;
    private String end;
    private Integer state;

    /*프로그램등록번호
    봉사제목
    모집기관(나눔주체명)
    봉사시작일자
    봉사종료일자
    모집상태*/

    public SearchProgramData(){}

    public SearchProgramData(Integer registNo, String title, String insti, String start, String end, Integer state) {
        RegistNo = registNo;
        this.title = title;
        this.insti = insti;
        this.start = start;
        this.end = end;
        this.state = state;
    }

    public Integer getRegistNo() {
        return RegistNo;
    }

    public String getTitle() {
        return title;
    }

    public String getInsti() {
        return insti;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public Integer getState() {
        return state;
    }
}
