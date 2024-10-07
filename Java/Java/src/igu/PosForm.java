package igu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.Color;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import igu.components.ButtonColumn;
import igu.models.Category;
import igu.models.OrderItem;
import igu.models.Product;
import igu.services.CategoryService;
import igu.services.OrderService;
import igu.services.ProductService;
import igu.services.UtilService;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;

public class PosForm extends JFrame {

	private JPanel contentPane;
	private JTable tblProduct;
	private JTable tblCart;
	DefaultTableModel modelProduct;
	DefaultTableModel modelCart;
	ArrayList<Category> categories;

	final int COLUMN_PRODUCT_NAME = 0;
	final int COLUMN_PRODUCT_CATEGORY = 1;
	final int COLUMN_PRODUCT_QUANTITY = 2;
	final int COLUMN_PRODUCT_PRICE = 3;
	final int COLUMN_PRODUCT_ADD = 4;
	final int COLUMN_PRODUCT_ID = 5;

	final int COLUMN_CART_NAME = 0;
	final int COLUMN_CART_DEC = 1;
	final int COLUMN_CART_QUANTITY = 2;
	final int COLUMN_CART_INC = 3;
	final int COLUMN_CART_AMOUNT = 4;
	final int COLUMN_CART_ID = 5;
	private JTextField txtTotalAmount;
	private JTextField txtPaid;
	private JTextField txtChange;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PosForm frame = new PosForm();
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
	public PosForm() {
		setTitle("Pos Satış");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 755, 509);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel1 = new JPanel();
		panel1.setBounds(0, 0, 400, 470);
		contentPane.add(panel1);
		panel1.setLayout(null);

		JScrollPane scrollPane1 = new JScrollPane();
		scrollPane1.setViewportBorder(new LineBorder(new Color(0, 0, 0)));
		scrollPane1.setBounds(10, 11, 361, 448);
		panel1.add(scrollPane1);

		modelProduct = new DefaultTableModel(new Object[][] {},
				new String[] { "Ürün Adı", "Kategori", "Adet", "Fiyat", "", "ProductId" }) {
			boolean[] columnEditables = new boolean[] { false, false, false, false, true, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		tblProduct = new JTable(modelProduct);
		tblProduct.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		tblProduct.setBounds(10, 10, 290, 425);
		tblProduct.setRowHeight(30);

		tblProduct.getTableHeader().setBackground(new Color(32, 136, 203));
		tblProduct.getTableHeader().setForeground(new Color(255, 255, 255));
		tblProduct.setRowHeight(30);

		tblProduct.getColumnModel().getColumn(COLUMN_PRODUCT_NAME).setPreferredWidth(100);
		tblProduct.getColumnModel().getColumn(COLUMN_PRODUCT_CATEGORY).setPreferredWidth(100);
		tblProduct.getColumnModel().getColumn(COLUMN_PRODUCT_QUANTITY).setPreferredWidth(70);
		tblProduct.getColumnModel().getColumn(COLUMN_PRODUCT_PRICE).setPreferredWidth(70);

		tblProduct.getColumnModel().getColumn(COLUMN_PRODUCT_ID).setMinWidth(0);
		tblProduct.getColumnModel().getColumn(COLUMN_PRODUCT_ID).setMaxWidth(0);
		tblProduct.getColumnModel().getColumn(COLUMN_PRODUCT_ID).setWidth(0);

		scrollPane1.setViewportView(tblProduct);

		Action addToCartAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				int productId = (int) table.getModel().getValueAt(modelRow, COLUMN_PRODUCT_ID);
				int rowNo = -1;
				int quantity = 1;

				for (int i = 0; i < modelCart.getRowCount(); i++) {
					if ((int) modelCart.getValueAt(i, COLUMN_PRODUCT_ID) == productId) {
						rowNo = i;
						break;
					}
				}

				if (rowNo == -1) {
					if (checkStock(productId, quantity)) {
						modelCart.addRow(new Object[] { table.getModel().getValueAt(modelRow, COLUMN_PRODUCT_NAME), "-",
								quantity, "+", table.getModel().getValueAt(modelRow, COLUMN_PRODUCT_PRICE),
								productId });
					}
				} else {
					quantity = (int) modelCart.getValueAt(rowNo, COLUMN_CART_QUANTITY) + 1;
					if (checkStock(productId, quantity)) {
						modelCart.setValueAt(quantity, rowNo, COLUMN_CART_QUANTITY);
						modelCart.setValueAt(quantity * getProductPrice(productId), rowNo, COLUMN_CART_AMOUNT);
					}
				}
				calculateAmount();
			}
		};

		ButtonColumn buttonColumn1 = new ButtonColumn(tblProduct, addToCartAction, COLUMN_PRODUCT_ADD);

		JPanel panel2 = new JPanel();
		panel2.setLayout(null);
		panel2.setBounds(410, 0, 319, 459);
		contentPane.add(panel2);

		JScrollPane scrollPane2 = new JScrollPane();
		scrollPane2.setBounds(10, 11, 295, 307);
		panel2.add(scrollPane2);

		modelCart = new DefaultTableModel(new Object[][] {},
				new String[] { "Ürün Adı", "", "Adet", "", "Tutar", "ProductId" }) {
			boolean[] columnEditables = new boolean[] { false, true, false, true, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		tblCart = new JTable(modelCart);
		tblCart.setFont(new Font("Segoe UI", Font.PLAIN, 12));

		tblCart.getTableHeader().setBackground(new Color(32, 136, 203));
		tblCart.getTableHeader().setForeground(new Color(255, 255, 255));
		tblCart.setRowHeight(30);

		tblCart.getColumnModel().getColumn(COLUMN_CART_NAME).setPreferredWidth(100);
		tblCart.getColumnModel().getColumn(COLUMN_CART_DEC).setPreferredWidth(50);
		tblCart.getColumnModel().getColumn(COLUMN_CART_QUANTITY).setPreferredWidth(50);
		tblCart.getColumnModel().getColumn(COLUMN_CART_INC).setPreferredWidth(50);
		tblCart.getColumnModel().getColumn(COLUMN_CART_AMOUNT).setPreferredWidth(70);

		tblCart.getColumnModel().getColumn(COLUMN_CART_ID).setMinWidth(0);
		tblCart.getColumnModel().getColumn(COLUMN_CART_ID).setMaxWidth(0);
		tblCart.getColumnModel().getColumn(COLUMN_CART_ID).setWidth(0);

		scrollPane2.setViewportView(tblCart);

		Action incQuantityAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {

				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				int productId = (int) table.getModel().getValueAt(modelRow, COLUMN_CART_ID);
				int quantity = (int) table.getModel().getValueAt(modelRow, COLUMN_CART_QUANTITY) + 1;

				if (checkStock(productId, quantity)) {
					table.getModel().setValueAt(quantity, modelRow, COLUMN_CART_QUANTITY);
					table.getModel().setValueAt(quantity * getProductPrice(productId), modelRow, COLUMN_CART_AMOUNT);
					calculateAmount();
				}
			}
		};

		Action decQuantityAction = new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				int productId = (int) table.getModel().getValueAt(modelRow, COLUMN_CART_ID);
				int quantity = (int) table.getModel().getValueAt(modelRow, COLUMN_CART_QUANTITY) - 1;

				if (quantity == 0) {
					modelCart.removeRow(modelRow);
				} else {
					table.getModel().setValueAt(quantity, modelRow, COLUMN_CART_QUANTITY);
					table.getModel().setValueAt(quantity * getProductPrice(productId), modelRow, COLUMN_CART_AMOUNT);
				}
				calculateAmount();
			}
		};

		ButtonColumn buttonColumn2 = new ButtonColumn(tblCart, decQuantityAction, COLUMN_CART_DEC);
		ButtonColumn buttonColumn3 = new ButtonColumn(tblCart, incQuantityAction, COLUMN_CART_INC);

		JLabel lblTotalAmount = new JLabel("Toplam Tutar");
		lblTotalAmount.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblTotalAmount.setBounds(10, 332, 87, 16);
		panel2.add(lblTotalAmount);

		txtTotalAmount = new JTextField();
		txtTotalAmount.setText("0");
		txtTotalAmount.setEditable(false);
		txtTotalAmount.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		txtTotalAmount.setColumns(10);
		txtTotalAmount.setBounds(240, 329, 65, 22);
		panel2.add(txtTotalAmount);

		JLabel lblPaid = new JLabel("Ödenen");
		lblPaid.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblPaid.setBounds(10, 365, 87, 16);
		panel2.add(lblPaid);

		txtPaid = new JTextField();
		txtPaid.setText("0");
		txtPaid.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		txtPaid.setColumns(10);
		txtPaid.setBounds(240, 362, 65, 22);
		panel2.add(txtPaid);

		txtPaid.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				onChange();
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				onChange();
			}

			@Override
			public void changedUpdate(DocumentEvent e) {
				onChange();
			}
		});

		JLabel lblChange = new JLabel("Para Üstü");
		lblChange.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblChange.setBounds(10, 397, 87, 16);
		panel2.add(lblChange);

		txtChange = new JTextField();
		txtChange.setText("0");
		txtChange.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		txtChange.setEditable(false);
		txtChange.setColumns(10);
		txtChange.setBounds(240, 396, 65, 22);
		panel2.add(txtChange);
		
		JButton btnOrder = new JButton("Ödeme");
		btnOrder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				float totalAmount = Float.parseFloat(txtTotalAmount.getText().equals("") ? "0" : txtTotalAmount.getText());
				float paid = Float.parseFloat(txtPaid.getText().equals("") ? "0" : txtPaid.getText());
				
	            if (modelCart.getRowCount() == 0) {
	            	JOptionPane.showMessageDialog(null, "Ürün Seçin");
	            } else if (totalAmount > paid) {
					JOptionPane.showMessageDialog(null, "Ödeme yapılmadı");
				} else {
					ArrayList<OrderItem> items = new ArrayList<OrderItem>();
					
					for (int i = 0; i < modelCart.getRowCount(); i++) {
						items.add( new OrderItem(0, 0, (int) modelCart.getValueAt(i, COLUMN_CART_ID), (int) modelCart.getValueAt(i, COLUMN_CART_QUANTITY), getProductPrice((int) modelCart.getValueAt(i, COLUMN_CART_ID))));
					}					
					
					OrderService service = new OrderService();
					
					try {
						if (service.AddOrder(items)) {
							JOptionPane.showMessageDialog(null, "Satış tamamlandı");
							fillProductTable();
							UtilService.clearModel(modelCart);
						    txtChange.setText("0");
						    txtTotalAmount.setText("0");
						    txtPaid.setText("0");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}					
				}
			}
		});
		btnOrder.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnOrder.setBounds(117, 423, 87, 25);
		panel2.add(btnOrder);

		fillProductTable();
	}

	public void fillProductTable() {
		UtilService.clearModel(modelProduct);

		CategoryService service1 = new CategoryService();
		ProductService service2 = new ProductService();

		ArrayList<Product> products;
		try {
			categories = service1.GetCategories();
			products = service2.GetProducts();

			for (Product item : products) {
				modelProduct.addRow(new Object[] { item.getName(), getCategoryName(item.getCategoryId()),
						item.getQuantity(), item.getPrice(), "Ekle", item.getId() });
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String getCategoryName(int id) {

		String categoryName = null;

		for (Category item : categories) {
			if (item.getId() == id) {
				categoryName = item.getName();
				break;
			}
		}

		return categoryName;
	}

	private boolean checkStock(int id, int quantity) {

		boolean status = true;

		for (int i = 0; i < modelProduct.getRowCount(); i++) {
			if ((int) modelProduct.getValueAt(i, COLUMN_PRODUCT_ID) == id
					&& (int) modelProduct.getValueAt(i, COLUMN_PRODUCT_QUANTITY) < quantity) {
				status = false;
				break;
			}
		}

		if (!status) {
			JOptionPane.showMessageDialog(null, "Stok yeterli değil");
		}

		return status;
	}

	private void calculateAmount() {

		float amount = 0;

		for (int i = 0; i < modelCart.getRowCount(); i++) {
			amount += (float) modelCart.getValueAt(i, COLUMN_CART_AMOUNT);
		}

		txtTotalAmount.setText(Float.toString(amount));
	}

	private void onChange() {

		float totalAmount = Float.parseFloat(txtTotalAmount.getText().equals("") ? "0" : txtTotalAmount.getText());
		float paid = Float.parseFloat(txtPaid.getText().equals("") ? "0" : txtPaid.getText());
		txtChange.setText(Float.toString(paid - totalAmount));   
	}
	
	private float getProductPrice(int id) {

		float price = 0;

		for (int i = 0; i < modelProduct.getRowCount(); i++) {
			if ((int) modelProduct.getValueAt(i, COLUMN_PRODUCT_ID) == id) {
				price = (float) modelProduct.getValueAt(i, COLUMN_PRODUCT_PRICE);
				break;
			}
		}

		return price;
	}

}
