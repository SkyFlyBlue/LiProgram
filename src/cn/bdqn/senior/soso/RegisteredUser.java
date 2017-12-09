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
		System.out.println("*********可选择的卡号*********");
		for(int i=1;i<10;i++){		//创建9个电话号码
			System.out.print(i+"."+createNumber()+"\t");
			if(i%3==0){
				System.out.print("\n");
			}
		}
		int i=0;//判断条件，如果输入的不是1~9的int值
		do{
			System.out.println("请输入1~9的序号");
			Scanner input=new Scanner(System.in);
			try{
				i=input.nextInt();}
			catch(Exception e){
				System.err.print("输入错误，");}
		}while(!(i<10&&i>0));
		number=numList.get(i-1);
		System.out.print("1.话痨套餐     2.网虫套餐      3.超人套餐,");
		i=0;
		do{
			System.out.println("请选择套餐（输入序号）：");
			Scanner input=new Scanner(System.in);
			try {
				i=input.nextInt();
				serHold=SosoMgr.serPa.get(i-1);
			} catch (Exception e) {
				System.out.print("输入错误，");
			}
		}while(i>3||i<1);
		Scanner input=new Scanner(System.in);
		System.out.print("请输入姓名：");
		name=input.next();
		System.out.print("请输入密码：");
		password=input.next();
		System.out.print("请输入预存金额：");
		do{
			input=new Scanner(System.in);
			try {
			money=input.nextDouble();
			if(money<serHold.price)
				System.out.print("您预存的话费金额不足以支付本月固定套餐资费，请重新充值：");
			} catch (Exception e) {
				System.err.print("输入错误，请重新输入：");
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
		System.out.println("注册成功！卡号："+number+"  用户名："+name+"  当前余额："+money+"元。");
		serHold.showInfo();
	}
	private String createNumber(){			//创建号码
		Random ran=new Random();			
		String num="139";					//区号
		boolean isExist=false;				//标签
		int temp=0;
		do{
			isExist=false;
			temp=ran.nextInt(90000000)+10000000;//随机一个1*10^7~1*10^8之间的一个数
			num+=temp;
			for (String numb:numList) {//如果集合中有这个重复的电话号码
				if(num==numb){
					isExist=true;		//标签：如果重复就再次随机。如果不重复，就跳出
					break;
					}
			}
			numList.add(num);
			isExist=px.lookUpNumber(num);
		}while(isExist);
		return num;
	}
}
