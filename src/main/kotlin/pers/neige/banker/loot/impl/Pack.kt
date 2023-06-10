package pers.neige.banker.loot.impl

import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import pers.neige.banker.loot.LootGenerator
import pers.neige.neigeitems.manager.ActionManager
import pers.neige.neigeitems.utils.SamplingUtils
import java.util.concurrent.ConcurrentHashMap

class Pack(data: ConfigurationSection) : LootGenerator(data) {
    override val type: String = "PACK"

    // 获取战利品动作
    private val lootAction = let {
        var lootAction = data.get("LootAction")
        if (lootAction !is List<*>) {
            lootAction = arrayListOf(lootAction)
        }
        lootAction as List<*>
    }

    override fun run(
        damageData: Map<String, Double>,
        sortedDamageData: List<Map.Entry<String, Double>>,
        totalDamage: Double
    ) {
        // 随机选取玩家ID
        SamplingUtils.weight(damageData, totalDamage)?.let { name ->
            // 获取在线玩家
            val player = Bukkit.getPlayer(name)
            // 玩家不在线则停止执行
            if (player != null) {
                hashMapOf(
                    "damage" to "%.2f".format(damageData[name]),
                    "totalDamage" to "%.2f".format(totalDamage)
                ).also { params ->
                    // 执行动作
                    ActionManager.runAction(
                        player,
                        lootAction,
                        params as HashMap<String, Any?>,
                        params
                    )
                }
            }
        }
    }
}