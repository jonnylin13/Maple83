package constants;

import java.io.FileInputStream;
import java.util.Properties;

public class ServerConstants {
    //Database Configuration ACTUALLY SET IN CONFIG
    public static String DB_URL = "";
    public static String DB_USER = "";
    public static String DB_PASS = "";
    public static final boolean DB_EXPERIMENTAL_POOL = true;   //[EXPERIMENTAL] Installs a connection pool to hub DB connections. Set false to default.
	
    //World And Version
    public static short VERSION = 83;
    public static String[] WORLD_NAMES = {"Scania", "Bera", "Broa", "Windia", "Khaini", "Bellocan", "Mardia", "Kradia", "Yellonde", "Demethos", "Galicia", "El Nido", "Zenith", "Arcenia", "Kastia", "Judis", "Plana", "Kalluna", "Stius", "Croa", "Medere"};

    //Login Configuration
    public static final int CHANNEL_LOAD = 100;                 //Max players per channel.
    
    public static final long PURGING_INTERVAL = 5 * 60 * 1000;
    public static final long RANKING_INTERVAL = 60 * 60 * 1000;	//60 minutes, 3600000.
    public static final long COUPON_INTERVAL = 60 * 60 * 1000;	//60 minutes, 3600000.
    public static final boolean ENABLE_PIC = true;             //Pick true/false to enable or disable Pic. Delete character needs this feature ENABLED.
    public static final boolean ENABLE_PIN = false;             //Pick true/false to enable or disable Pin.
		
    //Ip Configuration
    public static String HOST;
	
    //Other Configuration
    public static boolean JAVA_8 = false;
    public static boolean SHUTDOWNHOOK;
	
    //Server Flags
    public static final boolean USE_CUSTOM_KEYSET = false;           //Enables auto-setup of the Maple83's custom keybindings when creating characters.
    public static final boolean USE_MAXRANGE_ECHO_OF_HERO = true;
    public static final boolean USE_MAXRANGE = true;                //Will send and receive packets from all events on a map, rather than those of only view range.
    public static final boolean USE_DEBUG = true;                  //Will enable some text prints on the client, oriented for debugging purposes.
    public static final boolean USE_DEBUG_SHOW_RCVD_PACKET = true; //Prints on the cmd all received packet ids.
    public static final boolean USE_DEBUG_SHOW_INFO_EQPEXP = false; //Prints on the cmd all equip exp gain info.
    public static final boolean USE_MTS = false;
    public static final boolean USE_FAMILY_SYSTEM = false;
    public static final boolean USE_DUEY = true;
    public static final boolean USE_RANDOMIZE_HPMP_GAIN = true;     //Enables randomizing on MaxHP/MaxMP gains and INT accounting for the MaxMP gain.
    public static final boolean USE_STORAGE_ITEM_SORT = true;       //Enables storage "Arrange Items" feature.
    public static final boolean USE_ITEM_SORT = true;               //Enables inventory "Item Sort/Merge" feature.
    public static final boolean USE_ITEM_SORT_BY_NAME = false;      //Item sorting based on name rather than id.
    public static final boolean USE_PARTY_SEARCH = false;
    public static final boolean USE_PARTY_FOR_STARTERS = true;      //Players level 10 or below can create/invite other players on the given level range.
    public static final boolean USE_AUTOBAN = true;                //Commands the server to detect infractors automatically.
    public static final boolean USE_AUTOSAVE = true;                //Enables server autosaving feature (saves characters to DB each 1 hour).
    public static final boolean USE_SERVER_AUTOASSIGNER = true;     //Server-builtin autoassigner, uses algorithm based on distributing AP accordingly with required secondary stat on equipments.
    public static final boolean USE_REFRESH_RANK_MOVE = true;
    public static final boolean USE_ENFORCE_OWL_SUGGESTIONS = false;//Forces the Owl of Minerva to always display the defined item array on GameConstants.OWL_DATA instead of those featured by the players.
    public static final boolean USE_ENFORCE_UNMERCHABLE_PET = true; //Forces players to not sell pets via merchants. (since non-named pets gets dirty name and other possible DB-related issues)
    public static final boolean USE_ENFORCE_MDOOR_POSITION = true;  //Forces mystic door to be spawned near spawnpoints. (since things bugs out other way, and this helps players to locate the door faster)
    public static final boolean USE_ERASE_PERMIT_ON_OPENSHOP = false;//Forces "shop permit" item to be consumed when player deploy his/her player shop.
    public static final boolean USE_ERASE_UNTRADEABLE_DROP = true;  //Forces flagged untradeable items to disappear when dropped.
    public static final boolean USE_ERASE_PET_ON_EXPIRATION = false;//Forces pets to be removed from inventory when expire time comes, rather than converting it to a doll.
    public static final boolean USE_BUFF_MOST_SIGNIFICANT = true;   //When applying buffs, the player will stick with the highest stat boost among the listed, rather than overwriting stats.
    public static final boolean USE_UNDERLEVELED_EXP_BLOCK = true;  //Players N levels below the killed mob will gain no experience from defeating it.
    
    //Server Rates And Experience
    public static final int EXP_RATE = 4;
    public static final int MESO_RATE = 3;
    public static final int DROP_RATE = 2;
    public static final int BOSS_DROP_RATE = 4;
    public static final double EQUIP_EXP_RATE = 0.5;               //Rate for equipment exp gain, grows linearly. Set 1.0 for default (about 100~200 same-level range mobs killed to pass equip from level 1 to 2).
    
    public static final double PARTY_BONUS_EXP_RATE = 1.2;          //Rate for the party exp reward.
    public static final double PQ_BONUS_EXP_RATE = 1.0;             //Rate for the PQ exp reward.
    
    public static int PARTY_EXPERIENCE_MOD = 1;               //Change for event stuff.
    
    //Miscellaneous Configuration
    public static final byte MIN_UNDERLEVEL_TO_EXP_GAIN = 5;        //Characters are unable to get EXP from a mob if their level are under this threshold, only if "USE_UNDERLEVELED_EXP_BLOCK" is enabled.
    public static final byte MAX_MONITORED_BUFFSTATS = 5;           //Limits accounting for "dormant" buff effects, that should take place when stronger stat buffs expires.
    public static final int MAX_AP = 32767;                             //Max AP allotted on the auto-assigner.
    public static final int MAX_EVENT_LEVELS = 8;                       //Event has different levels of rewarding system.
    public static final long BLOCK_NPC_RACE_CONDT = (long)(0.5 * 1000); //Time the player client must wait before reopening a conversation with an NPC.
    public static final long PET_LOOT_UPON_ATTACK = (long)(0.7 * 1000); //Time the pet must wait before trying to pick items up.
    
    //Dangling Items Configuration
    public static final int ITEM_EXPIRE_TIME  = 3 * 60 * 1000;  //Time before items start disappearing. Recommended to be set up to 3 minutes.
    public static final int ITEM_MONITOR_TIME = 5 * 60 * 1000;  //Interval between item monitoring tasks on maps, which checks for dangling item objects on the map item history.
    public static final int ITEM_LIMIT_ON_MAP = 200;            //Max number of items allowed on a map.
    
    //Some Gameplay Enhancing Configurations
    //Scroll Configuration
    public static final boolean USE_PERFECT_GM_SCROLL = true;   //Scrolls from GMs never uses up slots nor fails.
    public static final boolean USE_PERFECT_SCROLLING = false;   //Scrolls doesn't use slots upon failure.
    public static final boolean USE_ENHANCED_CHSCROLL = true;   //Equips even more powerful with chaos upgrade.
    public static final boolean USE_ENHANCED_CRAFTING = false;   //Applys chaos scroll on every equip crafted.
    public static final int SCROLL_CHANCE_RATE = 0;             //Number of rolls for success on a scroll, set 0 for default.
    public static final int CHSCROLL_STAT_RANGE = 6;            //Stat upgrade range (-N, N) on chaos scrolls.
    
    //Beginner Skills Configuration
    public static final boolean USE_ULTRA_NIMBLE_FEET = true;   //Haste-like speed & jump upgrade.
    public static final boolean USE_ULTRA_RECOVERY = true;      //Massive recovery amounts overtime.
    public static final boolean USE_ULTRA_THREE_SNAILS = true;  //Massive damage on shell toss.
    public static final boolean USE_UNLIMITED_THREE_SNAILS = true; // Doesn't require shells.
    
    //Character Configuration
    public static final boolean USE_ADD_SLOTS_BY_LEVEL = true;  //Slots are added each 20 levels.
    public static final boolean USE_ADD_RATES_BY_LEVEL = true;  //Rates are added each 20 levels.
    public static final boolean USE_STACK_COUPON_RATES = true;  //Multiple coupons effects builds up together.
    public static final boolean USE_PERFECT_PITCH = false;	//For lvl 30 or above, each lvlup grants player 1 perfect pitch.
    public static final int FAME_GAIN_BY_QUEST = 2;             //Fame gain each N quest completes, set 0 to disable.
    
    //Equipment Configuration
    public static final boolean USE_EQUIPMNT_LVLUP_SLOTS = false;//Equips can upgrade slots at level up.
    public static final boolean USE_EQUIPMNT_LVLUP_POWER = true;//Enable more powerful stat upgrades at equip level up.
    public static final boolean USE_SPIKES_AVOID_BANISH = true; //Shoes equipped with spikes prevents mobs from banishing wearer.
    public static final boolean USE_CHAIR_EXTRAHEAL = true;     //Enable map chairs to further recover player's HP and MP (player must have the Chair Mastery skill).
    public static final int MAX_EQUIPMNT_LVLUP_STAT_UP = 10000; //Max stat upgrade an equipment can have on a levelup.
    public static final int MAX_EQUIPMNT_STAT = 32767;          //Max stat on an equipment by leveling up.
    public static final int USE_EQUIPMNT_LVLUP = 10;             //All equips lvlup at max level of N, set 1 to disable.
    public static final byte CHAIR_EXTRA_HEAL_HP = 70;          //Each chair extra heal proc increasing HP.
    public static final byte CHAIR_EXTRA_HEAL_MP = 42;          //Each chair extra heal proc increasing MP.
    
    //Pet Auto-Pot Recovery Rates
    public static final double PET_AUTOHP_RATIO = 0.99;         //Will automatically consume potions until given ratio of the MaxHP/MaxMP is reached.
    public static final double PET_AUTOMP_RATIO = 0.99;
    
    //Pet & Mount Configuration
    public static final byte PET_EXHAUST_COUNT = 3;             //Number of proc counts (1 per minute) on the exhaust schedule for fullness.
    public static final byte MOUNT_EXHAUST_COUNT = 1;           //Number of proc counts (1 per minute) on the exhaust schedule for tiredness.
    
    //Pet Hunger Configuration
    public static final boolean PETS_NEVER_HUNGRY = false;      //If true, pets and mounts will never grow hungry.
    public static final boolean GM_PETS_NEVER_HUNGRY = true;    //If true, pets and mounts owned by GMs will never grow hungry.
    
    //Event Configuration
    public static final int EVENT_MAX_GUILD_QUEUE = 10;         //Max number of guilds in queue for GPQ.
    public static final long EVENT_LOBBY_DELAY = 10;            //Cooldown duration in seconds before reopening an event lobby.
    
    //Dojo Configuration
    public static final boolean USE_DEADLY_DOJO = false;        //Should bosses really use 1HP,1MP attacks in dojo?
    public static final int DOJO_ENERGY_ATK = 100;              //Dojo energy gain when deal attack
    public static final int DOJO_ENERGY_DMG =  20;              //Dojo energy gain when recv attack
	
    //Event End Timestamp
    public static final long EVENT_END_TIMESTAMP = 1428897600000L;
	
    //Properties
    static {
        Properties p = new Properties();
        try {
            p.load(new FileInputStream("configuration.ini"));

            //Server Host
            ServerConstants.HOST = p.getProperty("HOST");

            //Sql Database
            ServerConstants.DB_URL = p.getProperty("URL");
            ServerConstants.DB_USER = p.getProperty("DB_USER");
            ServerConstants.DB_PASS = p.getProperty("DB_PASS");

            //java8 And Shutdownhook
            ServerConstants.JAVA_8 = p.getProperty("JAVA8").equalsIgnoreCase("TRUE");
            ServerConstants.SHUTDOWNHOOK = p.getProperty("SHUTDOWNHOOK").equalsIgnoreCase("true");

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load configuration.ini.");
            System.exit(0);
        }
    }
}
