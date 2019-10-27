package merl

import chisel3._
import chisel3.util.Cat
class Decode extends Module
{
	val io=IO(new Bundle{
		val instruction = Input(UInt(32.W))
		val pc          = Input(UInt(32.W))
		val Memwrite    = Output(UInt(1.W))
		val Branch      = Output(UInt(1.W))
		val Memread     = Output(UInt(1.W))
		val MemtoReg    = Output(UInt(1.W))
		val Regwrite    = Output(UInt(1.W))
		val AluOp       = Output(UInt(3.W))
		val OpA_sel     = Output(UInt(2.W))
		val OpB_sel     = Output(UInt(1.W))
		val Extend_sel  = Output(UInt(2.W))
		val NextPc_sel  = Output(UInt(2.W))
		val Readreg_1   = Output(UInt(5.W))
		val Readreg_2   = Output(UInt(5.W))
		val WriteReg    = Output(UInt(5.W))
		val SUI         = Output(UInt(32.W))
		val Sb_type     = Output(UInt(32.W))
		val Uj_type     = Output(UInt(32.W))
	//	val Func        = Output(UInt(5.W))
})
	val td = Module(new TypeDecode())  // instruction type decode unit 
	val cd = Module(new ControlDecode()) // control decode unit
	val ig = Module(new ImmGen()) // immidiate generation block
//	val ac = Module(new AluControll())  // Alu controll block
	
        // connecting opcode bits to type decode
	td.io.opcode := io.instruction(6,0) 

	// connecting outputs of type decode to the inputs of controldecode
	cd.io.R_type := td.io.R_type 
	cd.io.load   := td.io.load 
	cd.io.store  := td.io.store
	cd.io.branch := td.io.branch
	cd.io.I_type := td.io.I_type
	cd.io.JALR   := td.io.JALR
	cd.io.JAL    := td.io.JAL
	cd.io.Lui    := td.io.Lui

	// connecting outputs of control decode to the outputs of decode block
	io.Memwrite  := cd.io.Memwrite
	io.Branch    := cd.io.Branch
	io.Memread   := cd.io.MemRead
	io.MemtoReg  := cd.io.MemtoReg
	io.AluOp     := cd.io.Aluoperation
	io.OpA_sel   := cd.io.OpAsel
	io.OpB_sel   := cd.io.OpBsel
	io.Extend_sel:= cd.io.extendsel
	io.NextPc_sel:= cd.io.nextpcsel
	io.Regwrite  := cd.io.Regwrite
	
	// splitting register bits
	io.Readreg_1  := io.instruction(19,15)
	io.Readreg_2  := io.instruction(24,20)
	io.WriteReg   := io.instruction(11,7)

	// connecting immediate generation block 
	
	ig.io.instruction := io.instruction
	ig.io.pc          := io.pc
	
	// connecting outputs of immediate generation bloc with the outputs of decode unit
	io.Sb_type := ig.io.sb_im
	io.Uj_type := ig.io.uj_im
	
	when(io.Extend_sel==="b01".U(2.W))
	{io.SUI := ig.io.s_im}
	.elsewhen(io.Extend_sel==="b10".U(2.W))
	{io.SUI := ig.io.u_im}
	.otherwise
	{io.SUI := ig.io.i_im}
	
	// connecting Alucontrol
//	ac.io.func3 := io.instruction(14,12)
//	ac.io.func7 := io.instruction(31,25)
//	ac.io.Aluop := io.AluOp
	
	// connecting Alu control output to the output of decode unit
//	io.Func := ac.io.out
}

