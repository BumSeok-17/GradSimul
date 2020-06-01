package graduate;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Manager {

	public int errorNum;

	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	Student student;

	public Manager() {
		connectDB();
		loadClass();

	}

	public boolean checkUser(String sname, String sno, String semester) {
		String sql = "select * from student where sno=? and sname=?";
		boolean isUser = false;
		student = new Student();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "2017" + sno);
			pstmt.setString(2, sname);
			rs = pstmt.executeQuery();
			if (rs.isBeforeFirst()) {
				rs.next();
				isUser = true;
				student.sname = rs.getString(1);
				student.sno = rs.getString(2);
				student.rdgrade[0] = Integer.parseInt(rs.getString(3));
				student.rdgrade[1] = Integer.parseInt(rs.getString(4));
				student.rdgrade[2] = Integer.parseInt(rs.getString(5));
				student.others = Integer.parseInt(rs.getString(6));
				if (rs.getInt(7) == 1)
					student.ispass = true;
				else
					student.ispass = false;
				initStudent();
				sql = "select * from enrol where sno=? group by simul_name";
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, "2017" + sno);
					rs = pstmt.executeQuery();
					if (rs.isBeforeFirst()) {
						while (rs.next()) {
							if(rs.getString(6)!=null)
							student.simul_name.add(rs.getString(6));
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				sql = "insert into student values(?, ?, ?, ?, ?, ?, ?)";
				try {
					pstmt = conn.prepareStatement(sql);
					pstmt.setString(1, sname);
					pstmt.setString(2, "2017" + sno);
					pstmt.setString(3, "0");
					pstmt.setString(4, "0");
					pstmt.setString(5, "0");
					pstmt.setString(6, "0");
					pstmt.setString(7, "0");
					pstmt.executeUpdate();
					student.sname = sname;
					student.sno = "2017" + sno;
					student.rdgrade[0] = 0;
					student.rdgrade[1] = 0;
					student.rdgrade[2] = 0;
					student.others = 0;
					student.ispass = false;

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			student.semester = semester;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return isUser;

	}

	public boolean checkInput(String sname, String snum) {
		// 잘못된 입력값인지
		if (sname.contains(" ") || !sname.matches("[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힝]*") || sname.length() == 0) {
			// 특수문자가 있거나, 2개이상 공백있는가?
			errorNum = 1;
			return false;
		} else if (snum.contains(" ") || snum.length() != 5 || !isDigit(snum)) {
			errorNum = 2;
			return false;
		} // 0: 오류 없음 ,1: 이름 ,2 : 학번 오류
		errorNum = 0;
		return true;
	}

	public boolean isDigit(String snum) {
		for (int i = 0; i < snum.length(); i++) {
			char c = snum.charAt(i);
			if (c < 48 || c > 57) {// 숫자가 아닌 경우
				return false;
			}
		}
		return true;
	}

	public void connectDB() {
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mariadb://127.0.0.1:827/graduation", "root", "0827");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void closeDB() {
		try {
			pstmt.close();
			conn.close();
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void initStudent() {
		String sql = "select * from enrol where sno=? and simul_name=null";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, student.sno);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				String lname = rs.getString(2);
				int lcredit = Integer.parseInt(rs.getString(3));
				String ltype = rs.getString(4);
				String lgrade = rs.getString(5);
				student.lec.add(new Lecture(lname, ltype, lcredit, lgrade));
				switch (rs.getString(4)) {
				case "전필":
					student.mrgrade += rs.getInt(3);
					break;
				case "전선":
					student.msgrade += rs.getInt(3);
					break;
				case "지교":
					student.rrgrade += rs.getInt(3);
					break;
				case "기교(글쓰기)":
					student.rbgrade[0] += rs.getInt(3);
					break;
				case "기교(외국어)":
					student.rbgrade[1] += rs.getInt(3);
					break;
				case "기교(취창업)":
					student.rbgrade[2] += rs.getInt(3);
					break;
				case "기교(S/W)":
					student.rbgrade[3] += rs.getInt(3);
					break;
				case "기교(인성)":
					student.rbgrade[4] += rs.getInt(3);
					break;
				case "심교(글로벌인재양성)":
					student.rdgrade[0] += rs.getInt(3);
					break;
				case "심교(학문소양및인성함양)":
					student.rdgrade[1] += rs.getInt(3);
					break;
				case "심교(사고력증진)":
					student.rdgrade[2] += rs.getInt(3);
					break;
				case "일선":
					student.others += rs.getInt(3);
					break;

				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void SaveClass(String sno, String lname, String credit, String lcategory, String lgrade) {
		String sql = "insert into enrol values(?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sno);
			pstmt.setString(2, lname);
			pstmt.setString(3, credit);
			pstmt.setString(4, lcategory);
			pstmt.setString(5, lgrade);
			pstmt.setString(6, null);
			pstmt.setString(7, null);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void SaveSimul(String sno, String lname, String credit, String lcategory, String lgrade, String simul_name,
			int session) {
		String sql = "insert into enrol values(?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, sno);
			pstmt.setString(2, lname);
			pstmt.setString(3, credit);
			pstmt.setString(4, lcategory);
			pstmt.setString(5, lgrade);
			pstmt.setString(6, simul_name);
			pstmt.setInt(7, session);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String[] bringlec(String type) {
		String sql = "select * from lecture where lcategory=? group by lname";
		ArrayList<String> blec = new ArrayList<String>();
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, type);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				blec.add(rs.getString(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] str = blec.toArray(new String[blec.size()]);
		return str;
	}

	public ArrayList<Lecture> loadClass() {
		ArrayList<Lecture> lec = new ArrayList<Lecture>();
		String sql = "select * from lecture";
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				String lname = rs.getString(1);
				String ltype = rs.getString(2);
				int lcredit = Integer.parseInt(rs.getString(3));
				String semester = null;
				String front = null;
				String prof = "미배정";
				int session = 0;
				String g = null;
				if (rs.getString(4) != null) {
					semester = rs.getString(4);
				}
				if (rs.getString(5) != null) {
					front = rs.getString(5);
				}
				if (rs.getString(6) != null) {
					prof = rs.getString(6);
				}
				if (rs.getString(7) != null) {
					session = rs.getInt(7);
				}
				if (rs.getString(8) != null) {
					g = rs.getString(8);
				}
				lec.add(new Lecture(lname, ltype, lcredit, semester, front, prof, session, g));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return lec;
	}

	boolean flag = false;

	public boolean findenrol(String lname) {
		for (int i = 0; i < student.lec.size(); i++) {
			if (student.lec.get(i).lname.equals(lname)) {
				if (student.lec.get(i).lgrade.equals("A+") || student.lec.get(i).lgrade.equals("A")
						|| student.lec.get(i).lgrade.equals("B+") || student.lec.get(i).lgrade.equals("B")) {
					flag = true;
					return false;
				}
			}
		}
		return true;
	}

	public void deleteEnrol() {
		String sql = "delete from enrol where sno=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, student.sno);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void studentupdate() {
		connectDB();
		String sql = "delete from student where sno=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, student.sno);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql = "insert into student values(?, ?, ?, ?, ?, ?, ?)";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, student.sname);
			pstmt.setString(2, student.sno);
			pstmt.setInt(3, student.rdgrade[0]);
			pstmt.setInt(4, student.rdgrade[1]);
			pstmt.setInt(5, student.rdgrade[2]);
			pstmt.setInt(6, student.others);
			if (student.ispass == true) {
				pstmt.setString(7, "1");
			} else {
				pstmt.setString(7, "0");
			}
			pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("[SQL Error : " + e.getMessage() + "]");
			e.printStackTrace();
		}
	}

	public void initCredit() {
		student.mrgrade = 0;
		student.msgrade = 0;
		student.rrgrade = 0;
		student.rbgrade[0] = 0;
		student.rbgrade[1] = 0;
		student.rbgrade[2] = 0;
		student.rbgrade[3] = 0;
		student.rbgrade[4] = 0;
		student.rdgrade[0] = 0;
		student.rdgrade[1] = 0;
		student.rdgrade[2] = 0;
		student.others = 0;
	}

	public void calcredit() {
		initCredit();
		for (int i = 0; i < student.lec.size(); i++) {
			if (student.lec.get(i).lgrade.equals("F") == false && student.lec.get(i).lgrade.equals("NP") == false) {
				switch (student.lec.get(i).ltype) {
				case "전필":
					student.mrgrade += student.lec.get(i).lcredit;
					break;
				case "전선":
					student.msgrade += student.lec.get(i).lcredit;
					break;
				case "지교":
					student.rrgrade += student.lec.get(i).lcredit;
					break;
				case "기교(글쓰기)":
					student.rbgrade[0] += student.lec.get(i).lcredit;
					break;
				case "기교(외국어)":
					student.rbgrade[1] += student.lec.get(i).lcredit;
					break;
				case "기교(취창업)":
					student.rbgrade[2] += student.lec.get(i).lcredit;
					break;
				case "기교(S/W)":
					student.rbgrade[3] += student.lec.get(i).lcredit;
					break;
				case "기교(인성)":
					student.rbgrade[4] += student.lec.get(i).lcredit;
					break;
				case "심교(글로벌인재양성)":
					student.rdgrade[0] += student.lec.get(i).lcredit;
					break;
				case "심교(학문소양및인성함양)":
					student.rdgrade[1] += student.lec.get(i).lcredit;
					break;
				case "심교(사고력증진)":
					student.rdgrade[2] += student.lec.get(i).lcredit;
					break;
				case "일선":
					student.others += student.lec.get(i).lcredit;
					break;
				}
			}
		}
	}

	public boolean Graduation(Student stu) {
		int[] lecturearray = new int[12];
		int a = 0;// 총학점
		int b = 0;// 교양 과목
		int c = 0;// 지교
		int d = 0;// 전공
		int e = 0;// 기교 확인 변수
		int f = 0;// 필수 이수 과목 확인
		for (int i = 0; i < stu.lec.size(); i++) {
			switch (stu.lec.get(i).ltype) {
			case "전필":
				lecturearray[0] += stu.lec.get(i).lcredit;
				break;
			case "전선":
				lecturearray[1] += stu.lec.get(i).lcredit;
				break;
			case "지교":
				lecturearray[2] += stu.lec.get(i).lcredit;
				break;
			case "기교(글쓰기)":
				lecturearray[3] += stu.lec.get(i).lcredit;
				break;
			case "기교(외국어)":
				lecturearray[4] += stu.lec.get(i).lcredit;
				break;
			case "기교(취창업)":
				lecturearray[5] += stu.lec.get(i).lcredit;
				break;
			case "기교(S/W)":
				lecturearray[6] += stu.lec.get(i).lcredit;
				break;
			case "기교(인성)":
				lecturearray[7] += stu.lec.get(i).lcredit;
				break;
			case "심교(글로벌인재양성)":
				lecturearray[8] += stu.lec.get(i).lcredit;
				break;
			case "심교(학문소양및인성함양)":
				lecturearray[9] += stu.lec.get(i).lcredit;
				break;
			case "심교(사고력증진)":
				lecturearray[10] += stu.lec.get(i).lcredit;
				break;
			case "일선":
				lecturearray[11] += stu.lec.get(i).lcredit;
				break;
			}
		}
		for (int i = 0; i < 12; i++) {
			a += lecturearray[i];
		}
		for (int i = 2; i < 11; i++) {
			b += lecturearray[i];
		}
		c = lecturearray[2];
		d = lecturearray[0] + lecturearray[1];
		e = lecturearray[3] * lecturearray[5] * lecturearray[6] * lecturearray[7];
		if (a > 131 || b > 24 || c > 17 || d > 79 || e != 0) {// 총학점, 교양, 지교, 전공, 외국어를 제외한 기교 요구학점을 모두 채웠을 경우
			for (int i = 0; i < stu.lec.size(); i++) {
				if (stu.lec.get(i).lname.equals("물리학및실험1") || stu.lec.get(i).lname.equals("일반화학및실험1"))
					f++;
				else if (stu.lec.get(i).lname.equals("수학및연습1"))
					f++;
				else if (stu.lec.get(i).lname.equals("전산수학"))
					f++;
				else if (stu.lec.get(i).lname.equals("공학수학"))
					f++;
				else if (stu.lec.get(i).lname.equals("선형대수"))
					f++;
				else if (stu.lec.get(i).lname.equals("확률과통계") || stu.lec.get(i).lname.equals("수치해석"))
					f++;
				else if (stu.lec.get(i).lname.equals("졸업프로젝트1(종합설계)"))
					f++;
				else if (stu.lec.get(i).lname.equals("졸업프로젝트2(종합설계)"))
					f++;
				else if (stu.lec.get(i).lname.equals("전공기초프로젝트1(종합설계)"))
					f++;
				else if (stu.lec.get(i).lname.equals("전공기초프로젝트2(종합설계)"))
					f++;
				else if (stu.lec.get(i).lname.equals("산학협력프로젝트1(종합설계)")
						|| stu.lec.get(i).lname.equals("K-Lab프로젝트1(종합설계)"))
					f++;
				else if (stu.lec.get(i).lname.equals("산학협력프로젝트2(종합설계)"))
					f++;
				else if (stu.lec.get(i).lname.equals("시스템프로그래밍") || stu.lec.get(i).lname.equals("운영체제"))
					f++;
				else if (stu.lec.get(i).lname.equals("컴퓨터구조") || stu.lec.get(i).lname.equals("임베디드컴퓨팅"))
					f++;
			}
			if (f == 14) {// 14개의 필수 과목을 모두 이수하였을 경우
				if (stu.ispass)// 외국어 이수의무 면제 학생의 경우
					return true;
				else {// 외국어 이수의무 면제 학생이 아닌 경우
					if (lecturearray[4] > 0)
						return true;
				}
			}
		}
		return false;
	}

	public float calculate(Student stu) {
		float[] credit = new float[100];
		float[] grade = new float[100];

		int i = 0;
		float k = 0;
		float l = 0;
		for (int flag = 0; flag < stu.lec.size(); flag++) {

			switch (stu.lec.get(flag).lgrade) {
			case "A+":
				grade[i] = (float) 4.5;
				credit[i] = stu.lec.get(flag).lcredit;
				break;
			case "A":
				grade[i] = (float) 4.0;
				credit[i] = stu.lec.get(flag).lcredit;
				break;
			case "B+":
				grade[i] = (float) 3.5;
				credit[i] = stu.lec.get(flag).lcredit;
				break;
			case "B":
				grade[i] = (float) 3.0;
				credit[i] = stu.lec.get(flag).lcredit;
				break;
			case "C+":
				grade[i] = (float) 2.5;
				credit[i] = stu.lec.get(flag).lcredit;
				break;
			case "C":
				grade[i] = (float) 2.0;
				credit[i] = stu.lec.get(flag).lcredit;
				break;
			case "D+":
				grade[i] = (float) 1.5;
				credit[i] = stu.lec.get(flag).lcredit;
				break;
			case "D":
				grade[i] = (float) 1.0;
				credit[i] = stu.lec.get(flag).lcredit;
				break;
			case "F":
				grade[i] = (float) 0.0;
				credit[i] = stu.lec.get(flag).lcredit;
				break;
			case "P":
				break;
			case "NP":
				break;
			case "이수중":
				grade[i] = (float) 4.5;
				credit[i] = stu.lec.get(flag).lcredit;
				break;
			}
			i++;
		}
		for (int j = 0; j < i; j++) {
			k = k + (grade[j]) * (credit[j]);
			l = l + credit[j];
		}
		return (float) (Math.round(k / l * 100) / 100.00);
	}

	public ArrayList<String> bringSimul(String simul) {
		ArrayList<String> simul_lec = new ArrayList<String>();
		String sql = "select * from enrol where sno=? and simul_name=?";
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, student.sno);
			pstmt.setString(2, simul);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				simul_lec.add(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return simul_lec;
	}
}
