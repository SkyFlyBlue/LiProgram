package cn.bdqn.senior.soso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class UserLogin {
	Scanner input=new Scanner(System.in);
	PreservationXML px=new PreservationXML();
	String number=null;
	public boolean LoiginPut(){
		boolean flag=false;
		System.out.println("******�û���½����******");
		if(login()){
			return false;
		}
		System.out.print("���������루����0������һ�㣩��");
		do{
			Scanner input=new Scanner(System.in);
			String pass=input.next();
			if(pass.equals("0"))
				return false;
			if(px.lookUpLabel(number,"password").equals(pass)){
				flag=true;
			}else
				System.out.println("����������벻��ȷ�����������룺");
		}while(!flag);
		return flag;
	}
	public void twoLevelMenu(){
		boolean flag=true;
		do{
		Scanner input=new Scanner(System.in);
		System.out.println("***********************���ƶ��û��˵�***********************\n1.�����˵���ѯ\n2.�ײ�������ѯ\n3.��ӡ�����굥" +
				"\n4.�ײͱ��\n5.��������");
		System.out.println("��ѡ������1~5ѡ���ܣ�������������һ������");
		String i=input.next();
		switch (i) {
		case "1":
			Bill();						//�˵���ѯ��
			break;
		case "2":
			System.out.println("****�ײ�������ѯ****");
			System.out.println("���Ŀ����ǣ�"+number+",�ײ���ʣ�ࣺ\nͨ��ʱ����"+transformation(number, "talkTime")+
					"����\n����������"+transformation(number, "smsCount")+"��\n����������"+transformation(number, "flow")+"GB");
			break;
		case "3":
			System.out.println("****��ӡ�����굥****");
			if(transformation(number,"spendBill")==""||px.lookUpLabel(number, "spendBill")==null)
				System.out.println("�Բ��𣬲����ڴ˺�������Ѽ�¼�����ܴ�ӡ��");
			else{
				String[] a=spendBillchai();
				for(int z=0;z<a.length;z++)
					System.out.println(a[z]);
			}
			break;
		case "4":
			System.out.println("****�ײͱ��****");
			System.out.println(changePackage(number));
			break;
		case "5":
			System.out.println("****��������****");
			px.delCard(number);
			return;
		default:
			return;
		}}while(flag);
	}
	public void Bill(){//�˵�����
		System.out.println("****�����˵���ѯ****");
		System.out.println("���Ŀ��ţ�"+number+",�����˵���");
		String packagename= px.lookUpLabel(number, "package");	//�ó��ײ���
		String price=px.lookUpLabel(number, "price");			//�ó�����ֵ
		String surplus=px.lookUpLabel(number, "money");			//�ó�ʣ���Ǯ��(Stringֵ)
		double spend=0;
		try {
			spend=Double.parseDouble(price);					//ת��Ϊdoubleֵ
			} catch (Exception e) {
			spend=0;
		}
		double packageMoney=0;
		if(packagename.equals("�����ײ�")){
			packageMoney=58;
		}else if(packagename.equals("�����ײ�")){
			packageMoney=68;
		}else if(packagename.equals("�����ײ�")){
			packageMoney=78;}
		System.out.println("�ײ��ʷѣ�"+packageMoney+"Ԫ\n�ϼƣ�"+(packageMoney+spend)+"Ԫ\n�˻���"+surplus);
		
	}
	public String transformation(String number,String Label){				//ת��
		String a= px.lookUpLabel(number, Label);
		if(px.lookUpLabel(number, Label)==null||a==null)
			a="0";
		return a;
	}
	public String changePackage(String number){//������һ��֮���ٴθ�������ԭ�ȵ��ײ���һ��  
		int i=0;
		System.out.print("1.�����ײ�\t2.�����ײ�\t3.�����ײͣ���ţ�");
		do {
			try {
				i=input.nextInt();
			} catch (Exception e) {
				System.out.print("�����ʽ�������������룺");
			}finally{
				if(i>3||i<1)
					System.out.println("û�и�ѡ����������룺");
			}
		} while (i<1||i>3);
		ServicePackage holder=SosoMgr.serPa.get(i-1);		//ѡ����ײ�
		String b=px.lookUpLabel(number, "package");		//�����û��ײ���
		if(holder.name.equals(b))
			return "�Բ������Ѿ��Ǹ��ײ��û������軻�ײͣ�";
		else{
			if(px.replaceLabel(number, holder))			//��֤����Ƿ��㹻�����ײ͡�
			{
				return "�������㣬���ֵ��";				
			}else
				return "�����ɹ�";
		}
	}
	public boolean login(){								//��֤�û�
		boolean flag=true;
		do{
			px=new PreservationXML();
			System.out.print("�������ֻ����ţ�����0������һ�㣩��");
			try{
			Scanner input=new Scanner(System.in);
			number=input.next();
			if(number.equals("0"))
				return true;
			else if(number.length()==11){
				if(px.lookUpNumber(number)){
					flag=false;}
				else
					System.out.print("��������ֻ�����δע�ᣬ��Ҫ��ȥע�ᣡ");
				}
			else
				System.out.print("������Ĳ����ֻ���,");
			}catch(Exception e){
			System.out.println("�����ʽ����");}
		}while(flag);
		return flag;
	}
	public void playso(){
		Random random=new Random();
		int ranNum=0;
		StringBuffer[] playType=new StringBuffer[]{new StringBuffer("�ʺ�ͻ���˭֪������Ѳ���ͨ��90���ӡ�"),new StringBuffer("ѯ�������������������ͨ��30���ӡ�"),
				new StringBuffer("���뻷������ʵʩ�����ʾ���飬���Ͷ���5����"),new StringBuffer("֪ͨ�����ֻ����ţ����Ͷ���50����"),new StringBuffer("��Ů������΢����Ƶ���죬ʹ������1GB��")
				,new StringBuffer("�����ֻ����߿����磬������˯���ˣ�ʹ������2GB��")};
		if(login()){
			return;}
			double money=new Double(px.lookUpLabel(number, "money"));//�˻����
			ranNum=random.nextInt(5);		//�����
			int timeplay=0;					//��������
			px=new PreservationXML();
			switch(ranNum){
				case 0:
					timeplay=90;
					talkTimeTwo(money, timeplay, playType[0],"talkTime");
					break;
				case 1:
					timeplay=30;
					talkTimeTwo(money, timeplay, playType[1],"talkTime");
					break;
				case 2:
					timeplay=5;
					talkTimeTwo(money, timeplay, playType[2], "smsCount");
					break;
				case 3:
					timeplay=50;
					talkTimeTwo(money, timeplay, playType[3], "smsCount");
					break;
				case 4:
					timeplay=1000;
					talkTimeTwo(money, timeplay, playType[4], "flow");
					break;
				case 5:
					timeplay=2000;
					talkTimeTwo(money, timeplay, playType[5], "flow");
					break;
			}
		
	}
	/**
	 * 
	 * @param money	ʣ���Ǯ��
	 * @param timeplay	���ݵ���ֵ����Ҫ���ѵĶ��š�ͨ��ʱ�����������ֵ��
	 * @param playType	��Ҫ�洢�����Ѽ�¼
	 * @param type	��ǩ�ж�
	 */
	public void talkTimeTwo(double money,int timeplay,StringBuffer playType,String type){
		if(px.lookUpLabel(number,type)==null){				//�ײ���û�а����������
			talkTimePan(money, timeplay, playType,type);
		}else{
			int typeValue=new Integer(px.lookUpLabel(number, type));
			if(type.equals("flow")){
				typeValue*=1000;
			}
			if(typeValue>=timeplay){
				if(type.equals("flow"))
					px.setLabel(number, type, new Integer((typeValue-timeplay)/1000).toString());
				else
					px.setLabel(number, type, new Integer(typeValue-timeplay).toString());
				if(px.lookUpLabel(number, "spendBill")!=null){
						px.addSpend(number,"spendBill",px.lookUpLabel(number, "spendBill")+playType.toString());
				}else{
					px.addSpend(number,"spendBill",playType.toString());
					}
				System.out.println(playType.toString());
			}
			else
				talkTimePan(money,timeplay-typeValue, playType,type);
		}
	}
	public int talkTimePlay(double money,int timeplay,double value){ //�ж�ʣ���Ǯ����������
		int i=0;
		for (; i<=timeplay ; i++) {					//ѭ���ж�ʣ���Ǯ�����Ѷ���
			if(money-i*value<=0)
				break;
		}
		return i;
	}
	/**
	 * 
	 * @param money	���ݵ�Ǯ��
	 * @param timeplay	���ݵ�ͨ��ʱ�䣬���߶���������������������
	 * @param playType	���ݵ����Ѽ�¼��
	 * @param type	���ݵ�timeplay������
	 */
	public void talkTimePan(double money,int timeplay,StringBuffer playType,String type){//�ж�����Ƿ��㹻����
		double value=0;
		String a=null;
		String b=null;
		if(type.equals("talkTime")){
			value=0.2;a="����ֻ��ͨ��:"	;b="����";}
		else if(type.equals("smsCount")){
			value=0.1;a="����ֻ�ܷ��Ͷ���:"	;b="��";}
		else if(type.equals("flow")){
			value=0.1;a="����ֻ����������:";b="MB";}
		if(talkTimePlay(money,timeplay,value)<timeplay){		//���������֧��ʹ��
			StringBuffer gu=null;
			try{
				gu=new StringBuffer(playType.substring(0, playType.length()-1)+"������"+a+talkTimePlay(money, timeplay,value)+b+"��");
				px.addSpend(number,"spendBill",px.lookUpLabel(number, "spendBill")+"\n"+gu.toString());				//��XML��������Ѽ�¼
				px.Recharge(number, 0);							//�޸����Ϊ0
				px.addSpend(number, "price",new Double(new Double(px.lookUpLabel(number, "price"))+talkTimePlay(money,timeplay,value)*value).toString());
				px.setLabel(number, type, "0");					//�޸��ײ��е��������Ϊ0
				throw new MyException();
			}catch(Exception e){
				e.printStackTrace();
				System.out.println(e.getMessage());
				System.out.println("��������Ҫ��ֵ�Ľ�����0�˳���");
				input=new Scanner(System.in);
				int xuanze=input.nextInt();
				if(xuanze==0)
					return;
				else
				{
					money=new Double(px.lookUpLabel(number, "money"))+xuanze;
					System.out.println(number);
					px.addSpend(number, "money",new Double(money).toString());
					System.out.println(px.lookUpLabel(number, "money"));
					System.out.println("�������˻��У�"+money+"Ԫ");
				}
			}
		}else{													//����ʣ��
			money-=timeplay*value;								//������
			double price=0;
			if(px.lookUpLabel(number, "price")==null)
				price=0;
			else
				price=new Double(px.lookUpLabel(number, "price"));
			px.addSpend(number, "price",new Double(price+timeplay*value).toString());
			px.addSpend(number,"spendBill",px.lookUpLabel(number, "spendBill")+playType.toString());			//������Ѽ�¼
			px.Recharge(number, money);							//�޸�XML�е����
			px.setLabel(number, type, "0");//�޸��ײ�ʣ��ֵ
			System.out.println(playType.toString());
		}
	}
	public String[] spendBillchai(){
		String a=px.lookUpLabel(number, "spendBill");
		String[] b=new String[a.length()];
		b=a.split("��");
		return b;
	}
}
class MyException extends Exception{
	MyException(){
		super("�������㣬���ֵ����ʹ�ã�");
	}
}




