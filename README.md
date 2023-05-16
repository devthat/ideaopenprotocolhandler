# IDEA Open Protocol Handler

IDEA Open Protocol Handler is an IntelliJ IDEA plugin that allows you to navigate to a specific line in a file within
your opened project, directly from the command line interface. This plugin enhances the developer experience by
providing a quick and efficient way to navigate through your codebase.

## Installation

### Manual Installation from Source

1. Clone this repository:
   git clone https://github.com/devthat/ideaopenprotocolhandler.git

2. Open the project in IntelliJ IDEA

4. Configure the artifact for the project:
    - Open `File -> Project Structure -> Artifacts`.
    - Click the `+` button, choose `JAR -> From modules with dependencies`.
    - Select your main class and specify the JAR file name. Click `OK`.
    - Expand the artifact tree and find the `META-INF` directory.
    - Right-click on `META-INF`, select `Add Copy of...`, navigate to your `plugin.xml`, select it, and click `OK`.
    - Ensure your `plugin.xml` is now under `META-INF` in the artifact configuration.

4. Build the project using IntelliJ IDEA's built-in build tools:
   File -> Build Project

5. Build the plugin .jar artifact:
   Build -> Build Artifacts -> Build

6. You should now have a .jar file in the out/artifacts/ideaopenprotocolhandler_jar/ directory.

### Installing the Plugin in IntelliJ IDEA

1. Open IntelliJ IDEA and navigate to Preferences -> Plugins.
2. Click on the Install plugin from disk... button and select the built .jar file.
3. Restart IntelliJ IDEA.

## Usage

Once the plugin is installed, you can use the following command in your terminal to open a specific file at a specified
line:

`open "idea://open?file=YourFileName.java&line=10"`

Replace YourFileName.java with the name of the file you want to open and 10 with the line number you want to navigate
to.

## Contributing

We welcome contributions to the IDEA Open Protocol Handler plugin. If you want to contribute:

1. Fork this repository.
2. Create your feature branch: git checkout -b feature/your-feature.
3. Commit your changes: git commit -am 'Add some feature'.
4. Push to the branch: git push origin feature/your-feature.
5. Submit a pull request.

## License

IDEA Open Protocol Handler is open source software licensed under the MIT license.

## Disclaimer

This plugin is a community project and is not officially associated with JetBrains or IntelliJ IDEA. Use it at your own
risk.
