package pers.neige.banker.hook.mythicmobs.impl

import io.lumine.xikage.mythicmobs.MythicMobs
import io.lumine.xikage.mythicmobs.api.bukkit.events.MythicMobDeathEvent
import org.bukkit.entity.Entity
import org.bukkit.event.entity.EntityDamageByEntityEvent
import pers.neige.banker.hook.mythicmobs.MythicMobsHooker
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.function.registerBukkitListener
import taboolib.common.platform.function.submit
import kotlin.math.roundToInt

/**
 * 4.9.0版本MM挂钩
 *
 * @constructor 启用4.9.0版本MM挂钩
 */
class MythicMobsHookerImpl490 : MythicMobsHooker() {
    override fun isMythicMob(entity: Entity): Boolean {
        return apiHelper.isMythicMob(entity)
    }

    override fun getMythicId(entity: Entity): String? {
        return if (apiHelper.isMythicMob(entity))
            return apiHelper.getMythicMobInstance(entity).type.internalName
        else
            null
    }

    private val apiHelper = MythicMobs.inst().apiHelper

    override val damageListener = registerBukkitListener(EntityDamageByEntityEvent::class.java, priority = EventPriority.MONITOR) {
        damageEvent(it)
    }

    override val deathListener = registerBukkitListener(MythicMobDeathEvent::class.java, priority = EventPriority.MONITOR) {
        submit(async = true) {
            deathEvent(
                it.entity,
                it.mobType.internalName,
                it.mobLevel
            )
        }
    }

    init {
        loadMobConfigs()
    }
}