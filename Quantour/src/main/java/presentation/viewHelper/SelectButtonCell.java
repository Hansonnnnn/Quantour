package presentation.viewHelper;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import vo.StockVO;

/**
 * Created by xiaoJun on 2017/4/12.
 */
public class SelectButtonCell extends TableCell<StockVO, Boolean> {
    private TableView table;
    private Button selectButton;
    private ImageView imageView;
    private Trolley trolley;
    Image image1 = new Image(getClass().getResourceAsStream("../image/selectButton.png"));
    Image image2 = new Image(getClass().getResourceAsStream("../image/selectButtonSecond.png"));

    public SelectButtonCell(TableView<StockVO> tableView) {
        this.table = tableView;
        trolley = Trolley.getTrolley();
        selectButton = new Button();
        imageView = new ImageView();
        selectButton.setStyle("-fx-background-color: transparent");
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        selectButton.setGraphic(imageView);
        trolley.getStockButtons().add(selectButton);
        trolley.getStockImageViews().add(imageView);
//        System.out.println("zzz    "+trolley.getStockImageViews().size());
    }

    @Override
    protected void updateItem(Boolean t, boolean empty){
        super.updateItem(t, empty);
        if(empty){
            setGraphic(null);
            setText(null);
        }else{
            StockVO selectedStockVO = (StockVO) table.getItems().get(getIndex());
            if (trolley.isSelected(selectedStockVO)) {
                imageView.setImage(image2);
                selectButton.setDisable(true);
            } else {
                imageView.setImage(image1);
                selectButton.setDisable(false);
            }
            selectButton.setOnAction((ActionEvent e) -> {
                imageView.setImage(image2);
                selectButton.setDisable(true);
                trolley.addStock(selectedStockVO, imageView, selectButton);
//                trolley.printAllStocksNames();//该行用来测试是否添加成功
            });
            setGraphic(selectButton);
            setText(null);
        }
    }
}