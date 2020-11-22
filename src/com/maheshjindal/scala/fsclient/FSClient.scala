package com.maheshjindal.scala.fsclient


import com.maheshjindal.scala.fsclient.commands.Command
import com.maheshjindal.scala.fsclient.statemanager.State
import com.maheshjindal.scala.fsclient.storage.Directory
import com.maheshjindal.scala.fsclient.utils.IOUtils

import scala.io.Source

object FSClient {
  def main(args: Array[String]): Unit = {

    val _root = Directory.ROOT
    Source.stdin.getLines().foldLeft(State(_root,_root))((currState,newLine) => {
      currState.show
      Command.from(newLine).apply(currState)
    })
    /* var _state = State(_root,_root)
     val _scanner = IOUtils.getScannerInstance()

     while (true){
       _state.show
       val userInput = _scanner.nextLine()

       // Adds the new command entered by user
       _state = Command.from(userInput).apply(_state)


     }*/
  }
}
