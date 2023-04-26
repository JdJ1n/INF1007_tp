package viewGUI;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import models.Commande;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class CommandeModel extends AbstractTableModel {
    private String[] columnNames = { "ID", "Client", "Véhicule", "Status", "Date début", "Date fin", "Kilometrage",
            "Qarantie" };
    private List<Commande> ComdataList;
    private boolean isTableEditable;

    public CommandeModel(List<Commande> dataList, boolean isTableEditable) {
        this.ComdataList = dataList;
        this.isTableEditable = isTableEditable;
    }

    @Override
    public int getRowCount() {
        return ComdataList.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Commande data = ComdataList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return data.getId();
            case 1:
                return data.getCli();
            case 2:
                return data.getVeh();
            case 3:
                return data.getstatus();
            case 4:
                return data.getDateDebut();
            case 5:
                return data.getDateFin();
            case 6:
                return data.getKilometrage();
            case 7:
                return data.getGarantie();
            default:
                return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    // 修改以下方法
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        // 只允许DateDebut和DateFin列可编辑
        if (!isTableEditable) {
            return false;
        }

        return columnIndex == 4 || columnIndex == 5;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        Commande data = ComdataList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                data.setId((Integer) aValue);
                break;
            case 1:
                data.setCli((String) aValue);
                break;
            case 2:
                data.setVeh((String) aValue);
                break;
            case 3:
                data.setstatus((String) aValue);
                break;
            case 4:
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

                    Date date = sdf.parse((String) aValue);
                    if (data.getDateFin() != null && date.after(data.getDateFin())) {
                        JOptionPane.showMessageDialog(null,
                                "L'heure de début ne peut être postérieure à l'heure de fin", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        data.setDateDebut(date);
                        data.updateStatus();
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Veuillez saisir le format de date correct：EEE MMM dd HH:mm:ss zzz yyyy", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case 5:
                try {
                    SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

                    Date date = sdf.parse((String) aValue);
                    if (data.getDateDebut() != null && date.before(data.getDateDebut())) {
                        JOptionPane.showMessageDialog(null,
                                "L'heure de début ne peut être postérieure à l'heure de fin", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    } else {
                        data.setDateFin(date);
                        data.updateStatus(); // 更新状态
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Veuillez saisir le format de date correct：EEE MMM dd HH:mm:ss zzz yyyy", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case 6:
                data.setKilometrage((Integer) aValue);
                break;
            case 7:
                data.setGarantie((String) aValue);
                break;
            default:
                return;
        }
        fireTableCellUpdated(rowIndex, columnIndex);
    }

    public void setTableEditable(boolean b) {
    }
}
