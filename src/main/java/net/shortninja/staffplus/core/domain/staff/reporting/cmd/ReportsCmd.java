package net.shortninja.staffplus.core.domain.staff.reporting.cmd;

import be.garagepoort.mcioc.IocBean;
import be.garagepoort.mcioc.IocMultiProvider;
import net.shortninja.staffplus.core.common.cmd.AbstractCmd;
import net.shortninja.staffplus.core.common.cmd.CommandService;
import net.shortninja.staffplus.core.common.cmd.PlayerRetrievalStrategy;
import net.shortninja.staffplus.core.common.cmd.SppCommand;
import net.shortninja.staffplus.core.common.config.Messages;
import net.shortninja.staffplus.core.common.config.Options;
import net.shortninja.staffplus.core.common.exceptions.NoPermissionException;
import net.shortninja.staffplus.core.common.utils.MessageCoordinator;
import net.shortninja.staffplus.core.common.utils.PermissionHandler;
import net.shortninja.staffplus.core.domain.player.SppPlayer;
import net.shortninja.staffplus.core.domain.staff.reporting.ManageReportService;
import net.shortninja.staffplus.core.domain.staff.reporting.Report;
import net.shortninja.staffplus.core.domain.staff.reporting.ReportService;
import net.shortninja.staffplusplus.reports.IReport;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@IocBean(conditionalOnProperty = "reports-module.enabled=true")
@IocMultiProvider(SppCommand.class)
public class ReportsCmd extends AbstractCmd {
    private final PermissionHandler permissionHandler;
    private final ReportService reportService;
    private final ManageReportService manageReportService;

    public ReportsCmd(PermissionHandler permissionHandler,
                      Messages messages,
                      MessageCoordinator message,
                      Options options,
                      ReportService reportService,
                      ManageReportService manageReportService,
                      CommandService commandService) {

        super(options.commandReports, messages, message, options, commandService);
        this.permissionHandler = permissionHandler;
        this.reportService = reportService;
        this.manageReportService = manageReportService;
        setDescription("Manage Reports.");
        setUsage("[get|clear] [player]");
    }

    @Override
    protected boolean executeCmd(CommandSender sender, String alias, String[] args, SppPlayer player) {
        String argument = args[0];

        if (argument.equalsIgnoreCase("get")) {
            if (!permissionHandler.has(sender, options.manageReportConfiguration.getPermissionView())) {
                throw new NoPermissionException();
            }
            listReports(sender, player);
            return true;
        }
        if (argument.equalsIgnoreCase("clear")) {
            if (!permissionHandler.has(sender, options.manageReportConfiguration.getPermissionDelete())) {
                throw new NoPermissionException();
            }
            clearReports(sender, player);
            return true;
        }

        sendHelp(sender);
        return true;
    }

    @Override
    protected int getMinimumArguments(CommandSender sender, String[] args) {
        return 2;
    }

    @Override
    protected PlayerRetrievalStrategy getPlayerRetrievalStrategy() {
        return PlayerRetrievalStrategy.BOTH;
    }

    @Override
    protected Optional<String> getPlayerName(CommandSender sender, String[] args) {
        return Optional.of(args[1]);
    }

    private void listReports(CommandSender sender, SppPlayer player) {
        List<Report> reports = reportService.getReports(player, 0, 40);

        for (String message : messages.reportsListStart) {
            this.message.send(sender, message.replace("%longline%", this.message.LONG_LINE).replace("%target%", player.getUsername()).replace("%reports%", Integer.toString(reports.size())), message.contains("%longline%") ? "" : messages.prefixReports);
        }

        for (int i = 0; i < reports.size(); i++) {
            IReport report = reports.get(i);
            message.send(sender, messages.reportsListEntry
                .replace("%count%", Integer.toString(i + 1))
                .replace("%reason%", report.getReason())
                .replace("%reporter%", report.getReporterName()), messages.prefixReports);
        }

        messages.reportsListEnd
            .forEach(message -> this.message.send(sender, message.replace("%longline%", this.message.LONG_LINE).replace("%target%", player.getUsername()).replace("%reports%", Integer.toString(reports.size())), message.contains("%longline%") ? "" : messages.prefixReports));
    }

    private void clearReports(CommandSender sender, SppPlayer player) {
        manageReportService.clearReports(player);
        message.send(sender, messages.reportsCleared.replace("%target%", player.getUsername()), messages.prefixReports);
    }

    private void sendHelp(CommandSender sender) {
        message.send(sender, "&7" + message.LONG_LINE, "");
        message.send(sender, "&b/" + getName() + " &7" + getUsage(), messages.prefixReports);
        message.send(sender, "&b/" + getName() + " get &7[player]", messages.prefixReports);
        message.send(sender, "&b/" + getName() + " clear &7[player]", messages.prefixReports);
        message.send(sender, "&7" + message.LONG_LINE, "");
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        List<String> suggestions = new ArrayList<>();
        if (args.length == 1 && (!args[0].equals("get") && !args[0].equals("clear"))) {
            suggestions.add("get");
            suggestions.add("clear");
            return suggestions;
        }

        return Collections.emptyList();
    }
}