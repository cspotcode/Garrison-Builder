# Hacky script I used to pull images and origins from split GameMaker GB into entitytypes.json
# Not well-written
# specific use case is not obvious or very common

import re
import shutil

entityDir = "GMGG2GarrisonBuilder/Source/Sprites/Entities"
guiDir = "GMGG2GarrisonBuilder/Source/Sprites/GUI"

f = open("entitytypes.json")
lines = f.read().split("\n")
f.close()

f = open("entitytypesNew.json", "w")

for line in lines:
  match = re.match("(\s*)mapImage: \"(.*)(\.png\",)", line)
  if match != None:
    f.write('%smapImage: %s_map%s\n' % (match.group(1), match.group(2), match.group(3)))
    imageFilename = match.group(2) + '_map.png'
    shutil.copyfile("%s/%s_ent.images/image 0.png" % (entityDir, match.group(2)), imageFilename)
    for l in open("%s/%s_ent.xml" % (entityDir, match.group(2))):
      m = re.match("\s*<origin x=\"(\d+)\" y=\"(\d+)\"/>", l)
      if m != None:
        f.write('%smapImageOrigin: {x: "%s", y: "%s"},\n' % (match.group(1), m.group(1), m.group(2)))
        break
    continue
  
  match = re.match("(\s*)paletteImage: \"(.*)\.png\"", line)
  if match != None:
    f.write('%spaletteImage: %s_palette.png"\n' % (match.group(1), match.group(2)))
    shutil.copyfile("%s/%s.images/image 0.png" % (guiDir, match.group(2)), "%s_palette.png" % match.group(2))
    continue
  f.write(line + "\n")