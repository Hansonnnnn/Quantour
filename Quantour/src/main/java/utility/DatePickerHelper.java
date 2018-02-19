package utility;

import javafx.event.ActionEvent;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.util.Callback;

import java.time.DayOfWeek;
import java.time.LocalDate;

/**
 * Created by LENOVO on 2017/4/17.
 */
public class DatePickerHelper {

    /**
     * 设置开始日期选择和结束日期选择限制
     */
    public static void datePickerCustomize(DatePicker startDatePicker,DatePicker endDatePicker) {
        startDatePicker.setValue(LocalDate.of(2014, 2, 14));
        endDatePicker.setValue(LocalDate.of(2014, 3, 28));
        disableInWeek(startDatePicker);
        disableInWeek(endDatePicker);
        startDatePicker.setOnAction((ActionEvent e) -> {
            endDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(DatePicker param) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item.isBefore(
                                    startDatePicker.getValue().plusDays(1))
                                    ) {
                                setDisable(true);
                                setStyle("-fx-text-fill: #161618;");
                            }
                        }
                    };
                }
            });
        });

        endDatePicker.setOnAction((ActionEvent e) -> {
            startDatePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
                @Override
                public DateCell call(DatePicker param) {
                    return new DateCell() {
                        @Override
                        public void updateItem(LocalDate item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item.isAfter(
                                    endDatePicker.getValue().minusDays(1))
                                    ) {
                                setDisable(true);
                                setStyle("-fx-text-fill: #161618;");
                            }
                        }
                    };
                }
            });
        });
    }

    /**
     * 设置DatePicker中周六和周日不能点击
     * @param datePicker
     */
    public static void disableInWeek(DatePicker datePicker){
        datePicker.setDayCellFactory(new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(DatePicker param) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.getDayOfWeek().equals(DayOfWeek.SATURDAY) ||
                                item.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
                            setDisable(true);
                            setStyle("-fx-text-fill: #777777;");
                        }
                    }
                };
            }
        });
    }

}
