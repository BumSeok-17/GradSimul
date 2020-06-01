package graduate;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EventObject;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;

public class MyFrame extends JFrame {

	private CardLayout card;
	private Container mainContainer;// cardLayout 넣을 패널 (페이지넘기는 용도)

	private JPanel panel1;
	private JLabel label1, label2, label3;
	private JTextField textField1, textField2, textField3;
	private JButton btn1;

	private JComboBox comboBox;
	// 로그인 구성요소

	private JPanel panel2;
	private JButton menuBtn1, menuBtn2, menuBtn3, menuBtn4;
	private boolean isRegister;
	// 메인 메뉴 구성요소

	private JPanel panel3;
	String[] starr1 = { "전필", "전선", "지교", "기교(글쓰기)", "기교(외국어)", "기교(취창업)", "기교(S/W)", "기교(인성)", "심교(글로벌인재양성)",
			"심교(학문소양및인성함양)", "심교(사고력증진)", "일선" };
	String[] starr2 = { "A+", "A", "B+", "B", "C+", "C", "D+", "D", "F", "P", "NP", "이수중" };
	String[] starr3 = { "1", "2", "3" };
	String[] starr4;
	String[] starr5;
	String[] starr6;
	String[] starr7;
	String[] starr8;
	String[] starr9;
	String[] starr10;
	String[] starr11;
	String[] starr12 = new String[40];
	String[] starr13 = { "X" };
	List<String> resultList1 = new ArrayList<String>();
	List<String> resultList2 = new ArrayList<String>();
	List<String> resultList3 = new ArrayList<String>();
	List<String> resultList4 = new ArrayList<String>();
	int i = 0;
	int i1 = 0;
	List<Integer> selectedIds = new ArrayList<Integer>();
	List<Integer> unselectedIds = new ArrayList<Integer>();
	List<Integer> selectedIdsforFuture = new ArrayList<Integer>();
	JCheckBoxWithID[] cba = new JCheckBoxWithID[100];
	JComboBox[] combox1 = new JComboBox[100];
	JComboBox[] combox2 = new JComboBox[100];
	JComboBox[] combox3 = new JComboBox[100];
	JComboBox[] combox4 = new JComboBox[100];
	String semester;
	int e_sem=1;
	boolean isopen;
	boolean[] openArray = new boolean[200];
	boolean[] flagbool = new boolean[200];
	// 새 졸시

	private JPanel panel4;

	Manager manager;
	// 메인 메뉴구성

	public MyFrame() {
		manager = new Manager();

		super.setTitle("소프트웨어학과 졸업 시뮬레이션");
		this.setSize(1600, 900);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		mainContainer = new JPanel();
		card = new CardLayout();
		mainContainer.setLayout(card);
		setContentPane(mainContainer);

		panel1 = new JPanel();
		panel1.setLayout(null);
		panel2 = new JPanel();
		panel2.setLayout(null);
		panel3 = new JPanel();
		panel3.setLayout(null);

		panel4 = new JPanel();

		mainContainer.add(panel1, "1");
		mainContainer.add(panel2, "2");
		mainContainer.add(panel3, "3");
		mainContainer.add(panel4, "4");

		loginMenu();
		this.setVisible(true);
		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
	}

	public void loginMenu() {

		JPanel namePanel = new JPanel();
		namePanel.setSize(1600, 40);
		panel1.add(namePanel);
		label1 = new JLabel("이름 : ");
		namePanel.add(label1);
		textField1 = new JTextField(20);
		namePanel.add(textField1);

		JPanel snoPanel = new JPanel();
		snoPanel.setSize(1600, 40);
		snoPanel.setLocation(0, 40);
		panel1.add(snoPanel);
		label2 = new JLabel("학번 : 2017");
		snoPanel.add(label2);
		textField2 = new JTextField(20);
		snoPanel.add(textField2);

		JPanel semesterPanel = new JPanel();
		semesterPanel.setBounds(0, 80, 1600, 40);
		panel1.add(semesterPanel);
		label3 = new JLabel("학기 : ");
		semesterPanel.add(label3);
		textField3 = new JTextField(20);

		comboBox = new JComboBox();
		comboBox.addItem("1");
		comboBox.addItem("2");
		semesterPanel.add(comboBox);

		btn1 = new JButton("다음");
		btn1.setBounds(1400, 750, 100, 30);
		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				// 다음 버튼 클릭시 해야할 행동
				if (!manager.checkInput(textField1.getText(), textField2.getText())) {
					switch (manager.errorNum) {
					case 1:
						JOptionPane.showMessageDialog(null, "이름의 잘못된 입력입니다.", "잘못된 입력", JOptionPane.ERROR_MESSAGE);
						break;
					case 2:
						JOptionPane.showMessageDialog(null, "학번의 잘못된 입력입니다.", "잘못된 입력", JOptionPane.ERROR_MESSAGE);
						break;
					}
				} else {
					isRegister = manager.checkUser(textField1.getText(), textField2.getText(),
							comboBox.getSelectedItem().toString());
					mainMenu();
				}

			}

		});
		panel1.add(btn1);
		card.show(mainContainer, "1");

	}

	public void mainMenu() {
		semester = manager.student.semester;
		menuBtn1 = new JButton("새 졸업시뮬레이션");
		menuBtn2 = new JButton("기존 정보 불러오기");
		menuBtn3 = new JButton("기존 정보 수정하기");
		menuBtn4 = new JButton("종료");

		panel2.add(menuBtn1);
		panel2.add(menuBtn2);
		panel2.add(menuBtn3);
		panel2.add(menuBtn4);

		menuBtn1.setBounds(0, 0, 1600, 40);
		menuBtn2.setBounds(0, 40, 1600, 40);
		menuBtn3.setBounds(0, 80, 1600, 40);
		menuBtn4.setBounds(0, 120, 1600, 40);

		if (isRegister) {
			menuBtn1.setEnabled(false);

		} else {
			menuBtn2.setEnabled(false);
			menuBtn3.setEnabled(false);
		}

		menuBtn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				makeSimul();
			}

		});
		menuBtn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				showResult(0);
				card.show(mainContainer, "4");
			}

		});
		menuBtn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				panel3.removeAll();
				modifyMenu();
			}
		});
		menuBtn4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}

		});
		card.show(mainContainer, "2");
	}

	public void makeSimul() {

		for (int flag = 0; flag < 40; flag++) {
			starr12[flag] = Integer.toString(flag + 1);
		}

		starr4 = manager.bringlec("전필");
		starr5 = manager.bringlec("전선");
		starr6 = manager.bringlec("지교");
		starr7 = manager.bringlec("기교(글쓰기)");
		starr8 = manager.bringlec("기교(외국어)");
		starr9 = manager.bringlec("기교(취창업)");
		starr10 = manager.bringlec("기교(S/W)");
		starr11 = manager.bringlec("기교(인성)");

		Border blackline = BorderFactory.createLineBorder(Color.black);
		JPanel centerPanel = new JPanel();
		panel3.add(centerPanel);
		centerPanel.setLayout(null);
		centerPanel.setBounds(50, 50, 1300, 700);
		centerPanel.setPreferredSize(new Dimension(1300, 3000));

		JScrollPane scrollp = new JScrollPane(centerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollp.setBounds(50, 50, 1300, 700);
		panel3.add(scrollp);
		scrollp.setVisible(true);

		ItemListener itemListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getSource() instanceof JCheckBoxWithID) {
					JCheckBoxWithID checkBoxWithID = (JCheckBoxWithID) e.getSource();
					if (checkBoxWithID.isSelected()) {
						selectedIds.add(checkBoxWithID.getId());
					} else {
						selectedIds.remove(checkBoxWithID.getId());
					}
				}
			}
		};

		ItemListener itemListener2 = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				for (int flag = 0; flag < 100; flag++) {
					if (e.getSource().equals(combox1[flag])) {
						JComboBox combobox = (JComboBox) e.getSource();
						i1 = combobox.getSelectedIndex();
						combox4[flag].setVisible(false);
						if (i1 == 0) {
							combox4[flag] = new JComboBox<String>(starr4);
						} else if (i1 == 1) {
							combox4[flag] = new JComboBox<String>(starr5);
						} else if (i1 == 2) {
							combox4[flag] = new JComboBox<String>(starr6);
						} else if (i1 == 3) {
							combox4[flag] = new JComboBox<String>(starr7);
						} else if (i1 == 4) {
							combox4[flag] = new JComboBox<String>(starr8);
						} else if (i1 == 5) {
							combox4[flag] = new JComboBox<String>(starr9);
						} else if (i1 == 6) {
							combox4[flag] = new JComboBox<String>(starr10);
						} else if (i1 == 7) {
							combox4[flag] = new JComboBox<String>(starr11);
						} else if (i1 == 8 || i1 == 9 || i1 == 10 || i1 == 11) {
							combox3[flag].setVisible(false);
							combox3[flag] = new JComboBox<String>(starr12);
							combox4[flag] = new JComboBox<String>(starr13);
							combox3[flag].setBounds(320, 15 + 50 * flag, 70, 40);
							centerPanel.add(combox3[flag]);
						}
						combox4[flag].setBounds(410, 15 + 50 * flag, 280, 40);
						centerPanel.add(combox4[flag]);
						centerPanel.revalidate();
						centerPanel.repaint();
					}
				}
			}
		};

		JLabel la1 = new JLabel("새 졸업 시뮬레이션");
		la1.setBounds(20, 10, 200, 20);
		panel3.add(la1);

		JLabel la2 = new JLabel("외국어 영역 이수 의무 면제 여부");
		la2.setBounds(20, 800, 200, 20);
		panel3.add(la2);

		JCheckBox checkbox = new JCheckBox();
		checkbox.setBounds(210, 803, 17, 15);
		panel3.add(checkbox);
		if (checkbox.isSelected()) {
			manager.student.ispass = true;
		} else {
			manager.student.ispass = false;
		}

		JButton bt1 = new JButton("추가");
		bt1.setBounds(1380, 10, 70, 30);
		panel3.add(bt1);

		JButton bt2 = new JButton("삭제");
		bt2.setBounds(1470, 10, 70, 30);
		panel3.add(bt2);

		JButton bt3 = new JButton("결과 출력");
		bt3.setBounds(1400, 780, 90, 30);
		panel3.add(bt3);

		for (i = 0; i < 6; i++) {// 처음 6개 출력
			createClass(centerPanel, i, itemListener, itemListener2);
		}
		ActionListener listener = new ActionListener() { // 추가

			@Override
			public void actionPerformed(ActionEvent arg0) {

				createClass(centerPanel, i, itemListener, itemListener2);
				i++;
				centerPanel.revalidate();
				centerPanel.repaint();
			}
		};
		bt1.addActionListener(listener);

		ActionListener listener2 = new ActionListener() { // 삭제

			@Override
			public void actionPerformed(ActionEvent arg0) {

				for (int k = 0; k < i; k++) {

					unselectedIds.add(k);
				}
				for (int k = 0; k < selectedIds.size(); k++) {

					unselectedIds.remove(selectedIds.get(k));
					cba[selectedIds.get(k)].setVisible(false);
					combox1[selectedIds.get(k)].setVisible(false);
					combox2[selectedIds.get(k)].setVisible(false);
					combox3[selectedIds.get(k)].setVisible(false);
					combox4[selectedIds.get(k)].setVisible(false);
				}

				for (int k = 0; k < unselectedIds.size(); k++) {
					cba[unselectedIds.get(k)].setBounds(30, 25 + 50 * k, 17, 15);
					cba[unselectedIds.get(k)].setId(k);
					combox1[unselectedIds.get(k)].setBounds(70, 15 + 50 * k, 140, 40);
					combox2[unselectedIds.get(k)].setBounds(230, 15 + 50 * k, 70, 40);
					combox3[unselectedIds.get(k)].setBounds(320, 15 + 50 * k, 70, 40);
					combox4[unselectedIds.get(k)].setBounds(410, 15 + 50 * k, 280, 40);
				}
				for (int k = 0; k < unselectedIds.size(); k++) {
					cba[k] = cba[unselectedIds.get(k)];
					combox1[k] = combox1[unselectedIds.get(k)];
					combox2[k] = combox2[unselectedIds.get(k)];
					combox3[k] = combox3[unselectedIds.get(k)];
					combox4[k] = combox4[unselectedIds.get(k)];
				}
				for (int k = unselectedIds.size(); k < 40; k++) {
					cba[k] = null;
					combox1[k] = null;
					combox2[k] = null;
					combox3[k] = null;
					combox4[k] = null;
				}
				i = unselectedIds.size();
				unselectedIds.clear();
				selectedIds.clear();

				centerPanel.revalidate();
				centerPanel.repaint();
			}

		};
		bt2.addActionListener(listener2);

		ActionListener listener3 = new ActionListener() { // 결과출력

			@Override
			public void actionPerformed(ActionEvent arg0) {

				for (int k = 0; k < i; k++) {
					resultList1.add((String) combox1[k].getSelectedItem());
					resultList2.add((String) combox2[k].getSelectedItem());
					resultList3.add((String) combox3[k].getSelectedItem());
					resultList4.add((String) combox4[k].getSelectedItem());
					if (!(resultList1.get(k).equals("심교(글로벌인재양성)") || resultList1.get(k).equals("심교(학문소양및인성함양)")
							|| resultList1.get(k).equals("심교(사고력증진)") || resultList1.get(k).equals("일선"))) {
						int index = 0;
						boolean isDuplicate = false;
						for (int l = 0; l < manager.student.lec.size(); l++) {
							if (resultList4.get(k).equals(manager.student.lec.get(l).lname) == true) {
								index = l;
								isDuplicate = true;
								break;
							}
						}
						if (isDuplicate) {
							if (manager.student.lec.get(index).lgrade != null) {
								char[] grade1 = manager.student.lec.get(index).lgrade.toCharArray();
								char[] grade2 = resultList2.get(k).toCharArray();
								if (grade1[0] < grade2[0]) {
									continue;
								} else if (grade1[0] > grade2[0]) {
									manager.student.lec.remove(index);
								} else {
									if (manager.student.lec.get(index).lgrade.length() >= resultList4.get(k).length()) {
										continue;
									} else {
										manager.student.lec.remove(index);
									}
								}
							}
						}
					}
					manager.student.lec.add(new Lecture(resultList4.get(k), resultList1.get(k),
							Integer.parseInt(resultList3.get(k)), resultList2.get(k)));
				}
				manager.calcredit();
				showResult(0);
				card.show(mainContainer, "4");
			}
		};
		bt3.addActionListener(listener3);

		card.show(mainContainer, "3");
	}

	class JCheckBoxWithID extends JCheckBox { // 체크박스아이디 달기

		private Integer _id;

		public JCheckBoxWithID(Integer id) {
			_id = id;
		}

		public void setId(Integer id) {
			_id = id;
		}

		public Integer getId() {
			return _id;
		}
	}

	public void createClass(JPanel panel, int i, ItemListener itemListener, ItemListener itemListener2) {
		JCheckBoxWithID checkbox2 = new JCheckBoxWithID(i);
		checkbox2.setBounds(30, 25 + 50 * i, 17, 15);
		checkbox2.addItemListener(itemListener);
		panel.add(checkbox2);
		cba[i] = checkbox2;

		JComboBox<String> cb1 = new JComboBox<String>(starr1);
		cb1.setBounds(70, 15 + 50 * i, 140, 40);
		cb1.addItemListener(itemListener2);
		panel.add(cb1);
		combox1[i] = cb1;

		JComboBox<String> cb2 = new JComboBox<String>(starr2);
		cb2.setBounds(230, 15 + 50 * i, 70, 40);
		panel.add(cb2);
		combox2[i] = cb2;

		JComboBox<String> cb3 = new JComboBox<String>(starr3);
		cb3.setBounds(320, 15 + 50 * i, 70, 40);
		panel.add(cb3);
		combox3[i] = cb3;

		JComboBox<String> cb4 = new JComboBox<String>(starr4);
		cb4.setBounds(410, 15 + 50 * i, 280, 40);
		panel.add(cb4);
		combox4[i] = cb4;

		panel.revalidate();
		panel.repaint();

	}

	public void createClass(JPanel panel, int i, ItemListener itemListener, ItemListener itemListener2, Student stu) {
		JCheckBoxWithID checkbox2 = new JCheckBoxWithID(i);
		checkbox2.setBounds(30, 25 + 50 * i, 17, 15);
		checkbox2.addItemListener(itemListener);
		panel.add(checkbox2);
		cba[i] = checkbox2;

		JComboBox<String> cb1 = new JComboBox<String>(starr1);
		cb1.setBounds(70, 15 + 50 * i, 140, 40);
		cb1.addItemListener(itemListener2);
		cb1.setSelectedItem(stu.lec.get(i).ltype);
		panel.add(cb1);
		combox1[i] = cb1;

		JComboBox<String> cb2 = new JComboBox<String>(starr2);
		cb2.setBounds(230, 15 + 50 * i, 70, 40);
		cb2.setSelectedItem(stu.lec.get(i).lgrade);
		panel.add(cb2);
		combox2[i] = cb2;

		JComboBox<String> cb3 = new JComboBox<String>(starr3);
		cb3.setBounds(320, 15 + 50 * i, 70, 40);
		cb3.setSelectedIndex(stu.lec.get(i).lcredit - 1);
		panel.add(cb3);
		combox3[i] = cb3;

		if (stu.lec.get(i).ltype.contentEquals("전필")) {
			JComboBox<String> cb4 = new JComboBox<String>(starr4);
			cb4.setBounds(410, 15 + 50 * i, 280, 40);
			cb4.setSelectedItem(stu.lec.get(i).lname);
			panel.add(cb4);
			combox4[i] = cb4;
		} else if (stu.lec.get(i).ltype.equals("전선")) {
			JComboBox<String> cb4 = new JComboBox<String>(starr5);
			cb4.setBounds(410, 15 + 50 * i, 280, 40);
			cb4.setSelectedItem(stu.lec.get(i).lname);
			panel.add(cb4);
			combox4[i] = cb4;
		} else if (stu.lec.get(i).ltype.equals("지교")) {
			JComboBox<String> cb4 = new JComboBox<String>(starr6);
			cb4.setBounds(410, 15 + 50 * i, 280, 40);
			cb4.setSelectedItem(stu.lec.get(i).lname);
			panel.add(cb4);
			combox4[i] = cb4;
		} else if (stu.lec.get(i).ltype.equals("기교(글쓰기)")) {
			JComboBox<String> cb4 = new JComboBox<String>(starr7);
			cb4.setBounds(410, 15 + 50 * i, 280, 40);
			cb4.setSelectedItem(stu.lec.get(i).lname);
			panel.add(cb4);
			combox4[i] = cb4;
		} else if (stu.lec.get(i).ltype.equals("기교(외국어)")) {
			JComboBox<String> cb4 = new JComboBox<String>(starr8);
			cb4.setBounds(410, 15 + 50 * i, 280, 40);
			cb4.setSelectedItem(stu.lec.get(i).lname);
			panel.add(cb4);
			combox4[i] = cb4;
		} else if (stu.lec.get(i).ltype.equals("기교(취창업)")) {
			JComboBox<String> cb4 = new JComboBox<String>(starr9);
			cb4.setBounds(410, 15 + 50 * i, 280, 40);
			cb4.setSelectedItem(stu.lec.get(i).lname);
			panel.add(cb4);
			combox4[i] = cb4;
		} else if (stu.lec.get(i).ltype.equals("기교(S/W)")) {
			JComboBox<String> cb4 = new JComboBox<String>(starr10);
			cb4.setBounds(410, 15 + 50 * i, 280, 40);
			cb4.setSelectedItem(stu.lec.get(i).lname);
			panel.add(cb4);
			combox4[i] = cb4;
		} else if (stu.lec.get(i).ltype.equals("기교(인성)")) {
			JComboBox<String> cb4 = new JComboBox<String>(starr11);
			cb4.setBounds(410, 15 + 50 * i, 280, 40);
			cb4.setSelectedItem(stu.lec.get(i).lname);
			panel.add(cb4);
			combox4[i] = cb4;
		} else {
			JComboBox<String> cb4 = new JComboBox<String>(starr13);
			cb4.setBounds(410, 15 + 50 * i, 280, 40);
			cb4.setSelectedItem(stu.lec.get(i).lname);
			panel.add(cb4);
			combox4[i] = cb4;
		}

		panel.revalidate();
		panel.repaint();
	}

	public void showResult(int session) {
		panel4.removeAll();
		Object[] leftlec = new Object[8];
		String header[] = { "졸업요건", "기준학점", "취득학점", "잔여학점" };
		int totalget = manager.student.others + manager.student.mrgrade + manager.student.msgrade
				+ manager.student.rrgrade + manager.student.rbgrade[0] + manager.student.rbgrade[1]
				+ manager.student.rbgrade[2] + manager.student.rbgrade[3] + manager.student.rbgrade[4]
				+ manager.student.rdgrade[0] + manager.student.rdgrade[1] + manager.student.rdgrade[2];
		String contents[][] = {
				{ "전필", "13", Integer.toString(manager.student.mrgrade),
						Integer.toString((13 - manager.student.msgrade) > 0 ? 13 - manager.student.mrgrade : 0) },
				{ "전선", "67", Integer.toString(manager.student.msgrade),
						Integer.toString((67 - manager.student.msgrade) > 0 ? 67 - manager.student.msgrade : 0) },
				{ "지교", "18", Integer.toString(manager.student.rrgrade),
						Integer.toString((18 - manager.student.rrgrade) > 0 ? 18 - manager.student.rrgrade : 0) },
				{ "기교(글쓰기)", "3", Integer.toString(manager.student.rbgrade[0]),
						Integer.toString((3 - manager.student.rbgrade[0]) > 0 ? 3 - manager.student.rbgrade[0] : 0) },
				{ "기교(외국어)", "3", Integer.toString(manager.student.rbgrade[1]),
						Integer.toString((3 - manager.student.rbgrade[1]) > 0 ? 3 - manager.student.rbgrade[1] : 0) },
				{ "기교(취창업)", "2", Integer.toString(manager.student.rbgrade[2]),
						Integer.toString((2 - manager.student.rbgrade[2]) > 0 ? 2 - manager.student.rbgrade[2] : 0) },
				{ "기교(S/W)", "3", Integer.toString(manager.student.rbgrade[3]),
						Integer.toString((3 - manager.student.rbgrade[3]) > 0 ? 3 - manager.student.rbgrade[3] : 0) },
				{ "기교(인성)", "1", Integer.toString(manager.student.rbgrade[4]),
						Integer.toString((1 - manager.student.rbgrade[4]) > 0 ? 1 - manager.student.rbgrade[4] : 0) },
				{ "심교(글로벌인재양성)", "4", Integer.toString(manager.student.rdgrade[0]),
						Integer.toString((4 - manager.student.rdgrade[0]) > 0 ? 4 - manager.student.rdgrade[0] : 0) },
				{ "심교(학문소양및인성함양)", "4", Integer.toString(manager.student.rdgrade[1]),
						Integer.toString((4 - manager.student.rdgrade[1]) > 0 ? 4 - manager.student.rdgrade[1] : 0) },
				{ "심교(사고력증진)", "4", Integer.toString(manager.student.rdgrade[2]),
						Integer.toString((4 - manager.student.rdgrade[2]) > 0 ? 4 - manager.student.rdgrade[2] : 0) },
				{ "총학점", "132", Integer.toString(totalget),
						Integer.toString(132 - totalget > 0 ? 132 - totalget : 0) } };
		if (manager.student.ispass) {
			contents[4][1] = "0";
			contents[4][3] = "0";
		}
		String header2[] = { "과목명", "이수구분", "이수학점", "개설학기", "선이수과목", "교수명", "개설학년", "수강하기" };

		ArrayList<Lecture> lec = manager.loadClass();

		JTable table1 = new JTable(contents, header);// 결과 출력 테이블
		table1.setEnabled(false);
		JScrollPane pane1 = new JScrollPane(table1);// 혹시 모를 스크롤을 생성하기 위해
		JPanel hpanel = new JPanel();// 상단 패널
		JPanel bpanel = new JPanel(new BorderLayout());// 하단 패널
		// 버튼은 이미지를 넣을 수 있다면 넣어보자...
		JButton menu = new JButton("메인으로");// 메인 메뉴로 돌아가는 버튼
		JButton save = new JButton("저장");// 저장버튼
		JButton next = new JButton("다음 학기");// 미래 졸업시뮬레이션 버튼
		JLabel num = new JLabel("학번 : " + manager.student.sno);// 학번 출력
		JLabel name = new JLabel("이름 : " + manager.student.sname);// 이름 출력
		JLabel seme = new JLabel("학기 : " + semester);// 학기 출력

		menu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				int result = JOptionPane.showConfirmDialog(null, "저장하시겠습니까?", "Save", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					manager.deleteEnrol();
					for (int i = 0; i < manager.student.lec.size(); i++) {
						manager.SaveClass(manager.student.sno, manager.student.lec.get(i).lname,
								Integer.toString(manager.student.lec.get(i).lcredit), manager.student.lec.get(i).ltype,
								manager.student.lec.get(i).lgrade);
					}
					panel2.removeAll();
					manager.studentupdate();
					isRegister = true;
				} else if (result == JOptionPane.NO_OPTION) {
					manager.student.lec.clear();
					manager.initStudent();
					manager.calcredit();
				}
				mainMenu();
			}

		});

		save.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				manager.deleteEnrol();
				for (int i = 0; i < manager.student.lec.size(); i++) {
					manager.SaveClass(manager.student.sno, manager.student.lec.get(i).lname,
							Integer.toString(manager.student.lec.get(i).lcredit), manager.student.lec.get(i).ltype,
							manager.student.lec.get(i).lgrade);
				}
				panel2.removeAll();
				manager.studentupdate();
				isRegister = true;
				mainMenu();
			}

		});

		DefaultTableModel tm = new DefaultTableModel(header2, 0);
		JTable table2 = new JTable(tm);
		JScrollPane pane2 = new JScrollPane(table2);
		bpanel.add(pane2);
		JCheckBox checkbox = new JCheckBox();
		int row = 0;
		for (int i = 0; i < lec.size(); i++) {
			isopen = true;
			if (manager.findenrol(lec.get(i).lname)) {
				leftlec[0] = lec.get(i).lname;
				leftlec[1] = lec.get(i).ltype;
				leftlec[2] = Integer.toString(lec.get(i).lcredit);
				leftlec[3] = lec.get(i).semester;
				leftlec[4] = lec.get(i).front;
				leftlec[5] = lec.get(i).prof;
				leftlec[6] = lec.get(i).sgrade;
				leftlec[7]=false;
				flagbool[row] = manager.flag;
				if (leftlec[3] != null) {
					if (semester.equals(leftlec[3]) == false) {
						if (leftlec[4] != null) {
							for (int k = 0; k < manager.student.lec.size(); k++) {
								if (manager.student.lec.get(k).lname.equals(leftlec[4]) == false) {
									isopen = false;
								}
							}
						}
					} else
						isopen = false;
				}
				openArray[row++] = isopen;
				table2.getColumn("수강하기").setCellRenderer(defaultTableCellRenderer);
				table2.getColumn("수강하기").setCellEditor(new DefaultCellEditor(checkbox));
				checkbox.setHorizontalAlignment(JLabel.CENTER);
				if(session==0) tm.addRow(leftlec);
				else {
					if(session==lec.get(i).session || lec.get(i).session==3) tm.addRow(leftlec);
				}

			}
		}

		next.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				for (int i = 0; i < table2.getRowCount(); i++) {
					boolean value = (boolean) table2.getValueAt(i, 7);
					String a = (String) table2.getValueAt(i, 0);
					String b = (String) table2.getValueAt(i, 1);
					int c = Integer.parseInt((String) table2.getValueAt(i, 2));
					if (value == true) {
						if (!flagbool[i])
							manager.student.lec.add(new Lecture(a, b, c, "A+", e_sem));
						else {
							for (int k = 0; k < manager.student.lec.size(); k++) {
								if (manager.student.lec.get(k).lname.equals(a)) {
									manager.student.lec.remove(k);
									manager.student.lec.add(new Lecture(a, b, c, "A", e_sem++));
								}
							}

						}
					}
				}
				// for (int i = 0; i < manager.student.lec.size(); i++)
				manager.calcredit();
				int sess;
				if (session==1) {
					semester = "2";
					sess=0;
				}
				else if(session==2) {
					semester = "1";
					sess=0;
				}
				else sess=Integer.parseInt(semester);
				showResult(sess);
				panel4.revalidate();
				panel4.repaint();
				// mainMenu();
			}

		});
		// 상단 패널에 컴포넌트 추가
		hpanel.add(menu);
		hpanel.add(num);
		hpanel.add(name);
		hpanel.add(seme);
		hpanel.add(save);

		// 하단 패널에 컴포넌트 추가
		bpanel.add(next, BorderLayout.EAST);

		if (manager.Graduation(manager.student)) {
			String str= JOptionPane.showInputDialog("졸업을 축하합니다!\n미래 시뮬레이션 이름을 입력하세요.");

			manager.deleteEnrol();
			for(int i=0; i<manager.student.lec.size(); i++) {
				if(manager.student.lec.get(i).session==0) {
					manager.SaveClass(manager.student.sno, manager.student.lec.get(i).lname,
							Integer.toString(manager.student.lec.get(i).lcredit), manager.student.lec.get(i).ltype,
							manager.student.lec.get(i).lgrade);
				}
				else {
					manager.SaveSimul(manager.student.sno, manager.student.lec.get(i).lname,
							Integer.toString(manager.student.lec.get(i).lcredit), manager.student.lec.get(i).ltype,
							manager.student.lec.get(i).lgrade, str, manager.student.lec.get(i).session);
				}
			}
			manager.student.lec.clear();
			manager.initStudent();
			manager.calcredit();
			manager.studentupdate();
			mainMenu();
		}

		JLabel avg = new JLabel("평균 학점: " + Float.toString(manager.calculate(manager.student)));

		panel4.add(avg, BorderLayout.WEST);
		panel4.add(hpanel, BorderLayout.NORTH);// 상단에 상단 패널 추가
		panel4.add(pane1, BorderLayout.CENTER);// 중앙에 테이블 추가
		panel4.add(bpanel, BorderLayout.SOUTH);// 하단에 하단 패널 추가

	}

	DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer() {
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
				int row, int column) {
			JCheckBox checkBox = new JCheckBox();
			if (openArray[row])
				checkBox.setSelected(((Boolean) value).booleanValue());
			checkBox.setEnabled(openArray[row]);
			checkBox.setHorizontalAlignment(JLabel.CENTER);
			return checkBox;
		}
	};

	public void modifyMenu() {

		for (int flag = 0; flag < 40; flag++) {
			starr12[flag] = Integer.toString(flag + 1);
		}

		starr4 = manager.bringlec("전필");
		starr5 = manager.bringlec("전선");
		starr6 = manager.bringlec("지교");
		starr7 = manager.bringlec("기교(글쓰기)");
		starr8 = manager.bringlec("기교(외국어)");
		starr9 = manager.bringlec("기교(취창업)");
		starr10 = manager.bringlec("기교(S/W)");
		starr11 = manager.bringlec("기교(인성)");

		Border blackline = BorderFactory.createLineBorder(Color.black);
		JPanel centerPanel = new JPanel();
		panel3.add(centerPanel);
		centerPanel.setLayout(null);
		centerPanel.setBounds(50, 50, 1300, 700);
		centerPanel.setPreferredSize(new Dimension(1300, 3000));

		JScrollPane scrollp = new JScrollPane(centerPanel, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollp.setBounds(50, 50, 1300, 700);
		panel3.add(scrollp);
		scrollp.setVisible(true);

		ItemListener itemListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getSource() instanceof JCheckBoxWithID) {
					JCheckBoxWithID checkBoxWithID = (JCheckBoxWithID) e.getSource();
					if (checkBoxWithID.isSelected()) {
						selectedIds.add(checkBoxWithID.getId());
					} else {
						selectedIds.remove(checkBoxWithID.getId());
					}
				}
			}
		};

		ItemListener itemListener2 = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				for (int flag = 0; flag < 100; flag++) {
					if (e.getSource().equals(combox1[flag])) {
						JComboBox combobox = (JComboBox) e.getSource();
						i1 = combobox.getSelectedIndex();
						combox4[flag].setVisible(false);
						if (i1 == 0) {
							combox4[flag] = new JComboBox<String>(starr4);
						} else if (i1 == 1) {
							combox4[flag] = new JComboBox<String>(starr5);
						} else if (i1 == 2) {
							combox4[flag] = new JComboBox<String>(starr6);
						} else if (i1 == 3) {
							combox4[flag] = new JComboBox<String>(starr7);
						} else if (i1 == 4) {
							combox4[flag] = new JComboBox<String>(starr8);
						} else if (i1 == 5) {
							combox4[flag] = new JComboBox<String>(starr9);
						} else if (i1 == 6) {
							combox4[flag] = new JComboBox<String>(starr10);
						} else if (i1 == 7) {
							combox4[flag] = new JComboBox<String>(starr11);
						} else if (i1 == 8 || i1 == 9 || i1 == 10 || i1 == 11) {
							combox3[flag].setVisible(false);
							combox3[flag] = new JComboBox<String>(starr12);
							combox4[flag] = new JComboBox<String>(starr13);
							combox3[flag].setBounds(320, 15 + 50 * flag, 70, 40);
							centerPanel.add(combox3[flag]);
						}
						combox4[flag].setBounds(410, 15 + 50 * flag, 280, 40);
						centerPanel.add(combox4[flag]);
						centerPanel.revalidate();
						centerPanel.repaint();
					}
				}
			}
		};

		JLabel la1 = new JLabel("새 졸업 시뮬레이션");
		la1.setBounds(20, 10, 200, 20);
		panel3.add(la1);

		JLabel la2 = new JLabel("외국어 영역 이수 의무 면제 여부");
		la2.setBounds(20, 800, 200, 20);
		panel3.add(la2);

		JCheckBox checkbox = new JCheckBox();
		checkbox.setBounds(210, 803, 17, 15);
		panel3.add(checkbox);
		ItemListener itemListener3 = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getSource() instanceof JCheckBox) {
					JCheckBox checkbox = (JCheckBox) e.getSource();
					if (checkbox.isSelected()) {
						manager.student.ispass = true;
					} else {
						manager.student.ispass = false;
					}
				}
			}
		};
		checkbox.addItemListener(itemListener3);

		JButton bt1 = new JButton("추가");
		bt1.setBounds(1380, 10, 70, 30);
		panel3.add(bt1);

		JButton bt2 = new JButton("삭제");
		bt2.setBounds(1470, 10, 70, 30);
		panel3.add(bt2);

		JButton bt3 = new JButton("결과 출력");
		bt3.setBounds(1400, 780, 90, 30);
		panel3.add(bt3);

		for (i = 0; i < manager.student.lec.size(); i++) {// 학생 객체가 있을 때 초기 정보 출력
			createClass(centerPanel, i, itemListener, itemListener2, manager.student);
		}

		ActionListener listener = new ActionListener() { // 추가

			@Override
			public void actionPerformed(ActionEvent arg0) {

				createClass(centerPanel, i, itemListener, itemListener2);
				i++;
				centerPanel.revalidate();
				centerPanel.repaint();
			}
		};
		bt1.addActionListener(listener);

		ActionListener listener2 = new ActionListener() { // 삭제

			@Override
			public void actionPerformed(ActionEvent arg0) {

				for (int k = 0; k < i; k++) {

					unselectedIds.add(k);
				}
				for (int k = 0; k < selectedIds.size(); k++) {

					unselectedIds.remove(selectedIds.get(k));
					cba[selectedIds.get(k)].setVisible(false);
					combox1[selectedIds.get(k)].setVisible(false);
					combox2[selectedIds.get(k)].setVisible(false);
					combox3[selectedIds.get(k)].setVisible(false);
					combox4[selectedIds.get(k)].setVisible(false);
				}

				for (int k = 0; k < unselectedIds.size(); k++) {
					cba[unselectedIds.get(k)].setBounds(30, 25 + 50 * k, 17, 15);
					cba[unselectedIds.get(k)].setId(k);
					combox1[unselectedIds.get(k)].setBounds(70, 15 + 50 * k, 140, 40);
					combox2[unselectedIds.get(k)].setBounds(230, 15 + 50 * k, 70, 40);
					combox3[unselectedIds.get(k)].setBounds(320, 15 + 50 * k, 70, 40);
					combox4[unselectedIds.get(k)].setBounds(410, 15 + 50 * k, 280, 40);
				}
				for (int k = 0; k < unselectedIds.size(); k++) {
					cba[k] = cba[unselectedIds.get(k)];
					combox1[k] = combox1[unselectedIds.get(k)];
					combox2[k] = combox2[unselectedIds.get(k)];
					combox3[k] = combox3[unselectedIds.get(k)];
					combox4[k] = combox4[unselectedIds.get(k)];
				}
				for (int k = unselectedIds.size(); k < 40; k++) {
					cba[k] = null;
					combox1[k] = null;
					combox2[k] = null;
					combox3[k] = null;
					combox4[k] = null;
				}
				i = unselectedIds.size();
				unselectedIds.clear();
				selectedIds.clear();

				centerPanel.revalidate();
				centerPanel.repaint();
			}

		};
		bt2.addActionListener(listener2);

		ActionListener listener3 = new ActionListener() { // 결과출력

			@Override
			public void actionPerformed(ActionEvent arg0) {

				manager.student.lec.clear();

				for (int k = 0; k < i; k++) {
					resultList1.add((String) combox1[k].getSelectedItem());
					resultList2.add((String) combox2[k].getSelectedItem());
					resultList3.add((String) combox3[k].getSelectedItem());
					resultList4.add((String) combox4[k].getSelectedItem());
					if (!(resultList1.get(k).equals("심교(글로벌인재양성)") || resultList1.get(k).equals("심교(학문소양및인성함양)")
							|| resultList1.get(k).equals("심교(사고력증진)") || resultList1.get(k).equals("일선"))) {
						int index = 0;
						boolean isDuplicate = false;
						for (int l = 0; l < manager.student.lec.size(); l++) {
							if (resultList4.get(k).equals(manager.student.lec.get(l).lname) == true) {
								index = l;
								isDuplicate = true;
								break;
							}
						}
						if (isDuplicate) {
							if (manager.student.lec.get(index).lgrade != null) {
								char[] grade1 = manager.student.lec.get(index).lgrade.toCharArray();
								char[] grade2 = resultList2.get(k).toCharArray();
								if (grade1[0] < grade2[0]) {
									continue;
								} else if (grade1[0] > grade2[0]) {
									manager.student.lec.remove(index);
								} else {
									if (grade1.length >= grade2.length) {
										continue;
									} else {
										manager.student.lec.remove(index);
									}
								}
							}
						}
					}
					manager.student.lec.add(new Lecture(resultList4.get(k), resultList1.get(k),
							Integer.parseInt(resultList3.get(k)), resultList2.get(k)));
				}
				manager.calcredit();
				showResult(0);
				card.show(mainContainer, "4");
			}
		};
		bt3.addActionListener(listener3);

		card.show(mainContainer, "3");
	}

}