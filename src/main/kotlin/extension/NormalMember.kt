package cc.gxstudio.gmanager.extension

import net.mamoe.mirai.contact.NormalMember

fun NormalMember.warn(reason:String?){
   warnTimes+1
}



var NormalMember.warnTimes:Int
    get()  {
      return  0
    }
    set(value) {
        //todo:完成读取与写入设置
    }