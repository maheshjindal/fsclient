package com.maheshjindal.scala.fsclient.storage

import java.io.FileNotFoundException

import com.maheshjindal.scala.fsclient.fs.File
import com.maheshjindal.scala.fsclient.utils.FSClientEnvProperties

import scala.annotation.tailrec

/**
 * A Directory class which maintains the properties and state of a particular directory
 *
 * @param parentPath
 * @param name
 * @param dirContents
 */

class Directory(override val parentPath: String, override val name: String, val dirContents: List[DirReference])
  extends DirReference(parentPath, name) {

  /**
   * Removes the directory reference for a particular folder from the dirContents
   *
   * @param entryName Name of the directory
   * @return Updated reference for the directory.
   */
  def removeEntry(entryName: String): Directory = {
    if (!hasEntry(entryName)) this
    else new Directory(parentPath, name, dirContents.filter(c => !c.name.equals(entryName)))
  }


  override def toFile: File = throw new UnsupportedOperationException("Directory can't be converted to a file")

  /**
   * Returns the directory reference of the referring folder from the nested directory structure
   * If ["a","b","c","d"] is the existing structure of the desired folder, then this method will
   * return the reference to folder "d"
   *
   * @param existingDirs List of the directory names
   * @return the Reference of targeted directory
   */
  def findDescandants(existingDirs: List[String]): Directory = {
    if (existingDirs.isEmpty) this
    else findEntry(existingDirs.head).toDirectory.findDescandants(existingDirs.tail)
  }

  /**
   * Uses {@link Directory#findDescandants(existingDirs: List[String])} for finding descandants from a particular path
   *
   * @param relativePath The relative path of particular directory
   * @return
   */
  def findDescandants(relativePath: String): Directory = {
    if (relativePath.isEmpty) this
    else findDescandants(relativePath.split(Directory.SEPARATOR).toList)
  }


  def replaceEntry(dirName: String, directory: DirReference): Directory = {
    new Directory(parentPath, name, dirContents.filter(c => c.name.equals(dirName)) :+ directory)
  }

  /**
   * Adds a particular directory reference to the contents list
   * @param newEntry
   * @return
   */
  def addEntry(newEntry: DirReference) = {
    new Directory(parentPath, name, dirContents :+ newEntry)
  }

  /**
   * Returns the directory reference from the directory name.Note for every directory a particular reference is generated
   *
   * @param name Directory name
   * @return Directory reference
   */
  def findEntry(name: String): DirReference = {
    @tailrec
    def _findEntry(name: String, dirRefList: List[DirReference]): DirReference = {
      if (dirRefList.isEmpty) null
      else if (dirRefList.head.name.equals(name)) dirRefList.head
      else _findEntry(name, dirRefList.tail)
    }
    _findEntry(name, dirContents)
  }


  /**
   * Checks if the directory reference with a particular name is present or not
   *
   * @param name the
   * @return
   */
  def hasEntry(name: String): Boolean = findEntry(name) != null

  /**
   * Retries the list of directory names from a particular path
   * For example: If the path is '/a/b/c/d' then it will return ["a","b","c","d"]
   *
   * @return Directory names list
   */
  def getAllDirsInPath(): List[String] = path.substring(1).split(Directory.SEPARATOR).toList.filter(d => !d.isEmpty)

  /**
   * Checks if the particular directory is root or not
   * @return
   */
  def isRoot = parentPath.isEmpty

  /**
   * Converts a particular 'DirReference' to an existing 'Directory' instance
   *
   * @return Directory reference
   */
  override def toDirectory: Directory = this

  def getType: String = "Directory"
}


object Directory {
  /**
   * Separator used for the directory
   */
  val SEPARATOR = FSClientEnvProperties.SEPARATOR
  /**
   * The root path of file system
   */
  val ROOT_PATH = "/"

  /**
   * Creates a root directory
   *
   * @return
   */
  def ROOT: Directory = empty("", "")

  /**
   * Creates an empty directory with a particular parent path and directory name
   *
   * @param parentPath Parent path of particular directory
   * @param name Name of the directory
   * @return Reference of newly created empty directory
   */
  def empty(parentPath: String, name: String): Directory = new Directory(parentPath, name, List())

}