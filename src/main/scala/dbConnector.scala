import java.sql.DriverManager
import java.sql.Connection

class dbConnector {

  def dbConnection(): Connection = {

    val MySQLURL = "jdbc:mysql://localhost:3306/handyman_employees"
    val databaseUserName = "root"
    val databasePassword = "$Chayanne04"
    val driver = "com.mysql.cj.jdbc.Driver"

    val con = DriverManager.getConnection(MySQLURL,databaseUserName,databasePassword)

    try {
      Class.forName(driver)
      val con = DriverManager.getConnection(MySQLURL,databaseUserName,databasePassword)
    }catch {
      case e: Exception =>
        e.printStackTrace()
    }
    return con

  }
}
