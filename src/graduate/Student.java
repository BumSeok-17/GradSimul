package graduate;

import java.util.ArrayList;

public class Student {
   String sno;
   String sname;
   ArrayList<Lecture> lec;
   int mrgrade = 0; //���� �������
   int msgrade=0; //����
   int rrgrade=0; //����
   int[] rbgrade = new int[5]; //�ⱳ(0:�۾���, 1:�ܱ���, 2:��â��, 3:�Ӱ� �� �ֳ�?
   int[] rdgrade = new int[3]; //�ɱ�(0:�۷ι�, 1:�й��Ҿ�, 2:�μ�)
   boolean ispass = false; // �ⱳ-�ܱ��� ���� �н� ���� ���� ����(����)
   String semester; //�б� ����
   int others; //�ϼ� ����
   ArrayList<String> simul_name;
   
   public Student() {
      lec = new ArrayList<Lecture>();
      simul_name=new ArrayList<String>();
   }
}