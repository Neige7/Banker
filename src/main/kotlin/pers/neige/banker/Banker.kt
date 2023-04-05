package pers.neige.banker

import taboolib.common.platform.Plugin
import taboolib.common.platform.function.info

object Banker : Plugin() {

    override fun onEnable() {
        info("Successfully running ExamplePlugin!")
    }
}