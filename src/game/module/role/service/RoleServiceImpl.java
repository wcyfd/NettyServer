package game.module.role.service;

import game.cache.local.RoleCache;
import game.db.dao.RoleDao;
import game.entity.bo.Role;
import game.module.role.RoleConstant;

import java.util.List;
import java.util.Map;

public class RoleServiceImpl implements RoleService {

	private RoleDao roleDao;
	public void setRoleDao(RoleDao roleDao) {
		this.roleDao = roleDao;
	}
	@Override
	public void initService() {
		List<String> accountList = roleDao.getAllAccount();
		List<String> nameList = roleDao.getAllName();
		
		for (String accountStr : accountList) {
			RoleCache.getAllAccount().add(accountStr);
		}

		for (String nameStr : nameList) {
			RoleCache.getAllName().add(nameStr);
		}
	}

	@Override
	public void addRoleGold(Role role, int num) {
		this.addRoleProperty(role, RoleConstant.PROPERTY_GOLD, num);
	}

	@Override
	public void addRoleMoney(Role role, int num) {
		this.addRoleProperty(role, RoleConstant.PROPERTY_MONEY, num);
	}

	@Override
	public void addRolePrestige(Role role, int num) {
		this.addRoleProperty(role, RoleConstant.PROPERTY_PRESTIGE, num);
	}

	private void addRoleProperty(Role role, byte type, int num) {
		Map<Byte, Integer> map = role.getPropertyMap();
		if (num < 0) {
			num = 0;
		}
		Integer n = map.get(type);
		if (n != null) {
			int newValue = n + num;
			if (newValue < 0) {
				newValue = Integer.MAX_VALUE;
			}
			map.put(type, newValue);
		}
	}

}
