package util;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by bs211w on 7/7/2014.
 */
public class ExcelUtil {

    private static ArrayList arrayListRowTestData= new ArrayList();

    public static ArrayList ExcelTestDataRead() throws IOException {
        try {
            String fileName = System.getProperty("user.dir");
            fileName += "/src/main/resources/TestExecutionDriverExcel.xlsx";
            FileInputStream file = new FileInputStream(fileName);

            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();

            while(rowIterator.hasNext()) {
                ArrayList arrayListColumnTestData= new ArrayList();
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                while(cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch(cell.getCellType()) {
                        case Cell.CELL_TYPE_BOOLEAN:
                            boolean valueBoolean = cell.getBooleanCellValue();
                            arrayListColumnTestData.add(String.valueOf(valueBoolean));
                            break;
                        case Cell.CELL_TYPE_NUMERIC:
                            double valueDouble = cell.getNumericCellValue();
                            arrayListColumnTestData.add(String.valueOf(valueDouble));
                            break;
                        case Cell.CELL_TYPE_STRING:
                            String valueString  = cell.getStringCellValue();
                            arrayListColumnTestData.add(valueString);
                            break;
                        case Cell.CELL_TYPE_BLANK:
                            arrayListColumnTestData.add(null);
                            break;
                    }
                }
                arrayListRowTestData.add(arrayListColumnTestData);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayListRowTestData;
    }
}

