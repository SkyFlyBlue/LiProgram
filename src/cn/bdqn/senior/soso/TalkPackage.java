package cn.bdqn.senior.soso;

public class TalkPackage extends ServicePackage{
	public int talkTime;	//ͨ��ʱ��
	public int smsCount;	//��������
	
	public TalkPackage() {
		name="�����ײ�";
		price=58;
		talkTime = 500;	
		smsCount = 30;
	}
	
	public void showInfo() {
		System.out.println("�����ײͣ�ͨ��ʱ��Ϊ500����/�£���������Ϊ30��/��");
	}
}
