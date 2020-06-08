Sonar FPGA Metrics Plugin
==========

A SonarQube plugin allowing to create custom metrics and assign them values from external files.

### Building and installing

Build the plugin with :

mvn clean package

Copy the generated jar file from /target to "Sonarqube server path"/extensions/plugins , then launch Sonarqube.

### Usage

All custom metrics should be defined in a json file. The absolute path to this file can be defined in Sonarqube web interface, in the plugin's properties (Administration tab). Sonarqube server needs to be restarted when this file is modified, in order for the changes to apply.
Measures for each custom metric should be defined in a file named measures.json at the root of the analyzed project, when they relate to a project. When measures relate to a single file, they should be in a json file named [Source file name without extension]_measures.json, in the same folder as the corresponding file. Measures will be imported in Sonarqube user interface after executing sonar-scanner. 