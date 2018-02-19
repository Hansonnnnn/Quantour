package model;

/**
 * Created by xiezhenyu on 2017/5/25.
 * 该枚举类指明策略界面展示BP神经网络算法准确性时，选择展示的指标（Index）
 * 分别是开盘价、收盘价、最高价、最低价、成交量
 */
public enum PrecisionIndex {
    OPEN, CLOSE, HIGH, LOW, VOLUMN
}
