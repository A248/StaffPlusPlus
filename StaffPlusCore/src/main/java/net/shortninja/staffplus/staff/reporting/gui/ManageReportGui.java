package net.shortninja.staffplus.staff.reporting.gui;

import net.shortninja.staffplus.IocContainer;
import net.shortninja.staffplus.StaffPlus;
import net.shortninja.staffplus.common.cmd.CommandUtil;
import net.shortninja.staffplus.player.attribute.gui.AbstractGui;
import net.shortninja.staffplus.server.data.config.Options;
import net.shortninja.staffplus.staff.reporting.Report;
import net.shortninja.staffplus.staff.reporting.ReportService;
import net.shortninja.staffplus.session.SessionManager;
import net.shortninja.staffplus.unordered.IAction;
import net.shortninja.staffplus.util.Permission;
import net.shortninja.staffplus.util.lib.hex.Items;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ManageReportGui extends AbstractGui {
    private static final int SIZE = 54;

    private final SessionManager sessionManager = IocContainer.getSessionManager();
    private final ReportService reportService = IocContainer.getReportService();
    private final Permission permission = IocContainer.getPermissionHandler();
    private final Options options = IocContainer.getOptions();

    public ManageReportGui(Player player, String title, Report report) {
        super(SIZE, title);


        IAction reopenAction = new IAction() {
            @Override
            public void click(Player player, ItemStack item, int slot) {
                CommandUtil.playerAction(player, () -> {
                    int reportId = Integer.parseInt(StaffPlus.get().versionProtocol.getNbtString(item));
                    reportService.reopenReport(player, reportId);
                });
            }

            @Override
            public boolean shouldClose() {
                return true;
            }
        };

        IAction resolveAction = new ResolveReportAction();
        IAction rejectAction = new RejectReportAction();
        IAction deleteAction = new IAction() {
            @Override
            public void click(Player player, ItemStack item, int slot) {
                CommandUtil.playerAction(player, () -> {
                    int reportId = Integer.parseInt(StaffPlus.get().versionProtocol.getNbtString(item));
                    reportService.deleteReport(player, reportId);
                });
            }

            @Override
            public boolean shouldClose() {
                return true;
            }
        };

        setItem(13, ReportItemBuilder.build(report), null);

        addResolveItem(report, resolveAction, 34);
        addResolveItem(report, resolveAction, 35);
        addResolveItem(report, resolveAction, 43);
        addResolveItem(report, resolveAction, 44);

        addReopenItem(report, reopenAction, 27);
        addReopenItem(report, reopenAction, 28);
        addReopenItem(report, reopenAction, 36);
        addReopenItem(report, reopenAction, 37);

        addRejectItem(report, rejectAction, 30);
        addRejectItem(report, rejectAction, 31);
        addRejectItem(report, rejectAction, 32);
        addRejectItem(report, rejectAction, 39);
        addRejectItem(report, rejectAction, 40);
        addRejectItem(report, rejectAction, 41);

        if(permission.has(player, options.reportConfiguration.getDeletionPermission())) {
            addDeleteItem(report, deleteAction, 8);
        }

        player.closeInventory();
        player.openInventory(getInventory());
        sessionManager.get(player.getUniqueId()).setCurrentGui(this);
    }

    private void addResolveItem(Report report, IAction action, int slot) {
        ItemStack item = StaffPlus.get().versionProtocol.addNbtString(
            Items.editor(Items.createGreenColoredGlass("Resolve report", "Click to mark this report as resolved"))
                .setAmount(1)
                .build(), String.valueOf(report.getId()));
        setItem(slot, item, action);
    }

    private void addRejectItem(Report report, IAction action, int slot) {
        ItemStack item = StaffPlus.get().versionProtocol.addNbtString(
            Items.editor(Items.createRedColoredGlass("Reject report", "Click to mark this report as rejected"))
                .setAmount(1)
                .build(), String.valueOf(report.getId()));
        setItem(slot, item, action);
    }

    private void addReopenItem(Report report, IAction action, int slot) {
        ItemStack item = StaffPlus.get().versionProtocol.addNbtString(
            Items.editor(Items.createGrayColoredGlass("Unassign", "Click to unassign yourself from this report"))
                .setAmount(1)
                .build(), String.valueOf(report.getId()));
        setItem(slot, item, action);
    }

    private void addDeleteItem(Report report, IAction action, int slot) {
        ItemStack itemstack = Items.builder()
            .setMaterial(Material.REDSTONE_BLOCK)
            .setName("Delete")
            .addLore("Click to delete this report")
            .build();

        ItemStack item = StaffPlus.get().versionProtocol.addNbtString(
            Items.editor(itemstack)
                .setAmount(1)
                .build(), String.valueOf(report.getId()));
        setItem(slot, item, action);
    }
}