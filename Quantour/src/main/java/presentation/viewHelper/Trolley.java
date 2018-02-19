package presentation.viewHelper;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.ResultMessage;
import vo.StockVO;
import vo.TrolleyVO;

import java.util.ArrayList;

/**
 * Created by xiezhenyu on 2017/4/8.
 */
public class Trolley {
    private static Trolley trolley;
    private ArrayList<String> stockNames;
    private ArrayList<Button> stockButtons;
    private ArrayList<ImageView> stockImageViews;
    private ArrayList<TrolleyVO> selectedStockNames;
    private ArrayList<Button> selectedStockButtons;
    private ArrayList<ImageView> selectedStockImageView;
    private Button trolleyButton;
    private Button optionButton;
    private ImageView optionImageView;
    private Trolley()
    {
        stockNames = new ArrayList<>();
        stockButtons = new ArrayList<>();
        stockImageViews = new ArrayList<>();
        selectedStockNames = new ArrayList<>();
        selectedStockButtons = new ArrayList<>();
        selectedStockImageView = new ArrayList<>();
    }

    public static Trolley getTrolley()
    {
        if(trolley==null)
        {
            trolley = new Trolley();
            return trolley;
        }else
        {
            return trolley;
        }
    }

    /**
     * 添加自选股
     * @param stockVO 自选股stockVO
     * @param imageView 自选按钮图片
     * @param thisVOButton 自选股按钮
     * @return 添加结果
     */
    public ResultMessage addStock(StockVO stockVO, ImageView imageView, Button thisVOButton)
    {
        if (stockVO==null)
        {
            return ResultMessage.fail;
        }else
        {
            selectedStockNames.add(new TrolleyVO(stockVO.getName(), stockVO.getId()));
            selectedStockButtons.add(thisVOButton);
            selectedStockImageView.add(imageView);
            addCountLabel();
            return ResultMessage.success;
        }
    }

    /**
     * 在股票详情界面添加自选股
     */
    public ResultMessage addStock(StockVO stockVO){
        if (stockVO==null){
            return ResultMessage.fail;
        }else{
            selectedStockNames.add(new TrolleyVO(stockVO.getName(), stockVO.getId()));
            addCountLabel();
            return ResultMessage.success;
        }
    }



    /**
     * 删除特定的自选股
     * @param trolleyVO 待删除的自选股VO
     * @return 删除结果
     */
    public ResultMessage deleteStock(TrolleyVO trolleyVO){
        for (int i = 0;i < selectedStockNames.size();i++){
            if(trolleyVO.getName().equals(selectedStockNames.get(i).getName())){
                selectedStockNames.remove(i);
                selectedStockImageView.get(i).setImage(new Image(getClass().getResourceAsStream("../image/selectButton.png")));
                selectedStockButtons.get(i).setDisable(false);
                selectedStockImageView.remove(i);
                selectedStockButtons.remove(i);
                subCountLabel();
//                printAllStocksNames();
                optionButton.setStyle("-fx-text-fill:white;-fx-font-family:\"微软雅黑\";-fx-border-radius: 4px;-fx-background-color: linear-gradient(#2E4E7E, #2E4E6E);");
                optionButton.setText("加入自选股");
                optionButton.setDisable(false);
                optionImageView.setImage(new Image(getClass().getResourceAsStream("../image/add.png")));
                break;
            }
        }
        return ResultMessage.success;
    }

    /**
     * 删除所有自选股
     */
    public void deleteAllStock(){
        for(int i = 0; i < selectedStockNames.size();){
            deleteStock(selectedStockNames.get(i));
        }
    }

    /**
     * 打印所有自选股名称
     */
    public void printAllStocksNames(){
        for (int i = 0;i < selectedStockNames.size();i++){
            System.out.println(selectedStockNames.get(i).getName());
        }
        System.out.println(selectedStockButtons.size()+"  "+selectedStockImageView.size());
    }

    /**
     * 获取自选股票列表
     * @return 自选股list
     */
    public ArrayList<TrolleyVO> getSelectedStocks(){
        return selectedStockNames;
    }

    public ArrayList<Button> getSelectedButton(){
        return selectedStockButtons;
    }

    public ArrayList<ImageView> getSelectedImageView(){
        return selectedStockImageView;
    }

    /**
     * 获取自选股按钮
     * @param trolleyButton 自选股按钮
     */
    public void setCountLabel(Button trolleyButton){
        this.trolleyButton = trolleyButton;
    }

    /**
     * 判断某只股票是否被选为自选股
     * @param stockVO 待判断股票VO
     */
    public boolean isSelected(StockVO stockVO){
        for(int i = 0;i < selectedStockNames.size();i++){
            if(stockVO.getName().equals(selectedStockNames.get(i).getName())){
                return true;
            }
        }
        return false;
    }

    /**
     *将自选股数量加1
     */
    private void addCountLabel(){
        String before = trolleyButton.getText();
        int count = Integer.parseInt(before)+1;
        trolleyButton.setText(String.valueOf(count));
    }

    /**
     * 将自选股数量减1
     */
    private void subCountLabel(){
        String before = trolleyButton.getText();
        int count = Integer.parseInt(before)-1;
        trolleyButton.setText(String.valueOf(count));
    }

    /**
     * 获取股票详情界面添加自选股按钮
     */
    public void setOptionButtonAndImageView(Button optionButton,ImageView optionImageView){
        this.optionButton = optionButton;
        this.optionImageView = optionImageView;
    }

    /**
     * 清空button和imageview的List
     */
    public void clearButtonAndImageView(){
        selectedStockButtons.clear();
        selectedStockImageView.clear();
        System.out.println("hhe    "+selectedStockImageView.size());
    }

    public ArrayList<TrolleyVO> getSelectedStockNames(){
        return selectedStockNames;
    }

    public ArrayList<Button> getSelectedStockButtons(){
        return selectedStockButtons;
    }

    public ArrayList<ImageView> getSelectedStockImageView(){
        return selectedStockImageView;
    }

    public ArrayList<String> getStockNames(){
        return stockNames;
    }

    public ArrayList<Button> getStockButtons(){
        return stockButtons;
    }

    public ArrayList<ImageView> getStockImageViews(){
        return stockImageViews;
    }


}
