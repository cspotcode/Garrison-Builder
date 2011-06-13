The Garrison Builder and GG2DLL are licensed under the GNU GPLv3.  See GPL.txt
for details.

This repository is split into 4 main directories:
GMGG2GarrisonBuilder
* Contains the sources for the Garrison Builder Game Maker project.  These
sources are split up for Git using Medo42's Gmk Splitter.  You can download it
here:
https://github.com/Medo42/Gmk-Splitter

GG2DLL
* Sources for the GG2 DLL.  This DLL allows the Garrison Builder and Gang
Garrison to compute the MD5 sum of files, embed and extract map data from PNG
files, and perform a few other miscellaneous operations on PNG images.  This
directory contains not only the C++ source files but also the Code::Blocks
project files to build the DLL.

GG2DLLext
* Contains sources used to make a Game Maker .gex extension from the DLL.  See
GG2DLLext/Readme.txt.

netbeans-platform
* Contains sources for the new Garrison Builder, rewritten in Java, built on
top of the Netbeans Platform.