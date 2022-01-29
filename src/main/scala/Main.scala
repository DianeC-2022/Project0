import java.sql.{Connection, DriverManager, SQLException}
import scala.io.StdIn.{readInt, readLine}

object Main {
  case class handyman_employees()

  def displayJobs(con: Connection) : Unit = {

    val statement = con.createStatement
    val rs = statement.executeQuery("SELECT * FROM available_jobs ORDER BY Job_ID DESC LIMIT 5")
    var jobName = ""
    var jobId = 0
    var employeeId = 0

    while(rs.next) {

      jobName = rs.getString("JobDescription")
      jobId = rs.getInt("Job_ID")
      employeeId = rs.getInt("employee_id")
      println("Job Desc: " + jobName + " ")
      println("Job ID: " + jobId)

    }



  }

  def jobsPerEmployee(con: Connection) : Unit = {

    val statement = con.createStatement
    println("Enter employee ID to show assigned Jobs")
    val empId = readInt()
    var empName = ""

    val rsTwo = statement.executeQuery(s"SELECT * FROM employees WHERE id = $empId")
    while(rsTwo.next()) {
      empName = rsTwo.getString("name")
    }

    val rs = statement.executeQuery(s"SELECT * FROM available_jobs WHERE employee_id = $empId")
    var jobName = ""
    var jobId = 0
    var employeeId = 0

    println(
      s"""
        |*****************
        |Jobs for $empName
        |*****************
        |""".stripMargin)

    while(rs.next) {

      jobName = rs.getString("JobDescription")
      jobId = rs.getInt("Job_ID")
      employeeId = rs.getInt("employee_id")
      println("Job Desc: " + jobName + " ")
      println("Job ID: " + jobId)

    }
  }

  def displayByJobId(con: Connection) : Unit = {

    println("Enter a job Id to search")
    val jobIdLookup = readInt()

    val statement = con.createStatement

    try {
      val rs = statement.executeQuery(s"SELECT * FROM available_jobs WHERE Job_ID = $jobIdLookup")
      var jobName = ""
      var jobId = 0
      var employeeId = 0

      while(rs.next) {

        jobName = rs.getString("JobDescription")
        jobId = rs.getInt("Job_ID")
        employeeId = rs.getInt("employee_id")
        println("Job Desc: " + jobName + " ")
        println("Job ID: " + jobId)
      }
    } catch {
      case e: Exception =>
        println("Job not found")
    }
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

  def deleteJob(con:Connection) : Unit = {

    println("Enter job Id to delete")
    val jobId = readInt()


    val rs = "DELETE FROM available_jobs WHERE Job_ID = ?"

    val preparedStmt = con.prepareStatement(rs)
    preparedStmt.setInt(1,jobId)
    preparedStmt.execute()

    println(s"Job Id $jobId Delete Successful")
  }

  def menu(): Unit = {
    val connection = new dbConnector
    val con = connection.dbConnection()

    println(
      """
        |Main Menu
        |1. Create New Job
        |2. Update Job
        |3. Delete Job
        |4. Show all Jobs
        |5. Search Jobs For Employees
        |6. Search by Job Id
        |7. Quit
        |""".stripMargin)

    val menuSelection = readInt()

    menuSelection match {
      case 1 =>
        insertNewJob(con)
        menu()


      case 2 =>
        updateJob(con)
        menu()


      case 3 =>
        deleteJob(con)
        menu()


      case 4 =>
        displayJobs(con)
        menu()


      case 5 =>
       jobsPerEmployee(con)
        menu()


      case 6 =>
        displayByJobId(con)
        menu()


      case 7 =>
        System.exit(1)


    }
  }

  def main(args: Array[String]): Unit = {
    println("Welcome to the Handyman Services.")
    println("Please make a selection from the menu")

    val connection = new dbConnector

    val con = connection.dbConnection()
    /*displayJobs(con)
    insertNewJob(con)
    displayJobs(con)
    updateJob(con)
    displayJobs(con)
    deleteJob(con)
    displayJobs(con)*/
    menu()


    val results = "SELECT * from handyman_employees.available_jobs"

      }


    }

