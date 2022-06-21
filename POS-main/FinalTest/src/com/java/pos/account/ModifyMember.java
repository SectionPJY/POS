package com.java.pos.account;

import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.java.pos.db.AccountDAO;
import com.java.pos.db.AccountDTO;

public class ModifyMember extends JFrame implements ActionListener {
	JLabel idL, pwL, pwCheckL, nameL, positionL;
	JTextField idT, nameT;
	JPasswordField pwF, pwCheck;
	JComboBox<String> cbP;
	JButton btnOk, btnCancel;

	AccountDTO dto;
	String[] positionItem = { "관리자", "직원" };
	String[] memberList;

	public ModifyMember(ArrayList<AccountDTO> member, int index) {
		// id
		dto = member.get(index);

		initGrid();

		// 아이디 입력부분
		initIdPanel();

		// 비밀번호 입력부분
		initPwPanel();

		// 비밀번호 확인 입력부분
		initPwCheckPanel();

		// 이름 입력부분
		initNamePanel();

		// 버튼
		initButton();
	}

	private void initGrid() {
		Container ct = getContentPane();
		ct.setLayout(new GridLayout(6, 1, 3, 3));

		setTitle("회원수정");
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
		idT.setText(dto.getId());
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
		nameT.setText(dto.getName());
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
		btnOk = new JButton("수정");
		btnOk.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "수정하시겠습니까?", "회원수정", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					String pw = new String(pwF.getPassword());
					String pwC = new String(pwCheck.getPassword());
					if (pw.equals(pwC)) {
						AccountDAO dao = new AccountDAO();
						int resultUpdate = dao.memberUpdate(dto.getId(), idT.getText(), pw, nameT.getText());
						if (1 == resultUpdate) {
							JOptionPane.showMessageDialog(null, "수정되었습니다", "회원수정", JOptionPane.DEFAULT_OPTION);
							dispose();
							new MemberList();
						} else {
							System.out.println("수정실패");
						}
					} else {
						JOptionPane.showMessageDialog(null, "비밀번호가 일치하지 않습니다.", "회원수정", JOptionPane.ERROR_MESSAGE);
					}
				}
			}
		});

		btnCancel = new JButton("취소");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MemberList();
			}
		});

		jpB1.add(btnOk);
		jpB2.add(btnCancel);
		add(jpB1);
		add(jpB2);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String eType = e.getActionCommand();

	}
}