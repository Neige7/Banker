package pers.neige.banker

import taboolib.common.platform.Plugin
import taboolib.platform.BukkitPlugin

object Banker : Plugin() {
    val plugin by lazy { BukkitPlugin.getInstance() }
}