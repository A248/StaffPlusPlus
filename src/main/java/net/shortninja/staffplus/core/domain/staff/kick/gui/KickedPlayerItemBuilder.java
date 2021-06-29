package net.shortninja.staffplus.core.domain.staff.kick.gui;

import be.garagepoort.mcioc.IocBean;
import be.garagepoort.mcioc.IocMultiProvider;
import net.shortninja.staffplus.core.common.IProtocolService;
import net.shortninja.staffplus.core.common.Items;
import net.shortninja.staffplus.core.application.config.Options;
import net.shortninja.staffplus.core.domain.staff.infractions.InfractionType;
import net.shortninja.staffplus.core.domain.staff.infractions.gui.InfractionGuiProvider;
import net.shortninja.staffplus.core.domain.staff.kick.Kick;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static net.shortninja.staffplus.core.common.JavaUtils.formatLines;

@IocBean
@IocMultiProvider(InfractionGuiProvider.class)
public class KickedPlayerItemBuilder implements InfractionGuiProvider<Kick> {
    private final IProtocolService protocolService;
    private final Options options;

    public KickedPlayerItemBuilder(IProtocolService protocolService, Options options) {
        this.protocolService = protocolService;
        this.options = options;
    }

    public ItemStack build(Kick kick) {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(kick.getCreationDate().toInstant(), ZoneOffset.UTC);
        String time = localDateTime.truncatedTo(ChronoUnit.SECONDS).format(DateTimeFormatter.ofPattern(options.timestampFormat));

        List<String> lore = new ArrayList<>();

        lore.add("&bId: " + kick.getId());
        if(options.serverSyncConfiguration.isKickSyncEnabled()) {
            lore.add("&bServer: " + kick.getServerName());
        }
        lore.add("&bKicked player: " + kick.getTargetName());
        lore.add("&bIssuer: " + kick.getIssuerName());
        lore.add("&bIssued on: " + time);
        lore.add("&bReason:");
        for (String line : formatLines(kick.getReason(), 30)) {
            lore.add("  &b" + line);
        }

        ItemStack item = Items.builder()
            .setMaterial(Material.BANNER)
            .setName("&6Kick")
            .addLore(lore)
            .build();

        return protocolService.getVersionProtocol().addNbtString(item, String.valueOf(kick.getId()));
    }

    @Override
    public InfractionType getType() {
        return InfractionType.KICK;
    }

    @Override
    public ItemStack getMenuItem(Kick kick) {
        ItemStack itemStack = build(kick);
        itemStack.setType(options.infractionsConfiguration.getKicksGuiItem());
        return itemStack;
    }
}
