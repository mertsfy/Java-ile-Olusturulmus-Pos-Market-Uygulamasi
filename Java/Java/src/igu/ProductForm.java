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
import igu.models.Product;
import igu.services.CategoryService;
import igu.services.ProductService;
import igu.services.UtilService;
import javax.swing.JComboBox;

public class ProductForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtName;
	DefaultTableModel modelProduct;
	private JTable tblProduct;
	private JButton btnSave;
	int productId = 0;
	private JTextField txtQuantity;
	private JTextField txtPrice;
	private JComboBox<Category> cmbCategory;
	ArrayList<Category> categories;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProductForm frame = new ProductForm();
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
	public ProductForm() {
		setResizable(false);
		setTitle("Ürün Ekle");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 901, 418);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		
		JLabel lblName = new JLabel("Ürün Adı");
		lblName.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		btnSave = new JButton("Kaydet");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	            if (txtName.getText().equals("") || txtQuantity.getText().equals("") || txtPrice.getText().equals("") || cmbCategory.getSelectedIndex() < 1) {
	            	JOptionPane.showMessageDialog(null, "Ürün bilgilerini girin");
	                txtName.requestFocus();
	            }
	            else {
	                ProductService service = new ProductService();

	                try {
		                if (service.GetProductByName(txtName.getText(), productId) == null) {
		                	Category category = (Category) cmbCategory.getSelectedItem();
		                    if (productId == 0) {
		                        if (service.AddProduct(new Product(0, txtName.getText(), Integer.parseInt(txtQuantity.getText()), Float.parseFloat(txtPrice.getText()), category.getId()))) {
		                        	fillProductTable();
		                        	clearForm();
		                        }
		                    }
		                    else {
		                        if (service.UpdateProduct(new Product(productId, txtName.getText(), Integer.parseInt(txtQuantity.getText()), Float.parseFloat(txtPrice.getText()), category.getId()))) {
		                        	fillProductTable();
		                        	clearForm();
		                        }
		                    }
		                }
		                else {
		                	JOptionPane.showMessageDialog(null, "Bu ürün kayıtlıdır");
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
		
		JLabel lblCategory = new JLabel("Kagetory");
		lblCategory.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		JLabel lblQuantity = new JLabel("Stok Adet");
		lblQuantity.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		txtQuantity = new JTextField();
		txtQuantity.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		txtQuantity.setColumns(10);
		
		JLabel lblPrice = new JLabel("Fiyat");
		lblPrice.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		txtPrice = new JTextField();
		txtPrice.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		txtPrice.setColumns(10);
		
		cmbCategory = new JComboBox<Category>();
		
		cmbCategory.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(cmbCategory, 0, 127, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addContainerGap(50, Short.MAX_VALUE)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(btnClear, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnSave, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPrice, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtPrice, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)))
						.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(txtName, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCategory, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblQuantity, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtQuantity, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblName)
					.addGap(11)
					.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblCategory, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(cmbCategory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblQuantity, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(txtQuantity, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblPrice, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(txtPrice, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
					.addComponent(btnSave)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnClear, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		
		JPanel pnlContent = new JPanel();
		contentPane.add(pnlContent, BorderLayout.CENTER);
		pnlContent.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 708, 347);
		pnlContent.add(scrollPane);
		
		modelProduct = new DefaultTableModel(
				new Object[][] {},
				new String[] {"Ürün Adı", "Kategori", "Adet", "Fiyat", "", "", "", "ProductId"}
				)
				{
					boolean[] columnEditables = new boolean[] {false, false, false, false, true, true, true, false};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				};

		tblProduct = new JTable(modelProduct);
		tblProduct.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		tblProduct.getTableHeader().setBackground(new Color(32, 136, 203));
		tblProduct.getTableHeader().setForeground(new Color(255,255,255));
		tblProduct.setRowHeight(30);
        
		JFrame thisframe = this;
		
		Action updateAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				
				JTable table = (JTable)e.getSource();
				int modelRow = Integer.valueOf( e.getActionCommand() );
				productId = (int) table.getModel().getValueAt(modelRow, 7);
				txtName.setText((String) table.getModel().getValueAt(modelRow, 0));
				txtQuantity.setText(table.getModel().getValueAt(modelRow, 2).toString());
				txtPrice.setText(table.getModel().getValueAt(modelRow, 3).toString());
				setCategoryName(table.getModel().getValueAt(modelRow, 1).toString());
				
				thisframe.setTitle("Ürün Güncelle");
                btnSave.setText("Güncelle");			
                txtQuantity.setEditable(false);
			}
		};
		
		Action deleteAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				
				JTable table = (JTable)e.getSource();
				int modelRow = Integer.valueOf( e.getActionCommand() );
				int deleteId = (int) table.getModel().getValueAt(modelRow, 7);
				
				int result = JOptionPane.showConfirmDialog(null, "Ürünü silmek istiyormusunuz?", "Onay", JOptionPane.YES_NO_OPTION);

				if (result == JOptionPane.YES_OPTION) {
                    ProductService service = new ProductService();
                    try {
						if (service.DeleteProduct(deleteId)) {
						    fillProductTable();
						    clearForm();
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}					
				}
			}
		};
		
		Action addStockAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				
				JTable table = (JTable)e.getSource();
				int modelRow = Integer.valueOf( e.getActionCommand() );
				productId = (int) table.getModel().getValueAt(modelRow, 7);

				StockForm frame = new StockForm();
				frame.setProductId(productId);
				frame.setOpener(thisframe);
				frame.setTitle((String) table.getModel().getValueAt(modelRow, 0));
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		};
		
		ButtonColumn buttonColumn1 = new ButtonColumn(tblProduct, updateAction, 4);
		ButtonColumn buttonColumn2 = new ButtonColumn(tblProduct, deleteAction, 5);
		ButtonColumn buttonColumn3 = new ButtonColumn(tblProduct, addStockAction, 6);
		
		tblProduct.getColumnModel().getColumn(0).setPreferredWidth(100);
		tblProduct.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblProduct.getColumnModel().getColumn(2).setPreferredWidth(70);
		tblProduct.getColumnModel().getColumn(3).setPreferredWidth(70);
		
		tblProduct.getColumnModel().getColumn(7).setMinWidth(0);
		tblProduct.getColumnModel().getColumn(7).setMaxWidth(0);
		tblProduct.getColumnModel().getColumn(7).setWidth(0);

		scrollPane.setViewportView(tblProduct);
		
		fillCategoryCombo();
		fillProductTable();
	}
	
	public void fillProductTable() {
		UtilService.clearModel(modelProduct);
		
		ProductService service = new ProductService();
		ArrayList<Product> products;
		try {
			products = service.GetProducts();
			
			for (Product item : products) {
				modelProduct.addRow(new Object[]{item.getName(), getCategoryName(item.getCategoryId()), item.getQuantity(), item.getPrice(), "Güncelle", "Sil", "Stok Ekle", item.getId()});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void fillCategoryCombo() {
		
		CategoryService service = new CategoryService();
		
		try {
			categories = service.GetCategories();
			cmbCategory.addItem(null);
			for (Category item : categories) {
				cmbCategory.addItem(new Category(item.getId(), item.getName()));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String getCategoryName(int id) {
		
		String categoryName = null;
		
		for (Category item : categories) {
			if(item.getId() == id) {
				categoryName = item.getName();
				break;
			}
		}
		
		return categoryName;
	}

	private void setCategoryName(String name) {
		
		for (int i=1; i<cmbCategory.getItemCount(); i++) {
			Category category = (Category)cmbCategory.getItemAt(i); 	
			if (category.getName().equals(name)) {
				cmbCategory.setSelectedIndex(i);
				break;
			}
		}		
	}
	
    public void clearForm() {
    	JFrame thisframe = this;
    	thisframe.setTitle("Ürün Ekle");
        btnSave.setText("Kaydet");
        txtName.setText("");
        txtQuantity.setText("");
        txtPrice.setText("");
        cmbCategory.setSelectedIndex(-1);
        txtQuantity.setEditable(true);
        productId = 0;
    }
}
