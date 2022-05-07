//package converter;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//public class LocalDateAndStringConverter {
//    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/d");
//    
//    public static LocalDate stringToLocalDate(String s) {
//    	return LocalDate.parse(s, formatter);
//    }
//    
//    public static String localDateToString(LocalDate ld) {
//    	return ld.format(formatter);
//    }
//
//}
