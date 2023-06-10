package pers.neige.banker.loot.impl

import org.bukkit.Bukkit
import org.bukkit.configuration.ConfigurationSection
import pers.neige.banker.loot.LootGenerator
import pers.neige.neigeitems.manager.ActionManager
import pers.neige.neigeitems.utils.SamplingUtils
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.min

class MultiPack(data: ConfigurationSection) : LootGenerator(data) {
    override val type: String = "MULTIPACK"

    // 获取战利品动作
    private val lootAction = let {
        var lootAction = data.get("LootAction")
        if (lootAction !is List<*>) {
            lootAction = arrayListOf(lootAction)
        }
        lootAction as List<*>
    }

    private val amount = data.getInt("Amount")

    override fun run(
        damageData: ConcurrentHashMap<String, Double>,
        sortedDamageData: MutableList<MutableMap.MutableEntry<String, Double>>,
        totalDamage: Double
    ) {
        // 选取玩家ID
        val names = SamplingUtils.aExpj(damageData, min(damageData.size, amount))
        for (name in names) {
            // 获取在线玩家, 玩家不在线则停止执行
            val player = Bukkit.getPlayer(name) ?: continue
            // 执行动作
            ActionManager.runAction(
                player,
                lootAction,
                hashMapOf(
                    "damage" to "%.2f".format(damageData[name]),
                    "totalDamage" to "%.2f".format(totalDamage)
                ),
                null
            )
        }
    }
}