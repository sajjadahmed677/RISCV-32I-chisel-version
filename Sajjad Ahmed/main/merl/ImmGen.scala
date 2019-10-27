package merl 
import chisel3._
import chisel3.util.Cat
import chisel3.util.Fill
class ImmGen extends Module
{
	val io=IO(new Bundle{
		val instruction = Input(UInt(32.W))
		val pc          = Input(UInt(32.W))
		val s_im	= Output(UInt(32.W))
		val sb_im       = Output(UInt(32.W))
		val u_im	= Output(UInt(32.W))
		val uj_im       = Output(UInt(32.W))
		val i_im	= Output(UInt(32.W))
})
	val lsb = io.instruction(31,25)
	val msb = io.instruction(11,7)
	val S_im = Cat(io.instruction(31,25),io.instruction(11,7))
	val Sb_im= Cat(lsb(6),msb(0),lsb(5,0),msb(4,1),"b0".asUInt(1.W))
	val U_im = io.instruction(31,12)
	val Uj_im = Cat(io.instruction(31),io.instruction(19,12),io.instruction(20),io.instruction(30,21))
	val I_im = io.instruction(31,20)

	io.u_im    := Cat(U_im,"b0".U(12.W))
	io.i_im    := Cat(Fill(20, I_im(11)), I_im(11,0))
	io.s_im    := Cat(Fill(20, S_im(11)), S_im)
	val sbs  = Cat(Fill(19, Sb_im(12)), Sb_im)
	val ujs  = Cat(Fill(11,Uj_im(19)),Uj_im(19,0),"b0".U(1.W))
	io.uj_im := ujs + io.pc
	io.sb_im := sbs + io.pc
}
	
