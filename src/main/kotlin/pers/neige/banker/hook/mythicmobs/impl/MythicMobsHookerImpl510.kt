package pers.neige.banker.hook.mythicmobs.impl

import io.lumine.mythic.bukkit.MythicBukkit
import io.lumine.mythic.bukkit.events.MythicMobDeathEvent
import org.bukkit.entity.Entity
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Projectile
import org.bukkit.event.entity.EntityDamageByEntityEvent
import pers.neige.banker.hook.mythicmobs.MythicMobsHooker
import pers.neige.banker.manager.ConfigManager
import taboolib.common.platform.event.EventPriority
import taboolib.common.platform.function.registerBukkitListener
import taboolib.common.platform.function.submit
import java.util.concurrent.ConcurrentHashMap

/**
 * 5.1.0版本MM挂钩
 *
 * @constructor 启用5.1.0版本MM挂钩
 */
class MythicMobsHookerImpl510 : MythicMobsHooker() {
    override fun isMythicMob(entity: Entity): Boolean {
        return apiHelper.isMythicMob(entity)
    }

    override fun getMythicId(entity: Entity): String? {
        return if (apiHelper.isMythicMob(entity))
            apiHelper.getMythicMobInstance(entity).type.internalName
        else
            null
    }

    private val apiHelper = MythicBukkit.inst().apiHelper

    override val damageListener = registerBukkitListener(EntityDamageByEntityEvent::class.java, priority = EventPriority.MONITOR) {
        damageEvent(it)
    }

    override val deathListener = registerBukkitListener(MythicMobDeathEvent::class.java, priority = EventPriority.MONITOR) {
        submit(async = true) {
            deathEvent(it.entity, it.mobType.internalName)
        }
    }

    init {
        loadMobConfigs()
    }
}