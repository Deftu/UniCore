package xyz.deftu.onecore.commands

import xyz.deftu.onecore.api.commands.annotations.*
import xyz.deftu.onecore.api.commands.arguments.ArgumentQueue

@Command(
    name = "onecore"
) class OneCoreCommand {
    @Default
    private fun execute() {
        println("TEST EXECUTION")
        // TODO 2022/02/14 - OneCore.getGuiHelper().showScreen(OneCoreScreen())
    }

    @SubCommand("listtest")
    private fun doTheThing(@Argument list: ArgumentQueue) {
        println(list)
    }
}