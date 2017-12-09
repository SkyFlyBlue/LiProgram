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
		System.out.println("******用户登陆界面******");
		if(login()){
			return false;
		}
		System.out.print("请输入密码（输入0返回上一层）：");
		do{
			Scanner input=new Scanner(System.in);
			String pass=input.next();
			if(pass.equals("0"))
				return false;
			if(px.lookUpLabel(number,"password").equals(pass)){
				flag=true;
			}else
				System.out.println("您输入的密码不正确，请重新输入：");
		}while(!flag);
		return flag;
	}
	public void twoLevelMenu(){
		boolean flag=true;
		do{
		Scanner input=new Scanner(System.in);
		System.out.println("***********************嗖嗖移动用户菜单***********************\n1.本月账单查询\n2.套餐余量查询\n3.打印消费详单" +
				"\n4.套餐变更\n5.办理退网");
		System.out.println("请选择（输入1~5选择功能，其他键返回上一级）：");
		String i=input.next();
		switch (i) {
		case "1":
			Bill();						//账单查询。
			break;
		case "2":
			System.out.println("****套餐余量查询****");
			System.out.println("您的卡号是："+number+",套餐内剩余：\n通话时长："+transformation(number, "talkTime")+
					"分钟\n短信条数："+transformation(number, "smsCount")+"条\n上网流量："+transformation(number, "flow")+"GB");
			break;
		case "3":
			System.out.println("****打印消费详单****");
			if(transformation(number,"spendBill")==""||px.lookUpLabel(number, "spendBill")==null)
				System.out.println("对不起，不存在此号码的消费记录，不能打印！");
			else{
				String[] a=spendBillchai();
				for(int z=0;z<a.length;z++)
					System.out.println(a[z]);
			}
			break;
		case "4":
			System.out.println("****套餐变更****");
			System.out.println(changePackage(number));
			break;
		case "5":
			System.out.println("****办理退网****");
			px.delCard(number);
			return;
		default:
			return;
		}}while(flag);
	}
	public void Bill(){//账单方法
		System.out.println("****本月账单查询****");
		System.out.println("您的卡号："+number+",当月账单：");
		String packagename= px.lookUpLabel(number, "package");	//得出套餐名
		String price=px.lookUpLabel(number, "price");			//得出消费值
		String surplus=px.lookUpLabel(number, "money");			//得出剩余的钱数(String值)
		double spend=0;
		try {
			spend=Double.parseDouble(price);					//转换为double值
			} catch (Exception e) {
			spend=0;
		}
		double packageMoney=0;
		if(packagename.equals("话痨套餐")){
			packageMoney=58;
		}else if(packagename.equals("网虫套餐")){
			packageMoney=68;
		}else if(packagename.equals("超人套餐")){
			packageMoney=78;}
		System.out.println("套餐资费："+packageMoney+"元\n合计："+(packageMoney+spend)+"元\n账户余额："+surplus);
		
	}
	public String transformation(String number,String Label){				//转换
		String a= px.lookUpLabel(number, Label);
		if(px.lookUpLabel(number, Label)==null||a==null)
			a="0";
		return a;
	}
	public String changePackage(String number){//更换过一次之后，再次更换和最原先的套餐名一样  
		int i=0;
		System.out.print("1.话痨套餐\t2.网虫套餐\t3.超人套餐（序号）");
		do {
			try {
				i=input.nextInt();
			} catch (Exception e) {
				System.out.print("输入格式错误，请重新输入：");
			}finally{
				if(i>3||i<1)
					System.out.println("没有该选项，请重新输入：");
			}
		} while (i<1||i>3);
		ServicePackage holder=SosoMgr.serPa.get(i-1);		//选择的套餐
		String b=px.lookUpLabel(number, "package");		//输入用户套餐名
		if(holder.name.equals(b))
			return "对不起，您已经是该套餐用户，无需换套餐！";
		else{
			if(px.replaceLabel(number, holder))			//验证余额是否足够更换套餐。
			{
				return "您的余额不足，请充值！";				
			}else
				return "更换成功";
		}
	}
	public boolean login(){								//验证用户
		boolean flag=true;
		do{
			px=new PreservationXML();
			System.out.print("请输入手机卡号（输入0返回上一层）：");
			try{
			Scanner input=new Scanner(System.in);
			number=input.next();
			if(number.equals("0"))
				return true;
			else if(number.length()==11){
				if(px.lookUpNumber(number)){
					flag=false;}
				else
					System.out.print("您输入的手机号尚未注册，需要您去注册！");
				}
			else
				System.out.print("您输入的不是手机号,");
			}catch(Exception e){
			System.out.println("输入格式错误！");}
		}while(flag);
		return flag;
	}
	public void playso(){
		Random random=new Random();
		int ranNum=0;
		StringBuffer[] playType=new StringBuffer[]{new StringBuffer("问候客户，谁知其如此难缠，通话90分钟。"),new StringBuffer("询问妈妈身体情况，本地通话30分钟。"),
				new StringBuffer("参与环境保护实施方案问卷调查，发送短信5条。"),new StringBuffer("通知朋友手机换号，发送短信50条。"),new StringBuffer("和女朋友用微信视频聊天，使用流量1GB。")
				,new StringBuffer("晚上手机在线看韩剧，不留神睡着了！使用流量2GB。")};
		if(login()){
			return;}
			double money=new Double(px.lookUpLabel(number, "money"));//账户余额
			ranNum=random.nextInt(5);		//随机数
			int timeplay=0;					//消费用量
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
	 * @param money	剩余的钱数
	 * @param timeplay	传递的数值（需要消费的短信、通话时间或着流量的值）
	 * @param playType	需要存储的消费记录
	 * @param type	标签判断
	 */
	public void talkTimeTwo(double money,int timeplay,StringBuffer playType,String type){
		if(px.lookUpLabel(number,type)==null){				//套餐中没有包括这个类型
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
	public int talkTimePlay(double money,int timeplay,double value){ //判断剩余的钱够不够消费
		int i=0;
		for (; i<=timeplay ; i++) {					//循环判断剩余的钱能消费多少
			if(money-i*value<=0)
				break;
		}
		return i;
	}
	/**
	 * 
	 * @param money	传递的钱数
	 * @param timeplay	传递的通话时间，或者短信条数，或者数据流量
	 * @param playType	传递的消费记录。
	 * @param type	传递的timeplay的类型
	 */
	public void talkTimePan(double money,int timeplay,StringBuffer playType,String type){//判断余额是否足够消费
		double value=0;
		String a=null;
		String b=null;
		if(type.equals("talkTime")){
			value=0.2;a="本次只能通话:"	;b="分钟";}
		else if(type.equals("smsCount")){
			value=0.1;a="本次只能发送短信:"	;b="条";}
		else if(type.equals("flow")){
			value=0.1;a="本次只能消费流量:";b="MB";}
		if(talkTimePlay(money,timeplay,value)<timeplay){		//如果余额不足以支撑使用
			StringBuffer gu=null;
			try{
				gu=new StringBuffer(playType.substring(0, playType.length()-1)+"因余额不足"+a+talkTimePlay(money, timeplay,value)+b+"。");
				px.addSpend(number,"spendBill",px.lookUpLabel(number, "spendBill")+"\n"+gu.toString());				//在XML中添加消费记录
				px.Recharge(number, 0);							//修改余额为0
				px.addSpend(number, "price",new Double(new Double(px.lookUpLabel(number, "price"))+talkTimePlay(money,timeplay,value)*value).toString());
				px.setLabel(number, type, "0");					//修改套餐中的免费类型为0
				throw new MyException();
			}catch(Exception e){
				e.printStackTrace();
				System.out.println(e.getMessage());
				System.out.println("请输入您要充值的金额：（按0退出）");
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
					System.out.println("您现在账户有："+money+"元");
				}
			}
		}else{													//余额还有剩余
			money-=timeplay*value;								//算出余额
			double price=0;
			if(px.lookUpLabel(number, "price")==null)
				price=0;
			else
				price=new Double(px.lookUpLabel(number, "price"));
			px.addSpend(number, "price",new Double(price+timeplay*value).toString());
			px.addSpend(number,"spendBill",px.lookUpLabel(number, "spendBill")+playType.toString());			//添加消费记录
			px.Recharge(number, money);							//修改XML中的余额
			px.setLabel(number, type, "0");//修改套餐剩余值
			System.out.println(playType.toString());
		}
	}
	public String[] spendBillchai(){
		String a=px.lookUpLabel(number, "spendBill");
		String[] b=new String[a.length()];
		b=a.split("。");
		return b;
	}
}
class MyException extends Exception{
	MyException(){
		super("您的余额不足，请充值后再使用！");
	}
}




