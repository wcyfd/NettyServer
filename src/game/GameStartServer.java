package game;

import game.cache.file.TestConfigCache;
import game.entity.file.TestConfig;
import game.module.employee.service.EmployeeService;
import game.module.role.service.RoleService;
import game.net.ServerFramework;
import game.utils.excel.ExcelReader;
import game.utils.spring.SpringContext;

public class GameStartServer {

	public static void main(String[] args) {

		SpringContext.initSpringCtx("ApplicationContext.xml");

		ExcelReader reader = new ExcelReader();
		reader.setDirectory("excelFile").setCachePackageName("game.cache.file")
				.setMappingPackageName("game.entity.file").readExcel2Cache();

		for (TestConfig config :TestConfigCache.getMap().values()){
			System.out.println(config);
		}
		
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
