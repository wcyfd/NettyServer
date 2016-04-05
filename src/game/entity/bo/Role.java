package game.entity.bo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 玩家类
 * 
 * @author wcy 2016年3月29日
 *
 */
public class Role {
	/** 玩家id */
	private int id;
	/** 玩家名称 */
	private String name;
	/** 玩家帐号 */
	private String account;
	/** 财产表 */
	private Map<Byte, Integer> propertyMap = new HashMap<>();
	/* 财产字符串 */
	private String property;
	/**公司id*/
	private int companyId;
	
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	
	public int getCompanyId() {
		return companyId;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getProperty() {
		StringBuilder sb = new StringBuilder();
		for (Entry<Byte, Integer> entry : this.propertyMap.entrySet()) {
			byte type = entry.getKey();
			int value = entry.getValue();
			sb.append(type).append(",").append(value).append(";");
		}
		this.property = sb.toString();

		return property;
	}

	public void setProperty(String property) {
		if (property == null || property.equals("")) {
			return;
		}
		this.property = property;
		String[] propertyList = property.split(";");
		for (String info : propertyList) {
			String[] nameAndNum = info.split(",");
			byte type = Byte.parseByte(nameAndNum[0]);
			int num = Integer.parseInt(nameAndNum[1]);
			this.propertyMap.put(type, num);
		}
	}

	public Map<Byte, Integer> getPropertyMap() {
		return propertyMap;
	}

}
