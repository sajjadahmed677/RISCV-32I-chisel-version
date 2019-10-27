package merl
import chisel3._

class OpAselect extends Module
{
	val io=IO(new Bundle{
		val JALR = Input(UInt(1.W))
		val JAL = Input(UInt(1.W))
		val Lui = Input(UInt(1.W))
		val OpAsel= Output(UInt(2.W))
})
	when((io.JALR===1.U)||(io.JAL===1.U))
	{io.OpAsel := "b10".U}
	.elsewhen(io.Lui===1.U)
	{io.OpAsel := "b11".U}
	.otherwise
	{io.OpAsel := "b00".U}
}
