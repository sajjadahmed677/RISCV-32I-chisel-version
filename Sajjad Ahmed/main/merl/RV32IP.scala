package merl

import chisel3._
import chisel3.util.Cat

class RV32IP extends Module
{
    val io=IO(new Bundle{
        val out = Output(UInt(32.W))
    })
// calling all Modules to be connected

    val Pc      = Module(new Pc1())
    val InsMem  = Module(new Fetch())
    val Control = Module(new Decode())
    val Regfile = Module(new RegFile())
    val Execute = Module(new Alu())
    val AluC    = Module(new AluControll())
    val Dmem    = Module(new dMemo())
    val jump    = Module(new JALR())
    val forward = Module(new Forward())
    val stall   = Module(new Stall())
    val forwardm= Module(new ForwardM()) 
    val branch  = Module(new Branches())
    val forwardbr = Module(new BranchForward()) 
    val Mux2    = Module(new Mux())
    val Mux4    = Module(new Mux4())

// pipeline Registers
    // here p at the end of variable name is for indicating pipeline module
    val fetchp     = Module(new FetchReg())
    val decodep    = Module(new DecodeReg())
    val executep   = Module(new Exreg())
    val writebackp = Module(new WBreg())

// connection between program counter and instruction memory

    InsMem.io.wrAddr := Pc.io.p(11,2)

//  Fetch Pipline REgisters 

    // stall logic for holding current fetched instruction

// condition for next pc value on the basis of branching
    when(Control.io.NextPc_sel === "b00".U || Control.io.NextPc_sel === "b01".U || Control.io.NextPc_sel === "b11".U)
    {
    when((Control.io.Branch & branch.io.out) === 0.U)
    {
        when(stall.io.out2 ==="b0".U)
        {
            fetchp.io.fin1 := Pc.io.out   // Pc+4 
            fetchp.io.fin2 := Pc.io.p     // current value of Pc
            fetchp.io.fin3 := InsMem.io.rdData
            fetchp.io.fin4 := Pc.io.p  // Pc for immediate generation
        }
        .otherwise
        {
            fetchp.io.fin1 := fetchp.io.fout1
            fetchp.io.fin2 := fetchp.io.fout2
            fetchp.io.fin3 := fetchp.io.fout3
            fetchp.io.fin4 := fetchp.io.fout4
        }
    }
    .otherwise
    {
        fetchp.io.fin1 := 0.U 
        fetchp.io.fin2 := 0.U
        fetchp.io.fin3 := 0.U
        fetchp.io.fin4 := 0.U
    }
    }
    .otherwise
    {
        fetchp.io.fin1 := 0.U 
        fetchp.io.fin2 := 0.U
        fetchp.io.fin3 := 0.U
        fetchp.io.fin4 := 0.U
    }


// decode and control unit connections 

    Control.io.pc := fetchp.io.fout4
    Control.io.instruction := fetchp.io.fout3

// branch unit connections for getting branch condition earlier and save 2 cycles penalty

    branch.io.control := Control.io.instruction(14,12) 
// forwading unit connections with branch unit for getting correct results
    
    // for 1st input which is in1
    when(forwardbr .io.Out1 === "b01".U)
    {
        branch.io.in1     := Execute.io.out
    }
    .elsewhen(forwardbr .io.Out1 === "b10".U)
    {
        branch.io.in1     := executep.io.eout5
    }
    .elsewhen(forwardbr .io.Out1 === "b11".U)
    {
        branch.io.in1     := Regfile.io.write_data
    }
    .elsewhen(forwardbr.io.Out1 === "b100".U || forwardbr.io.Out1 === "b101".U)
    {
        branch.io.in1     := Dmem.io.rdData
    }
    .otherwise
    {
        branch.io.in1    := Regfile.io.read_data1
    }

    // for 2nd input which is in2

      when(forwardbr .io.Out2 === "b01".U)
    {
        branch.io.in2     := Execute.io.out
    }
    .elsewhen(forwardbr .io.Out2 === "b10".U)
    {
        branch.io.in2     := executep.io.eout5
    }
    .elsewhen(forwardbr .io.Out2 === "b11".U)
    {
        branch.io.in2     := Regfile.io.write_data
    }
    .elsewhen(forwardbr .io.Out2 === "b100".U || forwardbr.io.Out2 === "b101".U)
    {
        branch.io.in2     := Dmem.io.rdData
    }
    .otherwise
    {
        branch.io.in2    := Regfile.io.read_data2
    }




//    branch.io.in1     := Regfile.io.read_data1
 //   branch.io.in2     := Regfile.io.read_data2

// Register file connections

    Regfile.io.read_sel1 := Control.io.Readreg_1
    Regfile.io.read_sel2 := Control.io.Readreg_2

// jump and link register connections
    jump.io.a := Regfile.io.read_data1
    jump.io.b := Control.io.SUI

// forwarding unit for branches 

        forwardbr.io.Cbranch := Control.io.Branch
        forwardbr.io.IDRs1   := Control.io.Readreg_1
        forwardbr.io.IDRs2   := Control.io.Readreg_2
        forwardbr.io.EXerd   := decodep.io.dout13
        forwardbr.io.EXMemrd := executep.io.eout7
        forwardbr.io.MemWBrd := writebackp.io.wout5
        forwardbr.io.Memread := executep.io.eout2


// hazard detexction unit connections

    stall.io.IDrs1 := Control.io.Readreg_1
    stall.io.IDrs2 := Control.io.Readreg_2
    stall.io.EMread:=decodep.io.dout2
    stall .io.EMrd := decodep.io.dout13
    stall.io.EXwrite:= Control.io.Memwrite

// conditional logic 
    when(Control.io.NextPc_sel=== 1.U)
    {
        when((branch.io.out & Control.io.Branch)===1.U)
        {
            Pc.io.in := Control.io.Sb_type
        }
        .otherwise
        {
            when(stall.io.out3 === "b0".U)
            {
                Pc.io.in := Pc.io.out
            }
            .otherwise
            {
                Pc.io.in := Pc.io.p
            }
        }
    }
    .elsewhen(Control.io.NextPc_sel===2.U)
    {
        Pc.io.in := Control.io.Uj_type
    }
    .elsewhen(Control.io.NextPc_sel===3.U)
    {
        Pc.io.in := jump.io.out
    }
    .otherwise
    {
         when(stall.io.out3 === "b0".U)
            {
                Pc.io.in := Pc.io.out
            }
            .otherwise
            {
                Pc.io.in := Pc.io.p
            }
    }

// Decode pipeline Registers connections
    // stall logic for setting controll signals to zero
    when(stall.io.out1 === "b0".U)
    {
        decodep.io.din1 := Control.io.Memwrite
        decodep.io.din2 := Control.io.Memread
        decodep.io.din3 := Control.io.Regwrite
        decodep.io.din4 := Control.io.MemtoReg
        decodep.io.din5 := Control.io.AluOp
        decodep.io.din6 := Control.io.OpA_sel
        decodep.io.din7 := Control.io.OpB_sel
       
    }
    .otherwise    // when an instruction followed by a load instruction running in th pipeline
                 // then we have to stall one cycle the next instruction for that set all controll signals 
                 // to zero for making a nops condition.
    {
        decodep.io.din1 := 0.U
        decodep.io.din2 := 0.U
        decodep.io.din3 := 0.U
        decodep.io.din4 := 0.U
        decodep.io.din5 := 0.U
        decodep.io.din6 := 0.U
        decodep.io.din7 := 0.U
        
    }

        decodep.io.din8 := fetchp.io.fout2
        decodep.io.din9 := fetchp.io.fout1
        decodep.io.din10:= Regfile.io.read_data1
        decodep.io.din11:= Regfile.io.read_data2
        decodep.io.din12:= Control.io.SUI
        decodep.io.din13:= Control.io.WriteReg
        decodep.io.din14:= fetchp.io.fout3(14,12)
        decodep.io.din15:= fetchp.io.fout3(31,25) 
        decodep.io.din16:= Control.io.Readreg_1
        decodep.io.din17:= Control.io.Readreg_2





// forwarding unit connections 
    // ID/EX stage connections
    forward.io.IDrs1  := decodep.io.dout16
    forward.io.IDrs2  := decodep.io.dout17
   
// execution unit connections
    // Alu control connections

    AluC.io.Aluop := decodep.io.dout5
    AluC.io.func3 := decodep.io.dout14
    AluC.io.func7 := decodep.io.dout15

    // Connection between Alu and Alu control

    Execute.io.func := AluC.io.out

// Execute Pipline Registers
    // passing memory state controll lines
    
    executep.io.ein1 := decodep.io.dout1 
    executep.io.ein2 := decodep.io.dout2
    executep.io.ein3 := decodep.io.dout3
    executep.io.ein4 := decodep.io.dout4
    executep.io.ein5 := Execute.io.out
    executep.io.ein7 := decodep.io.dout13
    executep.io.ein8 := decodep.io.dout17
    // data memory's data pin connection(executep.io.ein6) to be done in forwarding

// forwarding logic output connections 
    // Alu operand A connection
    when(decodep.io.dout6 === "b00".U)
    {
        when(forward.io.out1 === "b01".U)
        {
            Execute.io.in1 := Regfile.io.write_data
        }
        .elsewhen(forward.io.out1 === "b10".U)
        {
            Execute.io.in1 := executep.io.eout5
        }
        .otherwise
        {
            Execute.io.in1 := decodep.io.dout10
        }
    }
    .elsewhen(decodep.io.dout6 === "b01".U)
    {
        Execute.io.in1 := decodep.io.dout8
    }
    .elsewhen(decodep.io.dout6 === "b10".U)
    {
        Execute.io.in1 := decodep.io.dout9
    }
    .otherwise
    {
        Execute.io.in1 := decodep.io.dout10
    }

    // Alu operand B connection
    Mux4.io.sel := forward.io.out2
    Mux4.io.in1 := decodep.io.dout11
    Mux4.io.in2 := Regfile.io.write_data
    Mux4.io.in3 := executep.io.eout5
    Mux4.io.in4 := Regfile.io.write_data
    // data memory input dataconnection through forwarding
    executep.io.ein6 := Mux4.io.out
    // selection between forwarded data and immediate value 
    Mux2.io.sel := decodep.io.dout7
    Mux2.io.a   := Mux4.io.out
    Mux2.io.b   := decodep.io.dout12
    Execute.io.in2 := Mux2.io.out


// forwarding unit connections
    // EX/MEM forwarding connections
    forward.io.EMregW := executep.io.eout3
    forward.io.EMrd   := executep.io.eout7

// forwarding module connections
// this forwarding module is only for condition where 
// a store instruction comes followed by a load instruction 
// lw x2,0(x0)
// sw x2,4(x0)
// and we want to store the same data which is being loading 
//  then we have to forward the data from output of data memory to its input with delay of one cycle. 

    forwardm.io.EMrs2    := executep.io.eout8
    forwardm.io.EMwrite := executep.io.eout1
    forwardm.io.MBread  := writebackp.io.wout6
    forwardm.io.MBrd    := writebackp.io.wout5
//    forwardm.io.branch  := Control.io.Branch

// data memory connetion

    Dmem.io.wen    := executep.io.eout1
    Dmem.io.ren    := executep.io.eout2
    Dmem.io.wrAddr := executep.io.eout5(9,0)
// here we select the data input for instruction memory either it will forwarded or normal
    when(forwardm.io.out === "b0".U)
    {
        Dmem.io.wrData := executep.io.eout6
    }
    .otherwise
    {
        Dmem.io.wrData := writebackp.io.wout3
    }

// write back pipeline registers

    writebackp.io.win1 := executep.io.eout3
    writebackp.io.win2 := executep.io.eout4
    writebackp.io.win3 := Dmem.io.rdData
    writebackp.io.win4 := executep.io.eout5
    writebackp.io.win5 := executep.io.eout7
    writebackp.io.win6 := executep.io.eout2

// enable register file
    Regfile.io.wEn       := writebackp.io.wout1
    Regfile.io.write_sel := writebackp.io.wout5

// forwarding unit connections
    // MEM/WB forwarding connections 
    forward.io.MBregW := writebackp.io.wout1
    forward.io.MBrd   := writebackp.io.wout5

// write back logic 

    when(writebackp.io.wout2 === 1.U)
    {
        Regfile.io.write_data := writebackp.io.wout3
    }
    .otherwise
    {
        Regfile.io.write_data := writebackp.io.wout4
    }
    
    // top module output
    io.out := writebackp.io.wout4
} 