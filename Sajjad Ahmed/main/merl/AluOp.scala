package merl 
import chisel3._

class AluOp extends Module
{
	val io= IO(new Bundle{
		val R_type = Input(UInt(1.W))
		val I_type = Input(UInt(1.W))
		val store = Input(UInt(1.W))
		val load  = Input(UInt(1.W))
		val Branch = Input(UInt(1.W))
		val JALR = Input(UInt(1.W))
		val JAL = Input(UInt(1.W))
		val Lui = Input(UInt(1.W))
		val Aluop = Output(UInt(3.W))
})
	when(io.R_type===1.U)
	{io.Aluop:="b000".U}
	.elsewhen(io.I_type===1.U)
	{io.Aluop:= "b001".U}
	.elsewhen(io.store===1.U)
	{io.Aluop:= "b101".U}
	.elsewhen(io.load===1.U)
	{io.Aluop:= "b100".U}
	.elsewhen(io.Branch===1.U)
	{io.Aluop:= "b010".U}
	.elsewhen((io.JALR===1.U)||(io.JAL===1.U))
	{io.Aluop:= "b011".U}
	.elsewhen(io.Lui===1.U)
	{io.Aluop:= "b110".U}
	.otherwise{io.Aluop:= "b111".U}
}
