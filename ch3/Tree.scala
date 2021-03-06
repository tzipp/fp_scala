sealed trait Tree[+A]
case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

object Tree {
  def size[A](tree: Tree[A]): Int = tree match {
    case Leaf(a) => 1
    case Branch(left, right) => 1 + size(left) + size(right)
  }

  def maximum(tree: Tree[Int]): Int = tree match {
    case Leaf(x) => x
    case Branch(left, right) => maximum(left) max maximum(right)
  }

  def maxTerminalBranch(tree: Tree[Int]): Int = tree match {
    case Branch(left: Leaf[Int], right: Leaf[Int]) => left.value.max(right.value)
  }

  def depth[A](tree: Tree[A]): Int = tree match {
    case Leaf(x) => 1
    case Branch(left, right) => (1 + depth(left)).max(1+ depth(right))
  }

  def cloneTree[Int](treeIn: Tree[Int]):Tree[Int] = {
    treeIn match {
      case Leaf(x) => Leaf(x)
      case Branch(left, right) => Branch(cloneTree(left), cloneTree(right))
    }
  }

  def map[A, B](treeIn: Tree[A])(f: A => B):Tree[B] = {
    treeIn match {
      case Leaf(x) => Leaf(f(x))
      case Branch(left, right) => Branch(map(left)(f), map(right)(f))
    }
  }

}