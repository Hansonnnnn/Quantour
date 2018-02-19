package presentation;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import presentation.chart.CandBReturnsChart;
import presentation.chart.YieldBarChart;
import vo.ColumnDiagramVO;
import vo.StrategyResultVO;

import java.text.DecimalFormat;

/**
 * Created by LENOVO on 2017/4/16.
 */
public class StrategyResultUIController {
    @FXML
    private Label yearRateLabel;
    @FXML
    private Label baseRateLabel;
    @FXML
    private Label alphaLabel;
    @FXML
    private Label betaLabel;
    @FXML
    private Label sharpLabel;
    @FXML
    private Label retreatLabel;

    @FXML
    private Label positiveProfitLabel;
    @FXML
    private Label nagivateProfitLabel;
    @FXML
    private Label winRateLabel;
    @FXML
    private Button sliderButton;
    @FXML
    private VBox accumulateVBox;
    @FXML
    private VBox distributionVBox;


    private Stage stage;
    private VBox beforeVBox;
    private ScrollPane contentScrollPane;
    private StrategyResultVO strategyResultVO;
    private ColumnDiagramVO columnDiagramVO;

    public void init(VBox beforeVBox, Stage stage, ScrollPane contentScrollPane, StrategyResultVO strategyResultVO, ColumnDiagramVO columnDiagramVO){
        this.stage = stage;
        this.beforeVBox = beforeVBox;
        this.contentScrollPane = contentScrollPane;
        this.strategyResultVO = strategyResultVO;
        this.columnDiagramVO = columnDiagramVO;
        initLabel();
    }

    /**
     * 点击返回按钮，返回到上一个界面
     */
    @FXML
    private void back(){
        contentScrollPane.setContent(beforeVBox);
    }

    /**
     * 初始化信息
     */
    private void initLabel(){
        yearRateLabel.setText(String.valueOf(strategyResultVO.getAnnualizedRateOfReturn())+"%");
        baseRateLabel.setText(String.valueOf(strategyResultVO.getBaseAnnualizedRateOfReturn())+"%");
        alphaLabel.setText(String.valueOf(strategyResultVO.getAlpha())+"%");
        betaLabel.setText(String.valueOf(strategyResultVO.getBeta()));
        sharpLabel.setText(String.valueOf(strategyResultVO.getSharpeRatio()));
        retreatLabel.setText(String.valueOf(strategyResultVO.getBiggestReturn())+"%");

        positiveProfitLabel.setText(String.valueOf(columnDiagramVO.getPeCircleNum()));
        nagivateProfitLabel.setText(String.valueOf(columnDiagramVO.getNeCircleNum()));
        winRateLabel.setText(toPercentage(columnDiagramVO.getWinRate()));
        accumulateVBox.getChildren().add(new CandBReturnsChart(strategyResultVO.getAccumulativRate(),strategyResultVO.getBaseAccumulativRate()));
        distributionVBox.getChildren().add(new YieldBarChart(columnDiagramVO));
    }

    /**
     * 点击累计收益率按钮，显示策略和基准收益率折线图
     */
    @FXML
    private void toAccumulativeRate(){
        sliderButton.setText("累计收益率");
        sliderButton.setLayoutX(0);
        sliderButton.setLayoutY(62);
        accumulateVBox.setVisible(true);
        distributionVBox.setVisible(false);
    }

    /**
     * 点击收益率分布按钮，显示收益率分布直方图
     */
    @FXML
    private void toDistribution(){
        sliderButton.setText("收益率分布");
        sliderButton.setLayoutX(103);
        sliderButton.setLayoutY(62);
        distributionVBox.setVisible(true);
        accumulateVBox.setVisible(false);
    }

    /**
     * 将double转换成保留两位小数的百分数
     */
    private String toPercentage(double num){
        num = num * 100;
        DecimalFormat df = new DecimalFormat("#.00");
        String result = df.format(num);
        return result+"%";
    }
}
