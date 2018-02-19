package model;

/**
 * Created by xiezhenyu on 2017/5/25.
 * 该枚举类用来标明用户新建策略时使用筛选条件的类型
 * 分别是
 * 开盘价， 收盘价， 最高价， 最低价，
 * 日涨幅， 月涨幅， 年涨幅
 * 日成交量， 月成交量， 年成交量
 */
public enum StrategyIndex {
    OPEN, CLOSE, HIGH, LOW,
    DayUp, MonthUp,
    DayVolumn, WeekVolumn, MonthVolumn
}
