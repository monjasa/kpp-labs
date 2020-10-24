package org.monjasa.view;

import org.monjasa.model.Employee;
import org.monjasa.model.Facility;
import org.monjasa.service.FacilityService;
import org.monjasa.service.FacilityStrategy;

import javax.swing.*;
import javax.swing.JSpinner.DateEditor;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DefaultFrame extends JFrame {

    private JPanel rootPanel;

    private JButton selectDataSourcesButton;
    private JButton computeButton;
    private JButton filterByBirthDateButton;

    private JCheckBox excludeLastNameDuplicatesCheckBox;
    private JComboBox<FacilityStrategy> strategyBox;
    private JSpinner yearBeforeSpinner;

    private JList<Employee> sourceList;
    private JList<Entry<?, ?>> resultList;

    private Facility facility;

    public DefaultFrame(String title) throws HeadlessException {

        super(title);

        yearBeforeSpinner.setModel(new SpinnerDateModel());
        yearBeforeSpinner.setEditor(new DateEditor(yearBeforeSpinner, new SimpleDateFormat("dd.MM.yyyy").toPattern()));

        List.of(
                new FacilityStrategy("Last names by position", FacilityService::groupEmployeesLastNamesByPosition),
                new FacilityStrategy("Youngest and oldest employee by position", FacilityService::groupYoungestAndOldestEmployeeByPosition),
                new FacilityStrategy("Employees by salary brackets", FacilityService::groupEmployeesBySalaryBracket)
        ).forEach(strategyBox::addItem);

        strategyBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                FacilityStrategy strategy = (FacilityStrategy) value;
                return super.getListCellRendererComponent(list, strategy.getName(), index, isSelected, cellHasFocus);
            }
        });

        selectDataSourcesButton.addActionListener(getListenerForDataSelectionButton());
        computeButton.addActionListener(getListenerForComputeButton());
        filterByBirthDateButton.addActionListener(getListenerForFilterButton());

        setContentPane(rootPanel);
    }

    private ActionListener getListenerForDataSelectionButton() {
        return event -> {
            JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
            fileChooser.setDialogTitle("Select data sources");
            fileChooser.setMultiSelectionEnabled(true);
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("JavaScript Object Notation (*.json)", "json"));

            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {

                File[] selectedFiles = fileChooser.getSelectedFiles();

                facility = excludeLastNameDuplicatesCheckBox.isSelected()
                        ? FacilityService.unmarshallFacility(Employee::getLastName, selectedFiles)
                        : FacilityService.unmarshallFacility(selectedFiles);

                sourceList.setListData(facility.getEmployees().toArray(Employee[]::new));
            }
        };
    }

    private ActionListener getListenerForComputeButton() {
        return event -> {
            Map<?, ?> result = strategyBox.getItemAt(strategyBox.getSelectedIndex()).getStrategy().apply(facility);
            resultList.setListData(result.entrySet().toArray(Entry[]::new));
        };
    }

    private ActionListener getListenerForFilterButton() {
        return event -> {
            Date date = (Date) yearBeforeSpinner.getValue();
            LocalDate localDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());

            Map<Long, Employee> employeeMap = FacilityService.excludeEmployeesBornAfterDate(facility, localDate).stream()
                    .collect(Collectors.toMap(Employee::getId, Function.identity()));

            resultList.setListData(employeeMap.entrySet().toArray(Entry[]::new));
        };
    }
}
