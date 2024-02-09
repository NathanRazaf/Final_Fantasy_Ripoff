# FINAL FANTASY RIPOFF

As its name implies, it's my own personal take on the Final Fantasy games. It provides the most basic features in a RPG game :
- A character creation system (you create a team of 4 characters, and you choose their class between **Warrior**, **Archer** and **Wizard**) 👯👯‍♂️
- A turn-based combat system against random enemies, which reward you with experience, loot and money 🔥
- A steady level up system to keep the game fresh 🔝
- A shop system to sell your loot and buy equipment for your team 🛒
- An inventory system to let you equip your team with the best loot ⏫
- An inn in which your team can heal and restore themselves between fight in exchange for some money 💸

And all of that with an automatic save file that lets you store up to *3* game states!

This game is in hardcore mode, which means *every hero who dies, **definitely* dies*. So do your best to make your whole team survive each fierce battle !

Since there's no story mode whatsoever, the only challenge you can do is try to fight and survive until reaching level 99. Good luck ! (you'll need quite a bit of it)

The game is available in 2 versions :

| Version | Instructions |
| ------- | ------------ |
| Command_Line_Interface | You just have to have Java 21 and later installed on your computer, then run `java -jar Final_Fantasy_CLI.jar` in your terminal, in the directory where you put the **.jar** file |
| Graphical_User_Interface | You also need Java 21 or later, and you must must also download and extract JavaFX in a known location in your computer, then open the **Final_Fantasy_GUI.jar** file on IntelliJ so that you can configure the VM options : `Run >> Edit Configurations... >> Select Final_Fantasy_GUI.jar >> VM options`, in which you must add the following line : `--module-path /path/to/javafx/lib --add-modules=javafx.controls`, where path/to/javafx/lib is the path to the **lib** directory of JavaFX which you downloaded on you computer. Now, you should be able to launch the app by selecting the .jar on the top menu near the green play button |

The save file of the game must always be in the same directory as the game itself, so if you try to move the game in another directory, you'll lose all progress until you put it back with the generated **gameState.ser** file.

I hope you have fun with this game !
