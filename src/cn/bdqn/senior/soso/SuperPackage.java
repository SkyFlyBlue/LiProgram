package cn.bdqn.senior.soso;

public class SuperPackage extends ServicePackage{
	
	public int talkTime;
	public int smsCount;
	public int flow;
	
	public SuperPackage() {
		name="�����ײ�";
		talkTime = 200;
		smsCount = 50;
		flow = 1;
		price=78;
	}

	@Override
	public void showInfo() {
		System.out.println("�����ײͣ�ͨ��ʱ��Ϊ200����/�£���������Ϊ1G/�£���������Ϊ30��/��");
	}

}
