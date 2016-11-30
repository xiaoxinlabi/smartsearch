package info.puton.product.common.util;

import org.apache.poi.ss.usermodel.Cell;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by taoyang on 2016/11/23.
 */
public class ExcelUtil {

    public static String getStringValue(Cell cell){
        if(cell!=null){
            switch (cell.getCellType()){
                case Cell.CELL_TYPE_STRING :
                    return cell.getStringCellValue();
                case Cell.CELL_TYPE_NUMERIC :

                    double doubleVal = cell.getNumericCellValue();
                    long longVal = Math.round(doubleVal);
                    if (Double.parseDouble(longVal + ".0") == doubleVal) {
                        return String.valueOf(longVal);
                    } else {
                        return String.valueOf(doubleVal);
                    }
                case Cell.CELL_TYPE_BLANK :
                    return null;
                default:
                    return null;
            }
        } else {
            return null;
        }

    }

    public static String getTrimValue(Cell cell){
        if(cell!=null){
            return getStringValue(cell).replace(" ", "");
        } else {
            return null;
        }
    }

}
