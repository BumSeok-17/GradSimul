package graduate;

public class Lecture {
	String lname; // ���Ǹ�
	String ltype; // �̼� ����
	int lcredit; // �̼� ����
	String semester; // �����б�
	String lgrade;
	String front;
	String prof;
	int session=0;
	int e_semester;
	String sgrade;

	public Lecture(String lname, String ltype, int lcredit, String lgrade) {
		this.lname = lname;
		this.ltype = ltype;
		this.lcredit = lcredit;
		this.semester = null;
		this.lgrade = lgrade;
		this.front = null;
		this.prof=null;
		this.session=0;
		this.e_semester =0;
	}
	
	public Lecture(String lname, String ltype, int lcredit, String lgrade, int e_semester) {
		this.lname = lname;
		this.ltype = ltype;
		this.lcredit = lcredit;
		this.semester = null;
		this.lgrade = lgrade;
		this.front = null;
		this.prof=null;
		this.session=0;
		this.e_semester = e_semester;
	}

	public Lecture(String lname, String ltype, int lcredit, String semester, String front, String prof, int session, String g) {
		this.lname = lname;
		this.ltype = ltype;
		this.lcredit = lcredit;
		this.semester = semester;
		this.lgrade = null;
		this.front = front;
		this.prof=prof;
		this.session = session;
		this.e_semester = 0;
		this.sgrade=g;
	}
}