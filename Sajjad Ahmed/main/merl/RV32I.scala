// single cycle riscv RV32I core 


package merl
import chisel3._
import chisel3.experimental.DataMirror

class RV32I extends Module 
{
	val io=IO(new Bundle{
		val clock= Input(Bool())
		var wrData = Input(UInt(32.W))
		val in = Output(UInt(32.W))
		val rdData= Output(UInt(32.W))
		val pout  = Output(UInt(32.W))
		val ad    = Output(UInt(32.W))
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
		 val SUI         = Output(UInt(32.W))
		 val Sb_type     = Output(UInt(32.W))
		 val Uj_type     = Output(UInt(32.W))
		 val Func        = Output(UInt(5.W))
		 val Readreg_1   = Output(UInt(5.W))
		 val Readreg_2   = Output(UInt(5.W))
		 val WriteReg    = Output(UInt(5.W))
		 val read_data1 = Output(UInt(32.W))
    		 val read_data2 = Output(UInt(32.W))
		 val result     = Output(UInt(32.W))
		 val newpc      = Output(UInt(32.W))
		 val jalr       = Output(UInt(32.W))

})
	

	val pc = Module(new Pc1())           // programm counter module
	val mem = Module(new Fetch())        // instruction memory module
	val D  = Module(new Decode())        // Decode unit 
	val rf = Module(new RegFile())	     // Register file block
	val ex = Module(new Alu())	     // Alu block
	val j  = Module(new JALR())	     // Jump and link register logic
	val dm= Module(new dMemo())	     // Data memory block

//	val mx = Module(new Mux())	     
//	val mx4= Module(new Mux4())
//	val mx41= Module(new Mux4())
	

// interfacing program counter

//	pc.io.clock := io.clock
	pc.io.in := io.in
	io.pout := pc.io.out
	io.ad := pc.io.p

// instruction memory interface 

//	mem.io.clock := io.clock
//	mem.io.wrData := io.wrData
	mem.io.wrAddr := pc.io.p(21,2)
	io.rdData     := mem.io.rdData

// connecting Decode unit with intruction memory 

	D.io.pc  := io.ad	
	D.io.instruction := io.rdData
	io.Memwrite := D.io.Memwrite
	io.Branch   := D.io.Branch
	io.Memread  := D.io.Memread
	io.MemtoReg := D.io.MemtoReg
	io.Regwrite := D.io.Regwrite
	io.AluOp    := D.io.AluOp
	io.OpA_sel  := D.io.OpA_sel
	io.OpB_sel  := D.io.OpB_sel
	io.Extend_sel:= D.io.Extend_sel
	io.NextPc_sel:= D.io.NextPc_sel
	io.Readreg_1 := D.io.Readreg_1
	io.Readreg_2 := D.io.Readreg_2
	io.WriteReg  := D.io.WriteReg
	io.SUI       := D.io.SUI
	io.Sb_type   := D.io.Sb_type
	io.Uj_type   := D.io.Uj_type
	io.Func      := D.io.Func 
	
// interfacing Register file with Decode unit 

	rf.io.wEn := io.Regwrite
	rf.io.read_sel1 := io.Readreg_1
	rf.io.read_sel2 := io.Readreg_2
	rf.io.write_sel := io.WriteReg
	io.read_data1   := rf.io.read_data1
	io.read_data2   := rf.io.read_data2
	
// connecting jump and link register(JALR) logic with regiter file
 
	j.io.a := io.read_data1
	j.io.b := io.read_data2
	io.jalr := j.io.out 

// defining Next program counter's value logic calculated on the basis of jumps and branches
 
	when(io.NextPc_sel === "b00".U)
	{io.newpc := io.pout }
	.elsewhen(D.io.NextPc_sel==="b01".U)
	{
		when((ex.io.branch&io.Branch)==="b0".U)
		{io.newpc := io.pout}
		.otherwise
		{io.newpc := io.Sb_type}	
	}
	.elsewhen(io.NextPc_sel=== "b10".U)
	{io.newpc := io.Uj_type}
	.otherwise
	{io.newpc := io.jalr}
	//when(io.clock)
	io.in :=io.newpc

	

//	mx.io.sel := ex.io.branch&io.Branch
//	mx.io.a   := io.pout
//	mx.io.b   := io.Sb_type
	
//	mx4.io.sel := io.NextPc_sel
//	mx4.io.in1 := io.pout
//	mx4.io.in2 := mx.io.out
//	mx4.io.in3 := io.Uj_type
//	mx4.io.in4 := io.jalr
//	io.newpc   := mx4.io.out



	ex.io.func := io.Func	// connecting Alu control pin with Alu control block defined in Decode unit

// selecting Alu's first Operand
 
	when((io.OpA_sel==="b00".U) || (io.OpA_sel==="b11".U)||(io.OpA_sel==="b10".U))
	{ex.io.in1 := io.read_data1}

//	.elsewhen(io.OpA_sel==="b10".U)
//	{ex.io.in1 := io.newpc}


//	mx41.io.sel := io.OpA_sel
//	mx41.io.in1 := io.Readreg_1
//	mx41.io.in2 := 0.U
//	mx41.io.in3 := 0.U
//	mx41.io.in4 := io.Readreg_1
//	ex.io.in1   := mx41.io.out

	
// selecting Alu's second operand 

	when(io.OpB_sel==="b0".U)
	{ex.io.in2 := io.read_data2}
	.elsewhen(io.OpB_sel === "b1".U)
	{ex.io.in2 := io.SUI}
	io.result := ex.io.out

// interfacing Data memory 
	
	dm.io.wrAddr := io.result(9,2)
	dm.io.wrData := io.read_data2
	dm.io. wen   := io.Memwrite
	dm.io.ren    := io.Memread

// Defining Write back logic to register file
	
	when(io.MemtoReg==="b0".U)
	{rf.io.write_data := io.result}
	.elsewhen(io.MemtoReg==="b1".U)
	{rf.io.write_data := dm.io.rdData}
}
