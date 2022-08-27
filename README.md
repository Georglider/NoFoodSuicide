# NoFoodSuicide
#### Makes your players stop dying to recover their food level
## Features
- Set minimal food level so players won't die by starvation all the time
- Set list of deaths when food level would be recovered
- Allow specific players to bypass food level being set to previous value
## Requirements
- Bukkit 1.13+ Minecraft Server
## Configuration
#### You only need to make changes in **config.yml** file, players.yml should not be touched
| Property Name             |    Default Value    | Description                                                              |
|---------------------------|:-------------------:|:-------------------------------------------------------------------------|
| min_food_level            |          6          | Smallest food level that could be set                                    |
| max_food_level            |         20          | Food level that is given by default on the server                        |
| save_left_players_on_stop |        false        | Save dead players that left server without respawning after server stop  |

And also there's a property: recover_cause, or you can invert it with not_recover_cause.
It is meant to set default level of food for the reasons you want player not to be annoyed by food recovery,
for example if they die by LIGHTNING. You need to specify list like this:<br>
\- LIGHTNING<br>
\- VOID<br>
<details>
<summary>You can see list of all reasons by clicking here</summary>

| Name                | Description                                                                                             |
|---------------------|---------------------------------------------------------------------------------------------------------|
| BLOCK_EXPLOSION     | Damage caused by being in the area when a block explodes                                                |
| CONTACT             | Damage caused when an entity contacts a block such as a Cactus, Dripstone (Stalagmite) or Berry Bush    |
| CRAMMING            | Damage caused when an entity is colliding with too many entities due to the maxEntityCramming game rule |
| CUSTOM              | Custom damage                                                                                           |
| DRAGON_BREATH       | Damage caused by a dragon breathing fire                                                                |
| DROWNING            | Damage caused by running out of air while in water                                                      |
| DRYOUT              | Damage caused when an entity that should be in water is not                                             |
| ENTITY_ATTACK       | Damage caused when an entity attacks another entity                                                     |
| ENTITY_EXPLOSION    | Damage caused by being in the area when an entity, such as a Creeper, explodes                          |
| ENTITY_SWEEP_ATTACK | Damage caused when an entity attacks another entity in a sweep attack                                   |
| FALL                | Damage caused when an entity falls a distance greater than 3 blocks                                     |
| FALLING_BLOCK       | Damage caused by being hit by a falling block which deals damage                                        |
| FIRE                | Damage caused by direct exposure to fire                                                                |
| FIRE_TICK           | Damage caused due to burns caused by fire                                                               |
| FLY_INTO_WALL       | Damage caused when an entity runs into a wall                                                           |
| FREEZE              | Damage caused from freezing                                                                             |
| HOT_FLOOR           | Damage caused when an entity steps on Material MAGMA_BLOCK                                              |
| LAVA                | Damage caused by direct exposure to lava                                                                |
| LIGHTNING           | Damage caused by being struck by lightning                                                              |
| MAGIC               | Damage caused by being hit by a damage potion or spell                                                  |
| MELTING             | Damage caused due to a snowman melting                                                                  |
| POISON              | Damage caused due to an ongoing poison effect                                                           |
| PROJECTILE          | Damage caused when attacked by a projectile                                                             |
| SONIC_BOOM          | Damage caused by the Sonic Boom attack from Warden                                                      |
| STARVATION          | Damage caused by starving due to having an empty hunger bar                                             |
| SUFFOCATION         | Damage caused by being put in a block                                                                   |
| SUICIDE             | Damage caused by committing suicide                                                                     |
| THORNS              | Damage caused in retaliation to another attack by the Thorns enchantment                                |
| VOID                | Damage caused by falling into the void                                                                  |
| WITHER              | Damage caused by Wither potion effect                                                                   |
</details>

## Permissions
**nofoodsuicide.bypass** - Allows player to bypass food level recovery to previous value; Default to OP