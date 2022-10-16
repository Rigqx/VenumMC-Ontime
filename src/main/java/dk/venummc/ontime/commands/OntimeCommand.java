package dk.venummc.ontime.commands;

import dev.dbassett.skullcreator.SkullCreator;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dk.venummc.ontime.Ontime;
import dk.venummc.ontime.utils.ItemStackBuilder;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

import static org.reflections.util.ConfigurationBuilder.build;

public class OntimeCommand implements CommandExecutor {
    public String getPlaytime(Player player){
        // Sætter values
        int onlineTicks = player.getStatistic(Statistic.PLAY_ONE_TICK);
        int playTime = onlineTicks/20;
        int days = (int) Math.floor(playTime / 86400);
        int hours = (int) Math.floor((playTime - (days * 86400)) / 3600);
        int minutes = (int) Math.floor((playTime - ((days * 86400) + (hours * 3600))) / 60);
        int seconds = (int) Math.floor(playTime - ((days * 86400) + (hours * 3600) + (minutes * 60)));
        // Return statement
        return days + " Dage, " + hours + " Timer, " + minutes + " Minutter, " + seconds + " Sekunder!";
    }

    Gui gui = Gui.gui()
            .title(Component.text(Ontime.getInstance().getConfig().getString("prefix")))
            .rows(5)
            .create();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player p = (Player) sender;
        if(sender instanceof Player) {
            if (args.length == 0) {
                ItemStack nop = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 3);
                gui.getFiller().fillBottom((ItemBuilder.from(Material.STAINED_GLASS_PANE)).setName("§f").asGuiItem());
                gui.getFiller().fillTop((ItemBuilder.from(nop)).setName("§f").asGuiItem());
                ItemStack skull = new ItemStackBuilder()
                        .withName("§f")
                        .withLore("§f")
                        .toSkullBuilder().withOwner(p.getPlayer().getName())
                        .buildSkull();
                GuiItem guiItem = ItemBuilder.from(skull).setLore("", "§8§l» §bDin §fOntime§8: §b" + getPlaytime(p)).setName("§b" + p.getDisplayName()).asGuiItem();
                gui.setItem(22, guiItem);
                gui.setDefaultClickAction(event -> {
                    event.setCancelled(true);
                    gui.updateItem(22, guiItem);
                });
                gui.open(p);
                return true;
            } else if (args.length >= 1) {
                Player arg = (Player) Bukkit.getPlayer(args[0]);
                if (arg.hasPlayedBefore()) {
                    gui.getFiller().fillTop((ItemBuilder.from(Material.STAINED_GLASS_PANE)).setName("§f").asGuiItem());
                    gui.getFiller().fillBottom((ItemBuilder.from(Material.STAINED_GLASS_PANE)).setName("§f").asGuiItem());
                    ItemStack skull = new ItemStackBuilder()
                            .withName("§f")
                            .withLore("§f")
                            .toSkullBuilder().withOwner(arg.getPlayer().getName())
                            .buildSkull();

                    GuiItem guiItem = ItemBuilder.from(skull).setLore("", "§8§l» §b" + arg.getDisplayName() + "'s §fOntime§8: §b" + getPlaytime(arg)).setName("§b" + arg.getDisplayName()).asGuiItem();
                    gui.setItem(22, guiItem);
                    gui.setDefaultClickAction(event -> {
                        event.setCancelled(true);
                        gui.updateItem(22, guiItem);
                    });
                    gui.open(p);
                    return true;
                } else {
                    p.sendMessage("");
                    p.sendMessage(Ontime.getInstance().getConfig().getString("prefix"));
                    p.sendMessage("");
                    p.sendMessage("§8§l» §fAngiv venligst en gyldig bruger!");
                    p.sendMessage("");
                    return true;
                }
            }
        }
        return true;
    }
}
