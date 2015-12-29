

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;

/**
 * CSV工具包
 * @author suwb
 */
public class CsvTool {
	
	/**
	 * 写CSV文件
	 * @param outFilePath	数据文件路径
	 * @param header		内容第一行标题
	 * @param dataList		内容
	 */
	public static void writeCsv(String outFilePath, String[] header, List<String[]> dataList) {

		CsvWriter writer = null;  
		try {
            writer = new CsvWriter(outFilePath, ',', Charset.forName("UTF-8"));
            //写文件头
            if(header != null){
            	writer.writeRecord(header);
            }
            //写文件内容
            for (String[] datas : dataList) {
				writer.writeRecord(datas);
			}
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            writer.close();  
        }  
	}
	
	/** 
     * 读CSV文件
     * @param csvFilePath 
     * @throws Exception 
     */  
    public static List<Object[]> readCsv(String csvFilePath) throws Exception {  
    	// 返回结果
    	List<Object[]> datas = new ArrayList<Object[]>();  
    	CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("UTF-8"));
        //读文件内容
        while (reader.readRecord()) {  
        	datas.add(reader.getValues());  
        }  
		return datas;
    }  

}
