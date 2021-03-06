package com.miningmark48.pearcelbot.commands;

import com.miningmark48.pearcelbot.ICommand;
import com.miningmark48.pearcelbot.reference.Reference;
import com.sun.xml.internal.ws.util.StringUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDAInfo;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

public class CommandBotInfo implements ICommand {

    public static final String desc = "Returns bot information.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "botinfo";
    public static final String info = desc + " " + usage;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.decode("#A2F000"));
        embedBuilder.setAuthor(event.getJDA().getSelfUser().getName() + " Information", "http://www.miningmark48.xyz/pearcelbot", event.getJDA().getSelfUser().getAvatarUrl());
        embedBuilder.setThumbnail(event.getJDA().getSelfUser().getAvatarUrl());

        embedBuilder.addField("Name", event.getJDA().getSelfUser().getName(), true);
        embedBuilder.addField("ID", event.getJDA().getSelfUser().getId(), true);
        embedBuilder.addField("Author", Reference.botAuthor, true);
        embedBuilder.addField("Version", "N/A", true);
        embedBuilder.addField("Library", "JDA - " + JDAInfo.VERSION, true);
        embedBuilder.addField("Current Game", event.getJDA().getPresence().getGame().getName(), true);
        embedBuilder.addField("Status", StringUtils.capitalize(event.getJDA().getPresence().getStatus().name().toLowerCase()), true);
        embedBuilder.addField("Created On", event.getJDA().getSelfUser().getCreationTime().toString().substring(0, 10), true);
        embedBuilder.addField("Command Key", Reference.botCommandKey, true);
        embedBuilder.addField("Custom Command Key", Reference.botCommandCustomKey, true);
        embedBuilder.addField("Commander Role", Reference.botCommanderRole, true);
        embedBuilder.addField("Auto Response Role", Reference.botAutoResponseRole, true);
        embedBuilder.addField("Verified?", event.getJDA().getSelfUser().isVerified() ? "Yes" : "No", true);

        embedBuilder.addField("", "Report any issues or suggestions to MiningMark48#6817.", false);

        embedBuilder.setFooter("Pearcel Bot: A Discord bot by MiningMark48.", event.getAuthor().getAvatarUrl());
        event.getTextChannel().sendMessage(embedBuilder.build()).queue();

    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {
        return;
    }
}
