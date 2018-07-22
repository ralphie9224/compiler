package backend.interpreter

import backend.Backend
import intermediate.IntermediateCodeGenerator
import intermediate.SymbolTable
import message.Message
import message.MessageListener
import message.MessageType

class Executor: Backend() {
    override fun process(iCode: IntermediateCodeGenerator, symbolTable: SymbolTable) {
        val startTime: Long = System.currentTimeMillis()
        val elapsedTime: Float= (System.currentTimeMillis() - startTime)/1000f
        val executionCount = 0
        val runtimeErrors = 0

        sendMessage(Message(MessageType.INTERPRETER_SUMMARY, listOf<Number>(executionCount, runtimeErrors, elapsedTime)))
    }

    override fun addMessageListener(listener: MessageListener) = messageHandler.addListener(listener)

    override fun removeMessageListener(listener: MessageListener) = messageHandler.removeListener(listener)

    override fun sendMessage(message: Message) = messageHandler.sendMessage(message)
}