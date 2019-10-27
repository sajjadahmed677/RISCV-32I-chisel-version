package merl
import chisel3._
class PriorityEncoder extends Module
{
val io= IO(new Bundle{
   val I0 = Input(UInt(1.W))
   val I1 = Input(UInt(1.W))
   val I2 = Input(UInt(1.W))
   val I3 = Input(UInt(1.W))
   val I4 = Input(UInt(1.W))
   val I5 = Input(UInt(1.W))
   val I6 = Input(UInt(1.W))
   val I7 = Input(UInt(1.W))
   val out1 = Output(UInt(1.W))
   val out2 = Output(UInt(1.W))
   val out3 = Output(UInt(1.W))
 
})
io.out1 := io.I1 | io.I3 | io.I5 | io.I7
io.out2 := io.I2 | io.I3 | io.I6 | io.I7
io.out3 := io.I4 | io.I5 | io.I6 | io.I7
}
