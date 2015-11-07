package model.manager.excel.reports.out;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import model.manager.PatientManager;



import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.celllife.idart.database.hibernate.Patient;
import org.hibernate.Session;

public class PatientVLSheet
{
   
	private static final Logger log = Logger.getLogger(PatientVLSheet.class);
	
	private String sheetname;
	
	
	
	public PatientVLSheet(String sheetname) {
		super();
		this.sheetname = sheetname;
	}



	public void write(Session session,String filePath,int clinicId) throws IOException
    {
        //Blank workbook
        HSSFWorkbook workbook = new HSSFWorkbook();
         
        //Create a blank sheet
        HSSFSheet sheet = workbook.createSheet(sheetname);
          
        //This data needs to be written (Object[])
        Map<String, Object[]> data = new TreeMap<String, Object[]>();
          
        List<Patient> patients=PatientManager.getAllPatients(session, clinicId); 
        
        for(Patient p: patients){
        	data.put(p.getId()+"", new Object []{p.getId(),p.getPatientId(),p.getFirstNames(),p.getLastname(),
        		p.getDateOfBirth(), null, null});
        	System.out.println(p.getDateOfBirth());
        }
        
        HSSFRow rowhead = sheet.createRow(0); 
        HSSFCellStyle styleHeader = workbook.createCellStyle();
        HSSFCellStyle style = workbook.createCellStyle();
        HSSFFont fontHeader = workbook.createFont();
        HSSFFont font = workbook.createFont();
        
        styleHeader.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleHeader.setBorderTop(HSSFCellStyle.BORDER_THIN);
        styleHeader.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleHeader.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_LEFT);
        
        fontHeader.setFontName(HSSFFont.FONT_ARIAL);
        fontHeader.setFontHeightInPoints((short)11);
        fontHeader.setBold(true);
        
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short)10);
        font.setBold(false);
        
        styleHeader.setFont(fontHeader);
        style.setFont(font);
        
        
        
        rowhead.createCell(0).setCellValue("ID Paciente");
        rowhead.createCell(1).setCellValue("BI");
        rowhead.createCell(2).setCellValue("Nome");
        rowhead.createCell(3).setCellValue("Apelido");
        rowhead.createCell(4).setCellValue("Data de nascimento");
        rowhead.createCell(5).setCellValue("Carga Viral alta?");
        rowhead.createCell(6).setCellValue("Data de Carga Viral");
        for(int j = 0; j<=6; j++)
        rowhead.getCell(j).setCellStyle(styleHeader);
       
        
        //Iterate over data and write to sheet
        Set<String> keyset = data.keySet();
        int rownum = 1;
        for (String key : keyset)
        {
            Row row = sheet.createRow(rownum++);
            Object [] objArr = data.get(key);
            int cellnum = 0;
            for (Object obj : objArr)
            {
               Cell cell = row.createCell(cellnum++);
               cell.setCellStyle(style);
               if(obj instanceof String)
                    cell.setCellValue((String)obj);
                else if(obj instanceof Integer)
                    cell.setCellValue((Integer)obj);
                else if (obj instanceof Date)
                	cell.setCellValue((Date)obj);
            }
        }
        
        for (int i=0; i<10; i++){
        	   sheet.autoSizeColumn(i);
        	}
        try
        {
            //Write the workbook in file system
            FileOutputStream out = new FileOutputStream(new File(filePath));
            workbook.write(out);
            out.close();
            workbook.close();
            System.out.println("written successfully on disk.");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
