package merl
import chisel3._
import chisel3.util.Cat
class ControlDecode extends Module
{
	val io=IO(new Bundle{
		val R_type=Input(UInt(1.W))
		val load=Input(UInt(1.W))
		val store=Input(UInt(1.W))
		val branch=Input(UInt(1.W))
		val I_type=Input(UInt(1.W))
		val JALR = Input(UInt(1.W))
		val JAL  = Input(UInt(1.W))
		val Lui  = Input(UInt(1.W))
		val Aluoperation=Output(UInt(3.W))
		val MemtoReg=Output(UInt(1.W))
		val Regwrite=Output(UInt(1.W))
		val MemRead=Output(UInt(1.W))
		val Memwrite=Output(UInt(1.W))
		val Branch=Output(UInt(1.W))
		val OpAsel=Output(UInt(2.W))
		val OpBsel=Output(UInt(1.W))
		val extendsel=Output(UInt(2.W))
		val nextpcsel=Output(UInt(2.W))
})
	io.Memwrite:= io.store
	io.Branch := io.branch
	io.MemRead := io.load
	io.Regwrite := (io.R_type | io.load | io.I_type | io.JALR | io.JAL | io.Lui)
	io.MemtoReg := io.load
// Aluoperation selection 	
	val aop= Module(new AluOp())
	aop.io.R_type:= io.R_type
	aop.io.load:= io.load
	aop.io.store := io.store
	aop.io.Branch := io.branch
	aop.io.I_type := io.I_type
	aop.io.JALR := io.JALR
	aop.io.JAL := io.JAL
	aop.io.Lui := io.Lui
	io.Aluoperation := aop.io.Aluop
// Operand A select
	val opa=Module(new OpAselect())
	opa.io.JALR:= io.JALR
	opa.io.JAL := io.JAL
	opa.io.Lui:= io.Lui
	io.OpAsel:= opa.io.OpAsel
	
// operand B select 
	io.OpBsel:= ( io.load | io.store | io.I_type | io.Lui)
// extend select
	io.extendsel:= Cat(io.store,io.Lui)
	
// next pc select
	val nps=Module(new NextPcSelect())
	nps.io.b:= io.branch
	nps.io.j := io.JAL
	nps.io.jr:= io.JALR
	io.nextpcsel:= nps.io.out	
}

