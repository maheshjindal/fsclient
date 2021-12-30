package com.maheshjindal.scala.fsclient.commands

object CommandParser {

  def parse(input: String): Command = {
    val tokens: Array[String] = input.split(" ")

    if(tokens.isEmpty || tokens.length == 0) return new EmptyCommand

    val headCmd = tokens(0)
    if(tokens.length < 1) return new IncompleteCommand(headCmd)
//    val userCmd = tokens(1)
    val operation = Operation.valueOf(headCmd)
    operation match {
      case Operation.MKDIR => new MkDir(tokens(1))
      case Operation.LS => new Ls
      case Operation.TOUCH => new Touch(tokens(1))
      case Operation.PWD => new Pwd
      case _ => new UnsupportedCommand
    }
  }
}
