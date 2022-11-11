package cc.gxstudio.gmanager.extension

import cc.gxstudio.gmanager.data.getGData
import net.mamoe.mirai.contact.NormalMember

fun NormalMember.warn() {
   warnTimes+=1
}



var NormalMember.warnTimes:Int
    get() = group.getGData().memberList[this.id]!!.warnTimes
    set(value) {
        group.getGData().memberList[this.id]!!.warnTimes=value
    }