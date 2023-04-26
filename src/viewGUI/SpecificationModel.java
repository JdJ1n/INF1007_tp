package viewGUI;

import javax.swing.table.AbstractTableModel;

import models.Specification;
import java.util.List;

class SpeDataTableModel extends AbstractTableModel {
    private List<Specification> datalist;
    private String[] columnNames = { "Type", "Marque", "Modele", "Nombre de place" };

    public SpeDataTableModel(List<Specification> dataList) {
        this.datalist = dataList;
    }

    @Override
    public int getRowCount() {
        return datalist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Specification data = datalist.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return data.getMarque();
            case 1:
                return data.getType();
            case 2:
                return data.getModele();
            case 3:
                return data.getNbplace();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
