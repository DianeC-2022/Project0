import java.sql.DriverManager
import java.sql.Connection
import scala.util.Random
import scala.io.Source
import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn.{readInt, readLine}

object Main {
  case class handyman_employees()

  def displayJobs(con: Connection) : Unit = {

    val statement = con.createStatement
    val rs = statement.executeQuery("SELECT * FROM available_jobs")
    var jobName = ""
    var jobId = 0
    var employeeId = 0

    while(rs.next) {
      jobName = rs.getString("JobDescription")
      jobId = rs.getInt("Job_ID")
      employeeId = rs.getInt("employee_id")
    }

    println(jobName,jobId,employeeId)

  }

  def insertNewJob(con:Connection) : Unit = {

    val newJob = readLine("Enter a new job")
    println("Enter employee Id")
    val empId = readInt()

    val rs = "INSERT INTO available_jobs (JobDescription, employee_id) VALUES (?,?)"

    val preparedStmt = con.prepareStatement(rs)
    preparedStmt.setString(1,newJob)
    preparedStmt.setInt(2,empId)
    preparedStmt.execute()

    println("Insert successful")
  }

  def updateJob(con:Connection) : Unit = {

    println("Enter job Id to update")
    val jobId = readInt()
    val newJob = readLine("Enter a new job description")
    println("Enter new employee Id")
    val empId = readInt()

    val rs = "UPDATE available_jobs SET JobDescription = ?, employee_id = ? WHERE Job_ID = ?"

    val preparedStmt = con.prepareStatement(rs)
    preparedStmt.setString(1,newJob)
    preparedStmt.setInt(2,empId)
    preparedStmt.setInt(3,jobId)
    preparedStmt.execute()

    println("Update successful")
  }

  def main(args: Array[String]): Unit = {
    println("Welcome to the Handyman Services.")
    println("Which job would you like to select?")

    val connection = new dbConnector

    val con = connection.dbConnection()
    //displayJobs(con)
    //insertNewJob(con)
    updateJob(con)


    val results = ("SELECT * from handyman_employees.available_jobs")

      }


    }

