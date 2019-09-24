When we have a maven not able connect to adobe-public repository when we try to create a new AEM project using  
below basic command:

  mvn archetype:generate \
     -DarchetypeGroupId=com.adobe.granite.archetypes \
     -DarchetypeArtifactId=aem-project-archetype \
     -DarchetypeVersion=20-SANAPSHOT
Then please use this command as it can connect to adobe-public repo directly:

 mvn org.apache.maven.plugins:maven-archetype-plugin:2.4:generate\
	  -DarchetypeGroupId=com.adobe.granite.archetypes\
    -DarchetypeArtifactId=aem-project-archetype\ 
    -DarchetypeVersion=20 -DarchetypeCatalog=https://repo.adobe.com/nexus/content/groups/public/



