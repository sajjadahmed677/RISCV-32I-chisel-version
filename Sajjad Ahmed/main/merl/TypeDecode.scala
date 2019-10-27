package merl 
import chisel3._

class TypeDecode extends Module
{
	val io=IO(new Bundle{
		val opcode=Input(UInt(7.W))
		val R_type=Output(UInt(1.W))
		val load=Output(UInt(1.W))
		val store=Output(UInt(1.W))
		val branch=Output(UInt(1.W))
		val I_type=Output(UInt(1.W))
		val JALR=Output(UInt(1.W))
		val JAL=Output(UInt(1.W))
		val Lui=Output(UInt(1.W))
})
	val comp1=Module(new Comparator())
	comp1.io.in1:=io.opcode
	comp1.io.in2:=51.U
	io.R_type:=comp1.io.equal
	val comp2=Module(new Comparator())
	comp2.io.in1:=io.opcode
	comp2.io.in2:=3.U
	io.load:=comp2.io.equal
	val comp3=Module(new Comparator())
	comp3.io.in1:=io.opcode
	comp3.io.in2:=35.U
	io.store:=comp3.io.equal
	val comp4=Module(new Comparator())
	comp4.io.in1:=io.opcode
	comp4.io.in2:=99.U
	io.branch:=comp4.io.equal
	val comp5=Module(new Comparator())
	comp5.io.in1:=io.opcode
	comp5.io.in2:=19.U
	io.I_type:=comp5.io.equal
	val comp6=Module(new Comparator())
	comp6.io.in1:=io.opcode
	comp6.io.in2:=103.U
	io.JALR:=comp6.io.equal
	val comp7=Module(new Comparator())
	comp7.io.in1:=io.opcode
	comp7.io.in2:=111.U
	io.JAL:=comp7.io.equal
	val comp8=Module(new Comparator())
	comp8.io.in1:=io.opcode
	comp8.io.in2:=55.U
	io.Lui:=comp8.io.equal	
}
