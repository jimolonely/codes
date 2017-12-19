## [case class和class的区别](https://www.scala-exercises.org/scala_tutorial/classes_vs_case_classes)
以以下两个class为例:

class
```scala
class BankAccount {

  private var balance = 0

  def deposit(amount: Int): Unit = {
    if (amount > 0) balance = balance + amount
  }

  def withdraw(amount: Int): Int =
    if (0 < amount && amount <= balance) {
      balance = balance - amount
      balance
    } else throw new Error("insufficient funds")
}
```
case class
```scala
case class Note(name: String, duration: String, octave: Int)
```
## 1.创建方式
一个要new,一个不用
```scala
val aliceAccount = new BankAccount
val c3 = Note("C", "Quarter", 3)
```
## 2.对象比较
```scala
val aliceAccount = new BankAccount
val bobAccount = new BankAccount

aliceAccount == bobAccount //false

val c3 = Note("C", "Quarter", 3) //不变性
val cThree = Note("C", "Quarter", 3)

c3 == cThree //true
```
## 3.模式匹配
只有case class支持:
```scala
c3 match {
  case Note(name, duration, octave) => s"The duration of c3 is $duration"
}
```
## 4.扩展性
class可继承,而case class是不变的
## 5.为什么需要case class
总的来说,case class就是class的特例,提取出来是因为实践中大量使用case class.

对于上面那个Note,扩展成class如下:
```scala
class Note(_name: String, _duration: String, _octave: Int) extends Serializable {

  // Constructor parameters are promoted to members
  val name = _name
  val duration = _duration
  val octave = _octave

  // Equality redefinition
  override def equals(other: Any): Boolean = other match {
    case that: Note =>
      (that canEqual this) &&
        name == that.name &&
        duration == that.duration &&
        octave == that.octave
    case _ => false
  }

  def canEqual(other: Any): Boolean = other.isInstanceOf[Note]

  // Java hashCode redefinition according to equality
  override def hashCode(): Int = {
    val state = Seq(name, duration, octave)
    state.map(_.hashCode()).foldLeft(0)((a, b) => 31 * a + b)
  }

  // toString redefinition to return the value of an instance instead of its memory addres
  override def toString = s"Note($name,$duration,$octave)"

  // Create a copy of a case class, with potentially modified field values
  def copy(name: String = name, duration: String = duration, octave: Int = octave): Note =
    new Note(name, duration, octave)

}

object Note {

  // Constructor that allows the omission of the `new` keyword
  def apply(name: String, duration: String, octave: Int): Note =
    new Note(name, duration, octave)

  // Extractor for pattern matching
  def unapply(note: Note): Option[(String, String, Int)] =
    if (note eq null) None
    else Some((note.name, note.duration, note.octave))

}
```
例子:
```scala
val c3 = Note("C", "Quarter", 3)
c3.toString shouldBe "Note(C,Quarter,3)"

val d = Note("D", "Quarter", 3)
c3.equals(d) shouldBe false

val c4 = c3.copy(octave = 4)
c4.toString shouldBe "Note(C,Quarter,4)"
```