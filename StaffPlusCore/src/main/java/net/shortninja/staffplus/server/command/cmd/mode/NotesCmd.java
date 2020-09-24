package net.shortninja.staffplus.server.command.cmd.mode;

import net.shortninja.staffplus.IocContainer;
import net.shortninja.staffplus.common.CommandUtil;
import net.shortninja.staffplus.common.NoPermissionException;
import net.shortninja.staffplus.session.SessionManager;
import net.shortninja.staffplus.server.data.config.Messages;
import net.shortninja.staffplus.server.data.config.Options;
import net.shortninja.staffplus.unordered.IPlayerSession;
import net.shortninja.staffplus.util.MessageCoordinator;
import net.shortninja.staffplus.util.PermissionHandler;
import net.shortninja.staffplus.util.lib.JavaUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.util.List;

public class NotesCmd extends BukkitCommand {
    private PermissionHandler permission = IocContainer.getPermissionHandler();
    private MessageCoordinator message = IocContainer.getMessage();
    private Options options = IocContainer.getOptions();
    private Messages messages = IocContainer.getMessages();
    private SessionManager sessionManager = IocContainer.getSessionManager();

    public NotesCmd(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        return CommandUtil.executeCommand(sender, true, () -> {
            if (!permission.has(sender, options.permissionExamine)) {
                throw new NoPermissionException();
            }

            if (args.length == 2) {
                String argument = args[0];
                String option = args[1];
                boolean hasPermission = permission.has(sender, options.permissionExamine);
                Player player = Bukkit.getPlayer(option);

                if (argument.equalsIgnoreCase("get") && hasPermission) {
                    if (player == null) {
                        message.send(sender, messages.playerOffline, messages.prefixGeneral);
                    } else listNotes(sender, player);
                } else if (argument.equalsIgnoreCase("clear") && hasPermission) {
                    if (player == null) {
                        message.send(sender, messages.playerOffline, messages.prefixGeneral);
                    } else clearNotes(sender, player);
                } else addNote(sender, argument, JavaUtils.compileWords(args, 1));
            } else if (args.length >= 3) {
                addNote(sender, args[0], JavaUtils.compileWords(args, 1));
            } else sendHelp(sender);

            return true;
        });
    }

    private void listNotes(CommandSender sender, Player player) {
        IPlayerSession user = sessionManager.get(player.getUniqueId());

        if (user != null) {
            List<String> notes = user.getPlayerNotes();

            for (String message : messages.noteListStart) {
                this.message.send(sender, message.replace("%longline%", this.message.LONG_LINE).replace("%target%", player.getName()).replace("%notes%", Integer.toString(notes.size())), message.contains("%longline%") ? "" : messages.prefixGeneral);
            }

            for (int i = 0; i < notes.size(); i++) {
                String note = notes.get(i);

                message.send(sender, messages.noteListEntry.replace("%count%", Integer.toString(i + 1)).replace("%note%", note), messages.prefixGeneral);
            }

            for (String message : messages.noteListEnd) {
                this.message.send(sender, message.replace("%longline%", this.message.LONG_LINE).replace("%target%", player.getName()).replace("%notes%", Integer.toString(notes.size())), message.contains("%longline%") ? "" : messages.prefixGeneral);
            }
        } else message.send(sender, messages.playerOffline, messages.prefixGeneral);
    }

    private void clearNotes(CommandSender sender, Player player) {
        IPlayerSession user = sessionManager.get(player.getUniqueId());

        if (user != null) {
            user.getPlayerNotes().clear();
            message.send(sender, messages.noteCleared.replace("%target%", player.getName()), messages.prefixGeneral);
        } else message.send(sender, messages.playerOffline, messages.prefixGeneral);
    }

    private void addNote(CommandSender sender, String option, String note) {
        Player player = Bukkit.getPlayer(option);

        if (player != null) {
            sessionManager.get(player.getUniqueId()).addPlayerNote(note);
            ;
            message.send(sender, messages.noteAdded.replace("%target%", player.getName()), messages.prefixGeneral);
        } else message.send(sender, messages.playerOffline, messages.prefixGeneral);
    }

    private void sendHelp(CommandSender sender) {
        message.send(sender, "&7" + message.LONG_LINE, "");
        message.send(sender, "&b/" + getName() + " &7" + getUsage(), messages.prefixGeneral);
        message.send(sender, "&b/" + getName() + " get &7[player]", messages.prefixGeneral);
        message.send(sender, "&b/" + getName() + " clear &7[player]", messages.prefixGeneral);
        message.send(sender, "&7" + message.LONG_LINE, "");
    }
}