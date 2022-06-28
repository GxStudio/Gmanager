package cc.gxstudio.gmanager.mysql

import java.sql.DriverManager


fun main() {
//    val connection = DriverManager.getConnection(
//        "jdbc:mysql://${MysqlConfig.ip}:${MysqlConfig.port}?" +
//            "user=${MysqlConfig.user}" +
//            "&password=${MysqlConfig.password}")
    val connection  = DriverManager.getConnection("jdbc:mysql://localhost:3306?user=root&password=Python@idk233")
    val statement = connection.createStatement()
    statement.execute("USE studyexample")
    statement.execute("SELECT * FROM orders")
    val resultset = statement.resultSet
    while (resultset.next()) {
        println(resultset.getInt("order_num"))
    }
    println(statement.resultSet)
}