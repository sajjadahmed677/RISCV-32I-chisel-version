// See LICENSE.txt for license details.
package merl

import chisel3._
import chisel3.util.experimental.loadMemoryFromFile
class Fetch extends Module {
  val io = IO(new Bundle {
    var wrAddr  = Input(UInt(32.W))
    val rdData  = Output(UInt(32.W))
  })
  val mem =Mem(1024, UInt(32.W))
  io.rdData := mem(io.wrAddr)
  loadMemoryFromFile(mem,"/home/sajjad/abc.txt")
}
