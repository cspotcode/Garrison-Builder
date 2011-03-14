/*
  argument0 - x-coordinate
  argument1 - y-coordinate
  argument2 - tool name
*/

{
  new_ent_x = argument0;
  new_ent_y = argument1;
  if(mc.enableGridSnap = true) {
    new_ent_x = round(round(new_ent_x / 6) * 6);
    new_ent_y = round(round(new_ent_y / 6) * 6);
  }
  if (mc.currentTool != "moveTool")
  {
  with(LevelEntity) {
    if(x = other.new_ent_x && y = other.new_ent_y) instance_destroy();
  }
  }
  new_ent = instance_create(new_ent_x, new_ent_y, LevelEntity);
  new_ent.type = argument2;
  switch(argument2) {
    case "redspawn":
      new_ent.sprite_index = redspawn_ent;
    break;
    case "bluespawn":
      new_ent.sprite_index = bluespawn_ent;
    break;
    case "redintel":
      new_ent.sprite_index = redintel_ent;
    break;
    case "blueintel":
      new_ent.sprite_index = blueintel_ent;
    break;
    case "redteamgate":
      new_ent.sprite_index = redteamgate_ent;
    break;
    case "redteamgate2":
      new_ent.sprite_index = redteamgate2_ent;
    break;
    case "blueteamgate":
      new_ent.sprite_index = blueteamgate_ent;
    break;
    case "blueteamgate2":
      new_ent.sprite_index = blueteamgate2_ent;
    break;
    case "redintelgate":
      new_ent.sprite_index = redintelgate_ent;
    break;
    case "redintelgate2":
      new_ent.sprite_index = redintelgate2_ent;
    break;
    case "blueintelgate":
      new_ent.sprite_index = blueintelgate_ent;
    break;
    case "blueintelgate2":
      new_ent.sprite_index = blueintelgate2_ent;
    break;
    case "intelgatehorizontal":
      new_ent.sprite_index = intelgatehorizontal_ent;
    break;
    case "intelgatevertical":
      new_ent.sprite_index = intelgatevertical_ent;
    break;
    case "killbox":
      new_ent.sprite_index = killbox_ent;
    break;
    case "spawnroom":
      new_ent.sprite_index = spawnroom_ent;
    break;
    case "medCabinet":
      new_ent.sprite_index = medCabinet_ent;
    break;
    case "map_end_trigger_red":
      new_ent.sprite_index = map_end_trigger_red_ent;
    break;    
    case "map_end_trigger_blu":
      new_ent.sprite_index = map_end_trigger_blu_ent;
    break;    
    case "map_end_trigger_red_back":
      new_ent.sprite_index = map_end_trigger_red_back_ent;
    break;    
    case "map_end_trigger_blu_back":
      new_ent.sprite_index = map_end_trigger_blu_back_ent;
    break; 
    case "fragbox":
      new_ent.sprite_index = fragbox_ent;
    break;       
    case "playerwall":
      new_ent.sprite_index = playerwall_ent;
    break;   
    case "playerwall_horizontal":
      new_ent.sprite_index = playerwall_horizontal_ent;
    break;           
    case "bulletwall":
      new_ent.sprite_index = bulletwall_ent;
    break;   
    case "bulletwall_horizontal":
      new_ent.sprite_index = bulletwall_horizontal_ent;
    break;     
    case "teleport_1_point_1":
      new_ent.sprite_index = teleport_1_point_1_ent;
    break;   
    case "teleport_1_point_2":
      new_ent.sprite_index = teleport_1_point_2_ent;
    break;   
    case "teleport_2_point_1":
      new_ent.sprite_index = teleport_2_point_1_ent;
    break;   
    case "teleport_2_point_2":
      new_ent.sprite_index = teleport_2_point_2_ent;
    break; 
    case "teleport_3_point_1":
      new_ent.sprite_index = teleport_3_point_1_ent;
    break;   
    case "teleport_3_point_2":
      new_ent.sprite_index = teleport_3_point_2_ent;
    break; 
    case "teleport_4_point_1":
      new_ent.sprite_index = teleport_4_point_1_ent;
    break;   
    case "teleport_4_point_2":
      new_ent.sprite_index = teleport_4_point_2_ent;
    break; 
    case "teleport_5_point_1":
      new_ent.sprite_index = teleport_5_point_1_ent;
    break;   
    case "teleport_5_point_2":
      new_ent.sprite_index = teleport_5_point_2_ent;
    break; 
    case "teleport_6_point_1":
      new_ent.sprite_index = teleport_6_point_1_ent;
    break;   
    case "teleport_6_point_2":
      new_ent.sprite_index = teleport_6_point_2_ent;
    break; 
    case "teleport_7_point_1":
      new_ent.sprite_index = teleport_7_point_1_ent;
    break;   
    case "teleport_7_point_2":
      new_ent.sprite_index = teleport_7_point_2_ent;
    break; 
    case "teleport_8_point_1":
      new_ent.sprite_index = teleport_8_point_1_ent;
    break;   
    case "teleport_8_point_2":
      new_ent.sprite_index = teleport_8_point_2_ent;
    break; 
    case "teleport_9_point_1":
      new_ent.sprite_index = teleport_9_point_1_ent;
    break;   
    case "teleport_9_point_2":
      new_ent.sprite_index = teleport_9_point_2_ent;
    break; 
    case "teleport_10_point_1":
      new_ent.sprite_index = teleport_10_point_1_ent;
    break;   
    case "teleport_10_point_2":
      new_ent.sprite_index = teleport_10_point_2_ent;
    break;                     
    case "gameMode_ctf":
      new_ent.sprite_index = gameMode_ctf_ent;
    break;        
    case "gameMode_vip":
      new_ent.sprite_index = gameMode_vip_ent;
    break;                         
    case "vip_start":
      new_ent.sprite_index = vip_start_ent;
    break;    
    case "vip_end":
      new_ent.sprite_index = vip_end_ent;
    break;         
    case "NeutralIntel":
      new_ent.sprite_index = NeutralIntel_ent;
    break;      
    case "IntelSpawn":
      new_ent.sprite_index = IntelSpawn_ent;
    break;    
    case "BlueIntelSpawn":
      new_ent.sprite_index = BlueIntelSpawn_ent;
    break;     
    case "RedIntelSpawn":
      new_ent.sprite_index = RedIntelSpawn_ent;
    break;
    case "controlPoint1":
      new_ent.sprite_index = controlPoint1_ent;
    break;   
    case "controlPoint2":
      new_ent.sprite_index = controlPoint2_ent;
    break;
    case "controlPoint3":
      new_ent.sprite_index = controlPoint3_ent;
    break;
    case "controlPoint4":
      new_ent.sprite_index = controlPoint4_ent;
    break;
    case "controlPoint5":
      new_ent.sprite_index = controlPoint5_ent;
    break;
    case "NextAreaO":
      new_ent.sprite_index = NextAreaO_ent;
    break;
    case "PreviousAreaO":
      new_ent.sprite_index = PreviousAreaO_ent;
    break;
    case "CapturePoint":
      new_ent.sprite_index = CapturePoint_ent;
    break;
    case "SetupGate":
      new_ent.sprite_index = SetupGate_ent;
    break;      
    case "timer":
      new_ent.sprite_index = timer_ent;
    break;    
    default:
      show_message("Error: system tried to create entity for which there is no sprite.");
      new_ent.sprite_index = trans;
//      instance_destroy()'
    break;    
  }
}
