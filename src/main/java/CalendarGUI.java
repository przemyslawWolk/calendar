import components.TableCalendarRenderer;
import models.CalendarNote;
import service.MapCalendarDaoImpl;
import service.CalendarService;
import service.SQLiteCalendarDaoImpl;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;

public class CalendarGUI {
    JFrame window;
    JPanel panelNorth, panelCenter, panelSouth;
    JButton buttonNext, buttonPrev, buttonSave, buttonDelete, buttonUpdate;
    JTextArea areaToSave;
    JLabel monthName, noteFromDay;
    static DefaultTableModel dtm;
    JScrollPane stbl;
    JTable tbl;
    static String selectedCellKey;

    CalendarService cs;

    public void createGUI() {
        window = new JFrame("Calendar");
        panelNorth = new JPanel();
        panelSouth = new JPanel();
        panelCenter = new JPanel();
        buttonNext = new JButton("Nastepny >>");
        buttonPrev = new JButton("<< Poprzedni");
        buttonSave = new JButton("Zapisz");
        buttonDelete = new JButton("Usuń notatkę");
        buttonUpdate = new JButton("Zaktualizuj");
        areaToSave = new JTextArea(1, 30);
        monthName = new JLabel();
        noteFromDay = new JLabel("<Notatka dnia>");
        cs = new CalendarService(new SQLiteCalendarDaoImpl());
        dtm = new DefaultTableModel() {
            public boolean isCellEditable(int rowIndex, int mColIndex) {
                return false;
            }
        };
        tbl = new JTable(dtm);
        stbl = new JScrollPane(tbl);

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelNorth.setBackground(Color.GRAY);
        panelSouth.setBackground(Color.lightGray);

        dtm.setNumRows(6);
        String[] headers = {"Pon", "Wt", "Sr", "Czw", "Pt", "Sob", "Ndz"};
        for (int i = 0; i < 7; i++) {
            dtm.addColumn(headers[i]);
        }

        window.getContentPane().add(BorderLayout.NORTH, panelNorth);
        window.getContentPane().add(BorderLayout.SOUTH, panelSouth);
        window.getContentPane().add(BorderLayout.CENTER, panelCenter);
        panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.PAGE_AXIS));
        panelCenter.add(stbl);
        panelCenter.add(noteFromDay);
        panelNorth.add(buttonPrev);
        panelNorth.add(monthName);
        panelNorth.add(buttonNext);
        panelSouth.add(areaToSave);
        panelSouth.add(buttonSave);
        panelSouth.add(buttonDelete);
        panelSouth.add(buttonUpdate);

        tbl.getParent().setBackground(tbl.getBackground());
        tbl.getTableHeader().setResizingAllowed(false);
        tbl.getTableHeader().setReorderingAllowed(false);
        tbl.setColumnSelectionAllowed(true);
        tbl.setRowSelectionAllowed(true);
        tbl.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tbl.setRowHeight(30);

        monthName.setText(cs.nameOfMonth());
        buttonPrev.addActionListener(createPrevButtonListener());
        buttonNext.addActionListener(createNextButtonListener());
        buttonSave.addActionListener(createSaveButtonListener());
        buttonDelete.addActionListener(createDeleteButtonListener());
        buttonUpdate.addActionListener(createUpdateButtonListener());

        tbl.addMouseListener(createMouseListener());

        window.setSize(650, 400);
        window.setResizable(false);
        window.setVisible(true);
    }

    public void refreshCalendar() {
        int numberOfDays, startOfMonth;
        cs.getCal().set(cs.getNumberOfYear(), cs.getMonthNumber(), 1);
        numberOfDays = cs.getCal().getActualMaximum(Calendar.DAY_OF_MONTH);
        startOfMonth = cs.getCal().get(Calendar.DAY_OF_WEEK);

        //Clear table
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                dtm.setValueAt(null, i, j);
            }
        }
        //Draw calendar
        if (startOfMonth == 1) {
            startOfMonth = 7;
            for (int i = 1; i <= numberOfDays; i++) {
                int row = ((i + startOfMonth - 2) / 7);
                int column = (i + startOfMonth - 2) % 7;
                dtm.setValueAt(i, row, column);
            }
        } else {
            for (int i = 1; i <= numberOfDays; i++) {
                int row = ((i + startOfMonth - 3) / 7);
                int column = (i + startOfMonth - 3) % 7;
                dtm.setValueAt(i, row, column);
            }
        }
        tbl.setDefaultRenderer(tbl.getColumnClass(0), new TableCalendarRenderer(cs.getPresentCal(), cs.getNumberOfYear(), cs.getMonthNumber()));
    }

    private MouseListener createMouseListener() {
        return new MouseListener() {
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    tbl = (JTable) e.getSource();
                    Integer valueOfSelectedCell = (Integer) dtm.getValueAt(tbl.getSelectedRow(), tbl.getSelectedColumn());
                    String keyOfSelected = cs.nameOfMonth() + " " + dtm.getColumnName(tbl.getSelectedColumn()) + " " + valueOfSelectedCell;
                    CalendarNote cn = cs.read(keyOfSelected);
                    if (cn == null) {
                        cn.setNote("");
                    }
                    String messageAboutSelected = cn.toString();
                    if (valueOfSelectedCell == (null)) {
                        noteFromDay.setText("");
                    } else {
                        noteFromDay.setText(messageAboutSelected);
                    }
                }
            }

            public void mouseExited(MouseEvent e) {
            }

            public void mouseEntered(MouseEvent e) {
            }

            public void mouseReleased(MouseEvent e) {
            }

            public void mouseClicked(MouseEvent e) {
            }
        };
    }

    private ActionListener createUpdateButtonListener() {
        return e -> {
            Integer numberOfSelectedCellKey = (Integer) dtm.getValueAt(tbl.getSelectedRow(), tbl.getSelectedColumn());
            selectedCellKey = cs.nameOfMonth() + " " + dtm.getColumnName(tbl.getSelectedColumn()) + " " + numberOfSelectedCellKey;
            if (!areaToSave.getText().isEmpty()) {
                CalendarNote cn = new CalendarNote(selectedCellKey, areaToSave.getText());
                cs.update(cn);
                noteFromDay.setText("Zaktualizowano");
            } else {
                noteFromDay.setText("Najpierw zapisz notatkę!");
            }
            areaToSave.setText("");
        };
    }

    private ActionListener createDeleteButtonListener() {
        return e -> {
            CalendarNote cn = new CalendarNote();
            cn.setDate(selectedCellKey);
            cs.delete(cn);
            noteFromDay.setText("Usunięto notatkę");
        };
    }

    private ActionListener createSaveButtonListener() {
        return e -> {
            Integer numberOfSelectedCellKey = (Integer) dtm.getValueAt(tbl.getSelectedRow(), tbl.getSelectedColumn());
            selectedCellKey = cs.nameOfMonth() + " " + dtm.getColumnName(tbl.getSelectedColumn()) + " " + numberOfSelectedCellKey;
            if (!areaToSave.getText().isEmpty()) {
                CalendarNote cn = new CalendarNote(selectedCellKey, areaToSave.getText());
                cs.create(cn);
                noteFromDay.setText("Zapisano");
            } else {
                noteFromDay.setText("Najpierw zapisz notatkę!");
            }
            areaToSave.setText("");
        };
    }

    private ActionListener createNextButtonListener() {
        return e -> {
            cs.increaseMonthByOne();
            monthName.setText(cs.nameOfMonth());
            noteFromDay.setText("");
            refreshCalendar();
        };
    }

    private ActionListener createPrevButtonListener() {
        return e -> {
            cs.decreaseMonthByOne();
            monthName.setText(cs.nameOfMonth());
            noteFromDay.setText("");
            refreshCalendar();
        };
    }
}
