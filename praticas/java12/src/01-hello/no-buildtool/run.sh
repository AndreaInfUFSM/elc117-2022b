# Instructions: https://openjfx.io/openjfx-docs/#install-javafx
# wget https://download2.gluonhq.com/openjfx/19/openjfx-19_linux-x64_bin-sdk.zip
# unzip openjfx-19_linux-x64_bin-sdk.zip

javac --module-path ./javafx-sdk-19/lib --add-modules javafx.controls HelloFX.java

java --module-path ./javafx-sdk-19/lib --add-modules javafx.controls HelloFX
