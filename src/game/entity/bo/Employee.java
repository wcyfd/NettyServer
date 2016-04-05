package game.entity.bo;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author wcy 2016年3月31日
 *
 */
public class Employee {
	// 雇员id
	private int employeeId;
	// 玩家id
	private int roleId;
	// 公司id
	private int companyId;
	// 雇员名称
	private String name;
	// 出生时间
	private long birthTime;
	// 天赋信息
	private String talent;
	// 科技信息
	private String tech;
	// 佣金
	private int salary;
	// 状态
	private byte status;
	// 天赋表
	private Map<Integer, Integer> talentMap = new HashMap<>();
	// 科技表
	private Map<Integer, Integer> techMap = new HashMap<>();

	public int getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(int employeeId) {
		this.employeeId = employeeId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public int getRoleId() {
		return roleId;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getBirthTime() {
		return birthTime;
	}

	public void setBirthTime(long birthTime) {
		this.birthTime = birthTime;
	}

	public String getTalent() {
		return talent;
	}

	public void setTalent(String talent) {
		this.talent = talent;
	}

	public String getTech() {
		return tech;
	}

	public void setTech(String tech) {
		this.tech = tech;
	}

	public Map<Integer, Integer> getTalentMap() {
		return talentMap;
	}

	public void setTalentMap(Map<Integer, Integer> talentMap) {
		this.talentMap = talentMap;
	}

	public Map<Integer, Integer> getTechMap() {
		return techMap;
	}

	public void setTechMap(Map<Integer, Integer> techMap) {
		this.techMap = techMap;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}

	public int getSalary() {
		return salary;
	}

	public byte getStatus() {
		return status;
	}

	public void setStatus(byte status) {
		this.status = status;
	}
}
