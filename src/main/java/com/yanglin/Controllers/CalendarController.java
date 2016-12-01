package com.yanglin.Controllers;
import com.yanglin.Models.*;
import com.yanglin.Service.CalendarManager;
import com.yanglin.Utils.Helper;
import com.yanglin.Views.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.LinkedList;
import java.util.SortedSet;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class CalendarController
{
    private final static Logger LOGGER = Logger.getLogger(CalendarController.class.getName());

    @Autowired
    private CalendarManager cm;

    @FXML
    private BorderPane calendarPane;

    @FXML
    private GridPane calendarGridPane;

    @FXML
    private Label monthLbl;

    @FXML
    private Button preBtn;
    @FXML
    private Button currentBtn;
    @FXML
    private Button nextBtn;

    private EventPopOver eventPopOver;

    private LinkedList<DayCellVBox> dayCells;
    private CalendarViewModel calendarViewModel;
    private LinkedList<DayCellVBox> weekdayCells;

    public CalendarController(CalendarManager cm)
    {
        this.cm = cm;

    }
    @FXML
    public void initialize()
    {
        /* test
        day = DayFactory.getInstance().createDay(1,1,1991);
        Calendar start = Calendar.getInstance();
        start.set(1991,Calendar.JANUARY,1,24,1);
        Calendar end = Calendar.getInstance();
        end.set(1991,Calendar.JANUARY,1,24,15);
        day.addEvent(new Event("x",start,end,1));
        lblMon1.textProperty().bind(Bindings.convert(day.getDayProp()));
        lVMon0.setItems(day.getEvents());
        */
        this.calendarViewModel = new CalendarViewModel();
        this.eventPopOver = new EventPopOver(this.calendarViewModel);
        updateCalendarViewModel(Helper.getCurrentYear(),Helper.getCurrentMonth());
        constructWeekdayCells();
        constructDayCells();
        initDayCells();
        addEventHandlersToDayCells();
        bindProperties();
    }

    private void constructWeekdayCells()
    {
        LOGGER.log(Level.INFO, "constructing weekday cells");
        this.weekdayCells = new LinkedList<>();
        int j=0;
        for (Weekday w : Weekday.values())
        {
            WeekdayLabel weekdayLabel = new WeekdayLabel();
            weekdayLabel.setText(w.getAfkorting());
            DayCellVBox weekdayBox = new DayCellVBox(weekdayLabel);
            this.weekdayCells.add(weekdayBox);
            this.calendarGridPane.add(weekdayBox, j, 0);
            j++;
        }
        LOGGER.log(Level.INFO, "weekday cells constructed.");
    }

    private void bindProperties()
    {
        this.monthLbl.textProperty().bind(this.calendarViewModel.getCurrentMonthYearStrProp());
    }

    private void addEventHandlersToDayCells()
    {

    }

    private void initDayCells()
    {
        resetDaycells();
        LOGGER.log(Level.INFO, "initing day cell");
        int start = this.calendarViewModel.getCurrentDays().first().getWeekday().getIndex();
        for (DayModel d : this.calendarViewModel.getCurrentDays())
        {
            DayCellVBox crrDayCell = this.dayCells.get(start);
            crrDayCell.getDayLbl().setText(String.valueOf(d.getDay()));
            crrDayCell.getEventsLv().setItems(d.getEvents());
            crrDayCell.setOnContextMenuRequested(event -> this.eventPopOver.show(crrDayCell));
            start++;
        }
        LOGGER.log(Level.INFO, "day cell initialized. count:{0}",this.dayCells.size());
    }

    private void updateCalendarViewModel(int year, Month month)
    {
        this.calendarViewModel.setDays(cm.getDaysByMonthYear(year, month));
    }

    private void updateCalendarViewModel(SortedSet<DayModel> days)
    {
        this.calendarViewModel.setDays(days);
    }

    private void resetDaycells()
    {
        LOGGER.log(Level.INFO, "starting to resetting day lbl and eventslistview");
        dayCells.stream().filter(DayCellVBox::isNotWeekday).forEach(v -> {
            v.getDayLbl().setText("");
            v.getEventsLv().setItems(null);
        });
        LOGGER.log(Level.INFO, "resetting day lbl and eventslistview completed");
    }

    private void constructDayCells()
    {
        LOGGER.log(Level.INFO, "starting to constructing day cells");

        this.dayCells = new LinkedList<>();
        for (int i=1; i<7; i++)
        {
            for (int j=0; j<7; j++)
            {
                DayLabel label = new DayLabel();
                EventsListView<Event> listView = new EventsListView<>();
                DayCellVBox vBox = new DayCellVBox(label, listView);
                vBox.setOnMouseClicked(event -> {
                    this.eventPopOver.show(vBox,event.getScreenX(),event.getScreenY());
                });
                this.dayCells.add(vBox);
                this.calendarGridPane.add(vBox, j, i);
            }
        }
        LOGGER.log(Level.INFO, "constructing day lbls finished");
    }

    @FXML
    public void handlePreBtn(ActionEvent actionEvent)
    {
        updateCalendarViewModel(cm.getDaysOfPreMonth(this.calendarViewModel.getCurrentYear(), this.calendarViewModel.getCurrentMonth()));
        initDayCells();
    }
    @FXML
    public void handleCurrentMonthBtn(ActionEvent actionEvent)
    {
        updateCalendarViewModel(Helper.getCurrentYear(), Helper.getCurrentMonth());
        initDayCells();
    }
    @FXML
    public void handleNextMonthBtn(ActionEvent actionEvent)
    {
        this.calendarViewModel.setDays(cm.getDaysOfNextMonth(this.calendarViewModel.getCurrentYear(),this.calendarViewModel.getCurrentMonth()));
        initDayCells();
    }
}
