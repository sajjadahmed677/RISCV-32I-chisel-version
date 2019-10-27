package merl 
import chisel3._

class InsF extends Module
{
	val io= IO(new Bundle{
//	val in = Output(UInt(32.W))
	val out = Output(UInt(32.W))
})
	val prc = Module(new Pc())
	val INsM = Module(new InsMem())
//	prc.io.input := io.in
	INsM.io.wrAddr := prc.io.pc(11,2)
	io.out := INsM.io.rdData
	prc.io.input := prc.io.pc4
}
