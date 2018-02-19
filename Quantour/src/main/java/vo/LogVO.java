package vo;

import java.util.Date;

/**
 * Created by xiezhenyu on 2017/3/14.
 */
public class LogVO {
    private Date date;
    private double logValue;//对数收益率
    private int code;//股票id
    private String name;//股票名称

    public LogVO() {
	
    }
    public LogVO(Date date, double logValue, int code, String name)
    {
        this.date = date;
        this.logValue = logValue;
        this.code = code;
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getLogValue() {
        return logValue;
    }

    public void setLogValue(double logValue) {
        this.logValue = logValue;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
