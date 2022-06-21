package com.java.pos.account;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.java.pos.db.AccountDAO;
import com.java.pos.db.AccountDTO;
import com.java.pos.order.Order;

public class Login extends JFrame implements ActionListener {

	JLabel idL, pwL;
	JTextField idT;
	JPasswordField pwF;
	JButton btnLogin;
	JButton btnRegister;
	public final String PATTERN = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]*$";

	public Login() {
		initGrid();

		// 아이디 입력부분
		initIdPanel();

		// 비밀번호 입력부분
		initPwPanel();

		// 버튼
		initButton();
		setTitle("로그인");
		setSize(500, 300);
		setVisible(true);
		setLocationRelativeTo(null); // 화면 중앙
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initGrid() {
		Container ct = getContentPane();
		ct.setLayout(new GridLayout(4, 1, 3, 3));

	}

	private void initIdPanel() {
		JPanel jpId1 = new JPanel();
		JPanel jpId2 = new JPanel();
		idL = new JLabel("아이디");
		idT = new JTextField(15);
		jpId1.add(idL);
		jpId2.add(idT);
		add(jpId1);
		add(jpId2);
	}

	private void initPwPanel() {
		JPanel jpPw1 = new JPanel();
		JPanel jpPw2 = new JPanel();
		pwL = new JLabel("비밀번호");
		pwF = new JPasswordField(15);
		jpPw1.add(pwL);
		jpPw2.add(pwF);
		add(jpPw1);
		add(jpPw2);
	}

	private void initButton() {
		JPanel jpB1 = new JPanel();
		JPanel jpB2 = new JPanel();
		btnLogin = new JButton("로그인");
		btnLogin.addActionListener(this);
		btnRegister = new JButton("회원가입");
		btnRegister.addActionListener(this);
		jpB1.add(btnLogin);
		jpB2.add(btnRegister);
		add(jpB1);
		add(jpB2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String eType = e.getActionCommand();

		if (eType.equals(btnRegister.getText())) {
			dispose();
			new Register();
		}
		if (eType.equals(btnLogin.getText())) {
			String uId = idT.getText();
			String uPw = new String(pwF.getPassword());
			if (idT.getText().equals("") || uPw.equals("")) {
				JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호가 입력되지 않았습니다.", "로그인", JOptionPane.ERROR_MESSAGE);
			} else {
				if (!Pattern.matches(PATTERN, idT.getText())) { // 특수문자 입력
					JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 잘못 입력하였습니다.", "로그인", JOptionPane.ERROR_MESSAGE);
				} else {
					AccountDAO dao = new AccountDAO();
					AccountDTO mDTO = dao.memberLogin(uId, uPw);
					if (mDTO == (null)) {
						JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 잘못 입력하였습니다.", "로그인",
								JOptionPane.ERROR_MESSAGE);
					} else {
						System.out.println(mDTO.getName());
						UserThreadLocal.set(mDTO.getId());
						JOptionPane.showMessageDialog(null, "로그인 성공", "로그인", JOptionPane.DEFAULT_OPTION);
						dispose();
						new Order(mDTO);
					}
				}
			}
		}
	}
}
