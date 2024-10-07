package igu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import igu.components.ButtonColumn;
import igu.models.Category;
import igu.services.CategoryService;
import igu.services.UtilService;

public class CategoryForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	DefaultTableModel modelCategory;
	private JTable tblCategory;
	private JButton btnSave;
	int categoryId = 0;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CategoryForm frame = new CategoryForm();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public CategoryForm() {
		setResizable(false);
		setTitle("Kategori Ekle");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		
		JLabel lblName = new JLabel("Kategori Adı");
		lblName.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		btnSave = new JButton("Kaydet");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            if (txtName.getText().equals("")) {
	            	JOptionPane.showMessageDialog(null, "Kategori adı girin");
	                txtName.requestFocus();
	            }
	            else {
	                CategoryService service = new CategoryService();

	                try {
		                if (service.GetCategoryByName(txtName.getText(), categoryId) == null) {
		                    if (categoryId == 0) {
		                        if (service.AddCategory(txtName.getText())) {
		                        	fillCategoryTable();
		                        	clearForm();
		                        }
		                    }
		                    else {
		                        if (service.UpdateCategory(txtName.getText(), categoryId))
		                        {
		                        	fillCategoryTable();
		                        	clearForm();
		                        }
		                    }
		                }
		                else {
		                	JOptionPane.showMessageDialog(null, "Bu kategori kayıtlıdır");
		                }
					} catch (Exception e2) {
					}
	            }				
			}
		});
		btnSave.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		txtName = new JTextField();
		txtName.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		txtName.setColumns(10);
		
		JButton btnClear = new JButton("Temizle");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearForm();
			}
		});
		btnClear.setBackground(new Color(240, 128, 128));
		btnClear.setForeground(new Color(0, 0, 0));
		btnClear.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
							.addComponent(btnClear, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnSave, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)))
					.addContainerGap())
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(50, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(32)
					.addComponent(lblName)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addComponent(btnSave)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(82, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		JPanel pnlContent = new JPanel();
		contentPane.add(pnlContent, BorderLayout.CENTER);
		pnlContent.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 307, 229);
		pnlContent.add(scrollPane);
		
		modelCategory = new DefaultTableModel(
				new Object[][] {},
				new String[] {"Kategori Adı", "", "", "CategoryId"}
				)
				{
					boolean[] columnEditables = new boolean[] {false, true, true, false};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				};

		tblCategory = new JTable(modelCategory);
		tblCategory.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		tblCategory.getTableHeader().setBackground(new Color(32, 136, 203));
		tblCategory.getTableHeader().setForeground(new Color(255,255,255));
		tblCategory.setRowHeight(30);
        
		JFrame thisframe = this;
		
		Action updateAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				
				JTable table = (JTable)e.getSource();
				int modelRow = Integer.valueOf( e.getActionCommand() );
				categoryId = (int) table.getModel().getValueAt(modelRow, 3);
				txtName.setText((String) table.getModel().getValueAt(modelRow, 0));
				
				thisframe.setTitle("Kategori Güncelle");
                btnSave.setText("Güncelle");				
			}
		};
		
		Action deleteAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				
				JTable table = (JTable)e.getSource();
				int modelRow = Integer.valueOf( e.getActionCommand() );
				int deleteId = (int) table.getModel().getValueAt(modelRow, 3);
				
				int result = JOptionPane.showConfirmDialog(null, "Kategoriyi silmek istiyormusunuz?", "Onay", JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION) {
                    CategoryService service = new CategoryService();
                    try {
						if (service.DeleteCategory(deleteId)) {
						    fillCategoryTable();
						    clearForm();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}					
				}
			}
		};
		
		ButtonColumn buttonColumn1 = new ButtonColumn(tblCategory, updateAction, 1);
		ButtonColumn buttonColumn2 = new ButtonColumn(tblCategory, deleteAction, 2);
		
		tblCategory.getColumnModel().getColumn(0).setPreferredWidth(250);
		tblCategory.getColumnModel().getColumn(1).setPreferredWidth(150);
		tblCategory.getColumnModel().getColumn(2).setPreferredWidth(100);
		tblCategory.getColumnModel().getColumn(3).setMinWidth(0);
		tblCategory.getColumnModel().getColumn(3).setMaxWidth(0);
		tblCategory.getColumnModel().getColumn(3).setWidth(0);

		scrollPane.setViewportView(tblCategory);
		
		fillCategoryTable();
	}
	
	private void fillCategoryTable() {
		UtilService.clearModel(modelCategory);
		
		CategoryService service = new CategoryService();
		ArrayList<Category> categories;
		try {
			categories = service.GetCategories();
			
			for (Category item : categories) {
				modelCategory.addRow(new Object[]{item.getName(), "Güncelle", "Sil", item.getId()});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    private void clearForm() {
    	JFrame thisframe = this;
    	thisframe.setTitle("Kategori Ekle");
        btnSave.setText("Kaydet");
        txtName.setText("");
        categoryId = 0;
    }
    
}
