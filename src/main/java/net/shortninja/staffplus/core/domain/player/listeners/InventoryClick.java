package net.shortninja.staffplus.core.domain.player.listeners;

import net.shortninja.staffplus.core.StaffPlus;
import be.garagepoort.mcioc.IocContainer;
import net.shortninja.staffplus.core.common.cmd.CommandUtil;
import net.shortninja.staffplus.core.common.config.Options;
import net.shortninja.staffplus.core.common.gui.IAction;
import net.shortninja.staffplus.core.common.gui.PassThroughClickAction;
import net.shortninja.staffplus.core.session.PlayerSession;
import net.shortninja.staffplus.core.session.SessionManagerImpl;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

public class InventoryClick implements Listener {
    private final Options options = IocContainer.get(Options.class);
    private final SessionManagerImpl sessionManager = IocContainer.get(SessionManagerImpl.class);

    public InventoryClick() {
        Bukkit.getPluginManager().registerEvents(this, StaffPlus.get());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        UUID uuid = player.getUniqueId();
        PlayerSession playerSession = sessionManager.get(uuid);
        ItemStack item = event.getCurrentItem();
        int slot = event.getSlot();

        if (!playerSession.getCurrentGui().isPresent() || item == null) {
            if (playerSession.isInStaffMode() && !options.modeConfiguration.isModeInventoryInteraction()) {
                event.setCancelled(true);
            }
            return;
        }

        if (event.getClickedInventory() != null && event.getClickedInventory().equals(playerSession.getCurrentGui().get().getInventory())) {
            IAction action = playerSession.getCurrentGui().get().getAction(slot);
            if (action != null) {
                if (action instanceof PassThroughClickAction) {
                    return;
                }

                CommandUtil.playerAction(player, () -> {
                    action.click(player, item, slot);
                });

                if (action.shouldClose(player)) {
                    player.closeInventory();
                }
            }
            event.setCancelled(true);
        }

    }
}