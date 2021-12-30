package com.maheshjindal.scala.fsclient.commands

object Operation extends Enumeration {
  type Operation = Value
  val MKDIR = Value("mkdir")
  val LS = Value("ls")
  val TOUCH = Value("touch")
  val PWD = Value("pwd")
  val UNSUPPORTED = Value("unsupported")

  def valueOf(userOperation : String): Operation = {
    var result: Operation = UNSUPPORTED
    if (userOperation.isEmpty) return result
    values.foreach( operationVal =>
      result = if(operationVal.toString.equalsIgnoreCase(userOperation)) operationVal else result
    )
    result
  }
}
