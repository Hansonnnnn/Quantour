package vo.iterationThreeVO.strategyPageVO;

import java.util.Date;
import java.util.Map;

/**
 * Created by xiezhenyu on 2017/5/25.
 * 该VO是在展示BP神经网络算法准确性时用到
 */
public class PrecisionVO {
    //开盘价的集合，一个日期对应于一个开盘价
    private Map<Date, Double> openSet;
    //收盘价的集合
    private Map<Date, Double> closeSet;
    //最高价的集合
    private Map<Date, Double> highSet;
    //最低价的集合
    private Map<Date, Double> lowSet;
    //成交量的集合
    private Map<Date, Integer> volumnSet;

    public PrecisionVO() {

    }

    public PrecisionVO(Map<Date, Double> openSet, Map<Date, Double> closeSet, Map<Date, Double> highSet, Map<Date, Double> lowSet, Map<Date, Integer> volumnSet) {
        this.openSet = openSet;
        this.closeSet = closeSet;
        this.highSet = highSet;
        this.lowSet = lowSet;
        this.volumnSet = volumnSet;
    }

    public Map<Date, Double> getOpenSet() {
        return openSet;
    }

    public void setOpenSet(Map<Date, Double> openSet) {
        this.openSet = openSet;
    }

    public Map<Date, Double> getCloseSet() {
        return closeSet;
    }

    public void setCloseSet(Map<Date, Double> closeSet) {
        this.closeSet = closeSet;
    }

    public Map<Date, Double> getHighSet() {
        return highSet;
    }

    public void setHighSet(Map<Date, Double> highSet) {
        this.highSet = highSet;
    }

    public Map<Date, Double> getLowSet() {
        return lowSet;
    }

    public void setLowSet(Map<Date, Double> lowSet) {
        this.lowSet = lowSet;
    }

    public Map<Date, Integer> getVolumnSet() {
        return volumnSet;
    }

    public void setVolumnSet(Map<Date, Integer> volumnSet) {
        this.volumnSet = volumnSet;
    }
}
