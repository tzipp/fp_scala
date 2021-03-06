package fpinscala.datastructures

sealed trait List[+A]
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

object List {
  def sum(ints: List[Int]): Int = ints match {
    case Nil => 0
    case Cons(x, xs) => x + sum(xs)
  }

  def product(ds: List[Double]): Double = ds match {
    case Nil => 1.0
    case Cons(0.0, _) => 0.0
    case Cons(x, xs) => x * product(xs)
  }

  def combine[A](lst: List[A])(f: (A, A) => A, term: A): A = lst match {
    case Nil => term
    case Cons(h, t) => f(h, combine(t)(f, term))
  }

  def identity[A](lst: List[A]): List[A] = lst match {
    case Nil => Nil
    case Cons(x, xs) => Cons(x, xs)
  }

  def tail[A](lst: List[A]): List[A] = lst match {
    case Nil => Nil
    case Cons(x, xs) => xs
  }

  def head[A](lst: List[A]): A = lst match {
    case Cons(x, xs) => x
  }

  def setHead[A](newHead: A, lst: List[A]): List[A] = {
    if (newHead != Nil) {
      lst match {
        case Nil => Cons(newHead, Nil)
        case Cons(x, xs) => Cons(newHead, xs)
      }
    }
    else Nil
  }

  def drop[A](l: List[A], n: Int): List[A] = n match {
    case 0 => l
    case n => drop(tail(l), n - 1)
  }

  def dropWhile[A](lst: List[A], f: A => Boolean): List[A] = lst match {
    case Nil => Nil
    case Cons(x, xs) =>
      if (f(x)) {
        dropWhile(xs, f)
      }
      else {
        Cons(x, dropWhile(xs, f))
      }
  }

  def append[A](a1: List[A], a2: List[A]): List[A] =
    a1 match {
      case Nil => a2
      case Cons(h, t) => Cons(h, append(t, a2))
    }

  def init[A](lst: List[A]): List[A] = lst match {
    case Cons(term, Nil) => Nil // Excise the terminal node.
    case Cons(h, t) => Cons(h, init(t))
    case _ => Nil
  }

  def dropWhileInfer[A](as: List[A])(f: A => Boolean): List[A] =
    as match {
      case Cons(h, t) if f(h) => dropWhileInfer(t)(f)
      case Cons(h, t) if !f(h) => Cons(h, dropWhileInfer(t)(f))
      case _ => as
    }

  def foldRight[A, B](as: List[A], z: B)(f: (A, B) => B): B =
    as match {
      case Nil => z
      case Cons(x, xs) => f(x, foldRight(xs, z)(f))
    }

  def foldRightShort[A, B](as: List[A], z: B, short: A)(f: (A, B) => B): B =
    as match {
      case Nil => z
      case Cons(short, Nil) => z
      case Cons(x, xs) if x == short => f(short, foldRightShort(Cons(short, Nil), z, short)(f))
      case Cons(x, xs) => f(x, foldRightShort(xs, z, short)(f))
    }

  def sum2(ns: List[Int]) =
    foldRight(ns, 0)((x, y) => x + y)

  def product2(ns: List[Double]) =
    foldRight(ns, 1.0)(_ * _)

  def length[A](as: List[A]): Int = {
    foldRight(as, 0)((x, y) => 1 + y)
  }

  def foldLeft[A, B](as: List[A], z: B)(f: (A, B) => B): B = {
    @annotation.tailrec
    def loop(lst: List[A], acc: B): B = {
      if (lst == Nil) acc
      else loop(tail(lst), f(head(lst), acc))
    }
    loop(tail(as), f(head(as), z))
  }

  def sum3(ns: List[Int]) =
    foldLeft(ns, 0)(_ + _)

  def product3(ns: List[Double]) =
    foldLeft(ns, 1.0)(_ * _)

  def length2[A](lst: List[A]): Int =
    foldLeft(lst, 0)((x, y) => 1 + y)

  def plusOne(lst: List[Int]): List[Int] =
    foldRight(lst, Nil: List[Int])((x, y) => Cons(x + 1, y))

  def dubStr(lst: List[Double]): List[String] =
    foldRight(lst, Nil: List[String])((x, y) => Cons(x.toString, y))

  def map[A, B](as: List[A])(f: A => B): List[B] =
    foldRight(as, Nil: List[B])((x, y) => Cons(f(x), y))

  def append2[A](lst: List[A], a2: List[A]): List[A] =
    foldRight(lst, a2)((x, y) => Cons(x, y))

  def reverse[A](lst: List[A]): List[A] =
    foldLeft(lst, Nil: List[A])((x, y) => Cons(x, y))

  def filter[A](lst: List[A])(f: A => Boolean): List[A] = lst match {
    case Nil => Nil
    case Cons(x, xs) =>
      if (!f(x)) {
        filter(xs)(f)
      }
      else {
        Cons(x, filter(xs)(f))
      }
  }

  def transformAppend[A, B](a: A, bs: List[B])(f: A => List[B]): List[B] = {
    append(f(a), bs)
  }

  def flatMap[A, B](as: List[A])(f: A => List[B]): List[B] =
    foldRight(as, Nil: List[B])((x, y) => transformAppend(x, y)(f))

  def filter2[A](lst: List[A])(f: A => Boolean): List[A] =
    flatMap(lst)(x => {
      if (f(x)) List(x) else Nil: List[A]
    })

  def addTwo(a1: List[Int], a2: List[Int]): List[Int] = {
    def loop(lst1: List[Int], lst2: List[Int], acc: List[Int]): List[Int] = {
      if (lst1 == Nil) reverse(acc)
      else if (lst2 == Nil) reverse(acc)
      else {
        loop(tail(lst1), tail(lst2), Cons(head(lst1) + head(lst2), acc))
      }
    }
    loop(a1, a2, Nil: List[Int])
  }

  def zipWith[A, B](a1: List[A], a2: List[A])(f: (A, A) => B) = {
    def loop(lst1: List[A], lst2: List[A], acc: List[B]): List[B] = {
      if (lst1 == Nil) reverse(acc)
      else if (lst2 == Nil) reverse(acc)
      else {
        loop(tail(lst1), tail(lst2), Cons(f(head(lst1), head(lst2)), acc))
      }
    }
    loop(a1, a2, Nil: List[B])
  }


  def apply[A](as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head, apply(as.tail: _*))
}