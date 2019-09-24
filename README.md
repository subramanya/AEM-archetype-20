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

 mvn org.apache.maven.plugins:maven-archetype-plugin:2.4:generate\
	  -DarchetypeGroupId=com.adobe.granite.archetypes\
    -DarchetypeArtifactId=aem-project-archetype\ 
    -DarchetypeVersion=20 -DarchetypeCatalog=https://repo.adobe.com/nexus/content/groups/public/



