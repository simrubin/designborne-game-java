package game;


import edu.monash.fit2099.engine.displays.Display;
import edu.monash.fit2099.engine.positions.*;
import game.entity.Blacksmith;
import game.entity.Traveller;
import game.entity.enemy.Abxervyer;
import game.entity.Player;
import game.grounds.*;
import game.grounds.Void;
import game.items.Bloodberry;
import game.items.HealingVial;
import game.items.OldKey;
import game.items.RefreshingFlask;
import game.rune.DroppedRuneManager;
import game.rune.Rune;
import game.spawner.EldentreeGuardianSpawner;
import game.spawner.ForestKeeperSpawner;
import game.spawner.HollowSoldierSpawner;
import game.spawner.LivingBranchSpawner;
import game.spawner.RedWolfSpawner;
import game.spawner.WanderingUndeadSpawner;
import game.utils.FancyMessage;
import game.utils.Maps;
import game.weapon.Broadsword;
import game.weather.WeatherControl;
import game.weapon.GiantHammer;
import game.weapon.GreatKnife;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * The main class to start the game.
 * Created by:
 * @author Adrian Kristanto
 * Modified by:
 *
 */
public class Application {

    private static final GroundFactory groundFactory = new FancyGroundFactory(new Dirt(),
        new Wall(), new Floor(), new Puddle(), new Void());

    /**
     * Apply emoji displays to all ground tiles of a specific type in a game map
     * 
     * @param map The GameMap to modify
     * @param groundClass The class of ground to look for
     * @param emoji The emoji string to set as display
     */
    private static void applyEmojiToGroundType(GameMap map, Class<? extends Ground> groundClass, String emoji) {
        for (int x : map.getXRange()) {
            for (int y : map.getYRange()) {
                Ground ground = map.at(x, y).getGround();
                if (groundClass.isInstance(ground)) {
                    ground.setDisplayString(emoji);
                }
            }
        }
    }

    public static void main(String[] args) {

        World world = new World(new Display());

        // Create map
        GameMap AbandonedVillageMap = new GameMap(groundFactory, Maps.ABANDONED_VILLAGE);
        GameMap BurialGroundMap = new GameMap(groundFactory, Maps.BURIAL_GROUND);
        GameMap AncientWoodMap = new GameMap(groundFactory, Maps.ANCIENT_WOOD);
        GameMap AbxervyerBattleMap = new GameMap(groundFactory, Maps.ABXERYYER_BATTLE);
        GameMap OvergrownSanctuaryMap = new GameMap(groundFactory, Maps.OVERGROWN_SANCTUARY);

        // Add maps to world
        world.addGameMap(AbandonedVillageMap);
        world.addGameMap(BurialGroundMap);
        world.addGameMap(AncientWoodMap);
        world.addGameMap(AbxervyerBattleMap);
        world.addGameMap(OvergrownSanctuaryMap);

        // Apply emojis to basic terrain types for all maps
        GameMap[] allMaps = {AbandonedVillageMap, BurialGroundMap, AncientWoodMap, AbxervyerBattleMap, OvergrownSanctuaryMap};
        for (GameMap map : allMaps) {
            applyEmojiToGroundType(map, Dirt.class, "🌱");
            applyEmojiToGroundType(map, Wall.class, "🧱");
            applyEmojiToGroundType(map, Floor.class, "⬜");
            applyEmojiToGroundType(map, Puddle.class, "💧");
            applyEmojiToGroundType(map, Void.class, "⚫");
        }

        for (String line : FancyMessage.TITLE.split("\n")) {
            new Display().println(line);
            try {
                Thread.sleep(200);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }

        Location playerSpawnLocation = AbandonedVillageMap.at(29,5);
        Player player = new Player("The Abstracted One", '@', 150,200,playerSpawnLocation);
        player.setDisplayString("🧙");
        world.addPlayer(player, playerSpawnLocation);

        // Create emoji ground objects by setting custom display strings
        Gate burialGroundGate = new Gate(BurialGroundMap, Maps.burialGround,19,9);
        burialGroundGate.setDisplayString("🚪");
        AbandonedVillageMap.at(28,9).setGround(burialGroundGate);
        
        Graveyard wanderingUndeadGraveyard = new Graveyard(new WanderingUndeadSpawner());
        wanderingUndeadGraveyard.setDisplayString("⚰️");
        AbandonedVillageMap.at(30,10).setGround(wanderingUndeadGraveyard);
        
        Graveyard otherGraveyard = new Graveyard(new WanderingUndeadSpawner());
        otherGraveyard.setDisplayString("⚰️");
        AbandonedVillageMap.at(42,3).setGround(otherGraveyard);
        
        Blacksmith blacksmith = new Blacksmith();
        blacksmith.setDisplayString("👨");
        AbandonedVillageMap.at(27,5).addActor(blacksmith);

        // Burial Ground Map features with emojis
        Graveyard hollowSoldierGraveyard = new Graveyard(new HollowSoldierSpawner());
        hollowSoldierGraveyard.setDisplayString("⚰️");
        BurialGroundMap.at(25,10).setGround(hollowSoldierGraveyard);
        
        Gate backToVillageGate = new Gate(AbandonedVillageMap, Maps.abandonedVillage, 28, 9);
        backToVillageGate.setDisplayString("🚪");
        BurialGroundMap.at(19,9).setGround(backToVillageGate);
        
        Gate toAncientWoodGate = new Gate(AncientWoodMap, Maps.ancientWoods, 19, 7);
        toAncientWoodGate.setDisplayString("🚪");
        BurialGroundMap.at(22,10).setGround(toAncientWoodGate);

        // Ancient Wood Map features with emojis
        Gate toBurialGroundGate = new Gate(BurialGroundMap, Maps.burialGround, 22, 10);
        toBurialGroundGate.setDisplayString("🚪");
        AncientWoodMap.at(19,7).setGround(toBurialGroundGate);
        
        EmptyHut forestKeeperHut = new EmptyHut(new ForestKeeperSpawner());
        forestKeeperHut.setDisplayString("🏠");
        AncientWoodMap.at(10,8).setGround(forestKeeperHut);
        
        Bush redWolfBush = new Bush(new RedWolfSpawner());
        redWolfBush.setDisplayString("🌳");
        AncientWoodMap.at(8,5).setGround(redWolfBush);
        
        Traveller traveller = new Traveller();
        traveller.setDisplayString("🧍");
        AncientWoodMap.at(20,3).addActor(traveller);
        
        Gate toBattleMapGate = new Gate(AbxervyerBattleMap, Maps.abxeryyerBattle, 22, 10);
        toBattleMapGate.setDisplayString("🚪");
        AncientWoodMap.at(17,11).setGround(toBattleMapGate);

        // Abxervyer Battle Map features with emojis
        EmptyHut forestKeeperHut1 = new EmptyHut(new ForestKeeperSpawner());
        forestKeeperHut1.setDisplayString("🏠");
        AbxervyerBattleMap.at(17,11).setGround(forestKeeperHut1);
        
        EmptyHut forestKeeperHut2 = new EmptyHut(new ForestKeeperSpawner());
        forestKeeperHut2.setDisplayString("🏠");
        AbxervyerBattleMap.at(10,14).setGround(forestKeeperHut2);
        
        EmptyHut forestKeeperHut3 = new EmptyHut(new ForestKeeperSpawner());
        forestKeeperHut3.setDisplayString("🏠");
        AbxervyerBattleMap.at(7,2).setGround(forestKeeperHut3);
        
        EmptyHut forestKeeperHut4 = new EmptyHut(new ForestKeeperSpawner());
        forestKeeperHut4.setDisplayString("🏠");
        AbxervyerBattleMap.at(13,5).setGround(forestKeeperHut4);
        
        Bush redWolfBush1 = new Bush(new RedWolfSpawner());
        redWolfBush1.setDisplayString("🌳");
        AbxervyerBattleMap.at(5,6).setGround(redWolfBush1);
        
        Bush redWolfBush2 = new Bush(new RedWolfSpawner());
        redWolfBush2.setDisplayString("🌳");
        AbxervyerBattleMap.at(10,10).setGround(redWolfBush2);
        
        Bush redWolfBush3 = new Bush(new RedWolfSpawner());
        redWolfBush3.setDisplayString("🌳");
        AbxervyerBattleMap.at(15,1).setGround(redWolfBush3);
        
        Abxervyer abxervyer = new Abxervyer(new WeatherControl(), new ArrayList<>(Arrays.asList(AncientWoodMap, OvergrownSanctuaryMap)));
        abxervyer.setDisplayString("👹");
        AbxervyerBattleMap.at(19,8).addActor(abxervyer);

        // Overgrown Sanctuary Map features with emojis
        Gate toBattleMapGate2 = new Gate(AbxervyerBattleMap, Maps.abxeryyerBattle, 22, 10);
        toBattleMapGate2.setDisplayString("🚪");
        OvergrownSanctuaryMap.at(10,8).setGround(toBattleMapGate2);
        
        Graveyard hollowSoldierGraveyard1 = new Graveyard(new HollowSoldierSpawner());
        hollowSoldierGraveyard1.setDisplayString("⚰️");
        OvergrownSanctuaryMap.at(17,11).setGround(hollowSoldierGraveyard1);
        
        Graveyard hollowSoldierGraveyard2 = new Graveyard(new HollowSoldierSpawner());
        hollowSoldierGraveyard2.setDisplayString("⚰️");
        OvergrownSanctuaryMap.at(10,14).setGround(hollowSoldierGraveyard2);
        
        EmptyHut eldentreeGuardianHut1 = new EmptyHut(new EldentreeGuardianSpawner());
        eldentreeGuardianHut1.setDisplayString("🏠");
        OvergrownSanctuaryMap.at(7,2).setGround(eldentreeGuardianHut1);
        
        EmptyHut eldentreeGuardianHut2 = new EmptyHut(new EldentreeGuardianSpawner());
        eldentreeGuardianHut2.setDisplayString("🏠");
        OvergrownSanctuaryMap.at(13,5).setGround(eldentreeGuardianHut2);
        
        Bush livingBranchBush1 = new Bush(new LivingBranchSpawner());
        livingBranchBush1.setDisplayString("🌿");
        OvergrownSanctuaryMap.at(5,6).setGround(livingBranchBush1);
        
        Bush livingBranchBush2 = new Bush(new LivingBranchSpawner());
        livingBranchBush2.setDisplayString("🌿");
        OvergrownSanctuaryMap.at(10,10).setGround(livingBranchBush2);
        
        Bush livingBranchBush3 = new Bush(new LivingBranchSpawner());
        livingBranchBush3.setDisplayString("🌿");
        OvergrownSanctuaryMap.at(15,1).setGround(livingBranchBush3);

        // For testing purpose
//        Bloodberry bloodberry = new Bloodberry();
//        bloodberry.setDisplayString("🍒");
//        player.addItemToInventory(bloodberry);
//
//        HealingVial healingVial = new HealingVial();
//        healingVial.setDisplayString("🧪");
//        player.addItemToInventory(healingVial);
//
//        GiantHammer giantHammer = new GiantHammer();
//        giantHammer.setDisplayString("🔨");
//        player.addItemToInventory(giantHammer);
//
//        player.addBalance(10000);
//        gameMap.at(23, 10).addActor(new WanderingUndead());
//        gameMap.at(22,8).addActor(new HollowSoldier());

    //    OldKey oldKey = new OldKey();
    //    oldKey.setDisplayString("🗝️");
    //    gameMap.at(29,7).addItem(oldKey);
//
//        RefreshingFlask refreshingFlask = new RefreshingFlask();
//        refreshingFlask.setDisplayString("🧴");
//        gameMap.at(29,8).addItem(refreshingFlask);
//
//        HealingVial testHealingVial = new HealingVial();
//        testHealingVial.setDisplayString("🧪");
//        gameMap.at(29,9).addItem(testHealingVial);
//
//        Bloodberry testBloodberry = new Bloodberry();
//        testBloodberry.setDisplayString("🍒");
//        gameMap.at(26, 8).addItem(testBloodberry);

        // Test case for upgrade (healing vial)
//        player.hurt(149);
//        HealingVial vial1 = new HealingVial();
//        vial1.setDisplayString("🧪");
//        player.addItemToInventory(vial1);
//
//        HealingVial vial2 = new HealingVial();
//        vial2.setDisplayString("🧪");
//        player.addItemToInventory(vial2);
//
//        player.addBalance(1000);

        // Testing money despawn
//        Rune rune = new Rune(696);
//        rune.setDisplayString("💰");
//        AbandonedVillageMap.at(30,6).addItem(rune);
//        DroppedRuneManager.getInstance().register(rune,AbandonedVillageMap.at(30,6));
//
//        Rune inventoryRune = new Rune(9000);
//        inventoryRune.setDisplayString("💰");
//        player.addItemToInventory(inventoryRune);

        // Test case for selling stuff
//        world.addPlayer(player, AncientWoodMap.at(19, 3));
//
//        HealingVial sellVial = new HealingVial();
//        sellVial.setDisplayString("🧪");
//        player.addItemToInventory(sellVial);
//
//        Broadsword broadsword = new Broadsword();
//        broadsword.setDisplayString("⚔️");
//        player.addItemToInventory(broadsword);
//
//        RefreshingFlask sellFlask = new RefreshingFlask();
//        sellFlask.setDisplayString("🧴");
//        player.addItemToInventory(sellFlask);
//
//        Bloodberry sellBerry = new Bloodberry();
//        sellBerry.setDisplayString("🍒");
//        player.addItemToInventory(sellBerry);
//
//        Rune sellRune1 = new Rune(6969);
//        sellRune1.setDisplayString("💰");
//        player.addItemToInventory(sellRune1);
//
//        Rune sellRune2 = new Rune(12);
//        sellRune2.setDisplayString("💰");
//        player.addItemToInventory(sellRune2);

//        Broadsword testBroadsword = new Broadsword();
//        testBroadsword.setDisplayString("⚔️");
//        player.addItemToInventory(testBroadsword);
//        player.addBalance(1000);

        // Test for req 4
//        world.addPlayer(player,AbxervyerBattleMap.at(18, 9));
//        GreatKnife greatKnife = new GreatKnife();
//        greatKnife.setDisplayString("🔪");
//        player.addItemToInventory(greatKnife);
//
//        OldKey testKey = new OldKey();
//        testKey.setDisplayString("🗝️");
//        player.addItemToInventory(testKey);
//
//        GiantHammer testHammer = new GiantHammer();
//        testHammer.setDisplayString("🔨");
//        player.addItemToInventory(testHammer);

        // Abxervyer monologue testing
//        Abxervyer testAbxervyer = new Abxervyer(new WeatherControl(), new ArrayList<>(Arrays.asList(AncientWoodMap,OvergrownSanctuaryMap)));
//        testAbxervyer.setDisplayString("👹");
//        AbandonedVillageMap.at(30,5).addActor(testAbxervyer);
//
//        GreatKnife testKnife = new GreatKnife();
//        testKnife.setDisplayString("🔪");
//        player.addItemToInventory(testKnife);


        world.run();
    }
}
