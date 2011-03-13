{
// global defines

global.BG = "";
global.WM = "";

global.loadedBG = false;
global.loadedWM = false;
global.loadedEnt = false;
global.currentCap = "Garrison Builder - Gang Garrison 2";
room_caption = global.currentCap;
global.defaults = true;

global.wkmskName = "";
global.bgName = "";

global.caughtError = 0;

global.combo = 0;
global.madeChange = false;

global.erasedOnce = true;
global.erasedOnce2 = true;

global.notOnTools = true;

global.CustomMapCollisionSprite = -1;
global.mapURL = "";
global.loadedC = false;

global.sixtyFourBitV = false;

global.quickSave = true;

draw_set_font(gg2Fnt);

/*
    message_background(popupBackgroundB);
    message_button(popupButtonS);
    message_text_font(gg2Fnt,9,c_white,1);
    message_button_font(gg2Fnt,9,c_white,1);
    message_input_font(gg2Fnt,9,c_white,0);
*/

global.tool = "";

if file_exists("game_errors.log") file_delete("game_errors.log");

ini_open("gb.ini");
global.paintApp = ini_read_string("Settings", "Paint Application", "MSPaint");  
global.autoRefresh = ini_read_real("Settings", "Refresh BG/WM after edit", 1);
global.gg2Dir = ini_read_string("Settings", "Gang Garrison 2.exe Location", "");  
global.specialE = ini_read_real("Settings", "Graphics Effects", 1);  
global.width = ini_read_real("Settings", "Toolbar Width", 5);  
global.sixtyFourBitV = ini_read_real("Settings", "Windows Vista x64", false);  
global.quickSave = ini_read_real("Settings", "Quicksave Maps", 1);  

ini_write_string("Settings", "Paint Application", global.paintApp);
ini_write_real("Settings", "Refresh BG/WM after edit", global.autoRefresh);
ini_write_string("Settings", "Gang Garrison 2.exe Location", global.gg2Dir);
ini_write_real("Settings", "Graphics Effects", global.specialE);
ini_write_real("Settings", "Toolbar Width", global.width);
ini_write_real("Settings", "Windows Vista x64", global.sixtyFourBitV);
ini_write_real("Settings", "Quicksave Maps", global.quickSave);
ini_close();

switch (global.paintApp) // let's determine what program the users wants to launch every time.
{
    case "MSPaint":
    if (global.sixtyFourBitV == 1)
    {
        global.paintAppdir = "C:\WINDOWS\SYSWOW64\mspaint.exe";
    }
    else
    {
        global.paintAppdir = "C:\WINDOWS\system32\mspaint.exe";
    }
    break;
    
    case "Paint":
    if (global.sixtyFourBitV == 1)
    {
        global.paintAppdir = "C:\WINDOWS\SYSWOW64\mspaint.exe";
    }
    else
    {
        global.paintAppdir = "C:\WINDOWS\system32\mspaint.exe";
    }
    break;
    
    case "mspaint":
    if (global.sixtyFourBitV == 1)
    {
        global.paintAppdir = "C:\WINDOWS\SYSWOW64\mspaint.exe";
    }
    else
    {
        global.paintAppdir = "C:\WINDOWS\system32\mspaint.exe";
    }
    break;
    
    case "paint":
    if (global.sixtyFourBitV == 1)
    {
        global.paintAppdir = "C:\WINDOWS\SYSWOW64\mspaint.exe";
    }
    else
    {
        global.paintAppdir = "C:\WINDOWS\system32\mspaint.exe";
    }
    break;
    
    case "GIMP": 
    global.paintAppdir = "C:\Progra~1\GIMP-2.0\bin\gimp-2.6.exe";
    break;
    
    case "gimp": 
    global.paintAppdir = "C:\Progra~1\GIMP-2.0\bin\gimp-2.6.exe";
    break;
    
    case "graphicsgale": 
    global.paintAppdir = "C:\Progra~1\GraphicsGale\Gale.exe";
    break;
    
    case "GraphicsGale": 
    global.paintAppdir = "C:\Progra~1\GraphicsGale\Gale.exe";
    break;
    
    case "paint.net": 
    global.paintAppdir = "C:\Progra~1\Paint.NET\PaintDotNet.exe";
    break;
    
    case "paint.NET": 
    global.paintAppdir = "C:\Progra~1\Paint.NET\PaintDotNet.exe";
    break;
    
    case "Paint.NET": 
    global.paintAppdir = "C:\Progra~1\Paint.NET\PaintDotNet.exe";
    break;
    
    default: // if we didn't know what the program was
    global.paintAppdir = "C:\WINDOWS\system32\mspaint.exe";
    break;
}

createButton(loadExisMap);
createButton(loadBG);
createButton(unloadBG);
createButton(loadWalkmask);
createButton(unloadWalkmask);
createButton(loadEntities);
createButton(unloadEntities);
createButton(saveEntities);
createButton(embedLevelDataInPNG);
createButton(editBG);
createButton(editWM);
createButton(testMap);
//createButton(customClear);
createToolButton(moveTool);

createToggleButton(bgVisible, true);
createToggleButton(walkmaskVisible, false);
createToggleButton(snapToGrid, true);
createToggleButton(gridVisible, false);

createToolButton(redspawn);
createToolButton(bluespawn);
createToolButton(redintel);
createToolButton(blueintel);
createToolButton(NeutralIntel);
createToolButton(IntelSpawn);
createToolButton(RedIntelSpawn);
createToolButton(BlueIntelSpawn);
createToolButton(redteamgate);
createToolButton(redteamgate2);
createToolButton(blueteamgate);
createToolButton(blueteamgate2);
createToolButton(redintelgate);
createToolButton(redintelgate2);
createToolButton(blueintelgate);
createToolButton(blueintelgate2);
createToolButton(intelgatevertical);
createToolButton(intelgatehorizontal);
createToolButton(bulletwall);
createToolButton(bulletwall_horizontal);
createToolButton(playerwall);
createToolButton(playerwall_horizontal);
createToolButton(killbox);
createToolButton(fragbox);
createToolButton(spawnroom);
/*
createToolButton(map_end_trigger_red);
createToolButton(map_end_trigger_blu);
createToolButton(map_end_trigger_red_back);
createToolButton(map_end_trigger_blu_back);
*/
createToolButton(medCabinet);
createToolButton(controlPoint1);
createToolButton(controlPoint2);
createToolButton(controlPoint3);
createToolButton(controlPoint4);
createToolButton(controlPoint5);
createToolButton(NextAreaO);
createToolButton(CapturePoint);
createToolButton(SetupGate);


// createSpecifyButton(timer);
/*
createToolButton(gameMode_ctf);
createToolButton(gameMode_vip);
createToolButton(vip_start);
createToolButton(vip_end);
createToolButton(medCabinet);
createSpecifyButton(Teleports);
*/

_buttony = 0;
_buttonx = 0;
_toolbarWidth = global.width;

with(Button) {
  relative_y = other._buttony
  relative_x = other._buttonx
  other._buttonx += 45;
  if(other._buttonx >= 45 * other._toolbarWidth) {
    other._buttonx = 0;
    other._buttony += 45;
  }
}
}