package merl 
import chisel3._
import chisel3.util.Cat

class AluControll extends Module
{
	val io=IO(new Bundle{
		val func3 = Input(UInt(3.W))
		val func7 = Input(UInt(7.W))
		val Aluop = Input(UInt(3.W))
		val out  = Output(UInt(5.W))
})
	when(io.Aluop==="b011".U)//jalr
	{io.out := "b11111".U}
	.elsewhen(io.Aluop=== "b010".U)//branches
	{io.out := Cat(io.Aluop,io.func3)}	
	//R-type
	.elsewhen((io.Aluop==="b000".U)&&(io.func7==="b0000000".U))
	{io.out := Cat(io.Aluop,io.func3)}
	.elsewhen((io.Aluop==="b000".U)&&(io.func7==="b0100000".U))
	{io.out := Cat("b001".U,io.func3)}
	.elsewhen(io.Aluop==="b000".U)
	{io.out := Cat(io.Aluop,io.func3)}
	// I-type
	.elsewhen((io.Aluop==="b001".U)&&(io.func3==="b101".U)&&(io.func7==="b0100000".U))
	{io.out := Cat("b000".U,io.func3)}
	.elsewhen((io.Aluop==="b001".U)&&(io.func3==="b101".U)&&(io.func7==="b0100000".U))
	{io.out := Cat("b001".U,io.func3)}
	.elsewhen((io.Aluop==="b001".U)&&(io.func3==="b101".U))
	{io.out := Cat("b000".U,io.func3)}
	.elsewhen(io.Aluop==="b001".U)
	{io.out := Cat("b000".U,io.func3)}
	.otherwise
	{io.out := "b00000".U}		
}
