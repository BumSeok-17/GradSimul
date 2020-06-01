package graduate;

import java.util.ArrayList;

public class Student {
   String sno;
   String sname;
   ArrayList<Lecture> lec;
   int mrgrade = 0; //전필 취득학점
   int msgrade=0; //전선
   int rrgrade=0; //지교
   int[] rbgrade = new int[5]; //기교(0:글쓰기, 1:외국어, 2:취창업, 3:머가 또 있냐?
   int[] rdgrade = new int[3]; //심교(0:글로벌, 1:학문소양, 2:인성)
   boolean ispass = false; // 기교-외국어 영역 패스 조건 충족 여부(토익)
   String semester; //학기 정보
   int others; //일선 학점
   ArrayList<String> simul_name;
   
   public Student() {
      lec = new ArrayList<Lecture>();
      simul_name=new ArrayList<String>();
   }
}