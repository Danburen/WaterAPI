# WaterAPI
![bg_middle](https://github.com/user-attachments/assets/c1f25cc1-48b5-4896-b88e-61741e4e2da7)

[![](https://www.jitpack.io/v/Danburen/WaterAPI.svg)](https://www.jitpack.io/#Danburen/WaterAPI)
![](https://img.shields.io/github/v/release/Danburen/WaterAPI)

## 🤠 Introduction
  A useful tool to build up Minecraft Java Plugin in Velocity and bukkit plugin.
  Including serveral plugin hocks like [LuckPerms](https://luckperms.net/)

## 🌟 Features
* Inherit and refactor [JavaPlugin](https://bukkit.windit.net/javadoc/org/bukkit/plugin/java/JavaPlugin.html)
* For [Velocity](https://papermc.io/software/velocity) and other platform we use [WaterPlugin](https://github.com/Danburen/WaterAPI/blob/main/src/main/java/org/waterwood/plugin/WaterPlugin.java) to start up
* Auto updater plugin and automatically get Changelog from your repositors
* Read local file more easily.
* Colorful logger infos
* i18n is support

## 👉 Getting Start
* import WaterApi to your project
* Simply let your main class inherit [WaterPlugin](https://github.com/Danburen/WaterAPI/blob/main/src/main/java/org/waterwood/plugin/WaterPlugin.java) / [BukkitPlugin](https://github.com/Danburen/WaterAPI/blob/main/src/main/java/org/waterwood/plugin/bukkit/BukkitPlugin.java)
* while you build the artifacts don't forget to put WaterAPI to the artifact

## 📖 Docs(Constructing)

## 🧱 How to build
* Java SDK 17 or newer
*	dependency:
	```Gradle:
	dependencies {
		implementation 'com.github.Danburen:WaterAPI:-SHAPSHOT'
	}
	```

	#### 1. Improt the project as sub module
	```git
	git submodule add https://github.com/Danburen/WaterAPI.git path/to/submodule
	git submodule init
	git submodule update
	```
	setting.gradle
	```gradle
	include ':path-to-submodule'
	```
	build.gradle
	```gradle
	dependencies {
    	implementation project(':path-to-submodule')
	}
	```
	#### 2. build with jitpack:
	* [JitPack](https://www.jitpack.io/#Danburen/WaterAPI)
	```Gradle:
	dependencyResolutionManagement {
		repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
		repositories {
			mavenCentral()
			maven { url 'https://www.jitpack.io' }
		}
	}
	```

## ❤️ Support us

[![](https://pic1.afdiancdn.com/static/img/welcome/button-sponsorme.png)](https://afdian.com/a/WaterWood/plan)
