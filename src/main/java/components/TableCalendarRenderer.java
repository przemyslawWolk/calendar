package components;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.*;

public class TableCalendarRenderer extends DefaultTableCellRenderer {
    private final Calendar presentCal;
    private final int numberOfYear;
    private final int monthNumber;

    public TableCalendarRenderer(Calendar presentCal, int numberOfYear, int monthNumber) {

        this.presentCal = presentCal;
        this.numberOfYear = numberOfYear;
        this.monthNumber = monthNumber;
    }
    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        super.getTableCellRendererComponent(table, value, selected, focused, row, column);
        if (column == 5 || column == 6) { //Week-end
            setBackground(new Color(255, 220, 220));
        } else { //Week
            setBackground(new Color(255, 255, 255));
        }
        if (value != null) {
            if (Integer.parseInt(value.toString()) == presentCal.get(Calendar.DAY_OF_MONTH) && monthNumber == presentCal.get(Calendar.MONTH) && numberOfYear == presentCal.get(Calendar.YEAR)) { //Today
                setBackground(new Color(220, 220, 255));
            }
        }
        if (selected) {
            setBackground(Color.green);
        }

        setBorder(null);
        setForeground(Color.black);
        return this;
    }
}
