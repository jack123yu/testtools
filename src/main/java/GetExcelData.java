import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

public class GetExcelData {

	public static Object[][] getExcelData(Method method) {
		String relativePath=null;
		DataFile dataFile = (DataFile) method.getAnnotation(DataFile.class);
		String path = dataFile.path();
		String sheetName=dataFile.sheetName();
		if ((StringUtils.startsWith(path, "classpath")) && (StringUtils.contains(path, ":"))) {
		    relativePath = StringUtils.split(path, ":")[1];
			relativePath = "src/test/resources/" + relativePath;
			/*File excelFile=null;
			try {
				excelFile = ResourceUtils.getFile(relativePath);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}*/
			
			
		}
		
		return switchExcelData(relativePath,sheetName);
	}
	private static Object[][] switchExcelData(String relativePath ,String sheetName){
		   Workbook wb = null;
		   Object[][]excelData;
	        File file=new File(relativePath);
	        try {
				wb=Workbook.getWorkbook(file);
			} catch (BiffException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        Sheet sheet=wb.getSheet(sheetName);
	        int rowsNum=sheet.getRows();
	        int columnsNum=sheet.getColumns();
			excelData = new String[rowsNum - 1][columnsNum - 3];
			for (int i = 1; i < sheet.getRows(); i++) {
				int excelDataIndex=i-1;
				/*
				 * 判断用例是否是需要执行，如果为yes为执行的用例，为no为不需要执行的用例
				 */
				
				if (sheet.getCell(2, i).getContents().trim().toLowerCase().equals("true")) {
					for (int j = 3; j <columnsNum; j++) {
						Cell cell = sheet.getCell(j, i);
						excelData[excelDataIndex][j-3] = cell.getContents();
					
					}
				}else {
					
					continue;
					
				}
				
			}
			wb.close();
		    /*
		     * 剔除Exceldata中空值即Excel标记为no的用例
		     */
			int acount=0;
			for (int i = 0; i < excelData.length; i++) {
				
				if (excelData[i][0]!=null) {
					acount++;
				}
			}
			Object[][] valueExcelData=new String[acount][];
			int falg=0;//定义一个标识，剔除Exceldata空值时，valueExcelData下标不随Excel的增加而增加
		   for (int i = 0; i < excelData.length; i++) {
				
			if (excelData[i][0]!=null) {
				valueExcelData[i-falg]=excelData[i];
			}else {
				falg++;
			}
			}
	    return valueExcelData;    
	}
}
