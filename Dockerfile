# Utilisation de Tomcat 10.1 avec JDK 21 (version slim pour une image plus légère)
FROM tomcat:10.1-jdk21-temurin-jammy

# Suppression des applications par défaut de Tomcat (optionnel mais recommandé)
RUN rm -rf /usr/local/tomcat/webapps/*

# Copie du fichier WAR généré par NetBeans
# Le fichier est copié en tant que ROOT.war pour être accessible directement à la racine
COPY dist/PastDoc.war /usr/local/tomcat/webapps/ROOT.war

# Exposition du port par défaut de Tomcat
EXPOSE 8080

# Commande de démarrage de Tomcat
CMD ["catalina.sh", "run"]