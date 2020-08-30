When we have a maven not able connect to adobe-public repository when we try to create a new AEM project using  
below basic command (maven archetype:generate):

  mvn archetype:generate \
     -DarchetypeGroupId=com.adobe.granite.archetypes \
     -DarchetypeArtifactId=aem-project-archetype \
     -DarchetypeVersion=20-SANAPSHOT
*************************************************************************************************************************     
We get below error:
[ERROR] Failed to execute goal org.apache.maven.plugins:maven-archetype-plugin:3.1.0:generate (default-cli) on project standalone-pom: The desired archetype does not exist (com.adobe.granite.archetypes:aem-project-archetype:20-SNAPSHOT) 

Reason either missing adobe public repository entries in settings.xml file or maven not reading the settings.xml file entries 
************************************************************************************************************************* 
  
FIX for this issue:--------  
Then please use this command as it can connect to adobe-public repo directly:

mvn org.apache.maven.plugins:maven-archetype-plugin:2.4:generate \
-DarchetypeGroupId=com.adobe.granite.archetypes \
-DarchetypeArtifactId=aem-project-archetype \
-DarchetypeVersion=20 -DarchetypeCatalog=https://repo.adobe.com/nexus/content/groups/public/

For generating Weekend Project using commond line use the below command

mvn org.apache.maven.plugins:maven-archetype-plugin:2.4:generate -DarchetypeGroupId=com.adobe.granite.archetypes -DarchetypeArtifactId=aem-project-archetype -DarchetypeVersion=22 -DgroupId=com.adobe.aem.guides -Dversion=0.0.1-SNAPSHOT -DappsFolderName=wknd -DartifactId=aem-guides-wknd -Dpackage=com.adobe.aem.guides.wknd -DartifactName="WKND Sites Project" -DcomponentGroupName=WKND -DconfFolderName=wknd -DcontentFolderName=wknd -DcssId=wknd -DisSingleCountryWebsite=n -Dlanguage_country=en_us -DoptionAemVersion=6.5.0 -DoptionDispatcherConfig=none -DoptionIncludeErrorHandler=n -DoptionIncludeExamples=y -DoptionIncludeFrontendModule=y -DpackageGroup=wknd -DsiteName="WKND Site"  -DarchetypeCatalog=https://repo.adobe.com/nexus/content/groups/public/


*************************************************************************************************************************
When I run 
mvn clean install -PautoInstallPackage I get below error: To solve it use --->  mvn -PautoInstallPackage -Padobe-public clean install

[ERROR] Failed to execute goal org.apache.maven.plugins:maven-archetype-plugin:3.0.1:generate (default-cli) on project standalone-pom: The desired archetype does not exist

OR these ERRORS as well

[ERROR] The build could not read 1 project -> [Help 1]
[ERROR]
[ERROR]   The project com.coral.ui.components:coral-ui-components.ui.apps:1.0-SNAPSHOT (C:\Users\svemalal\Documents\AEM-upgrade\CoralUI-component-customisation\coral-ui-components-master\ui.apps\pom.xml) has 2 errors
[ERROR]     Unresolveable build extension: Plugin com.day.jcr.vault:content-package-maven-plugin:0.5.1 or one of its dependencies could not be resolved: Failure to find com.day.jcr.vault:content-package-maven-plugin:jar:0.5.1 in https://repo.maven.apache.org/maven2 was cached in the local repository, resolution will not be reattempted until the update interval of central has elapsed or updates are forced -> [Help 2]
[ERROR]     Unknown packaging: content-package @ line 34, column 16
[ERROR]
[ERROR] To see the full stack trace of the errors, re-run Maven with the -e switch.
[ERROR] Re-run Maven using the -X switch to enable full debug logging.
[ERROR]
[ERROR] For more information about the errors and possible solutions, please read the following articles:
[ERROR] [Help 1] http://cwiki.apache.org/confluence/display/MAVEN/ProjectBuildingException
[ERROR] [Help 2] http://cwiki.apache.org/confluence/display/MAVEN/PluginManagerException

FIX for the issue --
Run this command
 mvn -PautoInstallPackage -Padobe-public clean install
*************************************************************************************************************************
 
