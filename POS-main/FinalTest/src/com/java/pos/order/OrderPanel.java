package com.java.pos.order;

import static javax.swing.JOptionPane.NO_OPTION;
import static javax.swing.JOptionPane.showConfirmDialog;
import static javax.swing.JOptionPane.showMessageDialog;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.java.pos.account.Login;
import com.java.pos.account.MemberList;
import com.java.pos.account.UserThreadLocal;
import com.java.pos.db.AccountDTO;
import com.java.pos.db.OrderDAO;
import com.java.pos.db.OrderDTO;
import com.java.pos.db.OrderDetailDTO;
import com.java.pos.db.PayType;
import com.java.pos.db.ProductDAO;
import com.java.pos.db.ProductDTO;
import com.java.pos.order.component.CachePayButtonEventListener;
import com.java.pos.order.component.MenuButton;
import com.java.pos.order.component.MenuButtonEventListener;
import com.java.pos.order.component.PayButtonEventListener;
import com.java.pos.order.payment.CardPay;
import com.java.pos.order.payment.CashPay;

public class OrderPanel extends JPanel {
	JButton[] btnMenu = new JButton[9];

	JButton[] btnAction = new JButton[9];
	String[] menu = null;
	String[] action = { "카드결제", "현금결제", "선택취소", "전체취소", "주문현황", "직원관리", "처음화면", "+", "-" };
	String[] column = { "", "메뉴명", "가격", "개수" };
	String[][] data;
	String[] orderMenu = null;
	int[] orderPrice = null;
	int[] quatity = null;
	int[] price = null;
	int cnt = 1;
	JTextField tf = new JTextField(20);

	DefaultTableModel tModel = new DefaultTableModel(data, column) {
		public boolean isCellEditable(int i, int c) {
			return false;
		}
	};
	JTable table = new JTable(tModel);

	class Screen extends JPanel {
		Screen() {
			DefaultTableModel model = (DefaultTableModel) table.getModel();
			table.setRowHeight(50);
			table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			add(new JScrollPane(table));
		}
	}

	// 버튼을 생성
	class BtnMenu extends JPanel {
		BtnMenu() {
			setLayout(new GridLayout(3, 3, 3, 3));
			ProductDAO dao = new ProductDAO();
			List<ProductDTO> products = dao.findAll();
			for (int i = 0; i < btnMenu.length; i++) {
				ProductDTO product = products.get(i);
				add(new MenuButton(product, new MenuButtonEventListener(product, table, tf)));
			}
		}
	}

	class BtnAction extends JPanel {
		BtnAction() {
			setLayout(new GridLayout(1, 7, 3, 3));
			for (int i = 0; i < btnAction.length; i++) {
				btnAction[i] = new JButton(action[i]);
				add(btnAction[i]);
			}
		}
	}

	public OrderPanel(AccountDTO mDTO, JFrame frame) {
		Screen sc = new Screen();
		BtnMenu bm = new BtnMenu();
		BtnAction ba = new BtnAction();
		ProductDAO dao = new ProductDAO();
		price = dao.getProductPrice();

		table.getColumnModel().getColumn(0).setMinWidth(0);
		table.getColumnModel().getColumn(0).setMaxWidth(0);
		table.getColumnModel().getColumn(0).setWidth(0);

		sc.setSize(500, 500);
		sc.setLocation(25, 20);
		add(sc);

		bm.setSize(400, 430);
		bm.setLocation(530, 23);
		add(bm);

		// 최종금액
		tf.setSize(100, 70);
		tf.setLocation(530, 23);
		tf.setHorizontalAlignment(JTextField.CENTER);
		tf.setFont(new Font(null, Font.BOLD, 20));
		tf.setEditable(false);
		add(tf);

		ba.setSize(400, 70);
		ba.setLocation(530, 480);
		add(ba);

		// 카드결제
		btnAction[0].addActionListener(new PayButtonEventListener(table, "정말 카드로 결제하시겠습니까?", PayType.CARD, tf));

		// 현금결제
		btnAction[1].addActionListener(new PayButtonEventListener(table, "정말 현금으로 결제하시겠습니까?", PayType.CACHE, tf));

		// 선택 취소
		btnAction[2].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getRowCount() == 0) {
					showMessageDialog(null, "주문 메뉴가 존재하지 않습니다.");
					return;
				}
				if (table.getSelectedRow() == -1) {
					showMessageDialog(null, "취소할 메뉴를 선택해주세요.");
					return;
				}
				tModel.removeRow(table.getSelectedRow());
			}
		});

		// 감소
		btnAction[8].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DecimalFormat decFormat = new DecimalFormat("###,###");
				if (table.getSelectedRow() == -1) {
					showMessageDialog(null, "메뉴를 선택해주세요.");
					return;
				}
				int count = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 3).toString().replace("개", ""));
				if (count == 1) {
					tModel.removeRow(table.getSelectedRow());
					int currentPrice = getCurrentPrice();
					String currentPriceString = decFormat.format(currentPrice) + "원";
					tf.setText(currentPriceString);
					return;
				}
				table.setValueAt((count - 1) + "개", table.getSelectedRow(), 3);
				int currentPrice = getCurrentPrice();
				String currentPriceString = decFormat.format(currentPrice) + "원";
				tf.setText(currentPriceString);
			}
		});

		// 증가
		btnAction[7].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedRow() == -1) {
					showMessageDialog(null, "메뉴를 선택해주세요.");
					return;
				}
				int count = Integer.parseInt(table.getValueAt(table.getSelectedRow(), 3).toString().replace("개", ""));
				table.setValueAt((count + 1) + "개", table.getSelectedRow(), 3);
			}
		});

		// 전체 취소
		btnAction[3].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DefaultTableModel m = (DefaultTableModel) table.getModel();
				m.setRowCount(0);
				tf.setText(String.valueOf(""));
			}
		});

		// 주문정보
		btnAction[4].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mDTO.getPosition().equals("직원")) {
					System.out.println(mDTO.getPosition());
					JOptionPane.showMessageDialog(null, "권한이 없습니다.", "주문", JOptionPane.ERROR_MESSAGE);
				} else {
					new OrderListPanel();
				}
			}
		});

		// 직원관리
		btnAction[5].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (mDTO.getPosition().equals("직원")) {
					System.out.println(mDTO.getPosition());
					JOptionPane.showMessageDialog(null, "권한이 없습니다.", "주문", JOptionPane.ERROR_MESSAGE);
				} else {
					new MemberList();
				}
			}
		});

		// 처음화면
		btnAction[6].addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				frame.dispose();
				new Login();
			}
		});
	}

	private int getCurrentPrice() {
		int currentPrice = 0;
		for (int i = 0; i < table.getRowCount(); i++) {
			int price = Integer.parseInt(table.getValueAt(i, 2).toString().replaceAll(",", "").replace("원", ""));
			int quantity = Integer.parseInt(table.getValueAt(i, 3).toString().replace("개", ""));
			currentPrice += price * quantity;
		}
		return currentPrice;
	}
}