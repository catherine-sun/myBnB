import java.sql.ResultSet;
import java.sql.Statement;

public class QueryResult {
    public Statement stmt;
    public ResultSet rs;
    public int rowsChanged;

    public QueryResult (Statement s, ResultSet r) {
        this.stmt = s;
        this.rs = r;
    }

    public QueryResult (Statement s, ResultSet r, int rowsChanged) {
        this.stmt = s;
        this.rs = r;
        this.rowsChanged = rowsChanged;
    }
}
