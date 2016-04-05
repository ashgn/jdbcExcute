import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.*;

/**
 * Created by ASH on 2016-04-04.
 */
public class Main {
    public static void main(String[] args) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl", "sa", "1234");

            BufferedReader in = new BufferedReader(new FileReader(args[0]));
            StringBuffer sb = new StringBuffer();
            String s;
            String column;

            while ((s = in.readLine()) != null) {
                sb.append(s);
                sb.append(" ");
            }

            in.close();

            pstmt = conn.prepareStatement(sb.toString());
            rs = pstmt.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            int sizeOfColumn = metaData.getColumnCount();

            while (rs.next()) {
                for (int indexOfcolumn = 0; indexOfcolumn < sizeOfColumn; indexOfcolumn++) {
                    column = metaData.getColumnName(indexOfcolumn + 1);
                    System.out.print(column + " = " + rs.getString(column) + "     ");
                }
                System.out.println("");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) try {
                pstmt.close();
            } catch (SQLException e) {
            }
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
            }
            if (conn != null) try {
                conn.close();
            } catch (SQLException e) {
            }
        }

    }

}
