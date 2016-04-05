package game;

import game.cache.file.TestConfigCache;
import game.entity.file.TestConfig;
import game.module.employee.service.EmployeeService;
import game.module.role.service.RoleService;
import game.net.ServerFramework;
import game.utils.excel.ExcelReader;
import game.utils.spring.SpringContext;

import java.util.Map;
import java.util.Map.Entry;

public class GameStartServer {

	public static void main(String[] args) {

		SpringContext.initSpringCtx("ApplicationContext.xml");

		ExcelReader.readExcel2Cache("excelFile", "game.cache.file", "game.entity.file");

		

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
