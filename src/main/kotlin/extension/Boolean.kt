package cc.gxstudio.gmanager.extension
fun main(){
    println("Hello, world!")
    val pluginList = mapOf<String,Int>("net.mamoe.mirai-api-http" to 1,"io.github.karlatemp.luckperms-mirai" to 2)
    println(pluginList["net.mamoe.mirai-api-http"])
}