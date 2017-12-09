package cn.bdqn.senior.soso;

public class SuperPackage extends ServicePackage{
	
	public int talkTime;
	public int smsCount;
	public int flow;
	
	public SuperPackage() {
		name="超人套餐";
		talkTime = 200;
		smsCount = 50;
		flow = 1;
		price=78;
	}

	@Override
	public void showInfo() {
		System.out.println("超人套餐：通话时长为200分钟/月，上网流量为1G/月，短信条数为30条/月");
	}

}
