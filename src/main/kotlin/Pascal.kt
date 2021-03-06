import backend.Backend
import backend.BackendFactory
import frontend.FrontEndFactory
import frontend.Parser
import frontend.Source
import intermediate.IntermediateCode
import intermediate.IntermediateCodeGenerator
import intermediate.SymbolTableStack
import util.CrossReferencer
import java.io.BufferedReader
import java.io.FileReader

class Pascal(operation: String, filePath: String, flags: String) {

    lateinit var parser: Parser
    lateinit var source: Source
    lateinit var iCode: IntermediateCode
    lateinit var symbolTableStack: SymbolTableStack
    lateinit var backend: Backend
    lateinit var operation: String

    init {
        try {
            val intermediate = flags.indexOf('i') > -1
            val xref = flags.indexOf('x') > -1

            source = Source(BufferedReader(FileReader(filePath)))
            source.addMessageListener(MessageListenerImpl())

            parser = FrontEndFactory.createParser("pascal", "top-down", source)
            parser.addMessageListener(MessageListenerImpl())

            backend = BackendFactory.createBackend(operation)
            backend.addMessageListener(MessageListenerImpl())

            parser.parse()
            source.close()


            symbolTableStack = parser.symbolTableStack

            if (xref) {
                val crossReferencer = CrossReferencer()
                crossReferencer.print(symbolTableStack)

            }

            backend.process(iCode, symbolTableStack)

//            iCode = parser.iCode!!
//            symbolTable = parser.symbolTable!!
//
//            backend.process(iCode, symbolTable)
        } catch (ex: Exception) {
            print("***** Internal translator error. *****")
            ex.printStackTrace()
        }
    }
}