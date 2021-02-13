package net.shortninja.staffplus.staff.warn.appeals;

import me.rayzr522.jsonmessage.JSONMessage;
import net.shortninja.staffplus.StaffPlus;
import net.shortninja.staffplus.common.exceptions.BusinessException;
import net.shortninja.staffplus.common.exceptions.NoPermissionException;
import net.shortninja.staffplus.event.warnings.WarningAppealApprovedEvent;
import net.shortninja.staffplus.event.warnings.WarningAppealRejectedEvent;
import net.shortninja.staffplus.player.PlayerManager;
import net.shortninja.staffplus.player.SppPlayer;
import net.shortninja.staffplus.server.data.config.Messages;
import net.shortninja.staffplus.server.data.config.Options;
import net.shortninja.staffplus.staff.warn.appeals.database.AppealRepository;
import net.shortninja.staffplus.staff.warn.warnings.Warning;
import net.shortninja.staffplus.staff.warn.warnings.database.WarnRepository;
import net.shortninja.staffplus.unordered.AppealStatus;
import net.shortninja.staffplus.util.MessageCoordinator;
import net.shortninja.staffplus.util.Permission;
import net.shortninja.staffplus.util.lib.JavaUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.util.List;
import java.util.Optional;

import static org.bukkit.Bukkit.getScheduler;

public class AppealService {

    private final PlayerManager playerManager;
    private final AppealRepository appealRepository;
    private final WarnRepository warnRepository;
    private final MessageCoordinator message;
    private final Messages messages;
    private final Permission permission;
    private final Options options;

    public AppealService(PlayerManager playerManager, AppealRepository appealRepository, WarnRepository warnRepository, MessageCoordinator message, Messages messages, Permission permission, Options options) {
        this.playerManager = playerManager;
        this.appealRepository = appealRepository;
        this.warnRepository = warnRepository;
        this.message = message;
        this.messages = messages;
        this.permission = permission;
        this.options = options;
    }


    public List<Appeal> getAppeals(int warningId) {
        return appealRepository.getAppeals(warningId);
    }

    public void addAppeal(Player appealer, Warning warning, String reason) {
        if (!permission.has(appealer, options.appealConfiguration.getCreateAppealPermission())) {
            throw new NoPermissionException();
        }
        if (StringUtils.isEmpty(reason)) {
            throw new BusinessException("Reason for appeal can not be empty");
        }
        Appeal appeal = new Appeal(warning.getId(), appealer.getUniqueId(), appealer.getName(), reason);
        appealRepository.addAppeal(appeal);

        String message = messages.appealCreated.replace("%reason%", reason);
        this.message.send(appealer, message, messages.prefixWarnings);

        sendAppealedMessageToStaff(appealer);
    }

    private void sendAppealedMessageToStaff(Player appealer) {
        String manageWarningsCommand = options.manageWarningsConfiguration.getCommandManageWarningsGui() + " " + appealer.getName();
        JSONMessage jsonMessage = JavaUtils.buildClickableMessage(appealer.getName() + " has appealed his warning",
            "View warnings!",
            "Click to open the warnings view",
            manageWarningsCommand);
        this.message.sendGroupMessage(jsonMessage, options.appealConfiguration.getPermissionNotifications());
    }

    public void approveAppeal(Player resolver, int appealId) {
        this.approveAppeal(resolver, appealId, null);
    }

    public void approveAppeal(Player resolver, int appealId, String appealReason) {
        permission.check(resolver, options.appealConfiguration.getApproveAppealPermission());

        Appeal appeal = resolveAppeal(resolver, appealId, AppealStatus.APPROVED, appealReason);
        Warning warning = warnRepository.findWarning(appeal.getWarningId())
            .orElseThrow(() -> new BusinessException("No warning found. Cannot apply appeal"));

        resolveAppeal(resolver, appealId, AppealStatus.APPROVED, appealReason);
        sendAppealMessage(appeal, messages.appealApproved);
        this.message.send(resolver, messages.appealApprove, messages.prefixWarnings);

        sendEvent(new WarningAppealApprovedEvent(warning));
    }

    public void rejectAppeal(Player resolver, int appealId) {
        this.rejectAppeal(resolver, appealId, null);
    }

    public void rejectAppeal(Player resolver, int appealId, String appealReason) {
        permission.check(resolver, options.appealConfiguration.getRejectAppealPermission());

        Appeal appeal = resolveAppeal(resolver, appealId, AppealStatus.REJECTED, appealReason);
        Warning warning = warnRepository.findWarning(appeal.getWarningId())
            .orElseThrow(() -> new BusinessException("No warning found. Cannot apply appeal"));

        resolveAppeal(resolver, appealId, AppealStatus.REJECTED, appealReason);
        sendAppealMessage(appeal, messages.appealRejected);
        this.message.send(resolver, messages.appealReject, messages.prefixWarnings);
        sendEvent(new WarningAppealRejectedEvent(warning));
    }

    private void sendAppealMessage(Appeal appeal, String message) {
        Optional<SppPlayer> appealer = playerManager.getOnOrOfflinePlayer(appeal.getAppealerUuid());
        if (appealer.isPresent() && appealer.get().isOnline()) {
            this.message.send(appealer.get().getPlayer(), message, messages.prefixWarnings);
        }
    }

    private Appeal resolveAppeal(Player resolver, int appealId, AppealStatus rejected, String appealReason) {
        Appeal appeal = appealRepository.findAppeal(appealId).orElseThrow(() -> new BusinessException("No appeal found with id: [" + appealId + "]"));
        appealRepository.updateAppealStatus(appealId, resolver.getUniqueId(), appealReason, rejected);
        return appeal;
    }

    private void sendEvent(Event event) {
        getScheduler().runTask(StaffPlus.get(), () -> {
            Bukkit.getPluginManager().callEvent(event);
        });
    }
}
