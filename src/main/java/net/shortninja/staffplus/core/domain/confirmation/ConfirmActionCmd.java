package net.shortninja.staffplus.core.domain.confirmation;

import be.garagepoort.mcioc.IocContainer;
import net.shortninja.staffplus.core.common.cmd.AbstractCmd;
import net.shortninja.staffplus.core.common.cmd.PlayerRetrievalStrategy;
import net.shortninja.staffplus.core.common.config.Options;
import net.shortninja.staffplus.core.common.exceptions.BusinessException;
import net.shortninja.staffplus.core.domain.player.SppPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

public class ConfirmActionCmd extends AbstractCmd {
    public ConfirmActionCmd(String name) {
        super(name, IocContainer.get(Options.class).permissionMode);
    }

    @Override
    protected boolean executeCmd(CommandSender sender, String alias, String[] args, SppPlayer player) {
        if (!(sender instanceof Player)) {
            throw new BusinessException(messages.onlyPlayers);
        }
        String action = args[0];
        UUID uuid = UUID.fromString(args[1]);

        if (action.equalsIgnoreCase("confirm")) {
            IocContainer.get(ConfirmationChatService.class).confirmAction(uuid, (Player) sender);
            return true;
        }
        if (action.equalsIgnoreCase("cancel")) {
            IocContainer.get(ConfirmationChatService.class).cancelAction(uuid, (Player) sender);
            return true;
        }
        return false;
    }

    @Override
    protected int getMinimumArguments(CommandSender sender, String[] args) {
        return 2;
    }

    @Override
    protected PlayerRetrievalStrategy getPlayerRetrievalStrategy() {
        return PlayerRetrievalStrategy.NONE;
    }

    @Override
    protected Optional<String> getPlayerName(CommandSender sender, String[] args) {
        return Optional.empty();
    }
}
