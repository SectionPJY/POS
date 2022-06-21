package com.java.pos.account;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.java.pos.db.AccountDAO;

public class Register extends JFrame {
	JLabel idL, pwL, pwCheckL, nameL, positionL;
	JTextField idT, nameT;
	JPasswordField pwF, pwCheck;
	JComboBox<String> cbP;
	JButton btnOk, btnCancel;

	String[] positionItem = { "관리자", "직원" };
	public final String PATTERN = "^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣]*$";
	
	public Register() {
		initGrid();

		// 아이디 입력부분
		initIdPanel();

		// 비밀번호 입력부분
		initPwPanel();

		// 비밀번호 확인 입력부분
		initPwCheckPanel();

		// 이름 입력부분
		initNamePanel();

		// 직책 입력부분
		initPositionPanel();

		// 버튼
		initButton();
	}

	private void initGrid() {
		Container ct = getContentPane();
		ct.setLayout(new GridLayout(6, 1, 3, 3));

		setTitle("회원가입");
		setLocationRelativeTo(null); // 화면 중앙
		setSize(450, 550);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
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

	private void initPwCheckPanel() {
		JPanel jpPwCheck1 = new JPanel();
		JPanel jpPwCheck2 = new JPanel();
		pwCheckL = new JLabel("비밀번호 확인");
		pwCheck = new JPasswordField(15);
		jpPwCheck1.add(pwCheckL);
		jpPwCheck2.add(pwCheck);
		add(jpPwCheck1);
		add(jpPwCheck2);
	}

	private void initNamePanel() {
		JPanel jpName1 = new JPanel();
		JPanel jpName2 = new JPanel();
		nameL = new JLabel("이름");
		nameT = new JTextField(15);
		jpName1.add(nameL);
		jpName2.add(nameT);
		add(jpName1);
		add(jpName2);
	}

	private void initPositionPanel() {
		JPanel jpP1 = new JPanel();
		JPanel jpP2 = new JPanel();
		positionL = new JLabel("직책");
		cbP = new JComboBox<String>(positionItem);
		jpP1.add(positionL);
		jpP2.add(cbP);
		add(jpP1);
		add(jpP2);
	}

	private void initButton() {
		JPanel jpB1 = new JPanel();
		JPanel jpB2 = new JPanel();
		btnOk = new JButton("등록");
//		btnOk.addActionListener(this);
		btnCancel = new JButton("취소");
//		btnCancel.addActionListener(this);
		jpB1.add(btnOk);
		jpB2.add(btnCancel);
		add(jpB1);
		add(jpB2);
		
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				AccountDAO dao = new AccountDAO();
				int result = 0;
				String pwString = new String(pwF.getPassword());
				String pwcString = new String(pwCheck.getPassword());

				if (idT.getText().equals("") || pwString.equals("") || pwcString.equals("") || nameT.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "입력이 되지 않았습니다.", "회원등록", JOptionPane.ERROR_MESSAGE);
				} else {
					if(!Pattern.matches(PATTERN, idT.getText())||!Pattern.matches(PATTERN, pwString)||!Pattern.matches(PATTERN, pwcString)) {
						JOptionPane.showMessageDialog(null, "아이디 또는 비밀번호를 잘못 입력했습니다.", "회원등록", JOptionPane.ERROR_MESSAGE);
					} else {

						if (dao.checkId(idT.getText())) {
							if (pwString.equals(pwcString)) {
								try {
									result = dao.memberRegister(idT.getText(), pwString, nameT.getText(),
											cbP.getSelectedItem().toString());
								} catch (Exception e2) {
									e2.printStackTrace();
								} finally {
									if (1 == result) {
										JOptionPane.showMessageDialog(null, "등록이 완료되었습니다.", "회원등록", JOptionPane.PLAIN_MESSAGE);
										dispose();
										new Login();
									} else {
										JOptionPane.showMessageDialog(null, "등록에 실패했습니다.", "회원등록", JOptionPane.ERROR_MESSAGE);
									}
								}
							} else {
								JOptionPane.showMessageDialog(null, "비밀번호를 확인해주세요.", "회원등록", JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				}
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new Login();
			}
		});
	}
}
