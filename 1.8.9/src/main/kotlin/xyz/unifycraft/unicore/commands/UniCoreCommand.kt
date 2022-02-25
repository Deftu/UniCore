package xyz.unifycraft.unicore.commands

import xyz.unifycraft.unicore.api.commands.annotations.*

@Command(
    name = "unicore"
) class UniCoreCommand {
    @Default
    private fun execute() {
        // TODO 2022/02/14 - UniCore.getGuiHelper().showScreen(UniCoreScreen())
    }
}