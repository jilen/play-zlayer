package models

trait Err {
  def code: Int
  def msg: String
}
