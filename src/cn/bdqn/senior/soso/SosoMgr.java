package cn.bdqn.senior.soso;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class SosoMgr {
	static List<ServicePackage> serPa=new ArrayList<ServicePackage>();
	static{
	TalkPackage talkPa=new TalkPackage();
	NetPackage netPa=new NetPackage();
	SuperPackage supPa=new SuperPackage();
	serPa.add(talkPa);
	serPa.add(netPa);
	serPa.add(supPa);}
	public static void main(String[] args) {
		
		boolean flag=true;
		Scanner input=new Scanner(System.in);
		UserLogin us=new UserLogin();			//��½ʵ����
		RegisteredUser ru=new RegisteredUser();	//ע��ʵ����
		do{
		PreservationXML px=new PreservationXML();
		System.out.println("***********************��ӭʹ�����ƶ�ҵ�����***********************");
		System.out.println("1.�û���½     2.�û�ע��     3.ʹ����     4.���ѳ�ֵ     5.�ʷ�˵��      6.�˳�ϵͳ��");
		System.out.println("������ѡ��");
		int i=0;
		while(!input.hasNextInt()){
			System.out.println("���벻��ȷ�����������룺");
			input=new Scanner(System.in);
		}
		i=input.nextInt();
		switch (i) {
			case 1:
				px=new PreservationXML();
				if(us.LoiginPut())
					us.twoLevelMenu();
				break;
			case 2:
				ru.Registered();
				break;
			case 3:
				us.playso();
				break;
			case 4:
				System.out.println("****�˻���ֵ****");
				boolean flage=false;
				String number=null;
				double money=0;
				do{
				System.out.print("�������ֻ����ţ�����0������һ�㣩��");
				input=new Scanner(System.in);
				number=input.next();
				if(number.length()==11){
					if(px.lookUpNumber(number))
						flage=true;
					else
						System.out.print("��������ֻ�����δע�ᣬ��Ҫ��ȥע�ᣡ");
					}
					else
						System.out.print("������Ĳ����ֻ���,");
				}while(!flage);
					do{
						System.out.print("�������ֵ��");
						input=new Scanner(System.in);
						try {
							money=input.nextInt();
						} catch (Exception e) {
							System.out.print("������ĸ�ʽ����ȷ��");
						}
					}while(money<0);
					money+=new Double(px.lookUpLabel(number, "money"));
					System.out.println(number);
					px.addSpend(number, "money",new Double(money).toString());
					System.out.println(px.lookUpLabel(number, "money"));
					System.out.println("�������˻��У�"+money+"Ԫ");
				break;
			case 5:
				System.out.println("****�ʷ�˵��****");
				for (int j=0;j<3;j++) {
					ServicePackage onePackage=serPa.get(j);
					onePackage.showInfo();
				}
				break;
			case 6:
				flag=false;
				break;

			default:
				System.out.println("����������½����ƶ�ҵ�������");
				break;
		}
		}while(flag);
		System.out.println("��л����ʹ�ã�");
	}

}
