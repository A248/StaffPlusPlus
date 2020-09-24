package net.shortninja.staffplus.server.command.cmd.mode;

import net.shortninja.staffplus.IocContainer;
import net.shortninja.staffplus.StaffPlus;
import net.shortninja.staffplus.common.BusinessException;
import net.shortninja.staffplus.player.SppPlayer;
import net.shortninja.staffplus.player.attribute.mode.handler.GadgetHandler;
import net.shortninja.staffplus.server.command.AbstractCmd;
import net.shortninja.staffplus.server.command.PlayerRetrievalStrategy;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class FollowCmd extends AbstractCmd {

    private final GadgetHandler gadgetHandler = StaffPlus.get().gadgetHandler;

    public FollowCmd(String name) {
        super(name, IocContainer.getOptions().permissionFollow);
    }

    @Override
    public boolean executeCmd(CommandSender sender, String alias, String[] args, SppPlayer targetPlayer) {
        if (!(sender instanceof Player)) {
            throw new BusinessException(messages.onlyPlayers);
        }

        gadgetHandler.onFollow((Player) sender, targetPlayer.getPlayer());
        return true;
    }

    @Override
    protected boolean canBypass(Player player) {
        return false;
    }

    @Override
    protected int getMinimumArguments(CommandSender sender, String[] args) {
        return 1;
    }

    @Override
    protected boolean isAuthenticationRequired() {
        return true;
    }

    @Override
    protected PlayerRetrievalStrategy getPlayerRetrievalStrategy() {
        return PlayerRetrievalStrategy.ONLINE;
    }

    @Override
    protected Optional<String> getPlayerName(CommandSender sender, String[] args) {
        return Optional.ofNullable(args[0]);
    }

    @Override
    protected boolean isDelayable() {
        return false;
    }
}