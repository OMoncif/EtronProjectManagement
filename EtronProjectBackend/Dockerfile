# Utilisez une image OpenJDK 17 comme base
FROM openjdk:17

# Spécifiez un répertoire de travail
WORKDIR /app

# Copiez le code source de votre projet dans le conteneur
COPY target/EtronProject5-0.0.1-SNAPSHOT.jar myEtronProject.jar  

# Exécutez votre application Spring Boot directement
CMD ["java","-jar", "myEtronProject.jar"]  