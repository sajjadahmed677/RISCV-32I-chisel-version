// See LICENSE.txt for license details.
package merl

import chisel3._

object main  extends App {

 chisel3.Driver.emitVerilog(new RegFile) //<:  new BRISC_V_Core)
}
//
class RegFile extends Module {
  val io = IO(new Bundle {
    val wEn        = Input(Bool())
    val write_data = Input(UInt(32.W))
    val read_sel1  = Input(UInt(5.W))
    val read_sel2  = Input(UInt(5.W))
    val write_sel  = Input(UInt(5.W))
    val read_data1 = Output(UInt(32.W))
    val read_data2 = Output(UInt(32.W))
  //  val regout     = Output(UInt(32.W))
  })
   val regFile=Reg( Vec(32, UInt(32.W)))
	  regFile(0) := 0.U
  

      when((io.wEn) && (io.write_sel > 0.U ))
      {  regFile(io.write_sel) := io.write_data}
     //.otherwise{
    //   regFile(0) := 0.U
      //}

	io.read_data1 := regFile(io.read_sel1)
  	io.read_data2 := regFile(io.read_sel2)
  //  io.regout := regFile(7)
}
