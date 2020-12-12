package net.shortninja.staffplus.staff.ban.gui;

import net.shortninja.staffplus.IocContainer;
import net.shortninja.staffplus.StaffPlus;
import net.shortninja.staffplus.player.attribute.gui.AbstractGui;
import net.shortninja.staffplus.session.SessionManager;
import net.shortninja.staffplus.staff.ban.Ban;
import net.shortninja.staffplus.unordered.IAction;
import net.shortninja.staffplus.util.lib.hex.Items;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class ManageBannedPlayerGui extends AbstractGui {
    private static final int SIZE = 54;

    private final SessionManager sessionManager = IocContainer.getSessionManager();

    public ManageBannedPlayerGui(Player player, String title, Ban ban, Supplier<AbstractGui> previousGuiSupplier) {
        super(SIZE, title, previousGuiSupplier);

        IAction unbanAction = new UnbanPlayerAction();

        setItem(13, BannedPlayerItemBuilder.build(ban), null);

        addUnbanItem(ban, unbanAction, 30);
        addUnbanItem(ban, unbanAction, 31);
        addUnbanItem(ban, unbanAction, 32);
        addUnbanItem(ban, unbanAction, 39);
        addUnbanItem(ban, unbanAction, 40);
        addUnbanItem(ban, unbanAction, 41);

        player.closeInventory();
        player.openInventory(getInventory());
        sessionManager.get(player.getUniqueId()).setCurrentGui(this);
    }

    private void addUnbanItem(Ban ban, IAction action, int slot) {
        ItemStack item = StaffPlus.get().versionProtocol.addNbtString(
            Items.editor(Items.createRedColoredGlass("Unban player", "Click to unban this player"))
                .setAmount(1)
                .build(), String.valueOf(ban.getId()));
        setItem(slot, item, action);
    }
}