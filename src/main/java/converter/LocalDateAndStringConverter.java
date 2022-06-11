package converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAndStringConverter {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    private static DateTimeFormatter formatterwithtime = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public static LocalDate stringToLocalDate(String s) {
    	if(s != null)
    		return LocalDate.parse(s, formatter);
    	else
    		return null;
    }
    
    public static String localDateToString(LocalDate ld) {
    	if (ld != null)
    		return ld.format(formatter);
    	else
    		return null;
    }
    
    public static LocalDate stringToLocalDateCheckin(String s) {
    	if(s != null)
    		return LocalDate.parse(s, formatterwithtime);
    	else
    		return null;
    }
    
    public static String localDateToStringCheckin(LocalDate ld) {
    	if (ld != null)
    		return ld.format(formatterwithtime);
    	else
    		return null;
    }

}
