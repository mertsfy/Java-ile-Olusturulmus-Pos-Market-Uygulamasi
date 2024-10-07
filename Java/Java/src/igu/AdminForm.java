package igu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.imageio.ImageIO;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class AdminForm extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AdminForm frame = new AdminForm();
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
	public AdminForm() {
		setTitle("Admin Ana Menu");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 616, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel pnlTitle = new JPanel();
		pnlTitle.setBackground(SystemColor.controlHighlight);
		contentPane.add(pnlTitle, BorderLayout.NORTH);
		
		JLabel lblTitle = new JLabel("Şen Market POS");
		lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 25));
		pnlTitle.add(lblTitle);
		
		JPanel pnlMenu = new JPanel();
		pnlMenu.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(pnlMenu, BorderLayout.WEST);
		
		JButton btnHome = new JButton("Ana Sayfa");
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ReportForm frm = new ReportForm();
				frm.setLocationRelativeTo(null);
				frm.setVisible(true);				
			}
		});
		btnHome.setBackground(new Color(255, 153, 102));
		btnHome.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		JButton btnProduct = new JButton("Ürün");
		btnProduct.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ProductForm frm = new ProductForm();
				frm.setLocationRelativeTo(null);
				frm.setVisible(true);				
			}
		});
		btnProduct.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnProduct.setBackground(new Color(153, 204, 255));
		
		JButton btnCategory = new JButton("Kategory");
		btnCategory.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CategoryForm frame = new CategoryForm();
				frame.setLocationRelativeTo(null);
				frame.setVisible(true);
			}
		});
		btnCategory.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnCategory.setBackground(new Color(102, 255, 204));
		
		JButton btnExit = new JButton("Çıkış");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnExit.setBackground(new Color(255, 102, 102));
		GroupLayout gl_pnlMenu = new GroupLayout(pnlMenu);
		gl_pnlMenu.setHorizontalGroup(
			gl_pnlMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlMenu.createSequentialGroup()
					.addGap(44)
					.addGroup(gl_pnlMenu.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlMenu.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(btnProduct, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnHome, Alignment.LEADING))
						.addComponent(btnCategory, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(54, Short.MAX_VALUE))
		);
		gl_pnlMenu.setVerticalGroup(
			gl_pnlMenu.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlMenu.createSequentialGroup()
					.addGap(26)
					.addComponent(btnHome, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnProduct, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnCategory, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnExit, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(42, Short.MAX_VALUE))
		);
		pnlMenu.setLayout(gl_pnlMenu);
		
		JPanel pnlContent = new JPanel();
		pnlContent.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(pnlContent, BorderLayout.CENTER);
		pnlContent.setLayout(null);
		
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("src/igu/pos.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    ImageIcon icon = new ImageIcon(img);

		JLabel lblImage = new JLabel(icon);
		lblImage.setBounds(1, 1, 400, 313);
		pnlContent.add(lblImage);
	}

}
