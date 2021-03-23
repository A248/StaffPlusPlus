package net.shortninja.staffplus.core.domain.staff.mute.gui;

import net.shortninja.staffplus.core.StaffPlus;
import be.garagepoort.mcioc.IocContainer;
import net.shortninja.staffplus.core.common.Items;
import net.shortninja.staffplus.core.common.gui.AbstractGui;
import net.shortninja.staffplus.core.common.gui.IAction;
import net.shortninja.staffplus.core.domain.staff.mute.Mute;
import org.bukkit.inventory.ItemStack;

import java.util.function.Supplier;

public class ManageMutedPlayerGui extends AbstractGui {
    private static final int SIZE = 54;

    private final Mute mute;
    private final MutedPlayerItemBuilder mutedPlayerItemBuilder = StaffPlus.get().iocContainer.get(MutedPlayerItemBuilder.class);

    public ManageMutedPlayerGui(String title, Mute mute, Supplier<AbstractGui> previousGuiSupplier) {
        super(SIZE, title, previousGuiSupplier);
        this.mute = mute;
    }

    @Override
    public void buildGui() {
        IAction unmuteAction = new UnmutePlayerAction();

        setItem(13, mutedPlayerItemBuilder.build(mute), null);

        addUnmuteItem(mute, unmuteAction, 30);
        addUnmuteItem(mute, unmuteAction, 31);
        addUnmuteItem(mute, unmuteAction, 32);
        addUnmuteItem(mute, unmuteAction, 39);
        addUnmuteItem(mute, unmuteAction, 40);
        addUnmuteItem(mute, unmuteAction, 41);
    }

    private void addUnmuteItem(Mute mute, IAction action, int slot) {
        ItemStack item = StaffPlus.get().versionProtocol.addNbtString(
            Items.editor(Items.createRedColoredGlass("Unmute player", "Click to unmute this player"))
                .setAmount(1)
                .build(), String.valueOf(mute.getId()));
        setItem(slot, item, action);
    }
}