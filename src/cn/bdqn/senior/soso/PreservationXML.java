package cn.bdqn.senior.soso;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class PreservationXML {
	static Document doc=null;//�ļ�
	{
		SAXReader reader=new SAXReader();
		try {
			doc=reader.read(new File("UserInformation.xml"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	Element root=doc.getRootElement();
	List<Element> nodes=root.elements("number");
	//�����ͻ���Ϣ��
	public void addParents(String number,String name,String pass,double price,String packageName,int talkTime,int smsCount,int flow){
		Element brandEle=root.addElement("number");						//�绰��
		brandEle.addAttribute("id", number);
		Element numberName=brandEle.addElement("name");					//�û���
		numberName.addText(name);
		Element numberPassword=brandEle.addElement("password");			//����
		numberPassword.addText(pass);
		Element numberPrice=brandEle.addElement("money");				//ʣ���Ǯ
		numberPrice.addText(new Double(price).toString());
		Element numberPackage=brandEle.addElement("package");			//�ײ�
		numberPackage.addText(packageName);
		if(talkTime!=0){
			Element numberTalkTime=brandEle.addElement("talkTime");		//���ͨ��ʱ��
			numberTalkTime.addText(new Integer(talkTime).toString());
		}
		if(smsCount!=0){	
			Element numberSmsCount=brandEle.addElement("smsCount");		//��Ѷ�������
			numberSmsCount.addText(new Integer(smsCount).toString());
		}
		if(flow!=0){
			Element numberFlow=brandEle.addElement("flow");				//�ײ�����
			numberFlow.addText(new Integer(flow).toString());
		}
		saveInfo("UserInformation.xml");
	}
	public void saveInfo(String path){//�����޸�
			OutputFormat format=OutputFormat.createPrettyPrint();
			format.setEncoding("gb2312");
			try {
				XMLWriter writer=new XMLWriter(new FileWriter(path),format);
				writer.write(doc);
				writer.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
	public boolean lookUpNumber(String number){//�����Ƿ��д˺��룬�з���true��û�з���false
		boolean flag=false;
		
		for(Iterator<Element> it=nodes.iterator();it.hasNext();){
			Element elm=it.next();
			if(elm.attribute("id").getText().equals(number)){
				flag=true;
				break;
			}
		}
		return flag;
		
	}
	public String lookUpLabel(String number,String Label){		//������Label��ǩ���ı�ֵ
		String lable=null;
		for(Iterator<Element> it=nodes.iterator();it.hasNext();){
			Element elm=it.next();
			if(elm.attributeValue("id").equals(number)){
				if(elm.element(Label)!=null)
					lable=elm.element(Label).getText();
				
				
			}
		}
		return lable;
	}
	public void delCard(String number){							//ע���˻�
		for(Iterator<Element> it=nodes.iterator();it.hasNext();){
			Element elm=it.next();
			if(elm.attributeValue("id").equals(number)){
				root.remove(elm);
			}
		}
		saveInfo("UserInformation.xml");
	}
	public boolean replaceLabel(String number,ServicePackage holder){	//�����ײ�
		boolean flag=false;
		for(Iterator<Element> it=nodes.iterator();it.hasNext();){
			Element elm=it.next();
			elm.element("package").setText(holder.name);
			if(elm.attributeValue("id").equals(number)){
				if(holder instanceof TalkPackage){
					TalkPackage talk=(TalkPackage)holder;
					try {
						if(Double.parseDouble(elm.element("money").getText())-holder.price<0){//����˻����е�Ǯ����֧���ײͷ���
							flag=true;
							return flag;
						}
						elm.element("money").setText(new Double(Double.parseDouble(elm.element("money").getText())-holder.price).toString());
						if(elm.element("talkTime")==null){
							Element numberTalkTime=elm.addElement("talkTime");		//���ͨ��ʱ��
							numberTalkTime.addText(new Integer(talk.talkTime).toString());
						}else
							elm.element("talkTime").setText(new Integer(talk.talkTime).toString());
						if(elm.element("smsCount")==null){
							Element numberTalkTime=elm.addElement("smsCount");		//��Ѷ���
							numberTalkTime.addText(new Integer(talk.smsCount).toString());
						}else
							elm.element("smsCount").setText(new Integer(talk.smsCount).toString());
						if(elm.element("flow")!=null)
							elm.remove(elm.element("flow"));						//ɾ������
					} catch (Exception e) {
						System.out.println("ϵͳ����");
					}
				}
				if(holder instanceof NetPackage){
					NetPackage net=(NetPackage)holder;
					try {
						if(Double.parseDouble(elm.element("money").getText())-holder.price<0){//����˻����е�Ǯ����֧���ײͷ���
							flag=true;
							return flag;
						}
						elm.element("money").setText(new Double(Double.parseDouble(elm.element("money").getText())-holder.price).toString());
						if(elm.element("talkTime")!=null)
							elm.remove(elm.element("talkTime"));					//ɾ�����ͨ��ʱ��
						if(elm.element("smsCount")!=null)
							elm.remove(elm.element("smsCount"));					//ɾ�����ͨ��ʱ��
						if(elm.element("flow")==null){
							Element numberFlow=elm.addElement("flow");				//�ײ�����
							numberFlow.addText(new Integer(net.flow).toString());}
						else
							elm.element("flow").setText(new Integer(net.flow).toString());
					} catch (Exception e) {System.out.println("ϵͳ����");
					}
				}
					if(holder instanceof SuperPackage){
						SuperPackage supr=(SuperPackage)holder;
					try {
						if(Double.parseDouble(elm.element("money").getText())-holder.price<0){//����˻����е�Ǯ����֧���ײͷ���
							flag=true;
							return flag;
						}
						elm.element("money").setText(new Double(Double.parseDouble(elm.element("money").getText())-holder.price).toString());
						if(elm.element("talkTime")==null){
							Element numberTalkTime=elm.addElement("talkTime");		//���ͨ��ʱ��
							numberTalkTime.addText(new Integer(supr.talkTime).toString());
						}else
							elm.element("talkTime").setText(new Integer(supr.talkTime).toString());
						if(elm.element("smsCount")==null){
							Element numberTalkTime=elm.addElement("smsCount");		//��Ѷ���
							numberTalkTime.addText(new Integer(supr.smsCount).toString());
						}else
							elm.element("smsCount").setText(new Integer(supr.smsCount).toString());
						if(elm.element("flow")==null){
							Element numberTalkTime=elm.addElement("flow");		//��Ѷ���
							numberTalkTime.addText(new Integer(supr.flow).toString());
						}else
							elm.element("flow").setText(new Integer(supr.flow).toString());
					} catch (Exception e) {System.out.println("ϵͳ����");
					}
				}
			}
		}
		saveInfo("UserInformation.xml");
		return flag;
	}
	public void Recharge(String number, double money){					//�����˻����
		for(Iterator<Element> it=nodes.iterator();it.hasNext();){
			Element elm=it.next();
			if(elm.attributeValue("id").equals(number)){
				 elm.element("money").setText(new Double(money).toString());
			}
		}
		saveInfo("UserInformation.xml");
	}
	public void addSpend(String number,String Label,String value){			//��������	
		for(Iterator<Element> it=nodes.iterator();it.hasNext();){
			Element elm=it.next();
			if(elm.attributeValue("id").equals(number)){
				if(elm.element(Label)==null){
					Element numberSpend=elm.addElement(Label);			
					numberSpend.setText(value);
				}else{
					elm.element(Label).setText(value);
				}
			}
		}
		saveInfo("UserInformation.xml");
	}
	public void setLabel(String number,String Label,String value){		//����
		for(Iterator<Element> it=nodes.iterator();it.hasNext();){
			Element elm=it.next();
			if(elm.attributeValue("id").equals(number)){
				if(elm.element(Label)!=null)
					 elm.element(Label).setText(value);
				else{
					Element numberSpend=elm.addElement("price");
					numberSpend.addText(value);
				}
			}
		}
		saveInfo("UserInformation.xml");
	}
}
