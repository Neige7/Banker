package pers.neige.banker.command

import pers.neige.banker.command.subcommand.Help
import pers.neige.banker.command.subcommand.Reload
import taboolib.common.platform.command.CommandBody
import taboolib.common.platform.command.CommandHeader
import taboolib.platform.BukkitAdapter

/**
 * 插件指令
 */
@CommandHeader(name = "banker")
object Command {
    val bukkitAdapter = BukkitAdapter()

    @CommandBody
    val reload = Reload.reload

    @CommandBody
    val help = Help.helpCommand
}