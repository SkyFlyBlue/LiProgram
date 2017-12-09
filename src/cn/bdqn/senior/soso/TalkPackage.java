package cn.bdqn.senior.soso;

public class TalkPackage extends ServicePackage{
	public int talkTime;	//通话时长
	public int smsCount;	//短信条数
	
	public TalkPackage() {
		name="话痨套餐";
		price=58;
		talkTime = 500;	
		smsCount = 30;
	}
	
	public void showInfo() {
		System.out.println("话痨套餐：通话时长为500分钟/月，短信条数为30条/月");
	}
}
