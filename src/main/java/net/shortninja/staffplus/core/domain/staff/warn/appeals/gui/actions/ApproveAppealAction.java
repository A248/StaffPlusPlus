package net.shortninja.staffplus.core.domain.staff.warn.appeals.gui.actions;

import net.shortninja.staffplus.core.StaffPlus;
import net.shortninja.staffplus.core.common.config.Messages;
import net.shortninja.staffplus.core.common.config.Options;
import net.shortninja.staffplus.core.common.gui.IAction;
import net.shortninja.staffplus.core.common.utils.MessageCoordinator;
import net.shortninja.staffplus.core.domain.staff.warn.appeals.AppealService;
import net.shortninja.staffplus.core.session.PlayerSession;
import net.shortninja.staffplus.core.session.SessionManagerImpl;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ApproveAppealAction implements IAction {
    private static final String CANCEL = "cancel";
    private final Messages messages = StaffPlus.get().iocContainer.get(Messages.class);
    private final MessageCoordinator messageCoordinator = StaffPlus.get().iocContainer.get(MessageCoordinator.class);
    private final SessionManagerImpl sessionManager = StaffPlus.get().iocContainer.get(SessionManagerImpl.class);
    private final AppealService appealService = StaffPlus.get().iocContainer.get(AppealService.class);
    private final Options options = StaffPlus.get().iocContainer.get(Options.class);

    private final int appealId;

    public ApproveAppealAction(int appealId) {
        this.appealId = appealId;
    }

    @Override
    public void click(Player player, ItemStack item, int slot) {
        if (options.appealConfiguration.isResolveReasonEnabled()) {
            messageCoordinator.send(player, "&1===================================================", messages.prefixWarnings);
            messageCoordinator.send(player, "&6       You have chosen to approve this appeal", messages.prefixWarnings);
            messageCoordinator.send(player, "&6Type your closing reason in chat to approve the appeal", messages.prefixWarnings);
            messageCoordinator.send(player, "&6      Type \"cancel\" to cancel approving the appeal ", messages.prefixWarnings);
            messageCoordinator.send(player, "&1===================================================", messages.prefixWarnings);
            PlayerSession playerSession = sessionManager.get(player.getUniqueId());
            playerSession.setChatAction((player1, message) -> {
                if (message.equalsIgnoreCase(CANCEL)) {
                    messageCoordinator.send(player, "&CYou have cancelled approving this appeal", messages.prefixWarnings);
                    return;
                }
                appealService.approveAppeal(player, appealId, message);
            });
        } else {
            appealService.approveAppeal(player, appealId);
        }
    }

    @Override
    public boolean shouldClose(Player player) {
        return true;
    }
}
