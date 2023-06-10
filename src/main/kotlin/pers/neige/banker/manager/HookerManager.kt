package pers.neige.banker.manager

import org.bukkit.Bukkit
import pers.neige.banker.hook.mythicmobs.MythicMobsHooker
import pers.neige.banker.hook.mythicmobs.impl.MythicMobsHookerImpl459
import pers.neige.banker.hook.mythicmobs.impl.MythicMobsHookerImpl490
import pers.neige.banker.hook.mythicmobs.impl.MythicMobsHookerImpl502
import pers.neige.banker.hook.mythicmobs.impl.MythicMobsHookerImpl510
import pers.neige.banker.manager.ConfigManager.invalidPlugin
import taboolib.common.LifeCycle
import taboolib.common.platform.Awake

/**
 * 插件兼容管理器, 用于尝试与各个软依赖插件取得联系
 */
object HookerManager {
    // 某些情况下 MythicMobs 的 ItemManager 加载顺序很奇怪，因此写成 by lazy, 然后在 active 阶段主动调用
    // 没事儿改包名很爽吗, 写MM的, 你妈死了
    val mythicMobsHooker: MythicMobsHooker? by lazy {
        try {
            try {
                // 5.0.3+
                Class.forName("io.lumine.mythic.bukkit.utils.config.file.YamlConfiguration")
                MythicMobsHookerImpl510()
            } catch (error: Throwable) {
                try {
                    // 5.0.3-
                    Class.forName("io.lumine.mythic.utils.config.file.YamlConfiguration")
                    Class.forName("io.lumine.mythic.bukkit.MythicBukkit")
                    MythicMobsHookerImpl502()
                } catch (error: Throwable) {
                    try {
                        // 5.0.0-
                        Class.forName("io.lumine.xikage.mythicmobs.utils.config.file.YamlConfiguration")
                        MythicMobsHookerImpl490()
                    } catch (error: Throwable) {
                        // 4.7.2-
                        Class.forName("io.lumine.utils.config.file.YamlConfiguration")
                        MythicMobsHookerImpl459()
                    }
                }
            }
        } catch (error: Throwable) {
            Bukkit.getLogger().info(invalidPlugin?.replace("{plugin}", "MythicMobs"))
            null
        }
    }

    /**
     * 加载MM挂钩功能
     */
    @Awake(LifeCycle.ACTIVE)
    fun loadMythicMobsHooker() {
        mythicMobsHooker
    }
}