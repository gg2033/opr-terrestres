package Excel;

import java.util.ArrayList;

import lombok.Data;
@Data
public class Table {
    private ArrayList<Col> cols;
    private ArrayList<Row> rows;
    private int parsedNumHeaders;

}
