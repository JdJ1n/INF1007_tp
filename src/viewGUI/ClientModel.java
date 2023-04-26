package viewGUI;

import javax.swing.table.AbstractTableModel;

import models.Client;

import java.util.List;

class ClientModel extends AbstractTableModel {
    private String[] columnNames = { "ID", "Nom", "Prénom", "Adress", "Numtélé", "Numpermis", "Numcartes", "Info",
            "Status", "Dette" };
    private List<Client> ClidataList;
    private boolean isTableEditable;

    public ClientModel(List<Client> dataList, boolean isTableEditable) {
        this.ClidataList = dataList;
        this.isTableEditable = isTableEditable;
    }

    @Override
    public int getRowCount() {
        return ClidataList.size();

    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Client data = ClidataList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return data.getId();
            case 1:
                return data.getNom();
            case 2:
                return data.getPrenom();
            case 3:
                return data.getAdresse();
            case 4:
                return data.getNumtele();
            case 5:
                return data.getNumpermis();
            case 6:
                return data.getNumcartes();
            case 7:
                return data.getInfo();
            case 8:
                return data.getStatus();
            case 9:
                return data.getDette();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    // 设置单元格可编辑
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (!isTableEditable) {
            return false;
        }
        return true;
    }

    // 将修改后的值保存到数据对象中
    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Client data = ClidataList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                data.setId((Integer) aValue);
                break;
            case 1:
                data.setNom((String) aValue);
                break;
            case 2:
                data.setPrenom((String) aValue);
                break;
            case 3:
                data.setAdresse((String) aValue);
                break;
            case 4:
                data.setNumtele((String) aValue);
                break;
            case 5:
                data.setNumpermis((String) aValue);
                break;
            case 6:
                data.setNumcartes((String) aValue);
                break;
            case 7:
                data.setInfo((String) aValue);
                break;
            case 8:
                data.setStatus((String) aValue);
                break;
            case 9:
                data.setDette((Double) aValue);
                break;
            default:
                break;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }
}
