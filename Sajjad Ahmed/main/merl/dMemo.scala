// See LICENSE.txt for license details.
package merl

import chisel3._

// Problem:
//
// Implement a dual port memory of 256 8-bit words.
// When 'wen' is asserted, write 'wrData' to memory at 'wrAddr'
// When 'ren' is asserted, 'rdData' holds the output
// of reading the memory at 'rdAddr'
//
class dMemo extends Module {
  val io = IO(new Bundle {
    val wen     = Input(Bool())
    val wrAddr  = Input(UInt(32.W))
    val wrData  = Input(UInt(32.W))
    val ren     = Input(Bool())
    val rdData  = Output(UInt(32.W))
  })

  val mem =Mem(1024, UInt(32.W))
    io.rdData := 0.U 
 
  // write
  when (io.wen) 
	{ mem.write(io.wrAddr,io.wrData)}

 when (io.ren) 
	{io.rdData := mem.read(io.wrAddr) }

}
