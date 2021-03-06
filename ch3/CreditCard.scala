/**
 * www.seas.upenn.edu/~cis194/hw/01-intro.pdf
 */

/**
 * Exercise 1
 */
object CreditCard extends App {

def lastDigit(num: Long): Int = (num % 10).toInt
def dropLastDigit(num: Long): Long = num / 10

/**
  * Exercise 2
  */

def toDigits(num: Long): List[Int] = {
  def loop(num: Long, acc: List[Int]):List[Int] = {
    if (num == 0) acc
    else if (num < 0) List[Int]()
    else loop(dropLastDigit(num), lastDigit(num)::acc)
  }
  loop(num, List[Int]())
}

def toRevDigits(nums: List[Int]):List[Int] = nums.reverse

/**
 * Exercise 3
 */
def doubleEveryOther(nums:List[Int]):List[Int] = {
  val len = nums.length

  def loop(index: Int, con: List[Int], acc: List[Int]):List[Int] = {
    if (index > len) acc.reverse
    else {
      if (index % 2 == 0) {
        loop(index+1, con.tail, (2 * con.head)::acc)
      }
      else {
        loop(index+1, con.tail, con.head::acc)
      }
    }
  }
  loop(1, nums, List[Int]())
}

def numDigits(num: Int): Int = {
  def loop(i: Int, acc: Int): Int = {
    if (i == 0) acc
    else {
      loop(i/10, acc + 1)
    }
  }
  loop(num, 0)
}

/**
  * Exercise 4
  */

def sumDigit(n: Int): Int = {
  def loop(i: Int, acc: Int): Int = {
    if (i == 0) acc
    else loop(i / 10, acc + i % 10)
  }
  loop(n, 0)
}

def sumDigits(digits: List[Int]): Int = digits match {
  case Nil => 0
  case x :: xs => sumDigit(x) + sumDigits(xs)
}

def sumDigits2(digits: List[Int]): Int = {
  def loop(lst: List[Int], acc: Int): Int = {
    if (lst.isEmpty) acc
    else loop(lst.tail, acc + sumDigit(lst.head))
  }
  loop(digits, 0)
}

/**
  * Exercise 5
  */

def luhn(creditCardNum: Long): Boolean = {
  val ccDigitsReversed = toDigits(creditCardNum).reverse
  val doubledEveryOther = doubleEveryOther(ccDigitsReversed)
  sumDigits(doubledEveryOther) % 10 == 0
}

  val cc1: Long = 5594589764218858L
  println(cc1)
  println("Is it a valid credit card?")
  println(luhn(cc1))
}