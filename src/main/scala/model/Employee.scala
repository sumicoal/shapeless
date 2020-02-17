package model

case class Employee(
  name:    String,
  number:  Int,
  manager: Boolean
) {

}

object Employee{
  def employeeCsv(e: Employee): List[String] =
    List(e.name, e.number.toString, e.manager.toString)
}
