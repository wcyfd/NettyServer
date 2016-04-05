package game.utils.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {
	private String evaluationCachePackageName = null;
	private String evaluationMappingPackageName = null;
	private String directoryName = null;
	
	public ExcelReader() {
	}
	
	public ExcelReader setDirectory(String directoryName){
		this.directoryName = directoryName;
		return this;
	}
	
	public ExcelReader setCachePackageName(String cachePackName){
		this.evaluationCachePackageName = cachePackName.concat(".");
		return this;
	}
	
	public ExcelReader setMappingPackageName(String mapPackName){
		this.evaluationMappingPackageName = mapPackName.concat(".");
		return this;
	}

	public void readExcel2Cache() {
		File[] files = null;
		File tempfile = new File(System.getProperty("user.dir") + System.getProperty("file.separator")
				+ this.directoryName);
		if (tempfile.isDirectory()) {
			files = tempfile.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					if ("xlsx".equals(name.substring(name.length() - 4))) {
						return true;
					}
					return false;
				}
			});
		}

		try {
			Arrays.sort(files, new Comparator() {
				public int compare(File o1, File o2) {
					return o1.getName().compareTo(o2.getName());
				}

				@Override
				public int compare(Object o1, Object o2) {
					File oa = (File) o1;
					File ob = (File) o2;
					return oa.getName().compareTo(ob.getName());
				}
			});
			for (File file : files)
				if (file != null)
					try {
						loadEachFile(file);
					} catch (Exception e) {
						System.err.println("loading {} an error occured: " + file.getName());
						e.printStackTrace();
						throw new RuntimeException(e);
					}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadEachFile(File file) throws FileNotFoundException, IOException, SecurityException,
			IllegalArgumentException, ClassNotFoundException, NoSuchMethodException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		FileInputStream fis = new FileInputStream(file);
		try {
			Workbook workbook = new XSSFWorkbook(fis);

			for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
				Sheet sheet = workbook.getSheetAt(i);
				List setOrderList = new ArrayList();
				Row firstRow = sheet.getRow(0);
				if (firstRow == null) {
					return;
				}

				for (Cell cell : firstRow) {
					setOrderList.add(concatSetMethod(cell.getStringCellValue()));
				}
				sheet.removeRow(firstRow);

				for (Row row : sheet) {
					List valueList = new ArrayList();

					for (Cell cell : row) {
						switch (cell.getCellType()) {
						case 1:
							valueList.add(cell.getStringCellValue());
							break;
						case 0:
							valueList.add(Double.valueOf(cell.getNumericCellValue()));
							break;
						case 3:
						case 2:
						}

					}

					setValue(workbook.getSheetName(i), setOrderList, valueList);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void setValue(String clazzName, List<String> setOrderList, List<Object> valueList)
			throws SecurityException, ClassNotFoundException, NoSuchMethodException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, InvocationTargetException {
		String upClazzName = upFirstChar(clazzName);
		Class clazz = null;
		try {
			clazz = Class.forName(this.evaluationMappingPackageName.concat(upClazzName));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return;
		}
		Constructor constructor = clazz.getConstructor(new Class[0]);
		Object newInstance = constructor.newInstance(new Object[0]);
		Method[] methods = clazz.getMethods();

		for (String setOrder : setOrderList) {
			for (Method method : methods) {
				if (method.getName().equals(setOrder)) {
					int index = setOrderList.indexOf(setOrder);
					Object object = null;
					try {
						object = valueList.get(index);
					} catch (Exception e) {
						e.printStackTrace();
						System.out.println("className: " + upClazzName + " method: " + method);
						System.out.println("Type: String " + object);
						System.out.println("Type: " + newInstance);
					}

					if ((object instanceof String)) {
						try {
							method.invoke(newInstance, new Object[] { object.toString() });
						} catch (Exception e) {
							e.printStackTrace();
							System.out.println("className: " + upClazzName + " method: " + method);
							System.out.println("Type: String" + object);
							System.out.println("Type: " + newInstance);
						}
					}

					if (!(object instanceof Double))
						break;
					Class[] clazzType = method.getParameterTypes();
					String parameterName = clazzType[0].getName();
					if (parameterName.equals("int")) {
						method.invoke(newInstance, new Object[] { Integer.valueOf(((Double) object).intValue()) });
						break;
					}
					if (parameterName.equals("float")) {
						method.invoke(newInstance, new Object[] { Float.valueOf(((Double) object).floatValue()) });
						break;
					}
					if (!parameterName.equals("long"))
						break;
					method.invoke(newInstance, new Object[] { Long.valueOf(((Double) object).longValue()) });

					break;
				}
			}

		}

		putCache(upClazzName, clazz, newInstance);
	}

	private void putCache(String upClazzName, Class<?> paramClazz, Object param) throws ClassNotFoundException,
			SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException,
			IllegalAccessException, InvocationTargetException {
		Class clazz = Class.forName(this.evaluationCachePackageName.concat(upClazzName.concat("Cache")));
		Constructor constructor = clazz.getConstructor(new Class[0]);
		Object newInstance = constructor.newInstance(new Object[0]);
		Method method = clazz.getMethod("put".concat(upClazzName), new Class[] { paramClazz });
		method.invoke(newInstance, new Object[] { param });
	}

	private String concatSetMethod(String str) {
		String setMethod = "set";
		return setMethod.concat(upFirstChar(str));
	}

	private String upFirstChar(String str) {
		return str.substring(0, 1).toUpperCase().concat(str.substring(1, str.length()));
	}
}