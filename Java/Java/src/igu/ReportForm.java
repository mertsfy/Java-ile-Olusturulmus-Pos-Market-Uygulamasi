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
import igu.models.Sale;
import igu.services.CategoryService;
import igu.services.OrderService;
import igu.services.UtilService;

public class ReportForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtDailyAmount;
	DefaultTableModel modelCategory;
	private JTable tblSale;
	int categoryId = 0;
	private JTextField txtTotalAmount;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReportForm frame = new ReportForm();
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
	public ReportForm() {
		setResizable(false);
		setTitle("Satış Raporu");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 500, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.WEST);
		
		JLabel lblDailyAmount = new JLabel("Günlük Satış");
		lblDailyAmount.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		txtDailyAmount = new JTextField();
		txtDailyAmount.setEditable(false);
		txtDailyAmount.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		txtDailyAmount.setColumns(10);
		
		JLabel lblTotalAmount = new JLabel("Toplam Satış");
		lblTotalAmount.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		txtTotalAmount = new JTextField();
		txtTotalAmount.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		txtTotalAmount.setEditable(false);
		txtTotalAmount.setColumns(10);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(txtDailyAmount, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDailyAmount, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(50, Short.MAX_VALUE))
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTotalAmount, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtTotalAmount, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(32)
					.addComponent(lblDailyAmount)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtDailyAmount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(36)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblTotalAmount, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
					.addGap(11)
					.addComponent(txtTotalAmount, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(110, Short.MAX_VALUE))
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
				new String[] {"Satış Tarihi", "Adet", "Tutar"}
				)
				{
					boolean[] columnEditables = new boolean[] {false, false, false};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				};

		tblSale = new JTable(modelCategory);
		tblSale.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		tblSale.getTableHeader().setBackground(new Color(32, 136, 203));
		tblSale.getTableHeader().setForeground(new Color(255,255,255));
		tblSale.setRowHeight(30);
        
		JFrame thisframe = this;
		
		tblSale.getColumnModel().getColumn(0).setPreferredWidth(250);
		tblSale.getColumnModel().getColumn(1).setPreferredWidth(100);
		tblSale.getColumnModel().getColumn(2).setPreferredWidth(100);

		scrollPane.setViewportView(tblSale);
		
		fillSaleTable();
	}
	
	private void fillSaleTable() {
		UtilService.clearModel(modelCategory);
		
		OrderService service = new OrderService();
		
        try {
        	txtDailyAmount.setText(Float.toString(service.GetDailyTotalSales()));
			txtTotalAmount.setText(Float.toString(service.GetTotalSales()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
        
		ArrayList<Sale> sales;
		try {
			sales = service.GetSales();
			
			for (Sale item : sales) {
				modelCategory.addRow(new Object[]{item.getSaleDate(), item.getCount(), item.getAmount()});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
