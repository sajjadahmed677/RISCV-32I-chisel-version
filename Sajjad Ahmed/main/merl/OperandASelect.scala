package merl
import chisel3._
import chisel3.util.Cat
class OperandASelect extends Module
{
val io= IO(new Bundle{
val LUI= Input(UInt(1.W))
val JALR=Input(UInt(1.W))
val JAL= Input(UInt(1.W))
                val out= Output(UInt(2.W))

})
val and1= (~io.LUI) & (~io.JALR) & (io.JAL)
        val and2= (~io.LUI) & (io.JALR)  & (~io.JAL)
        val and3= (io.LUI)  & (~io.JALR) & (~io.JAL)
        val out1= and1 | and2 | and3
        val out2= (io.LUI)  & (~io.JALR) & (~io.JAL)
        io.out:= Cat(out1, out2)

}
