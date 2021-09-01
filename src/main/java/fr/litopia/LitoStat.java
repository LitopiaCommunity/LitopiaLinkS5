package fr.litopia;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

public class LitoStat implements Serializable {

    private Player player = null;
    private HashMap<String, Integer> globalStatistics = new HashMap<String, Integer>();
    private HashMap<String, HashMap<String, Integer>> componentStatisics = new HashMap<String, HashMap<String, Integer>>();

    public LitoStat(Player _player)
    {
        player = _player;
        this.update();
    }

    public HashMap<String, Integer> get() { return globalStatistics; }
    public int getStat(Statistic _stat) { return globalStatistics.get(_stat.toString()); }

    
    public void update()
    {
        for (Statistic stat : Statistic.values())
        {
            if (!stat.isSubstatistic())
            {
                globalStatistics.put(stat.toString(), player.getStatistic(stat));
            }
            else
            {
                // Item & Block
                if (stat.equals(Statistic.DROP) || stat.equals(Statistic.PICKUP) || stat.equals(Statistic.USE_ITEM) || 
                stat.equals(Statistic.BREAK_ITEM) || stat.equals(Statistic.CRAFT_ITEM) || stat.equals(Statistic.MINE_BLOCK))
                {
                    componentStatisics.put(stat.toString(), new HashMap<String, Integer>());
                    for ( Material item  : Material.values())
                    {
                        if (item.isBlock() || !stat.equals(Statistic.MINE_BLOCK))
                        {
                            try {
                                componentStatisics.get(stat.toString()).put(item.toString(), player.getStatistic(stat, item));
                            }
                            catch (IllegalArgumentException _error)
                            {
                                System.out.println(_error.toString());
                            }
                        }
                    }
                }

               // Entity
                if (stat.equals(Statistic.KILL_ENTITY) || stat.equals(Statistic.ENTITY_KILLED_BY))
                {
                    componentStatisics.put(stat.toString(), new HashMap<String, Integer>());
                    for ( EntityType entity  : EntityType.values())
                    {
                        try {
                            componentStatisics.get(stat.toString()).put(entity.toString() + " : ", player.getStatistic(stat, entity));
                        }
                        catch (IllegalArgumentException _error)
                        {
                            System.out.println(_error.toString());
                        }
                    }
                }
            }
        }
    }

    public String toJSON()
    {
        String JSONcontent = "";

        JSONcontent += "{\n\t\"globalStatistics\": {\n";
        for (Map.Entry<String, Integer> stat : globalStatistics.entrySet())
        {
            JSONcontent += "\t\t\"" + stat.getKey() + "\": " + stat.getValue() + " ,\n";
        }
        JSONcontent = JSONcontent.substring(0, JSONcontent.length() - 2);
        JSONcontent += "\n\t},\n";

        JSONcontent += "\n\t\"componentStatistics\": [\n";
        for (Map.Entry<String, HashMap<String,Integer>> component : componentStatisics.entrySet()) {
            JSONcontent += "\t\t{\""+component.getKey()+"\": {\n";
            for (Map.Entry<String, Integer> stat : component.getValue().entrySet())
            {
                JSONcontent += "\t\t\t\" " + stat.getKey() + "\": " + stat.getValue() + " ,\n";
            }
            JSONcontent = JSONcontent.substring(0, JSONcontent.length() - 2);
            JSONcontent += "\n\t\t}\n\t},\n";
        }
        JSONcontent = JSONcontent.substring(0, JSONcontent.length() - 2);
        JSONcontent += "\n\t]\n}";

        return JSONcontent;
    }

    public void exportInNewFile() {
        File file = new File("./player_stats/" +player.getUniqueId().toString() + ".json");
        FileWriter writer = null;
        try {
            writer = new FileWriter(file);
            writer.write(this.toJSON());
            writer.close();
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[LitopiaLink] Export Finish !");
        } catch (IOException e) {
            Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[LitopiaLink] Can't Export !");
        }
    }
}
