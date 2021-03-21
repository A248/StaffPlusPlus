package net.shortninja.staffplus.domain.staff.reporting.gui;

import net.shortninja.staffplus.application.IocContainer;
import net.shortninja.staffplus.common.gui.IAction;
import net.shortninja.staffplus.common.gui.PagedGui;
import net.shortninja.staffplus.domain.player.SppPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.stream.Collectors;

public class MyReportsGui extends PagedGui {

    public MyReportsGui(Player player, String title, int page) {
        super(player, title, page);
    }

    @Override
    protected MyReportsGui getNextUi(Player player, SppPlayer target, String title, int page) {
        return new MyReportsGui(player, title, page);
    }

    @Override
    public IAction getAction() {
        return new IAction() {
            @Override
            public void click(Player player, ItemStack item, int slot) {
                //Do nothing
            }

            @Override
            public boolean shouldClose(Player player) {
                return false;
            }
        };
    }

    @Override
    public List<ItemStack> getItems(Player player, SppPlayer target, int offset, int amount) {
        return IocContainer.getReportService()
                .getMyReports(player.getUniqueId(), offset, amount)
                .stream()
                .map(ReportItemBuilder::build)
                .collect(Collectors.toList());
    }
}