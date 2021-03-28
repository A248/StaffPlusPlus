package net.shortninja.staffplus.core.domain.staff.mode.cmd;

import be.garagepoort.mcioc.IocBean;
import be.garagepoort.mcioc.IocMultiProvider;
import net.shortninja.staffplus.core.common.cmd.AbstractCmd;
import net.shortninja.staffplus.core.common.cmd.CommandService;
import net.shortninja.staffplus.core.common.cmd.PlayerRetrievalStrategy;
import net.shortninja.staffplus.core.common.cmd.SppCommand;
import net.shortninja.staffplus.core.common.config.Messages;
import net.shortninja.staffplus.core.common.config.Options;
import net.shortninja.staffplus.core.common.utils.MessageCoordinator;
import net.shortninja.staffplus.core.domain.player.SppPlayer;
import net.shortninja.staffplus.core.domain.staff.mode.handler.GadgetHandler;
import org.bukkit.command.CommandSender;

import java.util.Optional;

@IocBean
@IocMultiProvider(SppCommand.class)
public class CpsCmd extends AbstractCmd {
    private final GadgetHandler gadgetHandler;

    public CpsCmd(Messages messages, MessageCoordinator message, Options options, GadgetHandler gadgetHandler, CommandService commandService) {
        super(options.commandCps, messages, message, options, commandService);
        this.gadgetHandler = gadgetHandler;
        setDescription("Starts a CPS test on the player.");
        setUsage("{player}");
        setPermission(options.permissionCps);
    }

    @Override
    public boolean executeCmd(CommandSender sender, String alias, String[] args, SppPlayer targetPlayer) {
        gadgetHandler.onCps(sender, targetPlayer.getPlayer());
        return true;
    }

    @Override
    protected int getMinimumArguments(CommandSender sender, String[] args) {
        return 1;
    }

    @Override
    protected PlayerRetrievalStrategy getPlayerRetrievalStrategy() {
        return PlayerRetrievalStrategy.ONLINE;
    }

    @Override
    protected Optional<String> getPlayerName(CommandSender sender, String[] args) {
        return Optional.ofNullable(args[0]);
    }
}