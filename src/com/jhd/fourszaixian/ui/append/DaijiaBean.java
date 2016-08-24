package com.jhd.fourszaixian.ui.append;

public class DaijiaBean {
	//代驾信息实体类
	/* "uid": "2716",
    "ucode": "BJ0491",
    "name": "姚师傅",
    "phone": "4006138138",
    "pic": "http://imgs.aidaijia.com/upimage/9fa8f339-42fe-4b14-ade1-4114c073263a.jpg",
    "jialin": "6",
    "cishu": "754",
    "xinji": "10",
    "state": "空闲中",
    "juli": 3.2018723487854004,
    "jiguan": "河南",
    "idcode": "41272419740224****",
    "lng": "116.472815734773",
    "lat": "39.9577594815747",
    "goodrate": "5",
    "SatisfactionRate": "94.00"*/
	//其他信息不需要填写
	String name;
	public String phone;
	String pic;
	String cishu;
	String jialin;
	double juli;
	String goodrate;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}
	public String getCishu() {
		return cishu;
	}
	public void setCishu(String cishu) {
		this.cishu = cishu;
	}
	public String getJialin() {
		return jialin;
	}
	public void setJialin(String jialin) {
		this.jialin = jialin;
	}
	public double getJuli() {
		return juli;
	}
	public void setJuli(double juli) {
		this.juli = juli;
	}
	public String getGoodrate() {
		return goodrate;
	}
	public void setGoodrate(String goodrate) {
		this.goodrate = goodrate;
	}
	
	
	public DaijiaBean() {
		super();
	}
	public DaijiaBean(String name, String pic, String cishu, String jialin,
			double juli, String goodrate) {
		super();
		this.name = name;
		this.pic = pic;
		this.cishu = cishu;
		this.jialin = jialin;
		this.juli = juli;
		this.goodrate = goodrate;
	}
	@Override
	public String toString() {
		return "DaijiaBean [name=" + name + ", pic=" + pic + ", cishu=" + cishu
				+ ", jialin=" + jialin + ", juli=" + juli + ", goodrate="
				+ goodrate + "]";
	}
	
	

}
