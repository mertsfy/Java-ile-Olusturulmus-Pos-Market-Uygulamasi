package igu.services;

import javax.swing.table.DefaultTableModel;

public class UtilService {
	
	public static void clearModel(DefaultTableModel model) {
		if (model.getRowCount() > 0) {
		    for (int i = model.getRowCount() - 1; i > -1; i--) {
		    	model.removeRow(i);
		    }
		}
	}
}
