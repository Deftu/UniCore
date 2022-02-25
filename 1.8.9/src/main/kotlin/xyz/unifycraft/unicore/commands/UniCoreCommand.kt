package xyz.unifycraft.unicore.commands

import xyz.deftu.unicore.api.commands.annotations.*

@Command(
    name = "unicore"
) class UniCoreCommand {
    @Default
    private fun execute() {
        println("TEST EXECUTION")
        // TODO 2022/02/14 - UniCore.getGuiHelper().showScreen(UniCoreScreen())
    }
}