package com.miningmark48.pearcelbot.commands.pbc;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonWriter;
import com.miningmark48.pearcelbot.ICommand;
import com.miningmark48.pearcelbot.reference.Reference;
import com.miningmark48.pearcelbot.util.JSON.JSONParseFile;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.*;

public class CommandAddCommand implements ICommand {

    public static final String desc = "Add a custom command for your server.";
    public static final String usage = "USAGE: " + Reference.botCommandKey + "addcommand <arg-command name> <arg-message>";
    public static final String info = desc + " " + usage;

    public static String fileName = "custom_commands.json";

    BufferedWriter bufferedWriter = null;

    @Override
    public boolean called(String[] args, MessageReceivedEvent event) {
        return true;
    }

    @Override
    public void action(String[] args, MessageReceivedEvent event) {

        if (event.getMember().getRoles().toString().contains(Reference.botCommanderRole) || event.getGuild().getOwner() == event.getMember() || event.getAuthor().getId().equals(Reference.botOwner)){
            String commandMessage = "";
            String command = "";

            if (args.length > 0){
                command = args[0];

                for (int i = 2; i <= args.length; i++){
                    commandMessage = commandMessage + args[i - 1] + " ";
                    commandMessage = commandMessage.substring(0, 1).toUpperCase() + commandMessage.substring(1);
                }

            }

            try{

                File file = new File(fileName);


                if(!file.exists()) {

                    Writer writer = new OutputStreamWriter(new FileOutputStream(fileName) , "UTF-8");
                    Gson gson = new GsonBuilder().setPrettyPrinting().create();
                    JsonWriter jw = gson.newJsonWriter(writer);

                    jw.beginObject();

                    jw.name(event.getGuild().getId());
                    jw.beginObject();

                    jw.name("_comment_serverName_").value(event.getGuild().getName());
                    jw.name(command).value(commandMessage);

                    jw.endObject();
                    jw.endObject();

                    writer.close();
                    event.getTextChannel().sendMessage("Successfully added the command **" + command + "** : " + commandMessage).queue();
                }else{
                    JsonObject jsonObj = JSONParseFile.JSONParse(fileName);

                    Writer writer = new OutputStreamWriter(new FileOutputStream(fileName) , "UTF-8");
                    bufferedWriter = new BufferedWriter(writer);

                    if(!jsonObj.has(event.getGuild().getId())) {
                        JsonObject toAdd = new JsonObject();
                        toAdd.addProperty("_comment_serverName_", event.getGuild().getName());
                        jsonObj.add(event.getGuild().getId(), toAdd);
                    }

                    JsonObject newJson = jsonObj.getAsJsonObject(event.getGuild().getId());

                    if(newJson.get(command) == null) {
                        newJson.addProperty(command, commandMessage);
                        event.getTextChannel().sendMessage("Successfully added the command **" + command + "** : " + commandMessage).queue();
                    }else{
                        event.getTextChannel().sendMessage("**Error:** ICommand already exists!").queue();
                    }

                    bufferedWriter.write(jsonObj.toString());

                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bufferedWriter != null){
                        bufferedWriter.close();
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        }else{
            event.getTextChannel().sendMessage(event.getAuthor().getName() + ", You do not have permission to run that command.").queue();
        }


    }

    @Override
    public void executed(boolean success, MessageReceivedEvent event) {

    }
}
