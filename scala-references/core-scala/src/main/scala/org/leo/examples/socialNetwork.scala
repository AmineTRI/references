package org.leo.examples

/**
  * This is uncommon but we name these type of object in lower camel case
  * when we mimic a package of a function
  */
object socialNetwork {
  def createSessionFor(user: String, pwd: String) = new Session(user, pwd)

  class Session(user: String, pwd: String) {
    private val friends: List[Friend] = Nil
    private val posts: List[Post] = Nil

    def getFriends() = friends

    def getPosts() = posts
  }

  case class Friend(name: String)

  case class Post(text: String)
}
