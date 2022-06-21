package com.java.pos.account;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.java.pos.db.AccountDAO;
import com.java.pos.db.AccountDTO;

public class MemberList extends JFrame {
	AccountDAO mDAO = new AccountDAO();
	ArrayList<AccountDTO> member = new ArrayList<AccountDTO>();
	String[] colName = { "ID", "이름", "등록일", "최종접속일" };
	Object[] row = new Object[4];

	public MemberList() {
		DefaultTableModel tModel = new DefaultTableModel(colName, 0);
		JTable table = new JTable(tModel);
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(2, 1, 3, 3));
		add(southPanel, BorderLayout.SOUTH);

		member = mDAO.memberList();
		for (int i = 0; i < member.size(); i++) {
			AccountDTO dto = member.get(i);
			row[0] = dto.getId();
			row[1] = dto.getName();
			row[2] = dto.getRegistryDate();
			row[3] = dto.getAccessDate();

			tModel.addRow(row);
		}

		JPanel jp = new JPanel();
		JButton btnModify = new JButton("수정");
		JButton btnDelete = new JButton("삭제");
		jp.add(btnModify);
		jp.add(btnDelete);

		southPanel.add(jp);

		setTitle("회원관리");
		setSize(600, 500);
		setVisible(true);

		btnModify.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new ModifyMember(member, table.getSelectedRow());
			}
		});

		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int result = JOptionPane.showConfirmDialog(null, "정말로 삭제하시겠습니까?", "회원삭제", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					AccountDTO dto = member.get(table.getSelectedRow());
					AccountDAO dao = new AccountDAO();
					int resultD = dao.memberDelete(dto.getId());
					if (1 == resultD) {
						JOptionPane.showMessageDialog(null, "삭제가 완료되었습니다.", "회원삭제", JOptionPane.DEFAULT_OPTION);
						dispose();
						new MemberList();
					}
				}
			}
		});
	}

	public static void main(String[] args) {
		new MemberList();
	}
}
