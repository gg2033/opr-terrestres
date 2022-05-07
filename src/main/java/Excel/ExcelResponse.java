package Excel;

import lombok.Data;

@Data
public class ExcelResponse {
	
    private String version;
    private String reqId;
    private String status;
    private String sig;
    private Table table;

}
