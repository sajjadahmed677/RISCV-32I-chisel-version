package merl
import chisel3._
import chisel3.core.UInt._
class JALR extends Module
{
val io= IO(new Bundle{
    val a  = Input(UInt(32.W))
    val b  = Input(UInt(32.W))
    val out = Output(UInt(32.W))

})

        io.out:= (io.a + io.b) & "b11111111111111111111111111111110".U
	
}
