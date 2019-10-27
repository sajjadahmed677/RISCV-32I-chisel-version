// See LICENSE.txt for license details.
package merl
import chisel3._
 import chisel3.Bool

class Forward extends Module {
    val io = IO(new Bundle {
    val EMregW   = Input(UInt(1.W))
    val EMrd     = Input(UInt(5.W))
    val IDrs1    = Input(UInt(5.W))
    val IDrs2    = Input(UInt(5.W))
    val MBregW   = Input(UInt(1.W))
    val MBrd     = Input(UInt(5.W))
    val out1     = Output(UInt(2.W))
    val out2     = Output(UInt(2.W))
  })
   //execution hazard and MEM hazard
   when((io.EMregW === 1.U) && (io.EMrd =/= 0.U) && (io.EMrd === io.IDrs1))  
   {
   io.out1 := "b10".U
   }
    .elsewhen((io.MBregW === 1.U) && (io.MBrd=/= 0.U) && (io.MBrd=== io.IDrs1)) 
   {
   io.out1 := "b01".U
   }
   .otherwise 
   {
      io.out1 := "b00".U
   }
   when((io.EMregW === 1.U) && (io.EMrd =/= 0.U) && (io.EMrd === io.IDrs2))  
   {
   io.out2 := "b10".U
   }
   .elsewhen((io.MBregW === 1.U) && (io.MBrd=/= 0.U) && (io.MBrd=== io.IDrs2)) 
   {
   io.out2 := "b01".U
   }
  .otherwise
  {
       io.out2 := "b00".U
  }
}
