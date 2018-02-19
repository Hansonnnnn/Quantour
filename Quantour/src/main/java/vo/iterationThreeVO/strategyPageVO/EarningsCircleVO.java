package vo.iterationThreeVO.strategyPageVO;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by xiezhenyu on 2017/5/25.
 * 展示收益周期时用到该VO
 */
public class EarningsCircleVO {
    //正收益率数据所组成的集合，key为对数收益率，value为该对数收益率对应的频数
    private Map<Integer, Integer> peData;
    //负收益率数据所组成的集合，key为对数收益率，value为该对数收益率对应的频数
    private Map<Integer, Integer> neData;
    //正收益周期数
    private int peCircleNum;
    //负收益周期数
    private int neCircleNum;
    //策略赢率
    private double winRate;

    public EarningsCircleVO(){
        peData=new LinkedHashMap<>();
        neData=new LinkedHashMap<>();
    }
    public double getWinRate() {
        return winRate;
    }

    public void setWinRate(double winRate) {
        this.winRate = winRate;
    }

    public Map<Integer, Integer> getPeData() {
        return peData;
    }

    public void setPeData(Map<Integer, Integer> peData) {
        this.peData = peData;
    }

    public Map<Integer, Integer> getNeData() {
        return neData;
    }

    public void setNeData(Map<Integer, Integer> neData) {
        this.neData = neData;
    }

    public int getPeCircleNum() {
        return peCircleNum;
    }

    public void setPeCircleNum(int peCircleNum) {
        this.peCircleNum = peCircleNum;
    }

    public int getNeCircleNum() {
        return neCircleNum;
    }

    public void setNeCircleNum(int neCircleNum) {
        this.neCircleNum = neCircleNum;
    }
}
