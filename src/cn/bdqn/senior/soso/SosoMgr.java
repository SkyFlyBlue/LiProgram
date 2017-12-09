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
		UserLogin us=new UserLogin();			//登陆实例化
		RegisteredUser ru=new RegisteredUser();	//注册实例化
		do{
		PreservationXML px=new PreservationXML();
		System.out.println("***********************欢迎使用嗖嗖移动业务大厅***********************");
		System.out.println("1.用户登陆     2.用户注册     3.使用嗖嗖     4.话费充值     5.资费说明      6.退出系统：");
		System.out.println("请输入选择：");
		int i=0;
		while(!input.hasNextInt()){
			System.out.println("输入不正确，请重新输入：");
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
				System.out.println("****账户充值****");
				boolean flage=false;
				String number=null;
				double money=0;
				do{
				System.out.print("请输入手机卡号（输入0返回上一层）：");
				input=new Scanner(System.in);
				number=input.next();
				if(number.length()==11){
					if(px.lookUpNumber(number))
						flage=true;
					else
						System.out.print("您输入的手机号尚未注册，需要您去注册！");
					}
					else
						System.out.print("您输入的不是手机号,");
				}while(!flage);
					do{
						System.out.print("请输入充值金额：");
						input=new Scanner(System.in);
						try {
							money=input.nextInt();
						} catch (Exception e) {
							System.out.print("您输入的格式不正确，");
						}
					}while(money<0);
					money+=new Double(px.lookUpLabel(number, "money"));
					System.out.println(number);
					px.addSpend(number, "money",new Double(money).toString());
					System.out.println(px.lookUpLabel(number, "money"));
					System.out.println("您现在账户有："+money+"元");
				break;
			case 5:
				System.out.println("****资费说明****");
				for (int j=0;j<3;j++) {
					ServicePackage onePackage=serPa.get(j);
					onePackage.showInfo();
				}
				break;
			case 6:
				flag=false;
				break;

			default:
				System.out.println("输入错误！重新进入移动业务大厅！");
				break;
		}
		}while(flag);
		System.out.println("感谢您的使用！");
	}

}
