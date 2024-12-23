package Server;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import DataPack.ResultPack;

public class ServerWindows {
    private JFrame windows;
    private DefaultTableModel model;

    public ServerWindows() {
        windows = new JFrame("Data Grid Example");
        windows.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        windows.setSize(600, 400);
        windows.setLayout(new BorderLayout());

        String[] columnNames = { "Name", "ID", "Map", "Score" };
        Object[][] data = {};

        model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        table.setFillsViewportHeight(true);
        table.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(table);

        windows.add(scrollPane, BorderLayout.CENTER);
        windows.setVisible(true);
    }
    
    public void addResult(ResultPack pack) {
        Object[] data = {pack.getClientName(), pack.getClientId(), pack.getMapName(), pack.getScore()};
        model.addRow(data);
    }
}
