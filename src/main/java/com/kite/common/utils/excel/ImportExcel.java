/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.common.utils.excel;

import com.google.common.collect.Lists;
import com.kite.common.utils.Encodes;
import com.kite.common.utils.Reflections;
import com.kite.common.utils.excel.annotation.ExcelField;
import com.kite.modules.sys.entity.Office;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.utils.DictUtils;
import com.kite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 导入Excel文件（支持“XLS”和“XLSX”格式）
 * @author kite
 * @version 2013-03-10
 */
public class ImportExcel {

	private static Logger log = LoggerFactory.getLogger(ImportExcel.class);

	/**
	 * 工作薄对象
	 */
	private Workbook wb;

	/**
	 * 工作表对象
	 */
	private Sheet sheet;

	/**
	 * 标题行号
	 */
	private int headerNum;

	/**
	 * 值行号开始数
	 */
	public int rowFirstNum;

	/**
	 * 值行号结束数
	 */
	public int rowLastNum;

	/**
	 * 校验结果
	 */
	public boolean isCheckOk = true;

	private int iNum = 1;

	/**
	 * 构造函数
	 * @param path 导入文件，读取第一个工作表
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ImportExcel(String fileName, int headerNum)
			throws InvalidFormatException, IOException {
		this(new File(fileName), headerNum);
		this.rowFirstNum = this.headerNum + 1;
		this.rowLastNum = this.sheet.getLastRowNum();
	}

	/**
	 * 构造函数
	 * @param path 导入文件对象，读取第一个工作表
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ImportExcel(File file, int headerNum)
			throws InvalidFormatException, IOException {
		this(file, headerNum, 0);
		this.rowFirstNum = this.headerNum + 1;
		this.rowLastNum = this.sheet.getLastRowNum();
	}

	/**
	 * 构造函数
	 * @param path 导入文件
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @param sheetIndex 工作表编号
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ImportExcel(String fileName, int headerNum, int sheetIndex)
			throws InvalidFormatException, IOException {
		this(new File(fileName), headerNum, sheetIndex);
		this.rowFirstNum = this.headerNum + 1;
		this.rowLastNum = this.sheet.getLastRowNum();
	}

	/**
	 * 构造函数
	 * @param path 导入文件对象
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @param sheetIndex 工作表编号
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ImportExcel(File file, int headerNum, int sheetIndex)
			throws InvalidFormatException, IOException {
		this(file.getName(), new FileInputStream(file), headerNum, sheetIndex);
		this.rowFirstNum = this.headerNum + 1;
		this.rowLastNum = this.sheet.getLastRowNum();
	}

	/**
	 * 构造函数
	 * @param file 导入文件对象
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @param sheetIndex 工作表编号
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ImportExcel(MultipartFile multipartFile, int headerNum, int sheetIndex)
			throws InvalidFormatException, IOException {
		this(multipartFile.getOriginalFilename(), multipartFile.getInputStream(), headerNum, sheetIndex);
		this.rowFirstNum = this.headerNum + 1;
		this.rowLastNum = this.sheet.getLastRowNum();
	}

	/**
	 * 构造函数
	 * @param path 导入文件对象
	 * @param headerNum 标题行号，数据行号=标题行号+1
	 * @param sheetIndex 工作表编号
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public ImportExcel(String fileName, InputStream is, int headerNum, int sheetIndex)
			throws InvalidFormatException, IOException {
		if (StringUtils.isBlank(fileName)){
			throw new RuntimeException("导入文档为空!");
		}else if(fileName.toLowerCase().endsWith("xls")){
			this.wb = new HSSFWorkbook(is);
        }else if(fileName.toLowerCase().endsWith("xlsx")){
        	this.wb = new XSSFWorkbook(is);
        }else{
        	throw new RuntimeException("文档格式不正确!");
        }
		if (this.wb.getNumberOfSheets()<sheetIndex){
			throw new RuntimeException("文档中没有工作表!");
		}
		this.sheet = this.wb.getSheetAt(sheetIndex);
		this.headerNum = headerNum;
		this.rowFirstNum = this.headerNum + 1;
		this.rowLastNum = this.sheet.getLastRowNum();
		log.debug("Initialize success.");
	}

	/**
	 * 新建excel列作为校验结果
	 * @param columnNum
	 * @param currentRow
	 * @param RequirStr
	 */
	public void structureCheckResult (int columnNum, Row currentRow, String RequirStr) {
		Cell wrongCell = currentRow.getCell(columnNum);
		CellStyle style = this.wb.createCellStyle();
		style.setFillBackgroundColor(IndexedColors.RED.getIndex());
	    style.setFillPattern(CellStyle.THICK_BACKWARD_DIAG);
	    wrongCell.setCellStyle(style);
		if (this.isCheckOk) {
			isCheckOk = false;
		}
		StringBuilder strBuilder = new StringBuilder();
		int colNum = columnNum + 1;
		strBuilder.append("导入失败! 此行第");
		strBuilder.append(colNum);
		strBuilder.append("列上的值不符合要求, 第");
		strBuilder.append(colNum);
		strBuilder.append("列的值要求");
		strBuilder.append(RequirStr);
		Row headRow = this.getRow(headerNum);
		Cell justCol = currentRow.getCell(headRow.getLastCellNum());
		Cell newCell = null;
		if (justCol != null) {
			newCell = currentRow.createCell(currentRow.getLastCellNum());
			this.sheet.autoSizeColumn(currentRow.getLastCellNum() - 1);
		}
		else {
			newCell = currentRow.createCell(headRow.getLastCellNum());
			this.sheet.autoSizeColumn(currentRow.getLastCellNum() - 1);
		}
		newCell.setCellValue(strBuilder.toString());

		if (headerNum > 0) {
			Row topRow = this.getRow(headerNum - 1);
			Cell reasonHeahCell = topRow.getCell(currentRow.getLastCellNum() - 1);
			if (reasonHeahCell == null) {
				Cell newReasonHeahCell = topRow.createCell(currentRow.getLastCellNum() - 1);
				this.sheet.autoSizeColumn(currentRow.getLastCellNum() - 1);
				newReasonHeahCell.setCellValue("错误原因" + this.iNum);
				iNum ++ ;
			}
		}
	}

	/**
	 * 输出到客户端
	 * @param fileName 输出文件名
	 */
	public void write(HttpServletResponse response, String fileName) throws IOException{
		response.reset();
        response.setContentType("application/octet-stream; charset=utf-8");
        response.setHeader("Content-Disposition", "attachment; filename="+Encodes.urlEncode(fileName));
        wb.write(response.getOutputStream());
	}


	/**
	 * 获取行对象
	 * @param rownum
	 * @return
	 */
	public Row getRow(int rownum){
		return this.sheet.getRow(rownum);
	}

	/**
	 * 获取数据行号
	 * @return
	 */
	public int getDataRowNum(){
		return headerNum+1;
	}

	/**
	 * 获取最后一个数据行号
	 * @return
	 */
	public int getLastDataRowNum(){
		return this.sheet.getLastRowNum()+headerNum;
	}

	/**
	 * 获取最后一个列号
	 * @return
	 */
	public int getLastCellNum(){
		return this.getRow(headerNum).getLastCellNum();
	}

	/**
	 * 获取单元格值
	 * @param row 获取的行
	 * @param column 获取单元格列号
	 * @return 单元格值
	 */
	public Object getCellValue(Row row, int column) {
		Object val = "";
		try {
			Cell cell = row.getCell(column);
			if (cell != null) {
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					// val = cell.getNumericCellValue();
					// 当excel 中的数据为数值或日期是需要特殊处理
					if (HSSFDateUtil.isCellDateFormatted(cell)) {
						double d = cell.getNumericCellValue();
						Date date = HSSFDateUtil.getJavaDate(d);
                        try {
                            SimpleDateFormat dformat = new SimpleDateFormat(
                                    "yyyy-MM-dd HH:mm:ss");
                            val = dformat.format(date);
                        } catch (Exception e) {
                            SimpleDateFormat dformat = new SimpleDateFormat(
                                    "yyyy-MM-dd");
                            val = dformat.format(date);
                        }
                    } else {
						NumberFormat nf = NumberFormat.getInstance();
						nf.setGroupingUsed(false);// true时的格式：1,234,567,890
						val = nf.format(cell.getNumericCellValue());// 数值类型的数据为double，所以需要转换一下
					}
				} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					val = cell.getStringCellValue();
				} else if (cell.getCellType() == Cell.CELL_TYPE_FORMULA) {
					val = cell.getCellFormula();
				} else if (cell.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
					val = cell.getBooleanCellValue();
				} else if (cell.getCellType() == Cell.CELL_TYPE_ERROR) {
					val = cell.getErrorCellValue();
				}
			}
		} catch (Exception e) {
			return val;
		}
		return val;
	}

	/**
	 * 获取导入数据列表
	 * @param cls 导入对象类型
	 * @param groups 导入分组
	 */
	public <E> List<E> getDataList(Class<E> cls, int... groups) throws InstantiationException, IllegalAccessException{
		List<Object[]> annotationList = Lists.newArrayList();
		// Get annotation field
		Field[] fs = cls.getDeclaredFields();
		for (Field f : fs){
			ExcelField ef = f.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type()==0 || ef.type()==2)){
				if (groups!=null && groups.length>0){
					boolean inGroup = false;
					for (int g : groups){
						if (inGroup){
							break;
						}
						for (int efg : ef.groups()){
							if (g == efg){
								inGroup = true;
								annotationList.add(new Object[]{ef, f});
								break;
							}
						}
					}
				}else{
					annotationList.add(new Object[]{ef, f});
				}
			}
		}
		// Get annotation method
		Method[] ms = cls.getDeclaredMethods();
		for (Method m : ms){
			ExcelField ef = m.getAnnotation(ExcelField.class);
			if (ef != null && (ef.type()==0 || ef.type()==2)){
				if (groups!=null && groups.length>0){
					boolean inGroup = false;
					for (int g : groups){
						if (inGroup){
							break;
						}
						for (int efg : ef.groups()){
							if (g == efg){
								inGroup = true;
								annotationList.add(new Object[]{ef, m});
								break;
							}
						}
					}
				}else{
					annotationList.add(new Object[]{ef, m});
				}
			}
		}
		// Field sorting
		Collections.sort(annotationList, new Comparator<Object[]>() {
			@Override
			public int compare(Object[] o1, Object[] o2) {
				return new Integer(((ExcelField)o1[0]).sort()).compareTo(
						new Integer(((ExcelField)o2[0]).sort()));
			};
		});
		//log.debug("Import column count:"+annotationList.size());
		// Get excel data
		List<E> dataList = Lists.newArrayList();
		for (int i = this.getDataRowNum(); i < this.getLastDataRowNum(); i++) {
			E e = (E)cls.newInstance();
			int column = 0;
			Row row = this.getRow(i);
			StringBuilder sb = new StringBuilder();
			for (Object[] os : annotationList){
				Object val = this.getCellValue(row, column++);
				if (val != null){
					ExcelField ef = (ExcelField)os[0];
					// If is dict type, get dict value
					if (StringUtils.isNotBlank(ef.dictType())){
						val = DictUtils.getDictValue(val.toString().trim(), ef.dictType().trim(), "");
						//log.debug("Dictionary type value: ["+i+","+colunm+"] " + val);
					}
					// Get param type and type cast
					Class<?> valType = Class.class;
					if (os[1] instanceof Field){
						valType = ((Field)os[1]).getType();
					}else if (os[1] instanceof Method){
						Method method = ((Method)os[1]);
						if ("get".equals(method.getName().substring(0, 3))){
							valType = method.getReturnType();
						}else if("set".equals(method.getName().substring(0, 3))){
							valType = ((Method)os[1]).getParameterTypes()[0];
						}
					}
					//log.debug("Import value type: ["+i+","+column+"] " + valType);
					try {
						//如果导入的java对象，需要在这里自己进行变换。
						//System.out.println("val="+val+"___"+"valType="+valType);
						if (valType == String.class){
							String s = String.valueOf(val.toString()).trim();
							if(StringUtils.endsWith(s, ".0")){
								val = StringUtils.substringBefore(s, ".0");
							}else{
								val = String.valueOf(val.toString().trim());
							}
						}else if (valType == Integer.class){
							val = Double.valueOf(val.toString().trim()).intValue();
						}else if (valType == Long.class){
							val = Double.valueOf(val.toString().trim()).longValue();
						}else if (valType == Double.class){
							val = Double.valueOf(val.toString().trim());
						}else if (valType == BigDecimal.class){
							val = new BigDecimal(val.toString().trim());
						}else if (valType == Float.class){
							val = Float.valueOf(val.toString().trim());
						}else if (valType == Date.class){
							SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							try{
								val=sdf.parse(val.toString().trim());
							}catch (Exception ex){
								SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
								val=sdf2.parse(val.toString().trim());
							}
						}else if (valType == User.class){
							val = UserUtils.getByUserName(val.toString().trim());
						}else if (valType == Office.class){
							val = UserUtils.getByOfficeName(val.toString().trim());
						}else{
							if (ef.fieldType() != Class.class){
								val = ef.fieldType().getMethod("getValue", String.class).invoke(null, val.toString().trim());
							}else{
								val = Class.forName(this.getClass().getName().replaceAll(this.getClass().getSimpleName(),
										"fieldtype."+valType.getSimpleName()+"Type")).getMethod("getValue", String.class).invoke(null, val.toString().trim());
							}
						}
					} catch (Exception ex) {
						log.info("Get cell value ["+i+","+column+"] error: " + ex.toString());
						val = null;
					}
					// set entity value
					if (os[1] instanceof Field){
						Reflections.invokeSetter(e, ((Field)os[1]).getName(), val);
					}
					else if (os[1] instanceof Method){
						String mthodName = ((Method)os[1]).getName();
						if ("get".equals(mthodName.substring(0, 3))){
							mthodName = "set"+StringUtils.substringAfter(mthodName, "get");
						}
						Reflections.invokeMethod(e, mthodName, new Class[] {valType}, new Object[] {val});
					}
				}
				sb.append(val+", ");
			}
			dataList.add(e);
			log.debug("Read success: ["+i+"] "+sb.toString());
		}
		return dataList;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public int getRowFirstNum() {
		return rowFirstNum;
	}

	public int getRowLastNum() {
		return rowLastNum;
	}

	public boolean isCheckOk() {
		return isCheckOk;
	}

	public void setCheckOk(boolean isCheckOk) {
		this.isCheckOk = isCheckOk;
	}

	/**
	 * 检验数据是否重复
	 * @param list
	 * @return 是否重复
	 */
	public boolean checkRepeat(ImportExcel ei,List<Integer> list) {
		Map<String,Integer> map = new HashMap<String,Integer>();
		for (int i = ei.getRowFirstNum(); i <= ei.getRowLastNum(); i ++) {
			Row row = ei.getSheet().getRow(i);
			if (row == null) {
				continue;
			}
			StringBuffer str = new StringBuffer("");
			for (Integer num : list) {
				str.append( ei.getCellValue(row,num)+ ",");
			}
			if(map.containsKey(str.toString())) {
				ei.structureCheckResult(0,row,"该被举报号码全码，举报时间，举报人号码的数据与第"+map.get(str.toString())+"行相同");
				return false;
			}else {
				map.put(str.toString(),i-1);
			}
		}
		return true;
	}

//	/**
//	 * 导入测试
//	 */
//	public static void main(String[] args) throws Throwable {
//
//		ImportExcel ei = new ImportExcel("target/export.xlsx", 1);
//
//		for (int i = ei.getDataRowNum(); i < ei.getLastDataRowNum(); i++) {
//			Row row = ei.getRow(i);
//			for (int j = 0; j < ei.getLastCellNum(); j++) {
//				Object val = ei.getCellValue(row, j);
//				System.out.print(val+", ");
//			}
//			System.out.print("\n");
//		}
//
//	}

}
