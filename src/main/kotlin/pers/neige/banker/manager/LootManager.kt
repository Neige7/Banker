package pers.neige.banker.manager

import org.bukkit.configuration.ConfigurationSection
import pers.neige.banker.loot.LootGenerator
import pers.neige.banker.loot.impl.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object LootManager {
    /**
     * 解析配置, 返回战利品生成器(无法解析则返回null)
     *
     * @param data 待解析配置
     * @return 战利品生成器(无法解析则为null)
     */
    fun parseGenerator(data: ConfigurationSection): LootGenerator? {
        val type = data.getString("LootType")
        val upperType = type?.uppercase(Locale.getDefault())
        return lootGenerators[upperType]?.apply(data)
    }

    /**
     * 添加新的战利品生成器
     *
     * @param type 生成器ID
     * @param generator 一个解析配置并返回生成器的Function
     */
    fun addGenerator(type: String, generator: java.util.function.Function<ConfigurationSection, LootGenerator>) {
        lootGenerators[type] = generator
    }

    val lootGenerators = ConcurrentHashMap<String, java.util.function.Function<ConfigurationSection, LootGenerator>>()

    init {
        addGenerator("ALL", java.util.function.Function {
            return@Function All(it)
        })
        addGenerator("MULTIPACK", java.util.function.Function {
            return@Function MultiPack(it)
        })
        addGenerator("PACK", java.util.function.Function {
            return@Function Pack(it)
        })
        addGenerator("RANK", java.util.function.Function {
            return@Function Rank(it)
        })
        addGenerator("SEPARATE", java.util.function.Function {
            return@Function Separate(it)
        })
    }
}