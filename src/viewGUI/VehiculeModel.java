package viewGUI;

import javax.swing.table.AbstractTableModel;

import models.Vehicule;

import java.util.List;

class VeDataTableModel extends AbstractTableModel {
    private List<Vehicule> VedataList;
    private String[] columnNames = { "idVehicule", "kilometrage", "Status", "Type", "Marque", "Modele",
            "Nombre de place" };

    public VeDataTableModel(List<Vehicule> dataList) {
        VedataList = dataList;
    }

    @Override
    public int getRowCount() {
        return VedataList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Vehicule data = VedataList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return data.getIdVehicule();
            case 1:
                return data.getKilometage();
            case 2:
                return data.getStatus();
            case 3:
                return data.getType();
            case 4:
                return data.getMarque();
            case 5:
                return data.getModele();
            case 6:
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
