package converter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAndStringConverter {
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    
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

}
