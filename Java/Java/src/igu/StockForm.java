package igu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import igu.services.ProductService;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class StockForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtQuantity;

	private int productId;
	private JFrame opener;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StockForm frame = new StockForm();
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
	public StockForm() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 243, 230);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 207, 177);
		contentPane.add(panel);
		
		JLabel lblQuantity = new JLabel("Adet");
		lblQuantity.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		JFrame thisframe = this;
		
		JButton btnSave = new JButton("Kaydet");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(txtQuantity.getText().equals(""))
				{
					JOptionPane.showMessageDialog(null, "Adet girin");
					txtQuantity.requestFocus();
				}
				else {
					ProductService service = new ProductService();
					try {
						if(service.AddStockProduct(getProductId(), Integer.parseInt(txtQuantity.getText()))) {
							((ProductForm) getOpener()).fillProductTable();
							((ProductForm) getOpener()).clearForm();
							thisframe.dispose();
						}
					} catch (NumberFormatException e1) {
						e1.printStackTrace();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		btnSave.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		txtQuantity = new JTextField();
		txtQuantity.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(52, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblQuantity, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
							.addComponent(btnSave)
							.addComponent(txtQuantity, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)))
					.addGap(42))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(30)
					.addComponent(lblQuantity)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtQuantity, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(27)
					.addComponent(btnSave)
					.addContainerGap(48, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public JFrame getOpener() {
		return opener;
	}

	public void setOpener(JFrame opener) {
		this.opener = opener;
	}
}
