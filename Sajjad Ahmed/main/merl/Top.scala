
package merl
import chisel3._
import chisel3.util.Cat

class Top extends Module
{
    val io = IO(new Bundle{
        val out = Output(UInt(32.W))
    })
    val Decode = Module(new Decode())
    val InsM   = Module(new Fetch())
    val Dmem   = Module(new dMemo())
    val Regfile= Module(new RegFile())
    val Execute = Module(new Alu())
    val Pc      = Module(new Pc1())
    val jump    = Module(new JALR())
    val AluC    = Module(new AluControll())
    val fwd     = Module(new Forward())
    // pc and intruction memory

    InsM.io.wrAddr := Pc.io.p(11,2)

    // instruction memory and Decode

    // pipeline registers

    val Freg1=RegInit(0.U(32.W))
    val Freg2=RegInit(0.U(32.W))
    val Freg3=RegInit(0.U(32.W))
    val Freg4=RegInit(0.U(32.W))
    
    Freg1 := InsM.io.rdData
    Freg2 := Pc.io.p
    Freg3 := Pc.io.out
    Freg4 := Pc.io.p

// decode pipeline

val Decreg1 = RegInit(0.U(1.W))
     val Decreg2 = RegInit(0.U(1.W))
     val Decreg3 = RegInit(0.U(1.W))
     val Decreg4 = RegInit(0.U(1.W))
     val Decreg5 = RegInit(0.U(3.W))
     val Decreg6 = RegInit(0.U(2.W))
     val Decreg7 = RegInit(0.U(1.W))
     val Decreg8 = RegInit(0.U(32.W))
     val Decreg9 = RegInit(0.U(32.W))
     val Decreg10 = RegInit(0.U(32.W))
     val Decreg11 = RegInit(0.U(32.W))
     val Decreg12 = RegInit(0.U(5.W))
     val Decreg13 = RegInit(0.U(32.W))
     val Decreg14 = RegInit(0.U(3.W))
     val Decreg15 = RegInit(0.U(7.W))
     val Decreg16 = RegInit(0.U(5.W))
     val Decreg17 = RegInit(0.U(1.W))
     Decreg1 := Decode.io.Memwrite
     Decreg2 := Decode.io.Memread
     Decreg3 := Decode.io.Regwrite
     Decreg4 := Decode.io.MemtoReg
     Decreg5 := Decode.io.AluOp
     Decreg6 := Decode.io.OpA_sel
     Decreg7 := Decode.io.OpB_sel
     Decreg8 := Freg3
     Decreg9 := Freg4
     Decreg10:= Regfile.io.read_data1
     Decreg11:= Regfile.io.read_data2
     Decreg12:= Decode.io.WriteReg
     Decreg13:= Decode.io.SUI
     Decreg14:= Decode.io.instruction(14,12)
     Decreg15:= Decode.io.instruction(31,25)
     Decreg16:= Decode.io.Readreg_1
     Decreg17:= Decode.io.Readreg_2
    Decode.io.instruction := Freg1
    Decode.io.pc          := Freg2
    
        // Execute pipeline registers
    val Exreg1 = RegInit(0.U(1.W))
    val Exreg2 = RegInit(0.U(1.W))
    val Exreg3 = RegInit(0.U(1.W))
    val Exreg4 = RegInit(0.U(1.W))
    val Exreg5 = RegInit(0.U(32.W))
    val Exreg6 = RegInit(0.U(32.W))
    val Exreg7 = RegInit(0.U(5.W))

    Exreg1 := Decreg1
    Exreg2 := Decreg2
    Exreg3 := Decreg3
    Exreg4 := Decreg4
    Exreg5 := Execute.io.out
    Exreg6 := Decreg11
    Exreg7 := Decreg12
    Dmem.io.wrAddr := Exreg5(11,2)
    Dmem.io.wrData := Exreg6
    Dmem.io.wen    := Exreg1
    Dmem.io.ren    := Exreg2

       // Write back pipeline registers
    val Wbreg1 = RegInit(0.U(1.W))
    val Wbreg2 = RegInit(0.U(1.W))
    val Wbreg3 = RegInit(0.U(32.W))
    val Wbreg4 = RegInit(0.U(32.W))
    val Wbreg5 = RegInit(0.U(5.W))
    Wbreg1 := Exreg3
    Wbreg2 := Exreg4
    Wbreg3 := Dmem.io.rdData
    Wbreg4 := Exreg5
    Wbreg5 := Exreg7
    
    // Register file and Decode

 //   Regfile.io.wEn :=  Wbreg1
    Regfile.io.read_sel1 := Decode.io.Readreg_1
    Regfile.io.read_sel2 := Decode.io.Readreg_2
//    Regfile.io.write_sel :=  Wbreg5
    // jump module

    jump.io.a := Regfile.io.read_data1
    jump.io.b := Regfile.io.read_data2

    // Alu control and decode

    // Decode pipeline registers

     
    AluC.io.func3 := Decreg14
    AluC.io.func7 := Decreg15
    AluC.io.Aluop := Decreg5

    //RegFile Alu and Alu Control

   Execute.io.func := AluC.io.out
//    when(Decreg6 === "b00".U)
//    {Execute.io.in1 := Decreg10}
//    .elsewhen(Decreg6 === "b01".U)
//    {Execute.io.in1 := Decreg9}
//    .elsewhen(Decreg6 === "b10".U)
//    {Execute.io.in1 := Decreg8}
//    .otherwise
//    {Execute.io.in1 := Decreg10}

//    when(Decreg7 === "b0".U)
//    {Execute.io.in2 := Decreg11}
//    .elsewhen(Decreg7 === "b1".U)
//    {Execute.io.in2 := Decreg13}
//    .otherwise
//    {Execute.io.in2 := Decreg11}

    // data meory alu and Decode 

    
    // Write back 
 

 Regfile.io.wEn :=  Wbreg1
  Regfile.io.write_sel :=  Wbreg5

    when( Wbreg2 === "b0".U)
    {Regfile.io.write_data :=  Wbreg4}
    .otherwise
    {Regfile.io.write_data := Wbreg3}

    // conditional logic

    when(Decode.io.NextPc_sel === "b00".U)
    {Pc.io.in := Pc.io.out}
    .elsewhen(Decode.io.NextPc_sel === "b01".U)
    {
        when((Decode.io.Branch & Execute.io.branch) ==="b0".U )
        {Pc.io.in := Pc.io.out}
        .otherwise
        {Pc.io.in := Decode.io.Sb_type}
    }
    .elsewhen(Decode.io.NextPc_sel === "b10".U)
    {Pc.io.in := Decode.io.Uj_type}
    .otherwise
    {Pc.io.in := jump.io.out }
     
     io.out := Execute.io.out



     fwd.io.IDrs1 := Decreg16
     fwd.io.IDrs2 := Decreg17
     fwd.io.EMregW:= Exreg3
     fwd.io.EMrd := Exreg7
     fwd.io.MBregW := Exreg5
     fwd.io.MBrd := Wbreg5
     


 
    when(Decreg6 === "b00".U)
    {
      //  Execute.io.in1 := Decreg10
      when(fwd.io.out1==="b10".U)
      {
          Execute.io.in1 := Exreg5
      }
      .elsewhen(fwd.io.out1 === "b01".U)
      {
          Execute.io.in1 := Wbreg4
      }
      .otherwise
      {
         Execute.io.in1 := Decreg10
      }
    
    }
    .elsewhen(Decreg6 === "b01".U)
    {Execute.io.in1 := Decreg9}
    .elsewhen(Decreg6 === "b10".U)
    {Execute.io.in1 := Decreg8}
    .otherwise
    {Execute.io.in1 := DontCare}

    when(Decreg7 === "b0".U)
    {
      //  Execute.io.in2 := Decreg11
         when(fwd.io.out2==="b10".U)
      {
          Execute.io.in2 := Exreg5
      }
      .elsewhen(fwd.io.out2 === "b01".U)
      {
          Execute.io.in2 := Wbreg4
      }
      .otherwise
      {
         Execute.io.in2 := Decreg11
      }
    }
    .elsewhen(Decreg7 === "b1".U)
    {Execute.io.in2 := Decreg13}
    .otherwise
    {Execute.io.in2 := DontCare}


}