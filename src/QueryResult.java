import java.sql.ResultSet;
import java.sql.Statement;

public class QueryResult {
    public Statement stmt;
    public ResultSet rs;

    public QueryResult (Statement s, ResultSet r) {
        this.stmt = s;
        this.rs = r;
    }
}
