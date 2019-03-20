import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;

public class DataProvide {
	@DataProvider(name="excel")
	public Object[][] providerData(Method method){
		Object[][] test=GetExcelData.getExcelData(method );
		return GetExcelData.getExcelData(method );
	}
	

}
