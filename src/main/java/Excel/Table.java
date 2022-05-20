package Excel;

import java.util.List;

import lombok.Data;
@Data
public class Table {
    private List<Col> cols;
    private List<Row> rows;
    private int parsedNumHeaders;

}
