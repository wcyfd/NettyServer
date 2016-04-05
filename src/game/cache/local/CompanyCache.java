package game.cache.local;

import game.entity.bo.Company;

import java.util.HashMap;
import java.util.Map;

public class CompanyCache {
	//<companyId,company>
	private static Map<Integer,Company> companyMap = new HashMap<>();
	//<roleId,company>
	private static Map<Integer,Company> roleCompanyMap =new HashMap<>();
	
	public static void putCompany(Company company){
		int companyId= company.getCompanyId();
		int roleId = company.getRoleId();
		
		companyMap.put(companyId, company);
		roleCompanyMap.put(roleId,company);
	}
	
	public static Map<Integer,Company> getCompanyMap(){
		return companyMap;
	}
	
	public static Map<Integer,Company> getRoleCompanyMap(){
		return roleCompanyMap;
	}
}
