package game.nav;

import java.util.HashMap;
import java.util.Map;

import game.module.register.action.RegisterRegisterRoleAction;

public class Nav {
	private static Nav _instance = new Nav();

	public Map<Integer, AbstractAction> navMap = new HashMap<>();

	private Nav() {
		this.init();
	}

	private void init() {
		navMap.put(NavNum.REGISTER_ROLE, new RegisterRegisterRoleAction());
	}

	public static AbstractAction getAction(int protocalNum) {
		return _instance.navMap.get(protocalNum);
	}

}
