package net.shortninja.staffplus.player.attribute.gui.hub;

import net.shortninja.staffplus.IocContainer;
import net.shortninja.staffplus.common.config.GuiItemConfig;
import net.shortninja.staffplus.session.PlayerSession;
import net.shortninja.staffplus.player.attribute.gui.AbstractGui;
import net.shortninja.staffplus.staff.protect.cmd.ProtectedAreasGui;
import net.shortninja.staffplus.staff.reporting.config.ReportConfiguration;
import net.shortninja.staffplus.staff.reporting.gui.ClosedReportsGui;
import net.shortninja.staffplus.staff.reporting.gui.MyReportsGui;
import net.shortninja.staffplus.staff.reporting.gui.OpenReportsGui;
import net.shortninja.staffplus.server.data.config.Options;
import net.shortninja.staffplus.unordered.IAction;
import net.shortninja.staffplus.util.lib.hex.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class HubGui extends AbstractGui {
    private static final int SIZE = 27;
    private final Options options = IocContainer.getOptions();
    private final GuiItemConfig protectGuiItemConfig;
    private final ReportConfiguration reportConfiguration;

    public HubGui(Player player, String title) {
        super(SIZE, title);
        protectGuiItemConfig = options.protectConfiguration.getGuiItemConfig();
        reportConfiguration = options.reportConfiguration;

        if (reportConfiguration.getOpenReportsGui().isEnabled()) {
            setItem(1, reportsItem(), new IAction() {
                @Override
                public void click(Player player, ItemStack item, int slot) {
                    new OpenReportsGui(player, reportConfiguration.getOpenReportsGui().getTitle(), 0);
                }

                @Override
                public boolean shouldClose() {
                    return false;
                }
            });
            setItem(2, myReportsItem(), new IAction() {
                @Override
                public void click(Player player, ItemStack item, int slot) {
                    new MyReportsGui(player, options.reportConfiguration.getMyReportsGui().getTitle(), 0);
                }

                @Override
                public boolean shouldClose() {
                    return false;
                }
            });
            setItem(3, closedReportsItem(), new IAction() {
                @Override
                public void click(Player player, ItemStack item, int slot) {
                    new ClosedReportsGui(player, options.reportConfiguration.getClosedReportsGui().getTitle(), 0);
                }

                @Override
                public boolean shouldClose() {
                    return false;
                }
            });
        }

        if (options.modeGuiMiner) {
            setItem(10, minerItem(), new IAction() {
                @Override
                public void click(Player player, ItemStack item, int slot) {
                    new MinerGui(player, options.modeGuiMinerTitle);
                }

                @Override
                public boolean shouldClose() {
                    return false;
                }
            });
        }

        if (protectGuiItemConfig.isEnabled()) {
            setItem(19, shieldItem(), new IAction() {
                @Override
                public void click(Player player, ItemStack item, int slot) {
                    new ProtectedAreasGui(player, protectGuiItemConfig.getTitle(), 0);
                }

                @Override
                public boolean shouldClose() {
                    return false;
                }
            });
        }

        PlayerSession playerSession = IocContainer.getSessionManager().get(player.getUniqueId());
        setGlass(playerSession);
        player.openInventory(getInventory());
        playerSession.setCurrentGui(this);
    }

    private ItemStack reportsItem() {
        ItemStack item = Items.builder()
                .setMaterial(Material.PAPER).setAmount(1)
                .setName(reportConfiguration.getOpenReportsGui().getItemName())
                .addLore(reportConfiguration.getOpenReportsGui().getItemLore())
                .build();

        return item;
    }
    private ItemStack myReportsItem() {
        ItemStack item = Items.builder()
                .setMaterial(Material.PAPER).setAmount(1)
                .setName(reportConfiguration.getMyReportsGui().getTitle())
                .addLore(reportConfiguration.getMyReportsGui().getItemName())
                .build();

        return item;
    }

    private ItemStack closedReportsItem() {
        ItemStack item = Items.builder()
                .setMaterial(Material.PAPER).setAmount(1)
            .setName(reportConfiguration.getClosedReportsGui().getTitle())
            .addLore(reportConfiguration.getClosedReportsGui().getItemName())
                .build();

        return item;
    }

    private ItemStack minerItem() {
        ItemStack item = Items.builder()
                .setMaterial(Material.STONE_PICKAXE).setAmount(1)
                .setName(options.modeGuiMinerName)
                .addLore(options.modeGuiMinerLore)
                .build();

        return item;
    }

    private ItemStack shieldItem() {
        ItemStack item = Items.builder()
                .setMaterial(Material.SHIELD).setAmount(1)
                .setName(protectGuiItemConfig.getItemName())
                .addLore(protectGuiItemConfig.getItemLore())
                .build();

        return item;
    }
}