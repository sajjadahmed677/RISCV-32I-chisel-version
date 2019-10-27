package merl

import chisel3._
import chisel3.iotesters.{Driver, TesterOptionsManager}
import utils.TutorialRunner

object Launcher {
  val examples = Map(
		"Mux" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Mux(), manager) {
          (c) => new MuxTests(c)
        }
      },
	"Subtractor" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Subtractor(), manager) {
          (c) => new SubtractorTests(c)
        }
      },
	"Comparator" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Comparator(), manager) {
          (c) => new ComparatorTests(c)
        }
      },
	"Adder" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Adder(), manager) {
          (c) => new AdderTests(c)
        }
      },
	"Alu" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Alu(), manager) {
          (c) => new AluTests(c)
        }
      },
	"Pc1" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Pc1(), manager) {
          (c) => new Pc1Tests(c)
        }
      },
	"Mux4" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Mux4(), manager) {
          (c) => new Mux4Tests(c)
        }
      },
	"TypeDecode" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new TypeDecode(), manager) {
          (c) => new TypeDecodeTests(c)
        }
      },
	"ControlDecode" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new ControlDecode(), manager) {
          (c) => new ControlDecodeTests(c)
        }
      },

	"PriorityEncoder" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new PriorityEncoder(), manager) {
          (c) => new PriorityEncoderTests(c)
        }
      },	

	"NextPcSelect" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new NextPcSelect(), manager) {
          (c) => new NextPcSelectTests(c)
        }
      }	,	

	"JALR" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new JALR(), manager) {
          (c) => new JALRTests(c)
        }
      },
	"AluOp" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new AluOp(), manager) {
          (c) => new AluOpTests(c)
        }
      },
	"OpAselect" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new OpAselect(), manager) {
          (c) => new OpAselectTests(c)
        }
      }, 

	"OperandASelect" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new OperandASelect(), manager) {
          (c) => new OperandASelectTests(c)
        }
      },
	
	"Fetch" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Fetch(), manager) {
          (c) => new FetchTests(c)
        }
      },
	"AluControll" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new AluControll(), manager) {
          (c) => new AluControllTests(c)
        }
      },
	"RegFile" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new RegFile(), manager) {
          (c) => new RegFileTests(c)
        }
      },
	"ImmGen" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new ImmGen(), manager) {
          (c) => new ImmGenTests(c)
        }
      },
	"Decode" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Decode(), manager) {
          (c) => new DecodeTests(c)
        }
      },
	"dMemo" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new dMemo(), manager) {
          (c) => new dMemoTests(c)
        }
      },
	"reg" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new reg(), manager) {
          (c) => new regTests(c)
        }
      },
      	"Top" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Top(), manager) {
          (c) => new TopTests(c)
        }
      },
      "Pc" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Pc(), manager) {
          (c) => new PcTests(c)
        }
      },
      "InsMem" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new InsMem(), manager) {
          (c) => new InsMemTests(c)
        }
      },
      "InsF" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new InsF(), manager) {
          (c) => new InsFTests(c)
        }
      },
       "Forward" -> { (manager: TesterOptionsManager) =>
        Driver.execute(() => new Forward(), manager) {
          (c) => new ForwardTests(c)
        }
      }											
)
  def main(args: Array[String]): Unit = {
    TutorialRunner("merl", examples, args)
  }
}
