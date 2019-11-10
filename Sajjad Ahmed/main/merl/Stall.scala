package merl
import chisel3._
class Stall extends Module
{
	val io= IO(new Bundle{
    val EXwrite    = Input(UInt(1.W))
		val EMrd       = Input(UInt(5.W))
    val EMread     = Input(UInt(1.W))
    val IDrs1      = Input(UInt(5.W))
    val IDrs2      = Input(UInt(5.W))
    val out1       = Output(UInt(1.W))
    val out2       = Output(UInt(1.W))
    val out3       = Output(UInt(1.W))

})

 when ((io.EXwrite === 0.U)&&(io.EMread === "b1".U) && ((io.EMrd===io.IDrs1)||(io.EMrd===io.IDrs2)))
 {
   io.out1 := "b1".U
   io.out2 := "b1".U
   io.out3 := "b1".U
 }
.otherwise
{
  io.out1 := 0.U
  io.out2 := 0.U
  io.out3 := 0.U
}
	

}
