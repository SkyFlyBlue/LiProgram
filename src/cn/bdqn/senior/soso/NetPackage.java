package cn.bdqn.senior.soso;

public class NetPackage extends ServicePackage{
	public int flow;
	
	public NetPackage() {
		name="�����ײ�";
		flow=3;//����
		price=68;//�ײͷ���
	}

	@Override
	public void showInfo() {
		System.out.println("�����ײͣ���������Ϊ3G/��");
		
	}

}
