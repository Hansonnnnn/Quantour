package presentation.chart;

import javafx.embed.swing.SwingNode;
import javafx.scene.image.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.*;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.CandlestickRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import vo.StockInfoForK_lineVO;

import java.awt.*;
import java.awt.Image;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by LENOVO on 2017/3/12.
 */
public class KlineChart extends SwingNode {

    private SimpleDateFormat dateFormat;
    private OHLCSeries series;
    private ArrayList<StockInfoForK_lineVO> infoVOList;
    private Date startDate;
    private Date endDate;
    private double highValue = Double.MIN_VALUE;//设置K线数据当中的最大值
    private double minValue = Double.MAX_VALUE;//设置K线数据当中的最小值
    private final OHLCSeriesCollection seriesCollection = new OHLCSeriesCollection();//保留K线数据的数据集，必须申明为final，后面要在匿名内部类里面用到
    private final CandlestickRenderer candlestickRender = new CandlestickRenderer();//设置K线图的画图器，必须申明为final，后面要在匿名部类里面用到
    private DateAxis x1Axis;
    private NumberAxis y1Axis;
    private XYPlot plot1;

    public KlineChart(ArrayList<StockInfoForK_lineVO> infoVOList, Date startDate, Date endDate) {
        this.infoVOList = infoVOList;
        this.startDate = startDate;
        this.endDate = endDate;
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        getData();//获取K线图数据
        seriesCollection.addSeries(series);
        getMaxAndMinValue();//获得K线图数据的最大值和最小值
        setCandlestickRenderStyle();//设置画图器的样式
        setDateAxisStyle();//设置X轴的样式
        setNumberAxisStyle();//设置Y轴的样式
        plot1 = new XYPlot(seriesCollection, x1Axis, y1Axis, candlestickRender);//设置画图区域对象
        plot1.setDomainGridlinesVisible(false);
        plot1.setBackgroundPaint(new Color(25, 27, 31));
        plot1.setRangeGridlinePaint(Color.WHITE);
        plot1.setDomainGridlinePaint(Color.WHITE);
        JFreeChart chart = new JFreeChart(" ", JFreeChart.DEFAULT_TITLE_FONT, plot1, false);
        chart.setBackgroundPaint(new Color(25, 27, 31));
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(920, 600));
        chartPanel.setMaximumSize(new Dimension(920,600));
        chartPanel.setMinimumSize(new Dimension(920,600));
        this.setContent(chartPanel);
    }

    /**
     * 实现日期加一
     *
     * @param date 待增加的原日期
     * @return 增加一天后的日期
     */
    private Date addOneDay(Date date) {
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, 1);  //把日期往后增加一天
        date = calendar.getTime();
        return date;
    }

    /**
     * 获取K线图数据
     */
    private void getData() {
        series = new OHLCSeries("");//高开低收数据序列，股票K线图的四个数据，依次是开，高，低, 收
        for (int i = 0; i < infoVOList.size(); i++) {
            StockInfoForK_lineVO stockInfoVO = infoVOList.get(i);
            series.add(new Day(stockInfoVO.getDate()), stockInfoVO.getOpenValue(), stockInfoVO.getHighValue(), stockInfoVO.getLowValue(), stockInfoVO.getCloseValue());
        }
    }

    /**
     * 获取K线图数据的最大值和最小值
     */
    private void getMaxAndMinValue() {
        int seriesCount = seriesCollection.getSeriesCount();//一共有多少个序列，目前为一个
        for (int i = 0; i < seriesCount; i++) {
            int itemCount = seriesCollection.getItemCount(i);//每一个序列有多少个数据项
            for (int j = 0; j < itemCount; j++) {
                if (highValue < seriesCollection.getHighValue(i, j)) {//取第i个序列中的第j个数据项的最大值
                    highValue = seriesCollection.getHighValue(i, j);
                }
                if (minValue > seriesCollection.getLowValue(i, j)) {//取第i个序列中的第j个数据项的最小值
                    minValue = seriesCollection.getLowValue(i, j);
                }
            }
        }
    }

    /**
     * 设置CandlestickRender画图器样式
     */
    private void setCandlestickRenderStyle() {
        candlestickRender.setUseOutlinePaint(true); //设置是否使用自定义的边框线，程序自带的边框线的颜色不符合中国股票市场的习惯
        candlestickRender.setAutoWidthMethod(CandlestickRenderer.WIDTHMETHOD_AVERAGE);//设置如何对K线图的宽度进行设定
        candlestickRender.setAutoWidthGap(0.1);//设置各个K线图之间的间隔
        candlestickRender.setUpPaint(new Color(184, 37, 37));//设置股票上涨的K线图颜色
        candlestickRender.setDownPaint(new Color(91, 198, 72));//设置股票下跌的K线图颜色
//        candlestickRender.setOutlinePaint(Color.WHITE);
        candlestickRender.setBaseOutlinePaint(Color.WHITE);
        candlestickRender.setItemLabelFont(new Font("微软雅黑", Font.BOLD, 18));
    }

    /**
     * 设置日期轴样式
     */
    private void setDateAxisStyle() {
        x1Axis = new DateAxis();//设置x轴，也就是时间轴
        x1Axis.setAutoRange(true);//设置不采用自动设置时间范围
        x1Axis.setTimeline(SegmentedTimeline.newMondayThroughFridayTimeline());//设置时间线显示的规则，用这个方法就摒除掉了周六和周日这些没有交易的日期(很多人都不知道有此方法)，使图形看上去连续
        x1Axis.setAutoTickUnitSelection(false);//设置不采用自动选择刻度值
        x1Axis.setTickMarkPosition(DateTickMarkPosition.MIDDLE);//设置标记的位置
        x1Axis.setStandardTickUnits(DateAxis.createStandardDateTickUnits());//设置标准的时间刻度单位
        x1Axis.setTickUnit(new DateTickUnit(DateTickUnit.DAY, 1));//设置时间刻度的间隔，一般以周为单位
        x1Axis.setDateFormatOverride(new SimpleDateFormat("yyyy-MM-dd"));//设置显示时间的格式
        x1Axis.setLabel("日期");        //设置坐标轴标题
        x1Axis.setLabelPaint(Color.white);     //设置坐标轴字体颜色
        x1Axis.setLabelFont(new Font("微软雅黑", Font.BOLD, 20));  //设置坐标轴标题字体
        x1Axis.setVisible(true);
        x1Axis.setTickLabelFont(new Font("Tahoma", Font.PLAIN, 12));
        x1Axis.setTickLabelPaint(Color.white);
        x1Axis.setVerticalTickLabels(true);
    }

    /**
     * 设置数值轴样式
     */
    private void setNumberAxisStyle() {
        y1Axis = new NumberAxis();//设定y轴，就是数字轴
        y1Axis.setAutoRange(false);//不不使用自动设定范围
        y1Axis.setRange(minValue * 0.98, highValue * 1.02);//设定y轴值的范围，比最低值要低一些，比最大值要大一些，这样图形看起来会美观些
        y1Axis.setTickUnit(new NumberTickUnit((highValue * 1.02 - minValue * 0.98) / 10));//设置刻度显示的密度
        y1Axis.setTickLabelFont(new Font("Tahoma", Font.PLAIN, 12));
        y1Axis.setTickLabelPaint(Color.white);
    }
}
