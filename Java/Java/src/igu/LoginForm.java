package igu;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import igu.models.User;
import igu.services.UserService;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class LoginForm extends JFrame {

	private JPanel contentPane;
	private JTextField txtEmail;
	private JPasswordField txtPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginForm frame = new LoginForm();
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
	public LoginForm() {
		setTitle("Giriş");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 321, 323);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel pnlTitle = new JPanel();
		pnlTitle.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlTitle.setBackground(SystemColor.controlHighlight);
		pnlTitle.setBounds(62, 25, 180, 37);
		contentPane.add(pnlTitle);
		
		JLabel lblTitle = new JLabel("Şen Market");
		lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		pnlTitle.add(lblTitle);
		
		JPanel pnlBody = new JPanel();
		pnlBody.setBorder(new LineBorder(new Color(0, 0, 0)));
		pnlBody.setBounds(62, 65, 180, 208);
		contentPane.add(pnlBody);
		pnlBody.setLayout(null);
		
		JLabel lblEmail = new JLabel("Email");
		lblEmail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblEmail.setBounds(10, 28, 87, 14);
		pnlBody.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		txtEmail.setBounds(10, 53, 127, 20);
		pnlBody.add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblPassword = new JLabel("Şifre");
		lblPassword.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		lblPassword.setBounds(10, 90, 87, 14);
		pnlBody.add(lblPassword);
		
		txtPassword = new JPasswordField();
		txtPassword.setBounds(10, 115, 127, 20);
		pnlBody.add(txtPassword);
		
		JFrame thisframe = this;
		
		JButton btnLogin = new JButton("Giriş");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UserService service = new UserService();
				try {
					if(txtEmail.getText().equals("") || txtPassword.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "Kullanıcı bilgilerini girin");
					} else {
						User user = service.Login(txtEmail.getText(), txtPassword.getText());
						
						if (user != null) {
							if(user.getRole().equals("ADMIN")) {
								AdminForm frm = new AdminForm();
								frm.setLocationRelativeTo(null);
								frm.setVisible(true);
							} else {
								CashierForm frm = new CashierForm();
								frm.setLocationRelativeTo(null);
								frm.setVisible(true);
							}
							thisframe.dispose();
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnLogin.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		btnLogin.setBounds(48, 162, 89, 23);
		pnlBody.add(btnLogin);
	}
}
