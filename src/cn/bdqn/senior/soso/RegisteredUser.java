package cn.bdqn.senior.soso;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class RegisteredUser {
	List<String> numList=new ArrayList<String>();
	ServicePackage serHold=null;
	PreservationXML px=null;
	public void Registered(){
		px=new PreservationXML();
		String number="";
		String name=null;
		String password=null;
		double money=0;
		System.out.println("*********��ѡ��Ŀ���*********");
		for(int i=1;i<10;i++){		//����9���绰����
			System.out.print(i+"."+createNumber()+"\t");
			if(i%3==0){
				System.out.print("\n");
			}
		}
		int i=0;//�ж��������������Ĳ���1~9��intֵ
		do{
			System.out.println("������1~9�����");
			Scanner input=new Scanner(System.in);
			try{
				i=input.nextInt();}
			catch(Exception e){
				System.err.print("�������");}
		}while(!(i<10&&i>0));
		number=numList.get(i-1);
		System.out.print("1.�����ײ�     2.�����ײ�      3.�����ײ�,");
		i=0;
		do{
			System.out.println("��ѡ���ײͣ�������ţ���");
			Scanner input=new Scanner(System.in);
			try {
				i=input.nextInt();
				serHold=SosoMgr.serPa.get(i-1);
			} catch (Exception e) {
				System.out.print("�������");
			}
		}while(i>3||i<1);
		Scanner input=new Scanner(System.in);
		System.out.print("������������");
		name=input.next();
		System.out.print("���������룺");
		password=input.next();
		System.out.print("������Ԥ���");
		do{
			input=new Scanner(System.in);
			try {
			money=input.nextDouble();
			if(money<serHold.price)
				System.out.print("��Ԥ��Ļ��ѽ�����֧�����¹̶��ײ��ʷѣ������³�ֵ��");
			} catch (Exception e) {
				System.err.print("����������������룺");
			}
		}while(money<serHold.price);
		money=money-serHold.price;
		if(serHold instanceof TalkPackage){
			TalkPackage tap=(TalkPackage)serHold;
			px.addParents(number, name, password, money, serHold.name,tap.talkTime , tap.smsCount, 0);
		}else if(serHold instanceof NetPackage){
			NetPackage tap=(NetPackage)serHold;
			px.addParents(number, name, password, money, serHold.name,0 ,0, tap.flow);
		}else if(serHold instanceof SuperPackage){
			SuperPackage tap=(SuperPackage)serHold;
			px.addParents(number, name, password, money, serHold.name,tap.talkTime ,tap.smsCount, tap.flow);
		}
		System.out.println("ע��ɹ������ţ�"+number+"  �û�����"+name+"  ��ǰ��"+money+"Ԫ��");
		serHold.showInfo();
	}
	private String createNumber(){			//��������
		Random ran=new Random();			
		String num="139";					//����
		boolean isExist=false;				//��ǩ
		int temp=0;
		do{
			isExist=false;
			temp=ran.nextInt(90000000)+10000000;//���һ��1*10^7~1*10^8֮���һ����
			num+=temp;
			for (String numb:numList) {//���������������ظ��ĵ绰����
				if(num==numb){
					isExist=true;		//��ǩ������ظ����ٴ������������ظ���������
					break;
					}
			}
			numList.add(num);
			isExist=px.lookUpNumber(num);
		}while(isExist);
		return num;
	}
}
