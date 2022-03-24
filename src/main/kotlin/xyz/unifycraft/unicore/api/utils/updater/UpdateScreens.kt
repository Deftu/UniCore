package xyz.unifycraft.unicore.api.utils.updater

import gg.essential.elementa.ElementaVersion
import gg.essential.elementa.WindowScreen
import gg.essential.elementa.components.UIBlock
import gg.essential.elementa.components.UIContainer
import gg.essential.elementa.components.UIText
import gg.essential.elementa.components.UIWrappedText
import gg.essential.elementa.constraints.CenterConstraint
import gg.essential.elementa.constraints.ChildBasedSizeConstraint
import gg.essential.elementa.constraints.RelativeWindowConstraint
import gg.essential.elementa.constraints.SiblingConstraint
import gg.essential.elementa.dsl.*
import gg.essential.universal.USound
import kotlinx.coroutines.runBlocking
import xyz.unifycraft.unicore.api.UniCore
import xyz.unifycraft.unicore.api.UniCorePalette
import java.awt.Color

class ModUpdateCheckConfirmation : WindowScreen(
    version = ElementaVersion.V1,
    restoreCurrentGuiOnClose = true
) {
    private val outerBlock = UIBlock(UniCorePalette.mediumGray).constrain {
        width = RelativeWindowConstraint()
        height = RelativeWindowConstraint()
    } childOf window
    private val outerContainer = UIContainer().constrain {
        width = RelativeWindowConstraint()
        height = RelativeWindowConstraint()
    } childOf window

    val questionText = UIWrappedText("Are you sure you want to check for updates?").constrain {
        x = CenterConstraint()
        y = CenterConstraint() - 25.pixels()
    } childOf outerContainer

    val buttonContainer = UIContainer().constrain {
        x = CenterConstraint()
        y = CenterConstraint() + 25.pixels()
        width = ChildBasedSizeConstraint(2.5f)
        height = ChildBasedSizeConstraint()
    } childOf outerContainer
    val agreeButton = UIBlock(UniCorePalette.darkGray).constrain {
        width = 150.pixels()
        height = 20.pixels()
    }.onMouseClick {
        USound.playButtonPress()
        runBlocking {
            UniCore.getUpdater().check()
            if (UniCore.getUpdater().outdated.isNotEmpty()) {
                UniCore.getGuiHelper().showScreen(ModUpdateListScreen(UniCore.getUpdater().outdated))
            } else {
                UniCore.getNotifications().post(UniCore.getName(), "No updates were found.")
                restorePreviousScreen()
            }
        }
    } childOf buttonContainer
    val agreeButtonText = UIText("Yes").constrain {
        x = CenterConstraint()
        y = CenterConstraint()
        color = Color.GREEN.toConstraint()
    } childOf agreeButton
    val denyButton = UIBlock(UniCorePalette.darkGray).constrain {
        x = SiblingConstraint(2.5f)
        width = 150.pixels()
        height = 20.pixels()
    }.onMouseClick {
        USound.playButtonPress()
        restorePreviousScreen()
    } childOf buttonContainer
    val denyButonText = UIText("No").constrain {
        x = CenterConstraint()
        y = CenterConstraint()
        color = Color.RED.toConstraint()
    } childOf denyButton
}

internal class ModUpdateListScreen(
    val outdated: List<UpdaterMod>
) : WindowScreen(
    version = ElementaVersion.V1,
    restoreCurrentGuiOnClose = true
) {
    init {
        for (mod in outdated) {
            UniCore.getGuiHelper().showScreen(ModUpdateScreen(mod))
        }
    }

    private val outerBlock = UIBlock(UniCorePalette.mediumGray).constrain {
        width = RelativeWindowConstraint()
        height = RelativeWindowConstraint()
    } childOf window
    private val outerContainer = UIContainer().constrain {
        width = RelativeWindowConstraint()
        height = RelativeWindowConstraint()
    } childOf window

    private val headerContainer = UIContainer().constrain {
        width = RelativeWindowConstraint()
        height = 25.percent()
    } childOf outerContainer
    private val bodyContainer = UIContainer().constrain {
        y = SiblingConstraint()
        width = RelativeWindowConstraint()
        height = 75.percent()
    }
}

internal class ModUpdateScreen(
    val mod: UpdaterMod
) : WindowScreen(
    version = ElementaVersion.V1,
    restoreCurrentGuiOnClose = true
) {
    private val outerBlock = UIBlock(UniCorePalette.mediumGray).constrain {
        width = RelativeWindowConstraint()
        height = RelativeWindowConstraint()
    } childOf window
    private val outerContainer = UIContainer().constrain {
        width = RelativeWindowConstraint()
        height = RelativeWindowConstraint()
    } childOf window
}