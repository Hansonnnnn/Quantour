package presentation;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Callback;
import presentation.viewHelper.Trolley;
import vo.TrolleyVO;

import java.util.ArrayList;

/**
 * Created by xiezhenyu on 2017/4/8.
 */
public class TrolleyPageUIController {
    private Popup popup;
    private double xOffset = 0;
    private double yOffset = 0;
    @FXML private TableView<TrolleyVO> tableView;
    @FXML private TableColumn<TrolleyVO, Boolean> idColumn;
    @FXML private TableColumn<TrolleyVO, Boolean> nameColumn;
    @FXML private TableColumn<TrolleyVO, Boolean> deleteButtonColumn;
    @FXML private Button exitButton;

    private ObservableList<TrolleyVO> stockNameData;

    private Trolley trolley;

    public void init(Popup popup){
        this.popup = popup;
        trolley = Trolley.getTrolley();
//        drag();
        initTable();
    }


    private void initTable()
    {
        idColumn.setCellValueFactory(new PropertyValueFactory<TrolleyVO, Boolean>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<TrolleyVO, Boolean>("name"));
        deleteButtonColumn.setCellFactory(new Callback<TableColumn<TrolleyVO, Boolean>, TableCell<TrolleyVO, Boolean>>() {
            @Override
            public TableCell<TrolleyVO, Boolean> call(TableColumn<TrolleyVO, Boolean> param) {
                return new DeleteButtonCell();
            }
        });

        ArrayList<TrolleyVO> trolleyVOArrayList = trolley.getSelectedStocks();
        stockNameData = FXCollections.observableArrayList();
        for (int i = 0;i < trolleyVOArrayList.size();i++)
        {
            stockNameData.add(trolleyVOArrayList.get(i));
        }
        tableView.setItems(stockNameData);
    }


    public class DeleteButtonCell extends TableCell<TrolleyVO, Boolean>
    {
        private Button deleteButton = new Button();
        public DeleteButtonCell()
        {
            deleteButton.setStyle("-fx-background-color: transparent");
            ImageView imageView = new ImageView();
            imageView.setFitHeight(22);
            imageView.setFitWidth(22);
            Image image = new Image(getClass().getResourceAsStream("image/deleteButton.png"));
            imageView.setImage(image);
            deleteButton.setGraphic(imageView);

            deleteButton.setOnAction((ActionEvent e)->{
                int selectedIndex = getTableRow().getIndex();
                TrolleyVO trolleyVO = (TrolleyVO) tableView.getItems().get(selectedIndex);
                stockNameData.remove(trolleyVO);
                tableView.refresh();
                trolley.deleteStock(trolleyVO);
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty)
        {
            super.updateItem(t, empty);
            if(empty)
            {
                setGraphic(null);
                setText(null);
            }else
            {
                setGraphic(deleteButton);
                setText(null);
            }
        }
    }

    @FXML
    private void close(){
//        stage.close();
    }

    /**
     * 清空所有自选股
     */
    @FXML
    private void deleteAllStock(){
        trolley.deleteAllStock();
        stockNameData.clear();
        tableView.refresh();
    }
}
