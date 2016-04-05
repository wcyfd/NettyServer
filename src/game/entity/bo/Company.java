package game.entity.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author wcy 2016年3月31日
 *
 */
public class Company {
	// 玩家id
	private int roleId;
	// 公司id
	private int companyId;
	// 类型
	private int type;
	// 雇员信息
	private String employeeInfo;
	// 雇员列表
	private List<Integer> employeeList = new ArrayList<>();
	// 官员映射
	private Map<Integer, Employee> empolyeeMap = new HashMap<>();

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getEmployeeInfo() {
		StringBuilder sb = new StringBuilder();
		
		for(Integer id:employeeList){
			sb.append(id).append(",");
		}
		this.employeeInfo = sb.toString();
		return employeeInfo;
	}

	public void setEmployeeInfo(String employeeInfo) {
		if(employeeInfo == null|| employeeInfo.equals("")){
			return;
		}
		this.employeeInfo = employeeInfo;
		String[] idStrArray=employeeInfo.split(",");
		for(String idStr:idStrArray	){
			int id = Integer.parseInt(idStr);
			employeeList.add(id);
		}
	}

	public List<Integer> getEmployeeList() {
		return employeeList;
	}

	public Map<Integer, Employee> getEmpolyeeMap() {
		return empolyeeMap;
	}

}
