package pers.neige.banker.command.subcommand

import org.bukkit.command.CommandSender
import pers.neige.banker.manager.ConfigManager
import taboolib.common.platform.command.subCommand
import taboolib.common.platform.function.submit

object Reload {
    val reload = subCommand {
        execute<CommandSender> { sender, _, _ ->
            reloadCommand(sender)
        }
    }

    private fun reloadCommand(sender: CommandSender) {
        submit(async = true) {
            // 准备重载
            ConfigManager.reload()
            sender.sendMessage(ConfigManager.reloaded)
        }
    }
}