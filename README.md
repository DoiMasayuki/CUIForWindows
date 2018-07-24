# CUIForWindows
CUI command for Windows. When linux command inputs, this program execute the linux command **without `wsl`**.

# Example
## Input
Enviroment : Windows10
```
find ./src | xargs grep main
```
## Output
```
./src/main/java/cui/CUI.java:	public static void main(String[] args) {
```
Executing `wsl find ./src | wsl xargs grep main` internally.


# Enviroment
OS is Windows10.
Your PC need to be able to use `wsl` command.
