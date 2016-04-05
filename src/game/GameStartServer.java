package game;

import game.module.employee.service.EmployeeService;
import game.module.role.service.RoleService;
import game.net.ServerFramework;
import game.utils.spring.SpringContext;

public class GameStartServer {

	public static void main(String[] args) {

		SpringContext.initSpringCtx("ApplicationContext.xml");

		GameStartServer server = new GameStartServer();
		server.init();

		ServerFramework framework = (ServerFramework) SpringContext.getBean("serverFramework");
		framework.start();

	}

	public void init() {
		EmployeeService employeeService = (EmployeeService) SpringContext.getBean("employeeService");
		employeeService.initService();
		RoleService roleService = (RoleService) SpringContext.getBean("roleService");
		roleService.initService();
	}

}
