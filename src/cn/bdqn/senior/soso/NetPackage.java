package cn.bdqn.senior.soso;

public class NetPackage extends ServicePackage{
	public int flow;
	
	public NetPackage() {
		name="网虫套餐";
		flow=3;//流量
		price=68;//套餐费用
	}

	@Override
	public void showInfo() {
		System.out.println("网虫套餐：上网流量为3G/月");
		
	}

}
